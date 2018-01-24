<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.core.config.SystemConfig"%>
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
	<title>页面工作流流程设置</title>
	<meta charset="utf-8">
	<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
    <link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
    <link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"  />

	<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/plugins/bootstrap/dialog/css/bootstrap-dialog.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaiss-jsbpm.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaiss-mtbox.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/style.css">
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath }" ,
			pageId = '${param.pageId}' || "4fdd46cd87774f5d8de586c5afc34d99" ,
			mtxx = {
				tfMsg:{},
				saveData:{
					pageId: pageId
				}
			};
	</script>
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white page-sidebar-closed">
<input type="hidden" name="hidParam" id="hidParam">
 <!-- component template -->
    <script type="text/x-template" id="grid-template">
    <div class="mtbox_container" >
	     <div class="mtbox_inner" >
	     		<div class="mtbox_header"></div>
				<div class="mtbox_content"  :class="{ mtbox_scroll: scroll }" v-if="filteredData.length">
				 	<template v-if="!showGroup"></template>
	     			<template v-for="(entry,index) in filteredData" v-else>
		     			<div class="mtbox_group" :group-index="index"><span class="mtbox_expander mtbox_collapse" @click="expander"></span></div>
				        <table class="mtbox_table" :group-index="index" cellpadding="0" cellspacing="0">
					        <tbody>
						          <tr v-for="row in entry.rows" >
							           
						          </tr>
					        </tbody>
				        </table>
			        </template>
		      	</div>
				<div class="mtbox_content" v-else></div>
	    </div>
		<div class="mtbox_view" :style="{width:width}">
			<div class="mtbox_header">
				<table class="mtbox_table" cellpadding="0" cellspacing="0">
			        <thead>
				          <tr>
					            <th v-for="key in columns"
						              @click="sortBy(key)"
						              :class="{ active: sortKey == key['field'] }" :width="key['width']">
						              <div class="mtbox_cell">
						              		{{ key["title"] }}
						              		<span class="arrow" :class="sortOrders[key['field']] > 0 ? 'asc' : 'dsc'">
						              		</span>
						              </div>
					            </th>
				          </tr>
				        </thead>
				</table>
			</div>
     		<div class="mtbox_content" :class="{ mtbox_scroll: scroll }" v-if="filteredData.length">
     		 	<template  v-if="!showGroup" >
			        <table class="mtbox_table" cellpadding="0" cellspacing="0">
				        <tbody>
					          <tr v-for="(row,index) in filteredData" >
						            <td v-for="key in columns" :width="key['width']">
						              		<template v-if="key['edit']">
							            		<template v-if="row['editor']=='dialog'">
						              				<input type="text" name="" :class="['in_'+row['code']]" @input="updateValue" :tname="row['code']" v-bind:value="obj[row['code']]">
						              			</template>
						              			<template v-else>
						              				<input type="text" name="" :class="['in_'+row['code']]" @input="updateValue" :tname="row['code']" v-bind:value="obj[row['code']]">
						              			</template>
							            	</template>
							                <template v-else>
							                	{{row[key['field']]}}
							                </template>
						            </td>
					          </tr>
				        </tbody>
			        </table>
		        </template >
     			<template  v-else v-for="(entry,index) in filteredData" >
	     			<div class="mtbox_group" :group-index="index">{{entry.groupName}}</div>
			        <table class="mtbox_table" :group-index="index" cellpadding="0" cellspacing="0">
				        <tbody>
					          <tr v-for="row in entry.rows" >
						            <td v-for="key in columns" :width="key['width']">
							            	<template v-if="key['edit']">
							            		<template v-if="row['editor']=='dialog'">
						              				<input type="text" name="" style="width:60%;" readonly :class="['in_'+row['code']]" v-show="dependMethod(row['depend'],row['depVal'])" :tname="row['code']"  v-bind:value="obj[row['code']]"><button @click="dialogClick" :tname="row['code']" v-show="dependMethod(row['depend'],row['depVal'])" :dep="row['depend']">..</button>
						              			</template>
						              			<template v-if="row['editor']=='textbox'">
						              				<input type="text" name="" :class="['in_'+row['code']]" @input="updateValue" v-show="dependMethod(row['depend'],row['depVal'])" :tname="row['code']" v-bind:value="obj[row['code']]">
						              			</template>
							            	</template>
							                <template v-else>
							                	{{row[key['field']]}}
							                </template>
							          </tr>
						            </td>
					          </tr>
				        </tbody>
			        </table>
		        </template >
	      	</div>
	      	<div class="mtbox_content" v-else></div>
	      </div>
      </div>
    </script>

     <div class="page-header navbar navbar-fixed-top">
            <!-- BEGIN HEADER INNER -->
            <div class="page-header-inner ">
                <!-- LOGO -->
                <div class="page-logo">
                    <a href="#">
                        <img src="${pageContext.request.contextPath }/scripts/home/metrolic/images/yellow_chissLOGOg.png" alt="logo" class="logo-default" /> </a>
                    <div class="menu-toggler sidebar-toggler"> </div>
                </div>
                
                <!-- END LOGO -->
                <!-- BEGIN RESPONSIVE MENU TOGGLER -->
                <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
                <!-- END RESPONSIVE MENU TOGGLER -->
                <!-- BEGIN TOP NAVIGATION MENU -->
            	<div class="btn-toolbar" role="toolbar" style="float: left;padding-left: 88px;padding-top: 8px;">
					<div class="btn-group">
	                    <button type="button" id="saveBt" class="btn btn-icon-only btn-default" title="保存" style="height:30px;">
							<i class="fa fa-save"></i>
						</button>
					</div>
				</div>
                <div class="top-menu">
                    <ul class="nav navbar-nav pull-right">
                      
                        <li class="dropdown dropdown-quick-sidebar-toggler">
                            <a href="javascript:;" class="dropdown-toggle">
                                <i class="icon-logout"></i>
                            </a>
                        </li>
                        <!-- END QUICK SIDEBAR TOGGLER -->
                    </ul>
                </div>
                <!-- END TOP NAVIGATION MENU -->
            </div>
            <!-- END HEADER INNER -->
        </div>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN SIDEBAR -->
            <div class="page-sidebar-wrapper">
                <div class="page-sidebar navbar-collapse collapse">
					
                </div>
                <!-- END SIDEBAR -->
            </div>
            <!-- END SIDEBAR -->
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- BEGIN PAGE HEADER-->
              		<div id="canvas" style="min-height: 100%"></div>
             
                </div>
                <!-- END CONTENT BODY -->
            </div>
            <!-- END CONTENT -->
			 <a href="javascript:;" class="page-quick-sidebar-toggler">
                <i class="icon-login"></i>
            </a>
				<div class="page-quick-sidebar-wrapper" data-close-on-body-click="false">
                <div class="page-quick-sidebar">
                	<div id="demo" class="mtbox" :style="{width:width}">
				      <div style="height:23px">
				      	查询:<input type="text" name="query" v-model="searchQuery">分组:<input type="checkbox" v-model="showGroup">
				      </div>
				      <demo-grid
				      	:obj="obj"
				        :data="gridData"
				        :columns="gridColumns" 
				        :show-group="showGroup" 
				        :scroll="scroll"
				        :group-field="groupField" 
				        :filter-key="searchQuery"
				        v-on:updatevalue="updateObj" >
				      </demo-grid>
				    </div>
                 
                </div>
            </div>
        </div>
        <!-- END CONTAINER -->
        <!-- BEGIN FOOTER -->
        <div class="page-footer">
            <div class="page-footer-inner"> 华闽通达.
               
            </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
	        ...
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>


    
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/moddles.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/replaceOptions.js"></script>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/layout.js" type="text/javascript"></script>

    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js"></script>
	<script src="${pageContext.request.contextPath }/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/defined.kendo.paramConvert.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/defined.kendo.eventParam.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/defined.kendo.param.js" ></script>

	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/zhcn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaiss-jsbpm.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaiss-mtbox.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/vue.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/grid.js"></script>

	
</body>
</html>