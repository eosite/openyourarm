var compRenderMethod={
      render:function(id,componentId){
	    if(compRenderMethod[componentId])
	    compRenderMethod[componentId].call(this,id);
	 }
		 ,2050:function(id){
			   $('#'+id).find('.control-label').attr('for',id+'_ctrlid');
$('#'+id).find('.input-group-btn>button').attr('data-select2-open',id+'_ctrlid');
$('#'+id).find('select').attr('id',id+'_ctrlid');
$('#'+id).find('select.select2').select2({width:"auto"});
		 }
		 ,1450:function(id){
			   $('#'+id).treelist({
    keys : {
        idKey : 'EmployeeId',
        parentKey : ''
    },
    datas : [
                {
                "EmployeeId": 1,
                "FirstName": "Dary",
                "LastName": "Sweeney",
                "City": "Castelvecchio Calvisio",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 2,
                "FirstName": "Guy",
                "LastName": "Wooten",
                "City": "Acoz",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 3,
                "FirstName": "Priscilla",
                "LastName": "Frank",
                "City": "Wells",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 4,
                "FirstName": "Ursula",
                "LastName": "Holmes",
                "City": "Sarre",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 5,
                "FirstName": "Anika",
                "LastName": "Vega",
                "City": "Pavone del Mella",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 6,
                "FirstName": "Moses",
                "LastName": "Duncan",
                "City": "Bressoux",
                "Phone": "0329238934"
                }
            ],
    columns: [
        {
            field: 'FirstName',
            title: 'FirstName',
            style: 'width: 120px;'
        },
        {
            field: 'LastName',
            title: 'LastName',
            style: 'width: 100px;'
        },
        {
            field: 'City',
            title: 'City',
            style: 'width: 100px;'
        },
        {
            field: 'Phone',
            title: 'Phone',
            style: 'width: 100px;'
        }
    ]
});
		 }
		 ,3550:function(id){
			    $("#"+id).find("input").TouchSpin({
            initval: 0
        });
		 }
		 ,3650:function(id){
			   $('#'+id).find('.input-group-btn>button').attr('data-select2-open',id+'_ctrlid');
$('#'+id).find('select').attr('id',id+'_ctrlid');
$('#'+id).find('select.select2-multiple').select2({width:"auto"});
		 }
		 ,1650:function(id){
			   $('#'+id).tabs({
        minHeight: 200,
	tabs: [
		{
			title: 'New Tab 1',
			content: ''
		},{
			title: 'New Tab 2',
			content: ''
		}
	],
        onLoad: function(){initContainer();disableSortable();},
        onSelect:function(){
					enableSortable();
					disableSortable();
				}
});
$('#'+id).find("a[data-toggle='tab']").find("span").attr("contenteditable","true");
		 }
		 ,4250:function(id){
			   $('#'+id).grid({
    datas : [
                {
                "EmployeeId": 1,
                "FirstName": "Dary",
                "LastName": "Sweeney",
                "City": "Castelvecchio Calvisio",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 2,
                "FirstName": "Guy",
                "LastName": "Wooten",
                "City": "Acoz",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 3,
                "FirstName": "Priscilla",
                "LastName": "Frank",
                "City": "Wells",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 4,
                "FirstName": "Ursula",
                "LastName": "Holmes",
                "City": "Sarre",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 5,
                "FirstName": "Anika",
                "LastName": "Vega",
                "City": "Pavone del Mella",
                "Phone": "0329238934"
                },
                {
                "EmployeeId": 6,
                "FirstName": "Moses",
                "LastName": "Duncan",
                "City": "Bressoux",
                "Phone": "0329238934"
                }
            ],
    columns: [
        {
            field: 'FirstName',
            title: 'FirstName',
            style: 'width: 120px;'
        },
        {
            field: 'LastName',
            title: 'LastName',
            style: 'width: 100px;'
        },
        {
            field: 'City',
            title: 'City',
            style: 'width: 100px;'
        },
        {
            field: 'Phone',
            title: 'Phone',
            style: 'width: 100px;'
        }
    ]
});
		 }
		 ,4350:function(id){
			   $('#'+id).iframegantt({asDemo:true});
		 }
		 ,2250:function(id){
			   $('#'+id).icheckradio({
	asDemo:true
});
		 }
		 ,2150:function(id){
			   $('#'+id).icheckbox({
	asDemo:true
});
		 }
		 ,2650:function(id){
			   App.initSlimScroll($('#'+id+' .portlet-body>.scroller'));
		 }
		 ,5450:function(id){
			   $('#'+id).find("input").tagsinput();
		 }
		 ,6150:function(id){
			   $('#'+id).gridList({
	datas : [{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	},{
		"id":"sss",
	}],
});
		 }
		 ,6450:function(id){
			   $('#'+id).svgEditor({asDemo:true});
		 }
		 ,5650:function(id){
			   $("#"+id).find("input.switch_input").bootstrapSwitch();
		 }
		 ,33:function(id){
			   $('#'+id).prepend("<button id='ztree_" + id + "_ul_add_next' type='button'>" + '增加子级' + "</button>");
$('#'+id).prepend("<button id='ztree_" + id + "_ul_add' type='button'>" + '增加同级' + "</button>");

$('#'+id).find("ul.ztree").attr("id",id+"_ul");

glafZtree.init(id+'_ul', {
 	'data': {
 		'simpleData': {
 			'enable': true,
 			'idKey': 'id',
 			'pIdKey': 'parentId',
 		}
 	},
 	'check': {
 		'enable': true,
 		'chkStyle': 'checkbox',
 		'chkboxType': {
 			"Y": "s",
 			"N": "s"
 		},
 	},
 	'callback': {
 		'beforeRename': function(treeId, treeNode, newName, isCancel) {
 			return glafZtree.beforeRename(treeId, treeNode, newName, isCancel, 'ce189d853374478c999d41d9386b111f');
 		},
 		'beforeRemove': function(treeId, treeNode) {
 			return glafZtree.beforeRemove(treeId, treeNode, 'ce189d853374478c999d41d9386b111f');
 		},
 		'beforeDrop': function(treeId, treeNodes, targetNode, moveType, isCopy) {
 			return glafZtree.beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy, 'ce189d853374478c999d41d9386b111f')
 		},
 		'onClick': function(event, treeId, treeNode, clickFlag) {},
 		'beforeAsync': function(treeId, treeNode) {
 			var $this = $("#" + treeId);
 			var expandingNum = $this.data("expandingNum") || 0;
 			if ($this.data("loadding") && !$this.data("expanding")) {
 				return false;
 			};
 			expandingNum++;
 			$this.data("expandingNum", expandingNum);
 			$this.data("loadding", true);
 			return true;
 		},
 		'onAsyncSuccess': function(event, treeId, treeNode, msg) {
 			if (msg && msg.length == 2 && true) {
 				if (treeNode) {
 					treeNode.isParent = false;
 					$.fn.zTree.getZTreeObj(treeId).updateNode(treeNode);
 				}
 			}
 			var $this = $("#" + treeId),
 				params = $this.data("params");
 			var expandingNum = $this.data("expandingNum") | 0;
 			if (params) {
 				ztreeFunc.linkageControl(treeId, params);
 				$this.data("params", null);
 			}
 			var expandChilds = $this.data("expandChilds");
 			var treeDatas = null;
 			if (treeNode == null) {
 				treeDatas = $.fn.zTree.getZTreeObj(treeId).getNodes();
 			} else {
 				treeDatas = treeNode.children;
 			}
 			if ((treeNode == null && expandChilds > 1) || (treeNode && (treeNode.level + 2) < expandChilds)) {
 				if (treeDatas && treeDatas.length) {
 					$this.data("expanding", true);
 					$.each(treeDatas, function(i, item) {
 						$this.data("isloaded", false);
 						if (i > 5) {
 							return false;
 						}
 						$.fn.zTree.getZTreeObj(treeId).expandNode(item, true, false, true);
 					});
 					if (!true) {
 						$this.data("isloaded", true);
 					}
 					$this.data("expanding", false);
 				} else {
 					$this.data("isloaded", true);
 				}
 			} else {
 				$this.data("isloaded", true);
 			}
 			$this.data("loadding", false);
 			expandingNum--;
 			$this.data("expandingNum", expandingNum);
 			var ztreeObj = $.fn.zTree.getZTreeObj('ztree_1482802459548');
 			if ('true' == 'true' && treeNode && treeNode.checked) {
 				if (ztreeObj.setting && ztreeObj.setting.callback.onCheck && $.isFunction(ztreeObj.setting.callback.onCheck)) {
 					ztreeObj.setting.callback.onCheck();
 				}
 			}
 			if (ztreeObj && !expandingNum) {
 				if (ztreeObj.setting && ztreeObj.setting.callback.onLoadSuccess && $.isFunction(ztreeObj.setting.callback.onLoadSuccess)) {
 					ztreeObj.setting.callback.onLoadSuccess();
 				}
 			}
 		},
 	},
 	'expandChilds': 0,
 	'edit': {
 		'enable': false,
 		'removeTitle': '删除',
 		'showRenameBtn': true,
 		'renameTitle': '重命名',
 		'drag': {
 			'isCopy': true,
 			'isMove': true,
 		}
 	},
 	'opts': {
 		'editPanleAutoClose': false,
 		'ruleId': 'test'+id,
 	},
},[
			{ 'id':1, 'parentId':0, 'name':"父节点 1", 'open':true},
			{ 'id':11, 'parentId':1, 'name':"叶子节点 1-1"},
			{ 'id':12, 'parentId':1, 'name':"叶子节点 1-2"},
			{ 'id':13, 'parentId':1, 'name':"叶子节点 1-3"},
			{ 'id':2, 'parentId':0, 'name':"父节点 2", 'open':true},
			{ 'id':21, 'parentId':2, 'name':"叶子节点 2-1"},
			{ 'id':22, 'parentId':2, 'name':"叶子节点 2-2"},
			{ 'id':23, 'parentId':2, 'name':"叶子节点 2-3"},
			{ 'id':3, 'parentId':0, 'name':"父节点 3", 'open':true},
			{ 'id':31, 'parentId':3, 'name':"叶子节点 3-1"},
			{ 'id':32, 'parentId':3, 'name':"叶子节点 3-2"},
			{ 'id':33, 'parentId':3, 'name':"叶子节点 3-3"}
		]);
		 }
		 ,9250:function(id){
			   $("#"+id).addClass("inlineContent");
$("#"+id).definedPanelExt({datas : [{
			id : "1",
			children : [{
				id : "2",
			},{
				id : "2",
			}]
		},{
			id : "1",
			children : [{
				id : "2",
			},{
				id : "2",
			},{
				id : "2",
			},{
				id : "2",
			},{
				id : "2",
			}]
		}]});
		 }
		 ,12850:function(id){
			   $("#"+id).muitab({
    tabdatas: [
		{
			title: 'New Tab 1',
active:true,
			content: ''
		},{
			title: 'New Tab 2',
			content: ''
		}
	],
})
		 }
};