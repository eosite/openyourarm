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
#rMenu {
    position:absolute; 
    visibility:hidden; 
    background-color: #c9c9c9;
    text-align: left;
    padding: 0px;
    border-radius:5px;
}
#rMenu ul{
    padding:0px 1px;
    height: 100%;
    margin:1px;
}
#rMenu ul li{
    width:100px;
    line-height:25px;
    margin: 1px 0;
    padding-left:3px;
    cursor: pointer;
    list-style: none outside none;
    background-color: #FFF;
    vertical-align:middle;
}
#rMenu ul li:first-child{
    border-top-left-radius:3px;
    border-top-right-radius:3px;
}
#rMenu ul li:last-child{
    border-bottom-left-radius:3px;
    border-bottom-right-radius:3px;
}
#rMenu ul li:hover{
    background-color: #c9c9c9;
}
</style>
<script type="text/javascript">

    var treeObj,rMenu,rootCategoryId;
    function OnRightClick(event, treeId, treeNode){
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            //如果右键点击的不是节点，则取消节点选中，并不处理其他事件
            treeObj.cancelSelectedNode();
        } else if (treeNode && !treeNode.noR) {
            treeObj.selectNode(treeNode);
            showRMenu(treeNode, event.clientX, event.clientY);
        }
    }

    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){

    }

    function zTreeOnClick(event, treeId, treeNode, clickFlag){
        var baseurl = "${pageContext.request.contextPath}/mx/dep/base/depBaseProp/getPropByCategoryId";
        if(${empty param.rootCategoryId}){
            baseurl = baseurl + "/list";
        }else{
            baseurl = baseurl + "/list_select";
        }
        var url = baseurl+"?categoryId="+treeNode.id+"&rootCategoryId="+rootCategoryId;
        parent.mainFrame.document.location.href = encodeURI(url);
    }

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
                onClick: zTreeOnClick,
                onAsyncSuccess: zTreeOnAsyncSuccess,
                onRightClick: OnRightClick
            }
        };

        $.fn.zTree.init($("#myTree"), setting, data);
        treeObj = $.fn.zTree.getZTreeObj("myTree");
        rMenu = $("#rMenu");
    }

    $(document).ready(function(){
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

        $(document).bind("click",function(){
             hideRMenu();
        });

        $("#addRootCategory").bind("click",function(){
            editRow('${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/edit?pid=-1');
        });

        $("#copyCategory").bind("click",function(){
            var rootNode = treeObj.getNodeByParam("pid", -1, null);
            var id = rootNode.id;

            var nodes = treeObj.getSelectedNodes();
            if(nodes.length>0){
               id = nodes[0].id;
            }

           var link = '${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/copyCategoryList?filterId='+rootNode.id+'&categoryId='+id;
           parent.mainFrame.$.layer({
                type : 2,
                maxmin : true,
                shadeClose : true,
                title : "编辑使用规则分类树信息",
                closeBtn : [ 0, true ],
                shade : [ 0.8, '#000' ],
                border : [ 10, 0.3, '#000' ],
                offset : [ '', '' ],
                fadeIn : 100,
                area : [ '350px', '650px' ],
                iframe : {
                    src : link
                }
            });

        });

        if(${not empty param.rootCategoryId}){
            $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseCategory/getCategoryByTreeId', {rootId: "${param.rootCategoryId}"}, function(data) {
                   reloadZTree(data);
                },"json");
        }
    });

    function showRMenu(treeNode, x, y) {
        $("#rMenu ul").show();
        if(treeNode.level==0){
            $("#m_add0").hide();
        } else {
            $("#m_add0").show();
        }

        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

        $("body").bind("mousedown", onBodyMouseDown);
    }
    function hideRMenu() {
        if (rMenu) rMenu.css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
    }
    function onBodyMouseDown(event){
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
            rMenu.css({"visibility" : "hidden"});
        }
    }

    //增加
    function add(type){
        hideRMenu();

        var nodes = treeObj.getSelectedNodes();
        if(nodes.length===0 || nodes.length>1){
            alert("请选择一个节点");
            return;
        }
        var node = nodes[0];
        
        var parentId = node.pid;
        if(type===1){
            parentId = node.id;
        }

        editRow('${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/edit?pid='+parentId);
    }

     //修改
    function modify(){
        hideRMenu();
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length==0 || nodes.length>1){
            alert("请选择一个节点");
            return;
        }
        var node = nodes[0];

        var link = "${pageContext.request.contextPath}/mx/dep/base/depBaseCategory/edit?id=" + node.id+"&pid="+node.pid;
        editRow(link);
    }

     //删除
    function del(){
        hideRMenu();
        
        if(!confirm("是否删除所选择的数据以及其下层数据？")){
            return ;
        }

        var nodes = treeObj.getSelectedNodes();
        if(nodes.length==0 || nodes.length>1){
            alert("请选择一个节点");
            return;
        }
        var node = nodes[0];

        $.post('${pageContext.request.contextPath}/rs/dep/base/depBaseCategory/updateDelFlagByTreeIdLike', 
            {treeId: node.treeId,delFlag:"1"}, 
            function(data) {
                if(data.statusCode==200){
                    alert(data.message);
                    document.location = document.location;
                }
            },
            'json'
        );
    }

    function editRow(link) {
        parent.mainFrame.$.layer({
            type : 2,
            maxmin : true,
            shadeClose : true,
            title : "编辑使用规则分类树信息",
            closeBtn : [ 0, true ],
            shade : [ 0.8, '#000' ],
            border : [ 10, 0.3, '#000' ],
            offset : [ '', '' ],
            fadeIn : 100,
            area : [ '620px', '450px' ],
            iframe : {
                src : link
            }
        });
    }

 </script>
</head>
<body>
<c:if test="${empty param.rootCategoryId}">
    选择分类：<input id="categoryDropdownList" />&nbsp;
    <button type="button" class="k-button" id="addRootCategory">新增分类</button>&nbsp;
    <button type="button" class="k-button" id="copyCategory">引入分类</button>
</c:if>
    <div>
        <ul id="myTree" class="ztree"></ul> 
    </div>
    <div id="rMenu">
        <ul>
            <li id="m_add0" onclick="add(0);">同级增加</li>
            <li id="m_add1" onclick="add(1);">下级增加</li>
            <li id="m_modify" onclick="modify();">修改</li>
            <li id="m_del" onclick="del();">删除</li>
        </ul>
    </div>
</body>
</html>