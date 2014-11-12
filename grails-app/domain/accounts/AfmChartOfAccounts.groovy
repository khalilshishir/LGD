package accounts

class AfmChartOfAccounts {

    static mapping = {
        table 'AFM_CHART_OF_ACCOUNTS'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ACCOUNT_HEAD_ID'
        parentAccountHead column: 'PARENT_ACCOUNT_HEAD_ID'
        sort "accountCode"
        sort accountCode: "asc" // or "desc"
    }

    Long id
    String accountHeadName
    AfmChartOfAccounts parentAccountHead
    String companyId
    String isSubsidiary
    String accountIdType
    String accountHeadType
    String isAgreement
    String isInterest
    String plBlHeadId
    String noteNo
    Long accountCode
    Float depRatio
    String isBankCash
    String isBudgetHead

    static hasMany = [afmVoucherDetails: AfmVoucherDetail,afmChartOfAccs: AfmChartOfAccounts]

    static constraints = {
        id(size: 0..50)
        accountHeadName(blank: false,unique:true)
        parentAccountHead(nullable: true)
        companyId(nullable: true)
        isSubsidiary(nullable: true)
        accountIdType(nullable: true)
        accountHeadType(nullable: true)
        isAgreement(nullable: true)
        isInterest(nullable: true)
        plBlHeadId(nullable: true)
        noteNo(nullable: true)
        accountCode(nullable: true,unique:true)
        depRatio(size: 0..15, scale: 2,nullable: true)
        isBankCash(nullable: true)
        isBudgetHead(nullable: true)

    }

    String toString() {
        return "${accountHeadName}"
    }

}
