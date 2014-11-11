package hrms

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrDesignationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hrDesignationInstanceList: HrDesignation.list(params), hrDesignationInstanceTotal: HrDesignation.count()]
    }

    def create() {
        [hrDesignationInstance: new HrDesignation(params)]
    }

    def save() {
        def hrDesignationInstance = new HrDesignation(params)
        //hrDesignationInstance.setProperties(designationName:params.designationName.toString().trim().toUpperCase())
        if (!hrDesignationInstance.save(flush: true)) {
            render(view: "create", model: [hrDesignationInstance: hrDesignationInstance])
            return
        }
        flash.message = "Record Successfully Saved."
        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), hrDesignationInstance.id])
        redirect(action: "show", id: hrDesignationInstance.id)
    }

    def show() {
        def hrDesignationInstance = HrDesignation.get(params.id)
        if (!hrDesignationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), params.id])
            redirect(action: "list")
            return
        }
        [hrDesignationInstance: hrDesignationInstance]
    }

    def edit() {
        def hrDesignationInstance = HrDesignation.get(params.id)
        if (!hrDesignationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), params.id])
            redirect(action: "list")
            return
        }

        [hrDesignationInstance: hrDesignationInstance]
    }

    def update() {
        def hrDesignationInstance = HrDesignation.get(params.id)
        if (!hrDesignationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrDesignationInstance.version > version) {
                hrDesignationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrDesignation.label', default: 'HrDesignation')] as Object[],
                        "Another user has updated this HrDesignation while you were editing")
                render(view: "edit", model: [hrDesignationInstance: hrDesignationInstance])
                return
            }
        }

        hrDesignationInstance.properties = params
        //hrDesignationInstance.setProperties(designationName:params.designationName.toString().trim().toUpperCase())
        if (!hrDesignationInstance.save(flush: true)) {
            render(view: "edit", model: [hrDesignationInstance: hrDesignationInstance])
            return
        }
        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), hrDesignationInstance.id])
        redirect(action: "show", id: hrDesignationInstance.id)
    }

    def delete() {
        def hrDesignationInstance = HrDesignation.get(params.id)
        if (!hrDesignationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrDesignationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrDesignation.label', default: 'HrDesignation'), params.id])
            redirect(action: "show", id: params.id)
        }
    }


    // for search option Start ----------------------------------------------------------------------------------------------------------------
    /*def search() {
        def designationName = '%'+params.designationName+'%'
        def hrDesignationInstanceList = null;
        def totalCount = 0;

        hrDesignationInstanceList = HrDesignation.findAllByDesignationNameLike(designationName, params)
        totalCount =  HrDesignation.countByDesignationName(designationName)

        render(view: "list", model: [hrDesignationInstanceList: hrDesignationInstanceList, hrDesignationInstanceTotal: totalCount])
    }*/

    def search() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = HrDesignation.where
                {
                    designationName =~ '%'+params.designationName+'%'
                }

        def hrDesignationInstanceList = query.list(params)

        render(view: "list", model: [hrDesignationInstanceList: hrDesignationInstanceList, hrDesignationInstanceTotal: query.count()])
    }

    // for search option End --------------------------------- --------------------------------------------------------------------------------


    // start Auto Designation Name Search Query
    def autoDesignationName()
    {
        def foundData = HrDesignation.withCriteria {
            ilike( 'designationName', '%'+params.term.toString().trim()+ '%' )
            order("designationName", "asc")
        }
        render (foundData?.designationName as JSON)
    }
    // end Auto Designation Name Search Query

}
