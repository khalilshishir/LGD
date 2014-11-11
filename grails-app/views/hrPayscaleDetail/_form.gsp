<%@ page import="hrms.HrPayscaleDetail" %>



<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'stage', 'error')} ">
	<label for="stage">
		<g:message code="hrPayscaleDetail.stage.label" default="Stage" />
		
	</label>
	<g:textField name="stage" maxlength="15" value="${hrPayscaleDetailInstance?.stage}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'basic', 'error')} ">
	<label for="basic">
		<g:message code="hrPayscaleDetail.basic.label" default="Basic" />
		
	</label>
	<g:field type="number" name="basic" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'basic')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'da', 'error')} required">
	<label for="da">
		<g:message code="hrPayscaleDetail.da.label" default="Da" />
		%{--<span class="required-indicator">*</span>--}%
	</label>
	<g:field type="number" name="da" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'da')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'houseRent', 'error')} ">
	<label for="houseRent">
		<g:message code="hrPayscaleDetail.houseRent.label" default="House Rent" />
		
	</label>
	<g:field type="number" name="houseRent" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'houseRent')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'conveyanceAllow', 'error')} ">
	<label for="conveyanceAllow">
		<g:message code="hrPayscaleDetail.conveyanceAllow.label" default="Conveyance Allow" />
		
	</label>
	<g:field type="number" name="conveyanceAllow" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'conveyanceAllow')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'washingAllow', 'error')} ">
	<label for="washingAllow">
		<g:message code="hrPayscaleDetail.washingAllow.label" default="Washing Allow" />
		
	</label>
	<g:field type="number" name="washingAllow" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'washingAllow')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'medicalAllow', 'error')} ">
	<label for="medicalAllow">
		<g:message code="hrPayscaleDetail.medicalAllow.label" default="Medical Allow" />
		
	</label>
	<g:field type="number" name="medicalAllow" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'medicalAllow')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'pfContribution', 'error')} ">
	<label for="pfContribution">
		<g:message code="hrPayscaleDetail.pfContribution.label" default="Pf Contribution" />
		
	</label>
	<g:field type="number" name="pfContribution" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'pfContribution')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'festivalBonus', 'error')} ">
	<label for="festivalBonus">
		<g:message code="hrPayscaleDetail.festivalBonus.label" default="Festival Bonus" />
		
	</label>
	<g:field type="number" name="festivalBonus" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'festivalBonus')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'annualBonus', 'error')} ">
	<label for="annualBonus">
		<g:message code="hrPayscaleDetail.annualBonus.label" default="Annual Bonus" />
		
	</label>
	<g:field type="number" name="annualBonus" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'annualBonus')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'gratuity', 'error')} ">
	<label for="gratuity">
		<g:message code="hrPayscaleDetail.gratuity.label" default="Gratuity" />
		
	</label>
	<g:field type="number" name="gratuity" value="${fieldValue(bean: hrPayscaleDetailInstance, field: 'gratuity')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: hrPayscaleDetailInstance, field: 'payscaleIdHrPayscale', 'error')} required">
	<label for="payscaleIdHrPayscale">
		<g:message code="hrPayscaleDetail.payscaleIdHrPayscale.label" default="Payscale Id Hr Payscale" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="payscaleIdHrPayscale" name="payscaleIdHrPayscale.id" from="${hrms.HrPayscale.list()}" optionKey="id" required="" value="${hrPayscaleDetailInstance?.payscaleIdHrPayscale?.id}" class="many-to-one"/>
</div>

