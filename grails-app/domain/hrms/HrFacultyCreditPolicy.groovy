package hrms

class HrFacultyCreditPolicy {
    static mapping = {
        table 'HR_FACULTY_CREDIT_POLICY'
        version false
        id column: 'ID'
        designationIdHrDesignation column: 'DESIGNATION_ID'
    }

    Long id
    Integer autumnSemesterHour
    Integer summerSemesterHour
    Integer springSemesterHour
    Integer maxCreditHour
    Float graduateExtraPerHour
    Float undergraduateExtraPerHour
    // Relations
    HrDesignation designationIdHrDesignation

    static constraints = {
    id(size: 0..19)
    designationIdHrDesignation(nullable: true)
    autumnSemesterHour(nullable: true)
    summerSemesterHour(nullable: true)
    springSemesterHour(nullable: true)
    maxCreditHour(nullable: true)
    graduateExtraPerHour(nullable: true)
    undergraduateExtraPerHour(nullable: true)
    }

    String toString() {
        return "${designationIdHrDesignation}"
    }

}
