package hrms

import accounts.AfmBankInfo

class HrPartTimeProcess {

    static mapping = {
        table 'HR_PART_TIME_PROCESS'
        version false

    }
    Long id
    HrDesignation designation
    HrDepartment department
    HrEmployee employee
    AfmBankInfo bank
    String cardNo
    Float grossPay
    Float netPay
    String semester
    Integer semesterYear
    Date salaryMonth
    Float revenueStamp
    Float incomeTax
    String paymentMonth
    Integer paymentYear
    String paymentFor
    String bankAcNo
    String salaryType
    String paymentDesc
    String paymentType
    Integer isAccounts

    static constraints = {
        id(size: 0..19)
        designation(nullable: true)
        department(nullable: true)
        employee(nullable: true)
        cardNo(nullable: true)
        bank(nullable: true)
        grossPay(nullable: true)
        netPay(nullable: true)
        semester(nullable: true)
        semesterYear(nullable: true)
        salaryMonth(nullable: true)
        revenueStamp(nullable: true)
        incomeTax(nullable: true)
        paymentMonth(nullable: true)
        paymentYear(nullable: true)
        paymentFor(nullable: true)
        bankAcNo(nullable: true)
        revenueStamp(nullable: true)
        incomeTax(nullable: true)
        salaryType(nullable: true)
        paymentDesc(nullable: true)
        paymentType(nullable: true)
        isAccounts(nullable: true)
    }
}
