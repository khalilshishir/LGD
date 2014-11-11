<%@ page import="hrms.HrDepartment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrReport.label', default: 'HR Report')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>

    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
       %{-- <a style="color:#666; font-size: 11px;" href="#"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></a>--}%
    </p>

    <div id="create-hrReport" class="content scaffold-create" role="main">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">Employee Payroll Process</h3>
               %{--
                <g:if test="${flash.message}">
                    <div  class="message" role="status">${flash.message}</div>
                </g:if>
                --}%

            </div>
            <div class="panel-body">
                    <fieldset class="form">
                        <g:render template="hrPayrollProcess"/>
                    </fieldset>
            </div>
        </div>
    </div>
    </body>


</html>
