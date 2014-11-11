package hrms

class PayGratuityPaymentPolicy {

    static mapping = {
        table 'PAY_GRATUITY_PAYMENT_POLICY'
        version false
        id column: 'ID'
    }

    Long id
    String typeName
    Float serviceLengthFrom
    Float serviceLengthTo
    Float provisionRatio
    Float paymentRatio

    static constraints = {
        id(size: 0..19)
        typeName(size: 0..15, nullable: false)
        serviceLengthFrom(nullable: false)
        serviceLengthTo(nullable: false)
        provisionRatio(nullable: false)
        paymentRatio(nullable: false)
    }

    String toString(){
        return "${typeName}"
    }
}
