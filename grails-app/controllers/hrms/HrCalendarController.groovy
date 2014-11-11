package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrCalendarController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [hrCalendarInstanceList: HrCalendar.list(params), hrCalendarInstanceTotal: HrCalendar.count()]
    }

    def create() {
        [hrCalendarInstance: new HrCalendar(params)]
    }

    def save() {
        params.startMonth = Util.getDateMonddyyyy(params.startMonth)
        params.endMonth = Util.getDateMonddyyyy(params.endMonth)
        //def hrCalendarInstance = new HrCalendar(params)

        // ---------- start add for data saved -------------------------------------------------------
        def hrCalendarInstance = new HrCalendar()
        // bind parameters
        hrCalendarInstance.properties['id', 'calendarYear', 'startMonth', 'endMonth'] = params
        int i = 0
        while (params["hrCalendarMonths[" + i + "].calendarYear"] != null) {
            def hrCalendarMonth = new HrCalendarMonth()
            hrCalendarMonth.properties['calendarYear'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].calendarYear"])
            hrCalendarMonth.properties['startDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].startDate"])
            hrCalendarMonth.properties['endDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].endDate"])
            hrCalendarMonth.properties['periodName'] = params["hrCalendarMonths[" + i + "].periodName"]
            hrCalendarMonth.properties['serialNo'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].serialNo"])
            hrCalendarInstance.addToHrCalendarMonths(hrCalendarMonth)
            i++
        }
        // ---------- end  add for data saved ---------------------------------------------------------

        if (!hrCalendarInstance.save(flush: true)) {
            render(view: "create", model: [hrCalendarInstance: hrCalendarInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), hrCalendarInstance.id])
        redirect(action: "show", id: hrCalendarInstance.calendarYear)
    }

    def show() {
        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrCalendar = HrCalendar.findById(params.id)
        List<HrCalendarMonth> hrCalendarMonthList = HrCalendarMonth.findAllByHrCalendarIdHrCalendar(hrCalendar)

        [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList]
        // [hrCalendarInstance: hrCalendarInstance]
    }

    def processCalenderShow() {

        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrCalendar = HrCalendar.findById(params.id)
        List<HrCalendarMonth> hrCalendarMonthList = HrCalendarMonth.findAllByHrCalendarIdHrCalendar(hrCalendar)

        [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList]
    }

    def edit() {
        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrCalendar = HrCalendar.findById(params.id)
        List<HrCalendarMonth> hrCalendarMonthList = HrCalendarMonth.findAllByHrCalendarIdHrCalendar(hrCalendar)

        [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList]

        //[hrCalendarInstance: hrCalendarInstance]
    }

    def update() {
        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrCalendarInstance.version > version) {
                hrCalendarInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrCalendar.label', default: 'HrCalendar')] as Object[],
                        "Another user has updated this HrCalendar while you were editing")
                render(view: "edit", model: [hrCalendarInstance: hrCalendarInstance])
                return
            }
        }

        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        params.startMonth = Util.getDateMonddyyyy(params.startMonth)
        params.endMonth = Util.getDateMonddyyyy(params.endMonth)
        hrCalendarInstance.properties['id', 'calendarYear', 'startMonth', 'endMonth'] = params
        //hrCalendarInstance.properties = params

        int i = 0

        while (params["hrCalendarMonths[" + i + "].calendarYear"] != null) {
            def hrCalendarMonth = new HrCalendarMonth()
            if (params["hrCalendarMonths[" + i + "].deleted"] == "true" && params["hrCalendarMonths[" + i + "].new"] == "false") {
                hrCalendarMonth = HrCalendarMonth.get(Long.valueOf(params["hrCalendarMonths[" + i + "].id"]))
                hrCalendarInstance.removeFromHrCalendarMonths(hrCalendarMonth)
                hrCalendarMonth.delete()
                i++
                continue
            }
            else if (params["hrCalendarMonths[" + i + "].deleted"] == "true" && params["hrCalendarMonths[" + i + "].new"] == "true") {
                i++
                continue
            }
            else if (params["hrCalendarMonths[" + i + "].deleted"] == "false" && params["hrCalendarMonths[" + i + "].new"] == "true") {
                hrCalendarMonth = new HrCalendarMonth()
                hrCalendarMonth.properties['calendarYear'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].calendarYear"])
                hrCalendarMonth.properties['startDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].startDate"])
                hrCalendarMonth.properties['endDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].endDate"])
                hrCalendarMonth.properties['periodName'] = params["hrCalendarMonths[" + i + "].periodName"]
                hrCalendarMonth.properties['serialNo'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].serialNo"])
            }
            else {
                hrCalendarMonth = hrCalendarMonth.get(Long.valueOf(params["hrCalendarMonths[" + i + "].id"]))
            }
            //hrCalendarInstance.properties = params
            hrCalendarInstance.properties['id', 'calendarYear', 'startMonth', 'endMonth'] = params

            hrCalendarMonth.properties['calendarYear'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].calendarYear"])
            hrCalendarMonth.properties['startDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].startDate"])
            hrCalendarMonth.properties['endDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].endDate"])
            hrCalendarMonth.properties['periodName'] = params["hrCalendarMonths[" + i + "].periodName"]
            hrCalendarMonth.properties['serialNo'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].serialNo"])

            hrCalendarInstance.addToHrCalendarMonths(hrCalendarMonth)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------

        if (!hrCalendarInstance.save(flush: true)) {
            render(view: "edit", model: [hrCalendarInstance: hrCalendarInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), hrCalendarInstance.id])
        redirect(action: "show", id: hrCalendarInstance.id)

    }

    def delete() {
        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrCalendarInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // ---------------- start search option ---------------------------------
    def search() {
        redirect(action: "show", id: params.hrCalendar)
    }
    // ---------------- start search option ---------------------------------


    def processCalenderSearch(){
        redirect(action: "processCalenderShow", id: params.hrCalendar)
    }

    def processCalender() {

        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        render(view: 'processCalenderList', model: [hrCalendarInstanceList: HrCalendar.list(params), hrCalendarInstanceTotal: HrCalendar.count()])

        /* def hrCalendarInstance = HrCalendar.get(params.id)
     if (!hrCalendarInstance) {
         flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
         redirect(action: "list")
         return
     }
     // For details List Value
     def hrCalendar = HrCalendar.findById(params.id)
     List<HrCalendarMonth> hrCalendarMonthList = HrCalendarMonth.findAllByHrCalendarIdHrCalendar(hrCalendar)

//        render(view:'processCalender',model: [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList] )
     [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList]*/

    }

    def processCalenderEdit() {
        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrCalendar = HrCalendar.findById(params.id)
        List<HrCalendarMonth> hrCalendarMonthList = HrCalendarMonth.findAllByHrCalendarIdHrCalendar(hrCalendar)

        render(view: 'processCalender', model: [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList])
//        [hrCalendarInstance: hrCalendarInstance, hrCalendarMonths: hrCalendarMonthList]
    }

    def processCalenderUpdate() {

        def hrCalendarInstance = HrCalendar.get(params.id)
        if (!hrCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrCalendarInstance.version > version) {
                hrCalendarInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrCalendar.label', default: 'HrCalendar')] as Object[],
                        "Another user has updated this HrCalendar while you were editing")
                render(view: "edit", model: [hrCalendarInstance: hrCalendarInstance])
                return
            }
        }

        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        params.startMonth = Util.getDateMonddyyyy(params.startMonth)
        params.endMonth = Util.getDateMonddyyyy(params.endMonth)
        hrCalendarInstance.properties['id', 'calendarYear', 'startMonth', 'endMonth'] = params
        //hrCalendarInstance.properties = params

        int i = 0

        while (params["hrCalendarMonths[" + i + "].calendarYear"] != null) {
            def hrCalendarMonth = new HrCalendarMonth()
            if (params["hrCalendarMonths[" + i + "].deleted"] == "true" && params["hrCalendarMonths[" + i + "].new"] == "false") {
                hrCalendarMonth = HrCalendarMonth.get(Long.valueOf(params["hrCalendarMonths[" + i + "].id"]))
                hrCalendarInstance.removeFromHrCalendarMonths(hrCalendarMonth)
                hrCalendarMonth.delete()
                i++
                continue
            }
            else if (params["hrCalendarMonths[" + i + "].deleted"] == "true" && params["hrCalendarMonths[" + i + "].new"] == "true") {
                i++
                continue
            }
            else if (params["hrCalendarMonths[" + i + "].deleted"] == "false" && params["hrCalendarMonths[" + i + "].new"] == "true") {
                hrCalendarMonth = new HrCalendarMonth()
                hrCalendarMonth.properties['calendarYear'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].calendarYear"])
                hrCalendarMonth.properties['startDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].startDate"])
                hrCalendarMonth.properties['endDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].endDate"])
                hrCalendarMonth.properties['periodName'] = params["hrCalendarMonths[" + i + "].periodName"]
                hrCalendarMonth.properties['serialNo'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].serialNo"])
            }
            else {
                hrCalendarMonth = hrCalendarMonth.get(Long.valueOf(params["hrCalendarMonths[" + i + "].id"]))
            }
            //hrCalendarInstance.properties = params
            hrCalendarInstance.properties['id', 'calendarYear', 'startMonth', 'endMonth'] = params

            hrCalendarMonth.properties['calendarYear'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].calendarYear"])
            hrCalendarMonth.properties['startDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].startDate"])
            hrCalendarMonth.properties['endDate'] = Util.getDateMonddyyyy(params["hrCalendarMonths[" + i + "].endDate"])
            hrCalendarMonth.properties['periodName'] = params["hrCalendarMonths[" + i + "].periodName"]
            hrCalendarMonth.properties['serialNo'] = Integer.valueOf(params["hrCalendarMonths[" + i + "].serialNo"])

            if ((!params["hrCalendarMonths[" + i + "].salary"].equals('')) && (!params["hrCalendarMonths[" + i + "].salary"].equals(null))) {
                hrCalendarMonth.properties['salary'] = params["hrCalendarMonths[" + i + "].salary"]
            }
            else {
                hrCalendarMonth.properties['salary'] = 0
            }
            if ((!params["hrCalendarMonths[" + i + "].increment"].equals('')) && (!params["hrCalendarMonths[" + i + "].increment"].equals(null))) {
                hrCalendarMonth.properties['increment'] = params["hrCalendarMonths[" + i + "].increment"]

            }
            else {
                hrCalendarMonth.properties['increment'] = 0
            }
            if ((!params["hrCalendarMonths[" + i + "].bonus"].equals('')) && (!params["hrCalendarMonths[" + i + "].bonus"].equals(null))) {
                hrCalendarMonth.properties['bonus'] = params["hrCalendarMonths[" + i + "].bonus"]

            }
            else {
                hrCalendarMonth.properties['bonus'] = 0
            }

            hrCalendarInstance.addToHrCalendarMonths(hrCalendarMonth)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------

        if (!hrCalendarInstance.save(flush: true)) {
            render(view: "edit", model: [hrCalendarInstance: hrCalendarInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrCalendar.label', default: 'HrCalendar'), hrCalendarInstance.id])
        redirect(action: "processCalenderShow", id: hrCalendarInstance.id)

    }

}
