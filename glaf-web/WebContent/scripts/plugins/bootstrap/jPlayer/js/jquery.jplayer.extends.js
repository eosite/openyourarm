(function($, window, document, undefined) {
	var plugin = "jplayer", optionKey = plugin + ".options",
	
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
				this.data("jplayer", new Plugin(this, params));
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
			    target = that.target,
			    opts = that.option;
			that.target.find(".jp-type-playlist").css("height",that.option.height);
			that.target.find(".jp-type-playlist").css("overflow-y","auto");
			opts.read.data.params != undefined && opts.read.data.params != null ? that._ajaxData(opts) : null; 
			
		},
		_createData : function(ret){
			var that = this;
			var config = new jPlayerPlaylist({
				jPlayer: "#jquery_jplayer_2",
				cssSelectorAncestor: "#jp_container_2"
			}, ret , {
				swfPath: "js/jplayer",
				supplied: "oga, mp3,wav",
				wmode: "window",
				useStateClassSkin: true,
				autoBlur: false,
				smoothPlayBar: true,
				keyEnabled: true
			});
			that.option.config = config;
		},
		_ajaxData : function(opts){	
			var that = this;
			$.ajax({
				url : opts.read.url,
				async : opts.read.async,
	            data : JSON.stringify(opts.read.data),
	            type : 'post',
	            dataType: 'JSON', 
	            contentType: "application/json",
	            success : function(ret){
	            	
		            	that._createData(that._convertData(ret[0].data,opts));
	            	
	            }
			});
			
			
		},
		_convertData : function(ret,opts){
		    var title = opts.jplayerSource != undefined && opts.jplayerSource != null ? opts.jplayerSource[0].title : null,
		        source = opts.jplayerSource != undefined && opts.jplayerSource != null ? opts.jplayerSource[0].source : null;	    
			$.each(ret,function(i,r){
				$.each(r,function(y,data){
					$.each(title,function(j,t){
						if(y == t){
							r.title = data;
						}
					});
					$.each(source,function(j,s){
						if(y == s.en){
							r['mp3'] = contextPath + data;
							r['oga'] = contextPath + data;
							r['wav'] = contextPath + data;
							r['flv'] = contextPath + data;
						}
					});
				})
				
			});
			return ret;
		}
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);




















