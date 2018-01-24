<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑服务数据导入实例信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
<style scoped>

   .k-textbox {
        width: 18.8em;
    }

    .main-section {
        width: 800px;
        padding: 0;
     }

    label {
        display: inline-block;
        width: 150px;
        text-align: right;
        padding-right: 10px;
    }

    .required {
        font-weight: bold;
    }

    .accept, .status {
        padding-left: 90px;
    }
    .confirm {
        text-align: right;
    }

    .valid {
        color: green;
    }

    .invalid {
        color: red;
    }
    span.k-tooltip {
        margin-left: 6px;
    }
	
	.datagrid-header td,th {
	border-right: 1px dotted #ccc;
	font-size: 12px;
	font-weight: normal;
	background: #fafafa url('${contextPath}/images/datagrid_header_bg.gif')
		repeat-x left bottom;
	border-bottom: 1px dotted #ccc;
	border-top: 1px dotted #ccc;
	height: 28px;
	}
	.datagrid-header td:first-child,th:first-child {
		border-left: 1px dotted #ccc;
	}
.datagrid-tbody td {
	border-right: 1px solid #ccc;
	font-size: 12px;
	font-weight: normal;
	border-bottom: 1px solid #ccc;
	border-top: 1px solid #fff;
	height: 28px;
}
.datagrid-tbody td:first-child {
	border-left: 1px solid #ccc;
}
.datagrid-tbody tr {
	text-align: center;
}
.datagrid-tbody tr td input,.datagrid-tbody tr th input{
	width:98%;height:100%;border:none;
}
.datagrid-title td {
	font-size: 12px;
	font-weight: bolder;
	background: #E0ECFF repeat-x left bottom;
	height: 28px;
	vertical-align: middle;
}

.titleTd {
	background: #E0ECFF repeat-x left bottom;
}

td,th {
	display: table-cell;
	vertical-align: inherit;
}
ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
<script type="text/javascript">
                
  jQuery(function() {
    var viewModel = kendo.observable({
        "name": "${eimServerDataImp.name}",
        "appId": "${eimServerDataImp.appId}",
        "tmpId": "${eimServerDataImp.tmpId}",
        "emptyTable": "${eimServerDataImp.emptyTable}",
        "preSql": "${eimServerDataImp.preSql}",
        "incrementFlag": "${eimServerDataImp.incrementFlag}",
        "params": '${eimServerDataImp.params}',
        "targetDatabase": "${eimServerDataImp.targetDatabase}",
        "targetTable": "${eimServerDataImp.targetTable}",
        "createBy": "${eimServerDataImp.createBy}",
        "createTime": "<fmt:formatDate value='${eimServerDataImp.createTime}' pattern='MM/dd/yyyy'/>",
        "updateBy": "${eimServerDataImp.updateBy}",
        "updateTime": "<fmt:formatDate value='${eimServerDataImp.updateTime}' pattern='MM/dd/yyyy'/>",
        "deleteFlag": "${eimServerDataImp.deleteFlag}",
        "id": "${eimServerDataImp.id}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });  
          //初始化grid
		  initGrid($("#params"),"paramsGrid",'${eimServerDataImp.params}');
		  var tmpId="${eimServerDataImp.tmpId}";
		  var columnMapping='${eimServerDataImp.columnMapping}';
		  if(tmpId!=''){
             initColumnGrid($("#columnMapping"),"columnMappingGrid",columnMapping,tmpId);
		  }		  
    });
    /**
	*初始化grid
	*/
    function initGrid(preObj,id,content){
		var tableInst=$($("#tableTmp").html());
		tableInst.attr("id",id);
		//产生行记录
		if(content!='')
		{
			tableInst.find("tbody").empty();
			var data=JSON.parse(content);
			var name,incrementParam,type,upperLimit,lowerLimit;
			var row;
			$.each(data,function(code,item){
				name=item.name;
				incrementParam=item.incrementParam;
				type=item.type;
				upperLimit=item.upperLimit;
				lowerLimit=item.lowerLimit;
				row=$($("#rowTmp").html());
				 if(incrementParam==1){
			      $(row).find(".upperLimit").removeAttr("disabled");
		        }
				row.find(".code").val(code);
				row.find(".name").val(name);
				row.find(".incrementParam").val(incrementParam);
				row.find(".upperLimit").val(upperLimit);
				row.find(".lowerLimit").val(lowerLimit);
				row.find(".type").val(type);
				tableInst.append(row);
			});
		}
		bindBt(tableInst);
		tableInst.find(".topAdd").click(function(){
			var row=$($("#rowTmp").html());
			tableInst.find("tbody").append(row);
		});
		preObj.after(tableInst);
	}
	/**
	* 根据表名获取字段信息
	*/
	function getTargetTableFields(tableName){
		var fields;
		var url = "${contextPath}/mx/isdp/rdataTable/getColumnsByTableName";
		$.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			data : {
				tableName :tableName,
				systemName : 'default'
			},
			async : false,
			success : function(data){
				fields = data || {};
			}
		});
		return fields;
	}
	/**
	*获取模板返回响应体结构
	*/
	
	function getTmpResponseBody(tmpId){
		var responseBody;
		$.ajax({
			url :"${contextPath}/rs/teim/tmp/resbody",
			type : 'post',
			dataType : 'json',
			data : {
				tmpId:tmpId
			},
			async : false,
			success : function(data){
				responseBody=data;
			}
		});
		return responseBody;
	}
	/**
	*初始化字段映射Grid
	*/
    function initColumnGrid(preObj,id,content,tmpId){
		var tableInst=$("#"+id).length==0?$($("#columnTableTmp").html()):($("#"+id).remove()&&$($("#columnTableTmp").html()));
        tableInst.attr("id",id);
		var responseBody=getTmpResponseBody(tmpId);
		//产生行记录
		if(responseBody&&responseBody!='')
		{
			tableInst.find("tbody").empty();
			var data=content?JSON.parse(content):null;
			
			var dataStruct=responseBody.data!=undefined?responseBody.data.dataitem:null;
			var name,code,mappingCode;
			var row;
			//如果当前有选中目标表，初始化目标表列下拉选择框值
			var targetTable=$("#targetTable").val();
			if(targetTable!=''){
				updateRowTmp(targetTable);
			}
			if(dataStruct){
			$.each(dataStruct,function(code,item){
				name=item.name;
				if(data&&data[code]){
				  mappingCode=data[code];	
				}
				row=$($("#columnRowTmp").html());
				row.find(".fieldCode").val(code);
				row.find(".fieldName").val(name);
				if(mappingCode)
				row.find(".mappingFields").val(mappingCode);
				tableInst.append(row);
			});
			}
		}
		preObj.after(tableInst);
	}
	/**
	 *更新字段映射Grid行模板
	 */
	function updateRowTmp(targetTable){
		var fields=getTargetTableFields(targetTable);
		var tmpClone=$($("#columnRowTmp").html());
		var sel=tmpClone.find(".mappingFields");
		sel.empty();
		sel.append("<option value=\"\">--请选择--</option>");
		if(fields&&fields.columns){
			var columns=fields.columns;
			var opt;
			$.each(columns,function(i,column){
				opt="<option value=\""+column.fieldName+"\">"+column.text+"</option>";
				sel.append(opt);
			});
		}
		$("#columnRowTmp").html(tmpClone);
	}
	/**
	*更新数据表字段下拉列表
	*/
	function updateFieldSelect(targetTable){
		var fields=getTargetTableFields(targetTable);
		var tmpClone=$($("#columnRowTmp").html());
		var selects=$("#columnMappingGrid").find(".mappingFields");
		if(fields&&fields.columns){
			var columns=fields.columns;
			var opt;
			$.each(selects,function(k,sel){
				var $sel=$(sel);
				$sel.empty();
				$sel.append("<option value=\"\">--请选择--</option>");
				$.each(columns,function(i,column){
				opt="<option value=\""+column.fieldName+"\">"+column.text+"</option>";
				$sel.append(opt);
			});
			});
		}
	}
    jQuery(function () {
        var container = jQuery("#iForm");
        kendo.init(container);
        container.kendoValidator({
                rules: {
                      greaterdate: function (input) {
                            if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                    
                               var date = kendo.parseDate(input.val()),
                               otherDate = kendo.parseDate(jQuery("[name='" + input.data("greaterdateField") + "']").val());
                               return otherDate == null || otherDate.getTime() < date.getTime();
                             }

                             return true;
                          }
                      }
        });
    });
   /**
   *保存
   */
   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	   //form.method="post";
	   //form.action = "<%=request.getContextPath()%>/mx/EimServerDataImp/eimServerDataImp/saveEimServerDataImp";
	   //form.submit();
	   var tableInst=$("#paramsGrid");
	   setParamsVal($("#params"),tableInst);
	   //设置映射值
	   var columnMappingTableInst=$("#columnMappingGrid");
	   setColumnMappingVal($("#columnMapping"),columnMappingTableInst);
	   var link = "<%=request.getContextPath()%>/mx/teim/dataimp/saveEimServerDataImp";
	   var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
					   window.parent.location.reload();
				   }
			 });
       }
   }
   //设置GRID参数值
   function setParamsVal(inputObj,tableInst){
	   var returnJson={};
	   var rows=tableInst.find("tbody").find("tr");
	   var code,name,incrementParam,type,upperLimit,lowerLimit;
	   var itemJson;
	   $.each(rows,function(i,row){
		   code=$(row).find(".code").val();
		   if(code!=""){
		   name=$(row).find(".name").val();
		   incrementParam=$(row).find(".incrementParam").val();
		   upperLimit=$(row).find(".upperLimit").val();
		   lowerLimit=$(row).find(".lowerLimit").val();
		   type=$(row).find(".type").val();
		   itemJson={};
		   itemJson.name=name;
		   itemJson.type=type;
		   itemJson.incrementParam=incrementParam;
		   if(upperLimit!=""){
		   if(type=="int")
		      itemJson.upperLimit=parseInt(upperLimit);
	       else
			  itemJson.upperLimit=upperLimit;
		   }
		    if(lowerLimit!=""){
		   if(type=="int")
		      itemJson.lowerLimit=parseInt(lowerLimit);
	       else
			  itemJson.lowerLimit=lowerLimit;
		   }
		   returnJson[code]=itemJson;
		   }
	   });
	   inputObj.val(JSON.stringify(returnJson));
   }
   /**
   *设置字段映射值
   */
   function setColumnMappingVal(inputObj,tableInst){
	   var returnJson={};
	   var rows=tableInst.find("tbody").find("tr");
	   var fieldCode,mappingColumn;
	   $.each(rows,function(i,row){
		   fieldCode=$(row).find(".fieldCode").val();
		   mappingColumn=$(row).find(".mappingFields").val();
		   returnJson[fieldCode]=mappingColumn;
		 });
	   inputObj.val(JSON.stringify(returnJson));
   }
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content">
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${eimServerDataImp.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td width="150" align="left"><label for="name" class="required">实例名称&nbsp;</label></td>
    <td align="left">
        <input id="name" name="name" type="text" class="k-textbox"  
	   data-bind="value: name"
	   value="${eimServerDataImp.name}" validationMessage="请输入name"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="appId" class="required">应用ID&nbsp;</label></td>
    <td align="left">
        <select id="appId" name="appId" type="text" data-bind="value: appId" value="${eimServerDataImp.appId}">
	    </select>
    </td>
  </tr>
  <tr>
      <td width="150" align="left"><label for="tmpId" class="required">服务模板ID&nbsp;</label></td>
    <td align="left">
	    <input id="tmpName" name="tmpName" type="text" class="k-textbox"  onclick="showMenu();"/>
        <input id="tmpId" name="tmpId" type="hidden" class="k-textbox"  data-bind="value: tmpId" value="${eimServerDataImp.tmpId}" />
	   <span class="k-invalid-msg" data-for="tmpName"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="incrementFlag" class="required">导入模式&nbsp;</label></td>
    <td align="left">
	 <input type="radio"  name="incrementFlag"  value="0" ${eimServerDataImp.incrementFlag!=1?"checked":""}/> 全量
	 <span></span>
	 <input type="radio"  name="incrementFlag" value="1" ${eimServerDataImp.incrementFlag==1?"checked":""}/> 增量
	 <span></span>
    </td>
  </tr>
  <tr>
   <td width="150" align="left"><label for="emptyTable" class="required">导入前清空目标表&nbsp;</label></td>
    <td align="left">
	 <input type="radio"  name="emptyTable" value="0" ${eimServerDataImp.emptyTable!=1?"checked":""}/> 否
			 <span></span>
			 <input type="radio"  name="emptyTable"  value="1" ${eimServerDataImp.emptyTable==1?"checked":""}/> 是
			 <span></span>
    </td>
  </tr>
  <tr>
     <td width="150" align="left"><label for="preSql" class="required">导入前执行SQL&nbsp;</label></td>
    <td align="left">
        <input id="preSql" name="preSql" type="text" class="k-textbox"  
	   data-bind="value: preSql"
	   value="${eimServerDataImp.preSql}" validationMessage="请输入preSql"/>
	   <span class="k-invalid-msg" data-for="preSql"></span>
    </td>
  </tr>
  <tr>
     <td width="150" align="left"><label for="params" class="required">参数&nbsp;</label></td>
    <td align="left">
        <input id="params" name="params" type="hidden" 
	   data-bind="value: params"
	   value="${eimServerDataImp.params}"/>
    </td>
  </tr>
  <tr>
     <td width="150" align="left"><label for="targetDatabase" class="required">目标数据库&nbsp;</label></td>
    <td align="left">
        <input id="targetDatabase" name="targetDatabase" type="text" class="k-textbox"  
	   data-bind="value: targetDatabase"
	   value="${eimServerDataImp.targetDatabase}" validationMessage="请输入targetDatabase"/>
	<span class="k-invalid-msg" data-for="targetDatabase"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="targetTable" class="required">目标表&nbsp;</label></td>
    <td align="left">
	    <input id="targetTableName" name="targetTableName" type="text" class="k-textbox"  onclick="showTable();"/>
        <input id="targetTable" name="targetTable" type="hidden" class="k-textbox"  
	   data-bind="value: targetTable"
	   value="${eimServerDataImp.targetTable}" validationMessage="请输入targetTable"/>
	<span class="k-invalid-msg" data-for="targetTable"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="columnMapping" class="required">接口字段映射&nbsp;</label></td>
    <td align="left">
	    <input id="columnMapping" name="columnMapping" type="hidden"/>
    </td>
  </tr>
    <tr>
    <td width="150" align="left"> <label for="deleteFlag" class="required">有效性&nbsp;</label></td>
    <td align="left"> 
			 <input type="radio"  name="deleteFlag" value="0" ${eimServerDataImp.deleteFlag!=1?"checked":""}/> 有效
			 <span></span>
			 <input type="radio"  name="deleteFlag"  value="1" ${eimServerDataImp.deleteFlag==1?"checked":""}/> 无效
			 <span></span>
    </td>
  </tr>
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	</div>
	</td>
      </tr>
</table>   
</form>
</div>  
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="tmpTree" class="ztree" style="margin-top:0; width:180px; height: 300px;background-color: #f3f3f3;"></ul>
</div>
<div id="tableContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="tableTree" class="ztree" style="margin-top:0; width:180px; height: 300px;background-color: #f3f3f3;"></ul>
</div>   
<script>
    jQuery(document).ready(function() {	    
		 //初始化应用下拉选择框
		 $.ajax({
			 type: "POST",
				   url: "<%=request.getContextPath()%>/rs/teim/base/all",
				   dataType:  'json',
				   error: function(data){
					   console.log('获取应用数据错误！');
				   },
				   success: function(data){
					   if(data!= null && data.data!= null){
						 var appSelect = $("#appId");
						 $.each(data.data,function(i,item){
							  appSelect.append("<option value=\""+item.ID_+"\">"+item.NAME_+"</option>");
						 });
						 var currSelect="${eimServerDataImp.appId}";
						 appSelect.val(currSelect);
					   } else {
						  console.log('未获取到应用数据！');
					   }
					  
				   }
		 });
    });
	/**
	*参数新增删除按钮事件绑定
	*/
   function bindBt(tableObj){
		tableObj.on("click",".add",function(){
			var row=$($("#rowTmp").html());
			$(this).closest("tr").after(row);
		});
		tableObj.on("click",".delete",function(){
			$(this).closest("tr").remove();
		});
		tableObj.on("change",".incrementParam",function(){
			var incrementParam=$(this).val();
			if(incrementParam==1){
				$(this).closest("tr").find(".upperLimit").removeAttr("disabled");
			}else{
				$(this).closest("tr").find(".upperLimit").val("");
				$(this).closest("tr").find(".upperLimit").attr("disabled","disabled");
			}
		});
	}
	/**
	*模板选择树配置
	*/
	var setting = {
			view: {
				dblClickExpand: false,
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId"
				}
			},
			callback: {
				onClick: onClick
			}
		};
         var url="${contextPath}/rs/teim/tmp/tree";
		 //初次加载
		 var initFlag=1;
        $.fn.extend($.fn.zTree,{
		  initZtree:function (container,url,setting,defaultSelect){
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if($.isEmptyObject(rdata)){
						rdata=new Array();
					}
					var treeObj =$.fn.zTree.init(container,setting, rdata);
					//默认选中节点
		            var node = treeObj.getNodesByParam("id",defaultSelect, null);
		             if(node.length>0){
						treeObj.selectNode(node[0]);
						$("#"+node[0].tId+"_a").click();
					 }
				},
				error : function() {
					console.log("获取数据失败");
				}

		   }
		  );
		}});
		/**
		*模板选择事件
		*/
		function onClick(e, treeId, treeNode) {
			var tmpName = $("#tmpName");
			var tmpId = $("#tmpId");
			if(treeNode.type==0)
			{
			   tmpName.val(treeNode.name);
			   tmpId.val(treeNode.id);
			   if(initFlag==0){
				   var tmpId=tmpId.val();
				   var columnMapping='${eimServerDataImp.columnMapping}';
				   initColumnGrid($("#columnMapping"),"columnMappingGrid",columnMapping,tmpId);
			   }
			}
			initFlag=0;
		}
	   /**
	   *控制模板树的显示与隐藏
	   */
	   function showMenu() {
			var tmpName = $("#tmpName");
			var cityOffset = $("#tmpName").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + tmpName.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "tmpName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
	/**
	*数据表选择树配置
	*/
	var setting2 = {
		async:{
			enable:true,
			url:'${contextPath}/rs/isdp/cellUTableTree/getUtableTreeByTableTypeInit?type=1&tableType=2&systemName=default',
			autoParam:["indexId","nlevel=level"],
			dataFilter : function(treeId, msg, ret){
				iterator(ret);
				return ret;
			}
		},
		data: {
			simpleData:{
				enable:true,
				idKey : 'indexId',
				pIdKey:"parentId"
			},
			key:{
				name:"indexName"
			}
		},
		callback: {
			onClick: ztreeClick
		},
		view: {
			selectedMulti: false,
			fontCss: function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
			}
		}
	};
	/**
	*控制数据表选择树的显示与隐藏
	*/
	function showTable() {
			var tmpName = $("#targetTableName");
			var cityOffset = $("#targetTableName").offset();
			$("#tableContent").css({left:cityOffset.left + "px", top:cityOffset.top + tmpName.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown",tableOnBodyDown);
		}
		function hideTable() {
			$("#tableContent").fadeOut("fast");
			$("body").unbind("mousedown",tableOnBodyDown);
		}
		function tableOnBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "targetTableName" || event.target.id == "tableContent" || $(event.target).parents("#tableContent").length>0)) {
				hideTable();
			}
		}
	/**
	*树节点增加图标
	*/
	function iterator(nodes){
			if(nodes && nodes.length){
				$.each(nodes, function(i,v){
					if(v.tableName){
						v.icon = "${contextPath}/images/table_tab.png";
						if(v.tableName=='${eimServerDataImp.targetTable}'){
							$("#targetTableName").val(v.tableNameCN);
						}
					}
					if(v.children && v.children.length){
						iterator(v.children);
					}
				});
			}
       }
	/**
	*数据表选中事件
	*/
	function ztreeClick(e,treeId,treeNode){
			var targetTableName = $("#targetTableName");
			var targetTable = $("#targetTable");
			if(treeNode.tableName&&treeNode.tableName!='')
			{
			   targetTable.val(treeNode.tableName);
			   targetTableName.val(treeNode.tableNameCN);
			   if(initFlag==0){
				  //初始化映射table
				  var tmpId=$("#tmpId").val();
				  initColumnGrid($("#columnMapping"),"columnMappingGrid",null,tmpId);
			      updateFieldSelect(treeNode.tableName);
			   }
			}
		}
		var tableTreeObj;
	/**
	* 初始化模板树和数据表树
	*/
	$(document).ready(function(){
			 $.fn.zTree.initZtree($("#tmpTree"),url,setting,"${eimServerDataImp.tmpId}");
			 tableTreeObj =$.fn.zTree.init($("#tableTree"),setting2,null);
	});
</script>
<script type="text/html" id="tableTmp">
     <table style="width: 700px; margin: 0px; padding: 0px; border-spacing: 0px;">
	     <thead class="datagrid-header">
		 <tr>
		   <th style="width:100px;">代码</th>
		   <th style="width:210px;">描述</th>
		   <th style="width:100px;">数据类型</th>
		   <th style="width:130px;">增量条件</th>
		   <th style="width:130px;">下限值</th>
		   <th style="width:130px;">上限值</th>
		   <th style="width:150px;"><button type="button" class="topAdd">新增</button></th>
		 </tr>
		 </thead>
		 <tbody class="datagrid-tbody">
		  <tr>
		   <td><input type="text" class="code"/></td>
		   <td><input type="text" class="name"/></td>
		   <td>
		      <select class="type">
			   <option value="string">字符串</option>
			   <option value="int">数值</option>
			   <option value="date">日期</option>
			 </select>
		   </td>
		   <td>
		      <select class="incrementParam">
			   <option value="0">否</option>
			   <option value="1">是</option>
			 </select>
		   </td>
		   <td><input type="text" class="lowerLimit"/></td>
		   <td><input type="text" class="upperLimit" disabled/></td>
		   <td><button type="button" class="add">新增</button>&nbsp;<button type="button" class="delete">删除</button></td>
		  </tr>
		 </tbody>
	</table>
   </script>
    <script type="text/html" id="rowTmp">
      <tr>
		   <td><input type="text" class="code"/></td>
		   <td><input type="text" class="name"/></td>
		    <td>
		      <select class="type">
			   <option value="string">字符串</option>
			   <option value="int">数值</option>
			   <option value="date">日期</option>
			 </select>
		   </td>
		   <td>
		      <select class="incrementParam">
			   <option value="0">否</option>
			   <option value="1">是</option>
			 </select>
		   </td>
		    <td><input type="text" class="lowerLimit"/></td>
		   <td><input type="text" class="upperLimit" disabled /></td>
		   <td><button type="button" class="add">新增</button>&nbsp;<button type="button" class="delete">删除</button></td>
	  </tr>
   </script>
   <script type="text/html" id="columnTableTmp">
     <table style="width: 600px; margin: 0px; padding: 0px; border-spacing: 0px;">
	     <thead class="datagrid-header">
		 <tr>
		   <th style="width:200px;">接口字段代码</th>
		   <th style="width:200px;">接口字段名称</th>
		   <th style="width:200px;">数据表字段名称</th>
		 </tr>
		 </thead>
		 <tbody class="datagrid-tbody">

		 </tbody>
	</table>
   </script>
   <script type="text/html" id="columnRowTmp">
      <tr>
		   <td><input type="text" class="fieldCode"/></td>
		   <td><input type="text" class="fieldName"/></td>
		   <td>
		      <select class="mappingFields">
			          
			  </select>
		   </td>
	  </tr>
   </script>
</body>
</html>