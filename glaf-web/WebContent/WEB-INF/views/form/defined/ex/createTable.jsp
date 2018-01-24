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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建表信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
var mtxx = {
	fn : '${fn}',
	targetId : '${targetId}',
	tableid : '${tableid}',
	parent : window.opener || window.parent,
	contextPath : '${pageContext.request.contextPath}',
	url : '${pageContext.request.contextPath}/mx/form/defined'
};

mtxx.getDictByCode = function(code,fn){
	$.ajax({
		url : mtxx.url + '/getDictByCode',
		data : {code : code},
		type : 'post',
		dataType : 'json',
		async : false,
		success:function(data){ 
			if(fn)
				fn(data);
		}
	});
};

/**
*字段类型
*/
mtxx.getDictByCode('dType',function(data){
	data = data || new Array(),mtxx.dTypeSourceObj = new Object();
	$.each(data,function(i,v){
		mtxx.dTypeSourceObj[v.code] = v;
	});
	mtxx.dTypeSource = new kendo.data.DataSource({
		data : data
	});
});

/**
 * 验证
 */
function validateFunc(validate){
	var data = mtxx["grid-1"].dataSource.data(),row,undo = false;
	h1 : for(var i = 0; i < data.length; i ++){
		row = data[i];
		if(row.dType == 'string'){
			for(key in mtxx.dataTemplate){
				if(!row[key]){
					mtxx['grid-1'].select("#grid-1 tbody tr:eq(" + i + ")");
					undo = true;
				}
			}
		} else {
			row.strlen = null;
		}
	}
	if(undo){
		alert('请完成高亮选中内容!');
	} else if(validate){
		alert('验证成功!');
	}
	
	return undo;
}

	$(function(){
		
		$('#create_table_type').kendoDropDownList({
			 dataSource : [
				{name:"普通表",value:"1"}
			 ],
			 dataTextField: "name",
			 dataValueField: "value"
		});
		
		var kcreate_table_sort = $("#create_table_sort").kendoDropDownList({
			dataTextField: "indexName",
			dataValueField: "indexId",
			dataSource : new kendo.data.DataSource({
				type:"json",
				transport:{
					read:{
						contentType:"application/json",
						url:mtxx.contextPath + "/rs/isdp/cellUTableTree/getUtableTreeByTableType",
						type: "POST",
                        dataType:"JSON"
					},
					parameterMap : function(options, operation) {
	                    if (operation == "read") {
                    		options.systemName = "${databaseCode}";
	                    	return JSON.stringify(options);
	                    }
	                 }
				}
			})
		}).data("kendoDropDownList");
		
		var $create_table_name = $("#create_table_name"),set = function(table){
			mtxx.tableid = table.id;
			kcreate_table_sort.value(table.indexId);
			$create_table_name.val(table.name);
		};
		
		var datas = '',dataTemplate = mtxx.dataTemplate = {
				dType : 'string',
				text : '',
				strlen : null
		},rst = false;
		var $p = mtxx.parent;
		
		if(mtxx.tableid){//修改表
			var tcs = getColumnsByTableId(mtxx.tableid);
			if(tcs){
				set(tcs.table);
				datas = tcs.columns;
			}
		} else if($p){
			
			var func = $p[mtxx.fn],parentMsg,
			target = $p.document.getElementById(mtxx.targetId);
			if(func){
				parentMsg = func.call(target);
			} else if (target){
				parentMsg = target.value;
			}
			/*
			try{
				parentMsg = JSON.parse(parentMsg);
			} catch (e){
				
			}
			*/
			if(parentMsg){
				var linkageControl = parentMsg.linkageControl,
				tableMsg = parentMsg.tableMsg;
				if(linkageControl){
					linkageControl = JSON.parse(linkageControl);
				}
				if(tableMsg){
					tableMsg = JSON.parse(tableMsg);
					if(tableMsg && tableMsg[0]){
						var table = tableMsg[0].table;
						var columns = tableMsg[0].columns;
						if(table){
							set(table);
						}
						if(linkageControl && linkageControl[0] && columns){
							var tmp = new Object();
							$.each(columns,function(i,v){
								tmp[v.id] = v;
							});
							$.each(linkageControl,function(i,v){
								var t = tmp[v.id];
								if(t){
									$.extend(v,t);
								}
							});
						}
					}
				}
				datas = linkageControl;
				rst = true;
			}
			
		}
		
		mtxx.datas = datas || [dataTemplate];
		
		initGridOne(mtxx.datas,rst);
		
		
		//创建表确认事件
		var validate = $("#create_table_content").kendoValidator();
		$("#create_table_submit_btn").kendoButton({
			imageUrl : mtxx.contextPath + '/images/database_save.png'
		}).on("click",function(e){
			if(validate.data("kendoValidator").validate()){
				if(validateFunc()){
					return false;
				}
				var params = {
					name:$create_table_name.val(),
					systemName:"${databaseCode}",
					indexId : kcreate_table_sort.value(),
					tableid : mtxx.tableid,
					columns : JSON.stringify(mtxx["grid-1"].dataSource.data())
				};
				$.ajax({
					type: "POST",
					url: mtxx.contextPath + "/rs/isdp/table/saveTableInfo",
					dataType: 'json',
					async : false,
					data: params,
					error: function(data){
						alert('服务器处理错误！');
					},
					success: function(data){
						if(data != null && data.message != null){
							alert(data.message);
						} else {
							mtxx.tableid = data.table.id;
							mtxx.datas = data.columns;
							mtxx["grid-1"].setDataSource(new kendo.data.DataSource({
								  data:mtxx.datas
							}));
							if(mtxx.parent){
								var func = mtxx.parent[mtxx.fn],target = mtxx.parent.document.getElementById(mtxx.targetId);
								if(func){
									func.call(target,data);
								} else if (target){
									target.value = JSON.stringify(data);
								}
							}
							alert('操作成功!');
						}
					}
				});
			}
		});
		
		/**
		*	验证
		*/
		$('#create_table_test_btn').kendoButton({
			imageUrl : mtxx.contextPath + '/images/filter.png',
			click : function(){
				validateFunc(true);
			}
		});
		
	});
	
	function initGridOne(datas,o){
		$.each(datas,function(i,v){
			for(var k in mtxx.dataTemplate)
				v[k] = v[k] || mtxx.dataTemplate[k];
		});
		
		var options = {
				height : 360,
				autoSync : true ,
				scrollable : true,
				editable : true,
				selectable : "multiple,row",
				dataSource : datas,
				columns : initColumns(datas,o)
		};
		
		if(!o){
			options.toolbar = kendo.template("<button class='k-button' id='grid-1-toolbar-create'>增加</button>");
		}
		
		mtxx["grid-1"] = $("#grid-1").kendoGrid(options).data("kendoGrid");
		
		$("#grid-1-toolbar-create").kendoButton({
			imageUrl : mtxx.contextPath + '/images/bullet_add.png',
			click : function(e){
				datas = mtxx["grid-1"].dataSource.data();
				datas.splice(0,0,mtxx.dataTemplate);
				mtxx["grid-1"].setDataSource(new kendo.data.DataSource({
					  data:datas
				}));
			}
		});
	}
	
	function initColumns(datas,o){
		var columns = [
			{
				title : '名称',
				field : 'text',
				editor : function(container,options,index){
					$("<input/>",{
						name : options.field,
						class : 'k-textbox'
					}).appendTo(container).change(function(){
						datas[$(container).closest('tr').index()][options.field] = $(this).val();
					});
				}
			},
			{
				title : '字段类型',
				field : 'dType',
				template : function(dataItem){
					var v = mtxx.dTypeSourceObj[dataItem.dType];
					if(v){
						return v.name;
					} else {
						return '';
					}
				},
				editor : function(container,options,index){
					$("<input>",{
						name : options.field
					}).appendTo(container).kendoDropDownList({
						dataValueField:'code',
						dataTextField:'name',
						dataSource:mtxx.dTypeSource,
						change:function(e){
						 	datas[$(container).closest('tr').index()][options.field] = e.sender.value();
				   }});
				}
			},
			{
				title : '字段长度',
				field : 'strlen',
				editor : function(container,options){
					var $tr = container.closest('tr'),index = $tr.index(),grid = $tr.closest('[data-role=grid]').data('kendoGrid');
					var dataItem = grid.dataItem($tr);
					$("<input>",{
						name : options.field
					}).appendTo(container).change(function(e){
						datas[index][options.field] = $(this).val();
					}).kendoNumericTextBox();
				}
			},
			{ title: "&nbsp;", width: "120px",template : function(){
		    	return "<button class='k-button k-delete' onclick='deletehandler(this);' >删除</button>";
		    } }
		];
		if(o){
			columns.splice(1,0,{
				title : '绑定状况',
				field :'status',
				template : function(dataItem){
					return dataItem.fieldName ? "已绑定" : "<span style='color:red;' >未绑定</span>";
				},
				editor : function(){
					
				}
			});
		}
		
		return columns;
	}
	
	function getColumnsByTableId(tableId){
		var o = null;
		$.ajax({
			url : mtxx.contextPath + "/rs/isdp/table/getColumnsByTableId",
			type : 'post',
			dataType : 'json',
			data : {
				tableid : tableId
			},
			async : false,
			success : function(data){
				o = data;
			}
		});
		return o;
	}
	
	function deletehandler(o){
		var item = mtxx["grid-1"].dataItem($(o).closest('tr'));
		mtxx["grid-1"].removeRow(o);
		if(item && item.tableName && '${tableid}'){
			$.post(mtxx.contextPath + "/rs/isdp/table/deleteColumn",{
				tablename : item.tableName,
				FieldId : item.fieldId,
				listId : item.listId,
				dname : item.fieldName
			},function(data){
				if(data != null && data.message != null){
					alert(data.message);
				} else {
					alert('操作成功!');
				}
			},'json');
		}
	}
	
</script>
</head>
<body>
	<div id="create_table_content" style='margin-top: 20px;'>
		<fieldset style="border:#DBDBDB dashed 1px;"  >
			<legend style="font-size:12px;font-weight:800; ">创建表信息</legend>
		 	<table cellspacing="5" border="0" style="width:90%">
		 		<tr>
		 			
		 			<td align="right">创建表类型</td>
		 			<td>
		 				<input id="create_table_type" />
		 			</td>
		 			<td align="right">业务表分类</td>
		 			<td>
		 				<input id="create_table_sort" />
		 			</td>
		 			<td align="right" width="60px">表名</td>
		 			<td>
		 				<input type="text" id="create_table_name" name="create_table_name" class="k-textbox" required validationMessage="必填项" />
		 				<span class="k-invalid-msg" data-for="create_table_name"></span>
		 			</td>
		 		</tr>
		 	</table>
		</fieldset>
	 	<div id='grid-1' style='margin-top: 10px;' ></div>
	 	<div style="float: right;margin: 10px;">
		 	<button id="create_table_test_btn" class="k-button">验证</button>
		 	<button id="create_table_submit_btn" class="k-button">保存</button>
	 	</div>
	 </div>
</body>
</html>