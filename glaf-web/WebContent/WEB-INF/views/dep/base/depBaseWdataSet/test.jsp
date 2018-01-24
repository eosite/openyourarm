<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String theme = com.glaf.core.util.RequestUtils.getTheme(request);
	request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新数据集[${depBaseWdataSet.dataSetName}]测试</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style type="text/css">
table {
	width: 100%;
	height: 100%;
	border: 1px solid #ccc;
	border-width: 1px 1px 0px 0px;
	height: 100%;
}

table tr td {
	height: 30px;
}

table tr td:nth-of-type(odd) {
	border: 1px solid #ccc;
	border-width: 0px 0px 1px 1px;
	width: 10%;
}

table tr td:nth-of-type(even) {
	border: 1px solid #ccc;
	border-width: 0px 0px 1px 1px;
	background-color: #fff;
}

table tr td input.k-textbox {
	height: 95%;
	border: 0px;
	width: 100%;
}

fieldset {
	border: 1px solid #b0aeae;
}

fieldset legend {
	color: #302A2A;
	font: bold 16px/2 Verdana, Geneva, sans-serif;
	font-weight: bold;
	text-align: left;
	text-shadow: 2px 2px 2px rgb(88, 126, 156);
}
</style>
</head>
<body style="margin-top: 0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="<%=request.getContextPath()%>/images/window.png"
				alt="更新数据集[${depBaseWdataSet.dataSetName}]测试">&nbsp;
			更新数据集[${depBaseWdataSet.dataSetName}]测试
			<div style="float: right; padding-right: 20px;">
				<button class="test" t-type="del" title="本操作不入库">测试删除</button>
				<button class="test" t-type="cu" title="本操作不入库">测试新增/修改</button>
			</div>
		</div>
		<br> <br>
	</div>
	<div>

		<c:if test="${selected != null  && fn:length(selected) > 0 }">
			<fieldset>
				<legend>已选字段</legend>
				<table cellspacing="0" cellpadding="0" style="">
					<tr>
						<c:forEach items="${selected}" var="v" varStatus="status">
							<c:if test="${status.index != 0 && status.index % 4 == 0 }">
					</tr>
					<tr>
						</c:if>
						<td align="center">${v.fname }</td>
						<td><input class="k-textbox k-params"
							name="${v.dataColumnName }" title="${v.fname }"></td>
						</c:forEach>
					</tr>
				</table>
			</fieldset>

		</c:if>

		<c:if test="${others != null  && fn:length(others) > 0 }">
			<fieldset>
				<legend>未选字段</legend>
				<table cellspacing="0" cellpadding="0" style="">
					<tr>
						<c:forEach items="${others}" var="v" varStatus="status">
							<c:if test="${status.index != 0 && status.index % 4 == 0 }">
					</tr>
					<tr>
						</c:if>
						<td align="center">${v.fname }</td>
						<td><input class="k-textbox k-params"
							name="${v.dataColumnName }" title="${v.fname }"></td>
						</c:forEach>
					</tr>
				</table>
			</fieldset>
		</c:if>


		<c:if
			test="${ruleJson != null  && fn:length(ruleJson.whereParams) > 0 }">
			<fieldset>
				<legend>参数</legend>
				<table cellspacing="0" cellpadding="0" style="">
					<tr>
						<c:forEach items="${ruleJson.whereParams}" var="v"
							varStatus="status">
							<c:if test="${status.index != 0 && status.index % 4 == 0 }">
					</tr>
					<tr>
						</c:if>
						<td align="center">${v.name }</td>
						<td><input class="k-textbox k-params" name="${v.param }"
							title="${v.name }"></td>
						</c:forEach>
					</tr>
				</table>
			</fieldset>
		</c:if>


		<fieldset>
			<legend>测试结果</legend>
			<div id="mt-result"></div>
		</fieldset>
	</div>
</body>
<script>
	jQuery(document).ready(function() {
		$("button.test").on("click", TestWDataSet);
	});

	var testUrl = "${contextPath}/mx/form/data/mtcrud", mtTest = "MTTEST__";

	function TestWDataSet(e) {
		var params = new Object();
		$("input.k-params").each(function() {
			this.value && (params[$(this).attr("name")] = this.value);
		});
		params[mtTest] = true;
		$.ajax({
			url : testUrl,
			data : {
				useId : '${param.id}',
				type : $(this).attr("t-type"),
				params : JSON.stringify(params)
			},
			dataType : 'JSON'
		}).done(done).error(function(ret) {
			alert(ret.message);
		});
	}

	function done(ret) {
		if (!ret || !ret[mtTest]) {
			alert("执行可能出错!");
		}
		var datas = ret[mtTest];

		try {
			delete ret[mtTest];
			delete ret[mtTest.toLowerCase()];
		} catch (e) {
			console.log(e);
		}

		var result = [ "执行SQL :", datas[0], "<br/><hr/>执行参数 : ",
				datas[1].join(", "), "<br/><hr/>原始参数:" ];

		result.push(JSON.stringify(ret));

		$("#mt-result").html(result.join(" "));
	}
</script>
</html>