<%@ page import="settings.SchemeInfo; procurement.up.rfqprocurement.UpRFQProcWorkOrder" %>

<div class="col-xs-4">
    <div class="form-group">
        <label for="schemeInfo"><g:message code="upCommuniProcurement.schemeInfo.label" default="কমিউনিটি প্রকিয়াই ক্রয়কৃত  স্কীম সমূহ" />
        <span class="required-indicator">*</span>
    </label>
        <g:select id="schemeInfo" name="schemeInfo.id" from="${SchemeInfo.findAllWhere(IMPLEMENTATION_SYSTEM :'Community Procurement')}" optionKey="id"  onchange="setValueOnSchemeChange(this.value)" noSelection="['': 'Select One']" optionValue="NAME" required="" value="${upCommuniProcurementInstance?.schemeInfo?.id}" class="form-control"/>

    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="signContractDate"><g:message code="upRfqEvaluation.signContractDate.label" default="সরবরাহ তারিখ" /></label>
        <div class="bfh-datepicker"   id="signContractDate" data-date="${formatDate(format:'MM/dd/yyyy',date:upRFQProcWorkOrderInstance?.signContractDate)}" data-close="true" data-name="signContractDate"></div>
    </div>
</div>





<div class="col-xs-4">
    <div class="form-group">
        <label for="supplier"><g:message code="upRFQProcWorkOrderInstance.supplier.label" default="সরবরাহকারী" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="supplier" name="supplier.id" from="${procurement.pmu.Supplier.list()}" optionKey="id"   noSelection="['': 'Select One']" optionValue="SUPP_NAME" required="" value="${upRFQProcWorkOrderInstance?.id}" class="form-control"/>

    </div>
</div>