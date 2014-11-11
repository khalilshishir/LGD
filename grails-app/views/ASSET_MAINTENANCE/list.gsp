
<%@ page import="fixedAsset.ASSET_MAINTENANCE" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ASSET_MAINTENANCE.label', default: 'Asset Maintenance')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		%{--<a href="#list-ASSET_MAINTENANCE" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>--}%
    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
		<div id="list-ASSET_MAINTENANCE" class="content scaffold-list" role="main">
			%{--<h1><g:message code="default.list.label" args="[entityName]" /></h1>--}%
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title"><g:message code="default.list.label" args="[entityName]" /></h3>
                </div>
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
			%{--<table>--}%
            <table id="example" class="dataListTable table table-bordered table-striped table-hover table-condensed">

				<thead>
					<tr>
					
						<th><g:message code="ASSET_MAINTENANCE.ASSET_BOOK_ID.label" default="Asset Name" /></th>
					
						<g:sortableColumn property="MAINTENANCE_START_DATE" title="${message(code: 'ASSET_MAINTENANCE.MAINTENANCE_START_DATE.label', default: 'Start Date')}" />
					
						%{--<g:sortableColumn property="MAINTENANCE_END_DATE" title="${message(code: 'ASSET_MAINTENANCE.MAINTENANCE_END_DATE.label', default: 'End Date')}" />--}%
					
						<g:sortableColumn property="MAINTENANCE_PLACE_NAME" title="${message(code: 'ASSET_MAINTENANCE.MAINTENANCE_PLACE_NAME.label', default: 'Maintenance Place')}" />
					
						%{--<g:sortableColumn property="MAINTENANCE_PLACE_ADDRESS" title="${message(code: 'ASSET_MAINTENANCE.MAINTENANCE_PLACE_ADDRESS.label', default: 'MAINTENANCEPLACEADDRESS')}" />--}%
					
						<g:sortableColumn property="REASON" title="${message(code: 'ASSET_MAINTENANCE.REASON.label', default: 'Reason')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ASSET_MAINTENANCEInstanceList}" status="i" var="ASSET_MAINTENANCEInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ASSET_MAINTENANCEInstance.id}">${fieldValue(bean: ASSET_MAINTENANCEInstance, field: "ASSET_BOOK_ID")}</g:link></td>
					
						<td><g:formatDate date="${ASSET_MAINTENANCEInstance.MAINTENANCE_START_DATE}" /></td>
					
						%{--<td><g:formatDate date="${ASSET_MAINTENANCEInstance.MAINTENANCE_END_DATE}" /></td>--}%
					
						<td>${fieldValue(bean: ASSET_MAINTENANCEInstance, field: "MAINTENANCE_PLACE_NAME")}</td>
					
						%{--<td>${fieldValue(bean: ASSET_MAINTENANCEInstance, field: "MAINTENANCE_PLACE_ADDRESS")}</td>--}%
					
						<td>${fieldValue(bean: ASSET_MAINTENANCEInstance, field: "REASON")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ASSET_MAINTENANCEInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
