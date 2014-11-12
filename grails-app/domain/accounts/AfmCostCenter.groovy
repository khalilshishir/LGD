package accounts

import hrms.HrEmployeeLoanSchedule

class AfmCostCenter {

    static mapping = {
        table 'AFM_COST_CENTER'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'COST_CENTER_ID'
    }

    Long id
    String costCenter
    String type
    String shortName

    static hasMany = [hrEmployeeLoanSchedules:HrEmployeeLoanSchedule]

    static constraints = {
        id(size: 0..19)
        costCenter(size: 0..50,blank:false,unique: true)
        type(nullable: true)
        shortName(nullable: true)
    }

    String toString(){
        return "${costCenter}"
    }

}
