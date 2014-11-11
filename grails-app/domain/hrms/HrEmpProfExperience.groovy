package hrms

class HrEmpProfExperience {

    static mapping = {
        table 'HR_EMP_PROF_EXPERIENCE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        sort 'slno'
    }

    Long id
    Integer slno
    String companyName
    String companyAddress
    Date fromdate
    Date todate
    Integer duration
    //Relations
    HrEmployee employeeIdHrEmployee

    static constraints = {
        id(size: 0..19)
        slno(nullable: true)
        companyName(nullable: true)
        companyAddress(nullable: true)
        fromdate(nullable: true)
        todate(nullable: true)
        duration(nullable: true)
        employeeIdHrEmployee(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
