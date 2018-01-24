<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%!
    public String outputPlayContent(HttpServletRequest request, int index){
	   String cameraId = (String)request.getAttribute("cam_"+index);
	   String json = (String)request.getAttribute("json_"+index);
	   if(cameraId != null && json != null){
           StringBuilder buffer = new StringBuilder();
	       buffer.append("\n			<object id=\"cam_"+index+"\" width=\"100%\" height=\"100%\" ")
			     .append(" classid=\"clsid:8d7d8518-ca58-4863-b94d-3c616fda7b35\"></object>");
		   buffer.append("\n			<script type=\"text/javascript\" language=\"javascript\">");
		   buffer.append("\n    			try{ ");
		   buffer.append("\n        			var cam_"+index+" = document.getElementById(\"cam_"+index+"\");");
		   buffer.append("\n        			var config_"+index+" = \"").append(json).append("\";");
		   buffer.append("\n        			cam_"+index+".Start(config_"+index+");");
		   buffer.append("\n    			}catch(exe){} ");
		   buffer.append("\n			</script>");
	       return buffer.toString();
	   }
	   return "";
    }
%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <title>视频监控</title>
  <script type="text/javascript" language="javascript">   

	function reinitIframe(elemId, cameraId){
		try{
			var ifm = document.getElementById(elemId);   
			var subWeb = document.frames ? document.frames[elemId].document : ifm.contentDocument;   
			if(ifm != null && subWeb != null) {
			   ifm.height = subWeb.body.scrollHeight;
			   ifm.width = subWeb.body.scrollWidth;
			}
			ifm.src="<%=request.getContextPath()%>/mx/video/camera/play?cameraId="+cameraId;
		}catch(exe){
		} 
	}

	function fnClose(){
		for(var i=1; i<=24; i++){
			try{
				var x = document.getElementById("cam_"+i);
				if(x != null){
					x.Stop();
				}
			}catch(exe){
		    } 
		}
	}

 </script>
 </head>
 <body style="margin:0px;"  onunload="fnClose();">
     <c:choose>
	   <c:when test="${videoUserLayout.layout eq 'A'}">
		<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" style="margin:0px; padding:0px;">
		 <tr height="50%">
			<td width="50%"><%=outputPlayContent(request, 1)%></td>
			<td width="50%"><%=outputPlayContent(request, 2)%></td>
		 </tr>
		 <tr height="50%">
			<td width="50%"><%=outputPlayContent(request, 3)%></td>
			<td width="50%"><%=outputPlayContent(request, 4)%></td>
		 </tr>
		</table>
       </c:when>

	   <c:when test="${videoUserLayout.layout eq 'B'}">
		<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		 <tr height="50%">
			<td width="33%"><%=outputPlayContent(request, 1)%></td>
			<td width="33%"><%=outputPlayContent(request, 2)%></td>
			<td width="33%"><%=outputPlayContent(request, 3)%></td>
		 </tr>
		 <tr height="50%">
			<td width="33%"><%=outputPlayContent(request, 4)%></td>
			<td width="33%"><%=outputPlayContent(request, 5)%></td>
			<td width="33%"><%=outputPlayContent(request, 6)%></td>
		 </tr>
		</table>
	   </c:when>

	   <c:when test="${videoUserLayout.layout eq 'C'}">
		<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		 <tr height="34%">
			<td width="33%"><%=outputPlayContent(request, 1)%></td>
			<td width="33%"><%=outputPlayContent(request, 2)%></td>
			<td width="33%"><%=outputPlayContent(request, 3)%></td>
		 </tr>
		 <tr height="33%">
			<td width="33%"><%=outputPlayContent(request, 4)%></td>
			<td width="33%"><%=outputPlayContent(request, 5)%></td>
			<td width="33%"><%=outputPlayContent(request, 6)%></td>
		 </tr>
		 <tr height="33%">
			<td width="33%"><%=outputPlayContent(request, 7)%></td>
			<td width="33%"><%=outputPlayContent(request, 8)%></td>
			<td width="33%"><%=outputPlayContent(request, 9)%></td>
		 </tr>
		 </table>
       </c:when>

	   <c:when test="${videoUserLayout.layout eq 'D'}">
		 <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		  <tr height="33%">
			<td width="25%"><%=outputPlayContent(request, 1)%></td>
			<td width="25%"><%=outputPlayContent(request, 2)%></td>
			<td width="25%"><%=outputPlayContent(request, 3)%></td>
			<td width="25%"><%=outputPlayContent(request, 4)%></td>
		  </tr>
		  <tr height="33%">
			<td width="25%"><%=outputPlayContent(request, 5)%></td>
			<td width="25%"><%=outputPlayContent(request, 6)%></td>
			<td width="25%"><%=outputPlayContent(request, 7)%></td>
			<td width="25%"><%=outputPlayContent(request, 8)%></td>
		  </tr>
		  <tr height="33%">
			<td width="25%"><%=outputPlayContent(request, 9)%></td>
			<td width="25%"><%=outputPlayContent(request, 10)%></td>
			<td width="25%"><%=outputPlayContent(request, 11)%></td>
			<td width="25%"><%=outputPlayContent(request, 12)%></td>
		  </tr>
		 </table>
       </c:when>

	   <c:when test="${videoUserLayout.layout eq 'E'}">
		 <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		  <tr height="25%">
			<td width="33%"><%=outputPlayContent(request, 1)%></td>
			<td width="33%"><%=outputPlayContent(request, 2)%></td>
			<td width="33%"><%=outputPlayContent(request, 3)%></td>
		  </tr>
		  <tr height="25%">
			<td width="33%"><%=outputPlayContent(request, 4)%></td>
			<td width="33%"><%=outputPlayContent(request, 5)%></td>
			<td width="33%"><%=outputPlayContent(request, 6)%></td>
		  </tr>
		  <tr height="25%">
			<td width="33%"><%=outputPlayContent(request, 7)%></td>
			<td width="33%"><%=outputPlayContent(request, 8)%></td>
			<td width="33%"><%=outputPlayContent(request, 9)%></td>
		  </tr>
		  <tr height="25%">
			<td width="33%"><%=outputPlayContent(request, 10)%></td>
			<td width="33%"><%=outputPlayContent(request, 11)%></td>
			<td width="33%"><%=outputPlayContent(request, 12)%></td>
		  </tr>
		 </table>
       </c:when>

	   <c:when test="${videoUserLayout.layout eq 'F'}">
		 <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		  <tr height="25%">
			<td width="25%"><%=outputPlayContent(request, 1)%></td>
			<td width="25%"><%=outputPlayContent(request, 2)%></td>
			<td width="25%"><%=outputPlayContent(request, 3)%></td>
			<td width="25%"><%=outputPlayContent(request, 4)%></td>
		  </tr>
		  <tr height="25%">
			<td width="25%"><%=outputPlayContent(request, 5)%></td>
			<td width="25%"><%=outputPlayContent(request, 6)%></td>
			<td width="25%"><%=outputPlayContent(request, 7)%></td>
			<td width="25%"><%=outputPlayContent(request, 8)%></td>
		  </tr>
		  <tr height="25%">
			<td width="25%"><%=outputPlayContent(request, 9)%></td>
			<td width="25%"><%=outputPlayContent(request, 10)%></td>
			<td width="25%"><%=outputPlayContent(request, 11)%></td>
			<td width="25%"><%=outputPlayContent(request, 12)%></td>
		  </tr>
		  <tr height="25%">
			<td width="25%"><%=outputPlayContent(request, 13)%></td>
			<td width="25%"><%=outputPlayContent(request, 14)%></td>
			<td width="25%"><%=outputPlayContent(request, 15)%></td>
			<td width="25%"><%=outputPlayContent(request, 16)%></td>
		  </tr>
		 </table>
       </c:when>

	   <c:when test="${videoUserLayout.layout eq 'G'}">
		 <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		 <tr>
			<td colspan="5" align="center">&nbsp;<input type="radio" name="layout" value="G">&nbsp;布局G&nbsp;</td>
		  </tr>
		  <tr height="25%">
			<td width="20%"><%=outputPlayContent(request, 1)%></td>
			<td width="20%"><%=outputPlayContent(request, 2)%></td>
			<td width="20%"><%=outputPlayContent(request, 3)%></td>
			<td width="20%"><%=outputPlayContent(request, 4)%></td>
			<td width="20%"><%=outputPlayContent(request, 5)%></td>
		  </tr>
		  <tr height="25%">
			<td width="20%"><%=outputPlayContent(request, 6)%></td>
			<td width="20%"><%=outputPlayContent(request, 7)%></td>
			<td width="20%"><%=outputPlayContent(request, 8)%></td>
			<td width="20%"><%=outputPlayContent(request, 9)%></td>
			<td width="20%"><%=outputPlayContent(request, 10)%></td>
		  </tr>
		  <tr height="25%">
			<td width="20%"><%=outputPlayContent(request, 11)%></td>
			<td width="20%"><%=outputPlayContent(request, 12)%></td>
			<td width="20%"><%=outputPlayContent(request, 13)%></td>
			<td width="20%"><%=outputPlayContent(request, 14)%></td>
			<td width="20%"><%=outputPlayContent(request, 15)%></td>
		  </tr>
		  <tr height="25%">
		    <td width="20%"><%=outputPlayContent(request, 16)%></td>
			<td width="20%"><%=outputPlayContent(request, 17)%></td>
			<td width="20%"><%=outputPlayContent(request, 18)%></td>
			<td width="20%"><%=outputPlayContent(request, 19)%></td>
			<td width="20%"><%=outputPlayContent(request, 20)%></td>
		  </tr>
		 </table>
       </c:when>

	   <c:when test="${videoUserLayout.layout eq 'H'}">
		 <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		  <tr height="25%">
			<td width="16%"><%=outputPlayContent(request, 1)%></td>
			<td width="16%"><%=outputPlayContent(request, 2)%></td>
			<td width="16%"><%=outputPlayContent(request, 3)%></td>
			<td width="16%"><%=outputPlayContent(request, 4)%></td>
			<td width="16%"><%=outputPlayContent(request, 5)%></td>
			<td width="16%"><%=outputPlayContent(request, 6)%></td>
		  </tr>
		  <tr height="25%">
			<td width="16%"><%=outputPlayContent(request, 7)%></td>
			<td width="16%"><%=outputPlayContent(request, 8)%></td>
            <td width="16%"><%=outputPlayContent(request, 9)%></td>
			<td width="16%"><%=outputPlayContent(request, 10)%></td>
			<td width="16%"><%=outputPlayContent(request, 11)%></td>
			<td width="16%"><%=outputPlayContent(request, 12)%></td>
		  </tr>
		  <tr height="25%">
			<td width="16%"><%=outputPlayContent(request, 13)%></td>
			<td width="16%"><%=outputPlayContent(request, 14)%></td>
			<td width="16%"><%=outputPlayContent(request, 15)%></td>
			<td width="16%"><%=outputPlayContent(request, 16)%></td>
			<td width="16%"><%=outputPlayContent(request, 17)%></td>
			<td width="16%"><%=outputPlayContent(request, 18)%></td>
		  </tr>
		  <tr height="25%">
			<td width="16%"><%=outputPlayContent(request, 19)%></td>
			<td width="16%"><%=outputPlayContent(request, 20)%></td>
		    <td width="16%"><%=outputPlayContent(request, 21)%></td>
			<td width="16%"><%=outputPlayContent(request, 22)%></td>
			<td width="16%"><%=outputPlayContent(request, 23)%></td>
			<td width="16%"><%=outputPlayContent(request, 24)%></td>
		  </tr>
		 </table>
	   </c:when>
	</c:choose>
 </body>
</html>
