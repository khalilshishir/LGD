
<%@ page import="procurement.up.communityprocurement.MasterRoleSalary" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="upprocurement">
    <g:set var="entityName" value="${message(code: 'masterRoleSalary.label', default: 'মাস্টার-রোলে মজুরী ')}" />
    <title><g:message code="default.show.label/" default="দেখান" args="[entityName]" /></title>
</head>
<body>
<p align="right" style="color:#666; font-size: 15px; padding-right: 5px; margin-top: 10px;">
    <a style="color:#666; font-size: 15px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label/" default="হোম"/></a>
    |
    <a style="color:#666; font-size: 15px;" href="#"><g:link class="list" action="list"><g:message code="default.list.label/" default="মাস্টার-রোলে মজুরী লিস্ট" args="[entityName]" /></g:link></a>
    |
    <a style="color:#666; font-size: 15px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label/" default="নতুন মাস্টার-রোলে মজুরী " args="[entityName]" /></g:link></a>
</p>
<div id="show-masterRoleSalary" class="content scaffold-show" role="main">
    %{--<h1><g:message code="default.show.label" args="[entityName]" /></h1>--}%
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title"><g:message code="default.show.label/" default="দেখান - মাস্টার-রোলে মজুরী " args="[entityName]" /></h3>
        </div>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <div class="panel-body">
            <ol class="property-list masterRoleSalary">

                <g:if test="${masterRoleSalaryInstance?.schemeInfo}">
                    <li class="fieldcontain">
                        <span id="schemeInfo-label" class="property-label"><g:message code="masterRoleSalary.schemeInfo.label" default="ইউনিয়ন পরিষদ ক্রয় : " /></span>

                        <span class="property-value" aria-labelledby="schemeInfo-label"><g:fieldValue bean="${masterRoleSalaryInstance}" field="schemeInfo"/></span>

                    </li>
                </g:if>



            </ol>
            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${masterRoleSalaryInstance?.id}" />
                    <g:link class="edit btn btn-primary btn-sm" action="edit" id="${masterRoleSalaryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message/', default: 'আপনি কি নিশ্চিত?')}');" />
                </fieldset>
            </g:form>

        </div>
    </div>
</body>
</html>
