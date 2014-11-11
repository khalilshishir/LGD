package hrms

class HrOvertime {
    static mapping = {
        table 'HR_OVERTIME'
        version false
        id column: 'OVERTIME_ID'
        payscaleIdHrPayscale column: 'PAYSCALE_ID'
    }
    Long id
    Integer overtimeYear
    String overtimeMonth
    // Relation
    HrPayscale payscaleIdHrPayscale

    static hasMany = [hrOvertimeDtls : HrOvertimeDtl]

    static constraints = {
        id(size: 0..19)
        overtimeYear(nullable: true)
        overtimeMonth(nullable: true)
        payscaleIdHrPayscale(nullable: true)
    }

    String toString() {
        return "${payscaleIdHrPayscale}"
    }
}
