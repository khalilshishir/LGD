package hrms

class HrEmployeeLeave {

    static mapping = {
        table 'HR_EMPLOYEE_LEAVE'
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        leaveTypeIdHrLeave column: 'LEAVE_TYPE_ID'
        salaryEffectTypeIdHrLookup column: 'SALARY_EFFECT_TYPE_ID'
    }

    Long id
    String cardNo
    Date applicationDate
    //String salaryEffectType
    Date startDate
    Date endDate
    Integer totalDays
    String leaveReason
    String remarks
    Integer leaveBalance
    Integer leaveAllowed
    Integer leaveAlreadyTaken
    Integer leaveWithPayDays
    Integer leaveWithoutPayDays
    // Relation
    HrEmployee employeeIdHrEmployee
    HrLeave leaveTypeIdHrLeave
    HrLookup salaryEffectTypeIdHrLookup

    static constraints = {
    id(size: 0..19)
    cardNo(size: 0..25,nullable: false,unique: ['startDate','endDate'])
    applicationDate(nullable: false)
   // salaryEffectType(nullable: true)
    startDate(nullable: false)
    endDate(nullable: false)          //,unique:true
    totalDays(nullable: false)
    leaveReason(nullable: true)
    remarks(nullable: true)
    employeeIdHrEmployee(nullable: false)
    leaveTypeIdHrLeave(nullable: false)
    salaryEffectTypeIdHrLookup(nullable: true)
    leaveBalance(nullable: true)
    leaveAllowed(nullable: true)
    leaveAlreadyTaken(nullable: true)
    leaveWithPayDays(nullable: true)
    leaveWithoutPayDays(nullable: true)
    }

    String toString() {
        return "${cardNo}"
    }

}
