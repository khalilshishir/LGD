package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrEmployeeDeductionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [hrEmployeeDeductionInstanceList: HrEmployeeDeduction.list(params), hrEmployeeDeductionInstanceTotal: HrEmployeeDeduction.count()]
    }

    def create() {
        [hrEmployeeDeductionInstance: new HrEmployeeDeduction(params)]
    }

    def save() {
        def hrEmployeeDeductionInstance = new HrEmployeeDeduction(params)
        if (!hrEmployeeDeductionInstance.save(flush: true)) {
            render(view: "create", model: [hrEmployeeDeductionInstance: hrEmployeeDeductionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), hrEmployeeDeductionInstance.id])
        redirect(action: "show", id: hrEmployeeDeductionInstance.id)
    }

    def show(Long id) {
        def hrEmployeeDeductionInstance = HrEmployeeDeduction.get(id)
        if (!hrEmployeeDeductionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), id])
            redirect(action: "list")
            return
        }

        [hrEmployeeDeductionInstance: hrEmployeeDeductionInstance]
    }

    def edit(Long id) {
        def hrEmployeeDeductionInstance = HrEmployeeDeduction.get(id)
        if (!hrEmployeeDeductionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), id])
            redirect(action: "list")
            return
        }

        [hrEmployeeDeductionInstance: hrEmployeeDeductionInstance]
    }

    def update(Long id, Long version) {
        def hrEmployeeDeductionInstance = HrEmployeeDeduction.get(id)
        if (!hrEmployeeDeductionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (hrEmployeeDeductionInstance.version > version) {
                hrEmployeeDeductionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction')] as Object[],
                        "Another user has updated this HrEmployeeDeduction while you were editing")
                render(view: "edit", model: [hrEmployeeDeductionInstance: hrEmployeeDeductionInstance])
                return
            }
        }

        hrEmployeeDeductionInstance.properties = params

        if (!hrEmployeeDeductionInstance.save(flush: true)) {
            render(view: "edit", model: [hrEmployeeDeductionInstance: hrEmployeeDeductionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), hrEmployeeDeductionInstance.id])
        redirect(action: "show", id: hrEmployeeDeductionInstance.id)
    }

    def delete(Long id) {
        def hrEmployeeDeductionInstance = HrEmployeeDeduction.get(id)
        if (!hrEmployeeDeductionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), id])
            redirect(action: "list")
            return
        }

        try {
            hrEmployeeDeductionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction'), id])
            redirect(action: "show", id: id)
        }
    }
}
