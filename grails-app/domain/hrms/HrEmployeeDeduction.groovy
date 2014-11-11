package hrms

class HrEmployeeDeduction {

    static mapping = {
        table 'HR_EMPLOYEE_DEDUCTION'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'EMPL_DEDUCTION_ID'
        sort id: "desc" // or "asc"
    }

    Long id

    String deductionType
    Date deductionDate
    Double deductionAmount
    String deductionReason

    HrEmployee hrEmployee

    static constraints = {
        id(size: 0..19)

        deductionType(size: 0..250,blank:false)
        deductionDate(nullable: false)
        deductionAmount(nullable: false)
        deductionReason(size: 0..500,nullable: false)

        hrEmployee(nullable: false)
    }

    String toString(){
        return "${deductionType}"
    }

}
