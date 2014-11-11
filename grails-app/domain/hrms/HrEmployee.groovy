package hrms

class HrEmployee {

    static mapping = {
        table 'HR_EMPLOYEE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id column: 'EMPLOYEE_ID'
        designationIdHrDesignation column: 'DESIGNATION_ID'
        joiningDesignationIdHrDesignation column: 'JOINING_DESIGNATION_ID'
        departmentIdHrDepartment column: 'DEPARTMENT_ID'
        religionNameIdHrLookup column: 'RELIGION_NAME_ID'
        bloodGroupIdHrLookup column: 'BLOOD_GROUP_ID'
        maritalStatusHrLookup column: 'MARITAL_STATUS_ID'
        nationalityIdHrLookup column: 'NATIONALITY_ID'
        genderIdHrLookup column: 'GENDER_ID'
        employeeTypeIdHrLookup column: 'EMPLOYEE_TYPE_ID'
        appointmentTypeIdHrLookup column: 'APPOINTMENT_TYPE_ID'
        presentStatusIdHrLookup column: 'PRESENT_STATUS_ID'
        paymentTypeIdHrLookup column: 'PAYMENT_TYPE_ID'
        salaryTypeIdHrLookup column: 'SALARY_TYPE_ID'
        accountingInfoIdHrLookup column: 'ACCOUNTING_INFO_ID'
        employeeCategoryIdHrLookup column: 'EMPLOYEE_CATEGORY_ID'
        // hrEmployeePayStructures ignoreNotFound: true
        sort "id"
        sort id: "desc" // or "desc"


    }
    Long id
    String cardNo
    String employeeName
    String personalFileNo
    Date joiningDate
    Integer probationaryPeriod
    Date confirmationDate
    Date contractExpiredDate
    Date regularationDate
    Date serviceEndDate
    Date lastIncrementDate
    Date nextIncrementDate
    Integer noticePeriod
    String tin
    String tinCircle
    //String paymentType
    String bankAcNo
    String bankName
    String branchName
    String fatherName
    String motherName
    String spouseName
    Date dateOfBirth
    String nationalId
    String passportNo
    String drivingLicienceNo
    Integer noOfChildren
    Integer maleChildren
    Integer femaleChildren
    String birthPlace
    String identificationMarks
    String preRoadNo
    String preHouseNo
    String preBlockNo
    String prePostCode
    String preCity
    String preDistrict
    String preThana
    String perRoadNo
    String perHouseNo
    String perBlockNo
    String perPostCode
    String perCity
    String perDistrict
    String perThana
    String tntNo
    String mobileNo
    String emailId
    String faxNo
    Long bankId
    Long branchId
    String officeInTime
    String officeOutTime
    Integer otAllow
    Long repEmployeeId
    String resignationType

    String imagePath
    String imageName

    String emplDocPath
    String emplDocName

    //Long schoolId
    String empRole

    //Long accountingInfoId
    // Long employeeCategoryId
    // Relation
   // HrSchool hrSchool
    HrDesignation designationIdHrDesignation
    HrDesignation joiningDesignationIdHrDesignation
    HrDepartment departmentIdHrDepartment
    HrLookup religionNameIdHrLookup
    HrLookup bloodGroupIdHrLookup
    HrLookup maritalStatusHrLookup
    HrLookup nationalityIdHrLookup
    HrLookup genderIdHrLookup
    HrLookup employeeTypeIdHrLookup
    HrLookup appointmentTypeIdHrLookup
    HrLookup presentStatusIdHrLookup
    HrLookup paymentTypeIdHrLookup
    HrLookup salaryTypeIdHrLookup
    HrLookup accountingInfoIdHrLookup
    HrLookup employeeCategoryIdHrLookup

    // Relation
    static hasOne = [hrEmployeePayStructure: HrEmployeePayStructure, hrEmployeeSettlement: HrEmployeeSettlement]
    static hasMany = [hrEmployeePayStructures: HrEmployeePayStructure,hrEmployeeLoanSchedules: HrEmployeeLoanSchedule, hrEmployeeIncrements: HrEmployeeIncrement, hrEmployeePromotions: HrEmployeePromotion, hrOvertimeDtlList: HrOvertimeDtl,hrLoadExtraloadList: HrLoadExtraload,hrOvertimeMasterList: HrOvertimeMaster,hrEmpAcademicQualifications: HrEmpAcademicQualification,hrEmpProfQualifications: HrEmpProfQualification,hrEmpProfExperiences: HrEmpProfExperience,hrEmployeeTrainings: HrEmployeeTraining,hrEmpPublications: HrEmpPublication,hrEmployeeReferences: HrEmployeeReference,hrEmployeeDeduction:HrEmployeeDeduction]

    static constraints = {
        id(size: 0..19)
        cardNo(size: 0..25,nullable: false,unique: true)
        personalFileNo(nullable: true,unique: true)
        employeeName(size: 0..50,nullable: false)
        nationalId(size: 0..50,nullable: true,unique: true)
        passportNo(size: 0..50,nullable: true,unique: true)
        emailId(size: 0..25,email: true,unique: true,nullable: true)
        drivingLicienceNo(size: 0..50,nullable: true,unique: true)
        designationIdHrDesignation(nullable: true)
        departmentIdHrDepartment(nullable: true)
        religionNameIdHrLookup(nullable: true)
        bloodGroupIdHrLookup(nullable: true)
        maritalStatusHrLookup(nullable: true)
        nationalityIdHrLookup(nullable: true)
        genderIdHrLookup(nullable: true)
        employeeTypeIdHrLookup(nullable: true)
        appointmentTypeIdHrLookup(nullable: true)
        presentStatusIdHrLookup(nullable: true)
        paymentTypeIdHrLookup(nullable: true)
        //paymentType(nullable: true)
        noOfChildren(nullable: true)
        maleChildren(nullable: true)
        femaleChildren(nullable: true)
        confirmationDate(nullable: true)
        noticePeriod(nullable: true)
        dateOfBirth(nullable: true)
        birthPlace(nullable: true)
        identificationMarks(nullable: true)
        fatherName(nullable: true)
        motherName(nullable: true)
        spouseName(nullable: true)
        bankAcNo(nullable: true)
        tin(nullable: true)
        tinCircle(nullable: true)
        probationaryPeriod(nullable: true)


        // present address
        preBlockNo(nullable: true)
        preCity(nullable: true)
        preDistrict(nullable: true)
        preHouseNo(nullable: true)
        prePostCode(nullable: true)
        preRoadNo(nullable: true)
        preThana(nullable: true)
        // permanent address
        perBlockNo(nullable: true)
        perCity(nullable: true)
        perDistrict(nullable: true)
        perHouseNo(nullable: true)
        perPostCode(nullable: true)
        perRoadNo(nullable: true)
        perThana(nullable: true)
        //contact info
        tntNo(nullable: true)
        faxNo(nullable: true)
        mobileNo(nullable: true)

        lastIncrementDate(nullable: true)
        nextIncrementDate(nullable: true)
        regularationDate(nullable: true)
        contractExpiredDate(nullable: true)
        serviceEndDate(nullable: true)
        joiningDate(nullable: false)
        hrEmployeePayStructure(nullable: true)
        hrEmployeeSettlement(nullable: true)
        hrEmployeeIncrements(nullable: true)
        hrEmployeePromotions(nullable: true)
        hrEmployeeLoanSchedules(nullable: true)
        hrOvertimeDtlList(nullable: true)
        hrLoadExtraloadList(nullable: true)
        hrOvertimeMasterList(nullable: true)
        joiningDesignationIdHrDesignation(nullable: true)
        salaryTypeIdHrLookup(nullable: true)

        hrEmpAcademicQualifications(nullable: true)
        hrEmployeeReferences(nullable: true)
        hrEmpProfExperiences(nullable: true)
        hrEmpPublications(nullable: true)
        hrEmployeeTrainings(nullable: true)
        hrEmpProfQualifications(nullable: true)

        hrEmployeePayStructures(nullable: true)

        bankId(nullable: true)
        branchId(nullable: true)
        bankName(nullable: true)
        branchName(nullable: true)
        officeInTime(nullable: true)
        officeOutTime(nullable: true)
        otAllow(nullable: true)
        repEmployeeId(nullable: true)
        resignationType(nullable: true)

        imagePath(nullable: true)
        imageName(nullable: true)

        emplDocPath(nullable: true)
        emplDocName(nullable: true)

        accountingInfoIdHrLookup(nullable: false)
        employeeCategoryIdHrLookup(nullable: true)

      //  hrSchool(nullable: true)
        empRole(nullable: true)
    }

    String toString() {
        return "${employeeName}"
    }
}
