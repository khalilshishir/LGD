<%--
  Created by IntelliJ IDEA.
  User: kcbarmon
  Date: 1/1/13
  Time: 12:04 PM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript">
    var childCount = ${hrPayscaleInstance?.hrPayscaleDetails?.size()} + 0;

   /* for default row coding
    $(document).ready(function()
    {
      <%--  <g:if test="${params.action == 'create'}"> --%>
        addChild();
       <%-- </g:if> --%>
    }
    );
     for default row coding */

    $('.delButton').live('click', function() {
        //find the parent div
        var prnt = $(this).parents(".detail-div");
        //find the deleted hidden input
        var delInput = prnt.find("input[id$=deleted]");
        delInput.attr('value','true');
        //hide the div
        prnt.hide();
    });

    function addChild() {
        var clone = $("#detail_clone").clone();
        var htmlId = 'hrPayscaleDetails['+childCount+'].';
        var displayItemId = 'hrPayscaleDetails' + childCount;
        var slNo=  childCount+0;
        //debugger;
        clone.find("input[id$=id]")
                .attr('id',htmlId + 'id')
                .attr('name',htmlId + 'id');

        clone.find("input[id$=deleted]")
                .attr('id',htmlId + 'deleted')
                .attr('name',htmlId + 'deleted');

        clone.find("input[id$=new]")
                .attr('id',htmlId + 'new')
                .attr('name',htmlId + 'new')
                .attr('value', 'true');

        clone.find("input[id$=stage]")
                .attr('id', htmlId + 'stage')
                .attr('name', htmlId + 'stage').attr('value',slNo);

        clone.find("input[id^=basic]")
                .attr('id',displayItemId+'basic')
                .attr('name',htmlId + 'basic').attr('value','0');

        clone.find("input[id^=da]")
                .attr('id',displayItemId+'da')
                .attr('name',htmlId + 'da').attr('value','0');

        clone.find("input[id^=houseRent]")
                .attr('id',displayItemId + 'houseRent')
                .attr('name',htmlId + 'houseRent').attr('value','0');

        clone.find("input[id^=conveyanceAllow]")
                .attr('id',displayItemId + 'conveyanceAllow')
                .attr('name',htmlId + 'conveyanceAllow').attr('value','0');

        clone.find("input[id^=washingAllow]")
                .attr('id',displayItemId + 'washingAllow')
                .attr('name',htmlId + 'washingAllow').attr('value','0');

        clone.find("input[id^=medicalAllow]")
                .attr('id',displayItemId + 'medicalAllow')
                .attr('name',htmlId + 'medicalAllow').attr('value','0');

        clone.find("input[id^=pfContribution]")
                .attr('id',displayItemId + 'pfContribution')
                .attr('name',htmlId + 'pfContribution').attr('value','0');

        clone.find("input[id^=festivalBonus]")
                .attr('id',displayItemId + 'festivalBonus')
                .attr('name',htmlId + 'festivalBonus').attr('value','0');

        clone.find("input[id^=annualBonus]")
                .attr('id',displayItemId + 'annualBonus')
                .attr('name',htmlId + 'annualBonus').attr('value','0');

        clone.find("input[id^=gratuity]")
                .attr('id',displayItemId + 'gratuity')
                .attr('name',htmlId + 'gratuity').attr('value','0');

        clone.find("input[id^=grandTotal]")
                .attr('id',displayItemId + 'grandTotal')
                .attr('name',htmlId + 'grandTotal').attr('value','0');


        clone.attr('id', 'detail'+childCount);
        $("#stage").val(slNo);
        $("#detailList").append(clone);
        clone.show();
        childCount++;
    }

</script>
<style type="text/css">

.myTable th { padding:8px; border:#999 1px solid; background:#D9EDF7 ; }

</style>

<table width="100%" border="0" cellpadding="0" cellspacing="0" id="detailList" class="common-list-table-payscale ">
    <tr class="myTable" style="background-color: #0188D1; font-family: arial;font-size: 12px; font-weight: bold; color: #31708F; height: 25px;">
        <th>Stage</th><th>Basic</th><th>Dearness</th><th>House Rent</th><th>Conveyance</th>
        <th>Washing</th><th>Medical</th><th> PF </th><th>Festival Bonus</th><th>Annual Bonus</th>
        <th>Gratuity</th>
        <th> Grand Total </th>
    </tr>

    <g:render template='detail' model="['hrPayscaleDetail':null,'i':'_clone','hidden':true]"/>

    <g:each var="hrPayscaleDetail" in="${hrPayscaleInstance.hrPayscaleDetails}" status="i">

        <g:render template='detail' model="['hrPayscaleDetail':hrPayscaleDetail,'i':i]"/>
    </g:each>
</table>


<input type="button" value="Add Detail" onclick="addChild();"  class="add-btn btn-sm"/>
