package hrms

class HrHolidayCalendar {
    static mapping = {
        table 'HR_HOLIDAY_CALENDAR'
        version false
        id column: 'HOLIDAY_ID'
        calendarMonthIdHrCalendarMonth column: 'CALENDAR_MONTH_ID'
        holidayTypeIdHrLookup column: 'HOLIDAY_TYPE_ID'
        holidayTypeDescIdHrLookup column: 'HOLIDAY_TYPE_DESC_ID'
        sort "startDate"
    }
    Long id
    Integer calendarYear
    Date startDate
    Date endDate
    Integer totalDays
    // Relations
    HrCalendarMonth calendarMonthIdHrCalendarMonth
    HrLookup holidayTypeIdHrLookup
    HrLookup holidayTypeDescIdHrLookup

    static constraints = {
       id(size: 0..19)
        calendarMonthIdHrCalendarMonth(nullable: true)
        holidayTypeIdHrLookup(nullable: true)
        holidayTypeDescIdHrLookup(nullable: true)
        startDate(nullable: true)
        endDate(nullable: true)
        totalDays(nullable: true)
        calendarYear(nullable: true)
    }

    String toString() {
        return "${calendarMonthIdHrCalendarMonth}"
    }
}
