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
<style type="text/css">
	.row_red{background: #F00}
	.row_yellow{background: #FF0}
</style>
<script type="text/javascript">
	var treegrid,tablegrid,tablecheckStore,filegrid,filecheckStore,node,viewport,expandAllFlag = false;
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
		
		//treegrid工具条
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
		
		var treegridTbar = new Ext.Toolbar({
			height: 30,
			items:[
			    "项目",
			    new Ext.form.ComboBox({
			    	id:'databaseCombobox',
			    	name:'databaseCombobox',
			        width:150,
			        typeAhead: true,
			        store:databaseStore,
			        emptyText:'请选择项目标段',
			        valueField:'code',
			        displayField:'text',
			        forceSelection:false,
			        mode: 'local',
			        editable:false,
			        triggerAction:'all',
			        listeners:{
			        	select:function(combo, record, index){
			        		document.location = "${pageContext.request.contextPath}/mx/isdp/wbs?method=projcheck&databaseCode="+combo.value;
			        	}
			        }
			    }),{
			    	text:"工程定位",
			    	iconCls:""
			    },/* {
			    	text:"打印表格",
		    		iconCls:""
			    },{
			    	text:"打印模板",
		    		iconCls:""
			    },{
			    	text:"产生文件",
			    	iconCls:""
			    },{
			    	text:"上传资料",
			    	iconCls:""
			    },{
			    	text:"补报审",
			    	iconCls:""
			    }, */
			    new Ext.form.Checkbox({
			    	boxLabel:"显示详细信息",
			    	checked:true,
			    	listeners:{
			    		check:function(obj,ischecked){
		    				var w = Ext.getCmp("south-panel");
		    				ischecked ? w.expand() : w.collapse();
			    		}
			    	}
			    })
			]
		});
		
		//表格检查数据源
		tablecheckStore = new Ext.data.Store({
	        url: '${pageContext.request.contextPath}/rs/isdp/wbs/tableCheck?databaseCode=${param.databaseCode}',
	        reader: new Ext.data.JsonReader({
	            idProperty:'id',
	            root:'rows',
	            totalProperty:'total',
	            fields: [
                    {name: 'indexId'},
	                {name: 'id'},
	                {name: 'useId'},
	                {name: 'useId_base64'},
	                {name: 'defId'},
	                {name: 'defId_base64'},
	                {name: 'intFinish'},
        			{name: 'intPage0'},
        			{name: 'intPage1'},
        			{name: 'intPage2'},
        			{name: 'fileDotNum'},
        			{name: 'name'},
        			{name: 'name_enc'}
	            ]
	        }),
	        listeners:{
	        	load:function(){
	        		tablegrid.getSelectionModel().selectFirstRow();
	        	},
	        	reload:function(){
	        		tablegrid.getSelectionModel().selectFirstRow();
	        	}
	        }
	    });
		//表格检查工具条
		var tablegridtoolbar = new Ext.Toolbar({
			height: 30,
			items:[
			    "表格",
			    {
			    	text:"完成状态",
			    	iconCls:""
			    },{
			    	text:"查看表格",
		    		iconCls:"",
		    		handler:function(item,e){
		    			if(tablegrid.getSelectionModel().hasSelection()){
		    				//选中表格后，异步判断是否有多张表
		    				//如果有多张表，则先弹出表格菜单再点表格打开
		    				//如果只有一张表，则直接打开表格
		    				var record = tablegrid.getSelectionModel().getSelected();
		    				
		    				if(record.data.intFinish==0){
		    					//缺少表格时，不做打开操作
		    					alert("未填写表格");
		    					return;
		    				}
		    				readCell(record.data.indexId,record.data.defId_base64,record.data.name_enc);
		    			}
		    		}
			    }/* ,{
			    	text:"打印选定的表格",
		    		iconCls:""
			    },{
			    	text:"打印选定的模板",
			    	iconCls:""
			    } */
			]
		});
		
		var gridheight=200,gridwidth=740;
		//表格检查grid
		tablegrid = new Ext.grid.GridPanel({
			title:"",
			renderTo:"tablegrid",
			width:gridwidth,
			height:gridheight,
			stripeRows:true,
			hideHeaders:false,
	        store: tablecheckStore,
	        tbar:tablegridtoolbar,
	        columns: [
	            { header: "id",dataIndex: 'id',hidden:true },  
	            { header: "状态",dataIndex: 'intFinish',menuDisabled:true, width:80,renderer:function(value){
	            	if(value==0){
	        			return "缺少";
	        		}else if(value==1){
	        			return "完成";
	        		}else if(value==2){
	        			return "不合格";
	        		}else{
	        			return "";
	        		}
	            }},  
		        { header: '应填',width:80, dataIndex: 'intPage0',menuDisabled:true},  
		        { header: "已填",width:80,dataIndex: 'intPage1',menuDisabled:true },
		        { header: "模板编号",width:150,dataIndex: 'fileDotNum',menuDisabled:true },
		        { header: "名称",width:350,dataIndex: 'name',menuDisabled:true }
	        ],
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			viewConfig: {
				forceFit: true,
				getRowClass:rowStyleFormatter
			}
	    });
		
		//检测项目数据源
		/* tablecheckStore = new Ext.data.Store({
	        url: '${pageContext.request.contextPath}/rs/isdp/wbs/checkItem',
	        reader: new Ext.data.JsonReader({
	            idProperty:'id',
	            root:'rows',
	            totalProperty:'total',
	            fields: [
                    {name: 'indexId'},
	                {name: 'id'},
	                {name: 'useId'},
	                {name: 'useId_enc'},
	                {name: 'defId'},
	                {name: 'defId_enc'},
	                {name: 'intFinish'},
        			{name: 'intPage0'},
        			{name: 'intPage1'},
        			{name: 'intPage2'},
        			{name: 'fileDotNum'},
        			{name: 'name'}
	            ]
	        }),
	        listeners:{
	        	load:function(){
	        		tablegrid.getSelectionModel().selectFirstRow();
	        	},
	        	reload:function(){
	        		tablegrid.getSelectionModel().selectFirstRow();
	        	}
	        }
	    }); */
		
		//检测项目grid
		/* new Ext.grid.GridPanel({
			title:"",
			renderTo:"tablegrid",
			width:gridwidth,
			height:gridheight,
			stripeRows:true,
			hideHeaders:false,
	        store: tablecheckStore,
	        tbar:new Ext.Toolbar({height:30,items:["检测项目"]}),
	        columns: [
	            { header: "id",dataIndex: 'id',hidden:true },  
	            { header: "状态",dataIndex: 'intFinish', width:80,renderer:function(value){
	            	if(value==0){
	        			return "缺少";
	        		}else if(value==1){
	        			return "完成";
	        		}else if(value==2){
	        			return "不合格";
	        		}else{
	        			return "";
	        		}
	            }},  
		        { header: '应填',width:80, dataIndex: 'intPage0'},  
		        { header: "已填",width:80,dataIndex: 'intPage1' },
		        { header: "模板编号",width:150,dataIndex: 'fileDotNum' },
		        { header: "名称",width:350,dataIndex: 'name' }
	        ],
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			viewConfig: {
				forceFit: true,
				getRowClass:rowStyleFormatter
			}
	    }); */
		
		//文件检查数据源
		filecheckStore = new Ext.data.Store({
	        url: '${pageContext.request.contextPath}/rs/isdp/wbs/fileCheck?databaseCode=${param.databaseCode}',
	        reader: new Ext.data.JsonReader({
	            idProperty:'id',
	            root:'rows',
	            totalProperty:'total',
	            fields: [
	                {name: 'id'},
	                {name: 'intFinish'},
        			{name: 'intPage0'},
        			{name: 'name'}
	            ]
	        })
	    });
		
		//文件检查grid
		filegrid = new Ext.grid.GridPanel({
			title:"",
			renderTo:"filegrid",
			width:gridwidth,
			height:gridheight,
			stripeRows:true,
			hideHeaders:false,
	        store: filecheckStore,
	        columns: [
	            { header: "id",dataIndex: 'id',hidden:true },  
	            { header: "状态",dataIndex: 'intFinish',menuDisabled:true, width:80,renderer:function(value){
	            	if(value==0){
	        			return "缺少";
	        		}else if(value==1){
	        			return "完成";
	        		}else if(value==2){
	        			return "不合格";
	        		}else{
	        			return "";
	        		}
	            }},  
		        { header: '页数',width:80,dataIndex: 'intPage0',menuDisabled:true},  
		        { header: "名称",width:580,dataIndex: 'name',menuDisabled:true }
	        ],
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			viewConfig: {
				forceFit: true,
				getRowClass:rowStyleFormatter
			}
	    });
		
		//treegrid树表格
	    treegrid = new Ext.ux.tree.TreeGrid({
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
            stripeRows:true,
            headersDisabled:true,
            tbar:treegridTbar,
            columns:[
            	{header : '名称',dataIndex : 'indexName',width : 600},
            	{header : '完成',dataIndex : 'intcellfinishIcon',align:'center',width : 60},
            	{header : '开工时间',dataIndex : 'bdate',width : 90},
            	{header : '完成时间',dataIndex : 'edate',width : 90},
            	{header : '应填',dataIndex : 'cell1',width : 60},
            	{header : '已填',dataIndex : 'cell2',width : 60},
            	{header : '缺少',dataIndex : 'cell3',width : 60},
	            {header : '应有',dataIndex : 'intpfile1',width : 60},
	            {header : '已有',dataIndex : 'intpfile2',width : 60},
	        	{header : '缺少',dataIndex : 'intpfile3',width : 60}
	        ],  
            loader: new Ext.tree.TreeLoader({  
                dataUrl:"${pageContext.request.contextPath}/rs/isdp/treepInfo/getTreepInfoJSON?databaseCode=${param.databaseCode}",  
                uiProviders:{
                    'col': Ext.tree.ColumnNodeUI  
                }
            }),
            listeners:{
            	click:function(node,e){
            		tabs.setActiveTab(0);
            		//更新表格检查数据
            		tablegrid.store.load({params:{indexId:node.attributes.indexId}});
            		//更新文件检查数据
            		filegrid.store.load({params:{indexId:node.attributes.indexId}});
            	}
            }
        });
	    treegrid.render();
	    //异步加载treegrid数据
	    treegrid.on("beforeload", function(node) {
        	treegrid.loader.dataUrl = "${pageContext.request.contextPath}/rs/isdp/treepInfo/getTreepInfoJSON?databaseCode=${param.databaseCode}&indexId="+node.attributes.indexId;
	    });
	    
	    //viewport layout布局
	    viewport = new Ext.Viewport({
	    	layout: 'border',
	    	items:[{
	    		region: 'center',
                title: '',
                collapsible: false,
                split: false,
                margins: '0 3 0 0',
                layout: 'fit',
                items: treegrid
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
	    
	    //增加treegrid树表格右键事件
        Ext.get("treegrid").on("contextmenu",function(e){
        	e.stopEvent();
       		if(!expandAllFlag && confirm("要展开下级所有节点，展开过程会比较慢，确定吗？")){
           		expandAllFlag = true;
           		treegrid.expandAll();
           	} else if(expandAllFlag){
           		expandAllFlag = false;
           		treegrid.collapseAll();
           	}
        });
	});
	
	//读取华表数据
	function readCell(indexId,fileID,name){
		var url = "${pageContext.request.contextPath}/mx/isdp/wbs/showTable?databaseCode=${param.databaseCode}&indexId="+indexId+"&fileID="+fileID+"&name="+name;
		var height = screen.availHeight-100;
		var width = screen.availWidth-10;
	    var title = "查看表格";
		window.open(url);
	}
	
	//行样式
	function rowStyleFormatter(record,rowIndex,rowParams,store){
		if(record.data.intFinish==0){
			return "row_red";
		}else if(record.data.intFinish==1){
			//return "background-color:#FFF;";
		}else if(record.data.intFinish==2){
			return "row_yellow";
		}else{
			return "";
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
			<div id="tablegrid" class="x-tab" title="表格检查"></div>
			<div id="filegrid" class="x-tab" title="文件检查"></div>
		</div>
	</div>
</body>
</html>