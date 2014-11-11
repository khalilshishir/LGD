package hrms

class HrEmpProfQualification {

    static mapping = {
        table 'HR_EMP_PROF_QUALIFICATION'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        degreeIdHrLookup column: 'DEGREE_ID'
        instituteNameIdHrLookup column: 'INSTITUTE_NAME_ID'
        sort 'slno'
    }
    Long id
    Integer slno
    Integer passingYear
    String remarks
    // Relations
    HrEmployee employeeIdHrEmployee
    HrLookup degreeIdHrLookup
    HrLookup instituteNameIdHrLookup

    static constraints = {
        id(size: 0..19)
        slno(nullable: true)
        degreeIdHrLookup(nullable: true)
        instituteNameIdHrLookup(nullable: true)
        passingYear(nullable: true)
        remarks(nullable: true)
        employeeIdHrEmployee(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
