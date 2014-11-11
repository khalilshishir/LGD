<script type="text/javascript">
    var childCount = ${hrCalendarMonthInstance?.hrHolidayCalendars?.size()} + 0;

    $('.sDate').live('focus', function () {
        $('.sDate').datepicker({ dateFormat: 'dd/mm/yy'});
    });

    $('.eDate').live('focus', function () {
        $('.eDate').datepicker({ dateFormat: 'dd/mm/yy'});
    });

    $('.delButton').live('click', function() {
        var prnt = $(this).parents(".detail-div");
        var delInput = prnt.find("input[id$=deleted]");
        delInput.attr('value', 'true');
        prnt.hide();
    });

    function addChild() {
      // debugger;
        var clone = $("#detail_clone").clone();
        var htmlId = 'hrHolidayCalendars[' + childCount + '].';
        var displayItemId = 'hrHolidayCalendars' + childCount;
        clone.find("input[id$=id]")
                .attr('id', htmlId + 'id')
                .attr('name', htmlId + 'id');

        clone.find("input[id$=deleted]")
                .attr('id', htmlId + 'deleted')
                .attr('name', htmlId + 'deleted');

        clone.find("input[id$=new]")
                .attr('id', htmlId + 'new')
                .attr('name', htmlId + 'new')
                .attr('value', 'true');

        clone.find("select[id^=holidayTypeIdHrLookup]")
                .attr('id', 'holidayTypeIdHrLookup' + childCount)
                .attr('name', htmlId + 'holidayTypeIdHrLookup');

        clone.find("select[id^=holidayTypeDescIdHrLookup]")
                .attr('id', 'holidayTypeDescIdHrLookup' + childCount)
                .attr('name', htmlId + 'holidayTypeDescIdHrLookup');

        clone.find("input[id^=startDate]").attr('id', displayItemId + 'startDate').attr('name', htmlId + 'startDate');

        clone.find("input[id^=endDate]").attr('id', displayItemId + 'endDate').attr('name', htmlId + 'endDate');

        clone.find("input[id^=totalDays]").attr('id', displayItemId + 'totalDays').attr('name', htmlId + 'totalDays').attr('value','0');

        clone.attr('id', 'detail' + childCount);
        $("#detailList").append(clone);
        clone.show();
        childCount++;
        clone.find('input[id$=startDate]').removeClass('hasDatepicker').datepicker({ dateFormat: 'dd/mm/yy'});
       clone.find('input[id$=endDate]').removeClass('hasDatepicker').datepicker({ dateFormat: 'dd/mm/yy'});
    }




</script>

<br/><br/>
<div align="center">
<table id="detailList" style="border: 1px; width: 100%" >
    <tr style="background-color: #0188D1; font-family: arial;font-size: 12px; font-weight: bold; color: white; height: 25px;">
        %{--<th>Calendar Year</th>--}%
        <th>Holiday Type</th>
        <th>Holiday Description</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Total Days</th>
    </tr>

    <g:render template='detail' model="['hrHolidayCalendar':null,'i':'_clone','hidden':true]"/>

    <g:each var="hrHolidayCalendar" in="${hrHolidayCalendars}" status="i">

        <g:render template='detail' model="['hrHolidayCalendar':hrHolidayCalendar,'i':i]"/>
    </g:each>
</table>
</div>
<div >
    <input align="right" class="btn-sm" type="button" value="Add Detail" onclick="addChild();" />
</div>
