<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%> 
<%
     request.setAttribute("contextPath", request.getContextPath());
%>
<c:url value="/rs/editor/imagebrowser/read" var="transportReadUrl" />
<c:url value="/rs/editor/imagebrowser/thumbnail" var="transportThumbnailUrl" />
<c:url value="/rs/editor/imagebrowser/upload" var="transportUploadUrl" />
<c:url value="/rs/editor/imagebrowser/create" var="transportCreateUrl" />
<c:url value="/rs/editor/imagebrowser/destroy" var="transportDestroyUrl" />
<c:url value="/resources/imagebrowser/{0}" var="transportImageUrl" />

<center>
<div class="box">
    <p>允许上传文件类型: jpg, jpeg, gif, png</p>
</div>

<kendo:editor name="editor" style="width:740px;height:440px">
	<kendo:editor-tools>
    <kendo:editor-tool name="insertImage"/>
	<kendo:editor-tool name="insertFile"/>
  	</kendo:editor-tools>
	<kendo:editor-imageBrowser>
		<kendo:editor-imageBrowser-transport 
			read="${ transportReadUrl }" 
			thumbnailUrl="${transportThumbnailUrl}"
			imageUrl="${ transportImageUrl }"
			uploadUrl="${ transportUploadUrl }">
			<kendo:editor-imageBrowser-transport-create type="POST" dataType="json" url="${ transportCreateUrl }"/>
			<kendo:editor-imageBrowser-transport-destroy type="POST" dataType="json" url="${ transportDestroyUrl }"/>
		</kendo:editor-imageBrowser-transport>		
	</kendo:editor-imageBrowser>
	<kendo:editor-fileBrowser>
		<kendo:editor-fileBrowser-transport 
			read="${ transportReadUrl }" 
			fileUrl="${ transportImageUrl }"
			uploadUrl="${ transportUploadUrl }">
			<kendo:editor-fileBrowser-transport-create type="POST" dataType="json" url="${ transportCreateUrl }"/>
			<kendo:editor-fileBrowser-transport-destroy type="POST" dataType="json" url="${ transportDestroyUrl }"/>
		</kendo:editor-fileBrowser-transport>		
	</kendo:editor-fileBrowser>
    <kendo:editor-value>
        &nbsp;&nbsp;
    </kendo:editor-value>    
</kendo:editor>
</center>