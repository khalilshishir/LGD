<%--
  Created by IntelliJ IDEA.
  User: mahye
  Date: 9/29/14
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'assessor.label', default: 'Assessor')}"/>
    <title>Report</title>

</head>

<body>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">Fixed Asset Reports</h3>
    </div>

    <div class="panel-body">
        <g:form action="generateReport" controller="FIXED_ASSET_RPT">
            <div class="col-xs-6">
                <div class="form-group">
                    <label>
                        Report Name
                    </label>
                        <g:select name="reportName" id="reportName"
                                  from="${['Fixed asset list',
//                                          'List of Fixed Asset',
                                          'Category Wise Asset List',
                                          'Assigned Person wise Asset List',
                                          'Location wise Asset List',
                                          'Stock Asset List',
                                          'Warranty Period wise Asset List',
                                          'Asset Update History',
                                          'Released Asset List',
                                          'Under Maintenance Asset List'
                                  ]}" required="" value=""
                                  class="form-control" noSelection="['': '--Select Report Name--']"/>

                </div>
            </div>
            <script type="text/javascript">
                $("#reportName").change(function () {
                    var report_name = $(this).val();
                    if (report_name == 'Fixed asset list') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);

                        $("#START_DATE_DIV").hide();
                        $("#START_DATE").prop('disabled', true);

                        $("#END_DATE_DIV").hide();
                        $("#END_DATE").prop('disabled', true);

                        $("#ASSET_ID").hide();
                        $("#ASSET_BOOK_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Category Wise Asset List') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#major").show();
                        $("#ASSET_MAJOR_CATEGORY_ID").removeAttr("disabled");

                        $("#minor").show();
                        $("#ASSET_MINOR_CATEGORY_ID").removeAttr("disabled");

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);

                        $("#START_DATE_DIV").hide();
                        $("#START_DATE").prop('disabled', true);

                        $("#END_DATE_DIV").hide();
                        $("#END_DATE").prop('disabled', true);

                        $("#ASSET_ID").hide();
                        $("#ASSET_BOOK_ID").prop('disabled', true);

                    }


                    else if (report_name == 'Assigned Person wise Asset List') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").show();
                        $("#STAKEHOLDER_ID").removeAttr("disabled");

                        $("#START_DATE_DIV").hide();
                        $("#START_DATE").prop('disabled', true);

                        $("#END_DATE_DIV").hide();
                        $("#END_DATE").prop('disabled', true);

                        $("#ASSET_ID").hide();
                        $("#ASSET_BOOK_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Location wise Asset List') {
                        $("#location").show();
                        $("#LOCATION_ID").removeAttr("disabled");

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);

                        $("#START_DATE_DIV").hide();
                        $("#START_DATE").prop('disabled', true);

                        $("#END_DATE_DIV").hide();
                        $("#END_DATE").prop('disabled', true);

                        $("#ASSET_ID").hide();
                        $("#ASSET_BOOK_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Stock Asset List') {
                        $("#location").show();
                        $("#LOCATION_ID").removeAttr("disabled");

                        $("#START_DATE_DIV").show();
                        $("#START_DATE").removeAttr("disabled");

                        $("#END_DATE_DIV").show();
                        $("#END_DATE").removeAttr("disabled");

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);

                        $("#ASSET_ID").hide();
                        $("#ASSET_BOOK_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Warranty Period wise Asset List') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#ASSET_ID").hide();
                        $("#ASSET_BOOK_ID").prop('disabled', true);

                        $("#START_DATE_DIV").show();
                        $("#START_DATE").removeAttr("disabled");

                        $("#END_DATE_DIV").show();
                        $("#END_DATE").removeAttr("disabled");

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Asset Update History') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#ASSET_ID").show();
                        $("#ASSET_BOOK_ID").removeAttr("disabled");

                        $("#START_DATE_DIV").show();
                        $("#START_DATE").removeAttr("disabled");

                        $("#END_DATE_DIV").show();
                        $("#END_DATE").removeAttr("disabled");

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Released Asset List') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#ASSET_ID").show();
                        $("#ASSET_BOOK_ID").removeAttr("disabled");

                        $("#START_DATE_DIV").show();
                        $("#START_DATE").removeAttr("disabled");

                        $("#END_DATE_DIV").show();
                        $("#END_DATE").removeAttr("disabled");

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);
                    }
                    else if (report_name == 'Under Maintenance Asset List') {
                        $("#location").hide();
                        $("#LOCATION_ID").prop('disabled', true);

                        $("#ASSET_ID").show();
                        $("#ASSET_BOOK_ID").removeAttr("disabled");

                        $("#START_DATE_DIV").hide();
                        $("#START_DATE").prop('disabled', true);

                        $("#END_DATE_DIV").hide();
                        $("#END_DATE").prop('disabled', true);

                        $("#major").hide();
                        $("#ASSET_MAJOR_CATEGORY_ID").prop('disabled', true);

                        $("#minor").hide();
                        $("#ASSET_MINOR_CATEGORY_ID").prop('disabled', true);

                        $("#stakeholder").hide();
                        $("#STAKEHOLDER_ID").prop('disabled', true);
                    }
                })
            </script>
            <div class="col-xs-6">
                <div class="form-group">
                    <label>
                        Report Format
                    </label>
                        <g:select name="reportFormat" id="reportFormat" from="${['XLS', 'PDF']}"
                                  required="" value=""
                                  class="form-control" noSelection="['': 'Select Format']"/>

                </div>
            </div>

        <div class="col-xs-6" id="ASSET_ID">
            <div class="form-group">
                <label>
                    <g:message code="ASSET_BOOK.book.label" default="Asset Name" />

                </label>
                <g:select disabled=""  id="ASSET_BOOK_ID" name="ASSET_BOOK_ID.id" from="${fixedAsset.ASSET_BOOK.executeQuery("from ASSET_BOOK where IS_ACTIVE=1")}" optionKey="id"
                          value="" class="many-to-one form-control"
                          noSelection="['': '-Select-']"/>
            </div>
        </div>

            <div class="col-xs-6" id="location">
                <div class="form-group">
                    <label>
                        <g:message code="ASSET_BOOK.LOCATION_ID.label" default="Location" />

                    </label>
                    <g:select disabled=""  id="LOCATION_ID" name="LOCATION_ID.id" from="${fixedAsset.LOCATION.list()}"
                              value="" optionKey="id"
                              class="form-control" noSelection="['': '--Select--']"/>
                </div>
            </div>

            <div class="col-xs-6" id="major">
                <div class="form-group">
                    <label for="ASSET_MAJOR_CATEGORY_ID">
                        <g:message code="ASSET_BOOK.ASSET_MAJOR_CATEGORY_ID.label" default="Major Asset Category Name" />

                    </label>
                    <g:select disabled="" id="ASSET_MAJOR_CATEGORY_ID" name="ASSET_MAJOR_CATEGORY_ID.id" from="${fixedAsset.ASSET_MAJOR_CATEGORY.list()}" optionKey="id" value="" class="form-control" noSelection="['': '-Select-']"/>
                </div>
            </div>

            <div class="col-xs-6" id="minor">
                <div class="form-group">
                    <label for="ASSET_MINOR_CATEGORY_ID">
                        <g:message code="ASSET_BOOK.ASSET_MINOR_CATEGORY_ID.label" default="Minor Asset Category Name" />

                    </label>
                    %{--<g:if test="${ASSET_BOOKInstance?.ASSET_MINOR_CATEGORY_ID?.id}">

                        <g:select id="ASSET_MINOR_CATEGORY_ID" name="ASSET_MINOR_CATEGORY_ID.id" from="${fixedAsset.ASSET_MINOR_CATEGORY.list()}" optionKey="id" value="${ASSET_BOOKInstance?.ASSET_MINOR_CATEGORY_ID?.id}" class="form-control" noSelection="['': '-Select-']"/>
                    </g:if>--}%
                    %{--<g:else>--}%
                        <g:select disabled="" id="ASSET_MINOR_CATEGORY_ID" name="ASSET_MINOR_CATEGORY_ID.id" from="" optionKey="id" value="" class="form-control" noSelection="['': '-Select-']"/>

                    %{--</g:else>--}%
                </div>
            </div>
            <script type="text/javascript">
                $("#ASSET_MAJOR_CATEGORY_ID").change(function(){
                    /*var division_id = $(this).val();*/
                    var mejor_id = 0;
                    if ($(this).val() != '') {
                        mejor_id = $(this).val();
                    } else
                        mejor_id = 0;
                    var base = "<%=request.getContextPath()%>";
                    $.ajax({
                        url: base + "/ASSET_BOOK/ajaxMinorCategoryDropDownList",
                        type: "POST",
                        data: "id=" + mejor_id,
                        //dataType: "json",
                        beforeSend: function () {
                        },
                        success: function (response) {
                            $("#ASSET_MINOR_CATEGORY_ID").html(response);
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            alert("An internal error occurred! Please try again later.");
                        },
                        complete: function () {
                        }
                    });
                })
            </script>

            <div class="col-xs-6" id="stakeholder">
                <div class="form-group">
                        <label for="STAKEHOLDER_ID">
                            <g:message code="ASSET_DISTRIBUTION.STAKEHOLDER_ID.label" default="Stakeholder Name"/>

                        </label>
                        <g:select disabled="" id="STAKEHOLDER_ID" name="STAKEHOLDER_ID" from="${fixedAsset.STAKEHOLDER.list()}"
                                  optionKey="id" value="" class="many-to-one form-control"
                                  noSelection="['': '-Select-']"/>

                </div>
            </div>

            <div class="col-xs-6" id="START_DATE_DIV">
                <div class="form-group">
                    <label for="FROM_DATE">
                        <g:message code="ASSET_BOOK.FROM_DATE.label" default="From Date" />

                    </label>
                    <div id="START_DATE" disabled="" class="bfh-datepicker"  data-date="${formatDate(format:'MM/dd/yyyy',date:advanceAdjustmentInstance?.WARRANTY_START_DATE)}" data-close="true" data-name="START_DATE"></div>
                </div>
            </div>

        <div class="col-xs-6" id="END_DATE_DIV">
            <div class="form-group">
                <label for="END_DATE">
                    <g:message code="ASSET_BOOK.END_DATE.label" default="To Date" />

                </label>
                <div id="END_DATE" disabled="" class="bfh-datepicker"  data-date="${formatDate(format:'MM/dd/yyyy',date:advanceAdjustmentInstance?.WARRANTY_START_DATE)}" data-close="true" data-name="END_DATE"></div>
            </div>
        </div>

            </div>
            <div class="panel-body">
            <g:submitButton name="create" class="btn btn-primary btn-sm" value="Generate"/>
            </div>
        </g:form>

</div>

</body>
</html>