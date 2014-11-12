<%@ page import="hrms.HrPayscale" %>

<div class="col-xs-4">
    <div class="form-group">
        <label for="gradeNo"><g:message code="hrPayscale.gradeNo.label" default="Grade Name" /></label>
        <g:if test="${hrPayscaleInstance?.gradeNo == null}">
            <g:textField class="form-control" id="gradeNo" name="gradeNo"  required="" value="${hrPayscaleInstance?.gradeNo}" />
        </g:if>
        <g:else>
            <g:textField class="form-control" id="gradeNo" name="gradeNo"  required="" value="${hrPayscaleInstance?.gradeNo}" readonly="readonly"/>
        </g:else>
    </div>
</div>


<%--  for details page here  --%>

<g:render template="details" />

%{--  for details page here --}%


