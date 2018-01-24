//验证当前浏览器内核环境
var Sys = (function(ua){ 
    var s = {}; 
    s.IE = ua.match(/msie ([\d.]+)/)?true:false; 
    s.Firefox = ua.match(/firefox\/([\d.]+)/)?true:false; 
    s.Chrome = ua.match(/chrome\/([\d.]+)/)?true:false; 
    s.IE6 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6))?true:false; 
    s.IE7 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 7))?true:false; 
    s.IE8 = (s.IE&&([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 8))?true:false; 
    return s; 
})(navigator.userAgent.toLowerCase());
//闭包限定命名空间
(function ($) {
    //默认参数
    var defaluts =  {
						'rules' : {
							'length' : {
								'regex': '.{8,}',
								'len':8,
								'score':50,
								'name': '长度',
								'message': '{0}长度的字符',
								'enable': true
							},
							'lowercase' :{
								'regex': '[a-z]{1,}',
								'len':1,
								'score':10,
								'name': '小写字母',
								'message': '{0}个小写字母',
								'enable': true
							},
							'uppercase' : {
								'regex': '[A-Z]{1,}',
								'len':1,
								'score':10,
								'name': '大写字母',
								'message': '{0}个大写字母',
								'enable': true
							},
							'number' : {
								'regex': '[0-9]{1,}',
								'len':1,
								'score':10,
								'name': '数值',
								'message': '{0}个数值',
								'enable': true
							},
							'specialchar' : {
								'regex': '[^a-zA-Z0-9]{1,}',
								'len':1,
								'score':20,
								'name': '特殊字符',
								'message': '{0}个特殊字符',
								'enable': true
							}
						},
						strengthContainer:"#level"
        };
	/**
	*验证正则表达式模板
	*/
	var ruleRegex={
		'length' :'.{${0},}',
		'lowercase' :'[a-z]{${0},}',
		'uppercase': '[A-Z]{${0},}',
		'number': '[0-9]{${0},}',
		'specialchar': '[^a-zA-Z0-9]{${0},}'
	};
	var methods = {
        init : function(options) {
			//支持多选择器多对象
			return this.each(function () {				
			    //支持链式调用
                var $this = $(this);
				var opts = $.extend(true,{}, defaluts, options); //覆盖插件默认参数
				if(!isValid(opts)){
					return;
				}
				$this.data("options",opts);
				$this.passwordRule("addListener"); 
            });
        },
		addListener:function(){
			var opts=this.data("options");
			if(!isValid(opts)){
				return;
			}
            //获取验证规则
			var rules=opts.rules;
			var regExpStr,regExp;
			$.each(rules,function(ruleName,rule){
				 if(rule.enable){
					 regExpStr=ruleRegex[ruleName].replace('${0}',rule.len);
					 rules[ruleName].regex=regExpStr;
				 }
				 //regExp=new RegExp(regExpStr, 'g')
			});
			opts.rules=rules;
			var $this = $(this);
			$this.data("options",opts);
			$this.keyup(function() {
                $this.passwordRule("checkRules");
            });
            $this.on('paste', function () {
                $this.passwordRule("checkRules");
            });

            $this.change(function () {
                $this.passwordRule("checkRules");
            });
		},
		checkRules:function(){
			var opts=this.data("options");
			 //获取验证规则
			var rules=opts.rules;
			//获取显示强度容器
			var strengthContainer=$(opts.strengthContainer);
			var regExp,regExpStr;
			var sVal=this.val();
			//强度分值
			var strengthScore=0;
			$.each(rules,function(ruleName,rule){
				 regExpStr=rule.regex;
				 regExp=new RegExp(regExpStr, 'g');
				 if (regExp.test(sVal)) {
				    strengthScore+=rule.score;
				 }
			});
			//弱强度密码
			if(strengthScore<60){
			   strengthContainer.removeClass('pw-weak'); 
			   strengthContainer.removeClass('pw-medium'); 
			   strengthContainer.removeClass('pw-strong'); 
			   strengthContainer.addClass('pw-defule');
			   strengthContainer.attr("strength","0");
			}
			//弱强度密码
			else if(strengthScore==60){
				strengthContainer.removeClass('pw-defule'); 
				strengthContainer.removeClass('pw-medium'); 
				strengthContainer.removeClass('pw-strong'); 
				strengthContainer.addClass('pw-weak');
				strengthContainer.attr("strength","1");
			}
			//中强度密码
			else if(strengthScore>60&&strengthScore<=80){
				strengthContainer.removeClass('pw-weak'); 
				strengthContainer.removeClass('pw-defule'); 
				strengthContainer.removeClass('pw-strong'); 
				strengthContainer.addClass('pw-medium'); 
				strengthContainer.attr("strength","2");
			}
			//高强度密码
			else{
				strengthContainer.removeClass('pw-weak'); 
				strengthContainer.removeClass('pw-medium'); 
				strengthContainer.removeClass('pw-defule'); 
				strengthContainer.addClass('pw-strong');
				strengthContainer.attr("strength","3");
			}
		}
    };
	$.fn.passwordRule = function(method) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.passwordRule' );
			return this;
        }    
    };
	//私有方法，检测参数是否合法
    function isValid(options) {
        return !options || (options && typeof options === "object") ? true : false;
    }
})(window.jQuery);