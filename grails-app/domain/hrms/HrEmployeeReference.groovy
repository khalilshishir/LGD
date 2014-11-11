package hrms

class HrEmployeeReference {

    static mapping = {
        table 'HR_EMPLOYEE_REFERENCE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        sort 'slno'
    }
    Long id
    Integer slno
    String referenceName
    String identiee
    String addressName
    String contactNo
    // Relations
    HrEmployee employeeIdHrEmployee

    static constraints = {
        id(size: 0..19)
        slno(nullable: true)
        referenceName(nullable: true)
        identiee(nullable: true)
        addressName(nullable: true)
        contactNo(nullable: true)
        employeeIdHrEmployee(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
