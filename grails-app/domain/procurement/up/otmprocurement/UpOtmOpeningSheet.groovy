package procurement.up.otmprocurement

import procurement.up.Up_Proc_Master
import settings.SchemeInfo

class UpOtmOpeningSheet {
    static mapping = {
        table('UP_OTM_OPENING_SHEET')
        version(false)
        upOtmOpeningSheetDetails cascade: 'all'
    }
    long id
    SchemeInfo schemeInfo
//    Date SUB_LAST_DATE = new Date()
    Date OPENING_DATE = new Date()

    long CREATED_BY=0
    Date CREATED_DATE=new Date()
    long UPDATED_BY=0
    Date UPDATED_DATE=new Date()
    static hasMany = [upOtmOpeningSheetDetails: UpOtmOpeningSheetDetails]
    static constraints = {
        schemeInfo(nullable: false)
//        SUB_LAST_DATE(nullable: false)
        OPENING_DATE(nullable: false)

        CREATED_BY(nullable:true)
        CREATED_DATE(nullable:true)
        UPDATED_BY(nullable: true)
        UPDATED_DATE(nullable: true)
    }
    String toString(){
        return id
    }

}
