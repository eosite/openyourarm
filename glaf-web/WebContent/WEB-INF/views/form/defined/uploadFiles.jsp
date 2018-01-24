<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script type="text/javascript">
   	jQuery(document).ready(function() {
   		var id = '${id}',type = '${type}',pageCategory = '${pageCategory}',sortType = '${sortType}';
   		
   		var url = "<%=request.getContextPath() %>/mx/form/defined/saveFormPage?" + $.param({
   			id : id,
   			type : type,
   			formType : 0 ,
   			sortType : sortType,
   			pageCategory : pageCategory
   		});
	    $("#files").kendoUpload({
	    	async: {
	            saveUrl: url
	        },
	    	multiple : true,
	    	showFileList: true,
	    	success: function(){
	    		
	    	},
	    	upload: function(e){
	    		var files = e.files;
			    $.each(files, function () {
			        if (this.extension.toLowerCase() != ".html") {
			            alert("请上传html文件");
			            e.preventDefault();
			        }
			    });
	    	},
	    	error: function(){
	    		
	    	}
        });
	    
	    if(type == ''){
		    $('#addEmptyTemp').show();
	    }
	    
		$("#uiTypeSelect").kendoDropDownList({
			  dataSource: new kendo.data.DataSource({
				  	transport: {
					    read: {
					      url: '<%=request.getContextPath()%>/mx/form/defined/getDictByCode',
					      data : {code : 'ui_type'},
						  type : 'post',
						  dataType : 'json',
					    }
				  	}
		      }),
			  dataTextField: "name",
			  dataValueField: "code",
			  value: "bootstrap",
			  animation: false
		});

    	var validatable = $("#addEmptyTemp").kendoValidator({
    		required : "必填", 
        }).data('kendoValidator');
	    //保存空节点
	    $('#savePage').on('click',function(){
	        if (validatable.validate() === false) {
	          	return;
	        }
	    	var surl = "<%=request.getContextPath() %>/mx/form/defined/saveFormPage" ;
	    	$.ajax({
				url : surl,
				data : {
					id : id,
					type : type,
		   			formType : 0 ,
		   			name : $('#formName').val(),
		   			uiType : $('#uiTypeSelect').data('kendoDropDownList').value(),
		   			sortType:sortType,
		   			pageCategory : pageCategory
		   		},
				type : 'post',
				dataType : 'json',
				async : false,
				success:function(data){ 
					parent.mtxx.zTree.reAsyncChildNodes(null, "refresh");
					parent.layer.close(parent.layer.getFrameIndex());
				},
				error : function(data){
					alert('保存失败，请稍后重试！');
				}
			});
	    });
	});  
 </script>
</head>
<body style="margin-top:0px;">
	<div class="toolbar-backgroud" style="height:48px;line-height: 48px;"> 
		<input type="file" name="files" id="files"  accept="html/plain"/>
	</div>
	
	<div id="addEmptyTemp" style="display: none;">
		<hr>
		<p align="center">---------以下为增加空模板---------</p>
		<hr>
		<table>
			<tr>
				<td>模板名称:</td>
				<td><input id="formName" type="text" name="模板名称" class="k-textbox" required ></td>
			</tr>
			<tr>
				<td>UI类型:</td>
				<td><select id="uiTypeSelect" ></td>
			</tr>
		</table>
		<br>
		<button id="savePage" class="k-button" >保存</button>
	</div>
	
</body>
</html>