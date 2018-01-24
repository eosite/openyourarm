/**
 * mtbootstrap get、set、reload
 */
(function($, bootstrap) {
	$.mt = $.mt || {};

	$.mt.init = function(opt, fn) {
		if (typeof opt.options == 'string') {
			var method = $.fn[opt.plugin].methods[opt.options];
			if (method) {
				return method(this, opt.param, opt.args);
			} else {
				return this;
			}
		}
		opt.options = opt.options || {};
		return this.each(function() {
			var state = $.data(this, 'bootstrapData');
			if (state) {
				$.extend(state.options, opt.options);
			} else {
				opt.options.target = this;
				$.data(this, 'bootstrapData', {
					options : opt.options,
					target : this
				});
			}
			if (fn)
				fn(this);
		});
	};

	var plugin = 'mtbootstrap';

	function bootstrapBind(ele) {
		var state = $.data(ele, 'bootstrapData');
		var opts = state.options;
		var vm = bootstrap.observable(opts);
		state.viewModel = vm;
	}

	$.fn[plugin] = function(options, param) {

		return $.mt.init.call(this, {
			plugin : plugin,
			options : options,
			param : param,
			args : arguments
		}, bootstrapBind);
	};

	$.fn[plugin].methods = {
		init : function(jq, method, params, fn, args) {
			var bootstrapData = jq[plugin]('getBootstrapData'), dataRole = bootstrapData.dataRole, bootstrap = bootstrapData.bootstrap, roleFunction = $.fn[plugin].roleFunctions[dataRole];
			if (roleFunction) {
				if (roleFunction[method])
					return roleFunction[method].call(bootstrap, jq, params, args);
			} else if (fn) {
				return fn(bootstrap);
			}
		},
		reload : function(jq, params) {

			jq.each(function(index, item) {
				var bootstrap = $.fn[plugin].methods.init(jq, 'reload', params,
						null);
				$.extend(bootstrap.dataSource.transport.options.read.data, params);
			});
		},
		rebind : function(jq) {
			var bootstrapData = jq[plugin]('getBootstrapData'), K = bootstrapData.bootstrap;

			if (K.destroy) {
				var newJq = jq.clone();
				jq.parent().after(newJq).remove();
				newJq.mtbootstrap(bootstrapData.viewModel);
			}

		},
		getValue : function(jq, key) {

			return $.fn[plugin].methods.init(jq, 'getValue', key, function(
					bootstrap) {
				return jq.val();
			});

		},
		getRow : function(jq, key) {
			return $.fn[plugin].methods.init(jq, 'getRow', key,
					function(bootstrap) {
						return jq.val();
					});
		},
		setValue : function(jq, val, args) {
			var dataRole = jq.attr("data-role");
			if(dataRole.indexOf("editor")!=-1){
				$.fn[plugin].methods.init(jq, 'setValue',val,function(bootstrap) {},args);
			}else{
				
				if(val && (typeof val == 'string')){
					val = $('<div>').html(val).text();
				}
				
				/*$.fn[plugin].methods.init(jq, 'setValue', $('<div>').html(val).text(), 
				function(bootstrap) {
					return jq.val($('<div>').html(val).text());
					//return jq.val(val);
				},
				args);*/
				
				$.fn[plugin].methods.init(jq, 'setValue', val, 
						function(bootstrap) {
					return jq.val(val);
					//return jq.val(val);
				},
				args);
			}	
		},
		disable : function(jq, args){
			$.fn[plugin].methods.init(jq, 'disable', function(bootstrap) {
				return jq.attr("disable", true);
			}, args);
		},
		options : function(jq) {
			return jq.data('bootstrapData').options;
		},
		getBootstrapData : function(jq) {
			var data = jq.data('bootstrapData') || {};
			var dataRole = jq.attr('data-role');
			return $.extend(data, {
				dataRole : dataRole || 'text',
			});
		},
		getViewModel : function(jq) {
			return jq.data('bootstrapData').viewModel;
		},
		validate : function(jq, params) {
			return $.fn[plugin].methods.init(jq, 'validate', params, function(
					bootstrap) {
			});
		}
	};

	var roleFunctions = {
		label : {
			setValue : function(jq,val,v){
				val = getMtValue(val, v);
				var $span = jq.find("span.frame-variable").length>0?jq.find("span.frame-variable"):jq;
				$span.html(pubsub.htmlUnescape4Html(val));
			}
		},
		mswitch : {
			setValue : function(jq, val, v) {
				val = getMtValue(val, v);
				jq.mswitchext("setValue", val);
			}
		},
		videoplay : {
			setValue : function(jq, val, v) {
				val = getMtValue(val, v);
				jq.videoExt("setVideoSource", val);
			}
		},
		textboxbt : {
			getValue : function(jq, key) {
				return jq.textboxBtExt("getValue");
			},
			setValue : function(jq, val, v) {
				val = getMtValue(val, v);
				jq.textboxBtExt("setValue", val);
			},
			disable : function(jq, val){
				jq.textboxBtExt("disabled", !val);
			},
			readOnly : function(jq, val){
				jq.textboxBtExt("readable", val);
			},
			validate : function(jq) {
				return jq.valid();
			}
		},
		textareabt : {
			getValue : function(jq, key) {
				return jq.textAreaBtExt("getValue");
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				jq.textAreaBtExt("setValue", val);
			},
			disable : function(jq, val){
				jq.textAreaBtExt("disabled", !val);
			},
			readOnly : function(jq, val){
				jq.textAreaBtExt("readable", val);
			}
		},
		touchspin : {
			getValue : function(jq, key) {
				return jq.touchSpinExt("getValue");
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				jq.touchSpinExt("setValue", val);
			},
			disable : function(jq, val){
				jq.touchSpinExt("disabled", !val);
			},
			readOnly : function(jq, val){
				jq.touchSpinExt("readable", val);
			}
		},
		tagsinput : {
			getValue : function(jq, key) {
				return tagsinputObj.getValue(jq);
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				tagsinputObj.getValue(jq,val);
			}
		},
		datepickerbt : {
			getValue : function(jq, key) {
				return jq.datepickerExt("getValue");
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				jq.datepickerExt("setValue", val);
			},
			disable : function(jq, val){
				jq.datepickerExt("disabled", !val);
			},
			readOnly : function(jq, val){
				jq.datepickerExt("readable", val);
			}
		},
		datetimepickerbt : {
			getValue : function(jq, key) {
				return jq.datetimepickerExt("getValue");
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				jq.datetimepickerExt("setValue", val);
			},
			disable : function(jq, val){
				jq.datetimepickerExt("disabled", !val);
			},
			readOnly : function(jq, val){
				jq.datetimepickerExt("readable", val);
			}
		},
		progressbarbt : {
			getValue : function(jq, key) {
				return jq.progressBarExt("getValue");
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				jq.progressBarExt("setValue", val);
			},
			disable : function(jq, val){
				jq.progressBarExt("disabled", !val);
			}
		},
		metroselect : {
			getValue : function(jq, key) {
				return jq.metroselect('getValue');
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				if (val) {
					jq.metroselect('prepareSelect', val);
				}
			},
			disable : function(jq, val){
				jq.metroselect("disabled", !val);
			},
			validate : function(jq) {
				return jq.find('select').valid();
			}
		},
		metroselect_m : {
			getValue : function(jq, key) {
				return jq.metroselect_m('getValue');
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				if (val) {
					jq.metroselect_m('prepareSelect', val);
				}
			},
			disable : function(jq, val){
				jq.metroselect_m("disabled", !val);
			},
			validate : function(jq) {
				return $(jq.find('select')[0]).valid();
			}
		},
		icheckbox : {
			getValue : function(jq, key) {
				return jq.icheckbox('getValue');
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				if (val) {
					jq.icheckbox('setValue', val);
				}
			},
			disable : function(jq, val){
				jq.icheckbox("disabled", !val);
			},
			validate : function(jq) {
				return $(jq.find('input')[0]).valid();
			}
		},
		icheckradio : {
			getValue : function(jq, key) {
				return jq.icheckradio('getValue');
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				if (val) {
					jq.icheckradio('setValue', val);
				}
			},
			disable : function(jq, val){
				jq.icheckradio("disabled", !val);
			},
			validate : function(jq) {
				return $(jq.find('input')[0]).valid();
			}
		},
		jqfileupload2 : {
			getValue : function(jq, key) {
				return jq.data('saveId');
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				if (val) {
					jq.spFileUpload("setValue",val);
				}
			},
			disable : function(jq, val){
				jq.spFileUpload("disabled", !val);
			}
		},
		imageupload : {
			getValue : function(jq, key) {
				return jq.attr("randomparent");
			},
			setValue : function(jq, val,v) {
				val = getMtValue(val, v);
				if (val) {
					jq.attr("randomparent", val);
				}
			},
			disable : function(jq, val){
				//jq.spFileUpload("disabled", !val);
			}
		},
		office : {
			getValue : function(jq, key) {
				return jq.attr("randomparent");
			},
			setValue : function(jq, val) {
				if (val) {
					jq.attr({
						randomparent : val,
						isSet : true
					});
					jq.office("openDocs");
				}
			}
		},
		editorbt:{
			getValue:function(jq, key){
				return jq.code();
			},
			setValue:function(jq, val){
				//jq.next().find("div.note-editable.panel-body").html(val);
				jq.next().find("div.note-editable.panel-body").html(val.replace(/\\\"/gm,"\""));
			},
			disable : function(jq, val){
				jq.next().find("div.note-editable.panel-body").attr("contenteditable",!val);
			}
		},
		editor: {
			get: function(jq) {
				var key = jq.attr("id"),
					UE = window.UE || {};
				return UE.getEditor(key);
			},
			getText: function(jq) {
				$.fn[plugin].roleFunctions.editor.get(jq).getContentTxt();
			},
			getValue: function(jq) {
				return $.fn[plugin].roleFunctions.editor.get(jq).getContent();
			},
			setValue: function(jq, val, args) {
				if (val) {
					val = val.replace(/\\\"/gm,"\"");
					var key = jq.attr("id"),
						UE = window.UE || {};
						try {
							/**
							 * 简单转义(反转)
							 */
							if(/&lt/.test(val)){
								var $tmpDiv = $("<div>").html(val);
								val = $tmpDiv.get(0).innerText;
							}
						} catch(e){
							console.log(e);
						}
					UE.getEditor(key).ready(function() {
						this.setContent(val);
					});
				}
			}
		},
		custom: {
			setValue: function(jq, val, args) {
				customFunc.setValue(jq, args[2]);
			}
		},
		excelupload:{
			setValue : function(jq, val, args) {
				jq.excelFileUpload("setIniParam",args[2]);
			},
		},
		mtbutton : {
			disable : function(jq, val){
				jq.prop("disabled", true);
			},
			setValue : function(jq,val,v){
				val = getMtValue(val, v);
				jq.find("span.frame-variable")[0].innerHTML = pubsub.htmlUnescape4Html(val);
			}
		}

	};

	$.fn[plugin].roleFunctions = roleFunctions;

})(jQuery);

/**
 * 多种传参时，默认取最后一个
 * @param val
 * @param v
 * @returns
 */
function getMtValue(val, v){
	var value = val;
	if(v && v.length > 1){
		if(v[2] && v[2].length > 1){
			$.each(v[2], function(){
				value = this.value;
			});
		}
	}
	return value;
}

// 页面登录方法
var handleLogin = function(rule, params, args) {

	var username = $([]), password = $([]), button = $([]), verification = $([]), msgvalid = null;

	var pars = {},redirectUrl="";
	if (typeof params == "object") {
		for (var i = 0; i < rule.length; i++) {
			r = rule[i];
			if(!r.inid){
				//无输入新参时，为表达式
				pars[r.param] = params[r.param];
				continue;
			}
			pars[r.inid] = params[r.param];
			if (r.inid.indexOf("login_username") != -1) {
				username = $("#" + r.inid).find("input");
				pars.uname = r.param;
				pars.unameEle = username;
			} else if (r.inid.indexOf("login_password") != -1) {
				password = $("#" + r.inid).find("input");
				pars.pname = r.param;
				pars.pnameEle = password;
			} else if (r.inid.indexOf("login_verify") != -1) {
				verification = $("#" + r.inid).find("input");
				pars.vname = r.param;
				pars.vnameEle = verification;
			} else if(r.inid.indexOf("loginmsgvalid") != -1){
				msgvalid = $("#" + r.inid).find("input");
				pars.msgvalidname = r.param;
				pars.msgvalidnameEle = msgvalid;
			}
		}
		pars.redirectUrl = params.redirectUrl;
	}

	if (args[0]) {
		button = args[0].find("button");
	} else {
		button = $([]);
	}

	if (username.length == 1 && (password.length == 1 || msgvalid != null)){

		var uConfig = username.closest('div[data-role="login_username"]').data(
				"config");
		var pConfig = password.closest('div[data-role="login_password"]').data(
				"config");
		var bConfig = button.closest('div[data-role="login_actions"]').data(
				"config");
		if (verification.length == 1) {
			var vConfig = verification.closest(
					'div[data-role="login_verify"]').data("config");
		}
		var uname = "username", pname = "password", vname = "verification",msgvalidname="validateCode";
		var umessage = "用户名为必填项.", pmessage = "密码为必填项.", vmessage = "请输入图片验证码."
			,msgvalidmessage="请输入验证码";
		pars.toIndex = /*getUrlToSplitter() +*/ contextPath + "/index.jsp";
		pars.securityKey = contextPath+'/mx/login/getLoginSecurityKey';
		pars.loginUrl = contextPath+'/mx/login/doLogin';
		if (uConfig) {
			username.attr("name", pars.uname);
			uname = pars.uname;
			umessage = uConfig.validateInfo;
		}
		if (pConfig) {
			password.attr("name", pars.pname);
			pname = pars.pname;
			pmessage = pConfig.validateInfo;
		}

		if (bConfig) {
			pars.securityKey = bConfig.securityKey;
			pars.loginUrl = bConfig.loginUrl;
			if (bConfig.toIndex != "") {
				pars.toIndex = /*getUrlToSplitter() +*/ contextPath
						+ JSON.parse(bConfig.toIndex)[0].url;
			}
		}
		if (vConfig) {
			verification.attr("name", pars.vname);
			vname = pars.vname;
			vmessage = vConfig.validateInfo;
		}
		if(msgvalid){
			msgvalid.attr("name",pars.msgvalidname);
			msgvalidmessage = msgvalid.data("validateInfo");
		}

		var vrules = {};
		vrules[uname] = {
			required : true
		};
		vrules[pname] = {
			required : true
		};
		vrules[vname] = {
			required : true
		};
		vrules[msgvalidname] = {
			required : true
		};
		var vmessages = {};
		vmessages[uname] = {
			required : umessage
		};
		vmessages[pname] = {
			required : pmessage
		};
		vmessages[vname] = {
			required : vmessage
		};
		vmessages[msgvalidname] = {
			required : msgvalidmessage
		};

		window.isGobalAlert_handleLogin = false,window.gobalAlertError_handleLogin = "";
		if($('.alert-danger', $('.login-form'))[0]){
			isGobalAlert_handleLogin = true;
		}
		$('.login-form')
				.validate(
						{
							errorElement : 'span', // default input error
							// message container
							errorClass : 'help-block', // default input error
							// message class
							focusInvalid : true, // do not focus the last
							// invalid input

							rules : vrules,
							messages : vmessages,

							invalidHandler : function(event, validator) { // display
								// error
								// alert
								// on
								// form
								// submit
								$('.alert-danger', $('.login-form')).show();
							},

							highlight : function(element) { // hightlight error
								// inputs
								if ($(element).closest(
										'div[data-role="login_username"]').length > 0) {
									$(element).closest(
											'div[data-role="login_username"]')
											.addClass('has-error'); // set error
									// class to
									// the
									// control
									// group
								} else if ($(element).closest(
										'div[data-role="login_password"]').length > 0) {
									$(element).closest(
											'div[data-role="login_password"]')
											.addClass('has-error'); // set error
									// class to
									// the
									// control
									// group
								} else if ($(element).closest(
										'div[data-role="login_verify"]').length > 0) {
									$(element)
											.closest(
													'div[data-role="login_verify"]')
											.addClass('has-error'); // set error
									// class to
									// the
									// control
									// group
								}
							},

							success : function(label) {
								if (label
										.closest('div[data-role="login_username"]').length > 0) {
									label.closest(
											'div[data-role="login_username"]')
											.removeClass('has-error');
								} else if (label
										.closest('div[data-role="login_password"]').length > 0) {
									label.closest(
											'div[data-role="login_password"]')
											.removeClass('has-error');
								} else if (label
										.closest('div[data-role="login_verify"]').length > 0) {
									label
											.closest(
													'div[data-role="login_verify"]')
											.removeClass('has-error');
								}
								label.remove();
								// $('.alert-danger', $('.login-form')).hide();
							},

							errorPlacement : function(error, element) {
								if(isGobalAlert_handleLogin && !gobalAlertError_handleLogin){
									gobalAlertError_handleLogin = error.text();
								}
								if($('.alert-danger', $('.login-form'))[0] && error.text()){
									$('.alert-danger', $('.login-form')).find("span").html(error.text());
									return;
								}
								var ele;
								if (element
										.closest('div[data-role="login_username"]').length > 0) {
									ele = element.closest(
											'div[data-role="login_username"]')
											.children().last();
								} else if (element
										.closest('div[data-role="login_password"]').length > 0) {
									ele = element.closest(
											'div[data-role="login_password"]')
											.children().last();
								} else if (element
										.closest('div[data-role="login_verify"]').length > 0) {
									ele = element;
								}
								error.insertAfter(ele);
							},

							submitHandler : function(form) {
								// form.submit(); // form validation success,
								// call ajax form submit
								// checkUser();
							}
						});

		if ($('.login-form').validate().form()) {
			var param = {};
			if(pars.vnameEle){
				param[pars.vname]=pars.vnameEle.val();
				$.ajax({
					type : "POST",
					contentType:"application/x-www-form-urlencoded",
					url : contextPath+'/website/form/verification/checkcode',
					dataType : 'json',
					data: param,
					error : function(data) {
						alert('服务器处理错误！');
					},
					success : function(data) {
						if (data&&data.status){
							checkUser(pars);
						}else{	
							$('.alert-danger',$('.login-form')).show();
							$('.alert-danger',$('.login-form')).find("span").html("验证码错误！");
	//						opts.pnameEle.val("");
						}
					}
				});
			}else{
				checkUser(pars);
			}
			
		}else if(isGobalAlert_handleLogin){
			$('.alert-danger', $('.login-form')).find("span").html(gobalAlertError_handleLogin);
		}

		// $('button[frame-variable="loginBtn"]',$('.login-form')).on("click",function(){
		// if ($('.login-form').validate().form()) {
		// checkUser(toIndex);
		// }
		// });

	}
};

var checkUser = function(opts) {

	// var userName = $('div[data-role="login_username"] input').val();
	// var password = $('div[data-role="login_password"] input').val();

	jQuery
			.ajax({
				type : "POST",
				url : opts.securityKey,
				dataType : 'json',
				error : function(data) {
					alert('服务器处理错误！');
				},
				success : function(dataxy) {
					if (dataxy != null && dataxy.x_y != null
							&& dataxy.x_z != null) {

						//如果有密码，则根据规则生成密码，否则是以手机验证码登陆
						if(opts.pnameEle){
							var px = dataxy.x_y + opts.pnameEle.val() + dataxy.x_z;
							opts.pnameEle.val(px);	
						}

						var unameElex = opts.unameEle.val();
						unameElex=encodeURIComponent(unameElex);

						if(opts.rember && opts.rember == 1){
							//保存用户cookie
							if(!$.cookie){
								var $style = $('<script type="text/javascript" src="'+contextPath+'/webfile/js/jquery.cookie.js"></script>');
								$("body").append($style);
							}
							// var userInfo = $.cookie("userInfo");
							//保存多个用户账号信息
							// if(userInfo){
							// 	var indexof = userInfo.indexOf(unameElex);
							// 	if(indexof > -1 && ((userInfo[indexof+unameElex.length] == ",") || userInfo.length == indexof + unameElex.length)){
							// 		//已存在了不处理
							// 	}else{
							// 		userInfo += "," + unameElex;
							// 	}
							// }else{
							// 	userInfo = unameElex;
							// }
							// $.cookie("userInfo",userInfo);
							//保存单个用户信息
							var cookieopts = {};
							if(opts.expires){
								cookieopts.expires = opts.expires;
							}
							$.cookie("userInfo",unameElex,cookieopts);
						}
						
						

						var link = opts.loginUrl + "?" + opts.uname + "="
								+ unameElex
								+ "&responseDataType=json";
						// var params = {};
						// var formData = $("form.login-form").serializeArray();
						// $.each(formData,function(i,item){
						// 	params[item.name] = item.value;
						// })
						var params = $("form.login-form").serialize();
						// if(opts.redirectUrl){
							// params["redirectUrl"] = opts.redirectUrl
						// }
						
						jQuery
								.ajax({
									type : "POST",
									url : link,
									dataType : 'json',
									data : params,
									error : function(data) {
										alert('服务器处理错误！');
										// if(data.status == 200){
										// 	location.href = redirectUrl;
										// }else{
										// 	alert('服务器处理错误！');
										// }
									},
									success : function(data) {
										if (data != null) {
											if (data.statusCode == 200) {// 登录成功
												if(opts.redirectUrl){
													window.parent.location = opts.redirectUrl;	
												}else{
													console.log(opts.toIndex);
													top.location = opts.toIndex;	
												}
											} else {
												if (data.message != null) {
													// alert(data.message);
													var remainMIN = 0;
													var alertMessage = data.message;
													if(data.remainMs){
														//剩余分
														remainMIN = Math.ceil(data.remainMs/1000/60);
														alertMessage+=",剩余"+remainMIN+"分";
													}
													$('.alert-danger',
															$('.login-form'))
															.show();
													$('.alert-danger',
															$('.login-form'))
															.find("span")
															.html(alertMessage);
													if(opts.pnameEle){
														opts.pnameEle.val("");
													}
												}
											}
										} else {
											alert('服务器处理错误！');
										}
									}
								});
					}
				}
			});

};

(function($) {

	return;
	
	var plugin = "office", optionKey = plugin + ".options";

	$.fn[plugin] = function(options, param) {
		if (typeof options == 'string') {
			return $.fn[plugin].methods[options](this, param);
		}
		options = options || {};
		return this.each(function(i, o) {
			var state = $.data(o, optionKey);
			if (state) {
				$.extend(true, state.options, options);
			} else {
				$.data(o, optionKey, {
					options : $
							.extend(true, {}, $.fn[plugin].defaults, options),
					target : null
				});
			}
			create(o);
		});
	};

	function create(target) {

		var randomUUID = new UUID().createUUID();

		var $this = $(target).attr({
			randomparent : randomUUID
		}), state = $this.data(optionKey), options = state.options;

		var key = $this.attr("key");
		if (key == 'true') { //
			new buildKey($this).build();
		}
		officeForm.randomparent.value = randomUUID;
		
	}

	function buildKey(jq) {

		this.jq = jq;

		this.options = this.jq.data(optionKey).options;

		var keyBoard = [ "<div class='col-xs-2'>",
				"<br><br><ul style='list-style-type:none'></ul>", "</div>" ];
		var liStr = "<li><span style='font-size: 16px; font-weight: bolder;'>{0}</span></li>";

		this.build = function() {
			var that = this;
			url = contextPath + '/mx/form/defined/getFormCompoentProperties';
			jQuery
					.ajax({
						url : url,
						data : {
							id : this.jq.attr("id"),
							pageId : this.options.pageId,
							dataRole : "office"
						},
						dataType : 'json',
						async : true,
						success : function(ret) {
							if (ret && ret.rows) {
								var collection = new Object(), p = {};
								ret.rows.sort(function(a, b) {
									return b.listNo - a.listNo; // 根据容器属性排序(倒序)
								});
								$
										.each(
												ret.rows,
												function(i, v) {
													if (v.parentId) {
														if (!collection[v.parentId]) {
															collection[v.parentId] = new Array();
														}
														collection[v.parentId]
																.push(v);
													} else {
														p[v.name] = v;
													}
													if (v.id in collection) {
														v.children = collection[v.id];
													} else {
														v.children = collection[v.id] = new Array();
													}
												});
								if (p.officeKey) {
									var lis = new StringBuffer();
									$
											.each(
													p.officeKey.children,
													function(i, v) {

														if (v.children) {
															var parentTitle = false;
															$
																	.each(
																			v.children,
																			function(
																					$i,
																					$v) {
																				if ($v.value == 'true') {
																					if (!parentTitle) {
																						parentTitle = true;
																						lis
																								.appendFormat(
																										liStr,
																										v.title);
																					}
																					lis
																							.appendFormat(
																									"<li onclick='{0}'>{1}</li>",
																									$v.value_,
																									$v.title);
																				}
																			});
															lis.append("<br>");
														}

													});
									var $keyBoard = $(keyBoard.join(""));
									$keyBoard.find("ul").append(lis.toString());
									that.jq.find(">div").before($keyBoard);
								}
							}
						}
					});

		};

	}

	$.fn[plugin].methods = {
		openDocs : function(jq) {
			var isSet = jq.attr("isSet"), docUrl = null;
			if (isSet == 'true') {
				$.ajax({
					url : contextPath + "/mx/form/attachment?" + $.param({
						method : 'getFormAttachmentByRandomParent',
						randomparent : jq.attr("randomparent")
					}),
					async : true,
					type : 'post',
					dataType : 'JSON',
					success : function(ret) {
						if (ret) {
							var ext = /\.[^\.]+/.exec(ret.fileName);

							officeForm.filename.value = ret.fileName.replace(
									ext, '');
							officeForm.attachmentId.value = ret.id;
							officeForm.randomparent.value = ret.parent;
						}
					}
				});
				docUrl = contextPath + "/mx/form/attachment?" + $.param({
					method : 'getOfficeByRandomParent',
					randomparent : jq.attr("randomparent")
				});
			} else {
				docUrl = contextPath + "/webfile/templates/ntko/blank.docx";
			}
			window.intializePage && window.intializePage(docUrl);
		}
	};

})(jQuery);

var pageFunc = {
	getValue : function(rule, args) {
		if (mtxx.params) {
			return mtxx.params[rule.inparam];
		}
		return null;
	},
	getRow : function(rule, args) {
		var targer = pubsub.getJQObj(rule);
		var params = targer.data("params");
		if (params) {
			return params.value;
		}
		return null;
	},
	linkageControl : function(rule, params) {
		pageFunc.linkagePage(rule, params);
	},
	linkagePage : function(rule, params) {
		params.newWin = '1';
		var p = pubsub.paramsToStr(params), id = rule;
		if (typeof rule == 'object') {
			id = rule[0].outid;
		}
		$("iframe[src*='" + id + "'],iframe[prosrc*='" + id + "']").each(
				function(i) {
					$this = $(this);
					if (!$this.attr('prosrc')) {
						$this.attr('prosrc', $this.attr('src'));
					}
					$this.attr('src', $this.attr('prosrc') + p);
				});
	},
	newWindow : function(id, params, args) { // 弹出窗口
		// args[0]
		// 格式{link:{url:'xxxx',name:'图片链接',id:'xx'},model:true,title:'标题',jumpType:'singlePage',width:'200px',height:'100px'}
		var obj = args[0];
		if (obj && obj.jumpType) {
			var p = pubsub.paramsToStr(params); // 参数
			var path;
			var f = "";
			if (obj.slink) { // charts 查看原始数据专用
				if (obj.fillform) {
					f = "&fillform=" + obj.fillform;
				}
				/*
				 * if(obj.slink.startsWith("/mx")){ obj.slink = contextPath +
				 * obj.slink ; } path = obj.slink + "?" + p + f
				 */;
			}
			var link = JSON.parse(obj.link), url = link.url;
			path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + p
					+ f;
			if (obj.jumpType == 'singlePage') {
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval(obj.height
						.replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval(obj.width
						.replace('px', ''))) / 2; // 获得窗口的水平位置;
				if (obj.model) {
					window.open(path, obj.title, "width=" + obj.width
							+ ",height=" + obj.height + ",top=" + iTop
							+ ",left=" + iLeft + ", modal=true,status=no");
				} else {
					var kendoWindow = $('#mt-popwindow').data("kendoWindow")
							|| $("<div id='mt-popwindow'></div>").kendoWindow({
								modal : obj.model,
								title : obj.title,
								width : obj.width,
								height : obj.height,
								/*
								 * position : { top : iTop, left : iLeft },
								 */
								iframe : true
							}).data("kendoWindow");
					kendoWindow.refresh({
						url : path,
					});
					kendoWindow.open();
					kendoWindow.center();
					if (obj.maximize == "true") {
						kendoWindow.maximize();
					}
				}
			}
		}else{ //事件定义器中打开页面
			var p = pubsub.paramsToStr(params,['isMax','width','height','singlePage','model','title']), // 参数
				url = id[0].srcUrl ,
				path = contextPath + url + ((url.indexOf("?") > -1) ? "" : "?") + p;
			if(!params.singlePage){
				window.open(path);
			} else {
				var iTop = (window.screen.availHeight - 30 - eval((params.height+"").replace('px', ''))) / 2; // 获得窗口的垂直位置;
				var iLeft = (window.screen.availWidth - 10 - eval((params.width+"").replace('px', ''))) / 2; // 获得窗口的水平位置;
				if (params.model) {
					window.open(path, params.title, "width=" + params.width
							+ ",height=" + params.height + ",top=" + iTop
							+ ",left=" + iLeft + ", modal=true,status=no");
				} else {
					var kendoWindow = $('#mt-popwindow').data("kendoWindow")
							|| $("<div id='mt-popwindow'></div>").kendoWindow({
								modal : params.model,
								title : params.title,
								width : params.width,
								height : params.height,
								iframe : true
							}).data("kendoWindow");
					kendoWindow.refresh({
						url : path,
					});
					kendoWindow.open();
					kendoWindow.center();
					if (params.isMax) {
						kendoWindow.maximize();
					}
				}
			}
		}
	},
	pageClose : function(rule, args) {
		var that = pubsub.getThat(rule);
		if (that.top.location.href == that.location.href) {
			that.close();
		} else {
			var kendoWindows = that.parent.$('[data-role=window]');
			kendoWindows.each(function(i, k) {
				var tw = that.parent.$(k).data('kendoWindow');
				if (tw.content().indexOf(rule.outid) != -1)
					tw.close();
			});
		}
	},
	grefresh : function(rule, args) {
		var that = pubsub.getThat(rule);
		// that.location.href = that.location.href ;
		that.location.reload();
	},
	/**
	 * 保存操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtSave : function(rule, args) {
		var msg = $(document.body).data("saveMsg");
		if(msg){
			//btn.save();
			$.each(msg, function(i, btn){
				if(btn && btn.save){
					btn.save();
				}
			});
		}
	},
	/**
	 * 登录操作
	 * 
	 * @param rule
	 * @param args
	 */
	mtLogin : function(rule,params,args) {
		handleLogin(rule,params,args);
	},
	/**
	 * 流程提交
	 * 
	 * @param rule
	 * @param args
	 */
	mtSubmit : function(rule, args) {
		if(window.mtxx && window.mtxx.params){
			if(confirm("你确定提交吗?")){
				var pageId = mtxx.params.id, p = $.extend(true, {}, args, {
					approve : true
				}), id = $("." + pageId).attr("idValue");
				var data = {
						pageId : pageId,
				};
				data.flowParams = JSON.stringify(p);
				data[pageId] = id;
				$.ajax({
					url : contextPath + "/mx/form/workflow/defined/submit",
					type : "POST",
					dataType : "JSON",
					data : data,
					success : function(ret){
						alert("操作成功!");
					}
				});
			}
		}
	}
};
pubsub.sub("page", pageFunc);

function openDialog(e, el) {
	e.preventDefault();
	var $target = $(e.target).closest("a");
	if(!$target[0]){
		$target = $(e.currentTarget);
	}
	var dialogHeight = $target.attr("dialogHeight");
	var dialogWidth = $target.attr("dialogWidth");
	var isModel = $target.attr("isModel");
	var url = $target.attr("ahref");
	var title = $target.attr("title") || $target.text();
	var iTop = (window.screen.availHeight - 30 - dialogHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - dialogWidth) / 2; // 获得窗口的水平位置;
	// var iTop = e.screenY; // 获得窗口的垂直位置;
	// var iLeft = e.screenX; // 获得窗口的水平位置;
	if (isModel == "true") {
		if (url.startsWith(contextPath)) {
			url = url.replace(contextPath, "");
		}
		var link = {
			url: url,
			name: title,
			id: 'xx'
		};
		var rules = {
			link: JSON.stringify(link),
			model: true,
			title: title,
			jumpType: 'childPage',
			width: dialogWidth,
			height: dialogHeight
		};
		pageFunc.newWindow("", "", [rules]);
	} else {
		var option = "height=" + dialogHeight + ",width=" + dialogWidth + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
		window.open(url, title, option);
	}
}

/**
 * 数组排序
 */
var SortArray = function(datas, sortBy, desc){
	if(sortBy && datas && datas.length){
		try {
			//eg : sortBy = index_id asc, id desc
			function sort(key, dc){
				datas.sort(function(a, b){
					var rst = (a[key] || 0) - (b[key] || 0);
					return dc ? -rst : rst;
				});
			};
			var sortBys = sortBy.split(",")	;
			$.each(sortBys, function(i, v){
				var sbs = v.split(" "), key = sbs[0].trim(), dc = sbs[1];
				if(dc && (dc = dc.trim())){
					dc = dc == "desc";
				} else {
					dc = desc;
				}
				sort(key, dc);
			});
		} catch(e) {
			console.log(e);
		}
	}
};