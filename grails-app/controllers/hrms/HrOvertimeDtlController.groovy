package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrOvertimeDtlController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hrOvertimeDtlInstanceList: HrOvertimeDtl.list(params), hrOvertimeDtlInstanceTotal: HrOvertimeDtl.count()]
    }

    def create() {
        [hrOvertimeDtlInstance: new HrOvertimeDtl(params)]
    }

    def save() {
        def hrOvertimeDtlInstance = new HrOvertimeDtl(params)
        if (!hrOvertimeDtlInstance.save(flush: true)) {
            render(view: "create", model: [hrOvertimeDtlInstance: hrOvertimeDtlInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), hrOvertimeDtlInstance.id])
        redirect(action: "show", id: hrOvertimeDtlInstance.id)
    }

    def show() {
        def hrOvertimeDtlInstance = HrOvertimeDtl.get(params.id)
        if (!hrOvertimeDtlInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), params.id])
            redirect(action: "list")
            return
        }

        [hrOvertimeDtlInstance: hrOvertimeDtlInstance]
    }

    def edit() {
        def hrOvertimeDtlInstance = HrOvertimeDtl.get(params.id)
        if (!hrOvertimeDtlInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), params.id])
            redirect(action: "list")
            return
        }

        [hrOvertimeDtlInstance: hrOvertimeDtlInstance]
    }

    def update() {
        def hrOvertimeDtlInstance = HrOvertimeDtl.get(params.id)
        if (!hrOvertimeDtlInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrOvertimeDtlInstance.version > version) {
                hrOvertimeDtlInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl')] as Object[],
                        "Another user has updated this HrOvertimeDtl while you were editing")
                render(view: "edit", model: [hrOvertimeDtlInstance: hrOvertimeDtlInstance])
                return
            }
        }

        hrOvertimeDtlInstance.properties = params

        if (!hrOvertimeDtlInstance.save(flush: true)) {
            render(view: "edit", model: [hrOvertimeDtlInstance: hrOvertimeDtlInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), hrOvertimeDtlInstance.id])
        redirect(action: "show", id: hrOvertimeDtlInstance.id)
    }

    def delete() {
        def hrOvertimeDtlInstance = HrOvertimeDtl.get(params.id)
        if (!hrOvertimeDtlInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrOvertimeDtlInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrOvertimeDtl.label', default: 'HrOvertimeDtl'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
