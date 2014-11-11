
<%@ page import="accounts.AfmBankInfo" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'afmBankInfo.label', default: 'Bank Information')}" />
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
    <div id="show-afmBankInfo" class="content scaffold-show" role="main">
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
                <ol class="property-list afmBankInfo">

                    <g:if test="${afmBankInfoInstance?.bankName}">
                        <li class="fieldcontain">
                            <span id="bankName1-label" class="property-label">
                                <g:message code="afmBankInfo.bankName.label" default="Bank Name : "/>
                            </span>
                            <span class="property-value" aria-labelledby="lookupType-label">
                                <g:fieldValue bean="${afmBankInfoInstance}" field="bankName"/>
                            </span>
                        </li>
                    </g:if>

                    <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="myTable">
                        <thead>
                        <tr  >

                            <th><g:message code="afmBankBranch.branchName.label" default="Branch Name"/></th>
                            <th><g:message code="afmBankBranch.address.label" default="Address"/></th>
                            <th><g:message code="afmBankBranch.accountNo.label" default="Account No"/></th>
                            <th><g:message code="afmBankBranch.accountType.label" default="Account Type"/></th>
                            <th><g:message code="afmBankBranch.afmChartOfAccounts?.accountHeadName.label" default="Account Head Name"/></th>
                            <th><g:message code="afmBankBranch.afmChartOfAccounts?.accountCode.label" default="Account Code"/></th>


                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${afmBankBranchList}" var="v">
                            <tr>

                                <td style="padding-left: 5px;">${v?.branchName}</td>
                                <td>${v?.address}</td>
                                <td>${v?.accountNo}</td>
                                <td style="padding-left: 5px;">${v?.accountType}</td>
                                <td style="padding-left: 5px;">${v?.afmChartOfAccounts?.accountHeadName}</td>
                                <td style="padding-left: 5px;">${v?.afmChartOfAccounts?.accountCode}</td>


                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <li class="fieldcontain" style="margin-bottom: 1%">
                    </li>
                </ol>



                <g:form>
                    <fieldset class="buttons">
                        <g:hiddenField name="id" value="${afmBankInfoInstance?.id}"/>
                        <g:link class="edit btn btn-primary btn-sm" action="edit" id="${afmBankInfoInstance?.id}"><g:message code="default.button.edit.label"
                                                                                                                              default="Edit"/></g:link>
                        <g:actionSubmit class="delete btn btn-warning btn-sm" action="delete"
                                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                    </fieldset>
                </g:form>
            </div>
        </div>












	</body>
</html>
