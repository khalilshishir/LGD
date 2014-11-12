
<%@ page import="hrms.HrEmployeeLeave" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrEmployeeLeave.label', default: 'Employee Leave')}" />
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
    <div id="show-hrEmployeeLeave" class="content scaffold-show" role="main">
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
            <ol class="property-list hrEmployeeLeave">

                <g:if test="${hrEmployeeLeaveInstance?.cardNo}">
                    <li class="fieldcontain">
                        <span id="cardNo1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.cardNo.label" default="Employee ID : "/>
                        </span>
                        <span class="property-value" aria-labelledby="cardNo-label">
                            <g:fieldValue bean="${hrEmployeeLeaveInstance}" field="cardNo"/>
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.employeeIdHrEmployee}">
                    <li class="fieldcontain">
                        <span id="employeeIdHrEmployee1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.employeeIdHrEmployee.label" default="Employee Name : "/>
                        </span>
                        <span class="property-value" aria-labelledby="employeeIdHrEmployee-label">
                            ${hrEmployeeLeaveInstance?.employeeIdHrEmployee}
                        </span>
                    </li>
                </g:if>


                <g:if test="${hrEmployeeLeaveInstance?.applicationDate}">
                    <li class="fieldcontain">
                        <span id="applicationDate1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.applicationDate.label" default="Application Date : "/>
                        </span>
                        <span class="property-value" aria-labelledby="applicationDate-label">
                            <g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance?.applicationDate}" />
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.startDate}">
                    <li class="fieldcontain">
                        <span id="startDate1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.startDate.label" default="Start Date : "/>
                        </span>
                        <span class="property-value" aria-labelledby="startDate-label">
                            <g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance?.startDate}" />
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.endDate}">
                    <li class="fieldcontain">
                        <span id="endDate1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.endDate.label" default="End Date : "/>
                        </span>
                        <span class="property-value" aria-labelledby="endDate-label">
                            <g:formatDate format="dd/MM/yyyy" date="${hrEmployeeLeaveInstance?.endDate}" />
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.totalDays}">
                    <li class="fieldcontain">
                        <span id="totalDays1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.totalDays.label" default="Total Days : "/>
                        </span>
                        <span class="property-value" aria-labelledby="totalDays-label">
                            <g:fieldValue bean="${hrEmployeeLeaveInstance}" field="totalDays"/>
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave}">
                    <li class="fieldcontain">
                        <span id="salaryEffectTypeIdHrLookup1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.leaveTypeIdHrLeave.label" default="Salary Effect Type : "/>
                        </span>
                        <span class="property-value" aria-labelledby="leaveTypeIdHrLeave-label">
                            ${hrEmployeeLeaveInstance?.leaveTypeIdHrLeave}
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.leaveReason}">
                    <li class="fieldcontain">
                        <span id="leaveReason1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.leaveReason.label" default="Leave Reason : "/>
                        </span>
                        <span class="property-value" aria-labelledby="leaveReason-label">
                            <g:fieldValue bean="${hrEmployeeLeaveInstance}" field="leaveReason"/>
                        </span>
                    </li>
                </g:if>

                <g:if test="${hrEmployeeLeaveInstance?.remarks}">
                    <li class="fieldcontain">
                        <span id="remarks1-label" class="property-label">
                            <g:message code="hrEmployeeLeave.remarks.label" default="Remarks : "/>
                        </span>
                        <span class="property-value" aria-labelledby="remarks-label">
                            <g:fieldValue bean="${hrEmployeeLeaveInstance}" field="remarks"/>
                        </span>
                    </li>
                </g:if>

                <li class="fieldcontain" style="margin-bottom: 1%">
                </li>
                <li class="fieldcontain" style="padding-bottom: 1%;">
                    <g:form controller="hrReport" action="leaveApplicationReport" >
                        <span class="property-value" aria-labelledby="remarks-label"  >
                            <g:submitButton name="  Click Here For Leave Application Report  "  title="Click Here For Leave Application Report"
                                            class="btn btn-success">
                            </g:submitButton>
                        </span>
                        <g:hiddenField name="P_CARD_NO" value="${hrEmployeeLeaveInstance.cardNo}"  />
                        <g:hiddenField name="P_APPLICATION_DATE"  value="${hrEmployeeLeaveInstance.applicationDate != '' ?  hrEmployeeLeaveInstance.applicationDate?.format('dd/MM/yyyy') : ''}"  />
                    </g:form>
                </li>

                <li class="fieldcontain" style="padding-bottom: 1%;">
                    <g:form controller="hrReport" action="leaveReport" >
                        <span class="property-value" aria-labelledby="remarks-label" >
                            <g:submitButton name="  Click Here For Application Year Total Leave Report  "  title=" Click Here For Application Year Total Leave Report  " class="btn btn-primary" >
                            </g:submitButton>
                        </span>
                        <g:hiddenField name="P_CARD_NO" value="${hrEmployeeLeaveInstance.employeeIdHrEmployee.cardNo}"  />
                        <g:hiddenField name="year"  value="${hrEmployeeLeaveInstance.applicationDate != '' ?  hrEmployeeLeaveInstance.applicationDate?.format('yyyy') : ''}"  />
                    </g:form>
                </li>

            </ol>

            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${hrEmployeeLeaveInstance?.id}"/>
                    <g:link class="edit btn btn-primary btn-sm" action="edit" id="${hrEmployeeLeaveInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                          default="Edit"/></g:link>
                    <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </fieldset>
            </g:form>
        </div>

	</body>
</html>
