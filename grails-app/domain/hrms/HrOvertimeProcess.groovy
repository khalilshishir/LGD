package hrms


class HrOvertimeProcess {

    static mapping = {
        table 'HR_OVERTIME_PROCESS'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id                    	column: 'ID'
        overtimeMonth       	column: 'OVERTIME_MONTH'
        monthName            	column: 'MONTH_NAME'
        yearName        	    column: 'YEAR_NAME'
        employeeId           	column: 'EMPLOYEE_ID'
        cardNo               	column: 'CARD_NO'
        departmentId       	    column: 'DEPARTMENT_ID'
        designationId       	column: 'DESIGNATION_ID'
        gradeNo             	column: 'GRADE_NO'
        stage                	column: 'STAGE'
      /*  totalOtHour         	column: 'TOTAL_OT_HOUR'*/
      /*  totalPayableHour   	    column: 'TOTAL_PAYABLE_HOUR'*/
        amount              	column: 'AMOUNT'
        incomeTax           	column: 'INCOME_TAX'
        revenueStamp      	    column: 'REVENUE_STAMP'
        isAccounts     	        column: 'IS_ACCOUNTS'
      /*  voucherNo            	column: 'VOUCHER_NO'*/
        salaryType        	    column: 'SALARY_TYPE'
/*        bankId           	    column: 'BANK_ID'
        employeeCategoryId	    column: 'EMPLOYEE_CATEGORY_ID'*/
        paymentDesc        	    column: 'PAYMENT_DESC'
        /*acTypeId          	    column: 'AC_TYPE_ID'*/
        bankAcNo            	column: 'BANK_AC_NO'
 /*       isVoucher	            column: 'IS_VOUCHER'*/


        sort "id"
        sort id: "asc" // or "desc"
    }

    Long	  id
    Date	  overtimeMonth
    String	  monthName
    Integer	  yearName

    HrEmployee employeeId

    String    cardNo

    HrDepartment departmentId
    HrDesignation designationId

    String	  gradeNo
    String	  stage

    Float	  amount
    Float	  incomeTax
    Float	  revenueStamp
    Integer	  isAccounts

    String	  salaryType
    String	  paymentDesc
    String	  bankAcNo

    static constraints = {
        id                  (size: 0..19)
        overtimeMonth       (nullable: true)
        monthName           (nullable: true)
        yearName            (nullable: true)
        employeeId          (nullable: true)
        cardNo              (nullable: true)
        departmentId        (nullable: true)
        designationId       (nullable: true)
        gradeNo             (nullable: true)
        stage               (nullable: true)
        amount              (nullable:false)
        incomeTax           (nullable: true)
        revenueStamp        (nullable: true)
        isAccounts          (nullable: true)
        salaryType          (nullable: true)
        paymentDesc         (nullable: true)
        bankAcNo            (nullable: true)
    }

    String toString(){
        return "${cardNo}"
    }



}
