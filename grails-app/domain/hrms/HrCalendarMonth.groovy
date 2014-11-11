package hrms

class HrCalendarMonth {

    static mapping = {
        table 'HR_CALENDAR_MONTH'
        version false
        id column: 'HR_CALENDAR_MONTH_ID'
        salary column: 'IS_SALARY_PROCESS'
        increment column: 'IS_INCREMENT_PROCESS'
        bonus column: 'IS_BONUS_PROCES'
        hrCalendarIdHrCalendar column: 'HR_CALENDAR_ID'

        sort calendarYear: "desc" // or "asc"
        sort startDate: "desc" // or "asc"

    }
    Long id
    Date startDate
    Date endDate
    String periodName
    Integer calendarYear
    Integer serialNo
    Boolean salary
    Boolean increment
    Boolean bonus
    // Relation
    HrCalendar hrCalendarIdHrCalendar

    static hasMany = [hrHolidayCalendars: HrHolidayCalendar]

    static constraints = {
        id(size: 0..19)
        hrCalendarIdHrCalendar(nullable: true)
        startDate(nullable: true)
        endDate(nullable: true)
        periodName(nullable: true)
        serialNo(nullable: true)
        salary(nullable: true)
        increment(nullable: true)
        bonus(nullable: true)

    }

    String toString() {
        return "${calendarYear}"
    }
}
