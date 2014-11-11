package hrms

class HrAttendanceProcess {

    static mapping = {
        table 'HR_ATTENDANCE_PROCESS'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        // test
    }

    Long id
    Long employeeId
    String cardNo
    String employeeName
    Long departmentId
    String departmentName
    Long designationId
    String designationName
    Integer earnedLeave
    Integer sickLeave
    Integer sickLeaveHalfPay
    Integer maternityLeave
    Integer casualLeave
    Integer sactionalLeave
    Integer studyLeave
    Integer leaveWithoutPay
    Integer maxMonthDay
    Integer monthlyHoliday
    Integer salaryPayableDay
    String attnMonth
    Integer attnYear
    Date attendanceMonth
    Boolean attnHold
    Date joiningDate

    static constraints = {
        id(size:0..19)
        cardNo(nullable: true)
        employeeName(nullable: true)
        employeeId(nullable: true)
        departmentId(nullable: true)
        departmentName(nullable: true)
        designationId(nullable: true)
        designationName(nullable: true)
        earnedLeave(nullable: true)
        sickLeave(nullable: true)
        sickLeaveHalfPay(nullable: true)
        maternityLeave(nullable: true)
        casualLeave(nullable: true)
        sactionalLeave(nullable: true)
        studyLeave(nullable: true)
        leaveWithoutPay(nullable: true)
        maxMonthDay(nullable: true)
        monthlyHoliday(nullable: true)
        salaryPayableDay(nullable: true)
        attnMonth(nullable: true)
        attnYear(nullable: true)
        attendanceMonth(nullable: true)
        attnHold(nullable: true)
        joiningDate(nullable: true)
    }

    String toString() {
        return "${employeeName}"
    }
}
