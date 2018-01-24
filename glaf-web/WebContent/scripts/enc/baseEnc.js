(function() {

	var data = {
			app_id: 'flk_test', //应用ID
			organization_id: 'hzflk', //组织ID
			member_id: 1.2, //用户
			token: 112, //token
			version: '1.0.0', //版本号
		}, //用户配置信息
		sysParams = {

		};
	//kms服务器地址

	/*flkEnc.getKey('http://cde.qiyemixin.cn:8080', data, function(res, iv) {
		sysParams.res = res;
		sysParams.iv = iv;
	})*/

	function enc(data) {
		var text = null;
		flkEnc.enc(sysParams.res, sysParams.iv, data, '', function(res, cipher, metaId) {
			text = cipher;
		})
		return text;
	}
	function dec(data){
		return flkEnc.dec;
	}
	window.chinaissEnc = {
		enc: enc,
		dec: dec
	};

	var _open = window.XMLHttpRequest.prototype.open;
	window.XMLHttpRequest.prototype.open = function() {
		var XMLReq = this,
			args = [].slice.call(arguments);
		var _onreadystatechange = XMLReq.onreadystatechange || function() {};
		XMLReq.onload = XMLReq.onreadystatechange = function() {
console.log(XMLReq.readyState);
console.log(XMLReq.status);
			if (XMLReq.readyState == 4) {

				if (XMLReq.status == 200) {
					if(typeof XMLReq.response == "string" && XMLReq.response.indexOf("___chinaiss___hmtd___encrypts___")>=0){
						var resObj = JSON.parse(XMLReq.response),
							encObj = resObj.encrypts;
							result = resObj.result;
						if(result && result.data && result.data.length){
							var encCols = [];
							for(var i =0;i<encObj.length;i++){
								encCols.push(encObj[i].columnLabel);
							}
							for(var j =0;j<result.data.length;j++){
								var data = result.data[j];
								for(var k =0;k<encCols.length;k++){
									data[encCols[k]] = dec(data[encCols[k]]);
								}
							}
							XMLReq.response = JSON.stringify(result);
							XMLReq.responseText = XMLReq.response;
						}
					}
				} else {
					console.error(XMLReq.response);
				}
			}
			return _onreadystatechange.apply(XMLReq, arguments);
		};
		return _open.apply(XMLReq, args);
	};
})();