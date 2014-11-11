
<%@ page import="procurement.up.rfqprocurement.UpRfqEvaluation" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="upprocurement">
    <g:set var="entityName" value="${message(code: 'upRfqEvaluation.label', default: 'ইউপি দরপত্র মূল্যায়ন প্রতিবেদন')}" />
    <title><g:message code="default.list.label/" default="লিস্ট" args="[entityName]" /></title>
</head>
<body>
<p align="right" style="color:#666; font-size: 15px; padding-right: 5px; margin-top: 10px;">
    <a style="color:#666; font-size: 15px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label/" default="হোম"/></a>
    |
    <a style="color:#666; font-size: 15px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label/" default="নতুন ইউপি দরপত্র মূল্যায়ন প্রতিবেদন" args="[entityName]" /></g:link></a>
</p>
<div id="list-upRfqEvaluation" class="content scaffold-list" role="main">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3 class="panel-title"><g:message code="default.list.label/" default="ইউপি দরপত্র মূল্যায়ন প্রতিবেদন লিস্ট" args="[entityName]" /></h3>
        </div>
    </div>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table id="example" class="dataListTable table table-bordered table-striped table-hover table-condensed">
        <thead>
        <tr>

            <g:sortableColumn property="UPPROCMASTER" title="${message(code: 'upRfqEvaluation.UP_PROC_MASTER.label', default: 'ইউনিয়ন পরিষদ ক্রয়')}" />

            <g:sortableColumn property="EVALUATION_DATE" title="${message(code: 'upRfqEvaluation.EVALUATION_DATE.label', default: 'দরপত্র মূল্যায়ন তারিখ')}" />

            <g:sortableColumn property="TEC" title="${message(code: 'upRfqEvaluation.TEC.label', default: 'দরপত্র মূল্যায়ন কমিটি')}" />

            <g:sortableColumn property="CREATED_BY" title="${message(code: 'upRfqEvaluation.CREATED_BY.label', default: 'দরপত্র তৈরি কারি')}" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${upRfqEvaluationInstanceList}" status="i" var="upRfqEvaluationInstance">
            <tr>



                <td><g:link action="show" id="${upRfqEvaluationInstance.id}">${fieldValue(bean: upRfqEvaluationInstance, field: "UP_PROC_MASTER.SCHEME_INFO")}</g:link></td>

                <td><g:formatDate date="${upRfqEvaluationInstance.EVALUATION_DATE}" format="yyyy-MM-dd"/></td>

                <td>${fieldValue(bean: upRfqEvaluationInstance, field: "TEC")}</td>

                <td>${fieldValue(bean: upRfqEvaluationInstance, field: "CREATED_BY")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>
