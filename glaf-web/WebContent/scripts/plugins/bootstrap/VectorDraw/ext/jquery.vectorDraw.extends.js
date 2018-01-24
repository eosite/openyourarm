(function($, window, document, undefined) {
	var plugin = "vectorDrawOpt", optionKey = plugin + ".options",
	
	
    Build = function(o) {
		this.target = $(o);
		this.w = $(document);
		this.option = this.target.data(optionKey).options;
		this._init($(this));
		
	};
	
	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string' && (options in $.fn[plugin].methods)) {
			var p = this.data(plugin);
			if (!plugin) {
				this.data("vectorDrawOpt", new Plugin(this, params));
            } 
			return p ? p[options].apply(p, Array.prototype.slice.call(
					arguments, 1)) : $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					datas : param
				});
			}
			$.data(o, plugin, new Build(o));
		});
	};

	$.fn[plugin].defaults = {
	    events : {
	    	click : function(e){
	    		
	    	},
	    	mouseOverSpot : function(e){
	    		
	    	}
	    }
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
			    $opt = that.option,
			    $target = that.target;
			
			
			that.vdrawInitPageLoad();
			that.query(null,$target);
		},
		
		
		query:function(data,$target){
			var that = this;
			var ar = that.option.read;
			
			ar.success = function(ret) {
				that._ajaxSuccess(ret, $target);
			};
			//保存查询参数
			ar._query_params_ = data;
			var params = that.getParams(data);
			that._request(ar,$.extend(true, {}, params),data?data.id:null);
		},
		getParams: function(params) {
			// return params || {};
			var that = this,
				data = that.option.read.d_data__ = (that.option.read.__data__ ? $.extend({}, that.option.read.__data__) : undefined); //取动态参数
			var ret = $.extend({}, data || this.option.read.data);

			if (params && data) { //如果不是第一次查询

				if (!data.params) {
					data.params = {};
				} else {
					data.params = JSON.parse(data.params);
				}

				for (var key in params) {
					if (!(key in data)) {
						data.params[key] = params[key]; //保存要保留参数					
					} else {
						ret[key] = params[key]; //装载变化参数
					}
				}
				
				data.params = JSON.stringify(data.params);

				ret.params = data.params; //更新要保留参数
			}
			return ret;
		},
		_request: function(ar, params,id) {
			var that = this;
			if (!ar.__success__) { //第一次ajax 调用
				// ar.success = function(ret) {
				// 	that._ajaxSuccess(ret);
				// };
				ar.__success__ = true;
				ar.__data__ = $.extend(ar.__data__ || {}, ar.data); // 原始参数保存
				ar.d_data__ = $.extend(ar.d_data__ || {}, ar.data); // 动态参数保存
			}
			that._initParams(ar, params,id);
			$.ajax(ar);
		},
		_initParams: function(ar, params,id) {
			// var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
			// ar.data = this._getParameterMap(data);
			
			var data = $.extend(true, {}, ar.d_data__, ar.__data__, params);
//			if (this.options.pagination.paging) {
//				$.extend(data, {
//					page: this.options.pagination.page,
//					pageSize: this.options.pagination.pageSize
//				});
//			}
			
			$.extend(data, {
				sort: this.__sortArray
			});
			if(id){
				data.params = "";
				data.id = id;
			}
			ar.data = this._getParameterMap(data);
		},
		_getParameterMap: function(data) {
			return JSON.stringify(data || {});
		},
		
		_ajaxSuccess : function(item,$target){
			var that = $target.data("vectorDrawOpt"),
			    opt = that.option;
			
			that.getCoordinate(item.data,$target);
			
			that.drawRect(item.data,$target,opt.vdcanvas);
			
//			var i = setInterval(function(){
//				if(opt.succ){
//					clearinterval(i);
//				} else{
//					
//					
//				}
//			},1000);
		},
		
		vdrawInitPageLoad : function() {
			var that = this,
			    target = that.target,
			    opt = that.option,
			    InfoArea = target.find("#InfoArea");
//			that.getCoordinate(data,target);	 
			
			var _vdmousedown = function(e) {
				opt.events.click(e);
				
		    };
		    var _vdmousemove = function(e){
		    	var condriate = target.find(".condriate");
		    	condriate[0].innerHTML = "x : " + e.x.toFixed(3) + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;y : " +e.y.toFixed(3);
		    	that.vertiContent(e,target);
		    	
		    };
			
			
	        vdmanager.AttachCanvas('canvas', 400, 400);
	        target.find('#noJavascript')[0].innerHTML = "";
	        var vdcanvas = vdmanager.vdrawObject('canvas');
            vdcanvas.vdmousemove = _vdmousemove;
	        vdcanvas.vdmousedown = _vdmousedown;
	        vdcanvas.vdAfterOpenDocument = that._vdAfterOpenDocument;
	        vdcanvas.SetDefaultTimeOutMilliseconds(400);
	        vdcanvas.vdprogress = that._progress;
	        vdcanvas.count = 0;
	        that.ExtendCancvas();       
	        InfoArea.css("height","auto");
	        InfoArea.css("height",canvas.height+'px')
	        vdcanvas.ActiveAction().DefaultActions = vdConst.DEFAULT_ZOOMSCALE + vdConst.DEFAULT_SCROLL + vdConst.DEFAULT_ZOOMEXTENTS;
	        
	       
	        
	        
	        opt.vdcanvas = vdcanvas;
	        vdcanvas.targetVector = target;
//	        that.Open();
	    },
	    _vdAfterOpenDocument : function() {//fire when a web control document loaded.   
            var vdcanvas = vdmanager.vdrawObject('canvas'),
                document = vdcanvas.GetDocument();
//            vdcanvas.SetRenderMode(vdConst.RENDERMODE_RENDER); // set the RENDERMODE_SHADE_GL to see better the 3d shapes
	    	
            
            
	      
        },
        //回调方法
	    _progress : function(evt) {
	    	
            if (evt.percent < 0) {
            	
            }
            else if(evt.percent >= 100){
            	var that = this;
            	
            	this.count = this.count + 1;
            	if(this.count == 3){
            		setTimeout(function(){ 
            		var node = [],
            		//填充格子背景颜色 		
		        		BkColor = vdConst.createNewColor("255,0,0,1"),
		        		FillColor = vdConst.createNewColor("byblock"),
		        		hPatrn = that.createNewHatchProperties(eval("vdConst.FillModeSolid"), BkColor, FillColor),
		        		vectorDrawFunc = that.targetVector.data("vectorDrawOpt");
            		that.SetActiveHatchProperties(hPatrn);
                    //以下方法是为需求所写，在cad图中绘制格子和不规则图形
            		//有些格子是通过数据生成格子和不规则的图形,有些则是在仓库通过原有数据生成的格子下面逐渐增加
            		
            		 $.each(that.data,function(i,item){
                     	var x = item['xmin'],         
                     	    y = parseFloat(item[that.start]),
                     	    v = parseFloat(item[that.end]),
                     	    k = item['xmax'] - item['xmin'],
                     	    h = parseFloat(item[that.end]) - parseFloat(item[that.start]),
                     	    ir = item['ir']
                     	    dr = that.dr;
                     	    vectorParam = item[that.vectorParam];
                     	    takePart = item[that.takePart];
                     	    
                     	if(takePart != undefined){
                     		if(takePart.indexOf(",") != -1){
                             	var arr = takePart.split(",");
                             	
                             	if(arr.length == 2){
                             		if(dr[item['xmin']+"-"+item['xmax']] == item[that.end]){
                      	
                                     	vectorDrawFunc.__updateCadView(that,x,y,parseFloat(arr[0]),h);

                                     	var maxHeight = y;
                                     	while(maxHeight > item['ymin']){
                                     		if(ir != undefined){
                                     			if(maxHeight > ir['y'][4]){
                                     				if(maxHeight > 22){
                                     					
                                     					var vb = maxHeight - 2;
                                             			if(vb <= 22){
                                             				var dx = maxHeight - 22;
                                             			     	
                                                         	vectorDrawFunc.__updateCadView(that,x,22,parseFloat(arr[0]),dx);
                                                         	
                                                         	maxHeight = 22;
                                             			}
                                             			else{
                                             				                             				
                                             					maxHeight = maxHeight - 2;                                               				
                                                             	vectorDrawFunc.__updateCadView(that,x,maxHeight,parseFloat(arr[0]),2);                                             				                     	
                                             			}                
                                     				}
                                     				else{
                                     					var vb = maxHeight - 2;
                                             			if(vb > item['ymin']){
                                             				if(vb <= 9){
                                             					var dx = maxHeight - 9;                                              				        	
                                                             	vectorDrawFunc.__updateCadView(that,x,9,k,dx);                                                        	
                                                             	maxHeight = 9;
                                             				}
                                             				else{
                                             					maxHeight = maxHeight - 2;
    	
                                                             	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                             				}                 
                                             			}
                                             			else{
           				
                                             				var dx = maxHeight - item['ymin'];
                                             				maxHeight = maxHeight - 2;
                                          				
                                                         	vectorDrawFunc.__updateCadView(that,x,item['ymin'],k,dx);
                                             				                                            				
                                             			}
                                   	
                                     				}
                                     				
                                     			}
                                     			else{
         
                                                    var xar = [1,1,2,3];
                                                    var yar = [3,2,2,3];
                                                    vectorDrawFunc.__updateCadIrrRegularView(that,ir,xar,yar);
                                                    
                                                    maxHeight = item['ymin'];
                                     			}
                                     		}
                                     		else{
                                     		if(maxHeight > 22){
                                     			var vb = maxHeight - 2;
                                     			if(vb >= 22){
                                     				var dx = maxHeight - 22;
                                     				                                                 	
                                                 	vectorDrawFunc.__updateCadView(that,x,parseFloat(arr[0]),k,dx);
                                                 	
                                                 	maxHeight = 22;
                                     			}
                                     			else{
                                   				
                                     					maxHeight = maxHeight - 2;     	
                                                     	vectorDrawFunc.__updateCadView(that,x,maxHeight,parseFloat(arr[0]),2);
                                				                     	
                                     			}                                  	
                                     		}
                                     		
                                     		else{
                                     			var vb = maxHeight - 2;
                                     			
                                     			if(vb > item['ymin']){
                                     				maxHeight = maxHeight - 2;
                                     				                                                	
                                                 	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                                    
                                     			}
                                     			else{
                                     				var dx = maxHeight - item['ymin'];
                                     				maxHeight = maxHeight - 2;
                                     			                                                	
                                                 	vectorDrawFunc.__updateCadView(that,x,item['ymin'],k,dx);
                                     			}
                           	
                                     		}                   
                                     	}
                                     	}
                             		}
                             		
                                 	item['mouseXmin'] = x;
                                 	item['mouseXmax'] = x + parseFloat(arr[0]);
                                 	
                                 	node.push(item);                           	
                                 	
                                 	
                             	} else if(arr.length == 4){
                             		if(dr[item['xmin']+"-"+item['xmax']] == item[that.end]){
                             			
         	                         
                                     	vectorDrawFunc.__updateCadView(that,x,y,parseFloat(arr[0]),h);
                                     	
                                     	var l = x + parseFloat(arr[0]) + (parseFloat(arr[1]) - parseFloat(arr[0]));
                                     	var z = parseFloat(arr[2]) - parseFloat(arr[1]);
                                     	                     	
                                     	vectorDrawFunc.__updateCadView(that,l,y,z,h);
                                        
                                     	l = item['xmin'] + parseFloat(arr[3]);
                                     	z = item['xmax'] - l;
                                     	                       	
                                     	vectorDrawFunc.__updateCadView(that,l,y,z,h);
                                     	
                                     	var maxHeight = y;
                                     	while(maxHeight > item['ymin']){
                                     		
                                     		if(maxHeight > 22){
                                     			

                                         			var vb = maxHeight - 2;
                                         			if(vb <= 22){
                                         				var dy = maxHeight - 22;
                                         				                                 	
                                                     	vectorDrawFunc.__updateCadView(that,x,22,parseFloat(arr[0]),dy);
                                                     	
                                                     	var l = x + parseFloat(arr[0]) + (parseFloat(arr[1]) - parseFloat(arr[0]));
                                                     	var z = parseFloat(arr[2]) - parseFloat(arr[1]);
                                                     	                                	                                                   	
                                                     	vectorDrawFunc.__updateCadView(that,l,22,z,dy);
                                                     	               	
                                                     	l = item['xmin'] + parseFloat(arr[3]);
                                                     	z = item['xmax'] - l;
        	
                                                     	vectorDrawFunc.__updateCadView(that,l,22,z,dy);
                                                     	
                                                     	maxHeight = 22;
                                         			}
                                         			else{
                                         				maxHeight = maxHeight - 2;
                                         				
                                                     	
                                                     	vectorDrawFunc.__updateCadView(that,x,maxHeight,parseFloat(arr[0]),2);
                                                     	
                                                     	var l = x + parseFloat(arr[0]) + (parseFloat(arr[1]) - parseFloat(arr[0]));
                                                     	var z = parseFloat(arr[2]) - parseFloat(arr[1]);
                                                     	
                                                     	
                                                     	vectorDrawFunc.__updateCadView(that,l,maxHeight,z,2);
                                                     	               	
                                                     	l = item['xmin'] + parseFloat(arr[3]);
                                                     	z = item['xmax'] - l;
                                                 	
                                                     	vectorDrawFunc.__updateCadView(that,l,maxHeight,z,2);
                                                     	
                                         			}                                  	
                                             				
                                    		}
                                     		else{
                         						var vb = maxHeight - 2;
                                     			
                                     			if(vb > item['ymin']){
                                     				maxHeight = maxHeight - 2;
               	
                                                 	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                     			}
                                     			else{
                                     				var dx = maxHeight - item['ymin'];
                                     				maxHeight = maxHeight - 2;
                                                	
                                                 	vectorDrawFunc.__updateCadView(that,x,item['ymin'],k,dx);
                                     			}
                                             	                           
                                             	
                                     		} 
                                     	}
                             		}
                             		if(vectorParam.indexOf("左") != -1){
                             			item['mouseXmin'] = x;
                                     	item['mouseXmax'] = x + parseFloat(arr[0]);
                                     	var ar1 = [];
                                     	$.each(item,function(i,v){
                                     		ar1[i] = v;
                                     	});
                                     	node.push(ar1);
                             		} else if(vectorParam.indexOf("中") != -1){
                             			var l = x + parseFloat(arr[0]) + (parseFloat(arr[1]) - parseFloat(arr[0]));
                                     	var z = parseFloat(arr[2]) - parseFloat(arr[1]);
                                     	item['mouseXmin'] = l;
                                     	item['mouseXmax'] = l + z;
                                     	var ar2 = [];
                                     	$.each(item,function(i,v){
                                     		ar2[i] = v;
                                     	});
                                     	node.push(ar2);
                             		} else if(vectorParam.indexOf("右") != -1){
                             	       
                                     	l = item['xmin'] + parseFloat(arr[3]);
                                     	z = item['xmax'] - l;
                                     	item['mouseXmin'] = l;
                                     	item['mouseXmax'] = l + z;
                                     	
                                     	var ar3 = [];
                                     	$.each(item,function(i,v){
                                     		ar3[i] = v;
                                     	});
                                     	node.push(ar3);
                             		}	
                                 	
                                 	
                                 	
                             	}
                  		   }
                           	else{
                           		if(dr[item['xmin']+"-"+item['xmax']] == item[that.end]){
                           			
                           			
                                 	vectorDrawFunc.__updateCadView(that,x,y,k,h);

                                 	var maxHeight = y;
                                 	while(maxHeight > item['ymin']){
                                 		if(ir != undefined){
                                 		if(maxHeight > ir['y'][2]){
                                 		 if(maxHeight > 20){
                                 			var vb = maxHeight - 2;
                                 			if(vb <= 20){
                                 				var dy = maxHeight - 20;
                                 				         	
                                             	vectorDrawFunc.__updateCadView(that,x,20,k,dy);
                                             	maxHeight = 20;
                                 			}
                                 			else{
                                 				maxHeight = maxHeight -2;
                                 				                                            	
                                             	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                             	
                                 			}                                  	
                                 		}
                                 		else{
                                 			var vb = maxHeight - 2;
                                 			
                                 			if(vb > item['ymin']){
                                 				maxHeight = maxHeight - 2;
          		
                                             	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                 			}
                                 			else{
                                 				var dx = maxHeight - item['ymin'];
                                 				maxHeight = maxHeight - 2;
   				
                                             	
                                             	vectorDrawFunc.__updateCadView(that,x,item['ymin'],k,dx);
                                 			}
                       	
                                 		}   
                                 		 
                                 	}
                                    else{
                  			
                                            var xar = [2,3,6,7];
                                            var yar = [2,3,3,2];
                                            vectorDrawFunc.__updateCadIrrRegularView(that,ir,xar,yar);
                              
                                            xar = [4,5,6,7];
                                            yar = [4,5,6,4];
                                            vectorDrawFunc.__updateCadIrrRegularView(that,ir,xar,yar);
                                            
                                            
                                            maxHeight = item['ymin'];
                                 	}
                                 	}
                             		else{
                             			if(maxHeight > 22){
                                 			var vb = maxHeight - 2;
                                 			if(vb <= 22){
                                 				var dy = maxHeight - 22;
			
                                             	vectorDrawFunc.__updateCadView(that,x,22,k,dy);
                                             	
                                             	maxHeight = 22;
                                 			}
                                 			else{
                                 				maxHeight = maxHeight -2;

                                             	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                             	
                                 			}                                  	
                                 		}
                                 		else{
                                 			var vb = maxHeight - 2;
                                 			
                                 			if(vb > item['ymin']){
                                 				maxHeight = maxHeight - 2;
                                 				                      	
                                             	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                 			}
                                 			else{
                                 				var dx = maxHeight - item['ymin'];
                                 				maxHeight = maxHeight - 2;
                                 				                          	
                                             	vectorDrawFunc.__updateCadView(that,x,item['ymin'],k,dx);
                                 			}
                       	
                                 		}  
                             		}
                                 }
                           		}
                    	
                             	item['mouseXmin'] = x;
                             	item['mouseXmax'] = x + k;
                             	
                             	node.push(item);
                             	
                             	
                           	}
                     	}
                     	else{
                     		if(dr[item['xmin']+"-"+item['xmax']] == item[that.end]){
                     			
                     			
                             	vectorDrawFunc.__updateCadView(that,x,y,k,h);
                             	var maxHeight = y;
                             	while(maxHeight > item['ymin']){
                             		if(maxHeight > 22){
                             			var vb = maxHeight - 2;
                             			if(vb <= 22){
                             				var dy = maxHeight - 22;
                             				
                                         	vectorDrawFunc.__updateCadView(that,x,22,k,dy);
                                         	maxHeight = 22;
                             			}
                             			else{
                             				maxHeight = maxHeight -2;
                             				
                                         	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                         	that.UpdateFig(box);
                                         	
                             			}                                  	
                             		}
                             		else{
                             			maxHeight = maxHeight - 2;
                             			
                                     	vectorDrawFunc.__updateCadView(that,x,maxHeight,k,2);
                                     	that.UpdateFig(box);
                   	
                             		} 
                             	}                            	
                     		}                   			
                         	item['mouseXmin'] = x;
                         	item['mouseXmax'] = x + k;
                         	
                         	node.push(item);                        	
                     	}
                     	that.node = node;
                     	
//            			that.redraw();
         			});
            		 
            		},1000);
            	}
            	 
            }
            else{
            	
            } 
        },
        __updateCadView : function($that,xPosition,yPosition,xWidth,yHeight){
        	 
        	var vertexes = [vdgeo.newvertex(xPosition, yPosition, 0 , 0),vdgeo.newvertex(xPosition ,yPosition + yHeight, 0 , 0), vdgeo.newvertex(xPosition + xWidth, yPosition + yHeight, 0 , 0), vdgeo.newvertex(xPosition + xWidth, yPosition , 0 , 0)];
            var vdPolyline = $that.AddPolyline(vertexes, false);
            vdPolyline.PenColor = { ColorIndex: 0, ColorFlag: null };;
            vdPolyline.Flag = 1;
 			var box = $that.AddBox([xPosition, yPosition, 0], xWidth, yHeight, 1, 0, true);
         	box.PenColor = vdConst.colorFromString("255,255,255,2000");
//         	box.HatchProperties = hPatrn;
         	$that.UpdateFig(box);
        },
        __updateCadIrrRegularView : function($that,ir,xar,yar){
   
            var vertexes = [vdgeo.newvertex(ir['x'][xar[0]], ir['y'][yar[0]], 0 , 0),vdgeo.newvertex(ir['x'][xar[1]], ir['y'][yar[1]], 0 , 0), vdgeo.newvertex(ir['x'][xar[2]], ir['y'][yar[2]], 0 , 0), vdgeo.newvertex(ir['x'][xar[3]], ir['y'][yar[3]], 0 , 0)];
            var vdPolyline = $that.AddPolyline(vertexes, false);
            vdPolyline.PenColor = { ColorIndex: 0, ColorFlag: null };;
            vdPolyline.Flag = 1;
            
            vertexes = [vdgeo.newvertex(ir['x'][xar[0]], ir['y'][yar[0]], 0 , 0),vdgeo.newvertex(ir['x'][xar[1]], ir['y'][yar[1]], 0 , 0), vdgeo.newvertex(ir['x'][xar[2]], ir['y'][yar[2]], 0 , 0), vdgeo.newvertex(ir['x'][xar[3]], ir['y'][yar[3]], 0 , 0)];
            vdPolyline = $that.AddPolyline(vertexes, false);
            vdPolyline.PenColor = vdConst.colorFromString("255,255,255,2000");
            vdPolyline.HatchProperties = null;
        },
		Open : function(arg) {
			var that = this,
			    target = that.target,
			    opt = that.option;
			vdmanager.vdrawObject('canvas').SelectDocument(contextPath + arg);
			opt.vdcanvas = vdmanager.vdrawObject('canvas');
			
			
	    },
	    ExtendCancvas : function() {
	    	var that = this,
	    	    opt = that.option;
	        var winW, winH;
	       
	        winW = parseInt(opt.width);
	        winH = parseInt(opt.height);
	        vdmanager.vdrawObject('canvas').SetSize(winW,winH);
	    },
	    vertiContent : function(e,target){
			var that = target.data("vectorDrawOpt"),
			    target = that.target,
			    opt = that.option,
			    data = opt.coordinateData,
			    canvas = vdmanager.vdrawObject('canvas'),
			    start = JSON.parse(opt.vectorStartField)[0].columnName,
			    end = JSON.parse(opt.vectorEndField)[0].columnName;

			if(canvas.node != undefined){
				$.each(canvas.node,function(i,item){
					var xmin = item['mouseXmin'],
					    xmax = item['mouseXmax'],
					    startHeight = parseFloat(item[start]),
					    endHeight = parseFloat(item[end]);
					if(e.x >= xmin && e.x <= xmax && e.y >= startHeight && e.y <= endHeight){
						that.setDivToFloat(e,item,target);
						opt.events.mouseOverSpot(item);
						return false;
					}
					else{
						that.relocateDiv(target);
					}
				});
			}
		},
		drawRect : function(data,target,canvas){
			var that = target.data("vectorDrawOpt"),
			    opt = that.option,
			    start = JSON.parse(opt.vectorStartField)[0].columnName,
			    end = JSON.parse(opt.vectorEndField)[0].columnName,
			    xpara = JSON.parse(opt.vectorXField)[0].columnName,
				ypara = JSON.parse(opt.vectorYField)[0].columnName,
				takePart = JSON.parse(opt.takesPart)[0].columnName,
				vectorParam = JSON.parse(opt.vectorParam)[0].columnName,
				ar = [],
				dr = {};
			
			$.each(data,function(i,item){
				if(item[xpara].indexOf(",") != -1){
					var xarr = item[xpara].split(","),
					    yarr = item[ypara].split(","),
					    xmin = parseFloat(xarr[xarr.length - 1]),
					    ymin = parseFloat(yarr[yarr.length - 1]),
					    xmax = xmin,
					    ymax = ymax,
					    irregular = {};
					for(var j = 0 ;j < xarr.length ; j++){
						if(xmin > parseFloat(xarr[j])){
							xmin = parseFloat(xarr[j]);
						} 
						if(xmax < parseFloat(xarr[j])){
							xmax = parseFloat(xarr[j]);
						}
					}
					for(var j = 0 ;j < yarr.length ; j++){
						if(ymin > parseFloat(yarr[j])){
							ymin = parseFloat(yarr[j]);
						}
						if(ymax > parseFloat(yarr[j])){
							ymax = parseFloat(yarr[j]);
						}
					}
					if(xarr.length > 4 ){
						irregular['x'] = xarr;
						irregular['y'] = yarr;
						item['ir'] = irregular;
					}
					item['xmin'] = xmin;
					item['xmax'] = xmax;
					item['ymin'] = ymin;
					item['ymax'] = ymax;
					
					if(dr[xmin + "-"+ xmax] != undefined){
						if(dr[xmin + "-"+ xmax] < item[end]){
							dr[xmin + "-"+ xmax] = item[end];
						}
					} else{
						dr[xmin + "-"+ xmax] = item[end];
					}
					
					ar.push(item);
					
				} else{
					item['xmin'] = parseFloat(item[xpara]);
					item['ymin'] = parseFloat(item[ypara]);
					item['xmax'] = parseFloat(item[xpara]);
					item['ymax'] = parseFloat(item[ypara]);
					ar.push(item);
					if(dr[xmin + "-"+ xmax] != undefined){
						if(dr[xmin + "-"+ xmax] < item[end]){
							dr[xmin + "-"+ xmax] = item[end];
						}
					} else{
						dr[xmin + "-"+ xmax] = ymax;
					}
				}
			});
			canvas.data = ar;
			canvas.dr = dr;
			canvas.end = end;
			canvas.start = start;
			canvas.takePart = takePart;
//			canvas.count = 0;
			canvas.vectorParam = vectorParam;
			
            $.each(ar,function(i,item){
            	var x = item['xmin'];
            	var y = parseFloat(item[end]);
            	var k = item['xmax'] - item['xmin'];
            	var h = parseFloat(item[end]) - parseFloat(item[start]);
            	canvas.AddBox([x, y, 0], k, h, 1, 0, true);
			});
			
			
		},
		//字段坐标值为集合，解析字段x坐标和y坐标
		getCoordinate : function(data,target){
			var opt = target.data("vectorDrawOpt").option,
				xan = JSON.parse(opt.vectorXField),
				ar = [],
				yan = JSON.parse(opt.vectorYField),
				xpara = xan[0].columnName,
				ypara = yan[0].columnName;
			$.each(data,function(i,item){
				if(item[xpara].indexOf(",") != -1){
					var xarr = item[xpara].split(",");
					var yarr = item[ypara].split(",");
					
					for(var j = 0 ;j < xarr.length ; j++){
						item['x'] = parseFloat(xarr[j]);
						item['y'] = parseFloat(yarr[j]);
						var pr = {};
						$.each(item,function(z,node){
							pr[z] = node;
						});
						ar.push(pr);
					}
					
				} else{
					item['x'] = parseFloat(item[xpara]);
					item['y'] = parseFloat(item[ypara]);
					ar.push(item);
				}
				
				
			});
			opt.coordinateData = ar;
			
		},
		_buildTemplateContent : function(data){
			var that = this;
			var templateStr = that.option.template;

			var reg1 = /#:[^#]+#/g;
			// var columns = reg1.exec(templateStr);
			var columns = templateStr.match(reg1);
			$.each(columns,function(i,item){
				var dataItemName = item.substring(2,item.length-1);
				if(!data[dataItemName]){
					dataItemName = dataItemName.toLowerCase();
				}

				templateStr = templateStr.replace(item,data[dataItemName] || "");	
				
			})
			return templateStr;
		},
	    setDivToFloat : function(e,data,target)
		{
	    	var that = target.data("vectorDrawOpt"),
		    	opt = that.option,
		    	winW = document.body.offsetWidth,
		    	winH = document.body.offsetHeight,
		    	html = that._buildTemplateContent(data),
		    	text_Wrapper = target.find(".text_Wrapper");
//			floating = true;	
	    	text_Wrapper[0].innerHTML = html;
	    	
	    	text_Wrapper.css("left",Math.max(e.xPix + 20, 0));
	    	text_Wrapper.css("top",Math.max(e.yPix - 40, 0));
	    	text_Wrapper.css("display","block");
	    	text_Wrapper.css("zIndex",1000);
	    	
			
		},
		relocateDiv : function(target)
		{
			var text_Wrapper = target.find(".text_Wrapper");
			text_Wrapper.css("display","none");
			
			
		},
		
	    
	    
	};

    
   
 
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

