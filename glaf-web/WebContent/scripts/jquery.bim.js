 /**
  * bim 三维图
  *  	$("id").bim(options); // 初始化
  *  	$("id").bim('setValue',options); //赋值等
  */
 (function($) {
 	var pluginName = "bim",
 		_documentId = "_documentId";

 	function getToken(callback,opts) {
 		var that = this;
 		if (opts.tokenUrl && !opts.accessToken) {
 			$.ajax({
 					url: opts.tokenUrl
 				})
 				.done(function(data) {
 					opts.accessToken = data ;
 					callback.call(that);
 				})
 				.fail(function() {
 					console.log("error");
 				})
 				.always(function() {
 					console.log("complete");
 				});

 		}else{
 			callback.call(that);
 		}
 	}

 	$.fn[pluginName] = function(command, options) {
 		var self = this;
 		if (!self.length) {
 			return self;
 		}
 		if (typeof command === "object") {
 			options = command;
 			var opts = $.extend(true, {}, $.fn[pluginName].defaults, options);
 			getToken.call(self, function(){
 				return self.each(function() {
	 				var $this = $(this);
	 				$this.data(pluginName, opts);
	 				if($this.data(_documentId)){
	 					opts.documentId = opts.urn + $this.data(_documentId);
	 				}
	 				if (opts.documentId) {
	 					methods.__init__.call($this, opts);
	 				}
	 			});
 			},opts);
 		} else if (typeof command === "string" && methods[command]) {
 			return methods[command].apply(self, Array.prototype.slice.call(
					arguments, 1));
 		} else {
 			$.error('Method ' + command + ' does not exist on jQuery.bim');
 		}
 		return self;
 	};

 	
 	function GetNode(treeObj, id){
		return {
			id : id, 
			name : treeObj.getNodeName(id),
			nodeType : treeObj.getNodeType(id),
			parentId : treeObj.getNodeParentId(id),
			root : treeObj
		};
	}
 	
 	var methods = $.fn[pluginName].methods = {
 		hasMethod : function(method){
 			return (method in methods);
 		},
 			setValue: function(value) {
 			var $this = $(this),
 				opts = $this.data(pluginName);
 				if(opts){
					opts.documentId = opts.urn+value;
	 				methods.__init__.call($this, opts);
 				}else{
 					$this.data(_documentId,value);
 				}
 		},
 		__init__: function(opts) {
 			var $this = this;
 			Autodesk.Viewing.Initializer({
 				env: opts.env,
 				accessToken: opts.accessToken
 			}, function onInitialized() {
 				Autodesk.Viewing.Document.load(opts.documentId, function(doc) {
 					var viewables = Autodesk.Viewing.Document.getSubItemsWithProperties//
 						(doc.getRootItem(), opts.docOptions, true);
 					if (viewables.length === 0) {
 						opts.onDocumentLoadSuccessNoViewables.call();
 						return;
 					}
 					viewer = new Autodesk.Viewing.Private.GuiViewer3D($this[0]);
 					viewer.start(doc.getViewablePath(viewables[0]), {
 						sharedPropertyDbPath: doc.getPropertyDbPath()
 					}, function(model){
 						$this.data("autodesk", {
 							doc : doc,
 							model : model,
 							viewer : viewer
 						});
 						opts.onLoadModelSuccess.apply(this, arguments); 	

						/**
						 *
						 */
						viewer.addEventListener(Autodesk.Viewing.SELECTION_CHANGED_EVENT,//
							opts.onSelect);
						
					}, opts.onLoadModelError);
					
 				}, opts.onDocumentLoadFailure);
 			});
 		},
 		/**
 		 * 获取模型节点树
 		 */
 		getObjectTree : function(callback){
 			var collection = [], each = function(treeObj, id, c){
 				var node = GetNode(treeObj, id);
 				c.push(node);
 				if(treeObj.getChildCount(id)){
 					node.children = [];
 					treeObj.nodeAccess.enumNodeChildren(id, function(a, b, c){
 	 					each(treeObj, a, node.children);
 	 				});
 				}
 			};
 			viewer.getObjectTree(callback || function(treeObj){
 				each(treeObj, treeObj.getRootId(), collection);
 			});
 			return collection;
 		},
 		
 		GetNodeById : function(tree, id){
 			var node = null;
 			if(!tree){
 				viewer.getObjectTree(function(treeObj){
 	 				node = GetNode(treeObj, id);
 	 			});
 			} else {
 				node = GetNode(tree, id);
 			}
 			return node;
 		},
 		
 		/**
 		 * 聚焦
 		 */
 		fitToView : function(id){
 			viewer.fitToView(id);
 		},
 		/**
 		 * 显示
 		 */
 		show : function(id){
 			viewer.show(id);
 		},
 		/**
 		 * 隔离
 		 */
 		isolate : function(id){
 			viewer.isolate(id);
 		},
 		select : function(id){
 			viewer.select(id);
 		},
 		select2View : function(id){
 			window.setTimeout(function() {
 				viewer.select(id);
 				window.setTimeout(function() {
 					viewer.fitToView(id);
 				}, 0);
			}, 0);
 		},
 		GetSelection : function(){
 			var nodes = [], $this = $(this),//
 				opts = $this.data(pluginName) || {};
 			var selects = viewer.getSelection();
 			if(selects && selects.length){
 				$.each(selects, function(i, v){
 					var n = $this.bim("GetNodeById"//
 						, null, v);
 					nodes.push($.extend(true, n, {
						_documentId : opts[_documentId]
					}));
 				});
 			}
 			return nodes;
 		}
 	};

 	// default options
 	var defaults = $.fn[pluginName].defaults = {
 		urn: 'urn:',
 		env: 'AutodeskProduction',
 		tokenUrl: null,
 		accessToken: '',
 		documentId: '',
 		docOptions: {
 			'type': 'geometry'
 		},
 		onDocumentLoadSuccessNoViewables: function() {

 		},
 		onDocumentLoadFailure: function(viewerErrorCode) {

 		},
 		onLoadModelSuccess: function(model) {

 		},
 		onLoadModelError: function(viewerErrorCode) {

 		},
		/**
		 * 客户端调用点击事件
		 */
		onSelect : function(e){
			console.log(e);
		}
 	};

 })(jQuery);