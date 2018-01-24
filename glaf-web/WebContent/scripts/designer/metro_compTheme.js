function CompThemeManage(themeId,changeThemeCallbackFunc){
	this.compThemeManage = {
		_init : function(themeId,changeThemeCallbackFunc){
			if(changeThemeCallbackFunc != null){
				this.changeThemeCallbackFunc = changeThemeCallbackFunc;
			}
			this.$compThemePanel = $(".compThemePanel");
			if(themeId){
				this.themeId = themeId;
				if(this.compType){
					this._loadCompTheme(this.compType);
				}
			}else{
				//页面无主题时，提示选择主题
				this._showSelectedTheme();
			}
			this._bindEvent();
		},
		_changeTheme : function(themeId){
			if(themeId){
				this.themeId = themeId;
				if(this.compType){
					this._loadCompTheme(this.compType);
				}
			}else{
				//页面无主题时，提示选择主题
				this._showSelectedTheme();
			}
		},
		_showSelectedTheme : function(){

		},
		_createCompTheme : function(datas){
			var that = this;
			that.$compThemePanel.empty();
			$.each(datas,function(i,item){
				var $controlItem = $(ArtTemplateDataUtils.convertOne(item, $("#controlItem_template").html()));
				if(item.controlCode == that.selectedCompThemeCode){
					$controlItem.addClass("selected");
				}
				$controlItem.data("data",item);
				that.$compThemePanel.append($controlItem);
			})
		},
		_loadCompTheme : function(compType,selectedCompThemeCode){
			var that = this;
			if(!that.themeId){
				return;
			}
			this.compType = compType;
			this.selectedCompThemeCode = selectedCompThemeCode;
			var successFunc = function(ret){
				that._createCompTheme(ret.rows);
			}
			var params = {
				compType : compType,
				themeTmpId : that.themeId,
				commonFlag : 0,
			}
			$.ajax({
	            url: contextPath + "/mx/theme/sysThemeTmpControl/list",
	            async: false,
	            type: 'post',
	            contentType: 'application/json',
	            data: JSON.stringify(params),
	            dataType: 'json',
	            success: successFunc
	        });
		},
		_reload : function(compType){
			this.compType = compType;
		},
		_bindEvent : function(){
			var that = this;
			that.$compThemePanel.on("click",".compTheme_item",function(event){
				var $this = $(this);
				that.$compThemePanel.find(".compTheme_item.selected").removeClass("selected");
				$this.addClass("selected");
				var data = $this.data("data");
				if($.isFunction(that.changeThemeCallbackFunc)){
					that.changeThemeCallbackFunc(data);
				}
			})
		}
	}
	this.compThemeManage._init(themeId,changeThemeCallbackFunc);
}

CompThemeManage.prototype.loadCompTheme = function(compType,selectedCompThemeCode){
	this.compThemeManage._loadCompTheme(compType,selectedCompThemeCode);
}
CompThemeManage.prototype.setTheme = function(themeId){
	this.compThemeManage._changeTheme(themeId);
}