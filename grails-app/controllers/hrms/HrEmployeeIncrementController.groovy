package hrms

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrEmployeeIncrementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [hrEmployeeIncrementInstanceList: HrEmployeeIncrement.list(params), hrEmployeeIncrementInstanceTotal: HrEmployeeIncrement.count()]
    }

    def create() {
        [hrEmployeeIncrementInstance: new HrEmployeeIncrement(params)]
    }

    def save() {
        params.incrementEffectDate = Util.getDateMonddyyyy(params.incrementEffectDate)
        params.salaryEffectDate = Util.getDateMonddyyyy(params.salaryEffectDate)
        def hrEmployeeIncrementInstance = new HrEmployeeIncrement(params)

        /*// start update HR_EMPLOYEE_PAY_STRUCTURE table using increment data
        def  hrEmployeePayStructureInstance = HrEmployeePayStructure.findByEmployeeIdHrEmployee(hrEmployeeIncrementInstance.employeeIdHrEmployee)
        hrEmployeePayStructureInstance.properties['stage'] = params.stage
        hrEmployeePayStructureInstance.properties['pBasic'] = Float.valueOf(params.pBasic)
        hrEmployeePayStructureInstance.properties['pDa'] = Float.valueOf(params.pDa)
        hrEmployeePayStructureInstance.properties['pHr'] = Float.valueOf(params.pHrent)
        hrEmployeePayStructureInstance.properties['pConveyance'] = Float.valueOf(params.pConveyanceAllowance)
        hrEmployeePayStructureInstance.properties['pWashingAllow'] = Float.valueOf(params.pWashingAllowance)
        hrEmployeePayStructureInstance.properties['pMedicalAllowance'] = Float.valueOf(params.pMedicalAllowance)
        hrEmployeePayStructureInstance.properties['pOrgPfContribution'] = Float.valueOf(params.pPf)
        hrEmployeePayStructureInstance.save(flash:true)
        // end update HR_EMPLOYEE_PAY_STRUCTURE table using increment data*/

        if (!hrEmployeeIncrementInstance.save(flush: true)) {
            render(view: "create", model: [hrEmployeeIncrementInstance: hrEmployeeIncrementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), hrEmployeeIncrementInstance.id])
        redirect(action: "show", id: hrEmployeeIncrementInstance.id)
    }

    def show() {
        def hrEmployeeIncrementInstance = HrEmployeeIncrement.get(params.id)
        if (!hrEmployeeIncrementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeeIncrementInstance: hrEmployeeIncrementInstance]
    }

    def edit() {
        def hrEmployeeIncrementInstance = HrEmployeeIncrement.get(params.id)
        def grossSalary = getGrossSalary(hrEmployeeIncrementInstance.employeeIdHrEmployee.id)
        def stage = getGradeStage(hrEmployeeIncrementInstance.employeeIdHrEmployee.id)
        if (!hrEmployeeIncrementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeeIncrementInstance: hrEmployeeIncrementInstance, grossSalary: grossSalary, presentStage: stage]
    }

    def update() {
        def hrEmployeeIncrementInstance = HrEmployeeIncrement.get(params.id)
        if (!hrEmployeeIncrementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrEmployeeIncrementInstance.version > version) {
                hrEmployeeIncrementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement')] as Object[],
                        "Another user has updated this HrEmployeeIncrement while you were editing")
                render(view: "edit", model: [hrEmployeeIncrementInstance: hrEmployeeIncrementInstance])
                return
            }
        }
        params.incrementEffectDate = Util.getDateMonddyyyy(params.incrementEffectDate)
        params.salaryEffectDate = Util.getDateMonddyyyy(params.salaryEffectDate)
        hrEmployeeIncrementInstance.properties = params

        if (!hrEmployeeIncrementInstance.save(flush: true)) {
            render(view: "edit", model: [hrEmployeeIncrementInstance: hrEmployeeIncrementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), hrEmployeeIncrementInstance.id])
        redirect(action: "show", id: hrEmployeeIncrementInstance.id)
    }

    def delete() {
        def hrEmployeeIncrementInstance = HrEmployeeIncrement.get(params.id)
        if (!hrEmployeeIncrementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrEmployeeIncrementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmployeeIncrement.label', default: 'HrEmployeeIncrement'), params.id])
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

        def data3 = idObj.departmentIdHrDepartment.departmentName
        //System.out.println("data3 : "+ data3)


        /*def query = "from HrEmployeePayStructure  where employeeIdHrEmployee.cardNo in(" + idObj.cardNo + ")"
        def hrEmployeePayStructure = HrEmployeePayStructure.executeQuery(query)
        System.out.println("query : "+ hrEmployeePayStructure)*/


        def payScaleIdNo = idObj.hrEmployeePayStructure.payScaleId
        //System.out.println("payScaleIdNo : "+ payScaleIdNo)

        def data4 = HrPayscale.get(payScaleIdNo)
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
        System.out.println("data9 : "+ data9)

        def data10 = (idObj.hrEmployeePayStructure.pBasic+idObj.hrEmployeePayStructure.pDa+
                idObj.hrEmployeePayStructure.pHr+idObj.hrEmployeePayStructure.pConveyance+
                idObj.hrEmployeePayStructure.pWashingAllow+idObj.hrEmployeePayStructure.pMedicalAllowance+
                idObj.hrEmployeePayStructure.pOrgPfContribution).toFloat().toString()
        //System.out.println("data10 : "+ data10)

        // employees current salary structure
        def presentBasic = idObj.hrEmployeePayStructure.pBasic.toString()
        def presentDa = idObj.hrEmployeePayStructure.pDa.toString()
        def presentHr = idObj.hrEmployeePayStructure.pHr.toString()
        def presentConveyance = idObj.hrEmployeePayStructure.pConveyance.toString()
        def presentWashingAllow = idObj.hrEmployeePayStructure.pWashingAllow.toString()
        def presentMedicalAllow = idObj.hrEmployeePayStructure.pMedicalAllowance.toString()
        def presentOrgPf = idObj.hrEmployeePayStructure.pOrgPfContribution.toString()

        def data11 = idObj.id.toString()
        //System.out.println("data11 : "+ data11)

        def data12 = idObj.designationIdHrDesignation.id.toString()
        //System.out.println("data12 : "+ data12)

        //edited By Maruf    Start ----
        def data13 = idObj.hrEmployeePayStructure.spAllowance
        def data14 = idObj.hrEmployeePayStructure.spDaAllowance
        def data15 = idObj.hrEmployeePayStructure.addSpAllowance
        def data16 = idObj.hrEmployeePayStructure.spAllowance
        def data17 = idObj.hrEmployeePayStructure.spDaAllowance
        def data18 = idObj.hrEmployeePayStructure.addSpAllowance
        //edited By Maruf    End -----

        def data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+data8+":"+data9+":"+data10+":"+
        presentBasic+":"+presentDa+":"+presentHr+":"+presentConveyance+":"+presentWashingAllow+":"+presentMedicalAllow+":"+presentOrgPf+":"+
        payScaleIdNo.toString()+":"+data11+":"+data12+":"+data13+":"+data14+":"+data15+":"+data16+":"+data17+":"+data18

        //System.out.println("Total data= "+ data)

        render new WSReturn(100, data , null) as JSON
        // ------- end for data retrieved from another table -------------------------
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

        def data3 = idObj.departmentIdHrDepartment.departmentName
        //System.out.println("data3 : "+ data3)


        /*def query = "from HrEmployeePayStructure  where employeeIdHrEmployee.cardNo in(" + idObj.cardNo + ")"
        def hrEmployeePayStructure = HrEmployeePayStructure.executeQuery(query)
        System.out.println("query : "+ hrEmployeePayStructure)*/


        def payScaleIdNo = idObj.hrEmployeePayStructure.payScaleId
        System.out.println("payScaleIdNo : "+ payScaleIdNo)

        def data4 = HrPayscale.get(payScaleIdNo)
        System.out.println("data4 : "+ data4)

        def data5 = idObj.joiningDate.toString()
        //System.out.println("data5 : "+ data5)

        def data6 = idObj.confirmationDate.toString()
        //System.out.println("data6 : "+ data6)

        def data7 = idObj.employeeTypeIdHrLookup.lookupValue
        //System.out.println("data7 : "+ data7)

        def data8 = idObj.presentStatusIdHrLookup.lookupValue
        //System.out.println("data8 : "+ data8)

        def data9 = idObj.hrEmployeePayStructure.stage
        System.out.println("data9 : "+ data9)

        def data10 = (idObj.hrEmployeePayStructure.pBasic+idObj.hrEmployeePayStructure.pDa+
                idObj.hrEmployeePayStructure.pHr+idObj.hrEmployeePayStructure.pConveyance+
                idObj.hrEmployeePayStructure.pWashingAllow+idObj.hrEmployeePayStructure.pMedicalAllowance+
                idObj.hrEmployeePayStructure.pOrgPfContribution).toFloat().toString()
        //System.out.println("data10 : "+ data10)

        // employees current salary structure
        def presentBasic = idObj.hrEmployeePayStructure.pBasic.toString()
        def presentDa = idObj.hrEmployeePayStructure.pDa.toString()
        def presentHr = idObj.hrEmployeePayStructure.pHr.toString()
        def presentConveyance = idObj.hrEmployeePayStructure.pConveyance.toString()
        def presentWashingAllow = idObj.hrEmployeePayStructure.pWashingAllow.toString()
        def presentMedicalAllow = idObj.hrEmployeePayStructure.pMedicalAllowance.toString()
        def presentOrgPf = idObj.hrEmployeePayStructure.pOrgPfContribution.toString()

        def data11 = idObj.id.toString()
        //System.out.println("data11 : "+ data11)

        def data12 = idObj.designationIdHrDesignation.id.toString()
        //System.out.println("data12 : "+ data12)

        //edited By Maruf    Start ----
        def data13 = idObj.hrEmployeePayStructure.spAllowance
        def data14 = idObj.hrEmployeePayStructure.spDaAllowance
        def data15 = idObj.hrEmployeePayStructure.addSpAllowance
        def data16 = idObj.hrEmployeePayStructure.spAllowance
        def data17 = idObj.hrEmployeePayStructure.spDaAllowance
        def data18 = idObj.hrEmployeePayStructure.addSpAllowance
        //edited By Maruf    End -----

        def data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+data8+":"+data9+":"+data10+":"+
                presentBasic+":"+presentDa+":"+presentHr+":"+presentConveyance+":"+presentWashingAllow+":"+presentMedicalAllow+":"+presentOrgPf+":"+
                payScaleIdNo.toString()+":"+data11+":"+data12+":"+data13+":"+data14+":"+data15+":"+data16+":"+data17+":"+data18

        //System.out.println("Total data= "+ data)

        render new WSReturn(100, data , null) as JSON
        // ------- end for data retrieved from another table -------------------------
    }

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



    // ---------- start To get Increment Level for Increments ------------------------------------------
    def getIncrementStage(){
        def HrPayscaleInstance = HrPayscale.get(params.gradeId)
        def hrPayScaleDetailCat = HrPayscaleDetail.createCriteria()
        def hrPayScaleDetailInstance = hrPayScaleDetailCat.list{
            and{
                eq("stage", Integer.valueOf(params.incStage))
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
    // ---------- end To get Increment Level for Increments --------------------------------------------



    // start search for employees Increment information -----------------------------------------------------------------------------------
    def search() {
        def cardNo = '%'+params.cardNo
        def hrEmployeeIncrementInstanceList = HrEmployeeIncrement.findAllByCardNoLike(cardNo,params)
        def totalCount = HrEmployeeIncrement.countByCardNoLike(cardNo);
        render(view: "list", model: [hrEmployeeIncrementInstanceList: hrEmployeeIncrementInstanceList,hrEmployeeIncrementInstanceTotal:totalCount])
    }
    // end search for employees Increment information ---------------------------------------------------------------------------------------------

}
