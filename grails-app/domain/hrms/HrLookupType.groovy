package hrms

class HrLookupType {

    static mapping = {
        table 'HR_LOOKUP_TYPE'      // Master table  ,its  Details Table is 'HR_LOOKUP'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'LOOKUP_TYPE_ID'
        sort lookupType: "asc" // or "desc"

    }
    Long id
    Boolean isActive
    String lookupType

    static hasMany = [hrlookups:HrLookup]

    static constraints = {
        id(size: 0..19)
        //lookupType(inList:["BLOOD GROUP", "RELIGION", "GENDER", "MARITAL STATUS"],blank:false)
        lookupType(size: 0..50,blank:false,unique: true)
        isActive(nullable:true)
    }

    String toString() {
        return "${lookupType}"
    }
}
