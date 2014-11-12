
<%@ page import="hrms.HrLeave" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrLeave.label', default: 'Leave')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>

    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="list-hrLeave" class="content scaffold-list" role="main">
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
                <g:sortableColumn property="departmentName"
                                  title="${message(code: 'hrLeave.departmentName.label', default: 'Leave Type')}"/>

                <g:sortableColumn property="shortName"
                                  title="${message(code: 'hrLeave.shortName.label', default: 'Short Name')}"/>

                <g:sortableColumn property="isActive"
                                  title="${message(code: 'hrLeave.isActive.label', default: 'Maximum Days')}"/>

                <g:sortableColumn property="sortOrder"
                                  title="${message(code: 'hrLeave.sortOrder.label', default: 'Effective From Date')}"/>

                <g:sortableColumn property="sortOrder"
                                  title="${message(code: 'hrLeave.sortOrder.label', default: 'Holiday Count')}"/>


            </tr>
            </thead>
            <tbody>
            <g:each in="${hrLeaveInstanceList}" status="i" var="hrLeaveInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td><g:link action="show" id="${hrLeaveInstance.id}">
                        ${fieldValue(bean: hrLeaveInstance, field: "leaveType")}</g:link>
                    </td>
                    <td>${fieldValue(bean: hrLeaveInstance, field: "shortName")}</td>
                    <td>${fieldValue(bean: hrLeaveInstance, field: "maximumDays")}</td>
                    <td>${hrLeaveInstance?.effectiveDate == 'J'?'Joining':'Confirmation'}</td>
                    <td>
                        ${hrLeaveInstance?.holidayCount == 1?'Yes':'No'}
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
	</body>
</html>
