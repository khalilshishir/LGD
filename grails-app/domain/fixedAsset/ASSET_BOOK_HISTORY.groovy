package fixedAsset

class ASSET_BOOK_HISTORY {
    static mapping = {
        table('FA_ASSET_BOOK_HISTORY')
        version(false)
        ID(updateable: false,insertable: false)
    }
    Long ID
    ASSET_BOOK ASSET_BOOK_ID
    String NOTE
    Date CREATE_DATE=new Date()
    Integer user_id
    static constraints = {
        ASSET_BOOK_ID(nullable: true)
        NOTE(nullable: true)
        CREATE_DATE(nullable: true)
        user_id(nullable: true)
    }

}
