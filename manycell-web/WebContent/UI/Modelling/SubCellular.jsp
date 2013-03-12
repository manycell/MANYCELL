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
<h2>Multi-scale Model: Sub-cellular Data Specification</h2> 
<hr/>

<s:actionerror />
 		<s:fielderror /> 
 <s:if test="modelName ==null && speciesNames==null"> 	
 <h3>Sub-Cellular SBML model</h3> 
 <br/>		
 <s:form action="doSubmitSubCellularModel" method="POST" name="upload" enctype="multipart/form-data"> 
 <table>	
 <tr>
 <td>
 	<s:label value="Load SBML Model"/>
 </td>
 <td>	
        <s:file name="sbml" /> 
 </td>   
 <td>    
        <s:submit value="Upload Model" align="center" />  
  </td>
  </tr>    	 	
</table>	
  </s:form>
  
  <h4><s:label value="OR Select sub-cellular model from ManyCell" /></h4>  
<s:form action="doSubmitSubCellularModel" name="samples" id="form_samples" method="POST" enctype="multipart/form-data">	
	 <s:action name="doListSubCellularModelDirectory" executeResult="true"/>      
</s:form> 	
	<!--
<s:form action="doSubmitSubCellularModel" name="samples" method="POST" enctype="multipart/form-data"> 
 
 <table>   
 <tr>  	
 <td>
 	<s:label value="SBML Model"/>
 </td>
    <td>
     <s:select label="SBML Model" name="pathway"
		list="#{'please select':'please select','model-1':'Basic Sub-Cellular'}"
		value="selected" required="true" />
		</td>
		<td>
         <s:submit value="Submit" align="right" id="path" />
      	 </td>
     </tr>
 </table>
</s:form> -->

<hr/>
</s:if>
 <s:if test="modelName !=null && species!=null"> 
 <h4>Sub-cellular SBML model submitted</h4>
 Model Name: <s:property value="modelName" />	
 
 <h4> Selected sub-cellular species for trajectory tracking</h4>
 <table>
 	<tr>
 		<td><b>Name</b></td>
 		<td/>
 		<td><b>Initial Value</b></td>
 		</tr>
 	<c:forEach var="sp" items="${species}">
 	<tr>
 		<td><c:out value="${sp.name}"></c:out> </td>
 		<td/>
 		<td><c:out value="${sp.value}"></c:out> </td>
 	</tr>
	</c:forEach>        	
</table>
 <hr/>
 <div>
<s:form action="doSubmitSubCellularData" id="frm_demo"  method="POST" enctype="multipart/form-data"> 
   
     <b>Simulation parameters</b><br/>	 
      <table>
		<tr>
		<td>
		<s:label value = "Start Time"/>
		</td>
			<td><s:textfield  name="startOutputTime"
				value="0" size="15" maxlength="5" /></td>
			<td>
		</tr>

		<tr>
		<td>
		<s:label value = "Step Size"/>
		</td>
			<td><s:textfield name="stepSize" value="10"
				size="15" maxlength="5" />
			</td>
		</tr>
		<tr>
		<td>
		<s:label value = "Number of Steps"/>
		</td>
			<td><s:textfield name="stepNumber"
				value="10" size="15" maxlength="5" /></td>
		</tr>
		</table>
		
		<table>      			      
     	<tr>     	
      	<td>
         <s:submit value="Save Data" align="center" />
      	 </td>      	 
       </tr>          
     	</table>	
     </s:form> 
     </div>   		 
   
   <hr/> 
 </s:if>   
 <s:if test="speciesNames != null">
 <h4>Sub-cellular SBML model submitted</h4>
 Model Name: <s:property value="modelName" />
 <br/>
 <br/>
 <div>
<s:form action="doSubmitSubCellularData" id="frm_demo"  method="POST" enctype="multipart/form-data">       
     <b>Simulation parameters</b> <br/>	 
      <table>
		<tr>
		<td>
		<s:label value = "Start Time"/>
		</td>
			<td><s:textfield  name="startOutputTime"
				value="0" size="15" maxlength="5" /></td>
			<td>
		</tr>

		<tr>
		<td>
		<s:label value = "Step Size"/>
		</td>
			<td><s:textfield name="stepSize" value="10"
				size="15" maxlength="5" />
			</td>
		</tr>
		<tr>
		<td>
		<s:label value = "Number of Steps"/>
		</td>
			<td><s:textfield name="stepNumber"
				value="10" size="15" maxlength="5" /></td>
		</tr>
		</table>
		<hr/>
		<table>
		<tr>     	
      	<td>
         <s:submit value="Save Data" align="center" />      	      	 
      	 OR 
      	 <button type="button" onclick="popup('AddSpeciesWindow.jsp')" >Add Species! </button></td>
      	 </tr>
      	 <tr>
      	 <td><b>To track sub-cellular species trajectory during simulation </b></td>
       </tr>       	         
     	</table>	
     </s:form> 
     </div>    
   <hr/> 	
  </s:if>       	
</body>
</html>