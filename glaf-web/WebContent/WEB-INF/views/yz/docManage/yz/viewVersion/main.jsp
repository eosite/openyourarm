<%@page import="com.glaf.core.config.SystemConfig"%>
<%@page import="com.glaf.core.util.RequestUtils"%>
<%@page import="com.glaf.core.security.LoginContext"%>
<%@page import="com.glaf.chinaiss.query.InterfaceQuery"%>
<%@page import="com.glaf.chinaiss.service.InterfaceService"%>
<%@page import="com.glaf.chinaiss.model.Interface"%>
<%@page import="com.glaf.chinaiss.model.TreepName"%>
<%@page import="com.glaf.chinaiss.query.TreepNameQuery"%>
<%@page import="com.glaf.chinaiss.service.TreepNameService"%>
<%@page import="com.glaf.chinaiss.model.ProjMuiProjList"%>
<%@page import="java.util.List"%>
<%@page import="com.glaf.core.context.ContextFactory"%>
<%@page import="com.glaf.chinaiss.service.ProjMuiProjListService"%>
<%@page import="com.glaf.core.config.Environment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
    pageContext.setAttribute("contextPath", request.getContextPath());
    
    TreepNameService treepNameServcie = (TreepNameService)ContextFactory.getBean("com.glaf.chinaiss.service.treepNameService");
    TreepNameQuery treepNameQuery = new TreepNameQuery();
    List<TreepName> treepNameList = treepNameServcie.list(treepNameQuery);
    TreepName treepName = null;
    if(treepNameList.size()>0){
    	treepName = treepNameList.get(0);
    }
    
    InterfaceService interfaceService = (InterfaceService)ContextFactory.getBean("com.glaf.chinaiss.service.interfaceService");
    InterfaceQuery interfaceQuery = new InterfaceQuery();
    interfaceQuery.setFrmType("pfile");
    interfaceQuery.setIsSystem("1");
    interfaceQuery.setIsListShow("1");
    interfaceQuery.setOrderBy("listno");
    List<Interface> interfaceList = interfaceService.list(interfaceQuery);
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/icons/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/layout/css/styles.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
	function LoginCell(CellWeb){
		CellWeb.Login('炎晟软件','','11100101387','1120-1235-0064-6005');
		CellWeb.LocalizeControl(2052);// (&H804);
	}
</script>
<script type="text/javascript">
	jQuery(function(){
		//加载目录树
		jQuery("#tree").tree({
			url:'<%=request.getContextPath()%>/rs/treepInfoRest/getRootTreeNodeToEasyUIJSONView?parentId=-1',
			lines:true,
			onClick:function(node){
				jQuery("#myTable").datagrid({
					queryParams:{"treepInfoId":node.attributes.treepInfoId,"indexId":node.id}
				});
			},
			onExpand:function(node){
				//展开时load下一层,如果load过不会再load,只是展开
				var pid = node.attributes.parentId;
				if(pid>=-1){
					var a = jQuery(this).tree('getChildren',node.target);
					if(a[0].id==-2){
						getChildData(node, node.id, false);
					}
				}
			}
		});
		
		//加载表格
		jQuery("#myTable").datagrid({
			url:"<%=request.getContextPath()%>/rs/viewFileRest/fileTable",
			onSelect:function(rowIndex,rowData){
				if(rowData){
					setValue(rowData.tname,rowData.duty,rowData.slevel,rowData.ctime,rowData.savetime,rowData.tagnum,rowData.totalNum,rowData.page,rowData.thematic,rowData.carrierType,rowData.summary,rowData.annotations,rowData.pageType,"",rowData.listNum,rowData.vnum);
					jQuery("#listTable").datagrid({url:'<%=request.getContextPath()%>/rs/fileAttRest/getFileAttJSON?topId='+rowData.id_enc});
				}else{
					setValue("","","","","","","","","","","","","","","","");
				}
			},
			onLoadSuccess:function(data){
				if(data.rows!=0){
					//jQuery("#myTable").datagrid("selectRow",0);
					jQuery('#pagination').pagination({total:data.total});
					if(data.total>20){
						jQuery('#pagination').show();
					}
				}else{
					setValue("","","","","","","","","","","","","","","","");
					jQuery('#pagination').hide();
				}
			}
		});
		
		jQuery('#pagination').pagination({
			pageSize:20,
			showRefresh:false,
			showPageList:false,
			beforePageText:'第',
			afterPageText:'页，共{pages}页',
			displayMsg:'显示第 {from} 至 {to} 条记录，共 {total} 条记录',
			onSelectPage:function(pageNumber, pageSize){
				jQuery(this).pagination('loading');
				gotoPage(pageNumber,pageSize);
				jQuery(this).pagination('loaded');
			}
		});
		
		jQuery("#listTable").datagrid({
			onLoadSuccess:function(){
				jQuery("#listTable").datagrid("selectRow",0);
			},
			onSelect:function(rowIndex,rowData){
				fileID_enc = rowData.fileID_enc;
				showPages(0,fileID_enc,"new");
			}
		});
		
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
	 *选择页数
	 */
	function gotoPage(pageNumber,pageSize){
		jQuery("#myTable").datagrid({
			queryParams:{"pageNumber":pageNumber,"pageSize":pageSize}
		});
	}
	
	//设置表单值
	function setValue(tname,duty,slevel,ctime,savetime,tagnum,totalNum,page,thematic,carrierType,summary,annotations,pageType,unit,listNum,vnum){
		jQuery("#tname").val(tname);//文件题名
		jQuery("#duty").val(duty);//责任者
		jQuery("#slevel").val(slevel);//密级
		jQuery("#ctime").val(ctime);//文件时间
		jQuery("#savetime").val(savetime);//	保管期限
		jQuery("#tagnum").val(tagnum);//文件编号
		jQuery("#totalNum").val(totalNum);//总登记号
		jQuery("#page").val(page);//页数
		jQuery("#thematic").val(thematic);//主题词
		jQuery("#carrierType").val(carrierType);//载体类型
		jQuery("#summary").val(summary);//搞要
		jQuery("#annotations").val(annotations);//附注
		jQuery("#pageType").val(pageType);//纸张类型
		//jQuery("#pageType").val(unit);//编制单位
		jQuery("#listNum").val(listNum);//目录号
		jQuery("#vnum").val(vnum);//档号
	}
	
	/**
	  * 异步load子树方法
	  */
	function getChildData(node, pid, flag){
		jQuery.post("<%=request.getContextPath()%>/rs/treepInfoRest/getChildDataMethodView",{parentId:pid},
				function(data){
					var a = jQuery('#tree').tree('getChildren',node.target);
					jQuery('#tree').tree('remove', a[0].target);
					jQuery('#tree').tree('append',{
						parent: (node?node.target:null),
						data:data
					});
				},"JSON"
			);
 	}
	
	function showFile(){
		var row = jQuery("#myTable").datagrid("getSelected");
		var sFeatures = "width="+window.screen.width+",height="+window.screen.height+",top=0,left=0,toolbar=no,menubar=no,status=no,location=no,help=no,center=yes,resizable=no,scroll=no,depended=yes,alwaysRaised=yes";
		window.open("<%=request.getContextPath()%>/mx/docManage/yz/fileview/image/show?method=fileAtt&topId="+row.id_enc,"viewFile",sFeatures);
	}
	
	function logout(){
		if(confirm("您确定要重新登录吗？")){
			var link = '<%=request.getContextPath()%>/mx/login/logout';
			self.location.href = link;
		}
	}
	//以下JS为电子原文中的数据处理
	var countPages = 1;
	var curPageIndex = 0;
	var fileID_enc = "";
	var tmpImage = new Image();
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
		document.getElementById("image").src = "<%=request.getContextPath()%>/rs/imageRest/getFileAttFileContentByFileId?type="+type+"&pageIndex="+pageIndex+"&fileID_enc="+fileID_enc;
		jQuery("#pageIndex").val(curPageIndex+"/"+countPages);
		getCountPages(fileID_enc,"new");
	}
	/**
	 *获取总页数
	 */
	function getCountPages(fileID_enc,type){
		jQuery.get("<%=request.getContextPath()%>/rs/imageRest/getCountPages?fileID_enc="+fileID_enc+"&type="+type,
				function(data){
					countPages = data.countPages;
					jQuery("#pageIndex").val(curPageIndex+"/"+countPages);
				},"JSON"
		);
	}
	/**
	 *前往页数
	 */
	 function gotoImagePage(flag){
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
	
	function searchFrame(){
		var url = "<%=request.getContextPath()%>/mx/docManage/yz/fileview/viewFile/viewVersionSearch";
		art.dialog.open(url, { height: 550, width: 900, title: "检索", lock: false, scrollbars:"no" }, false);
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
<body  class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
<div region="north" split="falst" border="false" style="overflow: hidden; height: 35px;
        background: url(${contextPath}/themes/${theme}/images/top_bar_bg.jpg) #7f99be repeat-x center 50%;
        line-height: 35px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">
		欢迎 <%=RequestUtils.getLoginContext(request).getUser().getName()%>&nbsp; 
		<a href="javascript:logout();">退出登录</a>
        </span>
        <span style="padding-left:10px; font-size: 14px; ">
		<img src="<%=request.getContextPath()%>/layout/images/logo.gif" border="0" align="absmiddle" width="20px" height="20px" /> 
		<span class="sys_name" style="padding-left:5px; font-size: 16px; ">
		<%=SystemConfig.getString("res_system_name")%></span>&nbsp;
	   <span class="sys_version"><%=SystemConfig.getString("res_version")%></span>
      </span>
</div>
	<div title="工程划分WBS"  data-options="region:'west'" style="width:200px">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="width:185px;height:25px;border: 0px">
				<label>工程</label><input type="text" class="easyui-validatebox"  style="width: 155px;background-color: #EEE" readonly="readonly" value="<%=treepName==null?"":treepName.getIndexName()%>"/>
			</div>
			<div data-options="region:'center'" style="width:185px;border: 0px;">
				<ul id="tree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'" style="height:218px;border: 0px;">
				<div class="toolbar-backgroud" style="height: 25px">
					<table border="0" style="width: 100%;height:  25px" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<img src="<%=request.getContextPath() %>/images/window.png">&nbsp;<span class="x_content_title">文件列表</span>
								<a href="javascript:searchFrame();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">检索</a>
								<a href="javascript:showFile();" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-view'">查看</a>
							</td>
							<td  align="right"><div id="pagination" style="width: 470px;display: none"></div></td>
						</tr>
					</table>
				</div>
				<table id="myTable" class="easyui-datagrid" style="width:1600;height:185px" border="0" data-options="idField:'id_enc',singleSelect:true,fitColumns:false,rownumbers:true">
							<thead>
								<tr>
									<th data-options="field:'id_enc',hidden:true">id</th>
									<th data-options="field:'indexId',hidden:true">indexId</th>
									<th data-options="field:'efileNum',align:'center'">原文</th>
									<%
										for(Interface interfaceModel:interfaceList){
											String width = "100";
											String align = "center";
											if(interfaceModel.getDname().equals("tname")){//文件题名
												width = "800";
												align = "left";
											}else if(interfaceModel.getDname().equals("page")){//页数
												width = "50";
											}
									%>
										<th data-options="field:'<%=interfaceModel.getDname() %>',width:<%=width%>,align:'<%=align%>'"><%=interfaceModel.getFname() %></th>
									<%} %>
								</tr>
							</thead>
						</table>
			</div>
			<div title="文件信息" data-options="region:'center'" style="border: 0px">
				<div id="allTabs" class="easyui-tabs" data-options="fit:true" style="border: 0px">
				    <div title="文件著录" data-options="fit:true" style="overflow:auto;">  
				        <table cellpadding="0" cellspacing="6" border="0" >
				        	<tr>
				        		<td align="right" ><label>文件题名</label></td>
				        		<td colspan="3" ><textarea id="tname"   style="width: 100%;height:45px;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"></textarea></td>
				        	</tr>
				        	<tr>
				        		<td align="right" style="width: 5%"><label>责任者</label></td>
				        		<td style="width: 45%"><input id="duty"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" style="width: 5%"><label>密级</label></td>
				        		<td style="width: 45%"><input id="slevel"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>文件时间</label></td>
				        		<td><input id="ctime"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" ><label>保管期限</label></td>
				        		<td><input id="savetime"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>文件编号</label></td>
				        		<td><input id="tagnum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" ><label>总登记号</label></td>
				        		<td><input id="totalNum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>页数</label></td>
				        		<td><input id="page"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" ><label>主题词</label></td>
				        		<td><input id="thematic"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>载体类型</label></td>
				        		<td><input id="carrierType"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" ><label>摘要</label></td>
				        		<td><input id="summary"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>附注</label></td>
				        		<td><input id="annotations"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" ><label>纸张类型</label></td>
				        		<td><input id="pageType"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>编制单位</label></td>
				        		<td><input id="duty1"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" ><label>目录号</label></td>
				        		<td><input id="listNum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        	</tr>
				        	<tr>
				        		<td align="right" ><label>档号</label></td>
				        		<td><input id="vnum"  style="width: 100%;background-color: #EEE" class="easyui-validatebox"  readonly="readonly"/></td>
				        		<td align="right" >&nbsp;</td>
				        		<td>&nbsp;</td>
				        	</tr>
				        </table>
				    </div>
				    <div title="电子原文" data-options="fit:true,selected:true" style="overflow:auto;">
				    
				    	<div id="imageLayout" class="easyui-layout" data-options="fit:true" border="0">
				    		<div id="imageWestLayout" data-options="region:'west',title:'文件列表',collapsed:true" style="width:200px;">
						    	<table id="listTable" class="easyui-datagrid" style="width:auto;height:auto" border="0" data-options="idField:'fileID',singleSelect:true,fitColumns:true,rownumbers:true">
						    		<thead>
										<tr>
											<th data-options="field:'fileID_enc',hidden:true">fileID</th>
											<th data-options="field:'fileID',hidden:true">fileID</th>
											<th data-options="field:'fileName',width:200">文件名称</th>
										</tr>
									</thead>
						    	</table>
						    </div>  
						    <div id="imageCenterLayout" data-options="region:'center'" style="padding:0px;background:#eee;" border="0">
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
								    		<td><img alt="放大" onclick="javascript:zoom(1);" src="<%=request.getContextPath()%>/images/zoom_in_small.png" style="cursor: hand;"></td>
								    		<td>
								    			<img alt="左转90度" onclick="javascript:rotateLeft();" src="<%=request.getContextPath()%>/images/rotate_left.png" style="cursor: hand;"/>
								    			<img alt="右转90度" onclick="javascript:rotateRight();" src="<%=request.getContextPath()%>/images/rotate_right.png" style="cursor: hand;"/>
								    		</td>
								    		<td align="right" width="130px">
								    			<img alt="首页" src="<%=request.getContextPath()%>/images/page_first.gif" onclick="javascript:gotoImagePage(1);"  style="cursor: hand;">
								    			<img alt="上页" src="<%=request.getContextPath()%>/images/page_previous.gif" onclick="javascript:gotoImagePage(2);"  style="cursor: hand;">
								    			<input id="pageIndex"  type="text" value="1" class="easyui-validatebox"  style="width: 40px"/>
								    			<img alt="下页" src="<%=request.getContextPath()%>/images/page_next.gif" onclick="javascript:gotoImagePage(3);"  style="cursor: hand;">
								    			<img alt="尾页" src="<%=request.getContextPath()%>/images/page_last.gif" onclick="javascript:gotoImagePage(4);"  style="cursor: hand;">
								    		</td>
							    		</tr>
						    		</table>
								</div>
								<div style="width: 100%;height: 87%;overflow:auto">
							    	<img id="image" >
								</div>
						    </div>
				    	</div>
				    	
				    </div>
				</div>
			</div>
		</div>
	</div>
	<div region="south" split="false" class="south-backgroud" style="height: 23px;" border="0">
        <div class="footer"><%=SystemConfig.getString("res_copyright")%></div>
	</div>
</body>
</html>