package hrms

class HrOvertimeMaster {

    static mapping = {
        table 'HR_OVERTIME_MASTER'
        version false
        id column: 'OT_MST_ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
    }
    Long id
    Integer otYear
    String otMonth
    Long designationId
    Long departmentId
    String officeInTime
    String officeOutTime
    String totalOfficeTime
    Long repEmployeeId
    Boolean withheldPayment
    String reasonOfWithheld
    Long payscaleId
    String gradeNo
    String stage
    // Relations
    HrEmployee employeeIdHrEmployee

    static hasMany = [hrOvertimeDetails: HrOvertimeDetail]

    static constraints = {
        id(size: 0..19)
        otYear(nullable: true)
        otMonth(nullable: true)
        employeeIdHrEmployee(nullable: true)
        designationId(nullable: true)
        departmentId(nullable: true)
        officeInTime(nullable: true)
        officeOutTime(nullable: true)
        totalOfficeTime(nullable: true)
        repEmployeeId(nullable: true)
        withheldPayment(nullable: true)
        reasonOfWithheld(nullable: true)
        payscaleId(nullable: true)
        gradeNo(nullable: true)
        stage(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
