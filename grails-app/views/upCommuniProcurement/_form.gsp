<%@ page import="settings.SchemeInfo; procurement.up.communityprocurement.UpCommuniProcurement" %>

<div class="col-xs-3"></div>
<div class="col-xs-6">
    <div class="form-group">
        <label for="schemeInfo"><g:message code="upCommuniProcurement.schemeInfo.label" default="কমিউনিটি প্রকিয়াই ক্রয়কৃত  স্কীম সমূহ" /></label>
            <span class="required-indicator">*</span>
        </label>
        <g:select id="schemeInfo" name="schemeInfo.id" from="${SchemeInfo.findAllWhere(IMPLEMENTATION_SYSTEM :'Community Procurement')}" optionKey="id"  onchange="setValueOnSchemeChange(this.value)" noSelection="['': 'Select One']" optionValue="NAME" required="" value="${upCommuniProcurementInstance?.schemeInfo?.id}" class="form-control"/>

    </div>
</div>
<div class="col-xs-3"></div>

<div id="schemeTypeGrantedAmounIsLabourAppointed" class="col-xs-12" style="background-color:#e7e7e7;padding: 5px;">
</div>

<div class="col-xs-12 detail-list ${hasErrors(bean: upCommuniProcurementInstance, field: 'upCommuniProcurementDetails', 'error')} ">
    <div class="form-group">
        <ul class="one-to-many">
            <fieldset>
                <li><g:render template="details"/></li>
            </fieldset>
        </ul>
    </div>
</div>
