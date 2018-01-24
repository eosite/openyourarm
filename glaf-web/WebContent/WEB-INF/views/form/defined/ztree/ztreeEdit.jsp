<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/layer/skin/layer.ext.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/glaf-base.js"></script>
<%@ include file="/WEB-INF/views/isdp/table/select_js.jsp"%>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath }' ;
	var layerIndex = -1;
	$(function() {
		data = JSON.parse('${data}');//获取后台参数
		//数据绑定
		var viewModel = kendo.observable({
			idValue : data.id,
			ridValue : data.rid,
			idKeyValue : data.idKey,
			indexValue : data.indexKey,
			pIdKeyValue : data.pIdKey,
			textNameValue : data.nameKey,
			iconValue : data.icon,
			iconOpenValue : data.iconOpen,
			iconCloseValue : data.iconClose,
		});
		kendo.bind($(document.body), viewModel);
		//end
		if (data.iconEnable) {
			$('#iconTr').removeAttr('style');
		}
		if (data.iconOpenEnable) {
			$('#iconOpenTr').removeAttr('style');
		}
		if (data.iconCloseEnable) {
			$('#iconCloseTr').removeAttr('style');
		}
		//按钮
		$("#confirm_field_btn").kendoButton({
			icon : "k-icon k-i-tick"
		});
		$("#close_field_btn").kendoButton({
			icon : "k-icon k-i-close"
		});
		//end
		
		$("#textName").kendoValidator({
	         messages: {
	             required: "显示文本 为必填项",
	             customRule1 : "显示文本 不能为空",
	         },
	         rules: {
	             customRule1: function(input){
	               return $.trim(input.val()) !== "";
	            }
	         }
	    });

	});
	//页面初始化结束

	//确定
	function confirmSave() {
		 var validatable = $("#textName").data("kendoValidator");
		 if (validatable.validate() === false) {
		 	return ;
		 }
		var param = "id=" + $('#id').val() + "&rid=" + $('#rid').val()+"&name=" + $('#textName').val()+"&pid=" + $('#pIdKey').val()
			+"&idKey=" + $('#idKey').val();
		if (data.iconEnable) {
			param += "&icon=" + $('#icon').val();
		}
		if (data.iconOpenEnable) {
			param += "&iconOpen=" + $('#iconOpen').val();
		}
		if (data.iconCloseEnable) {
			param += "&iconClose=" + $('#iconClose').val();
		}
		$.ajax({
			type : "POST",
			url : contextPath + '/mx/form/treeData/save',
			data : param,
			async : false,
			success : function(msg) {
				alert('操作成功');
				var treeObj = window.parent.$.fn.zTree.getZTreeObj(data.treeId);
				var nodes;
				if (treeObj.setting.check.enable) {
					nodes = treeObj.getCheckedNodes(true);
				} else {
					nodes = treeObj.getSelectedNodes();
				}
				if(nodes.length>0){
					if(data.pIdKey == nodes[0].indexkey){
						nodes[0].isParent = true ;
						treeObj.reAsyncChildNodes(nodes[0], "refresh");
					}else{
						treeObj.reAsyncChildNodes(null, "refresh");
					}
				}else{
					treeObj.reAsyncChildNodes(null, "refresh");
				}
				if(!data.epac){
					if(parent.layer){
						parent.layer.close(parent.layer.getFrameIndex());
					}else{
						parent.ztreeOpenShow.close();
					}
				}
			},
			error : function() {
				alert("操作失败");
			}
		});
	}

	function colseWin(){
		if(parent.layer){
			parent.layer.close(parent.layer.getFrameIndex());
		}else{
			parent.ztreeOpenShow.close();
		}
	}
</script>
</head>
<body>
	<div style="width: 100%">
		<input type="hidden" id="id" style="width: 200px" data-role="maskedtextbox" data-bind="value:idValue" /> <input type="hidden" id="rid" style="width: 200px"
			data-role="maskedtextbox" data-bind="value:ridValue" /> <input type="hidden" id="idKey" style="width: 200px" data-role="maskedtextbox" data-bind="value:idKeyValue" />
		<input type="hidden" id="index" style="width: 200px" data-role="maskedtextbox" data-bind="value:indexValue" /> <input type="hidden" id="pIdKey" style="width: 200px"
			data-role="maskedtextbox" data-bind="value:pIdKeyValue" />
		<table style="width: 100%; text-align: center;">
			<tr>
				<td>显示文本<span style="color: red;">*</span>：
				</td>
				<td><input type="text" id="textName" name="显示文本 " style="width: 200px" data-role="maskedtextbox" required data-bind="value:textNameValue" /></td>
			</tr>
			<tr id="iconTr" style="display: none;">
				<td>显示图标：</td>
				<td><input type="text" id="icon" style="width: 200px" data-role="maskedtextbox" data-bind="
            	value:iconValue"
					onclick="javascript:openWindow('/glaf/mx/image/choose?elementId=icon', self, 50, 50, 1098, 520);" /></td>
			</tr>
			<tr id="iconOpenTr" style="display: none;">
				<td>打开图标：</td>
				<td><input type="text" id="iconOpen" style="width: 200px" data-role="maskedtextbox" data-bind="
            	value:iconOpenValue"
					onclick="javascript:openWindow('/glaf/mx/image/choose?elementId=iconOpen', self, 50, 50, 1098, 520);" /></td>
			</tr>
			<tr id="iconCloseTr" style="display: none;">
				<td>关闭图标：</td>
				<td><input type="text" id="iconClose" name="iconClose" style="width: 200px" data-role="maskedtextbox" data-bind="
            	value:iconCloseValue"
					onclick="javascript:openWindow('/glaf/mx/image/choose?elementId=iconClose', self, 50, 50, 1098, 520);" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<button id="confirm_field_btn" class="k-button" onclick="confirmSave()">确定</button>
					<button id="close_field_btn" class="k-button" onclick="colseWin()">关闭</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>