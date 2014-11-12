<%@ page import="hrms.HrEmployeeDeduction" %>

<script type="text/javascript">
    $(document).ready(function () {
        $("#deductionDate").datepicker({ dateFormat: 'dd/mm/yy'});
    });
</script>

<div class="col-xs-6 fieldcontain ${hasErrors(bean: hrEmployeeDeductionInstance, field: 'hrEmployee', 'error')} required">
    <div class="form-group">
        <label for="hrEmployee">
            <g:message code="hrEmployeeDeduction.hrEmployee.label" default="Hr Employee" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="hrEmployee" name="hrEmployee.id" from="${hrms.HrEmployee.list()}" optionKey="id" required=""
                  value="${hrEmployeeDeductionInstance?.hrEmployee?.id}"   class="many-to-one form-control"/>
    </div>
</div>


<div class="col-xs-6 fieldcontain ${hasErrors(bean: hrEmployeeDeductionInstance, field: 'deductionType', 'error')} required">
    <div class="form-group">
        <label for="deductionType">
            <g:message code="hrEmployeeDeduction.deductionType.label" default="Deduction Type" />
            <span class="required-indicator">*</span>
        </label>
        <g:textField name="deductionType" maxlength="250" required="" value="${hrEmployeeDeductionInstance?.deductionType}" class="form-control" />
    </div>
</div>

<div class="col-xs-6 fieldcontain ${hasErrors(bean: hrEmployeeDeductionInstance, field: 'deductionDate', 'error')} required">
    <div class="form-group">
        <label for="deductionDate">
            <g:message code="hrEmployeeDeduction.deductionDate.label" default="Deduction Date" />
            <span class="required-indicator">*</span>
        </label>
        <g:textField id="deductionDate" name="deductionDate" required=""
                     value="${formatDate(format:'dd/MM/yyyy',date:hrEmployeeDeductionInstance?.deductionDate)}" style="text-align: center;"   class="deductionDate form-control"/>
    </div>
</div>

<div class="col-xs-6 fieldcontain ${hasErrors(bean: hrEmployeeDeductionInstance, field: 'deductionAmount', 'error')} required">
    <div class="form-group">
        <label for="deductionAmount">
            <g:message code="hrEmployeeDeduction.deductionAmount.label" default="Deduction Amount" />
            <span class="required-indicator">*</span>
        </label>
        <g:field name="deductionAmount" value="${fieldValue(bean: hrEmployeeDeductionInstance, field: 'deductionAmount')}" required="" style="text-align: right;" class="form-control"/>
    </div>
</div>

<div class="col-xs-6 fieldcontain ${hasErrors(bean: hrEmployeeDeductionInstance, field: 'deductionReason', 'error')} ">
    <div class="form-group">
        <label for="deductionReason">
            <g:message code="hrEmployeeDeduction.deductionReason.label" default="Deduction Reason" />

        </label>
        <g:textArea name="deductionReason"  cols="20" rows="5" value="${hrEmployeeDeductionInstance?.deductionReason}" class="form-control"/>
    </div>
</div>



