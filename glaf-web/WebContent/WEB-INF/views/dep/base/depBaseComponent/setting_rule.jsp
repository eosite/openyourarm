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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则属性列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">

    var treeObj,rootCategoryId;
    function reloadZTree(data){

        var setting = {
            view: {
                showIcon:true,
                showLine:true,
                showTitle:false,
                selectedMulti: false
            },
            data:{
                simpleData:{
                    enable:true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: -1
                }
            },
            callback: {
                onClick: zTreeOnClick
            }
        };

        $.fn.zTree.init($("#myTree"), setting, data);
        treeObj = $.fn.zTree.getZTreeObj("myTree");
    }

     function zTreeOnClick(event, treeId, treeNode, clickFlag){
        var url = "${pageContext.request.contextPath}/rs/dep/base/depBaseProp/list";
        $.post(url, {delFlag: '0',categoryId:treeNode.id,rows:1000}, function(data) {
            if(data.total > 0){
                var rows = data.rows;
                var $content = $("<div>");
                for(var i=0;i<rows.length;i++){
                    var $inputbox = $('<input>');
                    $inputbox.attr({
                        'type': 'checkbox',
                        'name': 'ruleId',
                        'id': rows[i].ruleId,
                        'value': rows[i].ruleId,
                        'onchange': 'javascript:changeValue(this.checked,this.value)'
                    });

                    var componentIds = rows[i].componentIds;
                    $.each(componentIds, function(index, val) {
                        if(val=='${componentId}'){
                            $inputbox.attr("checked",true);
                        }
                    });

                    var $inline_block_div = $('<div style="display:inline-block;width:32%;height:30px;margin:2px;"></div>').append($inputbox);
                    $inline_block_div.append('<label for="'+rows[i].ruleId+'">'+rows[i].ruleName+'</label>');
                    $content.append($inline_block_div);
                }
                $("#rule-content").html($content);
            }else{
                $("#rule-content").html($("<div>"));
            }
        },'json');
     }

     function changeValue(flag,ruleId){
         var url = "${pageContext.request.contextPath}/rs/dep/base/depBaseCompProp/";
        if(flag){
            //新增
            url = url + "insert";
        }else{
            //删除
            url = url + "delete";
        }
        $.post(url, {componentId: '${componentId}',ruleId:ruleId});
     }

  $(function(){
    $("#categoryDropdownList").kendoDropDownList({
            dataSource:JSON.parse('${categorys}'),
            dataTextField:"name",
            dataValueField:"treeId",
            optionLabel:"请选择系统分类",
            change:function(e){
                rootCategoryId = this.value();
                $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseCategory/getCategoryByTreeId', {rootId: rootCategoryId}, 
                function(data) {
                   reloadZTree(data);
                },"json");
                
            }
        });
  });
 </script>
 <style type="text/css" media="screen">
     body{background-color:#fff;}
 </style>
</head>
<body>
	<div id="main_content" class="k-content">
		<br>
		<div class="x_content_title">
			<img src="${pageContext.request.contextPath}/images/window.png"
				alt="规则属性列表">&nbsp; 规则组件配置
		</div>
        <input id="categoryDropdownList" style="width:150px" />
        <div style="display:table;width:580px;">
            <div style="display: table-row">
        		<div style="width:150px;height:327px;display: table-cell">
                    <ul id="myTree" class="ztree"></ul> 
                </div>
                <div id="rule-content" style="display: table-cell;">
                    
                </div>
            </div>
        </div>
	</div>
	<br />
</body>
</html>