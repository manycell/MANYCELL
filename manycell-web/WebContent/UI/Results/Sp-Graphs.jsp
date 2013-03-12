<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:if test="modelName != null">
	<h3>Model Name: <s:property value="modelName"/> </h3>
</s:if>
  
   <div name="MyTreeDiv" id="MyTreeDiv">
       <img src="Sp-Graphs.jpeg" alt=""  border="0" />
</div>
