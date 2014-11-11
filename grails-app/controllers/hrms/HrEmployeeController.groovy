package hrms

import accounts.AfmBankBranch
//import inventory.InvUserRole

import org.grails.plugins.excelimport.AbstractExcelImporter
import org.grails.plugins.excelimport.ExcelImportService
import org.grails.plugins.excelimport.DefaultImportCellCollector

import org.codehaus.groovy.grails.plugins.jasper.JasperService
import accounts.Util
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import static util.DateRelated.changeDateForProcedure
import static util.DateRelated.changeDateForProcedure
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Cell
import static util.DateRelated.changeDateForExcel
import grails.converters.JSON
import groovy.sql.Sql
import java.text.SimpleDateFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

class HrEmployeeController extends AbstractExcelImporter {

    ExcelImportService excelImportService;
    def static cellReporter = new DefaultImportCellCollector()

    @Autowired
    JasperService jasperService

    def dataSource
    def sessionFactory

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    List sqlDataList
    List sqlValueList

    private static final String REPORT_FILE_FORMAT = 'pdf'
    private static final String REPORT_NAME_FIELD = 'REPORT_NAME'
    private static final String REPORT_FOLDER = 'storeInFromSite'
    private static final String SUB_REPORT_FOLDER = 'subReport'
    private static final String OUTPUT_FILE_NAME = 'roleReport'
    private static final String REPORT_TITLE = 'Company Name'
    private static final String REPORT_DIR = 'REPORT_DIR'
    private static final String SUBREPORT_DIR = 'SUBREPORT_DIR'
    private static final String JASPER_FILE = 'userRoleDetails.jasper'
    private static final String PDF_EXTENSION = ".pdf"
    private static final String JASPER_EXTENSION = ".jasper"
    private static final String REPORT = "report"

   // static InvUserRole loginUser
    static ArrayList<String> userRole
    List  accountingInfoIdList
    List  employeeCategoryIdList
    List  employeeTypeList
    List  schoolList

    // user login information for specific role apply for specific user
    def searchLoginUser() {

        def userRoleInstances = InvUserRole.getAll()
        def len = userRoleInstances.size()
//        println(len)
        for(int i=0; i<len; i++) {
//            println(userRole.get(i).iubUser?.id)

            if (session?.user?.id.equals(userRoleInstances.get(i).iubUser?.id) && userRoleInstances.get(i).purpose.equals("hr_info_update")) {
                loginUser = userRoleInstances.get(i)
            }
        }
        HashSet set = new HashSet()
        for(int i=0; i<len; i++) {
            if (userRoleInstances.get(i).purpose.equals("hr_info_update")) {
                set.add(userRoleInstances.get(i)?.role)
            }
        }
        userRole = new ArrayList<String>(set.sort())

        //      println(userRole)
//        HashSet set = new HashSet(userRole)
//        println(set)
//        println(userRole)


    }


    def index() {
        redirect(action: "list", params: params)
    }

    def list() {

       // searchLoginUser()

        params.max = Math.min(params.max ? params.int('max') : 50, 100)

        params.sort = params.sort ?: "cardNo"
        params.order = params.order ?: "desc" // or "asc"


        //params.sort = params.sort ?: "designationIdHrDesignation.sortOrder"

        //params.order = params.order ?: "asc"

        [hrEmployeeInstanceList: HrEmployee.list(params), hrEmployeeInstanceTotal: HrEmployee.count()]
    }

    /*def create() {
        def sql = new Sql(dataSource)
        def idNoObj = sql.firstRow("select (max(to_number(nvl(card_no,0)))+1) idNo from hr_employee")
        def cardNo =  idNoObj.idNo
        print(cardNo)
        [hrEmployeeInstance: new HrEmployee(params),hrEmployeePayStructures: new  HrEmployeePayStructure(), loginUser: loginUser, userRole: userRole,cardNo: cardNo]
    } */
    def create() {
        def totalYear = 0
        def totalMonth = 0
        def totalDays = 0

       // accountingInfoIdList=getAccountingInfoIdList();
      //  employeeCategoryIdList=getEmployeeCategoryIdList();
        // schoolList=getSchoolList();
        employeeTypeList=getEmployeeTypeList();

        //println("accountingInfoIdList  :"+accountingInfoIdList)
        //println("employeeCategoryIdList  :"+employeeCategoryIdList)
       // println("schoolList  :"+schoolList.size())


       // [hrEmployeeInstance: new HrEmployee(params),hrEmployeePayStructures: new  HrEmployeePayStructure(), loginUser: loginUser, userRole: userRole,totalServiceYear: totalYear,totalServiceMonth: totalMonth,totalServiceDay: totalDays,accountingInfoIdList:accountingInfoIdList,employeeCategoryIdList:employeeCategoryIdList,schoolList:schoolList]
        [hrEmployeeInstance: new HrEmployee(params),hrEmployeePayStructures: new  HrEmployeePayStructure(), totalServiceYear: totalYear,totalServiceMonth: totalMonth,totalServiceDay: totalDays,employeeTypeList:employeeTypeList]
    }

    public List  getAccountingInfoIdList() {
        Sql sql = new Sql(dataSource)
        accountingInfoIdList=null
        accountingInfoIdList = sql.rows("SELECT LOOKUP_ID,LOOKUP_VALUE FROM HR_LOOKUP WHERE LOOKUP_TYPE_ID=129883 AND IS_ACTIVE=1")

        sql.close()
        return accountingInfoIdList
    }

    public List  getEmployeeCategoryIdList() {
        Sql sql = new Sql(dataSource)
        employeeCategoryIdList=null
        employeeCategoryIdList = sql.rows("SELECT LOOKUP_ID,LOOKUP_VALUE FROM HR_LOOKUP WHERE LOOKUP_TYPE_ID=129884 AND IS_ACTIVE=1")

        sql.close()
        return employeeCategoryIdList
    }

    public List  getEmployeeTypeList() {
        Sql sql = new Sql(dataSource)
        employeeTypeList=null
        employeeTypeList = sql.rows("SELECT LOOKUP_ID,LOOKUP_VALUE FROM HR_LOOKUP WHERE LOOKUP_TYPE_ID=380 AND IS_ACTIVE=1")

        sql.close()
        return employeeTypeList
    }

    public List  getSchoolList() {
        Sql sql = new Sql(dataSource)
        schoolList=null
        schoolList = sql.rows("SELECT  H.SCHOOL_ID,H.SCHOOL_NAME " +
                              "FROM HR_SCHOOL H ORDER BY SCHOOL_NAME ASC")

        sql.close()
        return schoolList
    }

    def save() {

        if(params.joiningDate!=''){
            params.joiningDate = Util.getDateMonddyyyy(params.joiningDate)
        }
        if(params.confirmationDate!=''){
            params.confirmationDate = Util.getDateMonddyyyy(params.confirmationDate)
        }
        if(params.regularationDate!=''){
            params.regularationDate = Util.getDateMonddyyyy(params.regularationDate)
        }
        if(params.serviceEndDate!=''){
            params.serviceEndDate = Util.getDateMonddyyyy(params.serviceEndDate)
        }
        if(params.lastIncrementDate!=''){
            params.lastIncrementDate = Util.getDateMonddyyyy(params.lastIncrementDate)
        }
        if(params.nextIncrementDate!=''){
            params.nextIncrementDate = Util.getDateMonddyyyy(params.nextIncrementDate)
        }
        if(params.dateOfBirth!=''){
            params.dateOfBirth = Util.getDateMonddyyyy(params.dateOfBirth)
        }

        if (params.employeeTypeIdHrLookup.id!="395"){
            params.contractExpiredDate = null
        }else{
            params.contractExpiredDate = Util.getDateMonddyyyy(params.contractExpiredDate)
        }


        //def hrEmployeeInstance = new HrEmployee(params)

        /*---- start add for details part -------------*/
        def hrEmployeeInstance = new HrEmployee()
        String cardNo=params.cardNo
        String employeeName=params.employeeName
        hrEmployeeInstance.properties['cardNo'] = cardNo.toString().trim()
        hrEmployeeInstance.properties['employeeName'] = employeeName.toString().trim()

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               //'branchId',
        hrEmployeeInstance.properties['id','personalFileNo','nationalId','passportNo','emailId','drivingLicienceNo','religionNameIdHrLookup','bloodGroupIdHrLookup','maritalStatusHrLookup','nationalityIdHrLookup','genderIdHrLookup','employeeTypeIdHrLookup','appointmentTypeIdHrLookup','presentStatusIdHrLookup','paymentTypeIdHrLookup','noOfChildren','maleChildren','femaleChildren','confirmationDate','probationaryPeriod','noticePeriod','dateOfBirth','contractExpiredDate','lastIncrementDate','nextIncrementDate','regularationDate','serviceEndDate','joiningDate','salaryTypeIdHrLookup','spouseName','tin','tinCircle','tntNo','bankAcNo','bankName','birthPlace','branchName','fatherName','faxNo','mobileNo','motherName','perBlockNo','perCity','perDistrict','perHouseNo','perPostCode','perRoadNo','perThana','preBlockNo','preCity','preDistrict','preHouseNo','prePostCode','preRoadNo','preThana','identificationMarks','bankId','officeInTime','officeOutTime','otAllow','repEmployeeId','resignationType','accountingInfoIdHrLookup'] = params

        def departmentIdObj = HrDepartment.findByDepartmentName(params.departmentIdHrDepartment)
        hrEmployeeInstance.setDepartmentIdHrDepartment(departmentIdObj)

        def joiningDesignationIdObj = HrDesignation.findByDesignationName(params.joiningDesignationIdHrDesignation)
        hrEmployeeInstance.setJoiningDesignationIdHrDesignation(joiningDesignationIdObj)

        def designationIdObj = HrDesignation.findByDesignationName(params.designationIdHrDesignation)
        hrEmployeeInstance.setDesignationIdHrDesignation(designationIdObj)

        ////////// start for employee image path store in table column ///////////////////////
        def logoFile = request.getFile('imagePath')

        if (logoFile && (logoFile.getSize() > 0)) {

            def fileName = logoFile.getOriginalFilename()
            def ext = fileName.substring(fileName.lastIndexOf('.'))
            def logoFileTitle = params.cardNo
            logoFileTitle = logoFileTitle + ext
            String path  = ImageFunc.getReportDirectory()
            String savePath = path + File.separator + logoFileTitle
            logoFile.transferTo(new File(savePath))

            hrEmployeeInstance.properties['imagePath'] = savePath
            hrEmployeeInstance.properties['imageName'] = logoFileTitle
        }
        ////////// end for employee image path store in table column ///////////////////////

        // //////// start for employee Document path store in table column ///////////////////////
        def docFile = request.getFile('emplDocPath')

        if (docFile && (docFile.getSize() > 0)) {

            def fileName = docFile.getOriginalFilename()
            def fileExt = fileName.substring(fileName.lastIndexOf('.'))
            def docFileTitle = params.cardNo+"_UPLOADED_DOCUMENT"
            docFileTitle = docFileTitle + fileExt
            String path  = ImageFunc.getDirectoryPath('employeeDoc')
            String savePath = path + File.separator + docFileTitle
            logoFile.transferTo(new File(savePath))

            hrEmployeeInstance.properties['emplDocPath'] = savePath
            hrEmployeeInstance.properties['emplDocName'] = docFileTitle
        }
        ////////// end for employee Document path store in table column ///////////////////////

        // when payment type CASH/CHEQUE SELECTION
        if (params.paymentTypeIdHrLookup.id == '1347' || params.paymentTypeIdHrLookup.id == '8078') {
            hrEmployeeInstance.properties['bankAcNo']=""
            hrEmployeeInstance.properties['bankId']=null
            hrEmployeeInstance.properties['branchId']=null
        }

        int m = 0
        //details table HrEmpAcademicQualification
        m = 0
        while (params["hrEmpAcademicQualifications[" + m + "].slno"] != null) {
            def hrEmpAcademicQualification = new HrEmpAcademicQualification()
            hrEmpAcademicQualification.properties['slno'] = Integer.valueOf(params["hrEmpAcademicQualifications[" + m + "].slno"])

            def examIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].examinationIdHrLookup"]
            if(examIdHrLookupId!='null'){
                def examIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examIdHrLookupId))
                hrEmpAcademicQualification.setExaminationIdHrLookup(examIdHrLookupIdObj)
            }


            def examGroupIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].examGroupIdHrLookup"]
            if(examGroupIdHrLookupId!='null'){
                def examGroupIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examGroupIdHrLookupId))
                hrEmpAcademicQualification.setExamGroupIdHrLookup(examGroupIdHrLookupIdObj)
            }

            def examBoardIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].boardIdHrLookup"]
            if(examBoardIdHrLookupId!='null'){
                def examBoardIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examBoardIdHrLookupId))
                hrEmpAcademicQualification.setBoardIdHrLookup(examBoardIdHrLookupIdObj)
            }



            def passingYear = params["hrEmpAcademicQualifications[" + m + "].passingYear"]
            if(passingYear!='null'){
                hrEmpAcademicQualification.properties['passingYear'] = Integer.valueOf(params["hrEmpAcademicQualifications[" + m + "].passingYear"])
            }

            hrEmpAcademicQualification.properties['division'] = params["hrEmpAcademicQualifications[" + m + "].division"]


            if (params["hrEmpAcademicQualifications["+m+"].isDivGpa"] == null) {
                hrEmpAcademicQualification.properties['isDivGpa'] = Integer.parseInt('0')
            }
            else {
                hrEmpAcademicQualification.properties['isDivGpa'] = params["hrEmpAcademicQualifications["+m+"].isDivGpa"]
                hrEmpAcademicQualification.properties['cgpa'] = Float.valueOf(params["hrEmpAcademicQualifications[" + m + "].cgpa"])
            }

            hrEmployeeInstance.addToHrEmpAcademicQualifications(hrEmpAcademicQualification)
            m++
        }

        //details table HrEmpProfQualification
        m = 0
        while (params["hrEmpProfQualifications[" + m + "].slno"] != null) {
            def hrEmpProfQualification = new HrEmpProfQualification()
            hrEmpProfQualification.properties['slno'] = Integer.valueOf(params["hrEmpProfQualifications[" + m + "].slno"])

            def degreeIdHrLookupId = params["hrEmpProfQualifications[" + m + "].degreeIdHrLookup"]
            def degreeIdHrLookupIdObj = HrLookup.findById(Long.valueOf(degreeIdHrLookupId))
            hrEmpProfQualification.setDegreeIdHrLookup(degreeIdHrLookupIdObj)

            def instituteNameIdHrLookupId = params["hrEmpProfQualifications[" + m + "].instituteNameIdHrLookup"]
            def instituteNameIdHrLookupIdObj = HrLookup.findById(Long.valueOf(instituteNameIdHrLookupId))
            hrEmpProfQualification.setInstituteNameIdHrLookup(instituteNameIdHrLookupIdObj)

            hrEmpProfQualification.properties['passingYear'] = Integer.valueOf(params["hrEmpProfQualifications[" + m + "].passingYear"])
            hrEmpProfQualification.properties['remarks'] = params["hrEmpProfQualifications[" + m + "].remarks"]

            hrEmployeeInstance.addToHrEmpProfQualifications(hrEmpProfQualification)
            m++
        }

        //details table HrEmpProfExperience
        m = 0
        while (params["hrEmpProfExperiences[" + m + "].slno"] != null) {
            def hrEmpProfExperience = new HrEmpProfExperience()
            hrEmpProfExperience.properties['slno'] = Integer.valueOf(params["hrEmpProfExperiences[" + m + "].slno"])
            hrEmpProfExperience.properties['companyName'] = params["hrEmpProfExperiences[" + m + "].companyName"]
            hrEmpProfExperience.properties['companyAddress'] = params["hrEmpProfExperiences[" + m + "].companyAddress"]
            hrEmpProfExperience.properties['fromdate'] = Util.getDateMonddyyyy(params["hrEmpProfExperiences[" + m + "].fromdate"])
            hrEmpProfExperience.properties['todate'] = Util.getDateMonddyyyy(params["hrEmpProfExperiences[" + m + "].todate"])
            hrEmpProfExperience.properties['duration'] = Integer.valueOf(params["hrEmpProfExperiences[" + m + "].duration"])
            hrEmployeeInstance.addToHrEmpProfExperiences(hrEmpProfExperience)
            m++
        }

        //details table HrEmployeeTraining
        m = 0
        while (params["hrEmployeeTrainings[" + m + "].slno"] != null) {
            def hrEmployeeTraining = new HrEmployeeTraining()
            hrEmployeeTraining.properties['slno'] = Integer.valueOf(params["hrEmployeeTrainings[" + m + "].slno"])
            hrEmployeeTraining.properties['placeName'] = params["hrEmployeeTrainings[" + m + "].placeName"]
            hrEmployeeTraining.properties['topicName'] = params["hrEmployeeTrainings[" + m + "].topicName"]
            hrEmployeeTraining.properties['fromdate'] = Util.getDateMonddyyyy(params["hrEmployeeTrainings[" + m + "].fromdate"])
            hrEmployeeTraining.properties['todate'] = Util.getDateMonddyyyy(params["hrEmployeeTrainings[" + m + "].todate"])
            hrEmployeeTraining.properties['duration'] = Integer.valueOf(params["hrEmployeeTrainings[" + m + "].duration"])
            hrEmployeeInstance.addToHrEmployeeTrainings(hrEmployeeTraining)
            m++
        }

        //details table HrEmpPublication
        m = 0
        while (params["hrEmpPublications[" + m + "].slno"] != null) {
            def hrEmpPublication = new HrEmpPublication()
            hrEmpPublication.properties['slno'] = Integer.valueOf(params["hrEmpPublications[" + m + "].slno"])
            hrEmpPublication.properties['publicationTitle'] = params["hrEmpPublications[" + m + "].publicationTitle"]
            hrEmpPublication.properties['subjectName'] = params["hrEmpPublications[" + m + "].subjectName"]
            def yearName = params["hrEmpPublications[" + m + "].yearName"].toString().trim()
            if(yearName!=''){
                hrEmpPublication.properties['yearName'] = Integer.valueOf(params["hrEmpPublications[" + m + "].yearName"])
            }
            hrEmployeeInstance.addToHrEmpPublications(hrEmpPublication)
            m++
        }


        //details table HrEmployeeReference
        m = 0
        while (params["hrEmployeeReferences[" + m + "].slno"] != null) {
            def hrEmployeeReference = new HrEmployeeReference()
            hrEmployeeReference.properties['slno'] = Integer.valueOf(params["hrEmployeeReferences[" + m + "].slno"])
            hrEmployeeReference.properties['referenceName'] = params["hrEmployeeReferences[" + m + "].referenceName"]
            hrEmployeeReference.properties['identiee'] = params["hrEmployeeReferences[" + m + "].identiee"]
            hrEmployeeReference.properties['addressName'] = params["hrEmployeeReferences[" + m + "].addressName"]
            hrEmployeeReference.properties['contactNo'] = params["hrEmployeeReferences[" + m + "].contactNo"]
            hrEmployeeInstance.addToHrEmployeeReferences(hrEmployeeReference)
            m++
        }

        /*---- end add for details part -------------*/

        // start employees monthly salary payInfo
/*        int i=0
        while (params.payScaleId != '' && params.payScaleId != null && i<1) {*/
            def hrEmployeePayStructure = new HrEmployeePayStructure()
            hrEmployeePayStructure.properties['payScaleId'] = getLongValue(params.payScaleId)
        /*hrEmployeePayStructure.properties['stage'] = params.stage*/
            hrEmployeePayStructure.properties['consultantRemuneration'] = getFloatValue(params.consultantRemuneration)
            hrEmployeePayStructure.properties['pBasic'] = getFloatValue(params.pBasic)
            hrEmployeePayStructure.properties['pDa'] = getFloatValue(params.pDa)
            hrEmployeePayStructure.properties['pConveyance'] = getFloatValue(params.pConveyance)
            hrEmployeePayStructure.properties['pMedicalAllowance'] = getFloatValue(params.pMedicalAllowance)
            hrEmployeePayStructure.properties['pHr'] = getFloatValue(params.pHr)
           // hrEmployeePayStructure.properties['pOrgPfContribution'] = getFloatValue(params.pOrgPfContribution)
            hrEmployeePayStructure.properties['pWashingAllow'] = getFloatValue(params.pWashingAllow)

            //Newly Added By Maruf
            hrEmployeePayStructure.properties['festivalAllowance'] = getFloatValue(params.festivalAllowance)
            hrEmployeePayStructure.properties['extraLoadAllowance'] = getFloatValue(params.extraLoadAllowance)
            hrEmployeePayStructure.properties['conveyanceAllowance'] = getFloatValue(params.conveyanceAllowance)
            hrEmployeePayStructure.properties['hillAllowance'] = getFloatValue(params.hillAllowance)
            hrEmployeePayStructure.properties['educationAllowance'] = getFloatValue(params.educationAllowance)

            hrEmployeePayStructure.properties['spAllowance'] = getFloatValue(params.spAllowance)
            hrEmployeePayStructure.properties['spDaAllowance'] = getFloatValue(params.spDaAllowance)
            hrEmployeePayStructure.properties['addSpAllowance'] = getFloatValue(params.addSpAllowance)
            hrEmployeePayStructure.properties['spRetro'] = getFloatValue(params.spRetro)
            hrEmployeePayStructure.properties['retroPf'] = getFloatValue(params.retroPf)

            hrEmployeePayStructure.properties['dGroupInsurance'] = getFloatValue(params.dGroupInsurance)
            hrEmployeePayStructure.properties['dIncomeTax'] = getFloatValue(params.dIncomeTax)
            hrEmployeePayStructure.properties['dIncomeTaxPc'] = getFloatValue(params.dIncomeTaxPc)
            hrEmployeePayStructure.properties['dLoanPf'] = getFloatValue(params.dLoanPf)
            hrEmployeePayStructure.properties['dLoanSalary'] = getFloatValue(params.dLoanSalary)
            hrEmployeePayStructure.properties['dOwnPf'] = getFloatValue(params.dOwnPf)
           // hrEmployeePayStructure.properties['dRevenueStamp'] = getFloatValue(params.dRevenueStamp)
            hrEmployeePayStructure.properties['dOthers'] = getFloatValue(params.dOthers)
            hrEmployeePayStructure.properties['payrollRemarks'] = params.payrollRemarks

            if (params.salStopFlag == null)
            {
                hrEmployeePayStructure.properties['salStopFlag'] = Integer.parseInt('0')
            }
            else
            {
                hrEmployeePayStructure.properties['salStopFlag'] = params.salStopFlag
            }

            hrEmployeeInstance.addToHrEmployeePayStructures(hrEmployeePayStructure)
            /*i++;
        }*/
        // end employee's monthly salary payInfo

        if (!hrEmployeeInstance.save(flush: true)) {

            def totalYear = 0
            def totalMonth = 0
            def totalDays = 0

            accountingInfoIdList=getAccountingInfoIdList();
            employeeCategoryIdList=getEmployeeCategoryIdList();

            println("accountingInfoIdList  :"+accountingInfoIdList)
            println("employeeCategoryIdList  :"+employeeCategoryIdList)

           /* render(view: "create", model: [hrEmployeeInstance: hrEmployeeInstance,hrEmployeePayStructures: new  HrEmployeePayStructure(), loginUser: loginUser, userRole: userRole,totalServiceYear: totalYear,totalServiceMonth: totalMonth,totalServiceDay: totalDays,accountingInfoIdList:accountingInfoIdList,employeeCategoryIdList:employeeCategoryIdList])*/
            render(view: "create", model: [hrEmployeeInstance: hrEmployeeInstance,hrEmployeePayStructures: new  HrEmployeePayStructure(),totalServiceYear: totalYear,totalServiceMonth: totalMonth,totalServiceDay: totalDays,accountingInfoIdList:accountingInfoIdList,employeeCategoryIdList:employeeCategoryIdList])
            return
        }
        flash.message = "Record Successfully Saved."
        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), hrEmployeeInstance.id])
        redirect(action: "show", id: hrEmployeeInstance.id)
    }

    def show() {
        def hrEmployeeInstance = HrEmployee.get(params.id)
        if (!hrEmployeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), params.id])
            redirect(action: "list")
            return
        }
        //render(view: "show",model: [hrEmployeeInstance: hrEmployeeInstance])
        [hrEmployeeInstance: hrEmployeeInstance]
    }

    def edit() {
        println("params.id "+params.id)
        def hrEmployeeInstance = HrEmployee.get(params.id)

        if (!hrEmployeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), params.id])
            redirect(action: "list")
            return
        }

        // [hrEmployeeInstance: hrEmployeeInstance]
        // For details List Value

        def hrEmployee =HrEmployee.findById(params.id)
        /*List<HrEmpAcademicQualification> academicQualificationList = HrEmpAcademicQualification.findAllByEmployeeIdHrEmployee(hrEmployee)
        List<HrEmpProfQualification> hrEmpProfQualificationList = HrEmpProfQualification.findAllByEmployeeIdHrEmployee(hrEmployee)
        List<HrEmpProfExperience> hrEmpProfExperienceList = HrEmpProfExperience.findAllByEmployeeIdHrEmployee(hrEmployee)
        List<HrEmployeeTraining> hrEmployeeTrainingList = HrEmployeeTraining.findAllByEmployeeIdHrEmployee(hrEmployee)
        List<HrEmpPublication> hrEmpPublicationList = HrEmpPublication.findAllByEmployeeIdHrEmployee(hrEmployee)
        List<HrEmployeeReference> hrEmployeeReferenceList = HrEmployeeReference.findAllByEmployeeIdHrEmployee(hrEmployee)*/

        def hrEmployeePayStructure  = new  HrEmployeePayStructure()
        println("hrEmployee :"+hrEmployee)
        hrEmployeePayStructure = HrEmployeePayStructure.findByEmployeeIdHrEmployee(hrEmployee)
        println("hrEmployeePayStructure :"+hrEmployeePayStructure)
        // start LeaveWithOutPay Days Count
        def sql = new Sql(dataSource)
        def totalLeaveWithoutPayDays
        def totalLeaveWithoutPayDaysArray = sql.firstRow("select GETLEAVEWITHOUTPAY_YYMMDD(?) totalLeaveWithoutPayDays from dual",params.id)
        def totalLwpDays = totalLeaveWithoutPayDaysArray.totalLeaveWithoutPayDays
        // end LeaveWithOutPay Days Count

        // start Employee Service Length calculation
        def currentDate
        def joiningDate
        def totalYear,totalMonth,totalDays

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(!hrEmployeeInstance?.joiningDate.equals(null))
        {
            joiningDate = hrEmployeeInstance?.joiningDate
            joiningDate = simpleDateFormat.format(joiningDate)

            if (!hrEmployeeInstance?.serviceEndDate.equals(null))
            {
                currentDate = hrEmployeeInstance?.serviceEndDate
                currentDate =  simpleDateFormat.format(currentDate)
            }
            else
            {
                currentDate =  simpleDateFormat.format(new Date())
            }
            def serviceLength = sql.firstRow("select GET_EMP_SERVICE_LENGTH(?,?) totalServiceLength from dual",joiningDate,currentDate)
            print(serviceLength)
            def serviceLengthArray = serviceLength.totalServiceLength.split(":")
            totalYear = serviceLengthArray[0]
            totalMonth = serviceLengthArray[1]
            totalDays = serviceLengthArray[2]
        }
        else {
            totalYear = 0
            totalMonth = 0
            totalDays = 0
        }
        // end Employee Service Length calculation

        // start First tab info
        accountingInfoIdList=getAccountingInfoIdList();
        employeeCategoryIdList=getEmployeeCategoryIdList();
        employeeTypeList=getEmployeeTypeList();
       // schoolList=getSchoolList();

       /* println("loginUser?.role  :"+loginUser?.role)*/

        println("accountingInfoIdList.size()  :"+accountingInfoIdList.size())
        println("employeeCategoryIdList.size()  :"+employeeCategoryIdList.size())
        println("employeeTypeList.size()  :"+employeeTypeList.size())
       // println("schoolList.size()  :"+schoolList.size())
        // End First tab info                                                                                                                                                                                                                                                                                                                                                                                                /*loginUser: loginUser, userRole: userRole,*/

       // [hrEmployeeInstance: hrEmployeeInstance, hrEmpAcademicQualifications: academicQualificationList,hrEmpProfQualifications: hrEmpProfQualificationList,hrEmpProfExperiences:hrEmpProfExperienceList,hrEmployeeTrainings:hrEmployeeTrainingList,hrEmpPublications:hrEmpPublicationList,hrEmployeeReferences:hrEmployeeReferenceList,hrEmployeePayStructures:hrEmployeePayStructure,totalLeaveWithoutPayDays: totalLwpDays, totalServiceYear: totalYear,totalServiceMonth: totalMonth,totalServiceDay: totalDays,accountingInfoIdList:accountingInfoIdList,employeeCategoryIdList:employeeCategoryIdList,employeeTypeList:employeeTypeList]/*,schoolList:schoolList*/
        [hrEmployeeInstance: hrEmployeeInstance,hrEmployeePayStructures:hrEmployeePayStructure,totalLeaveWithoutPayDays: totalLwpDays, totalServiceYear: totalYear,totalServiceMonth: totalMonth,totalServiceDay: totalDays,accountingInfoIdList:accountingInfoIdList,employeeCategoryIdList:employeeCategoryIdList,employeeTypeList:employeeTypeList]/*,schoolList:schoolList*/
    }

    def update() {
        println("params.id "+params.id)
        def hrEmployeeInstance = HrEmployee.get(params.id)
        if (!hrEmployeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrEmployeeInstance.version > version) {
                hrEmployeeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmployee.label', default: 'HrEmployee')] as Object[],
                        "Another user has updated this HrEmployee while you were editing")
                render(view: "edit", model: [hrEmployeeInstance: hrEmployeeInstance])
                return
            }
        }
        if(params.joiningDate!=''){
            params.joiningDate = Util.getDateMonddyyyy(params.joiningDate)
        }
        if(params.confirmationDate!=''){
            params.confirmationDate = Util.getDateMonddyyyy(params.confirmationDate)
        }
        if(params.regularationDate!=''){
            params.regularationDate = Util.getDateMonddyyyy(params.regularationDate)
        }
        if(params.serviceEndDate!=''){
            params.serviceEndDate = Util.getDateMonddyyyy(params.serviceEndDate)
        }
        if(params.lastIncrementDate!=''){
            params.lastIncrementDate = Util.getDateMonddyyyy(params.lastIncrementDate)
        }
        if(params.nextIncrementDate!=''){
            params.nextIncrementDate = Util.getDateMonddyyyy(params.nextIncrementDate)
        }
        if(params.dateOfBirth!=''){
            params.dateOfBirth = Util.getDateMonddyyyy(params.dateOfBirth)
        }
/*        params.joiningDate = Util.getDateMonddyyyy(params.joiningDate)
        params.confirmationDate = Util.getDateMonddyyyy(params.confirmationDate)
        params.regularationDate = Util.getDateMonddyyyy(params.regularationDate)
        params.serviceEndDate = Util.getDateMonddyyyy(params.serviceEndDate)
        params.lastIncrementDate = Util.getDateMonddyyyy(params.lastIncrementDate)
        params.nextIncrementDate = Util.getDateMonddyyyy(params.nextIncrementDate)
        params.dateOfBirth = Util.getDateMonddyyyy(params.dateOfBirth)*/

        if (params.employeeTypeIdHrLookup.id!="395"){
            params.contractExpiredDate = null
        }else{
            params.contractExpiredDate = Util.getDateMonddyyyy(params.contractExpiredDate)
        }
        ////////// start for employee image path store in table column ///////////////////////
        def logoFile = request.getFile('imagePath')

        if (logoFile && (logoFile.getSize() > 0)) {

            def fileName = logoFile.getOriginalFilename()
            def ext = fileName.substring(fileName.lastIndexOf('.'))
            def logoFileTitle = params.cardNo
            logoFileTitle = logoFileTitle + ext
            String path  = ImageFunc.getReportDirectory()
            String savePath = path + File.separator + logoFileTitle
            logoFile.transferTo(new File(savePath))

            hrEmployeeInstance.properties['imagePath'] = savePath
            hrEmployeeInstance.properties['imageName'] = logoFileTitle
        }
        ////////// end for employee image path store in table column ///////////////////////

        // //////// start for employee Document path store in table column ///////////////////////
        def docFile = request.getFile('emplDocPath')

        if (docFile && (docFile.getSize() > 0)) {

            def fileName = docFile.getOriginalFilename()
            def fileExt = fileName.substring(fileName.lastIndexOf('.'))
            def docFileTitle = params.cardNo+"_UPLOADED_DOCUMENT"
            docFileTitle = docFileTitle + fileExt
            String path  = ImageFunc.getDirectoryPath('employeeDoc')
            String savePath = path + File.separator + docFileTitle
            docFile.transferTo(new File(savePath))

            hrEmployeeInstance.properties['emplDocPath'] = savePath
            hrEmployeeInstance.properties['emplDocName'] = docFileTitle
        }
        ////////// end for employee Document path store in table column ///////////////////////


        if (params.hrSchool){
            hrEmployeeInstance.properties['hrSchool'] = null
        }

        String cardNo=params.cardNo
        String employeeName=params.employeeName
        hrEmployeeInstance.properties['cardNo'] = cardNo.toString().trim()
        hrEmployeeInstance.properties['employeeName'] = employeeName.toString().trim()

        //hrEmployeeInstance.properties = params                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           //'branchId',
        hrEmployeeInstance.properties['id','personalFileNo','nationalId','passportNo','emailId','drivingLicienceNo','religionNameIdHrLookup','bloodGroupIdHrLookup','maritalStatusHrLookup','nationalityIdHrLookup','genderIdHrLookup','employeeTypeIdHrLookup','appointmentTypeIdHrLookup','presentStatusIdHrLookup','paymentTypeIdHrLookup','noOfChildren','maleChildren','femaleChildren','confirmationDate','probationaryPeriod','noticePeriod','dateOfBirth','contractExpiredDate','lastIncrementDate','nextIncrementDate','regularationDate','serviceEndDate','joiningDate','salaryTypeIdHrLookup','spouseName','tin','tinCircle','tntNo','bankAcNo','bankName','birthPlace','branchName','fatherName','faxNo','mobileNo','motherName','perBlockNo','perCity','perDistrict','perHouseNo','perPostCode','perRoadNo','perThana','preBlockNo','preCity','preDistrict','preHouseNo','prePostCode','preRoadNo','preThana','identificationMarks','bankId','officeInTime','officeOutTime','otAllow','repEmployeeId','resignationType','accountingInfoIdHrLookup'] = params

        def departmentIdObj = HrDepartment.findByDepartmentName(params.departmentIdHrDepartment)
        hrEmployeeInstance.setDepartmentIdHrDepartment(departmentIdObj)

        def joiningDesignationIdObj = HrDesignation.findByDesignationName(params.joiningDesignationIdHrDesignation)
        hrEmployeeInstance.setJoiningDesignationIdHrDesignation(joiningDesignationIdObj)

        def designationIdObj = HrDesignation.findByDesignationName(params.designationIdHrDesignation)
        hrEmployeeInstance.setDesignationIdHrDesignation(designationIdObj)

        // when payment type CASH/CHEQUE SELECTION
        if (params.paymentTypeIdHrLookup.id == '1347' || params.paymentTypeIdHrLookup.id == '8078') {
            hrEmployeeInstance.properties['bankAcNo']=""
            hrEmployeeInstance.properties['bankId']=null
            hrEmployeeInstance.properties['branchId']=null
        }

        /*if (params.bankId.equals('null')) {
            hrEmployeeInstance.properties['bankId']=null
        }
        else {
            hrEmployeeInstance.properties['bankId'] = Long.valueOf(params.bankId)
        }
        if (params.branchId.equals('null')) {
            hrEmployeeInstance.properties['branchId']=null
        }
        else {
          hrEmployeeInstance.properties['branchId'] = Long.valueOf(params.branchId)
        }*/

        int m=0
        /////////////////////////////
        //details table HrEmpAcademicQualification
        m = 0
        while (params["hrEmpAcademicQualifications[" + m + "].slno"] != null)
        {
            def hrEmpAcademicQualification = new HrEmpAcademicQualification()
            if(params["hrEmpAcademicQualifications[" + m + "].deleted"]=="true" && params["hrEmpAcademicQualifications[" + m + "].new"]=="false")
            {
                hrEmpAcademicQualification = hrEmpAcademicQualification.get(Long.valueOf(params["hrEmpAcademicQualifications[" + m + "].id"]))
                hrEmployeeInstance.removeFromHrEmpAcademicQualifications(hrEmpAcademicQualification)
                hrEmpAcademicQualification.delete()
                m++
                continue
            }
            else if(params["hrEmpAcademicQualifications[" + m + "].deleted"]=="true" && params["hrEmpAcademicQualifications[" + m + "].new"]=="true")
            {
                m++
                continue
            }
            else if(params["hrEmpAcademicQualifications[" + m + "].deleted"]=="false" && params["hrEmpAcademicQualifications[" + m + "].new"]=="true")
            {
                hrEmpAcademicQualification = new HrEmpAcademicQualification()
                hrEmpAcademicQualification.properties['slno'] = Integer.valueOf(params["hrEmpAcademicQualifications[" + m + "].slno"])

                def examIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].examinationIdHrLookup"]
                if(examIdHrLookupId!='null'){
                    def examIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examIdHrLookupId))
                    hrEmpAcademicQualification.setExaminationIdHrLookup(examIdHrLookupIdObj)
                }


                def examGroupIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].examGroupIdHrLookup"]
                if(examGroupIdHrLookupId!='null'){
                    def examGroupIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examGroupIdHrLookupId))
                    hrEmpAcademicQualification.setExamGroupIdHrLookup(examGroupIdHrLookupIdObj)
                }

                def examBoardIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].boardIdHrLookup"]
                if(examBoardIdHrLookupId!='null'){
                    def examBoardIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examBoardIdHrLookupId))
                    hrEmpAcademicQualification.setBoardIdHrLookup(examBoardIdHrLookupIdObj)
                }



                def passingYear = params["hrEmpAcademicQualifications[" + m + "].passingYear"]
                if(passingYear!='null'){
                    hrEmpAcademicQualification.properties['passingYear'] = Integer.valueOf(params["hrEmpAcademicQualifications[" + m + "].passingYear"])
                }

                hrEmpAcademicQualification.properties['division'] = params["hrEmpAcademicQualifications[" + m + "].division"]


                if (params["hrEmpAcademicQualifications["+m+"].isDivGpa"] == null) {
                    hrEmpAcademicQualification.properties['isDivGpa'] = Integer.parseInt('0')
                }
                else {
                    hrEmpAcademicQualification.properties['isDivGpa'] = params["hrEmpAcademicQualifications["+m+"].isDivGpa"]
                    hrEmpAcademicQualification.properties['cgpa'] = Float.valueOf(params["hrEmpAcademicQualifications[" + m + "].cgpa"])
                }
            }
            else
            {
                hrEmpAcademicQualification = HrEmpAcademicQualification.get(Long.valueOf(params["hrEmpAcademicQualifications[" + m + "].id"]))

                hrEmpAcademicQualification.properties['slno'] = Integer.valueOf(params["hrEmpAcademicQualifications[" + m + "].slno"])

                def examIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].examinationIdHrLookup"]
                def examIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examIdHrLookupId))
                hrEmpAcademicQualification.setExaminationIdHrLookup(examIdHrLookupIdObj)

                def examGroupIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].examGroupIdHrLookup"]
                def examGroupIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examGroupIdHrLookupId))
                hrEmpAcademicQualification.setExamGroupIdHrLookup(examGroupIdHrLookupIdObj)

                def examBoardIdHrLookupId = params["hrEmpAcademicQualifications[" + m + "].boardIdHrLookup"]
                def examBoardIdHrLookupIdObj = HrLookup.findById(Long.valueOf(examBoardIdHrLookupId))
                hrEmpAcademicQualification.setBoardIdHrLookup(examBoardIdHrLookupIdObj)

                hrEmpAcademicQualification.properties['passingYear'] = Integer.valueOf(params["hrEmpAcademicQualifications[" + m + "].passingYear"])
                hrEmpAcademicQualification.properties['division'] = params["hrEmpAcademicQualifications[" + m + "].division"]


                if (params["hrEmpAcademicQualifications["+m+"].isDivGpa"] == null) {
                    hrEmpAcademicQualification.properties['isDivGpa'] = Integer.parseInt('0')
                }
                else {
                    hrEmpAcademicQualification.properties['isDivGpa'] = params["hrEmpAcademicQualifications["+m+"].isDivGpa"]
                    hrEmpAcademicQualification.properties['cgpa'] = getFloatValue(params["hrEmpAcademicQualifications[" + m + "].cgpa"])
                }
            }

            hrEmployeeInstance.addToHrEmpAcademicQualifications(hrEmpAcademicQualification)
            m++
        }

        ///////////////
        //details table HrEmpProfQualification
        m = 0
        while (params["hrEmpProfQualifications[" + m + "].slno"] != null)
        {
            def hrEmpProfQualification = new HrEmpProfQualification()
            if(params["hrEmpProfQualifications[" + m + "].deleted"]=="true" && params["hrEmpProfQualifications[" + m + "].new"]=="false")
            {
                hrEmpProfQualification = hrEmpProfQualification.get(Long.valueOf(params["hrEmpProfQualifications[" + m + "].id"]))
                hrEmployeeInstance.removeFromHrEmpProfQualifications(hrEmpProfQualification)
                hrEmpProfQualification.delete()
                m++
                continue
            }
            else if(params["hrEmpProfQualifications[" + m + "].deleted"]=="true" && params["hrEmpProfQualifications[" + m + "].new"]=="true")
            {
                m++
                continue
            }
            else if(params["hrEmpProfQualifications[" + m + "].deleted"]=="false" && params["hrEmpProfQualifications[" + m + "].new"]=="true")
            {
                hrEmpProfQualification = new HrEmpProfQualification()
                hrEmpProfQualification.properties['slno'] = Integer.valueOf(params["hrEmpProfQualifications[" + m + "].slno"])

                def degreeIdHrLookupId = params["hrEmpProfQualifications[" + m + "].degreeIdHrLookup"]
                def degreeIdHrLookupIdObj = HrLookup.findById(Long.valueOf(degreeIdHrLookupId))
                hrEmpProfQualification.setDegreeIdHrLookup(degreeIdHrLookupIdObj)

                def instituteNameIdHrLookupId = params["hrEmpProfQualifications[" + m + "].instituteNameIdHrLookup"]
                def instituteNameIdHrLookupIdObj = HrLookup.findById(Long.valueOf(instituteNameIdHrLookupId))
                hrEmpProfQualification.setInstituteNameIdHrLookup(instituteNameIdHrLookupIdObj)

                hrEmpProfQualification.properties['passingYear'] = Integer.valueOf(params["hrEmpProfQualifications[" + m + "].passingYear"])
                hrEmpProfQualification.properties['remarks'] = params["hrEmpProfQualifications[" + m + "].remarks"]

            }
            else
            {
                hrEmpProfQualification = HrEmpProfQualification.get(Long.valueOf(params["hrEmpProfQualifications[" + m + "].id"]))

                hrEmpProfQualification.properties['slno'] = Integer.valueOf(params["hrEmpProfQualifications[" + m + "].slno"])

                def degreeIdHrLookupId = params["hrEmpProfQualifications[" + m + "].degreeIdHrLookup"]
                def degreeIdHrLookupIdObj = HrLookup.findById(Long.valueOf(degreeIdHrLookupId))
                hrEmpProfQualification.setDegreeIdHrLookup(degreeIdHrLookupIdObj)

                def instituteNameIdHrLookupId = params["hrEmpProfQualifications[" + m + "].instituteNameIdHrLookup"]
                def instituteNameIdHrLookupIdObj = HrLookup.findById(Long.valueOf(instituteNameIdHrLookupId))
                hrEmpProfQualification.setInstituteNameIdHrLookup(instituteNameIdHrLookupIdObj)

                hrEmpProfQualification.properties['passingYear'] = Integer.valueOf(params["hrEmpProfQualifications[" + m + "].passingYear"])
                hrEmpProfQualification.properties['remarks'] = params["hrEmpProfQualifications[" + m + "].remarks"]
            }

            hrEmployeeInstance.addToHrEmpProfQualifications(hrEmpProfQualification)
            m++
        }
        //////////////

        //details table HrEmpProfExperience
        m = 0
        while (params["hrEmpProfExperiences[" + m + "].slno"] != null)
        {
            def hrEmpProfExperience = new HrEmpProfExperience()
            if(params["hrEmpProfExperiences[" + m + "].deleted"]=="true" && params["hrEmpProfExperiences[" + m + "].new"]=="false")
            {
                hrEmpProfExperience = hrEmpProfExperience.get(Long.valueOf(params["hrEmpProfExperiences[" + m + "].id"]))
                hrEmployeeInstance.removeFromHrEmpProfExperiences(hrEmpProfExperience)
                hrEmpProfExperience.delete()
                m++
                continue
            }
            else if(params["hrEmpProfExperiences[" + m + "].deleted"]=="true" && params["hrEmpProfExperiences[" + m + "].new"]=="true")
            {
                m++
                continue
            }
            else if(params["hrEmpProfExperiences[" + m + "].deleted"]=="false" && params["hrEmpProfExperiences[" + m + "].new"]=="true")
            {
                hrEmpProfExperience = new HrEmpProfExperience()
                hrEmpProfExperience.properties['slno'] = Integer.valueOf(params["hrEmpProfExperiences[" + m + "].slno"])
                hrEmpProfExperience.properties['companyName'] = params["hrEmpProfExperiences[" + m + "].companyName"]
                hrEmpProfExperience.properties['companyAddress'] = params["hrEmpProfExperiences[" + m + "].companyAddress"]
                hrEmpProfExperience.properties['fromdate'] = Util.getDateMonddyyyy(params["hrEmpProfExperiences[" + m + "].fromdate"])
                hrEmpProfExperience.properties['todate'] = Util.getDateMonddyyyy(params["hrEmpProfExperiences[" + m + "].todate"])
                hrEmpProfExperience.properties['duration'] = Integer.valueOf(params["hrEmpProfExperiences[" + m + "].duration"])

            }
            else
            {
                hrEmpProfExperience = HrEmpProfExperience.get(Long.valueOf(params["hrEmpProfExperiences[" + m + "].id"]))

                hrEmpProfExperience.properties['slno'] = Integer.valueOf(params["hrEmpProfExperiences[" + m + "].slno"])
                hrEmpProfExperience.properties['companyName'] = params["hrEmpProfExperiences[" + m + "].companyName"]
                hrEmpProfExperience.properties['companyAddress'] = params["hrEmpProfExperiences[" + m + "].companyAddress"]
                hrEmpProfExperience.properties['fromdate'] = Util.getDateMonddyyyy(params["hrEmpProfExperiences[" + m + "].fromdate"])
                hrEmpProfExperience.properties['todate'] = Util.getDateMonddyyyy(params["hrEmpProfExperiences[" + m + "].todate"])
                hrEmpProfExperience.properties['duration'] = Integer.valueOf(params["hrEmpProfExperiences[" + m + "].duration"])
            }

            hrEmployeeInstance.addToHrEmpProfExperiences(hrEmpProfExperience)
            m++
        }

        //-----------------------
        //details table HrEmployeeTraining
        m = 0
        while (params["hrEmployeeTrainings[" + m + "].slno"] != null)
        {
            def hrEmployeeTraining = new HrEmployeeTraining()
            if(params["hrEmployeeTrainings[" + m + "].deleted"]=="true" && params["hrEmployeeTrainings[" + m + "].new"]=="false")
            {
                hrEmployeeTraining = hrEmployeeTraining.get(Long.valueOf(params["hrEmployeeTrainings[" + m + "].id"]))
                hrEmployeeInstance.removeFromHrEmployeeTrainings(hrEmployeeTraining)
                hrEmployeeTraining.delete()
                m++
                continue
            }
            else if(params["hrEmployeeTrainings[" + m + "].deleted"]=="true" && params["hrEmployeeTrainings[" + m + "].new"]=="true")
            {
                m++
                continue
            }
            else if(params["hrEmployeeTrainings[" + m + "].deleted"]=="false" && params["hrEmployeeTrainings[" + m + "].new"]=="true")
            {
                hrEmployeeTraining = new HrEmployeeTraining()
                hrEmployeeTraining.properties['slno'] = Integer.valueOf(params["hrEmployeeTrainings[" + m + "].slno"])
                hrEmployeeTraining.properties['placeName'] = params["hrEmployeeTrainings[" + m + "].placeName"]
                hrEmployeeTraining.properties['topicName'] = params["hrEmployeeTrainings[" + m + "].topicName"]
                hrEmployeeTraining.properties['fromdate'] = Util.getDateMonddyyyy(params["hrEmployeeTrainings[" + m + "].fromdate"])
                hrEmployeeTraining.properties['todate'] = Util.getDateMonddyyyy(params["hrEmployeeTrainings[" + m + "].todate"])
                hrEmployeeTraining.properties['duration'] = Integer.valueOf(params["hrEmployeeTrainings[" + m + "].duration"])

            }
            else
            {
                hrEmployeeTraining = HrEmployeeTraining.get(Long.valueOf(params["hrEmployeeTrainings[" + m + "].id"]))

                hrEmployeeTraining.properties['slno'] = Integer.valueOf(params["hrEmployeeTrainings[" + m + "].slno"])
                hrEmployeeTraining.properties['placeName'] = params["hrEmployeeTrainings[" + m + "].placeName"]
                hrEmployeeTraining.properties['topicName'] = params["hrEmployeeTrainings[" + m + "].topicName"]
                hrEmployeeTraining.properties['fromdate'] = Util.getDateMonddyyyy(params["hrEmployeeTrainings[" + m + "].fromdate"])
                hrEmployeeTraining.properties['todate'] = Util.getDateMonddyyyy(params["hrEmployeeTrainings[" + m + "].todate"])
                hrEmployeeTraining.properties['duration'] = Integer.valueOf(params["hrEmployeeTrainings[" + m + "].duration"])
            }

            hrEmployeeInstance.addToHrEmployeeTrainings(hrEmployeeTraining)
            m++
        }
        //-----------------------
        //details table HrEmpPublication
        m = 0
        while (params["hrEmpPublications[" + m + "].slno"] != null)
        {
            def hrEmpPublication = new HrEmpPublication()
            if(params["hrEmpPublications[" + m + "].deleted"]=="true" && params["hrEmpPublications[" + m + "].new"]=="false")
            {
                hrEmpPublication = hrEmpPublication.get(Long.valueOf(params["hrEmpPublications[" + m + "].id"]))
                hrEmployeeInstance.removeFromHrEmpPublications(hrEmpPublication)
                hrEmpPublication.delete()
                m++
                continue
            }
            else if(params["hrEmpPublications[" + m + "].deleted"]=="true" && params["hrEmpPublications[" + m + "].new"]=="true")
            {
                m++
                continue
            }
            else if(params["hrEmpPublications[" + m + "].deleted"]=="false" && params["hrEmpPublications[" + m + "].new"]=="true")
            {
                hrEmpPublication = new HrEmpPublication()
                hrEmpPublication.properties['slno'] = Integer.valueOf(params["hrEmpPublications[" + m + "].slno"])
                hrEmpPublication.properties['publicationTitle'] = params["hrEmpPublications[" + m + "].publicationTitle"]
                hrEmpPublication.properties['subjectName'] = params["hrEmpPublications[" + m + "].subjectName"]
                def yearName = params["hrEmpPublications[" + m + "].yearName"].toString().trim()
                if(yearName!=''){
                    hrEmpPublication.properties['yearName'] = Integer.valueOf(params["hrEmpPublications[" + m + "].yearName"])
                }


            }
            else
            {
                hrEmpPublication = HrEmpPublication.get(Long.valueOf(params["hrEmpPublications[" + m + "].id"]))

                hrEmpPublication.properties['slno'] = Integer.valueOf(params["hrEmpPublications[" + m + "].slno"])
                hrEmpPublication.properties['publicationTitle'] = params["hrEmpPublications[" + m + "].publicationTitle"]
                hrEmpPublication.properties['subjectName'] = params["hrEmpPublications[" + m + "].subjectName"]
                def yearName = params["hrEmpPublications[" + m + "].yearName"].toString().trim()
                if(yearName!=''){
                    hrEmpPublication.properties['yearName'] = Integer.valueOf(params["hrEmpPublications[" + m + "].yearName"])
                }
            }

            hrEmployeeInstance.addToHrEmpPublications(hrEmpPublication)
            m++
        }

        //details table HrEmployeeReference
        m = 0
        while (params["hrEmployeeReferences[" + m + "].slno"] != null)
        {
            def hrEmployeeReference = new HrEmployeeReference()
            if(params["hrEmployeeReferences[" + m + "].deleted"]=="true" && params["hrEmployeeReferences[" + m + "].new"]=="false")
            {
                hrEmployeeReference = hrEmployeeReference.get(Long.valueOf(params["hrEmployeeReferences[" + m + "].id"]))
                hrEmployeeInstance.removeFromHrEmployeeReferences(hrEmployeeReference)
                hrEmployeeReference.delete()
                m++
                continue
            }
            else if(params["hrEmployeeReferences[" + m + "].deleted"]=="true" && params["hrEmployeeReferences[" + m + "].new"]=="true")
            {
                m++
                continue
            }
            else if(params["hrEmployeeReferences[" + m + "].deleted"]=="false" && params["hrEmployeeReferences[" + m + "].new"]=="true")
            {
                hrEmployeeReference = new HrEmployeeReference()
                hrEmployeeReference.properties['slno'] = Integer.valueOf(params["hrEmployeeReferences[" + m + "].slno"])
                hrEmployeeReference.properties['referenceName'] = params["hrEmployeeReferences[" + m + "].referenceName"]
                hrEmployeeReference.properties['identiee'] = params["hrEmployeeReferences[" + m + "].identiee"]
                hrEmployeeReference.properties['addressName'] = params["hrEmployeeReferences[" + m + "].addressName"]
                hrEmployeeReference.properties['contactNo'] = params["hrEmployeeReferences[" + m + "].contactNo"]
            }
            else
            {
                hrEmployeeReference = HrEmployeeReference.get(Long.valueOf(params["hrEmployeeReferences[" + m + "].id"]))

                hrEmployeeReference.properties['slno'] = Integer.valueOf(params["hrEmployeeReferences[" + m + "].slno"])
                hrEmployeeReference.properties['referenceName'] = params["hrEmployeeReferences[" + m + "].referenceName"]
                hrEmployeeReference.properties['identiee'] = params["hrEmployeeReferences[" + m + "].identiee"]
                hrEmployeeReference.properties['addressName'] = params["hrEmployeeReferences[" + m + "].addressName"]
                hrEmployeeReference.properties['contactNo'] = params["hrEmployeeReferences[" + m + "].contactNo"]
            }

            hrEmployeeInstance.addToHrEmployeeReferences(hrEmployeeReference)
            m++
        }



        //////////////////////////////

        // start employees monthly salary payInfo
        /*int i=0
        while (params.payScaleId != '' && params.payScaleId != null && i<1)
        {  // start loop
            def hrEmployeePayStructure = new HrEmployeePayStructure()

            // for record add in payStructure table
            if(params["hrEmployeePayStructures.deleted"]=="false" && params["hrEmployeePayStructures.new"]=="true" && params["hrEmployeePayStructures.id"] == "")
            {

                hrEmployeePayStructure.properties['payScaleId'] = getLongValue(params.payScaleId)
                hrEmployeePayStructure.properties['stage'] = params.stage

                hrEmployeePayStructure.properties['pBasic'] = getFloatValue(params.pBasic)
                hrEmployeePayStructure.properties['pDa'] = getFloatValue(params.pDa)
                hrEmployeePayStructure.properties['pConveyance'] = getFloatValue(params.pConveyance)
                hrEmployeePayStructure.properties['pMedicalAllowance'] = getFloatValue(params.pMedicalAllowance)
                hrEmployeePayStructure.properties['pHr'] = getFloatValue(params.pHr)
               // hrEmployeePayStructure.properties['pOrgPfContribution'] = getFloatValue(params.pOrgPfContribution)
                hrEmployeePayStructure.properties['pWashingAllow'] = getFloatValue(params.pWashingAllow)

                hrEmployeePayStructure.properties['spAllowance'] = getFloatValue(params.spAllowance)
                hrEmployeePayStructure.properties['spDaAllowance'] = getFloatValue(params.spDaAllowance)
                hrEmployeePayStructure.properties['addSpAllowance'] = getFloatValue(params.addSpAllowance)
                hrEmployeePayStructure.properties['spRetro'] = getFloatValue(params.spRetro)
                hrEmployeePayStructure.properties['retroPf'] = getFloatValue(params.retroPf)

                hrEmployeePayStructure.properties['dGroupInsurance'] = getFloatValue(params.dGroupInsurance)
                hrEmployeePayStructure.properties['dIncomeTax'] = getFloatValue(params.dIncomeTax)
                hrEmployeePayStructure.properties['dIncomeTaxPc'] = getFloatValue(params.dIncomeTaxPc)
                hrEmployeePayStructure.properties['dLoanPf'] = getFloatValue(params.dLoanPf)
                hrEmployeePayStructure.properties['dLoanSalary'] = getFloatValue(params.dLoanSalary)
                hrEmployeePayStructure.properties['dOwnPf'] = getFloatValue(params.dOwnPf)
                hrEmployeePayStructure.properties['dRevenueStamp'] = getFloatValue(params.dRevenueStamp)
                hrEmployeePayStructure.properties['dOthers'] = getFloatValue(params.dOthers)
                hrEmployeePayStructure.properties['payrollRemarks'] = params.payrollRemarks

                if (params.salStopFlag == null)
                {
                    hrEmployeePayStructure.properties['salStopFlag'] = Integer.parseInt('0')
                }
                else
                {
                    hrEmployeePayStructure.properties['salStopFlag'] = params.salStopFlag
                }
                hrEmployeeInstance.addToHrEmployeePayStructures(hrEmployeePayStructure)
                i++;
            }

            else if(params["hrEmployeePayStructures.deleted"]=="true" && params["hrEmployeePayStructures.new"]=="false" && params["hrEmployeePayStructures.id"] != null)
            {
                hrEmployeePayStructure = HrEmployeePayStructure.get(Long.valueOf(params["hrEmployeePayStructures.id"]))
                hrEmployeeInstance.removeFromHrEmployeePayStructures(hrEmployeePayStructure)
                hrEmployeePayStructure.delete()
                i++
                continue
            }

            else
            {
                hrEmployeePayStructure = HrEmployeePayStructure.get(Long.valueOf(params["hrEmployeePayStructures.id"]))

                hrEmployeePayStructure.properties['payScaleId'] = getLongValue(params.payScaleId)
                hrEmployeePayStructure.properties['stage'] = params.stage

                hrEmployeePayStructure.properties['pBasic'] = getFloatValue(params.pBasic)
                hrEmployeePayStructure.properties['pDa'] = getFloatValue(params.pDa)
                hrEmployeePayStructure.properties['pConveyance'] = getFloatValue(params.pConveyance)
                hrEmployeePayStructure.properties['pMedicalAllowance'] = getFloatValue(params.pMedicalAllowance)
                hrEmployeePayStructure.properties['pHr'] = getFloatValue(params.pHr)
                hrEmployeePayStructure.properties['pOrgPfContribution'] = getFloatValue(params.pOrgPfContribution)
                hrEmployeePayStructure.properties['pWashingAllow'] = getFloatValue(params.pWashingAllow)

                hrEmployeePayStructure.properties['spAllowance'] = getFloatValue(params.spAllowance)
                hrEmployeePayStructure.properties['spDaAllowance'] = getFloatValue(params.spDaAllowance)
                hrEmployeePayStructure.properties['addSpAllowance'] = getFloatValue(params.addSpAllowance)
                hrEmployeePayStructure.properties['spRetro'] = getFloatValue(params.spRetro)
                hrEmployeePayStructure.properties['retroPf'] = getFloatValue(params.retroPf)

                hrEmployeePayStructure.properties['dGroupInsurance'] = getFloatValue(params.dGroupInsurance)
                hrEmployeePayStructure.properties['dIncomeTax'] = getFloatValue(params.dIncomeTax)
                hrEmployeePayStructure.properties['dIncomeTaxPc'] = getFloatValue(params.dIncomeTaxPc)
                hrEmployeePayStructure.properties['dLoanPf'] = getFloatValue(params.dLoanPf)
                hrEmployeePayStructure.properties['dLoanSalary'] = getFloatValue(params.dLoanSalary)
                hrEmployeePayStructure.properties['dOwnPf'] = getFloatValue(params.dOwnPf)
                hrEmployeePayStructure.properties['dRevenueStamp'] = getFloatValue(params.dRevenueStamp)
                hrEmployeePayStructure.properties['dOthers'] = getFloatValue(params.dOthers)
                hrEmployeePayStructure.properties['payrollRemarks'] = params.payrollRemarks

                if (params.salStopFlag == null)
                {
                    hrEmployeePayStructure.properties['salStopFlag'] = Integer.parseInt('0')
                }
                else
                {
                    hrEmployeePayStructure.properties['salStopFlag'] = params.salStopFlag
                }
            }

            hrEmployeeInstance.addToHrEmployeePayStructures(hrEmployeePayStructure)
            i++;

        } */ // end loop
        // end employees monthly salary payInfo

        // Start employees monthly salary payInfo New


        def hrEmployeePayStructure = new HrEmployeePayStructure()



        def payStructuresId=params["hrEmployeePayStructures.id"].toString().trim()
        println("payStructuresId |"+payStructuresId+"|")

        if(payStructuresId!='0'){

            hrEmployeePayStructure = HrEmployeePayStructure.get(Long.valueOf(payStructuresId))

            hrEmployeePayStructure.properties['payScaleId'] = getLongValue(params.payScaleId)
            /*    hrEmployeePayStructure.properties['stage'] = params.stage*/

            hrEmployeePayStructure.properties['consultantRemuneration'] = getFloatValue(params.consultantRemuneration)
            hrEmployeePayStructure.properties['pBasic'] = getFloatValue(params.pBasic)
            hrEmployeePayStructure.properties['pDa'] = getFloatValue(params.pDa)
            hrEmployeePayStructure.properties['pConveyance'] = getFloatValue(params.pConveyance)
            hrEmployeePayStructure.properties['pMedicalAllowance'] = getFloatValue(params.pMedicalAllowance)
            hrEmployeePayStructure.properties['pHr'] = getFloatValue(params.pHr)
           // hrEmployeePayStructure.properties['pOrgPfContribution'] = getFloatValue(params.pOrgPfContribution)
            hrEmployeePayStructure.properties['pWashingAllow'] = getFloatValue(params.pWashingAllow)
            //Newly Added By Maruf
            hrEmployeePayStructure.properties['festivalAllowance'] = getFloatValue(params.festivalAllowance)
            hrEmployeePayStructure.properties['extraLoadAllowance'] = getFloatValue(params.extraLoadAllowance)
            hrEmployeePayStructure.properties['conveyanceAllowance'] = getFloatValue(params.conveyanceAllowance)
            hrEmployeePayStructure.properties['hillAllowance'] = getFloatValue(params.hillAllowance)
            hrEmployeePayStructure.properties['educationAllowance'] = getFloatValue(params.educationAllowance)

            hrEmployeePayStructure.properties['spAllowance'] = getFloatValue(params.spAllowance)
            hrEmployeePayStructure.properties['spDaAllowance'] = getFloatValue(params.spDaAllowance)
            hrEmployeePayStructure.properties['addSpAllowance'] = getFloatValue(params.addSpAllowance)
            hrEmployeePayStructure.properties['spRetro'] = getFloatValue(params.spRetro)
            hrEmployeePayStructure.properties['retroPf'] = getFloatValue(params.retroPf)

            hrEmployeePayStructure.properties['dGroupInsurance'] = getFloatValue(params.dGroupInsurance)
            hrEmployeePayStructure.properties['dIncomeTax'] = getFloatValue(params.dIncomeTax)
            hrEmployeePayStructure.properties['dIncomeTaxPc'] = getFloatValue(params.dIncomeTaxPc)
            hrEmployeePayStructure.properties['dLoanPf'] = getFloatValue(params.dLoanPf)
            hrEmployeePayStructure.properties['dLoanSalary'] = getFloatValue(params.dLoanSalary)
            hrEmployeePayStructure.properties['dOwnPf'] = getFloatValue(params.dOwnPf)
           // hrEmployeePayStructure.properties['dRevenueStamp'] = getFloatValue(params.dRevenueStamp)
            hrEmployeePayStructure.properties['dOthers'] = getFloatValue(params.dOthers)
            hrEmployeePayStructure.properties['payrollRemarks'] = params.payrollRemarks

            if (params.salStopFlag == null)
            {
                hrEmployeePayStructure.properties['salStopFlag'] = Integer.parseInt('0')
            }
            else
            {
                hrEmployeePayStructure.properties['salStopFlag'] = params.salStopFlag
            }


            hrEmployeeInstance.addToHrEmployeePayStructures(hrEmployeePayStructure)
        }else{
            hrEmployeePayStructure.properties['payScaleId'] = getLongValue(params.payScaleId)
            hrEmployeePayStructure.properties['consultantRemuneration'] = getFloatValue(params.consultantRemuneration)
            hrEmployeePayStructure.properties['pBasic'] = getFloatValue(params.pBasic)
            hrEmployeePayStructure.properties['pDa'] = getFloatValue(params.pDa)
            hrEmployeePayStructure.properties['pConveyance'] = getFloatValue(params.pConveyance)
            hrEmployeePayStructure.properties['pMedicalAllowance'] = getFloatValue(params.pMedicalAllowance)
            hrEmployeePayStructure.properties['pHr'] = getFloatValue(params.pHr)
            //hrEmployeePayStructure.properties['pOrgPfContribution'] = getFloatValue(params.pOrgPfContribution)
            hrEmployeePayStructure.properties['pWashingAllow'] = getFloatValue(params.pWashingAllow)

            //Newly Added By Maruf
            hrEmployeePayStructure.properties['festivalAllowance'] = getFloatValue(params.festivalAllowance)
            hrEmployeePayStructure.properties['extraLoadAllowance'] = getFloatValue(params.extraLoadAllowance)
            hrEmployeePayStructure.properties['conveyanceAllowance'] = getFloatValue(params.conveyanceAllowance)
            hrEmployeePayStructure.properties['hillAllowance'] = getFloatValue(params.hillAllowance)
            hrEmployeePayStructure.properties['educationAllowance'] = getFloatValue(params.educationAllowance)

            hrEmployeePayStructure.properties['spAllowance'] = getFloatValue(params.spAllowance)
            hrEmployeePayStructure.properties['spDaAllowance'] = getFloatValue(params.spDaAllowance)
            hrEmployeePayStructure.properties['addSpAllowance'] = getFloatValue(params.addSpAllowance)
            hrEmployeePayStructure.properties['spRetro'] = getFloatValue(params.spRetro)
            hrEmployeePayStructure.properties['retroPf'] = getFloatValue(params.retroPf)

            hrEmployeePayStructure.properties['dGroupInsurance'] = getFloatValue(params.dGroupInsurance)
            hrEmployeePayStructure.properties['dIncomeTax'] = getFloatValue(params.dIncomeTax)
            hrEmployeePayStructure.properties['dIncomeTaxPc'] = getFloatValue(params.dIncomeTaxPc)
            hrEmployeePayStructure.properties['dLoanPf'] = getFloatValue(params.dLoanPf)
            hrEmployeePayStructure.properties['dLoanSalary'] = getFloatValue(params.dLoanSalary)
            hrEmployeePayStructure.properties['dOwnPf'] = getFloatValue(params.dOwnPf)
            //hrEmployeePayStructure.properties['dRevenueStamp'] = getFloatValue(params.dRevenueStamp)
            hrEmployeePayStructure.properties['dOthers'] = getFloatValue(params.dOthers)
            hrEmployeePayStructure.properties['payrollRemarks'] = params.payrollRemarks

            if (params.salStopFlag == null)
            {
                hrEmployeePayStructure.properties['salStopFlag'] = Integer.parseInt('0')
            }
            else
            {
                hrEmployeePayStructure.properties['salStopFlag'] = params.salStopFlag
            }


            hrEmployeeInstance.addToHrEmployeePayStructures(hrEmployeePayStructure)

        }

        // end employees monthly salary payInfo New


        if (!hrEmployeeInstance.save(flush: true)) {
            render(view: "edit", model: [hrEmployeeInstance: hrEmployeeInstance])
            return
        }
        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), hrEmployeeInstance.id])
        redirect(action: "show", id: hrEmployeeInstance.id)
        //render(view: "show",id: hrEmployeeInstance.id)
    }


    public Float getFloatValue(val){
        def floatValue=0.0
        if (val!=''){
            floatValue=Float.valueOf(val)
        }
        return floatValue
    }
    public Float getLongValue(val){
        def longValue=0
        if (val!=''){
            longValue=Long.valueOf(val)
        }
        return longValue
    }

    def delete() {

        def hrEmployeeInstance = HrEmployee.get(params.id)

        if (!hrEmployeeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrEmployeeInstance.delete(flush: true)
            //flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), params.id])
            flash.message = "Record Successfully Deleted."
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmployee.label', default: 'HrEmployee'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // for search option Start ----------------------------------------------------------------------------------------------------------------
    def search(){
        def cardNo = '%'+params.cardNo+'%'
        def hrEmployeeInstanceList = HrEmployee.findByCardNoLike(cardNo,params)
        if (!hrEmployeeInstanceList){
            flash.message = "Provided ID No is Not Matched!..."
            redirect(action: "list")
        }
        else{
            redirect(action: "show", id: hrEmployeeInstanceList.id)
        }
    }
/*    def searchByEmployeeName(){
        def employeeName = '%'+params.employeeName+'%'
        def hrEmployeeInstanceList = HrEmployee.findByEmployeeNameIlike(employeeName,params)
        if (!hrEmployeeInstanceList){
            flash.message = "Provided Employee Name is Not Matched!..."
            redirect(action: "list")
        }
        else{
            redirect(action: "list", id: hrEmployeeInstanceList.id)
        }
    }*/




    def searchByEmployeeName() {

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = HrEmployee.createCriteria()
        def allValueList = c.list {
            if (params?.employeeName) {
                ilike("employeeName", '%' + params.employeeName + '%')
            }
            // maxResults(params.max)
            order("employeeName", "desc")
            order("cardNo", "desc")
        }
        println(" allValueList.size() :"+ allValueList.size())
        if (!allValueList) {
            flash.message = "Provided Information is Not Matched..."
            redirect(action: "list")
        }
        else {

            List<Object> searchFoundData = new ArrayList<Object>();
            Integer a
            if (params.offset != null) {
                a = Integer.parseInt(params.offset.toString())
            }
            else {
                a = 0
            }
            Integer b = Integer.parseInt(params.max.toString())

            for (int i = a; i < (a + b); i++) {

                if (i < allValueList.size()) {
                    if (!allValueList.get(i).equals(null)) {
                        searchFoundData.add(allValueList.get(i))
                    }
                }
            }

            def  searchValueList = searchFoundData

            render(view: "list", model: [hrEmployeeInstanceList: searchValueList, hrEmployeeInstanceTotal: allValueList.size()])
           }
    }

    // for wild card search options -----------------------------
    /*def search() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = HrEmployee.where
                {
                    cardNo =~ '%'+params.cardNo+'%'
                }
        def hrEmployeeInstanceList = query.list(params)
        if (!hrEmployeeInstanceList){
            flash.message = "This IDNO is Not Matched!..."
            redirect(action: "list")
        }
        else{
            redirect(action: "show", id: hrEmployeeInstanceList.id)
        }
        //render(view: "list", model: [hrEmployeeInstanceList: hrEmployeeInstanceList, hrEmployeeInstanceTotal: query.count()])
    }*/

    // for search option End -----------------------------------------------------------------------------------------------------------------


    /*// start for data retrived from another table ----------------------
    def getServiceLength(){
        def joiningDate = Util.getDateMonddyyyy(params.joiningDate)
        System.out.println("joiningDate: "+ joiningDate)
        def serviceEndDate = Util.getDateMonddyyyy(params.serviceEndDate)
        System.out.println("serviceEndDate: "+ serviceEndDate)
        def todayDate = new Date()
        System.out.println("todayDate: "+ todayDate)
        if (todayDate > serviceEndDate){
          def serviceLength = ((serviceEndDate - joiningDate)/365)
            System.out.println("serviceLength: "+ serviceLength)
        }
        else{
            def serviceLength = ((todayDate - joiningDate)/365)
            System.out.println("serviceLength: "+ serviceLength)
        }
        //def data = serviceLength.toString()
        //System.out.println("data "+ data)
        render new WSReturn(100, serviceLength.toString() , null) as JSON
        // end for data retrieved from another table -------------------------
    }*/


    def delEmpPayInfo()
    {
        def employeesPayrollId = Long.valueOf(params.employeesPayrollId)
        def hrEmployeePayStructureInstance = HrEmployeePayStructure.get(employeesPayrollId)
        hrEmployeePayStructureInstance.delete(flash:true)
        def data = 'Payroll Record Deleted'
        render new WSReturn(100, data , null) as JSON
    }


    // for similar name searching for creating list
    def autoEmployeeName() {
        def foundData = HrEmployee.withCriteria {
            ilike( 'employeeName', '%'+params.term.toString().trim()+ '%' )
        }
        render (foundData?.employeeName as JSON)
    }
    // for similar code searching for creating list
    def autoEmployeeCardNo() {      //made by Maruf
        def foundData = HrEmployee.withCriteria {
            ilike( 'cardNo', '%'+params.term.toString().trim()+ '%' )
        }
        render (foundData?.cardNo as JSON)
    }


    // for similar name searching for creating list
    def autoEmployeeNameByTaGa() {
        Sql sql = new Sql(dataSource)
        def employeeNameList = sql.rows("select employee_name employeeName from hr_employee where upper(employee_name) like '%'||?||'%' and employee_type_id in('2654','2655')",params.term.trim().toString().toUpperCase())

        sql.close()
        render (employeeNameList.employeeName as JSON)
    }
    // for similar code searching for creating list
    def autoEmployeeCardNoByTaGa() {
        Sql sql = new Sql(dataSource)
        def employeeNameList = sql.rows("select card_no cardNo from hr_employee where card_no like '%'||?||'%' and employee_type_id in('2654','2655')",params.term.trim())


        sql.close()
        render (employeeNameList.cardNo as JSON)
    }

    def getEmployeeDetail() {      //made by Maruf
        def data
        def hrEmployee = HrEmployee.findByEmployeeName(params.autoData)
        if (hrEmployee!=null) {

            def data1 = hrEmployee.departmentIdHrDepartment.departmentName
            // System.out.println("data1 "+ data1)
            def data2 = hrEmployee.designationIdHrDesignation.designationName
            // System.out.println("data2 "+ data2)
            def data3 = hrEmployee.id.toString()
            // System.out.println("data3 "+ data3)
            def data4 = hrEmployee.cardNo.toString()
            // System.out.println("data4 "+ data4)
            data = data1+":"+data2+":"+data3+":"+data4
        }
        render new WSReturn(100, data, null) as JSON
    }

    def getEmployeeDetailByCardNo() {     //made by Maruf
        def data
        def hrEmployee = HrEmployee.findByCardNo(params.autoData)
        if (hrEmployee!=null) {

            def data1 = hrEmployee.departmentIdHrDepartment.departmentName
            // System.out.println("data1 "+ data1)
            def data2 = hrEmployee.designationIdHrDesignation.designationName
            // System.out.println("data2 "+ data2)
            def data3 = hrEmployee.id.toString()
            // System.out.println("data3 "+ data3)
            def data4 = hrEmployee.employeeName.toString()
            // System.out.println("data4 "+ data4)
            data = data1+":"+data2+":"+data3+":"+data4
        }
        render new WSReturn(100, data, null) as JSON
    }

    //--------------- start get branch name from a bank ---------------------------------------
    def getBranchName()
    {
        def bankId = Long.valueOf(params.bankId)
        def query = "from AfmBankBranch br where br.afmBankInfo.id in(" + bankId + ") order by id"
        def afmBankBranches = AfmBankBranch.executeQuery(query)
        def sb = new StringBuilder()
        sb.append("[")
        for (AfmBankBranch afmBankBranch: afmBankBranches) {
            sb.append("{'id':'" + afmBankBranch.id + "', 'value':'" + afmBankBranch.branchName + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        render new WSReturn(100, sb.toString(), null) as JSON
    }
    //--------------- start get branch name from a bank -------------------------


    // start Employee Service Length calculation
    def getEmpTotalServiceLength()
    {
        Sql sql = new Sql(dataSource)
        def currentDate
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        def hrEmployeeInstance = HrEmployee.findByCardNo(params.employeeCardNo)
        def joiningDate = hrEmployeeInstance?.joiningDate
        joiningDate = simpleDateFormat.format(joiningDate)

        //if (params.serviceEndDate != null && Util.getDateMonddyyyy(params.serviceEndDate) >= new Date()) {
        if (params.serviceEndDate != null) {
            currentDate = params.serviceEndDate
        }
        else {
            currentDate =  simpleDateFormat.format(new Date())
        }
        def serviceLength = sql.firstRow("select GET_EMP_SERVICE_LENGTH(?,?) totalServiceLength from dual",joiningDate,currentDate)
        print(serviceLength)
        def serviceLengthArray = serviceLength.totalServiceLength.split(":")
        def totalYear = serviceLengthArray[0]
        def totalMonth = serviceLengthArray[1]
        def totalDays = serviceLengthArray[2]

        def data = totalYear+":"+totalMonth+":"+totalDays


        sql.close()
        render new WSReturn(100, data , null) as JSON
    }
    // end Employee Service Length calculation


    def employeeListReport() {
        println params

        String fileName = 'EMPLOYEE_LIST_REPORT'                       // For Excel Step 1  Start
        String outputFileName = fileName + "." + params.reportFormat
        String jasperFileName = fileName + JASPER_EXTENSION     // For Excel Step 1  End

        String reportDir = Util.getReportDirectory()
        Map reportParams = new LinkedHashMap()
        reportParams.put(REPORT_DIR, reportDir)
        reportParams.put(SUBREPORT_DIR, reportDir)

        /*reportParams.put('FDATE', P_F_DATE)*/

        def fileFormat         // For Excel Step 2

        if (params.reportFormat.toString().toUpperCase() == 'PDF') {
            fileFormat = JasperExportFormat.PDF_FORMAT
            JasperReportDef reportDef = new JasperReportDef(name: jasperFileName, fileFormat: fileFormat,
                    parameters: reportParams, folder: reportDir)
            ByteArrayOutputStream report = jasperService.generateReport(reportDef)
            response.contentType = "application/pdf"
            response.setHeader("Content-disposition", "attachment;filename=" + outputFileName)   //OUTPUT_FILE_NAME+PDF_EXTENSION)
            response.outputStream << report.toByteArray()

        } else {         // For Excel Step 5
            employeeListReportExcelFormat()
            //scheduleOfFixedAssetsReportExcelFormat()
        }
    }


    def employeeListReportExcelFormat() {



        System.out.println("generateEMPLOYEE_LIST_REPORT..innn....");

        def columnMap = new HashMap()
        int j = 65
        String delimiter = "@MARUF@"                                                                                                                                            //  "Written Down Value as at "+P_F_DATE+" (Taka)"
        // Column Name will be show  by the string //Now space and new line will not excepted         //step -1
        String columnNameStr = "SL. No." + delimiter + "ID No" + delimiter + "Employee Name" + delimiter + "Department" + delimiter + "Designation" + delimiter +"Joining Date"+ delimiter +"Employee Type"

        println("columnNameStr "+columnNameStr)

        String[] columnNameArray = columnNameStr.split(delimiter)

        def file = new File("EMPLOYEE_LIST_REPORT.xls")          //step -2
        OutputStream os = new FileOutputStream(file)
        super.createEmpty()

        Sheet sheet = workbook?.getSheet("Sheet1");
        Row row = sheet.createRow(0)


        if (columnNameArray.length > 0) {
            for (int i; i < columnNameArray.length; i++) {
                columnMap.put(String.valueOf(Character.toChars(j)), columnNameArray[i])
                j++
                //  println("columnMap "+columnMap)

                // creating cell for those columnMap
                Cell cel = row.createCell(i)
                // println("columnNameArray[i] "+columnNameArray[i])
                cel.setCellValue(columnNameArray[i])
            }
        }


        Map CONFIG_PROJECTION_COLUMN_MAP = [
                sheet: 'Sheet1',
                startRow: 1,
                columnMap: columnMap
        ]


        Sql sql = new Sql(dataSource)
        //step -3
        String sqlStr="SELECT CARD_NO,EMPLOYEE_NAME,DEPARTMENT_NAME,DESIGNATION_NAME,JOINING_DATE,EMPLOYEE_TYPE FROM HR_EMPLOYEE_VW " +
                      " ORDER BY DEPARTMENT_NAME,CARD_NO ASC "

        println("sqlStr :"+sqlStr)

        sqlValueList = sql.rows(sqlStr)

        println("sqlValueList.size()  :" + sqlValueList.size())


        def columnValueList = new ArrayList()

        int i=1;
        //generate list for column map
        for (list in sqlValueList) {     //step -4   Start

            Map valueMap = new HashMap()

            valueMap.put('SL. No.', i)
            valueMap.put('ID No', list.CARD_NO)
            valueMap.put('Employee Name', list.EMPLOYEE_NAME)
            valueMap.put('Department', list.DEPARTMENT_NAME)
            valueMap.put('Designation', list.DESIGNATION_NAME)
            String DATE=""
            if (list.JOINING_DATE!=null){
                DATE=util.DateRelated.changeDateForExcel(list.JOINING_DATE.toString().trim())
               // println("DATE : dd/mon/yyyy "+DATE)
            }

            valueMap.put('Joining Date', DATE)
            valueMap.put('Employee Type', list.EMPLOYEE_TYPE)
            i++
            columnValueList.add(valueMap)  //step -4 End
        }
        // System.out.println("columnValueList...." + columnValueList);

        //generate list for column map end
        excelImportService.setColumns(columnValueList, workbook, CONFIG_PROJECTION_COLUMN_MAP)
        workbook.write(os);
        os.close()

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
        response.outputStream << file.newInputStream()

    }



    def downloadFile() {
        def  fName=params.emplDocName
        String fileAbsolutePath = grailsAttributes.getApplicationContext().getResource("employeeDoc"+File.separator +fName+"").getFile().toString()
        println("fileAbsolutePath "+fileAbsolutePath)
        def file = new File(fileAbsolutePath); //<-- you'll probably want to pass in the file name dynamically with the 'params' map
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
        response.outputStream << file.newInputStream()
    }



}
