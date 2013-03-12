<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<s:set name="theme" value="'simple'" scope="page" />

<div id="cloneRoot">	
	<table>	
	<tr>
		<td>Species</td><td>Initial value</td>
	</tr> 
	<tr>
		<td>
			<s:select list="speciesNames" name="SName"  onchange="javascript:show_model_name();return false;"></s:select>	
		</td>	
		<td> <s:url id="d_url" action="speciesValue" /> 
			<sx:div id="details" href="%{d_url}" listenTopics="show_model_name" formId="frm_demo" showLoadingText="....."></sx:div>	
		</td>	
	</tr>
	</table>		
 </div> 