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
 <title>
Monoscale Simulation in ManyCell
</title>
</head>
	<body>
	<s:set name="theme" value="'simple'" scope="page" />
<script>
 function show_model_name() {
dojo.event.topic.publish("show_model_name");
}
</script>
	<div >
		<h1>Sensitivity Analysis of sub-cellular models</h1>
		<h3>Example sub-cellular models in ManyCell</h3>
 	</div>
  		<s:actionerror />
 		<s:fielderror />
 		<s:form action="doRunSensitivity" name="samples" id="form_samples" method="POST" enctype="multipart/form-data">	
   		
   		<s:action name="doListSubCellularModelDirectoryTimeCourse" executeResult="true"/>    	
         		
         		<br/>         		
         		<table> 
         		<tr>
         		<td>
         		<b>Sensitivity parameters</b> 
         		</td>
         		</tr>
         		<tr>
         		<td>
         		Function
         		</td>  
         		<td>     		
         		<s:select 
         			 name = "function"  
         			list="#{'selectT':'please select','Concentration Fluxes of Reaction':'Concentration Fluxes of Reaction',
         			'Non-Constant Concentration of Species':'Non-Constant Concentration of Species'}" value="selected"
         			required="true"/> 
         		</td>
         		</tr>
         		<tr>
         		<td>
         		Variable
         		</td>	
         		<td>
         			<s:select 
         			name = "variable"  
         			list="#{'selectT':'please select','All Parameter Values':'All Parameter Values','Initial Concentration':'Initial Concentration'}" value="selected"
         			required="true"/> 
         			</td>
         			</tr>
         		<tr>
         		<td>
         		Delta Factor
         		</td>
    				<td>
        				<s:textfield name="deltaFactor" value="0.001"/>  
         			</td>
   		 			
         		</tr>  
         		
         		<tr>
         		<td>
         		Delta Minimum
         		</td>
    				<td>
        				<s:textfield name="deltaMinimum" value="1e-12"/>  
         			</td>
   		 			
         		</tr>      
         		         
     		 
         		<tr>
        			 <td>
        				 <s:submit value="Submit" align="center" />
        			 </td>
        			
         		</tr>          
     		</table>
    	</s:form>
</body>
</html>