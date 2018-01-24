/**
 * Created by liqiao on 8/10/15.
 */

/**
 * _config comes from server-side template. see views/index.jade
 */
dd.config({
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

dd.ready(function() {
	dd.runtime.permission.requestAuthCode({
		corpId : _config.corpId,
		onSuccess : function(info) {
            location.href=contextPath+'/website/dingtalk/login/index?code=' + info.code + '&corpid='
						+ _config.corpId;
		},
		onFail : function(err) {
			alert('获取授权验证码失败！');
			location.href=contextPath;
		}
	});
});

dd.error(function(err) {
	alert('dd error: ' + JSON.stringify(err));
});
