
<%@ page import="hrms.HrPayscale" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hrPayscale.label', default: 'Payscale')}" />
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
    <div id="show-hrPayscale" class="content scaffold-show" role="main">
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
                <ol class="property-list hrPayscale">

                    <g:if test="${hrPayscaleInstance?.gradeNo}">
                        <li class="fieldcontain">
                            <span id="gradeNo1-label" class="property-label">
                                <g:message code="hrPayscale.gradeNo.label" default="Grade No : "/>
                            </span>
                            <span class="property-value" aria-labelledby="gradeNo-label">
                                <g:fieldValue bean="${hrPayscaleInstance}" field="gradeNo"/>
                            </span>
                        </li>
                    </g:if>

                    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="myTable">
                        <thead>
                        <tr>

                            <th><g:message code="hrPayscaleDetails.stage.label" default="Stage"/></th>
                            <th><g:message code="hrPayscaleDetails.basic.label" default="Basic"/></th>
                            <th><g:message code="hrPayscaleDetails.da.label" default="DA"/></th>
                            <th><g:message code="hrPayscaleDetails.houseRent.label" default="House Rent"/></th>
                            <th><g:message code="hrPayscaleDetails.conveyanceAllow.label" default="Conveyance"/></th>
                            <th><g:message code="hrPayscaleDetails.washingAllow.label" default="Washing"/></th>
                            <th><g:message code="hrPayscaleDetails.medicalAllow.label" default="Medical"/></th>
                            <th><g:message code="hrPayscaleDetails.pfContribution.label" default="PF"/></th>
                            <th><g:message code="hrPayscaleDetails.festivalBonus.label" default="Festival Bonus"/></th>
                            <th><g:message code="hrPayscaleDetails.annualBonus.label" default="Annual Bonus"/></th>
                            <th><g:message code="hrPayscaleDetails.gratuity.label" default="Gratuity"/></th>
                            <th><g:message code="hrPayscaleDetails.grandTotal.label" default="Grand Total"/></th>

                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${hrPayscaleInstance.hrPayscaleDetails}" var="l">
                            <tr>

                                <td class="list-align-center">${l?.stage}</td>
                                <td class="list-align-right">${l?.basic}</td>
                                <td class="list-align-right">${l?.da}</td>
                                <td class="list-align-right">${l?.houseRent}</td>
                                <td class="list-align-right">${l?.conveyanceAllow}</td>
                                <td class="list-align-right">${l?.washingAllow}</td>
                                <td class="list-align-right">${l?.medicalAllow}</td>
                                <td class="list-align-right">${l?.pfContribution}</td>
                                <td class="list-align-right">${l?.festivalBonus}</td>
                                <td class="list-align-right">${l?.annualBonus}</td>
                                <td class="list-align-right">${l?.gratuity}</td>
                                <td class="list-align-right" style="font-weight: bold;">${Float.valueOf(l.basic)+Float.valueOf(l.da)+Float.valueOf(l.houseRent)+
                                        Float.valueOf(l.conveyanceAllow)+Float.valueOf(l.washingAllow)+Float.valueOf(l.medicalAllow)+
                                        Float.valueOf(l.pfContribution)+Float.valueOf(l.festivalBonus)+Float.valueOf(l.annualBonus)+
                                        Float.valueOf(l.gratuity)}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <li class="fieldcontain" style="margin-bottom: 1%">
                    </li>



                </ol>

                <g:form>
                    <fieldset class="buttons">
                        <g:hiddenField name="id" value="${hrPayscaleInstance?.id}"/>
                        <g:link class="edit btn btn-primary btn-sm" action="edit" id="${hrPayscaleInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                              default="Edit"/></g:link>
                        <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </div>


	</body>
</html>
