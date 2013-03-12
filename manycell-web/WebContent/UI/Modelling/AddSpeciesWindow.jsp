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

function windowClose(){
	self.close();
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
 <s:actionerror />
 <s:fielderror />  	
 	
 <h4> Available sub-cellular species for trajectory tracking</h4>		
 <s:form action="doStoreSpecies" id="frm_demo"  method="POST" enctype="multipart/form-data">   
 <div id="cellreadroot" >		
	<div id="sDiv">	
	    <s:action name="doAddSpeciesWindow" executeResult="true"/>                                                                      
     </div>   
</div> 
	<table>      			      
     	<tr>     	
      	<td>
         <s:submit value="Add Species" align="center" ></s:submit>
      	 </td>
      	 <td>
      	<button type="button" onclick="windowClose()">Cancel</button>
      	 </td>      	 
       </tr>          
     </table>	
  </s:form>   	
  
 <s:if test="species != null"> 
 <hr/>	
 <s:form action="doSubCellularData" method="POST" enctype="multipart/form-data" target="main">
 <h4> Selected sub-cellular species for trajectory tracking</p>
 <table>
 	<tr>
 		<td>Name</td>
 		<td>Initial value</td>
 		</tr>
 	<c:forEach var="sp" items="${species}">
 	<tr>
 		<td><c:out value="${sp.name}"></c:out> </td>
 		<td><c:out value="${sp.value}"></c:out> </td>
 	</tr>
	</c:forEach>	     			      
    <tr>     	
    	<td>
         <s:submit value="Save Data" align="center" onclick="windowClose()"></s:submit>
       </td>      	      	 
       </tr>    	
</table>
</s:form> 
</s:if>  
<hr/>	    	
</body>
</html>
