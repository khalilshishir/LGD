package hrms

class HrEmployeeLoanScheduleDtl {
     static mapping = {
         table 'HR_EMPLOYEE_LOAN_SCHEDULE_DTL'
         version false
         id column: 'ID'
         loanScheduleIdHrEmployeeLoanSchedule column: 'LOAN_SCHEDULE_ID'
         sort installmentNo: 'asc'
     }
     Long id
     Integer installmentNo
     Date paymentDate
     Float principalPayable
     Float interestPayable
     Boolean isPaid
     Boolean tobePaid
    String remarks
    Float installmentAmount
    Float restOfAmount
    // Relation
    HrEmployeeLoanSchedule loanScheduleIdHrEmployeeLoanSchedule

    static constraints ={
        id(size: 0..19)
        installmentNo(nullable: true)
        paymentDate(nullable: true)
        principalPayable(nullable: true)
        interestPayable(nullable: true)
        isPaid(nullable: true)
        tobePaid(nullable: true)
        remarks(size: 0..100, nullable: true)
        loanScheduleIdHrEmployeeLoanSchedule(nullable: false)
        installmentAmount(nullable: true)
        restOfAmount(nullable: true)
    }

    String toString(){
        return "${loanScheduleIdHrEmployeeLoanSchedule}"
    }


}
