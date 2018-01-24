<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典设置</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

		function onClickRow(index){
			$('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);	 
		}

		function formatterType(val, row){
		   if(val=="Integer"){
			   return "整数型";
		   } else if(val=="Long"){
			   return "长整数型";
		   } else if(val=="Double"){
			   return "数值型";
		   } else if(val=="Date"){
			   return "日期型";
		   }
		   return "字符型";
		}

		function formatterRequired(val, row){
		   if(val=="1"){
			   return "<div style='color:red'>是</div>";
		   }  
		   return "否";
		}

       
        function saveChanges(){
			$('#dg').datagrid('acceptChanges');
			var params = "";
		    var rows = $('#dg').datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				if(rows[i]['title'] != "" ){
                   params+="&"+rows[i]['name']+"_title="+rows[i]['title'];
                   params+="&"+rows[i]['name']+"_mappingToSourceIdColumn="+rows[i]['mappingToSourceIdColumn'];
				   params+="&"+rows[i]['name']+"_mappingToTargetColumn="+rows[i]['mappingToTargetColumn'];
				   params+="&"+rows[i]['name']+"_datasetId="+rows[i]['datasetId'];
				   params+="&"+rows[i]['name']+"_sqlDefId="+rows[i]['sqlDefId'];
				}
			}
			//alert(params);

		    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/treeTableSynthetic/saveRules?syntheticId=${treeTableSynthetic.id}',
				   data: params,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data.message != null){
						   alert(data.message);
					   } else {
						 alert('操作成功完成！');
					   }
				   }
		    });
	    }


	function dsEditor(){
     
	}

	function sqlEditor(){

	}
</script>
</head>
<body style="margin:1px;">  
 
<div class="easyui-layout" data-options="fit:true">  
  <div data-options="region:'center',border:true">
	 <table id="dg" class="easyui-datagrid" 
			data-options="
			    width:1075,
				height:420,
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '<%=request.getContextPath()%>/mx/sys/treeTableSynthetic/rulesJson?id=${treeTableSynthetic.id}',
				onClickRow: onClickRow">
		<thead>
			<tr>
				<th data-options="field:'name', width:90">名称</th>
				<th data-options="field:'columnName', width:120">目标表字段名</th>
				<th data-options="field:'columnLabel', width:120">中文名</th>
				<th data-options="field:'type', width:120, formatter:formatterType">类型</th>
				<th data-options="field:'datasetId', width:120, 
				                         editor: {type:'combotree', options:{url:'<%=request.getContextPath()%>/rs/dataset/treeJson?nodeId=203518',multiple:false}}">
				数据集</th>
				<th data-options="field:'sqlDefId', width:120, editor: sqlEditor">SQL定义</th>
				<th data-options="field:'title', width:200, editor:'text'">标题</th>
				<th data-options="field:'mappingToSourceIdColumn', width:120, editor:'text'">关联主键字段</th>
				<th data-options="field:'mappingToTargetColumn', width:120, editor:'text'">映射字段</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="javascript:saveChanges();">保存</a>
		（提示：来源于同一数据集或同一SQL语句的字段会自动合并提取，映射字段数据类型必须与目标表字段名一致。）
	</div>

  </div>  
</div>
 
</body>
</html>
