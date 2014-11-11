package hrms

class HrIncomeTaxPolicyMst {

    static mapping = {
        table 'HR_INCOME_TAX_POLICY_MST'
        version false
        id column: 'POLICY_TYPE_ID'
    }
    Long id
    String policyType
    Integer taxYear
    Integer basicPayTimes
    Integer basicPayExempted
    Integer specialPayTimes
    Integer specialPayExempted
    Integer daPayTimes
    Integer daPayExempted
    Integer conveyancePayTimes
    Integer conveyancePayExempted
    Integer hrPayTimes
    Integer hrPayExempted
    Integer medicalPayTimes
    Integer medicalPayExempted
    Integer servantPayTimes
    Integer servantPayExempted
    Integer leavePayTimes
    Integer leavePayExempted
    Integer honorariumPayTimes
    Integer honorariumPayExempted
    Integer overtimePayTimes
    Integer overtimePayExempted
    Integer bonusPayTimes
    Integer bonusPayExempted
    Integer otherAllowancePayTimes
    Integer otherAllowancePayExempted
    Integer pfOrgPayTimes
    Integer pfOrgPayExempted
    Integer accruedIntPayTimes
    Integer accruedIntPayExempted
    Integer deemIncTransportPayTimes
    Integer deemIncTransPayExempted
    Integer deemIncFreePayTimes
    Integer deemIncFreePayExempted
    Integer otherPayTimes
    Integer otherPayExempted

    static hasMany = [hrIncomeTaxPolicys : HrIncomeTaxPolicy]

    static constraints = {
        id(size: 0..19)
        policyType(nullable: true)
        taxYear(nullable: true)
        basicPayTimes(nullable: true)
        basicPayExempted(nullable: true)
        specialPayTimes(nullable: true)
        specialPayExempted(nullable: true)
        daPayTimes(nullable: true)
        daPayExempted(nullable: true)
        conveyancePayTimes(nullable: true)
        conveyancePayExempted(nullable: true)
        hrPayTimes(nullable: true)
        hrPayExempted(nullable: true)
        medicalPayTimes(nullable: true)
        medicalPayExempted(nullable: true)
        servantPayTimes(nullable: true)
        servantPayExempted(nullable: true)
        leavePayTimes(nullable: true)
        leavePayExempted(nullable: true)
        honorariumPayTimes(nullable: true)
        honorariumPayExempted(nullable: true)
        overtimePayTimes(nullable: true)
        overtimePayExempted(nullable: true)
        bonusPayTimes(nullable: true)
        bonusPayExempted(nullable: true)
        otherAllowancePayTimes(nullable: true)
        otherAllowancePayExempted(nullable: true)
        pfOrgPayTimes(nullable: true)
        pfOrgPayExempted(nullable: true)
        accruedIntPayTimes(nullable: true)
        accruedIntPayExempted(nullable: true)
        deemIncTransportPayTimes(nullable: true)
        deemIncTransPayExempted(nullable: true)
        deemIncFreePayTimes(nullable: true)
        deemIncFreePayExempted(nullable: true)
        otherPayTimes(nullable: true)
        otherPayExempted(nullable: true)
    }

    String toString(){
        return "${policyType}"
    }
}
