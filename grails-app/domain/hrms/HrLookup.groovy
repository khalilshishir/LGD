package hrms

class HrLookup {

    static mapping = {
        table 'HR_LOOKUP'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'LOOKUP_ID'

        hrLookupTypeIdLookupType column: 'LOOKUP_TYPE_ID'

        sort lookupValue: "desc" // or "desc"
    }
    Long id
    String lookupValue
    Boolean isActive
    // Relation
    HrLookupType hrLookupTypeIdLookupType

    static constraints = {
        id(size: 0..19)
        lookupValue(blank: false,unique: false)
        isActive(nullable: true)
        hrLookupTypeIdLookupType()

    }

    String toString() {
        return "${lookupValue}"
    }
}
