package hrms

class HrLoadExtraloadDetail {

    static mapping = {
        table 'HR_LOAD_EXTRALOAD_DETAIL'
        version false
        id column: 'ID'
        loadExtraloadIdHrLoadExtraload column: 'LOAD_EXTRALOAD_ID'
        courseIdHrDummyCourse column: 'COURSE_ID'
    }
    Long id
    String loadType
    //Integer courseId
    String programmeType
    Float creditHour
    Float lectureHour
    // Relations
    HrLoadExtraload loadExtraloadIdHrLoadExtraload
    String courseIdHrDummyCourse
    String courseName
    String courseSection

    static constraints = {
        id(size: 0..19)
        loadType(nullable: true)
        //courseId(nullable: true)
        programmeType(nullable: true)
        creditHour(nullable: true)
        lectureHour(nullable: true)
        loadExtraloadIdHrLoadExtraload(nullable: true)
        courseIdHrDummyCourse(nullable: true)
        courseName(nullable: true)
        courseSection(nullable: true)
    }

    String toString() {
        return "${loadExtraloadIdHrLoadExtraload}"
    }
}
