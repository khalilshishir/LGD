
<%@ page import="hrms.HrLeave" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrLeave.label', default: 'Leave')}" />
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
    <div id="show-hrLeave" class="content scaffold-show" role="main">
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
                <ol class="property-list hrLeave">

                    <g:if test="${hrLeaveInstance?.leaveType}">
                        <li class="fieldcontain">
                            <span id="leaveType1-label" class="property-label">
                                <g:message code="hrLeave.leaveType.label" default="Leave Type : "/>
                            </span>
                            <span class="property-value" aria-labelledby="leaveType-label">
                                <g:fieldValue bean="${hrLeaveInstance}" field="leaveType"/>
                            </span>
                        </li>
                    </g:if>

                    <g:if test="${hrLeaveInstance?.shortName}">
                        <li class="fieldcontain">
                            <span id="shortName1-label" class="property-label">
                                <g:message code="hrLeave.shortName.label" default="Sort Name : "/>
                            </span>
                            <span class="property-value" aria-labelledby="shortName-label">
                                <g:fieldValue bean="${hrLeaveInstance}" field="shortName"/>
                            </span>
                        </li>
                    </g:if>

                    <g:if test="${hrLeaveInstance?.maximumDays}">
                        <li class="fieldcontain">
                            <span id="maximumDays1-label" class="property-label">
                                <g:message code="hrLeave.maximumDays.label" default="Maximum Days : "/>
                            </span>
                            <span class="property-value" aria-labelledby="maximumDays-label">
                                <g:fieldValue bean="${hrLeaveInstance}" field="maximumDays"/>
                            </span>
                        </li>
                    </g:if>

                    <g:if test="${hrLeaveInstance?.effectiveDate}">
                        <li class="fieldcontain">
                            <span id="effectiveDate1-label" class="property-label">
                                <g:message code="hrLeave.effectiveDate.label" default="Effective From Date : "/>
                            </span>
                            <span class="property-value" aria-labelledby="effectiveDate-label">
                                ${hrLeaveInstance?.effectiveDate == 'J'?'Joining':'Confirmation'}
                            </span>
                        </li>
                    </g:if>
                    <g:if test="${hrLeaveInstance?.holidayCount}">
                        <li class="fieldcontain">
                            <span id="holidayCount1-label" class="property-label">
                                <g:message code="hrLeave.holidayCount.label" default="Holiday Count : "/>
                            </span>
                            <span class="property-value" aria-labelledby="holidayCount-label">
                                ${hrLeaveInstance?.holidayCount == 1?'Yes':'No'}
                            </span>
                        </li>
                    </g:if>


                    %{--<li class="fieldcontain">
                        <span id="isActive1-label" class="property-label">
                            <g:message code="hrLeave.departmentName.label" default="Active Status : "/>
                        </span>
                        <span class="property-value" aria-labelledby="isActive-label">
                            <g:if test="${hrLeaveInstance?.isActive==true}">
                                Yes
                            </g:if>
                            <g:elseif test="${hrLeaveInstance?.isActive==false}">
                                No
                            </g:elseif>
                            <g:else>

                            </g:else>
                        </span>
                    </li>--}%

                    <li class="fieldcontain" style="margin-bottom: 1%">
                    </li>
                </ol>

                <g:form>
                    <fieldset class="buttons">
                        <g:hiddenField name="id" value="${hrLeaveInstance?.id}"/>
                        <g:link class="edit btn btn-primary btn-sm" action="edit" id="${hrLeaveInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                              default="Edit"/></g:link>
                        <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </div>

	</body>
</html>