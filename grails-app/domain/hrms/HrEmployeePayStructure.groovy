package hrms

class HrEmployeePayStructure {

    static mapping = {
        table 'HR_EMPLOYEE_PAY_STRUCTURE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        //stageIdHrPayscaleDetail column: 'STAGE_ID'
    }
    Long id
    Long payScaleId
    Boolean salStopFlag
    // Earning Properties
    Float consultantRemuneration
    Float pBasic
    Float pDa
    Float pHr
    Float pConveyance
    Float pWashingAllow
    Float pMedicalAllowance
    Float pOrgPfContribution
    Float pChargeAllow
    Float pRetro
    Float retroPf
    //newly Added
    Float festivalAllowance
    Float extraLoadAllowance
    Float conveyanceAllowance
    Float hillAllowance
    Float educationAllowance



    // Deduction Properties
    Float dLoanSalary
    Float dLoanPf
    Float dOwnPf
    Float dIncomeTax
    Float dIncomeTaxPc
    Float dRevenueStamp
    Float dGroupInsurance
    Float dOthers
    String stage

    // SPECIAL ALLOWANCES COLUMNS
    Float spAllowance
    Float spDaAllowance
    Float addSpAllowance
    Float spRetro
    String payrollRemarks

    //Relation
    HrEmployee employeeIdHrEmployee
    //HrPayscaleDetail stageIdHrPayscaleDetail



    static constraints = {
        id(size: 0..19)
        consultantRemuneration(nullable: true)
        pBasic(nullable: true)
        pDa(nullable: true)
        pHr(nullable: true)
        pConveyance(nullable: true)
        pWashingAllow(nullable: true)
        pMedicalAllowance(nullable: true)
        pOrgPfContribution(nullable: true)
        pChargeAllow(nullable: true)
        pRetro(nullable: true)
        retroPf(nullable: true)

        festivalAllowance(nullable: true)
        extraLoadAllowance(nullable: true)
        conveyanceAllowance(nullable: true)
        hillAllowance(nullable: true)
        educationAllowance(nullable: true)

        dLoanSalary(nullable: true)
        dLoanPf(nullable: true)
        dOwnPf(nullable: true)
        dIncomeTax(nullable: true)
        dIncomeTaxPc(nullable: true)
        dRevenueStamp(nullable: true)
        dGroupInsurance(nullable: true)
        dOthers(nullable: true)
        stage(nullable: true)

        spAllowance(nullable: true)
        spDaAllowance(nullable: true)
        addSpAllowance(nullable: true)
        spRetro(nullable: true)

        employeeIdHrEmployee(nullable: false,unique: true)
        //stageIdHrPayscaleDetail(nullable: true)

        payScaleId(nullable: true)
        salStopFlag(nullable: true)
        payrollRemarks(nullable: true)
    }

    String toString(){
        return "${employeeIdHrEmployee}"
    }
}
