package hrms

class HrEmployeePromotion {

    static mapping = {
        table 'HR_EMPLOYEE_PROMOTION'
        version false
        id column: 'ID'
        employeeIdHrEmployee column: 'EMPLOYEE_ID'
        promotionTypeIdHrLookup column: 'PROMOTION_TYPE_ID'
    }
    Long id
    String cardNo
    Date promotionEffectDate
    Date salaryEffectDate
    String refNo
    String remarks
    Integer payScaleId
    String stage
    Long prevPayScaleId
    Long prevDesignationId
    Long designationId
    String prevStage
    // before Promotion
    Float bBasic
    Float bDa
    Float bHrent
    Float bConveyanceAllowance
    Float bWashingAllowance
    Float bMedicalAllowance
    Float bPf
    Float bFestivalBonus
    Float bAnnualBonus
    Float bGratuity
    Float bGrandTotal
    // after Promotion
    Float pBasic
    Float pDa
    Float pHrent
    Float pConveyanceAllowance
    Float pWashingAllowance
    Float pMedicalAllowance
    Float pPf
    Float pFestivalBonus
    Float pAnnualBonus
    Float pGratuity
    Float pGrandTotal

    // Relation
    HrEmployee employeeIdHrEmployee
    HrLookup promotionTypeIdHrLookup

    //add By Maruf
    Float bSpAllowance
    Float bSpDaAllowance
    Float bAddSpAllowance
    Float pSpAllowance
    Float pSpDaAllowance
    Float pAddSpAllowance

    static constraints = {
        id(size: 0..19)
        cardNo(nullable: true)
        employeeIdHrEmployee(nullable: true)
        promotionTypeIdHrLookup(nullable: true)
        promotionEffectDate(nullable: true)
        salaryEffectDate(nullable: true)
        refNo(nullable: true)
        payScaleId(nullable: true)
        stage(nullable: true)
        remarks(nullable: true)
        prevPayScaleId(nullable: true)
        prevDesignationId(nullable: true)
        designationId(nullable: true)
        prevStage(nullable: true)

        bBasic(nullable: true)
        bDa(nullable: true)
        bHrent(nullable: true)
        bConveyanceAllowance(nullable: true)
        bWashingAllowance(nullable: true)
        bMedicalAllowance(nullable: true)
        bPf(nullable: true)
        bFestivalBonus(nullable: true)
        bAnnualBonus(nullable: true)
        bGratuity(nullable: true)
        bGrandTotal(nullable: true)

        pBasic(nullable: true)
        pDa(nullable: true)
        pHrent(nullable: true)
        pConveyanceAllowance(nullable: true)
        pWashingAllowance(nullable: true)
        pMedicalAllowance(nullable: true)
        pPf(nullable: true)
        pFestivalBonus(nullable: true)
        pAnnualBonus(nullable: true)
        pGratuity(nullable: true)
        pGrandTotal(nullable: true)

        bSpAllowance(nullable: true)
        bSpDaAllowance(nullable: true)
        bAddSpAllowance(nullable: true)
        pSpAllowance(nullable: true)
        pSpDaAllowance(nullable: true)
        pAddSpAllowance(nullable: true)
    }
}
