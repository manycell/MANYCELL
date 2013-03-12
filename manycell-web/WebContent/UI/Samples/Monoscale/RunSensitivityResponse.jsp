<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
 <html>
 <head>
 <title>
Copasi Web User Interface HomePage
</title>
</head>
	<body>
	<div >
		<h1>Sensitivity Analysis Results from ManyCell</h1>
 	</div>
  	<div> 
 		
 			<!-- <p>Your File ContentType: <s:property value="modelContentType" /></p> -->
 			<p><b>Model name:</b> <s:property value="modelName" /></p>
 			  <br/>
 			   <s:set name="rOutputFormat" value="outputFormat"/>
    		 <s:if test="%{#rOutputFormat=='sbrml'}">			 
 		<font size=2 style="font-size: 20pt"><b>Sensitivity Results:  </b></font><a href="<s:property value="results"/>"><font size=2 style="font-size: 20pt">View results in SBRML format</font> </a>
       		</s:if>
       		<s:else>		 		
 		 <pre><s:property value="results"/></pre>
 		</s:else>	
	 </div> 	
</body>
</html>