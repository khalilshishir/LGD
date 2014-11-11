package hrms

import accounts.AfmBankBranch
import accounts.AfmBankInfo

class HrUpdateMonthlySalary {

    static mapping = {
        table 'HR_MONTHLY_SALARY_PROCESS'
        version false
    }
    Long id
    HrDesignation designation
    HrDepartment department
    HrEmployee employee
    Integer yearName
    String monthName
    Date salaryMonth
    String cardNo
    String bankName
    String bankAccountNo
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
    Integer accounting
    AfmBankInfo bank
    AfmBankBranch bankBranch
    String afmAccountNo
    String paymentDesc
    HrPayscale payScale
    String stage
    Float retroPf
    String isVoucher
//    AfmVoucher voucher



    static constraints = {
        id(size: 0..19)
        designation(nullable: true)
        department(nullable: true)
        employee(nullable: true)
        yearName(nullable: true)
        monthName(nullable: true)
        salaryMonth(nullable: true)
        cardNo(nullable: true)
        bankName(nullable: true)
        bankAccountNo(nullable: true)
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
        bank(nullable: true)
        bankBranch(nullable: true)
        afmAccountNo(nullable: true)
        paymentDesc(nullable: true)
        payScale(nullable: true)
        stage(nullable: true)
        retroPf(nullable: true)
        isVoucher(nullable: true)
//        voucher(nullable: true)

    }
}
