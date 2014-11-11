<%--
  Created by IntelliJ IDEA.
  User: kmmrashid
  Date: 3/24/33
  Time: 13:52 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="accounts.AfmVoucher" contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <meta name="layout" content="main"/>
    <title>Report</title>

    <script type="text/javascript">

        //For date field (Showing Calendar) Start
        $('.dtl-dt').live('focus', function () {
            $(".dtl-dt").datepicker({ dateFormat: 'dd/mm/yy'});
        });
        //For date field (Showing Calendar) End

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
                    },
                    error:function(err) {
                        // alert(err);
                    }
                }) ;
            }

        }
    </script>

    <script type="text/javascript">

        //For date field (Showing Calendar) Start
        /* $('.dtl-dt').live('focus', function () {
         $(".dtl-dt").datepicker({ dateFormat: 'mm/yy'});
         });*/

        // get Calendar periodName
        function getPeriodName() {

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

        function getDateRange(fld1,fld2,fld3) {               //'periodName','P_FROM_DATE','P_TO_DATE'
            var periodName = document.getElementById(fld1).value;

            $.ajax({
                type:"POST",
                url:'${request.contextPath}/HrAttendanceProcessForm/getDateRangeNew',
                dataType:'JSON',
                data:'periodName='+periodName,
                success:function(response) {
                    var json = response;
                    var data = json.message.split(":");
                    $("#"+fld2).val(data[0]);
                    $("#"+fld3).val(data[1]);
                },
                error:function(err) {
                    // alert(err);
                }
            });
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
                        }
                    },
                    error:function(err) {
                        // alert(err);
                    }
                }) ;
            }

        }

    </script>
    <script language="javascript" type="text/javascript">

        function showReportForm(v){
            var id=v
            var idStr="noRptType:rptE1:rptE2:rptB1";    //just give the id in the last portion of the string if new table added..
            var subStr=idStr.split(":")

            for(var i=0;i<subStr.length;i++ ){

                if(subStr[i]==id){

                    var sts=document.getElementById(id).style.display;
                    if(sts=='block'){
                        document.getElementById(id).style.display='none';
                    }else{
                        document.getElementById(id).style.display='block';
                    }

                }
                else{

                    document.getElementById(subStr[i]).style.display='none';
                }
            }

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

                <table id="radioTbl"  border="0" style="width: 100%; height: 100%; background: #fff; padding: 1%;border: 1px solid #ccc;">
                    <tr>
                        <td  valign="top" style="padding-left: 2%;">
                            <table id="noRptType" style="display:block"  border="0" style="width: 100%; height: 100%; background: #fff; padding: 1%;border: 1px solid #ccc;">
                                <tr>
                                    <td>Please Select A Report Type. </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
               %{--Radio button and Content Table Start--}%
                    <tr>
                        <td style="padding-left: 2%;">
                            <input name="rdb" id="rdb" type="radio" value="rptE1"  onclick="showReportForm(this.value);"/>
                            Type Wise Employee List Report
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-left: 2%;">
                            <g:form controller="hrReport" action="typeWiseEmployeeList" >
                                <table id="rptE1" style="display:none"  border="0" style="width: 100%; height: 100%; background: #fff;  border: 1px solid #ccc;">
                                    <tr>
                                        <td align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td  >
                                            <div class="col-xs-15">
                                                <div class="form-group">
                                                    <label for="employeeTypeIdHrLookup"><g:message code="hrEmployee.employeeTypeIdHrLookup.label" default="Type of Employment" /></label>
                                                    <select id="employeeTypeIdHrLookup"  name="employeeTypeIdHrLookup.id"   class="form-control">
                                                        <option value="" > -- Select -- </option>
                                                        <g:each in="${employeeTypeList}" var="employeeTypeInfo">

                                                            <option value="${employeeTypeInfo.LOOKUP_ID}">${employeeTypeInfo.LOOKUP_VALUE}</option>

                                                        </g:each>
                                                    </select>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td  align="center">
                                            <g:submitButton name="View Report" class="edit btn btn-primary btn-sm"></g:submitButton>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center">&nbsp;</td>
                                    </tr>
                                </table>
                            </g:form>
                        </td>
                    </tr>
              %{--Radio button and Content Table End--}%

              %{--Radio button and Content Table Start--}%
                <tr>
                    <td style="padding-left: 2%;">
                        <input name="rdb" id="rdb" type="radio" value="rptE2"  onclick="showReportForm(this.value);"/>
                        Employee Details Report
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 2%;">
                        <g:form controller="hrReport" action="empIdWiseEmployeeDetails" >
                            <table id="rptE2" style="display:none"  border="0" style="width: 100%; height: 100%; background: #fff;  border: 1px solid #ccc;">
                                <tr>
                                    <td align="center">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="col-xs-6">
                                            <div class="form-group">
                                                <label for="cardNo"><g:message code="hrEmployeeLeave.cardNo.label" default="Employee ID" /></label>
                                                    <g:textField id="cardNo" name="cardNo" required="" autocomplete="off" maxlength="25" onclick="getEmployeeCardNo(this.id)" onkeyup="showValueByCardNo(this);" onblur="showValueByCardNo(this);"
                                                                   value=""  style="text-align: left;font-weight: bold;"  class="form-control" />
                                            </div>
                                        </div>

                                        <div class="col-xs-6">
                                            <div class="form-group">
                                                <label for="employeeName"><g:message code="hrEmployeeLeave.employeeName.label" default="Employee's Name" /></label>
                                                    <g:textField name="employeeName"  id="employeeName" required="" autocomplete="off" value="" onclick="autoEmployeeName(this.id)" onkeyup="showValue(this);"
                                                                 onblur="showValue(this);"   class="form-control"/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td >
                                        <g:submitButton name="View Report" class="edit btn btn-primary btn-sm"></g:submitButton>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center">&nbsp;</td>
                                </tr>

                            </table>
                        </g:form>
                    </td>
                </tr>
                %{--Radio button and Content Table End--}%

              %{--Radio button and Content Table Start--}%
                    <tr>
                        <td style="padding-left: 2%;">
                            <input name="rdb" id="rdb" type="radio" value="rptB1"  onclick="showReportForm(this.value);"/>
                            Bank Information Report
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-left: 2%;">
                            <g:form controller="hrReport" action="bankDetailInformation" >
                                <table id="rptB1" style="display:none"  border="0" style="width: 100%; height: 100%; background: #fff;  border: 1px solid #ccc;">
                                    <tr>
                                        <td align="center">&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="col-xs-15">
                                                <div class="form-group">
                                                    <label for="afmBankId"><g:message code="afmBankInfo.afmBankId.label" default="Bank Name : " /></label>
                                                    <select id="afmBankId"  name="afmBankId" required="" class="form-control">
                                                        <option value="" > -- Select -- </option>
                                                        <g:each in="${bankNameList}" var="bankNameInfo">
                                                            <option value="${bankNameInfo.AFM_BANK_ID}">${bankNameInfo.BANK_NAME}</option>
                                                        </g:each>
                                                    </select>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td  align="center">
                                            <g:submitButton name="View Report" class="edit btn btn-primary btn-sm"></g:submitButton>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center">&nbsp;</td>
                                    </tr>
                                </table>
                            </g:form>
                        </td>
                    </tr>
              %{--Radio button and Content Table End--}%



                </table>

            </td>
        </tr>

    </table>


</div>
</div>
</div>
%{--New Design End(For that just copy and paste upper two </div> )--}%
</body>
</html>