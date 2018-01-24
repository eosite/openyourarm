<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="com.alibaba.fastjson.*"%>
<%@ page import="com.glaf.core.security.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
 
	LoginContext loginContext = RequestUtils.getLoginContext(request);
	List<UserPortal> userPortals = (List<UserPortal>)request.getAttribute("userPortals");
	UserPanel userPanel = (UserPanel)request.getAttribute("userPanel");
	String layoutName = null;
	List<Panel> panels01 = new ArrayList<Panel>();
	List<Panel> panels02 = new ArrayList<Panel>();
	List<Panel> panels03 = new ArrayList<Panel>();
	StringBuffer sb = new StringBuffer(); 
	List alPanels=new ArrayList();
    JSONArray arr = new JSONArray();
	int columnQty = 2;
	if(userPanel != null){
	  layoutName = userPanel.getLayoutName();
	}
	if(layoutName==null){
		layoutName="P2";
	}
	if("P3".equals(layoutName)){
		columnQty=3;
	}
	if(userPortals != null && !userPortals.isEmpty()){
	  Set paneIds = new HashSet();
	  for(UserPortal p: userPortals){
		 if(p.getPanel() != null && p.getPanel().getLocked() == 0){
			 if(paneIds.contains(p.getPanel().getId())){
				 continue;
			 }
			 paneIds.add(p.getPanel().getId());
			 JSONObject jsonObject = new JSONObject();
             jsonObject.put("id", p.getPanel().getId());
			 jsonObject.put("title", p.getPanel().getTitle());
			 jsonObject.put("custom",p.getPanel().getCustom());//边框
			 jsonObject.put("zborder",p.getPanel().getBorder());//边框
			 jsonObject.put("zheader",p.getPanel().getHeader());//头
			 jsonObject.put("ziframe",p.getPanel().getZiframe());//头
			 if(p.getPanel().getHeight() > 0){
			     jsonObject.put("height", p.getPanel().getHeight());
			 } else {
				 jsonObject.put("height", 300);
			 }
			 jsonObject.put("closable", p.getPanel().canClosable());
			 jsonObject.put("collapsible", p.getPanel().canCollapsible());
			 
             if(p.getPanel().getContent() != null && p.getPanel().getContent().trim().length()>0 ){
                  jsonObject.put("href", request.getContextPath()+"/mx/panel/content?pid="+p.getPanel().getId());
			 } else {
                 if(p.getPanel().getLink() != null){
					if(p.getPanel().getLink().startsWith("/mx/") || p.getPanel().getLink().startsWith("/website/")){
			           jsonObject.put("href", request.getContextPath()+p.getPanel().getLink());
					} else {
					   jsonObject.put("href", p.getPanel().getLink());
					}
			   }
			 }
			 if(jsonObject.get("href") == null){
			    continue;
			 }
			 
			 if(p.getPanel().getPanelButtons() != null && p.getPanel().getPanelButtons().size() > 0){
				 jsonObject.put("panelButtons", p.getPanel().getPanelButtons());
			 }

			 arr.add(jsonObject);
             

		     if(0 == p.getColumnIndex()){
                panels01.add(p.getPanel());
			 } else if(1 == p.getColumnIndex()){
                panels02.add(p.getPanel());
			 } else if(2 == p.getColumnIndex()){
				 if("P3".equals(layoutName)){
                    panels03.add(p.getPanel());
				 } else {
					panels01.add(p.getPanel());
				 }
			 } else {
				 panels01.add(p.getPanel());
			 }
		 }
	   }
	 }

	 Iterator iter = panels01.iterator();
     while(iter.hasNext()){
         Panel p = (Panel)iter.next();
		 sb.append(p.getId());
		 if(iter.hasNext()){
			 sb.append(",");
		 }
	 }

	 
	sb.append(":");
	 
   
	 iter = panels02.iterator();
     while(iter.hasNext()){
         Panel p = (Panel)iter.next();
		 sb.append(p.getId());
		 if(iter.hasNext()){
			 sb.append(",");
		 }
	 }
      
	 if("P3".equals(layoutName)){
		 sb.append(":");
		
		 iter = panels03.iterator();
		 while(iter.hasNext()){
			 Panel p = (Panel)iter.next();
			 sb.append(p.getId());
			 if(iter.hasNext()){
				 sb.append(",");
			 }
		 }
	 }
	
	String defaultHeight="height:250px;";
    ////System.out.println(arr.toJSONString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/themes/${theme}/easyui.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/scripts/easyui/css/portal.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/site.css" />
<link link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icons.css">
<style type="text/css">
	html {
		height: 100%;
		width: 99%;
	}
	
	body {
		height: 100%;
		width: 99%;
	}
	.mx_title{
			font-size:13px;
			font-weight:bold;
			padding:5px 10px;
			background:#eee;
			overflow:hidden;
			border-bottom:1px solid #ccc;
	}
		<%
	for(UserPortal up : userPortals){
		Panel panel = up.getPanel();
		if(panel != null && panel.getPanelButtons() != null && panel.getPanelButtons().size() > 0 ){
			PanelButton pb = panel.getPanelButtons().get(0);
	%>
			.icon<%=panel.getId()%>{
				background:url('<%=request.getContextPath()%><%=pb.getImgUrl()%>') no-repeat
			}
	<%	}
	}%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/easyui/jquery.portal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript">

function layerOpen(link){	
	jQuery.layer({
		type: 2,
		maxmin: true,
		shadeClose: true,
		closeBtn: [0, true],
		shade: [0.8, '#000'],
		border: [3, 3, '#000'],
		offset: ['0px',''],
		fadeIn: 100,
		area: [(jQuery(window).width()-10)+'px', (jQuery(window).height() - 10) +'px'],
        iframe: {src: link}
	});
}
function locationOpen(link){
	window.location.href  = link + "&reback=true" ;
}
		var panels =  <%=arr.toJSONString()%>;
		function getPanelOptions(id){
			for(var i=0; i<panels.length; i++){
				if (panels[i].id == id){
					return panels[i];
				}
			}
			return undefined;
		}

		function getPortalState(){
			var aa = [];
			for(var columnIndex=0; columnIndex < <%=columnQty%>; columnIndex++){
				var cc = [];
				var panels = $('#pp').portal('getPanels', columnIndex);
				for(var i=0; i<panels.length; i++){
					cc.push(panels[i].attr('id'));
				}
				aa.push(cc.join(','));
			}
			return aa.join(':');
		}

		function addPanels(portalState){
			var columns = portalState.split(':');
			for(var columnIndex=0; columnIndex<columns.length; columnIndex++){
				var cc = columns[columnIndex].split(',');
				for(var j=0; j<cc.length; j++){
					var options = getPanelOptions(cc[j]);
					if (options){
						var p = $('<div/>').attr('id',options.id).appendTo('body');
						if(1 == options.ziframe){
							$('<iframe/>').attr("src",options.href).attr("style","width:100%;height:99%;border:0px").appendTo(p);
							options.href = "" ;
						}
						<%if(!"true".equals(request.getParameter("edit"))){%>
								getTools(options) ;	
						<%}%>
						p.panel(options);
						$('#pp').portal('add',{
							panel:p,
							columnIndex:columnIndex
						});
					}
				}
			}
		}
		
		$(function(){
			$('#pp').portal({
				fit:true,
				border:false,
				onStateChange:function(){
					$("#mx_toolbar").show();
				}
			});
			var state  = '<%=sb.toString()%>'; 
			addPanels(state);
			$('#pp').portal('resize');
		});

		//获取自定义工具条
		function getTools(panel){
			var tools = [] ;
			if(panel.custom==1){
				if(panel.panelButtons){
					var panelButton = panel.panelButtons[0] ;
					panel.border = panel.zborder==0?true:false; //边框
					panel.noheader = panel.zheader==1?true:false ; //头
					var d = {iconCls:'icon'+panel.id,
							handler:function(){
								var url = "<%=request.getContextPath()%>" + panelButton.href ; 
								if(panelButton.openType == 0 ){
									locationOpen(url);
								}else if(panelButton.openType == 1){
									layerOpen(url);
								}else if(panelButton.openType == 2){
									window.open(url); 
								}
							}} ;  
					tools.push(d);
				}
			}
			var refresh ;
			if(panel.ziframe == 1){
				refresh = {iconCls:'icon-reload',handler:function(){var iframe = $('#'+panel.id +' > iframe');iframe.attr('src',iframe.attr('src'));}} ;
			}else{
				refresh = {iconCls:'icon-reload',handler:function(){$('#'+panel.id).panel('open').panel('refresh');}} ;
			}
			tools.push(refresh);
			panel.tools = tools ;
		}
		
		$(function(){
			for(var i=0; i<panels.length; i++){
				var panelId = '#' + panels[i].id;
				<%if(!"true".equals(request.getParameter("edit"))){%>
				//addReload(panelId);
				<%}%>
			}
		});
		function addReload(panelId){//panel增加刷新按钮
			$(panelId).panel({   
				tools: [{   
				iconCls:'icon-reload',   
				handler:function(){
					    $(panelId).panel('open').panel('refresh');
					}   
			  }]  
			}); 
		}

		function savePortal(){
             var state = getPortalState();
             var dashboardId = jQuery('#dashboardId').val();
			 //alert(state);
			 $.ajax({
				type: "POST",
				url:"<%=request.getContextPath()%>/mx/user/portal/savePortal",
				data:{"portalState":state,'dashboardId':dashboardId},
				success:function(){
					alert('保存成功！')
					window.location.reload();								
				},
				error:function(){
					alert('保存失败！');
					return;
				}
			 });
		}

        function openMoreMsg() {
	        openWindow('<%=request.getContextPath()%>/mx/workspace/message/showReceiveList', 600, 450);
	    }

	    function openMsg(id) {
		    openWindow('<%=request.getContextPath()%>/mx/workspace/message/showMessage?id=' + id, 600, 450);
	    }
	    function changePortal(value){
	    	var link = "<%=request.getContextPath()%>/mx/user/portal?edit=true&dashboardId="+value;
	    	window.location.href = link ;
	    }
</script>
</head>
<body class="easyui-layout">

    <%if("true".equals(request.getParameter("edit"))){%>
	<div id="mx_toolbar" region="north" class="mx_title" border="false" > 
	  <div  style="text-align:right;padding-right:10px;" >
		<%List<Dictory> dashboards = BaseDataManager.getInstance().getDictoryList("dashboard"); 
			if(dashboards != null && dashboards.size() > 0){
		%>
			布局分类：<select id="dashboardId" name="dashboardId" onchange="changePortal(this.value)">
			<%	for (Dictory dictory : dashboards) {
			%>
				<option value="<%=dictory.getCode()%>"  ><%=dictory.getName()%></option>
			<%}%>
			</select>
		<%}%>
		 <a href="#" onClick="javascript:location.reload();" target="_self"><span><img src="<%=request.getContextPath()%>/images/refresh.gif" border="0"/>&nbsp;刷新</span></a>
		&nbsp;<a href="#" id="subButton" onclick="javascript:savePortal();"><span><img src="<%=request.getContextPath()%>/images/save.gif" border="0"/>保存</span></a>
	  </div>
	</div>
	<%}%>
	<div region="center" border="false">
		<div id="pp" style="position:relative">
		  <%if("P3".equals(layoutName)){%>
		    <div style="width:28%;">
			</div>
			<div style="width:40%;">
			</div>
			<div style="width:28%;">
			</div>
		  <% }else{ %>
			<div style="width:60%; ">
			</div>
			<div style="width:25%;">
			</div>
		 <%}%>
		</div>
	</div>
	<script type="text/javascript">
	 if('${dashboardId}' != ''){
		    jQuery('#dashboardId').val('${dashboardId}');
	    }
	</script>
</body>
</html>