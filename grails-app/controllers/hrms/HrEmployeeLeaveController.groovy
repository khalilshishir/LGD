package hrms

//import inventory.IubUser
import grails.converters.JSON
import groovy.sql.Sql
import java.text.SimpleDateFormat
import org.springframework.dao.DataIntegrityViolationException

class HrEmployeeLeaveController {

    def dataSource


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    List sqlValueList

    def index() {
        redirect(action: "list", params: params)
    }

 /*   def getUserInfo() {
        def user = IubUser.findByUserIdAndPassword(session.user.userId, session.user.password)
        return user
        //print(user.hrEmployee.employeeName)
    }*/

    def list() {

       /* def sessionUserObj = getUserInfo()*/
        def hrEmployeeLeaveList
        /*print(sessionUserObj)
        def departmentId = sessionUserObj.hrEmployee.departmentIdHrDepartment.id
        def role = sessionUserObj.role

        if (role=='Admin'){
            hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
                    "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id)")
        }else{
            hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
                    "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id and br.departmentIdHrDepartment.id in(" + departmentId +"))")
        }
        println("hrEmployeeLeaveList Size  "+hrEmployeeLeaveList.size())*/


        hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
                                                      "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id)")

        params.max = Math.min(params.max ? params.int('max') : 20, 100)

        [hrEmployeeLeaveInstanceList: hrEmployeeLeaveList, hrEmployeeLeaveInstanceTotal: hrEmployeeLeaveList.size()]

    }

/*    def listBackupPreviouslyDoneByKmol() {

        //Sql sql = new Sql(dataSource)

        def sessionUserObj = getUserInfo()
        print(sessionUserObj)
        def departmentId = sessionUserObj.hrEmployee.departmentIdHrDepartment.id

        def hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
                "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id and br.departmentIdHrDepartment.id in(" + departmentId +"))")

        //def query = "from HrEmployee a, HrEmployeeLeave b where a.id=employeeIdHrEmployee.id and a.departmentIdHrDepartment.id in(" + departmentId + ")"
        //def hrEmployeeLeaveList = HrEmployee.executeQuery(query)
//        def hrEmployeeLeaveList = sql.rows("select a.* from HR_EMPLOYEE_LEAVE a,HR_EMPLOYEE b where a.employee_id=b.employee_id and b.department_id=?",departmentId) 
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        //[hrEmployeeLeaveInstanceList: HrEmployeeLeave.list(params), hrEmployeeLeaveInstanceTotal: HrEmployeeLeave.count()]
        [hrEmployeeLeaveInstanceList: hrEmployeeLeaveList, hrEmployeeLeaveInstanceTotal: hrEmployeeLeaveList.size()]
        //[hrEmployeeLeaveInstanceList: hrEmployeeLeaveList]
    }*/

    def create() {
        [hrEmployeeLeaveInstance: new HrEmployeeLeave(params)]
    }

    def saveBackup() {
        params.applicationDate = Util.getDateMonddyyyy(params.applicationDate)
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)

        def hrEmployeeLeaveInstance = new HrEmployeeLeave(params)
        if (!hrEmployeeLeaveInstance.save(flush: true)) {
            render(view: "create", model: [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance])
            return
        }
        flash.message = "Record Successfully Saved."
        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), hrEmployeeLeaveInstance.id])
        redirect(action: "show", id: hrEmployeeLeaveInstance.id)
    }

    def save1() {
       // String lastDate = Util.getLastDate(1,2014)

        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
        String firstDate = Util.getFirstDay(params.startDate)
        String lastDate = Util.getLastDay(params.startDate)
        String nextMonthDate = Util.getNextMonthDate(params.startDate,2)
        params.totalDays=Util.getDateDifference(params.startDate,params.endDate)
        println("firstDate "+firstDate)
        println("lastDate "+lastDate)
        println("nextMonthDate "+nextMonthDate)
        println("totalDays "+params.totalDays)
        flash.message = lastDate
        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), hrEmployeeLeaveInstance.id])
        redirect(action: "show")
    }
    def save() {
        def hrEmployeeLeaveInstance
        params.applicationDate = Util.getDateMonddyyyy(params.applicationDate)
        int startMonth = Util.getMonth(params.startDate)
        int endMonth = Util.getMonth(params.endDate)
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
        Date strDate= params.startDate
        Date edDate= params.endDate

        //def monthDifference= endMonth-startMonth
        def monthDifference= Util.getMonthDifference(strDate,edDate)
        println("monthDifference "+monthDifference)
        int success=0;
        int fail=0;

        //If Month difference more than  0 then enter into..
         if (monthDifference==0){
            println("monthDifference==0")
            hrEmployeeLeaveInstance = new HrEmployeeLeave(params)
            if (!hrEmployeeLeaveInstance.save(flush: true)) {
                render(view: "create", model: [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance])
                return
            }
            flash.message = "Record Successfully Saved."
            redirect(action: "show", id: hrEmployeeLeaveInstance.id)
        }else if (monthDifference==1){
            println("monthDifference==1")
            hrEmployeeLeaveInstance = new HrEmployeeLeave(params)
            if (!hrEmployeeLeaveInstance.save(flush: true)) {
                render(view: "create", model: [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance])
                return
            }
            flash.message = "Record Successfully Saved."
            redirect(action: "show", id: hrEmployeeLeaveInstance.id)
        }else if (monthDifference>1){
            println("monthDifference>1")
            for(int i=0;i<monthDifference;i++){
                // if i=0 first month then start date will be provided start date..
                // and end date will be from sql provided..
                // if i = something that is last month then start date will be from sql provided.
                // and end date will be provided end date..

                String nextMonthDate
                Integer totalDays

                if (i==0){
                   // params.startDate=params.startDate
                   // end date start will be new start date's last day..
                    println("i==0 "+i)
                   // params.startDate = Util.getDateMonddyyyy(params.startDate)
                    params.endDate = Util.getDateMonddyyyy(Util.getLastDay(strDate))
                    params.totalDays=Util.getDateDifference(strDate,params.endDate)
                    println(" i==0 firstDate "+params.startDate)
                    println(" i==0 lastDate "+params.endDate)
                    println(" i==0 params.totalDays "+params.totalDays)

                }else if (i==monthDifference-1){
                   // start date start will be edDate first day
                   // params.endDate=params.endDate
                    println("i==monthDifference "+i)
                    params.startDate = Util.getDateMonddyyyy(Util.getFirstDay(edDate))
                    params.endDate = edDate
                    params.totalDays=Util.getDateDifference(params.startDate,params.endDate)

                    println(" i==monthDifference firstDate "+params.startDate)
                    println(" i==monthDifference lastDate "+params.endDate)
                    println(" i==monthDifference params.totalDays "+params.totalDays)
                }else{
                   // start date start will be start date +i
                   // end date start will be new start date's last day..
                    println("Else Middle i "+i)
                    nextMonthDate = Util.getNextMonthDate(strDate,i)
                    params.startDate = Util.getDateMonddyyyy(Util.getFirstDay(Util.getDateMonddyyyy(nextMonthDate)))
                    params.endDate = Util.getDateMonddyyyy(Util.getLastDay(params.startDate))
                    params.totalDays=Util.getDateDifference(params.startDate,params.endDate)
                    println(" Else Middle firstDate "+params.startDate)
                    println(" Else Middle lastDate "+params.endDate)
                    println(" Else Middle params.totalDays "+params.totalDays)

                }

                hrEmployeeLeaveInstance = new HrEmployeeLeave(params)
                if (!hrEmployeeLeaveInstance.save(flush: true)) {
                    fail++
                } else{
                    success++
                }
            }
            if(fail>0){
                flash.message = success+" Record Saved Successfully. and "+ fail+" Record not Saved"
            }else{
                flash.message = success+" Record Saved Successfully."
            }
            redirect(action: "show", id: hrEmployeeLeaveInstance.id)
        }else if (monthDifference<0){
            flash.message = "Start date can not be more than end date."
            hrEmployeeLeaveInstance = new HrEmployeeLeave(params)
            render(view: "create", model: [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance])
            return
        }

    }

    def show() {

/*        def sessionUserObj = getUserInfo()
        def role = sessionUserObj.role*/

        def hrEmployeeLeaveInstance = HrEmployeeLeave.get(params.id)
        if (!hrEmployeeLeaveInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), params.id])
            redirect(action: "list")
            return
        }

       /* [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance,role:role]*/
        [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance]
    }

    def edit() {
        def hrEmployeeLeaveInstance = HrEmployeeLeave.get(params.id)
        if (!hrEmployeeLeaveInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance]
    }

    def update() {
        def hrEmployeeLeaveInstance = HrEmployeeLeave.get(params.id)
        if (!hrEmployeeLeaveInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrEmployeeLeaveInstance.version > version) {
                hrEmployeeLeaveInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave')] as Object[],
                        "Another user has updated this HrEmployeeLeave while you were editing")
                render(view: "edit", model: [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance])
                return
            }
        }
        params.applicationDate = Util.getDateMonddyyyy(params.applicationDate)
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
        hrEmployeeLeaveInstance.properties = params

        if (!hrEmployeeLeaveInstance.save(flush: true)) {
            render(view: "edit", model: [hrEmployeeLeaveInstance: hrEmployeeLeaveInstance])
            return
        }
        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), hrEmployeeLeaveInstance.id])
        redirect(action: "show", id: hrEmployeeLeaveInstance.id)
    }

    def delete() {
        def hrEmployeeLeaveInstance = HrEmployeeLeave.get(params.id)
        if (!hrEmployeeLeaveInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrEmployeeLeaveInstance.delete(flush: true)
            flash.message = "Record Successfully Deleted."
            //flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmployeeLeave.label', default: 'HrEmployeeLeave'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // get employees leave balance days
    def srcRBEmpLeaveBalanceBackup() {

        def db = new Sql(dataSource)
        def data
        def leaveApplicationDate = Util.getDateMonddyyyy(params.applicationDate)
        SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
        def leaveYearName = simpleDateformat.format(leaveApplicationDate)

        println("leaveApplicationDate "+leaveApplicationDate)
        println("leaveYearName "+leaveYearName)
        println("employeeId "+params.employeeId)
        println("leaveTypeId "+params.leaveTypeId)

        def employeeBalanceRows = db.rows("select h.BALANCE from HR_EMP_LEAVE_BALANCE_VW h where h.EMPLOYEE_ID=? and h.LEAVE_TYPE_ID=? and h.LEAVE_YEAR=?",params.employeeId,params.leaveTypeId,leaveYearName)
        if(employeeBalanceRows.size()!=0){
             data =  employeeBalanceRows[0].BALANCE.toString()
        }else{
            data =  ' '
        }

        render new WSReturn(100, data , null) as JSON
    }

    def srcRBEmpLeaveBalance() {

        //Getting MAXIMUM_DAYS value according to LEAVE_ID from HR_LEAVE
        //Getting HOLIDAY_COUNT value according to LEAVE_ID from HR_LEAVE
        //If HOLIDAY_COUNT=1 then do nothing,If  HOLIDAY_COUNT=0 MAXIMUM_DAYS-TOTAL_HOLIDAY

        //Getting Employee previously leave taken information according to employeeId and leaveTypeId and leaveYearName from HR_EMPLOYEE_LEAVE table


        def db = new Sql(dataSource)
        def data
        def leaveApplicationDate = Util.getDateMonddyyyy(params.applicationDate)
        SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
        def leaveYearName = simpleDateformat.format(leaveApplicationDate)
        int leaveTypeId = Integer.valueOf(params.leaveTypeId)
        int employeeId=Integer.valueOf(params.employeeId)

        println("leaveApplicationDate "+leaveApplicationDate)
        println("leaveYearName "+leaveYearName)
        println("employeeId "+employeeId)
        println("leaveTypeId "+leaveTypeId)

       int totalLeaveTaken=getEmpLeaveTakenNo(employeeId,leaveTypeId,leaveYearName)
       List leaveDetailList=getLeaveDetail(leaveTypeId)
        if(leaveDetailList.size()!=0){
            data =  leaveDetailList[0].MAXIMUM_DAYS.toString()+":"+totalLeaveTaken+":"+leaveDetailList[0].HOLIDAY_COUNT.toString()
        }else{
            data =  ' '
        }

/*        def employeeBalanceRows = db.rows("select h.BALANCE from HR_EMP_LEAVE_BALANCE_VW h where h.EMPLOYEE_ID=? and h.LEAVE_TYPE_ID=? and h.LEAVE_YEAR=?",params.employeeId,params.leaveTypeId,leaveYearName)
        if(employeeBalanceRows.size()!=0){
             data =  employeeBalanceRows[0].BALANCE.toString()
        }else{
            data =  ' '
        }*/

        render new WSReturn(100, data , null) as JSON
    }

    public List getLeaveDetail(int leaveTypeId) {

        Sql sql = new Sql(dataSource)
        sqlValueList=null
        sqlValueList = sql.rows("SELECT MAXIMUM_DAYS,HOLIDAY_COUNT FROM HR_LEAVE WHERE LEAVE_ID=?",leaveTypeId)

        println("sqlValueList.size()  :"+sqlValueList.size() )

        sql.close()
        return sqlValueList

    }

    public int getEmpLeaveTakenNo(int employeeId,int leaveTypeId,String leaveYearName) {

        Sql sql = new Sql(dataSource)
        int TOTAL_DAYS=0
        sqlValueList=null
        sqlValueList = sql.rows("SELECT NVL(SUM(TOTAL_DAYS),0) TOTAL_DAYS FROM HR_EMPLOYEE_LEAVE " +
                                " WHERE EMPLOYEE_ID=? " +
                                " AND LEAVE_TYPE_ID=? " +
                                " AND TO_CHAR(APPLICATION_DATE,'YYYY')=?",employeeId,leaveTypeId,leaveYearName)
        if(sqlValueList!=null){
            println("sqlValueList.size()  :"+sqlValueList.size() )
            println("sqlValueList[0].TOTAL_DAYS  :"+sqlValueList[0].TOTAL_DAYS +"|")
            TOTAL_DAYS=sqlValueList[0].TOTAL_DAYS
        }


        sql.close()
        return TOTAL_DAYS

    }


    // get employees Holiday Counts from Holiday Calendar
    def getHolidayCount() {

        def sql = new Sql(dataSource)

        def totalHoliday
        //def holidays

        def fromDate = params.startDate
        def toDate = params.endDate

        def holidayTypeIdObj = HrLeave.findById(Long.valueOf(params.holidayTypeId))
        def holidayCountStatus = holidayTypeIdObj.holidayCount

        println("fromDate :"+fromDate)
        println("toDate :"+toDate)
        println("holidayTypeIdObj :"+holidayTypeIdObj)
        println("holidayCountStatus :"+holidayCountStatus)

        if(holidayCountStatus == 1)
        {
        //totalHoliday = sql.call("{call get_hr_holiday_count(?,?)}",[fromDate,toDate])
        def totalHolidayArray = sql.firstRow("select get_hr_holiday_count(?,?) totalHoliday from dual",fromDate,toDate)
        //String query = "select get_hr_holiday_count("+fromDate+","+toDate+") totalHolidayArray from dual";
        //sql.call("{call get_hr_holiday_count(?,?)}",[fromDate,toDate]) {
          //  holidays -> println holidays
        //}
        //totalHoliday = sql.firstRow(query).get("totalHolidayArray")
        totalHoliday =totalHolidayArray[0]
        }
        else
        {
        totalHoliday = 0
        }
        //def totalHoliday = db.rows("select get_hr_holiday_count(fromDate,toDate) totalDays from dual")
        def data =  totalHoliday.toString()
        render new WSReturn(100, data , null) as JSON
    }

    // start for data retrived from another table ----------------------
    def getData()
    {
        def idNo = params.idNo
        HrEmployee    idObj = HrEmployee.findById(idNo)
        def data1 = idObj.cardNo
        // System.out.println("data1 "+ data1)
        def data2 = idObj.departmentIdHrDepartment.departmentName
        // System.out.println("data2 "+ data2)
        def data3 = idObj.designationIdHrDesignation.designationName
        // System.out.println("data3 "+ data3)
        def data4 = idObj.joiningDate.toString()
        // System.out.println("data4 "+ data4)
        def data5 = idObj.confirmationDate.toString()
        // System.out.println("data5 "+ data5)
        def data6 = idObj.presentStatusIdHrLookup.lookupValue
        // System.out.println("data6 "+ data6)
        def data7 = idObj.employeeTypeIdHrLookup.lookupValue
        // System.out.println("data7 "+ data7)

        def data = data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7
        //System.out.println("data "+ data)

        render new WSReturn(100, data , null) as JSON
        // end for data retrived from another table -------------------------
    }

    // start for data retrived for days from another table ----------------------
    /*def getDays()
    {
        def startDate = Util.getDateMonddyyyy(params.startDate)
        def endDate =  Util.getDateMonddyyyy(params.endDate)
        def utilityObj = new Utility()
        def data = utilityObj.differenceBetweenTwoDate(endDate,startDate)+1
        //def data = hrms.d(params.endDate, params.startDate)
        //def data = data
        //System.out.println("data "+ data)
        render new WSReturn(100, data.toString() , null) as JSON
    }*/
    // end for data retrived for days from another table -------------------------



    // start search for employees salary information
   def search() {

       def cardNo = params.cardNo
       def hrEmployeeLeaveList
       def sessionUserObj = getUserInfo()
       print(sessionUserObj)
       def departmentId = sessionUserObj.hrEmployee.departmentIdHrDepartment.id
       def role = sessionUserObj.role

       if (role=='Admin'){
            hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
                   "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id and br.cardNo in('" + cardNo +"'))")
       }else{
            hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
                   "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id and br.departmentIdHrDepartment.id in(" + departmentId +") and br.cardNo in('" + cardNo +"'))")
       }
       println("hrEmployeeLeaveList Size  "+hrEmployeeLeaveList.size())

       render(view: "list", model: [hrEmployeeLeaveInstanceList: hrEmployeeLeaveList,hrEmployeeLeaveInstanceTotal: hrEmployeeLeaveList.size()])
    }
    // end search for employees salary information
    //
    // start search for employees salary information
   def searchBackup() {

       def cardNo = params.cardNo

       def sessionUserObj = getUserInfo()
       print(sessionUserObj)
       def departmentId = sessionUserObj.hrEmployee.departmentIdHrDepartment.id

       def hrEmployeeLeaveList = HrEmployeeLeave.findAll("from HrEmployeeLeave as b where exists " +
               "(from HrEmployee as br where br.id = b.employeeIdHrEmployee.id and br.departmentIdHrDepartment.id in(" + departmentId +") and br.cardNo in(" + cardNo +"))")
        /*
        def cardNo = '%'+params.cardNo
        def hrEmployeeLeaveInstanceList = HrEmployeeLeave.findAllByCardNoLike(cardNo,params)
        def totalCount = HrEmployeeLeave.countByCardNoLike(cardNo);
        render(view: "list", model: [hrEmployeeLeaveInstanceList: hrEmployeeLeaveInstanceList,hrEmployeeLeaveInstanceTotal:totalCount])
        */
        render(view: "list", model: [hrEmployeeLeaveInstanceList: hrEmployeeLeaveList,hrEmployeeLeaveInstanceTotal: hrEmployeeLeaveList.size()])
    }
    // end search for employees salary information


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


    def checkValue() {

        Sql sql = new Sql(dataSource)

        def  CARD_NO= params.cardNo
        def  startDate= params.startDate
        println("CARD_NO "+CARD_NO+" startDate "+startDate)

        def startDateStr= startDate.split("/")
        def month=startDateStr[1]
        def year=startDateStr[2]
        def monthName=getMonthName(month)
        println("monthName "+monthName+" year "+year)
        def  P_MONTH_NAME =monthName
        def  P_YEAR_NAME =year

        sqlValueList = sql.rows("SELECT COUNT(1) TTL_VAL FROM HR_MONTHLY_SALARY_PROCESS" +
                                " WHERE MONTH_NAME=?" +
                                " AND YEAR_NAME=?" +
                                " AND CARD_NO=?", P_MONTH_NAME, P_YEAR_NAME, CARD_NO.toString().trim())
        println("data  :" + sqlValueList)
        println("sqlValueList.TTL_VAL  :" + sqlValueList.TTL_VAL[0])

        render(sqlValueList.TTL_VAL as JSON)
    }

    public String getMonthName(String str) {
        if (str.equals("01")) {
            return "January"
        }
        else if (str.equals("02")) {
            return "February"
        }
        else if (str.equals("03")) {
            return "March"
        }
        else if (str.equals("04")) {
            return "April"
        }
        else if (str.equals("05")) {
            return "May"
        }
        else if (str.equals("06")) {
            return "June"
        }
        else if (str.equals("07")) {
            return "July"
        }
        else if (str.equals("08")) {
            return "August"
        }
        else if (str.equals("09")) {
            return "September"
        }
        else if (str.equals("10")) {
            return "October"
        }
        else if (str.equals("11")) {
            return "November"
        }
        else if (str.equals("12")) {
            return "December"
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


    def getEmployeeDetailByDepartment() {
        def hrEmployee

        /*def sessionUserObj = getUserInfo()

        print(sessionUserObj)
        def departmentIdObj = sessionUserObj.hrEmployee.departmentIdHrDepartment
        def role = sessionUserObj.role
        if (role=='Admin'){
             hrEmployee = HrEmployee.findByEmployeeName(params.autoData)
        }else{
             hrEmployee = HrEmployee.findByEmployeeNameAndDepartmentIdHrDepartment(params.autoData,departmentIdObj)
        }*/

        hrEmployee = HrEmployee.findByEmployeeName(params.autoData)
        def data

        if (hrEmployee!=null) {

            def data1 = hrEmployee.departmentIdHrDepartment.departmentName
            // System.out.println("data1 "+ data1)
            def data2 = hrEmployee.designationIdHrDesignation.designationName
            // System.out.println("data2 "+ data2)
            def data3 = hrEmployee.id.toString()
            // System.out.println("data3 "+ data3)
            def data4 = hrEmployee.cardNo.toString()
            // System.out.println("data4 "+ data4)
            data = data1+":"+data2+":"+data3+":"+data4
        }
        render new WSReturn(100, data, null) as JSON
    }

    def getEmployeeDetailByCardNoByDepartment() {

        /*def sessionUserObj = getUserInfo()
        def hrEmployee
        print(sessionUserObj)
        def departmentIdObj = sessionUserObj.hrEmployee.departmentIdHrDepartment
        def role = sessionUserObj.role
        if (role=='Admin'){
             hrEmployee = HrEmployee.findByCardNo(params.autoData)
        }else{
             hrEmployee = HrEmployee.findByCardNoAndDepartmentIdHrDepartment(params.autoData,departmentIdObj)
        }*/
        def data
        def hrEmployee = HrEmployee.findByCardNoAndDepartmentIdHrDepartment(params.autoData,departmentIdObj)
        if (hrEmployee!=null) {

            def data1 = hrEmployee.departmentIdHrDepartment.departmentName
            // System.out.println("data1 "+ data1)
            def data2 = hrEmployee.designationIdHrDesignation.designationName
            // System.out.println("data2 "+ data2)
            def data3 = hrEmployee.id.toString()
            // System.out.println("data3 "+ data3)
            def data4 = hrEmployee.employeeName.toString()
            // System.out.println("data4 "+ data4)
            data = data1+":"+data2+":"+data3+":"+data4
        }
        render new WSReturn(100, data, null) as JSON
    }

}
