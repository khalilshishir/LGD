
<%@ page import="hrms.HrEmplReimbursable" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'HrEmplReimbursable.label', default: 'Employee Reimbursable')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <style type="text/css">

        .myTable { width:100%; border-collapse:collapse;  }

        .myTable td { padding:8px; border:#999 1px solid; }
        .myTable th { padding:8px; border:#999 1px solid; background:#D9EDF7 ; }


        .myTable tr:nth-child(even) { /*(even) or (2n 0)*/

            background: #FFFFFF;
        }
        .myTable tr:nth-child(odd) { /*(odd) or (2n 1)*/

            background: #EAF4FF;
        }
        </style>
	</head>
	<body>

    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="show-hrEmplReimbursable" class="content scaffold-show" role="main">
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
            <ol class="property-list hrEmplReimbursable">


                <g:if test="${hrEmplReimbursableInstance?.hrEmployee}">
                    <li class="fieldcontain">
                        <span id="hrEmployee-label" class="property-label"><g:message code="hrEmplReimbursable.hrEmployee.label" default="Employee ID : " /></span>

                        <span class="property-value" aria-labelledby="hrEmployee-label"><g:link controller="hrEmployee" action="show" id="${hrEmplReimbursableInstance?.hrEmployee?.id}">${hrEmplReimbursableInstance?.hrEmployee?.cardNo}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${hrEmplReimbursableInstance?.hrEmployee}">
                    <li class="fieldcontain">
                        <span id="hrEmployee-label" class="property-label"><g:message code="hrEmplReimbursable.hrEmployee.label" default="Employee Name : " /></span>

                        <span class="property-value" aria-labelledby="hrEmployee-label"><g:link controller="hrEmployee" action="show" id="${hrEmplReimbursableInstance?.hrEmployee?.id}">${hrEmplReimbursableInstance?.hrEmployee?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>


                <g:if test="${hrEmplReimbursableInstance?.contractDate}">
                    <li class="fieldcontain">
                        <span id="contractDate-label" class="property-label"><g:message code="hrEmplReimbursable.contractDate.label" default="Contract Date : " /></span>

                        <span class="property-value" aria-labelledby="contractDate-label">
                            <g:formatDate format="dd/MM/yyyy" date="${hrEmplReimbursableInstance?.contractDate}" />
                        </span>

                    </li>
                </g:if>

                <g:if test="${hrEmplReimbursableInstance?.contractAmount}">
                    <li class="fieldcontain">
                        <span id="contractAmount-label" class="property-label"><g:message code="hrEmplReimbursable.contractAmount.label" default="Contract Amount : " /></span>

                        <span class="property-value" aria-labelledby="contractAmount-label"><g:fieldValue bean="${hrEmplReimbursableInstance}" field="contractAmount"/></span>

                    </li>
                </g:if>


                <g:if test="${hrEmplReimbursableInstance?.startDate}">
                    <li class="fieldcontain">
                        <span id="startDate-label" class="property-label"><g:message code="hrEmplReimbursable.startDate.label" default="Start Date : " /></span>

                        <span class="property-value" aria-labelledby="startDate-label"><g:formatDate format="dd/MM/yyyy" date="${hrEmplReimbursableInstance?.startDate}" /></span>

                    </li>
                </g:if>

                <g:if test="${hrEmplReimbursableInstance?.endDate}">
                    <li class="fieldcontain">
                        <span id="endDate-label" class="property-label"><g:message code="hrEmplReimbursable.endDate.label" default="End Date : " /></span>

                        <span class="property-value" aria-labelledby="endDate-label"><g:formatDate format="dd/MM/yyyy" date="${hrEmplReimbursableInstance?.endDate}" /></span>

                    </li>
                </g:if>

                <g:if test="${hrEmplReimbursableInstance?.totalDays}">
                    <li class="fieldcontain">
                        <span id="totalDays-label" class="property-label"><g:message code="hrEmplReimbursable.totalDays.label" default="Total Days : " /></span>

                        <span class="property-value" aria-labelledby="totalDays-label"><g:fieldValue bean="${hrEmplReimbursableInstance}" field="totalDays"/></span>

                    </li>
                </g:if>

                <g:if test="${hrEmplReimbursableInstance?.contractReason}">
                    <li class="fieldcontain">
                        <span id="contractReason-label" class="property-label"><g:message code="hrEmplReimbursable.contractReason.label" default="Contract Reason : " /></span>

                        <span class="property-value" aria-labelledby="contractReason-label"><g:fieldValue bean="${hrEmplReimbursableInstance}" field="contractReason"/></span>

                    </li>
                </g:if>

                <g:if test="${hrEmplReimbursableInstance?.remarks}">
                    <li class="fieldcontain">
                        <span id="remarks-label" class="property-label"><g:message code="hrEmplReimbursable.remarks.label" default="Remarks : " /></span>

                        <span class="property-value" aria-labelledby="remarks-label"><g:fieldValue bean="${hrEmplReimbursableInstance}" field="remarks"/></span>

                    </li>
                </g:if>

                <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="myTable">
                    <thead>
                    <tr>
                        <th><g:message code="hrEmplReimbursable.hrEmplReimbursableDetail.allowanceTypeId.label" default="Allowance Type"/></th>
                        <th><g:message code="hrEmplReimbursable.hrEmplReimbursableDetail.allowanceDate.label" default="Allowance Date"/></th>
                        <th><g:message code="hrEmplReimbursable.hrEmplReimbursableDetail.allowanceAmount.label" default="Amount"/></th>
                        <th><g:message code="hrEmplReimbursable.hrEmplReimbursableDetail.allowanceAmount.label" default="Paid Status"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${hrEmplReimbursableDetailList}" var="l">
                        <tr>
                            <td>${l?.allowanceType?.lookupValue}</td>
                            <td><g:formatDate format="dd/MM/yyyy"  date="${l?.allowanceDate}"/></td>
                            <td>${l?.allowanceAmount}</td>
                            <td>${l?.isPaid == 'Y'?'Paid':'Not Paid'}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <li class="fieldcontain" style="margin-bottom: 1%">
                </li>
            </ol>

            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${hrEmplReimbursableInstance?.id}"/>
                    <g:link class="edit btn btn-primary btn-sm" action="edit" id="${hrEmplReimbursableInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                          default="Edit"/></g:link>
                    <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </fieldset>
            </g:form>
        </div>

	</body>
</html>
