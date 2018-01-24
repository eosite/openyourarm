<%@ page contentType="text/html;charset=UTF-8" %>
<%
  String text = (String)request.getAttribute("docker-compose.yml");
  out.println("<pre>"+text+"</pre>");
%>