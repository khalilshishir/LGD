package accounts

import org.springframework.dao.DataIntegrityViolationException

class AfmBankInfoController {

    AfmBankInfo afmBankInfo
    List<AfmBankInfo> afmBankInfoList

    AfmBankBranch afmBankBranch
    List<AfmBankBranch> afmBankBranchList

    AfmChartOfAccounts afmChartOfAccounts

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [afmBankInfoInstanceList: AfmBankInfo.list(params), afmBankInfoInstanceTotal: AfmBankInfo.count()]
    }

    def create() {
        [afmBankInfoInstance: new AfmBankInfo(params)]
    }

    def save() {

        /*Step-1 : Saving Master table data*/
        afmBankInfo = new AfmBankInfo()

        afmBankInfo.properties["id","bankName"] = params
        afmBankInfo=afmBankInfo.save(flush: true)

        /*Step-2 : Saving Master Details table data*/
        if (afmBankInfo!=null) {

            int i = 0
            while (params["afmBankBranch[" + i + "].branchName"] != null) {

                afmBankBranch = new AfmBankBranch()

                afmBankBranch.properties['branchName']=params["afmBankBranch[" + i + "].branchName"]

                afmBankBranch.properties['address']=params["afmBankBranch[" + i + "].address"]

                afmBankBranch.properties['accountType']=params["afmBankBranch[" + i + "].accountType"]

                afmBankBranch.properties['accountNo']=params["afmBankBranch[" + i + "].accountNo"]

                String accountHeadName=params["afmBankBranch[" + i + "].accountHeadName"]
                
                if(!accountHeadName.equals("")){
                    afmChartOfAccounts=new AfmChartOfAccounts()
                    afmChartOfAccounts=AfmChartOfAccounts.findByAccountHeadName(accountHeadName.trim())
                    if (afmChartOfAccounts!=null){
                        afmBankBranch.setAfmChartOfAccounts(afmChartOfAccounts)
                    }
                }

                afmBankBranch.setAfmBankInfo(afmBankInfo)

                afmBankBranch.save(flush: true)

                i++
            }

        }
        /*Step-3 : Checking whether Master table data insert into database or not*/

        if (afmBankInfo==null) {
            render(view: "create", model: [afmBankInfoInstance: afmBankInfo])
            flash.message = message(code: 'default.created.fail.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo')])
            return
        }
        // For the List Value
        afmBankBranchList= AfmBankBranch.findAllByAfmBankInfo(afmBankInfo)

        flash.message = message(code: 'default.created.message', args: [message(code: 'afmBankInfo.label', default: 'afmBankInfo'), afmBankInfo.id])
        redirect(action: "show", id: afmBankInfo.id,afmBankBranchList:afmBankBranchList)
    }

    def show() {
        def afmBankInfoInstance = AfmBankInfo.get(params.id)
        if (!afmBankInfoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), params.id])
            redirect(action: "list")
            return
        }

        // For details List (afmBankBranch List) Value
        def afmBankInfo =AfmBankInfo.findById(params.id)
        List<AfmBankBranch> afmBankBranchList = AfmBankBranch.findAllByAfmBankInfo(afmBankInfo)

        [afmBankInfoInstance: afmBankInfoInstance,afmBankBranchList:afmBankBranchList]
    }

    def edit() {
        def afmBankInfoInstance = AfmBankInfo.get(params.id)
        if (!afmBankInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), params.id])
            redirect(action: "list")
            return
        }

        // For details List (afmBankBranch List) Value
        def afmBankInfo =AfmBankInfo.findById(params.id)
        List<AfmBankBranch> afmBankBranchList = AfmBankBranch.findAllByAfmBankInfo(afmBankInfo)

        [afmBankInfoInstance: afmBankInfoInstance,afmBankBranchList:afmBankBranchList]
    }

    def update() {

        /*Step-1 : Saving Master table data*/
        afmBankInfo = new AfmBankInfo()
        afmBankInfo = AfmBankInfo.get(params.id)
        if (!afmBankInfo) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), params.id])
            redirect(action: "list")
            return
        }

        afmBankInfo.properties["id","bankName"] = params
        afmBankInfo=afmBankInfo.save(flush: true)

        /*Step-2 : Saving Master Details table data*/
        if (afmBankInfo!=null) {

            int i = 0
            while (params["afmBankBranch[" + i + "].branchName"] != null) {

                afmBankBranch = new AfmBankBranch()

                if (params["afmBankBranch[" + i + "].deleted"] == "true" && params["afmBankBranch[" + i + "].new"] == "false") {

                    afmBankBranch = (AfmBankBranch)AfmBankBranch.findById(Long.valueOf(params["afmBankBranch[" + i + "].id"]))
                    afmBankBranch.delete()
                    afmBankInfo.afmBankBranch.remove(afmBankBranch)
                    i++
                    continue
                }
                else if (params["afmBankBranch[" + i + "].deleted"] == "true" && params["afmBankBranch[" + i + "].new"] == "true") {
                    i++
                    continue
                }
                else if (params["afmBankBranch[" + i + "].deleted"] == "false" && params["afmBankBranch[" + i + "].new"] == "true") {

                    afmBankBranch = new AfmBankBranch()
                }
                else {
//                    afmBankBranch=new AfmBankBranch()
                    println("params[\"afmBankBranch[\" + i + \"].id\"]  "+ params["afmBankBranch[" + i + "].id"])
                    afmBankBranch = AfmBankBranch.get(Long.valueOf(params["afmBankBranch[" + i + "].id"]))

                }


                afmBankBranch.properties['branchName']=params["afmBankBranch[" + i + "].branchName"]
                println("params[\"afmBankBranch[\" + i + \"].branchName\"]  "+ params["afmBankBranch[" + i + "].branchName"])
                afmBankBranch.properties['address']=params["afmBankBranch[" + i + "].address"]
                println("params[\"afmBankBranch[\" + i + \"].address\"]  "+ params["afmBankBranch[" + i + "].address"])
                afmBankBranch.properties['accountType']=params["afmBankBranch[" + i + "].accountType"]
                println("params[\"afmBankBranch[\" + i + \"].accountType\"]  "+ params["afmBankBranch[" + i + "].accountType"])
                afmBankBranch.properties['accountNo']=params["afmBankBranch[" + i + "].accountNo"]
                println("params[\"afmBankBranch[\" + i + \"].accountNo\"]  "+ params["afmBankBranch[" + i + "].accountNo"])

                String accountHeadName=params["afmBankBranch[" + i + "].accountHeadName"]
                println("accountHeadName No-"+i+" : "+accountHeadName)

                if(!accountHeadName.equals("")){
                    afmChartOfAccounts=new AfmChartOfAccounts()
                    afmChartOfAccounts=AfmChartOfAccounts.findByAccountHeadName(accountHeadName.trim())
                    if (afmChartOfAccounts!=null){
                        afmBankBranch.setAfmChartOfAccounts(afmChartOfAccounts)
                    }
                }

                afmBankBranch.setAfmBankInfo(afmBankInfo)

                afmBankBranch.save(flush: true)

                i++
            }

        }

        // For the List Value
        afmBankBranchList= AfmBankBranch.findAllByAfmBankInfo(afmBankInfo)

        if (afmBankInfo==null) {
            render(view: "edit", model: [afmBankInfoInstance: afmBankInfo,afmBankBranchList:afmBankBranchList])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'afmBankInfo.label', default: ' '), afmBankInfo.bankName])
        redirect(action: "show", id: afmBankInfo.id,afmBankBranchList:afmBankBranchList)




/*

        afmBankInfoInstance.properties = params

        if (!afmBankInfoInstance.save(flush: true)) {
            render(view: "edit", model: [afmBankInfoInstance: afmBankInfoInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), afmBankInfoInstance.id])
        redirect(action: "show", id: afmBankInfoInstance.id)*/
    }

    def delete() {
        def afmBankInfoInstance = AfmBankInfo.get(params.id)
        if (!afmBankInfoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), params.id])
            redirect(action: "list")
            return
        }

        try {
            afmBankInfoInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'afmBankInfo.label', default: 'AfmBankInfo'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
