package hrms

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrDepartmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        /*render(view: "../hrms/hrDepartment/list", model:*/
        [hrDepartmentInstanceList: HrDepartment.list(params), hrDepartmentInstanceTotal: HrDepartment.count()]
    }

    def create() {
        [hrDepartmentInstance: new HrDepartment(params)]
    }

    def save() {
        def hrDepartmentInstance = new HrDepartment(params)
        //hrDepartmentInstance.setProperties(departmentName:params.departmentName.toString().trim().toUpperCase())
        //hrDepartmentInstance.setProperties(shortName:params.shortName.toString().trim().toUpperCase())
        if (!hrDepartmentInstance.save(flush: true))
        {
            render(view: "create", model: [hrDepartmentInstance: hrDepartmentInstance])
            return
        }

        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), hrDepartmentInstance.id])
        flash.message = "Record Successfully Saved."
        redirect(action: "show", id: hrDepartmentInstance.id)
    }

    def show()
    {
        def hrDepartmentInstance = HrDepartment.get(params.id)
        if (!hrDepartmentInstance)
        {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), params.id])
            redirect(action: "list")
            return
        }
        [hrDepartmentInstance: hrDepartmentInstance]
    }

    def edit() {
        def hrDepartmentInstance = HrDepartment.get(params.id)
        if (!hrDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), params.id])
            redirect(action: "list")
            return
        }

        [hrDepartmentInstance: hrDepartmentInstance]
    }

    def update() {
        def hrDepartmentInstance = HrDepartment.get(params.id)
        if (!hrDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrDepartmentInstance.version > version) {
                hrDepartmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrDepartment.label', default: 'HrDepartment')] as Object[],
                        "Another user has updated this HrDepartment while you were editing")
                render(view: "edit", model: [hrDepartmentInstance: hrDepartmentInstance])
                return
            }
        }

        hrDepartmentInstance.properties = params
        //hrDepartmentInstance.setProperties(departmentName:params.departmentName.toString().trim().toUpperCase())
        //hrDepartmentInstance.setProperties(shortName:params.shortName.toString().trim().toUpperCase())
        if (!hrDepartmentInstance.save(flush: true)) {
            render(view: "edit", model: [hrDepartmentInstance: hrDepartmentInstance])
            return
        }
        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), hrDepartmentInstance.id])
        redirect(action: "show", id: hrDepartmentInstance.id)
    }

    def delete() {
        def hrDepartmentInstance = HrDepartment.get(params.id)
        if (!hrDepartmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrDepartmentInstance.delete(flush: true)
            //flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), params.id])
            flash.message = "Record Successfully Deleted."
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrDepartment.label', default: 'HrDepartment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // for search option Start ----------------------------------------------------------------------------------------------------------------
    /*def search() {
        def departmentName = '%'+params.departmentName+'%'
        def hrDepartmentInstanceList = null;
        def totalCount = 0;

       hrDepartmentInstanceList = HrDepartment.findAllByDepartmentNameLike(departmentName, params)
       totalCount =  HrDepartment.countByDepartmentName(departmentName)

        render(view: "list", model: [hrDepartmentInstanceList: hrDepartmentInstanceList, hrDepartmentInstanceTotal: totalCount])
    }*/

    def search() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = HrDepartment.where
                {
            departmentName =~ '%'+params.departmentName+'%'
             //departmentName =~ params.departmentName
        }

        def hrDepartmentInstanceList = query.list(params)

        render(view: "list", model: [hrDepartmentInstanceList: hrDepartmentInstanceList, hrDepartmentInstanceTotal: query.count()])

    }
    // for search option End --------------------------------- --------------------------------------------------------------------------------

    // start Auto Department Name Search Query
    def autoDepartmentName()
    {
        def foundData = HrDepartment.withCriteria {
            ilike( 'departmentName', '%'+params.term.toString().trim()+ '%' )
            order("departmentName", "asc")
        }
        render (foundData?.departmentName as JSON)
    }
    // end Auto Department Name Search Query

}
