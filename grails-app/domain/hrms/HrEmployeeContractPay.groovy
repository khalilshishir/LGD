package hrms

class HrEmployeeContractPay {

    static mapping = {
        table 'HR_EMPLOYEE_PARTTIME_PAY'
        version false
        id column: 'PARTTIME_PAY_ID'
        employee column: 'EMPLOYEE_ID'
        semesterYear column: 'SEMESTER_YEAR'
        semesterName column: 'SEMESTER'
        semesterStartDate column: 'SEMESTER_START_DATE'
        semesterEndDate column: 'SEMESTER_END_DATE'
        ug1stInstallmentDate column: 'UG_1ST_INSTALLMENT_DATE'
        ug2ndInstallmentDate column: 'UG_2ND_INSTALLMENT_DATE'
        g1stInstallmentDate column: 'G_1ST_INSTALLMENT_DATE'
        ugCourseOffered column: 'UG_COURSE_OFFERED'
        ugTotalLectureHour column: 'UG_TOTAL_LECTURE_HOUR'
        ugTotalLectureHourRate column: 'UG_TOTAL_LECTURE_HOUR_RATE'
        ugTotalHonorium column: 'UG_TOTAL_HONORIUM'
        gCourseOffered column: 'G_COURSE_OFFERED'
        gTotalLectureHour column: 'G_TOTAL_LECTURE_HOUR'
        gTotalLectureHourRate column: 'G_TOTAL_LECTURE_HOUR_RATE'
        gTotalHonorium column: 'G_TOTAL_HONORIUM'
        incomeTax column: 'INCOME_TAX'
        revenueStamp column: 'REVENUE_STAMP'
        ugNumberOfInstallment column: 'UG_NUMBER_OF_INSTALLMENT'
        gNumberOfInstallment column: 'G_NUMBER_OF_INSTALLMENT'
    }

    Long id
    HrEmployee employee

    Integer semesterYear
    String semesterName

    Date semesterStartDate
    Date semesterEndDate
    Date ug1stInstallmentDate
    Date ug2ndInstallmentDate
    Date g1stInstallmentDate

    Integer ugNumberOfInstallment
    Integer ugCourseOffered
    Float ugTotalLectureHour
    Float ugTotalLectureHourRate
    Float ugTotalHonorium

    Integer gNumberOfInstallment
    Float gCourseOffered
    Float gTotalLectureHour
    Float gTotalLectureHourRate
    Float gTotalHonorium

    Float revenueStamp
    Float incomeTax

    static constraints = {
        id(size: 0..19)
        employee(nullable: true)
        semesterYear(nullable: true)
        semesterName(nullable: true)
        semesterStartDate(nullable: true)
        semesterEndDate(nullable: true)
        ug1stInstallmentDate(nullable: true)
        ug2ndInstallmentDate(nullable: true)
        g1stInstallmentDate(nullable: true)
        ugCourseOffered(nullable: true)
        ugTotalLectureHour(nullable: true,scale: 2)
        ugTotalLectureHourRate(nullable: true,scale: 2)
        ugTotalHonorium(nullable: true,scale: 2)
        gCourseOffered(nullable: true,scale: 2)
        gTotalLectureHour(nullable: true,scale: 2)
        gTotalLectureHourRate(nullable: true,scale: 2)
        gTotalHonorium(nullable: true,scale: 2)
        incomeTax(nullable: true,scale: 2)
        ugNumberOfInstallment(nullable: true)
        gNumberOfInstallment(nullable: true)
        revenueStamp(nullable: true,scale: 2)
    }

    String toString() {
        return employee?.employeeName
    }

}
