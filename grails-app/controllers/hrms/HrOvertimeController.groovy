package hrms

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrOvertimeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [hrOvertimeInstanceList: HrOvertime.list(params), hrOvertimeInstanceTotal: HrOvertime.count()]
    }

    def create() {
        [hrOvertimeInstance: new HrOvertime(params)]
    }

    def save() {
        //def hrOvertimeInstance = new HrOvertime(params)

        // ---------- start add for data saved -------------------------------------------------------
        def hrOvertimeInstance = new HrOvertime()
        // bind parameters
        hrOvertimeInstance.properties['id','overtimeYear','overtimeMonth','payscaleIdHrPayscale'] = params
        //def listSize=params.listSize
        int i=0
        //params["hrOvertimeDtls[" + i + "].employeeIdHrEmployee"]
        while (params["employeeIdHrEmployee["+i+"]"] != null)
        {
            def hrOvertimeDtl = new HrOvertimeDtl()
            def employeeIdHrEmployee= params["employeeIdHrEmployee["+i+"]"]
            if(!employeeIdHrEmployee.equals("")) {
                def  employeeId = HrEmployee.findByEmployeeName(employeeIdHrEmployee)
                hrOvertimeDtl.setEmployeeIdHrEmployee(employeeId)
            }
            hrOvertimeDtl.properties['overtimeHour'] = Integer.valueOf(params["overtimeHour["+i+"]"])

/*
            def employeeId = params["hrOvertimeDtls["+i+"].employeeIdHrEmployee"]
            if(!employeeId.equals("")) {
                employeeId = HrEmployee.findById(Long.valueOf(employeeId))
                hrOvertimeDtl.setEmployeeIdHrEmployee(employeeId)
            }
*/


            hrOvertimeInstance.addToHrOvertimeDtls(hrOvertimeDtl)
            i++
        }
        // ---------- end  add for data saved ---------------------------------------------------------



        if (!hrOvertimeInstance.save(flush: true)) {
            render(view: "create", model: [hrOvertimeInstance: hrOvertimeInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), hrOvertimeInstance.id])
        redirect(action: "show", id: hrOvertimeInstance.id)
    }

    def show() {
        def hrOvertimeInstance = HrOvertime.get(params.id)
        if (!hrOvertimeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrOvertime =HrOvertime.findById(params.id)
        List<HrOvertimeDtl> hrOvertimeList = HrOvertimeDtl.findAllByOvertimeIdHrOvertime(hrOvertime)

        [hrOvertimeInstance: hrOvertimeInstance, hrOvertimeDtls:hrOvertimeList]
        //[hrOvertimeInstance: hrOvertimeInstance]
    }

    def edit() {
        def hrOvertimeInstance = HrOvertime.get(params.id)
        if (!hrOvertimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrOvertime =HrOvertime.findById(params.id)
        List<HrOvertimeDtl> hrOvertimeList = HrOvertimeDtl.findAllByOvertimeIdHrOvertime(hrOvertime)

        [hrOvertimeInstance: hrOvertimeInstance, hrOvertimeDtls:hrOvertimeList]
        //[hrOvertimeInstance: hrOvertimeInstance]
    }

    def update() {
        def hrOvertimeInstance = HrOvertime.get(params.id)
        if (!hrOvertimeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrOvertimeInstance.version > version) {
                hrOvertimeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hrOvertime.label', default: 'HrOvertime')] as Object[],
                          "Another user has updated this HrOvertime while you were editing")
                render(view: "edit", model: [hrOvertimeInstance: hrOvertimeInstance])
                return
            }
        }

        //hrOvertimeInstance.properties = params
        hrOvertimeInstance.properties['id','overtimeYear','overtimeMonth','payscaleIdHrPayscale'] = params
        int i=0
        while (params["hrOvertimeDtls[" + i + "].employeeIdHrEmployee"] != null)
        {
            def hrOvertimeDtl = new HrOvertimeDtl()

            if(params["hrOvertimeDtls[" + i + "].deleted"]=="true" && params["hrOvertimeDtls[" + i + "].new"]=="false")
            {
                hrOvertimeDtl = HrOvertimeDtl.get(Long.valueOf(params["hrOvertimeDtls[" + i + "].id"]))
                hrOvertimeInstance.removeFromHrOvertimeDtls(hrOvertimeDtl)
                hrOvertimeDtl.delete()
                i++
                continue
            }
            else if(params["hrOvertimeDtls[" + i + "].deleted"]=="true" && params["hrOvertimeDtls[" + i + "].new"]=="true")
            {
                i++
                continue
            }
            else if(params["hrOvertimeDtls[" + i + "].deleted"]=="false" && params["hrOvertimeDtls[" + i + "].new"]=="true")
            {
                hrOvertimeDtl = new HrOvertimeDtl()
                def employeeId = params["hrOvertimeDtls["+i+"].employeeIdHrEmployee"]
                if(!employeeId.equals("")) {
                    employeeId = HrEmployee.findByEmployeeName(employeeId)
                    hrOvertimeDtl.setEmployeeIdHrEmployee(employeeId)
                }
                hrOvertimeDtl.properties['overtimeHour'] = Integer.valueOf(params["hrOvertimeDtls["+i+"].overtimeHour"])
            }
            else
            {
                hrOvertimeDtl = HrOvertimeDtl.get(Long.valueOf(params["hrOvertimeDtls[" + i + "].id"]))
            }

            hrOvertimeInstance.properties['id','overtimeYear','overtimeMonth','payscaleIdHrPayscale'] = params

            def employeeId = params["hrOvertimeDtls["+i+"].employeeIdHrEmployee"]
            if(!employeeId.equals("")) {
                employeeId = HrEmployee.findByEmployeeName(employeeId)
                hrOvertimeDtl.setEmployeeIdHrEmployee(employeeId)
            }
            hrOvertimeDtl.properties['overtimeHour'] = Integer.valueOf(params["hrOvertimeDtls["+i+"].overtimeHour"])

            hrOvertimeInstance.addToHrOvertimeDtls(hrOvertimeDtl)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------



        if (!hrOvertimeInstance.save(flush: true)) {
            render(view: "edit", model: [hrOvertimeInstance: hrOvertimeInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), hrOvertimeInstance.id])
        redirect(action: "show", id: hrOvertimeInstance.id)
    }

    def delete() {
        def hrOvertimeInstance = HrOvertime.get(params.id)
        if (!hrOvertimeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrOvertimeInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrOvertime.label', default: 'HrOvertime'), params.id])
            redirect(action: "show", id: params.id)
        }
    }


    // start for data retrieved from another table ----------------------
    def getData()
    {
        def idNo = params.idNo
        HrEmployee    idObj = HrEmployee.findById(idNo)
        def data1 = idObj.designationIdHrDesignation.designationName
        System.out.println("data3 "+ data1)
        def data = data1
        System.out.println("data "+ data)

        render new WSReturn(100, data , null) as JSON
        // end for data retrieved from another table -------------------------
    }


    def viewOvertimeDtls() {

        def HrPayScaleInstance = HrPayscale.get(params.payScaleId)
        def hrOvertimeCriteria = HrOvertime.createCriteria()
        def hrOvertimeInstanceList = hrOvertimeCriteria.list {
            and {
                eq("overtimeYear", Integer.valueOf(params.overtimeYear))
                eq("overtimeMonth", params.overtimeMonth)
                eq("payscaleIdHrPayscale", HrPayScaleInstance)
            }
        }
        def hrOvertimeInstanceListTotal = hrOvertimeInstanceList.size()
        System.out.println('count: '+hrOvertimeInstanceListTotal)
        
        if (hrOvertimeInstanceListTotal < 1) {
            def query = "from HrEmployeePayStructure where payScaleId="+Long.valueOf(params.payScaleId)
            def hrEmployeePayStructures = HrEmployeePayStructure.executeQuery(query)
            def hrEmployeePayStructuresTotal = hrEmployeePayStructures.size()
            [hrOvertimeInstanceList: hrEmployeePayStructures,hrOvertimeInstanceTotal: hrEmployeePayStructuresTotal]
        }
    }

}
