/**
 * klaus.wang
 */
function JP(container){
	
 	var instance = jsPlumb.getInstance({
        DragOptions: { cursor: 'pointer', zIndex: 2000 },
        Container: container
    }),containerCls = '.div-cls';
	var sourceAnchors = [
          [1, 0.5, 1, 0, 0, 0, "bar"],
      ],
      targetAnchors = [
          [0, 0.5, -1, 0]
      ],
      exampleColor = '#00f',
      connector = [ "Bezier", { cssClass: "connectorClass", hoverClass: "connectorHoverClass" } ],
      connectorStyle = {
          gradient: {stops: [
              [0, exampleColor],
              [0.5, '#09098e'],
              [1, exampleColor]
          ]},
          lineWidth: 3,
          strokeStyle: exampleColor
      },
      hoverStyle = {
          strokeStyle: "#449999"
      },
      overlays = [
          ["Diamond", { fillStyle: "#09098e", width: 15, length: 15 } ],
		  ["Custom", {
		           create:function(component) {
		        	  return JP.overlay();
		           },
		           location:0.7,
		           id:"customOverlay"
		  }]
      ],
      endpoint = ["Dot", { cssClass: "endpointClass", radius: 5, hoverClass: "endpointHoverClass" } ],
      endpointStyle = { fillStyle: exampleColor },
      anEndpoint = {
          endpoint: endpoint,
          paintStyle: endpointStyle,
          hoverPaintStyle: { fillStyle: "#449999" },
          isSource: true,
          isTarget: true,
          maxConnections: -1,
          dropOptions: { hoverClass: "hover", activeClass: "active" },
          connector: connector,
          connectorStyle: connectorStyle,
          connectorHoverStyle: hoverStyle,
          connectorOverlays: overlays
      };
	
	this.overlay = function(){
		var img = $("<img>",{
   		 src :  contextPath + "/scripts/jsPlumb/sql_join_inner.png",
   		 class : 'join-cls'
   	  }).on('click',function(e){
   		  //e.preventDefault(); 
   		  var $this = $(this);
   		  var id = $this.attr("id");
   		  
   		  var conns = instance.getAllConnections();
   		  if(conns){
   			  var conn;
   			  h1 : for(var i = 0; i < conns.length; i ++){
   				  conn = conns[i];
       				  var overlays = conn.getOverlays();
       				  if(!overlays)
       					  continue;
       				 h2 : for(var key in overlays){
       					  var overlayid = $(overlays[key].canvas).attr("id");
       					  if(overlayid && overlayid == id){
       						
       						var source = $("#" + conn.sourceId),target = $("#" + conn.targetId);
       						
       						$("#table0").attr({
       							tableName : conn.sourceId.split('_tr_')[0]
       						}).text(source.closest("table").attr("namecn"));
       						
       						$("#column0").attr({
       							columnName : conn.sourceId.split('_tr_')[1]
       						}).text(source.find(".fname").text());
       						
       						$("#table1").attr({
       							tableName : conn.targetId.split('_tr_')[0]
       						}).text(target.closest("table").attr("namecn"));
       						
       						$("#column1").attr({
       							columnName : conn.targetId.split('_tr_')[1]
       						}).text(target.find(".fname").text());
       						
       						/*var $joinDiv = $("#join-div");
       						var joinWindow = $joinDiv.data("kendoWindow") || $joinDiv.kendoWindow({
       								title : 'Join Properties',
       								animation : false,
       								modal : true,
       								height : 450,
       								width : 600,
       								actions : [ "Maximize", "Close" ],
       								close : function(){
       									$("input:radio[name='join-type']:checked").each(function(){
       										this.checked = false;
       									});
       									$("#table-column").data("kendoDropDownList").select(0);
       								},
       								position: {
       								   top: 200
       								},
       							}).data("kendoWindow");
       						  
       						  joinWindow.open();
       						  joinWindow.center();
       						  $("#join-btn").data("target",this);
       						  var msg = $this.data('msg') || {
       							connector : 'inner join',
       							operator : '='
       						  };
       						  $.log(msg);
    						  if(msg){//已经设置过了
    							  
    						  } else {
    							  
    						  }
    						  $("#table-column").data("kendoDropDownList").value(msg.operator);
							  $("input:radio[name='join-type'][value='" + msg.connector + "']")[0].checked = true;*/
       					   $("#join-btn").data("target",this);
       						openOperatorWin(function(t){
       							var $this = t.wrapper.find("[data-role=dropdownlist]"),
    	                		id = $this.attr("id"), index = id.replace("drop-table-",""),
    	                		tableName = $("#table" + index).attr("tableName");

       							t.value(tableName);
       							
       							setColumns(index, tableName, function(c){
       								c.value($("#column" + index).attr("columnName"));
       							});
       						}).data("defined",false);
       						
	        					  break h1;
       					  }
       				  }
   			  }
   		  }
   		  return false;
   	  });
   	  
   	  return img;
	};
	
	this.endpoints = {};
	
	this.addEndpoint = function(id,nobind,visible){
		
		if(!this.endpoints[id]){
			this.endpoints[id] = [
	  			instance.addEndpoint(id, anEndpoint, {anchor: sourceAnchors}),
	  			instance.addEndpoint(id, anEndpoint, {anchor: targetAnchors})
	          ];
		}
		
		visible = !!visible;
		
		$.each(instance.getConnections( "*" , {
			scope : "*",
			source : this.endpoints[id][0],
			target : this.endpoints[id][1]
		}  ,false ),function(i,connection){
			if(visible){
				connection.showOverlay("customOverlay");
			}
			/*else 
				connection.hideOverlay("customOverlay");*/
		});
		$.each(this.endpoints[id],function(i,item){
			item.setVisible(visible);
		});
		
        if(!nobind)
        	this.bind(instance);
	};
	
	this.deleteEndpointById = function(id){
		var points = JP.endpoints[id];
		if(points){
			$.each(points,function(i,ep){
				instance.deleteEndpoint(ep);
			});
		}
	};
	
	this.deleteEndpointByContainer = function($container){
		var id,connections = JP.connections();
		$container.find(".field").each(function(i,v){
			id = $(v).attr("id");
			if(connections[id]){
				instance.detach(connections[id]);
			}
			JP.deleteEndpointById(id);
		});
		connections = null;
	};
	
	this.connections = function(){
		var tmp = new Object();
		var conns = instance.getAllConnections();
		if(conns){
			$.each(conns,function(i,v){
				tmp[v.sourceId] = v;
				tmp[v.targetId] = v;
			});
		}
		return tmp;
	};
	
	this.bind = function(instance){
		
		instance.unbind("click");
		
        instance.bind("click", function (conn) {
            instance.detach(conn);
        });                

        instance.unbind("beforeDetach");
        
        instance.bind("beforeDetach", function (conn) {
            return confirm("确认删除连接吗?");
        });
        
	    var tableWithClass = jsPlumb.getSelector(containerCls);
	    
        instance.draggable(tableWithClass,{
        	stop : function(){
        		
        	}
        });
        
        jsPlumb.fire("jsPlumbDemoLoaded", instance);
	};
	
	this.batch = function(jq,nobind){
		if(jq && jq[0]){
			var $div,$this,pos,visible = undefined;
			instance.batch(function(){
				jq.each(function(i,v){
					$this = $(this),$div = $this.parents(containerCls);
					if($div[0]){
						visible = true;
						pos = $div.offset();
						var height = $div.css("height").replace('px','');
						if(($this.offset().top < pos.top) || ($this.offset().top > (pos.top + height * 1))){
							visible = false;
						}
					}
					JP.addEndpoint($this.attr('id'),true,visible);
				});
			});
			if(!nobind)
				this.bind(instance);
		}
	};
	
	this.connect = function(params){
		var common = {
			  anchors : [sourceAnchors,targetAnchors],
			  endpoint: endpoint,
	          overlays: overlays
		};
		//params = $.extend({},anEndpoint,params);
		return instance.connect(params,common);
	};
	
	this.initBatch = function(source,target){
		
		var filter = function(evt, el){
			var $target = $(evt.target);
			if($target.hasClass('start')){
				this.anchor = targetAnchors;
			}else{
				this.anchor = sourceAnchors;
			}
		    return evt.target.tagName !== "INPUT";
		};
		
		var common = {
				  anchors : [sourceAnchors,targetAnchors],
				  endpoint: endpoint,
				  connectorOverlays: overlays
			};

		instance.batch(function(){
		
			instance.makeSource(source, {
				anchor:sourceAnchors,
				filter: filter,
				maxConnections: -1
			},common);

			instance.makeTarget(target, {
				anchor:targetAnchors,
				filter: filter,
				maxConnections: -1
			},common);
			
		});
		
		this.bind(instance);
	};
	
	this.hideAllConnections = function(){
		this.setConnectionsVisible(false);
	};
	
	this.showAllConnections = function(){
		this.setConnectionsVisible(true);
	};
	
	this.setConnectionsVisible = function(visible){
		var $conns = $("[class*='_jsPlumb_']");
		if(visible)
			$conns.show();
		else 
			$conns.hide();
	};
	
	this.hideAllEndpoints = function(){
		this.setEndpointsVisible(false);
	};
	
	this.showAllEndpoints = function(){
		this.setEndpointsVisible(true);
	};
	
	this.setEndpointsVisible = function(visible){
		var $endpoints = $("._jsPlumb_endpoint");
		if(visible)
			$endpoints.hide();
		else
			$endpoints.show();
	};
	
	this.JP = this;
}