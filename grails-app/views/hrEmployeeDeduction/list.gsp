
<%@ page import="hrms.HrEmployeeDeduction" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>


    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="list-hrEmployeeDeduction" class="content scaffold-list" role="main">
        %{--<h1><g:message code="default.list.label" args="[entityName]"/></h1>--}%
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
                <th><g:message code="hrEmployeeDeduction.hrEmployee.label" default="Hr Employee" /></th>

                <g:sortableColumn property="deductionType" title="${message(code: 'hrEmployeeDeduction.deductionType.label', default: 'Deduction Type')}" />

                <g:sortableColumn property="deductionDate" title="${message(code: 'hrEmployeeDeduction.deductionDate.label', default: 'Deduction Date')}" />

                <g:sortableColumn property="deductionAmount" title="${message(code: 'hrEmployeeDeduction.deductionAmount.label', default: 'Deduction Amount')}" />

                %{--<g:sortableColumn property="deductionReason" title="${message(code: 'hrEmployeeDeduction.deductionReason.label', default: 'Deduction Reason')}" />--}%

            </tr>
            </thead>
            <tbody>
            <g:each in="${hrEmployeeDeductionInstanceList}" status="i" var="hrEmployeeDeductionInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td><g:link action="show" id="${hrEmployeeDeductionInstance.id}">${fieldValue(bean: hrEmployeeDeductionInstance, field: "hrEmployee")}</g:link></td>

                    <td>${fieldValue(bean: hrEmployeeDeductionInstance, field: "deductionType")}</td>

                    <td style="text-align: center;">${formatDate(format:'dd/MM/yyyy',date:hrEmployeeDeductionInstance?.deductionDate)}</td>

                    <td style="text-align: right;">${fieldValue(bean: hrEmployeeDeductionInstance, field: "deductionAmount")}</td>

                   %{-- <td>${fieldValue(bean: hrEmployeeDeductionInstance, field: "deductionReason")}</td>--}%

                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${hrEmployeeDeductionInstanceTotal}" />
        </div>
    </div>

	</body>
</html>
