<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择用户</title>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/scripts/extjs3/resources/css/ext-all.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/extjs3/ext-all.js"></script>
<script type="text/javascript">  
 	var tree;
    Ext.BLANK_IMAGE_URL="<%=request.getContextPath()%>/images/s.gif";
	Ext.onReady(function(){  
	         tree = new Ext.tree.TreePanel({
	          useArrows:false,  
		      autoScroll:true,
	          animate:false,  
			  border: false,
	          enableDD:false,
	          doubleClickExpand:false,
	          containerScroll: true,
	          dataUrl:'<%=request.getContextPath()%>/mx/identity/userSelection/json',
			  root: {
			     text: '<c:out value="${text}" />',
	             draggable: false,
			     checked: false,
	             id:'<c:out value="${node}" />'
			  }
	     	});

		tree.render('tree-div');
        tree.getRootNode().expand();
	
         /**
	      * 重写节点的双击事件
	      */
	     Ext.override(Ext.tree.TreeNodeUI, {
			onDblClick : function(e) {
				e.preventDefault();
				if (this.disabled) {
					return;
				}
				if (this.checkbox) {
					this.toggleCheck();
					tree.fireEvent('checkchange',this.node,this.checkbox.checked);
				}
				if (!this.animating && this.node.hasChildNodes()) {
					var isExpand = this.node.ownerTree.doubleClickExpand;
					if (isExpand) {
						this.node.toggle();
					};
				}
				this.fireEvent("dblclick", this.node, e);
			}
		}); 
	     /**
	      * checkbox单击事件
	      */
	     tree.on('checkchange', function(node, checked) {
   		     node.attributes.checked = checked;
	         node.eachChild(function(child) {
	         	 /** 操作其子节点 */
	             child.ui.toggleCheck(checked);
	             child.attributes.checked = checked;
	             /** 递归 */
		         child.fireEvent('checkchange', child, checked);
	         });
	         if(checked){
		         node.fireEvent('addChildNode',node);
		     } else {
		     	 node.fireEvent('removeChildNode',node);
		     }
		     tree.fireEvent('checkParent',node,checked);
	     }, tree);
	     /**
	      * 控制父节点的checkbox
	      */
	     tree.on('checkParent', function(node,checked){
	     	var parentNode = node.parentNode;
	     	if(parentNode){
	     		var childNodes = parentNode.childNodes;
	     		var flag = true;
		     	for(var i=0;i<childNodes.length;i++){
					if(childNodes[i].attributes.checked){
						flag = true;
						break;
					} else {
						flag = false;
						//break;
					}
		     	}
	     		//parentNode.ui.toggleCheck(flag);
	     		parentNode.attributes.checked = flag;
	     		//递归
	     		tree.fireEvent('checkParent',parentNode,flag);
			 }
	     });
	   	 /**
	   	  * 添加一个节点
	   	  */
	   	 tree.on('addChildNode',function(node){
	   	 	var selObj = document.getElementById("sel_id");
	   	 	var option = document.createElement("option");
			option.value = node.id;
		 	option.innerHTML = node.text;
			
		 	/** 不能重复加入 */
		 	for(var i=0;i<selObj.options.length;i++){
		 		var _option = selObj.options[i];
		 		if(_option.innerHTML==node.text){
		 			return;
		 		}
		 	}
		 	if(node.childNodes.length==0 && node.isLeaf()){
		 		/** ie和ff都支持的写法 */
			 	selObj.appendChild(option);
			}
		 });
		 /**
		  * 移除某个节点
		  */
		 tree.on('removeChildNode',function(node){
	   	 	var selObj = document.getElementById("sel_id");
	   	 	var option = document.createElement("option");
		 	option.innerHTML = node.text;
		 	var index = -1;
		 	/** 获得该节点在select中的索引index */
		 	for(var i=0;i<selObj.options.length;i++){
		 		var _option = selObj.options[i];
		 		if(_option.innerHTML==node.text){
		 			index = _option.index;
		 		}
		 	}
		 	if(index!=-1){
		 		/** 删除option */
				selObj.remove(index);
			}
		 });
	     
	     
	     /** 注册select双击事件 */
	 	 var selObj = document.getElementById("sel_id");
	 	 selObj.ondblclick = function(){
	 	 	var _options = selObj.options;
	 	 	/** 被选中的option */
	 	 	var _selOption ;
	 	 	for(var i=0;i<_options.length;i++){
	 	 		if(_options[i].selected){
	 	 			_selOption = _options[i];
	 	 		}
	 	 	}
	 	 	if(_selOption){
	 	 		tree.fireEvent('removeOption',tree.getRootNode(),_selOption);
	 	 	}
	 	 };
	 	 /** 
	 	  * 去掉select中的option
	 	  */
	 	 tree.on('removeOption',function(node,option){
	 	 	if(node.childNodes.length==0 && node.text==option.innerHTML){
	 	 		document.getElementById("sel_id").remove(option.index);
	 	 		node.attributes.checked = false;
             	node.ui.toggleCheck(false);
             	tree.fireEvent('checkParent',node,false);
	 	 	}else{
	 	 		for(var i=0;i<node.childNodes.length;i++){
			        node.childNodes[i].fireEvent('removeOption', node.childNodes[i], option);
		        }
	 	 	}
	 	 });
   });

	 function expandAll(){
		 tree.expandAll();
	 }

	 function collapseAll(){
		 tree.collapseAll();
	 }

	 function clearAll(){
          var selObj = document.getElementById("sel_id");
		  var parent_window = getOpener();
		  for (k = 0; k < selObj.length; k++) {
			selObj.options[k] = null;
		  }
 		  selObj.options.length = 0;
	 }

 </script>
<script language="javascript">

     function addToList(list, value, text) {
        if (list.length > 0) {
            for (k = 0; k < list.length; k++) {
                if (list.options[k].value == value) {
                    return;
                }
            }
        }

        var len = list.options.length;
        list.length = len + 1;
        list.options[len].value = value;
        list.options[len].text = text;
     }

    function chooseConfirm(){
		  var parent_window = getOpener();
		  var x_users = parent_window.document.getElementById("<c:out value="${elementId}"/>");
 
  		  var selObj = document.getElementById("sel_id");
		  /*
		  var exclusive = document.getElementById("exclusive").value;
		  if(exclusive == 'Y'){
             if(selObj.options.length != 1){
				 alert("必须并且只能选择一个用户！");
				 return;
			 }
		  }
		  */

          selObj.options.length = selObj.options.length;
	 	  for(var i=0;i<selObj.options.length;i++){
                var selOption = selObj.options[i];
				//alert(selOption.value);
				addToList(x_users, selOption.value, selOption.text);
				//x_users.options[i].value = selOption.value;
				//x_users.options[i].text = selOption.text;
			}
     
	       window.close();
		 
      }
 </script>
</head>
<body>
<div class="content-block" style="width: 100%; height: 100%;"><br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="选择用户">&nbsp;
选择用户</div>
<br>
<form name="iForm" method="post">
<div>&nbsp;&nbsp; <label onclick="javascript:expandAll();"
	style="cursor: pointer;">全部展开</label> | <label
	onclick="javascript:collapseAll();" style="cursor: pointer;">全部收缩</label>|
<label onclick="javascript:clearAll();" style="cursor: pointer;">全部清除</label>
<input type="button" name="submit" value="确 定" class="btnGray"
	onclick="javascript:chooseConfirm();"> <input type="button"
	name="submit2" value="关 闭 " class="btnGray"
	onclick="javacsript:window.close();"> <input type="hidden"
	id="exclusive" name="exclusive" value="<c:out value="${exclusive}"/>">
</div>
<table>
	<tr>
		<td noWrap>
		<div id="tree-div"
			style="overflow: auto; height: 455px; width: 350px; border: 1px solid #c3daf9; float: left; background-color: #ffffff;">
		</div>
		</td>
		</td>
		<td noWrap>
		<div id="append_div"><select multiple
			style="height: 460px; width: 200; border: 1px solid #c3daf9; float: left;"
			id="sel_id" name="sel_id">
		</select> 
		<c:if test="${!empty users}">
		<script language="javascript">		
				var selObj = document.getElementById("sel_id");
				<c:forEach items="${users}" var="user">
				var option = document.createElement("option");
				option.value =  "<c:out value="${user.actorId}"/>";
				option.innerHTML = "<c:out value="${user.name}"/>";
				selObj.appendChild(option);
		    </c:forEach>
		</script>
		</c:if>
		</div>
		</td>
	</tr>
</table>
<div>&nbsp;&nbsp; <label onclick="javascript:expandAll();"
	style="cursor: pointer;">全部展开</label> | <label
	onclick="javascript:collapseAll();" style="cursor: pointer;">全部收缩</label>|
<label onclick="javascript:clearAll();" style="cursor: pointer;">全部清除</label>
<input type="button" name="submit" value="确 定" class="btnGray"
	onclick="javascript:chooseConfirm();"> <input type="button"
	name="submit2" value="关 闭 " class="btnGray"
	onclick="javacsript:window.close();"></div>
</form>
</div>
</body>
</html>