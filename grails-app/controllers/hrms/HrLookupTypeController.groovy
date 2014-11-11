package hrms

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HrLookupTypeController {

    HrLookupType hrLookupType
    List<HrLookupType> hrLookupTypeList

    HrLookup hrLookup
    List<HrLookup> hrLookupList

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        params.sort = params.sort ?: "lookupType"
        params.order = params.order ?: "asc"
        [hrLookupTypeInstanceList: HrLookupType.list(params), hrLookupTypeInstanceTotal: HrLookupType.count()]
    }

    def create() {
        [hrLookupTypeInstance: new HrLookupType(params)]
    }

    def save() {

        // ---------- start add for data saved -------------------------------------------------------
        hrLookupType  =new HrLookupType()
        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        hrLookupType.properties['id','lookupType','isActive'] = params
        hrLookupType = hrLookupType.save(flush: true)

        if (hrLookupType != null) {
            int i=0
            while (params["hrlookups[" + i + "].lookupValue"] != null)
            {
                hrLookup = new HrLookup()
                if(params["hrlookups[" + i + "].deleted"]=="true" && params["hrlookups[" + i + "].new"]=="false")
                {
                    /* hrLookup = hrLookup.get(Long.valueOf(params["hrlookups[" + i + "].id"]))
                    hrLookupType.removeFromHrlookups( hrLookup)
                    hrLookup.delete()
                    i++
                    continue*/

                    hrLookup = (HrLookup) HrLookup.findById(Long.valueOf(params["hrLookups[" + i + "].id"]))
                    hrLookup.delete()
                    hrLookupType.hrlookups.remove(hrLookup)
                    i++
                    continue

                }
                else if(params["hrlookups[" + i + "].deleted"]=="true" && params["hrlookups[" + i + "].new"]=="true")
                {
                    i++
                    continue
                }
                else if(params["hrlookups[" + i + "].deleted"]=="false" && params["hrlookups[" + i + "].new"]=="true")
                {
                    hrLookup = new HrLookup()
                    println("hrlookups[" + i + "].isActive :"+params["hrlookups[" + i + "].isActive"])
                    Boolean isActive=Boolean.valueOf(params["hrlookups[" + i + "].isActive"].toString().trim())
                    hrLookup.properties['isActive'] = isActive
                    hrLookup.properties['lookupValue'] =params["hrlookups[" + i + "].lookupValue"]
                }
                else
                {
                    hrLookup=hrLookup.get(Long.valueOf(params["hrlookups[" + i + "].id"]))
                }

                println("hrlookups[" + i + "].isActive :"+params["hrlookups[" + i + "].isActive"])
                Boolean isActive=Boolean.valueOf(params["hrlookups[" + i + "].isActive"].toString().trim())
                hrLookup.properties['isActive'] = isActive
                hrLookup.properties['lookupValue'] =params["hrlookups[" + i + "].lookupValue"]

                hrLookup.setHrLookupTypeIdLookupType(hrLookupType)

                hrLookup.save()

                i++
            }
        }
        // end add update for records -----------------------------------------------------------------------------
        // For the List Value
        hrLookupList = HrLookup.findAllByHrLookupTypeIdLookupType(hrLookupType)

        if (hrLookupType == null) {
            render(view: "edit", model: [hrLookupTypeInstance: hrLookupType, hrLookupList: hrLookupList])
            return
        }

        flash.message = "Record Successfully Saved."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrLookupType.label', default: 'hrLookupType'), hrLookupType.id])
        redirect(action: "show", id: hrLookupType.id, hrLookupList: hrLookupList)
    }

    def show() {
        def hrLookupTypeInstance = HrLookupType.get(params.id)
        if (!hrLookupTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookupType.label', default: 'HrLookupType'), params.id])
            redirect(action: "list")
            return
        }

        // For details List Value
        def hrLookupType = HrLookupType.findById(params.id)
        List<HrLookup> hrLookupList = HrLookup.findAllByHrLookupTypeIdLookupType(hrLookupType)

        [hrLookupTypeInstance: hrLookupTypeInstance,hrLookupList:hrLookupList]
    }

    def edit() {
        def hrLookupTypeInstance = HrLookupType.get(params.id)
        if (!hrLookupTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookupType.label', default: 'HrLookupType'), params.id])
            redirect(action: "list")
            return
        }

        // For details List Value
        def hrLookupType = HrLookupType.findById(params.id)
        List<HrLookup> hrLookupList = HrLookup.findAllByHrLookupTypeIdLookupType(hrLookupType)

        [hrLookupTypeInstance: hrLookupTypeInstance,hrLookupList:hrLookupList]
    }

    def update() {

         hrLookupType  =new HrLookupType()
         hrLookupType = HrLookupType.get(params.id)
        if (!hrLookupType) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookupType.label', default: 'HrLookupType'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrLookupType.version > version) {
                hrLookupType.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrLookupType.label', default: 'HrLookupType')] as Object[],
                        "Another user has updated this HrLookupType while you were editing")
                render(view: "edit", model: [hrLookupTypeInstance: hrLookupType])
                return
            }
        }

        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        hrLookupType.properties['id','lookupType','lookupValue','isActive'] = params
        hrLookupType = hrLookupType.save(flush: true)

        if (hrLookupType != null) {
            int i=0
            while (params["hrlookups[" + i + "].lookupValue"] != null)
            {
                hrLookup = new HrLookup()
                if(params["hrlookups[" + i + "].deleted"]=="true" && params["hrlookups[" + i + "].new"]=="false")
                {
                    hrLookup = (HrLookup) HrLookup.findById(Long.valueOf(params["hrlookups[" + i + "].id"]))
                    hrLookup.delete()
                    hrLookupType.hrlookups.remove(hrLookup)
                    i++
                    continue

                }
                else if(params["hrlookups[" + i + "].deleted"]=="true" && params["hrlookups[" + i + "].new"]=="true")
                {
                    i++
                    continue
                }
                else if(params["hrlookups[" + i + "].deleted"]=="false" && params["hrlookups[" + i + "].new"]=="true")
                {
                    hrLookup = new HrLookup()
                    println("hrlookups[" + i + "].isActive :"+params["hrlookups[" + i + "].isActive"])
                    Boolean isActive=Boolean.valueOf(params["hrlookups[" + i + "].isActive"].toString().trim())
                    hrLookup.properties['isActive'] = isActive
                    hrLookup.properties['lookupValue'] =params["hrlookups[" + i + "].lookupValue"]
                }
                else
                {
                    hrLookup=hrLookup.get(Long.valueOf(params["hrlookups[" + i + "].id"]))
                }

                println("hrlookups[" + i + "].isActive :"+params["hrlookups[" + i + "].isActive"])
                Boolean isActive=Boolean.valueOf(params["hrlookups[" + i + "].isActive"].toString().trim())
                hrLookup.properties['isActive'] = isActive
                hrLookup.properties['lookupValue'] =params["hrlookups[" + i + "].lookupValue"]

                hrLookup.setHrLookupTypeIdLookupType(hrLookupType)

                hrLookup.save()

                i++
            }
        }
        // end add update for records -----------------------------------------------------------------------------
        // For the List Value
        hrLookupList = HrLookup.findAllByHrLookupTypeIdLookupType(hrLookupType)

        if (hrLookupType == null) {
            render(view: "edit", model: [hrLookupTypeInstance: hrLookupType, hrLookupList: hrLookupList])
            return
        }

        flash.message = "Record Successfully Updated."
        //flash.message = message(code: 'default.updated.message', args: [message(code: 'hrLookupType.label', default: 'hrLookupType'), hrLookupType.id])
        redirect(action: "show", id: hrLookupType.id, hrLookupList: hrLookupList)

    }

    def delete() {
        def hrLookupTypeInstance = HrLookupType.get(params.id)
        if (!hrLookupTypeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrLookupType.label', default: 'HrLookupType'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrLookupTypeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrLookupType.label', default: 'HrLookupType'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrLookupType.label', default: 'HrLookupType'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // for search option Start ----------------------------------------------------------------------------------------------------------------
    /*def search()
    {
        def lookupType = '%'+params.lookupType+'%'
        def hrLookupTypeInstanceList = null;
        def totalCount = 0;

        hrLookupTypeInstanceList = HrLookupType.findAllByLookupTypeLike(lookupType, params)
        totalCount =  HrLookupType.countByLookupType(lookupType)

        render(view: "list", model: [hrLookupTypeInstanceList: hrLookupTypeInstanceList, hrLookupTypeInstanceTotal: totalCount])
    }*/

    def search() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = HrLookupType.where{
                    lookupType =~ '%'+params.lookupType+'%'
                }

        def hrLookupTypeInstanceList = query.list(params)

        render(view: "list", model: [hrLookupTypeInstanceList: hrLookupTypeInstanceList, hrLookupTypeInstanceTotal: query.count()])
    }

    // for search option End -----------------------------------------------------------------------------------------------------------------


    def getLookupType() {

        def foundData = HrLookupType.withCriteria {
            ilike('lookupType', '%' + params.term.toString().trim() + '%')
            order("lookupType", "asc")
        }

        ArrayList<HrLookupType> typ = new ArrayList<HrLookupType>(foundData)
        ArrayList<String> info = new ArrayList<String>()

        for (i in typ) {
            info.add(i?.lookupType)
        }

        render(info as JSON)
    }
}
