<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<s:set name="theme" value="'simple'" scope="page" />

<div id="cloneRoot"> 
 <table>  
 <tr>
 <th>
 Models
 </th>
 <th align="center">
 Name
 </th>
 <th/>
 </tr> 
 <tr> 
    <td>
     <s:select name="pathway" list="fileNames" onchange="javascript:show_model_name();return false;"></s:select>
		</td>
		<td> <s:url id="d_url" action="doLoadSubCellularModel" /> 
			<sx:div id="details" href="%{d_url}" listenTopics="show_model_name" formId="form_samples" showLoadingText="....."></sx:div>	
		</td>		
     </tr>
 </table>
 </div> 