DingTalkPC.config({
    agentId : _config.agentid,
    corpId : _config.corpId,
    timeStamp : _config.timeStamp,
    nonceStr : _config.nonceStr,
    signature : _config.signature,
    jsApiList : [ 'runtime.info', 'biz.contact.choose',
        'device.notification.confirm', 'device.notification.alert',
        'device.notification.prompt', 'biz.ding.post',
        'biz.util.openLink' ]
});


DingTalkPC.ready(function() {
    DingTalkPC.runtime.info({
        onSuccess : function(info) {
            logger.e('runtime info: ' + JSON.stringify(info));
        },
        onFail : function(err) {
            logger.e('fail: ' + JSON.stringify(err));
        }
    });
    DingTalkPC.ui.pullToRefresh.enable({
        onSuccess: function() {
        },
        onFail: function() {
        }
    })


    DingTalkPC.runtime.permission.requestAuthCode({
        corpId : _config.corpId,
        onSuccess : function(info) {
			console.log("code:"+code);
			console.log("corpid:"+_config.corpId);
			alert(code);
            location.href=contextPath+'/website/dingtalk/login/index?code=' + info.code + '&corpid='
						+ _config.corpId;
		},
		onFail : function(err) {
			alert('获取授权验证码失败！');
			location.href=contextPath;
		}
    });
});

DingTalkPC.error(function(err) {
    alert('dd error: ' + JSON.stringify(err));
});
