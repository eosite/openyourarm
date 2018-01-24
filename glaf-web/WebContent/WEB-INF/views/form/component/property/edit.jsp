<%@ page contentType="text/html;charset=UTF-8" %>
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
<title>编辑组件属性信息</title>
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
</style>
<script type="text/javascript">
                
  jQuery(function() {
    var viewModel = kendo.observable({
        "name": "${formComponentProperty.name}",
        "dataType": "${formComponentProperty.dataType}",
        "type": "${formComponentProperty.type}",
		"category": "${formComponentProperty.category}",
        "title": "${formComponentProperty.title}",
        "desc": "${formComponentProperty.desc}",
        "value": "${formComponentProperty.value}",
        "defValue": "${formComponentProperty.defValue}"
    });

    kendo.bind(jQuery("#iForm"), viewModel);

   });

    jQuery(document).ready(function() {
          jQuery("#iconButton").kendoButton({
                spriteCssClass: "k-icon"
          });
          
          $("#name").focus();
    });

    jQuery(function () {
        var container = jQuery("#iForm");
        kendo.init(container);
        container.kendoValidator({
                rules: {
                      greaterdate: function (input) {
                            if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                    
                               var date = kendo.parseDate(input.val()),
                               otherDate = kendo.parseDate(jQuery("[name='" + input.data("greaterdateField") + "']").val());
                               return otherDate == null || otherDate.getTime() < date.getTime();
                             }

                             return true;
                          }
                      }
        });
    });

   function save(){
       var form = document.getElementById("iForm");
       var validator = jQuery("#iForm").data("kendoValidator");
       if (validator.validate()) {
	   //form.method="post";
	   //form.action = "<%=request.getContextPath()%>/mx/form/component/property/saveFormComponentProperty";
	   //form.submit();
	   var link = "<%=request.getContextPath()%>/mx/form/component/property/saveFormComponentProperty";
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
						   //alert('操作成功完成！');
					   }
					   window.parent.location.reload();
				   }
			 });
       }
   }
   
 </script>
</head>
<body style="margin-top:0px;">
<div id="main_content" class="k-content ">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="编辑组件属性信息">&nbsp;
编辑组件属性信息</div>
<br>
<form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
<input type="hidden" id="id" name="id" value="${formComponentProperty.id}"/>
<input type="hidden" id="kendoComponent" name="kendoComponent" value="${kendoComponent}" />
<input type="hidden" id="componentId" name="componentId" value="${componentId}" />
<input type="hidden" id="type" name="type" value="${param.type}" />
<table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
   <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="name" class="required">名称&nbsp;</label>
        <input id="name" name="name" type="text" class="k-textbox"  
	           data-bind="value: name"  style=" width:320px;"
	           value="${formComponentProperty.name}" validationMessage="请输入名称"/>
	    <span class="k-invalid-msg" data-for="name"></span>
	</td>
    </tr>
	
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="title" class="required">标题&nbsp;</label>
        <input id="title" name="title" type="text" class="k-textbox"  
	           data-bind="value: title" style=" width:320px;"
	           value="${formComponentProperty.title}" validationMessage="请输入标题"/>
	    <span class="k-invalid-msg" data-for="title"></span>
	</td>
    </tr>
    
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="isSort" class="required">设为分类&nbsp;</label>
		<input type="radio" name="isSort" value="0">是&nbsp;&nbsp;
		<input type="radio" name="isSort" value="1"  checked="checked">否
		<script type="text/javascript">
			var v = "${formComponentProperty.isSort}",radios = document.getElementsByName("isSort");
			
			for(var i = 0; i < radios.length; i ++){
				if(radios[i].value == v){
					radios[i].checked = true;
					break;
				}
			}
		</script>
	</td>
    </tr>
    
	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="category" class="required">分类&nbsp;</label>
        <select id="category" name="category">
			<option value="Base">基础</option>
			<option value="Data">数据</option>
			<option value="Facede">外观</option>
			<option value="Action">动作</option>
        </select>  
		<script type="text/javascript">
		    document.getElementById("category").value="${formComponentProperty.category}";
		</script>
	    <span class="k-invalid-msg" data-for="category"></span>
	</td>
    </tr>
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="dataType" class="required">数据类型&nbsp;</label>
        <select id="dataType" name="dataType">
			<option value="boolean">布尔型</option>
			<option value="int">整数型</option>
			<option value="double">数值型</option>
			<option value="date">日期时间</option>
			<option value="string">字符串</option>
        </select>  
		<script type="text/javascript">
		    document.getElementById("dataType").value="${formComponentProperty.dataType}";
		</script>
	    <span class="k-invalid-msg" data-for="dataType"></span>
	</td>
    </tr>
    <!-- 
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="type" class="required">类型&nbsp;</label>
        <select id="type" name="type">

		<c:if test="${!empty property_sort}">
			<c:forEach var="dict" items="${property_sort}">
              <option value="${dict.code}">${dict.name} [${dict.code}]</option>
			</c:forEach>
		 </c:if>
		 
        </select>  
		<script type="text/javascript">
		
			var t = "${formComponentProperty.type}";
			
		    document.getElementById("type").value = t;
		    
		    
		</script>
	    <span class="k-invalid-msg" data-for="type"></span>
	</td>
    </tr>
	 -->
	 
	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="editor" class="required">编辑器&nbsp;</label>
        <select id="editor" name="editor">
		 <option value="">----请选择----</option>
		 <c:if test="${!empty dictories}">
			<c:forEach var="dict" items="${dictories}">
              <option value="${dict.id}">${dict.name} [${dict.code}]</option>
			</c:forEach>
		 </c:if>
        </select>  
		<script type="text/javascript">
		    document.getElementById("editor").value="${formComponentProperty.editor}";
		</script>
	    <span class="k-invalid-msg" data-for="editor"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="enumValue" class="required">枚举值&nbsp;</label>
        <select id="enumValue" name="enumValue">
		  <option value="">----请选择----</option>
		  <c:if test="${!empty categories}">
			<c:forEach var="cat" items="${categories}">
              <option value="${cat.id}">${cat.name} [${cat.code}]</option>
			</c:forEach>
		  </c:if>
        </select>  
		<script type="text/javascript">
		    document.getElementById("enumValue").value="${formComponentProperty.enumValue}";
		</script>
	    <span class="k-invalid-msg" data-for="enumValue"></span>
	</td>
    </tr>
    
	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="parentId" class="required">引入到&nbsp;</label>
        <select id="parentId" name="parentId" onchange="parentIdOnchange(this)"  > 
		  <option value="">----请选择----</option>
		  <c:if test="${!empty props}">
			<c:forEach var="cat" items="${props}">
              <option value="${cat.id}" enumValue="${cat.enumValue}" >${cat.title}</option>
			</c:forEach>
		  </c:if>
        </select>  
		<script type="text/javascript">
		    document.getElementById("parentId").value = "${formComponentProperty.parentId}" || '${parentId}';
		    function parentIdOnchange(obj){
		    	var option = obj.options[obj.selectedIndex];
		    	var enumValue = $(option).attr('enumValue');
		    	
		    	if(enumValue){
		    		$.ajax({
		    			url : '<%=request.getContextPath()%>/mx/form/component/property/dict',
		    			type : 'post',
		    			async : false,
		    			data : {id : enumValue},
		    			dataType : 'json',
		    			success : function(data){
		    				if(data){
		    					
		    					$('#pValue_div').empty();
		    					
		    					var $select = $('<select>',{
		    						id : 'pValue_',
		    						multiple : "multiple",
		    						width : '220px'
		    					});
		    					var options = [];
		    					$.each(data,function(index,item){
		    						options.push("<option value='"+ item.code +"'>");
		    						options.push(item.name);
		    						options.push("</option>");
		    					});
		    					$select.append(options.join(''));
		    					$('#pValue_div').append($select).append($('<input>',{
		    						id : 'pValue',
		    		    			name : 'pValue',
		    		    			style : 'width:220px;',
		    		    			type :'hidden'
		    					}));
		    					initPvalue();
		    				}
		    			}
		    		});
		    	}else{
		    		$('#pValue_div').css({
		    			style : 'margin-right: 250px; float: right;'
		    		}).html($('<input>',{
		    			id : 'pValue',
		    			name : 'pValue',
		    			style : 'width:220px;'
		    		}));
		    	}
		    }
		    
		    function initPvalue(){
				var pValueVal = "${formComponentProperty.pValue}";
				if(pValueVal){
					pValueVal = pValueVal.split(',');
				}
				 $('#pValue_').kendoMultiSelect({
				 	value : pValueVal,
					animation: false,
					change: function(e) {
					    var value = this.value();
					    if(value){
					    	if($('#pValue')[0]){
					    		$('#pValue').val(value.join(','));
					    	}
					    }
					}
				 });
			}
		</script>
	    <span class="k-invalid-msg" data-for="parentId"></span>
	</td>
    </tr>
    
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="pValue" class="pValue">引入值&nbsp;</label>
 		<div style="margin-right: 200px; float: right;" id="pValue_div">
	    	<c:choose>
	        	<c:when test="${!empty pValues}">	
	        		<select id="pValue_" style="width:220px;" multiple="multiple">
						<c:forEach var="cat" items="${pValues}">
			              <option value="${cat.code}">${cat.name}</option>
						</c:forEach>
			        </select>
	        		<input type="hidden" name="pValue" id="pValue" >
			        <script type="text/javascript">
					    initPvalue();
					</script>
	        	</c:when>
	        	<c:otherwise>
	        		<input id="pValue" name="pValue" style='width : 220px;'>
	        	</c:otherwise>
	        </c:choose>
	    </div>
		<script type="text/javascript">
		    document.getElementById("pValue").value="${formComponentProperty.pValue}";
		</script>
	    <span class="k-invalid-msg" data-for="pValue"></span>
	</td>
    </tr>

    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="value" class="required">匹配值&nbsp;</label>
		<textarea id="value" name="value" data-bind="value: value" rows="6" cols="46" class="k-textbox" 
		          style="height:60px; width:320px;">${formComponentProperty.value}</textarea>
	    <span class="k-invalid-msg" data-for="value"></span>
	</td>
    </tr>
    
    <tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="defValue" class="required">默认值&nbsp;</label>
		<input id="defValue" name="defValue" data-bind="value: defValue" value="${formComponentProperty.defValue}" class="k-textbox" 
		          style="width:320px;" />
	    <span class="k-invalid-msg" data-for="defValue"></span>
	</td>
    </tr>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="desc" class="required">描述&nbsp;</label>
		<textarea id="desc" name="desc" data-bind="value: desc" rows="6" cols="46" class="k-textbox" 
		          style="height:60px; width:320px;">${formComponentProperty.desc}</textarea>
	    <span class="k-invalid-msg" data-for="desc"></span>
	</td>
    </tr>
    
	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="desc" class="required">触发事件&nbsp;</label>
	    <input type="radio" name="eventFlag" value="1" >是&nbsp;&nbsp;
		<input type="radio" name="eventFlag" value="0" checked="checked">否
	</td>
    </tr>
    <script type="text/javascript">
		var eventFlag = "${formComponentProperty.eventFlag}",eventFlagRadios = document.getElementsByName("eventFlag");
		
		for(var i = 0; i < eventFlagRadios.length; i ++){
			if(eventFlagRadios[i].value == eventFlag){
				eventFlagRadios[i].checked = true;
				break;
			}
		}
	</script>

	<tr>
	<td width="2%" align="left">&nbsp;</td>
	<td align="left">
	    <label for="desc" class="required">是否启用&nbsp;</label>
		<input type="radio" name="lock" value="0" checked="checked">启用&nbsp;&nbsp;
		<input type="radio" name="lock" value="1">禁用
	</td>
    </tr>
 
    <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
        <div>
          <button type="button" id="iconButton" class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
	    </div>
	</td>
      </tr>
</table>   
</form>
</div>     
<script>
    jQuery(document).ready(function() {
	    
    });
</script>
</body>
</html>