/**
 * @Author: Larry  2017-04-16 17:20:56
 *+----------------------------------------------------------------------
 *| LarryBlogCMS [ LarryCMS网站内容管理系统 ]
 *| Copyright (c) 2016-2017 http://www.larrycms.com All rights reserved.
 *| Version 1.09
 *| <313492783@qq.com>
 *+----------------------------------------------------------------------
 */
layui.use(['jquery', 'layer', 'form'], function() {
    'use strict';
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form();

    $(window).on('resize', function() {
        var w = $(window).width();
        var h = $(window).height();
        $('.larry-canvas').width(w).height(h);
    }).resize();

    form.verify({
        account: function(value) {
            if (value.length < 5) {
                return '请输入帐号';
            }
        },
        password: [/(.+){6,12}$/, '密码必须6到12位']
    });
	$(document).keyup(function(event){
	  if(event.keyCode ==13){
		$(".submit_btn").trigger("click");
	  }
	});

		
    //登录
    form.on('submit(submit_btn)', function(data) {
        /*layer.alert(JSON.stringify(data.field), {
            title: '最终的提交信息'
        })*/
        jQuery.ajax({
            type: "POST",
            url: "/glaf/mx/login/getLoginSecurityKey",
            dataType: 'json',
            error: function(data) {
                //alert('服务器处理错误！');
                document.getElementById("loginBt").disabled = false;
                //map.remove("login_status");
            },
            success: function(dataxy) {
                if (dataxy != null && dataxy.x_y != null && dataxy.x_z != null) {
                    var px = dataxy.x_y + data.field.password + dataxy.x_z;
                    var link = "/glaf/mx/login/doLogin?x=" + data.field.account +
                        "&responseDataType=json";
                    var params = $.param({"y":px});
                    //alert(params);
                    jQuery.ajax({
                        type: "POST",
                        url: link,
                        dataType: 'json',
                        data: params,
                        error: function(data) {
                            console.log(data);
                            //alert('服务器处理错误！');
                            //map.remove("login_status");
                            //$("#loginBt:enabled");
                        },
                        success: function(data) {
                            if (data != null) {
                                if (data.statusCode == 200) { //登录成功
                                    //map.remove("login_status");
                                    //alert("登录成功,准备跳转...");
                                    window.location = "/glaf/index.jsp";
                                } else {
                                    if (data.message != null) {
                                        //map.remove("login_status");
                                        alert(data.message);
                                        // document.getElementById("y").value = "";
                                        // document.getElementById("yy").value = "";
                                        // document.getElementById("x_y").value = "";
                                        // document.getElementById("x_z").value = "";
                                        //$("#loginBt:enabled");
                                    }
                                }
                            } else {
                                //alert('服务器处理错误！');
                               // map.remove("login_status");
                               // $("#loginBt:enabled");
                            }
                        }
                    });
                }
            }
        });

        return false;
    });
    $(function() {
        $("#canvas").jParticle({
            background: "#141414",
            color: "#E5E5E5"
        });
    });

});