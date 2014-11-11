
<%@ page import="hrms.HrPayscaleDetail" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrPayscaleDetail.label', default: 'HrPayscaleDetail')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-hrPayscaleDetail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-hrPayscaleDetail" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

        %{--Add Search option contents--}%%{--
            <div style="text-align: right;">
                <g:form id="search" name="search" action="search">
                    <g:select id="hrPayscale" name="hrPayscale" noSelection="['':'Select one']"
                              from="${hrms.HrPayscale.list()}" value="${params.hrPayscale}" optionKey="id"  class="many-to-one"/>
                    <span class="buttons">
                        <g:submitButton class="search" name="searchbtn" value="Search"/>
                    </span>
                </g:form>
            </div>
            --}%%{--Add Search option contents--}%

			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="stage" title="${message(code: 'hrPayscaleDetail.stage.label', default: 'Stage')}" />
					
						<g:sortableColumn property="basic" title="${message(code: 'hrPayscaleDetail.basic.label', default: 'Basic')}" />
					
						<g:sortableColumn property="da" title="${message(code: 'hrPayscaleDetail.da.label', default: 'Da')}" />
					
						<g:sortableColumn property="houseRent" title="${message(code: 'hrPayscaleDetail.houseRent.label', default: 'House Rent')}" />
					
						<g:sortableColumn property="conveyanceAllow" title="${message(code: 'hrPayscaleDetail.conveyanceAllow.label', default: 'Conveyance Allow')}" />
					
						<g:sortableColumn property="washingAllow" title="${message(code: 'hrPayscaleDetail.washingAllow.label', default: 'Washing Allow')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${hrPayscaleDetailInstanceList}" status="i" var="hrPayscaleDetailInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${hrPayscaleDetailInstance.id}">${fieldValue(bean: hrPayscaleDetailInstance, field: "stage")}</g:link></td>
					
						<td>${fieldValue(bean: hrPayscaleDetailInstance, field: "basic")}</td>
					
						<td>${fieldValue(bean: hrPayscaleDetailInstance, field: "da")}</td>
					
						<td>${fieldValue(bean: hrPayscaleDetailInstance, field: "houseRent")}</td>
					
						<td>${fieldValue(bean: hrPayscaleDetailInstance, field: "conveyanceAllow")}</td>
					
						<td>${fieldValue(bean: hrPayscaleDetailInstance, field: "washingAllow")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${hrPayscaleDetailInstanceTotal}"/>
			</div>
		</div>
	</body>
</html>
