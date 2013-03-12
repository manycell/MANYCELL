<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 				
 <s:set name="theme" value="'simple'" scope="page" />
<h2>Multi-scale Model: Cellular Data Specification</h2> 
<hr/> 
 <s:if test="subCellularData != null"> 
  <s:if test="species!=null">  	
 <s:form action="doSubmitCellularData" method="POST" id="frm_demo" enctype="multipart/form-data">  		
     <div>	
     <table>
     <tr>
     <td>
     <b>Number of Cells to be seeded </b>     
     </td>
     <td>     
     	 <s:textfield name="numberOfCells" value="4"	size="4" maxlength="4" />
     </td>
     	 </tr>
     	 </table>
     	 <br/>
     	 <b>Identify cell size (biomass) variable</b><br/>
     	  	  		
	   <s:action name="doAddNutrientSpeciesAndParamWindow" executeResult="true"/> 	   
	   <br/>   
	   	 
     </div>	 
     <hr/> 	 
 	<b> Selected sub-cellular species for Cell-Cell differentiations</b><br/>
 <table>
 	<tr>
 		<td><b>Name</b></td>
 		<td/>
 		<td><b>Default Value</b></td>
 		</tr>
 	<c:forEach var="sp" items="${species}">
 	<tr>
 		<td><c:out value="${sp.name}"></c:out> </td>
 		<td/>
 		<td><c:out value="${sp.value}"></c:out> </td>
 	</tr>
	</c:forEach>   
	     	
</table>
<table>    
<tr>
<td>
Cell-Cell differentiations will be introduced by random perturbation of initial conditions of the selected sub-cellullar model species around their default value.
    </td>
</tr>  			      
     	<tr>     	
      	<td>
         <s:submit value="Save Data" align="center" />
 	 </tr>
 	 </table>
 </s:form>  	 
 </s:if> 
  
 <s:if test="species == null">  
 <s:form action="doSubmitCellularData" method="POST" id="frm_demo" enctype="multipart/form-data">  		
     <div>	
     <table>
     <tr>
     <td>
     <b>Number of Cells to be seeded</b>     
     </td>
     <td>     
     	 <s:textfield name="numberOfCells" value="4"	size="4" maxlength="4" />
     </td>
     	 </tr>
     	 </table>
     	
  <!--   	 <br/>
     	 <table>
     	 <tr>
     	 <td>
     	 <button type="button" onclick="popup('AddDiffSpeciesWindow.jsp')" >Add Species! </button>
     	 <b>To introduce Cell-Cell differentiations using random perturbation of initial conditions of selected sub-cellullar
    model species around their default value. </b>
     	 </td>
      	 </tr>
      	 <tr/>
     	 <tr>
      	 <td><b>To introduce Cell-Cell differentiations using random perturbation of initial conditions of selected sub-cellullar
    model species around their default value. </b></td>
       </tr>           
     	</table>
          
     	<br/>	
       <div>
       <b>OR</b>
       </div>
       
  -->   	
     	 <br/>
     	 <b>Identify cell size (biomass) variable</b><br/>     	 	   		
	   <s:action name="doAddNutrientSpeciesAndParamWindow" executeResult="true"/> 	   
	   <br/>	      	 
     </div>	 
     <hr/>
		<table>      			      
     	<tr>     	
      	<td>
         <s:submit value="Save Data" align="center" /> 
         </td>         
         <td>    	      	 
        <b>OR</b>
      	 </td>
      	 <td>
      	 <button type="button" onclick="popup('AddDiffSpeciesWindow.jsp')" >Add Species </button>
      	 </td>
      	 </tr>
      	 </table>
      	 <table>      	 
      	 <tr>
      	 <td><b>To introduce Cell-Cell differentiations using random perturbation of initial conditions of selected sub-cellullar
    model species around their default value. </b>
    </td>
    
       </tr>          
     	</table>		
	
  </s:form>  
  </s:if>  
  </s:if>	
  <!--
</body>
</html>
-->