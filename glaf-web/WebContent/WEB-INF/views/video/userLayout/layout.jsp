<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <title>布局设置</title>
  <%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
  <script type="text/javascript">
      function saveLayout(){
        var link = "<%=request.getContextPath()%>/mx/video/userLayout/saveLayout";
	    var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: link,
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
			 });
      }
  </script>
 </head>
 <body>
 <form id="iForm" name="iForm" method="post" >
 <br>
 <table border="0" >

   <tr>
    <td colspan="4" align="center">
	    <input type="button" value="保存设置" class="k-button k-primary" onclick="javascript:saveLayout();" >
		&nbsp;<input type="button" value="预览" class="k-button" 
		       onclick="javascript:window.open('<%=request.getContextPath()%>/mx/video/userPlayer/play');" >
		<br><br>
	</td>
  </tr>

   <tr valign="top" align="center">
    <td width="200" height="150">
		<table border="1">
		 <tr>
			<td colspan="2" align="center">&nbsp;
			<input type="radio" name="layout" value="A" <c:if test="${videoUserLayout.layout eq 'A'}">checked</c:if>>
			&nbsp;布局A&nbsp;
			</td>
		 </tr>
		 <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
		 </tr>
		 <tr>
			<td>&nbsp;3&nbsp;</td>
			<td>&nbsp;4&nbsp;</td>
		 </tr>
		</table>
    </td>

	<td width="200" height="150">
		<table border="1">
		 <tr>
			<td colspan="3" align="center">&nbsp;
			<input type="radio" name="layout" value="B" <c:if test="${videoUserLayout.layout eq 'B'}">checked</c:if>>
			&nbsp;布局B&nbsp;
			</td>
		 </tr>
		 <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
		 </tr>
		 <tr>
			<td>&nbsp;4&nbsp;</td>
			<td>&nbsp;5&nbsp;</td>
			<td>&nbsp;6&nbsp;</td>
		 </tr>
		 </table>
    </td>
	<td width="200" height="150">
		<table border="1">
		 <tr>
			<td colspan="3" align="center">&nbsp;
			<input type="radio" name="layout" value="C" <c:if test="${videoUserLayout.layout eq 'C'}">checked</c:if>>
			&nbsp;布局C&nbsp;
			</td>
		 </tr>
		 <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
		 </tr>
		 <tr>
			<td>&nbsp;4&nbsp;</td>
			<td>&nbsp;5&nbsp;</td>
			<td>&nbsp;6&nbsp;</td>
		 </tr>
		 <tr>
			<td>&nbsp;7&nbsp;</td>
			<td>&nbsp;8&nbsp;</td>
			<td>&nbsp;9&nbsp;</td>
		 </tr>
		 </table>
    </td>
	<td width="200" height="150">
		 <table border="1">
		  <tr>
			<td colspan="4" align="center">&nbsp;
			<input type="radio" name="layout" value="D" <c:if test="${videoUserLayout.layout eq 'D'}">checked</c:if>>
			&nbsp;布局D&nbsp;
			</td>
		 </tr>
		  <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
			<td>&nbsp;4&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;5&nbsp;</td>
			<td>&nbsp;6&nbsp;</td>
			<td>&nbsp;7&nbsp;</td>
			<td>&nbsp;8&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;9&nbsp;</td>
			<td>&nbsp;10&nbsp;</td>
			<td>&nbsp;11&nbsp;</td>
			<td>&nbsp;12&nbsp;</td>
		  </tr>
		 </table>
    </td>
   </tr>

   <tr valign="top" align="center">	
     <td width="200" height="150">
		 <table border="1">
		  <tr>
			<td colspan="3" align="center">&nbsp;
			<input type="radio" name="layout" value="E" <c:if test="${videoUserLayout.layout eq 'E'}">checked</c:if>>
			&nbsp;布局E&nbsp;
			</td>
		  </tr>
		  <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;4&nbsp;</td>
			<td>&nbsp;5&nbsp;</td>
			<td>&nbsp;6&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;7&nbsp;</td>
			<td>&nbsp;8&nbsp;</td>
			<td>&nbsp;9&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;10&nbsp;</td>
			<td>&nbsp;11&nbsp;</td>
			<td>&nbsp;12&nbsp;</td>
		  </tr>
		 </table>
    </td>

    <td width="200" height="150">
		 <table border="1">
		  <tr>
			<td colspan="4" align="center">&nbsp;
			<input type="radio" name="layout" value="F" <c:if test="${videoUserLayout.layout eq 'F'}">checked</c:if>>
			&nbsp;布局F&nbsp;
			</td>
		  </tr>
		  <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
			<td>&nbsp;4&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;5&nbsp;</td>
			<td>&nbsp;6&nbsp;</td>
			<td>&nbsp;7&nbsp;</td>
			<td>&nbsp;8&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;9&nbsp;</td>
			<td>&nbsp;10&nbsp;</td>
			<td>&nbsp;11&nbsp;</td>
			<td>&nbsp;12&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;13&nbsp;</td>
			<td>&nbsp;14&nbsp;</td>
			<td>&nbsp;15&nbsp;</td>
			<td>&nbsp;16&nbsp;</td>
		  </tr>
		 </table>
    </td>

	<td width="200" height="150">
		 <table border="1">
		 <tr>
			<td colspan="5" align="center">&nbsp;
			<input type="radio" name="layout" value="G" <c:if test="${videoUserLayout.layout eq 'G'}">checked</c:if>>
			&nbsp;布局G&nbsp;
			</td>
		  </tr>
		  <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
			<td>&nbsp;4&nbsp;</td>
			<td>&nbsp;5&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;6&nbsp;</td>
			<td>&nbsp;7&nbsp;</td>
			<td>&nbsp;8&nbsp;</td>
			<td>&nbsp;9&nbsp;</td>
			<td>&nbsp;10&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;11&nbsp;</td>
			<td>&nbsp;12&nbsp;</td>
			<td>&nbsp;13&nbsp;</td>
			<td>&nbsp;14&nbsp;</td>
			<td>&nbsp;15&nbsp;</td>
		  </tr>
		  <tr>
		    <td>&nbsp;16&nbsp;</td>
			<td>&nbsp;17&nbsp;</td>
			<td>&nbsp;18&nbsp;</td>
			<td>&nbsp;19&nbsp;</td>
			<td>&nbsp;20&nbsp;</td>
		  </tr>
		 </table>
    </td>

	<td width="200" height="150">
		 <table border="1">
		  <tr>
			<td colspan="6" align="center">&nbsp;
			<input type="radio" name="layout" value="H" <c:if test="${videoUserLayout.layout eq 'H'}">checked</c:if>>
			&nbsp;布局H&nbsp;
			</td>
		  </tr>
		  <tr>
			<td>&nbsp;1&nbsp;</td>
			<td>&nbsp;2&nbsp;</td>
			<td>&nbsp;3&nbsp;</td>
			<td>&nbsp;4&nbsp;</td>
			<td>&nbsp;5&nbsp;</td>
			<td>&nbsp;6&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;7&nbsp;</td>
			<td>&nbsp;8&nbsp;</td>
            <td>&nbsp;9&nbsp;</td>
			<td>&nbsp;10&nbsp;</td>
			<td>&nbsp;11&nbsp;</td>
			<td>&nbsp;12&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;13&nbsp;</td>
			<td>&nbsp;14&nbsp;</td>
			<td>&nbsp;15&nbsp;</td>
			<td>&nbsp;16&nbsp;</td>
			<td>&nbsp;17&nbsp;</td>
			<td>&nbsp;18&nbsp;</td>
		  </tr>
		  <tr>
			<td>&nbsp;19&nbsp;</td>
			<td>&nbsp;20&nbsp;</td>
		    <td>&nbsp;21&nbsp;</td>
			<td>&nbsp;22&nbsp;</td>
			<td>&nbsp;23&nbsp;</td>
			<td>&nbsp;24&nbsp;</td>
		  </tr>
		 </table>
    </td>

  </tr>	

  <tr>
    <td colspan="4" align="center">
	     <%
		   for(int index = 1; index <= 24; index ++){
			  String camId = (String)request.getAttribute("cameraId_"+index);
			  if(camId == null){
				  camId = "";
			  }
         %>
		   <br><br>位置<%=index%> &nbsp;
           <select id="camera_<%=index%>" name="camera_<%=index%>">
			<option value="" selected>----请选择----</option>
			<c:forEach var="camera" items="${cameras}">
			<option value="${camera.cameraId}">${camera.name} ${camera.deviceName}[第${camera.cameraNo}通道]</option>
			</c:forEach>
           </select>
		   <script type="text/javascript">
		       document.getElementById("camera_<%=index%>").value="<%=camId%>";
		   </script>
		 <%
           }
		 %>
	</td>
  </tr>

  <tr>
    <td colspan="4" align="center">
	    <br><input type="button" value="保存设置" class="k-button k-primary" onclick="javascript:saveLayout();" >
		&nbsp;<input type="button" value="预览" class="k-button" 
		       onclick="javascript:window.open('<%=request.getContextPath()%>/mx/video/userPlayer/play');" >
	</td>
  </tr>

 </table> 
 </form>
 </body>
</html>
