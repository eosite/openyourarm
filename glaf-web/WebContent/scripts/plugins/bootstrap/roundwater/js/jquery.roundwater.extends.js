(function($, window, document, undefined) {
	var plugin = "roundwater", optionKey = plugin + ".options",
	
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
				this.data("roundwater", new Plugin(this, params));
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
			
	};

	$.fn[plugin].methods = {
		_init : function(e){
			var that = this,
		    $target = that.target;
			$.ajax({
				type : "POST",
				url : contextPath + "/mx/form/charts/datas",
				contentType : "application/json",
				dataType : "json",
				data : JSON.stringify({
					'rid' : that.option.rid
				}),
				success : function(msg) {
				  $target.find("img").css("display","none");
				  var canvas = $target.find("canvas");
				  that.option.width != undefined && that.option.width != "" ? canvas.css("width",that.option.width) : canvas.css("width","250px");
				  that.option.height != undefined && that.option.height != "" ? canvas.css("height",that.option.height) : canvas.css("height","250px");
		          that.data = msg[0].data;
		          var ctx = canvas[0].getContext('2d');	
				  //画布属性
				  var mW = parseInt(canvas[0].style.width);
				  var mH = parseInt(canvas[0].style.height) - 50;
				  var lineWidth = 1;		 
				  //圆属性
				  var r = mH / 2; //圆心
				  var cR = r - 16 * lineWidth; //圆半径			 
				  //Sin 曲线属性
				  var sX = 0;
				  var sY = mH / 2;
				  var axisLength = mW; //轴长
				  var waveWidth = 0.015 ; //波浪宽度,数越小越宽 
				  var waveHeight = 6; //波浪高度,数越大越高
				  var speed = 0.09; //波浪速度，数越大速度越快
				  var xOffset = 10; //波浪x偏移量		 
				  ctx.lineWidth = lineWidth;	
				  var nowRange = 0;
				  //画圈函数
				  var IsdrawCircled = false;
		          $.each(that.data,function(i,data){
						if(that.option.roundSource != undefined && that.option.roundSource != ""){
							var rs = eval("("+that.option.roundSource+")");								
								if(that.option.roundSet != undefined && that.option.roundSet != ""){
									$.each(eval("("+that.option.roundSet+")"),function(i,opt){							 	 
										  var config = {
											  mW : mW,
											  mH : mH,
											  lineWidth : lineWidth,
											  r : r,
											  cR : cR,
											  sX : sX,
											  sY : sY,
											  axisLength : axisLength,
											  waveWidth : waveWidth,
											  waveHeight : waveHeight,
											  speed : speed,
											  xOffset : xOffset,
											  IsdrawCircled : IsdrawCircled,
											  nowRange : parseInt(data[rs[0].value[0].en]),
										      title : data[rs[0].title.en],
										      titlefontSize : opt.tfontSize,
										      titleTextColor : opt.ttextColor,
                                              titleWeight : opt.tWeight,
                                              titleStyle : opt.tStyle,
                                              valueWeight : opt.vWeight,
                                              valueStyle : opt.vStyle,
										      valuefontSize : opt.vfontSize,
										      valueTextColor : opt.vtextColor
										  }
										  that.config = config;
								          that.render(this);
									});
								}
								else{			  
								  var config = {
									  mW : mW,
									  mH : mH,
									  lineWidth : lineWidth,
									  r : r,
									  cR : cR,
									  sX : sX,
									  sY : sY,
									  axisLength : axisLength,
									  waveWidth : waveWidth,
									  waveHeight : waveHeight,
									  speed : speed,
									  xOffset : xOffset,
									  IsdrawCircled : IsdrawCircled,
									  nowRange : nowRange,
									  size : parseInt(data[rs[0].value[0].en]),
								      title : data[rs[0].title.en]
								  }
								  that.config = config;
						          that.render(this);
								}
					
						}						
					});	        
				}
			});		  
		},
		render : function(){
			var ctx = this.target.find("canvas")[0].getContext('2d'),config = this.config,obj = this;	
			ctx.clearRect(0, 0, config.mW, config.mH);
			if (config.IsdrawCircled == false) {
				this.drawCircle(obj,config);
			}
			if (config.nowRange <= config.size) {
				var tmp = 1;
				config.nowRange += tmp;
				
			}
			 if(config.nowRange > config.size){
				    var tmp = 1;
				    config.nowRange -= tmp;
				   }
			this.drawSin(obj,config);
			this.drawText(obj,config);
			config.xOffset += config.speed;
			if (config.IsdrawCircled == false) {
				requestAnimationFrame(this.render.bind(this));
			}
			
		},
		drawCircle : function(obj,config) {
			var ctx = obj.target.find("canvas")[0].getContext('2d'),that = obj;
			ctx.beginPath();
			ctx.strokeStyle = that.option.roundBorderColor != undefined && that.option.roundBorderColor != "" ? that.option.roundBorderColor : '#1080d0';
			ctx.shadowColor = that.option.roundBorderColor != undefined && that.option.roundBorderColor != "" ? that.option.roundBorderColor : '#1080d0';
			
			ctx.shadowOffsetX = 1;
			ctx.arc(config.r, config.r, config.cR + 1, 0, 2 * Math.PI);
			ctx.stroke();
			ctx.beginPath();
			ctx.arc(config.r, config.r, config.cR, 0, 2 * Math.PI);
			ctx.clip();
		},
		drawText : function(obj,config){
			var ctx = obj.target.find("canvas")[0].getContext('2d');
			
			
			ctx.save();
			var size = 0.4 * config.cR;
			ctx.font = config.valuefontSize != undefined && config.valuefontSize != "" ?  + config.valuefontSize + 'px Microsoft Yahei' : size + 'px Microsoft Yahei';
			ctx.font = config.valueWeight ? "bold " + ctx.font : ctx.font;
			ctx.font = config.valueStyle ? "italic " + ctx.font : ctx.font;
			ctx.textAlign = 'center';
			ctx.fillStyle = config.valueTextColor;
			ctx.fillText(config.nowRange + '%', config.r, config.r * 0.7 + size / 2 );

			ctx.restore();
			ctx.save();
			var size = 0.4 * config.cR;
			ctx.font = config.titlefontSize != undefined && config.titlefontSize != "" ?  + config.titlefontSize + 'px Microsoft Yahei' : size + 'px Microsoft Yahei';
			ctx.textAlign = 'center';
			ctx.fillStyle = config.titleTextColor;
			ctx.font = config.titleWeight ? "bold " + ctx.font : ctx.font;
			ctx.font = config.titleStyle ? "italic " + ctx.font : ctx.font;
			/*ctx.fontStyle = "itabl"*/
			ctx.fillText(config.title, config.r, config.r + size / 2);
			ctx.restore();
		},
		requestAnimaFrame : function (callback) {
		    return window.requestAnimationFrame(callback) ||
	        window.webkitRequestAnimationFrame(callback) ||
	        window.mozRequestAnimationFrame(callback) ||
	        window.oRequestAnimationFrame(callback) ||
	        window.msRequestAnimationFrame(callback) ||
	        function (callback) {
	            setInterval(callback, 1000 / 60);
	        }
	  },
		//画sin 曲线函数
		  drawSin : function(obj,config){
			var ctx = obj.target.find("canvas")[0].getContext('2d');
			ctx.save();
			var points = []; // 用于存放绘制Sin曲线的点
			ctx.beginPath();
			// 在整个轴长上取点
			for (var x = config.sX; x < config.sX + config.axisLength; x += 20 / config.axisLength) {
				// 此处坐标(x,y)的取点，依靠公式 “振幅高*sin(x*振幅宽 + 振幅偏移量)”
				var y = -Math.sin((config.sX + x) * config.waveWidth + config.xOffset);
				var dY = config.mH * (1 - config.nowRange / 100);
				points.push([ x, dY + y * config.waveHeight ]);
				ctx.lineTo(x, dY + y * config.waveHeight);
			}
			// 封闭路径
			ctx.lineTo(config.axisLength, config.mH);
			ctx.lineTo(config.sX, config.mH);
			ctx.lineTo(points[0][0], points[0][1]);
			ctx.fillStyle = obj.option.roundColor != undefined && obj.option.roundColor != "" ? obj.option.roundColor : '#1c86d1';
			ctx.fill();
			ctx.restore();
		  }
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

