package hrms

class HrSodPolicy {
    static mapping = {
        table 'HR_SOD_POLICY'
        version false
        id column: 'SOD_POLICY_ID'
        employeeTypeIdHrLookup column: 'EMPLOYEE_TYPE_ID'
    }
    Long id
    Integer dailyHour
    Integer weeklyHour
    Integer monthlyHour
    Float hourlyAmount
    // Relations
    HrLookup employeeTypeIdHrLookup

    static constraints = {
    id(size: 0..19)
    employeeTypeIdHrLookup(nullable: true)
    dailyHour(nullable: true)
    weeklyHour(nullable: true)
    monthlyHour(nullable: true)
    hourlyAmount(nullable: true)
    }

    String toString() {
        return "${employeeTypeIdHrLookup}"
    }
}
