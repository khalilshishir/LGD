package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrLeaveController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hrLeaveInstanceList: HrLeave.list(params), hrLeaveInstanceTotal: HrLeave.count()]
    }

    def create() {
        [hrLeaveInstance: new HrLeave(params)]
    }

    def save() {
        def hrLeaveInstance = new HrLeave(params)
        //hrLeaveInstance.setProperties(leaveType:params.leaveType.toString().trim().toUpperCase())
        //hrLeaveInstance.setProperties(shortName:params.shortName.toString().trim().toUpperCase())
        if (!hrLeaveInstance.save(flush: true)) {
            render(view: "create", model: [hrLeaveInstance: hrLeaveInstance])
            return
        }
        flash.message = "Record Successfully Saved."
		//flash.message = message(code: 'default.created.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), hrLeaveInstance.id])
        redirect(action: "show", id: hrLeaveInstance.id)
    }

    def show() {
        def hrLeaveInstance = HrLeave.get(params.id)
        if (!hrLeaveInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), params.id])
            redirect(action: "list")
            return
        }
        [hrLeaveInstance: hrLeaveInstance]
    }

    def edit() {
        def hrLeaveInstance = HrLeave.get(params.id)
        if (!hrLeaveInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), params.id])
            redirect(action: "list")
            return
        }

        [hrLeaveInstance: hrLeaveInstance]
    }

    def update() {
        def hrLeaveInstance = HrLeave.get(params.id)
        if (!hrLeaveInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrLeaveInstance.version > version) {
                hrLeaveInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hrLeave.label', default: 'HrLeave')] as Object[],
                          "Another user has updated this HrLeave while you were editing")
                render(view: "edit", model: [hrLeaveInstance: hrLeaveInstance])
                return
            }
        }

        hrLeaveInstance.properties = params
        //hrLeaveInstance.setProperties(leaveType:params.leaveType.toString().trim().toUpperCase())
        //hrLeaveInstance.setProperties(shortName:params.shortName.toString().trim().toUpperCase())
        if (!hrLeaveInstance.save(flush: true)) {
            render(view: "edit", model: [hrLeaveInstance: hrLeaveInstance])
            return
        }
        flash.message = "Record Successfully Updated."
		//flash.message = message(code: 'default.updated.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), hrLeaveInstance.id])
        redirect(action: "show", id: hrLeaveInstance.id)
    }

    def delete() {
        def hrLeaveInstance = HrLeave.get(params.id)
        if (!hrLeaveInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrLeaveInstance.delete(flush: true)
			//flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), params.id])
            flash.message = "Record Successfully Deleted."
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrLeave.label', default: 'HrLeave'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // ---------- start search option ------------------------------------------------------------------------------
    def search() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = HrLeave.where
                {
                    leaveType =~ '%'+params.leaveType+'%'
                }

        def hrLeaveInstanceList = query.list(params)

        render(view: "list", model: [hrLeaveInstanceList: hrLeaveInstanceList, hrLeaveInstanceTotal: query.count()])
    }
    // ---------- end search option ---------------------------------------------------------------------------------

}
