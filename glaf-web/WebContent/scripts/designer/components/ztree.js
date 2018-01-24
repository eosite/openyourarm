var ztree_designer = {
	/**
	 * 设置控件名称
	 */
	setName: function (component, val){
		if(val!==''){
			var prop = "name"; 
			ztree_designer.setDataRule(component,prop,val);
			component.attr("cname", val);
		}
	},

	setDataRule : function(component,prop,val){
        var lastVal = ztree_designer.getDataRule(component,prop)
        
        if(component.attr("data-rule")){
            var data_rule = JSON.parse(component.attr("data-rule"));
            data_rule[prop]=val;
        }else{
            var data_rule={};
            data_rule[prop]=val;
        }
        component.attr("data-rule",JSON.stringify(data_rule));
        return lastVal;
    },
    getDataRule : function(component,prop){
        var value ="";
        if(component.attr("data-rule")){
            value = JSON.parse(component.attr("data-rule"))[prop];
        }
        return value;
    },
    setting :{
        'data': {
            'simpleData': {
                'enable': true,
                'idKey': 'id',
                'pIdKey': 'parentId',
            }
        },
        'check': {
            'enable': true,
            'chkStyle': 'checkbox',
            'chkboxType': {
                "Y": "s",
                "N": "s"
            },
        },
        'callback': {
            'beforeRename': function(treeId, treeNode, newName, isCancel) {
                return glafZtree.beforeRename(treeId, treeNode, newName, isCancel, 'ce189d853374478c999d41d9386b111f');
            },
            'beforeRemove': function(treeId, treeNode) {
                return glafZtree.beforeRemove(treeId, treeNode, 'ce189d853374478c999d41d9386b111f');
            },
            'beforeDrop': function(treeId, treeNodes, targetNode, moveType, isCopy) {
                return glafZtree.beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy, 'ce189d853374478c999d41d9386b111f')
            },
            'onClick': function(event, treeId, treeNode, clickFlag) {},
            'beforeAsync': function(treeId, treeNode) {
                var $this = $("#" + treeId);
                var expandingNum = $this.data("expandingNum") || 0;
                if ($this.data("loadding") && !$this.data("expanding")) {
                    return false;
                };
                expandingNum++;
                $this.data("expandingNum", expandingNum);
                $this.data("loadding", true);
                return true;
            },
            'onAsyncSuccess': function(event, treeId, treeNode, msg) {
                if (msg && msg.length == 2 && true) {
                    if (treeNode) {
                        treeNode.isParent = false;
                        $.fn.zTree.getZTreeObj(treeId).updateNode(treeNode);
                    }
                }
                var $this = $("#" + treeId),
                    params = $this.data("params");
                var expandingNum = $this.data("expandingNum") | 0;
                if (params) {
                    ztreeFunc.linkageControl(treeId, params);
                    $this.data("params", null);
                }
                var expandChilds = $this.data("expandChilds");
                var treeDatas = null;
                if (treeNode == null) {
                    treeDatas = $.fn.zTree.getZTreeObj(treeId).getNodes();
                } else {
                    treeDatas = treeNode.children;
                }
                if ((treeNode == null && expandChilds > 1) || (treeNode && (treeNode.level + 2) < expandChilds)) {
                    if (treeDatas && treeDatas.length) {
                        $this.data("expanding", true);
                        $.each(treeDatas, function(i, item) {
                            $this.data("isloaded", false);
                            if (i > 5) {
                                return false;
                            }
                            $.fn.zTree.getZTreeObj(treeId).expandNode(item, true, false, true);
                        });
                        if (!true) {
                            $this.data("isloaded", true);
                        }
                        $this.data("expanding", false);
                    } else {
                        $this.data("isloaded", true);
                    }
                } else {
                    $this.data("isloaded", true);
                }
                $this.data("loadding", false);
                expandingNum--;
                $this.data("expandingNum", expandingNum);
                var ztreeObj = $.fn.zTree.getZTreeObj('ztree_1482802459548');
                if ('true' == 'true' && treeNode && treeNode.checked) {
                    if (ztreeObj.setting && ztreeObj.setting.callback.onCheck && $.isFunction(ztreeObj.setting.callback.onCheck)) {
                        ztreeObj.setting.callback.onCheck();
                    }
                }
                if (ztreeObj && !expandingNum) {
                    if (ztreeObj.setting && ztreeObj.setting.callback.onLoadSuccess && $.isFunction(ztreeObj.setting.callback.onLoadSuccess)) {
                        ztreeObj.setting.callback.onLoadSuccess();
                    }
                }
            },
        },
        'expandChilds': 0,
        'edit': {
            'enable': true,
            'removeTitle': '删除',
            'showRenameBtn': true,
            'renameTitle': '重命名',
            'drag': {
                'isCopy': true,
                'isMove': true,
            }
        },
        'opts': {
            'editPanleAutoClose': false,
            'ruleId': 'test'+id,
        },
    },
    showStaticDataPanle : function(){
    	setStaticData(null,'ztree');
    },
    setStaticData : function(component,dataStr){
        var prop = 'staticData';
        if(dataStr){
            var datas = JSON.parse(dataStr);
            // ztree_designer.setDataRule(component,prop,dataStr);

            var component_ul = $("#"+component.attr("id")+"_ul");
            component_ul.empty();
            component_ul.removeData();

            $.each(datas.dataGrid.datas,function(i,item){
                item.open = true;
            })

            glafZtree.init(component_ul.attr("id"),ztree_designer.setting,datas.dataGrid.datas);

            var datasourceSet_columns = [];
            var dataSourceSet = [{
                "databaseId": "",
                "datasetId": "",
                "name": "staticSource",
                "title": "静态数据数据集",
                'columns': datas.fieldGrid.datas,
            }]

            var ruleData = {
                rule : {
                    dataSourceSet : dataSourceSet,
                },
                setting : ztree_designer.setting,
                staticDataRule : datas
            }
            var pageId = id;
            var param = {
                pageId : pageId,
                componentId : component.attr("id"),
                ruleData : JSON.stringify(ruleData),
            }

            $.ajax({
                url: contextPath + '/mx/form/staticFile/save',
                type: "post",
                data: param,
                async: false,
                success: function(msg) {
                    if(msg){
                        
                    }
                },
                error: function() {
                    alert("异常错误,请稍后再试.");
                }
            })
            //获取页面id
            //获取控件id
          /*  ztree_designer.setting.columns = ztree_designer.setting.columns.slice(0,ztree_designer.setting.columns.length-1);
			ztree_designer.setting.toolbar = "";
			component.ztreeStatic(ztree_designer.setting);*/
        }
    },

    setStyle : function(component,val){
        var prop = 'style';
        ztree_designer.setDataRule(component,prop,val);
        
        if(val == 'awesome'){
            component.attr("zTreeStyle","awesome");
            glafZtree.trunToMobileStyle();
            
        }
    },

    setLineHeight : function(component,val){
        var prop = 'lineHeight';
        ztree_designer.setDataRule(component,prop,val);
        if(val){
            val += 'px';  
        }
        ztree_designer.updateStyleByKey(component,prop,val);
    },

    setIconWidth : function(component,val){
        var prop = 'iconWidth';
        ztree_designer.setDataRule(component,prop,val);
        if(val){
            val += 'px';  
        }
        ztree_designer.updateStyleByKey(component,prop,val);
    }, 

    setFontSize : function(component,val){
        var prop = 'fontSize';
        ztree_designer.setDataRule(component,prop,val);
        if(val){
            val += 'px';  
        }
        ztree_designer.updateStyleByKey(component,prop,val);
    },

    setImg : function(component,val){
        var prop = 'imgStatus';
        ztree_designer.setDataRule(component,prop,val);
        ztree_designer.updateStyleByKey(component,prop,val);
    },
    setFontColor : function(component,val){
        var prop = 'fontColor';
        ztree_designer.setDataRule(component,prop,val);
        ztree_designer.updateStyleByKey(component,prop,val);
    },

        /**
     * 获取styler并且赋值
     */
    updateStyleByKey: function (component,key,val){
        var styler = ztree_designer.getStyler(component);
        styler[key] = val;
        ztree_designer.updateStyler(component,styler);
        ztree_designer.updateStylerBox(component,styler);
    },
    /**
     * 获取styler数组
     */
    getStyler: function (component){
        var sid = ztree_designer.getStylerId(component);
        var styler = component.find('#'+sid);
        if(!styler.length){
            component.append("<input id=\""+sid+"\" type=\"hidden\" value=\"\" />");
            return new Object();
        }else{
            return $.parseJSON(styler.val());
        }
    },
    getStylerId: function(component){
        return component.attr('id') +'_styler_hidden';
    },
    updateStyler: function (component,styler){
        var sid = ztree_designer.getStylerId(component);
        $('#'+sid).val(JSON.stringify(styler));
    },
    /**
     * 设置样式
     */
    updateStylerBox: function(component,styler){
        var id = component.attr('id');
        var _id = '#'+id;
        var _default = "";

        var _default_li = _id + " li{";
        if(styler['lineHeight']){
            _default_li += 'line-height:'+styler['lineHeight']+";";
        }
        _default_li += "}"

        var _default_span = _id + " li span{";
        if(styler['fontSize']){
            _default_span += 'font-size:'+styler['fontSize']+";";
        }
        _default_span += "}";

        var _default_font = _id + "  li :before," + _id + "  li :after,"+_id+" li a{";
         if(styler['fontSize']){
            _default_font += 'font-size:'+styler['fontSize']+";";
        }
        _default_font += "}"

        var _default_font2 = _id+" li a{";
        if(styler['fontColor']){
            _default_font2 += 'color:'+styler['fontColor']+" !important;";
        }
        
        _default_font2 += "}"

        var _default_icon = _id + " li span.button.switch,"+ _id + " li span.button.chk"+_id + " li span.button.ico_open,"+ _id + " li span.button.ico_close{";
        if(styler['iconWidth']){
            _default_icon += 'width:'+styler['iconWidth']+";";   
        }
        _default_icon += "}"

        var _default_img = _id + " li span.button.ico_open," + _id + " li span.button.ico_close,"+ _id + " li span.button.ico_docu{";
        if(styler['imgStatus']){
            _default_img += 'display:'+styler['imgStatus']+";";   
        }
        _default_img += "}"        

        _default += _default_li + _default_icon + _default_span + _default_img + _default_font + _default_font2;

        var stylebox = ztree_designer.getStyleBox(component);
        stylebox.html(_default);
    },
    /**
     * 获取<style id> 对象
     */
    getStyleBox: function (component){
        var sid = component.attr('id') + '_styler_init';
        var box = component.find('#'+sid);
        if(!box.length){
            component.append("<style id=\""+sid+"\"></style>");
        }
        return component.find('#'+sid);
    }
};