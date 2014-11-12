package accounts

class AfmCalendar {

    static mapping = {
        table 'AFM_CALENDAR'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'AFM_CALENDER_ID'
    }

    Long id
    String calendarYear
    Integer period
    String calendarType
    Date  fromDate
    Date  toDate

    static hasMany = [afmCalendarMonth: AfmCalendarMonth]

    static constraints = {
        id(size: 0..19)
        calendarYear(nullable:false,unique:true)
        period(nullable: false)
        calendarType(inList:["Fiscal" , "Calendar Year"],blank: false)
        fromDate(nullable: false)
        toDate(nullable: false)
    }

    String toString(){
        return "${calendarYear}"
    }


}
