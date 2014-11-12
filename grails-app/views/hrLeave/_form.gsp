<%@ page import="hrms.HrLeave" %>

<div class="col-xs-4">
    <div class="form-group">
        <label for="leaveType"><g:message code="hrLeave.leaveType.label" default="Leave Type" /></label>
        <g:textField id="leaveType" name="leaveType" required="" value="${hrLeaveInstance?.leaveType}" class="form-control" />
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="shortName"><g:message code="hrLeave.shortName.label" default="Short Name" /></label>
        <g:textField id="shortName" name="shortName" required="" value="${hrLeaveInstance?.shortName}" class="form-control" />
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="maximumDays"><g:message code="hrLeave.maximumDays.label" default="Maximum Days" /></label>
        <g:textField id="maximumDays" name="maximumDays" required="" value="${hrLeaveInstance?.maximumDays}" class="form-control" />
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="effectiveDate"><g:message code="hrLeave.effectiveDate.label" default="Effective From" /></label>
        <br>
        <g:if test="${params.action=='create'}">
            <g:radioGroup name="effectiveDate"
                          labels="['Joining Date','Confirmation Date']"
                          values="['J','C']" value="J">
                ${it.radio}${it.label}
            </g:radioGroup>
        </g:if>
        <g:else>
            <g:radioGroup name="effectiveDate"
                          labels="['Joining Date','Confirmation Date']"
                          values="['J','C']" value="${hrLeaveInstance?.effectiveDate}">
                ${it.radio}${it.label}
            </g:radioGroup>
        </g:else>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="holidayCount"><g:message code="hrLeave.holidayCount.label" default="Holiday Count" /></label>
        <br>
        <g:if test="${params.action=='create'}">
            <g:radioGroup name="holidayCount"
                          labels="['Yes','No']"
                          values="[1,0]" value="0">
                ${it.radio}${it.label}
            </g:radioGroup>
        </g:if>
        <g:else>
            <g:radioGroup name="holidayCount"
                          labels="['Yes','No']"
                          values="[1,0]" value="${hrLeaveInstance?.holidayCount}">
                ${it.radio}${it.label}&nbsp;
            </g:radioGroup>
        </g:else>
    </div>
</div>






