package procurement.up.otmprocurement

import procurement.up.Up_Proc_Master
import settings.SchemeInfo

class UP_OTM_Tender_Schedule_Sample {
    static mapping = {
        table('UP_OTM_TENDER_SCHEDULE_SAMPLE')
        version(false)
    }
    long id
    SchemeInfo schemeInfo
//    String ITEM_DETAILS
    double UNIT
    double AMOUNT
    double RATE
    double TOTAL_AMOUNT
    Date FINISH_DATE = new Date()
    Date TIMELINE_OF_RATE = new Date()

    long CREATED_BY=0
    Date CREATED_DATE=new Date()
    long UPDATED_BY=0
    Date UPDATED_DATE=new Date()
    static constraints = {
        schemeInfo(nullable: false)
//        ITEM_DETAILS(nullable: false)
        UNIT(nullable: false)
        AMOUNT(nullable: false)
        RATE(nullable: false)
        TOTAL_AMOUNT(nullable: false)
        FINISH_DATE(nullable: false)
        TIMELINE_OF_RATE(nullable: false)

        CREATED_BY(nullable:true)
        CREATED_DATE(nullable:true)
        UPDATED_BY(nullable: true)
        UPDATED_DATE(nullable: true)
    }
    String toString(){
        return id
    }

}
