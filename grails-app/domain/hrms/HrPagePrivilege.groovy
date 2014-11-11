package hrms

class HrPagePrivilege {

    static mapping = {
        table 'HR_PAGE_PRIVILEGE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'HR_PAGE_PRIVILEGE_ID'
        hrPageInfo column: 'HR_PAGE_INFO_ID'
        pagePrivilege column: 'PAGE_PRIVILEGE'
        sortBy column: 'SORT_BY'
        description column: 'DESCRIPTION'
        isActive column: 'IS_ACTIVE'

        sort sortBy: "desc" // or "desc"
    }
    Long id
    String pagePrivilege
    Integer sortBy
    String description
    Integer isActive
    // Relation
    HrPageInfo hrPageInfo

    static constraints = {
        id(size: 0..19)
        pagePrivilege(blank: false,unique: ['hrPageInfo'])
        sortBy(nullable: true)
        description(nullable: true)
        isActive(nullable: true)
        hrPageInfo()

    }

    String toString() {
        return "${pagePrivilege}"
    }
}
