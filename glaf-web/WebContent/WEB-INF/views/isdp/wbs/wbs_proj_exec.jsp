<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目信息管理</title>
<%@include file="/WEB-INF/views/inc/init_ext3.jsp" %>
<script type="text/javascript">
	var tree,node,expandAllFlag = false;
	Ext.onReady(function() {
		Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath }/scripts/extjs3/resources/images/default/s.gif'; 
		
		//检查TAB页
		var tabs = new Ext.TabPanel({
	        applyTo: 'my-tabs',
	        activeTab: 0,
	        autoTabs: true,
	        deferredRender: false,
	        border: false,
            tabPosition: 'top'
	    });
		
		//工具条
		var databaseStore = new Ext.data.ArrayStore({
			url:'${pageContext.request.contextPath}/rs/isdp/global/databaseArray',
        	fields:['code','text'],
        	listeners:{
        		load:function(){
        			Ext.getCmp("databaseCombobox").setValue("${param.databaseCode}");
        		}
        	}
		});
		databaseStore.load();
		
		var toolbar = new Ext.Toolbar({
			height: 30,
			items:[
			    "项目",
			    new Ext.form.ComboBox({
			    	id:'databaseCombobox',
			    	name:'databaseCombobox',
			        width:150,
			        typeAhead: true,
			        store:databaseStore,
			        emptyText:'请选择数据源',
			        valueField:'code',
			        displayField:'text',
			        forceSelection:false,
			        mode: 'local',
			        editable:false,
			        triggerAction:'all',
			        listeners:{
			        	select:function(combo, record, index){
			        		document.location = "${pageContext.request.contextPath}/mx/isdp/wbs?method=projexec&databaseCode="+combo.value;
			        	}
			        }
			    }),{
			    	text:"取消/恢复计划",
			    	iconCls:"",
			    	listeners:{
			    		click:rc
			    	}
			    },{
			    	text:"修改",
		    		iconCls:""
			    },
			    new Ext.form.Checkbox({
			    	boxLabel:"显示WBS信息",
			    	checked:true,
			    	listeners:{
			    		check:function(obj,ischecked){
			    			var w = Ext.getCmp("south-panel");
		    				ischecked ? w.expand() : w.collapse();
			    		}
			    	}
			    }),{
			    	text:"传设计值表",
		    		iconCls:""
			    },{
			    	text:"查询",
			    	iconCls:""
			    },{
			    	text:"删除",
			    	iconCls:""
			    }
			]
		});
		
	    tree = new Ext.ux.tree.TreeGrid({
	    	el:'treegrid',
            width: 1200,
            height:800,
            frame:false,
            columnLines:true,
            title: '执行WBS计划',
            lines:true,
            renderTo: Ext.getBody(),
            enableDD: false,
            useArrows:false,
            enableSort:false,
            headersDisabled:true,
            tbar:toolbar,
            columns:[{ 
	            header : '名称', 
	            dataIndex : 'indexName', 
	            width : 650
	        },{ 
	            header : '状态', 
	            dataIndex : 'finishString', 
	            width : 70
	        },{ 
	            header : '开始时间', 
	            dataIndex : 'bdate',
	            width : 80
	        },{ 
	            header : '结束时间', 
	            dataIndex : 'edate',
	            width : 80
	        },{ 
	            header : '要填表格', 
	            dataIndex : 'cell1',
	            width : 80
	        },{ 
	            header : '未填表格', 
	            dataIndex : 'cell3',
	            width : 80
	        },{ 
	            header : '已填表格', 
	            dataIndex : 'cell2',
	            width : 80
	        },{ 
	            header : '执行', 
	            dataIndex : 'strButtonStar',
	            width : 80,
	            tpl: new Ext.XTemplate('{strButtonStar:this.formatter}', {
	                formatter: function(value) {
	                    return '<a href="#" onclick="execStar()">'+value+'<a>';
	                }
	            })
	        }],  
            loader: new Ext.tree.TreeLoader({  
                dataUrl:"${pageContext.request.contextPath}/rs/isdp/treepInfo/getTreepInfoJSON?databaseCode=${param.databaseCode}",  
                uiProviders:{
                    'col': Ext.tree.ColumnNodeUI  
                }
            })
        });
	    tree.render();
	    
	    tree.on("beforeload", function(node) {
        	tree.loader.dataUrl = "${pageContext.request.contextPath}/rs/isdp/treepInfo/getTreepInfoJSON?databaseCode=${param.databaseCode}&indexId="+node.attributes.indexId;
	    });
	    
	    new Ext.Viewport({
	    	el:"treegrid",
	    	layout: 'border',
	    	items:[{
	    		region: 'center',
                title: '',
                collapsible: false,
                split: false,
                margins: '0 3 0 0',
                layout: 'fit',
                items: tree
	    	},{
	    		id:'south-panel',
                region: 'south',
                title: 'WBS详细信息',
                collapsible: true,
                collapsed:false,//是否折叠
                split: true,
                height:250,
                margins: '0 3 0 0',
                layout: 'fit',
                items:tabs
            }]
	    });
	    
        Ext.get("treegrid").onmousedown=function(e){
        	if(e.button == 2){
        		if(!expandAllFlag && confirm("要展开下级所有节点，展开过程会比较慢，确定吗？")){
            		expandAllFlag = true;
            		tree.expandAll();
            	} else if(expandAllFlag){
            		expandAllFlag = false;
            		tree.collapseAll();
            	}
        	}
        	
        }
	});
	
	//取消及恢复WBS计划
	function rc(){
		node = tree.getSelectionModel().getSelectedNode();
		
		var actionURL = "${pageContext.request.contextPath}/rs/isdp/wbs";
		if(node.attributes.finishInt==-1){
			//状态取消，执行恢复
			actionURL = actionURL + "/recoverProject?databaseCode=${param.databaseCode}";
			ajaxAction(actionURL);
		}else{
			//其他状态，执行取消
			Ext.MessageBox.confirm('Confirm', '取消工程：'+node.attributes.indexName+'及所属节点的计划，确定吗?', function(btn){
				if(btn=='no'){
					return;
				} 
				
				Ext.MessageBox.prompt('输入密码', '输入密码(ok):', function(btn,text){
					if(btn=='cancel'){
						return;
					} else if(text.toLowerCase()=='ok'){
						actionURL = actionURL + "/cancelProject?databaseCode=${param.databaseCode}";
						ajaxAction(actionURL);
						return;
					}else{
						Ext.MessageBox.alert('密码错误，密码是:ok');
						return;
					}
				});
			});
		}
	}
	
	//启动及终止WBS计划
	function execStar(){
		var execflag = true;
		node = tree.getSelectionModel().getSelectedNode();
		
		var actionURL = "${pageContext.request.contextPath}/rs/isdp/wbs";
		
		if(node.attributes.finishInt==1){
			//状态实施，执行终止，更新treepinfo状态
			actionURL = actionURL + "/stopProject?databaseCode=${param.databaseCode}";
		}else if(node.attributes.finishInt==0){
			//状态计划，执行启动，增加数据
			actionURL = actionURL + "/startProject?databaseCode=${param.databaseCode}";
		}else if(node.attributes.finishInt==-1){
			//状态取消，不做任何操作
			execflag = false;
		}else if(node.attributes.finishInt==-2){
			//状态终止,执行启动，更新treepinfo状态
			actionURL = actionURL + "/startProject?databaseCode=${param.databaseCode}";
		}
		
		if(execflag){
			ajaxAction(actionURL);
		}
	}
	
	function ajaxAction(action){
		Ext.Ajax.request({
			method: "POST",
		    url: action,
		    dataType:  'json',
		    params: {indexId:node.attributes.indexId},
		    failure: function(data){
		    	Ext.MessageBox.alert('服务器处理错误！');
		    },
		    success: function(response,options){
			    if(response.status==200){
			    	Ext.MessageBox.alert("成功","操作成功");
			    }
		    }
		 });
	}
</script>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div id="treegrid"></div>
		<div id="my-tabs">
			<div  class="x-tab" title="基本信息"></div>
			<div  class="x-tab" title="节点属性表"></div>
			<div  class="x-tab" title="检测项目"></div>
			<div  class="x-tab" title="执行情况"></div>
			<div  class="x-tab" title="填写表格"></div>
			<div  class="x-tab" title="填写数据"></div>
			<div  class="x-tab" title="相关规范"></div>
			<div  class="x-tab" title="相关资料"></div>
		</div>
	</div>
</body>
</html>