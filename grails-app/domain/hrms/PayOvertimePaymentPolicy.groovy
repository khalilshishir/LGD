package hrms

class PayOvertimePaymentPolicy {

    static mapping = {
        table 'PAY_OVERTIME_PAYMENT_POLICY'
        version false
        id column: 'ID'
        payscaleIdHrPayscale column: 'PAYSCALE_ID'
    }
    Long id
    String daysType
    Integer overtimeHrFrom
    Integer overtimeHrTo
    Float maxAmountInDay
    Float maxAmountInMonth
    // Relation
    HrPayscale payscaleIdHrPayscale

    static constraints = {
        id(size: 0..19)
        payscaleIdHrPayscale(nullable: true)
        daysType(nullable: false)
        overtimeHrFrom(nullable: true)
        overtimeHrTo(nullable: true)
        maxAmountInDay(nullable: true)
        maxAmountInMonth(nullable: true)
    }

    String toString(){
        return "${payscaleIdHrPayscale}"
    }
}
