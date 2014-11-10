
<%@ page import="procurement.up.rfqprocurement.UpRFQProcWorkOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="upprocurement">
		<g:set var="entityName" value="${message(code: 'upRFQProcWorkOrder.label', default: 'UpRFQProcWorkOrder')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-upRFQProcWorkOrder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-upRFQProcWorkOrder" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list upRFQProcWorkOrder">
			
				<g:if test="${upRFQProcWorkOrderInstance?.CREATED_BY}">
				<li class="fieldcontain">
					<span id="CREATED_BY-label" class="property-label"><g:message code="upRFQProcWorkOrder.CREATED_BY.label" default="CREATEDBY" /></span>
					
						<span class="property-value" aria-labelledby="CREATED_BY-label"><g:fieldValue bean="${upRFQProcWorkOrderInstance}" field="CREATED_BY"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.CREATED_DATE}">
				<li class="fieldcontain">
					<span id="CREATED_DATE-label" class="property-label"><g:message code="upRFQProcWorkOrder.CREATED_DATE.label" default="CREATEDDATE" /></span>
					
						<span class="property-value" aria-labelledby="CREATED_DATE-label"><g:formatDate date="${upRFQProcWorkOrderInstance?.CREATED_DATE}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.UPDATED_BY}">
				<li class="fieldcontain">
					<span id="UPDATED_BY-label" class="property-label"><g:message code="upRFQProcWorkOrder.UPDATED_BY.label" default="UPDATEDBY" /></span>
					
						<span class="property-value" aria-labelledby="UPDATED_BY-label"><g:fieldValue bean="${upRFQProcWorkOrderInstance}" field="UPDATED_BY"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.UPDATED_DATE}">
				<li class="fieldcontain">
					<span id="UPDATED_DATE-label" class="property-label"><g:message code="upRFQProcWorkOrder.UPDATED_DATE.label" default="UPDATEDDATE" /></span>
					
						<span class="property-value" aria-labelledby="UPDATED_DATE-label"><g:formatDate date="${upRFQProcWorkOrderInstance?.UPDATED_DATE}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.IS_ACTIVE}">
				<li class="fieldcontain">
					<span id="IS_ACTIVE-label" class="property-label"><g:message code="upRFQProcWorkOrder.IS_ACTIVE.label" default="ISACTIVE" /></span>
					
						<span class="property-value" aria-labelledby="IS_ACTIVE-label"><g:formatBoolean boolean="${upRFQProcWorkOrderInstance?.IS_ACTIVE}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.schemeInfo}">
				<li class="fieldcontain">
					<span id="schemeInfo-label" class="property-label"><g:message code="upRFQProcWorkOrder.schemeInfo.label" default="Scheme Info" /></span>
					
						<span class="property-value" aria-labelledby="schemeInfo-label"><g:link controller="schemeInfo" action="show" id="${upRFQProcWorkOrderInstance?.schemeInfo?.id}">${upRFQProcWorkOrderInstance?.schemeInfo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.signContractDate}">
				<li class="fieldcontain">
					<span id="signContractDate-label" class="property-label"><g:message code="upRFQProcWorkOrder.signContractDate.label" default="Sign Contract Date" /></span>
					
						<span class="property-value" aria-labelledby="signContractDate-label"><g:formatDate date="${upRFQProcWorkOrderInstance?.signContractDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${upRFQProcWorkOrderInstance?.supplier}">
				<li class="fieldcontain">
					<span id="supplier-label" class="property-label"><g:message code="upRFQProcWorkOrder.supplier.label" default="Supplier" /></span>
					
						<span class="property-value" aria-labelledby="supplier-label"><g:link controller="supplier" action="show" id="${upRFQProcWorkOrderInstance?.supplier?.id}">${upRFQProcWorkOrderInstance?.supplier?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${upRFQProcWorkOrderInstance?.id}" />
					<g:link class="edit" action="edit" id="${upRFQProcWorkOrderInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
