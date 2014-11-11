
<%@ page import="hrms.HrCalendarMonth" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrCalendarMonth.label', default: 'Holiday Calendar Month')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>

        <style type="text/css">

        .myTable { width:100%; border-collapse:collapse;  }

        .myTable td { padding:8px; border:#999 1px solid; }
        .myTable th { padding:8px; border:#999 1px solid; background:#D9EDF7 ; }


        .myTable tr:nth-child(even) { /*(even) or (2n 0)*/

            background: #FFFFFF;
        }
        .myTable tr:nth-child(odd) { /*(odd) or (2n 1)*/

            background: #EAF4FF;
        }
        </style>
	</head>
	<body>



    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="show-hrCalendarMonth" class="content scaffold-show" role="main">
        %{--<h1><g:message code="default.show.label" args="[entityName]"/></h1>--}%
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
                <div class="message" role="status" style="font-family: arial, helvetica, verdana, sans-serif;font-weight: bold; color: #008000;margin: 1%;">
                    ${flash.message}
                </div>
            </g:if>


            <div class="panel-body">
                <ol class="property-list hrCalendarMonth">

                    <g:if test="${hrCalendarMonthInstance?.hrCalendarIdHrCalendar}">
                        <li class="fieldcontain">
                            <span id="hrCalendarIdHrCalendar1-label" class="property-label">
                                <g:message code="hrCalendarMonth.hrCalendarIdHrCalendar.label" default="Calendar Year : "/>
                            </span>
                            <span class="property-value" aria-labelledby="hrCalendarIdHrCalendar-label">
                                ${hrCalendarMonthInstance?.hrCalendarIdHrCalendar?.encodeAsHTML()}
                            </span>
                        </li>
                    </g:if>

                    <g:if test="${hrCalendarMonthInstance?.startDate}">
                        <li class="fieldcontain">
                            <span id="startDate-label" class="property-label"><g:message code="hrCalendarMonth.startDate.label" default="Start Date:" /></span>

                            <span class="property-value" aria-labelledby="startDate-label"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance?.startDate}" /></span>

                        </li>
                    </g:if>

                    <g:if test="${hrCalendarMonthInstance?.endDate}">
                        <li class="fieldcontain">
                            <span id="endDate-label" class="property-label"><g:message code="hrCalendarMonth.endDate.label" default="End Date:" /></span>

                            <span class="property-value" aria-labelledby="endDate-label"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance?.endDate}" /></span>

                        </li>
                    </g:if>

                    <g:if test="${hrCalendarMonthInstance?.periodName}">
                        <li class="fieldcontain">
                            <span id="periodName-label" class="property-label"><g:message code="hrCalendarMonth.periodName.label" default="Period Name:" /></span>

                            <span class="property-value" aria-labelledby="periodName-label"><g:fieldValue bean="${hrCalendarMonthInstance}" field="periodName"/></span>

                        </li>
                    </g:if>

                    <li class="fieldcontain" style="margin-bottom: 1%">
                    </li>

                    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="myTable">
                        <thead>
                        <tr>
                            <th><g:message code="hrHolidayCalendars.holidayTypeIdHrLookup.label" default="Holiday Type"/></th>
                            <th><g:message code="hrHolidayCalendars.holidayTypeDescIdHrLookup.label" default="Holiday Description"/></th>
                            <th><g:message code="hrHolidayCalendars.startDate.label" default="Start Date"/></th>
                            <th><g:message code="hrHolidayCalendars.endDate.label" default="End Date"/></th>
                            <th><g:message code="hrHolidayCalendars.totalDays.label" default="Total Days"/></th>
                        <tr>
                        </thead>
                        <tbody>
                        <g:each in="${hrHolidayCalendars}" var="l">
                            <tr>
                                %{--<td class="list-align-center">${l?.calendarYear}</td>--}%
                                <td>${l?.holidayTypeIdHrLookup}</td>
                                <td>${l?.holidayTypeDescIdHrLookup}</td>
                                <td class="list-align-center">${formatDate(format:'dd/MM/yyyy',date:l?.startDate)}</td>
                                <td class="list-align-center">${formatDate(format:'dd/MM/yyyy',date:l?.endDate)}</td>
                                <td class="list-align-center">${l?.totalDays}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <li class="fieldcontain" style="margin-bottom: 1%">
                    </li>
                </ol>



                <g:form>
                    <fieldset class="buttons">
                        <g:hiddenField name="id" value="${hrCalendarMonthInstance?.id}"/>
                        <g:link class="edit btn btn-primary btn-sm" action="edit" id="${hrCalendarMonthInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                             default="Edit"/></g:link>
                        <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </div>
        </div>


%{--



		<a href="#show-hrCalendarMonth" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				--}%%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%%{--
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				--}%%{--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%%{--
			</ul>
		</div>
		--}%%{--<div id="show-hrCalendarMonth" class="content scaffold-show" role="main">--}%%{--
        <div id="wrapper">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

        <g:form>
            <fieldset class="buttons">
                <g:hiddenField name="id" value="${hrCalendarMonthInstance?.id}" />
                <g:link class="edit" action="edit" id="${hrCalendarMonthInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </fieldset>
        </g:form>


			<ol class="property-list hrCalendarMonth">
			

			
				<g:if test="${hrCalendarMonthInstance?.startDate}">
				<li class="fieldcontain">
					<span id="startDate-label" class="property-label"><g:message code="hrCalendarMonth.startDate.label" default="Start Date:" /></span>
					
						<span class="property-value" aria-labelledby="startDate-label"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance?.startDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrCalendarMonthInstance?.endDate}">
				<li class="fieldcontain">
					<span id="endDate-label" class="property-label"><g:message code="hrCalendarMonth.endDate.label" default="End Date:" /></span>
					
						<span class="property-value" aria-labelledby="endDate-label"><g:formatDate format="dd/MM/yyyy" date="${hrCalendarMonthInstance?.endDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${hrCalendarMonthInstance?.periodName}">
				<li class="fieldcontain">
					<span id="periodName-label" class="property-label"><g:message code="hrCalendarMonth.periodName.label" default="Period Name:" /></span>
					
						<span class="property-value" aria-labelledby="periodName-label"><g:fieldValue bean="${hrCalendarMonthInstance}" field="periodName"/></span>
					
				</li>
				</g:if>
			
			</ol>


            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="common-list-table">
                <thead>
                <tr>
                    --}%%{--<th>Calendar Year</th>--}%%{--
                    <th>Holiday Type</th>
                    <th>Holiday Description</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Total Days</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${hrHolidayCalendars}" var="l">
                    <tr>
                        --}%%{--<td class="list-align-center">${l?.calendarYear}</td>--}%%{--
                        <td>${l?.holidayTypeIdHrLookup}</td>
                        <td>${l?.holidayTypeDescIdHrLookup}</td>
                        <td class="list-align-center">${formatDate(format:'dd/MM/yyyy',date:l?.startDate)}</td>
                        <td class="list-align-center">${formatDate(format:'dd/MM/yyyy',date:l?.endDate)}</td>
                        <td class="list-align-center">${l?.totalDays}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>

			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${hrCalendarMonthInstance?.id}" />
					<g:link class="edit" action="edit" id="${hrCalendarMonthInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>--}%
	</body>
</html>
