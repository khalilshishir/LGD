package accounts

import org.springframework.dao.DataIntegrityViolationException

class AfmBankBranchController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [afmBankBranchInstanceList: AfmBankBranch.list(params), afmBankBranchInstanceTotal: AfmBankBranch.count()]
    }

    def create() {
        [afmBankBranchInstance: new AfmBankBranch(params)]
    }

    def save() {
        def afmBankBranchInstance = new AfmBankBranch(params)
        if (!afmBankBranchInstance.save(flush: true)) {
            render(view: "create", model: [afmBankBranchInstance: afmBankBranchInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), afmBankBranchInstance.id])
        redirect(action: "show", id: afmBankBranchInstance.id)
    }

    def show() {
        def afmBankBranchInstance = AfmBankBranch.get(params.id)
        if (!afmBankBranchInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), params.id])
            redirect(action: "list")
            return
        }

        [afmBankBranchInstance: afmBankBranchInstance]
    }

    def edit() {
        def afmBankBranchInstance = AfmBankBranch.get(params.id)
        if (!afmBankBranchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), params.id])
            redirect(action: "list")
            return
        }

        [afmBankBranchInstance: afmBankBranchInstance]
    }

    def update() {
        def afmBankBranchInstance = AfmBankBranch.get(params.id)
        if (!afmBankBranchInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (afmBankBranchInstance.version > version) {
                afmBankBranchInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'afmBankBranch.label', default: 'AfmBankBranch')] as Object[],
                          "Another user has updated this AfmBankBranch while you were editing")
                render(view: "edit", model: [afmBankBranchInstance: afmBankBranchInstance])
                return
            }
        }

        afmBankBranchInstance.properties = params

        if (!afmBankBranchInstance.save(flush: true)) {
            render(view: "edit", model: [afmBankBranchInstance: afmBankBranchInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), afmBankBranchInstance.id])
        redirect(action: "show", id: afmBankBranchInstance.id)
    }

    def delete() {
        def afmBankBranchInstance = AfmBankBranch.get(params.id)
        if (!afmBankBranchInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), params.id])
            redirect(action: "list")
            return
        }

        try {
            afmBankBranchInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'afmBankBranch.label', default: 'AfmBankBranch'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
