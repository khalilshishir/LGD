package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrLoanController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hrLoanInstanceList: HrLoan.list(params), hrLoanInstanceTotal: HrLoan.count()]
    }

    def create() {
        [hrLoanInstance: new HrLoan(params)]
    }

    def save() {
        def hrLoanInstance = new HrLoan(params)
        //hrLoanInstance.setProperties(loanType:params.loanType.toString().trim().toUpperCase())
        //hrLoanInstance.setProperties(shortName:params.shortName.toString().trim().toUpperCase())
        if (!hrLoanInstance.save(flush: true)) {
            render(view: "create", model: [hrLoanInstance: hrLoanInstance])
            return
        }
        flash.message = "Record Successfully Saved."
        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), hrLoanInstance.id])
        redirect(action: "show", id: hrLoanInstance.id)
    }

    def show() {
        def hrLoanInstance = HrLoan.get(params.id)
        if (!hrLoanInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), params.id])
            redirect(action: "list")
            return
        }
        [hrLoanInstance: hrLoanInstance]
    }

    def edit() {
        def hrLoanInstance = HrLoan.get(params.id)
        if (!hrLoanInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), params.id])
            redirect(action: "list")
            return
        }

        [hrLoanInstance: hrLoanInstance]
    }

    def update() {
        def hrLoanInstance = HrLoan.get(params.id)
        if (!hrLoanInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrLoanInstance.version > version) {
                hrLoanInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrLoan.label', default: 'HrLoan')] as Object[],
                        "Another user has updated this HrLoan while you were editing")
                render(view: "edit", model: [hrLoanInstance: hrLoanInstance])
                return
            }
        }

        hrLoanInstance.properties = params
        //hrLoanInstance.setProperties(loanType:params.loanType.toString().trim().toUpperCase())
        //hrLoanInstance.setProperties(shortName:params.shortName.toString().trim().toUpperCase())
        if (!hrLoanInstance.save(flush: true)) {
            render(view: "edit", model: [hrLoanInstance: hrLoanInstance])
            return
        }
        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), hrLoanInstance.id])
        redirect(action: "show", id: hrLoanInstance.id)
    }

    def delete() {
        def hrLoanInstance = HrLoan.get(params.id)
        if (!hrLoanInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrLoanInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrLoan.label', default: 'HrLoan'), params.id])
            redirect(action: "show", id: params.id)
        }
    }


    // ---------- start search option ------------------------------------------------------------------------------
    def search() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = HrLoan.where
                {
                    loanType =~ '%'+params.loanType+'%'
                }

        def hrLoanInstanceList = query.list(params)

        render(view: "list", model: [hrLoanInstanceList: hrLoanInstanceList, hrLoanInstanceTotal: query.count()])
    }
    // ---------- end search option ---------------------------------------------------------------------------------

}
