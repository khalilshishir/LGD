package hrms
//import accounts.AfmCostCenter
import accounts.AfmChartOfAccounts

class HrEmployeeLoanSchedule {

    static mapping = {
        table 'HR_EMPLOYEE_LOAN_SCHEDULE'
        version false
        id column: 'LOAN_SCHEDULE_ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        loanTypeIdHrLoan column: 'LOAN_TYPE_ID'
        hrEmployeeLoanScheduleDtls sort:'installmentNo'
        costCenterIdAfmCostCenter column: 'COST_CENTER_ID'
        acHeadIdPrincipalAfmChartOfAccounts column: 'AC_HEAD_ID_PRINCIPAL'
        acHeadIdInterestAfmChartOfAccounts column: 'AC_HEAD_ID_INTEREST'
    }

    Long id
    Date joiningDate
    Float serviceLength
    Integer leaveWithoutPay
    Date applicationDate
    Float loanAmount
    Date firstInstallmentDate
    Integer totalInstallment
    String reasonForLoan
    String loanStatus
    //Integer costCenterIdAfmCostCenter
    Date executionDate
    Float interest
    Integer updateInstallmentNo
    //Integer acHeadIdPrincipal
    //Integer acHeadIdInterest
    // Relation
    HrEmployee employeeIdHrEmployee
    HrLoan loanTypeIdHrLoan
    //AfmCostCenter costCenterIdAfmCostCenter
    HrDepartment costCenterIdAfmCostCenter
    AfmChartOfAccounts acHeadIdPrincipalAfmChartOfAccounts
    AfmChartOfAccounts acHeadIdInterestAfmChartOfAccounts

    static hasMany = [hrEmployeeLoanScheduleDtls:HrEmployeeLoanScheduleDtl]

    static constraints = {
        id(size: 0..19)
        employeeIdHrEmployee(nullable: true)
        loanTypeIdHrLoan(nullable: true)
        loanAmount(nullable: false)
        joiningDate(nullable: true)
        serviceLength(nullable: true)
        leaveWithoutPay(nullable: true)
        applicationDate(nullable: false)
        firstInstallmentDate(nullable: false)
        totalInstallment(nullable: false)
        reasonForLoan(size: 0..100, nullable: true)
        //costCenterId(nullable: true)
        executionDate(nullable: false)
        interest(nullable: false)
        //acHeadIdPrincipal(nullable: true)
        acHeadIdPrincipalAfmChartOfAccounts(nullable: true)
        //acHeadIdInterest(nullable: true)
        acHeadIdInterestAfmChartOfAccounts(nullable: true)
        costCenterIdAfmCostCenter(nullable: true)
        loanStatus(nullable: true)
        updateInstallmentNo(nullable: true)
    }

    String toString(){
        return "${employeeIdHrEmployee}"
    }

}
