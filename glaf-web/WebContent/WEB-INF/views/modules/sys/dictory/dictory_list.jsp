<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="html"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.base.modules.*"%>
<%@ page import="com.glaf.base.modules.sys.model.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
String context = request.getContextPath();
int parent=ParamUtil.getIntParameter(request, "parent", 0);
int pageSize=Constants.PAGE_SIZE;
com.glaf.core.util.PageResult pager=(com.glaf.core.util.PageResult)request.getAttribute("pager");
List list = pager.getResults();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础平台系统</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/themes/<%=com.glaf.core.util.RequestUtils.getTheme(request)%>/site.css">
<link href="<%=request.getContextPath()%>/scripts/artDialog/skins/default.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/scripts/layer/skin/layer.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.form.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/artDialog.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/artDialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/layer/layer.min.js"></script>
<script language="javascript" src='<%=context%>/scripts/verify.js'></script>
<script language="javascript" src='<%=context%>/scripts/main.js'></script>
<script language="javascript">
var num=0;
function checkOperation(form){
  num = getCheckedBoxNum(form,"id");
  if(num>0){
	if(num==1){
	  document.all.btn_modify.disabled=false;
	}else{
	  document.all.btn_modify.disabled=true;
	}
  }else{
	document.all.btn_modify.disabled=true;
  }
}
function add(){
  var url="dictory/prepareAdd?parent="+<%=parent%>;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=580;
  var height=420;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "添加字典", lock: false, scrollbars:"no" }, false);
}

function modify(form){
  var id =0;
  for (var i=0;i<form.id.length;i++) {
    var e = form.id[i];
    if (e.checked){
	  id=e.value;
	}     
  }
  var url="dictory/prepareModify?id="+id+"&parent="+<%=parent%>;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=580;
  var height=420;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "修改字典", lock: false, scrollbars:"no" }, false);
}

function editRow(id){
  var url="dictory/prepareModify?id="+id+"&parent="+<%=parent%>;
  var link = "<%=request.getContextPath()%>/mx/sys/"+url;
  var width=480;
  var height=420;
  var scroll="yes";
  //openWindow(url, width, height, scroll);
  art.dialog.open(link, { height: height, width: width, title: "修改字典", lock: false, scrollbars:"no" }, false);
}

function del(){
  var form = document.DictoryForm;
  if(confirmDelete(form)){
    form.action="dictory/batchDelete";
    form.target="hiddenFrame";
    form.submit();
  }
}

function sort(id, operate){  
  //DictoryAjaxService.sort(<%=parent%>, id, operate, {callback:function (){reloadPage();}});
  	jQuery.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/mx/sys/dictory/sort?parent=<%=parent%>&id='+id+'&operate='+operate,
			dataType:  'json',
				error: function(data){
					alert('服务器处理错误！');
				},
				success: function(data){
					   location.reload();
				 }
		});
}

function importJson(){
    var link='<%=request.getContextPath()%>/mx/sys/dictory/showImport';
    jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "导入字典信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['650px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
}

function exportJson(){
	window.open('<%=request.getContextPath()%>/mx/sys/dictory/exportJson?parentId=<%=parent%>');
}
</script>
</head>

<body>
<div class="nav-title"><span class="Title">字典管理</span>&gt;&gt;字典列表</div>
<html:form id="DictoryForm" name="DictoryForm" action="${contextPath}/mx/sys/dictory/batchDelete" method="post" target="_self">
<input type="hidden" name="id" value="0">  
<table width="100%" border="0" cellspacing="1" cellpadding="0" class="list-box">
  <tr class="list-title"> 
    <td width="5%" align="center">
        <input type="checkbox" name="chkall" value="checkbox" onClick="checkAll(this.form, this)";checkOperation(this.form)>
    </td>
    <td width="5%"  align="center"><div align="center" class="fontname_12">序号</div></td>
    <td  align="center"><div align="center" class="fontname_12">名称</div></td>
    <td  align="center"><div align="center" class="fontname_12">代码</div></td>
	<td  align="center"><div align="center" class="fontname_12">属性值</div></td>
    <td  align="center"><div align="center" class="fontname_12">是否有效</div></td>
    <td  align="center"><div align="center" class="fontname_12">排序</div></td>
	<td  width="8%" align="center"><div align="center" class="fontname_12">功能键</div></td>
  </tr>
  <%
int i=0;
if(list!=null){
  Iterator iter=list.iterator();   
  while(iter.hasNext()){
    Dictory bean=(Dictory)iter.next();	
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td width="5%" class="td-cb">
	<input type="checkbox" name="id" value="<%=bean.getId()%>" onClick="checkOperation(this.form)">
	</td>
    <td class="td-no"><%=(pager.getCurrentPageNo()-1)*10 + i+1%>&nbsp;</td>
    <td class="td-text"><%=bean.getName()%>&nbsp;</td>
    <td class="td-no"><%=bean.getCode() != null  ? bean.getCode()  : ""%>&nbsp;</td>
	<td class="td-no"><%=bean.getValue() != null ? bean.getValue() : ""%>&nbsp;</td>
    <td class="td-no"><%=bean.getBlocked()==0?"是":"否"%></td>
    <td class="td-no">
	<a href="javascript:sort(<%=bean.getId()%>, 0);" title="上移"><img src="<%=context%>/images/up.gif" border="0" height="13" width="13"></a> <a href="javascript:sort(<%=bean.getId()%>, 1);" title="下移"><img src="<%=context%>/images/down.gif" border="0" height="13" width="13"></a>
	</td>
	<td align="center">&nbsp;
	   <a href="#" onclick="javascript:editRow(<%=bean.getId()%>);">修改</a>&nbsp;
    </td>
  </tr>
  <%
    i++;
  }
}
for(; i<pageSize; i++){
%>
  <tr <%=i%2==0?"":"class='list-back'"%>> 
    <td height="20">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
  </tr>
<%
}
%>
</table>
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr> 
      <td width="50%">
	    <input name="btn_add" type="button" value="增加" class="button" onClick="javascript:add();">
        <input name="btn_modify" type="button" value="修改" class="button" onClick="javascript:modify(this.form);" disabled>
		<input name="btn_export" type="button" value="导出" class="button" onClick="javascript:exportJson(this.form);" >
		<input name="btn_import" type="button" value="导入" class="button" onClick="javascript:importJson(this.form);" >
		</td>
      <td width="50%"> 
		<%
		String params = WebUtil.getQueryString(request);
		%>	  
	  <jsp:include page="/WEB-INF/views/inc/show_page.jsp" flush="true"> 
              <jsp:param name="total" value="<%=pager.getTotalRecordCount()%>"/>
              <jsp:param name="page_count" value="<%=pager.getTotalPageCount()%>"/>
              <jsp:param name="page_size" value="<%=pageSize%>"/>
              <jsp:param name="page_no" value="<%=pager.getCurrentPageNo()%>"/>
              <jsp:param name="url" value=""/>
			  <jsp:param name="params" value="<%=java.net.URLEncoder.encode(params)%>"/>
        </jsp:include> </td>
    </tr>
  </table>
</html:form>
<script language="javascript">
attachFrame();
</script>
</body>
</html>