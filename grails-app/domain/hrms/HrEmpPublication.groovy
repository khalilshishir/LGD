package hrms

class HrEmpPublication {

    static mapping = {
        table 'HR_EMP_PUBLICATION'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        sort 'slno'
    }

    Long id
    Integer slno
    String publicationTitle
    String subjectName
    Integer yearName
    // Relations
    HrEmployee employeeIdHrEmployee

    static constraints = {
        id(size: 0..19)
        slno(nullable: true)
        publicationTitle(nullable: true)
        subjectName(nullable: true)
        yearName(nullable: true)
        employeeIdHrEmployee(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
