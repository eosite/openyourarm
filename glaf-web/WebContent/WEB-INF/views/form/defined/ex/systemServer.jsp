<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.glaf.base.utils.*"%>
<%@ page import="com.glaf.base.business.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.ui.model.*"%>
<%@ page import="com.glaf.core.security.LoginContext"%>
<%@ include file="/WEB-INF/views/inc/init_import.jsp"%>
<%
    String context = request.getContextPath();
    com.glaf.base.utils.ContextUtil.getInstance().setContextPath(context);
    pageContext.setAttribute("contextPath", context);
    if (request.getAttribute("userTheme") != null) {
        UserTheme userTheme = (UserTheme) request.getAttribute("userTheme");
        request.setAttribute("theme", userTheme.getThemeStyle());
        request.setAttribute("homeTheme", userTheme.getHomeThemeStyle());
    } else {
        String theme = com.glaf.core.util.RequestUtils.getTheme(request);
        request.setAttribute("theme", theme);
        String homeTheme = com.glaf.core.util.RequestUtils.getHomeTheme(request);
        request.setAttribute("homeTheme", homeTheme);
    }
    LoginContext loginContext = RequestUtils.getLoginContext(request);
    request.setAttribute("loginContext", loginContext);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=SystemConfig.getString("res_system_name")%></title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css" />
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/apps/css/inbox.min.css" rel="stylesheet" type="text/css"/>
        
        <!-- dialog --> 
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">
        <!--ztree-->
        <link rel="stylesheet" href="${contextPath}/scripts/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
        <!--图片弹窗-->
        <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />

        <!--grid-->
        <link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/treelist/css/treelist.css">

        <!-- iCheck -->
        <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
        <!-- datepicker3 -->
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/datepicker/css/bootstrap-datepicker3.min.css"/>
        <link rel="stylesheet" type="text/css" href="/glaf/scripts/plugins/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css"/>
        <!-- select2 -->
        <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />

        
        
        <style type="text/css">
            .portlet.box>.portlet-title{
                padding: 0 10px;
            }
            .portlet.box>.portlet-title>.nav-tabs>li.active>a,.portlet.box>.portlet-title>.nav-tabs>li:hover>a {
                color: #fff;
                font-weight: bold;
            }
            .portlet.box{
                min-height: 0px;    
            }
            .portlet.box>.portlet-title{
                min-height: 0px;    
            }
            .portlet.box>.portlet-title>.caption,.portlet.box>.portlet-title>.nav-tabs>li>a{
                padding: 6px 6px;
            }
            .portlet.box>.portlet-title>.nav-tabs>li>a {
                color: #fff;
            }
            .tabbable-line>.nav-tabs>li.active{
                border-bottom-color: #ab1e27;
            }
            line>.nav-tabs>li.open, .tabbable-line>.nav-tabs>li:hover {
                border-bottom-color: #f36a5a;
            }
            .form-group label{
                text-align: right;
            }

            .border_div{
                border:1px solid #ccc;
            }
        </style>
</head>
<body >
	<div class="tabbable-line">
		<ul class="nav nav-tabs">
            <li class="active">
                <a href="#tab_1" data-toggle="tab" aria-expanded="false"> 权限管理服务 </a>
            </li>
            <li class="">
                <a href="#tab_2" data-toggle="tab" aria-expanded="false"> 其他业务服务 </a>
            </li>
            <li class="">
                <a href="#tab_3" data-toggle="tab" aria-expanded="false"> 系统内置业务服务 </a>
            </li>
            
            <li class="pull-right" style="padding-top:6px；padding-right:6px">
            	<button type="button" id="sureButton" class="btn blue pull-right btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>确认</button>
            </li>
        </ul>
        <div class="tab-content" style="padding:10px 0px;">
			<div class="tab-pane active" id="tab_1">
				<div class="col-xs-8" id="content">
					<div id="systemServerGrid" style="height:600px"></div>
				</div>
				<div class="col-xs-4">
					<div id="systemOperatorGrid" style="height:600px"></div>
				</div>
			</div>
			<div class="tab-pane" id="tab_2">
				<div class="col-xs-12" id="content">
					<div id="bussinessServerGrid" style="height:600px"></div>
				</div>
			</div>
            <div class="tab-pane" id="tab_3">
                <div class="col-xs-8" id="content">
                    <div id="systemBussinessServerGrid" style="height:600px"></div>
                </div>
                <div class="col-xs-4">
                    <div id="systemBussinessOperatorGrid" style="height:600px"></div>
                </div>
            </div>
			
        </div>
       
	</div>
	<template id="添加部门"> 

	</template>    
</body>
</html>
<script type ="text/javascript">
    var contextPath = '${contextPath}';
</script>

<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-summernote/lang/summernote-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.exedit.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/html2canvas/html2canvas.svg.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
<script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.min.js"></script>
<!-- ztree-->
<script type="text/javascript" src="${contextPath}/scripts/kendo/bootstrap/ztree.extend.js"></script>

<!-- dialog --> 
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>
<script src="/glaf/scripts/plugins/bootstrap/dialog/js/jquery.dialog.extends.js"></script>
<!--grid-->
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src="/glaf/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js" type="text/javascript"></script>

<!-- iCheck -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js" type="text/javascript"></script>
<!-- datepicker -->
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datepicker/ext/jquery.datepicker.extends.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/datetimepicker/ext/jquery.datetimepicker.extends.js"></script>

<!-- slimscroll -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>

<!-- select2 -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

<script type ="text/javascript">
    //事件定义器中的回调方法
    var retFn = "${param.retFn}";
    function callback(config){
        var retObj = {
            name:config.dataItem.operation,
            value:config.dataItem.operationCode,
            otherParam : config.dataItem
        };
        if(config.itemParam != undefined){
        	retObj = {
                    name:config.dataItem.operation,
                    value:config.dataItem.operationCode,
                    otherParam : config.dataItem,
                    itemParam : config.itemParam,
                    tableName : config.itemParam.tableName,
                    databaseId : config.itemParam.databaseId
            };
         }
        parent[retFn](retObj);
        closeWin();
    }
    function callbackCopy(config){
        var retObj = {
            name:config.dataItem.operation,
            value:config.dataItem.operationCode,
            otherParam : config.dataItem
        };
        if(config.itemParam != undefined){
        	retObj = {
                    name:config.dataItem.operation,
                    value:config.dataItem.operationCode+"-"+config.itemParam.tableName+"-"+config.itemParam.databaseId+"-"+config.itemParam.item.index_id+"-"+config.itemParam.item.parent_id+"-"+config.itemParam.item.treeid,
                    otherParam : config.dataItem,
                    itemParam : config.itemParam,
                    
            };
         }
        parent[retFn](retObj);
        closeWin();
    }
    function closeWin(){
        parent.layer.close(parent.layer.getFrameIndex());
    }
    function OutExtItem(param){
    	window.itemParam = param;
    }
    var departmentParam = [
        {
            title:"上级部门",
            code:'parent'
        },
        {
            title:"部门名称",
            code:'name'
        },
        {
            title:"描述",
            code:'desc'
        },
        {
            title:"代码",
            code:'code'
        },
        {
            title:"编码",
            code:'no'
        },
        {
            title:"部门区分",
            code:'code2'
        },
        {
            title:"部门层级",
            code:'level'
        },
        {
            title:"是否有效",
            code:'status'
        },
    ];

    var userParam = [
        {
            title:"部门编号",
            code:'nodeId',
            desc:'部门id优先'
        },
        {
            title:"部门编码",
            code:'parent',
            desc:'优先与部门编号'
        },
        {
            title:"用户账号",
            code:'id'
        },{
            title:"姓名",
            code:'name'
        },{
            title:"编码",
            code:'code'
        },{
            title:"性别",
            code:'gender',
            desc:'0为男性,1为女性'
        },{
            title:"手机",
            code:'mobile'
        },{
            title:"邮件",
            code:'email'
        },{
            title:"办公电话",
            code:'telephone'
        },{
            title:"职位",
            code:'headship'
        },{
            title:"账户类型",
            code:'accountType'
        },{
            title:"直属上级",
            code:'superiorIds'
        },{
            title:"允许通过MQ登录",
            code:'mqLoginFlag'
        },{
            title:"允许第三方通过密锁登录",
            code:'secretLoginFlag'
        },{
            title:"截止日期",
            code:'deadlineTime'
        }
        ,{
            title:"是否有效",
            code:'status'
        }
    ];
    var blukUserParam = [
        {
            title:"部门编号",
            code:'nodeId',
            desc:'不支持分割,部门id优先'
        },
        {
            title:"部门id",
            code:'parent',
            desc:'不支持分割,优先与部门编号'
        },
        {
            title:"用户账号",
            code:'ids',
            desc:'使用,分割,如1,2,3,4'
        },{
            title:"密码",
            code:'password',
            desc:"使用,分割,如1,2,3,4,少于取第一个"
        },{
            title:"姓名",
            code:'name',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"编码",
            code:'code',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"性别",
            code:'gender',
            desc:'0为男性,1为女性'
        },{
            title:"手机",
            code:'mobile',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"邮件",
            code:'email',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"办公电话",
            code:'telephone',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"职位",
            code:'headship',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"账户类型",
            code:'accountType',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"直属上级",
            code:'superiorIds',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"允许通过MQ登录",
            code:'mqLoginFlag',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"允许第三方通过密锁登录",
            code:'secretLoginFlag',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        },{
            title:"是否有效",
            code:'status',
            desc:'使用,分割,如1,2,3,4,少于取第一个'
        }
    ];

    var roleParam = [
        {
            title:"角色名称",
            code:'name'
        },
        {
            title:"角色代码",
            code:'code'
        },
        {
            title:"顺序",
            code:'sort'
        },
        {
            title:"描述",
            code:'content'
        },
        {
            title:"首页连接",
            code:'indexUrl'
        },
        {
            title:"开放分级管理(Y/N)",
            code:'isUseBranch',
            desc:"Y为开放,N为不开放"
        },
        {
            title:"开放机构管理(Y/N)",
            code:'isUseDept',
            desc:"Y为开放,N为不开放"
        }
    ]

    var systemData = [{
        operation : '添加部门',
        operationCode : 'addDepartment',
        param:departmentParam
    },{
        operation : '修改部门',
        operationCode : 'modifyDepartment',
        param:$.merge([
            {
                title:"部门id",
                code:'id'
            },
        ],departmentParam)
    },{
        operation : '删除部门',
        operationCode : 'delDepartment',
        param:[
            {
                title:"部门id",
                code:'ids',
                desc:'批量时,分割,如1,2,3'
            },
            {
                title:"部门编号",
                code:'nodeId',
                desc:'不可批量操作'
            }
        ],
    },{
        operation : '添加角色',
        operationCode : 'addRole',
        param: roleParam
    },{
        operation : '修改角色',
        operationCode : 'modifyRole',
        param: $.merge([
            {
                title:"角色id",
                code:'id'
            }
        ],roleParam)
    },{
        operation : '删除角色',
        operationCode : 'deleteRole',
        param: [{
            title:"角色id",
            code:'ids',
            desc:'多个角色使用,分割,如1,2,3,优先'
        },{
            title:"角色代码",
            code:'roleCodes',
            desc:'多个角色使用,分割,如1,2,3'
        }]
    },{
        operation : '分配多个菜单给单个角色(删除)',
        operationCode : 'saveRoleMenus',
        param: [{
            title:"角色Id",
            code:'roleId',
            desc:'不可多个'
        },{
            title:"菜单Ids",
            code:'appIds',
            desc:'使用,分割,如1,2,3,优先'
        },{
            title:"菜单nodeIds",
            code:'nodeIds',
            desc:'多个角色使用,分割,如1,2,3'
        }]
    },{
        operation : '分配多个菜单给单个角色(不删除)',
        operationCode : 'saveRoleMenusNoDelete',
        param: [{
            title:"角色Id",
            code:'roleId',
            desc:'不可多个'
        },{
            title:"菜单Ids",
            code:'appIds',
            desc:'使用,分割,如1,2,3,优先'
        },{
            title:"菜单nodeIds",
            code:'nodeIds',
            desc:'多个角色使用,分割,如1,2,3'
        }]
    },{
        operation : '删除角色菜单',
        operationCode : 'deleteRoleMenu',
        param: [{
            title:"角色id",
            code:'roleId',
            desc:'不可多个'
        },{
            title:"菜单Id",
            code:'appId',
            desc:'不可多个'
        }]
    },{
        operation : '添加用户',
        operationCode : 'addUser',
        param: $.merge([
            {
                title:"密码",
                code:'password'
            },{
                title:"确认密码",
                code:'password2'
            },
        ],userParam)
    },{
        operation : '注册用户',
        operationCode : 'registerUser',
        param: $.merge([
            {
                title:"密码",
                code:'password'
            },{
                title:"确认密码",
                code:'password2'
            },
        ],userParam)
    },{
        operation : '批量添加用户',
        operationCode : 'blukAddUser',
        param: blukUserParam
    },{
        operation : '修改用户',
        operationCode : 'modifyUser',
        param: userParam
    },{
        operation : '用户密码修改',
        operationCode : 'resetUserPwd',
        param: [
            {
                title:"用户账号",
                code:'id'
            },
            {
                title:"密码",
                code:'newPwd'
            },{
                title:"确认密码",
                code:'newPwd2'
            },
        ]
    },{
        operation : '自身密码修改',
        operationCode : 'resetPwdByMySelf',
        param: [
            {
                title:"用户账号",
                code:'id'
            },
            {
                title:"原始密码",
                code:'oldPwd'
            },
            {
                title:"密码",
                code:'newPwd'
            },{
                title:"确认密码",
                code:'newPwd2'
            },
        ]
    },{
        operation : '删除用户',
        operationCode : 'deleteUser',
        param: [
            {
                title:"用户账号（批量)",
                code:'ids',
                desc:"批量操作分割:,"
            },
            {
                title:"用户账号（单个)",
                code:'id',
                desc:"单个删除,只能一小时内删除"
            }
        ]
    },{
        operation : '分配多个角色给单个用户(先删除后分配)',
        operationCode : 'saveUserRoles',
        param: [
            {
                title:"用户账号",
                code:'actorId'
            },
            {
                title:"角色id",
                code:'objectIds',
                desc:"多个角色使用,分隔"
            }
        ]
    },{
        operation : '批量分配多个角色给多个用户(先删除后分配)',
        operationCode : 'blukSaveUserRoles',
        param: [
            {
                title:"用户账号",
                code:'actorIds',
                desc:'多个用户使用,分割'
            },
            {
                title:"角色id",
                code:'objectIds',
                desc:"多个用户分配同一个角色或多个角色,优先"
            },
            {
                title:"角色代码",
                code:'roleCodes',
                desc:"多个用户分配同一个角色或多个角色,id优先"
            }
        ]
    },{
        operation : '批量分配多个角色给多个用户(不删除)',
        operationCode : 'blukInsertUserRoles',
        param: [
            {
                title:"用户账号",
                code:'actorIds',
                desc:'多个用户使用,分割'
            },
            {
                title:"角色id",
                code:'objectIds',
                desc:"多个用户分配同一个角色或多个角色,优先"
            },
            {
                title:"角色代码",
                code:'roleCodes',
                desc:"多个用户分配同一个角色或多个角色,id优先"
            }
        ]
    },{
        operation : '删除用户已分配的角色',
        operationCode : 'blukRemoveUserRoles',
        param: [
            {
                title:"用户账号",
                code:'actorIds',
                desc:'多个用户使用,分割'
            },
            {
                title:"角色id",
                code:'objectIds',
                desc:"多个用户分配同一个角色或多个角色,优先"
            },
            {
                title:"角色代码",
                code:'roleCodes',
                desc:"多个用户分配同一个角色或多个角色,id优先"
            }
        ]
    },{
        operation : '部门机构角色',
        operationCode : 'branchDepartmentSaveRole',
        param: [
            {
                title:"部门编号",
                code:'deptId'
            },
            {
                title:"角色类型",
                code:'menuFlag',
                desc:'角色下发(0),角色菜单(1)'
            },{
                title:"是否统一设置下级部门",
                code:'isPropagationAllowed',
                desc:"Y为设置,N为不设置"
            },{
                title:"角色ID",
                code:'items',
                desc:"多个使用,分割"
            }
        ]
    },{
        operation : '添加数据库权限',
        operationCode : 'addDatabaseAccessor',
        param: [
            {
                title:"用户账号",
                code:'actorId'
            },
            {
                title:"标段ID",
                code:'databaseId',
                desc:'数据库ID'
            }
        ]
    },{
        operation : '移除数据库权限',
        operationCode : 'deleteDatabaseAccessor',
        param: [
            {
                title:"用户账号",
                code:'actorId'
            },
            {
                title:"标段ID",
                code:'databaseId',
                desc:'数据库ID'
            }
        ]
    }]

    var systemBussinessData = [{
        operation : '设置可接受短信人(设置了才可发送短信给他)',
        operationCode : 'setSmsPersion',
        param: [
            {
                title:"用户账号",
                code:'accounts',
                desc:''
            },
            {
                title:"手机号码",
                code:'mobiles',
                desc:''
            },
            {
                title:"限制条数",
                code:'limits',
                desc:'每天最多可发送n条给对方'
            },
            {
                title:"操作",
                code:'operation',
                desc:'0为新增,1为删除(默认新增)'
            }
        ]
    }]
    // ,{
    //     operation : '新增角色',
    //     operationCode : 'addRole',
    //     param: roleParam
    // }
    var setting = {
        datas:systemData,
        // tableCls: 'table myTableStyle',
        resizable: false,
        scrollable: false,
        clickUpdate: false,
        occupy: false,
        sortable: {},
        selectable: '',
        showInLine: true,
        sortDesc: false,
        combineAble: false,
        // toolbar: "[{'name':'create','text':'新增'}]",
        columns:[
            {title:'操作类型', field:'operation', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                'style': 'text-align:center;width: 45%'
            },},
            {title:'系统代码', field:'operationCode', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                'style': 'text-align:center;width: 35%'
            },},
            {title:'', field:'operationCode',width:20,'style': 'width: 20%',
            	template : function(data){
            		return '<button type="button" class="btn green btn-sm selectBtn" style=""><i class="glyphicon glyphicon-ok"></i>选择</button>';
            	}
        	}
        	
        ],
        events:{}
    };
    var systemData2 = [{
        operation : '复制服务',
        operationCode : 'copyServer',
        
    }];
    var setting2 = {
            datas:systemData2,
            // tableCls: 'table myTableStyle',
            resizable: false,
            scrollable: false,
            clickUpdate: false,
            occupy: false,
            sortable: {},
            selectable: '',
            showInLine: true,
            sortDesc: false,
            combineAble: false,
            // toolbar: "[{'name':'create','text':'新增'}]",
            columns:[
                {title:'操作类型', field:'operation', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                    'style': 'text-align:center;width: 50%'
                },},
                {title:'系统代码', field:'operationCode', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                    'style': 'text-align:center;width: 40%'
                },},
                {title:'', field:'operationCode',width:20,'style': 'width: 20%',
                	template : function(data){
                		return '<button type="button" class="btn green btn-sm selectBtn" style=""><i class="glyphicon glyphicon-ok"></i>确定</button><button type="button" class="btn green btn-sm copyBtn" style=""><i class="glyphicon glyphicon-ok"></i>选择</button>';
                	}
            	}
            	
            ],
            events:{}
     }
    

    $(function(){
        //环境信息grid
        var $systemServerGrid = $("#systemServerGrid");
        //对应的字段信息
        var $systemOperatorGrid = $("#systemOperatorGrid");
        setting.events.onClickRow = function(index, row){
            var dataItems = $systemServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
                var fieldDatas = dataItems[0].param;
                if(fieldDatas){
                    $systemOperatorGrid.grid("_loadDatas",fieldDatas);
                }
            }
        }
        $systemServerGrid.grid(setting);
        $systemServerGrid.on("click",".selectBtn",function(event){
            var dataItems = $systemServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
            	var config = {
            	   dataItem :dataItems[0],
            	   itemParam : window.itemParam
            	}
                callback(config);
            }
        });
        
        setting.datas = systemData[1].param;
        setting.columns = [
            {title:'字段名称', field:'title', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'实际名称', field:'code', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'描述', field:'desc', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },}
        ]
        $systemOperatorGrid.grid(setting);
        
        var $bussinessServerGrid = $("#bussinessServerGrid");
        $bussinessServerGrid.grid(setting2);
        $bussinessServerGrid.on("click",".selectBtn",function(event){
            var dataItems = $bussinessServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
            	var config = {
            	   dataItem :dataItems[0],
            	   itemParam : window.itemParam
            	}
            	callbackCopy(config);
            }
        });
        $bussinessServerGrid.on("click",".copyBtn",function(event){
        	var link = contextPath + '/mx/form/defined/ex/copyServer';
			$.layer({
				type : 2,
				maxmin : true,
				shadeClose : true,
				title : "选择数据集",
				closeBtn : [ 0, true ],
				shade : [ 0.6, '#000' ],
				border : [ 10, 0.3, '#000' ],
				offset : [ '', '' ],
				fadeIn : 100,
				area : [ '980px', '700px' ],
				iframe : {
					src : link
				}
			});
        });


        //环境信息grid
        var $systemBussinessServerGrid = $("#systemBussinessServerGrid");
        //对应的字段信息
        var $systemBussinessOperatorGrid = $("#systemBussinessOperatorGrid");

        var setting3 = $.extend({},setting);
        setting3.datas = systemBussinessData;
        setting3.events.onClickRow = function(index, row){
            var dataItems = $systemBussinessServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
                var fieldDatas = dataItems[0].param;
                if(fieldDatas){
                    $systemBussinessOperatorGrid.grid("_loadDatas",fieldDatas);
                }
            }
        }
        setting3.columns = [
            {title:'操作类型', field:'operation', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                'style': 'text-align:center;width: 50%'
            },},
            {title:'系统代码', field:'operationCode', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                'style': 'text-align:center;width: 40%'
            },},
            {title:'', field:'operationCode',width:20,'style': 'width: 20%',
                template : function(data){
                    return '<button type="button" class="btn green btn-sm selectBtn" style=""><i class="glyphicon glyphicon-ok"></i>选择</button>';
                }
            }
            
        ],
        $systemBussinessServerGrid.grid(setting3);
        $systemBussinessServerGrid.on("click",".selectBtn",function(event){
            var dataItems = $systemBussinessServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
                var config = {
                   dataItem :dataItems[0],
                   itemParam : window.itemParam
                }
                callback(config);
            }
        });


        setting3.datas = systemBussinessData[0].param;
        setting3.columns = [
            {title:'字段名称', field:'title', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'实际名称', field:'code', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'描述', field:'desc', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },}
        ]
        $systemBussinessOperatorGrid.grid(setting3);
        
    });

</script>