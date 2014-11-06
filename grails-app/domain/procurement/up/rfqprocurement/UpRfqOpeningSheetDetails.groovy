package procurement.up.rfqprocurement

import procurement.pmu.Supplier

class UpRfqOpeningSheetDetails {
    static mapping = {
        table('UP_RFQ_OPENING_SHEET_DETAILS')
        version(false)
    }
    long id
    Supplier VENDOR
    double PRICE
    String COMMENTS

    UpRfqOpeningSheet upRfqOpeningSheet

    long CREATED_BY=0
    Date CREATED_DATE=new Date()
    long UPDATED_BY=0
    Date UPDATED_DATE=new Date()
    static constraints = {
        VENDOR(nullable: false)
        PRICE(nullable: false)
        COMMENTS(nullable: false)
        upRfqOpeningSheet(nullable: false)

        CREATED_BY(nullable:true)
        CREATED_DATE(nullable:true)
        UPDATED_BY(nullable: true)
        UPDATED_DATE(nullable: true)
    }
    String toString(){
        return VENDOR_NAME
    }

}
