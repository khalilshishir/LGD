<%@ page import="fixedAsset.ASSET_BOOK_HISTORY" %>



<div class="fieldcontain ${hasErrors(bean: ASSET_BOOK_HISTORYInstance, field: 'ASSET_BOOK_ID', 'error')} ">
	<label for="ASSET_BOOK_ID">
		<g:message code="ASSET_BOOK_HISTORY.ASSET_BOOK_ID.label" default="ASSETBOOKID" />
		
	</label>
	<g:select id="ASSET_BOOK_ID" name="ASSET_BOOK_ID.id" from="${fixedAsset.ASSET_BOOK.list()}" optionKey="id" value="${ASSET_BOOK_HISTORYInstance?.ASSET_BOOK_ID?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ASSET_BOOK_HISTORYInstance, field: 'NOTE', 'error')} ">
	<label for="NOTE">
		<g:message code="ASSET_BOOK_HISTORY.NOTE.label" default="NOTE" />
		
	</label>
	<g:textField name="NOTE" value="${ASSET_BOOK_HISTORYInstance?.NOTE}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ASSET_BOOK_HISTORYInstance, field: 'CREATE_DATE', 'error')} ">
	<label for="CREATE_DATE">
		<g:message code="ASSET_BOOK_HISTORY.CREATE_DATE.label" default="CREATEDATE" />
		
	</label>
	<g:datePicker name="CREATE_DATE" precision="day"  value="${ASSET_BOOK_HISTORYInstance?.CREATE_DATE}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: ASSET_BOOK_HISTORYInstance, field: 'user_id', 'error')} ">
	<label for="user_id">
		<g:message code="ASSET_BOOK_HISTORY.user_id.label" default="Userid" />
		
	</label>
	<g:field name="user_id" type="number" value="${ASSET_BOOK_HISTORYInstance.user_id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ASSET_BOOK_HISTORYInstance, field: 'ID', 'error')} required">
	<label for="ID">
		<g:message code="ASSET_BOOK_HISTORY.ID.label" default="ID" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="ID" type="number" value="${ASSET_BOOK_HISTORYInstance.ID}" required=""/>
</div>

