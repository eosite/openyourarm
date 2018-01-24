<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="renderer" content="ie-comp">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看表格</title>
<%@include file="/WEB-INF/views/inc/init_ext3.jsp" %>
<script type="text/javascript">
Ext.onReady(function() {
	var grid,viewport;
	var store = new Ext.data.Store({
        url: '${pageContext.request.contextPath}/rs/isdp/cellFillForm/tableList?databaseCode=${param.databaseCode}&indexId=${param.indexId}&fileDotFileId=${param.fileID}&name=${param.name}',
        reader: new Ext.data.JsonReader({
            idProperty:'id',
            root:'rows',
            totalProperty:'total',
            fields: [
                {name: 'indexId'},
                {name: 'id'},
                {name: 'id_base64'},
                {name: 'fileDotFileId_base64'},
    			{name: 'name'}
            ]
        }),
        listeners:{
        	load:function(){
        		grid.getSelectionModel().selectFirstRow();
        	},
        	reload:function(){
        		grid.getSelectionModel().selectFirstRow();
        	}
        }
    });
	store.load();
	
	grid = new Ext.grid.GridPanel({
		title:"",
		renderTo:"grid",
		width:150,
		stripeRows:true,
		hideHeaders:false,
        store: store,
        columns: [
            { header: "id",dataIndex: 'id',hidden:true },  
	        { header: "表格名称",width:350,dataIndex: 'name',menuDisabled:true,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	        	return  value + " 第"+(rowIndex+1)+"页";
	        }}
        ],
		sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners:{
				rowselect:function(selectionModel, rowIndex, record){
					document.getElementById("cellFrame").src = "${pageContext.request.contextPath}/mx/isdp/wbs/cellweb?type=1&databaseCode=${param.databaseCode}&fileID="+record.data.id_base64;
				}
			}
			
		}),
		viewConfig: {
			forceFit: true
		}
    });
	
	viewport = new Ext.Viewport({
    	layout: 'border',
    	items:[{
    		id:"west-panel",
    		region: 'west',
            title: '表格列表',
            collapsible: true,
            width:300,
            height:window.screen.height,
            split: true,
            margins: '0 0 0 0',
            layout: 'fit',
            items: grid
    	},{
    		id:'center-panel',
            region: 'center',
            title: '',
            collapsible: false,
            collapsed:false,//是否折叠
            split: true,
            margins: '0 3 0 0',
            layout: 'fit',
            items:Ext.getDom("cellFrame")
        }]
    });
});
</script>
<body>
	<div id="grid" title="表格"></div>
	<div>
	<iframe id="cellFrame" width="100%" height="100%" style="overflow:hidden;border:0px;margin:0px;left:0px;top:0px;"></iframe>
	</div>
</body>
</html>