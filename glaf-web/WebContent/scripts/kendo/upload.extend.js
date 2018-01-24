/**
 * 上传
 */
var uploadFunc = {
	init : function(rule, args) {
		var options = args[0];
		$("#" + rule.inid).kendoUpload(options);
	}
}
pubsub.sub("upload", uploadFunc);