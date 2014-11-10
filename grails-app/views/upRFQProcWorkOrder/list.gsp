
<%@ page import="procurement.up.rfqprocurement.UpRFQProcWorkOrder" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="upprocurement">
		<g:set var="entityName" value="${message(code: 'upRFQProcWorkOrder.label', default: 'UpRFQProcWorkOrder')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-upRFQProcWorkOrder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-upRFQProcWorkOrder" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="CREATED_BY" title="${message(code: 'upRFQProcWorkOrder.CREATED_BY.label', default: 'CREATEDBY')}" />
					
						<g:sortableColumn property="CREATED_DATE" title="${message(code: 'upRFQProcWorkOrder.CREATED_DATE.label', default: 'CREATEDDATE')}" />
					
						<g:sortableColumn property="UPDATED_BY" title="${message(code: 'upRFQProcWorkOrder.UPDATED_BY.label', default: 'UPDATEDBY')}" />
					
						<g:sortableColumn property="UPDATED_DATE" title="${message(code: 'upRFQProcWorkOrder.UPDATED_DATE.label', default: 'UPDATEDDATE')}" />
					
						<g:sortableColumn property="IS_ACTIVE" title="${message(code: 'upRFQProcWorkOrder.IS_ACTIVE.label', default: 'ISACTIVE')}" />
					
						<th><g:message code="upRFQProcWorkOrder.schemeInfo.label" default="Scheme Info" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${upRFQProcWorkOrderInstanceList}" status="i" var="upRFQProcWorkOrderInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${upRFQProcWorkOrderInstance.id}">${fieldValue(bean: upRFQProcWorkOrderInstance, field: "CREATED_BY")}</g:link></td>
					
						<td><g:formatDate date="${upRFQProcWorkOrderInstance.CREATED_DATE}" /></td>
					
						<td>${fieldValue(bean: upRFQProcWorkOrderInstance, field: "UPDATED_BY")}</td>
					
						<td><g:formatDate date="${upRFQProcWorkOrderInstance.UPDATED_DATE}" /></td>
					
						<td><g:formatBoolean boolean="${upRFQProcWorkOrderInstance.IS_ACTIVE}" /></td>
					
						<td>${fieldValue(bean: upRFQProcWorkOrderInstance, field: "schemeInfo")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${upRFQProcWorkOrderInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
