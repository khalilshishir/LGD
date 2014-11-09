<%@ page import="fixedAsset.ASSET_MAINTENANCE" %>


<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'ASSETBOOKID', 'error')} ">
            <label for="ASSET_BOOK_ID">
                <g:message code="ASSET_MAINTENANCE.ASSET_BOOK_ID.label" default="Asset Name"/>

            </label>
            <g:select id="ASSET_BOOK_ID" name="ASSET_BOOK_ID.id" from="${fixedAsset.ASSET_BOOK.executeQuery("from ASSET_BOOK where IS_ACTIVE=1 and IS_MAINTENANCE=0")}" optionKey="id"
                      value="${ASSET_MAINTENANCEInstance?.ASSET_BOOK_ID?.id}" class="many-to-one form-control"
                      noSelection="['': '--Select--']"/>
        </div>
    </div>
</div>

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_START_DATE', 'error')} ">
            <label for="MAINTENANCE_START_DATE">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_START_DATE.label" default="Start Date"/>

            </label>
            %{--<g:datePicker name="MAINTENANCE_START_DATE" precision="day"
                          value="${ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE}" default="none"
                          noSelection="['': '']"/>--}%
            <div class="bfh-datepicker"
                 data-date="${formatDate(format: 'MM/dd/yyyy', date: ASSET_MAINTENANCEInstance?.MAINTENANCE_START_DATE)}" data-close="true"
                 data-name="MAINTENANCE_START_DATE"></div>
        </div>
    </div>
</div>

%{--<div class="col-xs-6">
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
</div>--}%

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_PLACE_NAME', 'error')} ">
            <label for="MAINTENANCE_PLACE_NAME">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_NAME.label" default="Maintenance Place"/>

            </label>
            <g:textField class="form-control" name="MAINTENANCE_PLACE_NAME" value="${ASSET_MAINTENANCEInstance?.MAINTENANCE_PLACE_NAME}"/>
        </div>
    </div>
</div>

<div class="col-xs-6">
    <div class="form-group">
        <div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'MAINTENANCE_PLACE_ADDRESS', 'error')} ">
            <label for="MAINTENANCE_PLACE_ADDRESS">
                <g:message code="ASSET_MAINTENANCE.MAINTENANCE_PLACE_ADDRESS.label" default="Maintenance Place Address"/>

            </label>
            <g:textField class="form-control" name="MAINTENANCE_PLACE_ADDRESS"
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
            <g:textField class="form-control" name="REASON" value="${ASSET_MAINTENANCEInstance?.REASON}"/>
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
%{--<div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'IS_RELEASED', 'error')} ">
	<label for="IS_RELEASED">
		<g:message code="ASSET_MAINTENANCE.IS_RELEASED.label" default="ISRELEASED" />
		
	</label>
	<g:checkBox name="IS_RELEASED" value="${ASSET_MAINTENANCEInstance?.IS_RELEASED}" />
</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: ASSET_MAINTENANCEInstance, field: 'ID', 'error')} required">
	<label for="ID">
		<g:message code="ASSET_MAINTENANCE.ID.label" default="ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="ID" type="number" value="${ASSET_MAINTENANCEInstance.ID}" required=""/>
</div>--}%

