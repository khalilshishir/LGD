package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrCalendarMonthController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        [hrCalendarMonthInstanceList: HrCalendarMonth.list(params), hrCalendarMonthInstanceTotal: HrCalendarMonth.count()]
    }

    def create() {
        [hrCalendarMonthInstance: new HrCalendarMonth(params)]
    }

/*    def save() {
        def hrCalendarMonthInstance = new HrCalendarMonth(params)
        if (!hrCalendarMonthInstance.save(flush: true)) {
            render(view: "create", model: [hrCalendarMonthInstance: hrCalendarMonthInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), hrCalendarMonthInstance.id])
        redirect(action: "show", id: hrCalendarMonthInstance.id)
    }    */

    def save() {

        def hrCalendarMonthInstance = new HrCalendarMonth()
        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        HrCalendar hrCalendarIdHrCalendar=HrCalendar.get(params.hrCalendarIdHrCalendar.id)
        params.calendarYear = hrCalendarIdHrCalendar.calendarYear
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
        hrCalendarMonthInstance.properties['id','calendarYear','startDate','endDate','serialNo','periodName','hrCalendarIdHrCalendar'] = params
        // hrCalendarMonthInstance.properties = params

        int i=0
        while (params["hrHolidayCalendars[" + i + "].holidayTypeIdHrLookup"] != null)
        {
            // def calendarYearName = hrCalendarMonthInstance.properties['calendarYear']
            def hrHolidayCalendar = new HrHolidayCalendar()

            if(params["hrHolidayCalendars[" + i + "].deleted"]=="true" && params["hrHolidayCalendars[" + i + "].new"]=="false")
            {
                hrHolidayCalendar = HrHolidayCalendar.get(Long.valueOf(params["hrHolidayCalendars[" + i + "].id"]))
                hrCalendarMonthInstance.removeFromHrHolidayCalendars(hrHolidayCalendar)
                hrHolidayCalendar.delete()
                i++
                continue
            }
            else if(params["hrHolidayCalendars[" + i + "].deleted"]=="true" && params["hrHolidayCalendars[" + i + "].new"]=="true")
            {
                i++
                continue
            }
            else if(params["hrHolidayCalendars[" + i + "].deleted"]=="false" && params["hrHolidayCalendars[" + i + "].new"]=="true")
            {
                hrHolidayCalendar = new HrHolidayCalendar()
                hrHolidayCalendar.properties['calendarYear'] = hrCalendarMonthInstance.properties['calendarYear']
                //hrHolidayCalendar.properties['holidayTypeIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
                def holidayTypeId = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
                if(!holidayTypeId.equals("")){
                    holidayTypeId = HrLookup.findById(Long.valueOf(holidayTypeId))
                    hrHolidayCalendar.setHolidayTypeIdHrLookup(holidayTypeId)
                }
                //hrHolidayCalendar.properties['holidayTypeDescIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
                def holidayTypeDescId = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
                if(!holidayTypeDescId.equals("")){
                    holidayTypeDescId = HrLookup.findById(Long.valueOf(holidayTypeDescId))
                    hrHolidayCalendar.setHolidayTypeDescIdHrLookup(holidayTypeDescId)
                }
                hrHolidayCalendar.properties['startDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].startDate"])
                hrHolidayCalendar.properties['endDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].endDate"])
                hrHolidayCalendar.properties['totalDays'] = Integer.valueOf(params["hrHolidayCalendars["+i+"].totalDays"])
            }
            else
            {
                hrHolidayCalendar = HrHolidayCalendar.get(Long.valueOf(params["hrHolidayCalendars[" + i + "].id"]))
            }

            hrCalendarMonthInstance.properties['id','calendarYear','startDate','endDate','serialNo','periodName','hrCalendarIdHrCalendar'] = params

            hrHolidayCalendar.properties['calendarYear'] = hrCalendarMonthInstance.properties['calendarYear']
            //hrHolidayCalendar.properties['holidayTypeIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
            def holidayTypeId = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
            if(!holidayTypeId.equals("")){
                holidayTypeId = HrLookup.findById(Long.valueOf(holidayTypeId))
                hrHolidayCalendar.setHolidayTypeIdHrLookup(holidayTypeId)
            }
            //hrHolidayCalendar.properties['holidayTypeDescIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
            def holidayTypeDescId = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
            if(!holidayTypeDescId.equals("")){
                holidayTypeDescId = HrLookup.findById(Long.valueOf(holidayTypeDescId))
                hrHolidayCalendar.setHolidayTypeDescIdHrLookup(holidayTypeDescId)
            }
            hrHolidayCalendar.properties['startDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].startDate"])
            hrHolidayCalendar.properties['endDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].endDate"])
            hrHolidayCalendar.properties['totalDays'] = Integer.valueOf(params["hrHolidayCalendars["+i+"].totalDays"])

            hrCalendarMonthInstance.addToHrHolidayCalendars(hrHolidayCalendar)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------


        if (!hrCalendarMonthInstance.save(flush: true)) {
            render(view: "create", model: [hrCalendarMonthInstance: hrCalendarMonthInstance])
            return
        }


        flash.message = message(code: 'default.created.message', args: [message(code: 'hrCalendarMonth.label', default: 'Holiday Calendar Month'), hrCalendarMonthInstance.id])
        redirect(action: "show", id: hrCalendarMonthInstance.id)
    }

    def show() {
        def hrCalendarMonthInstance = HrCalendarMonth.get(params.id)
        if (!hrCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrCalendarMonth =HrCalendarMonth.findById(params.id)
        List<HrHolidayCalendar> holidayCalendarList = HrHolidayCalendar.findAllByCalendarMonthIdHrCalendarMonth(hrCalendarMonth)

        [hrCalendarMonthInstance: hrCalendarMonthInstance, hrHolidayCalendars:holidayCalendarList]
       // [hrCalendarMonthInstance: hrCalendarMonthInstance]
    }

    def edit() {
        def hrCalendarMonthInstance = HrCalendarMonth.get(params.id)
        if (!hrCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }
        // For details List Value
        def hrCalendarMonth =HrCalendarMonth.findById(params.id)
        List<HrHolidayCalendar> holidayCalendarList = HrHolidayCalendar.findAllByCalendarMonthIdHrCalendarMonth(hrCalendarMonth)

        [hrCalendarMonthInstance: hrCalendarMonthInstance, hrHolidayCalendars:holidayCalendarList]
        //[hrCalendarMonthInstance: hrCalendarMonthInstance]
    }

    def update() {
        def hrCalendarMonthInstance = HrCalendarMonth.get(params.id)
        if (!hrCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrCalendarMonthInstance.version > version) {
                hrCalendarMonthInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth')] as Object[],
                        "Another user has updated this HrCalendarMonth while you were editing")
                render(view: "edit", model: [hrCalendarMonthInstance: hrCalendarMonthInstance])
                return
            }
        }



        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        params.startDate = Util.getDateMonddyyyy(params.startDate)
        params.endDate = Util.getDateMonddyyyy(params.endDate)
        hrCalendarMonthInstance.properties['id','calendarYear','startDate','endDate','serialNo','periodName','hrCalendarIdHrCalendar'] = params
        // hrCalendarMonthInstance.properties = params

        int i=0
        while (params["hrHolidayCalendars[" + i + "].holidayTypeIdHrLookup"] != null)
        {
          // def calendarYearName = hrCalendarMonthInstance.properties['calendarYear']
            def hrHolidayCalendar = new HrHolidayCalendar()

            if(params["hrHolidayCalendars[" + i + "].deleted"]=="true" && params["hrHolidayCalendars[" + i + "].new"]=="false")
            {
                hrHolidayCalendar = HrHolidayCalendar.get(Long.valueOf(params["hrHolidayCalendars[" + i + "].id"]))
                hrCalendarMonthInstance.removeFromHrHolidayCalendars(hrHolidayCalendar)
                hrHolidayCalendar.delete()
                i++
                continue
            }
            else if(params["hrHolidayCalendars[" + i + "].deleted"]=="true" && params["hrHolidayCalendars[" + i + "].new"]=="true")
            {
                i++
                continue
            }
            else if(params["hrHolidayCalendars[" + i + "].deleted"]=="false" && params["hrHolidayCalendars[" + i + "].new"]=="true")
            {
                hrHolidayCalendar = new HrHolidayCalendar()
                hrHolidayCalendar.properties['calendarYear'] = hrCalendarMonthInstance.properties['calendarYear']
                //hrHolidayCalendar.properties['holidayTypeIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
                def holidayTypeId = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
                if(!holidayTypeId.equals("")){
                    holidayTypeId = HrLookup.findById(Long.valueOf(holidayTypeId))
                    hrHolidayCalendar.setHolidayTypeIdHrLookup(holidayTypeId)
                }
                //hrHolidayCalendar.properties['holidayTypeDescIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
                def holidayTypeDescId = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
                if(!holidayTypeDescId.equals("")){
                    holidayTypeDescId = HrLookup.findById(Long.valueOf(holidayTypeDescId))
                    hrHolidayCalendar.setHolidayTypeDescIdHrLookup(holidayTypeDescId)
                }
                hrHolidayCalendar.properties['startDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].startDate"])
                hrHolidayCalendar.properties['endDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].endDate"])
                hrHolidayCalendar.properties['totalDays'] = Integer.valueOf(params["hrHolidayCalendars["+i+"].totalDays"])
            }
            else
            {
                hrHolidayCalendar = HrHolidayCalendar.get(Long.valueOf(params["hrHolidayCalendars[" + i + "].id"]))
            }

            hrCalendarMonthInstance.properties['id','calendarYear','startDate','endDate','serialNo','periodName','hrCalendarIdHrCalendar'] = params

            hrHolidayCalendar.properties['calendarYear'] = hrCalendarMonthInstance.properties['calendarYear']
            //hrHolidayCalendar.properties['holidayTypeIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
            def holidayTypeId = params["hrHolidayCalendars["+i+"].holidayTypeIdHrLookup"]
            if(!holidayTypeId.equals("")){
                holidayTypeId = HrLookup.findById(Long.valueOf(holidayTypeId))
                hrHolidayCalendar.setHolidayTypeIdHrLookup(holidayTypeId)
            }
            //hrHolidayCalendar.properties['holidayTypeDescIdHrLookup'] = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
            def holidayTypeDescId = params["hrHolidayCalendars["+i+"].holidayTypeDescIdHrLookup"]
            if(!holidayTypeDescId.equals("")){
                holidayTypeDescId = HrLookup.findById(Long.valueOf(holidayTypeDescId))
                hrHolidayCalendar.setHolidayTypeDescIdHrLookup(holidayTypeDescId)
            }
            hrHolidayCalendar.properties['startDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].startDate"])
            hrHolidayCalendar.properties['endDate'] = Util.getDateMonddyyyy(params["hrHolidayCalendars["+i+"].endDate"])
            hrHolidayCalendar.properties['totalDays'] = Integer.valueOf(params["hrHolidayCalendars["+i+"].totalDays"])

            hrCalendarMonthInstance.addToHrHolidayCalendars(hrHolidayCalendar)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------


        if (!hrCalendarMonthInstance.save(flush: true)) {
            render(view: "edit", model: [hrCalendarMonthInstance: hrCalendarMonthInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrCalendarMonth.label', default: 'Holiday Calendar Month'), hrCalendarMonthInstance.id])
        redirect(action: "show", id: hrCalendarMonthInstance.id)
    }

    def delete() {
        def hrCalendarMonthInstance = HrCalendarMonth.get(params.id)
        if (!hrCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrCalendarMonthInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrCalendarMonth.label', default: 'HrCalendarMonth'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

     // --------- start search option ------------------------------------------
    def search() {
        def calendarYear = params.calendarYear
        def hrCalendarMonthList = HrCalendarMonth.findAllByCalendarYear(calendarYear,params)
        def totalCount = HrCalendarMonth.countByCalendarYear(calendarYear);
        render(view: "list", model: [hrCalendarMonthInstanceList: hrCalendarMonthList,hrCalendarMonthInstanceTotal:totalCount])
    }
    // --------- end search option ------------------------------------------

}
