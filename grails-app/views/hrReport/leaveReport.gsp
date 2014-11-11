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
  <title>Leave Report</title>
    <g:javascript src="jquery-1.6.2.js"></g:javascript>
    <script type="text/javascript">

        //For date field (Showing Calendar) Start
        $('.dtl-dt').live('focus', function () {
            $(".dtl-dt").datepicker({ dateFormat: 'dd/mm/yy'});
        });
        //For date field (Showing Calendar) End

    </script>

    <script type="text/javascript">

        //For date field (Showing Calendar) Start
        /* $('.dtl-dt').live('focus', function () {
         $(".dtl-dt").datepicker({ dateFormat: 'mm/yy'});
         });*/


        function getCardNo(fld1, fld2) {   //fld1 for passing data ,fld2 for getting data,fld3 for getting data    getCardNo('P_CARD_NO1','year1')
            // alert("hi "+fld1+" "+fld2+" "+fld3)

            var year=document.getElementById(fld2).value.trim();
            if(year!=""){
                $('#' + fld1).autocomplete({

                    source:function (request, response) {
                        $.ajax({
                            type:'POST',
                            url:'${request.contextPath}/hrReport/getCardNo',
                            dataType:"JSON",
                            data:{
                                term:request.term,
                                LEAVE_YEAR:year
                            },
                            success:function (data) {
                                response(data);
                            },
                            error:function (err) {
                                // alert(err);
                            }
                        });
                    }
                });
            }
        }

        function getEmployeeName(fld1, fld2) {   //fld1 for passing data ,fld2 for getting data,fld3 for getting data    getEmployeeName('EMPLOYEE_NAME1','year1');
            // alert("hi "+fld1+" "+fld2+" "+fld3)

            var year=document.getElementById(fld2).value.trim();
            if(year!=""){
                $('#' + fld1).autocomplete({

                    source:function (request, response) {
                        $.ajax({
                            type:'POST',
                            url:'${request.contextPath}/hrReport/getEmployeeName',
                            dataType:"JSON",
                            data:{
                                term:request.term,
                                LEAVE_YEAR:year
                            },
                            success:function (data) {
                                response(data);
                            },
                            error:function (err) {
                                // alert(err);
                            }
                        });
                    }
                });
            }
        }

        function getEmplNameAcrdToCardNo(fld1, fld2, fld3) {   //getEmplNameAcrdToCardNo('P_CARD_NO1','year1','EMPLOYEE_NAME1');
            //alert("val "+val+" methodNm "+methodNm)
            var P_CARD_NO = document.getElementById(fld1).value
            var year = document.getElementById(fld2).value

            $.ajax({
                type:'POST',
                url:'${request.contextPath}/hrReport/getEmplNameAcrdToCardNo',
                dataType:"JSON",
                data:'P_CARD_NO=' + P_CARD_NO + '&LEAVE_YEAR=' + year.trim(),
                success:function (response) {
                    //   alert("success :"+response);
                    document.getElementById(fld3).value = response
                },
                error:function (err) {
                    // alert("failure");
                }
            });
        }

        function getCardNoAcrdToEmplName(fld1, fld2, fld3) {   //getCardNoAcrdToEmplName('EMPLOYEE_NAME1','year1','P_CARD_NO1');
            //alert("val "+val+" methodNm "+methodNm)
            var EMPLOYEE_NAME = document.getElementById(fld1).value
            var year = document.getElementById(fld2).value

            $.ajax({
                type:'POST',
                url:'${request.contextPath}/hrReport/getCardNoAcrdToEmplName',
                dataType:"JSON",
                data:'EMPLOYEE_NAME=' + EMPLOYEE_NAME + '&LEAVE_YEAR=' + year.trim(),
                success:function (response) {
                    //   alert("success :"+response);
                    document.getElementById(fld3).value = response
                },
                error:function (err) {
                    // alert("failure");
                }
            });
        }
    </script>
    <script language="javascript" type="text/javascript">

        function showReportForm(v){
            var id=v
            var idStr="noRptType:attendanceRpt";    //just give the id in the last portion of the string if new table added..
            var subStr=idStr.split(":")

            for(var i=0;i<subStr.length;i++ ){

                if(subStr[i]==id){

                    document.getElementById(id).style.display='block';
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


<div ALIGN="CENTER" style="margin-bottom: 300px;">
    <div class="page-title-block">
        <div align="center" class="myHeader site-title" style="width: 100%">
            <h1 style="margin-left: 10px;">Employee Leave Reports</h1>
        </div>

        <div class="clear"></div>

    </div>
    %{--maruf--}%
    <table id="mainTab" width="860" border="0" style="width: 900px; margin-left: 70px; margin-top: 50px;">
        <tr>
            <td width="426" height="26"  valign="top" style="vertical-align: top;">

                <table id="radioTbl" width="429" border="0" >
                    <tr>
                        <td width="24">
                            <input name="rdb" id="rdb" type="radio" value="attendanceRpt"  onclick="showReportForm(this.value);"/>
                        </td>
                        <td width="389">Employee Leave Report Year wise</td>
                    </tr>
                </table>

            </td>
            <td width="418" valign="top">
            <table id="noRptType" style="display:block" width="720" border="0" >
                <tr>
                    <td >Please Select A Report Type. </td>
                </tr>
            </table>

            <g:form controller="hrReport" action="leaveReport" >
                <table id="attendanceRpt" style="display:none" width="415px;" border="0" >
                    <tr>
                        <td colspan="3" style="text-align: left; "> Year :       <br>
                            <g:textField id="year1" name="year"  required="" value=""  style="text-align: center;font-weight: bold;width: 18%"  ></g:textField>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: left; ">
                        Employee ID :<br>
                        <g:textField name="P_CARD_NO" id="P_CARD_NO1" required="" value=""  autocomplete="off"
                                         onkeyup="getCardNo('P_CARD_NO1','year1');getEmplNameAcrdToCardNo('P_CARD_NO1','year1','EMPLOYEE_NAME1');"
                                         onclick="getCardNo('P_CARD_NO1','year1')"
                                         onchange="getEmplNameAcrdToCardNo('P_CARD_NO1','year1','EMPLOYEE_NAME1');"
                                         onblur="getCardNo('P_CARD_NO1','year1');getEmplNameAcrdToCardNo('P_CARD_NO1','year1','EMPLOYEE_NAME1');"
                                         style="width: 70%;"></g:textField>
                        </td>
                        <td colspan="2" style="text-align: left; ">
                        Employee Name: <br>
                        <g:textField name="EMPLOYEE_NAME" id="EMPLOYEE_NAME1" value="" required="" autocomplete="off"
                                         onkeyup="getEmployeeName('EMPLOYEE_NAME1','year1');getCardNoAcrdToEmplName('EMPLOYEE_NAME1','year1','P_CARD_NO1');"
                                         onclick="getEmployeeName('EMPLOYEE_NAME1','year1');"
                                         onchange="getCardNoAcrdToEmplName('EMPLOYEE_NAME1','year1','P_CARD_NO1');"
                                         onblur="getEmployeeName('EMPLOYEE_NAME1','year1'); getCardNoAcrdToEmplName('EMPLOYEE_NAME1','year1','P_CARD_NO1');"
                                         style="width:200px;"></g:textField>
                        </td>
                    </tr>


                    <tr>
                        <td colspan="4" align="center">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <g:submitButton name="View Report"  style="width: 250px" onmouseover="this.style.color = '#E6443C';this.style.cursor='pointer';" onmouseout="this.style.color = '#024697';this.style.cursor='pointer'"></g:submitButton>
                        </td>
                    </tr>
                </table>
            </g:form>

            </td>
        </tr>
    </table>


</div>
</div>
</div>
%{--New Design End(For that just copy and paste upper two </div> )--}%
</body>
</html>