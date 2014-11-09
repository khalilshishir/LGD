package procurement.up.otmprocurement

import procurement.up.Up_Proc_Master
import settings.SchemeInfo

class IFT {
    static mapping = {
        table('IFT')
        version(false)
    }

    long id
    SchemeInfo schemeInfo
    String IFT_NUMBER
    Date LAST_CONTACT_DATE = new Date()
    Date SUB_LAST_DATE = new Date()
    String SUB_LAST_TIME

    long CREATED_BY=0
    Date CREATED_DATE=new Date()
    long UPDATED_BY=0
    Date UPDATED_DATE=new Date()

    static constraints = {
        IFT_NUMBER(nullable: false)
        LAST_CONTACT_DATE(nullable: false)
        SUB_LAST_DATE(nullable: false)
        SUB_LAST_TIME(nullable: false)
        schemeInfo(nullable: false)

        CREATED_BY(nullable:true)
        CREATED_DATE(nullable:true)
        UPDATED_BY(nullable: true)
        UPDATED_DATE(nullable: true)
    }
    String toString(){
        return id
    }

}
