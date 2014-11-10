package procurement.up.communityprocurement

import comonclass.UpProcType
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import procurement.pmu.PmuProcPlan
import procurement.pmu.PmuProcPlanWbs
import procurement.up.Procurement_Type.CommonService
import procurement.up.Up_Proc_Master
import settings.SchemeInfo

import java.text.SimpleDateFormat

class AdvanceAdjustmentController {
    CommonService commonService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [advanceAdjustmentInstanceList: AdvanceAdjustment.list(params), advanceAdjustmentInstanceTotal: AdvanceAdjustment.count()]
    }

    def create() {
//        def upProcMasterListByProcurement = Up_Proc_Master.findAllByProcurementType(UpProcType?.COMMUNITY_PROCUREMENT?.toString())
//        [advanceAdjustmentInstance: new AdvanceAdjustment(params), upProcMasterList: upProcMasterListByProcurement]
        [advanceAdjustmentInstance: new AdvanceAdjustment(params)]
    }

    def save() {
        double totalWagesPaidAmount = 0, totalAdvanceAdjustmentAmount = 0
        def advanceAdjustmentInstance = new AdvanceAdjustment(params)
//        def schemeInfo = SchemeInfo.get(advanceAdjustmentInstance?.schemeInfo?.id)
//        def masterRoleSalary = MasterRoleSalary.findBySchemeInfo(advanceAdjustmentInstance?.schemeInfo)
        def masterRoleSalary = MasterRoleSalary.findAllBySchemeInfo(advanceAdjustmentInstance?.schemeInfo)
        masterRoleSalary.each {
            it.masterRoleSalaryDetails.each {
                totalWagesPaidAmount += it.TOTAL_AMOUNT
            }
        }

//        if(masterRoleSalary?.masterRoleSalaryDetails?.size() > 0){
//            def masterRoleSalaryDetails = MasterRoleSalaryDetails.findAllByMasterRoleSalary(masterRoleSalary)
//            masterRoleSalaryDetails.each {
//                totalWagesPaidAmount += it.TOTAL_AMOUNT
//            }
//        }
//        def masterRoleSalaryDetails = MasterRoleSalaryDetails.findAllByMasterRoleSalary(masterRoleSalary)
        def advanceAdjustment = AdvanceAdjustment.findAllBySchemeInfo(advanceAdjustmentInstance?.schemeInfo)
//        double totalWagesPaidAmount = 0, totalAdvanceAdjustmentAmount = 0
//        masterRoleSalaryDetails.each {
//            totalWagesPaidAmount += it.TOTAL_AMOUNT
//        }
        advanceAdjustment.each {
            totalAdvanceAdjustmentAmount += it.ADJUSTMENT_AMOUNT
        }

//        println("Total Wages Paid >> " + totalWagesPaidAmount)
//        println("Total Advance Adjustment Amount >> " + totalAdvanceAdjustmentAmount)
//        println("Cash in Hand to pay wages >> " + (totalAdvanceAdjustmentAmount - totalWagesPaidAmount))

        if(totalAdvanceAdjustmentAmount > totalWagesPaidAmount){
            flash.message = "দুঃখিত, অগ্রিম প্রাপ্ত টাকা এখনো মজুরি দেয়ার জন্য হাতে আছে, তাই নতুন করে অগ্রিম সমন্বয় সম্ভব নয় ।"
            def upProcMasterListByProcurement = Up_Proc_Master.findAllByProcurementType(UpProcType?.COMMUNITY_PROCUREMENT?.toString())
            render(view: "create", model: [advanceAdjustmentInstance: advanceAdjustmentInstance, upProcMasterList: upProcMasterListByProcurement])
            return
        }

        if (!advanceAdjustmentInstance.save(flush: true)) {
            render(view: "create", model: [advanceAdjustmentInstance: advanceAdjustmentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), advanceAdjustmentInstance.id])
        redirect(action: "show", id: advanceAdjustmentInstance.id)
    }

    def show(Long id) {
        def advanceAdjustmentInstance = AdvanceAdjustment.get(id)
        if (!advanceAdjustmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), id])
            redirect(action: "list")
            return
        }

        [advanceAdjustmentInstance: advanceAdjustmentInstance]
    }

    def edit(Long id) {
        def advanceAdjustmentInstance = AdvanceAdjustment.get(id)
//        def upProcMaster = Up_Proc_Master.get(advanceAdjustmentInstance?.schemeInfo?.id)
//        def upProcMasterListByProcurement = Up_Proc_Master.createCriteria();
//        def results = upProcMasterListByProcurement.list {
//            inList('id',upProcMaster.id)
//        }
        if (!advanceAdjustmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), id])
            redirect(action: "list")
            return
        }

//        [advanceAdjustmentInstance: advanceAdjustmentInstance, upProcMasterList: results]
        [advanceAdjustmentInstance: advanceAdjustmentInstance]
    }

    def update(Long id, Long version) {
        def advanceAdjustmentInstance = AdvanceAdjustment.get(id)
        if (!advanceAdjustmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (advanceAdjustmentInstance.version > version) {
                advanceAdjustmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment')] as Object[],
                        "Another user has updated this AdvanceAdjustment while you were editing")
                render(view: "edit", model: [advanceAdjustmentInstance: advanceAdjustmentInstance])
                return
            }
        }

        advanceAdjustmentInstance.properties = params

        if (!advanceAdjustmentInstance.save(flush: true)) {
            render(view: "edit", model: [advanceAdjustmentInstance: advanceAdjustmentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), advanceAdjustmentInstance.id])
        redirect(action: "show", id: advanceAdjustmentInstance.id)
    }

    def delete(Long id) {
        def advanceAdjustmentInstance = AdvanceAdjustment.get(id)
        if (!advanceAdjustmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), id])
            redirect(action: "list")
            return
        }

        try {
            advanceAdjustmentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'advanceAdjustment.label', default: 'AdvanceAdjustment'), id])
            redirect(action: "show", id: id)
        }
    }

    def showone(){
//        print('id = ' + params.id)
        LinkedHashMap result=new LinkedHashMap()
        String outPut
        def schemeInfo = SchemeInfo.get(Long.parseLong(params.id))
        def advanceAdjustmentList=AdvanceAdjustment.findAllBySchemeInfo(schemeInfo)

        List dataReturn=new ArrayList()
        advanceAdjustmentList.each {
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            String payment_date = DATE_FORMAT.format(it.PAYMENT_DATE);
//            it.PAYMENT_DATE = payment_date
            dataReturn.add([id: it.id,adjustment_amount: it.ADJUSTMENT_AMOUNT,payment_date: payment_date])
        }

        result.put('isError',false)
        result.put('advanceAdjustment',advanceAdjustmentList)
        result.put('obj',advanceAdjustmentList)
        result.put('dataReturn',dataReturn)
        outPut = result as JSON
        render outPut

    }
}
