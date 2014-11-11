package hrms

import org.springframework.dao.DataIntegrityViolationException

class HrPayscaleDetailController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hrPayscaleDetailInstanceList: HrPayscaleDetail.list(params), hrPayscaleDetailInstanceTotal: HrPayscaleDetail.count()]
    }

    def create() {
        [hrPayscaleDetailInstance: new HrPayscaleDetail(params)]
    }

    def save() {
        def hrPayscaleDetailInstance = new HrPayscaleDetail(params)
        if (!hrPayscaleDetailInstance.save(flush: true)) {
            render(view: "create", model: [hrPayscaleDetailInstance: hrPayscaleDetailInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), hrPayscaleDetailInstance.id])
        redirect(action: "show", id: hrPayscaleDetailInstance.id)
    }

    def show() {
        def hrPayscaleDetailInstance = HrPayscaleDetail.get(params.id)
        if (!hrPayscaleDetailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), params.id])
            redirect(action: "list")
            return
        }

        [hrPayscaleDetailInstance: hrPayscaleDetailInstance]
    }

    def edit() {
        def hrPayscaleDetailInstance = HrPayscaleDetail.get(params.id)
        if (!hrPayscaleDetailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), params.id])
            redirect(action: "list")
            return
        }

        [hrPayscaleDetailInstance: hrPayscaleDetailInstance]
    }

    def update() {
        def hrPayscaleDetailInstance = HrPayscaleDetail.get(params.id)
        if (!hrPayscaleDetailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrPayscaleDetailInstance.version > version) {
                hrPayscaleDetailInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail')] as Object[],
                        "Another user has updated this HrPayscaleDetail while you were editing")
                render(view: "edit", model: [hrPayscaleDetailInstance: hrPayscaleDetailInstance])
                return
            }
        }

        hrPayscaleDetailInstance.properties = params

        if (!hrPayscaleDetailInstance.save(flush: true)) {
            render(view: "edit", model: [hrPayscaleDetailInstance: hrPayscaleDetailInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), hrPayscaleDetailInstance.id])
        redirect(action: "show", id: hrPayscaleDetailInstance.id)
    }

    def delete() {
        def hrPayscaleDetailInstance = HrPayscaleDetail.get(params.id)
        if (!hrPayscaleDetailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrPayscaleDetailInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    /*// for search option Start
    def search()
    {
        def hrPayscale =HrPayscale.findById(Long.valueOf(params.hrPayscale))
        def hrPayscaleInstanceList = null;
        def totalCount = 0;
        hrPayscaleInstanceList = HrPayscale.findAllByGradeNo(hrPayscale.gradeNo)
        totalCount =  hrPayscaleInstanceList.size()
        render(view: "list", model: [hrPayscaleInstanceList: hrPayscaleInstanceList, hrPayscaleInstanceTotal: totalCount])
    }
    // for search option End -----------------------------------------------------------------------------------------------------------------*/

}
