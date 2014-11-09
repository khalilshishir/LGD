package fixedAsset

class ASSET_MAINTENANCE {
    static mapping = {
        table('FA_ASSET_MAINTENANCE')
        version(false)
        ID(updateable: false,insertable: false)
    }
    Long ID
    ASSET_BOOK ASSET_BOOK_ID
    Date MAINTENANCE_START_DATE=new Date()
    Date MAINTENANCE_END_DATE=new Date()
    String MAINTENANCE_PLACE_NAME
    String MAINTENANCE_PLACE_ADDRESS
    String REASON
    String REMARKS
    Boolean IS_RELEASED=0
    static constraints = {
        ASSET_BOOK_ID(nullable: true)
        MAINTENANCE_START_DATE(nullable: true)
        MAINTENANCE_END_DATE(nullable: true)
        MAINTENANCE_PLACE_NAME(nullable: true)
        MAINTENANCE_PLACE_ADDRESS(nullable: true)
        REASON(nullable: true)
        REMARKS(nullable: true)
        IS_RELEASED(nullable: true)
    }
}
