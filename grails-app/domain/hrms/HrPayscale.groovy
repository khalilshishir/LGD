package hrms

class HrPayscale {

    static mapping = {
        table 'HR_PAYSCALE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'PAYSCALE_ID'
        // stop commented dated 20/02/2013
       // desingationIdHrDesignation column: 'DESIGNATION_ID'
        //hrPayscaleDetails sort:'stage'  //hrPayscaleDetails order: 'desc'
        sort id: "desc" // or "asc"
        hrPayscaleDetails sort:'stage'  //hrPayscaleDetails order: 'desc'

    }

    Long id
    String gradeNo
    // Relation   stop commented dated 20/02/2013
    //HrDesignation desingationIdHrDesignation

    static hasMany = [hrPayscaleDetails:HrPayscaleDetail,hrOvertimes: HrOvertime,payOvertimePaymentPolicy:PayOvertimePaymentPolicy]
    //static hasOne = [payOvertimePaymentPolicy:PayOvertimePaymentPolicy]

    static constraints = {
    id(size: 0..19)
    gradeNo(size: 0..25,blank: false,unique: true)
        //stop commented dated 20/02/2013
   // desingationIdHrDesignation(unique: true)
    payOvertimePaymentPolicy(nullable: true)
    hrOvertimes(nullable: true)
    }

    String toString() {
        return "${gradeNo}"
    }

}
