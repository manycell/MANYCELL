<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
 <html>
 <head>
 <title>
Time Course Simulation of Sub-Cellular Model in ManyCell
</title>
</head>
	<body>
	<div >
	<h1>Time Course Simulation Results</h1>
 	</div>
 	
 	 <s:set name="rOutputFormat" value="outputFormat"/>
    		 <s:if test="%{#rOutputFormat=='sbrml'}">	
 	<div>
 	
 	<img src="<s:property value="plot"/>" alt=""  border="0"  width="65%" />  </div>

<hr/>
<div>
<!-- <h3>Download the Time Course Data in:</h3>-->
<ul>
<li><a href="<s:property value="data"/>">SBRML </a>format, see <a href="http://www.comp-sys-bio.org/SBRML" target ="new">SBRML WEBSITE</a> for more information on SBRML.</li>
<li>.....</li>
</ul>
</div>
</s:if>
<div>
       		<s:else>		 		
 		 <pre><s:property value="results"/></pre>
 		</s:else>	
</div>

<!-- <p>Your File ContentType: <s:property value="modelContentType" /></p> -->
<!--  	<div>  			
 			<p><b>Your Model File Name:</b> <s:property value="modelFileName" /></p>
 			  
 			   <s:set name="rOutputFormat" value="outputFormat"/>
    		 <s:if test="%{#rOutputFormat=='sbrml'}">			 
 		<font size=2 style="font-size: 20pt"><b>Time Course Results:  </b></font><a href="<s:property value="results"/>"><font size=2 style="font-size: 20pt">View results in SBRML format</font> </a>
       		</s:if>
       		<s:else>		 		
 		 <pre><s:property value="results"/></pre>
 		</s:else>	
	 </div> 
	 -->
</body>
</html>