
<%@ page import="fixedAsset.ASSET_BOOK_HISTORY" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ASSET_BOOK_HISTORY.label', default: 'Asset Book Update History')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		%{--<a href="#list-ASSET_BOOK_HISTORY" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>--}%
		<div id="list-ASSET_BOOK_HISTORY" class="content scaffold-list" role="main">
			%{--<h1><g:message code="default.list.label" args="[entityName]" /></h1>--}%
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"><g:message code="default.list.label" args="[entityName]" /></h3>
            </div>
        </div>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			%{--<table>--}%
        <table id="example" class="dataListTable table table-bordered table-striped table-hover table-condensed">

				<thead>
					<tr>
					
						<th><g:message code="ASSET_BOOK_HISTORY.ASSET_BOOK_ID.label" default="Asset Name" /></th>
					
						<g:sortableColumn property="NOTE" title="${message(code: 'ASSET_BOOK_HISTORY.NOTE.label', default: 'Note')}" />
					
						<g:sortableColumn property="CREATE_DATE" title="${message(code: 'ASSET_BOOK_HISTORY.CREATE_DATE.label', default: 'Create date')}" />
					
						<g:sortableColumn property="user_id" title="${message(code: 'ASSET_BOOK_HISTORY.user_id.label', default: 'User Name')}" />
					
						%{--<g:sortableColumn property="ID" title="${message(code: 'ASSET_BOOK_HISTORY.ID.label', default: 'ID')}" />--}%
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ASSET_BOOK_HISTORYInstanceList}" status="i" var="ASSET_BOOK_HISTORYInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						%{--<td><g:link action="show" id="${ASSET_BOOK_HISTORYInstance.id}">${fieldValue(bean: ASSET_BOOK_HISTORYInstance, field: "ASSET_BOOK_ID")}</g:link></td>--}%
                        <td>${fieldValue(bean: ASSET_BOOK_HISTORYInstance, field: "ASSET_BOOK_ID")}</td>


						<td>${fieldValue(bean: ASSET_BOOK_HISTORYInstance, field: "NOTE")}</td>
					
						<td><g:formatDate date="${ASSET_BOOK_HISTORYInstance.CREATE_DATE}" /></td>
					
						<td>${fieldValue(bean: ASSET_BOOK_HISTORYInstance, field: "user_id")}</td>
					
						%{--<td>${fieldValue(bean: ASSET_BOOK_HISTORYInstance, field: "ID")}</td>--}%
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ASSET_BOOK_HISTORYInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
