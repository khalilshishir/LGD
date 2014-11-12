
<%@ page import="hrms.HrEmployeeLeave" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrEmployeeLeave.label', default: 'Employee Leave')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
        <script>
            function getEmployeeName(fldId) {
                $('#'+fldId).autocomplete({
                    source:'<g:createLink controller='HrEmployeeLeave' action='autoEmployeeNameByDepartmentWise'/>'
                });
            }
            function getEmployeeCardNo(fldId) {
                $('#'+fldId).autocomplete({
                    source:'<g:createLink controller='HrEmployeeLeave' action='autoEmployeeCardNoByDepartmentWise'/>'
                });
            }

            function getEmployeeDetail(val) {
                // alert("val "+val)
                var autoData = val;

                if(autoData!=''){
                    $.ajax({
                        type:'POST',
                        url:'${request.contextPath}/HrEmployeeLeave/getEmployeeDetailByDepartment',
                        dataType:"JSON",
                        data:'&autoData=' + autoData,
                        success:function (response) {
                            var outputStr=response.message
                            //  alert("outputStr  "+outputStr)
                            var str=outputStr.split(":")
                            var departmentName=str[0]
                            var designationName=str[1]
                            var repEmployeeId=str[2]
                            var repCardNo=str[3]

                            // document.getElementById('departmentLbl').innerHTML= "Department:"
                            // document.getElementById('departmentFld').innerHTML= departmentName
                            // document.getElementById('designationLbl').innerHTML= "Designation:"
                            // document.getElementById('designationFld').innerHTML= designationName
                            // document.getElementById('repEmployeeId').value= repEmployeeId
                            document.getElementById('cardNo').value= repCardNo
                        },
                        error:function (err) {
                            //alert("failure");
                        }
                    });
                }
            }
            function getEmployeeDetailByCardNo(val) {
                // alert("val "+val)
                var autoData = val;

                if(autoData!=''){
                    $.ajax({
                        type:'POST',
                        url:'${request.contextPath}/HrEmployeeLeave/getEmployeeDetailByCardNoByDepartment',
                        dataType:"JSON",
                        data:'&autoData=' + autoData,
                        success:function (response) {
                            var outputStr=response.message
                            //  alert("outputStr  "+outputStr)
                            var str=outputStr.split(":")
                            var departmentName=str[0]
                            var designationName=str[1]
                            var repEmployeeId=str[2]
                            var employeeName=str[3]

                            // document.getElementById('departmentLbl').innerHTML= "Department:"
                            // document.getElementById('departmentFld').innerHTML= departmentName
                            // document.getElementById('designationLbl').innerHTML= "Designation:"
                            // document.getElementById('designationFld').innerHTML= designationName
                            //document.getElementById('repEmployeeId').value= repEmployeeId
                            document.getElementById('employeeName').value= employeeName
                        },
                        error:function (err) {
                          //  alert("failure");
                        }
                    });
                }
            }
        </script>
	</head>
	<body>

    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="list-hrEmployeeLeave" class="content scaffold-list" role="main">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"><g:message code="default.list.label" args="[entityName]" /></h3>
            </div>
        </div>
        <g:if test="${flash.message}">
            <div class="message" role="status" style="font-family: arial, helvetica, verdana, sans-serif;font-weight: bold; color: #008000;margin: 1%;">${flash.message}</div>
        </g:if>
        <table id="example" class="dataListTable table table-bordered table-striped table-hover table-condensed" >
            <thead>
            <tr>
                <g:sortableColumn property="cardNo"
                                  title="${message(code: 'hrEmployeeLeave.cardNo.label', default: 'Employee ID')}"/>

                <g:sortableColumn property="employeeIdHrEmployee"
                                  title="${message(code: 'hrEmployeeLeave.employeeIdHrEmployee.label', default: 'Name of Employee')}"/>

                <g:sortableColumn property="applicationDate"
                                  title="${message(code: 'hrEmployeeLeave.applicationDate.label', default: 'Application Date')}"/>

                <g:sortableColumn property="startDate"
                                  title="${message(code: 'hrEmployeeLeave.startDate.label', default: 'Start Date')}"/>

                <g:sortableColumn property="endDate"
                                  title="${message(code: 'hrEmployeeLeave.endDate.label', default: 'End Date')}"/>

                <g:sortableColumn property="totalDays"
                                  title="${message(code: 'hrEmployeeLeave.totalDays.label', default: 'Total Days')}"/>

                <g:sortableColumn property="totalDays"
                                  title="${message(code: 'hrEmployeeLeave.totalDays.label', default: 'Leave Type')}"/>


            </tr>
            </thead>
            <tbody>
            <g:each in="${hrEmployeeLeaveInstanceList}" status="i" var="hrEmployeeLeaveInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td><g:link action="show" id="${hrEmployeeLeaveInstance.id}">
                        ${fieldValue(bean: hrEmployeeLeaveInstance, field: "cardNo")}</g:link>
                    </td>
                    <td>${hrEmployeeLeaveInstance?.employeeIdHrEmployee}</td>
                    <td><g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance.applicationDate}" /></td>
                    <td><g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance.startDate}" /></td>
                    <td><g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance.endDate}" /></td>
                    <td>${fieldValue(bean: hrEmployeeLeaveInstance, field: "totalDays")}</td>
                    <td>${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>








		%{--<a href="#list-hrEmployeeLeave" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				--}%%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%%{--
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		--}%%{--<div id="list-hrEmployeeLeave" class="content scaffold-list" role="main">--}%%{--
        <div id="wrapper">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

        --}%%{--Add Search option contents--}%%{--
            <div id="search-div"  style="width:100%; text-align: right">
                <g:form id="search" name="search" action="search">
                --}%%{--<g:select id="hrEmployee" name="hrEmployee" noSelection="['':'Select one']"
            from="${hrms.HrEmployee.list()}" value="${params.hrEmployee}" optionKey="id"  class="many-to-one"/>--}%%{--
                    <span><strong>Employee ID:</strong></span>
                    <g:textField id="cardNo" name="cardNo" value="${params.cardNo}" autocomplete="off" style="width: 100px;"   onkeyup="getEmployeeCardNo(this.id);getEmployeeDetailByCardNo(this.value)" onblur="getEmployeeDetailByCardNo(this.value)"  class="search-div-input"/>
                    <span><strong>Employee Name:</strong></span>
                    <g:textField id="employeeName" name="employeeName" value="${params.employeeName}" autocomplete="off" class="search-div-input" onkeyup="getEmployeeName(this.id);getEmployeeDetail(this.value)" onblur="getEmployeeDetail(this.value)" style="text-align: left;"/>
                    --}%%{--<span class="buttons">--}%%{--
                        <g:submitButton class="search-button" name="searchbtn" value="Search"/>
                    --}%%{--</span>--}%%{--
                    </g:form>
            </div>
            --}%%{--Add Search option contents--}%%{--

            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="common-list-table">
				<thead>
					<tr>
					   <th>IDNO</th>
						--}%%{--<g:sortableColumn property="cardNo" title="${message(code: 'hrEmployeeLeave.cardNo.label', default: 'Card No')}" />--}%%{--
                        <th>Name of Employee</th>
                        --}%%{--<g:sortableColumn property="employeeIdHrEmployee" title="${message(code: 'hrEmployeeLeave.employeeIdHrEmployee.label', default: 'Employee')}" />--}%%{--
                        <th>Application Date</th>
						--}%%{--<g:sortableColumn property="applicationDate" title="${message(code: 'hrEmployeeLeave.applicationDate.label', default: 'Application Date')}" />--}%%{--
					
						--}%%{--<g:sortableColumn property="salaryEffectType" title="${message(code: 'hrEmployeeLeave.salaryEffectType.label', default: 'Salary Effect Type')}" />--}%%{--
                        <th>Start Date</th>
						--}%%{--<g:sortableColumn property="startDate" title="${message(code: 'hrEmployeeLeave.startDate.label', default: 'Start Date')}" />--}%%{--
                        <th>End Date</th>
						--}%%{--<g:sortableColumn property="endDate" title="${message(code: 'hrEmployeeLeave.endDate.label', default: 'End Date')}" />--}%%{--
                        <th>Total Days</th>
						--}%%{--<g:sortableColumn property="totalDays" title="${message(code: 'hrEmployeeLeave.totalDays.label', default: 'Total Days')}" />--}%%{--
                        <th>Leave Type</th>
                        --}%%{--<g:sortableColumn property="leaveTypeIdHrLeave" title="${message(code: 'hrEmployeeLeave.leaveTypeIdHrLeave.label', default: 'Leave Type')}" />--}%%{--
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${hrEmployeeLeaveInstanceList}" status="i" var="hrEmployeeLeaveInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td class="list-align-center"><g:link action="show" id="${hrEmployeeLeaveInstance.id}">${fieldValue(bean: hrEmployeeLeaveInstance, field: "cardNo")}</g:link></td>

                        <td>${hrEmployeeLeaveInstance?.employeeIdHrEmployee}</td>
					
						<td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance.applicationDate}" /></td>
					
						--}%%{--<td>${fieldValue(bean: hrEmployeeLeaveInstance, field: "salaryEffectType")}</td>--}%%{--
					
						<td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance.startDate}" /></td>
					
						<td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance.endDate}" /></td>
					
						<td class="list-align-center">${fieldValue(bean: hrEmployeeLeaveInstance, field: "totalDays")}</td>

                        <td>${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${hrEmployeeLeaveInstanceTotal}" params="${[cardNo: params.cardNo]}"/>
			</div>
		</div>--}%
	</body>
</html>
