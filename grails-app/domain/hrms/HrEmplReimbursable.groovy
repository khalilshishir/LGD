package hrms

class HrEmplReimbursable {

    static mapping = {
        table 'HR_EMPL_REIMBURSABLE'
        version false
        id column: 'ID'
        hrEmployee column: 'EMPLOYEE_ID'
        sort id: "desc" // or "asc"
       // sort voucherDate: "desc" // or "asc"

    }

    Long id
    Double contractAmount
    Date contractDate

    Date startDate
    Date endDate
    Integer totalDays
    String contractReason
    String remarks

    // Relation
    HrEmployee hrEmployee

    static hasMany = [hrEmplReimbursableDetail: HrEmplReimbursableDetail]


    static constraints = {
        id(size: 0..19)

        contractAmount(size: 0..25,nullable: false,unique: ['hrEmployee','contractDate','startDate','endDate'])
        contractDate(nullable: true)
        startDate(nullable: true)
        endDate(nullable: true)
        totalDays(nullable: true)
        contractReason(nullable: true)
        remarks(nullable: true)
        hrEmployee(nullable: false)

    }

    String toString() {
        return "${hrEmployee}"
    }

}
