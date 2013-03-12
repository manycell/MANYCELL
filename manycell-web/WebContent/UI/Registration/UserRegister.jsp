<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ManyCell User Registration System</title>

</head>
<body>
<h1>Register to get access to the simulation server</h1> 

<s:actionerror />
 		<s:fielderror />
 		<s:form action="doRegister" method="POST" enctype="multipart/form-data">    		 
              		    
         	    <table>  
         	    	<s:textfield label="Access Code" name="accesscode" value="238238238" size="10" maxlength="10" />        	       			      			         			
         			<s:textfield label="Username" name="username" size="10" maxlength="10" />  
         			<s:textfield label="Full Name" name="name" size="32" maxlength="40" /> 
         			<s:textfield label="Email-Address" name="email" size="32" maxlength="32" />       							
					<s:password label="Password" name="password" size="32" maxlength="32"/>
					<s:password label="Confirm Your Password" name="cPassword" size="32" maxlength="32"/>							      			
         	    </table>	   
         	          
    			<table>      			      
         		<tr>
        			<td>
        				 <s:submit value="Register" align="center" />
        			 </td>
         		</tr>          
     		</table>
    	</s:form> 
    	
</body>
</html>