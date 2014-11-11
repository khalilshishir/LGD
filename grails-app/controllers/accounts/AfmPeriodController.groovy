package accounts

import org.springframework.dao.DataIntegrityViolationException
import inventory.WSReturn
import grails.converters.JSON

//import inventory.IubUser
import groovy.sql.Sql

class AfmPeriodController {

    def dataSource
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    List sqlValueList

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
/*        if (session.user==null){
            flash.message = "Sorry,for the inconvenient situation. Please login again."
            redirect(controller: "iubUser",action:"login")
        }*/

        params.max = Math.min(params.max ? params.int('max') : 10, 100)
/*        params.sort = params.sort ?: "periodYear"
        params.order = params.order ?: "asc" // or "asc"
        params.sort = params.sort ?: "serialNo"
        params.order = params.order ?: "asc" // or "desc"*/

        [afmPeriodInstanceList: AfmPeriod.list(params), afmPeriodInstanceTotal: AfmPeriod.count()]
    }

    def create() {
        if (session.user==null){
            flash.message = "Sorry,for the inconvenient situation. Please login again."
            redirect(controller: "iubUser",action:"login")
        }

        def periodNameList = new ArrayList<AfmCalendarMonth>()
        periodNameList=AfmCalendarMonth.executeQuery("from accounts.AfmCalendarMonth a " +
                                                    " where a.periodName not in ( " +
                                                    " select periodName from accounts.AfmPeriod) order by a.calendarYear,a.serialNo")
         println("periodNameList Size "+periodNameList.size())
         println("periodNameList "+periodNameList)

        [afmPeriodInstance: new AfmPeriod(params),periodNameList:periodNameList,periodNameList:periodNameList]
    }

    def showPeriodName() {

        def periodNameList = new ArrayList<AfmPeriod>()
        periodNameList=AfmCalendarMonth.executeQuery("select periodName from accounts.AfmPeriod where periodStatus<>'CLOSE'")
         println("periodNameList Size "+periodNameList.size())
         println("periodNameList "+periodNameList)


        [afmPeriodInstance: new AfmPeriod(params),periodNameList:periodNameList]
    }

    def periodNameList() {
       if (params.periodStatusVal.equals("OPEN")){
           def periodNameList = new ArrayList<AfmCalendarMonth>()
           periodNameList=AfmCalendarMonth.executeQuery("from accounts.AfmCalendarMonth a " +
                                                       " where a.periodName not in ( " +
                                                       " select periodName from accounts.AfmPeriod) order by a.calendarYear,a.serialNo")
           println("periodNameList Size "+periodNameList.size())
           println("periodNameList "+periodNameList)

           def sb = new StringBuilder()

           sb.append("[")

           for (int i=0; i<periodNameList.size(); i++) {

               sb.append("{'id':'" + periodNameList.get(i).id + "', 'value':'" + periodNameList.get(i).periodName + "'}")
               sb.append(",")
           }
           if (sb.length() > 1) sb.deleteCharAt(sb.lastIndexOf(","))
           sb.append("]")

           println("sb==> " + sb.toString());
           def info = sb.toString()
           render new WSReturn(100, info , null) as JSON
       }else{

        def periodNameList2 = new ArrayList<AfmPeriod>()
        periodNameList2=AfmPeriod.executeQuery("from accounts.AfmPeriod where periodStatus<>'CLOSE'")
        println("periodNameList2 Size "+periodNameList2.size())
        println("periodNameList2 "+periodNameList2)

           def sb = new StringBuilder()

           sb.append("[")

           for (int i=0; i<periodNameList2.size(); i++) {

               sb.append("{'id':'" + periodNameList2.get(i).id + "', 'value':'" + periodNameList2.get(i).periodName + "'}")
               sb.append(",")
           }
           if (sb.length() > 1) sb.deleteCharAt(sb.lastIndexOf(","))
           sb.append("]")

           println("sb==> " + sb.toString());
           def info = sb.toString()
           render new WSReturn(100, info , null) as JSON
       }

    }

    def showClosePeriod() {

        List  periodYearList = getCalendarYearList()

/*        if (session.user==null){
            flash.message = "Sorry,for the inconvenient situation. Please login again."
            redirect(controller: "iubUser",action:"login")
        }*/

/*        def periodNameList = new ArrayList<AfmPeriod>()
        periodNameList=AfmCalendarMonth.executeQuery("from accounts.AfmPeriod where periodStatus<>'CLOSE'")
        println("periodNameList Size "+periodNameList.size())
        println("periodNameList "+periodNameList)*/

      //  [afmPeriodInstance: new AfmPeriod(params),periodNameList:periodNameList]


         render(view: "showClosePeriod", model: [afmPeriodInstance: new AfmPeriod(params),periodYearList:periodYearList] )


    }

    def showOpenPeriod() {

      List  periodYearList = getCalendarYearList()

/*        def periodYearList = new ArrayList<AfmCalendarMonth>()
        periodYearList=AfmCalendarMonth.executeQuery("from accounts.AfmCalendarMonth a " +
                                                    " where a.calendarYear is not null order by a.calendarYear,a.serialNo")
        println("periodYearList Size "+periodYearList.size())
        println("periodYearList "+periodYearList)*/

        /*def periodNameList = new ArrayList<AfmCalendarMonth>()
        periodNameList=AfmCalendarMonth.executeQuery("from accounts.AfmCalendarMonth a " +
                                                    " where a.periodName not in ( " +
                                                    " select periodName from accounts.AfmPeriod) order by a.calendarYear,a.serialNo")
        println("periodNameList Size "+periodNameList.size())
        println("periodNameList "+periodNameList)*/

        [afmPeriodInstance: new AfmPeriod(params),periodYearList:periodYearList]

    }

    public List getCalendarYearList() {

        Sql sql = new Sql(dataSource)
        sqlValueList = null
        sqlValueList = sql.rows("SELECT DISTINCT CALENDAR_YEAR FROM AFM_CALENDAR_MONTH WHERE CALENDAR_YEAR IS NOT NULL ORDER BY CALENDAR_YEAR ASC")

        println("sqlValueList  :" + sqlValueList)
        sql.close()
        return sqlValueList
    }

    def getPeriodNameList() {
        Sql sql = new Sql(dataSource)
        def periodYr = params.periodYear
        def periodYear=""
        if(periodYr!=""){
           periodYear = Long.valueOf(periodYr)
        }

        sqlValueList = null
        sqlValueList = sql.rows("SELECT DISTINCT PERIOD_NAME,SERIAL_NO FROM AFM_CALENDAR_MONTH A" +
                                " WHERE PERIOD_NAME IS NOT NULL " +
                                " AND CALENDAR_YEAR=?" +
                                " ORDER BY A.SERIAL_NO ASC", periodYear)
        println("List   :" + sqlValueList)

        def sb = new StringBuilder()
        sb.append("[")
        for (def list: sqlValueList) {
            sb.append("{'id':'" + list.PERIOD_NAME + "', 'value':'" + list.PERIOD_NAME + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        sql.close()
        render new WSReturn(100, sb.toString(), null) as JSON
    }

    def getOpenPeriodNameList() {
        Sql sql = new Sql(dataSource)
        def periodYear = Long.valueOf(params.periodYear)
        sqlValueList = null
        sqlValueList = sql.rows("SELECT DISTINCT PERIOD_NAME,SERIAL_NO FROM AFM_PERIOD A  WHERE PERIOD_STATUS='OPEN' AND PERIOD_YEAR=? ORDER BY A.SERIAL_NO ASC", periodYear)
        println("List   :" + sqlValueList)

        def sb = new StringBuilder()
        sb.append("[")
        for (def list: sqlValueList) {
            sb.append("{'id':'" + list.PERIOD_NAME + "', 'value':'" + list.PERIOD_NAME + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        sql.close()
        render new WSReturn(100, sb.toString(), null) as JSON
    }

    def save() {
        def afmPeriodInstance = new AfmPeriod()

        def afmCalendarMonthInstance= AfmCalendarMonth.findByCalendarYearAndPeriodName(params.periodYear,params.periodName)

        def serialNo=afmCalendarMonthInstance.serialNo

        afmPeriodInstance.properties['serialNo'] = serialNo

        afmPeriodInstance.properties["id","periodName", "periodStatus", "periodYear"] = params

        if (!afmPeriodInstance.save(flush: true)) {
            render(view: "create", model: [afmPeriodInstance: afmPeriodInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), afmPeriodInstance.id])
        redirect(action: "show", id: afmPeriodInstance.id)
    }

    def show() {

/*        if (session.user==null){
            flash.message = "Sorry,for the inconvenient situation. Please login again."
            redirect(controller: "iubUser",action:"login")
        }*/

        def afmPeriodInstance = AfmPeriod.get(params.id)
        if (!afmPeriodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.id])
            redirect(action: "list")
            return
        }
        
/*        if (afmPeriodInstance?.periodStatus=='OPEN'){
            render(view: "showOpenPeriod", model: [afmPeriodInstance: afmPeriodInstance] )
        }else{
            render(view: "showClosePeriod", model: [afmPeriodInstance: afmPeriodInstance] )
        }*/

        render(view: "show", model: [afmPeriodInstance: afmPeriodInstance] )
    }

    def edit() {
        def afmPeriodInstance = AfmPeriod.get(params.id)
        if (!afmPeriodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.id])
            redirect(action: "list")
            return
        }

        [afmPeriodInstance: afmPeriodInstance]
    }

    def update() {

        def afmPeriodInstance = AfmPeriod.get(params.id)
        println("afmPeriodInstance.serialNo "+afmPeriodInstance.serialNo)
        if (afmPeriodInstance.serialNo==null){
            def afmCalendarMonthInstance= AfmCalendarMonth.findByCalendarYearAndPeriodName(params.periodYear,params.periodName)

            def serialNo=afmCalendarMonthInstance.serialNo

            afmPeriodInstance.properties['serialNo'] = serialNo

            afmPeriodInstance.properties["id","periodName", "periodStatus", "periodYear"] = params
        }

        if (!afmPeriodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (afmPeriodInstance.version > version) {
                afmPeriodInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'afmPeriod.label', default: 'AfmPeriod')] as Object[],
                          "Another user has updated this AfmPeriod while you were editing")
                render(view: "edit", model: [afmPeriodInstance: afmPeriodInstance])
                return
            }
        }

        afmPeriodInstance.properties = params

        if (!afmPeriodInstance.save(flush: true)) {
            render(view: "edit", model: [afmPeriodInstance: afmPeriodInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), afmPeriodInstance.id])
        redirect(action: "show", id: afmPeriodInstance.id)
    }

    def updateOpenPeriod() {

        println("params.periodName "+params.periodName)
        println("params.id "+params.id)

        def afmPeriodInstance = AfmPeriod.findByPeriodName(params.periodName)
        //def afmPeriodInstance = AfmPeriod.get(params.id)
        if (!afmPeriodInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.periodName])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (afmPeriodInstance.version > version) {
                afmPeriodInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'afmPeriod.label', default: 'AfmPeriod')] as Object[],
                          "Another user has updated this AfmPeriod while you were editing")
                render(view: "edit", model: [afmPeriodInstance: afmPeriodInstance])
                return
            }
        }

        afmPeriodInstance.properties = params

        if (!afmPeriodInstance.save(flush: true)) {
            render(view: "edit", model: [afmPeriodInstance: afmPeriodInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), afmPeriodInstance.id])
        redirect(action: "show", id: afmPeriodInstance.id)
    }

    def delete() {
        def afmPeriodInstance = AfmPeriod.get(params.id)
        if (!afmPeriodInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.id])
            redirect(action: "list")
            return
        }

        try {
            afmPeriodInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'afmPeriod.label', default: 'AfmPeriod'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
