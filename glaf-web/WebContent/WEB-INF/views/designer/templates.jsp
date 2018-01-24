<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<template id="tabstrip">
<div class="nav-tabs-custom">
	<ul class="nav nav-tabs">
		<li class="active"><a aria-expanded="true" href="#tab_1" data-toggle="tab">Tab 1</a></li>
		<li><a aria-expanded="false" href="#tab_2" data-toggle="tab">Tab 2</a></li>
		<li><a aria-expanded="false" href="#tab_3" data-toggle="tab">Tab 3</a></li>
		<li class="dropdown"><a class="dropdown-toggle" aria-expanded="false" href="#" data-toggle="dropdown"> Dropdown <span class="caret"></span>
		</a>
			<ul class="dropdown-menu">
				<li role="presentation"><a tabindex="-1" role="menuitem" href="#">Action</a></li>
				<li role="presentation"><a tabindex="-1" role="menuitem" href="#">Another action</a></li>
				<li role="presentation"><a tabindex="-1" role="menuitem" href="#">Something else here</a></li>
				<li class="divider" role="presentation"></li>
				<li role="presentation"><a tabindex="-1" role="menuitem" href="#">Separated link</a></li>
			</ul></li>
		<li class="pull-right"><a class="text-muted" href="#"><i class="fa fa-gear"></i></a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane active containerComp" id="tab_1">
			a greater artist than now.
		</div>
		<!-- /.tab-pane -->
		<div class="tab-pane containerComp" id="tab_2">
      The European
    </div>
		<!-- /.tab-pane -->
		<div class="tab-pane containerComp" id="tab_3">
      Lorem Ipsum 
    </div>
		<!-- /.tab-pane -->
	</div>
	<!-- /.tab-content -->
</div>
</template>
<template id="progressbar">
<div class="progress">
	<div class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
		<span class="sr-only">40% Complete (success)</span>
	</div>
</div>
</template>
<template id="charts">
<div class="box box-primary">
	<div class="box-header with-border">
		<h3 class="box-title">Area Chart</h3>

		<div class="box-tools pull-right">
			<button class="btn btn-box-tool" type="button" data-widget="collapse">
				<i class="fa fa-minus"></i>
			</button>
			<button class="btn btn-box-tool" type="button" data-widget="remove">
				<i class="fa fa-times"></i>
			</button>
		</div>
	</div>
	<div class="box-body chart-responsive">
		<div class="chart" id="revenue-chart" style="height: 300px;">
			<svg xmlns="http://www.w3.org/2000/svg" style="top: -0.63px; overflow: hidden; position: relative;" width="795" height="300" version="1.1"
				xmlns="http://www.w3.org/2000/svg">
				<desc>Created with Raphaël 2.1.0</desc>
				<defs />
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18"
					y="261.2" font="10px &quot;Arial&quot;">
				<tspan dy="3.96">0</tspan></text>
				<path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 261.2 H 770" />
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18"
					y="202.15" font="10px &quot;Arial&quot;">
				<tspan dy="3.96">7,500</tspan></text>
				<path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 202.15 H 770" />
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18"
					y="143.1" font="10px &quot;Arial&quot;">
				<tspan dy="3.96">15,000</tspan></text>
				<path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 143.1 H 770" />
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18"
					y="84.05" font="10px &quot;Arial&quot;">
				<tspan dy="3.96">22,500</tspan></text>
				<path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 84.05 H 770" />
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18" y="25"
					font="10px &quot;Arial&quot;">
				<tspan dy="3.96">30,000</tspan></text>
				<path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 25 H 770" />
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: middle;" fill="#888888" stroke="none" text-anchor="middle"
					transform="matrix(1 0 0 1 0 6.9)" x="640.0409" y="273.7" font="10px &quot;Arial&quot;">
				<tspan dy="3.96">2013</tspan></text>
				<text font-family="sans-serif" font-size="12px" font-weight="normal"
					style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: middle;" fill="#888888" stroke="none" text-anchor="middle"
					transform="matrix(1 0 0 1 0 6.9)" x="325.0408" y="273.7" font="10px &quot;Arial&quot;">
				<tspan dy="3.96">2012</tspan></text>
				<path style="fill-opacity: 1;" fill="#74a5c1" fill-opacity="1" stroke="none"
					d="M 61.68 219.219 C 81.4751 219.731 121.065 222.791 140.86 221.266 C 160.655 219.742 200.246 209.292 220.041 207.024 C 239.621 204.78 278.781 205.035 298.36 203.221 C 317.94 201.407 357.1 195.057 376.68 192.513 C 396.475 189.941 436.065 182.651 455.861 182.758 C 475.656 182.865 515.246 204.332 535.041 193.371 C 554.621 182.529 593.781 102.009 613.361 95.5451 C 632.725 89.152 671.455 135.231 690.82 141.943 C 710.615 148.803 750.205 147.859 770 149.832 L 770 261.2 L 61.68 261.2 Z" />
				<path fill="none" stroke="#3c8dbc" stroke-width="3"
					d="M 61.68 219.219 C 81.4751 219.731 121.065 222.791 140.86 221.266 C 160.655 219.742 200.246 209.292 220.041 207.024 C 239.621 204.78 278.781 205.035 298.36 203.221 C 317.94 201.407 357.1 195.057 376.68 192.513 C 396.475 189.941 436.065 182.651 455.861 182.758 C 475.656 182.865 515.246 204.332 535.041 193.371 C 554.621 182.529 593.781 102.009 613.361 95.5451 C 632.725 89.152 671.455 135.231 690.82 141.943 C 710.615 148.803 750.205 147.859 770 149.832" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="61.68" cy="219.219" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="140.86" cy="221.266" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="220.041" cy="207.024" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="298.36" cy="203.221" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="376.68" cy="192.513" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="455.861" cy="182.758" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="535.041" cy="193.371" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="613.361" cy="95.5451" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="690.82" cy="141.943" r="4" />
				<circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="770" cy="149.832" r="4" />
				<path style="fill-opacity: 1;" fill="#eaf2f5" fill-opacity="1" stroke="none"
					d="M 61.68 240.21 C 81.4751 239.989 121.065 241.538 140.86 239.328 C 160.655 237.117 200.246 223.505 220.041 222.526 C 239.621 221.558 278.781 233.409 298.36 231.541 C 317.94 229.673 357.1 209.445 376.68 207.583 C 396.475 205.699 436.065 214.6 455.861 216.558 C 475.656 218.517 515.246 232.555 535.041 223.251 C 554.621 214.047 593.781 148.331 613.361 142.525 C 632.725 136.783 671.455 170.594 690.82 177.058 C 710.615 183.665 750.205 190.373 770 194.812 L 770 261.2 L 61.68 261.2 Z" />
				<path fill="none" stroke="#a0d0e0" stroke-width="3"
					d="M 61.68 240.21 C 81.4751 239.989 121.065 241.538 140.86 239.328 C 160.655 237.117 200.246 223.505 220.041 222.526 C 239.621 221.558 278.781 233.409 298.36 231.541 C 317.94 229.673 357.1 209.445 376.68 207.583 C 396.475 205.699 436.065 214.6 455.861 216.558 C 475.656 218.517 515.246 232.555 535.041 223.251 C 554.621 214.047 593.781 148.331 613.361 142.525 C 632.725 136.783 671.455 170.594 690.82 177.058 C 710.615 183.665 750.205 190.373 770 194.812" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="61.68" cy="240.21" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="140.86" cy="239.328" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="220.041" cy="222.526" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="298.36" cy="231.541" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="376.68" cy="207.583" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="455.861" cy="216.558" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="535.041" cy="223.251" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="613.361" cy="142.525" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="690.82" cy="177.058" r="4" />
				<circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="770" cy="194.812" r="4" /></svg>
			<div class="morris-hover morris-default-style" style="left: 94.36px; top: 143px; display: none;">
				<div class="morris-hover-row-label">2011 Q2</div>
				<div class="morris-hover-point" style="color: rgb(160, 208, 224);">Item 1: 2,778</div>
				<div class="morris-hover-point" style="color: rgb(60, 141, 188);">Item 2: 2,294</div>
			</div>
		</div>
	</div>
	<!-- /.box-body -->
</div>
</template>
<template id="grid">
<div class="box">
	<div class="box-header">
		<h3 class="box-title" contenteditable="true">Simple Full Width Table</h3>
		<div class="box-tools">
			<ul class="pagination pagination-sm no-margin pull-right">
				<li><a href="#">«</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">»</a></li>
			</ul>
		</div>
	</div>
	<!-- /.box-header -->
	<div class="box-body no-padding">
		<table class="table" contenteditable="true">
			<tbody>
				<tr>
					<th style="width: 10px;">#</th>
					<th>Task</th>
					<th>Progress</th>
					<th style="width: 40px;">Label</th>
				</tr>
				<tr>
					<td>1.</td>
					<td>Update software</td>
					<td>
						<div class="progress progress-xs">
							<div class="progress-bar progress-bar-danger" style="width: 55%;"></div>
						</div>
					</td>
					<td><span class="badge bg-red">55%</span></td>
				</tr>
				<tr>
					<td>2.</td>
					<td>Clean database</td>
					<td>
						<div class="progress progress-xs">
							<div class="progress-bar progress-bar-yellow" style="width: 70%;"></div>
						</div>
					</td>
					<td><span class="badge bg-yellow">70%</span></td>
				</tr>
				<tr>
					<td>3.</td>
					<td>Cron job running</td>
					<td>
						<div class="progress progress-xs progress-striped active">
							<div class="progress-bar progress-bar-primary" style="width: 30%;"></div>
						</div>
					</td>
					<td><span class="badge bg-light-blue">30%</span></td>
				</tr>
				<tr>
					<td>4.</td>
					<td>Fix and squish bugs</td>
					<td>
						<div class="progress progress-xs progress-striped active">
							<div class="progress-bar progress-bar-success" style="width: 90%;"></div>
						</div>
					</td>
					<td><span class="badge bg-green">90%</span></td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- /.box-body -->
</div>
<!-- /.box --> </template>
<!--页面元素通用模板-->
<template id="elementTemplate">
<div class="mybox box-element ui-draggable">
	<a href="#close" class="remove label label-danger"><i class="glyphicon glyphicon-remove"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i>拖动</span> <span class="configuration"> <span class="btn-group listgroup-set" ng-controller="ListSimpleController"> <a
			class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="javascript:;">设置列分组<span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li ng-repeat="item in visibleData"><i style="display: inline-block" class="fa fa-check"></i> <a style="display: inline-block" ng-click="setListGroup">{{item.name}}</a>
				</li>
			</ul>
	</span> <span class="btn-group roweffect-set" ng-controller="ListSimpleController"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="javascript:;">设置行背景<span
				class="caret"></span></a>
			<ul class="dropdown-menu">
				<li ng-repeat="item in visibleData"><i style="display: inline-block" class="fa fa-check"></i> <a style="display: inline-block" ng-click="setRowEffect">{{item.name}}</a>
				</li>
			</ul>
	</span> <span class="btn-group listmt-set" ng-controller="ListSimpleController"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="javascript:;">列表风格<span
				class="caret"></span></a>
			<ul class="dropdown-menu">
				<li ng-repeat="item in data"><i style="display: inline-block" class="fa fa-check"></i> <a style="display: inline-block" ng-click="selectListType">{{item.name_cn}}</a>
				</li>
			</ul>
	</span> <span class="btn-group bgcolor-set" ng-controller="BgColorController"> <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="javascript:;">背景颜色<span
				class="caret"></span></a>
			<ul class="dropdown-menu">
				<li ng-repeat="color in data"><i style="display: inline-block; color: {{color.name==focusObj.bgColor?'#000000':'#eeeeee'" class="fa fa-check"></i> <a
					style="display: inline-block" class="{{color.name}}" ng-click="selectBgColor(color.name)">&nbsp;</a></li>
			</ul>
	</span>
	</span>
	<div class="preview"></div>
	<div class="view">
		<!--元素内容-->
	</div>
</div>
</template>
<!--布局模板-->
<template id="col-md-12">
<div class="lyrow">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="12" class="form-control">
	</div>
	<div class="view">
		<div class="row">
			<div class="col-md-12 column"></div>
		</div>
	</div>
</div>
</template>
<template id="col-md-6-6">
<div class="lyrow ui-draggable" style="display: block;">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="6 6" class="form-control">
	</div>
	<div class="view">
		<div class="row clearfix">

			<div class="col-md-6 column"></div>
			<div class="col-md-6 column"></div>
		</div>
	</div>
</div>
</template>
<template id="col-md-8-4">
<div class="lyrow ui-draggable" style="display: block;">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="8 4" class="form-control">
	</div>
	<div class="view">
		<div class="row clearfix">

			<div class="col-md-8 column"></div>
			<div class="col-md-4 column"></div>
		</div>
	</div>
</div>
</template>
<template id="col-md-4-4-4">
<div class="lyrow ui-draggable" style="display: block;">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="4 4 4" class="form-control">
	</div>
	<div class="view">
		<div class="row clearfix">

			<div class="col-md-4 column"></div>
			<div class="col-md-4 column"></div>
			<div class="col-md-4 column"></div>
		</div>
	</div>
</div>
</template>
<template id="col-md-2-6-4">
<div class="lyrow ui-draggable" style="display: block;">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="2 6 4" class="form-control">
	</div>
	<div class="view">
		<div class="row clearfix">

			<div class="col-md-2 column"></div>
			<div class="col-md-6 column"></div>
			<div class="col-md-4 column"></div>
		</div>
	</div>
</div>
</template>
<template id="col-md-9-3">
<div class="lyrow ui-draggable" style="display: block;">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="9 3" class="form-control">
	</div>
	<div class="view">
		<div class="row clearfix">

			<div class="col-md-9 column"></div>
			<div class="col-md-3 column"></div>
		</div>
	</div>
</div>
</template>
<template id="col-md-3-9">
<div class="lyrow ui-draggable" style="display: block;">
	<a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i> 删除</a> <span class="drag label label-default"><i
		class="glyphicon glyphicon-move"></i> 拖动</span>
	<div class="preview">
		<input type="text" value="3 9" class="form-control">
	</div>
	<div class="view">
		<div class="row clearfix">

			<div class="col-md-3 column"></div>
			<div class="col-md-9 column"></div>
		</div>
	</div>
</div>
</template>
<template id="definedTable">
<div class="box-body no-padding">
	<table class="table table-striped">
		<tr>
			<th style="width: 10px">#</th>
			<th>Task</th>
			<th>Progress</th>
			<th style="width: 40px">Label</th>
		</tr>
		<tr>
			<td>1.</td>
			<td>Update software</td>
			<td>
				<div class="progress progress-xs">
					<div class="progress-bar progress-bar-danger" style="width: 55%"></div>
				</div>
			</td>
			<td><span class="badge bg-red">55%</span></td>
		</tr>
		<tr>
			<td>2.</td>
			<td>Clean database</td>
			<td>
				<div class="progress progress-xs">
					<div class="progress-bar progress-bar-yellow" style="width: 70%"></div>
				</div>
			</td>
			<td><span class="badge bg-yellow">70%</span></td>
		</tr>
		<tr>
			<td>3.</td>
			<td>Cron job running</td>
			<td>
				<div class="progress progress-xs progress-striped active">
					<div class="progress-bar progress-bar-primary" style="width: 30%"></div>
				</div>
			</td>
			<td><span class="badge bg-light-blue">30%</span></td>
		</tr>
		<tr>
			<td>4.</td>
			<td>Fix and squish bugs</td>
			<td>
				<div class="progress progress-xs progress-striped active">
					<div class="progress-bar progress-bar-success" style="width: 90%"></div>
				</div>
			</td>
			<td><span class="badge bg-green">90%</span></td>
		</tr>
	</table>
</div>
</template>

<template id="treelist">
<div class="box-body no-padding">
	<table class="table table-striped">
		<tr>
			<th style="width: 10px">#</th>
			<th>Task</th>
			<th>Progress</th>
			<th style="width: 40px">Label</th>
		</tr>
		<tr>
			<td>1.</td>
			<td>Update software</td>
			<td>
				<div class="progress progress-xs">
					<div class="progress-bar progress-bar-danger" style="width: 55%"></div>
				</div>
			</td>
			<td><span class="badge bg-red">55%</span></td>
		</tr>
		<tr>
			<td>2.</td>
			<td>Clean database</td>
			<td>
				<div class="progress progress-xs">
					<div class="progress-bar progress-bar-yellow" style="width: 70%"></div>
				</div>
			</td>
			<td><span class="badge bg-yellow">70%</span></td>
		</tr>
		<tr>
			<td>3.</td>
			<td>Cron job running</td>
			<td>
				<div class="progress progress-xs progress-striped active">
					<div class="progress-bar progress-bar-primary" style="width: 30%"></div>
				</div>
			</td>
			<td><span class="badge bg-light-blue">30%</span></td>
		</tr>
		<tr>
			<td>4.</td>
			<td>Fix and squish bugs</td>
			<td>
				<div class="progress progress-xs progress-striped active">
					<div class="progress-bar progress-bar-success" style="width: 90%"></div>
				</div>
			</td>
			<td><span class="badge bg-green">90%</span></td>
		</tr>
	</table>
</div>
</template>


<template id="progressbar">
<div class="progress-group">
	<span class="progress-text">Complete Purchase</span> <span class="progress-number"><b>310</b>/400</span>

	<div class="progress sm">
		<div class="progress-bar progress-bar-red" style="width: 80%"></div>
	</div>
</div>
</template>

<template id="listview"> <!-- /.box-header -->
<div class="box-body no-padding">
	<ul class="users-list clearfix">
		<li><img src="dist/img/user1-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">Alexander Pierce</a> <span class="users-list-date">Today</span>
		</li>
		<li><img src="dist/img/user8-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">Norman</a> <span class="users-list-date">Yesterday</span></li>
		<li><img src="dist/img/user7-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">Jane</a> <span class="users-list-date">12 Jan</span></li>
		<li><img src="dist/img/user6-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">John</a> <span class="users-list-date">12 Jan</span></li>
		<li><img src="dist/img/user2-160x160.jpg" alt="User Image"> <a class="users-list-name" href="#">Alexander</a> <span class="users-list-date">13 Jan</span></li>
		<li><img src="dist/img/user5-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">Sarah</a> <span class="users-list-date">14 Jan</span></li>
		<li><img src="dist/img/user4-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">Nora</a> <span class="users-list-date">15 Jan</span></li>
		<li><img src="dist/img/user3-128x128.jpg" alt="User Image"> <a class="users-list-name" href="#">Nadia</a> <span class="users-list-date">15 Jan</span></li>
	</ul>
	<!-- /.users-list -->
</div>
<!-- /.box-body -->
<div class="box-footer text-center">
	<a href="javascript::" class="uppercase">View All Users</a>
</div>
<!-- /.box-footer
</div> -->
<!--/.box 
</div>-->
</template>
<template id="box-widget">
<div class="box box-primary">
	<div class="box-header with-border">
		<div class="user-block">
			<i class="fa fa-inbox"></i><span class="description" contenteditable="true">标题</span>
		</div>
		<!-- /.user-block -->
		<div class="box-tools">
			<button title="" class="btn btn-box-tool" data-original-title="Mark as read" data-toggle="tooltip">
				<i class="fa fa-circle-o"></i>
			</button>
			<button class="btn btn-box-tool" data-widget="collapse">
				<i class="fa fa-minus"></i>
			</button>
			<button class="btn btn-box-tool" data-widget="remove">
				<i class="fa fa-times"></i>
			</button>
		</div>
		<!-- /.box-tools -->
	</div>
	<!-- /.box-header -->
	<div class="box-body containerComp" style="display: block;"></div>
	<!-- /.box-body -->
	<div class="box-footer box-comments containerComp" style="display: block;">
		<div class="box-comment"></div>
		<!-- /.box-comment -->
	</div>
	<!-- /.box-footer -->
	<div class="box-footer containerComp" style="display: block;"></div>
	<!-- /.box-footer -->
</div>
</template>
<template id="yScroll">
<div class="box-body">
	<div class="carousel slide" id="carousel-example-generic" data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-slide-to="0" data-target="#carousel-example-generic"></li>
			<li class="active" data-slide-to="1" data-target="#carousel-example-generic"></li>
			<li data-slide-to="2" data-target="#carousel-example-generic"></li>
		</ol>
		<div class="carousel-inner">
			<div class="item">
				<img alt="First slide" src="http://placehold.it/900x500/39CCCC/ffffff&amp;text=I+Love+Bootstrap">
				<div class="carousel-caption">First Slide</div>
			</div>
			<div class="item active">
				<img alt="Second slide" src="http://placehold.it/900x500/3c8dbc/ffffff&amp;text=I+Love+Bootstrap">
				<div class="carousel-caption">Second Slide</div>
			</div>
			<div class="item">
				<img alt="Third slide" src="http://placehold.it/900x500/f39c12/ffffff&amp;text=I+Love+Bootstrap">
				<div class="carousel-caption">Third Slide</div>
			</div>
		</div>
		<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev"> <span class="fa fa-angle-left"></span>
		</a> <a class="right carousel-control" href="#carousel-example-generic" data-slide="next"> <span class="fa fa-angle-right"></span>
		</a>
	</div>
</div>
</template>
<template id="metrolist">
<div class="mt-element-list"></div>
</template>
<template id="list_mt2_default">
<div class="portlet-body">
	<div class="mt-element-list">
		<div ui-com="com-mt2" class="mt-list-head list-default font-white" ui-bgcolor="bg-dark">
			<h3 class="list-title">Default List</h3>
			<div class="list-date">Nov 8, 2015</div>
		</div>
		<div class="mt-list-container list-default">
			<ul>
				<li class="mt-list-item">
					<div class="list-icon-container">
						<a href="javascript:;"> <i class="icon-close"></i>
						</a>
					</div>
					<div class="list-datetime">
						11am <br> 8 Nov
					</div>
					<div class="list-item-content">
						<h3 class="uppercase bold">
							<a href="javascript:;">Listings Feature</a>
						</h3>
						<p>Lorem ipsum dolor sit amet</p>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
</template>
<template id="list_mt2_todo">
<div class="portlet-body">
	<div class="mt-element-list">
		<div ui-com="com-mt2" class="mt-list-head list-todo opt-2 font-white" ui-bgcolor="bg-dark">
			<div class="list-head-title-container">
				<h3 class="list-title">My Projects</h3>
				<div class="list-head-count">
					<div class="list-head-count-item">
						<i class="fa fa-check"></i> 15
					</div>
					<div class="list-head-count-item">
						<i class="fa fa-cog"></i> 34
					</div>
				</div>
			</div>
			<a href="javascript:;">
				<div ui-com="com-mt2" class="list-count pull-right" ui-bgcolor="bg-red-mint">
					<i class="fa fa-plus"></i>
				</div>
			</a>
		</div>
		<div class="mt-list-container list-todo opt-2">
			<div class="list-todo-line bg-red"></div>
			<ul>
				<li class="mt-list-item">
					<div class="list-todo-icon bg-white font-blue-steel">
						<i class="fa fa-database"></i>
					</div>
					<div class="list-todo-item item-1">
						<a class="list-toggle-container font-white" data-toggle="collapse" href="list_todo_collapse" aria-expanded="false">
							<div ui-com="com-mt2" class="list-toggle done uppercase" ui-bgcolor="bg-blue-steel">
								<div class="list-toggle-title bold">Metronic Database</div>
								<div class="badge badge-default pull-right bg-white font-dark bold">3</div>
							</div>
						</a>
						<div class="task-list panel-collapse collapse in" id="list_todo_collapse">
							<ul>
								<li class="task-list-item done">
									<div class="task-icon">
										<a href="javascript:;"> <i class="fa fa-database"></i>
										</a>
									</div>
									<div class="task-status">
										<a class="done" href="javascript:;"> <i class="fa fa-check"></i>
										</a> <a class="pending" href="javascript:;"> <i class="fa fa-close"></i>
										</a>
									</div>
									<div class="task-content">
										<h4 class="uppercase bold">
											<a href="javascript:;">Database Optimization</a>
										</h4>
									</div>
								</li>
								<li class="task-list-item">
									<div class="task-icon">
										<a href="javascript:;"> <i class="fa fa-table"></i>
										</a>
									</div>
									<div class="task-status">
										<a class="done" href="javascript:;"> <i class="fa fa-check"></i>
										</a> <a class="pending" href="javascript:;"> <i class="fa fa-close"></i>
										</a>
									</div>
									<div class="task-content">
										<h4 class="uppercase bold">
											<a href="javascript:;">Table Sorting</a>
										</h4>
									</div>
								</li>
								<li class="task-list-item">
									<div class="task-icon">
										<a href="javascript:;"> <i class="fa fa-pencil"></i>
										</a>
									</div>
									<div class="task-status">
										<a class="done" href="javascript:;"> <i class="fa fa-check"></i>
										</a> <a class="pending" href="javascript:;"> <i class="fa fa-close"></i>
										</a>
									</div>
									<div class="task-content">
										<h4 class="uppercase bold">
											<a href="javascript:;">Data Entry</a>
										</h4>
									</div>
								</li>
							</ul>
							<div class="task-footer bg-grey">
								<div class="row">
									<div class="col-xs-6">
										<a class="task-trash" href="javascript:;"> <i class="fa fa-trash"></i>
										</a>
									</div>
									<div class="col-xs-6">
										<a class="task-add" href="javascript:;"> <i class="fa fa-plus"></i>
										</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
</template>
<template id="list_mt2_news">
<div class="portlet-body">
	<div class="mt-element-list">
		<div ui-com="com-mt2" class="mt-list-head list-news ext-1 font-white" ui-bgcolor="bg-yellow-crusta">
			<div class="list-head-title-container">
				<h3 class="list-title">News List</h3>
			</div>
			<div ui-com="com-mt2" class="list-count pull-right" ui-bgcolor="bg-yellow-saffron">2</div>
		</div>
		<div class="mt-list-container list-news ext-2">
			<ul>
				<li class="mt-list-item">
					<div class="list-icon-container">
						<a href="javascript:;"> <i class="fa fa-angle-right"></i>
						</a>
					</div>
					<div class="list-thumb">
						<a href="javascript:;"> <img alt="" src="../assets/global/img/portfolio/600x600/12.jpg">
						</a>
					</div>
					<div class="list-datetime bold uppercase font-yellow-casablanca">8 Nov, 2015</div>
					<div class="list-item-content">
						Lorem ipsum dolor sit amet
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
</template>