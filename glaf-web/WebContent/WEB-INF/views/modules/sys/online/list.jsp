<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户在线</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">

    <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
     <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    
    <!-- select2 -->
    <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />

    <!-- iCheck -->
<link href="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />
    <link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/scripts/plugins/bootstrap/grid/css/grid.css'>
<%@ include file="/WEB-INF/views/inc/init_style.jsp"%>
<%@ include file="/WEB-INF/views/inc/init_script.jsp"%>
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/jquery.core.extends.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!--validate-->
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/login/js/jquery.validate.min.js"></script>
<!-- select2 -->
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

<!-- iCheck -->
<script src="<%=request.getContextPath()%>/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js" type="text/javascript"></script>

<!-- fileinput -->
<script src="<%=request.getContextPath()%>/scripts/designer/plugins/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/designer/plugins/bootstrap-fileinput/js/locales/zh.js" type="text/javascript"></script>

<script type='text/javascript' src='<%=request.getContextPath()%>/scripts/uuid.js'></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
<script src='<%=request.getContextPath()%>/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>
<script src='<%=request.getContextPath()%>/scripts/jquery.mtResizable.js' type='text/javascript'></script>
<style type="text/css">
.page-size {
    float: left;
    margin-top: 10px;
    }
 .total {
    float: left;
    margin-top: 16px;
    }


</style>
<script type="text/javascript">
   var contextPath="<%=request.getContextPath()%>";

   jQuery(function(){
		   $("#gridId").grid({
		       // toolbar: "[{'name':'create','text':'新增'},{'name':'save','text':'保存'},{'name':'cancel','text':'取消'}]",
		        resizable: false,
       			scrollable: false,
		        clickUpdate: false,
		        occupy: false,
		        sortable: {},
		        selectable: '',
		        showInLine: true,
		        sortDesc: false,
		        combineAble: false,
		         ajax: { 
		                read: { 
		                    url:'<%=request.getContextPath()%>/mx/public/online/read',
		                    async: true, 
		                    data: {}, 
		                    success: function(ret) { 
		                        if (ret) { 
		                            return { 
		                                total: ret.total, 
		                                rows: ret.data 
		                            } 
		                        } else { 
		                            return ret; 
		                        } 
		                    } 
		                }, 
		                parameterMap: function(data) { 
		                    return JSON.stringify(data || {}); 
		                }, 
		                schema: { 
		                    model: { 
		                        id: "id", 
		                        fields: { 
		                            } 
		                    }, 
		                    data: "rows", 
		                    total: "total" 
		                } 
		            }, 
		            columns:[ 
		                {title:'序号', field:'startIndex', sortable:false,'isEditor':false, 'attributes': { 
		                    'style': 'text-align:center;width:12%;' 
		                },}, 
		                {title:'用户名', field:'actorId', sortable:false,'isEditor':false, 'attributes': { 
		                    'style': 'text-align:center;width:12%;' 
		                },}, 
		                {title:'用户姓名', field:'name', sortable:false,'isEditor':false, 'attributes': { 
		                    'style': 'text-align:center;width:12%;' 
		                },}, 
		                {title:'登录时间', field:'loginDate_datetime', sortable:false,'isEditor':false, 'attributes': { 
		                    'style': 'text-align:center;width:12%;' 
		                },}, 
		                {title:'登录IP', field:'loginIP', sortable:false,'isEditor':false, 'attributes': { 
		                    'style': 'text-align:center;width:12%;' 
		                },}, 
		                {title:'最后更新时间', field:'checkDate_datetime', sortable:false,'isEditor':false, 'attributes': { 
		                    'style': 'text-align:center;width:12%;' 
		                },}, 
		                 {title:'功能键',field:'functionKey',sortable:true,'isEditor':true,  
		                'attributes': { 
		                    'style': 'text-align:center;width:28%;' 
		                },
		                'template':function(data){
		                	 var buttonHtml = '<button type="button" class="changeBtn">'+
			                    '<a href="#" onclick="javascript:viewUser();" style = "background-color: #fffff;">查看</a>  </button>';
			                    buttonHtml += '<button type="button"class="createuser">'+
			                    '<a href="#" onclick="javascript:kickOut();">注销</a>  </button>';
		                    return buttonHtml;
		                }
		                 
		                 }
		           
		            ],
		            pagination: {
		                paging: true,
		                page: 1,
		                pageable: {
		                    refresh: true,
		                    buttonCount: 10,
		                    
		                },
		                pageSize: 10
		            }
		});
		   
		   $("#queryBtn").click(function(){
			   $("#gridId").grid("query",{"searchWord":$("#searchWord").val()});
		   });
		   
		   $("#queBtn").click(function(){
			   doKickOut();
		   });
		   
		   $("body").on("click",".changeBtn",function(event){
			   viewUser();
		    });
		   $("body").on("click",".createuser",function(event){
			   var selected = $('#gridId').grid('getSelectedRows');
			   if(selected){
			   kickOut();
			   }
		    });
		   
	});

		function viewUser(){
			 var selected = $('#gridId').grid('getSelectedRows');
			 if(selected){
			var link = '<%=request.getContextPath()%>/mx/identity/user/view?actorId='+selected[0].actorId;
			art.dialog.open(link, { height: 420, width: 480, title: "查看用户信息", lock: true, scrollbars:"no" }, false);
			 }
		}


		function resize(){
			jQuery('#mydatagrid').datagrid('resize', {
				width:800,
				height:400
			});
		}
  	
		
		 function kickOut(){
			 var selected = $('#gridId').grid('getSelectedRows');
			 if(selected){
				if(confirm("在线用户可能正在进行业务办理，将影响用户正常使用，确定注销吗？")){
					jQuery.ajax({
						   type: "POST",
						   url: '<%=request.getContextPath()%>/mx/public/online/doKickOut?actorId='+selected[0].actorId,
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
							   $('#gridId').datagrid('query');
						   }
					 });
				}
		  }
			}
		

		function doKickOut(){
			var ids = [];
			var selected = $('#gridId').grid('getSelectedRows');
			if(selected && confirm("在线用户可能正在进行业务办理，将影响用户正常使用，确定注销吗？")){
				jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/public/online/doKickOut?actorId='+selected[0].actorId,
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
						   $('#gridId').datagrid('query');
					   }
				 });
			} 
		}

		function reloadGrid(){
		    jQuery('#mydatagrid').datagrid('reload');
		}
	
		function getSelected(){
		    var selected = jQuery('#mydatagrid').datagrid('getSelected');
		    if (selected){
			    alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
		    }
		}
	
		function getSelections(){
		    var ids = [];
		    var rows = jQuery('#mydatagrid').datagrid('getSelections');
		    for(var i=0;i<rows.length;i++){
			    ids.push(rows[i].code);
		    }
		    alert(ids.join(':'));
		}

		function clearSelections(){
		    jQuery('#mydatagrid').datagrid('clearSelections');
		}
	
		function loadGridData(url){
	        jQuery.post(url,{qq:'xx'},function(data){
	            //var text = JSON.stringify(data); 
	            //alert(text);
	            jQuery('#mydatagrid').datagrid('loadData', data);
	        },'json');
		}

	    function searchData(){
			var searchWord = document.getElementById("searchWord2").value.trim();
	        document.getElementById("searchWord").value = searchWord;
			var params = jQuery("#iForm").formSerialize();
	        jQuery.ajax({
	                    type: "POST",
	                    url: '<%=request.getContextPath()%>/mx/public/online/json',
	                    dataType:  'json',
					    data: params,
	                    error: function(data){
	                            alert('服务器处理错误！');
	                    },
	                    success: function(data){
	                            jQuery('#gridId').grid('loadData', data);
	                    }
	               });
		}
		 		 
</script>
</head>
<body style="margin:1px;"> 

<div style="margin:0;"></div>  

  <div class="portlet box strruleportlet" style="background: #F5F5F5;border:1px solid #ccc;">
       
        <div class="portlet-body">
            <div class="row form-group">
                <label class="control-label col-xs-2" style="margin-top: 6px;text-align:right;">
                    用户：
                </label>
                <div class="col-xs-4">
                    <input type="text" class="form-control" name="searchWord" id="searchWord">
                </div>
                <div class="col-xs-2">
                    <button class="btn btn-sm btn-info" id="queryBtn" style = "background-color: #4CAF50;"><i class="fa fa-search"></i>查询</button>
                    <button class="btn btn-sm btn-info" id="queBtn" style="background-color: #f44336;"><i class="fa fa-search"></i>注销</button>
                </div>
            </div>
        </div>
    </div>
  
  <table id="gridId"></table> 
 
</body>
</html>
