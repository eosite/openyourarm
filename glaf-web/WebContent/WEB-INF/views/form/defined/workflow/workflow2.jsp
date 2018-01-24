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
	
	<!-- ztree -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/assets/diagram-js.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/assets/bpmn-font/css/bpmn.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/assets/bpmn-font/css/bpmn-embedded.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/css/chinaiss-mtbox.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/css/style.css">
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath }" ,
			pageId = '${param.pageId}',
			complexId = '${param.complexId}',
			isComplex = '${param.isComplex}',
			templateId = '${param.templateId}',
			isTemplate = '${param.isTemplate}',
			willChange = '${param.willChange}',
			toDiagram = '${param.toDiagram}',
			mtxx = {
				tfMsg:{},
				saveData:{
					pageId: pageId
				}
			};
	</script>
</head>
<body id="body" class="page-header-fixed page-sidebar-closed-hide-logo page-content-white page-sidebar-closed">
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
						              				<input type="text" name="" style="width:60%;" readonly :class="['in_'+row['code']]" v-show="dependMethod(row['depend'],row['depVal'])" :tname="row['code']"  v-bind:value="obj[row['code']]"><button @click="dialogClick" :tname="row['code']" v-show="dependMethod(row['depend'],row['depVal'])" :dep="row['depend']">..</button>
						              			</template>
						              			<template v-if="row['editor']=='textbox'">
						              				<input type="text" name="" :class="['in_'+row['code']]" @input="updateValue" v-show="dependMethod(row['depend'],row['depVal'])" :tname="row['code']" v-bind:value="obj[row['code']]">
						              			</template>
						              			<template v-if="row['editor']=='dropdown'">
													<select @change="updateValue" :tname="row['code']" v-show="dependMethod(row['depend'],row['depVal'])" style="width:80%;"> 
														<template v-for="opt in row.data">
															<template v-if="obj[row['code']]==opt.NAME_" >
																<option  v-bind:value="opt.NAME_" selected>{{opt.TITLE_}}</option>
															</template>
															<template v-else>
																<option  v-bind:value="opt.NAME_">{{opt.TITLE_}}</option>
															</template>
														</template>
													</select>
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
						              			<template v-if="row['editor']=='dropdown'">
													<select @change="updateValue" :tname="row['code']" v-show="dependMethod(row['depend'],row['depVal'])" style="width:80%;"> 
														<template v-for="opt in row.data">
															<template v-if="obj[row['code']]==opt.NAME_" >
																<option  v-bind:value="opt.NAME_" selected>{{opt.TITLE_}}</option>
															</template>
															<template v-else>
																<option  v-bind:value="opt.NAME_">{{opt.TITLE_}}</option>
															</template>
														</template>
													</select>
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
	                    <button type="button" id="saveBt" class="btn btn-icon-only btn-default" title="保存" style="height:30px;" disabled>
							<i class="fa fa-save"></i>
						</button>
						
						<button type="button" id="saveBt2" class="btn btn-icon-only btn-default" title="保存为复合构件" style="height:30px;display:none;">
							<i class="fa fa-paste"></i>
						</button>
						
						<button type="button" id="saveBt3" class="btn btn-icon-only btn-default" title="保存为模板" style="height:30px;display:none;">
							<i class="fa fa-save"></i>
						</button>
					</div>
					<div class="btn-group">
	                    <button type="button" id="keyboardBt" class="btn btn-icon-only btn-default" title="快捷键" style="height:30px;">
							<i class="fa fa-keyboard-o"></i>
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
                	<!-- 选卡 -->
                	<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">属性</a></li>
						<li><a href="#tab2" data-toggle="tab">构件</a></li>
						<li class="dropdown">
							<a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown">其它 <b class="caret"></b></a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
								<li><a href="#tab3" tabindex="-1" data-toggle="tab">页面构件</a></li>
								<li><a href="#tab4" tabindex="-1" data-toggle="tab">复合构件</a></li>
							</ul>
						</li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="home">
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
						<div class="tab-pane fade" id="tab2">
							<ul id="ztree" class="ztree"></ul>
						</div>
						<div class="tab-pane fade" id="tab3">
							<ul id="ztree2" class="ztree"></ul>
						</div>
						<div class="tab-pane fade" id="tab4">
							<ul id="ztree3" class="ztree"></ul>
						</div>
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
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	        <h4 class="modal-title" id="myModalLabel">快捷键</h4>
	      </div>
	      <div class="modal-body">
			      <table>
			        <tbody>
			          <tr>
			            <td style="width:200px;">撤销</td>
			            <td class="binding"><code>ctrl + Z</code></td>
			          </tr>
			          <tr>
			            <td>恢复</td>
			            <td class="binding"><code>ctrl + shift + Z</code></td>
			          </tr>
			          <tr>
			            <td>全选</td>
			            <td class="binding"><code>ctrl + A</code></td>
			          </tr>
			          <tr>
			            <td>上下滚动</td>
			            <td class="binding">ctrl + 滚轮</td>
			          </tr>
			          <tr>
			            <td>左右滚动</td>
			            <td class="binding">ctrl + shift + 滚轮</td>
			          </tr>
			          <tr>
			            <td>快捷编辑</td>
			            <td class="binding">E</td>
			          </tr>
			          <tr>
			            <td>手型工具</td>
			            <td class="binding">H</td>
			          </tr>
			          <tr>
			            <td>套索工具</td>
			            <td class="binding">L</td>
			          </tr>
			          <tr>
			            <td>空间工具</td>
			            <td class="binding">S</td>
			          </tr>
			          <tr>
			            <td>快捷连线</td>
			            <td class="binding">C</td>
			          </tr>
			          <tr>
			            <td>快速查找</td>
			            <td class="binding">ctrl + F</td>
			          </tr>
			        </tbody>
			      </table>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <!--  <button type="button" class="btn btn-primary">Save changes</button>-->
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	        <h4 class="modal-title" id="myModalLabel">保存复合构件</h4>
	      </div>
	      <div class="modal-body">
			      <div id="fname_row" class="row">
					  <div class="col-md-12">
					  	<div class="form-group">
    						<label for="fname"><font style="color:red;">*</font>复合构件名称:</label>
    						<input type="text" class="form-control" id="fname" placeholder="请输入复合构件名称">
  						</div>
  					  </div>
				  </div>
			      <div class="row">
					  <div class="col-md-12">
					  	<div class="form-group">
    						<label for="fremark">备注:</label>
    						<textarea type="text" class="form-control" id="fremark" placeholder="备注"></textarea>
  						</div>
  					  </div>
				  </div>
			  	  <label><font style="color:red;">*</font>选择暴露构件:</label>
			      <div class="row">
					  <div id="expose_col" class="col-md-12">
					  	
  					  </div>
				  </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" id="complexSave" class="btn btn-primary">保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>


    
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.blockui.min.js"></script>
    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/scripts/app.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/layout/scripts/layout.js" type="text/javascript"></script>

    <script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

	<script src="${pageContext.request.contextPath }/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath }/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js"></script>
	<script src="${pageContext.request.contextPath }/scripts/plugins/bootstrap/dialog/js/bootstrap-dialog.min.js"></script>

	<!-- ztree -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/ztree/js/jquery.ztree.all.min.js"></script>
	
	<!-- 参数 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/filterObj.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/defined.kendo.paramConvert.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/defined.kendo.eventParam.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/defined.kendo.param.js" ></script>
	<!-- bpmn -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/diagram-minimap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/bpmn-modeler.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/bpmn-js-cli.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/bpmn-i18n.min.js"></script>
    <!-- vue -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/vue.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/chinaiss-mtbox.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/vue.ext.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/index.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jsWorkFlow/chinaissBpmn/old2diagram.js"></script>

	
</body>
</html>