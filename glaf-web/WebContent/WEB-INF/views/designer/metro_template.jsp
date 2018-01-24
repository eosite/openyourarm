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

 <template id="charts">
    <div class="box box-primary">
	<div id="container" style="min-width: 400px; height: 400px"
		data-highcharts-chart="0">
		<div id="highcharts-zqcx219-0" class="highcharts-container "
			style="position: relative; overflow: hidden; width: 829px; height: 400px; text-align: left; line-height: normal; z-index: 0; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
			<svg version="1.1" class="highcharts-root"
				style="font-family: &amp; quot; Lucida Grande&amp;quot; , &amp; quot; Lucida Sans Unicode&amp;quot; , Arial , Helvetica, sans-serif; font-size: 12px;"
				xmlns="http://www.w3.org/2000/svg" width="829" height="400"
				viewBox="0 0 829 400">
				<desc>Created with Highcharts 5.0.12</desc>
				<defs>
				<clipPath id="highcharts-zqcx219-1">
				<rect x="0" y="0" width="809" height="332" fill="none"></rect></clipPath></defs>
				<rect fill="#ffffff" class="highcharts-background" x="0" y="0"
					width="829" height="400" rx="0" ry="0"></rect>
				<rect fill="none" class="highcharts-plot-background" x="10" y="53"
					width="809" height="332"></rect>
				<rect fill="none" class="highcharts-plot-border" x="10" y="53"
					width="809" height="332"></rect>
				<g class="highcharts-series-group">
				<g
					class="highcharts-series highcharts-series-0 highcharts-pie-series highcharts-color-undefined highcharts-tracker highcharts-series-hover"
					transform="translate(10,53) scale(1 1)" style="cursor:pointer;">
				<path fill="#434348"
					d="M 449.41174674629394 315.89503244046284 A 145.5 145.5 0 0 1 261.9465316219205 206.6334627770176 L 271.7440208575273 204.6311629297999 A 135.5 135.5 0 0 0 446.32502875685793 306.3833463620805 Z"
					class="highcharts-halo highcharts-color-1" fill-opacity="0.25"></path>
				<path fill="#7cb5ec"
					d="M 404.4724022808993 42.00000281045797 A 135.5 135.5 0 0 1 446.4538911692268 306.3414568986267 L 404.5 177.5 A 0 0 0 0 0 404.5 177.5 Z"
					transform="translate(10,-2)" stroke="#ffffff" stroke-width="1"
					stroke-linejoin="round"
					class="highcharts-point highcharts-color-0 highcharts-point-select"></path>
				<path fill="rgb(92,92,97)"
					d="M 446.32502875685793 306.3833463620805 A 135.5 135.5 0 0 1 271.7440208575273 204.6311629297999 L 404.5 177.5 A 0 0 0 0 0 404.5 177.5 Z"
					transform="translate(0,0)" stroke="#ffffff" stroke-width="1"
					stroke-linejoin="round"
					class="highcharts-point highcharts-color-1 highcharts-point-hover"></path>
				<path fill="#90ed7d"
					d="M 271.7169560771034 204.4983934072031 A 135.5 135.5 0 0 1 292.85153185469164 100.72194609912242 L 404.5 177.5 A 0 0 0 0 0 404.5 177.5 Z"
					transform="translate(0,0)" stroke="#ffffff" stroke-width="1"
					stroke-linejoin="round"
					class="highcharts-point highcharts-color-2 "></path>
				<path fill="#f7a35c"
					d="M 292.92836572002557 100.61033603860896 A 135.5 135.5 0 0 1 347.47398390393664 54.58424231120112 L 404.5 177.5 A 0 0 0 0 0 404.5 177.5 Z"
					transform="translate(0,0)" stroke="#ffffff" stroke-width="1"
					stroke-linejoin="round"
					class="highcharts-point highcharts-color-3 "></path>
				<path fill="#8085e9"
					d="M 347.5969281541452 54.527277762483095 A 135.5 135.5 0 0 1 398.35190467490986 42.13955184813548 L 404.5 177.5 A 0 0 0 0 0 404.5 177.5 Z"
					transform="translate(0,0)" stroke="#ffffff" stroke-width="1"
					stroke-linejoin="round"
					class="highcharts-point highcharts-color-4 "></path>
				<path fill="#f15c80"
					d="M 398.48726817454906 42.13347143405352 A 135.5 135.5 0 0 1 404.3117932183913 42.00013070778496 L 404.5 177.5 A 0 0 0 0 0 404.5 177.5 Z"
					transform="translate(0,0)" stroke="#ffffff" stroke-width="1"
					stroke-linejoin="round"
					class="highcharts-point highcharts-color-5 "></path></g>
				<g
					class="highcharts-markers highcharts-series-0 highcharts-pie-series highcharts-color-undefined highcharts-series-hover"
					transform="translate(10,53) scale(1 1)"></g></g>
				<g class="highcharts-button highcharts-contextbutton"
					style="cursor:pointer;" stroke-linecap="round"
					transform="translate(795,10)">
				<title>图表导出菜单</title>
				<rect fill="#ffffff" class=" highcharts-button-box" x="0.5" y="0.5"
					width="24" height="22" rx="2" ry="2" stroke="none" stroke-width="1"></rect>
				<path fill="#666666"
					d="M 6 6.5 L 20 6.5 M 6 11.5 L 20 11.5 M 6 16.5 L 20 16.5"
					class="highcharts-button-symbol" stroke="#666666" stroke-width="3"></path>
				<text x="0" style="font-weight:normal;color:#333333;fill:#333333;"
					y="12"></text></g>
				<text x="415" text-anchor="middle" class="highcharts-title"
					style="color:#333333;font-size:18px;fill:#333333;" y="24">
				<tspan>2017 某网站各浏览器浏览量占比</tspan></text>
				<g
					class="highcharts-data-labels highcharts-series-0 highcharts-pie-series highcharts-color-undefined highcharts-tracker highcharts-series-hover"
					transform="translate(10,53) scale(1 1)" opacity="1"
					style="cursor:pointer;">
				<path fill="none"
					class="highcharts-data-label-connector highcharts-color-0"
					stroke="#7cb5ec" stroke-width="1"
					d="M 572.9624203684953 151.6100960358418 C 567.9624203684953 151.6100960358418 550.184030237783 154.42591640656596 544.2579001942121 155.36452319680734 L 538.3317701506412 156.30312998704872"></path>
				<path fill="none"
					class="highcharts-data-label-connector highcharts-color-1"
					stroke="#434348" stroke-width="1"
					d="M 316.1503601292815 320.4793255454142 C 321.1503601292815 320.4793255454142 330.2155777587253 304.9287040359432 333.23731696853986 299.7451635327862 L 336.2590561783544 294.5616230296292"></path>
				<path fill="none"
					class="highcharts-data-label-connector highcharts-color-2"
					stroke="#90ed7d" stroke-width="1"
					d="M 237.33398883040715 144.44799822486567 C 242.33398883040715 144.44799822486567 259.9713797733236 148.04277787412497 265.85051008762906 149.24103775721142 L 271.72964040193455 150.43929764029787"></path>
				<path fill="none"
					class="highcharts-data-label-connector highcharts-color-3"
					stroke="#f7a35c" stroke-width="1"
					d="M 282.9495817253323 60 C 287.9495817253323 60 309.4130204274301 64.74044911509408 313.2809653591957 69.32727830363262 L 317.1489102909613 73.91410749217117"></path>
				<path fill="none"
					class="highcharts-data-label-connector highcharts-color-4"
					stroke="#8085e9" stroke-width="1"
					d="M 313.66294506450026 36 C 318.66294506450026 36 369.6163979390004 34.18431939508565 371.0353919211428 40.01410979257369 L 372.45438590328513 45.84390019006173"></path>
				<path fill="none"
					class="highcharts-data-label-connector highcharts-color-5"
					stroke="#f15c80" stroke-width="1"
					d="M 395.8607582566896 12 C 400.8607582566896 12 401.2565670263554 30.035664845544858 401.38850328291045 36.03421407216676 L 401.5204395394655 42.032763298788666"></path>
				<g
					class="highcharts-label highcharts-data-label highcharts-data-label-color-0 "
					style="cursor:pointer;" transform="translate(578,142)">
				<text x="5"
					style="font-size:11px;font-weight:bold;color:black;fill:black;"
					y="16">
				<tspan style="font-weight: bold;" x="5" y="16"
					class="highcharts-text-outline" fill="#FFFFFF" stroke="#FFFFFF"
					stroke-width="2px" stroke-linejoin="round">Firefox</tspan>
				<tspan dx="0" class="highcharts-text-outline" fill="#FFFFFF"
					stroke="#FFFFFF" stroke-width="2px" stroke-linejoin="round">: 45.0 %</tspan>
				<tspan style="font-weight:bold" x="5" y="16">Firefox</tspan>
				<tspan dx="0">: 45.0 %</tspan></text></g>
				<g
					class="highcharts-label highcharts-data-label highcharts-data-label-color-1 "
					style="cursor:pointer;" transform="translate(247,310)">
				<text x="5"
					style="font-size:11px;font-weight:bold;color:black;fill:black;"
					y="16">
				<tspan style="font-weight: bold;" x="5" y="16"
					class="highcharts-text-outline" fill="#FFFFFF" stroke="#FFFFFF"
					stroke-width="2px" stroke-linejoin="round">IE</tspan>
				<tspan dx="0" class="highcharts-text-outline" fill="#FFFFFF"
					stroke="#FFFFFF" stroke-width="2px" stroke-linejoin="round">: 26.8 %</tspan>
				<tspan style="font-weight:bold" x="5" y="16">IE</tspan>
				<tspan dx="0">: 26.8 %</tspan></text></g>
				<g
					class="highcharts-label highcharts-data-label highcharts-data-label-color-2 "
					style="cursor:pointer;" transform="translate(134,134)">
				<text x="5"
					style="font-size:11px;font-weight:bold;color:black;fill:black;"
					y="16">
				<tspan style="font-weight: bold;" x="5" y="16"
					class="highcharts-text-outline" fill="#FFFFFF" stroke="#FFFFFF"
					stroke-width="2px" stroke-linejoin="round">Chrome</tspan>
				<tspan dx="0" class="highcharts-text-outline" fill="#FFFFFF"
					stroke="#FFFFFF" stroke-width="2px" stroke-linejoin="round">: 12.8 %</tspan>
				<tspan style="font-weight:bold" x="5" y="16">Chrome</tspan>
				<tspan dx="0">: 12.8 %</tspan></text></g>
				<g
					class="highcharts-label highcharts-data-label highcharts-data-label-color-3 "
					style="cursor:pointer;" transform="translate(199,50)">
				<text x="5"
					style="font-size:11px;font-weight:bold;color:black;fill:black;"
					y="16">
				<tspan style="font-weight: bold;" x="5" y="16"
					class="highcharts-text-outline" fill="#FFFFFF" stroke="#FFFFFF"
					stroke-width="2px" stroke-linejoin="round">Safari</tspan>
				<tspan dx="0" class="highcharts-text-outline" fill="#FFFFFF"
					stroke="#FFFFFF" stroke-width="2px" stroke-linejoin="round">: 8.5 %</tspan>
				<tspan style="font-weight:bold" x="5" y="16">Safari</tspan>
				<tspan dx="0">: 8.5 %</tspan></text></g>
				<g
					class="highcharts-label highcharts-data-label highcharts-data-label-color-4 "
					style="cursor:pointer;" transform="translate(228,26)">
				<text x="5"
					style="font-size:11px;font-weight:bold;color:black;fill:black;"
					y="16">
				<tspan style="font-weight: bold;" x="5" y="16"
					class="highcharts-text-outline" fill="#FFFFFF" stroke="#FFFFFF"
					stroke-width="2px" stroke-linejoin="round">Opera</tspan>
				<tspan dx="0" class="highcharts-text-outline" fill="#FFFFFF"
					stroke="#FFFFFF" stroke-width="2px" stroke-linejoin="round">: 6.2 %</tspan>
				<tspan style="font-weight:bold" x="5" y="16">Opera</tspan>
				<tspan dx="0">: 6.2 %</tspan></text></g>
				<g
					class="highcharts-label highcharts-data-label highcharts-data-label-color-5 "
					style="cursor:pointer;" transform="translate(320,2)">
				<text x="5"
					style="font-size:11px;font-weight:bold;color:black;fill:black;"
					y="16">
				<tspan style="font-weight: bold;" x="5" y="16"
					class="highcharts-text-outline" fill="#FFFFFF" stroke="#FFFFFF"
					stroke-width="2px" stroke-linejoin="round">其他</tspan>
				<tspan dx="0" class="highcharts-text-outline" fill="#FFFFFF"
					stroke="#FFFFFF" stroke-width="2px" stroke-linejoin="round">: 0.7 %</tspan>
				<tspan style="font-weight:bold" x="5" y="16">其他</tspan>
				<tspan dx="0">: 0.7 %</tspan></text></g></g>
				<g class="highcharts-legend">
				<rect fill="none" class="highcharts-legend-box" rx="0" ry="0" x="0"
					y="0" width="8" height="8" visibility="hidden"></rect>
				<g>
				<g></g></g></g>
				<text x="819" class="highcharts-credits" text-anchor="end"
					style="cursor:pointer;color:#999999;font-size:9px;fill:#999999;"
					y="395">Highcharts.com</text>
				<g class="highcharts-label highcharts-tooltip highcharts-color-1"
					style="cursor:default;pointer-events:none;white-space:nowrap;"
					transform="translate(359,276)" opacity="1" visibility="visible">
				<path fill="none"
					class="highcharts-label-box highcharts-tooltip-box"
					d="M 3.5 0.5 L 110.5 0.5 C 113.5 0.5 113.5 0.5 113.5 3.5 L 113.5 46.5 C 113.5 49.5 113.5 49.5 110.5 49.5 L 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
					isShadow="true" stroke="#000000"
					stroke-opacity="0.049999999999999996" stroke-width="5"
					transform="translate(1, 1)"></path>
				<path fill="none"
					class="highcharts-label-box highcharts-tooltip-box"
					d="M 3.5 0.5 L 110.5 0.5 C 113.5 0.5 113.5 0.5 113.5 3.5 L 113.5 46.5 C 113.5 49.5 113.5 49.5 110.5 49.5 L 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
					isShadow="true" stroke="#000000"
					stroke-opacity="0.09999999999999999" stroke-width="3"
					transform="translate(1, 1)"></path>
				<path fill="none"
					class="highcharts-label-box highcharts-tooltip-box"
					d="M 3.5 0.5 L 110.5 0.5 C 113.5 0.5 113.5 0.5 113.5 3.5 L 113.5 46.5 C 113.5 49.5 113.5 49.5 110.5 49.5 L 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
					isShadow="true" stroke="#000000" stroke-opacity="0.15"
					stroke-width="1" transform="translate(1, 1)"></path>
				<path fill="rgba(247,247,247,0.85)"
					class="highcharts-label-box highcharts-tooltip-box"
					d="M 3.5 0.5 L 110.5 0.5 C 113.5 0.5 113.5 0.5 113.5 3.5 L 113.5 46.5 C 113.5 49.5 113.5 49.5 110.5 49.5 L 3.5 49.5 C 0.5 49.5 0.5 49.5 0.5 46.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5"
					stroke="#434348" stroke-width="1"></path>
				<text x="8" style="font-size:12px;color:#333333;fill:#333333;"
					y="20">
				<tspan>浏览器访问量占比</tspan>
				<tspan x="8" dy="15">IE: </tspan>
				<tspan style="font-weight:bold" dx="0">26.8%</tspan></text></g></svg>
		</div>
	</div>
</div>
 </template>
<template id="searchbar">
  <div class="weui-search-bar" id="searchBar">
  <form class="weui-search-bar__form">
    <div class="weui-search-bar__box">
      <i class="weui-icon-search"></i>
      <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="">
      <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
    </div>
    <label class="weui-search-bar__label" id="searchText">
      <i class="weui-icon-search"></i>
      <span>搜索</span>
    </label>
  </form>
  <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
</div>
</template>
<template id="phoneTimePicker">
	<div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__bd">
          <input class="weui-input" id="time" type="text" placeholder="请输入时间" />
        </div>
      </div>
  </div>
</template>
<template id="mdatepicker">
	<input type="text" class = "form-control id frame-variable component" frame-variable="fv1" placeholder="请输入..." > 
</template>
<template id="slideouts">
	
	<div  class="mui-off-canvas-wrap mui-draggable" style="min-height:100px;overflow:visible" > 
    <!--侧滑菜单部分--> 
    <aside id="offCanvasSide" class="mui-off-canvas-left" style="visibility:visible;"> 
     <div id="offCanvasSideScroll" class="mui-scroll-wrapper containerComp ui-sortable" style="min-height:100px" data-scroll="1"> 
     
     </div>
    </aside> 
    <div class="mui-inner-wrap"> 
     <div id="offCanvasContentScroll" class="mui-content mui-scroll-wrapper containerComp" style="min-height:100px"> 
     </div> 
    </div> 
   </div> 
</template>
<template id="phonePanel">
<div class="weui-panel">
        <div class="weui-panel__hd">小图文组合列表</div>
        <div class="weui-panel__bd">
          <div class="weui-media-box weui-media-box_small-appmsg">
            <div class="weui-cells">
              <a class="weui-cell weui-cell_access" href="javascript:;">
                <div class="weui-cell__hd"><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=" alt="" style="width:20px;margin-right:5px;display:block"></div>
                <div class="weui-cell__bd weui-cell_primary">
                  <p>文字标题</p>
                </div>
                <span class="weui-cell__ft"></span>
              </a>
              
            </div>
          </div>
        </div>
      </div>
</template>
<template id="customSelect">
   <div>
            <select name="select-choice-a" multiple="multiple" data-native-menu="false">
                <option>Custom menu example</option>
                <option value="standard">Standard: 7 day</option>
                <option value="rush">Rush: 3 days</option>
                <option value="express">Express: next day</option>
                <option value="overnight">Overnight</option>
    </select>
   </div>
</template>
<template id="cellWebCab">
<div style="min-height:100px">
	<img src="${contextPath}/scripts/designer/images/cell.png" />
	<div data-options="region:'center'" style="padding:5px;">
	<OBJECT id="CellWeb" style="left:0px;width:100%;top:0px;height:0px;background:#eee;"
		codeBase="${contextPath }/cellPlugin/CellWeb5.CAB#version=5,3,9,16"
		classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" VIEWASTEXT>
		<PARAM NAME="_Version" VALUE="65536">
		<PARAM NAME="_ExtentX" VALUE="10266">
		<PARAM NAME="_ExtentY" VALUE="7011">
		<PARAM NAME="_StockProps" VALUE="0">
	</OBJECT>
	</div>
</div>
</template>
 <template id="bootstrapdialog">
    <div class="bootstrap-portlet" style="min-height:100px;">
        <div class="containerComp bootstrap-content" style="min-height:150px;margin:20px;">
        
        </div>
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
	<div class="btn-group-vertical btn-group-xs btn-group-solid toolbar">
	   <a class="drag dragBtn bg-green-sharp bg-font-green-sharp" data-toggle="dropdown"  data-close-others="true" title="按住鼠标左键，可拖动！" style="-webkit-border-radius: 0 0 0 28px !important;border-radius: 0 0 0 28px !important;">
	    <i class="icon-cursor-move"></i></a>
		<%-- <ul class="dropdown-menu pull-right" role="menu">
             <li><a class="moveUpBt"><i class="glyphicon glyphicon-circle-arrow-up"></i>上移</a></i>
			 <li><a class="moveDownBt"><i class="glyphicon glyphicon-circle-arrow-down"></i>下移</a></i>
             <li><a class="editBt"><i class="glyphicon-edit glyphicon"></i>编辑</a></i>
			 <li><a class="myremove"><i class="glyphicon-remove glyphicon"></i>删除</a></i>
			 <li class="divider"> </li>
			       <li>
						<a class="elemcopyaddup-row" href="javascript:;"><i class="glyphicon glyphicon-export "> 上方复制插入</i> </a>
					</li>
					<li>
						<a class="elemcopyadddown-row" href="javascript:;"><i class="glyphicon glyphicon-import "> 下方复制插入</i></a>
					</li>
					<li class="divider"> </li>
					<li>
						<a class="elemcut-row" href="javascript:;"><i class="fa fa-cut"> 剪切</i> </a>
					</li>
					<li>
						<a class="elemcopy-row" href="javascript:;"><i class="fa fa-copy"> 复制</i></a>
					</li>
					<li>
						<a class="elempaste-row" href="javascript:;"><i class="fa fa-paste"> 粘贴</i></a>
					</li>
					<li>
						<a class="elem-save-as" href="javascript:;"><i class="fa fa-save"> 另存为模板</i> </a>
					</li>
		 </ul> --%>
	  </div>
	<%-- <div class="preview"><input type="text" class="form-control"></div> --%>
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
		<%-- <ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;">
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
		 </ul> --%>
	  </div>
<%--      <div class="preview"><input type="text" class="form-control"></div>
 --%>	 <div class="view">
	 </div>
	 </div>
 </template>
 <template id="gridlayout_toolbar">
	  	  <div class="btn-group-vertical btn-group-xs btn-group-solid toolbar">
	    <a class="drag dragBtn bg-green-sharp bg-font-green-sharp" data-toggle="dropdown" data-close-others="true" title="按住鼠标左键，可拖动！" style="-webkit-border-radius: 0 0 0 28px !important;border-radius: 0 0 0 28px !important;">
	    <i class="icon-cursor-move"></i></a>
		<%-- <ul class="dropdown-menu pull-right" role="menu" style="font-size:12px;">
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
		 </ul> --%>
	  </div>
   </template>
     <!--列工具栏模板-->
	<!--小尺寸模板-->
  <template id="colconfigTemplate">
	<button type="button" class="removecol btn btn-xs red" title="删除"><i class="glyphicon-remove glyphicon"></i></button>
	<%-- <span class="configuration">
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
	</span> --%>
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
	<span class="id frame-variable" contenteditable="true" style="white-space:normal;line-height:1.4;" frame-variable="fv1">华闽通达</span>
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
      <span class="sr-only">40% 完成</span>
   </div>
   <div style="width: 60%;background: rgba(0,0,0,0);box-shadow: 0 0; line-height: 20px; float: left; text-align: center;" class="progress_remain">
   		<span style="color: black;" class="sr-only">60% 未完成</span>
   	</div>
</div>
</template>
<!--tabs -->
<template id="bootstrap_tabs">
  <div static-datas='[{"title":"New Tab 1", "content": "", "minHeight":200},{"title":"New Tab 2", "content": "", "minHeight":200}]'></div>
</template>

<template id="mtbutton">
  <button type="button" class="btn btn-default component"><span class="id frame-variable" contenteditable="true" frame-variable="fv1">默认按钮</span></button>
</template>
<template id="definedButton">

  <button type="button" class="btn btn-default component"><span class="id frame-variable" contenteditable="true" frame-variable="fv1">自定义按钮</span></button>
</template>
<template id="roundwater">
  <div>
  <img src="${contextPath}/scripts/plugins/bootstrap/roundwater/img/roundwater.png" />
  <canvas id='canvas-id'>
  </canvas>
  </div>
</template>
<template id="buttongroup">
  <div class="btn-group">
    <button type="button" data-role="mtbutton" cname="mtbutton_控件" scope="bootstrap" crtltype="kendo" class="btn btn-default"><span class="id frame-variable" frame-variable="fv1" contenteditable="true" >默认按钮</span></button>
    <button type="button" data-role="mtbutton" cname="mtbutton_控件" scope="bootstrap" crtltype="kendo" class="btn btn-default"><span class="id frame-variable" frame-variable="fv2" contenteditable="true" >默认按钮</span></button>
    <button type="button" data-role="mtbutton" cname="mtbutton_控件" scope="bootstrap" crtltype="kendo" class="btn btn-default"><span class="id frame-variable" frame-variable="fv3" contenteditable="true" >默认按钮</span></button>
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
<template id="bimupload">
<div class="mt-sp-jqfileupload component" style ="text-align: left;" data-role="bimupload">
  <span class="btn btn-success fileinput-button"><i class="glyphicon glyphicon-plus"></i><span>选择文件</span> 
    <input class="mt-sp-fileupload" type="file" name="file" multiple/>
  </span>
  <div class="progress">
    <div class="progress-bar progress-bar-success"></div>
  </div>
  <div class="mt-sp-files"></div> 
</div>
</template>
<template id="excelupload">
<div class="mt-sp-jqfileupload component" style ="text-align: left;" data-role="excelupload">  
 <!--  <span  class="btn btn-success dltemp">
  <i class="glyphicon glyphicon-plus"></i><span>选择模板</span>
  </span> -->
  <span class="btn btn-success fileinput-button"><i class="glyphicon glyphicon-plus"></i><span>选择文件</span> 
    <input class="mt-sp-fileupload" type="file" name="file" multiple/>
  </span>
  <div class="progress">
    <div class="progress-bar progress-bar-success"></div>
  </div>
  <div class="mt-sp-files"></div> 
</div>
</template>

 </template>
 <template id="cell">
    <img src="${contextPath}/scripts/designer/images/cell.png" />
 </template>
<template id="portlet">
  <div class="portlet box red">
      <div class="portlet-title">
          <div class="caption">
              <i class="fa fa-gift"></i><span class="id frame-variable" contenteditable="true" frame-variable="fv1">标题 </span></div>
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
              <i class="fa fa-gift"></i><span class="id frame-variable" contenteditable="true" frame-variable="fv1">标题 </span></div>
          <ul class="nav nav-tabs">
              <li class="active">
                  <a href="#portlet_tab_1" contenteditable="true" data-toggle="tab"> Tab 1 </a>
              </li>
              <li>
                  <a href="#portlet_tab_2" contenteditable="true" data-toggle="tab"> Tab 2 </a>
              </li>
          </ul>
      </div>
      <div class="portlet-body">
          <div class="tab-content">
              <div class="containerComp tab-pane active" id="portlet_tab_1" style="min-height:100px;overflow: auto;">
              </div>
              <div class=" containerComp tab-pane" id="portlet_tab_2" style="min-height:100px;overflow: auto;">
              </div>
          </div>
      </div>
  </div>
</template>
<template id="indexedlist">

	<div class="mui-content">
		<div id='list' class="mui-indexed-list">
			<div class="mui-indexed-list-search mui-input-row mui-search">
				<input type="search" class="mui-input-clear mui-indexed-list-search-input" placeholder="搜索机场">
			</div>
			<div class="mui-indexed-list-bar">
				<a>A</a>
				<a>B</a>
			</div>
			<div class="mui-indexed-list-alert"></div>
			<div class="mui-indexed-list-inner">
				<div class="mui-indexed-list-empty-alert">没有数据</div>
				<ul class="mui-table-view">
					<li data-group="A" class="mui-table-view-divider mui-indexed-list-group">A</li>
					<li data-value="AKU" data-tags="AKeSu" class="mui-table-view-cell mui-indexed-list-item">阿克苏机场</li>
					<li data-value="BPL" data-tags="ALaShanKou" class="mui-table-view-cell mui-indexed-list-item">阿拉山口机场</li>
					<li data-group="B" class="mui-table-view-divider mui-indexed-list-group">B</li>
					<li data-value="BSD" data-tags="BaoShan" class="mui-table-view-cell mui-indexed-list-item">保山机场</li>
					<li data-value="BAV" data-tags="BaoTou" class="mui-table-view-cell mui-indexed-list-item">包头机场</li>						
				</ul>
			</div>
		</div>
	</div>
</template>
<template id="modelbim">
  <div>
      <img src="${contextPath}/scripts/plugins/bootstrap/modelbim/images/modelbim.jpg" style="width:400px;height:400px" />
  </div>
</template>
<template id="expandview">
	<div class="mui-content">
			<div class="mui-card">
				<ul class="mui-table-view mui-table-view-chevron">
					
					<li class="mui-table-view-cell mui-collapse"><a class="mui-navigate-right" href="#">产品</a>
						<ul class="mui-table-view mui-table-view-chevron">
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">iOS</a>
							
							</li>
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">Android</a>
							</li>
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">HTML5</a>
							</li>
						</ul>
					</li>
					<li class="mui-table-view-cell mui-collapse"><a class="mui-navigate-right" href="#">方案</a>
						<ul class="mui-table-view mui-table-view-chevron">
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">PC方案</a>
							</li>
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">手机方案</a>
							</li>
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">TV方案</a>
							</li>
						</ul>
					</li>
					<li class="mui-table-view-cell mui-collapse"><a class="mui-navigate-right" href="#">新闻</a>
						<ul class="mui-table-view mui-table-view-chevron">
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">公司新闻</a>
							</li>
							<li class="mui-table-view-cell"><a class="mui-navigate-right" href="#">行业新闻</a>
							</li>
						</ul>
					</li>
				</ul>
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
				<h3 class="form-title font-green id frame-variable" contenteditable="true" frame-variable="fv1" >登 录</h3>
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
        <p class="id frame-variable" frame-variable="copyright" contenteditable="true">华闽通达信息技术有限公司版权所有 ? 1997- 2015 2015</p> 
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
   <p class="id frame-variable" frame-variable="copyright" contenteditable="true">华闽通达信息技术有限公司版权所有 ? 1997- 2015 2015</p> 
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
<template id="svgeditor">
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
													<a href="javascript:;" class="insert" type="prev" template="select_m_form_group"> 多选下拉框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="dateRange_form_group"> 日期时间 </a>
												</li>
                        <li>
                          <a href="javascript:;" class="insert" type="prev" template="datepickerbt_form_group"> 日期控件 </a>
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
												<li>
													<a href="javascript:;" class="insert" type="prev" template="icheckradio_form_group"> 单选按钮 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="icheckbox_form_group"> 复选框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="prev" template="a_form_group"> 超链接 </a>
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
													<a href="javascript:;" class="insert" type="after" template="select_m_form_group"> 多选下拉框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="dateRange_form_group"> 日期时间 </a>
												</li>
                        <li>
                          <a href="javascript:;" class="insert" type="after" template="datepickerbt_form_group"> 日期控件 </a>
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
												<li>
													<a href="javascript:;" class="insert" type="after" template="icheckradio_form_group"> 单选按钮 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="icheckbox_form_group"> 复选框 </a>
												</li>
												<li>
													<a href="javascript:;" class="insert" type="after" template="a_form_group"> 超链接 </a>
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
	<div role="form" class="form-inline myform" action="javascript:;">
		<div class="form-group myformgroup">
		      <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
		      <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	   </div> 
	   <div class="form-group myformgroup">
		      <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
		    <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	  </div>
      <div class="form-group myformgroup">
		       <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
		    <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	  </div> 	  
    </div>    
</template>

<template id="form_group">
	<div class="form-group myformgroup">
		    <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
		    <input type="text" data-role="textboxbt" scope="bootstrap" crtltype="kendo" contenteditable="true" class="form-control frame-variable" frame-variable="fv1" placeholder="请输入..."> 
	</div>   
</template>

<template id="a_form_group">
	<div class="form-group myformgroup" >
		<a style="display: block;" data-role="a"><p style="display: inline;">A标签</p></a>
	</div>
</template>

<template id="icheckradio_form_group">
	<div class="form-group myformgroup">
		<div class="form-group" data-role="icheckradio" static-datas='[{"text":"New 1"},{"text":"New 2"},{"text":"New 3"}]'>
		    <div class="input-group">
		      <div class="icheck-list">
		      </div>
		    </div>
		  </div> 
	</div>   
</template>

<template id="icheckbox_form_group">
	<div class="form-group myformgroup">
		<div class="form-group" data-role="icheckbox" static-datas='[{"text":"New 1"},{"text":"New 2"},{"text":"New 3"}]'>
	    <div class="input-group">
	      <div class="icheck-list">
	      </div>
	    </div>
	  </div> 
	</div>   
</template>


<template id="select_form_group">
	<div class="form-group myformgroup">
		    <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
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

<template id="select_m_form_group">
	<div class="form-group myformgroup">
    <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
	<div class="input-group select2-bootstrap-prepend" data-role="metroselect_m" scope="bootstrap" crtltype="kendo">
        <span class="input-group-btn" style="display:none;">
            <button class="btn btn-default" type="button" data-select2-open="single">
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </span>
        <select id="multiple" multiple="" class="form-control select2 select2-multiple" data-placeholder="请选择..." data-allow-clear="false">
            <option></option>
            <option value="AK">Alaska</option>
            <option value="HI" disabled="disabled">Hawaii</option>
            <option value="CA">California</option>
            <option value="NV">Nevada</option>
            <option value="OR">Oregon</option>
            <option value="WA">Washington</option>
            <option value="AZ">Arizona</option>
            <option value="CO">Colorado</option>
            <option value="ID">Idaho</option>
            <option value="MT">Montana</option>
        </select>
      </div>
	</div>   
</template>

<template id="dateRange_form_group">
<div class="form-group myformgroup" >
    <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
    <div class="input-group input-daterange id frame-variable" style="width:auto !important" data-role="daterangepicker" scope="bootstrap" crtltype="kendo">  
	<input type="text" class="form-control" name="from" frame-variable="fv1" data-role="datepickerbt" scope="bootstrap" crtltype="kendo"> 
	<span class="input-group-addon"> to </span> 
	<input type="text" class="form-control" name="to" frame-variable="fv2" data-role="datepickerbt" scope="bootstrap" crtltype="kendo">  
	</div>
</div>
</template>

<template id="bt_form_group">
	<div class="form-group myformgroup">
		<button type="button" class="btn btn-default" data-role="mtbutton" scope="bootstrap" crtltype="kendo"><span contenteditable="true" class="id frame-variable" frame-variable="fv1">默认按钮</span></button>
	</div>   
</template>
<template id="datepickerbt_form_group">
  <div class="form-group myformgroup">
  <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
  <div class="input-group date datepickerbt component" data-role="datepickerbt" scope="bootstrap" crtltype="kendo" data-date-format="dd-mm-yyyy">   
    <input type="text" class="form-control id frame-variable" frame-variable="fv1" >
    <span class="input-group-btn">
      <button class="btn default form-control" type="button">
        <i class="fa fa-calendar"></i>
      </button>
    </span>
  </div>
  </div>   
</template>
<template id="custom_form_group">
  <div class="form-group myformgroup">
  <img class="img-responsive  nlayout_elem" src="/glaf/images/component/custom.png" data-role="custom" crtltype="kendo" ></div>
  </div>   
</template>
<template id="blank_form_group">
  <div class="form-group myformgroup">
        <label data-role="label" scope="bootstrap" contenteditable="true" crtltype="kendo">标题</label>
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
<template id = "charts_treemap">
	<div class="box-body chart-responsive">
	     <img class="img-responsive" type="navigator" src="${contextPath}/images/component/charts_treemap.png"/>
	</div>
</template>
 <template id = "diagrambt">
 <div>
	<div id="diagram-container">
		<div id="chart-container">
			<div class="orgchart">
				<table>
					<tbody>
						<tr>
							<td colspan="4"><div class="node">
									<div class="title">
										<i class="fa fa-users symbol"></i>Lao Lao
									</div>
									<div class="content">general manager</div>
									<i class="edge verticalEdge bottomEdge fa"></i>
								</div></td>
						</tr>
						<tr class="lines">
							<td colspan="4"><div class="down"></div></td>
						</tr>
						<tr class="lines">
							<td class="right">&nbsp;</td>
							<td class="left top">&nbsp;</td>
							<td class="right top">&nbsp;</td>
							<td class="left">&nbsp;</td>
						</tr>
						<tr class="nodes">
							<td colspan="2"><table>
									<tbody>
										<tr>
											<td colspan="4"><div class="node middle-level">
													<div class="title">
														<i class="fa fa-users symbol"></i>Bo Miao
													</div>
													<div class="content">department manager</div>
													<i class="edge verticalEdge topEdge fa"></i><i
														class="edge horizontalEdge rightEdge fa"></i><i
														class="edge horizontalEdge leftEdge fa"></i><i
														class="edge verticalEdge bottomEdge fa"></i>
												</div></td>
										</tr>
										<tr class="lines">
											<td colspan="4"><div class="down"></div></td>
										</tr>
										<tr class="lines">
											<td class="right">&nbsp;</td>
											<td class="left top">&nbsp;</td>
											<td class="right top">&nbsp;</td>
											<td class="left">&nbsp;</td>
										</tr>
										<tr class="nodes">
											<td colspan="2"><table>
													<tbody>
														<tr>
															<td><div class="node product-dept">
																	<div class="title">Li Jing</div>
																	<div class="content">senior engineer</div>
																	<i class="edge verticalEdge topEdge fa"></i><i
																		class="edge horizontalEdge rightEdge fa"></i><i
																		class="edge horizontalEdge leftEdge fa"></i>
																</div></td>
														</tr>
													</tbody>
												</table></td>
											<td colspan="2"><table>
													<tbody>
														<tr>
															<td colspan="6"><div class="node product-dept">
																	<div class="title">
																		<i class="fa fa-users symbol"></i>Li Xin
																	</div>
																	<div class="content">senior engineer</div>
																	<i class="edge verticalEdge topEdge fa"></i><i
																		class="edge horizontalEdge rightEdge fa"></i><i
																		class="edge horizontalEdge leftEdge fa"></i><i
																		class="edge verticalEdge bottomEdge fa"></i>
																</div></td>
														</tr>
														<tr class="lines">
															<td colspan="6"><div class="down"></div></td>
														</tr>
														<tr class="lines">
															<td class="right">&nbsp;</td>
															<td class="left top">&nbsp;</td>
															<td class="right top">&nbsp;</td>
															<td class="left top">&nbsp;</td>
															<td class="right top">&nbsp;</td>
															<td class="left">&nbsp;</td>
														</tr>
														<tr class="nodes">
															<td colspan="2"><table>
																	<tbody>
																		<tr>
																			<td><div class="node pipeline1">
																					<div class="title">To To</div>
																					<div class="content">engineer</div>
																					<i class="edge verticalEdge topEdge fa"></i><i
																						class="edge horizontalEdge rightEdge fa"></i><i
																						class="edge horizontalEdge leftEdge fa"></i>
																				</div></td>
																		</tr>
																	</tbody>
																</table></td>
															<td colspan="2"><table>
																	<tbody>
																		<tr>
																			<td><div class="node pipeline1">
																					<div class="title">Fei Fei</div>
																					<div class="content">engineer</div>
																					<i class="edge verticalEdge topEdge fa"></i><i
																						class="edge horizontalEdge rightEdge fa"></i><i
																						class="edge horizontalEdge leftEdge fa"></i>
																				</div></td>
																		</tr>
																	</tbody>
																</table></td>
															<td colspan="2"><table>
																	<tbody>
																		<tr>
																			<td><div class="node pipeline1">
																					<div class="title">Xuan Xuan</div>
																					<div class="content">engineer</div>
																					<i class="edge verticalEdge topEdge fa"></i><i
																						class="edge horizontalEdge rightEdge fa"></i><i
																						class="edge horizontalEdge leftEdge fa"></i>
																				</div></td>
																		</tr>
																	</tbody>
																</table></td>
														</tr>
													</tbody>
												</table></td>
										</tr>
									</tbody>
								</table></td>
							<td colspan="2"><table>
									<tbody>
										<tr>
											<td colspan="4"><div class="node middle-level">
													<div class="title">
														<i class="fa fa-users symbol"></i>Su Miao
													</div>
													<div class="content">department manager</div>
													<i class="edge verticalEdge topEdge fa"></i><i
														class="edge horizontalEdge rightEdge fa"></i><i
														class="edge horizontalEdge leftEdge fa"></i><i
														class="edge verticalEdge bottomEdge fa"></i>
												</div></td>
										</tr>
										<tr class="lines">
											<td colspan="4"><div class="down"></div></td>
										</tr>
										<tr class="lines">
											<td class="right">&nbsp;</td>
											<td class="left top">&nbsp;</td>
											<td class="right top">&nbsp;</td>
											<td class="left">&nbsp;</td>
										</tr>
										<tr class="nodes">
											<td colspan="2"><table>
													<tbody>
														<tr>
															<td><div class="node rd-dept">
																	<div class="title">Pang Pang</div>
																	<div class="content">senior engineer</div>
																	<i class="edge verticalEdge topEdge fa"></i><i
																		class="edge horizontalEdge rightEdge fa"></i><i
																		class="edge horizontalEdge leftEdge fa"></i>
																</div></td>
														</tr>
													</tbody>
												</table></td>
											<td colspan="2"><table>
													<tbody>
														<tr>
															<td colspan="6"><div class="node rd-dept">
																	<div class="title">
																		<i class="fa fa-users symbol"></i>Hei Hei
																	</div>
																	<div class="content">senior engineer</div>
																	<i class="edge verticalEdge topEdge fa"></i><i
																		class="edge horizontalEdge rightEdge fa"></i><i
																		class="edge horizontalEdge leftEdge fa"></i><i
																		class="edge verticalEdge bottomEdge fa"></i>
																</div></td>
														</tr>
														<tr class="lines">
															<td colspan="6"><div class="down"></div></td>
														</tr>
														<tr class="lines">
															<td class="right">&nbsp;</td>
															<td class="left top">&nbsp;</td>
															<td class="right top">&nbsp;</td>
															<td class="left top">&nbsp;</td>
															<td class="right top">&nbsp;</td>
															<td class="left">&nbsp;</td>
														</tr>
														<tr class="nodes">
															<td colspan="2"><table>
																	<tbody>
																		<tr>
																			<td><div class="node frontend1">
																					<div class="title">Xiang Xiang</div>
																					<div class="content">UE engineer</div>
																					<i class="edge verticalEdge topEdge fa"></i><i
																						class="edge horizontalEdge rightEdge fa"></i><i
																						class="edge horizontalEdge leftEdge fa"></i>
																				</div></td>
																		</tr>
																	</tbody>
																</table></td>
															<td colspan="2"><table>
																	<tbody>
																		<tr>
																			<td><div class="node frontend1">
																					<div class="title">Dan Dan</div>
																					<div class="content">engineer</div>
																					<i class="edge verticalEdge topEdge fa"></i><i
																						class="edge horizontalEdge rightEdge fa"></i><i
																						class="edge horizontalEdge leftEdge fa"></i>
																				</div></td>
																		</tr>
																	</tbody>
																</table></td>
															<td colspan="2"><table>
																	<tbody>
																		<tr>
																			<td><div class="node frontend1">
																					<div class="title">Zai Zai</div>
																					<div class="content">engineer</div>
																					<i class="edge verticalEdge topEdge fa"></i><i
																						class="edge horizontalEdge rightEdge fa"></i><i
																						class="edge horizontalEdge leftEdge fa"></i>
																				</div></td>
																		</tr>
																	</tbody>
																</table></td>
														</tr>
													</tbody>
												</table></td>
										</tr>
									</tbody>
								</table></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div> 
 </div> 
 </template>
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
<template id="imageview">
  <img class="img-responsive" src="${contextPath}/images/component/image.png" data-preview-src="" data-preview-group="1"/>
</template>
<template id="sidebar">
	<div style="overflow:hidden">
		<div  class="containerComp con" style="width: 10%; min-height: 20%;border:1px solid black;">
		
		<!-- <img  class="img-responsive imgres conim" src="${contextPath}/images/component/menuHandleVL.png"/> -->
			<div class="img-responsive imgres" style="width: 20px; min-height: 20px;border-left-color:red;border-style:solid;border-left-width:20px;border-top-width:20px;border-bottom-width:20px;">
				<div class="imsive" style="position:absolute;border-style:solid;border-left-color:red;"></div>
			</div>
		</div>
		
		
	</div>
</template>
<template id="reviewArea">
	<div style="min-height:200px;">
		<div id="articleComment"></div>
	</div>
</template>


<template id="gridlist">
	<div>
	</div>
</template>

<template id="bridgeseam">
  <div>
    <svg height="400px" width="889px">
        <rect class="mrect" x="16.147058823529413" y="0" width="16.470588235294116" height="360" transform="rotate(15 62.61764705882353,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="64.91176470588235" y="0" width="16.470588235294116" height="360" transform="rotate(16 111.38235294117646,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="113.6764705882353" y="0" width="16.470588235294116" height="360" transform="rotate(13 160.14705882352942,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="162.44117647058826" y="0" width="16.470588235294116" height="360" transform="rotate(8 208.91176470588238,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="211.2058823529412" y="0" width="16.470588235294116" height="360" transform="rotate(7 257.6764705882353,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="259.9705882352941" y="0" width="16.470588235294116" height="360" transform="rotate(6 306.4411764705882,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="308.7352941176471" y="0" width="16.470588235294116" height="360" transform="rotate(5 355.2058823529412,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="357.5" y="0" width="16.470588235294116" height="360" transform="rotate(4 403.97058823529414,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="406.2647058823529" y="0" width="16.470588235294116" height="360" transform=" translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="455.02941176470586" y="0" width="16.470588235294116" height="360" transform="rotate(-4 485.02941176470586,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="503.7941176470588" y="0" width="16.470588235294116" height="360" transform="rotate(-5 533.7941176470588,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="552.5588235294117" y="0" width="16.470588235294116" height="360" transform="rotate(-6 582.5588235294117,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="601.3235294117648" y="0" width="16.470588235294116" height="360" transform="rotate(-7 631.3235294117648,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="650.0882352941177" y="0" width="16.470588235294116" height="360" transform="rotate(-8 680.0882352941177,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="698.8529411764706" y="0" width="16.470588235294116" height="360" transform="rotate(-13 728.8529411764706,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="747.6176470588235" y="0" width="16.470588235294116" height="360" transform="rotate(-16 777.6176470588235,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="mrect" x="796.3823529411765" y="0" width="16.470588235294116" height="360" transform="rotate(-15 826.3823529411765,380) translate(30,20)" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="371.6923076923077" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="359.38461538461536" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="347.0769230769231" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="334.7692307692308" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="322.46153846153845" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="310.15384615384613" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="297.8461538461538" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="285.53846153846155" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="273.2307692307692" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="260.9230769230769" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="248.6153846153846" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="236.3076923076923" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="224" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="211.69230769230768" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="199.3846153846154" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="187.07692307692307" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="174.76923076923075" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="162.46153846153845" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="150.15384615384613" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="137.84615384615384" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="125.53846153846155" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="113.23076923076923" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="100.9230769230769" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="88.61538461538458" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="76.30769230769226" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <rect class="hrect" x="30" y="64" width="829" height="8.307692307692308" fill="#fff" fill-opacity="0" stroke="#000"></rect>
        <circle class="circle circle_1_1" cx="26.567169433307164" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_2" cx="29.865005647844058" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_3" cx="33.16284186238096" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="red" stroke="#000"></circle>
        <circle class="circle circle_1_4" cx="36.460678076917844" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_5" cx="39.758514291454745" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_6" cx="43.05635050599164" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_7" cx="46.35418672052853" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_8" cx="49.652022935065425" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_9" cx="52.94985914960232" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_10" cx="56.24769536413922" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_11" cx="59.545531578676105" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_12" cx="62.843367793213005" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_13" cx="66.1412040077499" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_14" cx="69.43904022228679" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_15" cx="72.73687643682368" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_16" cx="76.03471265136059" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_17" cx="79.33254886589748" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_18" cx="82.63038508043437" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_19" cx="85.92822129497128" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_20" cx="89.22605750950817" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_21" cx="92.52389372404505" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_22" cx="95.82172993858197" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_23" cx="99.11956615311885" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_24" cx="102.41740236765574" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_25" cx="105.71523858219265" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_1_26" cx="109.01307479672954" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_1" cx="75.485136584332" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_2" cx="79.01431056290194" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_3" cx="82.54348454147188" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_4" cx="86.07265852004183" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_5" cx="89.60183249861177" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_6" cx="93.13100647718171" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_7" cx="96.66018045575166" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_8" cx="100.1893544343216" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_9" cx="103.71852841289154" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_10" cx="107.24770239146149" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_11" cx="110.77687637003143" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_12" cx="114.30605034860139" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_13" cx="117.83522432717132" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_14" cx="121.36439830574128" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_15" cx="124.8935722843112" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_16" cx="128.42274626288116" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_17" cx="131.9519202414511" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_18" cx="135.48109422002105" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_19" cx="139.010268198591" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_20" cx="142.53944217716094" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_21" cx="146.06861615573087" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_22" cx="149.59779013430082" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_23" cx="153.12696411287078" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_24" cx="156.6561380914407" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_25" cx="160.18531207001067" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_2_26" cx="163.7144860485806" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_1" cx="123.79422841813695" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_2" cx="126.63568307814388" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_3" cx="129.47713773815082" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_4" cx="132.31859239815773" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_5" cx="135.16004705816468" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_6" cx="138.0015017181716" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_7" cx="140.84295637817854" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_8" cx="143.68441103818546" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_9" cx="146.52586569819238" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_10" cx="149.36732035819932" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_11" cx="152.20877501820624" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_12" cx="155.05022967821319" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_13" cx="157.8916843382201" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_14" cx="160.73313899822705" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_15" cx="163.57459365823397" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_16" cx="166.41604831824088" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_17" cx="169.25750297824783" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_18" cx="172.09895763825475" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_19" cx="174.9404122982617" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_20" cx="177.7818669582686" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_21" cx="180.62332161827555" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_22" cx="183.46477627828247" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_23" cx="186.30623093828942" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_24" cx="189.14768559829633" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_25" cx="191.98914025830328" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_3_26" cx="194.8305949183102" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_1" cx="171.82241893273172" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_2" cx="173.55215228291502" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_3" cx="175.28188563309828" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_4" cx="177.01161898328158" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_5" cx="178.74135233346487" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_6" cx="180.47108568364814" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_7" cx="182.20081903383144" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_8" cx="183.9305523840147" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_9" cx="185.660285734198" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_10" cx="187.39001908438127" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_11" cx="189.11975243456456" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_12" cx="190.84948578474783" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_13" cx="192.57921913493112" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_14" cx="194.3089524851144" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_15" cx="196.03868583529768" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_16" cx="197.76841918548098" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_17" cx="199.49815253566425" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_18" cx="201.22788588584754" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_19" cx="202.9576192360308" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_20" cx="204.6873525862141" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_21" cx="206.41708593639737" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_22" cx="208.14681928658067" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_23" cx="209.87655263676396" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_24" cx="211.60628598694723" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_25" cx="213.33601933713052" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_4_26" cx="215.0657526873138" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_1" cx="220.44234289025806" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_2" cx="221.95353748598612" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_3" cx="223.46473208171417" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_4" cx="224.97592667744223" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_5" cx="226.4871212731703" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_6" cx="227.99831586889835" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_7" cx="229.5095104646264" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_8" cx="231.02070506035446" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_9" cx="232.53189965608252" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_10" cx="234.04309425181057" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_11" cx="235.55428884753863" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_12" cx="237.0654834432667" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_13" cx="238.57667803899474" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_14" cx="240.0878726347228" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_15" cx="241.59906723045086" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_16" cx="243.1102618261789" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_17" cx="244.62145642190697" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_18" cx="246.13265101763503" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_19" cx="247.64384561336308" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_20" cx="249.15504020909114" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_21" cx="250.6662348048192" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_22" cx="252.17742940054725" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_23" cx="253.6886239962753" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_24" cx="255.1998185920034" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_25" cx="256.71101318773145" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_5_26" cx="258.2222077834595" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_1" cx="269.06288611741513" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_2" cx="270.3564767053004" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_3" cx="271.65006729318566" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_4" cx="272.94365788107086" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_5" cx="274.2372484689561" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_6" cx="275.5308390568414" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_7" cx="276.82442964472665" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_8" cx="278.11802023261185" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_9" cx="279.4116108204971" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_10" cx="280.7052014083824" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_11" cx="281.99879199626764" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_12" cx="283.29238258415285" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_13" cx="284.5859731720381" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_14" cx="285.87956375992337" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_15" cx="287.17315434780863" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_16" cx="288.46674493569384" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_17" cx="289.7603355235791" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_18" cx="291.05392611146436" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_19" cx="292.3475166993496" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_20" cx="293.64110728723483" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_21" cx="294.9346978751201" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_22" cx="296.22828846300536" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_23" cx="297.5218790508906" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_24" cx="298.8154696387759" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_25" cx="300.1090602266611" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_6_26" cx="301.40265081454635" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_1" cx="317.68395733789015" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_2" cx="318.76074088897843" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_3" cx="319.8375244400667" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_4" cx="320.914307991155" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_5" cx="321.99109154224334" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_6" cx="323.0678750933316" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_7" cx="324.1446586444199" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_8" cx="325.2214421955082" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_9" cx="326.2982257465965" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_10" cx="327.3750092976848" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_11" cx="328.4517928487731" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_12" cx="329.5285763998614" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_13" cx="330.60535995094966" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_14" cx="331.682143502038" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_15" cx="332.7589270531263" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_16" cx="333.83571060421457" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_17" cx="334.91249415530285" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_18" cx="335.98927770639114" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_19" cx="337.0660612574795" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_20" cx="338.14284480856776" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_21" cx="339.21962835965604" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_22" cx="340.2964119107443" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_23" cx="341.3731954618326" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_24" cx="342.44997901292095" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_25" cx="343.52676256400923" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_7_26" cx="344.6035461150975" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_1" cx="366.3054665842634" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_2" cx="367.166104269722" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_3" cx="368.02674195518057" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_4" cx="368.88737964063915" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_5" cx="369.74801732609774" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_6" cx="370.6086550115563" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_7" cx="371.46929269701496" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_8" cx="372.32993038247355" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_9" cx="373.19056806793213" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_10" cx="374.0512057533907" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_11" cx="374.9118434388493" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_12" cx="375.7724811243079" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_13" cx="376.6331188097665" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_14" cx="377.49375649522506" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_15" cx="378.3543941806837" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_16" cx="379.2150318661423" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_17" cx="380.07566955160087" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_18" cx="380.93630723705945" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_19" cx="381.79694492251804" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_20" cx="382.6575826079766" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_21" cx="383.5182202934352" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_22" cx="384.3788579788938" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_23" cx="385.23949566435243" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_24" cx="386.100133349811" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_25" cx="386.9607710352696" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_8_26" cx="387.8214087207282" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_1" cx="414.5" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_2" cx="414.5" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_3" cx="414.5" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_4" cx="414.5" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_5" cx="414.5" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_6" cx="414.5" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_7" cx="414.5" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_8" cx="414.5" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_9" cx="414.5" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_10" cx="414.5" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_11" cx="414.5" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_12" cx="414.5" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_13" cx="414.5" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_14" cx="414.5" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_15" cx="414.5" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_16" cx="414.5" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_17" cx="414.5" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_18" cx="414.5" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_19" cx="414.5" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_20" cx="414.5" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_21" cx="414.5" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_22" cx="414.5" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_23" cx="414.5" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_24" cx="414.5" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_25" cx="414.5" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_9_26" cx="414.5" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_1" cx="462.6945334157366" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_2" cx="461.833895730278" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_3" cx="460.97325804481943" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_4" cx="460.11262035936085" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_5" cx="459.25198267390226" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_6" cx="458.3913449884437" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_7" cx="457.53070730298504" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_8" cx="456.67006961752645" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_9" cx="455.80943193206787" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_10" cx="454.9487942466093" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_11" cx="454.0881565611507" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_12" cx="453.2275188756921" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_13" cx="452.3668811902335" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_14" cx="451.50624350477494" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_15" cx="450.6456058193163" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_16" cx="449.7849681338577" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_17" cx="448.92433044839913" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_18" cx="448.06369276294055" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_19" cx="447.20305507748196" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_20" cx="446.3424173920234" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_21" cx="445.4817797065648" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_22" cx="444.6211420211062" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_23" cx="443.76050433564757" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_24" cx="442.899866650189" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_25" cx="442.0392289647304" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_10_26" cx="441.1785912792718" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_1" cx="511.31604266210985" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_2" cx="510.23925911102157" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_3" cx="509.1624755599333" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_4" cx="508.085692008845" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_5" cx="507.00890845775666" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_6" cx="505.9321249066684" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_7" cx="504.8553413555801" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_8" cx="503.7785578044918" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_9" cx="502.7017742534035" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_10" cx="501.6249907023152" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_11" cx="500.5482071512269" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_12" cx="499.4714236001386" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_13" cx="498.39464004905034" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_14" cx="497.317856497962" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_15" cx="496.2410729468737" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_16" cx="495.16428939578543" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_17" cx="494.08750584469715" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_18" cx="493.01072229360886" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_19" cx="491.9339387425205" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_20" cx="490.85715519143224" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_21" cx="489.78037164034396" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_22" cx="488.7035880892557" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_23" cx="487.6268045381674" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_24" cx="486.55002098707905" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_25" cx="485.47323743599077" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_11_26" cx="484.3964538849025" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_1" cx="559.9371138825848" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_2" cx="558.6435232946995" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_3" cx="557.3499327068143" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_4" cx="556.056342118929" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_5" cx="554.7627515310438" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_6" cx="553.4691609431586" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_7" cx="552.1755703552733" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_8" cx="550.8819797673881" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_9" cx="549.5883891795028" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_10" cx="548.2947985916176" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_11" cx="547.0012080037324" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_12" cx="545.707617415847" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_13" cx="544.4140268279618" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_14" cx="543.1204362400766" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_15" cx="541.8268456521913" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_16" cx="540.5332550643061" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_17" cx="539.2396644764208" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_18" cx="537.9460738885356" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_19" cx="536.6524833006504" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_20" cx="535.358892712765" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_21" cx="534.0653021248798" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_22" cx="532.7717115369946" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_23" cx="531.4781209491093" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_24" cx="530.1845303612241" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_25" cx="528.8909397733388" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_12_26" cx="527.5973491854536" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_1" cx="608.557657109742" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_2" cx="607.0464625140139" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_3" cx="605.5352679182859" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_4" cx="604.0240733225578" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_5" cx="602.5128787268297" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_6" cx="601.0016841311017" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_7" cx="599.4904895353736" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_8" cx="597.9792949396456" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_9" cx="596.4681003439175" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_10" cx="594.9569057481895" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_11" cx="593.4457111524614" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_12" cx="591.9345165567333" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_13" cx="590.4233219610053" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_14" cx="588.9121273652772" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_15" cx="587.4009327695492" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_16" cx="585.8897381738211" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_17" cx="584.3785435780931" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_18" cx="582.867348982365" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_19" cx="581.356154386637" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_20" cx="579.8449597909089" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_21" cx="578.3337651951808" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_22" cx="576.8225705994528" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_23" cx="575.3113760037247" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_24" cx="573.8001814079967" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_25" cx="572.2889868122686" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_13_26" cx="570.7777922165405" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_1" cx="657.1775810672683" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_2" cx="655.4478477170851" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_3" cx="653.7181143669018" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_4" cx="651.9883810167184" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_5" cx="650.2586476665352" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_6" cx="648.5289143163519" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_7" cx="646.7991809661686" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_8" cx="645.0694476159854" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_9" cx="643.3397142658021" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_10" cx="641.6099809156187" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_11" cx="639.8802475654355" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_12" cx="638.1505142152522" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_13" cx="636.420780865069" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_14" cx="634.6910475148857" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_15" cx="632.9613141647023" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_16" cx="631.2315808145191" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_17" cx="629.5018474643358" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_18" cx="627.7721141141525" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_19" cx="626.0423807639693" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_20" cx="624.312647413786" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_21" cx="622.5829140636026" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_22" cx="620.8531807134194" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_23" cx="619.1234473632361" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_24" cx="617.3937140130528" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_25" cx="615.6639806628696" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_14_26" cx="613.9342473126862" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_1" cx="705.2057715818631" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_2" cx="702.3643169218561" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_3" cx="699.5228622618492" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_4" cx="696.6814076018424" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_5" cx="693.8399529418353" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_6" cx="690.9984982818285" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_7" cx="688.1570436218215" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_8" cx="685.3155889618146" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_9" cx="682.4741343018077" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_10" cx="679.6326796418007" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_11" cx="676.7912249817938" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_12" cx="673.9497703217869" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_13" cx="671.1083156617799" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_14" cx="668.266861001773" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_15" cx="665.4254063417661" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_16" cx="662.5839516817591" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_17" cx="659.7424970217522" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_18" cx="656.9010423617453" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_19" cx="654.0595877017383" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_20" cx="651.2181330417314" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_21" cx="648.3766783817246" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_22" cx="645.5352237217176" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_23" cx="642.6937690617107" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_24" cx="639.8523144017037" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_25" cx="637.0108597416968" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_15_26" cx="634.1694050816899" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_1" cx="753.5148634156681" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_2" cx="749.9856894370981" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_3" cx="746.4565154585281" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_4" cx="742.9273414799582" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_5" cx="739.3981675013882" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_6" cx="735.8689935228183" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_7" cx="732.3398195442484" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_8" cx="728.8106455656784" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_9" cx="725.2814715871085" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_10" cx="721.7522976085386" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_11" cx="718.2231236299685" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_12" cx="714.6939496513986" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_13" cx="711.1647756728287" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_14" cx="707.6356016942588" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_15" cx="704.1064277156888" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_16" cx="700.5772537371189" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_17" cx="697.048079758549" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_18" cx="693.5189057799789" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_19" cx="689.989731801409" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_20" cx="686.4605578228391" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_21" cx="682.9313838442691" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_22" cx="679.4022098656992" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_23" cx="675.8730358871293" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_24" cx="672.3438619085593" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_25" cx="668.8146879299893" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_16_26" cx="665.2855139514194" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_1" cx="802.4328305666928" cy="374.8461538461538" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_2" cx="799.1349943521559" cy="362.5384615384615" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_3" cx="795.8371581376191" cy="350.2307692307692" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_4" cx="792.5393219230822" cy="337.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_5" cx="789.2414857085453" cy="325.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_6" cx="785.9436494940084" cy="313.30769230769226" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_7" cx="782.6458132794714" cy="300.99999999999994" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_8" cx="779.3479770649346" cy="288.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_9" cx="776.0501408503977" cy="276.38461538461536" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_10" cx="772.7523046358608" cy="264.07692307692304" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_11" cx="769.4544684213239" cy="251.76923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_12" cx="766.156632206787" cy="239.46153846153845" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_13" cx="762.8587959922501" cy="227.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_14" cx="759.5609597777133" cy="214.84615384615384" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_15" cx="756.2631235631764" cy="202.53846153846155" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_16" cx="752.9652873486394" cy="190.23076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_17" cx="749.6674511341025" cy="177.9230769230769" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_18" cx="746.3696149195656" cy="165.6153846153846" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_19" cx="743.0717787050287" cy="153.3076923076923" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_20" cx="739.7739424904919" cy="141" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_21" cx="736.476106275955" cy="128.6923076923077" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_22" cx="733.1782700614181" cy="116.38461538461539" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_23" cx="729.8804338468811" cy="104.07692307692307" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_24" cx="726.5825976323442" cy="91.76923076923075" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_25" cx="723.2847614178073" cy="79.46153846153842" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
        <circle class="circle circle_17_26" cx="719.9869252032705" cy="67.15384615384616" transform=" translate(30,0)" r="3.153846153846154" fill="#87CEFA" stroke="#000"></circle>
    </svg>
</div>
</template>
<template id="definedcard">
	<div class="definedCardExt">
		<div class="definedCardExt_header">
			<p><span style="color: rgb(255, 255, 255);">ssssssssssssssssss</span>		</p>
		</div>
		<div class="definedCardExt_content" style="height: 400px; overflow-y: scroll;">
			<div class="definedCardExt_list">
				<ul>
					<li class="list_item" item-index="0">
						<p>
							<img class="card-cover" src="/glaf/scripts/plugins/bootstrap/definedCard/image/Desert.jpg" alt="1484814525461011863.jpg" width="250" height="250" border="0" vspace="0" title="1484814525461011863.jpg" style="width: 250px; height: 250px;"> &nbsp; &nbsp;
						</p>
						<p class="color-gray">发表于 2015/01/15	</p>
						<p>	此处是内容...</p>
						<p></p>
					</li>
					<li class="list_item" item-index="1">
						<p>
							<img class="card-cover" src="/glaf/scripts/plugins/bootstrap/definedCard/image/Desert.jpg" alt="1484814525461011863.jpg" width="250" height="250" border="0" vspace="0" title="1484814525461011863.jpg" style="width: 250px; height: 250px;"> &nbsp; &nbsp;
						</p>
						<p class="color-gray">发表于 2015/01/15	</p>
						<p>	此处是内容...</p>
						<p></p>
					</li>
					<li class="list_item" item-index="2">
						<p>
							<img class="card-cover" src="/glaf/scripts/plugins/bootstrap/definedCard/image/Desert.jpg" alt="1484814525461011863.jpg" width="250" height="250" border="0" vspace="0" title="1484814525461011863.jpg" style="width: 250px; height: 250px;"> &nbsp; &nbsp;
						</p>
						<p class="color-gray">发表于 2015/01/15	</p>
						<p>	此处是内容...</p>
						<p></p>
					</li>
					<li class="list_item" item-index="3">
						<p>
							<img class="card-cover" src="/glaf/scripts/plugins/bootstrap/definedCard/image/Desert.jpg" alt="1484814525461011863.jpg" width="250" height="250" border="0" vspace="0" title="1484814525461011863.jpg" style="width: 250px; height: 250px;"> &nbsp; &nbsp;
						</p>
						<p class="color-gray">发表于 2015/01/15	</p>
						<p>	此处是内容...</p>
						<p></p>
					</li>
					<li class="list_item" item-index="4">
						<p>
							<img class="card-cover" src="/glaf/scripts/plugins/bootstrap/definedCard/image/Desert.jpg" alt="1484814525461011863.jpg" width="250" height="250" border="0" vspace="0" title="1484814525461011863.jpg" style="width: 250px; height: 250px;"> &nbsp; &nbsp;
						</p>
						<p class="color-gray">发表于 2015/01/15	</p>
						<p>	此处是内容...</p>
						<p></p>
					</li>
					<li class="text-center more list_item">
						<a href="javascript:;" total="33">加载更多</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</template>
<template id="editor">
<div>
	<div id="edui1" class="edui-editor  edui-default"
		style="width: 500px; z-index: 999;">
		<div id="edui1_toolbarbox" class="edui-editor-toolbarbox edui-default">
			<div id="edui1_toolbarboxouter"
				class="edui-editor-toolbarboxouter edui-default">
				<div class="edui-editor-toolbarboxinner edui-default">
					<div id="edui2" class="edui-toolbar   edui-default"
						onselectstart="return false;"
						onmousedown="return $EDITORUI[&quot;edui2&quot;]._onMouseDown(event, this);"
						style="user-select: none;">
						<div id="edui3"
							class="edui-box edui-button edui-for-fullscreen edui-default">
							<div id="edui3_state"
								onmousedown="$EDITORUI[&quot;edui3&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui3&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui3&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui3&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui3_body" unselectable="on" title="全屏"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui3&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui3&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui4"
							class="edui-box edui-splitbutton edui-for-emotion edui-default">
							<div title="表情" id="edui4_state"
								onmousedown="$EDITORUI[&quot;edui4&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui4&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui4&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui4&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-splitbutton-body edui-default">
									<div id="edui4_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui4&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui4&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui6"
							class="edui-box edui-button edui-for-simpleupload edui-default">
							<div id="edui6_state"
								onmousedown="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui6&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui6_body" unselectable="on" title="单图上传"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui6&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui6&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default">
											<iframe
												style="display: block; width: 20px; height: 20px; overflow: hidden; border: 0px; margin: 0px; padding: 0px; position: absolute; top: 0px; left: 0px; opacity: 0; cursor: pointer;"></iframe>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui7"
							class="edui-box edui-button edui-for-source edui-default">
							<div id="edui7_state"
								onmousedown="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui7&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui7_body" unselectable="on" title="源代码"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui7&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui7&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui8"
							class="edui-box edui-button edui-for-undo edui-default">
							<div id="edui8_state"
								onmousedown="$EDITORUI[&quot;edui8&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui8&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui8&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui8&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui8_body" unselectable="on" title="撤销"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui8&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui8&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui9"
							class="edui-box edui-button edui-for-redo edui-default">
							<div id="edui9_state"
								onmousedown="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui9&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui9_body" unselectable="on" title="重做"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui9&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui9&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui10"
							class="edui-box edui-button edui-for-bold edui-default">
							<div id="edui10_state"
								onmousedown="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui10&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui10_body" unselectable="on" title="加粗"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui10&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui10&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui11"
							class="edui-box edui-button edui-for-italic edui-default">
							<div id="edui11_state"
								onmousedown="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui11&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui11_body" unselectable="on" title="斜体"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui11&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui11&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui12"
							class="edui-box edui-button edui-for-underline edui-default">
							<div id="edui12_state"
								onmousedown="$EDITORUI[&quot;edui12&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui12&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui12&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui12&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui12_body" unselectable="on" title="下划线"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui12&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui12&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui13"
							class="edui-box edui-button edui-for-fontborder edui-default">
							<div id="edui13_state"
								onmousedown="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui13&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui13_body" unselectable="on" title="字符边框"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui13&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui13&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui14"
							class="edui-box edui-splitbutton edui-for-backcolor edui-default edui-colorbutton">
							<div title="背景色" id="edui14_state"
								onmousedown="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui14&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-splitbutton-body edui-default">
									<div id="edui14_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui14&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div id="edui14_colorlump" class="edui-colorlump"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui14&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui17" class="edui-box edui-separator  edui-default"></div>
						<div id="edui18"
							class="edui-box edui-menubutton edui-for-rowspacingtop edui-default">
							<div title="段前距" id="edui18_state"
								onmousedown="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui18&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-menubutton-body edui-default">
									<div id="edui18_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui18&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui18&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui25"
							class="edui-box edui-menubutton edui-for-rowspacingbottom edui-default">
							<div title="段后距" id="edui25_state"
								onmousedown="$EDITORUI[&quot;edui25&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui25&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui25&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui25&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-menubutton-body edui-default">
									<div id="edui25_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui25&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui25&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui32"
							class="edui-box edui-menubutton edui-for-lineheight edui-default">
							<div title="行间距" id="edui32_state"
								onmousedown="$EDITORUI[&quot;edui32&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui32&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui32&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui32&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-menubutton-body edui-default">
									<div id="edui32_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui32&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui32&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui41" class="edui-box edui-separator  edui-default"></div>
						<div id="edui42"
							class="edui-box edui-combox edui-for-fontsize edui-default">
							<div title="字号" id="edui42_state"
								onmousedown="$EDITORUI[&quot;edui42&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui42&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui42&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui42&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-combox-body edui-default">
									<div id="edui42_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui42&quot;]._onButtonClick(event, this);">字号</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui42&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui53"
							class="edui-box edui-combox edui-for-fontfamily edui-default">
							<div title="字体" id="edui53_state"
								onmousedown="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui53&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-combox-body edui-default">
									<div id="edui53_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui53&quot;]._onButtonClick(event, this);">字体</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui53&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui66"
							class="edui-box edui-button edui-for-justifyleft edui-default">
							<div id="edui66_state"
								onmousedown="$EDITORUI[&quot;edui66&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui66&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui66&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui66&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui66_body" unselectable="on" title="居左对齐"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui66&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui66&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui67"
							class="edui-box edui-button edui-for-justifyright edui-default">
							<div id="edui67_state"
								onmousedown="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui67&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui67_body" unselectable="on" title="居右对齐"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui67&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui67&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui68"
							class="edui-box edui-button edui-for-justifycenter edui-default">
							<div id="edui68_state"
								onmousedown="$EDITORUI[&quot;edui68&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui68&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui68&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui68&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui68_body" unselectable="on" title="居中对齐"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui68&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui68&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui69"
							class="edui-box edui-button edui-for-justifyjustify edui-default">
							<div id="edui69_state"
								onmousedown="$EDITORUI[&quot;edui69&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui69&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui69&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui69&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui69_body" unselectable="on" title="两端对齐"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui69&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui69&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui70"
							class="edui-box edui-button edui-for-strikethrough edui-default">
							<div id="edui70_state"
								onmousedown="$EDITORUI[&quot;edui70&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui70&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui70&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui70&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui70_body" unselectable="on" title="删除线"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui70&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui70&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui71"
							class="edui-box edui-button edui-for-superscript edui-default">
							<div id="edui71_state"
								onmousedown="$EDITORUI[&quot;edui71&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui71&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui71&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui71&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui71_body" unselectable="on" title="上标"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui71&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui71&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui72"
							class="edui-box edui-button edui-for-subscript edui-default">
							<div id="edui72_state"
								onmousedown="$EDITORUI[&quot;edui72&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui72&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui72&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui72&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui72_body" unselectable="on" title="下标"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui72&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui72&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui73"
							class="edui-box edui-button edui-for-removeformat edui-default">
							<div id="edui73_state"
								onmousedown="$EDITORUI[&quot;edui73&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui73&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui73&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui73&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui73_body" unselectable="on" title="清除格式"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui73&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui73&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui74"
							class="edui-box edui-button edui-for-formatmatch edui-default">
							<div id="edui74_state"
								onmousedown="$EDITORUI[&quot;edui74&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui74&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui74&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui74&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui74_body" unselectable="on" title="格式刷"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui74&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui74&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui75"
							class="edui-box edui-splitbutton edui-for-autotypeset edui-default">
							<div title="自动排版" id="edui75_state"
								onmousedown="$EDITORUI[&quot;edui75&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui75&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui75&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui75&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-splitbutton-body edui-default">
									<div id="edui75_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui75&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui75&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui78"
							class="edui-box edui-button edui-for-blockquote edui-default">
							<div id="edui78_state"
								onmousedown="$EDITORUI[&quot;edui78&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui78&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui78&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui78&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui78_body" unselectable="on" title="引用"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui78&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui78&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui79"
							class="edui-box edui-button edui-for-pasteplain edui-default">
							<div id="edui79_state"
								onmousedown="$EDITORUI[&quot;edui79&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui79&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui79&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui79&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui79_body" unselectable="on" title="纯文本粘贴模式"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui79&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui79&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui80" class="edui-box edui-separator  edui-default"></div>
						<div id="edui81"
							class="edui-box edui-splitbutton edui-for-forecolor edui-default edui-colorbutton">
							<div title="字体颜色" id="edui81_state"
								onmousedown="$EDITORUI[&quot;edui81&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui81&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui81&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui81&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-splitbutton-body edui-default">
									<div id="edui81_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui81&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div id="edui81_colorlump" class="edui-colorlump"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui81&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui84"
							class="edui-box edui-splitbutton edui-for-backcolor edui-default edui-colorbutton">
							<div title="背景色" id="edui84_state"
								onmousedown="$EDITORUI[&quot;edui84&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui84&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui84&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui84&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-splitbutton-body edui-default">
									<div id="edui84_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui84&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div id="edui84_colorlump" class="edui-colorlump"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui84&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui87"
							class="edui-box edui-menubutton edui-for-insertorderedlist edui-default">
							<div title="有序列表" id="edui87_state"
								onmousedown="$EDITORUI[&quot;edui87&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui87&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui87&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui87&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-menubutton-body edui-default">
									<div id="edui87_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui87&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui87&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui100"
							class="edui-box edui-menubutton edui-for-insertunorderedlist edui-default">
							<div title="无序列表" id="edui100_state"
								onmousedown="$EDITORUI[&quot;edui100&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui100&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui100&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui100&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-menubutton-body edui-default">
									<div id="edui100_button_body"
										class="edui-box edui-button-body edui-default"
										onclick="$EDITORUI[&quot;edui100&quot;]._onButtonClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
									<div class="edui-box edui-splitborder edui-default"></div>
									<div class="edui-box edui-arrow edui-default"
										onclick="$EDITORUI[&quot;edui100&quot;]._onArrowClick();"></div>
								</div>
							</div>
						</div>
						<div id="edui107"
							class="edui-box edui-button edui-for-selectall edui-default">
							<div id="edui107_state"
								onmousedown="$EDITORUI[&quot;edui107&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui107&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui107&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui107&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui107_body" unselectable="on" title="全选"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui107&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui107&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui108"
							class="edui-box edui-button edui-for-cleardoc edui-default">
							<div id="edui108_state"
								onmousedown="$EDITORUI[&quot;edui108&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui108&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui108&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui108&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui108_body" unselectable="on" title="清空文档"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui108&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui108&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
						<div id="edui113"
							class="edui-box edui-button edui-for-help edui-default">
							<div id="edui113_state"
								onmousedown="$EDITORUI[&quot;edui113&quot;].Stateful_onMouseDown(event, this);"
								onmouseup="$EDITORUI[&quot;edui113&quot;].Stateful_onMouseUp(event, this);"
								onmouseover="$EDITORUI[&quot;edui113&quot;].Stateful_onMouseOver(event, this);"
								onmouseout="$EDITORUI[&quot;edui113&quot;].Stateful_onMouseOut(event, this);"
								class="edui-default">
								<div class="edui-button-wrap edui-default">
									<div id="edui113_body" unselectable="on" title="帮助"
										class="edui-button-body edui-default"
										onmousedown="return $EDITORUI[&quot;edui113&quot;]._onMouseDown(event, this);"
										onclick="return $EDITORUI[&quot;edui113&quot;]._onClick(event, this);">
										<div class="edui-box edui-icon edui-default"></div>
										<div class="edui-box edui-label edui-default"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="edui1_toolbarmsg"
				class="edui-editor-toolbarmsg edui-default" style="display: none;">
				<div id="edui1_upload_dialog"
					class="edui-editor-toolbarmsg-upload edui-default"
					onclick="$EDITORUI[&quot;edui1&quot;].showWordImageDialog();">点击上传</div>
				<div class="edui-editor-toolbarmsg-close edui-default"
					onclick="$EDITORUI[&quot;edui1&quot;].hideToolbarMsg();">x</div>
				<div id="edui1_toolbarmsg_label"
					class="edui-editor-toolbarmsg-label edui-default"></div>
				<div style="height: 0; overflow: hidden; clear: both;"
					class="edui-default"></div>
			</div>
			<div id="edui1_message_holder"
				class="edui-editor-messageholder edui-default"
				style="top: 82px; z-index: 1000;"></div>
		</div>
		<div id="edui1_iframeholder"
			class="edui-editor-iframeholder edui-default"
			style="width: 500px; height: 500px; z-index: 999; overflow: hidden;">
			<iframe id="ueditor_0" width="100%" height="100%" frameborder="0"
				src="javascript:void(function(){document.open();document.write(&quot;<!DOCTYPE html><html xmlns='http://www.w3.org/1999/xhtml' class='view' ><head><style type='text/css'>.view{padding:0;word-wrap:break-word;cursor:text;height:90%;}
body{margin:8px;font-family:sans-serif;font-size:16px;}p{margin:5px 0;}</style><link rel='stylesheet' type='text/css' href='http://127.0.0.1:8080/glaf/scripts/ueditor/themes/iframe.css'/></head><body class='view' ></body><script type='text/javascript'  id='_initialScript'>setTimeout(function(){editor = window.parent.UE.instants['ueditorInstant0'];editor._setup(document);},0);var _tmpScript = document.getElementById('_initialScript');_tmpScript.parentNode.removeChild(_tmpScript);</script></html>&quot;);document.close();}())"></iframe>
		</div>
		<div id="edui1_bottombar"
			class="edui-editor-bottomContainer edui-default">
			<table class="edui-default">
				<tbody class="edui-default">
					<tr class="edui-default">
						<td id="edui1_elementpath"
							class="edui-editor-bottombar edui-default"><div
								class="edui-editor-breadcrumb">元素路径:</div></td>
						<td id="edui1_wordcount"
							class="edui-editor-wordcount edui-default">字数统计</td>
						<td id="edui1_scale" class="edui-editor-scale edui-default"
							style="display: none;"><div
								class="edui-editor-icon edui-default"></div></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="edui1_scalelayer" class="edui-default"></div>
	</div>
</div>

</template>
<template id="switch">
	<div>
		<div class="switch_content">
			<input type="checkbox" class="switch_input" checked />
		</div>
	</div>
</template>
<template id="radialindicator">
    <div>
    <div class="prg-cont rad-prg" id="indicatorContainer">
		  <img src="${contextPath}/images/component/radialindicator.png" />	
	</div>
	</div>
	
</template>

<template id="rangeSlider">
	<div style="position: relative;">
    	<div id="process">
    		<div class="progress">
   				 <div class="progress-bar" role="progressbar" aria-valuenow="60" 
       				 aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
        	         <span class="sr-only">40% 完成</span>
    	         </div>
	        </div>
    	</div>
    	<div>
       	 	<input style="display:none" type="text" id="range" value="" name="range" />
    	</div>
	</div>
</template>

<template id="fullpage">
	<div id="fullpages">
		<div class="section"  data-centered="true">
		</div>
	</div>
</template>
<template id="ztree">
	<div>
		<ul class='ztree'></ul>
	</div>
</template>
<template id="custom">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/custom/image/custom.png" />	
	</div>
</template>
<template id="echarts_panel">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/echarts/img/echarts_panel.png" />	
	</div>
</template>
<template id="echarts_line">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/echarts/img/echarts_line.png" />	
	</div>
</template>
<template id="echarts_cross">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/echarts/img/echarts_cross.png" />	
	</div>
</template>
<template id="tabbar">

<div class="weui-tab" style="min-height:200px">
 <div class="weui-tabbar">
        <a href="#tab1" class="weui-tabbar__item weui-bar__item--on">
          <div class="weui-tabbar__icon">
            <i class="icon-list" style="margin-top:5px"></i>
          </div>
          <p class="weui-tabbar__label id frame-variable" contenteditable="true" frame-variable="fv1">1</p>
        </a>
        <a href="#tab2" class="weui-tabbar__item">
          <div class="weui-tabbar__icon">
            <i class="icon-check" style="margin-top:5px"></i>
          </div>
          <p class="weui-tabbar__label id frame-variable" contenteditable="true" frame-variable="fv1">2</p>
        </a>
        <a href="#tab3" class="weui-tabbar__item">
          <div class="weui-tabbar__icon">
            <img src="${contextPath}/scripts/jquery-weui-master/demos/images/icon_nav_article.png" alt="">
          </div>
          <p class="weui-tabbar__label id frame-variable" contenteditable="true" frame-variable="fv1">3</p>
        </a>
        <a href="#tab4" class="weui-tabbar__item">
          <div class="weui-tabbar__icon">
            <img src="${contextPath}/scripts/jquery-weui-master/demos/images/icon_nav_cell.png" alt="">
          </div>
          <p class="weui-tabbar__label id frame-variable" contenteditable="true" frame-variable="fv1">4</p>
        </a>
      </div>
      <div class="weui-tab__bd">
        <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active containerComp" style="min-height:100px;max-height:auto">
          
        </div>
        <div id="tab2" class="weui-tab__bd-item containerComp" style="min-height:100px;max-height:auto">
          
        </div>
        <div id="tab3" class="weui-tab__bd-item containerComp" style="min-height:100px;max-height:auto">
         
        </div>
        <div id="tab4" class="weui-tab__bd-item containerComp" style="min-height:100px;max-height:auto">
          
        </div>
      </div>

     
    </div>
    
</template>
<template id="photoSwipe">
<article class="htmleaf-container">
	<div class="row">
		<div id="demo-test-gallery" class="demo-gallery">
			<a href="${contextPath}/scripts/plugins/bootstrap/photoSwipe/img/15008518202_c265dfa55f_h.jpg" data-size="1600x1600" data-med="img/15008465772_d50c8f0531_h.jpg" data-med-size="1024x1024" data-author="Folkert Gorter" class="demo-gallery__img--main">
				<img src="${contextPath}/scripts/plugins/bootstrap/photoSwipe/img/15008518202_b016d7d289_m.jpg" alt="" />
				<figure>This is dummy caption.</figure>
			</a>
			<a href="${contextPath}/scripts/plugins/bootstrap/photoSwipe/img/15008465772_d50c8f0531_h.jpg" data-size="1600x1067" data-med="img/15008465772_d50c8f0531_h.jpg" data-med-size="1024x683" data-author="Thomas Lefebvre">
			  <img src="${contextPath}/scripts/plugins/bootstrap/photoSwipe/img/15008465772_383e697089_m.jpg" alt="" />
			  <figure>It's a dummy caption. He who searches for meaning here will be sorely disappointed.</figure>
			</a>
			
		</div>
	</div>

	<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

		<div class="pswp__bg"></div>

		<div class="pswp__scroll-wrap">

			<div class="pswp__container">
				<div class="pswp__item"></div>
				<div class="pswp__item"></div>
				<div class="pswp__item"></div>
			</div>

			<div class="pswp__ui pswp__ui--hidden">

				<div class="pswp__top-bar">

					<div class="pswp__counter"></div>

					<button class="pswp__button pswp__button--close" title="Close (Esc)"></button>

					<button class="pswp__button pswp__button--share" title="Share"></button>

					<button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>

					<button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

					<div class="pswp__preloader">
						<div class="pswp__preloader__icn">
						  <div class="pswp__preloader__cut">
							<div class="pswp__preloader__donut"></div>
						  </div>
						</div>
					</div>
				</div>

				<div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
					<div class="pswp__share-tooltip"></div> 
				</div>

				<button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
				</button>

				<button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
				</button>

				<div class="pswp__caption">
					<div class="pswp__caption__center"></div>
				</div>

			  </div>

			</div>

	</div>
</article>
</template>

<template id="echarts_rose">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/echarts/img/echarts_rose.png" />	
	</div>
</template>
<template id="echarts_heatmap">
	<div>
		  <img src="${contextPath}/scripts/plugins/bootstrap/echarts/img/echarts_heatmap.png" />	
	</div>
</template>
<template id="ratylimaster">
	
	 <div>
	 <div id="demo3" class="ratymode">
		<h2>
			<div id="demo3" class="ratymode rated" data-rate="3" data-ratemax="5">
				<span class="rate rate-full" style="cursor: default;">★</span><span
					class="rate rate-full" style="cursor: default;">★</span><span
					class="rate rate-full" style="cursor: default;">★</span><span
					class="rate rate-empty" style="cursor: default;">☆</span><span
					class="rate rate-empty" style="cursor: default;">☆</span>
		</h2>
		<span class="ratyli"></span>
    </div>
	 </div> 
</template>

<template id="jplayer">
		
	<div>
	<div id="jquery_jplayer_2" class="jp-jplayer"></div>
	<br>
	<div id="jp_container_2" class="jp-audio" role="application"
		aria-label="media player">
		<div class="jp-type-playlist">
			<div class="jp-gui jp-interface">
				<div class="jp-controls">
					<button class="jp-previous" role="button" tabindex="0">previous</button>
					<button class="jp-play" role="button" tabindex="0">play</button>
					<button class="jp-next" role="button" tabindex="0">next</button>
					<button class="jp-stop" role="button" tabindex="0">stop</button>
				</div>
				<div class="jp-progress">
					<div class="jp-seek-bar">
						<div class="jp-play-bar"></div>
					</div>
				</div>
				<div class="jp-volume-controls">
					<button class="jp-mute" role="button" tabindex="0">mute</button>
					<button class="jp-volume-max" role="button" tabindex="0">max
						volume</button>
					<div class="jp-volume-bar">
						<div class="jp-volume-bar-value"></div>
					</div>
				</div>
				<div class="jp-time-holder">
					<div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
					<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
				</div>
				
			</div>
			<div class="jp-playlist">
				<ul>
					<li>&nbsp;</li>
				</ul>
			</div>
			<div class="jp-no-solution">
				<span>Update Required</span> To play the media you will need to
				either update your browser to a recent version or update your <a
					href="http://get.adobe.com/flashplayer/" target="_blank">Flash
					plugin</a>.
			</div>
		</div>
	</div>
</div>
</template>
<template id="fieldset">
	
	 <fieldset class="myFieldset"> 
        <legend>AAAAA</legend> 
        <div class="containerComp" style="min-height:100px"></div> 
    </fieldset> 
</template>
<template id="prompt">
   
	  <div id="promptset" class="tooltipmpt" role="tooltip">
	    <div class="tooltip-arrow"></div>
	    <div class="tooltip-inner"></div>
	    <div class="containerComp containprompt" style="border:1px solid #F00;min-height:200px;width:100%"><div>
	 
	 </div>
</template>
<template id="popover">
   
	  <div id="popovercomp" class="popoverCoper" role="tooltip">
	 		<h3 class="popover-title"></h3>
	 		<div class="popover-content"></div>
	 		<div class="containerComp popovertain" style="border-width:1px;border-style:solid;border-color:gray;min-height:200px;width:100%">&nbsp;</div>
	 </div> 
</template>

<template id="selectpicker">
	<input type="text" class = "form-control id frame-variable component mui-btn mui-btn-block" select="picker" frame-variable="fv1" placeholder="选择器" > 
</template>

<template id="definedpanel">
	<div class="definedPanel inlineContent"> 

	</div>
</template>

<template id="webchat">
	<div class="mt_webchat" data-role="webchat" style="height: 500px;">
        <div class="mt_webchat_left" >
            <div class="mt_webchat_chat" >
                <div class="mt_webchat_notice">
                    <i class="fa fa-bell"></i>&nbsp;[我是水]进入聊天室，当前在线人数为2位
                </div>
                <div class="mt_webchat_message" >
                    <font color="green">我是水:</font> 哈哈哈
                </div>
                <div class="mt_webchat_message">
                    <font color="green">系统管理员:</font> 你在笑什么
                </div>
                <div class="mt_webchat_message">
                    <font color="green">我是水:</font> 没有，我开心
                </div>
            </div>
            <div class="mt_webchat_send containerComp" >
                <!-- <div class="mt_webchat_input" >
                    <textarea placeholder="请输入内容点击发送"></textarea> 
                </div>
                <div class="mt_webchat_button">
                    <input class="btn btn-default" type="button" value="发送">
                </div> -->
            </div> 
        </div>
        <div class="mt_webchat_right containerComp" >
            <!-- <div class="mt_webchat_title" >在线用户列表</div>
            <div class="mt_webchat_user" >系统管理员</div>
            <div class="mt_webchat_user" >我是水</div> -->
        </div> 
    </div>
</template>
<template id="dhxgantt">
	<div style="width:100%; min-height:200px;">
	<div class="gantt_container"><div class="gantt_grid" role="treegrid" aria-multiselectable="false" style="height: 180px; width: 359px;"><div class="gantt_grid_scale" role="row" style="height: 34px; line-height: 33px; width: 359px;"><div class="gantt_grid_head_cell gantt_grid_head_text  " style="width:156px;" role="columnheader" aria-label="Task name" column_id="text">Task name</div><div class="gantt_grid_head_cell gantt_grid_head_start_date  " style="width:90px;" role="columnheader" aria-label="Start time" column_id="start_date">Start time</div><div class="gantt_grid_head_cell gantt_grid_head_duration  " style="width:70px;" role="columnheader" aria-label="Duration" column_id="duration">Duration</div><div class="gantt_grid_head_cell gantt_grid_head_add gantt_last_cell " style="width:43px;" role="button" aria-label="New task" column_id="add"></div><div class="gantt_grid_column_resize_wrap" column_index="0" role="separator" style="top: 0px; height: 35px; left: 156px;"><div class="gantt_grid_column_resize"></div></div><div class="gantt_grid_column_resize_wrap" column_index="1" role="separator" style="top: 0px; height: 35px; left: 246px;"><div class="gantt_grid_column_resize"></div></div></div><div class="gantt_grid_data" role="rowgroup" data-layer="true" style="height: 145px; width: 359px;"><div class="gantt_row" task_id="1" aria-label=" Task: Project #2 Start date: 2013-04-01 End date: 2013-04-19" aria-selected="false" role="row" aria-level="0" aria-expanded="true" style="height: 35px; line-height: 35px;"><div class="gantt_cell" style="width:156px;" role="gridcell" aria-label="Project #2"><div class="gantt_tree_icon gantt_close"></div><div class="gantt_tree_icon gantt_folder_open"></div><div class="gantt_tree_content">Project #2</div></div><div class="gantt_cell" style="width:90px;text-align:center;" role="gridcell" aria-label="2013-04-01"><div class="gantt_tree_content">2013-04-01</div></div><div class="gantt_cell" style="width:70px;text-align:center;" role="gridcell" aria-label="18"><div class="gantt_tree_content">18</div></div><div class="gantt_cell gantt_last_cell" style="width:43px;" role="gridcell" aria-label=""><div role="button" aria-label="New task" class="gantt_add"></div></div></div><div class="gantt_row odd" task_id="2" aria-label=" Task: Task #1 Start date: 2013-04-02 End date: 2013-04-10" aria-selected="false" role="row" aria-level="1" style="height: 35px; line-height: 35px;"><div class="gantt_cell" style="width:156px;" role="gridcell" aria-label="Task #1"><div class="gantt_tree_indent"></div><div class="gantt_tree_icon gantt_blank"></div><div class="gantt_tree_icon gantt_file"></div><div class="gantt_tree_content">Task #1</div></div><div class="gantt_cell" style="width:90px;text-align:center;" role="gridcell" aria-label="2013-04-02"><div class="gantt_tree_content">2013-04-02</div></div><div class="gantt_cell" style="width:70px;text-align:center;" role="gridcell" aria-label="8"><div class="gantt_tree_content">8</div></div><div class="gantt_cell gantt_last_cell" style="width:43px;" role="gridcell" aria-label=""><div role="button" aria-label="New task" class="gantt_add"></div></div></div><div class="gantt_row" task_id="3" aria-label=" Task: Task #2 Start date: 2013-04-11 End date: 2013-04-19" aria-selected="false" role="row" aria-level="1" style="height: 35px; line-height: 35px;"><div class="gantt_cell" style="width:156px;" role="gridcell" aria-label="Task #2"><div class="gantt_tree_indent"></div><div class="gantt_tree_icon gantt_blank"></div><div class="gantt_tree_icon gantt_file"></div><div class="gantt_tree_content">Task #2</div></div><div class="gantt_cell" style="width:90px;text-align:center;" role="gridcell" aria-label="2013-04-11"><div class="gantt_tree_content">2013-04-11</div></div><div class="gantt_cell" style="width:70px;text-align:center;" role="gridcell" aria-label="8"><div class="gantt_tree_content">8</div></div><div class="gantt_cell gantt_last_cell" style="width:43px;" role="gridcell" aria-label=""><div role="button" aria-label="New task" class="gantt_add"></div></div></div></div></div><div class="gantt_task" style="height: 180px; width: 563px;"><div class="gantt_task_scale" style="height: 34px; width: 1470px;"><div class="gantt_scale_line" style="height:34px;position:relative;line-height:34px"><div class="gantt_scale_cell" aria-label="31 Mar" style="width:70px;height:34px;position:absolute;left:0px">31 Mar</div><div class="gantt_scale_cell" aria-label="01 Apr" style="width:70px;height:34px;position:absolute;left:70px">01 Apr</div><div class="gantt_scale_cell" aria-label="02 Apr" style="width:70px;height:34px;position:absolute;left:140px">02 Apr</div><div class="gantt_scale_cell" aria-label="03 Apr" style="width:70px;height:34px;position:absolute;left:210px">03 Apr</div><div class="gantt_scale_cell" aria-label="04 Apr" style="width:70px;height:34px;position:absolute;left:280px">04 Apr</div><div class="gantt_scale_cell" aria-label="05 Apr" style="width:70px;height:34px;position:absolute;left:350px">05 Apr</div><div class="gantt_scale_cell" aria-label="06 Apr" style="width:70px;height:34px;position:absolute;left:420px">06 Apr</div><div class="gantt_scale_cell" aria-label="07 Apr" style="width:70px;height:34px;position:absolute;left:490px">07 Apr</div><div class="gantt_scale_cell" aria-label="08 Apr" style="width:70px;height:34px;position:absolute;left:560px">08 Apr</div></div></div><div class="gantt_data_area" style="height: 145px; width: 1470px;"><div class="gantt_task_bg" data-layer="true" style="width: 1470px;"><div class="gantt_task_row" task_id="1" style="height: 35px;"><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell gantt_last_cell" style="width: 70px;"></div></div><div class="gantt_task_row odd" task_id="2" style="height: 35px;"><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell gantt_last_cell" style="width: 70px;"></div></div><div class="gantt_task_row" task_id="3" style="height: 35px;"><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell" style="width: 70px;"></div><div class="gantt_task_cell gantt_last_cell" style="width: 70px;"></div></div></div><div class="gantt_links_area" data-layer="true" style="width: 1470px;"><div class="gantt_task_link" link_id="1" aria-label="Link Project #2 (start) Task #1 (start) "><div class="gantt_line_wrapper" style="top: 7.5px; left: 48px; height: 20px; width: 32px;"><div class="gantt_link_line_left" style="height: 2px; width: 14px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_line_wrapper" style="top: 7.5px; left: 48px; height: 55px; width: 20px;"><div class="gantt_link_line_down" style="height: 37px; width: 2px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_line_wrapper" style="top: 42.5px; left: 48px; height: 20px; width: 90px;"><div class="gantt_link_line_right" style="height: 2px; width: 72px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_line_wrapper" style="top: 42.5px; left: 118px; height: 20px; width: 26px;"><div class="gantt_link_line_right" style="height: 2px; width: 8px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_link_arrow gantt_link_arrow_right" style="top: 49.5px; left: 134px;"></div></div><div class="gantt_task_link" link_id="2" aria-label="Link Task #1 (end) Task #2 (start) "><div class="gantt_line_wrapper" style="top: 42.5px; left: 690px; height: 20px; width: 32px;"><div class="gantt_link_line_right" style="height: 2px; width: 14px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_line_wrapper" style="top: 42.5px; left: 702px; height: 55px; width: 20px;"><div class="gantt_link_line_down" style="height: 37px; width: 2px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_line_wrapper" style="top: 77.5px; left: 702px; height: 20px; width: 72px;"><div class="gantt_link_line_right" style="height: 2px; width: 54px; margin-top: 9px; margin-left: 9px;"></div></div><div class="gantt_link_arrow gantt_link_arrow_right" style="top: 84.5px; left: 764px;"></div></div></div><div class="gantt_bars_area" data-layer="true" style="width: 1470px;"><div task_id="1" class="gantt_task_line" aria-label=" Task: Project #2 Start date: 2013-04-01 End date: 2013-04-19" aria-selected="false" aria-grabbed="false" style="left: 70px; top: 2px; height: 30px; line-height: 30px; width: 1260px;"><div class="gantt_task_progress" style="width: 503px;"></div><div class="gantt_task_progress_drag" style="left: 503px;"></div><div class="gantt_task_content">Project #2</div><div class="gantt_task_drag task_left"></div><div class="gantt_task_drag task_right"></div><div class="gantt_link_control task_left" style="height: 30px; line-height: 30px;"><div class="gantt_link_point"></div></div><div class="gantt_link_control task_right" style="height: 30px; line-height: 30px;"><div class="gantt_link_point"></div></div></div><div task_id="2" class="gantt_task_line" aria-label=" Task: Task #1 Start date: 2013-04-02 End date: 2013-04-10" aria-selected="false" aria-grabbed="false" style="left: 140px; top: 37px; height: 30px; line-height: 30px; width: 560px;"><div class="gantt_task_progress" style="width: 335px;"></div><div class="gantt_task_progress_drag" style="left: 335px;"></div><div class="gantt_task_content">Task #1</div><div class="gantt_task_drag task_left"></div><div class="gantt_task_drag task_right"></div><div class="gantt_link_control task_left" style="height: 30px; line-height: 30px;"><div class="gantt_link_point"></div></div><div class="gantt_link_control task_right" style="height: 30px; line-height: 30px;"><div class="gantt_link_point"></div></div></div><div task_id="3" class="gantt_task_line" aria-label=" Task: Task #2 Start date: 2013-04-11 End date: 2013-04-19" aria-selected="false" aria-grabbed="false" style="left: 770px; top: 72px; height: 30px; line-height: 30px; width: 560px;"><div class="gantt_task_progress" style="width: 335px;"></div><div class="gantt_task_progress_drag" style="left: 335px;"></div><div class="gantt_task_content">Task #2</div><div class="gantt_task_drag task_left"></div><div class="gantt_task_drag task_right"></div><div class="gantt_link_control task_left" style="height: 30px; line-height: 30px;"><div class="gantt_link_point"></div></div><div class="gantt_link_control task_right" style="height: 30px; line-height: 30px;"><div class="gantt_link_point"></div></div></div></div></div></div><div class="gantt_ver_scroll" style="display: none; height: 0px; width: 0px;"><div></div></div><div class="gantt_hor_scroll" style="display: block; width: 923px; height: 18px;"><div style="width: 1832px;"></div></div></div>
	</div>
</template>

<template id="vectorDraw">
<div>
     <table>
        <tr>
            <td>
                <canvas id='canvas'> </canvas>
                <div id='noJavascript'>
                    <div style="height: 400px; width: 500px; background-color:#b0c4de;">                    
                    The browser you're using does not allow the use of Javascript, please enable Javascript execution or use a different web browser.
                    </div>
                </div>
            </td>
        
        </tr>
        <tr>
          <td class="condriate">x : 0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;y : 0</td>
        </tr>
    </table>
    <div id="text_Wrapper" class="text_Wrapper" style="display:none"><p id="text">Click inside the black circle</p></div>  
    </div>
</template>
<template id="islider">	
      <div class="mui-slider" style="width:300px;height:300px;">
			<div class="mui-slider-group mui-slider-loop">
				<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
					<img src="${contextPath}/scripts/plugins/bootstrap/islider/img/yuantiao.jpg" />
						<p class="mui-slider-title">静静看这世界</p>
					</a>
				</div>
				<div class="mui-slider-item">
					<a href="#">
						<img src="${contextPath}/scripts/plugins/bootstrap/islider/img/shuijiao.jpg" />
						<p class="mui-slider-title">幸福就是可以一起睡觉</p>
					</a>
				</div>
				
				<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
				<div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
						<img src="${contextPath}/scripts/plugins/bootstrap/islider/img/muwu.jpg" />
						<p class="mui-slider-title">幸福就是可以一起睡觉</p>
					</a>
				</div>
			</div>
			<div class="mui-slider-indicator mui-text-right">
				<div class="mui-indicator mui-active"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
				<div class="mui-indicator"></div>
			</div>
	</div>
</div>
</template>
<template id="levellist">
	<div  class="levellist mui-card">
	<div class="levellistLoading" style="display: none;">
		<div class="shade">
		</div>
		<div class="levellist-loadding">
			<div class="levellist-file-upload-spinner">
				<div class="levellist-file-upload-spinner-inner">
				</div>
			</div>
			<div class="levellist-file-hover-content-upload-message">Loading…</div>
		</div>
	</div>
	<div class="mui-card-header">
		<div class="headCell active">&nbsp;&gt;&nbsp;<span class="headText">全部</span></div>
	</div>
	<div  class="mui-card-content" style="width: 100%; height: 100%;">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell levelListRow" row-index="1">
				<div class="levelListText" row-index="1">2017-07-01</div>
				<div class="mui-switch" data-switch="1">
					<div class="mui-switch-handle">
					</div>
				</div>
			</li>
			<li class="mui-table-view-cell levelListRow selected" row-index="2">
				<div class="levelListText" row-index="2">2017-07-08</div>
				<div class="mui-switch" data-switch="2">
					<div class="mui-switch-handle">
					</div>
				</div>
			</li>
			<li class="mui-table-view-cell levelListRow" row-index="3">
				<div class="levelListText" row-index="3">2017-07-15</div>
				<div class="mui-switch" data-switch="3">
					<div class="mui-switch-handle">
					</div>
				</div>
			</li>
			<li class="mui-table-view-cell levelListRow" row-index="4">
				<div class="levelListText" row-index="4">2017-07-22</div>
				<div class="mui-switch" data-switch="4">
					<div class="mui-switch-handle">
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
	<div  class="levellist mui-card"><div class="levellistLoading" style="display: none;">	<div class="shade">	</div>	<div class="levellist-loadding"><div class="levellist-file-upload-spinner"><div class="levellist-file-upload-spinner-inner"></div></div><div class="levellist-file-hover-content-upload-message">Loading…</div>	</div></div><div class="mui-card-header">	<div class="headCell active">&nbsp;&gt;&nbsp;<span class="headText">全部</span></div></div><div  class="mui-card-content" style="width: 100%; height: 100%;">	<ul class="mui-table-view"><li class="mui-table-view-cell levelListRow" row-index="1"><div class="levelListText" row-index="1">2017-07-01</div><div class="mui-switch" data-switch="1"><div class="mui-switch-handle"></div></div></li><li class="mui-table-view-cell levelListRow selected" row-index="2"><div class="levelListText" row-index="2">2017-07-08</div><div class="mui-switch" data-switch="2"><div class="mui-switch-handle"></div></div></li><li class="mui-table-view-cell levelListRow" row-index="3"><div class="levelListText" row-index="3">2017-07-15</div><div class="mui-switch" data-switch="3"><div class="mui-switch-handle"></div></div></li><li class="mui-table-view-cell levelListRow" row-index="4"><div class="levelListText" row-index="4">2017-07-22</div><div class="mui-switch" data-switch="4"><div class="mui-switch-handle"></div></div></li></ul></div></div>
</template>
<template id="foldlist">
<div class="foldlist" style="width: 100%; height: 100%;"><div><div class="mui-card foldListRow selected" row-index="1"><div class="mui-card-header"><div style="width:100%" class="foldListText">1</div><div class="mui-switch" data-switch="1" style="transition-duration: 0.2s;"><div class="mui-switch-handle" style="transition-duration: 0.2s; transform: translate(0px, 0px);"></div></div></div><div class="mui-card-content"><div><p>sssss</p></div></div></div><div class="mui-card foldListRow" row-index="2"><div class="mui-card-header"><div style="width:100%" class="foldListText">2</div><div class="mui-switch" data-switch="2" style="transition-duration: 0.2s;"><div class="mui-switch-handle" style="transition-duration: 0.2s; transform: translate(0px, 0px);"></div></div></div><div class="mui-card-content"><div><p>sssss</p></div></div></div><div class="mui-card foldListRow" row-index="3"><div class="mui-card-header"><div style="width:100%" class="foldListText">3</div><div class="mui-switch" data-switch="3" style="transition-duration: 0.2s;"><div class="mui-switch-handle" style="transition-duration: 0.2s; transform: translate(0px, 0px);"></div></div></div><div class="mui-card-content"><div><p>sssss</p></div></div></div><div class="mui-card foldListRow" row-index="4"><div class="mui-card-header"><div style="width:100%" class="foldListText">4</div><div class="mui-switch" data-switch="4" style="transition-duration: 0.2s;"><div class="mui-switch-handle" style="transition-duration: 0.2s; transform: translate(0px, 0px);"></div></div></div><div class="mui-card-content"><div><p>sssss</p></div></div></div><div class="mui-card foldListRow" row-index="5"><div class="mui-card-header"><div style="width:100%" class="foldListText">5</div><div class="mui-switch" data-switch="5" style="transition-duration: 0.2s;"><div class="mui-switch-handle" style="transition-duration: 0.2s; transform: translate(0px, 0px);"></div></div></div><div class="mui-card-content"><div><p>sssss</p></div></div></div></div></div>
</template>
<template id="mswitch">
	<div class="mswitch">
		<div class="mui-switch mui-active" data-switch="1" style="transition-duration: 0.2s;"><div class="mui-switch-handle" style="transition-duration: 0.2s; transform: translate(40px, 0px);"></div></div>
	</div>
</template>
<template id="muitab">
	<div class="muitab">
	</div>
</template>
<template id="medialist">
  <ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:;">
						<img class="mui-media-object mui-pull-left" src="../images/shuijiao.jpg">
						<div class="mui-media-body">
							幸福
							<p class='mui-ellipsis'>能和心爱的人一起睡觉，是件幸福的事情；可是，打呼噜怎么办？</p>
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:;">
						<img class="mui-media-object mui-pull-left" src="../images/muwu.jpg">
						<div class="mui-media-body">
							木屋
							<p class='mui-ellipsis'>想要这样一间小木屋，夏天挫冰吃瓜，冬天围炉取暖.</p>
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:;">
						<img class="mui-media-object mui-pull-left" src="../images/cbd.jpg">
						<div class="mui-media-body">
							CBD
							<p class='mui-ellipsis'>烤炉模式的城，到黄昏，如同打翻的调色盘一般.</p>
						</div>
					</a>
				</li>

			</ul>
</template>
<template id="wslider">
    <div>
       <div id='mysite-slideshow'>
		       <div class="mui-slider-item mui-slider-item-duplicate">
					<a href="#">
					<img src="${contextPath}/scripts/plugins/bootstrap/islider/img/yuantiao.jpg" />
						<p class="mui-slider-title">静静看这世界</p>
					</a>
				</div>
				
       </div>
    </div>
</template>
<template id="mobiscroll">
 <input type="text" class = "form-control id frame-variable component" frame-variable="fv1" name="scroller"> 
</template>
<template id="rangecalendar">
	<div class="default-theme range-calendar rangecalendar" style="display: block;">
		<div class="wrapper">
			<div class="wrapper">
				<div class="calendar ui-draggable" style="left: 0px;">
					<div class="cal-cell cell" date-id="2017108" month-id="201710" month="10"><div class="cell-content"><div class="day ferial">周日</div><div class="day-number">8</div></div></div>
					<div class="cal-cell cell" date-id="2017109" month-id="201710" month="10"><div class="cell-content"><div class="day ">周一</div><div class="day-number">9</div></div></div>
					<div class="cal-cell cell" date-id="20171010" month-id="201710" month="10"><div class="cell-content"><div class="day ">周二</div><div class="day-number">10</div></div></div>
					<div class="cal-cell cell selected selectedk" date-id="20171011" month-id="201710" month="10"><div class="cell-content"><div class="day ">周三</div><div class="day-number">11</div></div></div>
					<div class="cal-cell cell hasData" date-id="20171012" month-id="201710" month="10"><div class="cell-content"><div class="day ">周四</div><div class="day-number">12</div></div></div>
					<div class="cal-cell cell" date-id="20171013" month-id="201710" month="10"><div class="cell-content"><div class="day ">周五</div><div class="day-number">13</div></div></div>
					<div class="cal-cell cell" date-id="20171014" month-id="201710" month="10"><div class="cell-content"><div class="day ferial">周六</div><div class="day-number">14</div></div></div>
				</div>
			</div>
		</div>
	</div>	
</template>
</template>
<template id="loginmsgvalid">
   <div class="loginmessagevalid">
		<div class="content leftcontent">
			<input class="form-control" type="text" placeholder="请输入验证码" /> 	
		</div>
		<div class="content rightcontent">
			<a class="getValidCodeBtn">获取验证码</a>
		</div>
	</div>
</template>
<template id="aspectlayout">
   <div class="aspectlayout aspectlayout-designer">
   		<div class="aspectlayout-north">
   			<div class="aspectlayout-content containerComp"></div>
   		</div>
   		<div class="aspectlayout-middle">
   			<div class="aspectlayout-west">
	   			<div class="aspectlayout-content containerComp"></div>
	   		</div>
	   		<div class="aspectlayout-center">
	   			<div class="aspectlayout-content containerComp"></div>
	   		</div>
	   		<div class="aspectlayout-east">
	   			<div class="aspectlayout-content containerComp"></div>
	   		</div>
   		</div>
   		<div class="aspectlayout-south">
   			<div class="aspectlayout-content containerComp"></div>
   		</div>
	</div>
</template>
