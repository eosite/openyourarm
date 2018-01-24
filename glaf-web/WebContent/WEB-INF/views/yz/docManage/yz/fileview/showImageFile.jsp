<%@page contentType="text/html; charset=UTF-8" %>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看文件</title>
<%@include file="../inc/script.jsp" %>
<script type="text/javascript">
	var countPages = 1;
	var curPageIndex = 0;
	var fileID_enc = "";
	var tmpImage = new Image();
	jQuery(function(){
		jQuery("#listTable").datagrid({
			onLoadSuccess:function(){
				jQuery("#listTable").datagrid("selectRow",0);
			},
			onSelect:function(rowIndex,rowData){
				fileID_enc = rowData.fileID_enc;
				showPages(0,fileID_enc,"new");
			}
		});
		jQuery("#listTable").datagrid("loadData",<%=(org.json.JSONArray)request.getAttribute("imgArr")%>);
		//选择百分比
		jQuery("#percentSelect").combobox({
			onSelect:function(rec){
				if(rec.value==-1){//适合宽度
					jQuery("#image").width("100%");
					var percent = jQuery("#image").width() * 100 / getImageWidth();
					var height = parseInt(getImageHeight() * percent / 100);
					jQuery("#image").height(height);
				}else if(rec.value==-2){//实际大小
					setImageOptions(getImageWidth(),getImageWidth());
				}else{
					var height = getImageHeight() * rec.value / 100;
					var width = getImageWidth() * rec.value / 100;
					setImageOptions(width,height);
				}
			}
		});
		
		jQuery("#percentSelect").combobox("select",-1);
	});
	
	/*
	 *缩放图片
	 */
	function zoom(flag){
		var width = "100%";
		var height = "100%";
		if(flag==-1){//缩小
			width = jQuery("#image").width()*(1-5/100);
			height = jQuery("#image").height()*(1-5/100);
		}else if(flag==1){//放大
			width = jQuery("#image").width()*(1+5/100);
			height = jQuery("#image").height()*(1+5/100);
		}
		setImageOptions(width,height);
	}
	/*
	 *设置图片高度
 	 */
	function setImageOptions(width,height){
		jQuery("#image").height(height);
		jQuery("#image").width(width);
	}
	
	/*
	 *获取图片原始高度
	 */
	 function getImageHeight(){
		if(tmpImage.src!=document.getElementById("image").src){
			tmpImage.src = document.getElementById("image").src;
		}
		return tmpImage.height;
	}
	
	 /*
	  *获取图片原始宽度
	  */
	 function getImageWidth(){
		if(tmpImage.src!=document.getElementById("image").src){
			tmpImage.src = document.getElementById("image").src;
		}
		return tmpImage.width;
	}
	
	/**
	 *获取图片数据
	 */
	function showPages(pageIndex,fileID_enc,type){
		curPageIndex = pageIndex + 1;
		document.getElementById("image").src = "<%=request.getContextPath()%>/rs/imageRest/getFileAttFileContentByFileId?useDatabase=${useDatabase}&type="+type+"&pageIndex="+pageIndex+"&fileID_enc="+fileID_enc;
		jQuery("#pageIndex").val(curPageIndex+"/"+countPages);
		getCountPages(fileID_enc,"new");
	}
	/**
	 *获取总页数
	 */
	function getCountPages(fileID_enc,type){
		jQuery.get("<%=request.getContextPath()%>/rs/imageRest/getCountPages?useDatabase=${useDatabase}&fileID_enc="+fileID_enc+"&type="+type,
				function(data){
					countPages = data.countPages;
					jQuery("#pageIndex").val(curPageIndex+"/"+countPages);
				},"JSON"
		);
	}
	/**
	 *前往页数
	 */
	 function gotoPage(flag){
		if(flag==1){
			//首页
			showPages(0,fileID_enc,"");
		}else if(flag==2){
			//上页
			if(curPageIndex==1){
				return;
			}
			showPages(curPageIndex-2,fileID_enc,"");
		}else if(flag==3){
			//下页
			if(curPageIndex==countPages){
				return;
			}
			showPages(curPageIndex,fileID_enc,"");
		}else if(flag==4){
			//尾页
			showPages(countPages-1,fileID_enc,"");
		}
	}
	 
	var curValue = 0;
	function rotateLeft(){
		curValue = (curValue==0?3:--curValue);
		document.getElementById("image").style.filter = "	progid:DXImageTransform.Microsoft.BasicImage(Rotation="+curValue+")";
	}
	function rotateRight(){
		curValue = (curValue==3?0:++curValue);
		document.getElementById("image").style.filter = "	progid:DXImageTransform.Microsoft.BasicImage(Rotation="+curValue+")";
	}
</script>
</head>
<body  class="easyui-layout">
	    <div data-options="region:'west',title:'文件列表'" style="width:250px;">
	    	<table id="listTable" class="easyui-datagrid" style="width:auto;height:auto" border="0" data-options="idField:'fileID',singleSelect:true,fitColumns:true,rownumbers:true">
	    		<thead>
					<tr>
						<th data-options="field:'fileID_enc',hidden:true">fileID</th>
						<th data-options="field:'fileID',hidden:true">fileID</th>
						<th data-options="field:'fileName',width:100">文件名称</th>
					</tr>
				</thead>
	    	</table>
	    </div>  
	    <div id="centerDIV" data-options="region:'center'" style="padding:0px;background:#eee;">
	    	<div class="toolbar-backgroud" align="center">
	    		<table>
		    		<tr>
		    			<td><img alt="缩小" onclick="javascript:zoom(-1);" src="<%=request.getContextPath()%>/images/zoom_out_small.png" style="cursor: hand;"></td>
			    		<td width="60px">
				    		<select id="percentSelect" class="easyui-combobox" >
				    			<option value="-1" selected="selected">适合宽度</option>
				    			<option value="-2">实际大小</option>
				    			<%for(int i=20;i<=200;i=i+20){ %>
				    			<option value="<%=i%>" ><%=i+"%" %></option>  
							    <%} %>
				    		</select>
			    		</td>
			    		<td><img alt="放大" onclick="javascript:zoom(1);" src="<%=request.getContextPath()%>/images/zoom_in_small.png" style="cursor: hand;"/></td>
			    		<td>
			    			<img alt="左转90度" onclick="javascript:rotateLeft();" src="<%=request.getContextPath()%>/images/rotate_left.png" style="cursor: hand;"/>
			    			<img alt="右转90度" onclick="javascript:rotateRight();" src="<%=request.getContextPath()%>/images/rotate_right.png" style="cursor: hand;"/>
			    		</td>
			    		<td align="right" width="130px">
			    			<img alt="首页" src="<%=request.getContextPath()%>/images/page_first.gif" onclick="javascript:gotoPage(1);"  style="cursor: hand;"/>
			    			<img alt="上页" src="<%=request.getContextPath()%>/images/page_previous.gif" onclick="javascript:gotoPage(2);"  style="cursor: hand;"/>
			    			<input id="pageIndex"  type="text" value="1" class="easyui-validatebox"  style="width: 40px"/>
			    			<img alt="下页" src="<%=request.getContextPath()%>/images/page_next.gif" onclick="javascript:gotoPage(3);"  style="cursor: hand;"/>
			    			<img alt="尾页" src="<%=request.getContextPath()%>/images/page_last.gif" onclick="javascript:gotoPage(4);"  style="cursor: hand;"/>
			    		</td>
		    		</tr>
	    		</table>
			</div>
			<div style="width: 100%;height: 94%;overflow:auto">
		    	<img id="image"  />
			</div>
	    </div>  
</body>
</html>