<tr id="detail${i}" name="tr[${i}]" class="detail-div" <g:if test="${hidden}">style="display:none;"</g:if>>

    <td ><g:hiddenField name='hrCalendarMonths[${i}].id' value='${hrCalendarMonth?.id}'/>
    <g:hiddenField name='hrCalendarMonths[${i}].deleted' value='false'/>
    <g:hiddenField name='hrCalendarMonths[${i}].new' value="${hrCalendarMonth?.id == null?'true':'false'}"/>
    <g:textField name='hrCalendarMonths[${i}].calendarYear' value='${hrCalendarMonth?.calendarYear}' style="text-align: center;" class="form-control"/>
    </td>
    %{--<g:select name="hrCalendarMonths[${i}].hrCalendarIdHrCalendar" id="hrCalendarIdHrCalendar${i}"
    from="${hrms.HrCalendar.list()}" optionKey="id" optionValue="calendarYear" value="${hrCalendarMonth?.hrCalendarIdHrCalendar?.id}"/>--}%
    <td ><g:textField name='hrCalendarMonths[${i}].startDate' value="${formatDate(format:'dd/MM/yyyy',date:hrCalendarMonth?.startDate)}"   style="text-align: center;" class="startDate form-control"/></td>
    <td ><g:textField name='hrCalendarMonths[${i}].endDate' value="${formatDate(format:'dd/MM/yyyy',date:hrCalendarMonth?.endDate)}"  style="text-align: center; " class="endDate form-control"/></td>
    <td ><g:textField name='hrCalendarMonths[${i}].periodName' value='${hrCalendarMonth?.periodName}' style="text-align: center;" class="form-control"/></td>

    <td><span class='delButton' id='delButton${i}' name='results[${i}].delButton'><img
    src="${resource(dir: 'images/skin', file: 'database_delete.png')}"/></span></td>

    <td><g:hiddenField name='hrCalendarMonths[${i}].serialNo' value='${hrCalendarMonth?.serialNo}' style="text-align: center;" class="form-control"/></td>
</tr>