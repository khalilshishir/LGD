package hrms

import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.dao.DataIntegrityViolationException

class HrEmployeePayStructureController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def dataSource

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [hrEmployeePayStructureInstanceList: HrEmployeePayStructure.list(params), hrEmployeePayStructureInstanceTotal: HrEmployeePayStructure.count()]
    }

    def create() {
        [hrEmployeePayStructureInstance: new HrEmployeePayStructure(params)]
    }

    def save() {
        def hrEmployeePayStructureInstance = new HrEmployeePayStructure(params)
        if (!hrEmployeePayStructureInstance.save(flush: true)) {
            render(view: "create", model: [hrEmployeePayStructureInstance: hrEmployeePayStructureInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), hrEmployeePayStructureInstance.id])
        redirect(action: "show", id: hrEmployeePayStructureInstance.id)
    }

    def show() {
        def hrEmployeePayStructureInstance = HrEmployeePayStructure.get(params.id)
        if (!hrEmployeePayStructureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeePayStructureInstance: hrEmployeePayStructureInstance]
    }

    def edit() {
        def hrEmployeePayStructureInstance = HrEmployeePayStructure.get(params.id)
        if (!hrEmployeePayStructureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), params.id])
            redirect(action: "list")
            return
        }

        [hrEmployeePayStructureInstance: hrEmployeePayStructureInstance]
    }

    def update() {
        def hrEmployeePayStructureInstance = HrEmployeePayStructure.get(params.id)
        if (!hrEmployeePayStructureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (hrEmployeePayStructureInstance.version > version) {
                hrEmployeePayStructureInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure')] as Object[],
                        "Another user has updated this HrEmployeePayStructure while you were editing")
                render(view: "edit", model: [hrEmployeePayStructureInstance: hrEmployeePayStructureInstance])
                return
            }
        }

        hrEmployeePayStructureInstance.properties = params

        if (!hrEmployeePayStructureInstance.save(flush: true)) {
            render(view: "edit", model: [hrEmployeePayStructureInstance: hrEmployeePayStructureInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), hrEmployeePayStructureInstance.id])
        redirect(action: "show", id: hrEmployeePayStructureInstance.id)
    }

    def delete() {
        def hrEmployeePayStructureInstance = HrEmployeePayStructure.get(params.id)
        if (!hrEmployeePayStructureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), params.id])
            redirect(action: "list")
            return
        }

        try {
            hrEmployeePayStructureInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hrEmployeePayStructure.label', default: 'HrEmployeePayStructure'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    // start for data retrived from another table ----------------------
    def getEmpInfo()
    {
        def idObj = HrEmployee.get(params.idNo)

        def data1 = idObj.cardNo
        //System.out.println("data1 :"+ data1)

        def data2 = idObj.designationIdHrDesignation.designationName
        //System.out.println("data2 : "+ data2)

        //def data3 = idObj.designationIdHrDesignation.hrPayscaleList.gradeNo
        //System.out.println("data3 : "+ data3)

        //def data4 = idObj.designationIdHrDesignation.hrPayscaleListId
        //System.out.println("data4 : "+ data4)

        def data = data1+":"+data2
        //System.out.println("Total data= "+ data)
        render new WSReturn(100, data , null) as JSON
        // end for data retrived from another table -------------------------
    }

    //--------------- start get Grade no from a grade --------------------------------
    def getGradeNo()
    {
        def query = "from HrPayscale order by id"
        def hrPayscales = HrPayscale.executeQuery(query)
        def sb = new StringBuilder()
        sb.append("[")
        for (HrPayscale hrPayscale: hrPayscales) {
            sb.append("{'id':'" + hrPayscale.id + "', 'value':'" + hrPayscale.gradeNo + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        //System.out.println("sb==> " + sb.toString());
        render new WSReturn(100, sb.toString(), null) as JSON
    }
    //--------------- end get Grade no from a grade --------------------------------


    //--------------- start get slab no from a grade --------------------------------
    def getSlabNo()
    {
        def payScaleId = params.payScaleId
        def query = "from HrPayscaleDetail d where d.payscaleIdHrPayscale.id in(" + payScaleId + ") order by d.stage"
        def hrPayScaleDetails = HrPayscaleDetail.executeQuery(query)
        def sb = new StringBuilder()
        sb.append("[")
        for (HrPayscaleDetail hrPayScaleDetail: hrPayScaleDetails) {
            sb.append("{'id':'" + hrPayScaleDetail.id + "', 'value':'" + hrPayScaleDetail.stage + "'}")
            sb.append(",")
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.lastIndexOf(","))
        sb.append("]")
        //System.out.println("sb==> " + sb.toString());
        render new WSReturn(100, sb.toString(), null) as JSON
    }
    //--------------- end get slab no from a grade --------------------------------

    def getStageData()
    {
        def HrPayscaleInstance = HrPayscale.get(params.payScaleId)
        def hrPayScaleDetailCat = HrPayscaleDetail.createCriteria()
        def hrPayScaleDetailInstance = hrPayScaleDetailCat.list{
           and{
               eq("stage", Integer.valueOf(params.stage))
               eq("payscaleIdHrPayscale", HrPayscaleInstance)
           }
        }


        def basic = hrPayScaleDetailInstance[0].basic
        //System.out.println("Basic: "+ basic)
        def data1 = basic.toString()

        def da = hrPayScaleDetailInstance[0].da
        //System.out.println("da: "+ da)
        def data2 = da.toString()

        def houseRent = hrPayScaleDetailInstance[0].houseRent
        //System.out.println("House Rent: "+ houseRent)
        def data3 = houseRent.toString()

        def conveyanceAllow = hrPayScaleDetailInstance[0].conveyanceAllow
        //System.out.println("Conveyance: "+ conveyanceAllow)
        def data4 = conveyanceAllow.toString()

        def washingAllow = hrPayScaleDetailInstance[0].washingAllow
        //System.out.println("Washing: "+ washingAllow)
        def data5 = washingAllow.toString()

        def medicalAllow = hrPayScaleDetailInstance[0].medicalAllow
        //System.out.println("Medical: "+ medicalAllow)
        def data6 = medicalAllow.toString()

        def pfContribution = hrPayScaleDetailInstance[0].pfContribution
        //System.out.println("PF Contribution: "+ pfContribution)
        def data7 = pfContribution.toString()

        def totalPayable = (basic+da+houseRent+conveyanceAllow+washingAllow+medicalAllow+pfContribution).toString()
        //System.out.println("Total Earnings= "+ totalPayable)

        def sql = new Sql(dataSource)
        String q = "SELECT GET_EMP_MONTHLY_LOAN_AMOUNT(" + params.id + ",3470)EMP_MONTHLY_LOAN_AMOUNT FROM DUAL";
        //println("Sql Str 1:"+q)
        def data9 = sql.firstRow(q).get("EMP_MONTHLY_LOAN_AMOUNT")
        //println("dLoanSalary :"+data9)

        String q2= "SELECT GET_EMP_MONTHLY_LOAN_AMOUNT(" + params.id + ",343)EMP_MONTHLY_LOAN_AMOUNT FROM DUAL";
        //println("Sql Str 2:"+q2)
        def data10 = sql.firstRow(q2).get("EMP_MONTHLY_LOAN_AMOUNT")
        //println("dLoanPf :"+data10)

        def data =  data1+":"+data2+":"+data3+":"+data4+":"+data5+":"+data6+":"+data7+":"+totalPayable+":"+data9+":"+data10
        //System.out.println("Total data= "+ data)

        render new WSReturn(100, data , null) as JSON
        // end for data retrived from another table -------------------------
    }


  // start search for employees salary information
  def search() {
        def cardNo = '%'+params.cardNo
        def idNo = HrEmployee.findByCardNoLike(cardNo)
      if (idNo){

          try{
              def payStructureIdNo = idNo.hrEmployeePayStructure.id
              def hrEmployeePayStructureInstanceList = null;
              def totalCount = 0;
              hrEmployeePayStructureInstanceList = HrEmployeePayStructure.get(payStructureIdNo)
              totalCount =  HrEmployeePayStructure.countById(payStructureIdNo)
              render(view: "list", model: [hrEmployeePayStructureInstanceList: hrEmployeePayStructureInstanceList,hrEmployeePayStructureInstanceTotal:totalCount])
          }
          catch(Exception ex) {
              flash.message = "This IDNO is Not yet Include in Monthly Salary!..."
              redirect(action: "list")
              System.out.println("Message Name is:"+ex);
          }
      }
      else{
          flash.message = "This IDNO is Not Found!..."
          redirect(action: "list")
          }
  }
    // end search for employees salary information


}
