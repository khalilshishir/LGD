<%--
  Created by IntelliJ IDEA.
  User: rajib.hossain
  Date: 10/27/14
  Time: 11:39 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Report Demo</title>
    <meta name="layout" content="upprocurement"/>
</head>

<body>
<div class="panel-body">
    <g:form action="generateReport">
        <fieldset class="form">
            <div class="col-xs-6">
                <div class="form-group">
                    <label for="schemeInfo"><g:message code="up_Proc_Master.SCHEME_INFO.label" default="স্কীম সমূহ" /></label>
                    <g:select id="schemeInfo" name="schemeInfo" from="${settings.SchemeInfo.list()}" optionKey="id" optionValue="NAME" required="" class="form-control" noSelection="['': 'Select One']"/>
                </div>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="submit" value="Generate" class="btn btn-primary btn-group-md save"/>
        </fieldset>
    </g:form>
</div>



%{--<div class="panel-body">--}%
    %{--<g:form action="generateReport">--}%
        %{--<fieldset class="form">--}%
            %{--<div class="col-xs-6">--}%
                %{--<div class="form-group">--}%

                    %{--<g:jasperReport--}%
                            %{--jasper="scheme/Topsial_Bibaron_o_dararhar-2"--}%
                            %{--format="PDF"--}%
                            %{--name="">--}%
                        %{--<label for="schemeInfo"><g:message code="up_Proc_Master.SCHEME_INFO.label" default="স্কীম সমূহ" /></label>--}%
                        %{--<g:select id="schemeInfo" name="schemeInfo" from="${settings.SchemeInfo.list()}" optionKey="id" optionValue="NAME" required="" class="form-control" noSelection="['': 'Select One']"/>--}%
                    %{--</g:jasperReport>--}%



                %{--</div>--}%
            %{--</div>--}%
        %{--</fieldset>--}%
        %{--<fieldset class="buttons">--}%
            %{--<g:submitButton name="submit" value="Generate" class="btn btn-primary btn-group-md save"/>--}%
        %{--</fieldset>--}%
    %{--</g:form>--}%
%{--</div>--}%

</body>
</html>

