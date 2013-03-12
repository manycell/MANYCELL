<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

 <s:set name="theme" value="'simple'" scope="page" />
 <h2>Multiscale model for simulation </h2> 
<hr/>
 <h3>Sub-cellular model section</h3>  		  		
<s:if test="subCellularData !=null && cellularData != null && environmentData!=null"> 

<s:form action="doGenerateMXMLModel" method="POST" enctype="multipart/form-data"> 
<table>
<tr>
<td>
 <b>SBML model Name:</b> 
 </td>
 <td>
 <s:property value="modelName" />  
 </td>
 </tr>
 </table>
 <br/>
  <b>Simulation parameters</b><br/>	 
      <table>
		<tr>
		<td>
		<s:label value = "Start Time"/>
		</td>
			<td><s:textfield  name="startOutputTime"
				value="%{startOutputTime}" size="15" maxlength="5"  readonly="true" /></td>
			<td>
		</tr>

		<tr>
		<td>
		<s:label value = "Step Size"/>
		</td>
			<td><s:textfield name="stepSize" value="%{stepSize}"
				size="15" maxlength="5" readonly="true" />
			</td>
		</tr>
		<tr>
		<td>
		<s:label value = "Number of Steps"/>
		</td>
			<td><s:textfield name="stepNumber"
				value="%{stepNumber}" size="15" maxlength="5" readonly="true" /></td>
		</tr>
		</table>	
		<br/>
<s:if test="activeSpecies!=null">
<b> Selected sub-cellular species for trajectory tracking</b><br/>
 <table>
 	<tr>
 		<td><b>Name</b></td>
 		<td/>
 		<td><b>Initial Value</b></td>
 		</tr>
 	<c:forEach var="sp" items="${activeSpecies}">
 	<tr>
 		<td><c:out value="${sp.name}"></c:out> </td>
 		<td/>
 		<td><c:out value="${sp.value}"></c:out> </td>
 	</tr>
	</c:forEach>        	
</table> 
</s:if>
<s:else><b>You have not selected any sub-cellular species for trajectory tracking, no data will be stored for sub-cellular species.</b><br/>
</s:else>
<hr/>

<h3>Cellular model section</h3> 
<table>
     <tr>
     <td>
     <b>Number of Cells</b>     
     </td>
     <td>     
     	 <s:textfield name="numberOfCells" value="%{numberOfCells}"	size="4" maxlength="4" readonly="true"/>
     </td>
     	 </tr>
     	 </table>     	 
       <br/>
       <b>Size and Event parameter selected<b><br/>
     
     <table>
     <tr>
     <td>
     Event/Behaviour     
     </td>
     <td>
     Trigger variable    
     </td>
    <td>
     Size partition variable     
     </td>     
     </tr>
     <tr>
     <td>     
     	<s:property value="behaviour"/>     	        
     </td>
     <td>
    <s:property value="SPName"/>     
     </td>
     <td>
   <s:property value="SSPName"/>   
     </td>
     </tr>
     </table>
       
       <br/>
   <s:if test="cellDiffSpecies!=null">    
 	<b> Selected sub-cellular species for Cell-Cell differentiations<b><br/>
 <table>
 	<tr>
 		<td><b>Name</b></td>
 		<td/>
 		<td><b>Default Value</b></td>
 		</tr>
 	<c:forEach var="sp" items="${cellDiffSpecies}">
 	<tr>
 		<td><c:out value="${sp.name}"></c:out> </td>
 		<td/>
 		<td><c:out value="${sp.value}"></c:out> </td>
 	</tr>
	</c:forEach> 	     	
</table>
</s:if>
<s:else><b>You have not selected any sub-cellular species for Cell-Cell differentiations, cells are assumed to be homogeneous.</b>
</s:else>

<hr/>
<h3>Micro-environment model section</h3> 
<table>
<tr>
<th>
Environment
</th>
<tr>
<td><b>Type:</b>
</td>
<td>
<s:property value="type"/>
</td>
</tr>
<tr>
<td><b>Volume:</b>
</td>
<td>
<s:property value="volume"/>
</td>
</tr>
<tr>
<td><b>Unit:</b>
</td>
<td>
<s:property value="unit"/>
</td>
</tr>
</table>
<br/>
<b>Environment nutrient variable</b><br/> 
<table>
<tr>
<td><b>Name</b>
</td>
<td><b>Initial Value</b>
</td>
</tr>
<tr>
<td>
<s:property value="SName"/>
</td>
<td>
<s:property value="SValue"/>
</td>
</tr>
</table>
<hr/>

<b>Multiscale simulation</b>
<table>
<tr>
<td>Execution Type</td> 
	<td>	    
  	   <s:select name = "executionType"  
     	list="#{'ISAT':'ISAT', 'Parallel':'Parallel Execution'}" value="selected" required="true"/>         			
	</td>
  </tr>				 
 </table>  
 <br/>
<table>      			      
  <tr>     	
  	<td>
   <s:submit value="Start Simulation" align="center" />      	      	 
 	 OR 
 <button type="button" onclick="popup('Cancel.jsp')" >Cancel</button></td>
      	 </tr>
      	 <tr>
      	 <td><b>Note: You will loose all your multiscale model information if you cancel. </b></td>
       </tr>          
     	</table>
     	<hr/>
</s:form>

 </s:if>	  