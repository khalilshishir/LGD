package hrms

class HrTagaDetail {

    static mapping = {
        table 'HR_TAGA_DETAIL'
        version false
        id column: 'ID'
        tagaMstIdHrTagaMaster column: 'TAGA_MST_ID'
        sort 'tagaDate'
    }

    Long id
    Date tagaDate
    String fromTagaHour
    String toTagaHour
    String totalTagaHour
    String purpose
    String extraTagaHour
    // Relations
    HrTagaMaster tagaMstIdHrTagaMaster

    static constraints = {
        id(size: 0..19)
        tagaDate(nullable: true)
        fromTagaHour(nullable: true)
        toTagaHour(nullable: true)
        totalTagaHour(nullable: true)
        tagaMstIdHrTagaMaster(nullable: true)
        extraTagaHour(nullable: true)
        purpose(nullable: true)
    }
}
