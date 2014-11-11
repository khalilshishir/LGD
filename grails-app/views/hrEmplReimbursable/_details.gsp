<script type="text/javascript">
    var childCount = ${HrEmplReimbursableInstance?.hrEmplReimbursableDetail?.size()} + 0;
    $(document).ready(function() {
        <g:if test="${params.action == 'create'}">

        addChild();
        </g:if>
    });


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
        var htmlId = 'hrEmplReimbursableDetail['+childCount+'].';

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

        clone.find("select[id$=allowanceTypeId]").attr('id', htmlId + 'allowanceTypeId').attr('name', htmlId + 'allowanceTypeId');

        clone.find("input[id$=allowanceAmount]").attr('id',htmlId + 'allowanceAmount').attr('name',htmlId + 'allowanceAmount');

        clone.find("input[id$=allowanceDate]").attr('id',htmlId + 'allowanceDate').attr('name',htmlId + 'allowanceDate');

        clone.find("select[id$=isPaid]").attr('id', htmlId + 'isPaid').attr('name', htmlId + 'isPaid');

        clone.attr('id', 'detail'+childCount);
        $("#detailList tbody:last").append(clone);
        clone.show();
        childCount++;
        clone.find("input[id$=allowanceDate]").removeClass('hasDatepicker').datepicker({ dateFormat:'dd/mm/yy'});

    }

</script>
<div align="center" style="width: 100%;">
<table id="detailList" style="width: 100%;">
    <tr style="background-color: #0188D1; font-family: arial;font-size: 12px; font-weight: bold; color: white; height: 25px;">
        <th>Allowance Type</th>
        <th>Allowance Amount</th>
        <th>Allowance Date</th>
        <th>Paid Status</th>
    </tr>
    <g:render template='detail' model="['hrEmplReimbursableDetail':null,'i':'_clone','hidden':true]"/>
    <g:each var="hrEmplReimbursableDetail" in="${hrEmplReimbursableDetailList}" status="i">
        <g:render template='detail' model="['hrEmplReimbursableDetail':hrEmplReimbursableDetail,'i':i]"/>
    </g:each>
</table>
<input type="button" value="Add Detail" onclick="addChild();" class="btn btn-success"  />
</div>