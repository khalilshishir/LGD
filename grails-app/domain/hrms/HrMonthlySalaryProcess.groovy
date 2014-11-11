package hrms

class HrMonthlySalaryProcess {

    static mapping = {
        table 'HR_MONTHLY_SALARY_PROCESS'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
    }
    Long id
    Date salaryMonth
    String monthName
    Integer yearName
    Long employeeId
    String cardNo
    Long departmentId
    Long designationId
    String bankName
    String bankAccountNo
    Long payScaleId
    String stage
    //earnings
    Float basicSalary
    Float houseRent
    Float conveyance
    Float medical
    Float washing
    Float dearness
    Float pfOwn
    Float specialAllowance
    Float specialAllowanceDa
    Float additionalSpAllowance
    Float chargeAllowance
    Float retro
    Float retroPf
    //deductions
    Float incomeTax
    Float pfOffice
    Float pfTotal
    Float pfLoanPrincipal
    Float pfLoanInterest
    Float pfLoanTotal
    Float loanAgainstSalaryPrincipal
    Float loanAgainstSalaryInterest
    Float salaryLoanTotal
    Float groupInsurance
    Float advance
    Float otherDeduction
    Float revenueStamp

    String salaryType
    String status
    Boolean accounting
    Long bankId
    Long bankBranchId
    String afmAccountNo
    Long accountHeadId

    static constraints = {
    id(size: 0..19)
    cardNo(nullable: false)
    salaryMonth(nullable: false)
    bankName(nullable: true)
    bankAccountNo(nullable: true)
    monthName(nullable: false)
    yearName(nullable: false)
    employeeId(nullable: false)
    departmentId(nullable: true)
    designationId(nullable: true)
    payScaleId(nullable: true)
    stage(nullable: true)
    //earnings
    basicSalary(nullable: true)
    houseRent(nullable: true)
    conveyance(nullable: true)
    medical(nullable: true)
    washing(nullable: true)
    dearness(nullable: true)
    pfOwn(nullable: true)
    specialAllowance(nullable: true)
    specialAllowanceDa(nullable: true)
    additionalSpAllowance(nullable: true)
    chargeAllowance(nullable: true)
    retro(nullable: true)
    retroPf(nullable: true)
    //deductions
    incomeTax(nullable: true)
    pfOffice(nullable: true)
    pfTotal(nullable: true)
    pfLoanPrincipal(nullable: true)
    pfLoanInterest(nullable: true)
    pfLoanTotal(nullable: true)
    loanAgainstSalaryPrincipal(nullable: true)
    loanAgainstSalaryInterest(nullable: true)
    salaryLoanTotal(nullable: true)
    groupInsurance(nullable: true)
    advance(nullable: true)
    otherDeduction(nullable: true)
    revenueStamp(nullable: true)

    salaryType(nullable: true)
    status(nullable: true)
    accounting(nullable: true)
    bankId(nullable: true)
    bankBranchId(nullable: true)
    afmAccountNo(nullable: true)
    accountHeadId(nullable: true)
    }

    String toString() {
        return "${cardNo}"
    }
}
