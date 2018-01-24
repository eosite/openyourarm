/**
 * 
 */
//初始化布局
window.MSPointerEvent = null;
window.PointerEvent = null;
var mainHeight = $(window).height();
var mainWidth = $(window).width();
$('#vertical').height(mainHeight);
$("#vertical").kendoSplitter({
	orientation : "vertical",
	panes : [ {
		collapsed : false,
		resizable : false,
		scrollable : false,
		collapsible : true,
		size : "40px"
	}, {
		collapsed : false,
		scrollable : false
	} ]
});
var splitter = $("#horizontal").kendoSplitter({
	panes : [ {
		collapsed : false,
		collapsible : true,
		collapsedSize : "0px",
		max : "300px",
		resizable : false,
		size : "200px",
		scrollable : true
	}, {
		scrollable : false
	} ]
});
//新建选项卡
var tabStrip = $("#tabstrip").kendoTabStrip({
	activate : onActivate,
	select : onSelect
}).data("kendoTabStrip");
var selectTab;
//选项卡选中方法
function onSelect(e) {
	selectTab = e.contentElement.id;
}
//选项卡选中渲染完成后执行方法
function onActivate(e) {
	if (selectTab == "tabstrip-1") {
		//bindPageClickEvent($("#" + selectTab + " iframe"), 0);
	}
	//高度自适应
	setTimeout("resizeTab()", 200);
}
//选项卡内容高度自适应
function resizeTab() {
	$("#" + selectTab).height($("#horizontal").height() - 32);
	$("#" + selectTab).css("padding", "0px");
}
$(window).resize(function() {
	mainHeight = $(window).height();
	$("#vertical").height(mainHeight);
	$("#content").height(mainHeight - 40);
});

 var setting = {
            view: {
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            check: {
                enable: false,
				chkStyle: "radio",
				radioType:"all"
            },
            data: {
                simpleData: {
                    enable: true,
					idKey: "subSysId",
					pIdKey: "parentId_",
					rootPId: 0
                }
            },
            edit: {
                enable: false,
				removeTitle:"删除子系统",
				renameTitle:"修改子系统"
            },
			callback:{
				onClick: zTreeOnClick,
				onRename:zTreeOnRename,
				beforeRemove:zTreeBeforeRemove
			}
        };

        $(document).ready(function(){	
           $.fn.zTree.initZtree($("#systemTree"),url,setting);
		   //选中第一个节点并触发点击事件
		   $("#systemTree").find("li:first>a:first").click();
		   //默认设置第一个选项卡选中
           tabStrip.select(0);
		   setTimeout("resizeTab()", 3000);
        });
		var url=contextPath+"/rs/modeling/subSystemDef/subSystems?sysId="+sysId;
        $.fn.extend($.fn.zTree,{
		  initZtree:function (container,url,setting){
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {	
					//rdata.push({categoryId:"0",parentId:"0",name:"XXX子系统",open:true,chkDisabled:true});
					$.fn.zTree.init(container,setting, rdata);
				},
				error : function() {
					console.log("获取系统结构失败");
				}

		   }
		  );
		}});
        var newCount = 1;
		//新增节点事件
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增子系统' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                
                //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"模板分类" + (newCount++)});
				return zTreeOnAdd(treeId,treeNode);
            });
        };
		//新增节点后台处理
		function zTreeOnAdd(treeId,treeNode){
			 var pId=treeNode.subSysId;
			 var pTreeId=treeNode.treeID;
			 var level=treeNode.level+1;
             var url=contextPath+"/rs/modeling/subSystemDef/add";
			 var name="新建子系统" + (newCount++);
			 var params={"pId":pId,"pTreeId":pTreeId,"level":level,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					if(rdata!=null&&rdata.result==1){
						var zTree = $.fn.zTree.getZTreeObj(treeId);
						zTree.addNodes(treeNode, {id:rdata.id, pId:pId,name:name});
						result= true;
					}else{
						result= false;
					}
					
				},
				error : function() {
					console.log("新增子系统失败");
					result= false;
				}
		   });
		   return result;
		}
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
		//ztree树节点重命名事件
		function zTreeOnRename(event, treeId, treeNode, isCancel){
			 var subSysId=treeNode.subSysId;
			 var name=treeNode.name;
			 var url=contextPath+"/rs/modeling/subSystemDef/rename";
			 var params={"subSysId":subSysId,"name":name};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("重命名失败");
					result= false;
				}

		   }
		  );
		  return result;
		}
		//ztree树节点移除事件
		function zTreeBeforeRemove(treeId, treeNode){
			 var subSysId=treeNode.subSysId;
			 var url=contextPath+"/rs/modeling/subSystemDef/delete";
			 var params={"subSysId":subSysId};
			 var result=false;
			 $.ajax({
				url : url,
				type : "post",
				async : false,
				data:params,
				contentType : "application/x-www-form-urlencoded",
				dataType : "json",
				success : function(rdata) {
					result= true;
				},
				error : function() {
					console.log("删除子系统失败");
					result= false;
				}
		   }
		  );
		  return result;
		}
		//ztree树节点点击事件
		function zTreeOnClick(event, treeId, treeNode){
			console.log(treeNode.currProcModelId);
			if(treeNode.currProcModelId!=undefined&&treeNode.currProcModelId!=null){
				//打开流程定义页面
				var url = "/workflow/modeler.html?modelId="
										+ treeNode.currProcModelId+"&definedType=systemplan";
				openPage(url, "规划流程");
				//打开系统功能结构图
				url = contextPath+"/mx/system/model/systemstructure?sysId="+treeNode.sysId+"&treeId="
										+ treeNode.treeId;
				$('#systemstructure').attr("src", url);
				
			}else if((treeNode.eleType!=undefined&&treeNode.eleType!=null&&treeNode.eleType=='subsystem')||treeNode.eleType==undefined||treeNode.eleType==''){
				//提示是否创建子系统流程
				if(confirm('当前节点未创建子系统流程，是否新建？')){
					addSubSystemProcess(treeNode);
				}else{
					return;
				}
			}
		}
		function addSubSystemProcess(node){
			//获取当前选中的节点
			var modelName;
			var modelKey;
			var subSysId;
			var systemProcDefId;
			if (node) {
				modelName = node.name + "子系统流程";
				modelKey ="subSystem_" + node.subSysId;
				subSysId=node.subSysId;
				sysId=node.sysId;
				systemProcDefId=node.systemProcDefId;
			}
			$.ajax({
				url : contextPath+"/rs/modeling/process/createProcess",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					sysId:sysId,
					subSysId : subSysId,
					modelName : modelName,
					modelKey : modelKey,
					systemProcDefId:systemProcDefId
				},
				success : function(data) {
					if (data) {
						if (data.result) {
							if (data.result == 1) {
								//跳转到编辑页面
								var url = "/workflow/modeler.html?modelId="
										+ data.modelId+"&definedType=systemplan";
								openPage(url, "规划流程");
							} else {
								alert("未找到子系统流程！");
							}
						}
					}
				},
				error : function() {
					alert("新建子系统流程失败！");
				}

			});
		}
		//跳转到页面
		splitter = splitter.data("kendoSplitter");
		function openPage(url, name) {
			//验证url是否使用相对路径，如果使用相对路径则补全
			if (url.indexOf("http") < 0) {
				url = webPath + url;
			}
			$('#process').attr("src", url);
			tabStrip.select(0);
			$("#tab-name1 .k-link").text(name);
			//splitter.ajaxRequest("#designArea", url);
		}
		//树控件数据绑定
		function bindWorkFlowTreeData(triggerFlag) {
			var selectedNodeId;
			if (treeObj) {
				if (selectedNode) {
					selectedNodeId = selectedNode.id;
				}
			}
			$.ajax({
				url : url,
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					if (data) {
						var nodes = eval(data);
						$.fn.zTree.init($("#systemTree"), setting, nodes);
						treeObj = $.fn.zTree.getZTreeObj("systemTree");
					}
				},
				error : function() {

				}

			});

		}
		//保存规划流程定义
		function saveSystemPlanActivitiSource(modelId) {
			var zTree = $.fn.zTree.getZTreeObj("systemTree");
			var selectedNodes=zTree.getSelectedNodes();
			var subSysId=selectedNodes[0].subSysId;
			var systemProcDefId=selectedNodes[0].systemProcDefId;
				$.ajax({
					url : contextPath+"/rs/modeling/subSystemDef/saveSystemPlan",
					type : "post",
					async : false,
					dataType : "json",
					data : {
						modelId : modelId,
						subSysId: subSysId,
						systemProcDefId:systemProcDefId
					},
					success : function(data) {
						if (data) {
							if (data.result) {
								if (data.result == 1) {
									console.log("保存系统规划流程定义成功");
									refreshSubSystemTreeData();
								} else if (data.result == 0) {
									console.log("保存系统规划流程定义失败");
								} else if (data.result == -1) {
									console.log("保存系统规划流程定义异常");
								}
							}
						}
					},
					error : function() {

					}

				});
		}
		/**
		*刷新子系统树
		*/
		function refreshSubSystemTreeData(){
		    var zTree = $.fn.zTree.getZTreeObj("systemTree");
			var selectedNodes=zTree.getSelectedNodes();
			  $.ajax({
				url : url,
				type : "post",
				async : false,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {	
					//rdata.push({categoryId:"0",parentId:"0",name:"XXX子系统",open:true,chkDisabled:true});
					$.fn.zTree.init($("#systemTree"),setting, rdata);
					zTree= $.fn.zTree.getZTreeObj("systemTree");
					var selectNode=zTree.getNodeByTId(selectedNodes[0].tId);
					zTree.selectNode(selectNode);
					zTree.expandNode(selectNode,true);
				},
				error : function() {
					console.log("获取系统结构失败");
				}

		   }
		  );
		}