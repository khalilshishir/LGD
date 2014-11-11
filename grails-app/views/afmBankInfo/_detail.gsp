<%@ page import="accounts.AfmBankInfo;accounts.AfmBankBranch" %>

<tr id="afmBankBranch${i}" name="tr[${i}]" class="detail-div" onclick="rowNo(this)" <g:if test="${hidden}">style="display:none;"</g:if>>
    <td>
        <g:hiddenField name='afmBankBranch[${i}].id' value='${afmBankBranch?.id}'/>
        <g:hiddenField name='afmBankBranch[${i}].deleted' value='false'/>
        <g:hiddenField name='afmBankBranch[${i}].new' value="${afmBankBranch?.id == null?'true':'false'}"/>
        <g:textField name='afmBankBranch[${i}].branchName' id='${i}branchName' value='${afmBankBranch?.branchName}' class='form-control'/>
    </td>
    <td><g:textField name='afmBankBranch[${i}].address' id='${i}address' value='${afmBankBranch?.address}' autocomplete='off'   class='form-control'/></td>

    <td><g:textField name='afmBankBranch[${i}].accountNo' id='${i}accountNo' value='${afmBankBranch?.accountNo}'   class='form-control'/></td>
    <td><g:textField name='afmBankBranch[${i}].accountType' id='${i}accountType' value='${afmBankBranch?.accountType}' autocomplete="off"   class='form-control'/></td>
    <td><g:textField name='afmBankBranch[${i}].accountHeadName' id='${i}accountHeadName' value='${afmBankBranch?.afmChartOfAccounts?.accountHeadName}' autocomplete="off" onclick="autoAccountHeadName(this.id)" onblur="getAccCodeByAccHeadName(this)" onkeyup="getAccCodeByAccHeadName(this)"  class='form-control'/></td>
    <td><g:textField name='afmBankBranch[${i}].accountCode' id='${i}accountCode' value='${afmBankBranch?.afmChartOfAccounts?.accountCode}' onclick="autoAccountCode(this.id)"   onkeyup="getAccountCode(this)" onblur="getAccountCode(this);"  class='form-control' /></td>
    <td><span class='delButton' id='delButton${i}' name='results[${i}].delButton'><img
            src="${resource(dir: 'images/skin', file: 'database_delete.png')}"/></span></td>
</tr>
