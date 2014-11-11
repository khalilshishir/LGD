package hrms

import groovy.sql.Sql
import grails.converters.JSON
import java.text.SimpleDateFormat
import static util.DateRelated.changeDateForProcedure


class HrPayrollProcessController {

    def dataSource
    List salaryTypeList

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        render(view: "hrPayrollProcessPage", model: [:])
    }



    // Payroll Process
    def payrollProcess() {

        /*
       * 1) Checking Whether attendance  Processed or not   If not Processed then processed it
       * 2) Checking Whether Deduction  Processed or not    If not Processed then processed it
       * 3)payroll process will be execute.
       * */

        Sql sql = new Sql(dataSource)
        def data
        def salaryMonth =  params.salaryMonth
        def attnFromDate =  params.attnFromDate
        def attnToDate =  params.attnToDate
        println("salaryMonth "+salaryMonth)
        println("attnFromDate "+attnFromDate)
        println("attnToDate "+attnToDate)

/*        HR_ATTN_PROCESS (P_DATE_FROM DATE,P_DATE_TO DATE)
        HR_DEDUCTION_PROCESS (P_MONTH_YEAR  IN VARCHAR2)
        HR_PAYROLL_PROCESS (P_MONTH_YEAR  IN VARCHAR2)*/

        sql.call("{call HR_ATTN_PROCESS (?,?) }", [attnFromDate,attnToDate])

        sql.call("{call HR_DEDUCTION_PROCESS (?)}", [salaryMonth])

        sql.call("{call HR_PAYROLL_PROCESS (?)}", [salaryMonth])

        data = 'Salary Process Successfully Completed...'

        System.out.println('Status is: '+data);

        sql.close()
        render new WSReturn(100, data , null) as JSON
    }





    // Payroll Rollback Process
    def payrollRollback()
    {
        def  data
        Sql sql = new Sql(dataSource)

        def salaryMonth =  params.salaryMonth
        def salaryType =  params.salaryType

        String salaryDate=changeDateForProcedure('01/'+params.salaryMonth)
        println("salaryDate : dd-mon-yyyy "+salaryDate)
        println("salaryMonth "+salaryMonth)
        println("salaryType "+salaryType)

        sql.call("{call HR_DEDUCTION_PROCESS_ROLLBACK (?,?) }", [salaryMonth,salaryType])

        sql.call("{call HR_ATTN_PROCESS_ROLLBACK (?,?)}", [salaryDate,salaryType])

        sql.call("{call HR_PAYROLL_PROCSS_ROLLBACK (?,?)}", [salaryMonth,salaryType])

        data = 'Process Rollback Successful...'

        render new WSReturn(100, data , null) as JSON
   }


    //--------------- start get periodName  from a CalendarYear ---------------------------------------------------
    def getPeriodName()
    {
        def yearName = Long.valueOf(params.yearName)
        def query = "from HrCalendarMonth m where m.hrCalendarIdHrCalendar.id in(" + yearName + ") and salary=0 order by m.serialNo"
        def hrCalendarMonths = HrCalendarMonth.executeQuery(query)
        def sb = new StringBuilder()
        sb.append("[")
        for (HrCalendarMonth hrCalendarMonth: hrCalendarMonths) {
            sb.append("{'id':'" + hrCalendarMonth.id + "', 'value':'" + hrCalendarMonth.periodName + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        //System.out.println("sb==> " + sb.toString());
        render new WSReturn(100, sb.toString(), null) as JSON
    }
    //--------------- end get periodName  from a CalendarYear ------------------------------------------------------
    def getSalaryType()
    {
        Sql sql = new Sql(dataSource)

        def P_MONTH_YEAR = params.salaryMonth
        salaryTypeList=null
        salaryTypeList = sql.rows("SELECT distinct SALARY_TYPE FROM HR_MONTHLY_SALARY_PROCESS WHERE TO_CHAR(SALARY_MONTH,'MM/YYYY') = ?",P_MONTH_YEAR)
        println("salaryTypeList "+salaryTypeList)
        def sb = new StringBuilder()
        sb.append("[")
        for ( def salaryTypeLst: salaryTypeList) {
            sb.append("{'id':'" + salaryTypeLst.SALARY_TYPE + "', 'value':'" + salaryTypeLst.SALARY_TYPE + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")

        sql.close()
        render new WSReturn(100, sb.toString(), null) as JSON
    }


    // start for data retrieved from another table ----------------------
    def getMonthName()
    {
        def idObj = HrCalendarMonth.get(params.periodName)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        String deductionMonth = simpleDateFormat.format(idObj.startDate);
        def data = deductionMonth
        //System.out.println("Total data= "+ data)
        render new WSReturn(100, data , null) as JSON
        // end for data retrieved from another table -------------------------
    }


    def getDateRange()
    {
        def idObj = HrCalendarMonth.get(params.periodName)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String fromDate = simpleDateFormat.format(idObj.startDate);
        //System.out.println("fromDate :"+ fromDate)
        String toDate = simpleDateFormat.format(idObj.endDate);
        //System.out.println("toDate :"+ toDate)
        def data = fromDate+":"+toDate
        //System.out.println("Total data= "+ data)
        render new WSReturn(100, data , null) as JSON
        // end for data retrieved from another table -------------------------
    }


}