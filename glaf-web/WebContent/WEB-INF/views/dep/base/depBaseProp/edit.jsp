<%@ page contentType="text/html;charset=UTF-8"%><%@ page import="java.util.*"%><%@ page import="com.glaf.core.util.*"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%><%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%><%	String theme = com.glaf.core.util.RequestUtils.getTheme(request);	request.setAttribute("theme", theme);%><!DOCTYPE html><html><head><meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>编辑规则属性信息</title><%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%><style type="text/css">.k-textbox {	width: 18.8em;}.main-section {	width: 800px;	padding: 0;}label {	display: inline-block;	width: 100px;	text-align: right;	padding-right: 10px;}.required {	font-weight: bold;}.accept,.status {	padding-left: 90px;}.confirm {	text-align: right;}.valid {	color: green;}.invalid {	color: red;}span.k-tooltip {	margin-left: 6px;}.extField,.openWindowClass{	display:none;}</style><script type="text/javascript">                  jQuery(function() {    var viewModel = kendo.observable({        "ruleCode": "${depBaseProp.ruleCode}",        "ruleName": "${depBaseProp.ruleName}",        "ruleDesc": "${depBaseProp.ruleDesc}",        "sysCategory": "${depBaseProp.sysCategory}",        "useCategory": "${depBaseProp.useCategory}",        "openFlag": "${depBaseProp.openFlag}",        "orderNo": "${depBaseProp.orderNo}",        "readOnly": "${depBaseProp.readOnly}",        "repeatFlag": "${depBaseProp.repeatFlag}",        "notNull": "${depBaseProp.notNull}",        "inputType": "${depBaseProp.inputType}",        "defaultVal": "${depBaseProp.defaultVal}",        "creator": "${depBaseProp.creator}",        "createDateTime": "<fmt:formatDate value='${depBaseProp.createDateTime}' pattern='MM/dd/yyyy'/>",        "modifier": "${depBaseProp.modifier}",        "modifyDateTime": "<fmt:formatDate value='${depBaseProp.modifyDateTime}' pattern='MM/dd/yyyy'/>",        "delFlag": "${depBaseProp.delFlag}",        "ruleId": "${depBaseProp.ruleId}"    });    kendo.bind(jQuery("#iForm"), viewModel);   });	function getDictDataSource(treeCode){		return new kendo.data.DataSource({			transport:{				read:{					url:"${pageContext.request.contextPath}/rs/form/formDictory/dataByTreeCode",					type: "POST",					data:{treeCode:treeCode},					//contentType: "application/json",					dataType:"json"				}			}		});	}	function getDictTreeDataSource(nodeCode){		return new kendo.data.DataSource({			transport:{				read:{					url:"${pageContext.request.contextPath}/rs/form/formDictTree/treeJson",					type:"POST",					data:{nodeCode:nodeCode},					dataType:"json"				}			}		});	}    jQuery(document).ready(function() {    	  $("#extJsonCheck").click(function(){          	if($("#extJsonCheck").is(":checked")){				$(".extField").show();          	}else{          		$(".extField").hide();          	}          });    	  //初始化控件          jQuery("#iconButton").kendoButton({                spriteCssClass: "k-icon"          });          $("#openFlag").kendoDropDownList({          	dataSource:getDictDataSource("isnot"),          	dataTextField:"name",          	dataValueField:"value",          	value:"${depBaseProp.openFlag}"          });          $("#readOnly").kendoDropDownList({          	dataSource:getDictDataSource("isnot"),          	dataTextField:"name",          	dataValueField:"value",          	value:"${depBaseProp.readOnly}"          });          $("#repeatFlag").kendoDropDownList({          	dataSource:getDictDataSource("isnot"),          	dataTextField:"name",          	dataValueField:"value",          	value:"${depBaseProp.repeatFlag}"          });           $("#notNull").kendoDropDownList({          	dataSource:getDictDataSource("isnot"),          	dataTextField:"name",          	dataValueField:"value",          	value:"${depBaseProp.notNull}"          });          $("#inputType").kendoDropDownList({          	dataSource:getDictDataSource("ruleInputType"),          	dataTextField:"name",          	dataValueField:"value",          	value:"${depBaseProp.inputType}",          	change:function(e){          		if(this.value()=='openWinSelect' || this.value()=='importArgsData'){          			//如果是弹窗，打开扩展属性          			if(!$("#extJsonCheck").is(":checked")){          				$("#extJsonCheck").trigger("click");          			}          			$('.openWindowClass').show();          		}else{          			$("#pageName").val('');					$("#pageId").val('');					$("#pageUrl").val('');          			$('.openWindowClass').hide();          		}          	},          	dataBound:function(e){          		if(this.value()=='openWinSelect' || this.value()=='importArgsData'){          			$('.openWindowClass').show();          		}          	}          });          $("#dataType").kendoDropDownList({          	dataSource:getDictDataSource("dType"),          	dataTextField:"name",          	dataValueField:"value"          });          $("#dataSource").kendoDropDownList({          	dataSource:getDictDataSource("widgetSourceType"),          	dataTextField:"name",          	dataValueField:"value",          	optionLabel:"请选择"          });          $("#dictCode").kendoDropDownList({          	dataSource:getDictTreeDataSource("widgetSource"),          	dataTextField:"name",          	dataValueField:"code",          	optionLabel:"请选择"          });          $("#propScope").kendoMultiSelect({          	dataTextField:"name",      		dataValueField:"code",      		value:[${uiid}],      		dataSource:new kendo.data.DataSource({      			transport:{      				read:{      					"contentType": "application/json",      					"type": "POST",                		"url": "${pageContext.request.contextPath}/rs/dep/base/depBaseUI/json"      				}      			}      		})          });          $("#compProp").kendoMultiSelect({          	dataTextField:"name",      		dataValueField:"id",      		value:[${compids}],      		dataSource:new kendo.data.DataSource({      			transport:{      				read:{      					contentType:"application/json",      					type:"post",      					url:"${pageContext.request.contextPath}/rs/dep/base/depBaseComponent/json"      				}      			}      		})          });          $("#selectPage").kendoButton({          	click:function(e){          		var link = '${pageContext.request.contextPath}/mx/form/defined/ex/selectPage?fn=selectPageBack';          		var width = 800;          		var height = 550;          		var top = (window.screen.availHeight-height)/2;          		var left = (window.screen.availWidth-width)/2;          		window.win = window.open(link,'选择页面','width='+width+',height='+height+',top='+top+',left='+left+',toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');          	}          });          //设置扩展属性值          if('${depBaseProp.extJson}' !== ''){	          var extJson = JSON.parse('${depBaseProp.extJson}');	          if(extJson.extJsonCheck){	          	$("#extJsonCheck").attr("checked",true);	          	$(".extField").show();	          	//弹窗配置	          	if(extJson.pageId){	          		$("#pageId").val(extJson.pageId);	          	}				if(extJson.pageName){	          		$("#pageName").val(extJson.pageName);	          	}	          	if(extJson.pageUrl){	          		$("#pageUrl").val(extJson.pageUrl);	          	}	          	///////end	          	if(extJson.commonRule){	          		//通用规则	          		$("#commonRule").attr("checked",true);	          	}	          	if(extJson.dataType){	          		$("#dataType").data("kendoDropDownList").value(extJson.dataType);	          	}	          	if(extJson.dataSource){	          		$("#dataSource").data("kendoDropDownList").value(extJson.dataSource);	          	}	          	if(extJson.dictCode){	          		$("#dictCode").data("kendoDropDownList").value(extJson.dictCode);	          	}	          	if(extJson.labelText){	          		$("#labelText").val(extJson.labelText);	          	}	          	if(extJson.dataUnit){	          		$("#dataUnit").val(extJson.dataUnit);	          	}	          	if(extJson.dataFormatter){	          		$("#dataFormatter").val(extJson.dataFormatter);	          	}	          	if(extJson.dataEnum){	          		$("#dataEnum").val(extJson.dataEnum);	          	}	          	if(extJson.dataMinValue){	          		$("#dataMinValue").data("kendoNumericTextBox").value(extJson.dataMinValue);	          	}	          	if(extJson.dataMaxValue){	          		$("#dataMaxValue").data("kendoNumericTextBox").value(extJson.dataMaxValue);	          	}	          	if(extJson.dataDecimal){	          		$("#dataDecimal").data("kendoNumericTextBox").value(extJson.dataDecimal);	          	}	          	if(extJson.dataDigit){					$("#dataDigit").data("kendoNumericTextBox").value(extJson.dataDigit);	          	}	          	if(extJson.customAttr){	          		$("#customAttr").val(JSON.stringify(extJson.customAttr));	          	}	          }		  }    });	function selectPageBack(retValue){		$("#pageName").val(retValue.name);		$("#pageId").val(retValue.id);		$("#pageUrl").val(retValue.url);		window.win.close();	}    $(function () {        var container = jQuery("#iForm");        kendo.init(container);        container.kendoValidator({            rules: {              greaterdate: function (input) {                if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                                       var date = kendo.parseDate(input.val()),                   otherDate = kendo.parseDate(jQuery("[name='" + input.data("greaterdateField") + "']").val());                   return otherDate == null || otherDate.getTime() < date.getTime();                 }                 return true;              }          }        });    });    /*验证RuleCode是否已经存在*/    function validateRuleCode(o){    	var validFlag = true;    	var ruleCode = o.value;    	if(ruleCode===''){    		showHideValidMsg("ruleCodeMsg",o.validationMessage,true);    		return false;    	}else if($('#ruleId').val()!=='' && '${depBaseProp.ruleCode}'===ruleCode){			return true;    	}    	$.ajax({		    		url: '${pageContext.request.contextPath}/rs/dep/base/depBaseProp/validateRuleCode',		    		type: 'post',		    		dataType: 'json',		    		async:false,		    		data: {ruleCode: ruleCode},		    		success:function(data){		    			if(data.statusCode==200){		    				if(data.rstNumber>0){		    					showHideValidMsg(o.id+"-msg","规则代码重复",true);		    					validFlag = false;		    				}else{		    					showHideValidMsg(o.id+"-msg","规则代码重复",false);		    				}		    			};		    		}		    	});    	return validFlag;    }    function showHideValidMsg(targetId,msg,showOrHide){    	var target = $("#"+targetId);    	if(showOrHide){    		target.addClass("k-widget");    		target.addClass('k-tooltip');    		target.addClass('k-tooltip-validation');			target.attr('role','alert');			target.show().html("<span class='k-icon k-warning'></span>"+msg);    	}else{    		target.removeClass("k-widget");    		target.removeClass("k-tooltip");    		target.removeClass("k-tooltip-validation");    		target.removeAttr('role');			target.hide().html("");    	}    }   function save(){       var form = document.getElementById("iForm");       var validator = jQuery("#iForm").data("kendoValidator");       if (validator.validate() && validateRuleCode(document.getElementById('ruleCode'))) {	   		var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseProp/saveDepBaseProp";	   		var propScopeMultiSelect = $("#propScope").data("kendoMultiSelect");			$("#propScope").val(propScopeMultiSelect.value());			var compPropMultiSelect = $("#compProp").data("kendoMultiSelect");			$("#compProp").val(compPropMultiSelect.value());			var params = jQuery("#iForm").formSerialize();			jQuery.ajax({				type : "POST",				url : link,				dataType : 'json',				data : params,				error : function(data) {					alert('服务器处理错误！');				},				success : function(data) {					if (data != null && data.message != null) {						alert(data.message);					} else {						alert('操作成功完成！');					}					window.parent.location.reload();				}			});		}	}</script>	</head><body style="margin-top: 0px;">	<div id="main_content" class="k-content" style="padding-top:5px">		<form id="iForm" name="iForm" method="post" data-role="validator"			novalidate="novalidate">			<input type="hidden" id="ruleId" name="ruleId"				value="${depBaseProp.ruleId}" />			<input type="hidden" id="categoryId" name="categoryId" value="${param.categoryId}" />			<table width="95%" align="center" border="0" cellspacing="0"				cellpadding="5">				<tr>					<td align="left"><label class="required">规则名称&nbsp;</label></td>					<td align="left">						<input id="ruleName" name="ruleName" type="text" class="k-textbox" required						data-bind="value: ruleName" value="${depBaseProp.ruleName}"						validationMessage="请输入规则名称" /> <span class="k-invalid-msg"						data-for="ruleName"></span></td>					<td align="left"><label class="required">规则代码&nbsp;</label></td>					<td align="left">						<input id="ruleCode" name="ruleCode" type="text" class="k-textbox" required						data-bind="value: ruleCode" value="${depBaseProp.ruleCode}" onblur="validateRuleCode(this);"  readonly 						validationMessage="请输入规则代码" /> <span class="k-invalid-msg" id="ruleCode-msg"						data-for="ruleCode"></span></td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">系统规则分类&nbsp;</label></td>					<td align="left">						<input id="sysCategory" name="sysCategory" type="text"						class="k-textbox" data-bind="value: sysCategory"						value="${depBaseProp.sysCategory}" validationMessage="请输入系统规则分类" />						<span class="k-invalid-msg" data-for="sysCategory"></span></td>					<td width="2%" align="left"><label class="required">使用规则分类&nbsp;</label></td>					<td align="left">						<input id="useCategory" name="useCategory" type="text"						class="k-textbox" data-bind="value: useCategory"						value="${depBaseProp.useCategory}" validationMessage="请输入使用规则分类" />						<span class="k-invalid-msg" data-for="useCategory"></span></td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">默认值&nbsp;</label></td>					<td align="left">						<input id="defaultVal" name="defaultVal" type="text"						class="k-textbox" data-bind="value: defaultVal"						value="${depBaseProp.defaultVal}" validationMessage="请输入默认值" /> <span						class="k-invalid-msg" data-for="defaultVal"></span>					</td>					<td width="2%" align="left"><label class="required">排序号&nbsp;</label></td>					<td align="left">						<input id="orderNo" name="orderNo" type="text" class="k-textbox"						data-bind="value: orderNo" value="${depBaseProp.orderNo}"						validationMessage="请输入排序号" /> <span class="k-invalid-msg"						data-for="orderNo"></span></td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">录入方式&nbsp;</label></td>					<td align="left">						<input id="inputType" name="inputType" /> <span class="k-invalid-msg" data-for="inputType"></span>					</td>					<td width="2%" align="left"><label class="required openWindowClass">弹出页面&nbsp;</label></td>					<td>						<input id="pageName" name="pageName" class="k-textbox openWindowClass" readonly style="width:70%" />						<input id="pageId" name="pageId" type="hidden" readonly />						<input id="pageUrl" name="pageUrl" type="hidden" readonly />						<button id="selectPage" type="button" class="k-button openWindowClass">选择</button>					</td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">开放用户&nbsp;</label></td>					<td align="left">						<input id="openFlag" name="openFlag" /> <span class="k-invalid-msg"						data-for="openFlag"></span></td>					<td width="2%" align="left"><label class="required">是否只读&nbsp;</label></td>					<td align="left">						<input id="readOnly" name="readOnly" /> <span class="k-invalid-msg"						data-for="readOnly"></span></td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">是否允许重复&nbsp;</label></td>					<td align="left">						<input id="repeatFlag" name="repeatFlag" />						<span class="k-invalid-msg" data-for="repeatFlag"></span></td>					<td width="2%" align="left"><label class="required">是否非空&nbsp;</label></td>					<td align="left">						<input id="notNull" name="notNull" /> <span class="k-invalid-msg"						data-for="notNull"></span></td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">规则描述&nbsp;</label></td>					<td align="left" colspan="3">						<textarea id="ruleDesc" name="ruleDesc" class="k-textbox" style="width:100%;height:40px" 							data-bind="value: ruleDesc" validationMessage="请输入规则描述">${depBaseProp.ruleDesc}</textarea> 						<span class="k-invalid-msg" data-for="ruleDesc"></span>					</td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">适用UI&nbsp;</label></td>					<td align="left" colspan="3">						<input id="propScope" name="propScope" class="k-textbox" style="width:100%;" /><span class="k-invalid-msg" data-for="propScope"></span>					</td>				</tr>				<tr>					<td width="2%" align="left"><label class="required">适用组件&nbsp;</label></td>					<td align="left" colspan="3">						<input id="compProp" name="compProp" class="k-textbox" style="width:100%;" /><span class="k-invalid-msg" data-for="compProp"></span>					</td>				</tr>				<tr>					<td width="2%" align="left"><label for="extJsonCheck" class="required"><input type="checkbox" id="extJsonCheck" name="extJsonCheck" />扩展属性&nbsp;</label></td>					<td align="left" colspan="3">&nbsp;</td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">通用规则&nbsp;</label></td>					<td><input type="checkbox" id="commonRule" name="commonRule" /></td>					<td width="2%" align="left"><label class="required">数据类型&nbsp;</label></td>					<td><input id="dataType" name="dataType" style="width:100%;"/></td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">显示文本&nbsp;</label></td>					<td><input id="labelText" name="labelText" class="k-textbox" style="width:100%;" /></td>					<td width="2%" align="left"><label class="required">单位&nbsp;</label></td>					<td><input id="dataUnit" name="dataUnit" class="k-textbox" style="width:100%;"/></td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">数据来源&nbsp;</label></td>					<td><input id="dataSource" name="dataSource" style="width:100%;"/></td>					<td width="2%" align="left"><label class="required">字典代码&nbsp;</label></td>					<td><input id="dictCode" name="dictCode" style="width:100%;"/></td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">数据格式&nbsp;</label></td>					<td><input id="dataFormatter" name="dataFormatter" class="k-textbox" style="width:100%;"/></td>					<td width="2%" align="left"><label class="required">枚举值&nbsp;</label></td>					<td><input id="dataEnum" name="dataEnum" class="k-textbox" style="width:100%;"/></td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">最小值&nbsp;</label></td>					<td><input id="dataMinValue" name="dataMinValue" data-role="numerictextbox" data-format="n0" style="width: 100%;"/></td>					<td width="2%" align="left"><label class="required">最大值&nbsp;</label></td>					<td><input id="dataMaxValue" name="dataMaxValue" data-role="numerictextbox" data-format="n0" style="width:100%;"/></td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">小数位数&nbsp;</label></td>					<td><input id="dataDecimal" name="dataDecimal" data-role="numerictextbox" data-format="n0" data-min="0" data-max="4" style="width:100%;"/></td>					<td width="2%" align="left"><label class="required">总位数&nbsp;</label></td>					<td><input id="dataDigit" name="dataDigit" data-role="numerictextbox" data-format="n0" style="width:100%;"/></td>				</tr>				<tr class="extField">					<td width="2%" align="left"><label class="required">自定义扩展属性&nbsp;</label></td>					<td colspan="3">						<textarea id="customAttr" name="customAttr" class="k-textbox" style="width:100%;height:40px"							 validationMessage="请自定义扩展属性"></textarea> 							 <font color="red">* JSON：格式属性与值都需要使用引号,如：{"a":"a","b":"b"}或{'a':'a','b':'b'}</font>						<span class="k-invalid-msg" data-for="customAttr"></span>					</td>				</tr>				<tr>					<td colspan="4" align="center" valign="bottom" height="20">&nbsp;						<div>							<button type="button" id="iconButton" class="k-button k-primary"								style="width: 90px" onclick="javascript:save();">保存</button>						</div>					</td>				</tr>			</table>		</form>	</div></body></html>