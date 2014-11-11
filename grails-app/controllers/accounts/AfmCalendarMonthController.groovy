package accounts

import org.springframework.dao.DataIntegrityViolationException

class AfmCalendarMonthController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [afmCalendarMonthInstanceList: AfmCalendarMonth.list(params), afmCalendarMonthInstanceTotal: AfmCalendarMonth.count()]
    }

    def create() {
        [afmCalendarMonthInstance: new AfmCalendarMonth(params)]
    }

    def save() {
        def afmCalendarMonthInstance = new AfmCalendarMonth(params)
        if (!afmCalendarMonthInstance.save(flush: true)) {
            render(view: "create", model: [afmCalendarMonthInstance: afmCalendarMonthInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), afmCalendarMonthInstance.id])
        redirect(action: "show", id: afmCalendarMonthInstance.id)
    }

    def show() {
        def afmCalendarMonthInstance = AfmCalendarMonth.get(params.id)
        if (!afmCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }

        [afmCalendarMonthInstance: afmCalendarMonthInstance]
    }


    def edit() {
        def afmCalendarMonthInstance = AfmCalendarMonth.get(params.id)
        if (!afmCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }

        [afmCalendarMonthInstance: afmCalendarMonthInstance]
    }

    def update() {
        def afmCalendarMonthInstance = AfmCalendarMonth.get(params.id)
        if (!afmCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (afmCalendarMonthInstance.version > version) {
                afmCalendarMonthInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth')] as Object[],
                        "Another user has updated this AfmCalendarMonth while you were editing")
                render(view: "edit", model: [afmCalendarMonthInstance: afmCalendarMonthInstance])
                return
            }
        }

        afmCalendarMonthInstance.properties = params

        if (!afmCalendarMonthInstance.save(flush: true)) {
            render(view: "edit", model: [afmCalendarMonthInstance: afmCalendarMonthInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), afmCalendarMonthInstance.id])
        redirect(action: "show", id: afmCalendarMonthInstance.id)
    }

    def delete() {
        def afmCalendarMonthInstance = AfmCalendarMonth.get(params.id)
        if (!afmCalendarMonthInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), params.id])
            redirect(action: "list")
            return
        }

        try {
            afmCalendarMonthInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'afmCalendarMonth.label', default: 'AfmCalendarMonth'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
