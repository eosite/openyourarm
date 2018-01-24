<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑组件</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script language="JavaScript">

	function submitRequest(form){
		document.iForm.action="<%=request.getContextPath()%>/mx/form/component/save";
        document.iForm.submit();
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">
<center>
<div class="content-block"  style=" width: 90%;" >
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑组件">&nbsp;编辑组件
</div>
 
<form id="iForm" name="iForm" method="post" data-role="validator" enctype="multipart/form-data" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${component.id}"/>
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
    <tr>
		<td width="20%" align="left"><label for="parentId" class="required">大类&nbsp;</label></td>
		<td align="left">
			<kendo:dropDownList id="parentId" name="parentId" dataTextField="name" dataValueField="id" 
			       value="${component.parentId}">
              <kendo:dataSource data="${components}"></kendo:dataSource>
            </kendo:dropDownList> 
		    <span class="k-invalid-msg" data-for="parentId"></span>
		</td>
    </tr>
	<tr>
		<td width="20%" align="left"><label for="type" class="required">类型&nbsp;</label></td>
		<td align="left">
		   <select id="type" name="type">
		    <option value="NumericTextBox">数值输入框</option>
			<option value="MaskedTextBox">掩码输入框</option>
			<option value="TextField">单行文本框</option>
			<option value="TextArea">多行文本框</option>
			<option value="Editor">富文本编辑器</option>
			<option value="Calendar">日历组件</option>
			<option value="DatePicker">日期选择</option>
			<option value="DateTimePicker">日期时间选择</option>
			<option value="TimePicker">时间选择</option>
			<option value="ComboBox">结合输入框</option>
			<option value="DropDownList">单项下拉选择</option>
			<option value="MultiSelect">多项下拉选择</option>
			<option value="Radio">单选框</option>
			<option value="CheckBox">复选框</option>
			<option value="Grid">数据网格</option>
			<option value="ListView">列表视图</option>
			<option value="TreeView">树型视图</option>
			<option value="TreeList">树型网格视图</option>
			<option value="page">表单页面</option>
			<option value="textbox">输入框</option>
		   </select>
		   <script type="text/javascript">
		        document.getElementById("type").value="${component.type}";
		   </script>
		</td>
    </tr> 
	<tr>
	<td width="20%" align="left"><label for="dataRole" class="required">data-role&nbsp;</label></td>
	<td align="left">
        <input id="dataRole" name="dataRole" type="text" class="k-textbox"  
	           data-bind="value: dataRole"  style=" width:320px;"
	           value="${component.dataRole}" validationMessage="请输入data-role"/>
	    <span class="k-invalid-msg" data-for="dataRole"></span>
	</td>
    </tr>
	<tr>
		<td width="20%" align="left">
		    <label for="kendoComponent" class="required">KendoUI类型&nbsp;</label>
		</td>
		<td align="left">
		    <select id="kendoComponent" name="kendoComponent">
				<option value="kendoAutoComplete">提示输入框(kendoAutoComplete)</option>
				<option value="kendoComboBox">组合输入框(kendoComboBox)</option>
				<option value="kendoNumericTextBox">数值输入框(kendoNumericTextBox)</option>
				<option value="kendoMaskedTextBox">掩码输入框(kendoMaskedTextBox)</option>
				<option value="kendoTextBox">单行文本框(kendoTextBox)</option>
				<option value="kendoEditor">富文本编辑器(kendoEditor)</option>
				<option value="kendoCalendar">日历组件(kendoCalendar)</option>
				<option value="kendoDatePicker">日期选择(kendoDatePicker)</option>
				<option value="kendoDateTimePicker">日期时间选择(kendoDateTimePicker)</option>
				<option value="kendoTimePicker">时间选择(kendoTimePicker)</option>
				<option value="kendoDropDownList">单项下拉选择(kendoDropDownList)</option>
				<option value="kendoMultiSelect">多项下拉选择(kendoMultiSelect)</option>
				<option value="kendoMultiSelectBox">带复选框的多项下拉选择(kendoMultiSelectBox)</option> 
				<option value="kendoGrid">数据网格(kendoGrid)</option>
				<option value="kendoListView">列表视图(kendoListView)</option>
				<option value="kendoTreeView">树型视图(kendoTreeView)</option>
				<option value="kendoTreeList">树型网格视图(kendoTreeList)</option>
				<option value="kendoScheduler">计划调度(kendoScheduler)</option>
				<option value="kendoUpload">文件上传(kendoUpload)</option>
				<option value="kendoButton">按钮(kendoButton)</option>
				<option value="kendoToolBar">工具条(kendoToolBar)</option>
				<option value="kendoColorPalette">调色板(kendoColorPalette)</option>
				<option value="kendoColorPicker">颜色选择(kendoColorPicker)</option>
				<option value="kendoPage">表单页面(kendoPage)</option>
				<option value="kendoTextBox">输入框(kendoTextBox)</option>
		    </select>
		    <script type="text/javascript">
		        document.getElementById("kendoComponent").value="${component.kendoComponent}";
		    </script>
		</td>
    </tr> 
	<tr>
		<td width="20%" align="left"><label for="name" class="required">名称&nbsp;</label></td>
		<td align="left">
			<input id="name" name="name" type="text" class="k-textbox"  
			 data-bind="value: name" style=" width:320px;"
		     value="${component.name}" validationMessage="请输入名称"/>
		    <span class="k-invalid-msg" data-for="name"></span>
		</td>
    </tr> 
	<tr>
        <td class="input-box2" valign="top">描述&nbsp;</td>
        <td>
          <textarea id="desc" name="desc" data-bind="value: desc" rows="6" cols="46" class="k-textbox" style="height:60px; width:320px;">${component.desc}</textarea>    
		</td>
      </tr>
	<tr>
		<td width="20%" align="left"><label for="jsFile" class="required">JS脚本文件&nbsp;</label></td>
		<td align="left">
			<input id="jsFile" name="jsFile" type="file" class="k-textbox input-file"  
			 data-bind="value: jsFile" style=" width:320px;"
		     validationMessage="请选择JS脚本文件"/>
		    <span class="k-invalid-msg" data-for="jsFile"></span>
		</td>
    </tr> 
	<tr>
		<td width="20%" align="left"><label for="imageFile" class="required">图片文件&nbsp;</label></td>
		<td align="left">
			<input id="imageFile" name="imageFile" type="file" class="k-textbox input-file"  
			 data-bind="value: imageFile" style=" width:320px;"
		     validationMessage="请选择图片文件"/>
		    <span class="k-invalid-msg" data-for="imageFile"></span>
		</td>
    </tr> 
	<tr>
		<td width="20%" align="left"><label for="mediumImageFile" >中等图片文件&nbsp;</label></td>
		<td align="left">
			<input id="mediumImageFile" name="mediumImageFile" type="file" class="k-textbox input-file"  
			 data-bind="value: mediumImageFile" style=" width:320px;"
		     validationMessage="请选择中等图片文件"/>
		     <span class="k-invalid-msg" data-for="mediumImageFile"></span>
		</td>
    </tr> 
	<tr>
		<td width="20%" align="left"><label for="smallImageFile"  >小图片文件&nbsp;</label></td>
		<td align="left">
			<input id="smallImageFile" name="smallImageFile" type="file" class="k-textbox input-file"  
			 data-bind="value: smallImageFile" style=" width:320px;"
		     validationMessage="请选择小图片文件"/>
		     <span class="k-invalid-msg" data-for="smallImageFile"></span>
		</td>
    </tr> 
    <tr>
    	<td width="20%" align="left"><label for="categoryMultiSelect"  >UI适用范围&nbsp;</label></td>
		<td align="left">
			<input type="hidden" name="category" id="category" value="${component.category}">
			<select id="categoryMultiSelect" name="categoryMultiSelect" ></select>
			<script type="text/javascript">
				var categorys = '${component.category}',categoryValue = [] ;
				if(categorys!=''){
					$.merge(categoryValue,categorys.split(','));
				}
				$("#categoryMultiSelect").kendoMultiSelect({
					  dataSource: new kendo.data.DataSource({
						  	transport: {
							    read: {
							      url: '<%=request.getContextPath()%>/mx/form/defined/getDictByCode',
							      data : {code : 'ui_type'},
								  type : 'post',
								  dataType : 'json',
							    }
						  	}
				      }),
					  dataTextField: "name",
					  dataValueField: "code",
					  animation: false,
					  value : categoryValue ,
					  change: function(e) {
						  var values = this.value();
						  $('#category').val(values.join(","))
					  }
				});
		    </script>
		</td>
    </tr>
    <tr>
		<td width="20%" align="left"><label for="integral" class="required">单击编辑&nbsp;</label></td>
		<td align="left">
			<input type="radio" name="integral" value="1" ${component.integral==1?'checked="true"':''}>是&nbsp;&nbsp;
			<input type="radio" name="integral" value="0"  ${component.integral==null||component.integral==0?'checked="true"':''}>否
		</td>
    </tr>
</table>   
</form>
<div align="center"><br />
<input type="button" name="bt01" value="确定" class="k-button k-primary"
	onclick="javascript:submitRequest(this.form);" /> 
<br />
<br />
</div>
</div>
</center>
<script type="text/javascript">
    jQuery(function() {
		    document.getElementById("parentId").value="${component.parentId}";
			document.getElementById("type").value="${component.type}";
			document.getElementById("kendoComponent").value="${component.kendoComponent}";
		});
</script>
</body>
</html>