package hrms

class HrAttnInfo {

    static mapping = {
        table 'HR_ATTN_INFO'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        sort entryDate: "desc" // or "asc"
        // test
    }

    Long id
    String terminalId
    String day
    String month
    String year
    String hour
    String minute
    String second
    String rowData
    String fileName
    String cardNo
    Integer hrMinSec
    Date entryDate
    String entryMonth
    Integer entryYear
    String entryTime

    static constraints = {
        id(size:0..19)
        terminalId(nullable: true)
        day(nullable: true)
        month(nullable: true)
        year(nullable: true)
        hour(nullable: true)
        minute(nullable: true)
        second(nullable: true)
        rowData(nullable: true)
        fileName(nullable: true,unique: ['cardNo','month','day','hour','minute','second'])
        cardNo(nullable: true)
        hrMinSec(nullable: true)
        entryDate(nullable: true)
        entryMonth(nullable: true)
        entryYear(nullable: true)
        entryTime(nullable: true)
    }

    String toString() {
        return "${fileName}"
    }
}
