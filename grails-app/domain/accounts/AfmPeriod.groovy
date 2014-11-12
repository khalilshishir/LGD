package accounts

class AfmPeriod {

    static mapping = {
        table 'AFM_PERIOD'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'

        sort periodYear: "asc" // or "desc"
        sort serialNo: "asc" // or "desc"

    }

    Long id
    String periodName
    String periodStatus
    Integer periodYear
    Integer serialNo

    static constraints = {
        id(size: 0..19)
        periodName(size: 0..25,blank:false)
        periodStatus(size: 0..5,nullable: true)
        periodYear(size: 0..4,nullable: true)
        serialNo(nullable: true)
    }

    String toString(){
        return "${periodName}"
    }

}
