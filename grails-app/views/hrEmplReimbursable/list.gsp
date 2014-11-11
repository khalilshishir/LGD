
<%@ page import="hrms.HrEmplReimbursable" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrEmplReimbursable.label', default: ' Employee Reimbursable')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>

    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="list-hrEmplReimbursable" class="content scaffold-list" role="main">
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
                <g:sortableColumn property="contractAmount" title="${message(code: 'hrEmplReimbursable.contractAmount.label', default: 'Employee ID')}" style="text-align: center"/>

                <g:sortableColumn property="contractAmount" title="${message(code: 'hrEmplReimbursable.contractAmount.label', default: 'Contract Amount')}" style="text-align: center"/>

                <g:sortableColumn property="contractDate" title="${message(code: 'hrEmplReimbursable.contractDate.label', default: 'Contract Date')}" style="text-align: center"/>

                <g:sortableColumn property="startDate" title="${message(code: 'hrEmplReimbursable.startDate.label', default: 'Start Date')}" style="text-align: center"/>

                <g:sortableColumn property="endDate" title="${message(code: 'hrEmplReimbursable.endDate.label', default: 'End Date')}" style="text-align: center"/>

                <g:sortableColumn property="totalDays" title="${message(code: 'hrEmplReimbursable.totalDays.label', default: 'Total Days')}" style="text-align: center"/>


            </tr>
            </thead>
            <tbody>
            <g:each in="${hrEmplReimbursableInstanceList}" status="i" var="hrEmplReimbursableInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td style="text-align: center"><g:link action="show" id="${hrEmplReimbursableInstance.id}">${hrEmplReimbursableInstance?.hrEmployee?.cardNo}</g:link></td>

                    <td style="text-align: center"><g:link action="show" id="${hrEmplReimbursableInstance.id}">${fieldValue(bean: hrEmplReimbursableInstance, field: "contractAmount")}</g:link></td>

                    <td style="text-align: center"><g:formatDate format="dd/MM/yyyy" date="${hrEmplReimbursableInstance.contractDate}" /></td>

                    <td style="text-align: center"><g:formatDate format="dd/MM/yyyy" date="${hrEmplReimbursableInstance.startDate}" /></td>

                    <td style="text-align: center"><g:formatDate format="dd/MM/yyyy" date="${hrEmplReimbursableInstance.endDate}" /></td>

                    <td style="text-align: right">${fieldValue(bean: hrEmplReimbursableInstance, field: "totalDays")}</td>


                </tr>
            </g:each>
            </tbody>
        </table>

    </div>
    <div class="pagination">
        <g:paginate total="${hrEmplReimbursableInstanceTotal}" />
    </div>


	</body>
</html>
