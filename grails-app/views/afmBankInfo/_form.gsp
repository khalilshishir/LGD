<%@ page import="accounts.AfmBankInfo" %>



<table style="width: 100%;">
    <tr>

        <td>
            <div class="col-xs-3">
                <div class="form-group">
                    <label for="bankName"><g:message code="afmBankInfo.bankName.label" default="Bank Name" /></label>
                    <g:textField class="form-control" id="bankName" name="bankName" required="" value="${afmBankInfoInstance?.bankName}"/>
                </div>
            </div>
        </td>
    </tr>

    <tr>
        <td>
            <g:render template="details"/>
        </td>
    </tr>

</table>






