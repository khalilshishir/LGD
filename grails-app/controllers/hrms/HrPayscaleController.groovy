package hrms

import org.springframework.dao.DataIntegrityViolationException


class HrPayscaleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        // --- start add new lines here -------------------------------------------------------------------------------------
        //def hrPayscaleInstanceList = hrms.HrPayscale.list(params)
        //def grandTotal = new HashMap()
        //[hrPayscaleInstanceList: HrPayscale.list(params),grandTotal: grandTotal, hrPayscaleInstanceTotal: HrPayscale.count()]
        // --- end add new lines here ---------------------------------------------------------------------------------------

        [hrPayscaleInstanceList: HrPayscale.list(params), hrPayscaleInstanceTotal: HrPayscale.count()]
    }

    def create() {
        [hrPayscaleInstance: new HrPayscale(params)]
    }

    def save()
    {
       // def hrPayscaleInstance = new HrPayscale(params)


        // ---------- start add for data saved -------------------------------------------------------
        def hrPayscaleInstance = new HrPayscale()
        // bind parameters
        hrPayscaleInstance.properties['id','gradeNo','desingationIdHrDesignation'] = params
        int i=0
        while (params["hrPayscaleDetails[" + i + "].stage"] != null)
        {
            def hrPayscaleDetail = new HrPayscaleDetail()
            //hrPayscaleDetail.properties['stage'] = params["hrPayscaleDetails["+i+"].stage"] -- stop commented 04/06/2013
            hrPayscaleDetail.properties['stage'] = Integer.valueOf(params["hrPayscaleDetails["+i+"].stage"])
            hrPayscaleDetail.properties['basic'] = Float.valueOf(params["hrPayscaleDetails["+i+"].basic"])
            hrPayscaleDetail.properties['da'] = Float.valueOf(params["hrPayscaleDetails["+i+"].da"])
            hrPayscaleDetail.properties['houseRent'] = Float.valueOf(params["hrPayscaleDetails["+i+"].houseRent"])
            hrPayscaleDetail.properties['conveyanceAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].conveyanceAllow"])
            hrPayscaleDetail.properties['washingAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].washingAllow"])
            hrPayscaleDetail.properties['medicalAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].medicalAllow"])
            hrPayscaleDetail.properties['pfContribution'] = Float.valueOf(params["hrPayscaleDetails["+i+"].pfContribution"])
            hrPayscaleDetail.properties['festivalBonus'] = Float.valueOf(params["hrPayscaleDetails["+i+"].festivalBonus"])
            hrPayscaleDetail.properties['annualBonus'] = Float.valueOf(params["hrPayscaleDetails["+i+"].annualBonus"])
            hrPayscaleDetail.properties['gratuity'] = Float.valueOf(params["hrPayscaleDetails["+i+"].gratuity"])

            hrPayscaleInstance.addToHrPayscaleDetails(hrPayscaleDetail)
            i++
        }
        // ---------- end  add for data saved ---------------------------------------------------------

        hrPayscaleInstance.setProperties(gradeNo:params.gradeNo.toString().trim().toUpperCase())
        if (!hrPayscaleInstance.save(flush: true)) {
            render(view: "create", model: [hrPayscaleInstance: hrPayscaleInstance])
            return
        }
        flash.message = "Record Successfully Saved."
		//flash.message = message(code: 'default.created.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), hrPayscaleInstance.id])
        redirect(action: "show", id: hrPayscaleInstance.id)
    }

    def show() {
        def hrPayscaleInstance = HrPayscale.get(params.id)
        if (!hrPayscaleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), params.id])
            redirect(action: "list")
            return
        }

        [hrPayscaleInstance: hrPayscaleInstance]
    }

    def edit() {
        def hrPayscaleInstance = HrPayscale.get(params.id)
        if (!hrPayscaleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), params.id])
            redirect(action: "list")
            return
        }

        [hrPayscaleInstance: hrPayscaleInstance]
    }

    def update() {
        def hrPayscaleInstance = HrPayscale.get(params.id)
        if (!hrPayscaleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrPayscaleInstance.version > version) {
                hrPayscaleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hrPayscale.label', default: 'HrPayscale')] as Object[],
                          "Another user has updated this HrPayscale while you were editing")
                render(view: "edit", model: [hrPayscaleInstance: hrPayscaleInstance])
                return
            }
        }

        // start add update for records ---------------------------------------------------------------------------
        // bind parameters ----------------------------------------------------------------------
        hrPayscaleInstance.properties['id','gradeNo','desingationIdHrDesignation'] = params

        int i=0
        while (params["hrPayscaleDetails[" + i + "].stage"] != null)
        {
            def hrPayscaleDetail = new HrPayscaleDetail()
            if(params["hrPayscaleDetails[" + i + "].deleted"]=="true" && params["hrPayscaleDetails[" + i + "].new"]=="false")
            {
                hrPayscaleDetail = hrPayscaleDetail.get(Long.valueOf(params["hrPayscaleDetails[" + i + "].id"]))
                hrPayscaleInstance.removeFromHrPayscaleDetails(hrPayscaleDetail)
                hrPayscaleDetail.delete()
                i++
                continue
            }
            else if(params["hrPayscaleDetails[" + i + "].deleted"]=="true" && params["hrPayscaleDetails[" + i + "].new"]=="true")
            {
                i++
                continue
            }
            else if(params["hrPayscaleDetails[" + i + "].deleted"]=="false" && params["hrPayscaleDetails[" + i + "].new"]=="true")
            {
                hrPayscaleDetail = new HrPayscaleDetail()
                //hrPayscaleDetail.properties['stage'] = params["hrPayscaleDetails["+i+"].stage"] -- stop commented 04/06/2013
                hrPayscaleDetail.properties['stage'] = Integer.valueOf(params["hrPayscaleDetails["+i+"].stage"])
                hrPayscaleDetail.properties['basic'] = Float.valueOf(params["hrPayscaleDetails["+i+"].basic"])
                hrPayscaleDetail.properties['da'] = Float.valueOf(params["hrPayscaleDetails["+i+"].da"])
                hrPayscaleDetail.properties['houseRent'] = Float.valueOf(params["hrPayscaleDetails["+i+"].houseRent"])
                hrPayscaleDetail.properties['conveyanceAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].conveyanceAllow"])
                hrPayscaleDetail.properties['washingAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].washingAllow"])
                hrPayscaleDetail.properties['medicalAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].medicalAllow"])
                hrPayscaleDetail.properties['pfContribution'] = Float.valueOf(params["hrPayscaleDetails["+i+"].pfContribution"])
                hrPayscaleDetail.properties['festivalBonus'] = Float.valueOf(params["hrPayscaleDetails["+i+"].festivalBonus"])
                hrPayscaleDetail.properties['annualBonus'] = Float.valueOf(params["hrPayscaleDetails["+i+"].annualBonus"])
                hrPayscaleDetail.properties['gratuity'] = Float.valueOf(params["hrPayscaleDetails["+i+"].gratuity"])
            }
            else
            {
            hrPayscaleDetail=hrPayscaleDetail.get(Long.valueOf(params["hrPayscaleDetails[" + i + "].id"]))
            }
            //hrPayscaleInstance.properties = params
            hrPayscaleInstance.properties['id','gradeNo','desingationIdHrDesignation'] = params
            //hrPayscaleDetail.properties['stage'] = params["hrPayscaleDetails["+i+"].stage"] -- stop commented 04/06/2013
            hrPayscaleDetail.properties['stage'] = Integer.valueOf(params["hrPayscaleDetails["+i+"].stage"])
            hrPayscaleDetail.properties['basic'] = Float.valueOf(params["hrPayscaleDetails["+i+"].basic"])
            hrPayscaleDetail.properties['da'] = Float.valueOf(params["hrPayscaleDetails["+i+"].da"])
            hrPayscaleDetail.properties['houseRent'] = Float.valueOf(params["hrPayscaleDetails["+i+"].houseRent"])
            hrPayscaleDetail.properties['conveyanceAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].conveyanceAllow"])
            hrPayscaleDetail.properties['washingAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].washingAllow"])
            hrPayscaleDetail.properties['medicalAllow'] = Float.valueOf(params["hrPayscaleDetails["+i+"].medicalAllow"])
            hrPayscaleDetail.properties['pfContribution'] = Float.valueOf(params["hrPayscaleDetails["+i+"].pfContribution"])
            hrPayscaleDetail.properties['festivalBonus'] = Float.valueOf(params["hrPayscaleDetails["+i+"].festivalBonus"])
            hrPayscaleDetail.properties['annualBonus'] = Float.valueOf(params["hrPayscaleDetails["+i+"].annualBonus"])
            hrPayscaleDetail.properties['gratuity'] = Float.valueOf(params["hrPayscaleDetails["+i+"].gratuity"])

            hrPayscaleInstance.addToHrPayscaleDetails(hrPayscaleDetail)
            i++
        }
        // end add update for records -----------------------------------------------------------------------------

        hrPayscaleInstance.setProperties(gradeNo:params.gradeNo.toString().trim().toUpperCase())
        if (!hrPayscaleInstance.save(flush: true)) {
            render(view: "edit", model: [hrPayscaleInstance: hrPayscaleInstance])
            return
        }
        flash.message = "Record Successfully Updated."
		//flash.message = message(code: 'default.updated.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), hrPayscaleInstance.id])
        redirect(action: "show", id: hrPayscaleInstance.id)
    }


    def delete() {
        def hrPayscaleInstance = HrPayscale.get(params.id)
        if (!hrPayscaleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrPayscaleInstance.delete(flush: true)
            flash.message = "Record Successfully Deleted."
			//flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e)
        {
			//flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrPayscale.label', default: 'HrPayscale'), params.id])
            flash.message = "Master Data Can't be Deleted!... Child Records Found."
            redirect(action: "show", id: params.id)
        }
    }

    // for search option Start ----------------------------------------------------------------------------------------------------------------
    def search()
    {
        /*def hrPayscale = HrPayscale.findById(Long.valueOf(params.hrPayscale))
        def hrPayscaleInstanceList = null;
        def totalCount = 0;
        hrPayscaleInstanceList =HrPayscaleDetail.findAllByPayscaleIdHrPayscale(hrPayscale)
        totalCount =  hrPayscaleInstanceList.size()*/
        //render(view: "list", model: [hrPayscaleInstanceList: hrPayscaleInstanceList, hrPayscaleInstanceTotal: totalCount])
        redirect(action: "show", id: params.hrPayscale)
    }
    // for search option End --------------------------------- --------------------------------------------------------------------------------

}
