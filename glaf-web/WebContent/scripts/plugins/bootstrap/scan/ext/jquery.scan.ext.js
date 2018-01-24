 /**
  *     扫描ocx
  *  	$("id").scan(options); // 初始化
  *  	$("id").scan('upload',url); //上传文件
  */
 (function($) {
     var pluginName = "scan";


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
             //this.each(function() {
                 var $this = $(this);
                 return methods[command].call($this, options);
             //});
         } else {
             $.error('Method ' + command + ' does not exist on jQuery.' + pluginName + "!");
         }

         return this;
     };

     function setOutPutName(outputNames, names) {
         var opns = outputNames.split(",");
         for (var k = 0; k < opns.length; k++) {
             outputName = opns[k];
             if (outputName) {
                 $("#" + outputName).val(names);
             }
         }
     }


     var methods = $.fn[pluginName].methods = {
         getValue: function() {
             return $(this).attr("attachmentId");
         },
         getParent: function() {
             return $(this).attr("randomparent");
         },
         loadFile: function(params) {
             var $this = $(this),
                 $obj = $this.find("#" + $this.attr("id") + "_obj"),
                 obj = $obj[0];
             try {
                 var loadUrl = window.location.origin + contextPath + "/mx/upload/file/downloadById?id=" + params+"&_="+new Date().getTime();
                 obj.LoadFile(loadUrl, params);
             } catch (e) {
                 console.log(e);
             }
         },
         upload: function(params) {
             var $this = $(this);
             //设置文件路径
             if (params) {
                 try {
                     var params = $.parseJSON(params);
                     $this.attr({
                         attachmentId: params.id,
                         randomparent: params.parent
                     });
                     var outputName = $this.attr("outputName");
                     if (outputName) {
                         setOutPutName(outputName, params.fileName);
                     }
                     var outputId = $this.attr("outputId");
                     if (outputId) {
                         setOutPutName(outputId, params.id);
                     }
                     window.$alert("保存成功");
                     var events = $("body").data("wpfscanSavedEvents");
                     events && events.call();
                 } catch (e) {
                     window.$alert("保存失败");
                 }
             }
         },
         __init__: function(opts) {
             var $this = this;
         }
     };

     // default options
     var defaults = $.fn[pluginName].defaults = {};

 })(jQuery);