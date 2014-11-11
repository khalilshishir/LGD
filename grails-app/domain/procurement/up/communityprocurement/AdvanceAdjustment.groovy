package procurement.up.communityprocurement

import procurement.up.Up_Proc_Master
import settings.SchemeInfo

class AdvanceAdjustment {
    static mapping = {
        table('ADVANCE_ADJUSTMENT')
        version(false)
    }
    SchemeInfo schemeInfo
    double ADJUSTMENT_AMOUNT
    Date PAYMENT_DATE = new Date()
    String BANK_NAME
    String CHECK_NO
    long CREATED_BY=0
    Date CREATED_DATE=new Date()
    long UPDATED_BY=0
    Date UPDATED_DATE=new Date()

    static constraints = {
        schemeInfo(nullable: false)
        ADJUSTMENT_AMOUNT(nullable: false)
        PAYMENT_DATE(nullable: false)
        BANK_NAME(nullable: false)
        CHECK_NO(nullable: false)

        CREATED_BY(nullable:true)
        CREATED_DATE(nullable:true)
        UPDATED_BY(nullable: true)
        UPDATED_DATE(nullable: true)
    }
}
