package fixedAsset

import org.springframework.dao.DataIntegrityViolationException

class ASSET_MAINTENANCEController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
//        String MainQQ="from ASSET_MAINTENANCE where IS_RELEASED=0"
        String MainQQ="from ASSET_MAINTENANCE"
        def lst=ASSET_MAINTENANCE.executeQuery(MainQQ)

        def totalCount = ASSET_MAINTENANCE.executeQuery(MainQQ).size()
        [ASSET_MAINTENANCEInstanceList: lst, ASSET_MAINTENANCEInstanceTotal: totalCount]
//        params.max = Math.min(max ?: 10, 100)
//        [ASSET_MAINTENANCEInstanceList: ASSET_MAINTENANCE.list(params), ASSET_MAINTENANCEInstanceTotal: ASSET_MAINTENANCE.count()]
    }
    def listRelease(Integer max) {
        String MainQQ="from ASSET_MAINTENANCE where IS_RELEASED=0"
        def lst=ASSET_MAINTENANCE.executeQuery(MainQQ)

        def totalCount = ASSET_MAINTENANCE.executeQuery(MainQQ).size()
        [ASSET_MAINTENANCEInstanceList: lst, ASSET_MAINTENANCEInstanceTotal: totalCount]
//        params.max = Math.min(max ?: 10, 100)
//        [ASSET_MAINTENANCEInstanceList: ASSET_MAINTENANCE.list(params), ASSET_MAINTENANCEInstanceTotal: ASSET_MAINTENANCE.count()]
    }
    def create() {
        [ASSET_MAINTENANCEInstance: new ASSET_MAINTENANCE(params)]
    }

    def save() {
        def ASSET_MAINTENANCEInstance = new ASSET_MAINTENANCE(params)
        def ASSET_BOOKInstance = ASSET_BOOK.get(params.ASSET_BOOK_ID.id)
        ASSET_BOOKInstance.IS_MAINTENANCE=1
        ASSET_BOOKInstance.save()
        if (!ASSET_MAINTENANCEInstance.save(flush: true)) {
            render(view: "create", model: [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), ASSET_MAINTENANCEInstance.id])
        redirect(action: "show", id: ASSET_MAINTENANCEInstance.id)
    }

    def show(Long id) {
        def ASSET_MAINTENANCEInstance = ASSET_MAINTENANCE.get(id)
        if (!ASSET_MAINTENANCEInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
            return
        }

        [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance]
    }

    def edit(Long id) {
        def ASSET_MAINTENANCEInstance = ASSET_MAINTENANCE.get(id)
        if (!ASSET_MAINTENANCEInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
            return
        }

        [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance]
    }
    def release(Long id) {
        def ASSET_MAINTENANCEInstance = ASSET_MAINTENANCE.get(id)
        if (!ASSET_MAINTENANCEInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
            return
        }

        [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance]
    }

    def update(Long id, Long version) {
        def ASSET_MAINTENANCEInstance = ASSET_MAINTENANCE.get(id)
        if (!ASSET_MAINTENANCEInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (ASSET_MAINTENANCEInstance.version > version) {
                ASSET_MAINTENANCEInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE')] as Object[],
                        "Another user has updated this ASSET_MAINTENANCE while you were editing")
                render(view: "edit", model: [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance])
                return
            }
        }

        ASSET_MAINTENANCEInstance.properties = params

        if (!ASSET_MAINTENANCEInstance.save(flush: true)) {
            render(view: "edit", model: [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), ASSET_MAINTENANCEInstance.id])
        redirect(action: "show", id: ASSET_MAINTENANCEInstance.id)
    }

    def updateForRelease(Long id, Long version) {
        def ASSET_MAINTENANCEInstance = ASSET_MAINTENANCE.get(id)
        def ASSET_BOOKInstance = ASSET_BOOK.get(params.ASSET_BOOK_ID.id)
        ASSET_BOOKInstance.IS_MAINTENANCE=0
        ASSET_BOOKInstance.save()
        if (!ASSET_MAINTENANCEInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (ASSET_MAINTENANCEInstance.version > version) {
                ASSET_MAINTENANCEInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE')] as Object[],
                        "Another user has updated this ASSET_MAINTENANCE while you were editing")
                render(view: "edit", model: [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance])
                return
            }
        }

        ASSET_MAINTENANCEInstance.properties = params

        if (!ASSET_MAINTENANCEInstance.save(flush: true)) {
            render(view: "edit", model: [ASSET_MAINTENANCEInstance: ASSET_MAINTENANCEInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), ASSET_MAINTENANCEInstance.id])
        redirect(action: "show", id: ASSET_MAINTENANCEInstance.id)
    }

    def delete(Long id) {
        def ASSET_MAINTENANCEInstance = ASSET_MAINTENANCE.get(id)
        if (!ASSET_MAINTENANCEInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
            return
        }

        try {
            ASSET_MAINTENANCEInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ASSET_MAINTENANCE.label', default: 'ASSET_MAINTENANCE'), id])
            redirect(action: "show", id: id)
        }
    }
}
