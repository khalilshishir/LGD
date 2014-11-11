package hrms

class HrBonusEntry {

    static mapping = {
        table 'HR_BONUS_PROCESS'
        version false
        bonusTypeId column: 'BONUS_TYPE_ID'
    }
    Long id
    HrDesignation designation
    HrDepartment department
    HrEmployee employee
    HrLookup bonusTypeId
    String cardNo
    String bonusType
    Integer yearName
    String monthName
    Date bonusMonth
    Float basicSalary
    Float houseRent
    Float conveyance
    Float medical
    Float washing
    Float specialAllowance
    Float grossSalary
    Float revenueStamp
    Float incomeTax
    Float netPayable
    Float bonusAmount


    static constraints = {
        id(size: 0..19)
        designation(nullable: true)
        department(nullable: true)
        employee(nullable: true)
        cardNo(nullable: true)
        bonusTypeId(nullable: true)
        bonusType(nullable: true)
        yearName(nullable: true)
        monthName(nullable: true)
        bonusMonth(nullable: true)
        basicSalary(nullable: true)
        houseRent(nullable: true)
        conveyance(nullable: true)
        medical(nullable: true)
        washing(nullable: true)
        specialAllowance(nullable: true)
        grossSalary(nullable: true)
        revenueStamp(nullable: true)
        incomeTax(nullable: true)
        netPayable(nullable: true)
        bonusAmount(nullable: true)
    }

}
