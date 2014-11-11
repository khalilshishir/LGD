package procurement.up.rfqprocurement

import procurement.pmu.Supplier
import procurement.up.Up_Proc_Master
import settings.SchemeInfo

class UpRFQProcWorkOrder {

    static mapping = {
        table('UP_RFQ_PROC_WORK_ORDER')
        version(false)
    }

    SchemeInfo schemeInfo
    long id
    Supplier  supplier
    Date signContractDate

    long CREATED_BY=0
    Date CREATED_DATE=new Date()
    long UPDATED_BY=0
    Date UPDATED_DATE=new Date()
    boolean IS_ACTIVE=Boolean.TRUE
    static constraints = {
        CREATED_BY(nullable:true)
        CREATED_DATE(nullable:true)
        UPDATED_BY(nullable: true)
        UPDATED_DATE(nullable: true)

    }
    String toString(){
        return RECEIVED_COMPANY
    }

}