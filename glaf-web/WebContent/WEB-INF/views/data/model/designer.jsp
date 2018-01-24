<%@page import="com.glaf.base.modules.BaseDataManager"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsPlumb Toolkit</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/scripts/plugins/bootstrap/base/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${contextPath}/scripts/jsPlumb/css/jsPlumbToolkit-defaults.css">
<link rel="stylesheet"
	href="${contextPath}/scripts/jsPlumb/css/jsPlumbToolkit-demo.css">
<link rel="stylesheet"
	href="${contextPath}/scripts/jsPlumb/css/app.css">
<link rel="stylesheet"
	href="${contextPath}/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
<style type="text/css">
.delete, .view .view-delete {
	float: right;
}

.input-group-btn {
	width: 10%
}

.txtViewQuery {
	width: 606px;
	height: 257px;
}

div.div-header div{
	font-size: large;
	font-weight: bolder;
	text-align: center;
}


.goes-dialog .modal-header, .goes-dialog .modal-footer{
	padding: 6px;
}
.goes-dialog.bootstrap-dialog.type-primary .modal-header{
	background-color: #808080;
} 
</style>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/base/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		var contextPath = "${contextPath}";
	</script>
<script type="text/javascript"
	src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>

</head>
<body>

	<!-- content -->

	<div class="jtk-demo-main" id="jtk-demo-dbase">
		<!-- the node palette -->
		<div class="sidebar node-palette" style="display: none;">
			<ul>
				<li jtk-node-type="table" title="Drag to add new"><i
					class="fa fa-table btn-icon-margin"></i>Table</li>
				<li jtk-node-type="view" title="Drag to add new"><i
					class="fa fa-eye btn-icon-margin"></i>View</li>
			</ul>
		</div>
		<!-- this is the main drawing area -->
		<div class="jtk-demo-canvas">
			<div class="controls">
				<i class="fa fa-arrows selected-mode" mode="pan" title="Pan Mode"></i>
				<i class="fa fa-pencil" mode="select" title="Select Mode"></i> <i
					class="fa fa-home" reset title="Zoom To Fit"></i> <i
					class="fa fa-table node-tool" title="表格" jtk-node-type="table"></i>
				<i class="fa fa-eye node-tool" title="视图" jtk-node-type="view"></i>
			</div>
			<!-- miniview -->
			<div class="miniview"></div>
		</div>

		<!-- the current dataset -->
		<div class="jtk-demo-dataset" style="display: none;"></div>


	</div>

	<!-- /content -->


	<div class="clear-footer"></div>

	<script type="text/javascript"
		src="${contextPath}/scripts/jsPlumb/js/jsPlumb-2.2.7-min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/scripts/jsPlumb/js/jsPlumbToolkit-1.1.4.js"></script>
	<script type="text/javascript"
		src="${contextPath}/webfile/js/data/model/toolkit.js"></script>
	<script type="text/javascript"
		src="${contextPath}/webfile/js/data/model/model.app.js"></script>
	 <script type="text/javascript"
		src="${contextPath}/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script> 

	<div id="jsPlumbToolkitTemplates" style="display: none;">
		<!-- table node type -->
		<script type="jtk" id="tmplTable">
    <div class="table node">
        <div class="name">
            <span>${"${"}name}</span>
            <!-- <div class="new-column add" title="Click to add a new column">
                <i class="fa fa-plus"/>
            </div>
-->
		<div class="delete" title="Click to delete">
                <i class="fa fa-times"/>
            </div>
        </div>
        <ul class="table-columns">
            <r-each in="columns">
                <r-tmpl id="tmplColumn"/>
            </r-each>
        </ul>
    </div>
</script>

		<!-- view node type -->
		<script type="jtk" id="tmplView">
    <div class="view node">
        <div class="name">
            <div class="view-delete" title="Click to delete">
                <i class="fa fa-times"/>
            </div>
            <span>${"${"}name}</span>
         <!--    <div class="edit" title="Click to edit">
                <i class="fa fa-pencil"/>
            </div>
-->
        </div>
        <div class="view-details">${"${"}query}</div>
    </div>
</script>

		<!-- table column template -->
		<script type="jtk" id="tmplColumn">
    <li class="table-column table-column-type-${"${"}datatype}" primary-key="${"${"}primaryKey}" data-port-id="${"${"}id}">
       <!--   <div class="table-column-edit">
            <i class="fa fa-pencil table-column-edit-icon"/>
        </div>

        <div class="table-column-delete">
            <i class="fa fa-times table-column-delete-icon"/>
        </div>-->
        <div>
			<span>${"${"}name}</span>
			<span>${"${"}datatype}</span>
			<r-if test="datatype == 'varchar'">
				<span>(${"${"}length})</span>
			</r-if>
		</div>
        <!--
            configure the li as an edge source, with a type of column, a scope derived from
            the columns datatype, and a filter that prevents dragging new edges from the delete button or from the label.
        -->
        <jtk-source port-id="${"${"}id}" port-type="column" scope="${"${"}datatype}" filter=".table-column-delete, .table-column-delete-icon, span, .table-column-edit, .table-column-edit-icon" filter-exclude="true"/>
        <!--
            configure the li as an edge target, with a type of column, and a scope derived from the
            column's datatype.
        -->
        <jtk-target port-id="${"${"}id}" port-type="column" scope="${"${"}datatype}"/>
    </li>
</script>

		<!-- edit relationship -->
		<script type="jtk" class="dlg" id="dlgRelationshipType"
			title="Edit Relationship">
    <div class="db-cardinality">
        <ul>
            <li>
                <label>
                    <input type="radio" name="rType" value="1:1" jtk-focus jtk-att="type" checked/>
                    1:1 - One to One
                </label>
            </li>
            <li>
                <label>
                    <input type="radio" name="rType" value="1:N" jtk-att="type"/>
                    1:N - One to Many
                </label>
            </li>
            <li>
                <label>
                    <input type="radio" name="rType" value="N:M" jtk-att="type"/>
                    N:M - Many to Many
                </label>
            </li>
        </ul>
    </div>
</script>

		<!-- edit column type and key details -->
		<script type="jtk" class="dlg" id="dlgColumnEdit" title="Edit Column">
    <div class="db-column-type">
        <label>
            <div class="form-labels-float">id:</div>
            <div class="pull-left"><input class="" jtk-att="id" jtk-focus jtk-commit="true"/></div>
            <div class="clearfix"/>
        </label>

        <div class="checkbox-id">
            <label>
                <input type="checkbox" class="chkPrimaryKey" jtk-att="primaryKey"/>
                Primary Key
            </label>
        </div>

        <p>
            <div class="form-labels">Type:</div>
            <div class="clearfix"></div>
            <ul>
                <li>
                    <label>
                        <input type="radio" name="cType" value="integer" jtk-focus jtk-att="datatype"/>
                        Integer
                    </label>
                </li>
                <li>
                    <label>
                        <input type="radio" name="cType" value="varchar" jtk-att="datatype" checked/>
                        Varchar
                    </label>
                </li>
                <li>
                    <label>
                        <input type="radio" name="cType" value="date" jtk-att="datatype"/>
                        Date
                    </label>
                </li>
            </ul>
        </p>
    </div>
</script>

		<!-- edit view query -->
		<script type="jtk" class="dlg" id="dlgViewQuery" title="Edit Query">
    <textarea class="txtViewQuery" jtk-focus jtk-att="query" jtk-commit="true"/>
</script>

		<!-- edit name (table or view) -->
		<script type="jtk" class="dlg" id="dlgName" title="Enter Name">    
<ul class="nav nav-tabs">
   <li class="active"><a href="#General" data-toggle="tab">General</a></li>
   <r-if test="type == 'table'">
		<li><a href="#Columns" data-toggle="tab">Columns</a></li>
	<r-else>
		<li><a href="#Query" data-toggle="tab">View Query</a></li>
	</r-else>
</r-if>
</ul>
<div class="tab-content">
  <div class="tab-pane fade in active" id="General">
<br/><div class="row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" style="width:82px;">表名</button>
                    </span>
                    <input type="text" jtk-focus jtk-att="id" jtk-commit="true" readonly="true" class="form-control"/>
                </div>
            </div>
        </div>
<br/>
	<div class="row">
            <div class="col-sm-6">
                <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default">中文描述</button>
                    </span>
                    <input  type="text" jtk-att="name" jtk-commit="true" class="form-control"/>
                </div>
            </div>
        </div>
<br/>
	<div class="row">
            <div class="col-sm-6">
                <div class="form-group">
    				<label for="name">表格说明</label>
    					<textarea class="form-control" rows="6" jtk-att="describe" jtk-commit="true"></textarea>
  				</div>
            </div>
    </div>

  </div>
  <div class="tab-pane fade" id="Columns">
<br/>
<div class="row">
	<div class="col-sm-6">
		<div class="input-group">
      <span class="input-group-btn">
         <button class="btn btn-default btn-xs column-btn" type="button" style="width:42px;"><i class="fa fa-plus"></i></button>
		<!-- <button class="btn btn-default btn-xs" type="button" style="width:42px;">表名</button> -->
      </span>
    </div>
	</div>
</div>	

<div class="row div-header">
  <div class="col-sm-2">
	Name
  </div>
<div class="col-sm-2">
	Code
  </div>
<div class="col-sm-2">
	DataType
  </div>
<div class="col-sm-2">
	Length
  </div>
<div class="col-sm-2">
	PK
  </div>	
<div class="col-sm-1">
	OP
  </div>	
</div>
<div class="row columns-detail" style="height:300px;overflow:auto">
<r-each in="columns">
       <r-tmpl id="column-detail" />
    </r-each>
</div>
	
  </div>
	<div class="tab-pane fade" id="Query">
		<r-tmpl id="dlgViewQuery" />
	</div>
</div>
</script>

		<script type="jtk" id="column-detail">
<div class="row">
  <div class="col-sm-2">
	<input class="form-control" jtk-att="columns[${"${"}$key}].name" jtk-commit="true" />
  </div>
<div class="col-sm-2" title="${"${"}id}">
	<input class="form-control column-id" readonly="true" jtk-att="columns[${"${"}$key}].id" jtk-commit=""/>
  </div>
<div class="col-sm-2">
	<select class="form-control dialog-select" jtk-att="columns[${"${"}$key}].datatype"  jtk-commit="">
		<option value="varchar">Varchar</option>
		<option value="integer">Integer</option>
		<option value="date">Date</option>
	</select>
</div>
<div class="col-sm-2">
	<input type="number" class="form-control" jtk-att="columns[${"${"}$key}].length" jtk-commit="" />
  </div>
<div class="col-sm-2">
  <input type="checkbox" class="form-control" jtk-att="columns[${"${"}$key}].primaryKey" jtk-commit=""  />
</div>
<div class="col-sm-1">
  <button class="btn btn-xs btn-default table-column-delete-icon"><i class="fa fa-times"></i></button>
</div>
</div>
</script>

		<script type="jtk" class="dlg" id="dlgConfirm" title="Please Confirm">
    ${"${"}msg}?
</script>

		<script type="jtk" class="dlg" id="dlgMessage" title="Message"
			cancel="false">
    ${"${"}msg}
</script>

	</div>
</body>
</html>