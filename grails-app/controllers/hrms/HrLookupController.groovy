package hrms

import accounts.Util

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrLookupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort = params.sort ?: "lookupValue"
        params.order = params.order ?: "asc"
        [hrLookupInstanceList: HrLookup.list(params), hrLookupInstanceTotal: HrLookup.count()]
    }

    def create() {
        [hrLookupInstance: new HrLookup(params)]
    }

    def save() {
        def hrLookupInstance = new HrLookup(params)
        hrLookupInstance.setProperties(lookupValue:params.lookupValue.toString().trim().toUpperCase())
        if (!hrLookupInstance.save(flush: true)) {
            render(view: "create", model: [hrLookupInstance: hrLookupInstance])
            return
        }
        flash.message = "Record Successfully Saved."
        //flash.message = message(code: 'default.created.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), hrLookupInstance.id])
        redirect(action: "show", id: hrLookupInstance.id)
    }

    def show() {
        def hrLookupInstance = HrLookup.get(params.id)
        if (!hrLookupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), params.id])
            redirect(action: "list")
            return
        }
        [hrLookupInstance: hrLookupInstance]
    }

    def edit() {
        def hrLookupInstance = HrLookup.get(params.id)
        if (!hrLookupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), params.id])
            redirect(action: "list")
            return
        }

        [hrLookupInstance: hrLookupInstance]
    }

    def update() {
        def hrLookupInstance = HrLookup.get(params.id)
        if (!hrLookupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrLookupInstance.version > version) {
                hrLookupInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrLookup.label', default: 'HrLookup')] as Object[],
                        "Another user has updated this HrLookup while you were editing")
                render(view: "edit", model: [hrLookupInstance: hrLookupInstance])
                return
            }
        }

        hrLookupInstance.properties = params
        hrLookupInstance.setProperties(lookupValue:params.lookupValue.toString().trim().toUpperCase())
        if (!hrLookupInstance.save(flush: true)) {
            render(view: "edit", model: [hrLookupInstance: hrLookupInstance])
            return
        }
        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), hrLookupInstance.id])
        redirect(action: "show", id: hrLookupInstance.id)
    }

    def delete() {
        def hrLookupInstance = HrLookup.get(params.id)
        if (!hrLookupInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrLookupInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrLookup.label', default: 'HrLookup'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // for search option Start ----------------------------------------------------------------------------------------------------------------

    def searchBackup() {

        //def hrLookupInstance = new HrLookup()
       // hrLookupInstance.properties['hrLookupTypeIdLookupType'] = params
       // params.max = Math.min(params.max ? params.int('max') : 3, 100)
        def hrLookupType = HrLookupType.findById(Long.valueOf(params.hrLookupType))
        def hrLookupInstanceList = null;
        def totalCount = 0;
        hrLookupInstanceList = HrLookup.findAllByHrLookupTypeIdLookupType(hrLookupType)
        totalCount =  hrLookupInstanceList.size()

        render(view: "list", model: [hrLookupInstanceList: hrLookupInstanceList, hrLookupInstanceTotal: totalCount])
    }


    def search() {

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def c = HrLookup.createCriteria()
        def allValueList = c.list {
            if (params?.lookupValue) {
                ilike("lookupValue", '%' + params.lookupValue + '%')
            }
            if (params?.hrLookupType) {
                def hrLookupType = HrLookupType.findById(Long.valueOf(params.hrLookupType))
                eq("hrLookupTypeIdLookupType",hrLookupType)
            }

            // maxResults(params.max)
            order("lookupValue", "desc")
            order("hrLookupTypeIdLookupType", "desc")
        }
        println(" allValueList.size() :"+ allValueList.size())
        if (!allValueList) {
            flash.message = "Provided Information is Not Matched..."
            redirect(action: "list")
        }
        else {

            List<Object> searchFoundData = new ArrayList<Object>();
            Integer a
            if (params.offset != null) {
                a = Integer.parseInt(params.offset.toString())
            }
            else {
                a = 0
            }
            Integer b = Integer.parseInt(params.max.toString())

            for (int i = a; i < (a + b); i++) {

                if (i < allValueList.size()) {
                    if (!allValueList.get(i).equals(null)) {
                        searchFoundData.add(allValueList.get(i))
                    }
                }
            }

            def  searchValueList = searchFoundData

            render(view: "list", model: [hrLookupInstanceList: searchValueList, hrLookupInstanceTotal: allValueList.size()])
        }
    }

    // for search option End --------------------------------- --------------------------------------------------------------------------------

    def getLookupValue() {

        def foundData = HrLookup.withCriteria {
            ilike('lookupValue', '%' + params.term.toString().trim() + '%')
            order("lookupValue", "asc")
        }
        ArrayList<HrLookup> typ = new ArrayList<HrLookup>(foundData)
        ArrayList<String> info = new ArrayList<String>()

        for (i in typ) {
            info.add(i?.lookupValue)
        }

        render(info as JSON)
    }


}
