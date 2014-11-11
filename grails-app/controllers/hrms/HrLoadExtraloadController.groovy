package hrms

import grails.converters.JSON
import inventory.WSReturn
import org.springframework.dao.DataIntegrityViolationException

class HrLoadExtraloadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)

        params.sort = params.sort ?: "semesterYear"
        params.order = params.order ?: "desc"
        params.sort = params.sort ?: "hrLoadExtraloadDetails.facultyId"
        params.order = params.order ?: "desc"

        [hrLoadExtraloadInstanceList: HrLoadExtraload.list(params), hrLoadExtraloadInstanceTotal: HrLoadExtraload.count()]
    }

    /*def search(){
        def cardNo = params.cardNo
        def emp = HrEmployee.findByCardNo(cardNo)
        def extra = HrLoadExtraload.findByEmployeeIdHrEmployee(emp)
        if (extra){

            render(view: "list", model: [hrLoadExtraloadInstanceList: extra, value: params.cardNo, hrLoadExtraloadInstanceTotal: 1])
        }
        else{
            render(view: "list", model: [hrLoadExtraloadInstanceList: extra, value: params.cardNo, hrLoadExtraloadInstanceTotal: 0])
        }
    }*/

    def toInitCap(String param) {
        param = param.toLowerCase()
        if (param != null && param.length() > 0) {
            char[] charArray = param.toCharArray(); // convert into char array
            charArray[0] = Character.toUpperCase(charArray[0]); // set capital letter to first postion
            return new String(charArray); // return desired output
        } else {
            return "";
        }
    }

    def search() {
        params.max = Math.min(params.max ? params.int('max') : 24, 100)
        def query = new ArrayList<HrLoadExtraload>()
        def year
        def pay
        def emp
        def semester
        def hold
        def process

        if (params.cardNo) {
            emp = HrEmployee.findByCardNo(params.cardNo)
            if (emp?.id) {
                pay = HrLoadExtraload.findAllByEmployeeIdHrEmployee(emp)
            }

        }
        if (params.semesterYear) {
            String mystr = params.semesterYear.toString().replaceAll("[^\\d]", "");
            if (mystr != '') {
                year = Integer.valueOf(mystr)
            }

        }


        if (params.cardNo != '' && year != null && params.semester != '') {

            semester = toInitCap(params.semester)

            def query1 = HrLoadExtraload.findAllBySemesterYearAndSemesterType(year, semester)
            for (int k = 0; k < pay.size(); k++) {
                for (int j = query1.size() - 1; j >= 0; j--) {
                    if (query1?.get(j)?.id.equals(pay?.get(k)?.id)) {
                        query.add(query1?.get(j))
                    }
                }

            }
        }

        else if (year != null && params.semester != '') {

            semester = toInitCap(params.semester)
            query = HrLoadExtraload.findAllBySemesterYearAndSemesterType(year, semester)
        }

        else if (params.cardNo != '' && year != null) {

            query = HrLoadExtraload.findAllByEmployeeIdHrEmployeeAndSemesterYear(emp, year)
        }

        else if (params.cardNo != '' && params.semester != '') {
            semester = toInitCap(params.semester)
            query = HrLoadExtraload.findAllByEmployeeIdHrEmployeeAndSemesterType(emp, semester.toString().trim())
        }

        else if (params.cardNo) {
            if (emp?.id) {
                query = HrLoadExtraload.findAllByEmployeeIdHrEmployee(emp)
            }

        }

        else if (year != null && params.salaryHold) {
            hold = toInitCap(params.salaryHold)
            query = HrLoadExtraload.findAllBySemesterYearAndSalaryHold(year, hold.toString().trim())
        }

        else if (params.semester && params.salaryHold) {
            semester = toInitCap(params.semester)
            hold = toInitCap(params.salaryHold)
            query = HrLoadExtraload.findAllBySemesterTypeAndSalaryHold(semester.toString().trim(), hold.toString().trim())
        }

        else if (year != null && params.salaryProcess) {
            process = params.salaryProcess.toString().toUpperCase()
            query = HrLoadExtraload.findAllBySemesterYearAndSalaryProcess(year, process.toString().trim())
        }

        else if (params.semester && params.salaryProcess) {
            semester = toInitCap(params.semester)
            process = params.salaryProcess.toString().toUpperCase()
            query = HrLoadExtraload.findAllBySemesterTypeAndSalaryProcess(semester.toString().trim(), process.toString().trim())
        }

        else if (params.salaryProcess) {
            process = params.salaryProcess.toString().toUpperCase()
            query = HrLoadExtraload.findAllBySalaryProcess(process)
        }
        else if (params.salaryHold) {
            hold = toInitCap(params.salaryHold)
            query = HrLoadExtraload.findAllBySalaryHold(hold.toString().trim())
        }

        else if (year != null) {

            query = HrLoadExtraload.findAllBySemesterYear(year)
        }
        else if (params.semester) {
            semester = toInitCap(params.semester)
            query = HrLoadExtraload.findAllBySemesterType(semester.toString().trim())
        }


        else {
            query = HrLoadExtraload.getAll()
        }

        if (query.size() == 0) {
            render(view: "list", model: [hrLoadExtraloadInstanceList: null, value4: params.cardNo, value3: params.employee, value1: params.semesterYear, value2: params.semester, value5: params.salaryHold, value6: params.salaryProcess, hrLoadExtraloadInstanceTotal: 0])

        }
        else {

            List<HrLoadExtraload> searchFoundData = new ArrayList<HrLoadExtraload>();
            Integer a
            if (params.offset != null) {
                a = Integer.parseInt(params.offset.toString())
            }
            else {
                a = 0
            }
            Integer b = Integer.parseInt(params.max.toString())

            for (int i = a; i < (a + b); i++) {

                if (i < query.size()) {
                    if (!query.get(i).equals(null)) {
                        searchFoundData.add(query.get(i))
                    }
                }
            }

            def hrLoadExtraloadInstanceList = searchFoundData
            render(view: "list", model: [hrLoadExtraloadInstanceList: hrLoadExtraloadInstanceList, value4: params.cardNo, value3: params.employee, value1: params.semesterYear, value2: params.semester, value5: params.salaryHold, value6: params.salaryProcess, hrLoadExtraloadInstanceTotal: query?.size()])

        }

    }

    def create() {
        [hrLoadExtraloadInstance: new HrLoadExtraload(params)]
    }

    def save() {
        // def hrLoadExtraloadInstance = new HrLoadExtraload(params)

        // ---------- start add for data saved -------------------------------------------------------
        def hrLoadExtraloadInstance = new HrLoadExtraload()
        // bind parameters

        if (params.employeeIdHrEmployee != "" && params.employeeIdHrEmployee != null) {

            def card = params.employeeIdHrEmployee.toString().split(" --> ")
            def user = HrEmployee.findByCardNo(card[1])
            hrLoadExtraloadInstance.setEmployeeIdHrEmployee(user)
        }

        hrLoadExtraloadInstance.properties['id', 'designationId', 'semesterType', 'semesterYear', 'salaryHold', 'salaryProcess', 'incomeTax'] = params
        int i = 0
        while (params["hrLoadExtraloadDetails[" + i + "].loadType"] != null) {
            def hrLoadExtraloadDetail = new HrLoadExtraloadDetail()

            hrLoadExtraloadDetail.properties['loadType'] = params["hrLoadExtraloadDetails[" + i + "].loadType"]

            /*def courseIdHrDummyCourse= params["hrLoadExtraloadDetails["+i+"].courseIdHrDummyCourse"]
            if(!courseIdHrDummyCourse.equals("")) {
                def  courseId = HrDummyCourse.findByCourseName(courseIdHrDummyCourse)
                hrLoadExtraloadDetail.setCourseIdHrDummyCourse(courseId)
            }*/

            hrLoadExtraloadDetail.properties['courseIdHrDummyCourse'] = params["hrLoadExtraloadDetails[" + i + "].courseIdHrDummyCourse"]
            hrLoadExtraloadDetail.properties['courseName'] = params["hrLoadExtraloadDetails[" + i + "].courseName"]
            hrLoadExtraloadDetail.properties['courseSection'] = params["hrLoadExtraloadDetails[" + i + "].courseSection"]

            hrLoadExtraloadDetail.properties['programmeType'] = params["hrLoadExtraloadDetails[" + i + "].programmeType"]
            hrLoadExtraloadDetail.properties['creditHour'] = Float.valueOf(params["hrLoadExtraloadDetails[" + i + "].creditHour"])
            hrLoadExtraloadDetail.properties['lectureHour'] = Float.valueOf(params["hrLoadExtraloadDetails[" + i + "].lectureHour"])
            hrLoadExtraloadInstance.addToHrLoadExtraloadDetails(hrLoadExtraloadDetail)
            i++
        }
        // ---------- end  add for data saved ---------------------------------------------------------

        if (!hrLoadExtraloadInstance.save(flush: true)) {
            render(view: "create", model: [hrLoadExtraloadInstance: hrLoadExtraloadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), hrLoadExtraloadInstance.id])
        redirect(action: "show", id: hrLoadExtraloadInstance.id)
    }

    def show() {
        def hrLoadExtraloadInstance = HrLoadExtraload.get(params.id)
        if (!hrLoadExtraloadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), params.id])
            redirect(action: "list")
            return
        }

        // [hrLoadExtraloadInstance: hrLoadExtraloadInstance]
        //[hrLoadExtraloadInstance: hrLoadExtraloadInstance]
        // For details List Value
        def hrLoadExtraload = HrLoadExtraload.findById(params.id)
        List<HrLoadExtraloadDetail> hrLoadExtraloadDetailList = HrLoadExtraloadDetail.findAllByLoadExtraloadIdHrLoadExtraload(hrLoadExtraload)

        [hrLoadExtraloadInstance: hrLoadExtraloadInstance, hrLoadExtraloadDetails: hrLoadExtraloadDetailList]
    }

    def edit() {
        def hrLoadExtraloadInstance = HrLoadExtraload.get(params.id)
        if (!hrLoadExtraloadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), params.id])
            redirect(action: "list")
            return
        }

        //[hrLoadExtraloadInstance: hrLoadExtraloadInstance]
        // For details List Value
        def hrLoadExtraload = HrLoadExtraload.findById(params.id)
        List<HrLoadExtraloadDetail> hrLoadExtraloadDetailList = HrLoadExtraloadDetail.findAllByLoadExtraloadIdHrLoadExtraload(hrLoadExtraload)

        [hrLoadExtraloadInstance: hrLoadExtraloadInstance, hrLoadExtraloadDetails: hrLoadExtraloadDetailList]

    }

    def update() {
        def hrLoadExtraloadInstance = HrLoadExtraload.get(params.id)
        if (!hrLoadExtraloadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrLoadExtraloadInstance.version > version) {
                hrLoadExtraloadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload')] as Object[],
                        "Another user has updated this HrLoadExtraload while you were editing")
                render(view: "edit", model: [hrLoadExtraloadInstance: hrLoadExtraloadInstance])
                return
            }
        }

        //hrLoadExtraloadInstance.properties = params
        if (params.employeeIdHrEmployee != "" && params.employeeIdHrEmployee != null) {

            def card = params.employeeIdHrEmployee.toString().split(" --> ")
            def user = HrEmployee.findByCardNo(card[1])
            hrLoadExtraloadInstance.setEmployeeIdHrEmployee(user)
        }

        hrLoadExtraloadInstance.properties['id', 'designationId', 'semesterType', 'semesterYear', 'salaryHold', 'salaryProcess', 'incomeTax'] = params
        int i = 0
        while (params["hrLoadExtraloadDetails[" + i + "].loadType"] != null) {
            def hrLoadExtraloadDetail = new HrLoadExtraloadDetail()

            if (params["hrLoadExtraloadDetails[" + i + "].deleted"] == "true" && params["hrLoadExtraloadDetails[" + i + "].new"] == "false") {
                hrLoadExtraloadDetail = HrLoadExtraloadDetail.get(Long.valueOf(params["hrLoadExtraloadDetails[" + i + "].id"]))
                hrLoadExtraloadInstance.removeFromHrLoadExtraloadDetails(hrLoadExtraloadDetail)
                hrLoadExtraloadDetail.delete()
                i++
                continue
            }
            else if (params["hrLoadExtraloadDetails[" + i + "].deleted"] == "true" && params["hrLoadExtraloadDetails[" + i + "].new"] == "true") {
                i++
                continue
            }
            else if (params["hrLoadExtraloadDetails[" + i + "].deleted"] == "false" && params["hrLoadExtraloadDetails[" + i + "].new"] == "true") {
                hrLoadExtraloadDetail = new HrLoadExtraloadDetail()
                /*hrLoadExtraloadDetail.properties['loadType'] = params["hrLoadExtraloadDetails[" + i + "].loadType"]

                def courseIdHrDummyCourse = params["hrLoadExtraloadDetails[" + i + "].courseIdHrDummyCourse"]
                if (!courseIdHrDummyCourse.equals("")) {
                    def courseId = HrDummyCourse.findById(Long.valueOf(courseIdHrDummyCourse))
                    hrLoadExtraloadDetail.setCourseIdHrDummyCourse(courseId)
                }

                hrLoadExtraloadDetail.properties['courseIdHrDummyCourse'] = params["hrLoadExtraloadDetails[" + i + "].courseIdHrDummyCourse"]
                hrLoadExtraloadDetail.properties['courseName'] = params["hrLoadExtraloadDetails[" + i + "].courseName"]
                hrLoadExtraloadDetail.properties['courseSection'] = params["hrLoadExtraloadDetails[" + i + "].courseSection"]

                hrLoadExtraloadDetail.properties['programmeType'] = params["hrLoadExtraloadDetails[" + i + "].programmeType"]
                hrLoadExtraloadDetail.properties['creditHour'] = Float.valueOf(params["hrLoadExtraloadDetails[" + i + "].creditHour"])
                hrLoadExtraloadDetail.properties['lectureHour'] = Float.valueOf(params["hrLoadExtraloadDetails[" + i + "].lectureHour"])*/
            }
            else {
                hrLoadExtraloadDetail = HrLoadExtraloadDetail.get(Long.valueOf(params["hrLoadExtraloadDetails[" + i + "].id"]))
            }

            hrLoadExtraloadDetail.properties['loadType'] = params["hrLoadExtraloadDetails[" + i + "].loadType"]

            hrLoadExtraloadDetail.properties['courseIdHrDummyCourse'] = params["hrLoadExtraloadDetails[" + i + "].courseIdHrDummyCourse"]
            hrLoadExtraloadDetail.properties['courseName'] = params["hrLoadExtraloadDetails[" + i + "].courseName"]
            hrLoadExtraloadDetail.properties['courseSection'] = params["hrLoadExtraloadDetails[" + i + "].courseSection"]

            hrLoadExtraloadDetail.properties['programmeType'] = params["hrLoadExtraloadDetails[" + i + "].programmeType"]
            hrLoadExtraloadDetail.properties['creditHour'] = Float.valueOf(params["hrLoadExtraloadDetails[" + i + "].creditHour"])
            hrLoadExtraloadDetail.properties['lectureHour'] = Float.valueOf(params["hrLoadExtraloadDetails[" + i + "].lectureHour"])

            hrLoadExtraloadInstance.addToHrLoadExtraloadDetails(hrLoadExtraloadDetail)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------


        if (!hrLoadExtraloadInstance.save(flush: true)) {
            render(view: "edit", model: [hrLoadExtraloadInstance: hrLoadExtraloadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), hrLoadExtraloadInstance.id])
        redirect(action: "show", id: hrLoadExtraloadInstance.id)
    }

    def delete() {
        def hrLoadExtraloadInstance = HrLoadExtraload.get(params.id)
        if (!hrLoadExtraloadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrLoadExtraloadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrLoadExtraload.label', default: 'HrLoadExtraload'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // start for data retrived from another table ----------------------
    def getEmpInfo() {

        def idObj = HrEmployee.findByCardNo(params.idNo)
        def data1 = idObj.designationIdHrDesignation.id
        //System.out.println("data1 : "+ data1)
        def data2 = idObj.designationIdHrDesignation.designationName
        //System.out.println("data2 : "+ data2)
        def data = data1 + ":" + data2
        render new WSReturn(100, data, null) as JSON
    }
    // end for data retrived from another table -------------------------

    // start for data retrived from another table ----------------------
    def getCourseInfo() {
        def idObj = HrDummyCourse.get(params.courseId)
        def data1 = idObj.programmeType
        //System.out.println("data1 : "+ data1)
        def data2 = idObj.creditHours.toString()
        //System.out.println("data2 : "+ data2)
        def data3 = idObj.lectureHours.toString()
        //System.out.println("data3 : "+ data3)
        def data = data1 + ":" + data2 + ":" + data3
        //System.out.println("data : "+ data)
        render new WSReturn(100, data, null) as JSON
    }
    // end for data retrived from another table -------------------------

    def getEmployeeCardList() {

        def data = HrLoadExtraload.getAll()
        ArrayList<String> foundData = new ArrayList<String>()
        for (i in data) {
            if (i?.employeeIdHrEmployee?.cardNo.contains(params.term.toString().trim())) {
                foundData.add(i?.employeeIdHrEmployee?.cardNo)
            }

        }
        render(foundData as JSON)
    }

    def getEmployeeNameList() {

        def data = HrLoadExtraload.getAll()

        List<HrEmployee> emps = HrEmployee.withCriteria {
            ilike('employeeName', '%' + params.term.toString().trim() + '%')
        }

        ArrayList<String> foundData = new ArrayList<String>()

        for (i in data) {
             for (j in emps){
                 if (i?.employeeIdHrEmployee?.employeeName.equals(j?.employeeName)) {
                     foundData.add(i?.employeeIdHrEmployee?.employeeName + ' --> ' + i?.employeeIdHrEmployee?.cardNo)
                 }
             }

        }
        render(foundData as JSON)
    }

    def getNameByCard() {

        def emp = HrEmployee.findByCardNo(params.code)
        def info = emp?.employeeName + ' --> ' + emp?.cardNo
        render new WSReturn(100, info, null) as JSON


    }
}
