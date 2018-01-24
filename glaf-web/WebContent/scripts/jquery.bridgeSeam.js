 /**
  * 桥缝显示
  *  	$("id").bim(options); // 初始化
  *  	$("id").bim('setValue',options); //赋值等
  */
 (function($) {
 	var pluginName = "bridgeSeam";


 	$.fn[pluginName] = function(command, options) {
 		if (!this.length) {
 			return this;
 		}
 		if (typeof command === "object") {
 			options = command;
 			var opts = $.extend({}, $.fn[pluginName].defaults, options);
 			this.each(function() {
 				var $this = $(this);
 				if (!$this.data(pluginName)) {
 					$this.data(pluginName, opts);
 					methods.__init__.call($this, opts);
 				}
 			});
 		} else if (typeof command === "string" && methods[command]) {
 			this.each(function() {
 				var $this = $(this);
 				methods[command].call($this, options);
 			});
 		} else {
 			$.error('Method ' + command + ' does not exist on jQuery.' + pluginName + "!");
 		}

 		return this;
 	};

 	var methods = $.fn[pluginName].methods = {
 		query: function(params) {
 			var $this = $(this),
 				opts = $this.data(pluginName);
 			if (opts) {
 				$this.find(".circle").attr("fill", opts.circleFill);
 				var data = JSON.parse(opts.data);
 				if (params) {
 					data.params = JSON.stringify(params);
 				}
 				$.ajax({
 						url: opts.url,
 						type: 'POST',
 						contentType: "application/json",
 						dataType: 'json',
 						data: JSON.stringify(data),
 					})
 					.done(function(data) {
 						methods.__showMaintain__.call($this, data);
 					})
 					.fail(function(e) {
 						console.error(e);
 					})
 					.always(function() {});

 			}
 		},
 		__showMaintain__: function(datas) {
 			var $this = $(this),
 				opts = $this.data(pluginName),
 				key,
 				arys,
 				source,
 				linkArys = [];
 			var _findSource = function(source, id) {
 				var ret;
 				for (var i = 0; i < source.length; i++) {
 					ret = source[i];
 					if (ret["id"] == id) {
 						return ret;
 					}
 				}
 				return ret;
 			}
 			if (datas && datas.length) {
 				$.each(datas, function(i, val) {
 					arys = val["data"];
 					key = val["id"];
 					source = _findSource(opts.chartSource, key);
 					$.each(arys, function(index, obj) {
 						$this.find(".circle_" + obj[source["xAxisName"]["en"]] + "_" + obj[source["yAxisName"]["en"]]).attr("fill", opts.circleMaintainFill);
 					});
 				});
 			}
 		},
 		__init__: function(opts) {
 			var $this = this;
 			//容器
 			var con = d3.select("#" + $this.attr("id"));
 			//创建画布
 			var svg = con.append('svg').attr("height", "100%").attr("width", "100%"),
 				height = svg.style("height").replace("px", "") - 0,
 				width = svg.style("width").replace("px", "") - 0,
 				padding = opts.padding,
 				innerHeight = height - padding.top - padding.bottom,
 				innerWidth = width - padding.left - padding.right;
 			//顶梁柱的倾斜角度
 			var angles = opts.angles,
 				vsize = angles.length,
 				//数据长度
 				dataset = [];
 			for (var i = 0; i < vsize; i++) {
 				dataset.push(1);
 			}
 			//顶梁柱宽度
 			var dsize = vsize,
 				rectWidth = (innerWidth) / dsize,
 				//顶梁柱之间的空白
 				vPadding = width / dsize - opts.vWidth;

 			//创建比例尺
 			var x = d3.scaleBand()
 				.domain(dataset)
 				.rangeRound([0, width])
 				.padding(0.1);

 			//创建x轴
 			var xAxis = d3.axisBottom()
 				.scale(x);

 			//y轴比例尺
 			var y = d3.scaleLinear()
 				.domain([0, d3.max(dataset)])
 				.range([innerHeight, 0]);

 			//创建x轴
 			var yAxis = d3.axisLeft()
 				.scale(y);

 			//创建支撑梁
 			svg.selectAll(".vrect")
 				.data(dataset)
 				.enter()
 				.append("rect")
 				.attr("class", "vrect")
 				.attr("x", function(d, i) {
 					return rectWidth * i + vPadding / 2;
 				})
 				.attr("y", function(d, i) {
 					return y(d);
 				})
 				.attr("width", rectWidth - vPadding)
 				.attr("height", function(d, i) {
 					return innerHeight - y(d);
 				})
 				.attr("transform", function(d, i, ary) {
 					var x_ = rectWidth * i + vPadding / 2,
 						y_ = y(d),
 						w = rectWidth - vPadding,
 						h = innerHeight - y(d),
 						pl = padding.left,
 						pt = padding.top,
 						angle = angles[i],
 						rotate = "";
 					if (angle > 0) {
 						rotate = "rotate(" + angle + " " + (x_ + w + pl) + "," + (y_ + h + pt) + ")";
 					} else if (angle < 0) {
 						rotate = "rotate(" + angle + " " + (x_ + pl) + "," + (y_ + h + pt) + ")";
 					}
 					return rotate + " translate(" + pl + "," + pt + ")";
 				})
 				// 填充颜色
 				.attr("fill", "#fff")
 				.attr("fill-opacity", "0")
 				.attr("stroke", "#000");

 			//添加横向数据
 			var hsize = opts.hSize,
 				hwidth = innerWidth,
 				hdataset = [],
 				hheight = (height * opts.hFill - padding.top - padding.bottom) / hsize,
 				hpadding = opts.hpadding,
 				hRectHeight = hheight - hpadding;
 			for (var i = 0; i < hsize; i++) {
 				hdataset.push(hwidth);
 			}
 			svg.selectAll(".hrect")
 				.data(hdataset)
 				.enter()
 				.append("rect")
 				.attr("class", "hrect")
 				.attr("x", function(d, i) {
 					return padding.left;
 				})
 				.attr("y", function(d, i) {
 					return height - padding.bottom - (hheight * (i + 1)) + hpadding;
 				})
 				.attr("width", function(d, i) {
 					return d;
 				})
 				.attr("height", function(d, i) {
 					return hRectHeight;
 				})
 				.attr("fill", "#fff")
 				.attr("fill-opacity", "0")
 				.attr("stroke", "#000");

 			var cdataset = [],
 				r = (hRectHeight - 2) / 2,
 				vl = dataset.length,
 				dl = hdataset.length,
 				tanConst = Math.PI / 180;
 			for (var i = 1; i <= vl; i++) {
 				for (var j = 1; j < dl; j++) {
 					cdataset.push([i, j]);
 				}
 			}

 			svg.selectAll(".circle")
 				.data(cdataset)
 				.enter()
 				.append("circle")
 				.attr("class", function(d) {
 					return "circle circle_" + d[0] + "_" + d[1];
 				})
 				.attr("cx", function(d, i) {
 					var angle = angles[d[0] - 1],
 						offset = 0,
 						angleOffset = (d[1] * hheight - (hRectHeight / 2)) / Math.tan((90 - Math.abs(angle)) * tanConst);
 					if (angle > 0) {
 						offset += angleOffset;
 					} else if (angle < 0) {
 						offset -= angleOffset;
 					}
 					return rectWidth * (d[0] - 1) + vPadding / 2 + (rectWidth - vPadding) / 2 + offset;
 				})
 				.attr("cy", function(d, i) {
 					return height - padding.bottom - (hheight * (d[1])) + hpadding + r;
 				})
 				.attr("transform", function(d, i, ary) {
 					return " translate(" + padding.left + ",0)";
 				})
 				.attr("r", r)
 				.attr("fill", function(d, i) {
 					return opts.circleFill;
 				})
 				.attr("stroke", "#000");

 			methods.query.call($this);
 		}
 	};

 	// default options
 	var defaults = $.fn[pluginName].defaults = {
 		//与容器的内边距
 		padding: {
 			left: 30,
 			right: 30,
 			top: 20,
 			bottom: 20
 		},
 		//顶梁柱的宽度
 		vWidth: 20,
 		//顶梁柱的角度 默认为17根
 		angles: [15, 16, 13, 8, 7, 6, 5, 4, 0, -4, -5, -6, -7, -8, -13, -16, -15],
 		//横柱的数量
 		hSize: 26,
 		//横柱的填充比例
 		hFill: 0.9,
 		//横柱间的间隙
 		hpadding: 4,
 		//圆点填充颜色
 		circleFill: "#87CEFA",
 		//圆点维修填充颜色
 		circleMaintainFill: "red",
 		url: "",
 		data: {}
 	};

 })(jQuery);