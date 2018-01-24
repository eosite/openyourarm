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
body{
	margin:0;
}
html,body,#main_content{
	height: 100%;
}
</style>
<script type="text/javascript">
var _callbackParams = [], _inParams = [];
	//事件定义器中的回调方法
    var retFn = "${param.retFn}";
    var getFn = "${param.getFn}";
    function callback(data){
        var retObj = {
            name:data.name,
            value:data.appId+"_0_"+data.tmpId,
            inParams : _inParams,
            callbackParams : _callbackParams
        };
        parent[retFn](retObj);
        closeWin();
    }

    function closeWin(){
        parent.layer.close(parent.layer.getFrameIndex());
    }
    var idata ={};
    var _tmpId = "";
    var _appId = "";
    var _name = "";

    if(getFn){
  		idata =  parent[getFn]();  	
    }
    if(idata){
    	var value = idata.value;
	    var ids = [];
	    if(value){
	    	ids = value.split("_0_");
	    	_tmpId = ids[1];
	    	_appId = ids[0];
	    }
	    _name = idata.name;
	    _inParams = idata.inParams;
		_callbackParams = idata.callbackParams;
    }
    
  
  jQuery(function() {
    var viewModel = kendo.observable({
        "name": _name,
        "appId": _appId,
        "tmpId": _tmpId
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });  
          //初始化grid
		  var tmpId=_tmpId;
		  var columnMapping='${eimServerDataImp.columnMapping}';
		  if(tmpId!=''){
             initColumnGrid($("#columnMapping"),"columnMappingGrid",columnMapping,tmpId);
		  }		  
    });
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
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				responseBody=data;
			},
			error : function(e){
				console.log(e);
			},
			complete : function(e){
				console.log(e);
			}
		});
		return responseBody;
	}
	/**
	 * 获取输入形参信息
	 * @param  {[type]} tmpId [description]
	 * @return {[type]}       [description]
	 */
	function getTmpRequestBody(tmpId){
		var requestBody;
		$.ajax({
			url :"${contextPath}/rs/teim/tmp/requestBody",
			type : 'post',
			dataType : 'json',
			data : {
				tmpId:tmpId
			},
			async : false,
			success : function(data){
				if(typeof data == 'string'){
					data = JSON.parse(data);
				}
				requestBody=data;
			},
			error : function(e){
				console.log(e);
			},
			complete : function(e){
				console.log(e);
			}
		});
		return requestBody;
	}
	function getInOrOutParam(tmpId){
		_callbackParams = [],_inParams = [];
		var responseBody=getTmpResponseBody(tmpId);
		var requestBody = getTmpRequestBody(tmpId);
		//产生行记录
		if(responseBody&&responseBody!='')
		{
			var dataStruct=responseBody.data!=undefined?responseBody.data.dataitem:null;
			//如果当前有选中目标表，初始化目标表列下拉选择框值
			if(dataStruct){
				$.each(dataStruct,function(code,item){
					_callbackParams.push({
						name: item.name || code,
						param: code,
						text: item.name || code
					});
				});
			}
		}
		if(requestBody && requestBody != ''){
			$.each(requestBody,function(code,item){
				_inParams.push({
					name: item.name || code,
					param: code,
					text: item.name || code
				});
			});
		}
	}
	/**
	*初始化字段映射Grid
	*/
    function initColumnGrid(preObj,id,content,tmpId){
        getInOrOutParam(tmpId);
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
	   var data = {};
	   var params = jQuery("#iForm").formToArray();
	   $.each(params,function(i,item){
	   		data[item.name] = item.value;
	   })

		callback(data);
	}

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
	   data-bind="value: name" validationMessage="请输入name"/>
	<span class="k-invalid-msg" data-for="name"></span>
    </td>
  </tr>
  <tr>
    <td width="150" align="left"><label for="appId" class="required">应用ID&nbsp;</label></td>
    <td align="left">
        <select id="appId" name="appId" type="text" data-bind="value: appId">
	    </select>
    </td>
  </tr>
  <tr>
      <td width="150" align="left"><label for="tmpId" class="required">服务模板ID&nbsp;</label></td>
    <td align="left">
	    <input id="tmpName" name="tmpName" type="text" class="k-textbox" data-bind="value: tmpName" onclick="showMenu();"/>
        <input id="tmpId" name="tmpId" type="hidden" class="k-textbox"  data-bind="value: tmpId"  />
	   <span class="k-invalid-msg" data-for="tmpName"></span>
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
						 var currSelect=_appId;
						 appSelect.val(currSelect);
					   } else {
						  console.log('未获取到应用数据！');
					   }
					  
				   }
		 });
    });
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
					 }else{
					 	initFlag = 0;
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
			 $.fn.zTree.initZtree($("#tmpTree"),url,setting,_tmpId);
			 tableTreeObj =$.fn.zTree.init($("#tableTree"),setting2,null);
	});
</script>
</body>
</html>