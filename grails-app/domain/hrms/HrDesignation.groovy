package hrms

class HrDesignation {

    static mapping = {
        table 'HR_DESIGNATION'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'DESIGNATION_ID'
        sort designationName: "asc" // or "asc"
    }

    Long id
    Boolean isActive
    String designationName
    Integer sortOrder
    String shortName
    //Relation   stop commented dated 20/02/2013
    //static belongsTo = [hrPayscaleList :HrPayscale]

    static hasOne = [hrFacultyCreditPolicy: HrFacultyCreditPolicy]


    static constraints = {
        id(size: 0..19)
        designationName(size: 0..50,blank:false,unique: true)
        isActive(nullable: true)
        // stop commented dated 20/02/2013
       // hrPayscaleList(nullable: true)
        sortOrder(nullable: true)
        shortName(nullable: true)
        hrFacultyCreditPolicy(nullable: true)
    }

    String toString(){
        return "${designationName}"
    }
}
