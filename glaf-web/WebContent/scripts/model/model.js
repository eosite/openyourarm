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
				collapsible : false,
				size : "30px"
			}, {
				collapsed : false,
				scrollable : false
			} ]
		});
		$(document).ready(function() {
			$("#grid").kendoGrid({
				"columnMenu" : true,
				"dataSource" :gridDataSource,
				"reorderable" : true,
				"filterable" : false,
				"sortable" : true,
				"selectable":"row",
				"pageable" : {
					"refresh" : true,
					"pageSizes" : [ 10, 15, 30, 50, 100 ],
					"buttonCount" : 10
				},
				"change": function(e) {
					var row = this.select();
			        var data = this.dataItem(row);
					controlToolBarStatus(data);
				},
				 toolbar: [
				    { template:"<div id=\"toolbar\" style=\"border:0px;text-align: left;\"></div>"}
				  ],
				"columns" : [ {
			        "field": "rowNumber",
			        "title": "序号",
			        "template": "<span class='row-number'></span>",
			        "width" : "80px"
			    },{
					"field" : "sysId",
					hidden: true
				},{
					"field" : "sysCode",
					"title" : "系统编号",
					"width" : "200px"
				},{
					"field" : "sysName",
					"title" : "系统名称",
					"width" : "350px"
				},  {
					"field" : "updateBy",
					"title" : "更新人",
					"width" : "100px"
				} , {
					"field" : "updateTime_datetime",
					"title" : "更新时间",
					"width" : "150px"
				} ,
				{
					"title" : "操作",
					"command": [
						{
			               "text": "<img src=\""+contextPath+"/images/application_cascade.png\" style=\"vertical-align:middle;margin-right:5px;\"/>规划",
			               "name": "editSys",
			               "click": editModel,
						    "width" : "100px",
							},{
			               "text": "<img src=\""+contextPath+"/images/application_delete.png\" style=\"vertical-align:middle;margin-right:5px;\"/>删除",
			               "name": "deleteBt",
			               "click": openDeleteModelDialog,
						    "width" : "100px",
							}]}
										],
				"dataBound": function() {
				    var rows = this.items();
		            var page=this.pager.page()-1;
		            var pagesize = this.pager.pageSize();
		            $(rows).each(function () {
		                var index = $(this).index() + 1+page*pagesize;
		                var rowLabel = $(this).find(".row-number");
		                $(rowLabel).html(index);
				    });
		            },
				"scrollable" : {},
				"resizable" : true,
				"groupable" : false
			});
			 $("#toolbar").kendoToolBar({
		        items: [{ type: "button",id: "create",text: "新增",imageUrl:contextPath+"/images/application_add.png",click : createModel},
				{ type: "separator" },
				{
					type : "button",
					id : "deploy",
					text : "发布",
					enable : false,
					imageUrl : contextPath + "/images/msn_messenger.png",
					click : openDeployModelDialog
				}, {
					type : "separator"
				}
		        ]
		    });
		});
		var gridDataSource = new kendo.data.DataSource(
				{
					schema : {
						total : "total",
						data : "rows",
						parse : function(response) {
							
							 return response;
						}
					},
					transport : {
						parameterMap : function(data) {
							return JSON.stringify(data);
						},
						read : {
							url : contextPath+"/rs/modeling/systemDef/data",
							dataType : "json",
							contentType: "application/json",
							type : "POST",
							data : {}
						}
					},		
                    pageSize : 10,
					serverPaging : true
				});
				//查询按钮
		$("#searchBt").kendoButton({
			imageUrl : contextPath+"/images/search.png"
		});
		var searchBt = $("#searchBt").data("kendoButton");
		  //查询按钮事件绑定
		  searchBt.bind("click",function(e) {
		    var grid=$("#grid").data("kendoGrid");
            var params={};
			 //获取查询条件
			var filterObjs=$("#filterCondition input");
			$.each(filterObjs,function(i,filterObj){
			var $filterObj=$(filterObj);
			if($filterObj.val()!=null&&$filterObj.val()!="")
			 params[$filterObj.attr("name")]=$filterObj.val();
			});
			 grid.dataSource.options.transport.read.data={data:params};
			 console.log(grid.dataSource);
		     grid.dataSource.read();
		     
		});

		//新增系统定义
		function createModel() {
			  var url = contextPath+"/mx/system/model/add";
			  window.top.openFancybox(window.event,url,"系统规划");
		}
		//修改系统定义
		function editModel(e) {
		  //获取选中行
		  var data = this.dataItem(jQuery(e.currentTarget).closest("tr"));
    	  openEditModelDialog(data);	 			
		}
		function openEditModelDialog(data){
			var url=contextPath+"/mx/system/model/modeling?sysId="+data.sysId;
		    //popWindow(url,'系统规划');
			var openWin=window;
			window.top.openFancybox(openWin,url,"系统规划");
		}
		function popWindow(url, Name, cause) {
			if (cause + "" == "undefined" || cause == null) {
				var w = screen.availWidth-10;
				var h = screen.availHeight-50;
				cause = "width=" + w + ",height=" + h + ",left=0,top=0,menubar=no,toolbar=no,scrollbars=yes,status=0,resizable=1";
			}
			window.open(url, Name, cause);
		}
		//弹出删除系统窗口
		function openDeleteModelDialog(e) {
			var data = this.dataItem(jQuery(e.currentTarget).closest("tr"));
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deleteModel(data);
					}
				};
				kendo.confirm("确认删除系统\"" + data.sysName + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}
		}
		//删除系统方法
		function deleteModel(data) {
	        if (data.id) {
			$.ajax({
				url : contextPath+"/rs/modeling/systemDef/deleteSystem",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					sysId : data.id
				},
				success : function(data) {
					if (data) {
						alert(data.message);
						refreshGrid();
					}
				},
				error : function() {
					alert("删除异常！");
				}

			});
	        }
		}
		//弹出发布系统窗口
		function openDeployModelDialog() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
				var okTemplate = {
					text : "&nbsp&nbsp确定&nbsp&nbsp",
					callback : function(kendoConfirm) {
						kendoConfirm.close();
						deployModel();
					}
				};
				kendo.confirm("确认发布系统\"" + data.sysName + "\"？", {
					title : "<font size=\"2\">系统提醒</font>",
					width : "250px",
					height : "150px"
				}, okTemplate);
			}

		}
		//部署模型方法
		function deployModel() {
			var grid=$("#grid").data("kendoGrid"); 
			var row = grid.select();
	        var data = grid.dataItem(row);
			if (data.id) {
			$.ajax({
				url : contextPath+"/rs/modeling/systemDef/publishSystemDef",
				type : "post",
				async : false,
				dataType : "json",
				data : {
					sysId : data.id,
				},
				success : function(data) {
					if (data) {
						if (data.result) {
							if (data.result == 1) {
								refreshGrid();
								alert("部署成功！");
							} else {
								alert("部署失败！");
							}
						}
					}
				},
				error : function() {
					alert("部署异常！");
				}

			});
			}
		}
		
		//控制菜单按钮状态
		function controlToolBarStatus(node) {
			var toolbar = $("#toolbar").data("kendoToolBar");
			if (toolbar) {
				toolbar.enable("#create");
				toolbar.enable("#deploy", false);
					if (!node.id || node.id== '') {
						toolbar.enable("#create");
						toolbar.enable("#deploy", false);
					}
					else {
						toolbar.enable("#create");
						toolbar.enable("#edit");
						if (node.publisher==null || node.publisher == '') {
							toolbar.enable("#deploy");
						}
					}
			}
		}
		//刷新grid
		function refreshGrid(){
			var grid = $("#grid").data(
			"kendoGrid");
			grid.dataSource.read();
		}