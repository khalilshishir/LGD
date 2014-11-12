package accounts

class AfmBankBranch {

    static mapping = {
        table 'AFM_BANK_BRANCH'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'AFM_BANK_BRANCH_ID'
        afmBankInfo column: 'AFM_BANK_ID'
        afmChartOfAccounts column: 'ACCOUNT_HEAD_ID'

        sort "id"
        sort id: "asc" // or "desc"
    }

    Long id
    String branchName
    String address
    String accountType
    String accountNo

    AfmBankInfo afmBankInfo
    AfmChartOfAccounts afmChartOfAccounts

    static constraints = {
        id(size: 0..19)
        branchName(size: 0..75,blank: false)
        address(size: 0..100,blank: true)
        accountType(size: 0..100,blank: true)
        accountNo(size: 0..100,blank: true,unique:true)
        afmBankInfo(nullable: true)
        afmChartOfAccounts(nullable: true)
    }

    String toString(){
        return "${branchName}"
    }



}
