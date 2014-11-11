
<%@ page import="hrms.HrPayscaleDetail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-hrPayscaleDetail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-hrPayscaleDetail" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list hrPayscaleDetail">
			
				<g:if test="${hrPayscaleDetailInstance?.stage}">
				<li class="fieldcontain">
					<span id="stage-label" class="property-label"><g:message code="hrPayscaleDetail.stage.label" default="Stage" /></span>
					
						<span class="property-value" aria-labelledby="stage-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="stage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.basic}">
				<li class="fieldcontain">
					<span id="basic-label" class="property-label"><g:message code="hrPayscaleDetail.basic.label" default="Basic" /></span>
					
						<span class="property-value" aria-labelledby="basic-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="basic"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.da}">
				<li class="fieldcontain">
					<span id="da-label" class="property-label"><g:message code="hrPayscaleDetail.da.label" default="Da" /></span>
					
						<span class="property-value" aria-labelledby="da-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="da"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.houseRent}">
				<li class="fieldcontain">
					<span id="houseRent-label" class="property-label"><g:message code="hrPayscaleDetail.houseRent.label" default="House Rent" /></span>
					
						<span class="property-value" aria-labelledby="houseRent-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="houseRent"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.conveyanceAllow}">
				<li class="fieldcontain">
					<span id="conveyanceAllow-label" class="property-label"><g:message code="hrPayscaleDetail.conveyanceAllow.label" default="Conveyance Allow" /></span>
					
						<span class="property-value" aria-labelledby="conveyanceAllow-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="conveyanceAllow"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.washingAllow}">
				<li class="fieldcontain">
					<span id="washingAllow-label" class="property-label"><g:message code="hrPayscaleDetail.washingAllow.label" default="Washing Allow" /></span>
					
						<span class="property-value" aria-labelledby="washingAllow-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="washingAllow"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.medicalAllow}">
				<li class="fieldcontain">
					<span id="medicalAllow-label" class="property-label"><g:message code="hrPayscaleDetail.medicalAllow.label" default="Medical Allow" /></span>
					
						<span class="property-value" aria-labelledby="medicalAllow-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="medicalAllow"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.pfContribution}">
				<li class="fieldcontain">
					<span id="pfContribution-label" class="property-label"><g:message code="hrPayscaleDetail.pfContribution.label" default="Pf Contribution" /></span>
					
						<span class="property-value" aria-labelledby="pfContribution-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="pfContribution"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.festivalBonus}">
				<li class="fieldcontain">
					<span id="festivalBonus-label" class="property-label"><g:message code="hrPayscaleDetail.festivalBonus.label" default="Festival Bonus" /></span>
					
						<span class="property-value" aria-labelledby="festivalBonus-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="festivalBonus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.annualBonus}">
				<li class="fieldcontain">
					<span id="annualBonus-label" class="property-label"><g:message code="hrPayscaleDetail.annualBonus.label" default="Annual Bonus" /></span>
					
						<span class="property-value" aria-labelledby="annualBonus-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="annualBonus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.gratuity}">
				<li class="fieldcontain">
					<span id="gratuity-label" class="property-label"><g:message code="hrPayscaleDetail.gratuity.label" default="Gratuity" /></span>
					
						<span class="property-value" aria-labelledby="gratuity-label"><g:fieldValue bean="${hrPayscaleDetailInstance}" field="gratuity"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrPayscaleDetailInstance?.payscaleIdHrPayscale}">
				<li class="fieldcontain">
					<span id="payscaleIdHrPayscale-label" class="property-label"><g:message code="hrPayscaleDetail.payscaleIdHrPayscale.label" default="Payscale Id Hr Payscale" /></span>
					
						<span class="property-value" aria-labelledby="payscaleIdHrPayscale-label"><g:link controller="hrPayscale" action="show" id="${hrPayscaleDetailInstance?.payscaleIdHrPayscale?.id}">${hrPayscaleDetailInstance?.payscaleIdHrPayscale?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${hrPayscaleDetailInstance?.id}" />
					<g:link class="edit" action="edit" id="${hrPayscaleDetailInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
