package hrms

class PayPfPaymentPolicy {

static mapping = {
    table 'PAY_PF_PAYMENT_POLICY'
    // version is set to false, because this isn't available by default for legacy databases
    version false
    id column: 'ID'
    }

    Long id
    String typeName
    Float serviceLengthFrom
    Float serviceLengthTo
    Float ownContribution
    Float orgContribution
    Boolean isActive

    static constraints = {
        id(size: 0..10)
        typeName(size: 0..15,nullable: false)
        serviceLengthFrom(nullable: false)
        serviceLengthTo(nullable: false)
        ownContribution(nullable: false)
        orgContribution(nullable: false)
        isActive(nullable: true)
    }

    String toString(){
        return "${typeName}"
    }
}
