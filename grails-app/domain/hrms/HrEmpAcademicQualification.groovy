package hrms

class HrEmpAcademicQualification {
    static mapping = {
        table 'HR_EMP_ACADEMIC_QUALIFICATION'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        examinationIdHrLookup column: 'EXAMINATION_ID'
        examGroupIdHrLookup column: 'EXAM_GROUP_ID'
        boardIdHrLookup column: 'BOARD_ID'
        sort 'slno'
    }

    Long id
    Boolean isDivGpa
    String division
    Float cgpa
    Integer slno
    Integer passingYear

    // Relations
    HrEmployee employeeIdHrEmployee
    HrLookup examinationIdHrLookup
    HrLookup examGroupIdHrLookup
    HrLookup boardIdHrLookup

    static constraints = {
        id(size: 0..19)
        slno(nullable: true)
        employeeIdHrEmployee(nullable: true)
        examinationIdHrLookup(nullable: true)
        examGroupIdHrLookup(nullable: true)
        boardIdHrLookup(nullable: true)
        isDivGpa(nullable: true)
        division(nullable: true)
        cgpa(nullable: true)
        passingYear(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
