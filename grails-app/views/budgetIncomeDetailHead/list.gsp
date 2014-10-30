
<%@ page import="planningbudget.BudgetIncomeDetailHead" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'budgetIncomeDetailHead.label', default: 'UP Income Detail Head')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
%{--<a href="#list-budgetIncomeDetailHead" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>--}%
%{--<div class="nav" role="navigation">--}%
%{--<ul>--}%
%{--<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>--}%
%{--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%
%{--</ul>--}%
%{--</div>--}%
<p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
    <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    |
    <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
</p>

<div id="list-budgetIncomeDetailHead" class="content scaffold-list" role="main">
    %{--<h1><g:message code="default.list.label" args="[entityName]" /></h1>--}%

    %{--<g:if test="${flash.message}">--}%
    %{--<div class="message" role="status">${flash.message}</div>--}%
    %{--</g:if>--}%
    %{--<table>--}%
    %{--<thead>--}%
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title"><g:message code="default.list.label" args="[entityName]" /></h3>
        </div>
    </div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table id="example" class="dataListTable table table-bordered table-striped table-hover table-condensed">
        <thead>

        <tr>

            <g:sortableColumn property="detailHeadName" title="${message(code: 'budgetIncomeDetailHead.detailHeadName.label', default: 'Detail Head Name')}" />

            <g:sortableColumn property="detailHeadCode" title="${message(code: 'budgetIncomeDetailHead.detailHeadCode.label', default: 'Detail Head Code')}" />

            <th><g:message code="budgetIncomeDetailHead.budgetIncomeMasterHead.label" default="Budget Income Master Head" /></th>



        </tr>
        </thead>
        <tbody>
        <g:each in="${budgetIncomeDetailHeadInstanceList}" status="i" var="budgetIncomeDetailHeadInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show" id="${budgetIncomeDetailHeadInstance.id}">${fieldValue(bean: budgetIncomeDetailHeadInstance, field: "detailHeadName")}</g:link></td>

                <td>${fieldValue(bean: budgetIncomeDetailHeadInstance, field: "detailHeadCode")}</td>

                <td>${fieldValue(bean: budgetIncomeDetailHeadInstance, field: "budgetIncomeMasterHead")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${budgetIncomeDetailHeadInstanceTotal}" />
    </div>
</div>
</body>
</html>
