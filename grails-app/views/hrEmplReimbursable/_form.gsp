<%@ page import="hrms.HrEmplReimbursable" %>
<script style="">
    // search by employee CardNo
    function showValueByCardNo(obj) {
        //  debugger;
        var idNo = obj.value ;
        //alert(idNo)

        if(idNo != null) {
            $.ajax({

                type:'POST',
                /*url: '/HrEmployeeLeave/getData',*/
                url: '${request.contextPath}/HrEmplReimbursable/getEmpBasicInfoByCardNo',
                dataType:"JSON",
                data: 'idNo=' + idNo,
                success:function(response) {
                    // var json = JSON.parse(response);
                    var json =  response;
                    var data = json.message.split(':');
                    //alert(data);
                    document.getElementById('employeeName').value = data[0];
                    document.getElementById('hrEmployee').value = data[11];
                },
                error:function(err) {
                    // alert(err);
                }
            }) ;
        }

    }

    // search by employee name
    function showValue(obj) {
        //  debugger;
        var idNo = obj.value ;
        //alert(idNo)

        if(idNo != null) {
            $.ajax({

                type:'POST',
                /*url: '/HrEmployeeLeave/getData',*/
                url: '${request.contextPath}/HrEmplReimbursable/getEmpBasicInfo',
                dataType:"JSON",
                data: 'idNo=' + idNo,
                success:function(response) {
                    // var json = JSON.parse(response);
                    var json =  response;

                    if(json.message!=null){
                        var data = json.message.split(':');
                        //alert(data);
                        document.getElementById('cardNo').value = data[0];
                        document.getElementById('hrEmployee').value = data[11];
                    }
                },
                error:function(err) {
                    // alert(err);
                }
            }) ;
        }

    }
</script>
<script type="text/javascript">

    // auto employee name searching
    function autoEmployeeName(fldId) {
        $('#'+fldId).autocomplete({
            source:'<g:createLink controller='HrEmplReimbursable'  action='autoEmployeeNameByDepartmentWise' />'
        });
    }

    // auto employee cardNo searching
    function getEmployeeCardNo(fldId) {
        $('#'+fldId).autocomplete({
            source:'<g:createLink controller='HrEmplReimbursable' action='autoEmployeeCardNoByDepartmentWise'/>'
        });
    }
</script>

<g:hiddenField id="hrEmployee" name="hrEmployee.id"  value="${hrEmplReimbursableInstance?.hrEmployee?.id}"/>
<div class="col-xs-4">
    <div class="form-group">
        <label for="cardNo"><g:message code="hrEmplReimbursable.cardNo.label" default="Employee ID" /></label>

        <g:if test="${hrEmplReimbursableInstance?.hrEmployee?.id == null}">
            <g:textField id="cardNo" name="cardNo" maxlength="25" onclick="getEmployeeCardNo(this.id)" onkeyup="showValueByCardNo(this);" onblur="showValueByCardNo(this);"
                         value=""  style="text-align: left;font-weight: bold;" class="form-control"/>
        </g:if>
        <g:else>
            <g:textField id="cardNo" name="cardNo" maxlength="25"  value="${hrEmplReimbursableInstance?.hrEmployee?.cardNo}"  style="text-align: left;font-weight: bold;" class="form-control" readonly="readonly"/>
        </g:else>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="employeeName"><g:message code="hrEmplReimbursable.employeeName.label" default="Employee's Name" /></label>
        <g:if test="${hrEmplReimbursableInstance?.hrEmployee?.id == null}">
            <g:textField name='employeeName' id='employeeName' value='' onclick="autoEmployeeName(this.id)" onkeyup="showValue(this);" onblur="showValue(this);"  class="form-control"/>
        </g:if>
        <g:else>
            <g:textField name='employeeName' id='employeeName' value='${hrEmplReimbursableInstance?.hrEmployee?.employeeName}' readonly="readonly" class="form-control"/>
        </g:else>
    </div>
</div>
%{--<div class="col-xs-4">
    <div class="form-group">
        <label for="hrEmployee">
            <g:message code="hrDepartment.hrEmployee.label" default="Employee Name" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cardNo" name="cardNo" from="${hrms.HrEmployee.list()}" optionKey="cardNo" required="" value="${HrEmplReimbursableInstance?.hrEmployee?.id}" class="form-control"/>
    </div>
</div>--}%
<div class="col-xs-4">
    <div class="form-group">
        <label for="contractAmount"><g:message code="hrDepartment.contractAmount.label" default="Contract Amount" /></label>
        <g:textField class="form-control" id="contractAmount" name="contractAmount" maxlength="50" required=""
                     value="${hrEmplReimbursableInstance?.contractAmount}" />
    </div>
</div>
<div class="col-xs-4">
    <div class="form-group">
        <label for="contractDate"><g:message code="hrEmployee.contractDate.label" default="Contract Date" /></label>
        <g:textField id="contractDate" name="contractDate" required=""
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmplReimbursableInstance?.contractDate != null?hrEmplReimbursableInstance?.contractDate:new Date())}"
                     style="text-align: center;" class="form-control"/>
    </div>
</div>
<div class="col-xs-4">
    <div class="form-group">
        <label for="startDate"><g:message code="hrEmployee.startDate.label" default="Start Date" /></label>
        <g:textField id="startDate" name="startDate" required=""
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmplReimbursableInstance?.startDate != null?hrEmplReimbursableInstance?.startDate:new Date())}"
                     style="text-align: center;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="endDate"><g:message code="hrEmployee.endDate.label" default="End Date" /></label>
        <g:textField id="endDate" name="endDate"
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmplReimbursableInstance?.endDate)}"
                     style="text-align: center;" class="form-control"/>
    </div>
</div>


<div class="col-xs-4">
    <div class="form-group">
        <label for="totalDays"><g:message code="hrDepartment.totalDays.label" default="Total Days" /></label>
        <g:textField class="form-control" id="totalDays" name="totalDays" maxlength="50"
                     value="${hrEmplReimbursableInstance?.totalDays}" />
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="contractReason"><g:message code="hrEmployee.contractReason.label" default="Contract Reason" /></label>
        <g:textArea id="contractReason" name="contractReason" value="${hrEmplReimbursableInstance?.contractReason}"   class="form-control" />
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="remarks"><g:message code="hrEmployee.remarks.label" default="Remarks" /></label>
        <g:textArea id="remarks" name="remarks" value="${hrEmplReimbursableInstance?.remarks}"   class="form-control" />
    </div>
</div>



<%--  for details page here ------------- --%>
<div class="col-xs-12">
    <div class="form-group">

            <g:render template="details"/>

    </div>
</div>



