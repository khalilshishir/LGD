
<%@ page import="accounts.AfmBankInfo" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'afmBankInfo.label', default: 'Bank Information')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
        <style type="text/css">

        .myTable { width:100%; border-collapse:collapse;  }

        .myTable td { padding:8px; border:#999 1px solid; }


       /* .myTable tr:nth-child(even) { *//*(even) or (2n 0)*//*

            background: #FFFFFF;
        }
        .myTable tr:nth-child(odd) { *//*(odd) or (2n 1)*//*

            background: #EAF4FF;
        }*/
        </style>
	</head>
	<body>


    <p align="right" style="color:#666; font-size: 11px; padding-right: 5px; margin-top: 10px;">
        <a style="color:#666; font-size: 11px;" class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        |
        <a style="color:#666; font-size: 11px;" href="#"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></a>
    </p>
    <div id="list-afmBankInfo" class="content scaffold-list" role="main">
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

                <g:sortableColumn property="shortName"
                                  title="${message(code: 'afmBankInfo.bankName.label', default: 'Bank Name')}"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${afmBankInfoInstanceList}" status="i" var="afmBankInfoInstance">

                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td><center><g:link action="show" id="${afmBankInfoInstance.id}">${fieldValue(bean: afmBankInfoInstance, field: "bankName")}</g:link></center></td>
                </tr>

            </g:each>
            </tbody>
        </table>
    </div>


	</body>
</html>
