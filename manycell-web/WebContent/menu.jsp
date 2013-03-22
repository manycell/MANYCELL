<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<sx:head/>
<title>MANYCELL Home Page</title>

 <script type="text/javascript" >
dojo.require("dojo.widget.*");
dojo.require("dojo.dojo.parser");
dojo.require("dojo.dijit.Tree");

</script>
 <style type="text/css">
@import "dojo/dojo/resources/dojo.css";
@import "dojo/dijit/themes/tundra/tundra.css";
@import "dojo/dijit/themes/dijit.css";
</style>

<script type="text/javascript" src="simpletreemenu.js">

/***********************************************
* Simple Tree Menu- � Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

</script>
<link rel="stylesheet" type="text/css" href="simpletree.css" />
</head>


<body  bgcolor = "F5F5F5" vlink="blue">
<!-- <body  bgcolor = "#FFFAFA" vlink="blue">-->


<div>
  <a href="./" target = "_top"><b><font size=2 style="font-size: 11pt">Home</font></b></a><br/><br/>
  
  <hr color="red"/>
<a href="./UI/Registration/UserRegister.jsp" target = "main"><font size=2 style="font-size: 11pt">Registration</font></a><br/> 
</div>
<sx:tree id="ManyCell" label="<font size=2 style='font-size: 12pt'><b>ManyCell</b></font>" >
<sx:treenode id="Modelling" label="<b>Modelling</b>">
<sx:treenode id="Monoscale" label="<a href='./UI/Modelling/MonoscaleModel.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Monoscale</font></a>" />
<sx:treenode id="Multiscale" label="<font size=2 style='font-size: 11pt'>Multiscale</font>" >
<sx:treenode id="SubCellular" label="<a href='./UI/Modelling/SubCellular.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Sub-Cellular</font></a>" />
<sx:treenode id="Cellular" label="<a href='./UI/Modelling/Cellular.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Cellular</font></a>" />
<sx:treenode id="Environment" label="<a href='./UI/Modelling/Environment.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Environment</font></a>" />
</sx:treenode>
</sx:treenode>

<sx:treenode id ="Simulation" label="<b>Simulation</b>">
<sx:treenode id="MonoscaleS" label="<a href='./UI/Samples/Monoscale/TimeCourse.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Monoscale</font></a>" />
<sx:treenode id="MultiscaleS" label="<a href='./UI/Simulation/MultiscaleModel.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Multiscale</font></a>" />
</sx:treenode>

<sx:treenode id="Results" label="<b>Results</b>">
<sx:treenode id="GrowthCurve" label="<a href='./UI/Results/SimulationResults.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Results</font></a>" />

<sx:treenode id="GrowthCurve" label="<a href='./UI/Results/GrowthCurve.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Growth Curve</font></a>" />
<sx:treenode id="Snapshots" label="<a href='./UI/Results/Snapshots.html' target = 'main'><font size=2 style='font-size: 11pt'>Snapshots</font></a>" />
<sx:treenode id="statistics" label="<a href='./UI/Results/Statistic.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Statistic</font></a>" />
<sx:treenode id="SubCellular" label="<a href='./UI/Results/SubCellular.jsp' target = 'main'><font size=2 style='font-size: 11pt'>Sub-Cellular</font></a>" />
</sx:treenode>
</sx:tree>
<br/>
<hr color="red"/>
<b>User Guide</b> 
<ul>
<li><a href='./introduction.html' target = 'main'><font size=2 style='font-size: 10pt'>Introduction</font></a><br/>
<li><a href='./requirements.html' target = 'main'><font size=2 style='font-size: 10pt'>Requirements</font></a><br/>
<li><a href='./installation.html' target = 'main'><font size=2 style='font-size: 10pt'>Installation</font></a><br/>
<li><a href='./configuration.html' target = 'main'><font size=2 style='font-size: 10pt'>Configuration</font></a>
</ul>

<hr color="red"/>
<b>Download</b>
<ul>
<li><a href='./readme.html' target = 'main'><font size=2 style='font-size: 10pt'>README</font></a><br/>
<li><a href='./dist/manycell-core/manycell-core-1.0.tar' target="main" ><font size=2 style='font-size: 10pt'>manycell-core</font></a><br/>
<li><a href='./dist/manycell-mas/manycell-mas-1.0.tar'target="main" ><font size=2 style='font-size: 10pt'>manycell-mas</font></a><br/>
<li><a href='./dist/manycell-web/manycell-web-1.0.tar' target="main"><font size=2 style='font-size: 10pt'>manycell-web</font></a><br/>
<li><a href='http://www.comp-sys-bio.org/CopasiWS/' target = 'new'><font size=2 style='font-size: 10pt'>CopasiWS</font></a>
</ul>

<hr color="red"/>
<b>Code Documentation</b> 
<ul>
<li><a href="https://github.com/manycell/MANYCELL" target='new'><font size=2 style='font-size: 10pt'>Code Repository</font></a><br/>
<li><a href='http://www.comp-sys-bio.org/ManyCell/documentation/manycell-core/html/index.html' target = 'main'><font size=2 style='font-size: 10pt'>manycell-core</font></a><br/>
<li><a href='http://www.comp-sys-bio.org/ManyCell/documentation/manycell-mas/html/index.html' target = 'main'><font size=2 style='font-size: 10pt'>manycell-mas</font></a><br/>
<li><a href='http://www.comp-sys-bio.org/ManyCell/documentation/manycell-web/html/index.html' target = 'main'><font size=2 style='font-size: 10pt'>manycell-web</font></a>
</uL>

<hr color="red"/>
<b>Links</b>
<ul>
<li><a href='http://www.unicellsys.eu/' target = 'new'><font size=2 style='font-size: 10pt'>UNICELLSYS</font></a><br/>
<li><a href='http://www.comp-sys-bio.org/SBRML' target = 'new'><font size=2 style='font-size: 10pt'>SBRML</font></a><br/>
<li><a href='http://www.comp-sys-bio.org/CopasiWS/' target = 'new'><font size=2 style='font-size: 10pt'>CopasiWS</font></a><br/>
<li><a href='http://www.copasi.org' target = 'new'><font size=2 style='font-size: 10pt'>COPASI</font></a>
</ul>
<hr color="red"/>

<!-- <script type="text/javascript">

ddtreemenu.createTree("treemenu1", true)

</script>
-->

</body>