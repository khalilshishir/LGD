<%@ page import="hrms.HrEmplReimbursable; hrms.HrLookupType; hrms.HrLookup" %>
<tr id="detail${i}" name="tr[${i}]" class="detail-div" <g:if test="${hidden}">style="display:none;"</g:if>>
    <td>
        <g:hiddenField name='hrEmplReimbursableDetail[${i}].id' value='${hrEmplReimbursableDetail?.id}'/>
        <g:hiddenField name='hrEmplReimbursableDetail[${i}].deleted' value='false'/>
        <g:hiddenField name='hrEmplReimbursableDetail[${i}].new' value="${hrEmplReimbursableDetail?.id == null?'true':'false'}"/>
        <g:select name="hrEmplReimbursableDetail[${i}].allowanceTypeId"
                  from="${HrLookup.findAllByHrLookupTypeIdLookupType(HrLookupType.findByLookupType('ALLOWANCE TYPE'))}" optionKey="id" optionValue="lookupValue"
                  value="${hrEmplReimbursableDetail?.allowanceType?.id}" noSelection="['null':'-Select One-']" class="form-control"/>
    </td>

    <td><g:textField name='hrEmplReimbursableDetail[${i}].allowanceAmount'  value='${hrEmplReimbursableDetail?.allowanceAmount}' style="text-align: right;" class="form-control"/></td>

    <td><g:textField name='hrEmplReimbursableDetail[${i}].allowanceDate' id="${i}allowanceDate"   value="${formatDate(format: 'dd/MM/yyyy', date: hrEmplReimbursableDetail?.allowanceDate)}" class='dtl-dt form-control'/></td>
    <td><g:select name='hrEmplReimbursableDetail[${i}].isPaid' id='${i}isPaid' from="${['No','Yes']}" keys='${['N','Y']}' value='${hrEmplReimbursableDetail?.isPaid}' style='width: 99%;' class='form-control'/></td>

    <td><span class='delButton' id='delButton${i}' name='hrEmplReimbursableDetail[${i}].delButton' ><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span></td>

</tr>