<%@ page import="hrms.HrLookup; hrms.HrLookupType; hrms.HrEmployeeLeave" %>


<script type="text/javascript">
    $(document).ready(function () {
        $("#applicationDate").datepicker({ dateFormat: 'dd/mm/yy'});
        $("#startDate").datepicker({ dateFormat: 'dd/mm/yy'});
        $("#endDate").datepicker({ dateFormat: 'dd/mm/yyyy'});
        $("#joiningDate").datepicker({ dateFormat: 'dd/mm/yy'});
        //$("#confirmationDate").datepicker({ dateFormat: 'dd/mm/yy'});

    });

    // search by employee name
    function showValue(obj) {
      //  debugger;
        var idNo = obj.value ;
        //alert(idNo)

        if(idNo != null) {
            $.ajax({

                type:'POST',
                /*url: '/HrEmployeeLeave/getData',*/
                url: '${request.contextPath}/HrEmployeeLeave/getEmpBasicInfo',
                dataType:"JSON",
                data: 'idNo=' + idNo,
                success:function(response) {
                    // var json = JSON.parse(response);
                    var json =  response;

                    if(json.message!=null){
                        var data = json.message.split(':');

                    //alert(data);
                    document.getElementById('cardNo').value = data[0];
                    document.getElementById('departmentName').value = data[1];
                    document.getElementById('designationName').value = data[2];

                    var data3 = data[3].split(' ');
                    //alert(data3);
                    document.getElementById('joiningDate').value = new Date(data3[0]).getDate()+'/'+(new Date(data3[0]).getMonth() + 1)+'/'+new Date(data3[0]).getFullYear();

                    var data4 = data[6].split(' ');
                    //alert(data4);
                    document.getElementById('confirmationDate').value = new Date(data4[0]).getDate()+'/'+(new Date(data4[0]).getMonth() + 1)+'/'+new Date(data4[0]).getFullYear();

                    document.getElementById('presentStatus').value = data[9];
                    document.getElementById('employeeType').value = data[10];
                    document.getElementById('employeeId').value = data[11];
                    }
                },
                error:function(err) {
                   // alert(err);
                }
            }) ;
        }

    }


    var resultReturn = '1' ;
    function myTest(){

        var fld1Val = document.getElementById('cardNo').value;
        var fld2Val = document.getElementById('startDate').value;

        alert("fld1Val "+fld1Val+" fld2Val "+fld2Val)

        if(fld1Val != '' && fld2Val != '' ) {
            $.ajax({
                type:'POST',
                url: '${request.contextPath}/HrEmployeeLeave/checkValue',            //getEmpBasicInfo
                dataType:"JSON",
                data: 'cardNo=' + fld1Val+'&startDate=' + fld2Val,
                success:function(response) {
                    // var json = JSON.parse(response);
                    var json =  response;
                    var data = json.message;
//                    resultReturn = data ;
                    verifyFormMyCall(data);
                },
                error:function(err) {
                    // alert(err);
                }
            }) ;
        }
    }

    function  verifyFormMyCall(data){
        resultReturn = data ;
    }

    function verifyForm() {

            myTest();
            alert("resultReturn "+resultReturn) ;
            if(resultReturn=='1'){    // 1 salary is processed in that month
                alert("resultReturn1 "+resultReturn);
                return false ;
            } else if (resultReturn=='0'){  // 0 salary is not processed in that month
                alert("resultReturn0 "+resultReturn);
                return true  ;
            }
        return false ;
    }






    function checkValue(fld1ID,fld2ID) {     // checkValue('cardNo','startDate')
      //  debugger;
        var resultReturn ;
        var fld1Val = document.getElementById(fld1ID).value;
        var fld2Val = document.getElementById(fld2ID).value;

       // alert("fld1Val "+fld1Val+" fld2Val "+fld2Val)

        if(fld1Val != '' && fld2Val != '' ) {
            $.ajax({
                type:'POST',
                url: '${request.contextPath}/HrEmployeeLeave/checkValue',            //getEmpBasicInfo
                dataType:"JSON",
                data: 'cardNo=' + fld1Val+'&startDate=' + fld2Val,
                success:function(response) {
                    // var json = JSON.parse(response);
                    var json =  response;
                    var data = json.message;
                     resultReturn=response ;
                    if(resultReturn=='1'){
                        document.getElementById('msgTd').innerHTML = "Sorry Salary is processed for the month";
                        document.getElementById('createBtn').style.display = "none";
                    } else{
                        document.getElementById('msgTd').innerHTML = "";
                        document.getElementById('createBtn').style.display = "block";
                    }
                },
                error:function(err) {
                   // alert(err);
                }
            }) ;
        }
    }


    // search by employee CardNo
    function showValueByCardNo(obj) {
        //  debugger;
        var idNo = obj.value ;
        //alert(idNo)

        if(idNo != null) {
            $.ajax({

                type:'POST',
                /*url: '/HrEmployeeLeave/getData',*/
                url: '${request.contextPath}/HrEmployeeLeave/getEmpBasicInfoByCardNo',
                dataType:"JSON",
                data: 'idNo=' + idNo,
                success:function(response) {
                    // var json = JSON.parse(response);
                    var json =  response;
                    var data = json.message.split(':');
                    //alert(data);
                    document.getElementById('employeeName').value = data[0];
                    document.getElementById('departmentName').value = data[1];
                    document.getElementById('designationName').value = data[2];

                    var data3 = data[3].split(' ');
                    //alert(data3);
                    document.getElementById('joiningDate').value = new Date(data3[0]).getDate()+'/'+(new Date(data3[0]).getMonth() + 1)+'/'+new Date(data3[0]).getFullYear();

                    var data4 = data[6].split(' ');
                    //alert(data4);
                    document.getElementById('confirmationDate').value = new Date(data4[0]).getDate()+'/'+(new Date(data4[0]).getMonth() + 1)+'/'+new Date(data4[0]).getFullYear();

                    document.getElementById('presentStatus').value = data[9];
                    document.getElementById('employeeType').value = data[10];
                    document.getElementById('employeeId').value = data[11];
                },
                error:function(err) {
                    // alert(err);
                }
            }) ;
        }

    }

    function getBalanceBackup()
    {
        var idNo = document.getElementById("employeeName").value;
        //alert('IDNO: '+idNo);
        var leaveTypeId =document.getElementById("leaveTypeIdHrLeave").value;
        //alert('Leave Type: '+leaveTypeId);
        var applicationDate =document.getElementById("applicationDate").value;
        //alert('Application Date: '+applicationDate);
        var employeeId = document.getElementById("employeeId").value;
        //alert('employeeId: '+employeeId);

        $.ajax({
            type:'POST',
            url: '${request.contextPath}/HrEmployeeLeave/srcRBEmpLeaveBalance',
            dataType:"JSON",
            data:'idNo='+idNo+'&leaveTypeId='+leaveTypeId+'&applicationDate='+applicationDate+'&employeeId='+employeeId,
            success:function(response) {
                //var json = JSON.parse(response);
                var json =  response;
                //alert(json.message);
                var data = json.message.split(' ');
                //alert("data[0] "+data[0]);
                document.getElementById('leaveBalance').value = data[0];
            },
            error:function(err) {
              //  alert(err);
            }
        }) ;
    }

    function getBalance()
    {
        var idNo = document.getElementById("employeeName").value;
        //alert('IDNO: '+idNo);
        var leaveTypeId =document.getElementById("leaveTypeIdHrLeave").value;
        //alert('Leave Type: '+leaveTypeId);
        var applicationDate =document.getElementById("applicationDate").value;
        //alert('Application Date: '+applicationDate);
        var employeeId = document.getElementById("employeeId").value;
        //alert('employeeId: '+employeeId);

        $.ajax({
            type:'POST',
            url: '${request.contextPath}/HrEmployeeLeave/srcRBEmpLeaveBalance',
            dataType:"JSON",
            data:'idNo='+idNo+'&leaveTypeId='+leaveTypeId+'&applicationDate='+applicationDate+'&employeeId='+employeeId,
            success:function(response) {
                //var json = JSON.parse(response);
                var json =  response;
                //alert(json.message);
                var data = json.message.split(':');
                //alert("data[0] "+data[0]);
                document.getElementById('leaveAllowed').value = data[0];
                document.getElementById('leaveAlreadyTaken').value = data[1];
                document.getElementById('leaveBalance').value = parseInt(data[0])-parseInt(data[1]);
            },
            error:function(err) {
              //  alert(err);
            }
        }) ;
    }

    function getCheckDate()// It check whether startDate is more than end date or not
    {
        var startDate=document.getElementById("startDate").value;
        var endDate=document.getElementById("endDate").value;

        if(startDate.toString().trim()!='' && endDate.toString().trim()!=''){
            var startDateArr=startDate.split('/');
            var startDateStr=startDateArr[2]+startDateArr[1];
            var startTtlDateStr=startDateArr[2]+startDateArr[1]+startDateArr[0];

            var endDateArr=endDate.split('/');
            var endDateStr=endDateArr[2]+endDateArr[1] ;
            var endTtlDateStr=endDateArr[2]+endDateArr[1]+endDateArr[0];
            //alert("Start date "+startTtlDateStr+" End date "+endTtlDateStr);
            if(parseInt(startTtlDateStr)>parseInt(endTtlDateStr)){
               // alert("Start date can not be more than end date.");
                var title="Message";
                var message = "Start date can not be more than end date.";
                showMessage(title,message);
            }else{
                if(startDateStr<endDateStr){
                  //  alert("You have entered more than one month leave.");
                  //  document.getElementById('msgTd').innerHTML = "You have entered more than one month leave.";
                    var title="Message";
                    var message = "You have entered more than one month leave.";
                    showMessage(title,message);

                }else{
                    //alert("ok ");
                    //document.getElementById('msgTd').innerHTML = "";
                }
            }
        }else{

        }


    }



    function showMessage(title,message)
    {
        var title=title;
        var message = message;
        $("<div></div>").dialog( {
            buttons: { "Ok": function () {
                $(this).dialog("close");
                return false;
            }},
            close: function (event, ui) { $(this).remove(); },
            resizable: false,
            title: title,
            modal: true,
            position:['center',200]
        }).html(message);
    }
    function getCheckValue()
    {
        var leaveTypeIdHrLeave=document.getElementById("leaveTypeIdHrLeave").value;
        //alert(leaveTypeIdHrLeave)
        if(leaveTypeIdHrLeave=='3115'){//Leave Without Pay
            checkValue('cardNo','startDate')
        }
        else if(leaveTypeIdHrLeave=='3101'){//Sick Leave (Halfpay)
            checkValue('cardNo','startDate')
        }
        else if(leaveTypeIdHrLeave=='9411'){
            checkValue('cardNo','startDate')
        }
        else{
            document.getElementById('msgTd').innerHTML = "";
            document.getElementById('createBtn').style.display = "block";
        }
    }


   function getDays()
   {
      // debugger;

       var startDate = document.getElementById("startDate").value;
       //alert('startDate: '+startDate);
       var endDate =document.getElementById("endDate").value;
       //alert('endDate: '+endDate);

       var holidayTypeId =document.getElementById("leaveTypeIdHrLeave").value;
       //alert('Holiday TypeId: '+holidayTypeId);

       $.ajax({
           type:'POST',
           url: '${request.contextPath}/HrEmployeeLeave/getHolidayCount',
           dataType:"JSON",
           data:'startDate='+startDate+'&endDate='+endDate+'&holidayTypeId='+holidayTypeId,
           success:function(response) {
               //var json = JSON.parse(response);
               var json =  response;
               var  data = json.message.split(' ');
               var holidayDays = data[0];
               //alert('holidayDays : '+holidayDays);
               var startingDate = $('#startDate').val().split("/");
               var endingDate = $('#endDate').val().split("/");
               var dateFrom = new Date(startingDate[2],(startingDate[1]-1),startingDate[0]);
               var dateTo = new Date(endingDate[2],(endingDate[1]-1),endingDate[0]);
               var oneDay = 1000*60*60*24;

               if($('#startDate').val() != "" && $('#endDate').val() != "" && dateFrom <= dateTo) {

                   //var totalDays = Math.ceil((dateTo.getTime() - dateFrom.getTime())/(oneDay))+1;
                   $('#totalDays').val((Math.ceil((dateTo.getTime() - dateFrom.getTime())/(oneDay))+1) - parseInt(holidayDays));

                   if (parseInt(document.getElementById('totalDays').value) <= parseInt(document.getElementById('leaveBalance').value))
                   {
                       $('#totalDays').val((Math.ceil((dateTo.getTime() - dateFrom.getTime())/(oneDay))+1) - parseInt(holidayDays));
                   }
                   else{
                       alert('Leave Balance is not available for Selected Leave Category...');
                       //document.getElementById('totalDays').value = "";
                       document.getElementById('totalDays').value = parseInt(document.getElementById('totalDays').value) ;
                   }

               }
               else {
                  // document.getElementById('totalDays').value = "";
                   document.getElementById('totalDays').value = parseInt(document.getElementById('totalDays').value) ;
                   /*alert('end date could not less than start date...');*/
               }
           },
           error:function(err) {
              // alert(err);
           }
       }) ;
   }

</script>


<script type="text/javascript">

    // auto employee name searching
    function autoEmployeeName(fldId) {
        $('#'+fldId).autocomplete({
            source:'<g:createLink controller='HrEmployeeLeave'  action='autoEmployeeNameByDepartmentWise' />'
        });
    }

    // auto employee cardNo searching
    function getEmployeeCardNo(fldId) {
        $('#'+fldId).autocomplete({
            source:'<g:createLink controller='HrEmployeeLeave' action='autoEmployeeCardNoByDepartmentWise'/>'
        });
    }
</script>


<div class="col-xs-4">
    <div class="form-group">
        <label for="cardNo"><g:message code="hrEmployeeLeave.cardNo.label" default="Employee ID" /></label>

        <g:if test="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id == null}">
            <g:textField id="cardNo" name="cardNo" maxlength="25" onclick="getEmployeeCardNo(this.id)" onkeyup="showValueByCardNo(this);" onblur="showValueByCardNo(this);"
                         value="${hrEmployeeLeaveInstance?.cardNo}"  style="text-align: left;font-weight: bold;" class="form-control"/>
        </g:if>
        <g:else>
            <g:textField id="cardNo" name="cardNo" maxlength="25"  value="${hrEmployeeLeaveInstance?.cardNo}"  style="text-align: left;font-weight: bold;" class="form-control" readonly="readonly"/>
        </g:else>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="employeeName"><g:message code="hrEmployeeLeave.employeeName.label" default="Employee's Name" /></label>
        <g:if test="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id == null}">
        %{--<g:select id="employeeIdHrEmployee" name="employeeIdHrEmployee.id" from="${hrms.HrEmployee.list()}" optionKey="id" required="" noSelection="${['null':'-Select One-']}" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}" class="list_sized_field" onchange="showValue(this);"/>--}%
            <g:textField name='employeeName' id='employeeName' value='' onclick="autoEmployeeName(this.id)" onkeyup="showValue(this);" onblur="showValue(this);"  class="form-control"/>
        </g:if>
        <g:else>
        %{--<g:select id="employeeIdHrEmployee" name="employeeIdHrEmployee.id" from="${hrms.HrEmployee.findById(hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id)}" optionKey="id" required="" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}" class="list_sized_field"/>--}%
            <g:textField name='employeeName' id='employeeName' value='${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.employeeName}' readonly="readonly" class="form-control"/>
        </g:else>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="designationName"><g:message code="hrEmployeeLeave.designationName.label" default="Designation" /></label>
        <g:textField id="designationName" name="designationName" maxlength="100"
                     value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.designationIdHrDesignation?.designationName}" readonly="readonly" style="background-color: #dcdcdc;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="departmentName"><g:message code="hrEmployeeLeave.departmentName.label" default="Department" /></label>
        <g:textField id="departmentName" name="departmentName" maxlength="100"
                     value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.departmentIdHrDepartment?.departmentName}"  readonly="readonly" style="background-color: #dcdcdc;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="joiningDate"><g:message code="hrEmployeeLeave.joiningDate.label" default="Joining Date" /></label>
        <g:textField id="joiningDate" name="joiningDate" maxlength="25"
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.employeeIdHrEmployee?.joiningDate)}" disabled="disabled" style="background-color: #dcdcdc;text-align: center;"  class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="confirmationDate"><g:message code="hrEmployeeLeave.confirmationDate.label" default="Confirmation Date" /></label>
        <g:textField id="confirmationDate" name="confirmationDate" maxlength="25"
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.employeeIdHrEmployee?.confirmationDate)}" disabled="disabled" style="background-color: #dcdcdc;text-align: center;"   class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="presentStatus"><g:message code="hrEmployeeLeave.presentStatus.label" default="Present Status" /></label>
        <g:textField id="presentStatus" name="presentStatus"
                     value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.presentStatusIdHrLookup?.lookupValue}" readonly="readonly" style="background-color: #dcdcdc;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="employeeType"><g:message code="hrEmployeeLeave.employeeType.label" default="Type of Employee" /></label>
        <g:textField id="employeeType" name="employeeType"
                     value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.employeeTypeIdHrLookup?.lookupValue}" readonly="readonly" style="background-color: #dcdcdc;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="applicationDate"><g:message code="hrEmployeeLeave.applicationDate.label" default="Application Date" /></label>
        <g:textField id="applicationDate" name="applicationDate" required="" value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.applicationDate !=null?hrEmployeeLeaveInstance?.applicationDate:new Date())}" style="text-align: center;" readonly="readonly" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="leaveTypeIdHrLeave"><g:message code="hrEmployeeLeave.leaveTypeIdHrLeave.label" default="Leave Type" /></label>

        <g:if test="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id == null}">
            <g:select id="leaveTypeIdHrLeave" name="leaveTypeIdHrLeave.id" from="${hrms.HrLeave.findAll("from HrLeave order by leaveType")}" optionKey="id"
                      optionValue="leaveType" required="" value="${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave?.id}"
                      noSelection="['':'-Select One-']" onchange="getBalance();getCheckValue();" class="form-control"/>
        </g:if>
        <g:else>
            <g:select id="leaveTypeIdHrLeave" name="leaveTypeIdHrLeave.id" from="${hrms.HrLeave.findAll("from HrLeave order by leaveType")}" optionKey="id"
                      optionValue="leaveType" required="" value="${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave?.id}"
                      noSelection="['':'-Select One-']"  class="form-control" disabled="disabled"/>

        </g:else>
    </div>
</div>

<div class="col-xs-8">
    <div class="form-group">
        <label for="emptyFld" style="color: transparent">lemptyFldLbl</label>
        <g:textField  id="emptyFld" name="emptyFld" class="form-control" style="border-color: transparent" readonly="readonly"/>
    </div>
</div>


<div class="col-xs-4">
    <div class="form-group">
        <label for="leaveAllowed"><g:message code="hrEmployeeLeave.leaveAllowed.label" default="Leave Allowed (Days)" /></label>
        <g:textField  id="leaveAllowed" name="leaveAllowed"  value="${hrEmployeeLeaveInstance?.leaveAllowed != null?hrEmployeeLeaveInstance?.leaveAllowed:null}" readonly="readonly" style="background-color: #dcdcdc;text-align: center;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="leaveAlreadyTaken"><g:message code="hrEmployeeLeave.leaveAlreadyTaken.label" default="Leave Already Taken (Days)" /></label>
        <g:textField  id="leaveAlreadyTaken" name="leaveAlreadyTaken"  value="${hrEmployeeLeaveInstance?.leaveAlreadyTaken != null?hrEmployeeLeaveInstance?.leaveAlreadyTaken:null}" readonly="readonly" style="background-color: #dcdcdc;text-align: center;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="leaveBalance"><g:message code="hrEmployeeLeave.leaveBalance.label" default="Leave Balance (Days)" /></label>
        <g:textField  id="leaveBalance" name="leaveBalance"  value="${hrEmployeeLeaveInstance?.leaveBalance != null?hrEmployeeLeaveInstance?.leaveBalance:null}" readonly="readonly" style="background-color: #dcdcdc;text-align: center;" class="form-control"/>
    </div>
</div>



<div class="col-xs-4">
    <div class="form-group">
        <label for="startDate"><g:message code="hrEmployeeLeave.startDate.label" default="Start Date" /></label>
        <g:textField id="startDate" name="startDate"  required=""
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.startDate)}"  onchange="getDays();getCheckDate();"  onkeyup="getDays();getCheckValue();" onblur="getDays();getCheckValue();"  style="text-align: center;" class="startDate form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="endDate"><g:message code="hrEmployeeLeave.endDate.label" default="End Date" /></label>
        <g:textField id="endDate" name="endDate"  required=""
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.endDate)}" onchange="getDays();getCheckDate();" onkeyup="getDays();getCheckValue();" onblur="getDays();getCheckValue();" style="text-align: center;" class="endDate form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="totalDays"><g:message code="hrEmployeeLeave.totalDays.label" default="Total Days" /></label>
        <g:textField  id="totalDays"  name="totalDays" required=""
                      value="${hrEmployeeLeaveInstance?.totalDays}" readonly="readonly"  style="text-align: center;" class="form-control"/>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="totalDays" id="msgTd" style="color: red;"></label>
    </div>
</div>



<div class="col-xs-10">
    <div class="form-group">
        <label for="leaveReason"><g:message code="hrEmployeeLeave.leaveReason.label" default="Leave Reason" /></label>
        <g:textArea name="leaveReason"  cols="20" rows="5" value="${hrEmployeeLeaveInstance?.leaveReason}" class="form-control"/>
    </div>
</div>

<div class="col-xs-10">
    <div class="form-group">
        <label for="remarks"><g:message code="hrEmployeeLeave.remarks.label" default="Remarks" /></label>
        <g:textArea name="remarks"  cols="20" rows="5" value="${hrEmployeeLeaveInstance?.remarks}" class="form-control"/>
    </div>
</div>

<g:hiddenField id="employeeId" name="employeeIdHrEmployee.id"  value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}"/>





%{--

<br>
       <h3>Employee's Leave Information</h3>
       <table class="promint_block">
           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Employee ID:</td>
               <td class="employeeTd">
                <g:textField id="cardNo" name="cardNo" maxlength="25" onclick="getEmployeeCardNo(this.id)" onkeyup="showValueByCardNo(this);" onblur="showValueByCardNo(this);" value="${hrEmployeeLeaveInstance?.cardNo}"  style="text-align: left;font-weight: bold;" class="mid-size-input"/>
            </td>
            <td class="employeeTd">Employee's Name:</td>
            <td>
                --}%%{--<g:select id="employeeIdHrEmployee" name="employeeIdHrEmployee.id" from="${hrms.HrEmployee.list()}" optionKey="id" required="" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}" class="many-to-one" onchange="showValue(this)" noSelection="['null':'-Select One-']"/>--}%%{--
                <g:if test="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id == null}">
                    --}%%{--<g:select id="employeeIdHrEmployee" name="employeeIdHrEmployee.id" from="${hrms.HrEmployee.list()}" optionKey="id" required="" noSelection="${['null':'-Select One-']}" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}" class="list_sized_field" onchange="showValue(this);"/>--}%%{--
                    <g:textField name='employeeName' id='employeeName' value='' onclick="autoEmployeeName(this.id)" onkeyup="showValue(this);" onblur="showValue(this);"  class="full-width-input"/>
                </g:if>
                <g:else>
                    --}%%{--<g:select id="employeeIdHrEmployee" name="employeeIdHrEmployee.id" from="${hrms.HrEmployee.findById(hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id)}" optionKey="id" required="" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}" class="list_sized_field"/>--}%%{--
                    <g:textField name='employeeName' id='employeeName' value='${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.employeeName}' readonly="readonly" class="full-width-input"/>
                </g:else>
            </td>
           </tr>

           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Designation:</td>
               <td class="employeeTd">
                <g:textField id="designationName" name="designationName" maxlength="100" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.designationIdHrDesignation?.designationName}" readonly="readonly" style="background-color: #dcdcdc;" class="mid-size-input"/>
            </td>
               <td class="employeeTd">Department:</td>
               <td>
                   <g:textField id="departmentName" name="departmentName" maxlength="100" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.departmentIdHrDepartment?.departmentName}"  readonly="readonly" style="background-color: #dcdcdc; width: 83%;" class="mid-size-input"/>
               </td>
        </tr>

           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Joining Date:</td>
               <td class="employeeTd">
                <g:textField id="joiningDate" name="joiningDate" maxlength="25" value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.employeeIdHrEmployee?.joiningDate)}" disabled="disabled" style="background-color: #dcdcdc;text-align: center;" class="mid-size-input"/>
            </td>
               <td class="employeeTd">Confirmation Date:</td>
            <td>
                <g:textField id="confirmationDate" name="confirmationDate" maxlength="25" value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.employeeIdHrEmployee?.confirmationDate)}" disabled="disabled" style="background-color: #dcdcdc;text-align: center;" class="mid-size-input"/>
            </td>
        </tr>

           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Present Status:</td>
               <td class="employeeTd">
                <g:textField id="presentStatus" name="presentStatus" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.presentStatusIdHrLookup?.lookupValue}" readonly="readonly" style="background-color: #dcdcdc;" class="mid-size-input"/>
            </td>
            <td class="employeeTd">Type of Employment:</td>
            <td>
                <g:textField id="employeeType" name="employeeType" value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.employeeTypeIdHrLookup?.lookupValue}" readonly="readonly" style="background-color: #dcdcdc;" class="mid-size-input"/>
            </td>
           </tr>
           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Application Date:</td>
               <td class="employeeTd">
                <g:textField id="applicationDate" name="applicationDate" required="" value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.applicationDate !=null?hrEmployeeLeaveInstance?.applicationDate:new Date())}" style="text-align: center;" class="mid-size-input"/>
            </td>
               <td class="employeeTd">Leave Type:</td>
               <td>
                   --}%%{--<g:select id="leaveTypeIdHrLeave" name="leaveTypeIdHrLeave.id" from="${hrms.HrLeave.list()}" optionKey="id" optionValue="leaveType" required="" value="${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave?.id}" class="list_sized_field" noSelection="['':'-Select One-']" onchange="getBalance();"/>--}%%{--
                   <g:select id="leaveTypeIdHrLeave" name="leaveTypeIdHrLeave.id" from="${hrms.HrLeave.findAll("from HrLeave order by leaveType")}" optionKey="id" optionValue="leaveType" required="" value="${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave?.id}" class="list_sized_field" noSelection="['':'-Select One-']" onchange="getBalance();getCheckValue();"/>
               </td>
        </tr>

           <tr class="captionSpaceFirst">

               <td class="captionSpaceFirst">--}%%{--Salary Effect Type:--}%%{--</td>
               <td class="employeeTd">
                       --}%%{--<g:textField id="salaryEffectType" name="salaryEffectType" value="${hrEmployeeLeaveInstance?.salaryEffectType}"/>--}%%{--
                       --}%%{--commented below 16/05/2013--}%%{--
                       --}%%{--<g:select id="salaryEffectTypeIdHrLookup" name="salaryEffectTypeIdHrLookup.id" from="${HrLookup.findAllByHrLookupTypeIdLookupType(HrLookupType.findByLookupType('SALARY EFFECT TYPE'))}" optionKey="id"  value="${hrEmployeeLeaveInstance?.salaryEffectTypeIdHrLookup?.id}" class="list_sized_field" noSelection="['null':'-Select One-']"/>--}%%{--
                   </td>
               <td class="employeeTd"> Leave Balance:</td>
                   <td>
                       <g:textField  id="leaveBalance" name="leaveBalance"  value="${hrEmployeeLeaveInstance?.leaveBalance != null?hrEmployeeLeaveInstance?.leaveBalance:null}" readonly="readonly" style="background-color: #dcdcdc;text-align: center;width: 40px;"/> <span> Days</span>
                   </td>
               </tr>

           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Start Date:</td>
               <td class="employeeTd">                                                                                                                                                                                                                --}%%{-- getCheckDate();--}%%{--
                   <g:textField id="startDate" name="startDate" class="startDate" required="" value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.startDate)}"  onchange="getDays();getCheckDate();"  onkeyup="getCheckValue();" onblur="getCheckValue();"  style="text-align: center;width: 180px;padding: 5px 5px;"/>
               </td>
               <td class="employeeTd">End Date:</td>
               <td>                                                                                                                                                                                                                          --}%%{--getCheckDate();--}%%{--
                   <g:textField id="endDate" name="endDate" class="endDate" required="" value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeLeaveInstance?.endDate)}" onchange="getDays();getCheckDate();" onkeyup="getCheckValue();" onblur="getCheckValue();" style="text-align: center;width: 180px;padding: 5px 5px;"/>
                   &nbsp;Total Days:&nbsp;&nbsp;
                   --}%%{--<g:textField  id="totalDays"  name="totalDays" required="" value="${hrEmployeeLeaveInstance?.totalDays != null?hrEmployeeLeaveInstance?.totalDays:null}" readonly="readonly"  style="background-color: #dcdcdc;text-align: center;width: 40px;"/>--}%%{--
                   <g:textField  id="totalDays"  name="totalDays" required="" value="${hrEmployeeLeaveInstance?.totalDays}" readonly="readonly"  style="background-color: #dcdcdc;text-align: center;width: 40px;"/>
               </td>
           </tr>

           --}%%{--<tr>
               <td>Leave With Pay:</td>
               <td>
                   <g:textField id="leaveWithPayDays" name="leaveWithPayDays" class="lwp"  value="${hrEmployeeLeaveInstance?.leaveWithPayDays != null?hrEmployeeLeaveInstance?.leaveWithPayDays:0}" style="text-align: center;"/><span> Days</span>
               </td>
               <td>Leave Without Pay:</td>
               <td>
                   <g:textField id="leaveWithoutPayDays" name="leaveWithoutPayDays" class="lwop"  value="${hrEmployeeLeaveInstance?.leaveWithoutPayDays != null?hrEmployeeLeaveInstance?.leaveWithoutPayDays:0}" style="text-align: center;"/><span> Days</span>
               </td>
           </tr>--}%%{--


           <tr class="captionSpaceFirst">
           <td class="captionSpaceFirst">Leave Reason:</td>
           <td class="employeeTd">
                   <g:textArea name="leaveReason" style="height: 45px;width: 360px;" cols="20" rows="5" value="${hrEmployeeLeaveInstance?.leaveReason}"/>
           </td>
           <td colspan="2" id="msgTd" style="color: red;">

           </td>
           </tr>

           <tr class="captionSpaceFirst">
               <td class="captionSpaceFirst">Remarks:</td>
               <td class="employeeTd">
                   <g:textArea name="remarks" style="height: 45px;width: 360px;" cols="20" rows="5" value="${hrEmployeeLeaveInstance?.remarks}"/>
               </td>
           </tr>

</table>
       <g:hiddenField id="employeeId" name="employeeIdHrEmployee.id"  value="${hrEmployeeLeaveInstance?.employeeIdHrEmployee?.id}"/>
</div>--}%






