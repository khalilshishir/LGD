package hrms

class HrOvertimeDetail {

    static mapping = {
        table 'HR_OVERTIME_DETAIL'
        version false
        id column: 'ID'
        otMstIdHrOvertimeMaster column: 'OT_MST_ID'
        sort 'otDate'
    }

    Long id
    Date otDate
    String fromOtHour
    String toOtHour
    String totalOtHour
    String purpose
    String extraOtHour
    // Relations
    HrOvertimeMaster otMstIdHrOvertimeMaster

    static constraints = {
        id(size: 0..19)
        otDate(nullable: true)
        fromOtHour(nullable: true)
        toOtHour(nullable: true)
        totalOtHour(nullable: true)
        otMstIdHrOvertimeMaster(nullable: true)
        extraOtHour(nullable: true)
        purpose(nullable: true)
    }
}
