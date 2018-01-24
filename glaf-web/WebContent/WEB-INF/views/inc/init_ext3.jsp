<%
String my_theme = com.glaf.core.util.RequestUtils.getTheme(request);
if(my_theme == null){
	my_theme="blue";
} else if("gray".equals(my_theme)){
	my_theme="gray";
} else if("access".equals(my_theme)){
	my_theme="access";
}else{
	my_theme="blue";
}
request.setAttribute("my_theme", my_theme);
%>
<link href="${pageContext.request.contextPath }/scripts/extjs3/resources/css/ext-all-notheme.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/extjs3/resources/css/xtheme-${my_theme }.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath }/scripts/extjs3/ux/css/ColumnNodeUI.css"  rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath }/scripts/extjs3/tree/column-tree.css"  rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/treegrid.css"  rel="stylesheet" type="text/css"/>

<script src="${pageContext.request.contextPath }/scripts/extjs3/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/scripts/extjs3/ext-all.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/ColumnNodeUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/TreeGrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/TreeGridLoader.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/TreeGridSorter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/TreeGridNodeUI.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/TreeGridColumns.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/treegrid/TreeGridColumnResizer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/extjs3/ux/ColumnHeaderGroup.js"></script>