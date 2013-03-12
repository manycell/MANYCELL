<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <html>
<head>
<body>
<s:set name="theme" value="'simple'" scope="page" />
<h3>Information about your sub-cellular model</h3>
<s:form action="doSubmitMonoscaleSubCellularModel" id="monoscal"  method="POST" enctype="multipart/form-data"> 
     <table>
		<tr>
		<td>
		<s:label value = "Username:"/>
		</td>
			<td><s:textfield  name="username"
				value="%{user}" size="15" maxlength="5" readonly="1" /></td>
			<td>
		</tr>
		<tr>
		<td>
		<s:label value = "Model name:"/>
		</td>
			<td><s:textfield name="modelName"
				value="%{modelName}" size="50" maxlength="50" readonly="1" /></td>
		</tr>
		
		<tr>
		<td>
		<s:label value = "Model Description:"/>
		</td>
			<td><s:textfield name="description"
				value="%{description}" size="50" maxlength="80" readonly="1" /></td>
		</tr>
		<tr>
		<td>
		<s:label value = "Time of submission:"/>
		</td>
			<td><s:textfield name="timestamp" value="%{timestamp}"
				size="15" maxlength="20" readonly="1"/>
			</td>
		</tr>
		
		
		</table>
		
		<table>      			      
     	<tr>     	
      	<td>
         <s:submit value="Submit" align="center" /> <b>To store your model in ManyCell repository</b>
      	 </td>      	 
       </tr>          
     	</table>	
     </s:form> 
 </body>
</html>