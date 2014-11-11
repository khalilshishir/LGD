package hrms

class HrLoan {

    static mapping = {
        table 'HR_LOAN'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'LOAN_ID'
        // test
    }
    Long  id
    String loanType
    Integer maxSchedule
    String shortName
    Float intRate
    String remarks
    Boolean isActive

    static constraints = {
        id(size:0..19)
        loanType(size: 0..25,blank: false, unique: true)
        shortName(size: 0..2,blank: false,unique: true)
        maxSchedule(size:0..3)
        intRate(nullable: false)
        remarks(size: 0..100,nullable: true)
        isActive(nullable:true)
    }

    String toString(){
        return "${loanType}"
    }
}
