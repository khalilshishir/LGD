
<%@ page import="hrms.HrCalendarMonth" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrCalendarMonth.label', default: 'Calendar Month')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>



    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="list-hrCalendarMonth" class="content scaffold-list" role="main">
        %{--<h1><g:message code="default.list.label" args="[entityName]"/></h1>--}%
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"><g:message code="default.list.label" args="[entityName]" /></h3>
            </div>
        </div>
        <g:if test="${flash.message}">
            <div class="message" role="status" style="font-family: arial, helvetica, verdana, sans-serif;font-weight: bold; color: #008000;margin: 1%;">${flash.message}</div>
        </g:if>
        <table id="example" class="dataListTable table table-bordered table-striped table-hover table-condensed" >
            <thead>
            <tr>

                <g:sortableColumn property="hrCalendarIdHrCalendar"
                                  title="${message(code: 'hrCalendarMonth.hrCalendarIdHrCalendar.label', default: 'Calendar Year')}"/>
                <g:sortableColumn property="hrCalendarIdHrCalendar"
                                  title="${message(code: 'hrCalendarMonth.hrCalendarIdHrCalendar.label', default: 'Start Date')}"/>
                <g:sortableColumn property="hrCalendarIdHrCalendar"
                                  title="${message(code: 'hrCalendarMonth.hrCalendarIdHrCalendar.label', default: 'End Date')}"/>
                <g:sortableColumn property="hrCalendarIdHrCalendar"
                                  title="${message(code: 'hrCalendarMonth.hrCalendarIdHrCalendar.label', default: 'Period Name')}"/>

            </tr>
            </thead>
            <tbody>
%{--            <g:each in="${hrCalendarMonthInstanceList}" status="i" var="hrCalendarMonthInstance">

                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td><center><g:link action="show" id="${hrCalendarMonthInstance.id}">${fieldValue(bean: hrCalendarMonthInstance, field: "bankName")}</g:link></center></td>
                </tr>

            </g:each>--}%
            <g:each in="${hrCalendarMonthInstanceList}" status="i" var="hrCalendarMonthInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td class="list-align-center"><g:link action="show" id="${hrCalendarMonthInstance.id}">${fieldValue(bean: hrCalendarMonthInstance, field: "hrCalendarIdHrCalendar")}</g:link></td>

                    <td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance.startDate}" /></td>

                    <td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance.endDate}" /></td>

                    <td class="list-align-center">${fieldValue(bean: hrCalendarMonthInstance, field: "periodName")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>

    </div>
    <div class="pagination">
        <g:paginate total="${hrCalendarMonthInstanceTotal}" params="${[calendarYear: params.calendarYear]}"/>
    </div>







	%{--	<a href="#list-hrCalendarMonth" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				--}%%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%%{--
				--}%%{--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%%{--
			</ul>
		</div>
		--}%%{--<div id="list-hrCalendarMonth" class="content scaffold-list" role="main">--}%%{--
        <div id="wrapper">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

    --}%%{--Add Search option contents--}%%{--
        <div id="search-div">
            <g:form id="search" name="search" action="search">
            --}%%{--<g:select id="hrEmployee" name="hrEmployee" noSelection="['':'Select one']"
        from="${hrms.HrEmployee.list()}" value="${params.hrEmployee}" optionKey="id"  class="many-to-one"/>--}%%{--
                <span><strong>Calendar Year Name:</strong></span>
                <g:textField id="calendarYear" name="calendarYear" value="${params.calendarYear}" class="search-div-input" style="width: 180px;text-align: center;"/>
            --}%%{--<span class="buttons">--}%%{--
                <g:submitButton class="search-button" name="searchbtn" value="Search"/>
            --}%%{--</span>--}%%{--
            </g:form>
        </div>
        --}%%{--Add Search option contents--}%%{--

        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="common-list-table">
				<thead>
					<tr>
					
						<th><g:message code="hrCalendarMonth.hrCalendarIdHrCalendar.label" default="Calendar Year" /></th>
					     <th>Start Date</th>
						--}%%{--<g:sortableColumn property="startDate" title="${message(code: 'hrCalendarMonth.startDate.label', default: 'Start Date')}" />--}%%{--
                        <th>End Date</th>
						--}%%{--<g:sortableColumn property="endDate" title="${message(code: 'hrCalendarMonth.endDate.label', default: 'End Date')}" />--}%%{--
                        <th>Period Name</th>
						--}%%{--<g:sortableColumn property="periodName" title="${message(code: 'hrCalendarMonth.periodName.label', default: 'Period Name')}" />--}%%{--
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${hrCalendarMonthInstanceList}" status="i" var="hrCalendarMonthInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td class="list-align-center"><g:link action="show" id="${hrCalendarMonthInstance.id}">${fieldValue(bean: hrCalendarMonthInstance, field: "hrCalendarIdHrCalendar")}</g:link></td>
					
						<td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance.startDate}" /></td>
					
						<td class="list-align-center"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance.endDate}" /></td>
					
						<td class="list-align-center">${fieldValue(bean: hrCalendarMonthInstance, field: "periodName")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${hrCalendarMonthInstanceTotal}" params="${[calendarYear: params.calendarYear]}"/>
			</div>
		</div>--}%
	</body>
</html>
