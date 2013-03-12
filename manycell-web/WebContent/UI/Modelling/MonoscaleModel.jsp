<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
 <html>
 <head>
 <title>
ManyCell: Multiscale Agent-Based Modelling and Simulation Web Portal
</title>
</head>
	<body>
	<div >
		<h1>Sub-Cellular SBML Model Submission </h1>
		<h5>Please note that your model must include at least one event for cell division and be coupled to external nutrient source. </h5> 
 	</div>
  		<s:actionerror />
 		<s:fielderror />
 		<s:form action="doCheckMonoscaleSubCellularModel" method="POST" enctype="multipart/form-data">    
   			<table>
    			<tr>
    			<td>
        			<s:file name="sbml" label="Model" />  
         		</td>
         		<td>
   		 			<s:textfield name="description" size="80" label="Description"/>
         			</td>
         		</tr>          
         						         			       
         		</table>
         		<table>         		
         		
         		<tr><td></td>
        			 <td>
        				 <s:submit value="Send" align="center" />
        			 </td>
        			 <td></td>
         		</tr>          
     		</table>
    	</s:form>
</body>
</html>