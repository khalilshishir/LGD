package accounts

class AfmCalendarMonth {

    static mapping = {
        table 'AFM_CALENDAR_MONTH'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'AFM_CALENDAR_MONTH_ID'
        afmCalendar column: 'AFM_CALENDER_ID'
        sort "serialNo"
        sort serialNo: "asc" // or "desc"
    }

    Long id

    Integer serialNo
    Integer calendarYear
    String calendarMonth
    Integer calendarQuater
    Date fromDate
    Date toDate
    String periodName
    Integer isAdjustmentPeriod

    AfmCalendar  afmCalendar


    static constraints = {
        id(size: 0..19)
        serialNo(nullable: false)
        calendarYear(size: 0..4,nullable: true)
        calendarMonth(blank: false)
        /*calendarMonth(nullable: false,inList:["January" , "February" , "March" , "April" , "May","June" ,"July" , "August" , "September" , "October" , "November","December"])*/
        calendarQuater(size: 0..4,nullable: true)
        fromDate(nullable: true)
        toDate(nullable: true)
        periodName(nullable: true,unique:true)
        isAdjustmentPeriod(nullable: false)

        afmCalendar cascade: "all-delete-orphan"
//        serialNo()
    }

    String toString(){
        return "${periodName}"
    }



}
