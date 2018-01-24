//注册返回JSON对象  使用方法 {{# data | stringify }}
template.helper('stringify', function (data) {
    return JSON.stringify(data);
});

var ArtTemplateDataUtils = {
	ajax: function(dataRole){
		return {
			url: contextPath+'/service/designer/'+dataRole+'/getTemplateMapping',
			type: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			async : true
		};
	},
	convertList: function(datas, source){
		var obj = {};
		obj.list = datas;
		var render = template.compile(source);
		return render(obj);
	},
	convertOne: function(obj, source){
		var render = template.compile(source);
		return render(obj);
	}
};
