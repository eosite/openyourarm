<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
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
<title>编辑数据库函数对照表信息</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<style scoped>
.k-textbox {
	width: 18.8em;
}

.main-section {
	width: 800px;
	padding: 0;
}

label {
	display: inline-block;
	width: 100px;
	text-align: right;
	padding-right: 10px;
}

.required {
	font-weight: bold;
}

.accept, .status {
	padding-left: 90px;
}

.confirm {
	text-align: right;
}

.valid {
	color: green;
}

.invalid {
	color: red;
}

span.k-tooltip {
	margin-left: 6px;
}

.k-textarea {
	border: 0;
	background-color: #fff;
	color: #666464;
	height: auto;
}

fieldset {
	border: 0 none;
	border-top: 1px dashed #ddd;
}
</style>
<script type="text/javascript">
	jQuery(function() {

		var dataModel = {
			"funcId" : "${systemDBFuncMapping.funcId}",
			"dtype" : "${systemDBFuncMapping.dtype}",
			"funcName" : "${systemDBFuncMapping.funcName}",
			"createBy" : "${systemDBFuncMapping.createBy}",
			"createTime" : "<fmt:formatDate value='${systemDBFuncMapping.createTime}' pattern='MM/dd/yyyy'/>",
			"updateBy" : "${systemDBFuncMapping.updateBy}",
			"updateTime" : "<fmt:formatDate value='${systemDBFuncMapping.updateTime}' pattern='MM/dd/yyyy'/>",
			"deleteFlag" : "${systemDBFuncMapping.deleteFlag}",
			"id" : "${systemDBFuncMapping.id}"
		};

		<c:if test="${!empty systemDBFuncMapping.params}" >
			dataModel.params = ${systemDBFuncMapping.params};
			
			$("[data-prop-for]").each(function(i, v){
				var $this = $(this), key = $this.attr("data-prop"), val;
				if(!key || !(val = dataModel.params[key])){
					return;
				}
				if($this.is("table") && val.length){
					var fn = "fieldname", $tbody = $this.find("tbody");
					function set(o, $tr){
						$tr.find("["+ fn +"]").each(function(){
							$(this).val(o[$(this).attr(fn)]);
						});
					}
					
					var $tr0 = $tbody.find("tr:eq(0)"), str = $tr0.get(0).outerHTML, $tr;
					$.each(val, function(i, v){
						if(i == 0){
							$tr =  $tr0;
						} else {
							$tr = $tbody.append(str).find("tr").eq(i);
						}
						set(v, $tr);
					});
				} else {
					$this.val(val);
				}
			});
			
			
		</c:if>
		
		var viewModel = kendo.observable(dataModel);

		kendo.bind(jQuery("#iForm"), viewModel);

	});

	jQuery(document).ready(function() {
		jQuery("#iconButton").kendoButton({
		//	spriteCssClass : "k-icon"
		});
	});

	jQuery(function() {
		var container = jQuery("#iForm");
		kendo.init(container);
		container
				.kendoValidator({
					rules : {
						greaterdate : function(input) {
							if (input.is("[data-greaterdate-msg]")
									&& input.val() != "") {
								var date = kendo.parseDate(input.val()), otherDate = kendo
										.parseDate(jQuery(
												"[name='"
														+ input
																.data("greaterdateField")
														+ "']").val());
								return otherDate == null
										|| otherDate.getTime() < date.getTime();
							}

							return true;
						}
					}
				});
	});

	function save() {
		var form = document.getElementById("iForm");
		var validator = jQuery("#iForm").data("kendoValidator");
		if (validator.validate()) {
			var link = "${contextPath}/mx/datamgr/systemDBFuncMapping/saveSystemDBFuncMapping";
			var params = {};
			$.each(jQuery("#iForm").serializeArray(), function(i, o){
				params[o.name] = o.value;
			});
			$.extend(true, params, InitValue());
			
			jQuery.ajax({
				type : "POST",
				url : link,
				dataType : 'json',
				data : params,
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(data) {
					if (data != null && data.message != null) {
						alert(data.message);
					} else {
						alert('操作成功完成！');
					}
					window.parent.location.reload();
				}
			});
		}
	}
</script>
</head>
<body style="margin-top: 0px;">
	<div id="main_content" class="k-content ">
		<br>
		<div class="x_content_title">
			<img src="${contextPath}/images/window.png" alt="编辑数据库函数对照表信息">&nbsp;
			编辑数据库函数对照表信息
		</div>
		<div style="float: right">
			<button type="button" id="iconButton" class="k-button"
				style="width: 90px" onclick="javascript:save();">保存</button>
		</div>
		<br>
		<form id="iForm" name="iForm" method="post" data-role="validator"
			novalidate="novalidate">
			<input type="hidden" id="id" name="id"
				value="${systemDBFuncMapping.id}" /> <input type="hidden"
				id="funcId" name="funcId"
				value="${!empty param.funcId ? param.funcId : systemDBFuncMapping.funcId }" />
			<table width="95%" align="center" border="0" cellspacing="0"
				cellpadding="5">
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td width="70%">
						<fieldset>
							<legend>基本信息</legend>
							<table>
								<tbody>
									<%-- <tr>
										<td width="2%" align="left">&nbsp;</td>
										<td align="left"><label for="funcId" class="required">函数ID&nbsp;</label>
											<input id="funcId" name="funcId" type="text"
											class="k-textbox" data-bind="value: funcId"
											value="${systemDBFuncMapping.funcId}"
											validationMessage="请输入函数ID" /> <span class="k-invalid-msg"
											data-for="funcId"></span></td>
									</tr> --%>
									<tr>
										<td width="2%" align="left">&nbsp;</td>
										<td align="left"><label for="funcName" class="required">名称&nbsp;</label>
											<input id="funcName" name="funcName" type="text"
											class="k-textbox" data-bind="value: funcName"
											value="${systemDBFuncMapping.funcName}"
											validationMessage="请输入函数名" /> <span class="k-invalid-msg"
											data-for="funcName"></span></td>
									</tr>

									<tr>
										<td width="2%" align="left">&nbsp;</td>
										<td align="left"><label class="required">描述&nbsp;</label>
											<input type="text" class="k-textbox" data-prop-for="params"
											data-prop="funcDesc" /> <span class="k-invalid-msg"></span></td>
									</tr>

									<tr>
										<td width="2%" align="left">&nbsp;</td>
										<td align="left"><label for="dtype" class="required">数据库&nbsp;</label>
											<input id="dtype" name="dtype"
											value="${systemDBFuncMapping.dtype}" style="width: 225px;" />
										</td>
									</tr>

								</tbody>
							</table>
						</fieldset>
					</td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td width="70%">
						<fieldset>
							<legend>参数明细</legend>
							<table data-prop-for="params" data-prop="desc">
								<thead>
									<tr>
										<td>参数名</td>
										<td>参数描述</td>
										<td>默认值</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input fieldname="name" class="k-textbox"
											style="width: 100%" /></td>
										<td><input fieldname="desc" class="k-textbox"
											style="width: 100%" /></td>
										<td><input fieldname="val" class="k-textbox"
											style="width: 100%" /></td>
										<td style="width: 10%">
											<button class="k-tools k-button" t="add">+</button>
											<button class="k-tools k-button" t="del">-</button>
										</td>
									</tr>
								</tbody>
							</table>
						</fieldset>
					</td>
				</tr>

				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td width="70%">
						<fieldset>
							<legend>脚本模版</legend>
							<textarea id="textarea-01" rows="10" cols="70"
								data-prop-for="params" data-prop="template" class="k-textarea"></textarea>
						</fieldset>
					</td>
				</tr>

				<br>

				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td width="70%">
						<fieldset>
							<legend>测试参数</legend>
							<table class="k-test-params">
								<tbody>
									<tr>
										<td><input class="k-textbox" style="width: 100%" /></td>
										<td><input class="k-textbox" style="width: 100%" /></td>
										<td><input class="k-textbox" style="width: 100%" /></td>
										<td><input class="k-textbox" style="width: 100%" /></td>
										<td><input class="k-textbox" style="width: 100%" /></td>
									</tr>
								</tbody>
							</table>
						</fieldset>
					</td>
				</tr>

				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="dtype0" class="required">测试目标库&nbsp;</label>
						<input id="dtype0" name="dtype0" style="width: 225px;" /></td>
				</tr>

				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td width="70%">
						<button class="k-button k-test">测试</button>
					</td>
				</tr>

				<%-- <tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="createBy" class="required">CREATEBY_&nbsp;</label>
						<input id="createBy" name="createBy" type="text" class="k-textbox"
						data-bind="value: createBy"
						value="${systemDBFuncMapping.createBy}"
						validationMessage="请输入CREATEBY_" /> <span class="k-invalid-msg"
						data-for="createBy"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="createTime" class="required">CREATETIME_&nbsp;</label>
						<input id="createTime" name="createTime" type="text"
						class="k-textbox" data-role='datepicker' data-type="date"
						data-bind="value: createTime"
						value="<fmt:formatDate value="${systemDBFuncMapping.createTime}" pattern="yyyy-MM-dd"/>"
						validationMessage="请输入CREATETIME_" /> <span class="k-invalid-msg"
						data-for="createTime"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="updateBy" class="required">UPDATEBY_&nbsp;</label>
						<input id="updateBy" name="updateBy" type="text" class="k-textbox"
						data-bind="value: updateBy"
						value="${systemDBFuncMapping.updateBy}"
						validationMessage="请输入UPDATEBY_" /> <span class="k-invalid-msg"
						data-for="updateBy"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="updateTime" class="required">UPDATETIME_&nbsp;</label>
						<input id="updateTime" name="updateTime" type="text"
						class="k-textbox" data-role='datepicker' data-type="date"
						data-bind="value: updateTime"
						value="<fmt:formatDate value="${systemDBFuncMapping.updateTime}" pattern="yyyy-MM-dd"/>"
						validationMessage="请输入UPDATETIME_" /> <span class="k-invalid-msg"
						data-for="updateTime"></span></td>
				</tr>
				<tr>
					<td width="2%" align="left">&nbsp;</td>
					<td align="left"><label for="deleteFlag" class="required">DELETE_FLAG_&nbsp;</label>
						<input id="deleteFlag" name="deleteFlag" type="text"
						class="k-textbox" data-bind="value: deleteFlag"
						value="${systemDBFuncMapping.deleteFlag}"
						validationMessage="请输入DELETE_FLAG_" /> <span
						class="k-invalid-msg" data-for="deleteFlag"></span></td>
				</tr> --%>

				<!-- <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
	  <input type="radio" name="status" id="engine1" class="k-radio" value="0" >
	  <label class="k-radio-label" for="engine1">有效</label>&nbsp;&nbsp;
	  <input type="radio" name="status" id="engine2" class="k-radio" value="1" >
	  <label class="k-radio-label" for="engine2">无效</label>
	</td>
    </tr> -->
			</table>
		</form>
	</div>
	<script>
	
	function InitValue(){
		var params = {}, keys = {};
		$("[data-prop-for]").each(function(i, v){
			var $this = $(this), key = $this.attr("data-prop");
			var propFor = $this.attr("data-prop-for"), val, o;
			!params[propFor] && (params[propFor] = {}) ;
			if($this.is("table")){
				val = [];
				$this.find("tbody tr").each(function($i, tr){
					o = {};
					$(tr).find("[fieldname]").each(function(){
						o[$(this).attr("fieldname")] = $(this).val();
					});
					if(o.name){
						val.push(o);
					}
				});
			} else {
				val = $this.val();
			}
			params[propFor][key] = val;
			keys[propFor] = propFor;
		});
		
		for(var k in keys){
			if(params[k] && (typeof params[k] == "object")){
				params[k] = JSON.stringify(params[k]);
			}
		}
		return params;
	}
	
		jQuery(document).ready(function() {
			
			$(document).on("click.table.k-tools", "table button.k-tools", function(e){
				e.preventDefault();
				var $this = $(this), t = $this.attr("t");
				var $tr = $this.closest("tr");
				if(t == "del"){ // 删除
					if($tr.index()){
						$tr.remove();
					} else {
						alert("第一行不能删!");
					}
				} else if(t == "add") {
					$tr.after($tr.get(0).outerHTML);
				}
				
			});
			
			var mappings = [{text : '--请选择--', value : ''}];
			<c:if test="${!empty mapping}">
				<c:forEach items="${mapping}" var="m" > 
				mappings.push({
					text : "${m.value}",
					value : "${m.key}"
				});
				</c:forEach> 
			</c:if>
			
			
			 $("#dtype, #dtype0").kendoDropDownList({
                 dataTextField: "text",
                 dataValueField: "value",
                 dataSource: mappings,
                 width : 300
             });
			 
			 $(".k-test").on("click", function(e){
				 e.preventDefault();
				 var params = [];
				 $("table.k-test-params tr td input").each(function(i, v){
					 params.push($(this).val()); 
				 });
				 
				 var dtype = $("#dtype0").data(//
						 "kendoDropDownList").dataItem();
				 if(!dtype || !(dtype = dtype.text)){
					 alert("请选择目标测试库!");
				 }
				 var val = $("#textarea-01").val();
				 var $v = eval("var $convert = " + val);
				 
				 if($convert[dtype]){
					var ret =  $convert[dtype](params);
					
					if(ret){
						ret = JSON.parse(ret);
						
						alert(ret.name + "(" + ret.args.join(", ") + ")");
					}

				 } else {
					 alert("脚本模版未包含" + dtype + "!");
				 }
			 });
		});
	</script>
</body>
</html>