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
		<h1>Time course simulation of sub-cellular models</h1>
		<h3>Example sub-cellular models in ManyCell</h3>
 	</div>
  		<s:actionerror />
 		<s:fielderror />
 		<s:form action="doRunDeterministicSimulator" name="samples" id="form_samples" method="POST" enctype="multipart/form-data">	
   		<s:action name="doListSubCellularModelDirectoryTimeCourse" executeResult="true"/>   	
   	<!--		<table>
    			<tr>
    			<td>
        				<s:file name="model" label="Model" />  
         			</td>
   		 			
         		</tr>          		 
         		
         			<s:select 
         			label = "Input model format" name = "inputFormat"  
         			list="#{'selectT':'please select','sbml':'sbml', 'CopasiML':'CopasiML'}" value="selected"
         			required="true"/>   
         			<s:select 
         			label = "Required output format" name = "outputFormat"  
         			list="#{'selectT':'please select','sbrml':'sbrml', 'COPASI native format':'COPASI native format'}" value="selected"
         			required="true"/>          
         		</table> -->
         		
         		<br/>
         		<table>         		
         		<tr><td><b>Simulation parameters</b></td><td></td><td></td></tr>
         		<tr>
    			<td>
        		Start time
         		</td>
         		<td>
         		<s:textfield  name="startOutputTime" value ="0" size="15" maxlength="5" />
         		</td>        		   		 			
         		</tr> 
         		 
       			 <tr>
    			<td>
        		Step size
         			</td>
         			<td>
         			<s:textfield name="stepSize" value ="0.01" size="15" maxlength="5" />
         			</td>
   		 			
         		</tr>  
         		   
         		<tr>
    			<td>
        		Number of steps
         			</td>
         			<td>
         			<s:textfield  name="stepNumber" value= "10" size="15" maxlength="5" />
         			</td>
   		 			
         		</tr>     
         		<tr>
        			 <td>
        				 <s:submit value="Simulate" align="center" />
        			 </td>
        			
         		</tr>          
     		</table>
    	</s:form>
</body>
</html>