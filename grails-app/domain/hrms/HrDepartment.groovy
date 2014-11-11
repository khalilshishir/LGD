package hrms

class HrDepartment {

    static mapping = {
        table 'HR_DEPARTMENT'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'DEPARTMENT_ID'
        // test
    }

    Long id
    Boolean isActive
    String departmentName
    Integer sortOrder
    String shortName

    HrSchool school

    static constraints = {
        id(size: 0..19)
        departmentName(size: 0..50,blank:false,unique: true)
        isActive(nullable: true)
        sortOrder(nullable: true)
        shortName(nullable: false)
        school(nullable: true)
    }

    String toString(){
        return "${departmentName}"
    }

}
