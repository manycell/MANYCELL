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
	
	<br/>
	<b>Select cell phenotype/behaviour and event trigger variable </b> 
	<table>
     	 <tr>
     <td>
     Behaviour     
     </td>
     <td>
     Event trigger parameter     
     </td>
    <td>
     Cell size partition variable     
     </td>  
     <td>
     Cell nutrient consumption variable     
     </td>        
     </tr>
     <tr>
     <td>     
     	<s:select name = "behaviour"  
     	list="#{'division':'division'}" value="selected" required="true"/>         
     </td>
     <td>
     <s:select list="paramNames" name="SPName" /> 
     </td>
     <td>
     <s:select list="paramNames" name="SSPName" /> 
     </td>     
     <td>
     <s:select list="paramNames" name="NUTPName" /> 
     </td>
     	 </tr>
     	 </table>  		
 </div> 