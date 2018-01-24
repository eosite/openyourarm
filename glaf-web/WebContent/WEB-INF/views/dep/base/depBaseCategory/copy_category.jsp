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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则分类列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ztree/js/jquery.ztree.all.min.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">

    var treeObj,rMenu,rootCategoryId;
    function OnRightClick(event, treeId, treeNode){
        
    }

    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){

    }

    function zTreeOnClick(event, treeId, treeNode, clickFlag){
       
    }

    function reloadZTree(data){

        var setting = {
            view: {
                showIcon:true,
                showLine:true,
                showTitle:false,
                selectedMulti: true
            },
            data:{
                simpleData:{
                    enable:true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: -1
                }
            },
            check:{
                enable:true,
                chkStyle:'checkbox',
                chkboxType:{
                    Y:'s',
                    N:'s'
                }
            },
            callback: {
                onClick: zTreeOnClick,
                onAsyncSuccess: zTreeOnAsyncSuccess,
                onRightClick: OnRightClick
            }
        };

        $.fn.zTree.init($("#myTree"), setting, data);
        treeObj = $.fn.zTree.getZTreeObj("myTree");
    }

    $(document).ready(function(){
        $("#categoryDropdownList").kendoDropDownList({
            dataSource:JSON.parse('${categorys}'),
            dataTextField:"name",
            dataValueField:"treeId",
            optionLabel:"请选择系统分类",
            change:function(e){
                rootCategoryId = this.value();
                $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseCategory/getCategoryByTreeId', 
                    {
                        rootId: rootCategoryId,
                        filter: true,
                        filterType: 'code_by_rootid',
                        filterValue: '${param.filterId}'
                    }, 
                    function(data) {
                       reloadZTree(data);
                    },
                "json");
                
            }
        });

        $("#copyCategoryBtn").bind('click',function(){
            var categoryId = '${param.categoryId}';
            var nodes = treeObj.getCheckedNodes(true);
            if(nodes.length == 0){
                alert('请选择需要导入的系统分类');
                return;
            }
            var copyObjParams = [];
            $.each(nodes, function(index, node) {
                var param = {};
                param.id = node.id;
                param.pId = node.pid;
                param.isParent = node.isParent;
                copyObjParams.push(param);
            });
            console.log(JSON.stringify(copyObjParams));

            
            $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseCategory/copyCategory', 
                {
                    categoryId : categoryId,
                    copyObjParams : JSON.stringify(copyObjParams)
                }, 
                function(data, textStatus, xhr) {
                    if(data != null && data.message != null){
                       alert(data.message);
                   } else {
                       alert('引入分类完成！');
                   }
                   window.parent.parent.location.reload();
                },
            'json');
    
        });
    });
 </script>
</head>
<body>
    选择分类：<input id="categoryDropdownList" />&nbsp;
    <button type="button" class="k-button" id="copyCategoryBtn">确定</button>
    <div>
        <ul id="myTree" class="ztree"></ul> 
    </div>
</body>
</html>