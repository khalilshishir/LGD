package hrms

class HrTaGaSodMonthlyEffort {

    static mapping = {
        table 'HR_TA_GA_SOD_MONTHLY_EFFORT'
        version false
        id column: 'ID'
        semester column: 'SEMESTER'
        semesterYear column: 'SEMESTER_YEAR'
        workingMonth column: 'WORKING_MONTH'
        workingYear column: 'WORKING_YEAR'
        hrTaSodInfo column: 'TA_GA_SOD_ID'
        workingHour column: 'WORKING_HOUR'
        isActive column: 'IS_ACTIVE'
    }

    Long id
    String   semester
    Integer  semesterYear
    String workingMonth
    Integer workingYear
    HrTaSodInfo hrTaSodInfo
    Double workingHour
    String isActive

    static constraints = {
        id(size: 0..19)
        semester(nullable: false)
        semesterYear(nullable: false)
        workingMonth(nullable: false)
        workingYear(nullable: false)
        //hrTaSodInfo(nullable: false)
        hrTaSodInfo(nullable: false,unique: ['semester','semesterYear','workingMonth','workingYear'])
        workingHour(nullable: false,size: 0..15,scale: 2)
        isActive(nullable: false,size: 0..5)
    }

    String toString(){
        return "${semester}"
    }


}
