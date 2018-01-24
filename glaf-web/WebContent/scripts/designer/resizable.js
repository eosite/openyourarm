//验证当前浏览器内核环境
var Sys = (function(ua){ 
    var s = {}; 
    s.IE = ua.match(/msie ([\d.]+)/)?true:false; 
    s.Firefox = ua.match(/firefox\/([\d.]+)/)?true:false; 
    s.Chrome = ua.match(/chrome\/([\d.]+)/)?true:false; 
    s.IE6 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6))?true:false; 
    s.IE7 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 7))?true:false; 
    s.IE8 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 8))?true:false; 
    return s; 
})(navigator.userAgent.toLowerCase());
//闭包限定命名空间
(function ($) {
    //默认参数
    var defaluts = {
        //允许拖拽的方位
		handles: 'n,e,s,w,ne,se,sw,nw',
		//拖拽点样式名称
		draggCss:{
        ncss: 'ui-resizable-n',
		ecss: 'ui-resizable-e',
		scss: 'ui-resizable-s',
		wcss: 'ui-resizable-w',
		necss: 'ui-resizable-ne',
		secss: 'ui-resizable-se',
		swcss: 'ui-resizable-sw',
		nwcss: 'ui-resizable-nw'
		},
		linked:'[data-role]:first'
    };
	var methods = {
        init : function(options) {
			//支持多选择器多对象
			return this.each(function () {				
			    //支持链式调用
                var $this = $(this);
				var opts = $.extend({}, defaluts, options); //覆盖插件默认参数
				if(!isValid(opts)){
					return;
				}
				var handles=opts.handles.split(',');
				var draggCss=opts.draggCss;
				var handlecss;
				$.each(handles,function(i,handle){
					handlecss=draggCss[handle+"css"];
					$this.remove("."+handlecss);
					$this.append("<div class=\""+handlecss+"\"></div>");
				});
				$this.data("options",opts);
				if(opts['linked']!=''){
				  $this.data("linkedItems",$this.find(opts['linked']));
				}
				$this.myresizable("set"); 
            });
        },
		set:function(){
			//获取当前设置可拖动的点
			var opts=this.data("options");
			if(!isValid(opts)){
				return;
			}
			var handles=opts.handles.split(',');
			var draggCss=opts.draggCss;
			var that=this;
			var draggelem;
			//绑定拖动事件
			$.each(handles,function(i,handle){
				 draggelem=draggCss[handle+"css"];
				 var params=new Object();
				 params.direct=handle;
				 params.draggelem=draggelem;
				 that.myresizable("addListener",params); 
			});
		},
		addListener:function (params){
			var direct=params.direct;
			var draggelem=params.draggelem;
			draggelem=this.find("."+draggelem);
			draggelem.data('direct',direct);
			//获取当前元素的位置信息
			var that=this;
			draggelem.mousedown(function(e){
				var width=that.width();
				var height=that.height();
				var left = that.position().left;
                var top = that.position().top;
				that.original = [width,height,left,top];
				that._width=(that.original[2]||0) + that.original[0]; 
				that._height=(that.original[3]||0) + that.original[1];
				if(that._diffTop==undefined)
				{
				   that._diffTop=that.offset().top-that.position().top;
				}
				if(that._diffWidth==undefined){
				 
				   that._diffWidth=that.offset().left-that.position().left;
				}
				//绑定鼠标移动事件
				var direct=$(this).data('direct');
				var draggelem=$(this);
				$(document).bind("mousemove",function(e){
					that.myresizable("resize",draggelem,direct,e);
				});
				//绑定鼠标释放事件
				$(document).bind("mouseup",function(e){
					that.myresizable("stop",draggelem);
				});
			});
		},
		stop : function(e){
			//解除鼠标移动事件绑定
			$(document).unbind("mousemove");
			//window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();     
		},
        //缩放入口方法		
		resize:function(draggElem,direct,e){
			this.myresizable("resize_"+direct,draggElem,e);
			var that=this;
			Sys.IE?(draggElem[0].onlosecapture=function(){that.myresizable("stop",$(this));}):(draggElem[0].onblur=function(){that.myresizable("stop",$(this));}) 
			
		},
		//右侧缩放
		resize_e:function(draggElem,e){
			if(e.clientX>(this.original[2]+this._diffWidth)){
				    this.css("left",this.original[2]+'px');
					this.css("width",e.clientX-this.original[2]-this._diffWidth+'px');
				if(this.data("linkedItems")){
					this.data("linkedItems").css("width",e.clientX-this.original[2]-this._diffWidth-2+'px');
				}
			}
			else {
				this.myresizable("turnLeft",e); 
			}
		},
		//下方缩放
		resize_s:function(draggElem,e){
			if(e.clientY>(this.original[3]+this._diffTop)){
				this.css("top",this.original[3]+'px');
			    this.css("height",(e.clientY-this.original[3]-this._diffTop)+'px');
				if(this.data("linkedItems")){
					this.data("linkedItems").css("height",(e.clientY-this.original[3]-this._diffTop)-2+'px');
				}
			}else{
			        this.myresizable("turnUp",e); 
			}
		},
		//左侧缩放
		resize_w:function(draggElem,e){
			if(e.clientX<(this._width+this._diffWidth)){
				this.css("left",(e.clientX-this._diffWidth)+'px');
			    this.css("width",this._width+this._diffTop-e.clientX+'px');
				if(this.data("linkedItems")){
					//this.data("linkedItems").css("left",(e.clientX-this._diffWidth)+'px');
					this.myresizable("position_",this.data("linkedItems"));
					this.data("linkedItems").css("width",this._width+this._diffTop-e.clientX-2+'px');
				}
			}
			else {
				this.myresizable("turnRight",e); 
			}				
		},
		//上方缩放
		resize_n:function(draggElem,e){
			if(this._height+this._diffTop>e.clientY){
				this.css("top",(e.clientY-this._diffTop)+'px');
			    this.css("height",this._height+this._diffTop-e.clientY +'px');
				if(this.data("linkedItems")){
					//this.data("linkedItems").css("top",(e.clientY-this._diffTop)+'px');
					this.myresizable("position_",this.data("linkedItems"));
					this.data("linkedItems").css("height",this._height+this._diffTop-e.clientY-2 +'px');
				}
			}else{
			        this.myresizable("turnDown",e); 
			}
		},
		//右下缩放
		resize_se:function(draggElem,e){
			 this.myresizable("resize_e",draggElem,e); 
			 this.myresizable("resize_s",draggElem,e); 
		},
		//右上缩放
		resize_ne:function(draggElem,e){
			 this.myresizable("resize_e",draggElem,e); 
			 this.myresizable("resize_n",draggElem,e); 
		},
		//左下缩放
		resize_sw:function(draggElem,e){
			 this.myresizable("resize_w",draggElem,e); 
			 this.myresizable("resize_s",draggElem,e); 
		},
		//左上缩放
		resize_nw:function(draggElem,e){
			 this.myresizable("resize_w",draggElem,e); 
			 this.myresizable("resize_n",draggElem,e); 
		}, 
		//上边界跨过下边界向下
		turnDown : function(e){
			this.css("top",(this._height+'px'));
			this.css("height",e.clientY - this._height-this._diffTop+'px');
			if(this.data("linkedItems")){
					//this.data("linkedItems").css("top",(this._height+'px'));
					this.myresizable("position_",this.data("linkedItems"));
			        this.data("linkedItems").css("height",e.clientY - this._height-this._diffTop-2+'px');
				}
        },
         //下边界跨过上边界向上	
		turnUp : function(e){ 
			this.css("top",e.clientY-this._diffTop+'px');
			this.css("height",this.original[3]+this._diffTop - e.clientY+'px');
			if(this.data("linkedItems")){
					//this.data("linkedItems").css("top",e.clientY-this._diffTop+'px');
					this.myresizable("position_",this.data("linkedItems"));
			        this.data("linkedItems").css("height",this.original[3]+this._diffTop - e.clientY-2+'px');
				}
		}, 
		 //左边界跨过右边界向右	
		turnRight : function(e){ 
			this.css("left",(this._width)+'px');
			this.css("width",(e.clientX - this._width-this._diffWidth)+'px');
			if(this.data("linkedItems")){
					//this.data("linkedItems").css("left",(this._width)+'px');
					this.myresizable("position_",this.data("linkedItems"));
			        this.data("linkedItems").css("width",(e.clientX - this._width-this._diffWidth)-2+'px');
				}
		}, 
		 //右边界跨过左边界向左
		turnLeft : function(e){ 
			this.css("left",e.clientX-this._diffWidth+'px');
			this.css("width",(this.original[2]+this._diffWidth-e.clientX)+'px'); 
            if(this.data("linkedItems")){
					//this.data("linkedItems").css("left",e.clientX-this._diffWidth+'px');
					this.myresizable("position_",this.data("linkedItems"));
			        this.data("linkedItems").css("width",(this.original[2]+this._diffWidth-e.clientX)-2+'px'); 
				}			
		},
		position_:function(linkedItems){
			linkedItems.css("position","relative");
			//var ol=this.offset().left;
			//var ot=this.offset().top;
			//linkedItems.offset({left:ol,top:ot});
		}
    };
	$.fn.myresizable = function(method) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.myresizable' );
			return this;
        }    
    };
	//私有方法，检测参数是否合法
    function isValid(options) {
        return !options || (options && typeof options === "object") ? true : false;
    }
})(window.jQuery);