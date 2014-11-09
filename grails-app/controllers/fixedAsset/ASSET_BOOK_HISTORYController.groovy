package fixedAsset

import org.springframework.dao.DataIntegrityViolationException

class ASSET_BOOK_HISTORYController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [ASSET_BOOK_HISTORYInstanceList: ASSET_BOOK_HISTORY.list(params), ASSET_BOOK_HISTORYInstanceTotal: ASSET_BOOK_HISTORY.count()]
    }

    def create() {
        [ASSET_BOOK_HISTORYInstance: new ASSET_BOOK_HISTORY(params)]
    }

    def save() {
        def ASSET_BOOK_HISTORYInstance = new ASSET_BOOK_HISTORY(params)
        if (!ASSET_BOOK_HISTORYInstance.save(flush: true)) {
            render(view: "create", model: [ASSET_BOOK_HISTORYInstance: ASSET_BOOK_HISTORYInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), ASSET_BOOK_HISTORYInstance.id])
        redirect(action: "show", id: ASSET_BOOK_HISTORYInstance.id)
    }

    def show(Long id) {
        def ASSET_BOOK_HISTORYInstance = ASSET_BOOK_HISTORY.get(id)
        if (!ASSET_BOOK_HISTORYInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), id])
            redirect(action: "list")
            return
        }

        [ASSET_BOOK_HISTORYInstance: ASSET_BOOK_HISTORYInstance]
    }

    def edit(Long id) {
        def ASSET_BOOK_HISTORYInstance = ASSET_BOOK_HISTORY.get(id)
        if (!ASSET_BOOK_HISTORYInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), id])
            redirect(action: "list")
            return
        }

        [ASSET_BOOK_HISTORYInstance: ASSET_BOOK_HISTORYInstance]
    }

    def update(Long id, Long version) {
        def ASSET_BOOK_HISTORYInstance = ASSET_BOOK_HISTORY.get(id)
        if (!ASSET_BOOK_HISTORYInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (ASSET_BOOK_HISTORYInstance.version > version) {
                ASSET_BOOK_HISTORYInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY')] as Object[],
                        "Another user has updated this ASSET_BOOK_HISTORY while you were editing")
                render(view: "edit", model: [ASSET_BOOK_HISTORYInstance: ASSET_BOOK_HISTORYInstance])
                return
            }
        }

        ASSET_BOOK_HISTORYInstance.properties = params

        if (!ASSET_BOOK_HISTORYInstance.save(flush: true)) {
            render(view: "edit", model: [ASSET_BOOK_HISTORYInstance: ASSET_BOOK_HISTORYInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), ASSET_BOOK_HISTORYInstance.id])
        redirect(action: "show", id: ASSET_BOOK_HISTORYInstance.id)
    }

    def delete(Long id) {
        def ASSET_BOOK_HISTORYInstance = ASSET_BOOK_HISTORY.get(id)
        if (!ASSET_BOOK_HISTORYInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), id])
            redirect(action: "list")
            return
        }

        try {
            ASSET_BOOK_HISTORYInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY'), id])
            redirect(action: "show", id: id)
        }
    }
}
