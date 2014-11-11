package hrms

class HrEmplReimbursableDetail {

    static mapping = {
        table 'HR_EMPL_REIMBURSABLE_DETAIL'
        version false
        id column: 'ID'

        sort id: "desc" // or "asc"
       // sort voucherDate: "desc" // or "asc"

    }

    Long id
    Double allowanceAmount
    Date allowanceDate
    String isPaid
    // Relation
    HrEmplReimbursable hrEmplReimbursable
    HrLookup allowanceType


    static constraints = {
        id(size: 0..19)
        allowanceAmount(nullable: true)
        allowanceDate(nullable: true)
        isPaid(nullable: true)
        hrEmplReimbursable(nullable: true)
        allowanceType(nullable: true)
    }

    String toString() {
        return "${allowanceType}"
    }

}
