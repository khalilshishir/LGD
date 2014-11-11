package procurement.up.communityprocurement

import comonclass.UpProcType
import org.springframework.dao.DataIntegrityViolationException
import settings.SchemeInfo

class MasterRoleSalaryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [masterRoleSalaryInstanceList: MasterRoleSalary.list(params), masterRoleSalaryInstanceTotal: MasterRoleSalary.count()]
    }

    def create() {
        [masterRoleSalaryInstance: new MasterRoleSalary(params)]
    }

    def save() {
        double grantedAmount = params.grantedAmount?.toDouble()
        println(params);
        def masterRoleSalaryInstance = new MasterRoleSalary()

        masterRoleSalaryInstance.properties["id","schemeInfo"] = params


        int i = 0, grantAmountCounter = 0
        while (params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"] != null && params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"] != "") {

            def masterRoleSalaryDetails = new MasterRoleSalaryDetails()

            masterRoleSalaryDetails.properties['LABOUR_NAME']=params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"]
            masterRoleSalaryDetails.properties['LABOUR_ADDRESS']=params["masterRoleSalaryDetails[" + i + "].LABOUR_ADDRESS"]
            masterRoleSalaryDetails.properties['SALARY']=Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].SALARY"])
            masterRoleSalaryDetails.properties['WORK_DAY']=Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].WORK_DAY"])
            masterRoleSalaryDetails.properties['TOTAL_AMOUNT']=Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].TOTAL_AMOUNT"])
            masterRoleSalaryDetails.properties['COMMENTS']=params["masterRoleSalaryDetails[" + i + "].COMMENTS"]



            masterRoleSalaryInstance.addToMasterRoleSalaryDetails(masterRoleSalaryDetails)

            grantAmountCounter += Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].TOTAL_AMOUNT"])


            i++
        }

        if(grantAmountCounter > grantedAmount){
            flash.message = "মাস্টার রোলে মজুরি প্রদানের মোট টাকা স্কীমের অনুমোদিত মূল্য হতে অধিক হতে পারবে না ।"
            render(view: "create", model: [masterRoleSalaryInstance: masterRoleSalaryInstance])
            return
        }

        if (!masterRoleSalaryInstance.save(flush: true)) {
            render(view: "create", model: [masterRoleSalaryInstance: masterRoleSalaryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'masterRoleSalary.label', default: 'Master Role Salary'), masterRoleSalaryInstance.id])
        redirect(action: "show", id: masterRoleSalaryInstance.id)

    }

    def show(Long id) {
        def masterRoleSalaryInstance = MasterRoleSalary.get(id)
        if (!masterRoleSalaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'masterRoleSalary.label', default: 'MasterRoleSalary'), id])
            redirect(action: "list")
            return
        }

        [masterRoleSalaryInstance: masterRoleSalaryInstance]
    }

    def edit(Long id) {
        def masterRoleSalaryInstance = MasterRoleSalary.get(id)
        def schemeInfo = SchemeInfo.get(masterRoleSalaryInstance.schemeInfoId)
        String grantedAmount = schemeInfo.GRANTED_AMOUNT
        def upProcMasterListByProcurement = SchemeInfo.createCriteria();
        def results = upProcMasterListByProcurement.list {
            inList('id',schemeInfo.id)
        }
        if (!masterRoleSalaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'masterRoleSalary.label', default: 'MasterRoleSalary'), id])
            redirect(action: "list")
            return
        }

        [masterRoleSalaryInstance: masterRoleSalaryInstance, upProcMasterList: results, grantedAmount: grantedAmount]
    }

    def update(Long id, Long version) {
        double grantedAmount = params.grantedAmount?.toDouble()
        def masterRoleSalaryInstance = MasterRoleSalary.get(id)
        if (!masterRoleSalaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'masterRoleSalary.label', default: 'Master Role Salary'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (masterRoleSalaryInstance.version > version) {
                masterRoleSalaryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'masterRoleSalary.label', default: 'Master Role Salary')] as Object[],
                        "Another user has updated this Master Role Salary while you were editing")
                render(view: "edit", model: [masterRoleSalaryInstance: masterRoleSalaryInstance])
                return
            }
        }

        masterRoleSalaryInstance.properties["id", "schemeInfo"] = params
        int i = 0, grantAmountCounter = 0
        while (params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"] != null && params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"] != "") {
            def studentDetail
            if (params["masterRoleSalaryDetails[" + i + "].deleted"] == "true" && params["masterRoleSalaryDetails[" + i + "].new"] == "false") {
                studentDetail = MasterRoleSalaryDetails.get(Long.valueOf(params["masterRoleSalaryDetails[" + i + "].id"]))
                masterRoleSalaryInstance.removeFromMasterRoleSalaryDetails(studentDetail)
                studentDetail.delete()
                i++
                continue
            } else if (params["masterRoleSalaryDetails[" + i + "].deleted"] == "true" && params["masterRoleSalaryDetails[" + i + "].new"] == "true") {
                i++
                continue
            } else if (params["masterRoleSalaryDetails[" + i + "].deleted"] == "false" && params["masterRoleSalaryDetails[" + i + "].new"] == "true") {
                studentDetail = new MasterRoleSalaryDetails()
                studentDetail.properties['LABOUR_NAME'] = params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"]
            } else {
                studentDetail = MasterRoleSalaryDetails.get(Long.valueOf(params["masterRoleSalaryDetails[" + i + "].id"]))
            }

            studentDetail.properties['LABOUR_NAME']=params["masterRoleSalaryDetails[" + i + "].LABOUR_NAME"]
            studentDetail.properties['LABOUR_ADDRESS']=params["masterRoleSalaryDetails[" + i + "].LABOUR_ADDRESS"]
            studentDetail.properties['SALARY']=Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].SALARY"])
            studentDetail.properties['WORK_DAY']=Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].WORK_DAY"])
            studentDetail.properties['TOTAL_AMOUNT']=Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].TOTAL_AMOUNT"])
            studentDetail.properties['COMMENTS']=params["masterRoleSalaryDetails[" + i + "].COMMENTS"]
            masterRoleSalaryInstance.addToMasterRoleSalaryDetails(studentDetail)

            grantAmountCounter += Double.parseDouble(params["masterRoleSalaryDetails[" + i + "].TOTAL_AMOUNT"])
            i++
        }

        if(grantAmountCounter > grantedAmount){
            flash.message = "মাস্টার রোলে মজুরি প্রদানের মোট টাকা স্কীমের অনুমোদিত মূল্য হতে অধিক হতে পারবে না ।"
            def schemeInfo = SchemeInfo.get(masterRoleSalaryInstance?.schemeInfoId)
            def upProcMasterListByProcurement = SchemeInfo.createCriteria();
            def results = upProcMasterListByProcurement.list {
                inList('id',schemeInfo.id)
            }
            render(view: "edit", model: [masterRoleSalaryInstance: masterRoleSalaryInstance, upProcMasterList: results, grantedAmount: grantedAmount])
            return
        }

        if (!masterRoleSalaryInstance.save(flush: true)) {
            render(view: "edit", model: [masterRoleSalaryInstance: masterRoleSalaryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'masterRoleSalary.label', default: 'Master Role Salary'), masterRoleSalaryInstance.id])
        redirect(action: "show", id: masterRoleSalaryInstance.id)

    }

    def delete(Long id) {
        def masterRoleSalaryInstance = MasterRoleSalary.get(id)
        if (!masterRoleSalaryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'masterRoleSalary.label', default: 'MasterRoleSalary'), id])
            redirect(action: "list")
            return
        }

        try {
            masterRoleSalaryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'masterRoleSalary.label', default: 'MasterRoleSalary'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'masterRoleSalary.label', default: 'MasterRoleSalary'), id])
            redirect(action: "show", id: id)
        }
    }

    def setValueForGrantedAmount(){
        String grantedAmount = ""
        if(params.schemeInfo != null && params.schemeInfo != "" && params.schemeInfo != "null"){
            def schemeInfo = SchemeInfo.get(params.schemeInfo)
            grantedAmount = schemeInfo.GRANTED_AMOUNT
        }
        render (template: 'grantedAmount', model: [grantedAmount: grantedAmount])
    }
}
