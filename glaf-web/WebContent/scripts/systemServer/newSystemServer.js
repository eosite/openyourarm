var systemServerPageFunc = {
	setting : {
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
            {title:'操作名称', field:'name', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                'style': 'text-align:center;width: 45%'
            },},
            {title:'代码', field:'key', width:80, sortable:false,'isEditor':true,'editor':{'component':'maskedtextbox'}, 'attributes': {
                'style': 'text-align:center;width: 35%'
            },},
            {title:'', field:'operationCode',width:20,'style': 'width: 20%',
            	template : function(data){
            		return '<button type="button" class="btn green btn-sm selectBtn" style=""><i class="glyphicon glyphicon-ok"></i>选择</button>';
            	}
        	}
        	
        ],
        events:{}
    },
    operationSetting : {
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
            {title:'参数名称', field:'title', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'代码', field:'code', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'描述', field:'desc', width:80, sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;'
            }}
        ],
        events:{}
    },
    resize : function(){
    	var that = this;

    	that.$tabContent.outerHeight(that.$target.height() - that.$tabTitle.outerHeight(true));
    	that.$tabContent.find(".grid").grid("resize");
    },
	init : function($target){
		var that = this;
		that.$target = $target;
		$.ajax({
            url: contextPath + "/mx/form/formSystemServer/jsonData",
            type: 'POST',
            dataType: 'json',
            // contentType: 'application/json',
        }).done(function(result) {
            that.createTab(result);
            that.resize();
        }).fail(function(e) {
            console.log(e);
        })

        $target.resize(function() {
			if(!that.count){
				that.count++;
				setTimeout(function(){
					that.resize.call(that);
					that.count = 0;
				},100)
			}
		});
        // that.resize();
	},
	//创建选卡
	createTab : function(datas){
		var that = this;
		var $target = that.$target;
		var $ul = $target.find(".nav.nav-tabs");
		that.$tabTitle = $ul;
		var $tabContent = $target.find(".tab-content");
		that.$tabContent = $tabContent;

		var tabid,i=0,$li,$tabPane;
		$.each(datas,function(key,item){
			//创建tab的标签栏和内容栏
			tabid = key;
			i++;
			$li = $('<li><a href="#'+tabid+'" data-toggle="tab" aria-expanded="false"> '+item.title+' </a></li>');
            $ul.append($li);
            $tabPane = $('<div class="tab-pane" id="'+tabid+'"></div>');
            $tabContent.append($tabPane);
            if(i == 1){
				$li.addClass("active");
				$tabPane.addClass("active");
            }
            
            that.buildTabPane($tabPane,item,tabid);
		})
	},
	//创建内容
	buildTabPane : function($tabPane,item,tabid){
		var that = this;
		var setting = $.extend(true,{},that.setting);
		var gridData = item.gridData;
		if(typeof gridData == 'string'){
			gridData = JSON.parse(gridData);
		}

		//选择栅格
		var $content = $('<div class="col-xs-8 full-height"></div>');
		var $systemServerGrid = $('<div id="'+tabid+'ServerGrid" class="grid" style="height:100%"></div>');
		$content.append($systemServerGrid);
		//详细信息栅格
		var $rightContent = $('<div class="col-xs-4 full-height"></div>');
		var $systemOperatorGrid = $('<div id="'+tabid+'OperatorGrid" class="grid" style="height:100%"></div>');
		$rightContent.append($systemOperatorGrid);

		$tabPane.append($content).append($rightContent);
		
		var systemOperatorGridInitEnd = false;
		setting.events.onClickRow = function(index, row){
			//左边的grid点击后，右边显示对应的字段说明
            var dataItems = $systemServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
                var fieldDatas = dataItems[0].param;
                if(fieldDatas){
                	if(systemOperatorGridInitEnd){
                		$systemOperatorGrid.grid("_loadDatas",fieldDatas);
                	}else{
                		var operationSetting = $.extend(true,{},that.operationSetting);
                		operationSetting.datas = fieldDatas;
						$systemOperatorGrid.grid(operationSetting);              		
						systemOperatorGridInitEnd = true;
                	}
                }
            }
        }
		setting.datas = gridData;
		$systemServerGrid.grid(setting);

		$systemServerGrid.on("click",".selectBtn",function(event){
            var dataItems = $systemServerGrid.grid("getSelectedRows");
            if(dataItems && dataItems.length > 0){
            	var config = {
            	   dataItem :dataItems[0],
            	   itemParam : window.itemParam,
            	   tabid : tabid
            	}
                callback(config);
            }
        });
	},
	initDefaultButton : function($button_panel){
        var $button_panel = $("#"+systemServerPageFunc.button_panel_ID);
        if($button_panel){
            $button_panel = $button_panel;
        }
        
    },
}
$(function(){
	systemServerPageFunc.init($("#systemServerTab"));
})