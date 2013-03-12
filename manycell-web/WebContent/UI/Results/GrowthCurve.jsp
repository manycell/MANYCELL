<%@ page 
 	language="java" 
 	contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags" %> 
 <%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript" src="js/jstree/_lib/jquery.js"></script>
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#MyTreeDiv").jstree({ 
		"json_data" : {
			"data" : [
				{ 
					"data" : "A node", 
					"metadata" : { id : 23 },
					"children" : [ "Child 1", "A Child 2" ]
				},
				{ 
					"attr" : { "id" : "li.node.id1" }, 
					"data" : { 
						"title" : "Long format demo", 
						"attr" : { "href" : "#" } 
					} 
				}
			]
		},
		"plugins" : [ "themes", "json_data", "ui" ]
	}).bind("select_node.jstree", function (e, data) { alert(data.rslt.obj.data("id")); });
});

<!--
$('#MyTreeDiv').jstree({
        "json_data": {
            "ajax": {
                "type": "POST",
                "url": "/MyServerPage.aspx?Action=GetNodes",
                "data": function (n) { return { id: n.attr ? n.attr("id") : 0} },
            }
        },
        "themes": {
            "theme": "default",
            "url": "/Content/Styles/JSTree.css",
            "dots": false
        },
        "plugins": ["themes", "json_data", "ui", "crrm"]
    }).bind("select_node.jstree", function (e, data) {
        var selectedObj = data.rslt.obj;
        alert(selectedObj.attr("id"));
});-->

</script>
</head>

<body>

  <div class="body">
       <div name="MyTreeDiv" id="MyTreeDiv">Under construction! users will be able to check various growth curves of their simulated cellular systems</div>
</body>

<!--<body>
Under construction! users will be able to check various growth curves of their simulated cellular systems
</body> -->
</html>