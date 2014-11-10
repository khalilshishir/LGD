<%@ page import="settings.SchemeInfo; procurement.up.Up_Proc_Master; procurement.up.rfqprocurement.UpRfqOpeningSheet" %>

%{--jquery form validation starts--}%
<script>
    $(document).ready(function() {
        $('#upRfqOpeningSheetForm').bootstrapValidator({
            // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                UP_PROC_MASTER: {
                    message: 'The Procurement Name is not valid',
                    validators: {
                        notEmpty: {
                            message: 'Procurement is required and cannot be empty'
                        }
                    }
                }
            }
        });
    });
</script>
%{--jquery form validation ends--}%

<script>
    function showEstimatedAmountForTheProcurement(procurementMasterId){
        <g:remoteFunction action="setValueForEstimatedAmount"  params="'schemeInfo='+procurementMasterId" update="estimatedAmount"/>
    }
</script>

<div class="col-xs-6">
    <div class="form-group">
        <label for="UP_PROC_MASTER"><g:message code="upRfqOpeningSheet.UP_PROC_MASTER.label" default="কমিউনিটি প্রকিয়াই ক্রয়কৃত  স্কীম সমূহ" /></label>
        %{--<g:select id="UP_PROC_MASTER" name="UP_PROC_MASTER.id" from="${upProcMasterList}" optionKey="id" optionValue="SCHEME_INFO" noSelection="['':'Select One']" required="" value="${upRfqOpeningSheetInstance?.UP_PROC_MASTER?.id}" class="form-control" onchange="showEstimatedAmountForTheProcurement(this.value)"/>--}%
        <g:select id="schemeInfo" name="schemeInfo.id" from="${SchemeInfo.findAllWhere(IMPLEMENTATION_SYSTEM :'RFQ Procurement')}" optionKey="id"  onchange="showEstimatedAmountForTheProcurement(this.value)" noSelection="['': 'Select One']" optionValue="NAME" required="" value="${upRfqOpeningSheetInstance?.schemeInfo?.id}" class="form-control"/>

    </div>
</div>

<div id="estimatedAmount">
    <div class="col-xs-6">
        <div class="form-group">
            <label for="estimatedAmount"><g:message code="upRfqOpeningSheet.UP_PROC_MASTER.label" default="প্রাক্কলিত মূল্য" /></label>
            <g:textField id="estimatedAmount" name="estimatedAmount" readonly="" class="form-control"/>
        </div>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="INVITATION_DATE"><g:message code="upRfqOpeningSheet.INVITATION_DATE.label" default="দরপত্র আহ্বান তারিখ" /></label>
        %{--<g:datePicker name="OPENING_DATE" precision="day"  value="${upRfqOpeningSheetInstance?.OPENING_DATE}" class="form-control" />--}%
        <div class="bfh-datepicker"   id="INVITATION_DATE" data-date="${formatDate(format:'MM/dd/yyyy',date:upRfqOpeningSheetInstance?.INVITATION_DATE)}" data-close="true" data-name="INVITATION_DATE"></div>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="SUB_LAST_DATE"><g:message code="upRfqOpeningSheet.SUB_LAST_DATE.label" default="দরপত্র দাখিলের শেষ তারিখ" /></label>
        %{--<g:datePicker name="OPENING_DATE" precision="day"  value="${upRfqOpeningSheetInstance?.OPENING_DATE}" class="form-control" />--}%
        <div class="bfh-datepicker"   id="SUB_LAST_DATE" data-date="${formatDate(format:'MM/dd/yyyy',date:upRfqOpeningSheetInstance?.SUB_LAST_DATE)}" data-close="true" data-name="SUB_LAST_DATE"></div>
    </div>
</div>

<div class="col-xs-4">
    <div class="form-group">
        <label for="OPENING_DATE"><g:message code="upRfqOpeningSheet.OPENING_DATE.label" default="দরপত্র খোলার তারিখ" /></label>
        %{--<g:datePicker name="OPENING_DATE" precision="day"  value="${upRfqOpeningSheetInstance?.OPENING_DATE}" class="form-control" />--}%
        <div class="bfh-datepicker"  id="OPENING_DATE" data-date="${formatDate(format:'MM/dd/yyyy',date:upRfqOpeningSheetInstance?.OPENING_DATE)}" data-close="true" data-name="OPENING_DATE"></div>
    </div>
</div>

<div class="col-xs-12 detail-list ${hasErrors(bean: upRfqOpeningSheetInstance, field: 'upRfqOpeningSheetDetails', 'error')} ">
    <div class="form-group">
        <ul class="one-to-many">
            <fieldset>
                <li><g:render template="details"/></li>
            </fieldset>
        </ul>
    </div>
</div>

