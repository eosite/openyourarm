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
<title>表关系定义</title>
<style type="text/css"> 
	.tdCls{
		background-color:#FCFCFC;
		align:left;
		height:16px;
	}
	.selectCls{
		width : 45%;
	}
	.textCls{
		width : 80%;
	}
</style>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/webfile/js/jquery.kendo.extends.js"></script>
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/glaf-base.js"></script>
<script type="text/javascript">

(function($){

	function createTable(target){
		
		var state = $(target).data('state');
		
		var options = state.options;
		
		var data = options.data;
		
		var trSB,thSB;
		$.each(data,function(index,row){
		
			trSB = new StringBuffer();
			
			trSB.appendFormat("<tr rowIndex='{0}' >",index);
			
			$.each(options.columns,function(i,v){
				if(index == 0){
					if(!thSB){
						thSB = new StringBuffer("<tr>");
					}
					thSB.appendFormat("<th class='{0}' >{1}</th>",v.cls || "tdCls",v.title);
					if(i == options.columns.length - 1){
						thSB.append("</tr>");
						$(target).append(thSB.toString());
					}
				}
				trSB.appendFormat("<td class='{0} {2}cls' >{1}</td>",v.cls || "tdCls",getFormatterVal(row,row[v.field] || '',index,v),v.field);
			});
			$(target).append(trSB.append("</tr>").toString());				
		});
		
		function getFormatterVal(row,value,index,item){
			if(item.formatter){
				return item.formatter(row,value,index);
			}else
			    return value;
		}
	}
	
	$.fn.initTable = function(options){
		 return this.each(function(i,item){
		 	  var state = $(item).data('state');
		 	  if(!state){
		 	  		$(item).data('state',{
		 	  			options : options
		 	  		});
		 	  }else{
		 	  		
		 	  }
		 	  createTable(item);
		 });
	};
})(jQuery);


	jQuery(function(){
	
		var fs = [
			'id','标题','类型','兴趣'
		];
		for(var i = 0; i < 1; i ++){
			fs.push("兴趣" + i);
		}
		
		var fields = new Array();
		
		$.each(fs,function(i,v){
			fields.push({
				field : v
			});
		});
	
		$('#fieldsTable').initTable({
			data :fields,
			columns : [
				{
					field : 'field',
					title : '表头列'
				},{
					field : 'dataBase',
					title : '数据源',
					formatter : function(row,value,index){
						return "<select></select>";
					}
				},{
					field : 'dataTable',
					title : '数据表',
					formatter : function(row,value,index){
						//return "<input class='k-textbox dataTableName' id='dataTableName" + index + "' ></input><input type='hidden' id='dataTableObj" + index + "' ></input>";
						return "<select></select>";
					}
				},{
					field : 'dataField',
					title : '数据列',
					formatter : function(row,value,index){
						return "<input class='k-textbox dataFieldName' id='dataFieldName" + index + "' ></input><input type='hidden' id='dataFieldObj" + index + "' ></input>";
					}
				}
			]
		});
		
		//获取数据源
		var databaseUrl = "<%=request.getContextPath()%>/mx/sys/database/json";
		$.mt.get(databaseUrl,{},function(data){
			$('.dataBasecls select,.dataTablecls select').each(function(i,v){
				$(this).kendoComboBox({
		           dataTextField: "title",
		           dataValueField: "code",
		           dataSource: data.rows,
		           select : function(e){
		           		var dataItem = this.dataItem(e.item.index());
		           		$(v).data('dataItem',dataItem);
		           }
		       }).data('kendoComboBox').select(0);
			});
		});
		
		$('.dataTableName').each(function(i,v){
			$(this).on('click',function(){
				
				var dataItem = $('.dataBasecls select').eq(i).data('kendoComboBox').dataItem();
				
				var nameElementId = $(this).attr('id'),objElementId = 'dataTableObj' + i;
				
				selectTable(dataItem.dbname,'',nameElementId,objElementId,$('#' + objElementId).val(),null);
				//selectTable(databaseCode,tableIdElementId,tableNameElementId,tableObjElementId,tableJson,endFunction)
				
				cssItem(v);
			});
		});
		
		$('.dataFieldName').each(function(i,v){
			$(this).on('click',function(){
				
				/* var dataItem = $('.dataBasecls select').eq(i).data('kendoComboBox').dataItem();
				
				var fieldNameElementId = $(this).attr('id'),fieldObjElementId = 'dataFieldObj' + i;
				
				var tableObjElementId = 'dataTableObj' + i;
				
				selectField(dataItem.dbname,'',fieldNameElementId,fieldObjElementId,$('#' + tableObjElementId).val() || '',$('#' + fieldObjElementId).val() || '','');
				//selectField(databaseCode,fieldIdElementId,fieldNameElementId,fieldObjElementId,tableJson,fieldJson,endFunction)
				 */
				 cssItem(v);
			});
		});
		
	});
	
	function cssItem(item){
		$('input').css({
			background : ''
		});
		
		$(item).css({
			background : '#FFB90F'
		});
	}
	
	function initdata(){
	
		var row,done = true;
		var rows = new Array();
		$('input').css({
			background : ''
		});
		$('#fieldsTable [rowindex]').each(function(i,tr){
			row = new Object();
			$(this).find('td').each(function(index,td){
				switch(index){
					case 0:
						row.title = $(this).text();
						break;
					case 1:
						row.database = $(this).find('select').data('kendoComboBox').dataItem();
						break;
					case 2:
						var dataTableName;
						row.dataTable = $(this).find('select').data('kendoComboBox').dataItem();
						break;
					case 3:
					
						var dataFieldName = $('#dataFieldName' + i),dataFieldObj = $('#dataFieldObj' + i).val();
						
						if(!dataFieldName.val()){
							dataFieldName.css({
								background : '#FFB90F'
							});
							done = false;
							return false;
						}else{
							row.dataField = {
								name : dataFieldName.val(),
								value : dataFieldObj
							};
						}
						break;
					default:break;
				}
			});
			rows.push(row);
		});
		if(!done){
			alert("请完成所有配置!");
		}else{
			data = rows;
			console.log(data);
			alert("保存成功！");
		}
	}
	
	var data;
	function relateset(){
		
		if(!data){
			alert('请先保存!');return;
		}
        var link = '<%=request.getContextPath()%>/mx/expression/defined/view?getFn=getExpressionData&retFn=returnExpressionData';
        $.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "选择字段",
			closeBtn: [0, true],
			shade: [0, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['',''],
			fadeIn: 100,
			area: ['800px', '630px'],
	        iframe: {src: link},
	        end: function(data){
	        	console.log(data);
	        }
		});
	}
	
	var tmpdata = [
		{ id:1, pId:0, name:"江苏高速",t:'',open:true,isParent:true}, 
		{ id:11, pId:1, name:"试验任务",t:'',open:true,isParent:true}, 
		{ id:111, pId:11, name:"名称",t:'',code:"$F{db1.cell_useradd12.column1}",value:"$F{数据源1.试验任务.名称}",isParent:false}, 
		{ id:112, pId:11, name:"备注",t:'',code:"$F{db1.cell_useradd12.column2}",value:"$F{数据源1.试验任务.备注}",isParent:false}, 
		{ id:2, pId:0, name:"京台高速",t:'',open:true,isParent:true}, 
		{ id:21, pId:2, name:"试验任务",t:'',open:true,isParent:true}, 
		{ id:211, pId:21, name:"名称",t:'',code:"$F{db2.cell_useradd12.column1}",value:"$F{数据源2.试验任务.名称}",isParent:false}, 
		{ id:212, pId:21, name:"备注",t:'',code:"$F{db2.cell_useradd12.column1}",value:"$F{数据源2.试验任务.备注}",isParent:false} 
	];
	
	
	function getExpressionData(){
		return tmpdata;
	}
	
	function returnExpressionData(data){
		
		if(data){
			$('#expressionName').val(data.name);
			$('#expressionObject').val(data.value);
			layer.close(layer.getFrameIndex());
		}
	}
	
</script>
</head>
<body>
	<div style=" width:50%; margin: 60px auto" >
		<table style="width:98%">
			<tr>
				<td>
					<table id="fieldsTable" width="98%" border="0" cellpadding="5" cellspacing="1" bgcolor="BCD2EE" align="center">
					</table>
				</td>
			</tr>
			
			<tr >
				<td align="center" >
					<button class='k-button' style="width:120px;" onclick='initdata();' > 保   存 </button>
					<button class='k-button' style="width:120px;" onclick='relateset();' > 关 联 设 置 </button>
				</td>
			</tr>
			<tr>
				<td align="center">
					<textarea id="expressionName" rows="10" cols="85" readonly="readonly" ></textarea>
					<input type="hidden" id="expressionObject">
				</td>
			</tr>
		</table>
	</div>
	<div id="window" style="display: none;">
		<iframe id="winFrame" src="" ></iframe>
	</div>
</body>
</html>