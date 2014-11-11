package hrms

class HrLeave {

    static mapping = {
        table 'HR_LEAVE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'LEAVE_ID'
        // test
    }
    Long  id
    String leaveType
    Integer maximumDays
    String effectiveDate
    String shortName
    Integer holidayCount

    static constraints = {
        id(size:0..19)
        leaveType(size: 0..25,blank: false, unique: true)
        shortName(size: 0..10,blank: false,unique: true)
        maximumDays(size:0..2)
        effectiveDate(size: 0..15,blank: true)
        holidayCount(nullable: true)
    }

    String toString(){
        return "${leaveType}"
    }
}
