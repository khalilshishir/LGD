package hrms

import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.dao.DataIntegrityViolationException

class HrEmplReimbursableController {

    def dataSource

    HrEmplReimbursable hrEmplReimbursable
    List<HrEmplReimbursable> hrEmplReimbursableList

    HrEmplReimbursableDetail hrEmplReimbursableDetail
    List<HrEmplReimbursableDetail> hrEmplReimbursableDetailList

    HrLookup hrLookup

    List sqlValueList


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [hrEmplReimbursableInstanceList: HrEmplReimbursable.list(params), hrEmplReimbursableInstanceTotal: HrEmplReimbursable.count()]
    }

    def create() {
        [hrEmplReimbursableInstance: new HrEmplReimbursable(params)]
    }

/*    def save() {
        def hrEmplReimbursableInstance = new HrEmplReimbursable(params)
        if (!hrEmplReimbursableInstance.save(flush: true)) {
            render(view: "create", model: [hrEmplReimbursableInstance: hrEmplReimbursableInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), hrEmplReimbursableInstance.id])
        redirect(action: "show", id: hrEmplReimbursableInstance.id)
    } */

    def save() {
        // ---------- start add for data saved -------------------------------------------------------
        hrEmplReimbursable  =new HrEmplReimbursable()
        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        params.contractDate = Util.getDateMonddyyyy(params.contractDate)
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
      //  println("params " + params)


        hrEmplReimbursable.properties['id','contractAmount','contractDate','startDate','endDate','totalDays','contractReason','remarks','hrEmployee'] = params

        hrEmplReimbursable = hrEmplReimbursable.save(flush: true)

        if (hrEmplReimbursable != null) {
            int i=0
            while (params["hrEmplReimbursableDetail[" + i + "].allowanceTypeId"] != null)
            {
                hrEmplReimbursableDetail = new HrEmplReimbursableDetail()
                if(params["hrEmplReimbursableDetail[" + i + "].deleted"]=="true" && params["hrEmplReimbursableDetail[" + i + "].new"]=="false")
                {

                    hrEmplReimbursableDetail = (HrEmplReimbursableDetail) HrEmplReimbursableDetail.findById(Long.valueOf(params["hrEmplReimbursableDetails[" + i + "].id"]))
                    hrEmplReimbursableDetail.delete()
                    hrEmplReimbursable.hrEmplReimbursableDetail.remove(hrEmplReimbursableDetail)
                    i++
                    continue

                }
                else if(params["hrEmplReimbursableDetail[" + i + "].deleted"]=="true" && params["hrEmplReimbursableDetail[" + i + "].new"]=="true")
                {
                    i++
                    continue
                }
                else if(params["hrEmplReimbursableDetail[" + i + "].deleted"]=="false" && params["hrEmplReimbursableDetail[" + i + "].new"]=="true")
                {
                    hrEmplReimbursableDetail = new HrEmplReimbursableDetail()

                    def allowanceTypeId = params["hrEmplReimbursableDetail[" + i + "].allowanceTypeId"]
                    if (!allowanceTypeId.equals("")) {
                        hrLookup = HrLookup.findById(Long.valueOf(allowanceTypeId))
                        hrEmplReimbursableDetail.setAllowanceType(hrLookup)
                    }

                    println("hrEmplReimbursableDetail[" + i + "].allowanceAmount :"+params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"])
                    hrEmplReimbursableDetail.properties['allowanceAmount'] =params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"]
                    def allowanceDate = params["hrEmplReimbursableDetail[" + i + "].allowanceDate"]
                    if (!allowanceDate.equals("")) {
                        hrEmplReimbursableDetail.properties['allowanceDate'] = Util.getDateMonddyyyy(allowanceDate)
                    }
                    hrEmplReimbursableDetail.properties['isPaid'] =params["hrEmplReimbursableDetail[" + i + "].isPaid"]
                }
                else
                {
                    hrEmplReimbursableDetail=hrEmplReimbursableDetail.get(Long.valueOf(params["hrEmplReimbursableDetail[" + i + "].id"]))
                }

                def allowanceTypeId = params["hrEmplReimbursableDetail[" + i + "].allowanceTypeId"]
                if (!allowanceTypeId.equals("")) {
                    hrLookup = HrLookup.findById(Long.valueOf(allowanceTypeId))
                    hrEmplReimbursableDetail.setAllowanceType(hrLookup)
                }
                println("hrEmplReimbursableDetail[" + i + "].allowanceAmount :"+params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"])

                hrEmplReimbursableDetail.properties['allowanceAmount'] =params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"]

                def allowanceDate = params["hrEmplReimbursableDetail[" + i + "].allowanceDate"]
                if (!allowanceDate.equals("")) {
                    hrEmplReimbursableDetail.properties['allowanceDate'] = Util.getDateMonddyyyy(allowanceDate)
                }
                hrEmplReimbursableDetail.properties['isPaid'] =params["hrEmplReimbursableDetail[" + i + "].isPaid"]

                hrEmplReimbursableDetail.setHrEmplReimbursable(hrEmplReimbursable)

                hrEmplReimbursableDetail.save()

                i++
            }
        }
        // end add update for records -----------------------------------------------------------------------------
        // For the List Value
        hrEmplReimbursableDetailList = HrEmplReimbursableDetail.findAllByHrEmplReimbursable(hrEmplReimbursable)

        if (hrEmplReimbursable == null) {
            render(view: "edit", model: [hrEmplReimbursableInstance: hrEmplReimbursable, hrEmplReimbursableDetailList: hrEmplReimbursableDetailList])
            return
        }
        def hrEmplReimbursableInstance=hrEmplReimbursable
        //flash.message = "Record Successfully Saved."
        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmplReimbursable.label', default: 'Employee Reimbursable'), hrEmplReimbursable.id])
        redirect(action: "show", id: hrEmplReimbursableInstance.id, hrEmplReimbursableDetailList: hrEmplReimbursableDetailList)

    }

    def show(Long id) {
        def hrEmplReimbursableInstance = HrEmplReimbursable.get(id)
        if (!hrEmplReimbursableInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrEmplReimbursable = HrEmplReimbursable.findById(params.id)
        hrEmplReimbursableDetailList = HrEmplReimbursableDetail.findAllByHrEmplReimbursable(hrEmplReimbursable)

        [hrEmplReimbursableInstance: hrEmplReimbursableInstance,hrEmplReimbursableDetailList:hrEmplReimbursableDetailList]
    }

    def edit(Long id) {
        def hrEmplReimbursableInstance = HrEmplReimbursable.get(id)
        if (!hrEmplReimbursableInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrEmplReimbursable = HrEmplReimbursable.findById(params.id)
        hrEmplReimbursableDetailList = HrEmplReimbursableDetail.findAllByHrEmplReimbursable(hrEmplReimbursable)

        [hrEmplReimbursableInstance: hrEmplReimbursableInstance,hrEmplReimbursableDetailList:hrEmplReimbursableDetailList]

    }



    def update(Long id, Long version) {

        hrEmplReimbursable  =new HrEmplReimbursable()
        hrEmplReimbursable  =HrEmplReimbursable.get(params.id)

        if (!hrEmplReimbursable) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (hrEmplReimbursable.version > version) {
                hrEmplReimbursable.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable')] as Object[],
                        "Another user has updated this HrEmplReimbursable while you were editing")
                render(view: "edit", model: [hrEmplReimbursable: hrEmplReimbursable])
                return
            }
        }

        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        params.contractDate = Util.getDateMonddyyyy(params.contractDate)
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
        //  println("params " + params)

        hrEmplReimbursable.properties['id','contractAmount','contractDate','startDate','endDate','totalDays','contractReason','remarks','hrEmployee'] = params

        hrEmplReimbursable = hrEmplReimbursable.save(flush: true)

        if (hrEmplReimbursable != null) {
            int i=0
            while (params["hrEmplReimbursableDetail[" + i + "].allowanceTypeId"] != null)
            {
                hrEmplReimbursableDetail = new HrEmplReimbursableDetail()
                if(params["hrEmplReimbursableDetail[" + i + "].deleted"]=="true" && params["hrEmplReimbursableDetail[" + i + "].new"]=="false")
                {

                    hrEmplReimbursableDetail = (HrEmplReimbursableDetail) HrEmplReimbursableDetail.findById(Long.valueOf(params["hrEmplReimbursableDetails[" + i + "].id"]))
                    hrEmplReimbursableDetail.delete()
                    hrEmplReimbursable.hrEmplReimbursableDetail.remove(hrEmplReimbursableDetail)
                    i++
                    continue

                }
                else if(params["hrEmplReimbursableDetail[" + i + "].deleted"]=="true" && params["hrEmplReimbursableDetail[" + i + "].new"]=="true")
                {
                    i++
                    continue
                }
                else if(params["hrEmplReimbursableDetail[" + i + "].deleted"]=="false" && params["hrEmplReimbursableDetail[" + i + "].new"]=="true")
                {
                    hrEmplReimbursableDetail = new HrEmplReimbursableDetail()

                    def allowanceTypeId = params["hrEmplReimbursableDetail[" + i + "].allowanceTypeId"]
                    if (!allowanceTypeId.equals("")) {
                        hrLookup = HrLookup.findById(Long.valueOf(allowanceTypeId))
                        hrEmplReimbursableDetail.setAllowanceType(hrLookup)
                    }

                    println("hrEmplReimbursableDetail[" + i + "].allowanceAmount :"+params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"])

                    def allowanceAmount = params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"]
                    if (!allowanceAmount.equals("")) {
                        hrEmplReimbursableDetail.properties['allowanceAmount'] = Double.parseDouble(allowanceAmount)
                    }
                    def allowanceDate = params["hrEmplReimbursableDetail[" + i + "].allowanceDate"]
                    if (!allowanceDate.equals("")) {
                        hrEmplReimbursableDetail.properties['allowanceDate'] = Util.getDateMonddyyyy(allowanceDate)
                    }
                    hrEmplReimbursableDetail.properties['isPaid'] =params["hrEmplReimbursableDetail[" + i + "].isPaid"]
                }
                else
                {
                    hrEmplReimbursableDetail=hrEmplReimbursableDetail.get(Long.valueOf(params["hrEmplReimbursableDetail[" + i + "].id"]))
                }

                def allowanceTypeId = params["hrEmplReimbursableDetail[" + i + "].allowanceTypeId"]
                if (!allowanceTypeId.equals("")) {
                    hrLookup = HrLookup.findById(Long.valueOf(allowanceTypeId))
                    hrEmplReimbursableDetail.setAllowanceType(hrLookup)
                }
                println("hrEmplReimbursableDetail[" + i + "].allowanceAmount :"+params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"])

                def allowanceAmount = params["hrEmplReimbursableDetail[" + i + "].allowanceAmount"]
                if (!allowanceAmount.equals("")) {
                    hrEmplReimbursableDetail.properties['allowanceAmount'] = Double.parseDouble(allowanceAmount)
                }

                def allowanceDate = params["hrEmplReimbursableDetail[" + i + "].allowanceDate"]
                if (!allowanceDate.equals("")) {
                    hrEmplReimbursableDetail.properties['allowanceDate'] = Util.getDateMonddyyyy(allowanceDate)
                }
                hrEmplReimbursableDetail.properties['isPaid'] =params["hrEmplReimbursableDetail[" + i + "].isPaid"]

                hrEmplReimbursableDetail.setHrEmplReimbursable(hrEmplReimbursable)

                hrEmplReimbursableDetail.save()

                i++
            }
        }
        // end add update for records -----------------------------------------------------------------------------
        // For the List Value
        hrEmplReimbursableDetailList = HrEmplReimbursableDetail.findAllByHrEmplReimbursable(hrEmplReimbursable)

        if (hrEmplReimbursable == null) {
            render(view: "edit", model: [hrEmplReimbursableInstance: hrEmplReimbursable, hrEmplReimbursableDetailList: hrEmplReimbursableDetailList])
            return
        }
        def hrEmplReimbursableInstance=hrEmplReimbursable

        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmplReimbursable.label', default: 'Employee Reimbursable'), hrEmplReimbursableInstance.id])
        flash.message = "Employee ID "+ hrEmplReimbursableInstance.hrEmployee.cardNo+ " Reimbursable Detail Updated."
        redirect(action: "show", id: hrEmplReimbursableInstance.id, hrEmplReimbursableDetailList: hrEmplReimbursableDetailList)
    }

    def delete(Long id) {
        def hrEmplReimbursableInstance = HrEmplReimbursable.get(id)
        if (!hrEmplReimbursableInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), id])
            redirect(action: "list")
            return
        }

        try {
            hrEmplReimbursableInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmplReimbursable.label', default: 'HrEmplReimbursable'), id])
            redirect(action: "show", id: id)
        }
    }



    // employees basic information retrieved  by using cardNo
    def getEmpBasicInfoByCardNo() {

        Sql sql = new Sql(dataSource)

        sqlValueList = sql.rows("SELECT COUNT(*) TOTAL_VALUE FROM HR_EMPLOYEE " +
                //" WHERE EMPLOYEE_TYPE_ID IN (393,394,395)" +
                " WHERE PRESENT_STATUS_ID=391" +
                " AND CARD_NO =?",params.idNo.toString().trim())

        println("sqlValueList.TOTAL_VALUE[0] :"+sqlValueList.TOTAL_VALUE[0])
        def data
        if (sqlValueList.TOTAL_VALUE[0]!=0){
            /*def sessionUserObj = getUserInfo()
            def hrEmployee
            print(sessionUserObj)
            def departmentIdObj = sessionUserObj.hrEmployee.departmentIdHrDepartment
            def role = sessionUserObj.role

            if (role=='Admin'){
                hrEmployee = HrEmployee.findByCardNo(params.idNo)
            }else{
                hrEmployee = HrEmployee.findByCardNoAndDepartmentIdHrDepartment(params.idNo,departmentIdObj)
            }*/
            def hrEmployee = HrEmployee.findByCardNo(params.idNo)

            if (hrEmployee!=null) {
                def data1 = hrEmployee.employeeName
                // System.out.println("data1 "+ data1)
                def data2 = hrEmployee.departmentIdHrDepartment.departmentName
                // System.out.println("data2 "+ data2)
                def data3 = hrEmployee.designationIdHrDesignation.designationName
                // System.out.println("data3 "+ data3)
                def data4 = hrEmployee.joiningDate.toString()
                // System.out.println("data4 "+ data4)
                def data5 = hrEmployee.confirmationDate.toString()
                // System.out.println("data5 "+ data5)
                def data6 = hrEmployee.presentStatusIdHrLookup.lookupValue
                // System.out.println("data6 "+ data6)
                def data7 = hrEmployee.employeeTypeIdHrLookup.lookupValue
                // System.out.println("data7 "+ data7)
                def data8 = hrEmployee.id.toString()
                // System.out.println("data8 "+ data8)
                data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+data8
            }
        }

        render new WSReturn(100, data, null) as JSON
    }

    // employees basic information retrieved  by using Employee Name
    def getEmpBasicInfo() {

        Sql sql = new Sql(dataSource)

        sqlValueList = sql.rows("SELECT COUNT(*) TOTAL_VALUE FROM HR_EMPLOYEE " +
                // " WHERE EMPLOYEE_TYPE_ID=393" +
                // " WHERE EMPLOYEE_TYPE_ID IN (393,394,395)" +
                "  WHERE PRESENT_STATUS_ID=391" +
                " AND  EMPLOYEE_NAME =?",params.idNo.toString().trim())

        println("params.idNo :"+params.idNo)
        println("sqlValueList.TOTAL_VALUE[0] :"+sqlValueList.TOTAL_VALUE[0])
        def data
        if (sqlValueList.TOTAL_VALUE[0]!=0){

            /*def sessionUserObj = getUserInfo()
            def hrEmployee
            print(sessionUserObj)
            def departmentIdObj = sessionUserObj.hrEmployee.departmentIdHrDepartment
            def role = sessionUserObj.role

            if (role=='Admin'){
                 hrEmployee = HrEmployee.findByEmployeeName(params.idNo)
            }else{
                 hrEmployee = HrEmployee.findByEmployeeNameAndDepartmentIdHrDepartment(params.idNo,departmentIdObj)
            }*/

            def hrEmployee = HrEmployee.findByEmployeeName(params.idNo)


            if (hrEmployee!=null) {
                def data1 = hrEmployee.cardNo
                // System.out.println("data1 "+ data1)
                def data2 = hrEmployee.departmentIdHrDepartment.departmentName
                // System.out.println("data2 "+ data2)
                def data3 = hrEmployee.designationIdHrDesignation.designationName
                // System.out.println("data3 "+ data3)
                def data4 = hrEmployee.joiningDate.toString()
                // System.out.println("data4 "+ data4)
                def data5 = hrEmployee.confirmationDate.toString()
                // System.out.println("data5 "+ data5)
                def data6 = hrEmployee.presentStatusIdHrLookup.lookupValue
                // System.out.println("data6 "+ data6)
                def data7 = hrEmployee.employeeTypeIdHrLookup.lookupValue
                // System.out.println("data7 "+ data7)
                def data8 = hrEmployee.id.toString()
                // System.out.println("data8 "+ data8)
                data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+data8
            }
        }
        render new WSReturn(100, data, null) as JSON
    }


    // for similar name searching for creating list
    def autoEmployeeNameByDepartmentWise() {
        Sql sql = new Sql(dataSource)
        def employeeNameList
        /* def sessionUserObj = getUserInfo()
         print(sessionUserObj)
         def departmentIdNo = sessionUserObj.hrEmployee.departmentIdHrDepartment.id
         def role = sessionUserObj.role

         if (role=='Admin'){
             // employeeNameList = sql.rows("select employee_name  from hr_employee where UPPER(employee_name) like '%'||UPPER(?)||'%' AND EMPLOYEE_TYPE_ID=393 AND PRESENT_STATUS_ID=391",params.term.trim())
              employeeNameList = sql.rows("select employee_name  from hr_employee where UPPER(employee_name) like '%'||UPPER(?)||'%' AND EMPLOYEE_TYPE_ID IN (393,394,395) AND PRESENT_STATUS_ID=391",params.term.trim())
         }else{
              //employeeNameList = sql.rows("select employee_name  from hr_employee where UPPER(employee_name) like '%'||UPPER(?)||'%' and department_id=? AND EMPLOYEE_TYPE_ID=393 AND PRESENT_STATUS_ID=391",params.term.trim(),departmentIdNo)
              employeeNameList = sql.rows("select employee_name  from hr_employee where UPPER(employee_name) like '%'||UPPER(?)||'%' and department_id=? AND EMPLOYEE_TYPE_ID IN (393,394,395) AND PRESENT_STATUS_ID=391",params.term.trim(),departmentIdNo)
         }*/

        employeeNameList = sql.rows("select employee_name  from hr_employee where UPPER(employee_name) like '%'||UPPER(?)||'%' AND PRESENT_STATUS_ID=391",params.term.trim())

        println("employeeNameList Size  "+employeeNameList.size())
        sql.close()
        render (employeeNameList.employee_name as JSON)
    }


    // for similar code searching for creating list
    def autoEmployeeCardNoByDepartmentWise() {
        Sql sql = new Sql(dataSource)
        def employeeNameList
        /*def sessionUserObj = getUserInfo()
        print(sessionUserObj)
        def departmentIdNo = sessionUserObj.hrEmployee.departmentIdHrDepartment.id
        println("Department Id :"+sessionUserObj.hrEmployee.departmentIdHrDepartment.id)
        println("Department Name :"+sessionUserObj.hrEmployee.departmentIdHrDepartment.departmentName)
        def role = sessionUserObj.role
        if (role=='Admin'){
           // employeeNameList = sql.rows("SELECT CARD_NO FROM HR_EMPLOYEE WHERE CARD_NO LIKE '%'||?||'%' AND EMPLOYEE_TYPE_ID=393 AND PRESENT_STATUS_ID=391",params.term.trim())
            employeeNameList = sql.rows("SELECT CARD_NO FROM HR_EMPLOYEE WHERE CARD_NO LIKE '%'||?||'%' AND EMPLOYEE_TYPE_ID IN (393,394,395) AND PRESENT_STATUS_ID=391",params.term.trim())
        }else{
            //employeeNameList = sql.rows("select card_no from hr_employee where card_no like '%'||?||'%' and department_id=? AND EMPLOYEE_TYPE_ID=393 AND PRESENT_STATUS_ID=391",params.term.trim(),departmentIdNo)
            employeeNameList = sql.rows("select card_no from hr_employee where card_no like '%'||?||'%' and department_id=? AND EMPLOYEE_TYPE_ID IN (393,394,395) AND PRESENT_STATUS_ID=391",params.term.trim(),departmentIdNo)
        }*/


        employeeNameList = sql.rows("SELECT CARD_NO FROM HR_EMPLOYEE WHERE CARD_NO LIKE '%'||?||'%'  AND PRESENT_STATUS_ID=391",params.term.trim())

        println("employeeNameList Size  "+employeeNameList.size())
        sql.close()
        render (employeeNameList.card_no as JSON)
    }

}
