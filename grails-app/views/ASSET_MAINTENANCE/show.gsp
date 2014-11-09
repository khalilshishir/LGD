
<%@ page import="fixedAsset.ASSET_MAINTENANCE" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ASSET_MAINTENANCE.label', default: 'Asset Maintenance')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		%{--<a href="#show-ASSET_MAINTENANCE" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>--}%
    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
		<div id="show-ASSET_MAINTENANCE" class="content scaffold-show" role="main">
			%{--<h1><g:message code="default.show.label" args="[entityName]" /></h1>--}%
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><g:message code="default.show.label" args="[entityName]" /></h3>
                </div>
			%{--<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>--}%
            <script>
                $(document).ready(function(){
                    $("#a").fadeOut(8000);
                });
            </script>
            <g:if test="${flash.message}">
                <div class="panel-body">
                    <div id="a" class="alert alert-success" role="alert">
                        <div class="message" role="status">
                            <h3 class="panel-title">${flash.message}</h3>
                        </div>
                    </div>
                </div>
            </g:if>
			%{--<ol class="property-list ASSET_MAINTENANCE">
			
				<g:if test="${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID}">
				<li class="fieldcontain">
					<span id="ASSET_BOOK_ID-label" class="property-label"><g:message code="ASSET_MAINTENANCE.ASSET_BOOK_ID.label" default="ASSETBOOKID" /></span>
					
						<span class="property-value" aria-labelledby="ASSET_BOOK_ID-label"><g:link controller="ASSET_BOOK" action="show" id="${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID?.id}">${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE}">
				<li class="fieldcontain">
					<span id="MAINTENANCE_START_DATE-label" class="property-label"><g:message code="ASSET_MAINTENANCE.MAINTENANCE_START_DATE.label" default="MAINTENANCESTARTDATE" /></span>
					
						<span class="property-value" aria-labelledby="MAINTENANCE_START_DATE-label"><g:formatDate date="${ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.MAINTENANCE_END_DATE}">
				<li class="fieldcontain">
					<span id="MAINTENANCE_END_DATE-label" class="property-label"><g:message code="ASSET_MAINTENANCE.MAINTENANCE_END_DATE.label" default="MAINTENANCEENDDATE" /></span>
					
						<span class="property-value" aria-labelledby="MAINTENANCE_END_DATE-label"><g:formatDate date="${ASSET_MAINTENANCEInstance?.MAINTENANCE_END_DATE}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_NAME}">
				<li class="fieldcontain">
					<span id="MAINTENANCE_PLACE_NAME-label" class="property-label"><g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_NAME.label" default="MAINTENANCEPLACENAME" /></span>
					
						<span class="property-value" aria-labelledby="MAINTENANCE_PLACE_NAME-label"><g:fieldValue bean="${ASSET_MAINTENANCEInstance}" field="MAINTENANCE_PLACE_NAME"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_ADDRESS}">
				<li class="fieldcontain">
					<span id="MAINTENANCE_PLACE_ADDRESS-label" class="property-label"><g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_ADDRESS.label" default="MAINTENANCEPLACEADDRESS" /></span>
					
						<span class="property-value" aria-labelledby="MAINTENANCE_PLACE_ADDRESS-label"><g:fieldValue bean="${ASSET_MAINTENANCEInstance}" field="MAINTENANCE_PLACE_ADDRESS"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.REASON}">
				<li class="fieldcontain">
					<span id="REASON-label" class="property-label"><g:message code="ASSET_MAINTENANCE.REASON.label" default="REASON" /></span>
					
						<span class="property-value" aria-labelledby="REASON-label"><g:fieldValue bean="${ASSET_MAINTENANCEInstance}" field="REASON"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.REMARKS}">
				<li class="fieldcontain">
					<span id="REMARKS-label" class="property-label"><g:message code="ASSET_MAINTENANCE.REMARKS.label" default="REMARKS" /></span>
					
						<span class="property-value" aria-labelledby="REMARKS-label"><g:fieldValue bean="${ASSET_MAINTENANCEInstance}" field="REMARKS"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.IS_RELEASED}">
				<li class="fieldcontain">
					<span id="IS_RELEASED-label" class="property-label"><g:message code="ASSET_MAINTENANCE.IS_RELEASED.label" default="ISRELEASED" /></span>
					
						<span class="property-value" aria-labelledby="IS_RELEASED-label"><g:formatBoolean boolean="${ASSET_MAINTENANCEInstance?.IS_RELEASED}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_MAINTENANCEInstance?.ID}">
				<li class="fieldcontain">
					<span id="ID-label" class="property-label"><g:message code="ASSET_MAINTENANCE.ID.label" default="ID" /></span>
					
						<span class="property-value" aria-labelledby="ID-label"><g:fieldValue bean="${ASSET_MAINTENANCEInstance}" field="ID"/></span>
					
				</li>
				</g:if>
			
			</ol>--}%
        <div class="panel-body">
        <div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'ASSETBOOKID', 'error')} ">
                    <label for="ASSET_BOOK_ID">
                        <g:message code="ASSET_MAINTENANCE.ASSET_BOOK_ID.label" default="Asset Name"/>

                    </label>
                    ${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID?.ASSET_NAME}
                </div>
            </div>
        </div>

        <div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_START_DATE', 'error')} ">
                    <label for="MAINTENANCE_START_DATE">
                        <g:message code="ASSET_MAINTENANCE.MAINTENANCE_START_DATE.label" default="Start Date"/>

                    </label>
                    %{--<g:datePicker name="MAINTENANCE_START_DATE" precision="day"
                   value="${ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE}" default="none"
                   noSelection="['': '']"/>--}%
                    ${formatDate(format: 'MM/dd/yyyy', date: ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE)}
                </div>
            </div>
        </div>

        %{--<div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_END_DATE', 'error')} ">
                    <label for="MAINTENANCE_END_DATE">
                        <g:message code="ASSET_MAINTENANCE.MAINTENANCE_END_DATE.label" default="End Date"/>

                    </label>

                    ${formatDate(format: 'MM/dd/yyyy', date: ASSET_MAINTENANCEInstance?.MAINTENANCE_END_DATE)}
                </div>
            </div>
        </div>--}%

        <div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_PLACE_NAME', 'error')} ">
                    <label for="MAINTENANCE_PLACE_NAME">
                        <g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_NAME.label" default="Maintenance Place"/>

                    </label>
                    ${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_NAME}
                </div>
            </div>
        </div>

        <div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_PLACE_ADDRESS', 'error')} ">
                    <label for="MAINTENANCE_PLACE_ADDRESS">
                        <g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_ADDRESS.label" default="Maintenance Place Address"/>

                    </label>
                    ${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_ADDRESS}
                </div>
            </div>
        </div>

        <div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'REASON', 'error')} ">
                    <label for="REASON">
                        <g:message code="ASSET_MAINTENANCE.REASON.label" default="Reason"/>

                    </label>
                    ${ASSET_MAINTENANCEInstance?.REASON}
                </div>
            </div>
        </div>

        <div class="col-xs-6">
            <div class="form-group">
                <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'REMARKS', 'error')} ">
                    <label for="REMARKS">
                        <g:message code="ASSET_MAINTENANCE.REMARKS.label" default="Remarks"/>

                    </label>
                    ${ASSET_MAINTENANCEInstance?.REMARKS}
                </div>
            </div>
        </div>
        %{--<div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'IS_RELEASED', 'error')} ">
            <label for="IS_RELEASED">
                <g:message code="ASSET_MAINTENANCE.IS_RELEASED.label" default="ISRELEASED" />

            </label>
            <g:checkBox name="IS_RELEASED" value="${ASSET_MAINTENANCEInstance?.IS_RELEASED}" />
        </div>--}%

        %{--<div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'ID', 'error')} required">
            <label for="ID">
                <g:message code="ASSET_MAINTENANCE.ID.label" default="ID" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="ID" type="number" value="${ASSET_MAINTENANCEInstance.ID}" required=""/>
        </div>--}%

        </div>
        <div class="panel-body">
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${ASSET_MAINTENANCEInstance?.id}" />
					<g:link class="edit btn btn-primary btn-sm" action="edit" id="${ASSET_MAINTENANCEInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete btn btn-warning btn-sm" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
        </div>
        </div>
        </div>
	</body>
</html>
