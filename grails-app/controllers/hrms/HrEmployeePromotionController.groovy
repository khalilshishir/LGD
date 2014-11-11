package hrms

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrEmployeePromotionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [hrEmployeePromotionInstanceList: HrEmployeePromotion.list(params), hrEmployeePromotionInstanceTotal: HrEmployeePromotion.count()]
    }

    def create() {
        [hrEmployeePromotionInstance: new HrEmployeePromotion(params)]
    }


    def save() {
        params.promotionEffectDate = Util.getDateMonddyyyy(params.promotionEffectDate)
        params.salaryEffectDate = Util.getDateMonddyyyy(params.salaryEffectDate)
        def hrEmployeePromotionInstance = new HrEmployeePromotion(params)

        /*// start update HR_EMPLOYEE table using By Promotion data
        def hrDesignation = HrDesignation.findById(hrEmployeePromotionInstance.designationId)
        def hrEmployee = HrEmployee.findById(hrEmployeePromotionInstance.employeeIdHrEmployee.id)
        hrEmployee.setDesignationIdHrDesignation(hrDesignation)
        hrEmployee.save(flash:true)
        // end update HR_EMPLOYEE table using By Promotion data

        // start update HR_EMPLOYEE_PAY_STRUCTURE table using By Promotion data
        def  hrEmployeePayStructureInstance = HrEmployeePayStructure.findByEmployeeIdHrEmployee(hrEmployeePromotionInstance.employeeIdHrEmployee)
        hrEmployeePayStructureInstance.properties['payScaleId'] = Integer.valueOf(params.payScaleId)
        hrEmployeePayStructureInstance.properties['stage'] = params.stage
        hrEmployeePayStructureInstance.properties['pBasic'] = Float.valueOf(params.pBasic)
        hrEmployeePayStructureInstance.properties['pDa'] = Float.valueOf(params.pDa)
        hrEmployeePayStructureInstance.properties['pHr'] = Float.valueOf(params.pHrent)
        hrEmployeePayStructureInstance.properties['pConveyance'] = Float.valueOf(params.pConveyanceAllowance)
        hrEmployeePayStructureInstance.properties['pWashingAllow'] = Float.valueOf(params.pWashingAllowance)
        hrEmployeePayStructureInstance.properties['pMedicalAllowance'] = Float.valueOf(params.pMedicalAllowance)
        hrEmployeePayStructureInstance.properties['pOrgPfContribution'] = Float.valueOf(params.pPf)
        // end update HR_EMPLOYEE_PAY_STRUCTURE table using By Promotion data*/

        if (!hrEmployeePromotionInstance.save(flush: true)) {
            render(view: "create", model: [hrEmployeePromotionInstance: hrEmployeePromotionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), hrEmployeePromotionInstance.id])
        redirect(action: "show", id: hrEmployeePromotionInstance.id)
    }

    def show() {
        def hrEmployeePromotionInstance = HrEmployeePromotion.get(params.id)
        if (!hrEmployeePromotionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeePromotionInstance: hrEmployeePromotionInstance]
    }

    def edit() {
        def hrEmployeePromotionInstance = HrEmployeePromotion.get(params.id)
        def grossSalary = getGrossSalary(hrEmployeePromotionInstance.employeeIdHrEmployee.id)
        def stage = getGradeStage(hrEmployeePromotionInstance.employeeIdHrEmployee.id)
        if (!hrEmployeePromotionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeePromotionInstance: hrEmployeePromotionInstance, grossSalary: grossSalary, presentStage: stage]
    }

    def update() {
        def hrEmployeePromotionInstance = HrEmployeePromotion.get(params.id)
        if (!hrEmployeePromotionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrEmployeePromotionInstance.version > version) {
                hrEmployeePromotionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion')] as Object[],
                        "Another user has updated this HrEmployeePromotion while you were editing")
                render(view: "edit", model: [hrEmployeePromotionInstance: hrEmployeePromotionInstance])
                return
            }
        }
        params.promotionEffectDate = Util.getDateMonddyyyy(params.promotionEffectDate)
        params.salaryEffectDate = Util.getDateMonddyyyy(params.salaryEffectDate)
        hrEmployeePromotionInstance.properties = params

        if (!hrEmployeePromotionInstance.save(flush: true)) {
            render(view: "edit", model: [hrEmployeePromotionInstance: hrEmployeePromotionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), hrEmployeePromotionInstance.id])
        redirect(action: "show", id: hrEmployeePromotionInstance.id)
    }

    def delete() {
        def hrEmployeePromotionInstance = HrEmployeePromotion.get(params.id)
        if (!hrEmployeePromotionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrEmployeePromotionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmployeePromotion.label', default: 'HrEmployeePromotion'), params.id])
            redirect(action: "show", id: params.id)
        }
    }


    // ------- start for data retrieved from another table by using Employee Name-------------------------
    def getEmpInfo()
    {
        //def idObj = HrEmployee.get(params.idNo)
        def idObj = HrEmployee.findByEmployeeName(params.idNo)

        def data1 = idObj.cardNo
        //System.out.println("data1 :"+ data1)

        def data2 = idObj.designationIdHrDesignation.designationName
        //System.out.println("data2 : "+ data2)

        def data12 = idObj.designationIdHrDesignation.id
        //System.out.println("data12 : "+ data12)

        def data3 = idObj.departmentIdHrDepartment.departmentName
        //System.out.println("data3 : "+ data3)

        //def data4 = idObj.designationIdHrDesignation.hrPayscaleList.gradeNo
        //System.out.println("data4 : "+ data4)

        //def data11 = idObj.designationIdHrDesignation.hrPayscaleList.id
        //System.out.println("data11 : "+ data11)

        def data11 = idObj.hrEmployeePayStructure.payScaleId
        //System.out.println("payScaleIdNo : "+ data11)

        def data4 = HrPayscale.get(data11)
        //System.out.println("data4 : "+ data4)


        def data5 = idObj.joiningDate.toString()
        //System.out.println("data5 : "+ data5)

        def data6 = idObj.confirmationDate.toString()
        //System.out.println("data6 : "+ data6)

        def data7 = idObj.employeeTypeIdHrLookup.lookupValue
        //System.out.println("data7 : "+ data7)

        def data8 = idObj.presentStatusIdHrLookup.lookupValue
        //System.out.println("data8 : "+ data8)

        def data9 = idObj.hrEmployeePayStructure.stage
        //System.out.println("data9 : "+ data9)

        def data10 = (idObj.hrEmployeePayStructure.pBasic+idObj.hrEmployeePayStructure.pDa+
                idObj.hrEmployeePayStructure.pHr+idObj.hrEmployeePayStructure.pConveyance+
                idObj.hrEmployeePayStructure.pWashingAllow+idObj.hrEmployeePayStructure.pMedicalAllowance+
                idObj.hrEmployeePayStructure.pOrgPfContribution).toFloat().toString()
        //System.out.println("data10 : "+ data10)


        def data13 = idObj.id.toString()
        //System.out.println("data13 : "+ data13)

        // employees current salary structure
        def presentBasic = idObj.hrEmployeePayStructure.pBasic.toString()
        def presentDa = idObj.hrEmployeePayStructure.pDa.toString()
        def presentHr = idObj.hrEmployeePayStructure.pHr.toString()
        def presentConveyance = idObj.hrEmployeePayStructure.pConveyance.toString()
        def presentWashingAllow = idObj.hrEmployeePayStructure.pWashingAllow.toString()
        def presentMedicalAllow = idObj.hrEmployeePayStructure.pMedicalAllowance.toString()
        def presentOrgPf = idObj.hrEmployeePayStructure.pOrgPfContribution.toString()


        //edited By Maruf    Start ----
        def data14 = idObj.hrEmployeePayStructure.spAllowance
        def data15 = idObj.hrEmployeePayStructure.spDaAllowance
        def data16 = idObj.hrEmployeePayStructure.addSpAllowance
        def data17 = idObj.hrEmployeePayStructure.spAllowance
        def data18 = idObj.hrEmployeePayStructure.spDaAllowance
        def data19 = idObj.hrEmployeePayStructure.addSpAllowance
        //edited By Maruf    End -----

        def data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+data8+":"+data9+":"+data10+":"+
                presentBasic+":"+presentDa+":"+presentHr+":"+presentConveyance+":"+presentWashingAllow+":"+presentMedicalAllow+":"+presentOrgPf+":"+
                data11.toString()+":"+data12.toString()+":"+data13+":"+data14+":"+data15+":"+data16+":"+data17+":"+data18+":"+data19

        //System.out.println("Total data= "+ data)

        render new WSReturn(100, data , null) as JSON
        // ------- end for data retrived from another table -------------------------
    }


    // ------- start for data retrieved from another table by using Employee CardNo-------------------------
    def getEmpInfoByCardNo()
    {
        //def idObj = HrEmployee.get(params.idNo)
        def idObj = HrEmployee.findByCardNo(params.idNo)

        def data1 = idObj.employeeName
        //System.out.println("data1 :"+ data1)

        def data2 = idObj.designationIdHrDesignation.designationName
        //System.out.println("data2 : "+ data2)

        def data12 = idObj.designationIdHrDesignation.id
        //System.out.println("data12 : "+ data12)

        def data3 = idObj.departmentIdHrDepartment.departmentName
        //System.out.println("data3 : "+ data3)

        //def data4 = idObj.designationIdHrDesignation.hrPayscaleList.gradeNo
        //System.out.println("data4 : "+ data4)

        //def data11 = idObj.designationIdHrDesignation.hrPayscaleList.id
        //System.out.println("data11 : "+ data11)

        def data11 = idObj.hrEmployeePayStructure.payScaleId
        //System.out.println("payScaleIdNo : "+ data11)

        def data4 = HrPayscale.get(data11)
        //System.out.println("data4 : "+ data4)


        def data5 = idObj.joiningDate.toString()
        //System.out.println("data5 : "+ data5)

        def data6 = idObj.confirmationDate.toString()
        //System.out.println("data6 : "+ data6)

        def data7 = idObj.employeeTypeIdHrLookup.lookupValue
        //System.out.println("data7 : "+ data7)

        def data8 = idObj.presentStatusIdHrLookup.lookupValue
        //System.out.println("data8 : "+ data8)

        def data9 = idObj.hrEmployeePayStructure.stage
        //System.out.println("data9 : "+ data9)

        def data10 = (idObj.hrEmployeePayStructure.pBasic+idObj.hrEmployeePayStructure.pDa+
                idObj.hrEmployeePayStructure.pHr+idObj.hrEmployeePayStructure.pConveyance+
                idObj.hrEmployeePayStructure.pWashingAllow+idObj.hrEmployeePayStructure.pMedicalAllowance+
                idObj.hrEmployeePayStructure.pOrgPfContribution).toFloat().toString()
        //System.out.println("data10 : "+ data10)


        def data13 = idObj.id.toString()
        //System.out.println("data13 : "+ data13)

        // employees current salary structure
        def presentBasic = idObj.hrEmployeePayStructure.pBasic.toString()
        def presentDa = idObj.hrEmployeePayStructure.pDa.toString()
        def presentHr = idObj.hrEmployeePayStructure.pHr.toString()
        def presentConveyance = idObj.hrEmployeePayStructure.pConveyance.toString()
        def presentWashingAllow = idObj.hrEmployeePayStructure.pWashingAllow.toString()
        def presentMedicalAllow = idObj.hrEmployeePayStructure.pMedicalAllowance.toString()
        def presentOrgPf = idObj.hrEmployeePayStructure.pOrgPfContribution.toString()

        //edited By Maruf    Start ----
        def data14 = idObj.hrEmployeePayStructure.spAllowance
        def data15 = idObj.hrEmployeePayStructure.spDaAllowance
        def data16 = idObj.hrEmployeePayStructure.addSpAllowance
        def data17 = idObj.hrEmployeePayStructure.spAllowance
        def data18 = idObj.hrEmployeePayStructure.spDaAllowance
        def data19 = idObj.hrEmployeePayStructure.addSpAllowance
        //edited By Maruf    End -----

        def data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+data8+":"+data9+":"+data10+":"+
                presentBasic+":"+presentDa+":"+presentHr+":"+presentConveyance+":"+presentWashingAllow+":"+presentMedicalAllow+":"+presentOrgPf+":"+
                data11.toString()+":"+data12.toString()+":"+data13+":"+data14+":"+data15+":"+data16+":"+data17+":"+data18+":"+data19

        //System.out.println("Total data= "+ data)

        render new WSReturn(100, data , null) as JSON
        // ------- end for data retrived from another table -------------------------
    }

    //--------------- start get Grade no from a grade --------------------------------
    def getGradeNo()
    {
        def query = "from HrPayscale order by id"
        def hrPayscales = HrPayscale.executeQuery(query)
        def sb = new StringBuilder()
        sb.append("[")
        for (HrPayscale hrPayscale: hrPayscales) {
            sb.append("{'id':'" + hrPayscale.id + "', 'value':'" + hrPayscale.gradeNo + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        //System.out.println("sb==> " + sb.toString());
        render new WSReturn(100, sb.toString(), null) as JSON
    }
    //--------------- end get Grade no from a grade --------------------------------

    // ---------------------- Start get gross salary  -----------------------------------------------------------------------------
    def getGrossSalary(idNo)
    {
        def HrEmployeeInstance = HrEmployee.get(idNo)
        def grossSal = (HrEmployeeInstance.hrEmployeePayStructure.pBasic+HrEmployeeInstance.hrEmployeePayStructure.pDa+
                HrEmployeeInstance.hrEmployeePayStructure.pHr+HrEmployeeInstance.hrEmployeePayStructure.pConveyance+
                HrEmployeeInstance.hrEmployeePayStructure.pWashingAllow+HrEmployeeInstance.hrEmployeePayStructure.pMedicalAllowance+
                HrEmployeeInstance.hrEmployeePayStructure.pOrgPfContribution).toFloat()
        return grossSal
    }
    // ---------------------- End get gross salary ---------------------------------------------------------------------------------


    // ---------------------- Start get stage of Grade  -----------------------------------------------------------------------------
    def getGradeStage(idNo)
    {
        def HrEmployeeInstance = HrEmployee.get(idNo)
        def stage = HrEmployeeInstance.hrEmployeePayStructure.stage
        return stage
    }
    // ---------------------- End get stage of Grade ---------------------------------------------------------------------------------



    //--------------- start get slab no from a grade --------------------------------
    def getSlabNo()
    {
        def payScaleId = params.payScaleId
        def query = "from HrPayscaleDetail d where d.payscaleIdHrPayscale.id in(" + payScaleId + ") order by d.stage"
        def hrPayScaleDetails = HrPayscaleDetail.executeQuery(query)
        def sb = new StringBuilder()
        sb.append("[")
        for (HrPayscaleDetail hrPayScaleDetail: hrPayScaleDetails) {
            sb.append("{'id':'" + hrPayScaleDetail.id + "', 'value':'" + hrPayScaleDetail.stage + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
            sb.append("]")
        //System.out.println("sb==> " + sb.toString());
        render new WSReturn(100, sb.toString(), null) as JSON
    }
    //--------------- end get slab no from a grade --------------------------------



    // start search for employees Increment information -----------------------------------------------------------------------------------
    def search() {
        def cardNo = '%'+params.cardNo
        def hrEmployeePromotionInstanceList = HrEmployeePromotion.findAllByCardNoLike(cardNo,params)
        def totalCount = HrEmployeePromotion.countByCardNoLike(cardNo);
        render(view: "list", model: [hrEmployeePromotionInstanceList: hrEmployeePromotionInstanceList,hrEmployeePromotionInstanceTotal:totalCount])
    }
    // end search for employees Increment information ---------------------------------------------------------------------------------------------


    // ---------- start To get Promotion for employee ------------------------------------------
    def getPromotionStage() {
        def HrPayscaleInstance = HrPayscale.get(params.gradeId)
        def hrPayScaleDetailCat = HrPayscaleDetail.createCriteria()
        def hrPayScaleDetailInstance = hrPayScaleDetailCat.list{
            and{
                eq("stage", Integer.valueOf(params.promoStage))
                eq("payscaleIdHrPayscale", HrPayscaleInstance)
            }
        }

        def basic = hrPayScaleDetailInstance[0].basic
        //System.out.println("Basic: "+ basic)
        def data1 = basic.toString()

        def da = hrPayScaleDetailInstance[0].da
        //System.out.println("da: "+ da)
        def data2 = da.toString()

        def houseRent = hrPayScaleDetailInstance[0].houseRent
        //System.out.println("House Rent: "+ houseRent)
        def data3 = houseRent.toString()

        def conveyanceAllow = hrPayScaleDetailInstance[0].conveyanceAllow
        //System.out.println("Conveyance: "+ conveyanceAllow)
        def data4 = conveyanceAllow.toString()

        def washingAllow = hrPayScaleDetailInstance[0].washingAllow
        //System.out.println("Washing: "+ washingAllow)
        def data5 = washingAllow.toString()

        def medicalAllow = hrPayScaleDetailInstance[0].medicalAllow
        //System.out.println("Medical: "+ medicalAllow)
        def data6 = medicalAllow.toString()

        def pfContribution = hrPayScaleDetailInstance[0].pfContribution
        //System.out.println("PF Contribution: "+ pfContribution)
        def data7 = pfContribution.toString()

        def totalPayable = (basic+da+houseRent+conveyanceAllow+washingAllow+medicalAllow+pfContribution).toString()
        //System.out.println("Total Earnings= "+ totalPayable)

        def data =  data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+totalPayable
        //System.out.println("Total data= "+ data)

        render new WSReturn(100, data , null) as JSON
        // end for data retrived from another table -------------------------
    }
    // ---------- end To get Promotion for employee ------------------------------------------

}
