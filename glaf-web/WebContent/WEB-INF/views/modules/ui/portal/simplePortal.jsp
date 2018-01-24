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
	Map<String, String> linkMap = new HashMap<String, String>();
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
			 if(p.getPanel().getHeight() > 0){
			     jsonObject.put("height", p.getPanel().getHeight());
			 } else {
				 jsonObject.put("height", 300);
			 }
			 jsonObject.put("closable", p.getPanel().canClosable());
			 jsonObject.put("collapsible", p.getPanel().canCollapsible());
			 
             if(p.getPanel().getContent() != null && p.getPanel().getContent().trim().length()>0 ){
                  jsonObject.put("href", request.getContextPath()+"/mx/panel/content?pid="+p.getPanel().getId());
				  linkMap.put(p.getPanel().getId(), request.getContextPath()+"/mx/panel/content?pid="+p.getPanel().getId());
			 } else {
                 if(p.getPanel().getLink() != null){
					if(p.getPanel().getLink().startsWith("/mx/") || p.getPanel().getLink().startsWith("/website/")){
			           jsonObject.put("href", request.getContextPath()+p.getPanel().getLink());
					   linkMap.put(p.getPanel().getId(), request.getContextPath()+p.getPanel().getLink());
					} else {
					   jsonObject.put("href", p.getPanel().getLink());
					   linkMap.put(p.getPanel().getId(), p.getPanel().getLink());
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/styles.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/${theme}/site.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>		
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script type="text/javascript">
</script>
</head>
<body style="margin:5px">
<table>
  <tbody>
    <tr>
		  <%if("P3".equals(layoutName)){%>
		    <td style="width:25%;" valign="top" >
              <table>
			   <% for(Panel panel:panels01){
			      if(linkMap.get(panel.getId())!=null){
					String link = linkMap.get(panel.getId());
					int height = 300;
					if(panel.getHeight() > 50){
						height = panel.getHeight();
					}
			   %>
			    <tr>
				 <td align="left">
				 <div id="panel_<%=panel.getId()%>"  ></div>
					<script type="text/javascript">
						jQuery("#panel_<%=panel.getId()%>").load("<%=link%>");
					</script>
				 </td>
				<tr>
				<% }
				}%>
			  </table>
			</td>
			<td style="width:50%;" >
              <table>
			   <% for(Panel panel:panels02){
			      if(linkMap.get(panel.getId())!=null){
					String link = linkMap.get(panel.getId());
					int height = 300;
					if(panel.getHeight() > 50){
						height = panel.getHeight();
					}
			   %>
			    <tr>
				 <td align="left">
				 <div id="panel_<%=panel.getId()%>" ></div>
					<script type="text/javascript">
						jQuery("#panel_<%=panel.getId()%>").load("<%=link%>");
					</script>
				 </td>
				<tr>
				<% }
				}%>
			  </table>
			</td>
			<td style="width:25%;" valign="top">
             <table>
			   <% for(Panel panel:panels03){
			      if(linkMap.get(panel.getId())!=null){
					String link = linkMap.get(panel.getId());
					int height = 300;
					if(panel.getHeight() > 50){
						height = panel.getHeight();
					}
			   %>
			    <tr>
				 <td align="left">
				 <div id="panel_<%=panel.getId()%>" ></div>
					<script type="text/javascript">
						jQuery("#panel_<%=panel.getId()%>").load("<%=link%>");
					</script>
				 </td>
				<tr>
				<% }
				}%>
			  </table>
			</div>
		  <% }else{ %>
			<td style="width:65%; " valign="top">
             <table>
			   <% for(Panel panel:panels01){
			      if(linkMap.get(panel.getId())!=null){
					String link = linkMap.get(panel.getId());
					int height = 300;
					if(panel.getHeight() > 50){
						height = panel.getHeight();
					}
			   %>
			    <tr>
				 <td align="left">
				 <div id="panel_<%=panel.getId()%>" ></div>
					<script type="text/javascript">
						jQuery("#panel_<%=panel.getId()%>").load("<%=link%>");
					</script>
				 </td>
				<tr>
				<% }
				}%>
			  </table>
			</td>
			<td style="width:35%;" valign="top">
             <table>
			   <% for(Panel panel:panels02){
			      if(linkMap.get(panel.getId())!=null){
					String link = linkMap.get(panel.getId());
					int height = 300;
					if(panel.getHeight() > 50){
						height = panel.getHeight();
					}
			   %>
			    <tr>
				 <td align="right">
				 <div id="panel_<%=panel.getId()%>" ></div>
					<script type="text/javascript">
						jQuery("#panel_<%=panel.getId()%>").load("<%=link%>");
					</script>
				 </td>
				<tr>
				<% }
				}%>
			  </table>
			</td>
		 <%}%>
	  </tr>
  </tbody>
</table>
</body>
</html>