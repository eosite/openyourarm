<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
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
<title>表单布局列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style scoped>
        .main-section {
                width: 960px;
                margin: 10px auto;
                border: 0;
                background: none;
            }
        #listView {
            padding: 10px 5px;
            margin-bottom: -1px;
            min-height: 510px;
        }
        .layout {
            float: left;
            position: relative;
            width: 450px;
            height: 350px;
            margin: 0 5px;
            padding: 0;
        }
        .layout img {
            width: 400px;
            height: 300px;
        }
        .layout h3 {
            margin: 0;
            padding: 3px 5px 0 0;
            max-width: 96px;
            overflow: hidden;
            line-height: 1.1em;
            font-size: .9em;
            font-weight: normal;
            text-transform: uppercase;
            color: #999;
        }
        .layout p {
            visibility: hidden;
        }
        .layout:hover p {
            visibility: visible;
            position: absolute;
            width: 110px;
            height: 110px;
            top: 0;
            margin: 0;
            padding: 0;
            line-height: 110px;
            vertical-align: middle;
            text-align: center;
            color: #fff;
            background-color: rgba(0,0,0,0.75);
            transition: background .2s linear, color .2s linear;
            -moz-transition: background .2s linear, color .2s linear;
            -webkit-transition: background .2s linear, color .2s linear;
            -o-transition: background .2s linear, color .2s linear;
        }
        .k-listview:after {
            content: ".";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden;
        }
</style>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	          onclick="javascript:addRow();">新增</button>               
   </div>
</script>
<script type="text/x-kendo-template" id="template2">
    <div class="layout">
        <span style="font-size:14px;">&nbsp;&nbsp;#:name#</span>
		&nbsp;&nbsp;<a href="\\#" onclick="javascript:preview('#:id#', '#:name#');" style="font-size:14px;">预览</a>
		<br>
		<img src="<%=request.getContextPath()%>/#= imagePath #" alt="#: name # 布局图片" style="cursor:pointer;"
		     onclick="javascript:updateRow('#:id#');"/>
    </div>
</script>

<script type="text/x-kendo-tmpl" id="editTemplate">
    <div class="layout-view k-widget">
        <div class="edit-buttons">
            <a class="k-button k-button-icontext k-update-button" href="\\#"
			   onclick="javascript:updateRow('#:id#');"><span class="k-icon k-update"></span></a>
        </div>   
    </div>
</script>

<!-- <script src="<%=request.getContextPath()%>/deploy/form/layout/layout.js"></script> -->
<script type="text/javascript">
	
  jQuery(function() {
         
		 /**
	      var dataSource = new kendo.data.DataSource({
                data: layouts,
                pageSize: 21
            });
	     **/
        
	    var dataSource = new kendo.data.DataSource({
            transport: {
                read: {
                        url: "<%=request.getContextPath()%>/rs/form/layout/json?refreshJS=true",
						contentType: "application/json",
                        dataType: "json"
                    }
                },
			batch: true,
            pageSize: 4
        });


        jQuery("#pager").kendoPager({
            dataSource: dataSource
        });

        jQuery("#listView").kendoListView({
            dataSource: dataSource,
			toolbar: kendo.template(jQuery("#template").html()),
            template: kendo.template(jQuery("#template2").html()),
			editTemplate: kendo.template(jQuery("#editTemplate").html())
        });

		jQuery(".k-add-button").click(function(e) {
                listView.add();
                e.preventDefault();
         });

    });


    jQuery(document.body).keydown(function(e) {
            if (e.altKey && e.keyCode == 87) {
                jQuery("#listView").focus();
            }
        });

    function addRow(){
         editRow('<%=request.getContextPath()%>/mx/form/layout/showImport');
    }

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑表单布局信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['630px', (jQuery(window).height() - 50) +'px'],
			iframe: {src: link}
		});
	}

	function updateRow(id){
		var link = "<%=request.getContextPath()%>/mx/form/layout/edit?id="+id
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑表单布局信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['630px', (jQuery(window).height() - 50) +'px'],
			iframe: {src: link}
		});
	}

	function showImport(id){
		var link = "<%=request.getContextPath()%>/mx/form/layout/showImport"
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑表单布局信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['630px', (jQuery(window).height() - 50) +'px'],
			iframe: {src: link}
		});
	}


    function preview(id, title){
		var link = "<%=request.getContextPath()%>/mx/form/layout/preview?id="+id;
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: title,
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function layoutPreview(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "预览布局",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['880px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
 <div class="x_content_title">
  <img src="<%=request.getContextPath()%>/images/window.png" alt="表单布局列表">&nbsp;
    表单布局列表
 </div>
 
 
<div class="main-section">
     <div class="k-header">
	    <br/>&nbsp;&nbsp;
        <a class="k-button k-button-icontext k-add-button" href="#" onclick="javascript:showImport();">
		 <span class="k-icon k-add"></span>上传布局模板</a>
		<br/>
		<br/>
     </div>
     <div id="listView"></div>
     <div id="pager" class="k-pager-wrap"></div>
</div>
</div>     
</body>
</html>