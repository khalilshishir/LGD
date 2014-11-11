package hrms

class HrOvertimeDtl {
    static mapping = {
        table 'HR_OVERTIME_DTL'
        version false
        id column: 'ID'
        overtimeIdHrOvertime column: 'OVERTIME_ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
    }
    Long id
    Integer overtimeHour
    // Relations
    HrOvertime overtimeIdHrOvertime
    HrEmployee employeeIdHrEmployee

    static constraints = {
    id(size: 0..19)
     employeeIdHrEmployee(nullable: true)
     overtimeIdHrOvertime(nullable: true)
     overtimeHour(nullable: true)
    }
}
