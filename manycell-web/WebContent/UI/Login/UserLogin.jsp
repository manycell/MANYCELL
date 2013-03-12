<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Registration System</title>
</head>
<body>
<h3>Please login to access the simulation server</h3> 
<s:actionerror />
 		<s:fielderror />
 		<s:form action="doCheckUser" method="POST" enctype="multipart/form-data"> 	
              		    
         	    <table>         	    	   			      			         			
         			<s:textfield label="User name" name="userName" size="8" maxlength="8" />         							
					<s:password label="Password" name="password" size="16" maxlength="16"/>											      			
         	    </table>	   
         	          
    			<table>      			      
         		<tr>
        			<td>
        				 <s:submit value="Login" align="center" />
        			 </td>
         		</tr>          
     		</table>
    	</s:form> 
    	<p>Please go to <a href="../Registration/UserRegister.jsp" target = "main"><font size=2 style="font-size: 12pt">Registration</font> </a> page if you have not yet registered.<br>
    	</p>
</body>
</html>