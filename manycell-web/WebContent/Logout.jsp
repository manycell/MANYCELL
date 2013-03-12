<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
<% session.invalidate(); %>
<%= new java.util.Date()%> 
<html>
  <head>
    <title>Logout</title>
  </head>
  <body>
    <h1>Logout</h1>
    <p>You've successfully logged out.</p>
    <p>
      You can navigate back to <a href="index.jsp">Home</a>,
      but you'll need to login again.      
    </p>
  </body> 
</html>