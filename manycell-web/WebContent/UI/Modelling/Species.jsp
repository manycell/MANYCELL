<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="SName != null">
	<td><s:textfield name="SValue" value="%{value}" /></td>
</s:if>