package hrms

class HrIncomeTaxPolicy {

    static mapping = {
        table 'HR_INCOME_TAX_POLICY'
        version false
        id column: 'ID'
        policyTypeIdHrIncomeTaxPolicyMst column: 'POLICY_TYPE_ID'
        sort 'slNo'
    }
    Long id
    Integer slNo
    Integer taxableAmountFrom
    Float taxRate
    Boolean isBalanceStage
    // Relations
    HrIncomeTaxPolicyMst policyTypeIdHrIncomeTaxPolicyMst

    static constraints = {
        id(size: 0..19)
        slNo(nullable: true)
        taxableAmountFrom(nullable: true)
        taxRate(nullable: true)
        policyTypeIdHrIncomeTaxPolicyMst(nullable: true)
    }

    String toString() {
        return "${policyTypeIdHrIncomeTaxPolicyMst}"
    }
}
