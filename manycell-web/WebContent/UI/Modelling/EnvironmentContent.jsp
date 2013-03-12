<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <h2>Multi-scale Model: Micro-Environment/Bioreactor Data Specification</h2> 
<hr/>
  <s:actionerror />
 <s:fielderror />
 		  		
 <s:if test="cellularData != null"> 		
 <s:form action="doSubmitEnvironmentData" method="POST" id="frm_demo" enctype="multipart/form-data">   
 <div id="readroot1" style="display: block"> 	
 	     	<table>	  	    
         	   <s:select 
         			label = "Environment Type" name = "type"  
         			list="#{'Yeast Cell Batch Culture ':'Yeast Cell Batch Culture', 
         			'Yeast Cell Continuous Culture':'Yeast Cell Continuous Culture', 'Tissue Culture':'Tissue Culture'}" value="selected"
         			required="true"/> 
         			<s:textfield label="Volume" name="volume" value="1.0" size="5" maxlength="5" />
        	    <s:select 
         			label = "Unit" name = "unit"  
         			list="#{'liter':'liter', 'milliliter':'milliliter', 'microliter':'microliter'}" value="selected"
         			required="true"/>					 
         	</table>  	   
         	        			
   </div>    	     	
     <hr/>     		
     	<b>Identify nutrient species</b>      	
	     	   		
	   <s:action name="doAddNutrientSpeciesWindow" executeResult="true"/> 
	    
		<table>      			      
     	<tr>     	
      	<td>
         <s:submit value="Save Data" align="center" />
      	 </td>
      	 
       </tr>          
     	</table> 	
  </s:form>  
   </s:if>	
   <!--
</body>
</html>-->