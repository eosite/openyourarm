        initMenu(false,"kendo");
		function initMenu(aysnc,type) {
			$.ajax({
				url : contextPath+"/rs/form/component/read?categoryLike="+type,
				type : "post",
				async : aysnc,
				contentType : "application/json",
				dataType : "json",
				success : function(rdata) {
					if (rdata) {
						var jsonData = eval(rdata);
						var jsonDataTree = transData(jsonData, 'id',
								'parentId', 'children');
						jsonDataTree = {
							"children" : jsonDataTree
						};
						if ("children" in jsonDataTree) {
							$(".sidebar-menu .header").siblings().remove();
							createMenu(jsonDataTree.children,
									$(".sidebar-menu"), 0);
						}
					}

				},
				error : function() {
					console.log("获取控件列表数据失败");
				}

			});

		}
        
		function createMenu(nodes, pnode, level) {
			var l = level;
			if (pnode[0].localName && pnode[0].localName == "ul") {
				var lidom, adom;
				$
						.each(
								nodes,
								function(i, node) {
									l = level;
									lidom = $("<li></li>");
									if (l == 0 && i == 0) {
										lidom.addClass("active");
									}
									lidom.addClass("treeview");
									adom = $("<a data_role='"+node.dataRole+"'></a>");
									//获取适用范围（哪些UI包含此组件）
									var scope=node.category;
									if(scope)
									{
									  adom.attr("scope",scope);
									}
									//判断页面元素是否属于布局元素
									if (("" + node.dataRole).indexOf("col-md") > -1) {
										adom.addClass("layout");
									} else {
										adom.addClass("elem");
									}
									adom.attr("href", "#");
									if (node.url && node.url != "") {
										adom.attr("onclick", "openUrl("
												+ node.id + ",\"" + node.name
												+ "\",\"" + node.url + "\",\""
												+ node.showmenu + "\")");
									}
									if (l == 0) {
										adom
												.append("<i class=\"fa fa-edit\"></i>");
									} else {
										adom
												.append("<i class=\"fa fa-hand-o-right\"></i>");
									}
									adom.append("<span>" + node.name
											+ "</span>");
									if ("children" in node) {
										adom
												.append("<i class=\"fa fa-angle-left pull-right\"></i>");
									}
									lidom.append(adom);
									if ("children" in node) {
										var uldom = $("<ul class=\"treeview-menu\"></ul>");
										createMenu(node.children, uldom, ++l);
									}
									lidom.append(uldom);
									pnode.append(lidom);
								});
			}
		}
		//重新计算iframe高度
		function settingFrameHeight() {
			var contentTh = $(".content-wrapper").height();
			var contentHh = $(".content-header").height();
			//var conentFh=$(".main-footer").height();
			$("#dsDiv").height(contentTh - contentHh - 100);
		}
		$(window).resize(function() {
			//settingFrameHeight();
			$("body").css("min-height", $(window).height() - 50);
			$("#dsDiv").css("min-height", $(window).height() - 80)
		});
		//从缓存加载最近一次定义
		function restoreData(){
             if (supportstorage()&&$(".designer").html().trim()=="") {
				layouthistory = JSON.parse(localStorage.getItem("layoutdata"));
				if (!layouthistory) return false;
				window.designerHtml = layouthistory.list[layouthistory.count-1];
				if (window.designerHtml) $(".designer").html(window.designerHtml);
			}
		}
		//为元素删除按钮绑定事件
		function removeElement() {
			$(".designer").delegate(".remove", "click", function(e) {
				e.preventDefault();
				$(this).parent().remove();
				if (!$(".designer .lyrow").length > 0) {
					clearDesigner()
				}
				clashCompute();
			})
		}
		//清空设计区
		function clearDesigner() {
			$(".designer").empty();
			$(".ui-list").empty();
			layouthistory = null;
			if (supportstorage())
				localStorage.removeItem("layoutdata");
		}
		/**
		 * [getInjector description]
		 * @return {[type]} [description]
		 */
		function getInjector() {
			return angular.injector(['ng','mainApp']);
		}
		//清空属性配置栏
		function clearConfigurations() {
			$(".designer .configuration").empty();
			//清除选择边框
			$(".selected_border").removeClass("selected_border");
			//清空选择对象
			mainApp.config(function(globalProviderProvider){
				globalProviderProvider._focusObj = {id:"",bgColor:""};
			});
		}
		//获取页面对象数据
		function getDesignerObjs() {
			var $injector = getInjector();
			var providerData = $injector.get('globalProvider');
			return providerData
		}
		//获取页面JSON数据
		function getDesignerJson() {
			return JSON.stringify(getDesignerObjs());
		}
		function updateDesignerJson() {
			var data = getDesignerObjs();
			var newComList = {};
			var newDataList = {};
			var newDataIdList = [];
			for(i=0;i<data.dataIdList.length;i++){
				var id = data.dataIdList[i];
				if(document.getElementById(id)!=null){
					newDataList[id] = data.dataList[id];
					newDataIdList.push(id);
				}

				var pid = id.substring(0, id.lastIndexOf('_'));
				if(document.getElementById(pid)!=null && newComList[pid]==undefined){
					newComList[pid] = data.comList[pid];
				}
			}

			data.comList = newComList;
			data.dataList = newDataList;
			data.dataIdList = newDataIdList;
			mainApp.config(function(globalProviderProvider){
				globalProviderProvider._comList=newComList;
				globalProviderProvider._dataList=newDataList;
				globalProviderProvider._dataIdList=newDataIdList;
			});
		}
		//创建属性配置
		function createConfs(id,confs) {
			var ngctrlId = id.substring(0,id.length-4)+"cb";
			var html = ("<span id=\""+ngctrlId+"\" class=\"btn-group\" ng-controller=\"BgColorController\">");
			html += ("<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\">背景颜色<span class=\"caret\"></span></a>");
			html += ("<ul class=\"dropdown-menu\">");
			html += ("<li ng-repeat=\"color in data\">");
			html += ("<i style=\"display:inline-block;color:{{color.name==focusObj.bgColor?'#000000':'#eeeeee'}};\" class=\"fa fa-check\"></i>");
			html += ("<a style=\"display:inline-block\" class=\"{{color.name}}\" ng-click=\"selectBgColor(color.name)\">&nbsp;</a>");
			html += ("</li>");
			html += ("</ul>");
			html += ("</span>");
			$('#'+id).append(html);


			var $injector = getInjector();
			$injector.invoke(function($rootScope, $compile, $document) {
				//$compile(document.getElementById(id))($rootScope);
				var scope = angular.element(document.getElementById(id)).scope();
				scope.$apply();
			});
			return;
			for (i = 0; i < confs.length; i++) {
				//$('#'+id).append(confs[i].html);//
				if(confs[i].type=="dropdown-toggle"){
					$('#'+id).append("<span id=\""+confs[i].ngctrlId+"\" class=\"btn-group\" ng-controller=\""+confs[i].ngController+"\">");
					$('#'+id).append("<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\">"+confs[i].name+"<span class=\"caret\"></span></a>");
					$('#'+id).append("<ul class=\"dropdown-menu\">");
					$('#'+id).append("<li ng-repeat=\"color in data\">");
					$('#'+id).append("<i style=\"display:inline-block;color:{{color.name==focusObj.bgColor?'#000000':'#eeeeee'}};\" class=\"fa fa-check\"></i>");
					$('#'+id).append("<a style=\"display:inline-block\" class=\"{{color.name}}\" ng-click=\"selectBgColor(color.name)\">&nbsp;</a>");
					$('#'+id).append("</li>");
					$('#'+id).append("</ul>");
					$('#'+id).append("</span>");
				}
			}
			var $injector = getInjector();
			$injector.invoke(function($rootScope, $compile, $document) {
				//$compile(document.getElementById(id))($rootScope);
				var scope = angular.element(document.getElementById(id)).scope();
				scope.$apply();
			});
			
		}
		//重新组装属性配置栏
		function rebuildConfigurations() {
			clearConfigurations();
			var conf_json = {};//dynamic data
			$(".designer .configuration").each(function(index, el) {
				var confs = conf_json[$(el).attr("id")];
				if(confs==undefined){
					//return;
				}
				//createConfs($(el).attr("id"),confs);
				var id = $(el).attr("id");
				var pid = id.substring(0,id.length-5);
				var html = "";
				var ngctrlId = "";
				if(id.match(/^list_mt/)!=null){
					//列表风格
					ngctrlId = pid+"_listmt";
					html = ("<span id=\""+ngctrlId+"\" class=\"btn-group listmt-set\" ng-controller=\"ListSimpleController\">");
					html += ("<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\">列表风格<span class=\"caret\"></span></a>");
					html += ("<ul class=\"dropdown-menu\">");
					html += ("<li ng-repeat=\"item in data\">");
					html += ("<i style=\"display:inline-block\" class=\"fa fa-check\"></i>");
					html += ("<a style=\"display:inline-block\" ng-click=\"selectListType\">{{item.name_cn}}</a>");
					html += ("</li>");
					html += ("</ul>");
					html += ("</span>");
					$(el).append(html);
					$('#'+ngctrlId).find(".fa.fa-check").attr("ng-class","{conf_icon_uncheck:item.name!=comList['"+pid+"'].listType}");
					$('#'+ngctrlId).find("[ng-click=selectListType]").attr("ng-click","selectListType('"+pid+"',item.name)");
					//设置行背景
					ngctrlId = pid+"_roweffect";
					html = ("<span id=\""+ngctrlId+"\" class=\"btn-group roweffect-set\" ng-controller=\"ListSimpleController\">");
					html += ("<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\">设置行背景<span class=\"caret\"></span></a>");
					html += ("<ul class=\"dropdown-menu\">");
					html += ("<li ng-repeat=\"item in visibleData\">");
					html += ("<i style=\"display:inline-block\" class=\"fa fa-check\"></i>");
					html += ("<a style=\"display:inline-block\" ng-click=\"setRowEffect\">{{item.name}}</a>");
					html += ("</li>");
					html += ("</ul>");
					html += ("</span>");
					$(el).append(html);
					$('#'+ngctrlId).find(".fa.fa-check").attr("ng-class","{conf_icon_uncheck:item.value!=comList['"+pid+"'].row_effect}");
					$('#'+ngctrlId).find("[ng-click=setRowEffect]").attr("ng-click","setRowEffect('"+pid+"',item.value)");
					//设置列分组
					ngctrlId = pid+"_listgroup";
					html = ("<span id=\""+ngctrlId+"\" class=\"btn-group listgroup-set\" ng-controller=\"ListSimpleController\">");
					html += ("<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\">设置列分组<span class=\"caret\"></span></a>");
					html += ("<ul class=\"dropdown-menu\">");
					html += ("<li ng-repeat=\"item in visibleData\">");
					html += ("<i style=\"display:inline-block\" class=\"fa fa-check\"></i>");
					html += ("<a style=\"display:inline-block\" ng-click=\"setListGroup\">{{item.name}}</a>");
					html += ("</li>");
					html += ("</ul>");
					html += ("</span>");
					$(el).append(html);
					$('#'+ngctrlId).find(".fa.fa-check").attr("ng-class","{conf_icon_uncheck:item.value!=comList['"+pid+"'].list_group}");
					$('#'+ngctrlId).find("[ng-click=setListGroup]").attr("ng-click","setListGroup('"+pid+"',item.value)");
				}

				ngctrlId = pid+"_cb";
				html = ("<span id=\""+ngctrlId+"\" class=\"btn-group\" ng-controller=\"BgColorController\">");
				html += ("<a class=\"btn btn-mini dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:;\">背景颜色<span class=\"caret\"></span></a>");
				html += ("<ul class=\"dropdown-menu\">");
				html += ("<li ng-repeat=\"color in data\">");
				html += ("<i style=\"display:inline-block;color:{{color.name==focusObj.bgColor?'#000000':'#eeeeee'}};\" class=\"fa fa-check\"></i>");
				html += ("<a style=\"display:inline-block\" class=\"{{color.name}}\" ng-click=\"selectBgColor(color.name)\">&nbsp;</a>");
				html += ("</li>");
				html += ("</ul>");
				html += ("</span>");
				$(el).append(html);

			});
		}
		//重新加载属性配置栏数据
		function reloadConfigurations() {
			var $injector = getInjector();
			var providerData = $injector.get('globalProvider');
			$(".designer .configuration").each(function(index, el) {
				var id = $(el).attr("id");
				var pid = id.substring(0,id.length-5);
				$injector.invoke(function($rootScope, $compile, $document) {
					var scope = angular.element(document.getElementById(pid)).scope();
					scope.$destroy();
					$compile(document.getElementById(pid+'_cb'))($rootScope);
					$compile(document.getElementById(pid+'_listmt'))($rootScope);
					$compile(document.getElementById(pid+'_roweffect'))($rootScope);
					$compile(document.getElementById(pid+'_listgroup'))($rootScope);
					$compile(document.getElementById(pid))($rootScope);
					//scope.$apply();
				});
			});
		}
		$(function() {
			//settingFrameHeight();
			$("body").css("min-height", $(window).height() - 50);
			$("#dsDiv").css("min-height", $(window).height() - 80);
			restoreData();
			initdraggable();
			initContainer();			
			//预览按钮绑定事件
			$("#previewBt").click(function() {
				$("body").removeClass("edit");
				$("body").addClass("devpreview preview");
				//修改左侧背景色 
				//修改左侧隐藏
				$(".main-sidebar").css("display", "none");
				$(".wrapper").css("background-color", "#ecf0f5");
				$(".main-sidebar").css("background-color", "#ecf0f5");
				$(".mymenu button").removeClass("active");
				$(".content").css("margin-left", "-230px");
				//取消编辑属性
				$("[contenteditable]").attr("contenteditable","false");
				$(this).addClass("active");

				//test
				var injector = getInjector();
				var listService = injector.get("listService");
				var providerData = injector.get("globalProvider");
				$.get('http://127.0.0.1:8080/glaf/scripts/designer/data.json', function(data) {
					//console.log(providerData.dataIdList)
					//for(i=0;i<providerData.dataIdList.length;i++){
						//var id = providerData.dataIdList[i];
						//var pid = id.substring(0,id.lastIndexOf('_'));
						//console.log(pid);
					//}
					$(data).each(function(index, obj) {
						listService.reLoadList(obj)
					});
				});
				return false
			});
			//编辑按钮绑定事件
			$("#editBt").click(function() {
				$("body").removeClass("devpreview preview");
				$("body").addClass("edit");
				$(".main-sidebar").attr("style", "");
				$(".wrapper").attr("style", "");
				$(".main-sidebar").attr("style", "");
				$(".mymenu button").removeClass("active");
				$(".content").css("margin-left", "auto");
				//取消编辑属性
				$("[contenteditable]").attr("contenteditable","true");
				$(this).addClass("active");
				return false
			});
			$("#saveBt").click(function(e){
				e.preventDefault();
				handleSaveLayout();
				if($(".designer").html().trim()==''){
					  $.alert(1,"设计页面内容为空");	
					  return;
				}
				//rebuildConfigurations();
				//updateDesignerJson();
				convertLayoutHtml();
				$.ajax({
					url : contextPath+"/mx/page/designer/save",
					type : "post",
					async : true,
					data:{id:id,pId:pId,pageLayout:encodeURI($("#pageLayout").text()),pageDesigner:encodeURI($(".designer").html()),designerJson:getDesignerJson()},
					contentType : "application/x-www-form-urlencoded",
					dataType : "json",
					success : function(rdata) {
						if (rdata.result==1) {
							$.alert(0,"保存成功!");
							reloadConfigurations();
						}
						else{
							$.alert(1,"保存失败!");
						}
					},
					error : function() {
						console.log("保存失败");
					}

				});
				
			});
			//清空按钮绑定事件
			$("#emptyBt").click(function() {
				$(".designer").empty();
				$(".ui-list").empty();
				layouthistory = null;
				if (supportstorage())
					localStorage.removeItem("layoutdata");
			});
			//导出按钮绑定事件
			$("#exportBt")
					.click(
							function() {
								if($(".designer").html().trim()==''){
								  $.alert(1,"设计页面内容为空");
								  return;
								}
								convertLayoutHtml();
								dForm.action="download";
								dForm.target=operFrame;
								dForm.submit();
							});
			        
			removeElement();
			//初始化适用UI范围
			clashCompute();
			//定时自动保存 
			setInterval(function() {
						handleSaveLayout()
					}, timerSave);
			//响应式模拟
			$(".reponsive-block li>a").click(function() {

				$(".reponsive-block li>a").removeClass("active");
				var designerWidth=$(this).attr("data-width");
				var currentWidth=$(".designer").width();
				//PC
				if(designerWidth=='auto'){
					$(".designer").width("auto")
				}
				else if( ($(window).width()-230)>=designerWidth){
					$(".designer").width(designerWidth)
				}
				$(this).addClass("active");
				
			});
			//设置页面为编辑模式
			//document.designMode = "on";
		});
		//转换页面布局HTML
		function convertLayoutHtml(){
			var layoutHtml = $(".designer").html();
			var $layoutHtml = $(layoutHtml);
			//去掉设计元素
			$layoutHtml
					.find(
							".preview, .configuration, .drag, .remove")
					.remove();
			$layoutHtml.find(".lyrow").addClass(
					"removeClean");
			$layoutHtml.find(".box-element").addClass(
					"removeClean");
			$layoutHtml
					.find(
							".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean")
					.each(function() {
						cleanHtml(this)
					});
			$layoutHtml
					.find(
							".lyrow .lyrow .lyrow .lyrow .removeClean")
					.each(function() {
						cleanHtml(this)
					});
			$layoutHtml.find(
					".lyrow .lyrow .lyrow .removeClean")
					.each(function() {
						cleanHtml(this)
					});
			$layoutHtml.find(".lyrow .lyrow .removeClean")
					.each(function() {
						cleanHtml(this)
					});
			$layoutHtml.find(".lyrow .removeClean").each(
					function() {
						cleanHtml(this)
					});
			$layoutHtml.find(".removeClean").each(
					function() {
						cleanHtml(this)
					});
			$layoutHtml.find(".removeClean").remove();
			$layoutHtml.find(".column").removeClass(
					"ui-sortable");
			$layoutHtml.find(".row")
					.removeClass("clearfix").children()
					.removeClass("column ui-droppable");
			var outPutHtml = $("<div class=\"container\"></div>");
			outPutHtml.append($layoutHtml);
			//$layoutHtml.find(".row").each(
					//function(i, item) {
					//	outPutHtml.append(item);
					//});
			$("#pageLayout").text(outPutHtml.html());
			$("#pageDesigner").text($(".designer").html());
		}
		//布局控件拖动对象
		function initdraggable(){
		$(".layout").draggable(
				{
					connectToSortable : ".designer",
					cursor : "move",
					helper : function(event) {
						//获取当前元素类型
						var elementType = $(this).attr("data_role");
						var scope=$(this).attr("scope");
						var targetElem;
						if ($.template[elementType]
								&& $.template[elementType] != '') {
							targetElem = $($.template[elementType]);
						} else if ($('#' + elementType)
								&& $('#' + elementType).html()) {
							targetElem = $($('#' + elementType).html());
						} else {
							targetElem = $("<img></img>");
							targetElem.attr("src",
									contextPath+"/images/component/"
											+ elementType + ".png");
							targetElem.css("vertical-align", "middle");
						}
						var dtStr = new Date().getMilliseconds();
						targetElem.attr("id", elementType + "_" + dtStr);
						targetElem.attr("data-role", elementType);
						targetElem.attr("scope", scope);
						targetElem.attr("crtltype", "kendo");
						return targetElem;
					},
					start: function(e,t) {
						if (!startdrag) stopsave++;
						startdrag = 1;
					},
					drag : function(e, t) {
						t.helper.width(400);
					},
					stop: function(e,t) {
						initContainer();
						var moveObj=$(t.item);
						changeElemStyle(moveObj);
						if(stopsave>0) stopsave--;
						startdrag = 0;
						clashCompute();
					}
		});
		//非布局控件拖动对象
		$(".elem").draggable(
				{
					connectToSortable : ".column",//启用此属性helper需设置为clone，否则droppable drop会执行两次；后面对droppable drop方法进行了处理
					cursor : "move",
					helper : function(event) {
						//获取当前元素类型
						var elementType = $(this).attr("data_role");
						var scope=$(this).attr("scope");
						//获取元素模板对象
						var elementTemplate = $($('#elementTemplate')
								.html());
						var targetElem;
						if ($('#' + elementType)
								&& $('#' + elementType).html()) {
							targetElem = $($('#' + elementType).html());
						} 
						else if ($.template[elementType]
								&& $.template[elementType] != '') {
							targetElem = $($.template[elementType]);
						} else {
							targetElem = $("<img class='img-responsive'></img>");
							targetElem.attr("src",
									contextPath+"/images/component/"
											+ elementType + ".png");
							targetElem.css("vertical-align", "middle");
						}
						var dtStr = new Date().getMilliseconds();
						var id = elementType + "_" + dtStr;
						targetElem.attr("id", id);
						targetElem.attr("data-role", elementType);
						targetElem.attr("scope", scope);
						targetElem.attr("crtltype", "kendo");

						if(elementType.match(/^list_mt/)!=null){
							/** 
							 * public
							 */
							targetElem.attr("ui-role", "ui-"+elementType);
							targetElem.attr("ng-controller", "ElementController");
							elementTemplate.find(".configuration").attr("id",elementType + "_" + dtStr+"_"+"conf");
							//背景颜色
							elementTemplate.find(".configuration .bgcolor-set").attr("id",elementType + "_" + dtStr+"_"+"cb");

							/** 
							 * private
							 */
							//list_mt风格
							elementTemplate.find(".configuration .listmt-set").attr("id",elementType + "_" + dtStr+"_"+"listmt");
							elementTemplate.find(".configuration .listmt-set .fa.fa-check").attr("ng-class","{conf_icon_uncheck:item.name!=comList['"+id+"'].listType}");
							elementTemplate.find(".configuration .listmt-set [ng-click=selectListType]").attr("ng-click","selectListType('"+id+"',item.name)");
							//list_mt设置行背景
							elementTemplate.find(".configuration .roweffect-set").attr("id",elementType + "_" + dtStr+"_"+"roweffect");
							elementTemplate.find(".configuration .roweffect-set .fa.fa-check").attr("ng-class","{conf_icon_uncheck:item.value!=comList['"+id+"'].row_effect}");
							elementTemplate.find(".configuration .roweffect-set [ng-click=setRowEffect]").attr("ng-click","setRowEffect('"+id+"',item.value)");
							//list_mt设置列分组
							elementTemplate.find(".configuration .listgroup-set").attr("id",elementType + "_" + dtStr+"_"+"listgroup");
							elementTemplate.find(".configuration .listgroup-set .fa.fa-check").attr("ng-class","{conf_icon_uncheck:item.value!=comList['"+id+"'].list_group}");
							elementTemplate.find(".configuration .listgroup-set [ng-click=setListGroup]").attr("ng-click","setListGroup('"+id+"',item.value)");
						}

						elementTemplate.find(".view").append(targetElem);
						elementTemplate.find(".preview").html(
								$(this).find('span').text());
						elementTemplate.attr("id", elementType + "_"
								+ dtStr + "_ct");
						//元素内容可编辑
						elementTemplate.attr("contenteditable","true");
						return elementTemplate;
					},
					start: function(e,t) {
						 var moveObj=$(t.item);
						 beforeDropElemStyle(moveObj);
						if (!startdrag) stopsave++;
						startdrag = 1;
					},
					drag : function(e, t) {
						t.helper.width(400);
					},
					stop: function(e,t) {
						var moveObj=$(t.item);
						changeElemStyle(moveObj);
						initContainer();
						$(".designer .mybox").draggable(
						{
						  connectToSortable : ".column,.containerComp",
						  cursor : "move",
						  scroll: false,
						  revert:"invalid",
						  handle: ".drag"
					    });
						if(stopsave>0) stopsave--;
						startdrag = 0;
						clashCompute();

						console.log(id)
						//$('#'+id).metrolist({});
						//css initialization
						//uiDataInit();
					}
				});
		 $(".designer .mybox").draggable(
				{
				  connectToSortable : ".column,.containerComp",
				  cursor : "move", 
			      scroll: false,
			      revert:"invalid",
			      handle: ".drag"
			    });
		}
		function cleanHtml(e) {
			$(e).parent().append($(e).children().html())
		}
        //撤销
        $('#undoBt').click(function(){
		stopsave++;
		if (undoLayout()) initContainer();
		     stopsave--;
		     clashCompute();
		});
        //重做
		$('#redoBt').click(function(){
			stopsave++;
			if (redoLayout()) initContainer();
			stopsave--;
			clashCompute();
		});
        //ui查询绑定事件
        $('#ui-select').change(function(){
			//异步获取元素列表
        	initMenu(false,$("#ui-select").val());
        	initdraggable();
		});
		//记录操作轨迹
		var timerSave = 1000;
		var stopsave = 0;
		var startdrag= 0;
		var designerHtml = $(".designer").html();
		//判断当前环境是否支持客户端缓存
		function supportstorage() {
			if (typeof window.localStorage == 'object')
				return true;
			else
				return false;
		}
        //保存页面布局入口方法
		function handleSaveLayout() {
			var e = $(".designer").html();
			if (!stopsave && e != window.designerHtml) {
				stopsave++;
				window.designerHtml = e;
				saveLayout();
				stopsave--;
			}
		}

		var layouthistory;
		//保存页面布局 方法
		function saveLayout() {
			var data = layouthistory;
			if (!data) {
				data = {};
				data.count = 0;
				data.list = [];
			}
			if (data.list.length > data.count) {
				for (i = data.count; i < data.list.length; i++)
					data.list[i] = null;
			}
			data.list[data.count] = window.designerHtml;
			data.count++;
			if (supportstorage()) {
				localStorage.setItem("layoutdata", JSON.stringify(data));
			}
			layouthistory = data;
		}
		//撤销方法实现
		function undoLayout() {
			var data = layouthistory;
			if (data) {
				if (data.count < 2)
					return false;
				window.designerHtml = data.list[data.count - 2];
				data.count--;
				$('.designer').html(window.designerHtml);
				if (supportstorage()) {
					localStorage.setItem("layoutdata", JSON.stringify(data));
				}
				return true;
			}
			return false;
		}
		//重做方法实现
		function redoLayout() {
			var data = layouthistory;
			if (data) {
				if (data.list[data.count]) {
					window.designerHtml = data.list[data.count];
					data.count++;
					$('.designer').html(window.designerHtml);
					if (supportstorage()) {
						localStorage
								.setItem("layoutdata", JSON.stringify(data));
					}
					return true;
				}
			}
			return false;
		}
		//初始化容器 
		function initContainer(){
			$(".designer,.designer .column,.designer .containerComp").sortable({
				connectWith: ".designer,.column,.containerComp",
				opacity: .35,
				handle: ".drag",
				start: function(e,t) {
					 var moveObj=$(t.item);
					 beforeDropElemStyle(moveObj);
					if (!startdrag) stopsave++;
					startdrag = 1;
				},
				stop: function(e,t) {
					var moveObj=$(t.item);
					var contId = moveObj.attr("id");
					changeElemStyle(moveObj);
					if(stopsave>0) stopsave--;
					startdrag = 0;
				}
			});
		}
		//修改元素拖入后的样式
		function beforeDropElemStyle(moveObj){
				moveObj.find(".preview").css('display', 'block');
		}
		//修改元素拖入后的样式
		function changeElemStyle(moveObj){
				moveObj.attr('style', '');
				moveObj.css('display', 'block');
				moveObj.find(".preview").css('display', 'none');
		}
		//获取适用的UI冲突
		var hisUiList;
		function clashCompute(){
			
			hisUiList=$(".ui-list").html();
			var scopeArray=[];
			var scope;
			var array;
			$(".designer").find("[scope]").each(function(i,item){
				scope=$(item).attr("scope");
				array=scope.split(',');
				if(scope&&scope!=""){
					if(i==0)
					scopeArray=array;
					else
					scopeArray=Array.intersect(scopeArray,array);
				}
			});
			 $(".ui-list").empty();
			if(array&&array.length>0&&(scopeArray==null||scopeArray.length==0)&&hisUiList.trim().length>0){
			    //显示内容
				$('.modalcontainer').removeClass("hidemy-modal");
				$('.modalcontainer').addClass("my-modal");
			    $('#warningModal').modal('show');
			}else{
				$(scopeArray).each(function(i,item){
					$(".ui-list").append("<li class=\"glyphicon glyphicon-edit\">"+item.toLocaleUpperCase()+"</li>");
				});
			}
			//$('#warningModal').modal('show');
		}
		//警告框显示时调用事件
		 $('#warningModal').on('show.bs.modal', function () {
		        $(this).find(".modal-body").html("<p>新增控件与页面控件存在冲突,是否继续？</p>");
		  });
		//关闭警告框
		 $('.modalClose').click(function(){
			stopsave++;
			if (undoLayout()) initContainer();
			stopsave--;
			$(".ui-list").html(hisUiList);
			$('.modalcontainer').removeClass("my-modal");
			$('.modalcontainer').addClass("hidemy-modal");
		 });
		//关闭警告框
		 $('.modalContinue').click(function(){
			 $('#warningModal').modal('hide');
			 $('.modalcontainer').removeClass("my-modal");
			 $('.modalcontainer').addClass("hidemy-modal");
		 });
		/**
		* 求两个集合的交集
		* @param {Array} a 集合A
		* @param {Array} b 集合B
		* @returns {Array} 两个集合的交集
		*/
		Array.intersect = function(a, b){
			    var result = [];
			    for(var i = 0; i < b.length; i ++) {
			        var temp = b[i];
			        for(var j = 0; j < a.length; j ++) {
			            if(temp === a[j]) {
			                result.push(temp);
			                break;
			            }
			        }
			    }
			    return result.uniquelize();
		};
		/*
		*数组去重复
		*/
		Array.prototype.uniquelize=function() { // 去重
		    var r = [];
		    for(var i = 0; i < this.length; i ++) {
		        var flag = true;
		        var temp = this[i];
		        for(var j = 0; j < r.length; j ++) {
		            if(temp === r[j]) {
		                flag = false;
		                break;
		            }
		        }
		        if(flag) {
		            r.push(temp);
		        }
		    }
		    return r;
		}
		/*
		*提示框
		*/
		$.alert=function(type,msg){
			 var alertDom=$("<div style=\"display:none\" class=\"alert alert-dismissable myalert\"></div>");
			 alertDom.append("<button class=\"close\" aria-hidden=\"true\" type=\"button\" data-dismiss=\"alert\">×</button>");
			 if(type==1){
				 alertDom.addClass("alert-danger");
				
			 }else{
				 alertDom.addClass("alert-success");
			 }
             alertDom.append("<h4>	<i class=\"icon fa fa-check\"></i> 操作提示!</h4>");
             alertDom.append(msg);
             $("body").append(alertDom);
             $(".myalert").fadeIn();
		}

/**
 * @author [Jack]
 * [uiDataInit description]
 * @return {[type]} [description]
 */
function uiDataInit(){
	//$(".selected_border").removeClass("selected_border");
	var $injector = getInjector();
	var providerData = $injector.get('globalProvider');
	$("[ui-role^='ui-']").each(function(index, el) {
		var id = $(el).attr("id");
		mainApp.config(function(globalProviderProvider){
			//globalProviderProvider._comList[id]={};
		});
		if(id.match(/^list_mt/)!=null){
			providerData.comList[id]={};
			providerData.comList[id].id=id;
			providerData.comList[id].row_effect="0";
			providerData.comList[id].list_group="0";
		}
		if(id.match(/^list_mt2_simple/)!=null){
			providerData.comList[id].listType="list-simple";
		}
		if(id.match(/^list_mt2_default/)!=null){
			$("#"+id+" .list-default.mt-list-container").css("padding","0")
			$("#"+id+" .list-icon-container").css("border","none")
			providerData.comList[id].listType="list-default";
		}
		if(id.match(/^list_mt2_todo/)!=null){
			$("#"+id+" [href=list_todo_collapse]").attr("href","#"+id+"_collapse");
			$("#"+id+" [id=list_todo_collapse]").attr("id",id+"_collapse");
			providerData.comList[id].listType="list-todo";
		}
		if(id.match(/^list_mt2_news/)!=null){
			providerData.comList[id].listType="list-news";
		}
		var flag = false;
		$('#'+id+' [ui-com^="com-"]').each(function(index, item) {
			var subId = id+'_'+index;
			if(providerData.dataList[subId]==undefined){
				flag = true;
				$(item).attr({"id":subId,"ng-click":"focusOn('"+subId+"')","ng-class":"{selected_border: focusObj.id=='"+subId+"'}"});
				//$(item).addClass("{{data['"+subId+"'].bgColor}}");
				var bgcolor = $(item).attr('ui-bgcolor');
				if(bgcolor==undefined){
					bgcolor = providerData.bgColors[0].name;
				}
				$(item).addClass(bgcolor);
				mainApp.config(function(globalProviderProvider){
					//globalProviderProvider._dataList[subId]={};
					//globalProviderProvider._dataIdList.push(subId);
				});
				providerData.dataList[subId]={};
				providerData.dataList[subId].id=subId;
				providerData.dataList[subId].bgColor=bgcolor;
				providerData.dataIdList.push(subId);

			}
		});
		if(flag){
			$injector.invoke(function($rootScope, $compile, $document) {
				$compile(angular.element(document.getElementById(id+'_cb')))($rootScope);
				$compile(angular.element(document.getElementById(id+'_listmt')))($rootScope);
				$compile(angular.element(document.getElementById(id+'_roweffect')))($rootScope);
				$compile(angular.element(document.getElementById(id+'_listgroup')))($rootScope);
				$compile(angular.element(document.getElementById(id)))($rootScope);
				//var scope = angular.element(document.getElementById('t1')).scope();
				//scope.compile();
				//$compile(angular.element(document.getElementById('t1')))(scope);
				//$rootScope.$digest();
			});
		}

	});
    mainApp.config(function(globalProviderProvider){
		globalProviderProvider._comList=providerData.comList;
		globalProviderProvider._dataList=providerData.dataList;
		globalProviderProvider._dataIdList=providerData.dataIdList;
	});
}
$(function(){
	//uiDataInit();
});