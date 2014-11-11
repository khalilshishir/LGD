package hrms

class HrCalendar {
    static mapping = {
        table 'HR_CALENDAR'
        version false
        id column: 'HR_CALENDAR_ID'
        sort calendarYear: "desc" // or "asc"
        sort startMonth: "desc" // or "asc"
    }
    Long id
    Integer calendarYear
    Date startMonth
    Date endMonth

    static hasMany = [hrCalendarMonths : HrCalendarMonth]

    static constraints = {
        id(size: 0..19)
        calendarYear(nullable: true)
        startMonth(nullable: true)
        endMonth(nullable: true)
    }

    String toString(){
        return "${calendarYear}"
    }
}
