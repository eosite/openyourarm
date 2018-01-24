<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>用户列表</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
<script id="template" type="text/x-kendo-template">
   <div class="toolbar">
      <button type="button" id="newButton"  class="k-button" style="width: 90px" 
	  onclick="javascript:addRow();">新增</button> 
	  <!-- <button type="button" id="impButton2"  class="k-button" style="width: 90px" 
	  onclick="javascript:doImport();">同步到云众联</button> --> 
   </div>
</script>
<script type="text/javascript">
	
    jQuery(function() {
      jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "actorId": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "telephone": {
                            "type": "string"
                        },
                        "deptId": {
                            "type": "string"
                        },
                        "lastLoginTime": {
                            "type": "date",
							"format": "{0: yyyy-MM-dd}"
                        }
                    }
                },
                "data": "rows"
            },
            "transport": {
                "parameterMap": function(options) {
					//alert(JSON.stringify(options));
                    return JSON.stringify(options);
                },
                "read": {
                    //"dataType": "json",
				    "contentType": "application/json",
                    "type": "POST",
                    "url": "<%=request.getContextPath()%>/rs/sys/user/data"
                }
            },
			"serverFiltering": true,
            "serverSorting": true,
            "pageSize": 10,
            "serverPaging": true,
            "serverGrouping": false,
        },
        "height": x_height,
        "reorderable": true,
        "filterable": true,
        "sortable": true,
		"pageable": {
                       "refresh": true,
                       "pageSizes": [5, 10, 15, 20, 25, 50, 100, 200, 500],
                       "buttonCount": 10
                     },
		"selectable": "single",
		"toolbar": kendo.template(jQuery("#template").html()),
        "columns": [{
            "field": "actorId",
            "title": "用户名",
            "width": "90px",
            "lockable": false,
            "locked": false
        },
        {
            "field": "name",
            "title": "姓名",
            "width": "90px",
			"lockable": false,
            "locked": false
        },
        {
            "field": "deptName",
            "title": "部门",
            "width": "150px",
			"filterable": {
                            "extra": false,
                            "operators": {
                                "string": {
                                    "startswith": "开头为",
									"eq": "等于",
                                    "contains": "包含",
                                    "doesnotcontain": "不包含",
									"endswith": "结尾为",
                                }
                            }
                        }
        },
        {
            "field": "telephone",
            "title": "电话",
            "width": "120px",
            "locked": false
        },
        {
            "field": "lastLoginTime_datetime",
            "title": "最后登录日期",
            "width": "150px",
            "lockable": false,
			"format": "{0: yyyy-MM-dd HH:mm:ss}",
			"align": "center",
			"filterable": {
			  "operator": ["gt", "gte", "lt", "lte"],
              "ui": "datetimepicker"  
           }
        },
		{
			"command": [{
                "text": "修改",
                "name": "edit",
                "click": function showDetails(e) {
                      //alert("修改记录");
					  var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                      //alert(JSON.stringify(dataItem));
					  //alert(dataItem.actorId);
					  var link = "<%=request.getContextPath()%>/mx/system/user/edit?actorId="+dataItem.actorId_enc;
					  var width=480;
					  var height=450;
					  var scroll="no";
					  //openWindow(link, width, height, scroll);
					  editRow(link);
				    }
                },
				{
                "text": "重置密码",
                "name": "restPwd",
                "click": function showDetails(e) {
                     //alert("修改记录");
					 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                     //alert(JSON.stringify(dataItem));
					 //alert(dataItem.actorId);
					 document.getElementById("actorId").value=dataItem.actorId_enc;
					 document.getElementById("newPwd").value="";
					 document.getElementById("password2").value="";
					 jQuery("#actorName").html(dataItem.name);
					 var link = "<%=request.getContextPath()%>/mx/system/user/prepareModifyPwd?actorId="+dataItem.actorId_enc;
					 var window = jQuery("#window");
					 window.kendoWindow({
                        width: "385px",
						height: "245px",
						resizable: true,
						draggable: true,
                        title: "重置密码",
						position: {top:23, left:320, pinned:"fixed"}
                    });
					//alert(dataItem.actorId);
					window.data("kendoWindow").open();
				   }
                },
				{
                "text": "重置Token",
                "name": "restToken",
                "click": function showTokenDetails(e) {
                     //alert("修改记录");
					 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                     //alert(JSON.stringify(dataItem));
					 //alert(dataItem.actorId);
					 document.getElementById("actorId2").value=dataItem.actorId_enc;
					 document.getElementById("name2").value=dataItem.name;
					 document.getElementById("mail2").value=dataItem.mail;
					 document.getElementById("token2").value=dataItem.token;
					 jQuery("#actorName2").html(dataItem.actorId);
					 jQuery("#name2").html(dataItem.name);
					 jQuery("#mail2").html(dataItem.mail);
					 jQuery("#token2").html(dataItem.token);
					 var window = jQuery("#window2");
					 window.kendoWindow({
                        width: "895px",
						height: "245px",
						resizable: true,
						draggable: true,
                        title: "重置Token",
						position: {top:23, left:120, pinned:"fixed"}
                    });
					//alert(dataItem.actorId);
					window.data("kendoWindow").open();
				   }
                },
				{
                "text": "角色设置",
                "name": "updateRole",
                "click": function showDetails(e) {
                     //alert("修改记录");
					 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                     //alert(JSON.stringify(dataItem));
					 //alert(dataItem.actorId);
					 var link="<%=request.getContextPath()%>/mx/system/user/showUserRoles?actorId="+dataItem.actorId_enc;
                     userRoles(link);
				   }
                },
				{
                "text": "菜单",
                "name": "viewMenus",
                "click": function showDetails(e) {
                     //alert("修改记录");
					 var dataItem = this.dataItem(jQuery(e.currentTarget).closest("tr"));
                     //alert(JSON.stringify(dataItem));
					 //alert(dataItem.actorId);
					 var link="<%=request.getContextPath()%>/mx/sys/user/showUserMenus?actorId="+dataItem.actorId_enc;
                     viewUserMenus(link);
                  }
            }]
          }
		],
        "scrollable": {},
        "resizable": true,
        "groupable": false
    });
  });


	function addRow(){
         editRow('<%=request.getContextPath()%>/mx/system/user/edit');
	}

    function editRow(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "编辑用户信息",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['520px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}


	function viewUserMenus(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "用户菜单",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['520px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

    function userRoles(link){
		jQuery.layer({
			type: 2,
			maxmin: true,
			shadeClose: true,
			title: "用户角色",
			closeBtn: [0, true],
			shade: [0.8, '#000'],
			border: [10, 0.3, '#000'],
			offset: ['20px',''],
			fadeIn: 100,
			area: ['920px', (jQuery(window).height() - 50) +'px'],
            iframe: {src: link}
		});
	}

	function resetPwd(){
        var form = document.getElementById("iForm");
	    if(form.newPwd.value == ''){
		    alert("新密码不能为空！");
		    form.newPwd.focus();
		    return;
	    }
	    if(form.newPwd.value!=form.password2.value){
	        alert("新密码与确认密码不匹配！");
		    form.password2.focus();
		    return;
	    }
	    //form.method="post";
	    //form.action = "<%=request.getContextPath()%>/mx/system/user/resetPwd";
	    //form.submit();
	    var params = jQuery("#iForm").formSerialize();
		jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/system/user/resetPwd',
				   dataType:  'json',
				   data: params,
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
                      var window = jQuery("#window");
                      window.data("kendoWindow").close(); 
				   }
			 });
   }

 function resetUserToken(){
	var actorId = document.getElementById("actorId2").value;
    if(confirm("确定要重置用户的密锁吗？")){
	    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/user/resetUserToken?actorId='+actorId,
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
			 });
	  }
  }

  function doImport(){
    jQuery.ajax({
				   type: "POST",
				   url: '<%=request.getContextPath()%>/mx/sys/user/gnUserImport',
				   dataType:  'json',
				   error: function(data){
					   alert('服务器处理错误！');
				   },
				   success: function(data){
					   if(data != null && data.message != null){
						   alert(data.message);
					   } else {
						   alert('操作成功完成！');
					   }
				   }
	});
}	
 </script>
</head>
<body>
<div id="main_content" class="k-content">
<br>
<div class="x_content_title"><img
	src="<%=request.getContextPath()%>/images/window.png" alt="用户列表">&nbsp;
用户列表</div>
<br>
<div id="grid"></div>
<div id="window" style="display:none">
 <form id="iForm" name="iForm">
  <table width="98%" align="center" border="0" cellspacing="0" cellpadding="5">
	  <tr>
        <td width="20%" class="input-box">用户名</td>
        <td width="80%">
		<label id="actorName"></label>
		<input type="hidden" id="actorId" name="actorId" value="">
		</td>
      </tr>
	  <tr>
        <td class="input-box2" valign="top">新密码*</td>
        <td><input id="newPwd" name="newPwd" type="password" size="30" class="k-textbox" value="" datatype="string" nullable="no" minsize="6" maxsize="20" chname="密码"></td>
      </tr>
      <tr>
        <td class="input-box2" valign="top">确认密码*</td>
        <td><input id="password2" name="password2" type="password" size="30" class="k-textbox" value=""  datatype="string" nullable="no" minsize="6" maxsize="20" chname="确认密码"></td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
		<button type="button" id="iconButton"  class="k-primary" style="width: 90px" onclick="javascript:resetPwd();">确定</button>
	    </td>
      </tr>
  </table>   
 </form>
</div> 

<div id="window2" style="display:none">
 <form id="iForm2" name="iForm2">
  <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
	  <tr>
        <td width="20%" class="input-box">用户名</td>
        <td width="80%">
		<label id="actorName2"></label>
		<input type="hidden" id="actorId2" name="actorId2" value="">
		</td>
      </tr>
	  <tr>
        <td width="20%" class="input-box">姓名</td>
        <td width="80%">
		<label id="name2"></label>
		<input type="hidden" id="name2" name="name2" value="">
		</td>
      </tr>
	  <tr>
        <td width="20%" class="input-box">Email</td>
        <td width="80%">
		<label id="mail2"></label>
		<input type="hidden" id="mail2" name="mail2" value="">
		</td>
      </tr>
	  <tr>
        <td width="20%" class="input-box">当前令牌</td>
        <td width="80%">
		<label id="token2"></label>
		<input type="hidden" id="token2" name="token2" value="">
		</td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">&nbsp;
		<button type="button" id="iconButton"  class="k-primary" style="width: 90px" onclick="javascript:resetUserToken();">确定</button>
	    </td>
      </tr>
  </table>   
 </form>
</div> 
</div> 
<br/>
<br/>
<br/>
<br/>
</body>
</html>