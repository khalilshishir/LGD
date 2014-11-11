package hrms

class HrEmployeeTraining {

    static mapping = {
        table 'HR_EMPLOYEE_TRAINING'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        sort 'slno'
    }

    Long id
    Integer slno
    String placeName
    String topicName
    Date fromdate
    Date todate
    Integer duration
    //Relations
    HrEmployee employeeIdHrEmployee

    static constraints = {
        id(size: 0..19)
        slno(nullable: true)
        placeName(nullable: true)
        topicName(nullable: true)
        fromdate(nullable: true)
        todate(nullable: true)
        duration(nullable: true)
        employeeIdHrEmployee(nullable: true)
    }

    String toString() {
        return "${employeeIdHrEmployee}"
    }
}
