<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>  
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/glaf-base.js"></script>
</head>
<body >
    <div>
        <form method="post" class="" id="uploadForm" style="text-align: center;" enctype="multipart/form-data">
            <label class="control-label">文件名称：
                <input name="fileName" class="form-control" type="text" >
            </label>

            <label class="control-label">图片：
                <input id="uploadFile" class="form-control" placeholder="Choose File" disabled="disabled" />
                <div class="fileUpload k-button ">
                    <span><i class="fa fa-search"></i>选择图片</span>
                    <!-- <button class="k-button" id="sure-1" data-role="button" role="button" aria-disabled="false" tabindex="0">选择文件</button> -->
                    <input id="uploadBtn" type="file" name="file" class="upload" accept=".gif,.png,.jpg" />
                </div>
                <button class="k-button" type="button" id="uploadImg">确认上传</button>
                <script type="text/javascript">
                    document.getElementById("uploadBtn").onchange = function () {
                        document.getElementById("uploadFile").value = this.value;
                    };

                </script>
            </label>
        </form>
        
        
    </div>
    <div class="demo-section k-header">
        <div id="listView"></div>
        <div id="pager" class="k-pager-wrap"> </div>
        <div style="float:right;margin-top: 5px; ">
        	<button class='k-button' id='sure-1' >确定</button>
        </div>
    </div>

	<script type="text/x-kendo-tmpl" id="template">
			<div class="product">
				<img src="<%=request.getContextPath()%>#:imagePath#" alt="#:name# image" />
				<h3>#:name#</h3>
			</div>
	</script>

	<script>
        $(document).ready(function() {
            $("#uploadImg").click(function(){
                var $this = $(this);
                if($this.attr("disabled")){
                    return;
                }
                if(!$("#uploadFile").val()){
                    alert("未选择导入的文件！");
                    return;
                }

                var FileController = "<%=request.getContextPath()%>/mx/image/upload";                    // 接收上传文件的后台地址 
                var xhr = new XMLHttpRequest();
                xhr.open("post", FileController, true);
                xhr.onload = function (oEvent) {
                    if (xhr.status == 200) {
                        var ret = xhr.responseText;
                        if(ret){
                            ret = JSON.parse(ret);
                            if(ret.status == "200"){
                                alert("上传图标成功");
                                location.href = location;
                            }else{
                                alert("上传失败");
                            }
                        }else{
                            alert("异常错误")
                        }
                    }else{
                        alert("异常错误")
                    }
                    // $this.attr("disabled");
                    $this.removeAttr("disabled");;
                };
                xhr.oncomponent = function(oEvent){

                }
                var formdata = new FormData($("#uploadForm")[0]);
                $this.attr("disabled",true);
                xhr.send(formdata); //发送请求
            });

            var dataSource = new kendo.data.DataSource({
                    transport: {
                        read: {
                            url: "<%=request.getContextPath()%>/mx/image/data?rows=10000",
                            dataType: "json"
                        }
                    },
                    pageSize: 36
                });

            $("#pager").kendoPager({
                dataSource: dataSource
            });

            var $listView = $("#listView").kendoListView({
                dataSource: dataSource,
                selectable: "single",
                //change: onChange,
                template: kendo.template($("#template").html())
            });
            
            $("#sure-1").kendoButton({
            	click : function(e){
            		var kendoListView = $listView.data("kendoListView");
            		var selectedItem = null;
                    var data = dataSource.view(),
                        selected = $.map(kendoListView.select(), function(item) {
    					    selectedItem = data[$(item).index()];
                            return selectedItem.imagePath;
                        });
    					
    					/* var parent_window = getOpener();
    					if(parent_window != null){
    	                    var x_elementId = parent_window.document.getElementById("${elementId}");
    						x_elementId.value = selected.join(", ");
    					} else {
    						var x_elementId = parent.window.document.getElementById("${elementId}");
    						x_elementId.value = selected.join(", ");
    					} */
    					
    					
    					var parent_window = getOpener() || parent.window;
    					
    					if(parent_window){
    						var x_elementId = "${elementId}",fn = "${fn}";
    						var element = parent_window.document.getElementById(x_elementId);
    						if(element){
    							element.value = selected.join(", ");
    						}
    						if(fn){//调用父页面方法
    							parent_window[fn].call(element,selected.join(", "));
    						}
    					}
            	}
            });

            function onChange() {
				var selectedItem = null;
                var data = dataSource.view(),
                    selected = $.map(this.select(), function(item) {
					    selectedItem = data[$(item).index()];
                        return selectedItem.imagePath;
                    });
					
					/* var parent_window = getOpener();
					if(parent_window != null){
	                    var x_elementId = parent_window.document.getElementById("${elementId}");
						x_elementId.value = selected.join(", ");
					} else {
						var x_elementId = parent.window.document.getElementById("${elementId}");
						x_elementId.value = selected.join(", ");
					} */
					
					
					var parent_window = getOpener() || parent.window;
					
					if(parent_window){
						var x_elementId = "${elementId}",fn = "${fn}";
						var element = parent_window.document.getElementById(x_elementId);
						if(element){
							element.value = selected.join(", ");
						}
						if(fn){//调用父页面方法
							parent_window[fn].call(element,selected.join(", "));
						}
					}
					
            }
    });

	 
    </script>

    <style>
        .fileUpload {
            position: relative;
            overflow: hidden;
            /*margin: 10px;*/
            margin-left: 0px;
        }

        .fileUpload input.upload {  position: absolute;
            top: 0;
            right: 0;
            margin: 0;
            padding: 0;
            font-size: 20px;
            cursor: pointer;
            opacity: 0;
            filter: alpha(opacity=0);
        }

        .demo-section {
            padding: 10px;
            width: 982px;
        }
        
        .product
        {
            float: left;
            width: 90px;
            height: 75px;
            margin: 0;
            padding: 5px;
            cursor: pointer;
        }
        .product img
        {
            float: left;
			width: 16px;
            height: 16px;
        }
        .product h3
        {
            margin: 0;
            padding: 10px 0 0 10px;
            font-size: .9em;
            overflow: hidden;
            font-weight: normal;
            float: left;
            max-width: 100px;
        }
        .k-pager-wrap
        {
            border-top: 0;
        }
        .demo-section .k-listview:after
        {
            content: ".";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden;
        }
       .demo-section .k-listview
        {
            padding: 0;
            min-width: 290px;
            min-height: 260px;
        }
    </style>
 
</body>
</html>