
<html>

<head>
    <meta name="layout" content="main"/>
    <title>Payroll Process</title>

    %{--<g:javascript src="jquery-1.6.2.js"></g:javascript>--}%

    <script type="text/javascript">

        //For date field (Showing Calendar) Start
       /* $('.dtl-dt').live('focus', function () {
            $(".dtl-dt").datepicker({ dateFormat: 'mm/yy'});
        });*/

        // get Calendar periodName
        function getPeriodName() {

            document.getElementById('salaryMonth').value = "";

            var yearName = document.getElementById('yearName').value;

            $.ajax({
                type:"POST",
                url:'${request.contextPath}/HrPayrollProcess/getPeriodName',
                dataType:'JSON',
                data:'yearName='+yearName,
                success:function(data) {
                    var json = eval("(" + data.message + ")");
                    var ddl = $("#periodName");
                    ddl.empty();
                    ddl.append("<option value='null'>-Select One-</option>");
                    jQuery.each(json, function() {
                        ddl.append("<option value='" + this.id + "'>" + this.value + "</option>");
                    });
                },
                error:function(err) {
                   // alert(err);
                }
            });
        }

        function getPeriodNameRB() {

            document.getElementById('salaryMonthRB').value = "";

            var yearName = document.getElementById('yearNameRB').value;

            $.ajax({
                type:"POST",
                url:'${request.contextPath}/HrPayrollProcess/getPeriodName',
                dataType:'JSON',
                data:'yearName='+yearName,
                success:function(data) {
                    var json = eval("(" + data.message + ")");
                    var ddl = $("#periodNameRB");
                    ddl.empty();
                    ddl.append("<option value='null'>-Select One-</option>");
                    jQuery.each(json, function() {
                        ddl.append("<option value='" + this.id + "'>" + this.value + "</option>");
                    });
                },
                error:function(err) {
                    // alert(err);
                }
            });
        }

        // get Salary month
        function getMonthName() {
            var periodName = document.getElementById('periodName').value;

            $.ajax({
                type:"POST",
                url:'${request.contextPath}/HrPayrollProcess/getMonthName',
                dataType:'JSON',
                data:'periodName='+periodName,
                success:function(response) {
                    var json = response;
                    //var data = json.message.split(":");
                    var data = json.message;
                    $("#salaryMonth").val(data);
                },
                error:function(err) {
                    // alert(err);
                }
            });
        }

        function getMonthNameRB() {
            var periodName = document.getElementById('periodNameRB').value;

            $.ajax({
                type:"POST",
                url:'${request.contextPath}/HrPayrollProcess/getMonthName',
                dataType:'JSON',
                data:'periodName='+periodName,
                success:function(response) {
                    var json = response;
                    //var data = json.message.split(":");
                    var data = json.message;
                    $("#salaryMonthRB").val(data);
                    getSalaryTypeRB(data);
                },
                error:function(err) {
                    // alert(err);
                }
            });
        }

        function getSalaryTypeRB(val) {
           // alert("getSalaryTypeRB "+val);
          //  var salaryMonth = document.getElementById('salaryMonthRB').value;
            var salaryMonth = val.trim();

            $.ajax({
                type:"POST",
                url:'${request.contextPath}/HrPayrollProcess/getSalaryType',
                dataType:'JSON',
                data:'salaryMonth='+salaryMonth,
                success:function(data) {
                    var json = eval("(" + data.message + ")");
                    var ddl = $("#salaryTypeRB");
                    ddl.empty();
                    ddl.append("<option value=''>-Select One-</option>");
                    jQuery.each(json, function() {
                        ddl.append("<option value='" + this.id + "'>" + this.value + "</option>");
                    });
                },
                error:function(err) {
                    // alert(err);
                }
            });
        }


        // Payroll Process
        function payrollProcess() {

            var salaryMonth = document.getElementById('salaryMonth').value;
            var attnFromDate = document.getElementById('attnFromDate').value;
            var attnToDate = document.getElementById('attnToDate').value;

            if(salaryMonth != "" && attnFromDate != "" && attnToDate != "") {

                var status = confirm("Do you want to Process Continue?");

                if (status == true)
                {
                    $.ajax({
                        type:"POST",
                        url:'${request.contextPath}/HrPayrollProcess/payrollProcess',
                        dataType:'JSON',
                        data:'salaryMonth='+salaryMonth+'&attnFromDate='+attnFromDate+'&attnToDate='+attnToDate,
                        success:function(response) {
                            var json = response;
                            //var data = json.message.split(":");
                            var data = json.message;
                            var message = data;
                            var title="Execution Status";
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
                        },
                        error:function(err) {
                            // alert(err);
                        }
                    });
                }
                else
                {
                    null;
                }
            }
            else {
                var message = "Fill Up Text Fields";
                var title="Field Information";
                $("<div></div>").dialog( {
                    buttons: { "Ok": function () {
                        $(this).dialog("close");
                        //$("#fromDate").attr("value","").focus();
                        return false;
                    }},
                    close: function (event, ui) { $(this).remove(); },
                    resizable: false,
                    title: title,
                    modal: true,
                    position:['center',200]
                }).html(message);
            }
        }

        // Payroll Rollback Process
        function payrollRollback() {


            var salaryMonth = document.getElementById('salaryMonthRB').value;
            var salaryType = document.getElementById('salaryTypeRB').value;

            if(salaryMonth != "" && salaryType != 'null') {

                var status = confirm("Do you want to Rollback Continue?");

                if (status == true)
                {
                    $.ajax({
                        type:"POST",
                        url:'${request.contextPath}/HrPayrollProcess/payrollRollback',
                        dataType:'JSON',
                        data:'salaryMonth='+salaryMonth+'&salaryType='+salaryType,
                        success:function(response) {
                            var json = response;
                            //var data = json.message.split(":");
                            var data = json.message;
                            var message = data;
                            var title="Execution Status";
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
                        },
                        error:function(err) {
                            // alert(err);
                        }
                    });
                }
                else
                {
                    null;
                }
            }
            else {
                var message = "Fill Up Text Fields";
                var title="Field Information";
                $("<div></div>").dialog( {
                    buttons: { "Ok": function () {
                        $(this).dialog("close");
                        //$("#fromDate").attr("value","").focus();
                        return false;
                    }},
                    close: function (event, ui) { $(this).remove(); },
                    resizable: false,
                    title: title,
                    modal: true,
                    position:['center',200]
                }).html(message);
            }
        }

    </script>

<script language="javascript" type="text/javascript">

    function showReportForm(v){
        var id=v
        var idStr="noRptType:processTbl:rollbackTbl";    //just give the id in the last portion of the string if new table added..
        var subStr=idStr.split(":")

        for(var i=0;i<subStr.length;i++ ){

            if(subStr[i]==id){

                document.getElementById(id).style.display='block';
                if(subStr[i]=='rollbackTbl'){
                   // salaryTypeList("salaryTypeRB")
                  //  semesterList("startingSemesterRB")
                    //yearList("startingYearRB")
                }

            }
            else if(subStr[i]==id && subStr[i]=='rollbackTbl'){

                document.getElementById(id).style.display='block';
               // salaryTypeList("salaryTypeRB")
               // semesterList("startingSemesterRB")
              // yearList("startingYearRB")

            }
            else{

                document.getElementById(subStr[i]).style.display='none';
            }
        }

    }


    function getDateRange() {
        var periodName = document.getElementById('periodName').value;

        $.ajax({
            type:"POST",
            url:'${request.contextPath}/HrPayrollProcess/getDateRange',
            dataType:'JSON',
            data:'periodName='+periodName,
            success:function(response) {
                var json = response;
                var data = json.message.split(":");
                $("#attnFromDate").val(data[0]);
                $("#attnToDate").val(data[1]);
            },
            error:function(err) {
                // alert(err);
            }
        });
    }

</script>

<SCRIPT type="text/javascript">
    /* code to run on page load Start*/
    function addLoadEvent(func) {
        var oldonload = window.onload;
        if (typeof window.onload != 'function') {
            window.onload = func;
        } else {
            window.onload = function() {
                if (oldonload) {
                    oldonload();
                }
                func();
            }
        }
    }
    addLoadEvent(function() {
        /* more code to run on page load */

        showReportForm("noRptType")  ;

    });
    /* code to run on page load  End*/

</SCRIPT>

</head>

<body>

%{--New Design Start For that just copy and paste below till maruf--}%
<div id="show-afmBankInfo" class="content scaffold-show myDiv" role="main">
    <div class="inner-wrapper">
        <div ALIGN="CENTER" >
            <div class="page-title-block">

                <div class="clear"></div>

            </div>
    %{--maruf--}%
    <table id="mainTab"  border="0" style="width: 100%; height: 100%; background: #fff; padding: 1%;border: 1px solid #ccc;">
        <tr>
            <td   valign="top" style="vertical-align: top;">

                <div class="col-xs-4">
                    <div class="form-group">
                        <input name="rdb" id="rdb" type="radio" style="margin-left: 5%;" value="processTbl"  onclick="showReportForm(this.value);"/> Process
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <input name="rdb" id="rdb" type="radio" value="rollbackTbl"  onclick="showReportForm(this.value);"/> Roll Back
                    </div>
                </div>
            </td>
        </tr>
     </table>

    <table id="noRptType" style="display:none"  border="0" style="width: 100%; height: 100%; background: #fff;  border: 1px solid #ccc;">
        <tr>
            <td >Please Select One. </td>
        </tr>
    </table>
    <table id="processTbl" style="display:none"  border="0" style="width: 100%; height: 100%; background: #fff;  border: 1px solid #ccc;">
        <tr>
            <td>
                <div class="col-xs-12">
                    <div class="form-group" id="LoadingImage" style="float: left; display: none; font-family: arial;font-weight: bold;font-size: 12px; color: #008000;">
                        Processing...<img src="${resource(dir: 'images', file: 'loadingImg.gif')}" alt=""/>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="yearName"><g:message code="hrPayrollProcess.yearName.label" default="Calendar Year : " /></label>
                        <g:select name="yearName" id="yearName"  from="${hrms.HrCalendar.list()}" optionKey="id" optionValue="calendarYear" noSelection="['null':'-Select One-']" onchange="getPeriodName();" style="font-weight: bold;" class="form-control"></g:select>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="periodName"><g:message code="hrPayrollProcess.periodName.label" default="Period Name : " /></label>
                        <g:select name="periodName" id="periodName"  from="" optionKey="id" optionValue="periodName" noSelection="['null':'-Select One-']" onchange="getMonthName();getDateRange();" style="font-weight: bold;" class="form-control"></g:select>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="salaryMonth"><g:message code="hrPayrollProcess.salaryMonth.label" default="Salary Month/Year : " /></label>
                        <g:textField name="salaryMonth" id="salaryMonth" value="" readonly="readonly" style="text-align: center;padding: 5px;font-weight: bold;" class="form-control"></g:textField> (mm/yyyy)
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="attnFromDate"><g:message code="hrPayrollProcess.attnFromDate.label" default="From : " /></label>
                        <g:textField name="attnFromDate" id="attnFromDate"   required="" value=""  style="text-align: center;padding: 5px;font-weight: bold;" readonly="true" class="form-control"></g:textField>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="attnToDate"><g:message code="hrPayrollProcess.attnToDate.label" default="To : " /></label>
                        <g:textField name="attnToDate" id="attnToDate"   required="" value=""  style="text-align: center;padding: 5px;font-weight: bold;" readonly="true" class="form-control"></g:textField>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="form-group">
                        <input type="button" id="salaryProcess" name="salaryProcess" value="Process" onclick="payrollProcess();" class="btn btn-success" />
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <table id="rollbackTbl" style="display:none"  border="0" style="width: 100%; height: 100%; background: #fff;  border: 1px solid #ccc;">
        <tr>
            <td>
                <div class="col-xs-12">
                    <div class="form-group" id="LoadingImageRB" style="float: left; display: none; font-family: arial;font-weight: bold;font-size: 12px; color: #008000;">
                        Processing...<img src="${resource(dir: 'images', file: 'loadingImg.gif')}" alt=""/>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="yearNameRB"><g:message code="hrPayrollProcess.yearNameRB.label" default="Calendar Year : " /></label>
                        <g:select name="yearNameRB" id="yearNameRB"  from="${hrms.HrCalendar.list()}" optionKey="id" optionValue="calendarYear" noSelection="['null':'-Select One-']" onchange="getPeriodNameRB();" style="font-weight: bold;" class="form-control"></g:select>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="periodNameRB"><g:message code="hrPayrollProcess.periodNameRB.label" default="Period Name : " /></label>
                        <g:select name="periodNameRB" id="periodNameRB"  from="" optionKey="id" optionValue="periodName" noSelection="['null':'-Select One-']" onchange="getMonthNameRB();" style="font-weight: bold;" class="form-control"></g:select>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="salaryMonthRB"><g:message code="hrPayrollProcess.salaryMonthRB.label" default="Salary Month/Year : " /></label>
                        <g:textField name="salaryMonthRB" id="salaryMonthRB" value="" readonly="readonly" style="text-align: center;padding: 5px;font-weight: bold;" class="form-control"></g:textField> (mm/yyyy)
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="salaryTypeRB"><g:message code="hrPayrollProcess.salaryTypeRB.label" default="Salary Type : " /></label>
                        <select  required=""  id="salaryTypeRB" name="salaryTypeRB" class="form-control">
                            <option value=""> -- Select -- </option>
                            <g:each in="${semesterListPR}" var="semesterInfoPR">
                                <option value="${semesterInfoPR.SEMESTER}">${semesterInfoPR.SEMESTER}</option>
                            </g:each>
                        </select>
                    </div>
                </div>

                <div class="col-xs-12">
                    <div class="form-group">
                        <input type="button" id="salaryRollback" name="salaryRollback" value="Rollback" onclick="payrollRollback();" class="btn btn-success" />
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <table id="rollbackTbl" class="promint_block" style="width: 100%;display: none;">
        <tr>
            <td colspan="3" style="text-align: left; padding-left: 10%">
                <div id="LoadingImageRB" style="float: left; display: none; font-family: arial;font-weight: bold;font-size: 12px; color: #008000;">
                    Processing...<img src="${resource(dir: 'images', file: 'loadingImg.gif')}" alt=""/>
                </div>
            </td>
        </tr>
        <tr>
            <td style="text-align: right; width: 50%">Calendar Year </td>
            <td> :</td>
            <td>
                <g:select name="yearNameRB" id="yearNameRB"  from="${hrms.HrCalendar.list()}" optionKey="id" optionValue="calendarYear" noSelection="['null':'-Select One-']" onchange="getPeriodNameRB();" style="font-weight: bold;"></g:select>
            </td>
        </tr>
        <tr>
            <td style="text-align: right">Period Name </td>
            <td> :</td>
            <td>
                <g:select name="periodNameRB" id="periodNameRB"  from="" optionKey="id" optionValue="periodName" noSelection="['null':'-Select One-']" onchange="getMonthNameRB();" style="font-weight: bold;"></g:select>
            </td>
        </tr>

        <tr>
            <td style="text-align: right; width: 15%">Salary Month/Year</td>
            <td> :</td>
            <td>
                <g:textField name="salaryMonthRB" id="salaryMonthRB" value="" readonly="readonly" style="text-align: center;padding: 5px;font-weight: bold;width: 100px;"></g:textField> (mm/yyyy)
            </td>
        </tr>
        <tr>
            <td style="text-align: right; width: 15%">Salary Type</td>
            <td> :</td>
            <td>
                <select  required=""  id="salaryTypeRB" name="salaryTypeRB" >
                    <option value=""> -- Select -- </option>
                    <g:each in="${semesterListPR}" var="semesterInfoPR">
                        <option value="${semesterInfoPR.SEMESTER}">${semesterInfoPR.SEMESTER}</option>
                    </g:each>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="text-align: left; padding-left: 10%">
                <input type="button" id="salaryRollback" name="salaryRollback" value="Rollback" onclick="payrollRollback();" class="add-btn" />
            </td>
        </tr>
    </table>


       </td>
     </tr>
  </table>
</div>

    </div>
</div>
</body>
</html>