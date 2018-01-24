(function($, window, document, undefined) {
	var plugin = "phonePanel", optionKey = plugin + ".options",
	
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
				this.data("phonePanel", new Plugin(this, params));
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
			    $target = that.target,
			    $opt = that.option;
			that.__resultSet($target,$opt);
			that.__loadData(that);
			that.initPhotoSwipeFromDOM();
		},
		__loadData : function($obj){
			$.ajax($obj.option.read);
		},
		__resultSet : function($target,$opt){
			var that = this;
			$opt.read.success = function(ret){
				$opt.phonelistDataSource = eval("("+$opt.phoneDataSource+")");
				that.__renderTemplate($target,$opt,ret);
				
			}
		},
		__renderTemplate : function($target,$opt,ret){
			var pldataSource = $opt.phonelistDataSource;
			$($target[0]).find(".weui-cells").empty();
			
			$.each(ret[0].data,function(i,data){	
				var imgeUrl = pldataSource[0].imgUrl != undefined ? data[pldataSource[0].imgUrl.en] : undefined;
				var title = pldataSource[0].title.en != undefined ? data[pldataSource[0].title.en] : undefined;
				
				var element = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\">" +
				"<div class=\"weui-cell__hd\"><img src=\""+contextPath + imgeUrl +"\" " +
				"alt=\"\" style=\"width:20px;margin-right:5px;display:block\"></div><div class=\"weui-cell__bd weui-cell_primary\">" +
				"<p>"+ title +"</p></div><span class=\"weui-cell__ft\"></span></a>";
				
				if(pldataSource[0].imgUrl != undefined){
					var element = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\">" +
					"<div class=\"weui-cell__hd\"><img src=\""+contextPath + imgeUrl +"\" " +
					"alt=\"\" style=\"width:30px;height:30px;margin-right:5px;display:block\"></div><div class=\"weui-cell__bd weui-cell_primary\">" +
					"<p>"+ title +"</p></div><span class=\"weui-cell__ft\"></span></a>";
				} else if(pldataSource[0].datasetId != undefined){
					var thumbPath = contextPath +"/mx/form/imageUpload?method=downloadById&id="+data[pldataSource[0].datasetId[0].en];
					var imageName = pldataSource[0].imgName != undefined ? data[pldataSource[0].imgName[0].en] : undefined;   
					
					var element = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\">" +
					"<div class=\"weui-cell__hd\"><img src=\""+thumbPath+"\" " +
					"alt=\"\" style=\"width:50px;height:50px;margin-right:5px;display:block\"></div><div class=\"weui-cell__bd weui-cell_primary\">" +
					"<p>"+ title +"</p></div><span class=\"weui-cell__ft\"></span></a>";
				} else{
					var element = "<a class=\"weui-cell weui-cell_access\" href=\"javascript:;\">" +
					"<div class=\"weui-cell__hd\"><img src=\""+contextPath + imgeUrl +"\" " +
					"alt=\"\" style=\"width:30px;height:30px;margin-right:5px;display:block\"></div><div class=\"weui-cell__bd weui-cell_primary\">" +
					"<p>"+ title +"</p></div><span class=\"weui-cell__ft\"></span></a>";
				}
							
		    	$($target[0]).find(".weui-cells").append(element);		
		     });
		},
		
		
		parseThumbnailElements : function(el) {
			var thumbElements = el.childNodes,
				numNodes = thumbElements.length,
				items = [],
				el,
				childElements,
				thumbnailEl,
				size,
				item;

			for(var i = 0; i < numNodes; i++) {
				el = thumbElements[i];

				// include only element nodes 
				if(el.nodeType !== 1) {
				  continue;
				}

				childElements = el.children;

				size = el.getAttribute('data-size').split('x');

				// create slide object
				item = {
					src: el.getAttribute('href'),
					w: parseInt(size[0], 10),
					h: parseInt(size[1], 10),
					author: el.getAttribute('data-author')
				};

				item.el = el; // save link to element for getThumbBoundsFn

				if(childElements.length > 0) {
				  item.msrc = childElements[0].getAttribute('src'); // thumbnail url
				  if(childElements.length > 1) {
					  item.title = childElements[1].innerHTML; // caption (contents of figure)
				  }
				}


				var mediumSrc = el.getAttribute('data-med');
				if(mediumSrc) {
					size = el.getAttribute('data-med-size').split('x');
					// "medium-sized" image
					item.m = {
						src: mediumSrc,
						w: parseInt(size[0], 10),
						h: parseInt(size[1], 10)
					};
				}
				// original image
				item.o = {
					src: item.src,
					w: item.w,
					h: item.h
				};

				items.push(item);
			}

			return items;
		},
		
		onThumbnailsClick : function(e) {
			var that = this;
			e = e || window.event;
			e.preventDefault ? e.preventDefault() : e.returnValue = false;

			var eTarget = e.target || e.srcElement;
			var closest = function closest(el, fn) {
				return el && ( fn(el) ? el : closest(el.parentNode, fn) );
			};

			var clickedListItem = closest(eTarget, function(el) {
				return el.tagName === 'A';
			});

			if(!clickedListItem) {
				return;
			}

			var clickedGallery = clickedListItem.parentNode;

			var childNodes = clickedListItem.parentNode.childNodes,
				numChildNodes = childNodes.length,
				nodeIndex = 0,
				index;

			for (var i = 0; i < numChildNodes; i++) {
				if(childNodes[i].nodeType !== 1) { 
					continue; 
				}

				if(childNodes[i] === clickedListItem) {
					index = nodeIndex;
					break;
				}
				nodeIndex++;
			}

			if(index >= 0) {
				if(that.openPhotoSwipe == undefined){
					$(that).closest("article").data("photoSwipe").openPhotoSwipe( index, clickedGallery );
				}
				else{
					that.openPhotoSwipe( index, clickedGallery );
				}
				
			}
			return false;
		},
		photoswipeParseHash : function() {
			var hash = window.location.hash.substring(1),
			params = {};

			if(hash.length < 5) { // pid=1
				return params;
			}

			var vars = hash.split('&');
			for (var i = 0; i < vars.length; i++) {
				if(!vars[i]) {
					continue;
				}
				var pair = vars[i].split('=');  
				if(pair.length < 2) {
					continue;
				}           
				params[pair[0]] = pair[1];
			}

			if(params.gid) {
				params.gid = parseInt(params.gid, 10);
			}

			return params;
		},
		openPhotoSwipe : function(index, galleryElement, disableAnimation, fromURL) {
			var that = this,
				$target = that.target,
				$opt = that.option,
				pswpElement = $target.find(".pswp")[0],
				gallery,
				options,
				items,
				that = this;

			items = that.parseThumbnailElements(galleryElement);

			// define options (if needed)
			options = {

				galleryUID: galleryElement.getAttribute('data-pswp-uid'),

				getThumbBoundsFn: function(index) {
					// See Options->getThumbBoundsFn section of docs for more info
					var thumbnail = items[index].el.children[0],
						pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
						rect = thumbnail.getBoundingClientRect(); 

					return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
				},

				addCaptionHTMLFn: function(item, captionEl, isFake) {
					if(!item.title) {
						captionEl.children[0].innerText = '';
						return false;
					}
					captionEl.children[0].innerHTML = item.title +  '<br/><small>Photo: ' + item.author + '</small>';
					return true;
				}
				
			};


			if(fromURL) {
				if(options.galleryPIDs) {
					// parse real index when custom PIDs are used 
					// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
					for(var j = 0; j < items.length; j++) {
						if(items[j].pid == index) {
							options.index = j;
							break;
						}
					}
				} else {
					options.index = parseInt(index, 10) - 1;
				}
			} else {
				options.index = parseInt(index, 10);
			}

			// exit if index not found
			if( isNaN(options.index) ) {
				return;
			}



			var radios = document.getElementsByName('gallery-style');
			for (var i = 0, length = radios.length; i < length; i++) {
				if (radios[i].checked) {
					if(radios[i].id == 'radio-all-controls') {

					} else if(radios[i].id == 'radio-minimal-black') {
						options.mainClass = 'pswp--minimal--dark';
						options.barsSize = {top:0,bottom:0};
						options.captionEl = false;
						options.fullscreenEl = false;
						options.shareEl = false;
						options.bgOpacity = 0.85;
						options.tapToClose = true;
						options.tapToToggleControls = false;
					}
					break;
				}
			}

			if(disableAnimation) {
				options.showAnimationDuration = 0;
			}

			// Pass data to PhotoSwipe and initialize it
			gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);

			// see: http://photoswipe.com/documentation/responsive-images.html
			var realViewportWidth,
				useLargeImages = false,
				firstResize = true,
				imageSrcWillChange;

			gallery.listen('beforeResize', function() {

				var dpiRatio = window.devicePixelRatio ? window.devicePixelRatio : 1;
				dpiRatio = Math.min(dpiRatio, 2.5);
				realViewportWidth = gallery.viewportSize.x * dpiRatio;


				if(realViewportWidth >= 1200 || (!gallery.likelyTouchDevice && realViewportWidth > 800) || screen.width > 1200 ) {
					if(!useLargeImages) {
						useLargeImages = true;
						imageSrcWillChange = true;
					}
					
				} else {
					if(useLargeImages) {
						useLargeImages = false;
						imageSrcWillChange = true;
					}
				}

				if(imageSrcWillChange && !firstResize) {
					gallery.invalidateCurrItems();
				}

				if(firstResize) {
					firstResize = false;
				}

				imageSrcWillChange = false;

			});

			gallery.listen('gettingData', function(index, item) {
				if( useLargeImages ) {
					item.src = item.o.src;
					item.w = item.o.w;
					item.h = item.o.h;
				} else {
					item.src = item.m.src;
					item.w = item.m.w;
					item.h = item.m.h;
				}
			});

			gallery.init();
		},
		initPhotoSwipeFromDOM : function(gallerySelector) {
            var that = this,
            	$target = that.target,
            	$opt = that.option;
			// select all gallery elements
            var galleryElements = $target.find(".demo-gallery");
			for(var i = 0, l = galleryElements.length; i < l; i++) {
				galleryElements[i].setAttribute('data-pswp-uid', i+1);
				galleryElements[i].onclick = that.onThumbnailsClick;
			}

			// Parse URL and open gallery if it contains #&pid=3&gid=1
			var hashData = that.photoswipeParseHash();
			if(hashData.pid && hashData.gid) {
				that.openPhotoSwipe( hashData.pid,  galleryElements[ hashData.gid - 1 ], true, true );
			}
		}
	};
	$.extend(true, Build.prototype, {
		contructor : Build
	}, $.fn[plugin].methods);

})(window.jQuery || window.Zepto, window, document);

