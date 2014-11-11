package hrms

class HrDummyCourse {

    static mapping = {
        table 'HR_DUMMY_COURSE'
        version false
        id column: 'COURSE_ID'
    }
    Long id
    String courseName
    String programmeType
    Float creditHours
    Float lectureHours

    static hasMany = [hrLoadExtraloadDetailLists: HrLoadExtraloadDetail]

    static constraints = {
        id(size: 0..19)
        courseName(nullable: true)
        programmeType(nullable: true)
        creditHours(nullable: true)
        lectureHours(nullable: true)
    }
}
