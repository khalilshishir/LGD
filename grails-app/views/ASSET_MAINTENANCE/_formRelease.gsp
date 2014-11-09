<%--
  Created by IntelliJ IDEA.
  User: mahye
  Date: 11/6/14
  Time: 11:37 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>


<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'ASSETBOOKID', 'error')} ">
            <label for="ASSET_BOOK_ID">
                <g:message code="ASSET_MAINTENANCE.ASSET_BOOK_ID.label" default="Asset Name"/>

            </label>
            <g:select disabled="" id="ASSET_BOOK_ID" name="ASSET_BOOK_ID.id" from="${fixedAsset.ASSET_BOOK.executeQuery("from ASSET_BOOK where IS_ACTIVE=1")}" optionKey="id"
                      value="${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID?.id}" class="many-to-one form-control"
                      noSelection="['': '--Select--']"/>
        </div>
    </div>
</div>

%{--<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_START_DATE', 'error')} ">
            <label for="MAINTENANCE_START_DATE">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_START_DATE.label" default="Start Date"/>

            </label>

            <div disabled="" class="bfh-datepicker"
                 data-date="${formatDate(format: 'MM/dd/yyyy', date: ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE)}" data-close="true"
                 data-name="MAINTENANCE_START_DATE"></div>
        </div>
    </div>
</div>--}%

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_END_DATE', 'error')} ">
            <label for="MAINTENANCE_END_DATE">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_END_DATE.label" default="End Date"/>

            </label>

            <div class="bfh-datepicker"
                 data-date="${formatDate(format: 'MM/dd/yyyy', date: ASSET_MAINTENANCEInstance?.MAINTENANCE_END_DATE)}" data-close="true"
                 data-name="MAINTENANCE_END_DATE"></div>
        </div>
    </div>
</div>

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_PLACE_NAME', 'error')} ">
            <label for="MAINTENANCE_PLACE_NAME">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_NAME.label" default="Maintenance Place"/>

            </label>
            <g:textField disabled="" class="form-control" name="MAINTENANCE_PLACE_NAME" value="${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_NAME}"/>
        </div>
    </div>
</div>

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_PLACE_ADDRESS', 'error')} ">
            <label for="MAINTENANCE_PLACE_ADDRESS">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_ADDRESS.label" default="Maintenance Place Address"/>

            </label>
            <g:textField disabled="" class="form-control" name="MAINTENANCE_PLACE_ADDRESS"
                         value="${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_ADDRESS}"/>
        </div>
    </div>
</div>

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'REASON', 'error')} ">
            <label for="REASON">
                <g:message code="ASSET_MAINTENANCE.REASON.label" default="Reason"/>

            </label>
            <g:textField disabled="" class="form-control" name="REASON" value="${ASSET_MAINTENANCEInstance?.REASON}"/>
        </div>
    </div>
</div>

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'REMARKS', 'error')} ">
            <label for="REMARKS">
                <g:message code="ASSET_MAINTENANCE.REMARKS.label" default="Remarks"/>

            </label>
            <g:textField class="form-control" name="REMARKS" value="${ASSET_MAINTENANCEInstance?.REMARKS}"/>
        </div>
    </div>
</div>
<div class="col-xs-6">
    <div class="form-group">
<div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'IS_RELEASED', 'error')} ">
	<label for="IS_RELEASED">
		<g:message code="ASSET_MAINTENANCE.IS_RELEASED.label" default="Is Released" />

	</label>
	<g:checkBox name="IS_RELEASED" checked="true" value="${ASSET_MAINTENANCEInstance?.IS_RELEASED}" />
</div>
    </div>
</div>
%{--<div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'ID', 'error')} required">
	<label for="ID">
		<g:message code="ASSET_MAINTENANCE.ID.label" default="ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="ID" type="number" value="${ASSET_MAINTENANCEInstance.ID}" required=""/>
</div>--}%
<g:hiddenField id="ASSET_BOOK_ID" name="ASSET_BOOK_ID.id" value="${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID?.id}"/>
