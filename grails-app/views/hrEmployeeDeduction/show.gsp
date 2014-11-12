
<%@ page import="hrms.HrEmployeeDeduction" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrEmployeeDeduction.label', default: 'Employee Deduction')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="show-hrEmployeeDeduction" class="content scaffold-show" role="main">
        %{--<h1><g:message code="default.show.label" args="[entityName]"/></h1>--}%
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
                <div class="message" role="status" style="font-family: arial, helvetica, verdana, sans-serif;font-weight: bold; color: #008000;margin: 1%;">
                    ${flash.message}
                </div>
            </g:if>


            <div class="panel-body">
                <ol class="property-list hrEmployeeDeduction">

                    <g:if test="${hrEmployeeDeductionInstance?.hrEmployee}">
                        <li class="fieldcontain">
                            <span id="hrEmployee-label" class="property-label"><g:message code="hrEmployeeDeduction.hrEmployee.label" default="Employee : " /></span>

                            <span class="property-value" aria-labelledby="hrEmployee-label"><g:link controller="hrEmployee" action="show" id="${hrEmployeeDeductionInstance?.hrEmployee?.id}">${hrEmployeeDeductionInstance?.hrEmployee?.encodeAsHTML()}</g:link></span>

                        </li>
                    </g:if>

                    <g:if test="${hrEmployeeDeductionInstance?.deductionType}">
                        <li class="fieldcontain">
                            <span id="deductionType-label" class="property-label"><g:message code="hrEmployeeDeduction.deductionType.label" default="Deduction Type : " /></span>

                            <span class="property-value" aria-labelledby="deductionType-label"><g:fieldValue bean="${hrEmployeeDeductionInstance}" field="deductionType"/></span>

                        </li>
                    </g:if>

                    <g:if test="${hrEmployeeDeductionInstance?.deductionDate}">
                        <li class="fieldcontain">
                            <span id="deductionDate-label" class="property-label"><g:message code="hrEmployeeDeduction.deductionDate.label" default="Deduction Date : " /></span>

                            <span class="property-value" aria-labelledby="deductionDate-label">${formatDate(format:'dd/MM/yyyy',date:hrEmployeeDeductionInstance?.deductionDate)}</span>

                        </li>
                    </g:if>

                    <g:if test="${hrEmployeeDeductionInstance?.deductionAmount}">
                        <li class="fieldcontain">
                            <span id="deductionAmount-label" class="property-label"><g:message code="hrEmployeeDeduction.deductionAmount.label" default="Deduction Amount : " /></span>

                            <span class="property-value" aria-labelledby="deductionAmount-label"><g:fieldValue bean="${hrEmployeeDeductionInstance}" field="deductionAmount"/></span>

                        </li>
                    </g:if>

                    <g:if test="${hrEmployeeDeductionInstance?.deductionReason}">
                        <li class="fieldcontain">
                            <span id="deductionReason-label" class="property-label"><g:message code="hrEmployeeDeduction.deductionReason.label" default="Deduction Reason : " /></span>

                            <span class="property-value" aria-labelledby="deductionReason-label"><g:fieldValue bean="${hrEmployeeDeductionInstance}" field="deductionReason"/></span>

                        </li>
                    </g:if>

                    <li class="fieldcontain" style="margin-bottom: 1%">
                    </li>



                </ol>

                <g:form>
                    <fieldset class="buttons">
                        <g:hiddenField name="id" value="${hrEmployeeDeductionInstance?.id}"/>
                        <g:link class="edit btn btn-primary btn-sm" action="edit" id="${hrEmployeeDeductionInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                              default="Edit"/></g:link>
                        <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </div>


	</body>
</html>
