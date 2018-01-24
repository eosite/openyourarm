<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">  
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title><%=SystemConfig.getString("res_system_name")%></title>  
    <%@ include file="/WEB-INF/views/mobile/inc/init_easyui.jsp"%>
</head>
<body>
	<div class="easyui-navpanel">
	    <header>
	    	<div class="m-toolbar">
	    		<div class="m-title"><%=SystemConfig.getString("res_system_name")%></div>
	    	</div>
	    </header>
		<div class="easyui-tabs" data-options="tabHeight:60,fit:true,tabPosition:'bottom',border:false,pill:true,narrow:true,justified:true">
			<div style="padding:10px">
				<div class="panel-header tt-inner">
					<img src='${contextPath}/images/modem.png'/><br>Modem
				</div>
				<p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital information, and also demodulates such a carrier signal to decode the transmitted information.</p>
			</div>
			<div style="padding:10px">
				<div class="panel-header tt-inner">
					<img src='${contextPath}/images/scanner.png'/><br>Scanner
				</div>
				<p>In computing, an image scanner—often abbreviated to just scanner—is a device that optically scans images, printed text, handwriting, or an object, and converts it to a digital image.</p>
			</div>
			<div style="padding:10px">
				<div class="panel-header tt-inner">
					<img src='${contextPath}/images/pda.png'/><br>Pda
					<span class="m-badge">23</span>
				</div>
				<p>A personal digital assistant (PDA), also known as a palmtop computer, or personal data assistant, is a mobile device that functions as a personal information manager. PDAs are largely considered obsolete with the widespread adoption of smartphones.</p>
			</div>
			<div style="padding:10px">
				<div class="panel-header tt-inner">
					<img src='${contextPath}/images/tablet.png'/><br>Pda
					<span class="m-badge">13</span>
				</div>
				<p>A tablet computer, or simply tablet, is a one-piece mobile computer. Devices typically have a touchscreen, with finger or stylus gestures replacing the conventional computer mouse.</p>
			</div>
		</div>
	</div>
	<style scoped>
       .tt-inner{
            display:inline-block;
            line-height:12px;
            padding-top:6px;
            position: relative;
        }
		p{
			line-height:150%;
		}
	</style>
</body>	
</html>
