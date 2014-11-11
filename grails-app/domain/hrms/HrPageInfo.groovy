package hrms

class HrPageInfo {

    static mapping = {
        table 'HR_PAGE_INFO'      // Master table  ,its  Details Table is 'HR_LOOKUP'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'HR_PAGE_INFO_ID'
        pageName column: 'PAGE_NAME'
        description column: 'DESCRIPTION'
        isActive column: 'IS_ACTIVE'

        sort pageName: "asc" // or "desc"

    }
    Long id

    String pageName
    String description
    Integer isActive

    static hasMany = [hrPagePrivilege:HrPagePrivilege]

    static constraints = {

        id(size: 0..19)
        pageName(size: 0..50,blank:false,unique: true)
        description(nullable:true)
        isActive(nullable:false)
    }

    String toString() {
        return "${pageName}"
    }
}
