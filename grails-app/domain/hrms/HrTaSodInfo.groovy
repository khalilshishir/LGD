package hrms


class HrTaSodInfo {

    static mapping = {
        table 'HR_TA_SOD_INFO'
        version false
        id column: 'TA_SOD_INFO_ID'

        semesterYear column: 'SEMESTER_YEAR'
        semester column: 'SEMESTER'
        appointmentFor column: 'APPOINTMENT_FOR'
      /*  totalWorkingHour column: 'TOTAL_WORKING_HOUR'*/
        student column: 'STUDENT_ID'
        department column: 'DEPARTMENT_ID'
/*        fromDate column: 'FROM_DATE'
        toDate column: 'TO_DATE'*/
        paymentTypeIdHrLookup column: 'PAYMENT_TYPE_ID'
        bankAcNo column: 'BANK_AC_NO'
        bankId column: 'BANK_ID'
        branchId column: 'BRANCH_ID'

        taJaSodType column: 'TA_JA_SOD_TYPE'
        revenueStamp column: 'REVENUE_STAMP'
        ratePerHour column: 'RATE_PER_HOUR'
        maxWorkLimit  column: 'MAX_WORK_LIMIT'
        studentName  column: 'STUDENT_NAME'
    }

    Long id
    Integer semesterYear
    String semester
    String appointmentFor
    /*String totalWorkingHour*/
    String student
    /*
    Date fromDate
    Date toDate
    */
    HrDepartment department

    HrLookup paymentTypeIdHrLookup
    String bankAcNo
    Long bankId
    Long branchId

    String  taJaSodType
    Integer revenueStamp
    Integer  ratePerHour
    Integer  maxWorkLimit
    String  studentName

    static constraints = {
        id(size: 0..19)
        semesterYear(nullable: true)
        semester(nullable: true)
        appointmentFor(nullable: true)
        /*totalWorkingHour(nullable: true)*/
        student(nullable: true)
        department(nullable: true)
        /*fromDate(nullable: true)
        toDate(nullable: true)*/
        paymentTypeIdHrLookup(nullable: true)
        bankAcNo(nullable: true)
        bankId(nullable: true)
        branchId(nullable: true)

        taJaSodType(nullable: false)
        revenueStamp(nullable: false)
        ratePerHour(nullable: false)
        maxWorkLimit(nullable: false)
        studentName(nullable: false)
    }


}
