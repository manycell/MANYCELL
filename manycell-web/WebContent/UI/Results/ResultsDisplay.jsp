<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<s:set name="theme" value="'simple'" scope="page" />

<div id="cloneRoot"> 
 <h4>Select Sub-Cellular Model to view Multiscale Simulation Results
 <s:select name="pathway" list="fileNames" onchange="javascript:show_model_name();return false;"></s:select>
 </h4>
	<s:url id="d_url" action="doGetResults" /> 
			<sx:div id="details" href="%{d_url}" listenTopics="show_model_name" formId="form_samples" showLoadingText=".....">
		
		</sx:div>	
		
 </div> 