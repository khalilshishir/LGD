<%--
  Created by IntelliJ IDEA.
  User: kcbarmon
  Date: 1/1/13
  Time: 12:20 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="hrms.HrPayscaleDetail" %>

 <script type="text/javascript">

     // for basic amount
     $('.bc').live('change',function()
             {
                 var basicId= $(this).closest("tr").find(".bc").attr("id");
                 var daId= $(this).closest("tr").find(".dc").attr("id");
                 var houseRentId= $(this).closest("tr").find(".hc").attr("id");
                 var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
                 var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
                 var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
                 var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
                 var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
                 var annualBonus= $(this).closest("tr").find(".ac").attr("id");
                 var gratuityId= $(this).closest("tr").find(".gc").attr("id");
                 var totalId= $(this).closest("tr").find(".gtc").attr("id");
                 $("#"+totalId).val(
                         parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                        +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                        +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                        +parseFloat($("#"+gratuityId).val())
                 );
             }
     );

     // for dearness allowance
   $('.dc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for house rent allowance
   $('.hc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for Conveyance  allowance
   $('.cc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for washing allowance
   $('.wc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for medical allowance
   $('.mc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for provident fund allowance
   $('.pc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for festival bonus allowance
   $('.fc').live('change',function()
           {

               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");

               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for annual bonus allowance
   $('.ac').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );

     // for gratuity allowance
   $('.gc').live('change',function()
           {
               var basicId= $(this).closest("tr").find(".bc").attr("id");
               var daId= $(this).closest("tr").find(".dc").attr("id");
               var houseRentId= $(this).closest("tr").find(".hc").attr("id");
               var conveyanceAllowId= $(this).closest("tr").find(".cc").attr("id");
               var washingAllowId= $(this).closest("tr").find(".wc").attr("id");
               var medicalAllowId= $(this).closest("tr").find(".mc").attr("id");
               var pfContributionId= $(this).closest("tr").find(".pc").attr("id");
               var festivalBonusId= $(this).closest("tr").find(".fc").attr("id");
               var annualBonus= $(this).closest("tr").find(".ac").attr("id");
               var gratuityId= $(this).closest("tr").find(".gc").attr("id");
               var totalId= $(this).closest("tr").find(".gtc").attr("id");
               $("#"+totalId).val(
                       parseFloat($("#"+basicId).val())+parseFloat($("#"+daId).val())+parseFloat($("#"+houseRentId).val())
                               +parseFloat($("#"+conveyanceAllowId).val())+parseFloat($("#"+washingAllowId).val())+parseFloat($("#"+medicalAllowId).val())
                               +parseFloat($("#"+pfContributionId).val())+parseFloat($("#"+festivalBonusId).val())+parseFloat($("#"+annualBonus).val())
                               +parseFloat($("#"+gratuityId).val())
               );
           }
   );


</script>


%{--}<g:if test="${hrPayscaleDetail?.stage == null }"></g:if>--}%

<tr id="detail${i}" name="tr[${i}]" class="detail-div" <g:if test="${hidden}">style="display:none;"</g:if>>

    <td><g:hiddenField name='hrPayscaleDetails[${i}].id' value='${hrPayscaleDetail?.id}'/>

    <g:hiddenField name='hrPayscaleDetails[${i}].deleted' value='false'/>

    <g:hiddenField name='hrPayscaleDetails[${i}].new' value="${hrPayscaleDetail?.id == null?'true':'false'}"/>

    <g:textField name='hrPayscaleDetails[${i}].stage' value='${hrPayscaleDetail?.stage}' style="text-align: center;" class="form-control"/> </td>

    <td><g:textField  name='hrPayscaleDetails[${i}].basic' value='${hrPayscaleDetail?.basic}' id="basic${i}" class="bc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].da' value='${hrPayscaleDetail?.da}' id="da${i}" class="dc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].houseRent' value='${hrPayscaleDetail?.houseRent}' id="houseRent${i}" class="hc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].conveyanceAllow' value='${hrPayscaleDetail?.conveyanceAllow}' id="conveyanceAllow${i}" class="cc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].washingAllow' value='${hrPayscaleDetail?.washingAllow}' id="washingAllow${i}" class="wc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].medicalAllow' value='${hrPayscaleDetail?.medicalAllow}' id="medicalAllow${i}" class="mc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].pfContribution' value='${hrPayscaleDetail?.pfContribution}' id="pfContribution${i}" class="pc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].festivalBonus' value='${hrPayscaleDetail?.festivalBonus}' id="festivalBonus${i}" class="fc form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].annualBonus' value='${hrPayscaleDetail?.annualBonus}' id="annualBonus${i}" class="ac form-control" style="text-align: right;"/></td>

    <td><g:textField name='hrPayscaleDetails[${i}].gratuity' value='${hrPayscaleDetail?.gratuity}' id="gratuity${i}" class="gc form-control" style="text-align: right;"/></td>

    <g:set var="rowval" value="${i}"></g:set>

    <g:if test="${!('_clone').toString().equals(rowval.toString())}">
    <g:set var="gtotal" value="${Float.valueOf(hrPayscaleDetail.basic)+Float.valueOf(hrPayscaleDetail.da)+Float.valueOf(hrPayscaleDetail.houseRent)+
            Float.valueOf(hrPayscaleDetail.conveyanceAllow)+Float.valueOf(hrPayscaleDetail.washingAllow)+Float.valueOf(hrPayscaleDetail.medicalAllow)+
            Float.valueOf(hrPayscaleDetail.pfContribution)+Float.valueOf(hrPayscaleDetail.festivalBonus)+Float.valueOf(hrPayscaleDetail.annualBonus)+
            Float.valueOf(hrPayscaleDetail.gratuity)}" />
    </g:if>

    <td><g:textField name='hrPayscaleDetails[${i}].grandTotal' value='${gtotal}' id="grandTotal${i}" class="gtc form-control"  readonly="readonly" style="text-align: right;font-weight: bold;"/></td>


    <td><span class='delButton' id='delButton${i}' name='hrPayscaleDetails[${i}].delButton' ><img src="${resource(dir:'images/skin', file:'database_delete.png')}" /></span></td>

</tr>