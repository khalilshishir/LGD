package accounts

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import inventory.WSReturn
import groovy.sql.Sql

class AfmChartOfAccountsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def dataSource
    List parentHeadOfAccountsList
    List accountHeadNameList
    List sqlValueList

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [afmChartOfAccountsInstanceList: AfmChartOfAccounts.list(params), afmChartOfAccountsInstanceTotal: AfmChartOfAccounts.count()]
    }

    def create() {

        // For Parent Account Head comboo
        /*List<AfmChartOfAccounts> parentHeadOfAccountsList1 = AfmChartOfAccounts.findAllByIsSubsidiary('Y')*/
         Sql sql = new Sql(dataSource)
         parentHeadOfAccountsList = sql.rows("SELECT A.ACCOUNT_HEAD_ID,A.ACCOUNT_HEAD_NAME FROM AFM_CHART_OF_ACCOUNTS A WHERE IS_SUBSIDIARY=?  AND A.ACCOUNT_HEAD_NAME IS NOT NULL ORDER BY A.ACCOUNT_HEAD_NAME",'Y')
         sql.close()
        [afmChartOfAccountsInstance: new AfmChartOfAccounts(params),parentHeadOfAccountsList:parentHeadOfAccountsList]
    }
   def report() {

        [afmChartOfAccountsInstance: new AfmChartOfAccounts(params)]
    }

    def save() {
        params.accountHeadName =params.accountHeadName.toString().trim()
        def afmChartOfAccountsInstance = new AfmChartOfAccounts(params)
        if (!afmChartOfAccountsInstance.save(flush: true)) {
            // For Parent Account Head comboo
          //  List<AfmChartOfAccounts> parentHeadOfAccountsList = AfmChartOfAccounts.findAllByIsSubsidiary('Y')
            Sql sql = new Sql(dataSource)
            parentHeadOfAccountsList = sql.rows("SELECT A.ACCOUNT_HEAD_ID,A.ACCOUNT_HEAD_NAME FROM AFM_CHART_OF_ACCOUNTS A WHERE IS_SUBSIDIARY=?  AND A.ACCOUNT_HEAD_NAME IS NOT NULL ORDER BY A.ACCOUNT_HEAD_NAME",'Y')  //Y means it has child

            flash.message = message(code: 'default.created.fail.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), afmChartOfAccountsInstance.id])

            sql.close()
            render(view: "create", model: [parentHeadOfAccountsList: parentHeadOfAccountsList,parentHeadOfAccountsList:parentHeadOfAccountsList])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), afmChartOfAccountsInstance.id])
       /* redirect(action: "show", id: afmChartOfAccountsInstance.id)*/
        redirect(action: "create")
    }

    def show() {
        def afmChartOfAccountsInstance = AfmChartOfAccounts.get(params.id)
        if (!afmChartOfAccountsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), params.id])
            redirect(action: "list")
            return
        }

        [afmChartOfAccountsInstance: afmChartOfAccountsInstance]
    }

    def edit() {
        def afmChartOfAccountsInstance = AfmChartOfAccounts.get(params.id)
        if (!afmChartOfAccountsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), params.id])
            redirect(action: "list")
            return
        }
        // For Parent Account Head comboo
       // List<AfmChartOfAccounts> parentHeadOfAccountsList = AfmChartOfAccounts.findAllByIsSubsidiary('Y')
        Sql sql = new Sql(dataSource)
        parentHeadOfAccountsList = sql.rows("SELECT A.ACCOUNT_HEAD_ID,A.ACCOUNT_HEAD_NAME FROM AFM_CHART_OF_ACCOUNTS A WHERE IS_SUBSIDIARY=?  AND A.ACCOUNT_HEAD_NAME IS NOT NULL ORDER BY A.ACCOUNT_HEAD_NAME",'Y')

        sql.close()
        [afmChartOfAccountsInstance: afmChartOfAccountsInstance,parentHeadOfAccountsList:parentHeadOfAccountsList]
    }

    def update() {
        def afmChartOfAccountsInstance = AfmChartOfAccounts.get(params.id)
        if (!afmChartOfAccountsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), params.id])
            redirect(action: "create")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (afmChartOfAccountsInstance.version > version) {
                afmChartOfAccountsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts')] as Object[],
                        "Another user has updated this AfmChartOfAccounts while you were editing")
                render(view: "edit", model: [afmChartOfAccountsInstance: afmChartOfAccountsInstance])
                return
            }
        }

      //  params.parentAccountHead =null
        params.accountHeadName =params.accountHeadName.toString().trim()
        afmChartOfAccountsInstance.properties = params

        if (!afmChartOfAccountsInstance.save(flush: true)) {
            render(view: "edit", model: [afmChartOfAccountsInstance: afmChartOfAccountsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), afmChartOfAccountsInstance.id])
        redirect(action: "create", id: afmChartOfAccountsInstance.id)
    }

    def delete() {
        def afmChartOfAccountsInstance = AfmChartOfAccounts.get(params.id)
        if (!afmChartOfAccountsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), params.id])
            redirect(action: "create")
            return
        }

        try {
            afmChartOfAccountsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), params.id])
            redirect(action: "create")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'afmChartOfAccounts.label', default: 'AfmChartOfAccounts'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def getAccHeadNameForBankTrn(){
        def info
        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountCode(params.accountCode)
            if (afmChartOfAccounts!=null){
                def accountHeadName = afmChartOfAccounts.accountHeadName
                def id = afmChartOfAccounts.id
                System.out.println(id)
                info = id+":"+accountHeadName
            }
            render new WSReturn(100, info, null) as JSON
    }
    def getAccHeadName(){
        def info
        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountCode(params.accountCode)
            if (afmChartOfAccounts!=null){
                def accountHeadName = afmChartOfAccounts.accountHeadName
                def id = afmChartOfAccounts.id
                System.out.println(id)
                info = id+":"+accountHeadName
            }
            render new WSReturn(100, info, null) as JSON
   }

    def getLedgerCode(){
        def info
        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountCode(params.ledgerCode)
            if (afmChartOfAccounts!=null){
                def accountHeadName = afmChartOfAccounts.accountHeadName
                def id = afmChartOfAccounts.id
                System.out.println(id)
                info = id+":"+accountHeadName
            }
            render new WSReturn(100, info, null) as JSON
   }



    def getSubTreeData(){
        def finalNode = new ArrayList<SelfReference>()
        AfmChartOfAccounts.executeQuery("from AfmChartOfAccounts a where a.parentAccountHead=null").sort {it.accountCode}.each {a ->
//            AclForm.executeQuery("from AclForm a where a.parentIdAclForm=null").sort {it.sortBy}.each {a ->

            finalNode.add(getChildNodes(a, new SelfReference()))
        }
        def data = finalNode
        render data as JSON
    }

    def getChildNodes(AfmChartOfAccounts afmChartOfAccounts, SelfReference pNode) {

        pNode.setText("<a href='javascript:void(0);' onclick=\"goTo('"+afmChartOfAccounts.id +"');\""+">" +afmChartOfAccounts.accountHeadName + "</a>")
        pNode.setId(afmChartOfAccounts.id)
        pNode.setMenu("peer")
        pNode.setExpanded(true)
        pNode.setClasses("icon-userActivity")
        afmChartOfAccounts.afmChartOfAccs.sort {it.accountCode}.each {child ->
            def childNode = new SelfReference()
            childNode.setText("<a href='javascript:void(0);' onclick=\"goTo('"+child.id +"');\""+">" + child.accountHeadName + "</a>")
            childNode.setId(child.id)
            if (child.parentAccountHead == null) {
                childNode.setOwner("Chart of Accounts")
            }
            else {
                childNode.setOwner(child.parentAccountHead.accountHeadName)
            }
            childNode.setMenu("peer")
            pNode.children.add(childNode)

            if (child.afmChartOfAccs.size() > 0) {
                getChildNodes(child, childNode)
            }
        }
        // nodes.add  (pNode)

        return pNode
    }


    def autoAccountCode() {   // It is for a field which data type is Long/Integer

        // checking the list status  and adding data to that list
        // checking the provided params status whether it is empty or not
        // if not empty then compare with the list value
        // if empty then just provide the list value without comparing the value.

        List accountCodeList=new ArrayList()
        List accountCodeList2=new ArrayList()

        if (accountCodeList.size()<1){

            def foundData = AfmChartOfAccounts.withCriteria {
                //  eq('accountCode', params.term )
                like( 'isSubsidiary', 'N' )       //N means it has no child
            }
            println("foundData.size() :"+foundData.size())
            println("foundData.size() :"+foundData?.accountCode)

            for (int i=0;i<foundData.size();i++){

                accountCodeList.add(""+foundData["accountCode"].get(i).toString()+"")

            }
          //  println("accountCodeList :"+accountCodeList)
        }


        Iterator it=accountCodeList.iterator();
        String proVal=params.term
        if (!proVal.equals("")){
            while(it.hasNext())
            {
                String value=(String)it.next();
                proVal=proVal.trim()
                String Str = new String(value);
                String SubStr = new String(proVal);
                // int rslt=Str.startsWith(SubStr)
                println("value :"+Str+" proVal "+SubStr+" Str.startsWith(SubStr) "+Str.startsWith(SubStr))

                if (Str.startsWith(SubStr)){
                    accountCodeList2.add(Str)
                   // System.out.println("accountCodeList2 Value1 :"+accountCodeList2);
                }else{
                   // System.out.println("accountCodeList Value2 :"+accountCodeList);
                }

            }
        }else{
            List accountCodeList3=new ArrayList()
            render (accountCodeList3 as JSON)
        }
        if (accountCodeList2.size()>0){
            render (accountCodeList2 as JSON)
        }else{
            render (accountCodeList as JSON)
        }

    }

    def autoAllAccountCode() {   // It is for a field which data type is Long/Integer

        // checking the list status  and adding data to that list
        // checking the provided params status whether it is empty or not
        // if not empty then compare with the list value
        // if empty then just provide the list value without comparing the value.

        List accountCodeList=new ArrayList()
        List accountCodeList2=new ArrayList()

        if (accountCodeList.size()<1){

            def foundData = AfmChartOfAccounts.withCriteria {
                //  eq('accountCode', params.term )
                isNotNull( 'accountCode')
            }
            println("foundData.size() :"+foundData.size())
            println("foundData.size() :"+foundData?.accountCode)

            for (int i=0;i<foundData.size();i++){

                accountCodeList.add(""+foundData["accountCode"].get(i).toString()+"")

            }
         //   println("accountCodeList :"+accountCodeList)
        }


        Iterator it=accountCodeList.iterator();
        String proVal=params.term
        if (!proVal.equals("")){
            while(it.hasNext())
            {
                String value=(String)it.next();
                proVal=proVal.trim()
                String Str = new String(value);
                String SubStr = new String(proVal);
                // int rslt=Str.startsWith(SubStr)
               // println("value :"+Str+" proVal "+SubStr+" Str.startsWith(SubStr) "+Str.startsWith(SubStr))

                if (Str.startsWith(SubStr)){  //here comparing the provided value with list value
                    accountCodeList2.add(Str)
                    //System.out.println("accountCodeList2 Value1 :"+accountCodeList2);
                }else{
                    //System.out.println("accountCodeList Value2 :"+accountCodeList);
                }

            }
        }else{
            List accountCodeList3=new ArrayList()
            render (accountCodeList3 as JSON)
        }
        if (accountCodeList2.size()>0){
            render (accountCodeList2 as JSON)
        }else{
            render (accountCodeList as JSON)
        }

    }

    def autoParentAccountCode() {   // It is for a field which data type is Long/Integer

        // checking the list status  and adding data to that list
        // checking the provided params status whether it is empty or not
        // if not empty then compare with the list value
        // if empty then just provide the list value without comparing the value.

        List accountCodeList=new ArrayList()
        List accountCodeList2=new ArrayList()

        if (accountCodeList.size()<1){

            def foundData = AfmChartOfAccounts.withCriteria {
                //  eq('accountCode', params.term )
                like( 'isSubsidiary', 'Y' )       //N means it has no child
            }
            println("foundData.size() :"+foundData.size())
            println("foundData.size() :"+foundData?.accountCode)

            for (int i=0;i<foundData.size();i++){

                accountCodeList.add(""+foundData["accountCode"].get(i).toString()+"")

            }
          //  println("accountCodeList :"+accountCodeList)
        }


        Iterator it=accountCodeList.iterator();
        String proVal=params.term
        if (!proVal.equals("")){
            while(it.hasNext())
            {
                String value=(String)it.next();
                proVal=proVal.trim()
                String Str = new String(value);
                String SubStr = new String(proVal);
                // int rslt=Str.startsWith(SubStr)
                //println("value :"+Str+" proVal "+SubStr+" Str.startsWith(SubStr) "+Str.startsWith(SubStr))

                if (Str.startsWith(SubStr)){       //here comparing the provided value with list value
                    accountCodeList2.add(Str)
                   // System.out.println("accountCodeList2 Value1 :"+accountCodeList2);
                }else{
                   // System.out.println("accountCodeList Value2 :"+accountCodeList);
                }

            }
        }else{
            List accountCodeList3=new ArrayList()
            render (accountCodeList3 as JSON)
        }
        if (accountCodeList2.size()>0){
            render (accountCodeList2 as JSON)
        }else{
            render (accountCodeList as JSON)
        }

    }

    def autoParentLedgerCode() {   // It is for a field which data type is Long/Integer

        // checking the list status  and adding data to that list
        // checking the provided params status whether it is empty or not
        // if not empty then compare with the list value
        // if empty then just provide the list value without comparing the value.

        List accountCodeList=new ArrayList()
        List accountCodeList2=new ArrayList()

        if (accountCodeList.size()<1){

            def foundData = AfmChartOfAccounts.withCriteria {
                //  eq('accountCode', params.term )
                like( 'isSubsidiary', 'N' )       //N means it has no child
            }
            println("foundData.size() :"+foundData.size())
            println("foundData.size() :"+foundData?.accountCode)

            for (int i=0;i<foundData.size();i++){

                accountCodeList.add(""+foundData["accountCode"].get(i).toString()+"")

            }
           // println("accountCodeList :"+accountCodeList)
        }


        Iterator it=accountCodeList.iterator();
        String proVal=params.term
        if (!proVal.equals("")){
            while(it.hasNext())
            {
                String value=(String)it.next();
                proVal=proVal.trim()
                String Str = new String(value);
                String SubStr = new String(proVal);
                // int rslt=Str.startsWith(SubStr)
                //println("value :"+Str+" proVal "+SubStr+" Str.startsWith(SubStr) "+Str.startsWith(SubStr))

                if (Str.startsWith(SubStr)){       //here comparing the provided value with list value
                    accountCodeList2.add(Str)
                    //System.out.println("accountCodeList2 Value1 :"+accountCodeList2);
                }else{
                    //System.out.println("accountCodeList Value2 :"+accountCodeList);
                }

            }
        }else{
            List accountCodeList3=new ArrayList()
            render (accountCodeList3 as JSON)
        }
        if (accountCodeList2.size()>0){
            render (accountCodeList2 as JSON)
        }else{
            render (accountCodeList as JSON)
        }

    }

    def autoAccountCodeForBankTrn() {   // It is for a field which data type is Long/Integer

        // checking the list status  and adding data to that list
        // checking the provided params status whether it is empty or not
        // if not empty then compare with the list value
        // if empty then just provide the list value without comparing the value.

        List accountCodeList=new ArrayList()
        List accountCodeList2=new ArrayList()

        if (accountCodeList.size()<1){

            def parentAccountHead = AfmChartOfAccounts.get(127)

            def foundData = AfmChartOfAccounts.findAllByParentAccountHead(parentAccountHead)
            /*def foundData = AfmChartOfAccounts.withCriteria {
                eq('parentAccountHead', parentAccountHead )
                like( 'isSubsidiary', 'N' )       //N means it has no child
            }*/
            println("foundData.size() :"+foundData.size())
            println("foundData.size() :"+foundData?.accountCode)

            for (int i=0;i<foundData.size();i++){

                accountCodeList.add(""+foundData["accountCode"].get(i).toString()+"")

            }
            //println("accountCodeList :"+accountCodeList)
        }


        Iterator it=accountCodeList.iterator();
        String proVal=params.term
        if (!proVal.equals("")){
            while(it.hasNext())
            {
                String value=(String)it.next();
                proVal=proVal.trim()
                String Str = new String(value);
                String SubStr = new String(proVal);
                // int rslt=Str.startsWith(SubStr)
                println("value :"+Str+" proVal "+SubStr+" Str.startsWith(SubStr) "+Str.startsWith(SubStr))

                if (Str.startsWith(SubStr)){        //here comparing the provided value with list value
                    accountCodeList2.add(Str)
                    //System.out.println("accountCodeList2 Value1 :"+accountCodeList2);
                }else{
                    //System.out.println("accountCodeList Value2 :"+accountCodeList);
                }

            }
        }else{
            List accountCodeList3=new ArrayList()
            render (accountCodeList3 as JSON)
        }
        if (accountCodeList2.size()>0){
            render (accountCodeList2 as JSON)
        }else{
            render (accountCodeList as JSON)
        }

    }

    def autoAccountHeadName() {
        def foundData = AfmChartOfAccounts.withCriteria {
            ilike( 'accountHeadName', '%'+params.term.toString().trim()+ '%' )
            ilike( 'isSubsidiary', 'N' )     //N means it has no child
            order("accountHeadName", "asc")
        }

        render (foundData?.accountHeadName as JSON)
      }

    def autoAllAccountHeadName() {
        def foundData = AfmChartOfAccounts.withCriteria {
            ilike( 'accountHeadName', '%'+params.term.toString().trim()+ '%' )
            order("accountHeadName", "asc")
        }

        render (foundData?.accountHeadName as JSON)
      }
    def autoParentAccountHeadName() {
            def foundData = AfmChartOfAccounts.withCriteria {
                ilike( 'accountHeadName', '%'+params.term.toString().trim()+ '%' )
                ilike( 'isSubsidiary', 'Y' )     //N means it has no child
                order("accountHeadName", "asc")
            }

            render (foundData?.accountHeadName as JSON)
          }

    def autoAssetIdentificationNumber() {
        Sql sql = new Sql(dataSource)
        sqlValueList=null
        sqlValueList = sql.rows("SELECT DISTINCT ASSET_IDENTIFICATION_NUMBER,ASSET_BOOK_ID FROM ASSET_BOOK_SCHEDULE_V " +
                                " WHERE " +
                                " UPPER(ASSET_IDENTIFICATION_NUMBER) LIKE '%'||UPPER(?)||'%' ORDER BY ASSET_IDENTIFICATION_NUMBER ASC ",params.term.toString().trim())

       // println("sqlValueList.ASSET_IDENTIFICATION_NUMBER :"+sqlValueList.ASSET_IDENTIFICATION_NUMBER)

        sql.close()
        render (sqlValueList.ASSET_IDENTIFICATION_NUMBER as JSON)
    }


    def autoParentLedgerHeadName() {
        Sql sql = new Sql(dataSource)
        def P_ACC_ID
        def foundData
        String accountsHeadName=params.fld2IdVal
        println("accountsHeadName :"+accountsHeadName)
        if (!accountsHeadName.equals("")){
            def afmChartOfAccounts = AfmChartOfAccounts.findByAccountHeadName(accountsHeadName.trim())
            if (afmChartOfAccounts!=null){
                P_ACC_ID = afmChartOfAccounts.getId()
                println("P_ACC_ID :"+P_ACC_ID)
            }else{
                println("P_ACC_ID :"+P_ACC_ID)
            }
        }
        accountHeadNameList = sql.rows("SELECT ACCOUNT_HEAD_ID,ACCOUNT_CODE,ACCOUNT_HEAD_NAME " +
                            " FROM AFM_CHART_OF_ACCOUNTS A " +
                            " WHERE ACCOUNT_HEAD_TYPE=" +
                            " (" +
                            " SELECT ACCOUNT_HEAD_TYPE" +
                            " FROM AFM_CHART_OF_ACCOUNTS A " +
                            " WHERE ACCOUNT_HEAD_ID=?" +
                            " )" +
                            " AND A.IS_SUBSIDIARY='N' " +
                            " AND  upper(ACCOUNT_HEAD_NAME)  like  '%'||upper(?)||'%' " +
                            " ORDER BY ACCOUNT_HEAD_NAME ASC",P_ACC_ID,params.term.toString().trim())
        println("accountHeadNameList.size()  :"+accountHeadNameList.size())


        sql.close()
        render (accountHeadNameList?.ACCOUNT_HEAD_NAME as JSON)
    }

    def autoAccountHeadNameForBankTrn() {
        def parentAccountHead = AfmChartOfAccounts.get(127)
        def foundData = AfmChartOfAccounts.withCriteria {
            ilike( 'accountHeadName', '%'+params.term.toString().trim()+ '%' )
            eq( 'parentAccountHead', parentAccountHead )
            ilike( 'isSubsidiary', 'N' )     //N means it has no child
            order("accountHeadName", "asc")
        }

        render (foundData?.accountHeadName as JSON)
      }

    def getAccCodeByAccHeadNameForBankTrn() {
        def info
        String accountHeadNameStr=params.accountHeadName.toString().trim()

        System.out.println("accountHeadNameStr "+accountHeadNameStr)
        String accountHeadName=accountHeadNameStr.toString().trim().replace("MARUF","&")
        System.out.println("accountHeadName "+accountHeadName)

        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountHeadName(accountHeadName)
        if (afmChartOfAccounts!=null){
            def accountCode = afmChartOfAccounts.accountCode
            def id = afmChartOfAccounts.id
            System.out.println("id "+id)
            System.out.println("accountCode "+accountCode)
            info = id+":"+accountCode
        }
        render new WSReturn(100, info, null) as JSON
     }

    def getAccCodeByAccHeadName() {
        def info

        String accountHeadNameStr=params.accountHeadName.toString().trim()
        println("accountHeadNameStr |"+accountHeadNameStr+"|")
        String accountHeadName=accountHeadNameStr.trim().replace("MARUF","&")

       println("accountHeadName :|"+accountHeadName+"|")

        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountHeadNameAndIsSubsidiary(accountHeadName.toString().trim(),'N')
        if (afmChartOfAccounts!=null){
            def accountCode = afmChartOfAccounts.accountCode
            def id = afmChartOfAccounts.id
            def isBankCash = afmChartOfAccounts.isBankCash
            System.out.println(id)
            info = id+":"+accountCode+":"+isBankCash
        }
        render new WSReturn(100, info, null) as JSON
     }

    def getAccCodeByAccHeadNameIsBankCash() {
        def info

        String accountHeadNameStr=params.accountHeadName.toString().trim()
        String accountHeadName=accountHeadNameStr.trim().replace("MARUF","&")

      //  println("accountHeadName :"+accountHeadName)

        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountHeadNameAndIsSubsidiary(accountHeadName,'N')
        if (afmChartOfAccounts!=null){
            def accountCode = afmChartOfAccounts.accountCode
            def id = afmChartOfAccounts.id
            def isBankCash = afmChartOfAccounts.isBankCash
            System.out.println(id)
            info = id+":"+accountCode+":"+isBankCash
        }
        render new WSReturn(100, info, null) as JSON
     }

    def getAccCodeByParentAccHeadName() {

        def info
        String accountHeadNameStr=params.accountHeadName.toString().trim()
        String accountHeadName=accountHeadNameStr.trim().replace("MARUF","&")

        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountHeadNameAndIsSubsidiary(accountHeadName,'Y')
        if (afmChartOfAccounts!=null){
            def accountCode = afmChartOfAccounts.accountCode
            def id = afmChartOfAccounts.id
            System.out.println(id)
            info = id+":"+accountCode
        }
        render new WSReturn(100, info, null) as JSON
    }


    def getLedgerCodeByLedgerHeadName() {

        def info
        String accountHeadNameStr=params.accountHeadName.toString().trim()
        String accountHeadName=accountHeadNameStr.trim().replace("MARUF","&")

        def afmChartOfAccounts = AfmChartOfAccounts.findByAccountHeadNameAndIsSubsidiary(accountHeadName,'N')
        if (afmChartOfAccounts!=null){
            def accountCode = afmChartOfAccounts.accountCode
            def id = afmChartOfAccounts.id
            System.out.println(id)
            info = id+":"+accountCode
        }
        render new WSReturn(100, info, null) as JSON
    }
}