package hrms

class HrSchool {

    static mapping = {
        table 'HR_SCHOOL'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'SCHOOL_ID'
        // test
    }

    Long id
    String schoolName
    String shortName

    static constraints = {
        id(size: 0..19)
        schoolName(size: 0..100,blank:false,unique: true)
        shortName(size: 0..25,nullable: true)
    }

    String toString(){
        return "${schoolName}"
    }
}
