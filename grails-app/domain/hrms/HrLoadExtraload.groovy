package hrms

class HrLoadExtraload {
    static mapping = {
        table 'HR_LOAD_EXTRALOAD'
        version false
        id column: 'LOAD_EXTRALOAD_ID'
        semesterYear column: 'SEMESTER_YEAR'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'

        sort id: "desc" // or "asc"
        sort semesterYear: "desc" // or "asc"

    }
    Long id
    Integer designationId
    String semesterType
    Integer semesterYear
    Boolean salaryHold
    String salaryProcess
    Integer incomeTax

    String facultyId
    // Relation
    HrEmployee employeeIdHrEmployee

    static hasMany = [hrLoadExtraloadDetails: HrLoadExtraloadDetail]

    static constraints = {
        id(size: 0..19)
        employeeIdHrEmployee(nullable: true)
        designationId(nullable: true)
        semesterType(nullable: true)
        semesterYear(nullable: true)
        incomeTax(nullable: true)
    }
    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
