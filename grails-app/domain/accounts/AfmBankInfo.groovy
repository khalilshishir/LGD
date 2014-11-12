package accounts

class AfmBankInfo {

    static mapping = {
        table 'AFM_BANK_INFO'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'AFM_BANK_ID'
        sort "id"
        sort id: "asc" // or "desc"
    }

    Long id
    String bankName

    static hasMany = [afmBankBranch: AfmBankBranch]

    static constraints = {
        id(size: 0..19)
        bankName(size: 0..100,blank: false,unique:true)
     //   afmVoucherDetail cascade: "all-delete-orphan"
    }

    String toString(){
        return "${bankName}"
    }

}
