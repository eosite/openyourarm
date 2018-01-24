/**
 * Bootstrap Gantt扩展
 */
(function($){
	var plugin = 'gantt';

	/*Ganttalendar.prototype.synchHighlight = function () {
		if (this.master.currentTask && this.master.currentTask.ganttElement){
			this.highlightBar.css("top", (parseInt(this.master.currentTask.ganttElement.attr("y"))-this.taskVertOffset) + "px");
		}
		var $target = this.element;
		$target.find('th').css('text-align','center');
		$target.find('svg').css('overflow','auto');
	};*/

	var _init = function(target){
		$(target).removeAttr('contenteditable');
		$.ajax({
			url: contextPath+'/scripts/plugins/bootstrap/gantt/resource/tmpls.html',
			type: 'POST',
			dataType: 'html'
		})
		.done(function(result) {
			var $result = $(result);
			var temp = $('body').find('#'+$result.attr('id'));
			if(temp.length==0){
				$(result).appendTo('body');
			}
			createGantt(target);
		})
		.fail(function() {
			//console.log("error");
		})
		.always(function() {
			//console.log("complete");
		});

	}
	
	var createGantt = function(target){
		var opts = getOptions(target);
		if(opts.demo){
			$.ajax({
				url: contextPath+'/scripts/plugins/bootstrap/gantt/resource/demo.json',
				type: 'POST',
				dataType: 'json'
			})
			.done(function(result) {
				initGanttData(result, target);
			})
			.fail(function() {
				//console.log("error");
			})
			.always(function() {
				//console.log("complete");
			});
			return;
		}
		var ar = opts.ajax.read;
		ar.success = function(ret){
			if(ret && ret instanceof Object){
				convert(ret[opts.ajax.schema.data] || [], target);
			}
		}
		ar.beforeSend = function(){
			
		}
		ar.complete = function(XHR,TS){
			ar.data = $.parseJSON(ar.data);
		}
		ar.data = opts.ajax.parameterMap(ar.data);
		$.ajax(ar);
	}

	function initGanttData(data, target){
		IframeGantt.init($(target), data);
		/**
			var offset=new Date().getTime()-data.tasks[0].start;
			for (var i=0;i<data.tasks.length;i++)
			  data.tasks[i].start=data.tasks[i].start+offset;
		*/
		var ge ;
		return;
		var opts = getOptions(target);
		var workSpace = $(target).find('.workSpace');
		workSpace.css({width:'100%',height:600});
		ge.init(workSpace);
		if(!opts.editalbe){
			$(target).find('.ganttButtonBar').remove();
		}
		ge.loadProject(data);

		//fill default Teamwork roles if any
		if (!ge.roles || ge.roles.length == 0) {
			setRoles(ge);
		}

		//fill default Resources roles if any
		if (!ge.resources || ge.resources.length == 0) {
			setResource(ge);
		}
		
		initEvents(target, ge);
		loadDecorator();
	}

	function initEvents(target, ge){
		$(target).on('click.button.saveGanttOnServer', 'button.saveGanttOnServer', function(event) {
			event.preventDefault();
			var prj = ge.saveProject();
		});

		$(target).on('click.button.redrawGantt', 'button.redrawGantt', function(event) {
			event.preventDefault();
			ge.gantt.showCriticalPath=!ge.gantt.showCriticalPath;
			ge.redraw();
		});

		$(target).on('click.button.editResources', 'button.editResources', function(event) {
			event.preventDefault();
			editResources(ge);
		});

		$(target).on('click.button.gantt_tools', 'button.gantt_tools', function(event) {
			event.preventDefault();
			$(target).find('.workSpace').trigger($(event.currentTarget).attr('edata'));
		});

	}

	function convert(datas, target){
		$.ajax(ArtTemplateDataUtils.ajax($(target).attr('data-role')))
		.done(function(result) {
			if(result.template){
				var ret = ArtTemplateDataUtils.convertList(datas, result.template);
				initGanttData($.parseJSON(ret), target);
			}

		})
		.fail(function(XHR, TS) {
			console.log(TS);
		})
		.always(function() {
			//console.log("complete");
		});
	}

	function saveGanttOnServer() {
		if(!ge.canWrite)
		return;
		//this is a simulation: save data to the local storage or to the textarea
		saveInLocalStorage();
	}

	function saveInLocalStorage() {
		var prj = ge.saveProject();
		if (localStorage) {
			localStorage.setObject("teamworkGantDemo", prj);
		} else {
			$("#ta").val(JSON.stringify(prj));
		}
	}

	function setRoles(ge) {
		ge.roles = [
			{
			  id:"tmp_1",
			  name:"Project Manager"
			},
			{
			  id:"tmp_2",
			  name:"Worker"
			},
			{
			  id:"tmp_3",
			  name:"Stakeholder/Customer"
			}
		];
	}

	function setResource(ge) {
		var res = [];
		for (var i = 1; i <= 10; i++) {
			res.push({id:"tmp_" + i,name:"Resource " + i});
		}
		ge.resources = res;
	}

	function editResources(ge){
	  //make resource editor
	  var resourceEditor = $.JST.createFromTemplate({}, "RESOURCE_EDITOR");
	  var resTbl=resourceEditor.find("#resourcesTable");

	  if(!ge.resources){
	  	ge.resources = [];
	  }

	  for (var i=0;i<ge.resources.length;i++){
	    var res=ge.resources[i];
	    resTbl.append($.JST.createFromTemplate(res, "RESOURCE_ROW"))
	  }


	  //bind add resource
	  resourceEditor.find("#addResource").click(function(){
	    resTbl.append($.JST.createFromTemplate({id:"new",name:"resource"}, "RESOURCE_ROW"))
	  });

	  //bind save event
	  resourceEditor.find("#resSaveButton").click(function(){
	    var newRes=[];
	    //find for deleted res
	    for (var i=0;i<ge.resources.length;i++){
	      var res=ge.resources[i];
	      var row = resourceEditor.find("[resId="+res.id+"]");
	      if (row.size()>0){
	        //if still there save it
	        var name = row.find("input[name]").val();
	        if (name && name!="")
	          res.name=name;
	        newRes.push(res);
	      } else {
	        //remove assignments
	        for (var j=0;j<ge.tasks.length;j++){
	          var task=ge.tasks[j];
	          var newAss=[];
	          for (var k=0;k<task.assigs.length;k++){
	            var ass=task.assigs[k];
	            if (ass.resourceId!=res.id)
	              newAss.push(ass);
	          }
	          task.assigs=newAss;
	        }
	      }
	    }

	    //loop on new rows
	    resourceEditor.find("[resId=new]").each(function(){
	      var row = $(this);
	      var name = row.find("input[name]").val();
	      if (name && name!="")
	        newRes.push (new Resource("tmp_"+new Date().getTime(),name));
	    });

	    ge.resources=newRes;

	    closeBlackPopup();
	    ge.redraw();
	  });


	  var ndo = createBlackPage(400, 500).append(resourceEditor);
	}

	function loadDecorator(){
		$.JST.loadDecorator("ASSIGNMENT_ROW", function(assigTr, taskAssig) {
			var resEl = assigTr.find("[name=resourceId]");
			for (var i in taskAssig.task.master.resources) {
			  var res = taskAssig.task.master.resources[i];
			  var opt = $("<option>");
			  opt.val(res.id).html(res.name);
			  if (taskAssig.assig.resourceId == res.id)
			    opt.attr("selected", "true");
			  resEl.append(opt);
			}


			var roleEl = assigTr.find("[name=roleId]");
			for (var i in taskAssig.task.master.roles) {
			  var role = taskAssig.task.master.roles[i];
			  var optr = $("<option>");
			  optr.val(role.id).html(role.name);
			  if (taskAssig.assig.roleId == role.id)
			    optr.attr("selected", "true");
			  roleEl.append(optr);
			}

			if(taskAssig.task.master.canWrite && taskAssig.task.canWrite){
			  assigTr.find(".delAssig").click(function() {
			    var tr = $(this).closest("[assigId]").fadeOut(200, function() {
			      $(this).remove();
			    });
			  });
			}
		});
	}

	function getOptions(target){
		return $(target).data(plugin).options;
	}

	function close(target){
		
	}

	function open(target){
		
	}

	function destroy(target){
		$(target).find('.ganttButtonBar').empty();
		$(target).find('.workSpace').empty();
	}

	function restore(target){
		_init(target);
	}

	//初始化
	$.fn[plugin] = function(options, param){
	    if (typeof options == 'string'){
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i,o){
			var state = $.data(o, plugin);
			if (state) {
				state.tabs = [];
				$.extend(true,state.options,options);
			} else {
				$.data(o, plugin, {
					options : $.extend(true,{},$.fn[plugin].defaults, options),
					target : o,
					columns : [],
					datas : param,
				});
			}
			 _init(o);
			
		});
	};
	
	//外部调用方法
	$.fn[plugin].methods = {
		close: function(jq){
			close(jq[0]);
		},
		open: function(jq){
			open(jq[0]);
		},
		destroy: function(jq){
			destroy(jq[0]);
		},
		restore: function(jq){
			restore(jq[0]);
		}
	};
	
	$.fn[plugin].defaults = {
		width: 'auto',
		height: 'auto',
		editalbe: true,
		demo: false,
		ajax : {
            read : {
                url : '',
                type : 'POST',
                dataType : 'JSONp',
                data : {},
                contentType : "application/json",
                async : true
            },
            save : {
                url : '',
                type : 'POST',
                data : {},
                success : function(e){}
            },
            destroy : {
                url : '',
                type : 'POST',
                data : {},
                success : function(e){}
            },
            parameterMap: function(params){
            	return params;
            },
            schema : {
            	data : 'data',
            	total : 'total'
            }
        }
	};
})(jQuery);