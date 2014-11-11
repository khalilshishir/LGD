package accounts

import org.springframework.dao.DataIntegrityViolationException

class AfmCalendarController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
/*        params.sort="serialNo"
        params.order="asc"*/
        [afmCalendarInstanceList: AfmCalendar.list(params), afmCalendarInstanceTotal: AfmCalendar.count()]
    }

    def create() {
        [afmCalendarInstance: new AfmCalendar(params)]
    }

    def save() {

        def afmCalendarInstance = new AfmCalendar()

        params.fromDate= Util.getDateMonddyyyy(params.fromDate)
        params.toDate= Util.getDateMonddyyyy(params.toDate)

        afmCalendarInstance.properties["id", "calendarYear","fromDate","toDate","period","calendarType"] = params

        int i = 0
        while (params["afmCalendarMonth[" + i + "].calendarMonth"] != null) {

            def afmCalendarMonthInstance = new AfmCalendarMonth()
            def serialNo=params["afmCalendarMonth[" + i + "].serialNo"]
            System.out.println(serialNo)
            if(!serialNo.equals("")){
                afmCalendarMonthInstance.properties['serialNo']=Integer.valueOf(serialNo)
                
            }
            afmCalendarMonthInstance.properties['calendarMonth']=params["afmCalendarMonth[" + i + "].calendarMonth"]
            def calendarYear=params["afmCalendarMonth[" + i + "].calendarYear"]
            if(!calendarYear.equals("")){
                afmCalendarMonthInstance.properties['calendarYear']=Integer.valueOf(calendarYear)
            }
            def calendarQuater=params["afmCalendarMonth[" + i + "].calendarQuater"]
            if(!calendarQuater.equals("")){
                afmCalendarMonthInstance.properties['calendarQuater']=Integer.valueOf(calendarQuater)
            }
            def fromDate = params["afmCalendarMonth[" + i + "].fromDate"]
            if(!fromDate.equals("")){
                afmCalendarMonthInstance.properties['fromDate']=Util.getDateMonddyyyy(fromDate)
            }
            def toDate = params["afmCalendarMonth[" + i + "].toDate"]
            if(!toDate.equals("")){
                afmCalendarMonthInstance.properties['toDate']=Util.getDateMonddyyyy(toDate)
            }
            afmCalendarMonthInstance.properties['periodName']=params["afmCalendarMonth[" + i + "].periodName"]

            def isAdjustmentPeriod = params["afmCalendarMonth[" + i + "].isAdjustmentPeriod"]
            if(!isAdjustmentPeriod.equals("")){
                afmCalendarMonthInstance.properties['isAdjustmentPeriod']=Integer.valueOf(isAdjustmentPeriod)
            }
            afmCalendarInstance.addToAfmCalendarMonth(afmCalendarMonthInstance)
            i++
        }

        if (!afmCalendarInstance.save(flush: true)) {
            render(view: "create", model: [afmCalendarInstance: afmCalendarInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), afmCalendarInstance.id])
        redirect(action: "show", id: afmCalendarInstance.id)

    }

    def show() {
        def afmCalendarInstance = AfmCalendar.get(params.id)
        if (!afmCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), params.id])
            redirect(action: "list")
            return
        }

        // For details List Value
        def afmCalendar =AfmCalendar.findById(params.id)
        List<AfmCalendarMonth> afmCalendarMonthList = AfmCalendarMonth.findAllByAfmCalendar(afmCalendar)

        [afmCalendarInstance: afmCalendarInstance,afmCalendarMonthList:afmCalendarMonthList]
    }

    def edit() {
        def afmCalendarInstance = AfmCalendar.get(params.id)
        if (!afmCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), params.id])
            redirect(action: "list")
            return
        }

        // For details List Value
        def afmCalendar =AfmCalendar.findById(params.id)
        List<AfmCalendarMonth> afmCalendarMonthList = AfmCalendarMonth.findAllByAfmCalendar(afmCalendar)

        [afmCalendarInstance: afmCalendarInstance,afmCalendarMonthList:afmCalendarMonthList]
    }

    def update() {
        def afmCalendarInstance = AfmCalendar.get(params.id)
        if (!afmCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), params.id])
            redirect(action: "list")
            return
        }

        params.fromDate= Util.getDateMonddyyyy(params.fromDate)
        params.toDate= Util.getDateMonddyyyy(params.toDate)

        afmCalendarInstance.properties["id", "calendarYear","fromDate","toDate","period","calendarType"] = params

        int i = 0

        while (params["afmCalendarMonth[" + i + "].calendarMonth"] != null) {

            def afmCalendarMonthInstance

            if (params["afmCalendarMonth[" + i + "].deleted"] == "true" && params["afmCalendarMonth[" + i + "].new"] == "false") {
                afmCalendarMonthInstance = AfmCalendarMonth.get(Long.valueOf(params["afmCalendarMonth[" + i + "].id"]))
                afmCalendarInstance.removeFromAfmCalendarMonth(afmCalendarMonthInstance)
                afmCalendarMonthInstance.delete()
                i++
                continue
            }
            else if (params["afmCalendarMonth[" + i + "].deleted"] == "true" && params["afmCalendarMonth[" + i + "].new"] == "true") {
                i++
                continue
            }
            else if (params["afmCalendarMonth[" + i + "].deleted"] == "false" && params["afmCalendarMonth[" + i + "].new"] == "true") {
                afmCalendarMonthInstance = new AfmCalendarMonth()
                afmCalendarMonthInstance.properties['calendarMonth'] =params["afmCalendarMonth[" + i + "].calendarMonth"]
            }
            else {
                afmCalendarMonthInstance = AfmCalendarMonth.get(Long.valueOf(params["afmCalendarMonth[" + i + "].id"]))
            }

            def serialNo=params["afmCalendarMonth[" + i + "].serialNo"]
            if(!serialNo.equals("")){
                afmCalendarMonthInstance.properties['serialNo']=Integer.valueOf(serialNo)
            }
            afmCalendarMonthInstance.properties['calendarMonth']=params["afmCalendarMonth[" + i + "].calendarMonth"]
            def calendarYear=params["afmCalendarMonth[" + i + "].calendarYear"]
            if(!calendarYear.equals("")){
                afmCalendarMonthInstance.properties['calendarYear']=Integer.valueOf(calendarYear)
            }
            def calendarQuater=params["afmCalendarMonth[" + i + "].calendarQuater"]
            if(!calendarQuater.equals("")){
                afmCalendarMonthInstance.properties['calendarQuater']=Integer.valueOf(calendarQuater)
            }
            def fromDate = params["afmCalendarMonth[" + i + "].fromDate"]
            if(!fromDate.equals("")){
                afmCalendarMonthInstance.properties['fromDate']=Util.getDateMonddyyyy(fromDate)
            }
            def toDate = params["afmCalendarMonth[" + i + "].toDate"]
            if(!toDate.equals("")){
                afmCalendarMonthInstance.properties['toDate']=Util.getDateMonddyyyy(toDate)
            }
            afmCalendarMonthInstance.properties['periodName']=params["afmCalendarMonth[" + i + "].periodName"]

            def isAdjustmentPeriod = params["afmCalendarMonth[" + i + "].isAdjustmentPeriod"]
            if(!isAdjustmentPeriod.equals("")){
                afmCalendarMonthInstance.properties['isAdjustmentPeriod']=Integer.valueOf(isAdjustmentPeriod)
            }
            afmCalendarInstance.addToAfmCalendarMonth(afmCalendarMonthInstance)

            i++

        }

        if (!afmCalendarInstance.save(flush: true)) {
            render(view: "edit", model: [afmCalendarInstance: afmCalendarInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), afmCalendarInstance.id])
        redirect(action: "show", id: afmCalendarInstance.id)

    }

    def delete() {
        def afmCalendarInstance = AfmCalendar.get(params.id)
        if (!afmCalendarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), params.id])
            redirect(action: "list")
            return
        }

        try {
            afmCalendarInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'afmCalendar.label', default: 'AfmCalendar'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
