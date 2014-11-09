
<%@ page import="fixedAsset.ASSET_BOOK_HISTORY" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ASSET_BOOK_HISTORY.label', default: 'ASSET_BOOK_HISTORY')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-ASSET_BOOK_HISTORY" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-ASSET_BOOK_HISTORY" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list ASSET_BOOK_HISTORY">
			
				<g:if test="${ASSET_BOOK_HISTORYInstance?.ASSET_BOOK_ID}">
				<li class="fieldcontain">
					<span id="ASSET_BOOK_ID-label" class="property-label"><g:message code="ASSET_BOOK_HISTORY.ASSET_BOOK_ID.label" default="ASSETBOOKID" /></span>
					
						<span class="property-value" aria-labelledby="ASSET_BOOK_ID-label"><g:link controller="ASSET_BOOK" action="show" id="${ASSET_BOOK_HISTORYInstance?.ASSET_BOOK_ID?.id}">${ASSET_BOOK_HISTORYInstance?.ASSET_BOOK_ID?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_BOOK_HISTORYInstance?.NOTE}">
				<li class="fieldcontain">
					<span id="NOTE-label" class="property-label"><g:message code="ASSET_BOOK_HISTORY.NOTE.label" default="NOTE" /></span>
					
						<span class="property-value" aria-labelledby="NOTE-label"><g:fieldValue bean="${ASSET_BOOK_HISTORYInstance}" field="NOTE"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_BOOK_HISTORYInstance?.CREATE_DATE}">
				<li class="fieldcontain">
					<span id="CREATE_DATE-label" class="property-label"><g:message code="ASSET_BOOK_HISTORY.CREATE_DATE.label" default="CREATEDATE" /></span>
					
						<span class="property-value" aria-labelledby="CREATE_DATE-label"><g:formatDate date="${ASSET_BOOK_HISTORYInstance?.CREATE_DATE}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_BOOK_HISTORYInstance?.user_id}">
				<li class="fieldcontain">
					<span id="user_id-label" class="property-label"><g:message code="ASSET_BOOK_HISTORY.user_id.label" default="Userid" /></span>
					
						<span class="property-value" aria-labelledby="user_id-label"><g:fieldValue bean="${ASSET_BOOK_HISTORYInstance}" field="user_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ASSET_BOOK_HISTORYInstance?.ID}">
				<li class="fieldcontain">
					<span id="ID-label" class="property-label"><g:message code="ASSET_BOOK_HISTORY.ID.label" default="ID" /></span>
					
						<span class="property-value" aria-labelledby="ID-label"><g:fieldValue bean="${ASSET_BOOK_HISTORYInstance}" field="ID"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${ASSET_BOOK_HISTORYInstance?.id}" />
					<g:link class="edit" action="edit" id="${ASSET_BOOK_HISTORYInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
