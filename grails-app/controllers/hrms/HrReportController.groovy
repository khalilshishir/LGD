package hrms

import accounts.Util
import grails.converters.JSON
import groovy.sql.Sql
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.plugins.jasper.JasperService
import org.springframework.beans.factory.annotation.Autowired


class HrReportController {
    @Autowired
    JasperService jasperService


    def dataSource

    List bankNameList
    List branchNameList
    List departmentNameList
    List userRoleList
    List employeeNameList
    List cardNoList
    List accountNoList
    List deptNameList
    List bonusTypeList
    List sqlValueList
    List  employeeTypeList

    private static final String REPORT_FILE_FORMAT = 'pdf'
    private static final String REPORT_NAME_FIELD = 'REPORT_NAME'
    /*private static final String REPORT_FOLDER = 'storeInFromSite'*/
    private static final String SUB_REPORT_FOLDER = 'subReport'
    private static final String OUTPUT_FILE_NAME = 'roleReport'
    private static final String REPORT_TITLE = 'Company Name'
    private static final String REPORT_DIR = 'REPORT_DIR'
    private static final String SUBREPORT_DIR = 'SUBREPORT_DIR'
    private static final String EMPLOYEE_IMAGE_DIR = 'EMPLOYEE_IMAGE_DIR'
    private static final String PDF_EXTENSION = ".pdf"
    private static final String JASPER_EXTENSION = ".jasper"
    private static final String REPORT = "report"
    def index() { }


    def hrReportPage() {

      //  departmentNameList=getDepartmentNameList()

/*        def SYSTEM_ROLE =getUserRole()
        println("SYSTEM_ROLE : "+SYSTEM_ROLE)*/

        //render(view: "hrReport",model:[departmentNameList:departmentNameList,SYSTEM_ROLE:SYSTEM_ROLE])
        //render(view: "hrReportPage",model:[departmentNameList:departmentNameList])

        employeeTypeList=getEmployeeTypeList();
        bankNameList=getBankNameList();

        render(view: "hrReportPage",model:[employeeTypeList:employeeTypeList,bankNameList:bankNameList])

    }

    def leaveReportPage() {

        render(view: "leaveReport",model:[:])

    }

/*    def getUserRole() {

        Sql sql = new Sql(dataSource)

        def sessionUserObj = getUserInfo()
        print(sessionUserObj)
        def userId = sessionUserObj.hrEmployee.id
        userRoleList=null
        userRoleList = sql.rows("SELECT SYSTEM_ROLE,EMPLOYEE_ID FROM IUB_USER WHERE EMPLOYEE_ID=?",userId)
        def SYSTEM_ROLE =userRoleList.SYSTEM_ROLE[0]
        println("SYSTEM_ROLE : "+SYSTEM_ROLE)

        sql.close()
        return SYSTEM_ROLE
   }*/

/*    def getUserInfo() {
        def user = IubUser.findByUserIdAndPassword(session.user.userId, session.user.password)
        return user
        //print(user.hrEmployee.employeeName)
    }*/

    public List getDepartmentNameList() {

        Sql sql = new Sql(dataSource)

/*        def sessionUserObj = getUserInfo()
        print(sessionUserObj)
        def departmentId = sessionUserObj.hrEmployee.departmentIdHrDepartment.id

        departmentNameList = sql.rows("SELECT DEPARTMENT_ID,DEPARTMENT_NAME FROM HR_DEPARTMENT WHERE IS_ACTIVE=1 AND DEPARTMENT_ID=? ORDER BY DEPARTMENT_NAME",departmentId)*/

        departmentNameList = sql.rows("SELECT DEPARTMENT_ID,DEPARTMENT_NAME FROM HR_DEPARTMENT WHERE IS_ACTIVE=1  ORDER BY DEPARTMENT_NAME")

        println("departmentNameList  :"+departmentNameList )

        sql.close()
        return departmentNameList

    }




    def typeWiseEmployeeList() {

        def EMPLOYEE_TYPE_ID= params.employeeTypeIdHrLookup.id

        String reportDir = Util.getReportDirectory()

        String fileName = 'TYPE_WISE_EMPLOYEE_LIST'
        String outputFileName = fileName + PDF_EXTENSION
        String jasperFileName = fileName + JASPER_EXTENSION

        String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
        Map reportParams = new LinkedHashMap()
        reportParams.put(REPORT_DIR, reportDir)
        reportParams.put(SUBREPORT_DIR, reportDir)


        reportParams.put('EMPLOYEE_TYPE_ID', EMPLOYEE_TYPE_ID)

        //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
        JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: reportParams, folder: reportDir)

        ByteArrayOutputStream report = jasperService.generateReport(reportDef)
       // response.contentType = "application/vnd.ms-excel"
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
        response.outputStream << report.toByteArray()
    }

    def bankDetailInformation() {

        def AFM_BANK_ID= params.afmBankId

        String reportDir = Util.getReportDirectory()

        String fileName = 'BANK_DETAIL_INFORMATION'
        String outputFileName = fileName + PDF_EXTENSION
        String jasperFileName = fileName + JASPER_EXTENSION

        String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
        Map reportParams = new LinkedHashMap()
        reportParams.put(REPORT_DIR, reportDir)
        reportParams.put(SUBREPORT_DIR, reportDir)


        reportParams.put('AFM_BANK_ID', AFM_BANK_ID)

        //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
        JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: reportParams, folder: reportDir)

        ByteArrayOutputStream report = jasperService.generateReport(reportDef)
       // response.contentType = "application/vnd.ms-excel"
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
        response.outputStream << report.toByteArray()
    }

    def empIdWiseEmployeeDetails() {

        def CARD_NO= params.cardNo
        //def AFM_BANK_ID= params.employeeName

        String reportDir = Util.getReportDirectory()

        String fileName = 'EMP_ID_WISE_EMPLOYEE_DETAILS'
        String outputFileName = fileName + PDF_EXTENSION
        String jasperFileName = fileName + JASPER_EXTENSION

        String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
        String employeeImageDir = Util.getEmployeeImageDirectory()   //+ File.separator + SUB_REPORT_FOLDER
        Map reportParams = new LinkedHashMap()
        reportParams.put(REPORT_DIR, reportDir)
        reportParams.put(SUBREPORT_DIR, subReportDir)
        reportParams.put(EMPLOYEE_IMAGE_DIR, employeeImageDir)


        reportParams.put('CARD_NO', CARD_NO)

        //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
        JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: reportParams, folder: reportDir)

        ByteArrayOutputStream report = jasperService.generateReport(reportDef)
       // response.contentType = "application/vnd.ms-excel"
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
        response.outputStream << report.toByteArray()
    }

    def monthlyAttendanceReport() {

        Date P_FROM_DATE= Date.parse("dd/MM/yyyy",params.P_FROM_DATE)
        Date P_TO_DATE= Date.parse("dd/MM/yyyy",params.P_TO_DATE)

        String P_DEPARTMENT_NAME= params.DEPARTMENT_NAME

        String reportDir = Util.getReportDirectory()
       // String outputFileName = 'MONTHLY_ATTENDANCE_REPORT' + ".xls"
        String fileName = 'MONTHLY_ATTENDANCE_REPORT'
        String outputFileName = fileName + PDF_EXTENSION
        String jasperFileName = fileName + JASPER_EXTENSION

        String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
        Map reportParams = new LinkedHashMap()
        reportParams.put(REPORT_DIR, reportDir)
        reportParams.put(SUBREPORT_DIR, reportDir)


        reportParams.put('P_FROM_DATE', P_FROM_DATE)
        reportParams.put('P_TO_DATE', P_TO_DATE)
        reportParams.put('P_DEPARTMENT_NAME', P_DEPARTMENT_NAME.trim())

        //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
        JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: reportParams, folder: reportDir)

        ByteArrayOutputStream report = jasperService.generateReport(reportDef)
       // response.contentType = "application/vnd.ms-excel"
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
        response.outputStream << report.toByteArray()
    }

    def leaveReport() {

            String P_LEAVE_YEAR= params.year
            String P_CARD_NO= params.P_CARD_NO

            String reportDir = Util.getReportDirectory()
           // String outputFileName = 'MONTHLY_ATTENDANCE_REPORT' + ".xls"
            String fileName = 'LEAVE_REPORT'
            String outputFileName = fileName + PDF_EXTENSION
            String jasperFileName = fileName + JASPER_EXTENSION

            String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
            Map reportParams = new LinkedHashMap()
            reportParams.put(REPORT_DIR, reportDir)
            reportParams.put(SUBREPORT_DIR, reportDir)


            reportParams.put('P_LEAVE_YEAR', P_LEAVE_YEAR.toString().trim())
            reportParams.put('P_CARD_NO', P_CARD_NO.toString().trim())

            //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
            JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                    parameters: reportParams, folder: reportDir)

            ByteArrayOutputStream report = jasperService.generateReport(reportDef)
           // response.contentType = "application/vnd.ms-excel"
            response.contentType = "application/pdf"
            response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
            response.outputStream << report.toByteArray()
        }

        def empLoanTypeWiseEmployeeLoanSchedule() {

            String LOAN_TYPE_ID= params.LOAN_TYPE_ID
            String EMPLOYEE_ID= params.EMPLOYEE_ID

            String reportDir = Util.getReportDirectory()
           // String outputFileName = 'MONTHLY_ATTENDANCE_REPORT' + ".xls"
            String fileName = 'EMP_LOAN_TYPE_WISE_EMPLOYEE_LOAN_SCHEDULE'
            String outputFileName = fileName + PDF_EXTENSION
            String jasperFileName = fileName + JASPER_EXTENSION

            String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
            Map reportParams = new LinkedHashMap()
            reportParams.put(REPORT_DIR, reportDir)
            reportParams.put(SUBREPORT_DIR, reportDir)


            reportParams.put('LOAN_TYPE_ID', LOAN_TYPE_ID)
            reportParams.put('EMPLOYEE_ID', EMPLOYEE_ID)

            //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
            JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                    parameters: reportParams, folder: reportDir)

            ByteArrayOutputStream report = jasperService.generateReport(reportDef)
           // response.contentType = "application/vnd.ms-excel"
            response.contentType = "application/pdf"
            response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
            response.outputStream << report.toByteArray()
        }

    def leaveApplicationReport() {

        String  APPLICATION_DATE =params.P_APPLICATION_DATE

        Date P_APPLICATION_DATE

        String  P_CARD_NO =params.P_CARD_NO
        if (APPLICATION_DATE != ""){       //Date conversion for report
            P_APPLICATION_DATE= Util.getDateMonddyyyy(APPLICATION_DATE)
        }

        String reportDir = Util.getReportDirectory()
       // String outputFileName = 'MONTHLY_ATTENDANCE_REPORT' + ".xls"
        String fileName = 'LEAVE_APPLICATION'
        String outputFileName = fileName + PDF_EXTENSION
        String jasperFileName = fileName + JASPER_EXTENSION

        String subReportDir = Util.getReportDirectory()   //+ File.separator + SUB_REPORT_FOLDER
        Map reportParams = new LinkedHashMap()
        reportParams.put(REPORT_DIR, reportDir)
        reportParams.put(SUBREPORT_DIR, reportDir)

        reportParams.put('P_CARD_NO', P_CARD_NO)
        reportParams.put('P_APPLICATION_DATE', P_APPLICATION_DATE)


        //JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.XLS_FORMAT,
        JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: reportParams, folder: reportDir)

        ByteArrayOutputStream report = jasperService.generateReport(reportDef)
       // response.contentType = "application/vnd.ms-excel"
        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename="+outputFileName)   //OUTPUT_FILE_NAME+.xls)
        response.outputStream << report.toByteArray()
    }


    def getCardNo() {     // B.EMPLOYEE_TYPE_ID <> 29224 for Regular employee
        Sql sql = new Sql(dataSource)
        def P_CARD_NO = params.term
        def LEAVE_YEAR = params.LEAVE_YEAR

        sqlValueList=null
        sqlValueList = sql.rows("SELECT DISTINCT CARD_NO,EMPLOYEE_NAME " +
                                " FROM HR_EMP_LEAVE_BALANCE_VW " +
                                " WHERE " +
                                " CARD_NO like '%'||?||'%' " +
                                " AND " +
                                " LEAVE_YEAR=? " +
                                " ORDER BY CARD_NO ASC",P_CARD_NO.toString().trim(), LEAVE_YEAR)

        println(" sqlValueList.size()  :" + sqlValueList.size())
        println("sqlValueList.CARD_NO  :" + sqlValueList.CARD_NO)

        sql.close()
        render(sqlValueList.CARD_NO as JSON)
    }

    def getEmployeeName() {     // B.EMPLOYEE_TYPE_ID <> 29224 for Regular employee
        Sql sql = new Sql(dataSource)
        def EMPLOYEE_NAME = params.term
        def LEAVE_YEAR = params.LEAVE_YEAR

        sqlValueList=null
        sqlValueList = sql.rows("SELECT DISTINCT EMPLOYEE_NAME " +
                                " FROM HR_EMP_LEAVE_BALANCE_VW " +
                                " WHERE " +
                                " UPPER(EMPLOYEE_NAME) like '%'||UPPER(?)||'%' " +
                                " AND " +
                                " LEAVE_YEAR=? " +
                                " ORDER BY EMPLOYEE_NAME ASC",EMPLOYEE_NAME.toString().trim(), LEAVE_YEAR)
        println(" sqlValueList.size()  :" + sqlValueList.size())
        println("sqlValueList.EMPLOYEE_NAME  :" + sqlValueList.EMPLOYEE_NAME)

        sql.close()
        render(sqlValueList.EMPLOYEE_NAME as JSON)
    }

    def getEmplNameAcrdToCardNo() {
        Sql sql = new Sql(dataSource)
        def P_CARD_NO = params.P_CARD_NO
        def LEAVE_YEAR = params.LEAVE_YEAR
        println("P_CARD_NO "+P_CARD_NO)
        println("LEAVE_YEAR "+LEAVE_YEAR)
        sqlValueList=null
        sqlValueList = sql.rows("SELECT DISTINCT CARD_NO,EMPLOYEE_NAME " +
                                " FROM HR_EMP_LEAVE_BALANCE_VW " +
                                " WHERE " +
                                " CARD_NO =? " +
                                " AND " +
                                " LEAVE_YEAR=? " +
                                " ORDER BY CARD_NO ASC",P_CARD_NO.toString().trim(), LEAVE_YEAR)
        println(" sqlValueList.size()  :" + sqlValueList.size())
        println("sqlValueList.EMPLOYEE_NAME  :" + sqlValueList.EMPLOYEE_NAME)

        sql.close()
        render(sqlValueList.EMPLOYEE_NAME as JSON)
    }

    def getCardNoAcrdToEmplName() {
        Sql sql = new Sql(dataSource)
        def EMPLOYEE_NAME = params.EMPLOYEE_NAME
        def LEAVE_YEAR = params.LEAVE_YEAR
        println("EMPLOYEE_NAME "+EMPLOYEE_NAME)
        println("LEAVE_YEAR "+LEAVE_YEAR)
        sqlValueList=null
        sqlValueList = sql.rows("SELECT DISTINCT CARD_NO " +
                                " FROM HR_EMP_LEAVE_BALANCE_VW " +
                                " WHERE " +
                                " EMPLOYEE_NAME =? " +
                                " AND " +
                                " LEAVE_YEAR=? " +
                                " ORDER BY CARD_NO ASC",EMPLOYEE_NAME.toString().trim(),LEAVE_YEAR)
        println(" sqlValueList.size()  :" + sqlValueList.size())
        println("sqlValueList.CARD_NO  :" + sqlValueList.CARD_NO)

        sql.close()
        render(sqlValueList.CARD_NO as JSON)
    }

    public List  getEmployeeTypeList() {
        Sql sql = new Sql(dataSource)
        employeeTypeList=null
        employeeTypeList = sql.rows("SELECT LOOKUP_ID,LOOKUP_VALUE FROM HR_LOOKUP WHERE LOOKUP_TYPE_ID=380 AND IS_ACTIVE=1")

        sql.close()
        return employeeTypeList
    }

    public List  getBankNameList() {
        Sql sql = new Sql(dataSource)
        bankNameList=null
        bankNameList = sql.rows("SELECT A.AFM_BANK_ID,A.BANK_NAME FROM AFM_BANK_INFO A" +
                                 " ORDER BY A.BANK_NAME ASC")

        sql.close()
        return bankNameList
    }


}
