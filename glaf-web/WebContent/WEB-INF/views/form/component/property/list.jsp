<%@ page contentType="text/html;charset=UTF-8"%>
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
<title>表单组件属性列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
	<!--
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button>
	-->
	<button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:importProperties();">引入属性</button>

	<button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:sortBy();">分类到</button>
             
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:history.go(-1);">返回</button>
            
   </div>
</script>
<style type="text/css">
	.kendo-state-sort{
		background-color: #FFDEAD
	}
</style>
<script type="text/javascript">
	var treeList,sortDiv;
  jQuery(function() {
	  
	  var dataSource = new kendo.data.TreeListDataSource({
          transport: {
              read: {
                  url: "<%=request.getContextPath()%>/rs/form/component/property/data?kendoComponent=${formComponent.kendoComponent}&componentId=${formComponent.id}",
                  dataType: "json",
				  contentType: "application/json",
                  type: "POST"
              },
              parameterMap: function(options, operation) {
               	  options.sort = [{field:'listno_',dir:'desc'}];
               	  options.pageSize = 1000;
                     return JSON.stringify(options);
              }
          },
		  batch: false,
          schema: {
        	  data : 'rows',
              model: {
                  id: "id",
                  fields: {
                      id: { type: "number", nullable: false },
                      parentId: { field: "parentId", type: "number", nullable: true } 
                  },
				   expanded: true
              }
          }
      });
	 
	  treeList = jQuery("#grid").kendoTreeList({
	        "dataSource": dataSource,
	        "height": x_height,
			"toolbar": kendo.template(jQuery("#template").html()),
			"selectable": "multiple, row",
            "columns": [
					{
						"field": "title",
						"title": "标题",
			            "width": "150px",
						"lockable": false,
						"locked": false
		            },
		            {
						"field": "name",
						"title": "名称",
			            "width": "150px",
						"lockable": false,
						"locked": false
		            },
		            {
						"field": "dataType",
						"title": "数据类型",
			            "width": "150px",
						"lockable": false,
						"locked": false
		            },
		            {
						"field": "type",
						"title": "类型",
			            "width": "150px",
						"lockable": false,
						"locked": false,
						template : function(dataItem){
							return dataItem.type;
						}
		            },              
		            {
						"field": "createDate",
						"title": "创建日期",
						"width": "120px",
						"format": "{0: yyyy-MM-dd}",
						"filterable": {
							"ui": "datetimepicker"  
						},
						"lockable": false,
						"locked": false
		            },
		            {
						"field": "createBy",
						"title": "创建人",
			                "width": "150px",
						"lockable": false,
						"locked": false
		            },
		            {
		            	title : '操作',
		            	width: "650px",
		            	template : formatterStatusField
		            }
			   ],
			   dataBound: function(e) {
			   }
	    }).data('kendoTreeList');
	  
	  
  });
  

Array.prototype.insert = function (index, item) {  
   this.splice(index, 0, item);
}; 
  var command = {
		 funcs : function(o,t){
			 
			if(t in command){
				
				var dataItem = treeList.dataItem(o);
				
				command[t].click(dataItem);
			}
			
		},
		add : {
			text : '增加下级',
	    	name : 'add',
	    	click : function(dataItem){
	    		addRow(dataItem.id,dataItem.type);
	    	}
		},
		addSort : {
			text : '增加分类',
	    	name : 'addSort',
	    	click : function(dataItem){
	    		//addRow(dataItem.parentId);
	    	}
		},
		addSameLevel : {
			text : '增加同级',
	    	name : 'addSameLevel',
	    	click : function(dataItem){
	    		addRow(dataItem.parentId,dataItem.type);
	    	}
		},
		update : {
			text : '修改',
			name : 'update',
			click : function(dataItem){
				
			   // var link = "<%=request.getContextPath()%>/mx/form/component/property/edit?id="+dataItem.id;
			    
			   // editRow(link);
			    
			    var params = {
		    		kendoComponent : '${formComponent.kendoComponent}',
		    		componentId : '${formComponent.id}',
		    		type : dataItem.type,
		    		id : dataItem.id
		    	};
		    	
		        editRow('<%=request.getContextPath()%>/mx/form/component/property/edit?' + $.param(params));
			}
		},
		'delete' : {
			text: "删除", 
	        name: "del",
	        click: function deleteRow(dataItem) {
	        	 if(confirm("属性删除后不能恢复，确定删除吗？")){
	      		   var link = "<%=request.getContextPath()%>/mx/form/component/property/delete?id="+dataItem.id;
	      		   jQuery.ajax({
	      		   type: "POST",
	      		   url: link,
	      		   dataType:  'json',
	      		   error: function(data){
	      			   alert('服务器处理错误！');
	      		   },
	      		   success: function(data){
	      			   if(data != null && data.message != null){
	      				   alert(data.message);
	      			   } else {
	      				   alert('操作成功完成！');
	      			   }
	      			   window.location.reload();
	      		   }
	      	      });
	      	    }
	         }
		},
		up : {
          text : '↑',
          name : 'up',
          click : function (dataItem){
          	 upsidedown(dataItem,'up');
          }
		},
		down : {
          text : '↓',
          name : 'down',
          click : function (dataItem){
          	 upsidedown(dataItem,'down');
          }
		}
  };
  
  function StringBuffer(){
	  
	  this.collection = new Array();
	  
	  this.append = function(str){
		  this.collection[this.collection.length] = str;
		  return this;
	  };
	  
	  this.toString = function(split){
		  return this.collection.join(split ? split : '');
	  };
	  
	  this.appendFormat = function(){
		  if(arguments.length == 0) 
			  return this;
		  var str = arguments[0];
		  for(var i = 1; i < arguments.length; i++)  
			  str = str.replace(new RegExp("\\{" + (i - 1) + "\\}","g"), arguments[i]);  
		  return this.append(str);
	  };
	  
  }
  
  function formatterStatusField(dataItem){
	  
		var buttons = ['update','delete','up','down','addSameLevel'];
		
		if(dataItem.isSort == 0){
			buttons.push('add');
			
			if(dataItem.root){
				buttons = ['add'];
			}
			
			//buttons.push('addSort');
		}
		
		var SB = new StringBuffer();
		
		jQuery.each(buttons,function(i,t){
			var handler = command[t];
			if(handler){
				SB.appendFormat("<button type=\"button\" class=\"k-button\" style=\"width:80px\" onclick=\"{0}\">{1}</button>","command.funcs(this,'"+ t +"')",handler.text);
			}
		});
		
		return SB.toString();
	}
  
    function upsidedown(d,t){
    	var params = {
			componentId : '${formComponent.id}',
			type : t,
			id : d.id,
			parentId : d.parentId
    	};
    	
    	$.ajax({
    		url : '<%=request.getContextPath()%>/mx/form/component/property/upsidedown',
    		type : 'post',
    		data : params,
    		async : false,
    		success:function(){
		    	treeList.dataSource.read();
    		}
    	});
    	
    }
  

    function addRow(parentId,type){
    	
    	var params = {
    		kendoComponent : '${formComponent.kendoComponent}',
    		componentId : '${formComponent.id}',
    		parentId : parentId,
    		type : type
    	};
    	
        editRow('<%=request.getContextPath()%>/mx/form/component/property/edit?' + $.param(params));
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑表单组件属性信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['620px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}
	
	function importProperties(){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "引入属性",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['920px', (jQuery(window).height() - 240) +'px'],
                        iframe: {src: "<%=request.getContextPath()%>/mx/form/component/property/importProperties?kendoComponent=${formComponent.kendoComponent}&componentId=${formComponent.id}"
					}
				});
	}
	
	function sortBy(){

		if(treeList.select().length == 0){
			alert('请选择!');
		}else{
			
			 var data = treeList.dataSource.data();
			 var dataSource = [{id:-9999,title:'根节点'}];
			 if(data){
				 $.each(data,function(i,item){
					 if(item.isSort == 0){
						 dataSource.push(item);
					 }
				 });
			 }
			
			$("#sortList").data("kendoDropDownList").setDataSource(dataSource);
			
			$("#sortDiv").data("kendoWindow").center();
			$("#sortDiv").data("kendoWindow").open();
			
		}
	}
	
	function sortListFunc(){
		
		var dataItem;
		var ids = new Array();
		var SB = new StringBuffer();
		$.each(treeList.select(),function(i,row){
			
			dataItem = treeList.dataItem(row);
			
			ids.push(dataItem.id);
			
			SB.append(dataItem.title);
			
		});
		
		var parentItem = $("#sortList").data("kendoDropDownList").dataItem();
		
		if(confirm("确定把<" + SB.toString(',') + ">移动到:" + parentItem.title + "吗？")){
			$.ajax({
				url : '<%=request.getContextPath()%>/mx/form/component/property/sortListFunc',
				data : {ids : ids.join(','),parentId : parentItem.id},
				async : false,
				success:function(){
					treeList.dataSource.read();
					$("#sortDiv").data("kendoWindow").close();
				}
			});
		}else{
			
		}
		
		
	}
	
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt="${formComponent.kendoComponent}组件属性列表">&nbsp;
			${formComponent.name}组件属性列表
		</div>
		<br>
		<div id="grid"></div>
		<div id="sortDiv" style="display: none;" >
			请选择分类 : <input id="sortList" />
			<button class='k-button' onclick='sortListFunc();' >确定</button>
			<script>
				$("#sortDiv").kendoWindow({
						title : '分类',
						animation : false,
						modal : true,
						height : '100',
						width : '320'
				  });
				
				$("#sortList").kendoDropDownList({
				    dataTextField: "title",
				    dataValueField: "id"
				});
			</script>
		</div>
	</div>
</body>
</html>