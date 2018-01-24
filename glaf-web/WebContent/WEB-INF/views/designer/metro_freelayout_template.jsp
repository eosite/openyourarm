<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<template id="tabstrip"> 
<div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a aria-expanded="true" href="#tab_1" data-toggle="tab">Tab 1</a></li>
              <li><a aria-expanded="false" href="#tab_2" data-toggle="tab">Tab 2</a></li>
              <li><a aria-expanded="false" href="#tab_3" data-toggle="tab">Tab 3</a></li>
              <li class="dropdown">
                <a class="dropdown-toggle" aria-expanded="false" href="#" data-toggle="dropdown">
                  Dropdown <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                  <li role="presentation"><a tabindex="-1" role="menuitem" href="#">Action</a></li>
                  <li role="presentation"><a tabindex="-1" role="menuitem" href="#">Another action</a></li>
                  <li role="presentation"><a tabindex="-1" role="menuitem" href="#">Something else here</a></li>
                  <li class="divider" role="presentation"></li>
                  <li role="presentation"><a tabindex="-1" role="menuitem" href="#">Separated link</a></li>
                </ul>
              </li>
              <li class="pull-right"><a class="text-muted" href="#"><i class="fa fa-gear"></i></a></li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane active containerComp" id="tab_1">
                <b>How to use:</b>
                <p>Exactly like the original bootstrap tabs except you should use
                  the custom wrapper <code>.nav-tabs-custom</code> to achieve this style.</p>
                A wonderful serenity has taken possession of my entire soul,
                like these sweet mornings of spring which I enjoy with my whole heart.
                I am alone, and feel the charm of existence in this spot,
                which was created for the bliss of souls like mine. I am so happy,
                my dear friend, so absorbed in the exquisite sense of mere tranquil existence,
                that I neglect my talents. I should be incapable of drawing a single stroke
                at the present moment; and yet I feel that I never was a greater artist than now.
              </div>
              <!-- /.tab-pane -->
              <div class="tab-pane containerComp" id="tab_2">
                The European languages are members of the same family. Their separate existence is a myth.
                For science, music, sport, etc, Europe uses the same vocabulary. The languages only differ
                in their grammar, their pronunciation and their most common words. Everyone realizes why a
                new common language would be desirable: one could refuse to pay expensive translators. To
                achieve this, it would be necessary to have uniform grammar, pronunciation and more common
                words. If several languages coalesce, the grammar of the resulting language is more simple
                and regular than that of the individual languages.
              </div>
              <!-- /.tab-pane -->
              <div class="tab-pane containerComp" id="tab_3">
                Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                It has survived not only five centuries, but also the leap into electronic typesetting,
                remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset
                sheets containing Lorem Ipsum passages, and more recently with desktop publishing software
                like Aldus PageMaker including versions of Lorem Ipsum.
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
 </div>
 </template>
 <template id="progressbar"> 
 <div class="progress">
                <div class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
                  <span class="sr-only">40% Complete (success)</span>
                </div>
 </div>
 </template>
<!-- 
 <template id="charts">
 <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Area Chart</h3>

              <div class="box-tools pull-right">
                <button class="btn btn-box-tool" type="button" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button class="btn btn-box-tool" type="button" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body chart-responsive">
              <div class="chart" id="revenue-chart" style="height: 300px;"><svg xmlns="http://www.w3.org/2000/svg" style="top: -0.63px; overflow: hidden; position: relative;" width="795" height="300" version="1.1" xmlns="http://www.w3.org/2000/svg"><desc>Created with Raphaël 2.1.0</desc><defs /><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18" y="261.2" font="10px &quot;Arial&quot;"><tspan dy="3.96">0</tspan></text><path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 261.2 H 770" /><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18" y="202.15" font="10px &quot;Arial&quot;"><tspan dy="3.96">7,500</tspan></text><path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 202.15 H 770" /><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18" y="143.1" font="10px &quot;Arial&quot;"><tspan dy="3.96">15,000</tspan></text><path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 143.1 H 770" /><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18" y="84.05" font="10px &quot;Arial&quot;"><tspan dy="3.96">22,500</tspan></text><path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 84.05 H 770" /><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: end;" fill="#888888" stroke="none" text-anchor="end" x="49.18" y="25" font="10px &quot;Arial&quot;"><tspan dy="3.96">30,000</tspan></text><path fill="none" stroke="#aaaaaa" stroke-width="0.5" d="M 61.68 25 H 770" /><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: middle;" fill="#888888" stroke="none" text-anchor="middle" transform="matrix(1 0 0 1 0 6.9)" x="640.0409" y="273.7" font="10px &quot;Arial&quot;"><tspan dy="3.96">2013</tspan></text><text font-family="sans-serif" font-size="12px" font-weight="normal" style="font: 12px/normal sans-serif; font-size-adjust: none; font-stretch: normal; text-anchor: middle;" fill="#888888" stroke="none" text-anchor="middle" transform="matrix(1 0 0 1 0 6.9)" x="325.0408" y="273.7" font="10px &quot;Arial&quot;"><tspan dy="3.96">2012</tspan></text><path style="fill-opacity: 1;" fill="#74a5c1" fill-opacity="1" stroke="none" d="M 61.68 219.219 C 81.4751 219.731 121.065 222.791 140.86 221.266 C 160.655 219.742 200.246 209.292 220.041 207.024 C 239.621 204.78 278.781 205.035 298.36 203.221 C 317.94 201.407 357.1 195.057 376.68 192.513 C 396.475 189.941 436.065 182.651 455.861 182.758 C 475.656 182.865 515.246 204.332 535.041 193.371 C 554.621 182.529 593.781 102.009 613.361 95.5451 C 632.725 89.152 671.455 135.231 690.82 141.943 C 710.615 148.803 750.205 147.859 770 149.832 L 770 261.2 L 61.68 261.2 Z" /><path fill="none" stroke="#3c8dbc" stroke-width="3" d="M 61.68 219.219 C 81.4751 219.731 121.065 222.791 140.86 221.266 C 160.655 219.742 200.246 209.292 220.041 207.024 C 239.621 204.78 278.781 205.035 298.36 203.221 C 317.94 201.407 357.1 195.057 376.68 192.513 C 396.475 189.941 436.065 182.651 455.861 182.758 C 475.656 182.865 515.246 204.332 535.041 193.371 C 554.621 182.529 593.781 102.009 613.361 95.5451 C 632.725 89.152 671.455 135.231 690.82 141.943 C 710.615 148.803 750.205 147.859 770 149.832" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="61.68" cy="219.219" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="140.86" cy="221.266" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="220.041" cy="207.024" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="298.36" cy="203.221" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="376.68" cy="192.513" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="455.861" cy="182.758" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="535.041" cy="193.371" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="613.361" cy="95.5451" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="690.82" cy="141.943" r="4" /><circle fill="#3c8dbc" stroke="#ffffff" stroke-width="1" cx="770" cy="149.832" r="4" /><path style="fill-opacity: 1;" fill="#eaf2f5" fill-opacity="1" stroke="none" d="M 61.68 240.21 C 81.4751 239.989 121.065 241.538 140.86 239.328 C 160.655 237.117 200.246 223.505 220.041 222.526 C 239.621 221.558 278.781 233.409 298.36 231.541 C 317.94 229.673 357.1 209.445 376.68 207.583 C 396.475 205.699 436.065 214.6 455.861 216.558 C 475.656 218.517 515.246 232.555 535.041 223.251 C 554.621 214.047 593.781 148.331 613.361 142.525 C 632.725 136.783 671.455 170.594 690.82 177.058 C 710.615 183.665 750.205 190.373 770 194.812 L 770 261.2 L 61.68 261.2 Z" /><path fill="none" stroke="#a0d0e0" stroke-width="3" d="M 61.68 240.21 C 81.4751 239.989 121.065 241.538 140.86 239.328 C 160.655 237.117 200.246 223.505 220.041 222.526 C 239.621 221.558 278.781 233.409 298.36 231.541 C 317.94 229.673 357.1 209.445 376.68 207.583 C 396.475 205.699 436.065 214.6 455.861 216.558 C 475.656 218.517 515.246 232.555 535.041 223.251 C 554.621 214.047 593.781 148.331 613.361 142.525 C 632.725 136.783 671.455 170.594 690.82 177.058 C 710.615 183.665 750.205 190.373 770 194.812" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="61.68" cy="240.21" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="140.86" cy="239.328" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="220.041" cy="222.526" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="298.36" cy="231.541" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="376.68" cy="207.583" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="455.861" cy="216.558" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="535.041" cy="223.251" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="613.361" cy="142.525" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="690.82" cy="177.058" r="4" /><circle fill="#a0d0e0" stroke="#ffffff" stroke-width="1" cx="770" cy="194.812" r="4" /></svg><div class="morris-hover morris-default-style" style="left: 94.36px; top: 143px; display: none;"><div class="morris-hover-row-label">2011 Q2</div><div class="morris-hover-point" style="color: rgb(160, 208, 224);">
  Item 1:
  2,778
</div><div class="morris-hover-point" style="color: rgb(60, 141, 188);">
  Item 2:
  2,294
</div></div></div>
            </div>

         </div>
 </template>
  -->
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
        </div><!-- /.box-header -->
        <div class="box-body no-padding">
          <table class="table" contenteditable="true">
            <tbody><tr>
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
          </tbody></table>
        </div><!-- /.box-body -->
              </div><!-- /.box -->
 </template>
 <!--页面元素通用模板-->
 <template id="elementTemplate">
   <div class="mybox box-element ui-draggable">
	<div class="preview"><input type="text" class="form-control"></div>
	<div class="view">
	</div>

  </div>
 </template>
<!-- 工具栏模板 -->
 <template id="gridlayout">
    <div class="lyrow">
	  <div class="btn-group-vertical btn-group-xs btn-group-solid toolbar">
	    <a class="drag dragBtn bg-green-sharp bg-font-green-sharp" data-toggle="dropdown"  data-close-others="true" title="按住鼠标左键，可拖动！" style="-webkit-border-radius: 0 0 0 28px !important;border-radius: 0 0 0 28px !important;">
	    <i class="icon-cursor-move"></i></a>
		<ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;">
			 <li><a class="divideBt"><i class="glyphicon glyphicon-th"></i>分栏</a></li>
			 <li><a class="moveUpBt"><i class="glyphicon glyphicon-circle-arrow-up"></i>上移</a></li>
			 <li><a class="moveDownBt"><i class="glyphicon glyphicon-circle-arrow-down"></i>下移</a></li>
			 <li><a class="editBt"><i class="glyphicon-edit glyphicon"></i>编辑</a></li>
			 <li><a class="myremove"><i class="glyphicon-remove glyphicon"></i>删除</a></li>
			 <li class="divider"> </li>
			  <li>
						<a class="addup-row" href="javascript:;"><i class="fa fa-chevron-up"> 上方插入空行</i> </a>
					</li>
					<li>
						<a class="adddown-row" href="javascript:;"><i class="fa fa-chevron-down"> 下方插入空行</i></a>
					</li>
					<li>
						<a class="copyaddup-row" href="javascript:;"><i class="glyphicon glyphicon-export "> 上方复制插入</i> </a>
					</li>
					<li>
						<a class="copyadddown-row" href="javascript:;"><i class="glyphicon glyphicon-import "> 下方复制插入</i></a>
					</li>
					<li class="divider"> </li>
					<li>
						<a class="cut-row" href="javascript:;"><i class="fa fa-cut"> 剪切</i> </a>
					</li>
					<li>
						<a class="copy-row" href="javascript:;"><i class="fa fa-copy"> 复制</i></a>
					</li>
					<li>
						<a class="paste-row" href="javascript:;"><i class="fa fa-paste"> 粘贴</i></a>
					</li>
					<li>
						<a class="save-as-row" href="javascript:;"><i class="fa fa-save"> 另存为模板</i> </a>
					</li>
		 </ul>
	  </div>
     <div class="preview"><input type="text" class="form-control"></div>
	 <div class="view">
	 </div>
	 </div>
 </template>
 <template id="gridlayout_toolbar">
	  	  <div class="btn-group-vertical btn-group-xs btn-group-solid toolbar">
	    <a class="drag dragBtn bg-green-sharp bg-font-green-sharp" data-toggle="dropdown" data-close-others="true" title="按住鼠标左键，可拖动！" style="-webkit-border-radius: 0 0 0 28px !important;border-radius: 0 0 0 28px !important;">
	    <i class="icon-cursor-move"></i></a>
		<ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;">
			 <li><a class="divideBt"><i class="glyphicon glyphicon-th"></i>分栏</a></li>
			 <li><a class="moveUpBt"><i class="glyphicon glyphicon-circle-arrow-up"></i>上移</a></li>
			 <li><a class="moveDownBt"><i class="glyphicon glyphicon-circle-arrow-down"></i>下移</a></li>
			 <li><a class="editBt"><i class="glyphicon-edit glyphicon"></i>编辑</a></li>
			 <li><a class="myremove"><i class="glyphicon-remove glyphicon"></i>删除</a></li>
			 <li class="divider"> </li>
			  <li>
						<a class="addup-row" href="javascript:;"><i class="fa fa-chevron-up"> 上方插入空行</i> </a>
					</li>
					<li>
						<a class="adddown-row" href="javascript:;"><i class="fa fa-chevron-down"> 下方插入空行</i></a>
					</li>
					<li>
						<a class="copyaddup-row" href="javascript:;"><i class="glyphicon glyphicon-export "> 上方复制插入</i> </a>
					</li>
					<li>
						<a class="copyadddown-row" href="javascript:;"><i class="glyphicon glyphicon-import "> 下方复制插入</i></a>
					</li>
					<li class="divider"> </li>
					<li>
						<a class="cut-row" href="javascript:;"><i class="fa fa-cut"> 剪切</i> </a>
					</li>
					<li>
						<a class="copy-row" href="javascript:;"><i class="fa fa-copy"> 复制</i></a>
					</li>
					<li>
						<a class="paste-row" href="javascript:;"><i class="fa fa-paste"> 粘贴</i></a>
					</li>
					<li>
						<a class="save-as-row" href="javascript:;"><i class="fa fa-save"> 另存为模板</i> </a>
					</li>
		 </ul>
	  </div>
   </template>
     <!--列工具栏模板-->
	<!--小尺寸模板-->
  <template id="colconfigTemplate">
	<button type="button" class="removecol btn btn-xs red" title="删除"><i class="glyphicon-remove glyphicon"></i></button>
	<span class="configuration">
	<div class="btn-group btn-group-xs btn-group-solid">
		<a class="btn blue" aria-expanded="false"  href="javascript:;" data-toggle="dropdown" data-close-others="true">
			<i class="icon-wrench"></i>
		</a>
		<ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;">
			 <li>
			 <a class="leftAddBt" title="左侧插入列"><i class="glyphicon glyphicon-chevron-left"></i><i class="glyphicon  glyphicon-plus "> 左侧插入列</i></a>
			 </li>
			 <li><a class="rightAddBt" title="右侧插入列"><i class="glyphicon glyphicon-plus "></i><i class="glyphicon glyphicon-chevron-right"> 右侧插入列</i></a></li>
			 <li><a class="moveLeftBt" title="向左移"><i class="glyphicon glyphicon-chevron-left"> 向左移</i></a></li>
			 <li><a class="moveRightBt" title="向右移"><i class="glyphicon glyphicon-chevron-right"> 向右移</i></a></li>
			 <li class="divider"> </li>
			 <li class="dropdown-submenu">
			   <a><i class="fa fa-align-center"> 水平对齐</i></a>
			   <ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;">
			         <li><a class="col-align" align-type="left" title="居左"><i class="fa fa-align-left"> 居左</i></a></li>
					 <li><a class="col-align" align-type="center"title="居中"><i class="fa fa-align-center"> 居中</i></a></li>
					 <li><a class="col-align" align-type="right" title="居右"><i class="fa fa-align-right"> 居右</i></a></li>
			   </ul>
			 </li>
			 <li class="divider"> </li>
			 <li>
					<a class="column-cut-row" href="javascript:;"><i class="fa fa-cut">  剪切</i> </a>
			 </li>
			<li>
					<a class="column-copy-row" href="javascript:;"><i class="fa fa-copy">  复制</i></a>
			</li>
			<li>
					<a class="column-paste-row" href="javascript:;"><i class="fa fa-paste">  粘贴</i></a>
		    </li>
			<li>
					<a class="column-empty-row" href="javascript:;"><i class="fa fa-eraser">  清空</i> </a>
			</li>
			<li>
					<a class="column-custom-row" href="javascript:;"><i class="fa fa-cogs">  自定义</i> </a>
			</li>
		    <li>
					<a class="column-save-as" href="javascript:;"><i class="fa fa-save">  另存为模板</i> </a>
		    </li>
		 </ul>
	</div>
	</span>
  </template>
  <!--布局模板-->
 <template id="col-md-12">
		<div class="row">
			<div class="col-md-12 column">
				 
			</div>
		</div>
 </div>
 </template>
 <template id="col-md-6-6">
 <div class="row clearfix">
		    
			<div class="col-md-6 column">
			     
			</div>
			<div class="col-md-6 column">
			    
			</div>
		</div>
</template>
<template id="col-md-8-4">
<div class="row clearfix">
		    
			<div class="col-md-8 column">
			  
			</div>
			<div class="col-md-4 column">
			  
			</div>
		</div>
 </template>
 <template id="col-md-4-4-4">
<div class="row clearfix">
		    
			<div class="col-md-4 column">
			  
			</div>
			<div class="col-md-4 column">
			    
			</div>
			<div class="col-md-4 column">
			   
			</div>
		</div>
  </template>
 <template id="col-md-2-6-4">
<div class="row clearfix">
		    
			<div class="col-md-2 column">
			  
			</div>
			<div class="col-md-6 column">
			   
			</div>
			<div class="col-md-4 column">

			</div>
		</div>
  </template>
  <template id="col-md-9-3">
<div class="row clearfix">
		    
			<div class="col-md-9 column"></div>
			<div class="col-md-3 column"></div>
		</div>
  </template>
   <template id="col-md-3-9">
<div class="row clearfix">  
			<div class="col-md-3 column"></div>
			<div class="col-md-9 column"></div>
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

  <template id="treelistbt"> 
    <div></div>
  </template>

  <template id="gridbt"> 
    <div></div>
  </template>


  <template id="progressbar"> 
	  <div class="progress-group">
		<span class="progress-text">Complete Purchase</span>
		<span class="progress-number"><b>310</b>/400</span>

		<div class="progress sm">
		  <div class="progress-bar progress-bar-red" style="width: 80%"></div>
		</div>
	  </div>
  </template>

  <template id="listview"> 
                <!-- /.box-header -->
                <div class="box-body no-padding">
                  <ul class="users-list clearfix">
                    <li>
                      <img src="dist/img/user1-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Alexander Pierce</a>
                      <span class="users-list-date">Today</span>
                    </li>
                    <li>
                      <img src="dist/img/user8-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Norman</a>
                      <span class="users-list-date">Yesterday</span>
                    </li>
                    <li>
                      <img src="dist/img/user7-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Jane</a>
                      <span class="users-list-date">12 Jan</span>
                    </li>
                    <li>
                      <img src="dist/img/user6-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">John</a>
                      <span class="users-list-date">12 Jan</span>
                    </li>
                    <li>
                      <img src="dist/img/user2-160x160.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Alexander</a>
                      <span class="users-list-date">13 Jan</span>
                    </li>
                    <li>
                      <img src="dist/img/user5-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Sarah</a>
                      <span class="users-list-date">14 Jan</span>
                    </li>
                    <li>
                      <img src="dist/img/user4-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Nora</a>
                      <span class="users-list-date">15 Jan</span>
                    </li>
                    <li>
                      <img src="dist/img/user3-128x128.jpg" alt="User Image">
                      <a class="users-list-name" href="#">Nadia</a>
                      <span class="users-list-date">15 Jan</span>
                    </li>
                  </ul>
                  <!-- /.users-list -->
                </div>
                <!-- /.box-body -->
                <div class="box-footer text-center">
                  <a href="javascript::" class="uppercase">View All Users</a>
                </div>
                <!-- /.box-footer -->
              </div>
              <!--/.box -->
            </div>
  </template>
  <template id="box-widget"> 
            <div class="box box-primary">
                <div class="box-header with-border">
                  <div class="user-block">
                    <i class="fa fa-inbox"></i><span class="description" contenteditable="true">标题</span>
                  </div><!-- /.user-block -->
                  <div class="box-tools">
                    <button title="" class="btn btn-box-tool" data-original-title="Mark as read" data-toggle="tooltip"><i class="fa fa-circle-o"></i></button>
                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                    <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                  </div><!-- /.box-tools -->
                </div><!-- /.box-header -->
                <div class="box-body containerComp" style="display: block;">
                   
                </div><!-- /.box-body -->
                <div class="box-footer box-comments containerComp" style="display: block;">
                  <div class="box-comment">
                   
                  </div><!-- /.box-comment -->
                </div><!-- /.box-footer -->
                <div class="box-footer containerComp" style="display: block;">
                   
                </div><!-- /.box-footer -->
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
                     <div class="carousel-caption">
                       First Slide
                     </div>
                   </div>
                   <div class="item active">
                     <img alt="Second slide" src="http://placehold.it/900x500/3c8dbc/ffffff&amp;text=I+Love+Bootstrap">
                     <div class="carousel-caption">
                       Second Slide
                     </div>
                   </div>
                   <div class="item">
                     <img alt="Third slide" src="http://placehold.it/900x500/f39c12/ffffff&amp;text=I+Love+Bootstrap">
                     <div class="carousel-caption">
                       Third Slide
                     </div>
                   </div>
                 </div>
                 <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                   <span class="fa fa-angle-left"></span>
                 </a>
                 <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                   <span class="fa fa-angle-right"></span>
                 </a>
               </div>
               </div>
   </template>
   <template id="metrolist">
        <div class="mt-element-list">
          <div class="mt-list-head list-simple bg-red font-white">
            <div class="list-head-title-container">
              <div class="list-date frame-variable" frame-variable="fv2">2016/03/02</div>
              <h3 class="list-title frame-variable" frame-variable="fv1">Metronic List</h3>
            </div>
          </div>
          <div class="mt-list-container list-simple">
              <ul>
                  <li class="mt-list-item">
                      <div class="list-icon-container done">
                          <i class="icon-check"></i>
                      </div>
                      <div class="list-datetime"> 8 Nov </div>
                      <div class="list-item-content">
                          <h3 class="uppercase">
                              <a href="javascript:;">Concept Proof</a>
                          </h3>
                      </div>
                  </li>
                  <li class="mt-list-item">
                      <div class="list-icon-container">
                          <i class="icon-close"></i>
                      </div>
                      <div class="list-datetime"> 8 Nov </div>
                      <div class="list-item-content">
                          <h3 class="uppercase">
                              <a href="javascript:;">Listings Feature</a>
                          </h3>
                      </div>
                  </li>
              </ul>
          </div>
        </div>
</template>
<template id="portlet-template">
   <div class="portlet box blue">
          <div class="portlet-title">
              <div class="caption">
                  <i class="fa fa-gift"></i>栏目</div>
              <div class="tools">
                  <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
                  <a href="#portlet-config" data-toggle="modal" class="config" data-original-title="" title=""> </a>
                  <a href="" class="fullscreen" data-original-title="" title=""> </a>
                  <a href="javascript:;" class="reload" data-original-title="" title=""> </a>
              </div>
          </div>
          <div class="portlet-body portlet-collapsed" style="display: block;">
             <div class="row clearfix" style="height: 100%;width:100%">  
			 <div class="col-md-3 column"  style="height: 100%;"></div>
			 <div class="col-md-9 column"  style="height: 100%;"></div>
		     </div>
          </div>
    </div>
</template>
<!-- 日期选择控件datepicker -->
<template id="datepickerbt">
 	<div class="input-group date datepickerbt component" data-date-format="dd-mm-yyyy">		
		<input type="text" class="form-control id frame-variable" frame-variable="fv1" >
		<span class="input-group-btn">
			<button class="btn default form-control" type="button">
				<i class="fa fa-calendar"></i>
			</button>
		</span>
	</div>
</template>
<!-- 日期时间选择控件datetimepicker -->
<template id="datetimepickerbt">
<div class="input-group date form_datetime component">
	<input type="text" class="form-control id frame-variable" frame-variable="fv1">
	<span class="input-group-btn">
		<button class="btn default form-control date-set" type="button">
            <i class="fa fa-clock-o"></i>
       </button>
	</span>
</div>
</template>
<!-- 日期范围选择控件daterangepicker -->
<template id="daterangepicker">
	<div class="input-group input-daterange id frame-variable component" >	
		<input type="text" class="form-control" name="from" frame-variable="fv1" data-role="datepickerbt" scope="bootstrap" crtltype="kendo">
		<span class="input-group-addon"> to </span>
		<input type="text" class="form-control" name="to" frame-variable="fv2" data-role="datepickerbt" scope="bootstrap" crtltype="kendo"> 
	</div>
</template>
<!-- 输入框控件 -->
<template id="textboxbt">
	<input type="text" class = "form-control id frame-variable component" frame-variable="fv1" placeholder="请输入..." > 
</template>
<!-- 文本域控件 -->
<template id="textareabt">
<div class="component">
	<textarea class="form-control id frame-variable" frame-variable="fv1" rows="3"></textarea>
</div>   
</template>
<!-- label标签控件-->
<template id="label">
<div class="component">
	<span class="id frame-variable" style="white-space:normal;line-height:1.4;" frame-variable="fv1">华闽通达</span>
</div>
</template>
<!-- touchspin数值输入控件 -->
<template id="touchspin">
<div class="component">  
	<input type="text" value=""  class="id frame-variable" frame-variable="fv1">
</div> 
</template>
<!-- progressbar控件-->
<template id="progressbarbt">
<div class="progress component">
   <div class="progress-bar progress-bar-danger id frame-variable" frame-variable="fv1"
      aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
      style="width: 40%;">
      <span class="sr-only">10% 完成</span>
   </div> 
</div>
</template>
<!--tabs -->
<template id="bootstrap_tabs">
  <div static-datas='[{"title":"New Tab 1", "content": "", "minHeight":200},{"title":"New Tab 2", "content": "", "minHeight":200}]'></div>
</template>
<template id="cell">
    <img src="${contextPath}/scripts/designer/images/cell.png" />
 </template>
<template id="mtbutton">
  <button type="button" class="btn btn-default component"><span class="id frame-variable" frame-variable="fv1">默认按钮</span></button>
</template>
<template id="definedButton">
  
  <button type="button" class="btn btn-default component"><span class="id frame-variable" contenteditable="true" frame-variable="fv1">自定义按钮</span></button>
  
</template>
<template id="buttongroup">
  <div class="btn-group">
    <button type="button" data-role="mtbutton" cname="mtbutton_控件" scope="bootstrap" crtltype="kendo" class="btn btn-default"><span class="id frame-variable" frame-variable="fv1">默认按钮</span></button>
    <button type="button" data-role="mtbutton" cname="mtbutton_控件" scope="bootstrap" crtltype="kendo" class="btn btn-default"><span class="id frame-variable" frame-variable="fv2">默认按钮</span></button>
    <button type="button" data-role="mtbutton" cname="mtbutton_控件" scope="bootstrap" crtltype="kendo" class="btn btn-default"><span class="id frame-variable" frame-variable="fv3">默认按钮</span></button>
  </div>
</template>
<template id="metroselect">
  <div class="component form-group">
      <div class="select2-bootstrap-prepend">
        <span class="input-group-btn" style="display:none;">
            <button class="btn btn-default" type="button" data-select2-open="single">
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </span>
        <select id="single" class="form-control select2" data-placeholder="请选择..." data-allow-clear="true">
            <option></option>
            <optgroup label="Alaskan">
                <option value="AK">Alaska</option>
                <option value="HI" disabled="disabled">Hawaii</option>
            </optgroup>
            <optgroup label="Pacific Time Zone">
                <option value="CA">California</option>
                <option value="NV">Nevada</option>
                <option value="OR">Oregon</option>
                <option value="WA">Washington</option>
            </optgroup>
            <optgroup label="Mountain Time Zone">
                <option value="AZ">Arizona</option>
                <option value="CO">Colorado</option>
                <option value="ID">Idaho</option>
                <option value="MT">Montana</option>
                <option value="NE">Nebraska</option>
                <option value="NM">New Mexico</option>
                <option value="ND">North Dakota</option>
                <option value="UT">Utah</option>
                <option value="WY">Wyoming</option>
            </optgroup>
            <optgroup label="Central Time Zone">
                <option value="AL">Alabama</option>
                <option value="AR">Arkansas</option>
                <option value="IL">Illinois</option>
                <option value="IA">Iowa</option>
                <option value="KS">Kansas</option>
                <option value="KY">Kentucky</option>
                <option value="LA">Louisiana</option>
                <option value="MN">Minnesota</option>
                <option value="MS">Mississippi</option>
                <option value="MO">Missouri</option>
                <option value="OK">Oklahoma</option>
                <option value="SD">South Dakota</option>
                <option value="TX">Texas</option>
                <option value="TN">Tennessee</option>
                <option value="WI">Wisconsin</option>
            </optgroup>
            <optgroup label="Eastern Time Zone">
                <option value="CT">Connecticut</option>
                <option value="DE">Delaware</option>
                <option value="FL">Florida</option>
                <option value="GA">Georgia</option>
                <option value="IN">Indiana</option>
                <option value="ME">Maine</option>
                <option value="MD">Maryland</option>
                <option value="MA">Massachusetts</option>
                <option value="MI">Michigan</option>
                <option value="NH">New Hampshire</option>
                <option value="NJ">New Jersey</option>
                <option value="NY">New York</option>
                <option value="NC">North Carolina</option>
                <option value="OH">Ohio</option>
                <option value="PA">Pennsylvania</option>
                <option value="RI">Rhode Island</option>
                <option value="SC">South Carolina</option>
                <option value="VT">Vermont</option>
                <option value="VA">Virginia</option>
                <option value="WV">West Virginia</option>
            </optgroup>
        </select>
      </div>
  </div>
</template>
<template id="metroselect_m">
  <div class="component form-group">
      <div class="select2-bootstrap-prepend">
        <span class="input-group-btn" style="display:none;">
            <button class="btn btn-default" type="button" data-select2-open="multiple">
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </span>
        <select id="multiple" class="form-control select2-multiple" multiple="" data-placeholder="请选择..." data-allow-clear="false">
            <option></option>
            <optgroup label="Alaskan">
                <option value="AK">Alaska</option>
                <option value="HI" disabled="disabled">Hawaii</option>
            </optgroup>
            <optgroup label="Pacific Time Zone">
                <option value="CA">California</option>
                <option value="NV">Nevada</option>
                <option value="OR">Oregon</option>
                <option value="WA">Washington</option>
            </optgroup>
            <optgroup label="Mountain Time Zone">
                <option value="AZ">Arizona</option>
                <option value="CO">Colorado</option>
                <option value="ID">Idaho</option>
                <option value="MT">Montana</option>
                <option value="NE">Nebraska</option>
                <option value="NM">New Mexico</option>
                <option value="ND">North Dakota</option>
                <option value="UT">Utah</option>
                <option value="WY">Wyoming</option>
            </optgroup>
            <optgroup label="Central Time Zone">
                <option value="AL">Alabama</option>
                <option value="AR">Arkansas</option>
                <option value="IL">Illinois</option>
                <option value="IA">Iowa</option>
                <option value="KS">Kansas</option>
                <option value="KY">Kentucky</option>
                <option value="LA">Louisiana</option>
                <option value="MN">Minnesota</option>
                <option value="MS">Mississippi</option>
                <option value="MO">Missouri</option>
                <option value="OK">Oklahoma</option>
                <option value="SD">South Dakota</option>
                <option value="TX">Texas</option>
                <option value="TN">Tennessee</option>
                <option value="WI">Wisconsin</option>
            </optgroup>
            <optgroup label="Eastern Time Zone">
                <option value="CT">Connecticut</option>
                <option value="DE">Delaware</option>
                <option value="FL">Florida</option>
                <option value="GA">Georgia</option>
                <option value="IN">Indiana</option>
                <option value="ME">Maine</option>
                <option value="MD">Maryland</option>
                <option value="MA">Massachusetts</option>
                <option value="MI">Michigan</option>
                <option value="NH">New Hampshire</option>
                <option value="NJ">New Jersey</option>
                <option value="NY">New York</option>
                <option value="NC">North Carolina</option>
                <option value="OH">Ohio</option>
                <option value="PA">Pennsylvania</option>
                <option value="RI">Rhode Island</option>
                <option value="SC">South Carolina</option>
                <option value="VT">Vermont</option>
                <option value="VA">Virginia</option>
                <option value="WV">West Virginia</option>
            </optgroup>
        </select>
      </div>
  </div>
</template>
<template id="icheckbox">
  <div class="form-group" static-datas='[{"text":"New 1"},{"text":"New 2"},{"text":"New 3"}]'>
    <div class="input-group">
      <div class="icheck-list">
      </div>
    </div>
  </div>
</template>
<template id="icheckradio">
  <div class="form-group" static-datas='[{"text":"New 1"},{"text":"New 2"},{"text":"New 3"}]'>
    <div class="input-group">
      <div class="icheck-list">
      </div>
    </div>
  </div>
</template>
<template id="jqfileupload">
  <div class="component">
    <!-- The fileinput-button span is used to style the file input field as button -->
    <span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>选择文件...</span>
        <!-- The file input field used as target for the file upload widget -->
        <input id="fileupload" type="file" name="file" multiple>
    </span>
    <!-- The global progress bar -->
    <div id="progress" class="progress">
        <div class="progress-bar progress-bar-success"></div>
    </div>
    <!-- The container for the uploaded files -->
    <div  id="files" class="files"></div>
  </div>
</template>
<template id="jqfileupload2">
<div class="mt-sp-jqfileupload component" style ="text-align: left;" data-role="jqfileupload2">
	<span class="btn btn-success fileinput-button"><i class="glyphicon glyphicon-plus"></i><span>选择文件</span> 
		<input class="mt-sp-fileupload" type="file" name="file" multiple/>
	</span>
	<div class="progress">
		<div class="progress-bar progress-bar-success"></div>
	</div>
	<div class="mt-sp-files"></div> 
</div>
</template>
<template id="portlet">
  <div class="portlet box red">
      <div class="portlet-title">
          <div class="caption">
              <i class="fa fa-gift"></i><span class="id frame-variable" frame-variable="fv1">Portlet </span></div>
          <div class="tools">
            <a href="javascript:;" class="collapse" style=""> </a>
            <a href="javascript:;" class="fullscreen" style=""> </a>
            <a href="javascript:;" class="reload" style="" data-url="/reload"> </a>
          </div>
      </div>
      <div class="portlet-body">
          <div class="containerComp scroller" style="height:200px" data-rail-visible="1" data-rail-color="yellow" data-handle-color="#a1b2bd">
            
          </div>
      </div>
  </div>
</template>
<template id="portlet_tabs">
  <div class="portlet box red">
      <div class="portlet-title">
          <div class="caption">
              <i class="fa fa-gift"></i><span class="id frame-variable" frame-variable="fv1">Portlet </span></div>
          <ul class="nav nav-tabs">
              <li class="active">
                  <a href="#portlet_tab_1" data-toggle="tab"> Tab 1 </a>
              </li>
              <li>
                  <a href="#portlet_tab_2" data-toggle="tab"> Tab 2 </a>
              </li>
          </ul>
      </div>
      <div class="portlet-body">
          <div class="tab-content">
              <div class="containerComp tab-pane active" id="portlet_tab_1" style="height:200px;overflow: auto;">
              </div>
              <div class=" containerComp tab-pane" id="portlet_tab_2" style="height:200px;overflow: auto;">
              </div>
          </div>
      </div>
  </div>
</template>
<!-- 日历组件 -->
<template id="calendarbt">
<div>
<div class="portlet light portlet-fit bordered calendar">

	<div class="portlet-title">
		<div class="caption">
			<i class=" icon-layers font-green"></i> <span
				class="caption-subject font-green sbold uppercase id frame-variable" frame-variable="title">Calendar</span>
		</div>
	</div>

	<div class="portlet-body id frame-variable" frame-variable="body">

		<div class="has-toolbar fc fc-ltr fc-unthemed">
			<div class="fc-toolbar">
				<div class="fc-left">
					<h2>2016年 三月</h2>
				</div>
				<div class="fc-right">
					<div class="fc-button-group">
						<button type="button"
							class="fc-prev-button fc-button fc-state-default fc-corner-left">
							<span class="fc-icon fc-icon-left-single-arrow"></span>
						</button>
						<button type="button"
							class="fc-next-button fc-button fc-state-default">
							<span class="fc-icon fc-icon-right-single-arrow"></span>
						</button>
						<button type="button"
							class="fc-today-button fc-button fc-state-default fc-state-disabled"
							disabled="disabled">今天</button>
						<button type="button"
							class="fc-month-button fc-button fc-state-default fc-state-active">月</button>
						<button type="button"
							class="fc-agendaWeek-button fc-button fc-state-default">周</button>
						<button type="button"
							class="fc-agendaDay-button fc-button fc-state-default fc-corner-right">日</button>
					</div>
				</div>
				<div class="fc-center"></div>
				<div class="fc-clear"></div>
			</div>
			<div class="fc-view-container" style="">
				<div class="fc-view fc-month-view fc-basic-view">
					<table>
						<thead class="fc-head">
							<tr>
								<td class="fc-widget-header"><div
										class="fc-row fc-widget-header">
										<table>
											<thead>
												<tr>
													<th class="fc-day-header fc-widget-header fc-mon">周一</th>
													<th class="fc-day-header fc-widget-header fc-tue">周二</th>
													<th class="fc-day-header fc-widget-header fc-wed">周三</th>
													<th class="fc-day-header fc-widget-header fc-thu">周四</th>
													<th class="fc-day-header fc-widget-header fc-fri">周五</th>
													<th class="fc-day-header fc-widget-header fc-sat">周六</th>
													<th class="fc-day-header fc-widget-header fc-sun">周日</th>
												</tr>
											</thead>
										</table>
									</div>
								</td>
							</tr>
						</thead>
						<tbody class="fc-body">
							<tr>
								<td class="fc-widget-content"><div
										class="fc-day-grid-container">
										<div class="fc-day-grid">
											<div class="fc-row fc-week fc-widget-content">
												<div class="fc-bg">
													<table>
														<tbody>
															<tr>
																<td
																	class="fc-day fc-widget-content fc-mon fc-other-month fc-past"
																	data-date="2016-02-29"></td>
																<td class="fc-day fc-widget-content fc-tue fc-past"
																	data-date="2016-03-01"></td>
																<td class="fc-day fc-widget-content fc-wed fc-past"
																	data-date="2016-03-02"></td>
																<td class="fc-day fc-widget-content fc-thu fc-past"
																	data-date="2016-03-03"></td>
																<td class="fc-day fc-widget-content fc-fri fc-past"
																	data-date="2016-03-04"></td>
																<td class="fc-day fc-widget-content fc-sat fc-past"
																	data-date="2016-03-05"></td>
																<td class="fc-day fc-widget-content fc-sun fc-past"
																	data-date="2016-03-06"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="fc-content-skeleton">
													<table>
														<thead>
															<tr>
																<td class="fc-day-number fc-mon fc-other-month fc-past"
																	data-date="2016-02-29">29</td>
																<td class="fc-day-number fc-tue fc-past"
																	data-date="2016-03-01">1</td>
																<td class="fc-day-number fc-wed fc-past"
																	data-date="2016-03-02">2</td>
																<td class="fc-day-number fc-thu fc-past"
																	data-date="2016-03-03">3</td>
																<td class="fc-day-number fc-fri fc-past"
																	data-date="2016-03-04">4</td>
																<td class="fc-day-number fc-sat fc-past"
																	data-date="2016-03-05">5</td>
																<td class="fc-day-number fc-sun fc-past"
																	data-date="2016-03-06">6</td>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class="fc-row fc-week fc-widget-content">
												<div class="fc-bg">
													<table>
														<tbody>
															<tr>
																<td class="fc-day fc-widget-content fc-mon fc-past"
																	data-date="2016-03-07"></td>
																<td class="fc-day fc-widget-content fc-tue fc-past"
																	data-date="2016-03-08"></td>
																<td class="fc-day fc-widget-content fc-wed fc-past"
																	data-date="2016-03-09"></td>
																<td class="fc-day fc-widget-content fc-thu fc-past"
																	data-date="2016-03-10"></td>
																<td class="fc-day fc-widget-content fc-fri fc-past"
																	data-date="2016-03-11"></td>
																<td class="fc-day fc-widget-content fc-sat fc-past"
																	data-date="2016-03-12"></td>
																<td class="fc-day fc-widget-content fc-sun fc-past"
																	data-date="2016-03-13"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="fc-content-skeleton">
													<table>
														<thead>
															<tr>
																<td class="fc-day-number fc-mon fc-past"
																	data-date="2016-03-07">7</td>
																<td class="fc-day-number fc-tue fc-past"
																	data-date="2016-03-08">8</td>
																<td class="fc-day-number fc-wed fc-past"
																	data-date="2016-03-09">9</td>
																<td class="fc-day-number fc-thu fc-past"
																	data-date="2016-03-10">10</td>
																<td class="fc-day-number fc-fri fc-past"
																	data-date="2016-03-11">11</td>
																<td class="fc-day-number fc-sat fc-past"
																	data-date="2016-03-12">12</td>
																<td class="fc-day-number fc-sun fc-past"
																	data-date="2016-03-13">13</td>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class="fc-row fc-week fc-widget-content">
												<div class="fc-bg">
													<table>
														<tbody>
															<tr>
																<td class="fc-day fc-widget-content fc-mon fc-past"
																	data-date="2016-03-14"></td>
																<td class="fc-day fc-widget-content fc-tue fc-past"
																	data-date="2016-03-15"></td>
																<td class="fc-day fc-widget-content fc-wed fc-past"
																	data-date="2016-03-16"></td>
																<td class="fc-day fc-widget-content fc-thu fc-past"
																	data-date="2016-03-17"></td>
																<td class="fc-day fc-widget-content fc-fri fc-past"
																	data-date="2016-03-18"></td>
																<td class="fc-day fc-widget-content fc-sat fc-past"
																	data-date="2016-03-19"></td>
																<td class="fc-day fc-widget-content fc-sun fc-past"
																	data-date="2016-03-20"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="fc-content-skeleton">
													<table>
														<thead>
															<tr>
																<td class="fc-day-number fc-mon fc-past"
																	data-date="2016-03-14">14</td>
																<td class="fc-day-number fc-tue fc-past"
																	data-date="2016-03-15">15</td>
																<td class="fc-day-number fc-wed fc-past"
																	data-date="2016-03-16">16</td>
																<td class="fc-day-number fc-thu fc-past"
																	data-date="2016-03-17">17</td>
																<td class="fc-day-number fc-fri fc-past"
																	data-date="2016-03-18">18</td>
																<td class="fc-day-number fc-sat fc-past"
																	data-date="2016-03-19">19</td>
																<td class="fc-day-number fc-sun fc-past"
																	data-date="2016-03-20">20</td>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class="fc-row fc-week fc-widget-content">
												<div class="fc-bg">
													<table>
														<tbody>
															<tr>
																<td class="fc-day fc-widget-content fc-mon fc-past"
																	data-date="2016-03-21"></td>
																<td class="fc-day fc-widget-content fc-tue fc-past"
																	data-date="2016-03-22"></td>
																<td class="fc-day fc-widget-content fc-wed fc-past"
																	data-date="2016-03-23"></td>
																<td
																	class="fc-day fc-widget-content fc-thu fc-today fc-state-highlight"
																	data-date="2016-03-24"></td>
																<td class="fc-day fc-widget-content fc-fri fc-future"
																	data-date="2016-03-25"></td>
																<td class="fc-day fc-widget-content fc-sat fc-future"
																	data-date="2016-03-26"></td>
																<td class="fc-day fc-widget-content fc-sun fc-future"
																	data-date="2016-03-27"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="fc-content-skeleton">
													<table>
														<thead>
															<tr>
																<td class="fc-day-number fc-mon fc-past"
																	data-date="2016-03-21">21</td>
																<td class="fc-day-number fc-tue fc-past"
																	data-date="2016-03-22">22</td>
																<td class="fc-day-number fc-wed fc-past"
																	data-date="2016-03-23">23</td>
																<td
																	class="fc-day-number fc-thu fc-today fc-state-highlight"
																	data-date="2016-03-24">24</td>
																<td class="fc-day-number fc-fri fc-future"
																	data-date="2016-03-25">25</td>
																<td class="fc-day-number fc-sat fc-future"
																	data-date="2016-03-26">26</td>
																<td class="fc-day-number fc-sun fc-future"
																	data-date="2016-03-27">27</td>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class="fc-row fc-week fc-widget-content">
												<div class="fc-bg">
													<table>
														<tbody>
															<tr>
																<td class="fc-day fc-widget-content fc-mon fc-future"
																	data-date="2016-03-28"></td>
																<td class="fc-day fc-widget-content fc-tue fc-future"
																	data-date="2016-03-29"></td>
																<td class="fc-day fc-widget-content fc-wed fc-future"
																	data-date="2016-03-30"></td>
																<td class="fc-day fc-widget-content fc-thu fc-future"
																	data-date="2016-03-31"></td>
																<td
																	class="fc-day fc-widget-content fc-fri fc-other-month fc-future"
																	data-date="2016-04-01"></td>
																<td
																	class="fc-day fc-widget-content fc-sat fc-other-month fc-future"
																	data-date="2016-04-02"></td>
																<td
																	class="fc-day fc-widget-content fc-sun fc-other-month fc-future"
																	data-date="2016-04-03"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="fc-content-skeleton">
													<table>
														<thead>
															<tr>
																<td class="fc-day-number fc-mon fc-future"
																	data-date="2016-03-28">28</td>
																<td class="fc-day-number fc-tue fc-future"
																	data-date="2016-03-29">29</td>
																<td class="fc-day-number fc-wed fc-future"
																	data-date="2016-03-30">30</td>
																<td class="fc-day-number fc-thu fc-future"
																	data-date="2016-03-31">31</td>
																<td
																	class="fc-day-number fc-fri fc-other-month fc-future"
																	data-date="2016-04-01">1</td>
																<td
																	class="fc-day-number fc-sat fc-other-month fc-future"
																	data-date="2016-04-02">2</td>
																<td
																	class="fc-day-number fc-sun fc-other-month fc-future"
																	data-date="2016-04-03">3</td>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<div class="fc-row fc-week fc-widget-content">
												<div class="fc-bg">
													<table>
														<tbody>
															<tr>
																<td
																	class="fc-day fc-widget-content fc-mon fc-other-month fc-future"
																	data-date="2016-04-04"></td>
																<td
																	class="fc-day fc-widget-content fc-tue fc-other-month fc-future"
																	data-date="2016-04-05"></td>
																<td
																	class="fc-day fc-widget-content fc-wed fc-other-month fc-future"
																	data-date="2016-04-06"></td>
																<td
																	class="fc-day fc-widget-content fc-thu fc-other-month fc-future"
																	data-date="2016-04-07"></td>
																<td
																	class="fc-day fc-widget-content fc-fri fc-other-month fc-future"
																	data-date="2016-04-08"></td>
																<td
																	class="fc-day fc-widget-content fc-sat fc-other-month fc-future"
																	data-date="2016-04-09"></td>
																<td
																	class="fc-day fc-widget-content fc-sun fc-other-month fc-future"
																	data-date="2016-04-10"></td>
															</tr>
														</tbody>
													</table>
												</div>
												<div class="fc-content-skeleton">
													<table>
														<thead>
															<tr>
																<td
																	class="fc-day-number fc-mon fc-other-month fc-future"
																	data-date="2016-04-04">4</td>
																<td
																	class="fc-day-number fc-tue fc-other-month fc-future"
																	data-date="2016-04-05">5</td>
																<td
																	class="fc-day-number fc-wed fc-other-month fc-future"
																	data-date="2016-04-06">6</td>
																<td
																	class="fc-day-number fc-thu fc-other-month fc-future"
																	data-date="2016-04-07">7</td>
																<td
																	class="fc-day-number fc-fri fc-other-month fc-future"
																	data-date="2016-04-08">8</td>
																<td
																	class="fc-day-number fc-sat fc-other-month fc-future"
																	data-date="2016-04-09">9</td>
																<td
																	class="fc-day-number fc-sun fc-other-month fc-future"
																	data-date="2016-04-10">10</td>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
																<td></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</template>
<template id="echarts_line">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/echarts/img/echarts_line.png" />	
	</div>
</template>
<!-- 菜单组件 -->
<template id="megamenu">
	 <div class="page-header-mt navbar">
            <div class="page-header-inner ">      
                <!-- DOC: Remove "hor-menu-light" class to have a horizontal menu with theme background instead of white background -->
                <div class="hor-menu hidden-sm hidden-xs">
                    <ul class="nav navbar-nav">
                        <!-- DOC: Remove data-hover="megamenu-dropdown" and data-close-others="true" attributes below to disable the horizontal opening on mouse hover -->
                      
                        <li class="mega-menu-dropdown">
                            <a href="javascript:;" class="dropdown-toggle id frame-variable" frame-variable="fv1" data-hover="megamenu-dropdown" data-close-others="true" > Mega
                                
                            </a>
                            <ul class="dropdown-menu" style="min-width: 700px;">
                                <li>
                                    <!-- Content container to add padding -->
                                    <div class="mega-menu-content">
                                        <div class="mt-row">
                                            <div class="col-md-4">
                                                <ul class="mega-menu-submenu">
                                                    <li>
                                                        <h3>Section 1</h3>
                                                    </li>
                                                    <li>
                                                        <a href="#">Example Link</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Example Link</a>
                                                    </li>
                                                    
                                                </ul>
                                            </div>
                                            <div class="col-md-4">
                                                <ul class="mega-menu-submenu">
                                                    <li>
                                                        <h3>Section 2</h3>
                                                    </li>
                                                    <li>
                                                        <a href="#">Example Link</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Example Link</a>
                                                    </li>
                                                
                                                </ul>
                                            </div>
                                            <div class="col-md-4">
                                                <ul class="mega-menu-submenu">
                                                    <li>
                                                        <h3>Section 3</h3>
                                                    </li>
                                                    <li>
                                                        <a href="#">Example Link</a>
                                                    </li>
                                                    <li>
                                                        <a href="#">Example Link</a>
                                                    </li>
                                                    
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="mega-menu-dropdown mega-menu-full" data-hover="megamenu-dropdown" data-close-others="true">
                            <a href="javascript:;" class="dropdown-toggle id frame-variable" frame-variable="fv2" data-hover="megamenu-dropdown" data-close-others="true"> Full Mega
                               
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <!-- Content container to add padding -->
                                    <div class="mega-menu-content ">
                                        <div class="mt-row">
                                            <div class="col-md-8">
                                                <div class="mt-row">
                                                    <div class="col-md-3">
                                                        <ul class="mega-menu-submenu">
                                                            <li>
                                                                <h3>Section 1</h3>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                           
                                                        </ul>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <ul class="mega-menu-submenu">
                                                            <li>
                                                                <h3>Section 2</h3>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            
                                                        </ul>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <ul class="mega-menu-submenu">
                                                            <li>
                                                                <h3>Section 3</h3>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            
                                                        </ul>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <ul class="mega-menu-submenu">
                                                            <li>
                                                                <h3>Section 4</h3>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            <li>
                                                                <a href="#">Example Link</a>
                                                            </li>
                                                            
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li class="classic-menu-dropdown">
                            <a href="javascript:;" data-hover="megamenu-dropdown" data-close-others="true" class="id frame-variable" frame-variable="fv3"> Classic
                            
                            </a>
                            <ul class="dropdown-menu pull-left">
                                <li>
                                    <a href="javascript:;">
                                        <i class="fa fa-bookmark-o"></i> Section 1 </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        <i class="fa fa-user"></i> Section 2 </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        <i class="fa fa-puzzle-piece"></i> Section 3 </a>
                                </li>
                                <li class="dropdown-submenu">
                                    <a href="javascript:;">
                                        <i class="fa fa-envelope-o"></i> More options </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="javascript:;"> Second level link </a>
                                        </li>
                                        <li class="dropdown-submenu">
                                            <a href="javascript:;"> More options </a>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a href="index.html"> Third level link </a>
                                                </li>
                                                <li>
                                                    <a href="index.html"> Third level link </a>
                                                </li>
                                            </ul>
                                        </li>              
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>  		      
            </div>   
        </div>
</template>
<template id="btalert">
  <div class="alert alert-warning">
     <a href="#" class="close" data-dismiss="alert" aria-hidden="true">&times;</a>
     <strong class="id frame-variable" frame-variable="fv1">警告！</strong><span class="id frame-variable" frame-variable="fv2">这是一个警告框。</span>
  </div>
</template>
<template id="btmessenger">
  <div data-msg="This is my message..." data-theme="flat" x-position="center" y-position="top"><button type="button" class="btn btn-default checker">查看</button></div>
</template>
<template id="login_page">
<div class = "template1 login column ui-sortable containerComp">
    <div class="logo" data-role="login_logo" id="login_logo" cname="login_logo_控件" scope="bootstrap" crtltype="kendo">
        <a href="javascript:;">
            <img src="${contextPath}/scripts/designer/images/logo.png" alt="" /> </a>
    </div>
    <div class="content">
        <form class="login-form column ui-sortable containerComp" action="" method="post">
            <div class="title1" data-role="login_title" id="login_title" cname="login_title_控件" scope="bootstrap" crtltype="kendo">
				<h3 class="form-title font-green id frame-variable" frame-variable="fv1" >登 录</h3>
			</div>
            <div class="alert alert-danger display-hide" data-role="login_alert" id="login_alert" cname="login_alert_控件" scope="bootstrap" crtltype="kendo">
                <button class="close" data-close="alert"></button>
                <span> 请输入用户名和密码. </span>
            </div>
            <div class="form-group" data-role="login_username" id="login_username" cname="login_username_控件" scope="bootstrap" crtltype="kendo">
                <label class="control-label visible-ie8 visible-ie9">用户名</label>
                <input class="form-control form-control-solid placeholder-no-fix id frame-variable" frame-variable="username" type="text" autocomplete="off" placeholder="用户名" name="username" /> </div>
            <div class="form-group" data-role="login_password" id="login_password" cname="login_password_控件" scope="bootstrap" crtltype="kendo">
                <label class="control-label visible-ie8 visible-ie9">密码</label>
                <input class="form-control form-control-solid placeholder-no-fix id frame-variable" frame-variable="password" type="password" autocomplete="off" placeholder="密码" name="password" /> </div>
            <div class="form-actions" data-role="login_actions" id="login_actions" cname="login_actions_控件" scope="bootstrap" crtltype="kendo">
                <button type="button" class="btn green uppercase id frame-variable" frame-variable="loginBtn">登录</button>
            </div>
            <div class="create-account" data-role="login_register" id="login_register" cname="login_register_控件" scope="bootstrap" crtltype="kendo">
                <p style="margin: 0px 40px 0px 40px;">
                    <a href="javascript:;" id="register-btn" class="uppercase">创建用户</a>
                </p>
            </div>
        </form>
    </div>
    
    <div class="copyright" data-role="login_copyright"  id="login_copyright" cname="login_copyright_控件" scope="bootstrap" crtltype="kendo"> 
        <p class="id frame-variable" frame-variable="copyright">华闽通达信息技术有限公司版权所有 ? 1997- 2015 2015</p> 
    </div>
 </div>
</template>
<template id="login_title">
	<div class="title1">
       	<h3 class="form-title font-green id frame-variable" frame-variable="fv1" >登 录</h3>
   	</div>
</template>
<template id="login_username">
  <div class="form-group" data-role="login_username">
     <label class="control-label visible-ie8 visible-ie9">用户名</label>
     <input class="form-control form-control-solid placeholder-no-fix id frame-variable" frame-variable="username" type="text" autocomplete="off" placeholder="用户名" name="username" /> 
  </div>
</template>
<template id="login_password">
   <div class="form-group" data-role="login_password">
       <label class="control-label visible-ie8 visible-ie9">密码</label>
       <input class="form-control form-control-solid placeholder-no-fix id frame-variable" frame-variable="password" type="password" autocomplete="off" placeholder="密码" name="password" /> 
   </div>
</template>
<template id="login_actions">
<div class="form-actions">
	<button type="button" class="btn green uppercase id frame-variable" frame-variable="loginBtn">登录</button>
</div>
</template>
<template id="login_copyright">
<div class="copyright" > 
   <p class="id frame-variable" frame-variable="copyright">华闽通达信息技术有限公司版权所有 ? 1997- 2015 2015</p> 
</div>
</template>
<template id="login_register">
   <div class="create-account" data-role="login_register">
      <p style="margin: 0px 40px 0px 40px;">
          <a href="javascript:;" id="register-btn" class="uppercase id frame-variable" frame-variable="register">创建用户</a>
      </p>
   </div>
</template>
<template id="login_verify">
   <div class="form-group" data-role="login_verify" id="login_verify" cname="login_verify_控件" scope="bootstrap" crtltype="kendo">
		<div style = "float: left;">
	        <input class="form-control placeholder-no-fix id frame-variable" frame-variable="verification" type="text" name="verification" placeholder="请输入图中验证码" /> 
		</div>
		<div>
			<img id="captcha-img" src="${contextPath}/scripts/plugins/bootstrap/login/img/verification2.jpg" alt="点击可刷新图片验证码">
		</div>
	</div>
</template>
<template id="login_alert">
<div class="alert alert-danger">
    <button class="close" data-close="alert"></button>
    <span class="id frame-variable" frame-variable="loginAlert">请输入用户名和密码.</span>
</div>
</template>
<template id="login_logo">
<div class="logo">
    <a href="javascript:;" class="id frame-variable" frame-variable="logo">
        <img src="${contextPath}/scripts/designer/images/logo.png" alt="" /> </a>
</div>
</template>
<template id="login_form">
<form action="javascript:;" class="login-form column ui-sortable containerComp" method="post">
	
</form>
</template>
<template id="a">
	<a style="display: block;"><p style="display: inline;">A标签</p></a>
</template>
<template id="gantt">
  <div></div>
</template>

<!--表单-->
 <template id="formgrouptoolbarTemplate">
     		       <div class="btn-group btn-group-xs formgrouptoolbar" title="高级">		
								<a class="btn blue dragBtn-sm dropdown-toggle" aria-expanded="false"  data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">
									 <i class="icon-cursor-move"></i>
								</a>
								<ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;min-width:120px;">
									<li class="dropdown-submenu">
										<a class="insert-group-left" href="javascript:;"><i class="glyphicon glyphicon-chevron-left"></i><i class="glyphicon  glyphicon-plus "> 左侧插入</i> </a>
										    <ul class="dropdown-menu">
												<li>
													<a href="javascript:;" class="insert" type="prev" template="form_group"> 文本框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="select_form_group"> 下拉框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="dateRange_form_group"> 日期时间 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="bt_form_group"> 按钮</a>
												</li>
                        <li>
                          <a href="javascript:;" class="insert" type="prev" template="custom_form_group"> 自定义控件 </a>
                        </li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="blank_form_group"> 空白 </a>
												</li>
											</ul>
									</li>
									<li class="dropdown-submenu">
										<a class="insert-group-right" href="javascript:;"><i class="glyphicon glyphicon-plus "></i><i class="glyphicon glyphicon-chevron-right"> 右侧插入</i></a>
										  <ul class="dropdown-menu">
												<li>
													<a href="javascript:;" class="insert" type="after" template="form_group"> 文本框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="select_form_group"> 下拉框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="dateRange_form_group"> 日期时间 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="bt_form_group"> 按钮</a>
												</li>
                        <li>
                          <a href="javascript:;" class="insert" type="after" template="custom_form_group"> 自定义控件 </a>
                        </li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="blank_form_group"> 空白 </a>
												</li>
											</ul>
									</li>
									<li>
										<a class="move-left" href="javascript:;"><i class="glyphicon glyphicon-chevron-left">   向左移</i></a>
									</li>
									<li>
										<a class="move-right" href="javascript:;"><i class="glyphicon glyphicon-chevron-right">   向右移</i></a>
									</li>
									<li>
										<a class="delete" href="javascript:;"><i class="fa fa-remove">   删除</i></a>
									</li>
									<li>
										<a class="apply-template" href="javascript:;"><i class="glyphicon glyphicon-leaf">套用模板</i></a>
									</li>
								</ul>
			 </div>
 </template>
<!-- 表单控件 -->
<template id="form">
	<form role="form" class="form-inline myform">
		<div class="form-group myformgroup">
		      <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
		      <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	   </div> 
	   <div class="form-group myformgroup">
		      <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
		    <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	  </div>
      <div class="form-group myformgroup">
		       <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
		    <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	  </div> 	  
    </form>    
</template>

<template id="form_group">
	<div class="form-group myformgroup">
		    <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
		    <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	</div>   
</template>

<template id="select_form_group">
	<div class="form-group myformgroup">
		    <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
	<div class="input-group select2-bootstrap-prepend" data-role="metroselect" scope="bootstrap" crtltype="kendo">
        <span class="input-group-btn" style="display:none;">
            <button class="btn btn-default" type="button" data-select2-open="single">
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </span>
        <select id="single" class="form-control select2" data-placeholder="请选择..." data-allow-clear="true">
            <option></option>
            <optgroup label="Alaskan">
                <option value="AK">Alaska</option>
                <option value="HI" disabled="disabled">Hawaii</option>
            </optgroup>
            <optgroup label="Pacific Time Zone">
                <option value="CA">California</option>
                <option value="NV">Nevada</option>
                <option value="OR">Oregon</option>
                <option value="WA">Washington</option>
            </optgroup>
            <optgroup label="Mountain Time Zone">
                <option value="AZ">Arizona</option>
                <option value="CO">Colorado</option>
                <option value="ID">Idaho</option>
                <option value="MT">Montana</option>
                <option value="NE">Nebraska</option>
                <option value="NM">New Mexico</option>
                <option value="ND">North Dakota</option>
                <option value="UT">Utah</option>
                <option value="WY">Wyoming</option>
            </optgroup>
            <optgroup label="Central Time Zone">
                <option value="AL">Alabama</option>
                <option value="AR">Arkansas</option>
                <option value="IL">Illinois</option>
                <option value="IA">Iowa</option>
                <option value="KS">Kansas</option>
                <option value="KY">Kentucky</option>
                <option value="LA">Louisiana</option>
                <option value="MN">Minnesota</option>
                <option value="MS">Mississippi</option>
                <option value="MO">Missouri</option>
                <option value="OK">Oklahoma</option>
                <option value="SD">South Dakota</option>
                <option value="TX">Texas</option>
                <option value="TN">Tennessee</option>
                <option value="WI">Wisconsin</option>
            </optgroup>
            <optgroup label="Eastern Time Zone">
                <option value="CT">Connecticut</option>
                <option value="DE">Delaware</option>
                <option value="FL">Florida</option>
                <option value="GA">Georgia</option>
                <option value="IN">Indiana</option>
                <option value="ME">Maine</option>
                <option value="MD">Maryland</option>
                <option value="MA">Massachusetts</option>
                <option value="MI">Michigan</option>
                <option value="NH">New Hampshire</option>
                <option value="NJ">New Jersey</option>
                <option value="NY">New York</option>
                <option value="NC">North Carolina</option>
                <option value="OH">Ohio</option>
                <option value="PA">Pennsylvania</option>
                <option value="RI">Rhode Island</option>
                <option value="SC">South Carolina</option>
                <option value="VT">Vermont</option>
                <option value="VA">Virginia</option>
                <option value="WV">West Virginia</option>
            </optgroup>
        </select>
      </div>
	</div>   
</template>

<template id="dateRange_form_group">
<div class="form-group myformgroup" >
    <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
    <div class="input-group input-daterange id frame-variable" style="width:auto !important" data-role="daterangepicker" scope="bootstrap" crtltype="kendo">  
	<input type="text" class="form-control" name="from" frame-variable="fv1" data-role="datepickerbt" scope="bootstrap" crtltype="kendo"> 
	<span class="input-group-addon"> to </span> 
	<input type="text" class="form-control" name="to" frame-variable="fv2" data-role="datepickerbt" scope="bootstrap" crtltype="kendo">  
	</div>
</div>
</template>

<template id="bt_form_group">
	<div class="form-group myformgroup">
		<button type="button" class="btn btn-default" data-role="mtbutton" scope="bootstrap" crtltype="kendo"><span class="id frame-variable" frame-variable="fv1">默认按钮</span></button>
	</div>   
</template>
<template id="custom_form_group">
  <div class="form-group myformgroup">
  <img class="img-responsive  nlayout_elem" src="/glaf/images/component/custom.png" data-role="custom" crtltype="kendo" ></div>
  </div>   
</template>
<template id="blank_form_group">
  <div class="form-group myformgroup">
        <label data-role="label" scope="bootstrap" crtltype="kendo">标题</label>
  </div>   
</template>
<template id="officebt">
  <img src="${contextPath}/scripts/designer/images/office.png" />
</template>
<template id="carousel">
  <div>
  <div id="myCarousel" class="carousel slide">
   <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
   </ol>
   <div class="carousel-inner">
      <div class="item active" style="text-align:center;">
         <img src="${contextPath}/scripts/designer/images/404.png" alt="404" style="display:inline-block">
      </div>
      <div class="item" style="text-align:center;">
         <img src="${contextPath}/scripts/designer/images/404.png" alt="404" style="display:inline-block">
      </div>
      <div class="item" style="text-align:center;">
         <img src="${contextPath}/scripts/designer/images/404.png" alt="404" style="display:inline-block">
      </div>
   </div>
   <a class="carousel-control left" href="#myCarousel" 
      data-slide="prev"><i class="glyphicon glyphicon-chevron-left"></i></a>
   <a class="carousel-control right" href="#myCarousel" 
      data-slide="next"><i class="glyphicon glyphicon-chevron-right"></i></a>
  </div>
  </div>
</template>
<template id="charts_line">
<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="628" height="353">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-1">
				<rect x="0" y="0" width="558" height="206"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="628" height="353" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid">
			<path fill="none" d="M 60 266.5 L 618 266.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 225.5 L 618 225.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 184.5 L 618 184.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 142.5 L 618 142.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 101.5 L 618 101.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 59.5 L 618 59.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
		</g>
		<g class="highcharts-grid"></g>
		<g class="highcharts-axis">
			<path fill="none" d="M 199.5 266 L 199.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 338.5 266 L 338.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 478.5 266 L 478.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 618.5 266 L 618.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 59.5 266 L 59.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<text x="339" text-anchor="middle" transform="translate(0,0)" class=" highcharts-xaxis-title" style="color:#707070;fill:#707070;" y="309"></text>
			<path fill="none" d="M 60 266.5 L 618 266.5" stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="19.828125" text-anchor="middle" transform="translate(0,0) rotate(270 19.828125 163)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="163"></text>
		</g>
		<g class="highcharts-axis">
			<text x="9.828125" text-anchor="middle" transform="translate(0,0) rotate(270 9.828125 163)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="163"></text>
		</g>
		<g class="highcharts-series-group">
			<path fill="#f7a35c" fill-opacity="0.25" d="M 0 0"></path>
			<path fill="#7cb5ec" fill-opacity="0.25" d="M 0 0"></path>
			<g class="highcharts-series highcharts-series-0" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="none" d="M 69.75 144.2 L 209.25 65.91999999999999 L 348.75 119.48 L 488.25 86.52" stroke="#7cb5ec" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M 59.75 144.2 L 69.75 144.2 L 209.25 65.91999999999999 L 348.75 119.48 L 488.25 86.52 L 498.25 86.52" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-0 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#7cb5ec" d="M 488 82.52 C 493.328 82.52 493.328 90.52 488 90.52 C 482.672 90.52 482.672 82.52 488 82.52 Z" stroke-width="1"></path>
				<path fill="#7cb5ec" d="M 348 115.48 C 353.328 115.48 353.328 123.48 348 123.48 C 342.672 123.48 342.672 115.48 348 115.48 Z" stroke-width="1"></path>
				<path fill="#7cb5ec" d="M 209 61.91999999999999 C 214.328 61.91999999999999 214.328 69.91999999999999 209 69.91999999999999 C 203.672 69.91999999999999 203.672 61.91999999999999 209 61.91999999999999 Z"></path>
				<path fill="#7cb5ec" d="M 69 140.2 C 74.328 140.2 74.328 148.2 69 148.2 C 63.672 148.2 63.672 140.2 69 140.2 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-1" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="none" d="M 69.75 185.4 L 209.25 152.44 L 348.75 177.16 L 488.25 185.4" stroke="#434348" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M 59.75 185.4 L 69.75 185.4 L 209.25 152.44 L 348.75 177.16 L 488.25 185.4 L 498.25 185.4" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-1 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#434348" d="M 488 181.4 L 492 185.4 488 189.4 484 185.4 Z"></path>
				<path fill="#434348" d="M 348 173.16 L 352 177.16 348 181.16 344 177.16 Z"></path>
				<path fill="#434348" d="M 209 148.44 L 213 152.44 209 156.44 205 152.44 Z"></path>
				<path fill="#434348" d="M 69 181.4 L 73 185.4 69 189.4 65 185.4 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-2" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="none" d="M 69.75 103 L 209.25 45.31999999999999 L 348.75 32.96000000000001 L 488.25 74.16" stroke="#90ed7d" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M 59.75 103 L 69.75 103 L 209.25 45.31999999999999 L 348.75 32.96000000000001 L 488.25 74.16 L 498.25 74.16" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-2 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#90ed7d" d="M 484 70.16 L 492 70.16 492 78.16 484 78.16 Z"></path>
				<path fill="#90ed7d" d="M 344 28.960000000000008 L 352 28.960000000000008 352 36.96000000000001 344 36.96000000000001 Z"></path>
				<path fill="#90ed7d" d="M 205 41.31999999999999 L 213 41.31999999999999 213 49.31999999999999 205 49.31999999999999 Z"></path>
				<path fill="#90ed7d" d="M 65 99 L 73 99 73 107 65 107 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-3" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="none" d="M 69.75 160.68 L 209.25 94.75999999999999 L 348.75 148.32 L 488.25 140.07999999999998" stroke="#f7a35c" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M 59.75 160.68 L 69.75 160.68 L 209.25 94.75999999999999 L 348.75 148.32 L 488.25 140.07999999999998 L 498.25 140.07999999999998" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-3 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#f7a35c" d="M 488 136.07999999999998 L 492 144.07999999999998 484 144.07999999999998 Z" stroke-width="1"></path>
				<path fill="#f7a35c" d="M 348 144.32 L 352 152.32 344 152.32 Z"></path>
				<path fill="#f7a35c" d="M 209 90.75999999999999 L 213 98.75999999999999 205 98.75999999999999 Z"></path>
				<path fill="#f7a35c" d="M 69 156.68 L 73 164.68 65 164.68 Z"></path>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-0" visibility="visible" transform="translate(60,60) scale(1 1)" opacity="1">
			<g style="cursor:pointer;" transform="translate(57,116)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>15</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(197,38)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>34</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(336,91)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>21</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(476,59)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>29</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-1" visibility="visible" transform="translate(60,60) scale(1 1)" opacity="1">
			<g style="cursor:pointer;" transform="translate(61,157)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(197,124)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>13</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(340,149)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>7</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(479,157)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-2" visibility="visible" transform="translate(60,60) scale(1 1)" opacity="1">
			<g style="cursor:pointer;" transform="translate(57,75)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>25</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(197,17)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>39</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(336,5)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>42</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(476,46)" opacity="0" visibility="hidden">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>32</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-3" visibility="visible" transform="translate(60,60) scale(1 1)" opacity="1">
			<g style="cursor:pointer;" transform="translate(57,133)" opacity="0" visibility="hidden">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>11</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(197,67)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>27</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(336,120)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>14</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(476,112)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>16</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-legend" transform="translate(118,309)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(8,3)">
						<path fill="none" d="M 0 11 L 16 11" stroke="#7cb5ec" stroke-width="2"></path>
						<path fill="#7cb5ec" d="M 8 7 C 13.328 7 13.328 15 8 15 C 2.6719999999999997 15 2.6719999999999997 7 8 7 Z"></path>
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">二级公路</text>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,3)">
						<path fill="none" d="M 0 11 L 16 11" stroke="#434348" stroke-width="2"></path>
						<path fill="#434348" d="M 8 7 L 12 11 8 15 4 11 Z"></path>
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,3)">
						<path fill="none" d="M 0 11 L 16 11" stroke="#90ed7d" stroke-width="2"></path>
						<path fill="#90ed7d" d="M 4 7 L 12 7 12 15 4 15 Z"></path>
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,3)">
						<path fill="none" d="M 0 11 L 16 11" stroke="#f7a35c" stroke-width="2"></path>
						<path fill="#f7a35c" d="M 8 7 L 12 15 4 15 Z"></path>
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-xaxis-labels">
			<text x="129.75" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">待受监审核</text>
			<text x="269.25" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">受监</text>
			<text x="408.75" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">交工检测评定</text>
			<text x="548.25" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">竣工检测评定</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="268" opacity="1">0</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="227" opacity="1">10</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="186" opacity="1">20</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="144" opacity="1">30</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="103" opacity="1">40</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="62" opacity="1">50</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels"></g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(498,-9999)" opacity="0" visibility="visible">
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" stroke="#f7a35c" stroke-width="1"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20">
				<tspan style="font-size: 10px">竣工检测评定</tspan>
				<tspan style="fill:#f7a35c" x="8" dy="15">●</tspan>
				<tspan dx="0">一级公路:</tspan>
				<tspan style="font-weight:bold" dx="0">16</tspan>
			</text>
		</g>
		<text x="618" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="348"></text>
	</svg>
</div>
</template>

<template id="charts_histo">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="631" height="361">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-3">
				<rect x="0" y="0" width="571" height="292"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="631" height="361" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid">
			<path fill="none" d="M 50 302.5 L 621 302.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 185.5 L 621 185.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 68.5 L 621 68.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 244.5 L 621 244.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 127.5 L 621 127.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 9.5 L 621 9.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
		</g>
		<g class="highcharts-axis">
			<path fill="none" d="M 192.5 302 L 192.5 312" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 477.5 302 L 477.5 312" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 335.5 302 L 335.5 312" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 621.5 302 L 621.5 312" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 49.5 302 L 49.5 312" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<text x="335.5" text-anchor="middle" transform="translate(0,0)" class=" highcharts-xaxis-title" style="color:#707070;fill:#707070;" y="345"></text>
			<path fill="none" d="M 50 302.5 L 621 302.5" stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="9.828125" text-anchor="middle" transform="translate(0,0) rotate(270 9.828125 156)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="156"></text>
		</g>
		<g class="highcharts-series-group">
			<g class="highcharts-series highcharts-series-0 highcharts-tracker" transform="translate(50,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="30.5" y="263.5" width="18" height="29" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="172.5" y="216.5" width="18" height="76" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="315.5" y="251.5" width="18" height="41" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="458.5" y="263.5" width="18" height="29" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-0" transform="translate(50,10) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-1 highcharts-tracker" transform="translate(50,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="51.5" y="228.5" width="18" height="64" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="194.5" y="134.5" width="18" height="158" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="337.5" y="210.5" width="18" height="82" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="479.5" y="199.5" width="18" height="93" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-1" transform="translate(50,10) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-2 highcharts-tracker" transform="translate(50,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="73.5" y="204.5" width="18" height="88" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="215.5" y="93.5" width="18" height="199" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="358.5" y="169.5" width="18" height="123" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="501.5" y="123.5" width="18" height="169" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-2" transform="translate(50,10) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-3 highcharts-tracker" transform="translate(50,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="94.5" y="146.5" width="18" height="146" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="237.5" y="64.5" width="18" height="228" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="379.5" y="47.5" width="18" height="245" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="522.5" y="105.5" width="18" height="187" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-3" transform="translate(50,10) scale(1 1)" clip-path="none"></g>
		</g>
		<g class="highcharts-data-labels highcharts-series-0 highcharts-tracker" visibility="visible" transform="translate(50,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(31,236)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(169,189)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>13</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(316,224)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>7</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(459,236)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-1 highcharts-tracker" visibility="visible" transform="translate(50,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(48,201)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>11</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(191,107)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>27</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(334,183)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>14</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(476,172)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>16</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-2 highcharts-tracker" visibility="visible" transform="translate(50,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(70,177)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>15</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(212,66)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>34</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(355,142)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>21</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(498,96)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>29</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-3 highcharts-tracker" visibility="visible" transform="translate(50,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(91,119)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>25</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(234,37)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>39</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(376,20)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>42</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(519,78)" opacity="1">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>32</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-legend" transform="translate(127,333)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(0,-5)">
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#7cb5ec"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(89.9375,-5)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#434348"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(179.875,-5)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#90ed7d"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(269.8125,-5)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f7a35c"></rect>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-xaxis-labels">
			<text x="121.375" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:133px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="321" opacity="1">待受监审核</text>
			<text x="406.875" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:133px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="321" opacity="1">交工检测评定</text>
			<text x="264.125" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:133px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="321" opacity="1">受监</text>
			<text x="549.625" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:133px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="321" opacity="1">竣工检测评定</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="304" opacity="1">0</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="187" opacity="1">20</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="70" opacity="1">40</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="246" opacity="1">10</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="129" opacity="1">30</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="12" opacity="1">50</text>
		</g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(492,-9999)" opacity="0" visibility="visible">
			<path fill="none" d="M 3.5 0.5 L 134.5 0.5 C 137.5 0.5 137.5 0.5 137.5 3.5 L 137.5 46.5 C 137.5 49.5 137.5 49.5 134.5 49.5 L 95.5 49.5 89.5 55.5 83.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 134.5 0.5 C 137.5 0.5 137.5 0.5 137.5 3.5 L 137.5 46.5 C 137.5 49.5 137.5 49.5 134.5 49.5 L 95.5 49.5 89.5 55.5 83.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 134.5 0.5 C 137.5 0.5 137.5 0.5 137.5 3.5 L 137.5 46.5 C 137.5 49.5 137.5 49.5 134.5 49.5 L 95.5 49.5 89.5 55.5 83.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 134.5 0.5 C 137.5 0.5 137.5 0.5 137.5 3.5 L 137.5 46.5 C 137.5 49.5 137.5 49.5 134.5 49.5 L 95.5 49.5 89.5 55.5 83.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" stroke="#f7a35c" stroke-width="1"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20">
				<tspan style="font-size: 10px">竣工检测评定</tspan>
				<tspan style="fill:#f7a35c" x="8" dy="15">●</tspan>
				<tspan dx="0">三级及以下公路:</tspan>
				<tspan style="font-weight:bold" dx="0">32</tspan>
			</text>
		</g>
		<text x="621" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="356"></text>
	</svg>
	<span style="position: absolute; font-family: 'Lucida Grande', 'Lucida Sans Unicode', Arial, Helvetica, sans-serif; font-size: 18px; white-space: nowrap; color: rgb(51, 51, 51); margin-left: 0px; margin-top: 0px; left: 316px; top: 7px;" class="highcharts-title" transform="translate(0,0)"></span>
	<span style="position: absolute; font-family: 'Lucida Grande', 'Lucida Sans Unicode', Arial, Helvetica, sans-serif; font-size: 12px; white-space: nowrap; color: rgb(85, 85, 85); margin-left: 0px; margin-top: 0px; left: 316px; top: 12px;" class="highcharts-subtitle" transform="translate(0,0)"></span>
</div>
</template>
<template id="charts_pie">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="628" height="353">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-1">
				<rect x="0" y="0" width="608" height="201"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="628" height="353" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-series-group">
			<g class="highcharts-series highcharts-series-0 highcharts-tracker" transform="translate(10,60) scale(1 1)" style="cursor:pointer;">
				<path fill="#7cb5ec" d="M 303.9883906273894 44.00000118225907 A 57 57 0 0 1 319.7958898782238 46.232401340253865 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#434348" d="M 319.8506495698113 46.248224611296486 A 57 57 0 0 1 349.2730856018044 66.3683999778859 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#90ed7d" d="M 349.30769455951366 66.41369037174076 A 57 57 0 0 1 359.09885480057073 86.40150008851523 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#f7a35c" d="M 359.1134257486241 86.45660623338202 A 57 57 0 0 1 358.70071420147184 117.02597472383175 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#8085e9" d="M 358.6846608790642 117.08066741592975 A 57 57 0 0 1 356.955000224154 122.08952230990197 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#f15c80" d="M 356.9338842278611 122.14246675654002 A 57 57 0 0 1 350.29150640668047 134.25802812254906 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#e4d354" d="M 350.2582252383496 134.3043029922278 A 57 57 0 0 1 345.55051865327914 140.01992311170667 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#2b908f" d="M 345.51147796141316 140.06145411347492 A 57 57 0 0 1 341.7046099864791 143.74765941858686 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#f45b5b" d="M 341.6618434818817 143.78534264846132 A 57 57 0 0 1 318.31158712087455 156.1740742929287 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#91e8e1" d="M 318.2564058999844 156.18835829062948 A 57 57 0 0 1 277.8200683963813 151.63211610460152 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#7cb5ec" d="M 277.7694493786801 151.6059108613053 A 57 57 0 0 1 249.64087753154277 118.14893012579117 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#434348" d="M 249.6237557838341 118.09456243791821 A 57 57 0 0 1 249.45246590923998 84.46015342823964 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#90ed7d" d="M 249.46903302681986 84.40561417316273 A 57 57 0 0 1 253.98892978157102 73.65237020128866 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#f7a35c" d="M 254.0163024123448 73.60237281321918 A 57 57 0 0 1 273.558895039406 52.80934604326167 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#8085e9" d="M 273.6071009058822 52.77892903869955 A 57 57 0 0 1 287.0797287058146 46.56926953153057 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
				<path fill="#f15c80" d="M 287.1341678873463 46.55237647841939 A 57 57 0 0 1 303.92082814352995 44.00005498408667 L 304 101 A 0 0 0 0 0 304 101 Z" stroke="#FFFFFF" stroke-width="1" stroke-linejoin="round" transform="translate(0,0)"></path>
			</g>
			<g class="highcharts-markers highcharts-series-0" transform="translate(10,60) scale(1 1)"></g>
		</g>
		<g class="highcharts-data-labels highcharts-series-0 highcharts-tracker" visibility="visible" transform="translate(10,60) scale(1 1)" opacity="1" style="cursor:pointer;">
			<path fill="none" d="M 321.1978238885241 14 C 316.1978238885241 14 315.07618491026903 22.780321351762574 313.5339313151683 33.671669011643736 L 311.9916777200675 44.5630166715249" stroke="#7cb5ec" stroke-width="1"></path>
			<path fill="none" d="M 372.9374694525831 42 C 367.9374694525831 42 348.61880812438136 35.80673377134997 342.4060626893409 44.88427717027592 L 336.19331725430044 53.961820569201876" stroke="#434348" stroke-width="1"></path>
			<path fill="none" d="M 390.2896057316063 70 C 385.2896057316063 70 374.9686057108067 66.29471216854606 365.0869011181627 71.12709401849534 L 355.2051965255187 75.95947586844463" stroke="#90ed7d" stroke-width="1"></path>
			<path fill="none" d="M 395.9482604771366 98 C 390.9482604771366 98 382.99218403540067 102.1112431405401 371.9932723342689 101.95651308299654 L 360.99436063313715 101.80178302545298" stroke="#f7a35c" stroke-width="1"></path>
			<path fill="none" d="M 392.33066662399864 126 C 387.33066662399864 126 373.263888151692 138.99360206811016 363.6195492951273 133.70335367887964 L 353.97521043856256 128.4131052896491" stroke="#f15c80" stroke-width="1"></path>
			<path fill="none" d="M 377.99275324264136 154 C 372.99275324264136 154 364.96488657860436 151.24223924605568 356.4761049031025 144.2464844143264 L 347.98732322760065 137.25072958259713" stroke="#e4d354" stroke-width="1"></path>
			<path fill="none" d="M 340.7490157327751 182 C 335.7490157327751 182 341.0155517232435 170.79146746289183 335.86148755924756 161.0736681959069 L 330.7074233952516 151.35586892892195" stroke="#f45b5b" stroke-width="1"></path>
			<path fill="none" d="M 289.23013590296046 187.44969494177164 C 294.23013590296046 187.44969494177164 295.12851421073424 179.50029770574668 296.36378438392313 168.56987650621232 L 297.599054557112 157.63945530667797" stroke="#91e8e1" stroke-width="1"></path>
			<path fill="none" d="M 230.00724675735864 154 C 235.00724675735864 154 243.5089533863984 151.81174352048038 251.9317573452543 144.73669062522362 L 260.35456130411023 137.66163772996686" stroke="#7cb5ec" stroke-width="1"></path>
			<path fill="none" d="M 215.66933337600136 126 C 220.66933337600136 126 225.0008684532403 101.37042523909082 236.00074752937138 101.31884704124273 L 247.00062660550248 101.26726884339465" stroke="#434348" stroke-width="1"></path>
			<path fill="none" d="M 212.0517395228634 98 C 217.0517395228634 98 231.1919647333903 70.34074363889232 241.32979242874103 74.60975401828706 L 251.46762012409175 78.8787643976818" stroke="#90ed7d" stroke-width="1"></path>
			<path fill="none" d="M 217.7103942683937 70 C 222.7103942683937 70 246.4622186402404 46.8667965459599 254.47380844982717 54.40433120411738 L 262.48539825941396 61.94186586227487" stroke="#f7a35c" stroke-width="1"></path>
			<path fill="none" d="M 235.06253054741688 42 C 240.06253054741688 42 270.9679364613066 29.23731625436715 275.5673377135297 39.22958867464514 L 280.1667389657528 49.221861094923135" stroke="#8085e9" stroke-width="1"></path>
			<path fill="none" d="M 285.99490682082285 14 C 290.99490682082285 14 292.19077745798853 22.88763053809437 293.8350995840914 33.764036412536925 L 295.47942171019423 44.64044228697948" stroke="#f15c80" stroke-width="1"></path>
			<g style="cursor:pointer;" transform="translate(326,4)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>二级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(378,32)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>二级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(395,60)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>二级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(401,88)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>二级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(0,-9999)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>高速公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(397,116)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>高速公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(383,144)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>高速公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(0,-9999)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>高速公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(346,172)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>三级及以下公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(189,177)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>三级及以下公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(129,144)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>三级及以下公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(115,116)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>三级及以下公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(148,88)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>一级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(154,60)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>一级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(171,32)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>一级公路</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(222,4)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>一级公路</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-legend" transform="translate(36,273)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(8,3)">
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#7cb5ec"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#434348"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#90ed7d"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(277.8125,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f7a35c"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(367.75,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#8085e9"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(457.6875,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f15c80"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(8,21)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#e4d354"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,21)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#2b908f"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,21)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f45b5b"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,21)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#91e8e1"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(441.15625,21)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#7cb5ec"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(8,39)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#434348"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(134.640625,39)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#90ed7d"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(224.578125,39)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f7a35c"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,39)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#8085e9"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(404.453125,39)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f15c80"></rect>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(0,-9999)">
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20"></text>
		</g>
		<text x="618" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="348"></text>
	</svg>
</div>
</template>
<template id="charts_dial">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="628" height="353">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-1">
				<rect x="0" y="0" width="608" height="278"></rect>
			</clipPath>
			<linearGradient x1="0" y1="0" x2="0" y2="1" id="highcharts-2">
				<stop offset="0" stop-color="#FFF" stop-opacity="1"></stop>
				<stop offset="1" stop-color="#DDD" stop-opacity="1"></stop>
			</linearGradient>
		</defs>
		<rect x="0" y="0" width="628" height="353" fill="#FFFFFF" class=" highcharts-background"></rect>
		<path fill="url(#highcharts-2)" d="M 314 74.9425 A 124.0575 124.0575 0 1 1 313.8759425206762 74.94256202874483 M 314 199 A 0 0 0 1 0 314 199 " stroke="silver" stroke-width="1"></path>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid"></g>
		<g class="highcharts-axis">
			<path fill="none" d="M 321.27342105972537 81.07409170971823 L 320.6578119983859 91.05512499708867" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 339.2761937808477 83.58537125670317 L 337.1368629487827 93.35385443429918" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 356.68070286001216 88.82840609587535 L 353.0682861981406 98.15312838991892" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 373.07500000000005 96.67909854286857 L 368.07500000000005 105.33935258071295" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 388.0710476663887 106.95163011979204 L 381.80180960744764 114.74243586504875" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 401.3139035696209 119.40285970315928 L 393.9238143974143 126.13981613962484" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 412.4901218138753 133.73807844471625 L 404.1540979616641 139.26172817432132" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 421.33517186547675 149.61798525162655 L 412.25051914728147 153.79758870049437" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 427.6396997408686 166.66671772298344 L 418.0214433091404 169.40334762370426" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 431.2544832177392 184.48074845796404 L 421.3302781210199 185.70963136461117" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 432.0939637841342 202.63843616841154 L 422.0987065870005 202.33048558284983" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 430.138271727653 220.71000553002781 L 420.308540730814 218.8725103518621" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 425.4336964327814 238.2677195585218 L 416.00215208806867 234.9441715637252" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 418.0915907548654 254.89600374017462 L 409.2814688120076 250.16506817181454" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 408.2857354031603 270.2012822882092 L 400.3055631303579 264.1749359244166" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 396.24822571551977 283.82129371006386 L 389.28688625589047 276.6421744794194" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 382.26397818088486 295.43366519488757 L 376.48623986680235 287.2716960713254" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 366.663986734998 304.7635428736006 L 362.20660317723264 295.81190996005" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 349.8174884464561 311.59009735046857 L 346.7859617053257 302.060677346197" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 332.1232240239167 315.75175052640077 L 330.58930747512983 305.8700958055882" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 314 317.15 L 314 307.15" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 295.8767759760833 315.75175052640077 L 297.41069252487017 305.8700958055882" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 278.1825115535439 311.59009735046857 L 281.21403829467437 302.060677346197" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 261.336013265002 304.7635428736006 L 265.79339682276736 295.81190996005" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 245.73602181911514 295.43366519488757 L 251.51376013319765 287.2716960713254" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 231.75177428448023 283.82129371006386 L 238.71311374410948 276.6421744794194" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 219.7142645968397 270.20128228820914 L 227.6944368696421 264.17493592441656" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 209.90840924513458 254.89600374017465 L 218.71853118799243 250.16506817181454" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 202.56630356721857 238.26771955852178 L 211.99784791193133 234.9441715637252" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 197.86172827234702 220.71000553002784 L 207.69145926918603 218.87251035186213" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 195.9060362158658 202.63843616841154 L 205.90129341299948 202.33048558284983" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 196.74551678226078 184.48074845796407 L 206.6697218789801 185.70963136461123" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 200.3603002591314 166.6667177229834 L 209.97855669085962 169.40334762370424" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 206.66482813452325 149.61798525162658 L 215.74948085271848 153.79758870049443" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 215.50987818612472 133.73807844471622 L 223.8459020383359 139.2617281743213" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 226.68609643037908 119.40285970315931 L 234.07618560258567 126.13981613962487" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 239.92895233361133 106.95163011979204 L 246.19819039255236 114.74243586504875" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 254.92499999999995 96.6790985428686 L 259.92499999999995 105.33935258071298" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 271.3192971399879 88.82840609587535 L 274.9317138018594 98.15312838991892" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 288.72380621915227 83.58537125670318 L 290.8631370512173 93.3538544342992" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 306.72657894027463 81.07409170971823 L 307.3421880016141 91.05512499708867" stroke="#A0A0A0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 321.27342105972537 81.07409170971823 L 320.6578119983859 91.05512499708867" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 401.3139035696209 119.40285970315928 L 393.9238143974143 126.13981613962484" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 432.0939637841342 202.63843616841154 L 422.0987065870005 202.33048558284983" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 396.24822571551977 283.82129371006386 L 389.28688625589047 276.6421744794194" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 314 317.15 L 314 307.15" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 231.75177428448023 283.82129371006386 L 238.71311374410948 276.6421744794194" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 195.9060362158658 202.63843616841154 L 205.90129341299948 202.33048558284983" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 226.68609643037908 119.40285970315931 L 234.07618560258567 126.13981613962487" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<path fill="none" d="M 306.72657894027463 81.07409170971823 L 307.3421880016141 91.05512499708867" stroke="#C0D0E0" stroke-width="2" opacity="1"></path>
			<text x="314" text-anchor="middle" transform="translate(0,0)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="139.925"></text>
			<path fill="none" d="M 314 80.85 A 118.15 118.15 0 1 1 313.8818500196916 80.85005907499507 M 314 199 A 0 0 0 1 0 314 199 " stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="314" text-anchor="middle" transform="translate(0,0)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="139.925"></text>
			<path fill="none" d="M 314 80.85 A 118.15 118.15 0 1 1 313.8818500196916 80.85005907499507 M 314 199 A 0 0 0 1 0 314 199 " stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-data-labels highcharts-series-0 highcharts-tracker" visibility="visible" transform="translate(10,60) scale(1 1)" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>15</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>34</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>21</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>29</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-1 highcharts-tracker" visibility="visible" transform="translate(10,60) scale(1 1)" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(295,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="18" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>13</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(295,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="18" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>7</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(295,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="18" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-2 highcharts-tracker" visibility="visible" transform="translate(10,60) scale(1 1)" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(291,154)">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>25</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>39</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>42</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>32</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-3 highcharts-tracker" visibility="visible" transform="translate(10,60) scale(1 1)" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>11</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>27</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>14</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(291,154)" opacity="0" visibility="hidden">
				<rect x="0.5" y="0.5" width="25" height="28" fill="none" stroke="silver" stroke-width="1" rx="3" ry="3"></rect>
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>16</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-series-group">
			<g class="highcharts-series highcharts-series-0 highcharts-tracker" transform="translate(10,60) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-1)">
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(90 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(425.2941176470588 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(195.88235294117646 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(337.05882352941177 0 0)"></path>
				<circle cx="0" cy="0" r="5" fill="black" transform="translate(304,139)"></circle>
			</g>
			<g class="highcharts-markers highcharts-series-0" transform="translate(10,60) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-1 highcharts-tracker" transform="translate(10,60) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-1)">
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(-86.47058823529412 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(54.705882352941174 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(-51.17647058823529 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(-86.47058823529412 0 0)"></path>
				<circle cx="0" cy="0" r="5" fill="black" transform="translate(304,139)"></circle>
			</g>
			<g class="highcharts-markers highcharts-series-1" transform="translate(10,60) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-2 highcharts-tracker" transform="translate(10,60) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-1)">
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(266.4705882352941 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(513.5294117647061 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(566.4705882352943 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(390 0 0)"></path>
				<circle cx="0" cy="0" r="5" fill="black" transform="translate(304,139)"></circle>
			</g>
			<g class="highcharts-markers highcharts-series-2" transform="translate(10,60) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-3 highcharts-tracker" transform="translate(10,60) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-1)">
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(19.411764705882362 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(301.764705882353 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(72.35294117647058 0 0)"></path>
				<path fill="black" d="M -9.452 -1.5 L 66.164 -1.5 94.52 -0.5 94.52 0.5 66.164 1.5 -9.452 1.5 z" transform="translate(304,139) rotate(107.6470588235294 0 0)"></path>
				<circle cx="0" cy="0" r="5" fill="black" transform="translate(304,139)"></circle>
			</g>
			<g class="highcharts-markers highcharts-series-3" transform="translate(10,60) scale(1 1)" clip-path="none"></g>
		</g>
		<g class="highcharts-legend">
			<g>
				<g></g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="319.7343984063768" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="108.02667492814433" opacity="1">5</text>
			<text x="382.8386806391044" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="138.2452507943232" opacity="1">7.5</text>
			<text x="407.10582079130006" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="203.86855970450728" opacity="1">10</text>
			<text x="378.8448770664466" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="267.87349563345276" opacity="1">12.5</text>
			<text x="314" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="294.15" opacity="1">15</text>
			<text x="249.1551229335534" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="267.87349563345276" opacity="1">17.5</text>
			<text x="220.89417920869997" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="203.86855970450728" opacity="1">20</text>
			<text x="245.16131936089556" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="138.24525079432323" opacity="1">22.5</text>
			<text x="308.2656015936232" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="108.02667492814433" opacity="1">25</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels"></g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(264,-9999)" opacity="0" visibility="visible">
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 31.5 C 101.5 34.5 101.5 34.5 98.5 34.5 L 55.5 34.5 49.5 40.5 43.5 34.5 3.5 34.5 C 0.5 34.5 0.5 34.5 0.5 31.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 31.5 C 101.5 34.5 101.5 34.5 98.5 34.5 L 55.5 34.5 49.5 40.5 43.5 34.5 3.5 34.5 C 0.5 34.5 0.5 34.5 0.5 31.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 31.5 C 101.5 34.5 101.5 34.5 98.5 34.5 L 55.5 34.5 49.5 40.5 43.5 34.5 3.5 34.5 C 0.5 34.5 0.5 34.5 0.5 31.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 31.5 C 101.5 34.5 101.5 34.5 98.5 34.5 L 55.5 34.5 49.5 40.5 43.5 34.5 3.5 34.5 C 0.5 34.5 0.5 34.5 0.5 31.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" stroke="#7cb5ec" stroke-width="1"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20">
				<tspan style="fill:#7cb5ec">●</tspan>
				<tspan dx="0">二级公路:</tspan>
				<tspan style="font-weight:bold" dx="0">29</tspan>
			</text>
		</g>
		<text x="618" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="348"></text>
	</svg>

</div>
</template>
<template id="charts_bar">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="628" height="353">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-1">
				<rect x="0" y="0" width="201" height="511"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="628" height="353" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid">
			<path fill="none" d="M 106.5 60 L 106.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 163.5 60 L 163.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 220.5 60 L 220.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 276.5 60 L 276.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 333.5 60 L 333.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 390.5 60 L 390.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 447.5 60 L 447.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 503.5 60 L 503.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 560.5 60 L 560.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 618.5 60 L 618.5 261" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
		</g>
		<g class="highcharts-grid"></g>
		<g class="highcharts-axis">
			<path fill="none" d="M 107 110.5 L 97 110.5" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 107 161.5 L 97 161.5" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 107 211.5 L 97 211.5" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 107 261.5 L 97 261.5" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 107 59.5 L 97 59.5" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<text x="10" text-anchor="middle" transform="translate(0,0) rotate(270 10 160.5)" class=" highcharts-xaxis-title" style="color:#707070;fill:#707070;" y="160.5"></text>
			<path fill="none" d="M 106.5 60 L 106.5 261" stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="362.5" text-anchor="middle" transform="translate(0,0)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="304"></text>
		</g>
		<g class="highcharts-axis">
			<text x="362.5" text-anchor="middle" transform="translate(0,0)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="309"></text>
		</g>
		<g class="highcharts-series-group">
			<g class="highcharts-series highcharts-series-0 highcharts-tracker" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" style="cursor:pointer;" width="511" height="201" clip-path="url(#highcharts-1)">
				<rect x="183.5" y="341.5" width="7" height="170" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="133.5" y="125.5" width="7" height="386" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="83.5" y="273.5" width="7" height="238" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="32.5" y="182.5" width="7" height="329" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-0" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" width="511" height="201" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-1 highcharts-tracker" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" style="cursor:pointer;" width="511" height="201" clip-path="url(#highcharts-1)">
				<rect x="176.5" y="454.5" width="7" height="57" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="125.5" y="363.5" width="7" height="148" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="75.5" y="432.5" width="7" height="79" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="25.5" y="454.5" width="7" height="57" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-1" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" width="511" height="201" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-2 highcharts-tracker" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" style="cursor:pointer;" width="511" height="201" clip-path="url(#highcharts-1)">
				<rect x="168.5" y="227.5" width="7" height="284" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="118.5" y="68.5" width="7" height="443" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="68.5" y="34.5" width="7" height="477" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="17.5" y="148.5" width="7" height="363" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-2" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" width="511" height="201" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-3 highcharts-tracker" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" style="cursor:pointer;" width="511" height="201" clip-path="url(#highcharts-1)">
				<rect x="161.5" y="386.5" width="7" height="125" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="110.5" y="204.5" width="7" height="307" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="60.5" y="352.5" width="7" height="159" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="10.5" y="329.5" width="7" height="182" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-3" transform="translate(618,261) rotate(90) scale(-1,1) scale(1 1)" width="511" height="201" clip-path="none"></g>
		</g>
		<g class="highcharts-legend" transform="translate(118,309)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(8,3)">
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#7cb5ec"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#434348"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#90ed7d"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f7a35c"></rect>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-xaxis-labels">
			<text x="92" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="87" opacity="1">待受监审核</text>
			<text x="92" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="137" opacity="1">受监</text>
			<text x="92" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="188" opacity="1">交工检测评定</text>
			<text x="92" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="238" opacity="1">竣工检测评定</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="107" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">0</text>
			<text x="163.77777777777777" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">5</text>
			<text x="220.55555555555554" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">10</text>
			<text x="277.33333333333337" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">15</text>
			<text x="334.1111111111111" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">20</text>
			<text x="390.8888888888889" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">25</text>
			<text x="447.6666666666667" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">30</text>
			<text x="504.44444444444446" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">35</text>
			<text x="561.2222222222222" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">40</text>
			<text x="610.4140625" style="color:#606060;cursor:default;font-size:11px;fill:#606060;" text-anchor="middle" transform="translate(0,0)" y="280" opacity="1">45</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels"></g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(0,-9999)">
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20"></text>
		</g>
		<text x="618" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="348"></text>
	</svg>

</div>
</template>
<template id="charts_scatter">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="628" height="353">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-1">
				<rect x="0" y="0" width="558" height="206"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="628" height="353" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid">
			<path fill="none" d="M 60 266.5 L 618 266.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 225.5 L 618 225.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 184.5 L 618 184.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 142.5 L 618 142.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 101.5 L 618 101.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 60 59.5 L 618 59.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
		</g>
		<g class="highcharts-grid"></g>
		<g class="highcharts-axis">
			<path fill="none" d="M 199.5 266 L 199.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 338.5 266 L 338.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 478.5 266 L 478.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 618.5 266 L 618.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 59.5 266 L 59.5 276" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<text x="339" text-anchor="middle" transform="translate(0,0)" class=" highcharts-xaxis-title" style="color:#707070;fill:#707070;" y="309"></text>
			<path fill="none" d="M 60 266.5 L 618 266.5" stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="19.828125" text-anchor="middle" transform="translate(0,0) rotate(270 19.828125 163)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="163"></text>
		</g>
		<g class="highcharts-axis">
			<text x="9.828125" text-anchor="middle" transform="translate(0,0) rotate(270 9.828125 163)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="163"></text>
		</g>
		<g class="highcharts-series-group">
			<g class="highcharts-series highcharts-series-0 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)" style="cursor:pointer;"></g>
			<g class="highcharts-markers highcharts-series-0 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#7cb5ec" d="M 488 82.52 C 493.328 82.52 493.328 90.52 488 90.52 C 482.672 90.52 482.672 82.52 488 82.52 Z"></path>
				<path fill="#7cb5ec" d="M 348 115.48 C 353.328 115.48 353.328 123.48 348 123.48 C 342.672 123.48 342.672 115.48 348 115.48 Z"></path>
				<path fill="#7cb5ec" d="M 209 61.91999999999999 C 214.328 61.91999999999999 214.328 69.91999999999999 209 69.91999999999999 C 203.672 69.91999999999999 203.672 61.91999999999999 209 61.91999999999999 Z"></path>
				<path fill="#7cb5ec" d="M 69 140.2 C 74.328 140.2 74.328 148.2 69 148.2 C 63.672 148.2 63.672 140.2 69 140.2 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-1 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)" style="cursor:pointer;"></g>
			<g class="highcharts-markers highcharts-series-1 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#434348" d="M 488 181.4 L 492 185.4 488 189.4 484 185.4 Z"></path>
				<path fill="#434348" d="M 348 173.16 L 352 177.16 348 181.16 344 177.16 Z"></path>
				<path fill="#434348" d="M 209 148.44 L 213 152.44 209 156.44 205 152.44 Z"></path>
				<path fill="#434348" d="M 69 181.4 L 73 185.4 69 189.4 65 185.4 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-2 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)" style="cursor:pointer;"></g>
			<g class="highcharts-markers highcharts-series-2 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#90ed7d" d="M 484 70.16 L 492 70.16 492 78.16 484 78.16 Z"></path>
				<path fill="#90ed7d" d="M 344 28.960000000000008 L 352 28.960000000000008 352 36.96000000000001 344 36.96000000000001 Z"></path>
				<path fill="#90ed7d" d="M 205 41.31999999999999 L 213 41.31999999999999 213 49.31999999999999 205 49.31999999999999 Z"></path>
				<path fill="#90ed7d" d="M 65 99 L 73 99 73 107 65 107 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-3 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-1)" style="cursor:pointer;"></g>
			<g class="highcharts-markers highcharts-series-3 highcharts-tracker" transform="translate(60,60) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#f7a35c" d="M 488 136.07999999999998 L 492 144.07999999999998 484 144.07999999999998 Z"></path>
				<path fill="#f7a35c" d="M 348 144.32 L 352 152.32 344 152.32 Z"></path>
				<path fill="#f7a35c" d="M 209 90.75999999999999 L 213 98.75999999999999 205 98.75999999999999 Z"></path>
				<path fill="#f7a35c" d="M 69 156.68 L 73 164.68 65 164.68 Z"></path>
			</g>
		</g>
		<g class="highcharts-legend" transform="translate(118,309)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(8,3)">
						<path fill="#7cb5ec" d="M 8 7 C 13.328 7 13.328 15 8 15 C 2.6719999999999997 15 2.6719999999999997 7 8 7 Z"></path>
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">二级公路</text>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,3)">
						<path fill="#434348" d="M 8 7 L 12 11 8 15 4 11 Z"></path>
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,3)">
						<path fill="#90ed7d" d="M 4 7 L 12 7 12 15 4 15 Z"></path>
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,3)">
						<path fill="#f7a35c" d="M 8 7 L 12 15 4 15 Z"></path>
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-xaxis-labels">
			<text x="129.75" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">待受监审核</text>
			<text x="269.25" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">受监</text>
			<text x="408.75" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">交工检测评定</text>
			<text x="548.25" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:142px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="285" opacity="1">竣工检测评定</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="268" opacity="1">0</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="227" opacity="1">10</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="186" opacity="1">20</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="144" opacity="1">30</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="103" opacity="1">40</text>
			<text x="45" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:197px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="62" opacity="1">50</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels"></g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(0,-9999)">
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20"></text>
		</g>
		<text x="618" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="348"></text>
	</svg>
</div>
</template>
<template id="charts_area">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:&quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="630" height="350">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-1">
				<rect x="0" y="0" width="570" height="253"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="630" height="350" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid">
			<path fill="none" d="M 50 263.5 L 620 263.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 212.5 L 620 212.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 162.5 L 620 162.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 111.5 L 620 111.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 61.5 L 620 61.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 50 10.5 L 620 10.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
		</g>
		<g class="highcharts-axis">
			<path fill="none" d="M 55.5 263 L 55.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 148.5 263 L 148.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 241.5 263 L 241.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 334.5 263 L 334.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 427.5 263 L 427.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 520.5 263 L 520.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 613.5 263 L 613.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<text x="335" text-anchor="middle" transform="translate(0,0)" class=" highcharts-xaxis-title" style="color:#707070;fill:#707070;" y="306"></text>
			<path fill="none" d="M 50 263.5 L 620 263.5" stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="9.828125" text-anchor="middle" transform="translate(0,0) rotate(270 9.828125 136.5)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="136.5"></text>
		</g>
		<g class="highcharts-series-group">
			<path fill="#90ed7d" fill-opacity="0.25" d="M 0 0"></path>
			<path fill="#434348" fill-opacity="0.25" d="M 0 0"></path>
			<path fill="#f7a35c" fill-opacity="0.25" d="M 0 0"></path>
			<g class="highcharts-series highcharts-series-0" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="#7cb5ec" d="M 5.5882352941176 177.10000000000002 L 191.86274509804 80.96000000000001 L 378.13725490196 146.74 L 564.41176470588 106.26000000000002 L 564.41176470588 253 L 378.13725490196 253 L 191.86274509804 253 L 5.5882352941176 253" fill-opacity="0.75"></path>
				<path fill="none" d="M 5.5882352941176 177.10000000000002 L 191.86274509804 80.96000000000001 L 378.13725490196 146.74 L 564.41176470588 106.26000000000002" stroke="#7cb5ec" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M -4.4117647058824 177.10000000000002 L 5.5882352941176 177.10000000000002 L 191.86274509804 80.96000000000001 L 378.13725490196 146.74 L 564.41176470588 106.26000000000002 L 574.41176470588 106.26000000000002" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-0 highcharts-tracker" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#7cb5ec" d="M 564 102.26000000000002 C 569.328 102.26000000000002 569.328 110.26000000000002 564 110.26000000000002 C 558.672 110.26000000000002 558.672 102.26000000000002 564 102.26000000000002 Z"></path>
				<path fill="#7cb5ec" d="M 378 142.74 C 383.328 142.74 383.328 150.74 378 150.74 C 372.672 150.74 372.672 142.74 378 142.74 Z"></path>
				<path fill="#7cb5ec" d="M 191 76.96000000000001 C 196.328 76.96000000000001 196.328 84.96000000000001 191 84.96000000000001 C 185.672 84.96000000000001 185.672 76.96000000000001 191 76.96000000000001 Z"></path>
				<path fill="#7cb5ec" d="M 5 173.10000000000002 C 10.328 173.10000000000002 10.328 181.10000000000002 5 181.10000000000002 C -0.32800000000000007 181.10000000000002 -0.32800000000000007 173.10000000000002 5 173.10000000000002 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-1" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="#434348" d="M 5.5882352941176 227.7 L 191.86274509804 187.22 L 378.13725490196 217.58 L 564.41176470588 227.7 L 564.41176470588 253 L 378.13725490196 253 L 191.86274509804 253 L 5.5882352941176 253" fill-opacity="0.75"></path>
				<path fill="none" d="M 5.5882352941176 227.7 L 191.86274509804 187.22 L 378.13725490196 217.58 L 564.41176470588 227.7" stroke="#434348" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M -4.4117647058824 227.7 L 5.5882352941176 227.7 L 191.86274509804 187.22 L 378.13725490196 217.58 L 564.41176470588 227.7 L 574.41176470588 227.7" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-1 highcharts-tracker" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#434348" d="M 564 223.7 L 568 227.7 564 231.7 560 227.7 Z" stroke-width="1"></path>
				<path fill="#434348" d="M 378 213.58 L 382 217.58 378 221.58 374 217.58 Z"></path>
				<path fill="#434348" d="M 191 183.22 L 195 187.22 191 191.22 187 187.22 Z"></path>
				<path fill="#434348" d="M 5 223.7 L 9 227.7 5 231.7 1 227.7 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-2" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="#90ed7d" d="M 5.5882352941176 126.50000000000001 L 191.86274509804 55.660000000000025 L 378.13725490196 40.48000000000002 L 564.41176470588 91.08000000000001 L 564.41176470588 253 L 378.13725490196 253 L 191.86274509804 253 L 5.5882352941176 253" fill-opacity="0.75"></path>
				<path fill="none" d="M 5.5882352941176 126.50000000000001 L 191.86274509804 55.660000000000025 L 378.13725490196 40.48000000000002 L 564.41176470588 91.08000000000001" stroke="#90ed7d" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M -4.4117647058824 126.50000000000001 L 5.5882352941176 126.50000000000001 L 191.86274509804 55.660000000000025 L 378.13725490196 40.48000000000002 L 564.41176470588 91.08000000000001 L 574.41176470588 91.08000000000001" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-2 highcharts-tracker" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#90ed7d" d="M 560 87.08000000000001 L 568 87.08000000000001 568 95.08000000000001 560 95.08000000000001 Z" stroke-width="1"></path>
				<path fill="#90ed7d" d="M 374 36.48000000000002 L 382 36.48000000000002 382 44.48000000000002 374 44.48000000000002 Z"></path>
				<path fill="#90ed7d" d="M 187 51.660000000000025 L 195 51.660000000000025 195 59.660000000000025 187 59.660000000000025 Z"></path>
				<path fill="#90ed7d" d="M 1 122.50000000000001 L 9 122.50000000000001 9 130.5 1 130.5 Z"></path>
			</g>
			<g class="highcharts-series highcharts-series-3" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-1)">
				<path fill="#f7a35c" d="M 5.5882352941176 197.34 L 191.86274509804 116.38000000000002 L 378.13725490196 182.16000000000003 L 564.41176470588 172.04000000000002 L 564.41176470588 253 L 378.13725490196 253 L 191.86274509804 253 L 5.5882352941176 253" fill-opacity="0.75"></path>
				<path fill="none" d="M 5.5882352941176 197.34 L 191.86274509804 116.38000000000002 L 378.13725490196 182.16000000000003 L 564.41176470588 172.04000000000002" stroke="#f7a35c" stroke-width="2" stroke-linejoin="round" stroke-linecap="round"></path>
				<path fill="none" d="M -4.4117647058824 197.34 L 5.5882352941176 197.34 L 191.86274509804 116.38000000000002 L 378.13725490196 182.16000000000003 L 564.41176470588 172.04000000000002 L 574.41176470588 172.04000000000002" stroke-linejoin="round" visibility="visible" stroke="rgba(192,192,192,0.0001)" stroke-width="22" class=" highcharts-tracker" style="cursor:pointer;"></path>
			</g>
			<g class="highcharts-markers highcharts-series-3 highcharts-tracker" transform="translate(50,10) scale(1 1)" clip-path="url(#highcharts-2)" style="cursor:pointer;">
				<path fill="#f7a35c" d="M 564 168.04000000000002 L 568 176.04000000000002 560 176.04000000000002 Z"></path>
				<path fill="#f7a35c" d="M 378 178.16000000000003 L 382 186.16000000000003 374 186.16000000000003 Z" stroke-width="1"></path>
				<path fill="#f7a35c" d="M 191 112.38000000000002 L 195 120.38000000000002 187 120.38000000000002 Z"></path>
				<path fill="#f7a35c" d="M 5 193.34 L 9 201.34 1 201.34 Z"></path>
			</g>
		</g>
		<g class="highcharts-legend" transform="translate(119,306)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(8,3)">
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#7cb5ec"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#434348"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#90ed7d"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f7a35c"></rect>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-xaxis-labels">
			<text x="55.588235294117645" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">0</text>
			<text x="148.72549019607845" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">0.5</text>
			<text x="241.86274509803926" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">1</text>
			<text x="335.00000000000006" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">1.5</text>
			<text x="428.13725490196083" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">2</text>
			<text x="521.2745098039215" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">2.5</text>
			<text x="614.4117647058824" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:92px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">3</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="265" opacity="1">0</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="214" opacity="1">10</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="164" opacity="1">20</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="113" opacity="1">30</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="63" opacity="1">40</text>
			<text x="35" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="12" opacity="1">50</text>
		</g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(378,-9999)" opacity="0" visibility="visible">
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 98.5 0.5 C 101.5 0.5 101.5 0.5 101.5 3.5 L 101.5 46.5 C 101.5 49.5 101.5 49.5 98.5 49.5 L 55.5 49.5 49.5 55.5 43.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" stroke="#f7a35c" stroke-width="1"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20">
				<tspan style="font-size: 10px">交工检测评定</tspan>
				<tspan style="fill:#f7a35c" x="8" dy="15">●</tspan>
				<tspan dx="0">一级公路:</tspan>
				<tspan style="font-weight:bold" dx="0">14</tspan>
			</text>
		</g>
		<text x="620" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="345"></text>
	</svg>
	<span style="position: absolute; font-family: 'Lucida Grande', 'Lucida Sans Unicode', Arial, Helvetica, sans-serif; font-size: 18px; white-space: nowrap; color: rgb(51, 51, 51); margin-left: 0px; margin-top: 0px; left: 315px; top: 7px;" class="highcharts-title" transform="translate(0,0)"></span>
	<span style="position: absolute; font-family: 'Lucida Grande', 'Lucida Sans Unicode', Arial, Helvetica, sans-serif; font-size: 12px; white-space: nowrap; color: rgb(85, 85, 85); margin-left: 0px; margin-top: 0px; left: 315px; top: 12px;" class="highcharts-subtitle" transform="translate(0,0)"></span>
</div>
</template>
<template id="charts_pile">
	<div class="box-body chart-responsive">
	<svg version="1.1" style="font-family:&quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="630" height="350">
		<desc>Created with Highcharts 4.2.5</desc>
		<defs>
			<clipPath id="highcharts-3">
				<rect x="0" y="0" width="562" height="253"></rect>
			</clipPath>
		</defs>
		<rect x="0" y="0" width="630" height="350" fill="#FFFFFF" class=" highcharts-background"></rect>
		<g class="highcharts-grid"></g>
		<g class="highcharts-grid">
			<path fill="none" d="M 58 263.5 L 620 263.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 58 212.5 L 620 212.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 58 162.5 L 620 162.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 58 111.5 L 620 111.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 58 61.5 L 620 61.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 58 9.5 L 620 9.5" stroke="#D8D8D8" stroke-width="1" opacity="1"></path>
		</g>
		<g class="highcharts-axis">
			<path fill="none" d="M 130.5 263 L 130.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 269.5 263 L 269.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 407.5 263 L 407.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<path fill="none" d="M 546.5 263 L 546.5 273" stroke="#C0D0E0" stroke-width="1" opacity="1"></path>
			<text x="339" text-anchor="middle" transform="translate(0,0)" class=" highcharts-xaxis-title" style="color:#707070;fill:#707070;" y="306"></text>
			<path fill="none" d="M 58 263.5 L 620 263.5" stroke="#C0D0E0" stroke-width="1"></path>
		</g>
		<g class="highcharts-axis">
			<text x="10.25" text-anchor="middle" transform="translate(0,0) rotate(270 10.25 136.5)" class=" highcharts-yaxis-title" style="color:#707070;fill:#707070;" y="136.5"></text>
		</g>
		<g class="highcharts-series-group">
			<g class="highcharts-series highcharts-series-0 highcharts-tracker" transform="translate(58,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="39.5" y="140.5" width="67" height="30" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="178.5" y="24.5" width="67" height="69" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="316.5" y="83.5" width="67" height="42" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
				<rect x="454.5" y="87.5" width="67" height="59" stroke="#FFFFFF" stroke-width="1" fill="#7cb5ec" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-0" transform="translate(58,10) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-1 highcharts-tracker" transform="translate(58,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="39.5" y="170.5" width="67" height="10" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="178.5" y="93.5" width="67" height="26" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="316.5" y="125.5" width="67" height="15" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
				<rect x="454.5" y="146.5" width="67" height="10" stroke="#FFFFFF" stroke-width="1" fill="#434348" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-1" transform="translate(58,10) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-2 highcharts-tracker" transform="translate(58,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="39.5" y="180.5" width="67" height="51" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="178.5" y="119.5" width="67" height="79" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="316.5" y="140.5" width="67" height="85" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
				<rect x="454.5" y="156.5" width="67" height="65" stroke="#FFFFFF" stroke-width="1" fill="#90ed7d" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-2" transform="translate(58,10) scale(1 1)" clip-path="none"></g>
			<g class="highcharts-series highcharts-series-3 highcharts-tracker" transform="translate(58,10) scale(1 1)" style="cursor:pointer;" clip-path="url(#highcharts-3)">
				<rect x="39.5" y="231.5" width="67" height="22" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="178.5" y="198.5" width="67" height="55" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="316.5" y="225.5" width="67" height="28" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
				<rect x="454.5" y="221.5" width="67" height="32" stroke="#FFFFFF" stroke-width="1" fill="#f7a35c" rx="0" ry="0"></rect>
			</g>
			<g class="highcharts-markers highcharts-series-3" transform="translate(58,10) scale(1 1)" clip-path="none"></g>
		</g>
		<g class="highcharts-data-labels highcharts-series-0 highcharts-tracker" visibility="visible" transform="translate(58,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(60,142)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>15</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(199,45)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>34</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(337,91)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>21</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(475,103)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>29</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-1 highcharts-tracker" visibility="visible" transform="translate(58,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(64,162)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#FFFFFF;text-shadow:0 0 6px #000000, 0 0 3px #000000;fill:#FFFFFF;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(199,93)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#FFFFFF;text-shadow:0 0 6px #000000, 0 0 3px #000000;fill:#FFFFFF;text-rendering:geometricPrecision;" y="16">
					<tspan>13</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(341,119)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#FFFFFF;text-shadow:0 0 6px #000000, 0 0 3px #000000;fill:#FFFFFF;text-rendering:geometricPrecision;" y="16">
					<tspan>7</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(479,138)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#FFFFFF;text-shadow:0 0 6px #000000, 0 0 3px #000000;fill:#FFFFFF;text-rendering:geometricPrecision;" y="16">
					<tspan>5</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-2 highcharts-tracker" visibility="visible" transform="translate(58,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(60,192)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>25</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(199,145)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>39</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(337,169)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>42</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(475,175)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>32</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-data-labels highcharts-series-3 highcharts-tracker" visibility="visible" transform="translate(58,10) scale(1 1)" opacity="1" style="cursor:pointer;">
			<g style="cursor:pointer;" transform="translate(60,228)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>11</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(199,212)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>27</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(337,225)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>14</tspan>
				</text>
			</g>
			<g style="cursor:pointer;" transform="translate(475,223)">
				<text x="5" style="font-size:11px;font-weight:bold;color:#000000;text-shadow:0 0 6px #FFFFFF, 0 0 3px #FFFFFF;fill:#000000;text-rendering:geometricPrecision;" y="16">
					<tspan>16</tspan>
				</text>
			</g>
		</g>
		<g class="highcharts-legend" transform="translate(119,306)">
			<g>
				<g>
					<g class="highcharts-legend-item" transform="translate(8,3)">
						<text x="21" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start" y="15">二级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#7cb5ec"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(97.9375,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">高速公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#434348"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(187.875,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">三级及以下公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#90ed7d"></rect>
					</g>
					<g class="highcharts-legend-item" transform="translate(314.515625,3)">
						<text x="21" y="15" style="color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;" text-anchor="start">一级公路</text>
						<rect x="0" y="4" width="16" height="12" fill="#f7a35c"></rect>
					</g>
				</g>
			</g>
		</g>
		<g class="highcharts-axis-labels highcharts-xaxis-labels">
			<text x="131.36453201970443" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:193px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">0</text>
			<text x="269.7881773399015" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:193px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">1</text>
			<text x="408.2118226600985" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:193px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">2</text>
			<text x="546.6354679802956" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:193px;text-overflow:clip;" text-anchor="middle" transform="translate(0,0)" y="282" opacity="1">3</text>
		</g>
		<g class="highcharts-axis-labels highcharts-yaxis-labels">
			<text x="43" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="265" opacity="1">0</text>
			<text x="43" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="214" opacity="1">25</text>
			<text x="43" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="164" opacity="1">50</text>
			<text x="43" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="113" opacity="1">75</text>
			<text x="43" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="63" opacity="1">100</text>
			<text x="43" style="color:#606060;cursor:default;font-size:11px;fill:#606060;width:198px;text-overflow:clip;" text-anchor="end" transform="translate(0,0)" y="12" opacity="1">125</text>
		</g>
		<g class="highcharts-tooltip" style="cursor:default;padding:0;pointer-events:none;white-space:nowrap;" transform="translate(362,-9999)" opacity="0" visibility="visible">
			<path fill="none" d="M 3.5 0.5 L 90.5 0.5 C 93.5 0.5 93.5 0.5 93.5 3.5 L 93.5 46.5 C 93.5 49.5 93.5 49.5 90.5 49.5 L 51.5 49.5 45.5 55.5 39.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.049999999999999996" stroke-width="5" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 90.5 0.5 C 93.5 0.5 93.5 0.5 93.5 3.5 L 93.5 46.5 C 93.5 49.5 93.5 49.5 90.5 49.5 L 51.5 49.5 45.5 55.5 39.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.09999999999999999" stroke-width="3" transform="translate(1, 1)"></path>
			<path fill="none" d="M 3.5 0.5 L 90.5 0.5 C 93.5 0.5 93.5 0.5 93.5 3.5 L 93.5 46.5 C 93.5 49.5 93.5 49.5 90.5 49.5 L 51.5 49.5 45.5 55.5 39.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" isShadow="true" stroke="black" stroke-opacity="0.15" stroke-width="1" transform="translate(1, 1)"></path>
			<path fill="rgba(249, 249, 249, .85)" d="M 3.5 0.5 L 90.5 0.5 C 93.5 0.5 93.5 0.5 93.5 3.5 L 93.5 46.5 C 93.5 49.5 93.5 49.5 90.5 49.5 L 51.5 49.5 45.5 55.5 39.5 49.5 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5" stroke="#434348" stroke-width="1"></path>
			<text x="8" style="font-size:12px;color:#333333;fill:#333333;" y="20">
				<tspan style="font-size: 10px">交工检测评定</tspan>
				<tspan style="fill:#434348" x="8" dy="15">●</tspan>
				<tspan dx="0">高速公路:</tspan>
				<tspan style="font-weight:bold" dx="0">7</tspan>
			</text>
		</g>
		<text x="620" text-anchor="end" style="cursor:pointer;color:#909090;font-size:9px;fill:#909090;" y="345"></text>
	</svg>
	<span style="position: absolute; font-family: 'Lucida Grande', 'Lucida Sans Unicode', Arial, Helvetica, sans-serif; font-size: 18px; white-space: nowrap; color: rgb(51, 51, 51); margin-left: 0px; margin-top: 0px; left: 315px; top: 7px;" class="highcharts-title" transform="translate(0,0)"></span>
	<span style="position: absolute; font-family: 'Lucida Grande', 'Lucida Sans Unicode', Arial, Helvetica, sans-serif; font-size: 12px; white-space: nowrap; color: rgb(85, 85, 85); margin-left: 0px; margin-top: 0px; left: 315px; top: 12px;" class="highcharts-subtitle" transform="translate(0,0)"></span>
</div>
</template>
<!-- <template id = "diagrambt"> -->
<!-- <div > -->
<!-- 	<img class="img-responsive" src="../../../images/component/diagrambt.png"/>	 -->
<!-- </div> -->
<!-- </template> -->
<template id="step">
<div class="mt-element-step">
	<div class="row step-line">
		<div class="col-md-4 mt-step-col first done">
			<div class="mt-step-number bg-white">1</div>
			<div class="mt-step-title uppercase font-grey-cascade">采购</div>
			<div class="mt-step-content font-grey-cascade">购买商品</div>
		</div>
		<div class="col-md-4 mt-step-col active">
			<div class="mt-step-number bg-white">2</div>
			<div class="mt-step-title uppercase font-grey-cascade">付款</div>
			<div class="mt-step-content font-grey-cascade">支付你的款项</div>
		</div>
		<div class="col-md-4 mt-step-col last">
			<div class="mt-step-number bg-white">3</div>
			<div class="mt-step-title uppercase font-grey-cascade">部署</div>
			<div class="mt-step-content font-grey-cascade">接收商品</div>
		</div>
	</div>
</div>
</template>
<template id = "tagsinput">
	<div>
		<input type="text" role="tagsinput"/>
	</div>
</template>
<template id="customList">
  <img class="img-responsive" type="navigator" src="${contextPath}/images/component/navigator.png"/>
</template>
<template id="image">
  <img class="img-responsive" src="${contextPath}/images/component/image.png"/>
</template>