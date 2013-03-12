<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <html>
<head>
<sx:head/> 
<script type="text/javascript" >
dojo.require("dojo.widget.*");
dojo.require("dojo.dojo.parser");
dojo.require("dojo.dijit.Tree");

</script>
 <style type="text/css">
@import "dojo/dojo/resources/dojo.css";
@import "dojo/dijit/themes/tundra/tundra.css";
@import "dojo/dijit/themes/dijit.css";
</style>
<title>ManyCell: Multiscale Agent-Based Simulator Web Portal </title>

<SCRIPT language="javascript">
    
function popup(url) 
{
 var width  = 700;
 var height = 500;
 var left   = (screen.width  - width)/2;
 var top    = (screen.height - height)/2;
 var params = 'width='+width+', height='+height;
 params += ', top='+top+', left='+left;
 params += ', directories=no';
 params += ', location=no';
 params += ', menubar=no';
 params += ', resizable=no';
 params += ', scrollbars=yes';
 params += ', status=no';
 params += ', toolbar=no';
 newwin=window.open(url,'windowname5', params);
 if (window.focus) {newwin.focus()}
 return false;
}
</SCRIPT>

</head>
<body> 
<s:set name="theme" value="'simple'" scope="page" />
<script>
 function show_model_name() {
dojo.event.topic.publish("show_model_name");
}
</script>
<h2>Multiscale Simulation Results</h2> 
<hr/>

<s:actionerror />
 <s:fielderror />   
 <br/>		  
<s:form action="doGetResults" name="samples" id="form_samples" method="POST" enctype="multipart/form-data">	
	 <s:action name="doListModelDirForResults" executeResult="true"/>      
</s:form>      	
</body>
</html>