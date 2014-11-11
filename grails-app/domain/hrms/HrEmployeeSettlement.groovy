package hrms

class HrEmployeeSettlement {
    static mapping = {
        table 'HR_EMPLOYEE_SETTLEMENT'
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        settlementTypeIdHrLookup column: 'SETTLEMENT_TYPE_ID'
    }
    Long id
    String cardNo
    Boolean idClose
    Date closingDate
    Integer noticePeriod
    // payable
    Float pArrear
    Float pGratuity
    Float pPfOwn
    Float pPfOwnInterest
    Float pPfCompany
    Float pPfCompanyInterest
    Float pExtraLoad
    Float pOvertime
    Float pOthersPay
    // deductions
    Float dLoanAgainstSalary
    Float dLoanAgainstPf
    Float dNoticePeriod
    Float dLibraryPenalty
    Float dOthersDeduction
    String remarks
    // Relation
    HrEmployee employeeIdHrEmployee
    HrLookup settlementTypeIdHrLookup

    static constraints = {
        id(size: 0..19)
        cardNo(size: 0..25,nullable: false,unique: true)
        employeeIdHrEmployee(nullable: false,unique: true)
        settlementTypeIdHrLookup(nullable: false)
        idClose(nullable: true)
        closingDate(nullable: true)
        noticePeriod(nullable: true)
        pArrear(nullable: true)
        pGratuity(nullable: true)
        pPfOwn(nullable: true)
        pPfOwnInterest(nullable: true)
        pPfCompany(nullable: true)
        pPfCompanyInterest(nullable: true)
        pExtraLoad(nullable: true)
        pOvertime(nullable: true)
        pOthersPay(nullable: true)
        dLoanAgainstSalary(nullable: true)
        dLoanAgainstPf(nullable: true)
        dNoticePeriod(nullable: true)
        dLibraryPenalty(nullable: true)
        dOthersDeduction(nullable: true)
        remarks(nullable: true)
    }

    String toString(){
        return "${cardNo}"
    }
}
