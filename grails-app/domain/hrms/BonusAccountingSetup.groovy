package hrms

import accounts.AfmChartOfAccounts

class BonusAccountingSetup {

    static mapping = {
        table 'HR_AC_SETUP'
        id column: 'ID'
        bonusType column: 'AC_TYPE_ID'
        sort id: 'desc'
    }
    static constraints = {
        id(size: 0..19)
        bonusType(nullable: true)
        employeeCategory(nullable: true)
        grossAmountAc(nullable: true)
        revenueStampAc(nullable: true)
        incomeTaxAc(nullable: true)
    }

    Long id
    HrLookup bonusType
    HrLookup employeeCategory
    AfmChartOfAccounts grossAmountAc
    AfmChartOfAccounts revenueStampAc
    AfmChartOfAccounts incomeTaxAc
}
