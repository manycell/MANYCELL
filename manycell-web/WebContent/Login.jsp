<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <title>ManyCell: Multiscale Agent-Based Modelling and Simulation Secure Login Page</title>
  </head>
  <body>
    <h1>Login Page</h1>
    <%
      String error = request.getParameter("error");
      if (error != null) {
    %>
        <p style="color: red">Error: <%=error%>.</p>
    <%
      }
    %>
    <form method="post" action="j_security_check">
      <table cellspacing="3">
        <tr>
          <th align="right">Username:</th>
          <td align="left"><input type="text" name="j_username"/></td>
        </tr>
        <tr>
          <th align="right">Password:</th>
          <td align="left"><input type="password" name="j_password"/></td>
        </tr>
        <tr>
          <th align="right">&nbsp;</th>
          <td align="left"><input type="submit" value="Log In"/></td>
        </tr>
      </table>
    </form>
  </body>
</html>
