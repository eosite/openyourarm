var videoId = 1, VideoNum = 9;

jQuery(function() {

	var $this = $("[data-role=video]");
	setTimeout(function(){		
		var thisWidth = $this.width(), thisHeight = $this.height()
		thisHeight == 0 && $this.css({
			height : 400
		});
		thisHeight = thisHeight == 0 ? 400 : thisHeight;
		$this.find('.mt-separate').css({
			left : thisWidth - 230,
			height : thisHeight - 6
		}).end().find(".mt-left").css({
			width : thisWidth - 233,
			height : thisHeight - 3
		}).end().find(".mt-right").css({
			height : thisHeight - 3,
			left : thisWidth - 212
		});
		$("#OCXBody").css({
			height : $(window).height() * 0.7
		});
	}, 0);

	if (!videoId) {
		return ErrorParams();
	}
	
	VideoNum = $("#OCXBody").find("div.smallocxdiv").length;
	
	var myDate = new Date();
	var iYear = myDate.getFullYear();
	
	
	m_bDVRControl = document.getElementById("sv_web_live_ctrl");
//	if (m_bDVRControl.object == null) {
//		alert("请先下载控件并注册！");
//	} else {
		ChangeStatus(2);
		ArrangeWindow(1);
                //initNetVideoActiveX23();
		initTools();
//	}

	
	$(document.body).attr({
		onunload : "closeAllOcx();",
		onbeforeunload  : "closeAllOcx();"
	});
	
	
	
});


function initNetVideoActiveX23() {
	initTools();
	
}

// 全局变量定义
var m_bDVRControl = null;
var m_iLoginUserId = -1; // 注册设备用户ID
var m_iChannelNum = -1; // 模拟通道总数
var m_iIPChannelNum = -1; // IP通道总数
var m_iProtocolType = 0; // 协议类型，0 – TCP， 1 - UDP
var m_iStreamType = 0; // 码流类型，0 表示主码流， 1 表示子码流
var m_szDeviceType = 0; // 设备类型

var CURRENTOCX = null;
/**
 * CURRENTOCX
 * 
 * @param sKey
 * @param ocx
 * @returns
 */
function ButtonPress(sKey) {
	if (CURRENTOCX) {
		return CURRENTOCX[sKey]();
	}
	return null;
}
function ptz_up() {
	sv_web_live_ctrl.PTZControl(101, 50, "");
}
function ptz_down() {
	sv_web_live_ctrl.PTZControl(102, 50, "");
}
function ptz_left() {
	sv_web_live_ctrl.PTZControl(103, 50, "");
}
function ptz_right() {
	sv_web_live_ctrl.PTZControl(104, 50, "");
}
function ptz_stop() {
	sv_web_live_ctrl.PTZControl(0, 0, "");
}	
/**
 * 登录
 * 
 * @param videoId
 */
function login(videoId, m_bDVRControl) {
	
}
var isLogin = false;
function loginNetVideo(data, m_bDVRControl) {
    window.sv_data = data;
    LogMessage("\u5f00\u59cb\u767b\u5f55");
	if (data && !isLogin) {
		m_iLoginUserId = sv_web_live_ctrl.SetLoginInfo(data.ip,parseInt(data.port), data.username, data.password);
		if (m_iLoginUserId == -1) {
			LogMessage("\u767b\u9646\u5931\u8d25");
		} else if(m_iLoginUserId == 2001){
		    m_iLoginUserId = sv_web_live_ctrl.SetLoginInfo(data.ip,parseInt(data.port), data.username, data.password);
			if(m_iLoginUserId == 0){
			LogMessage("\u767b\u9646\u6210\u529f");
			isLogin = true;
			
//			getChannelList(m_bDVRControl);
			sv_web_live_ctrl.StartLive(data.cameraId);
			console.log(data.cameraId);
			}
			else{
            LogMessage("\u767b\u9646\u5931\u8d25");
			}
		} else {
			LogMessage("\u767b\u9646\u6210\u529f");
			isLogin = true;
			
//			getChannelList(m_bDVRControl);
			sv_web_live_ctrl.StartLive(data.cameraId);
		}
	} else {
		return ErrorParams();
	}
}

/**
 * 获取通道
 * 
 * @param m_bDVRControl
 */
function getChannelList(m_bDVRControl) {
	var szServerInfo = m_bDVRControl.GetServerInfo();
	var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	xmlDoc.async = "false";
	xmlDoc.loadXML(szServerInfo);

	m_iChannelNum = parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
	m_iIPChannelNum = parseInt(xmlDoc.documentElement.childNodes[8].childNodes[0].nodeValue);
	m_szDeviceType = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
	var cl = new ChannelList();
	if (m_iChannelNum < 1) {
		LogMessage("\u83b7\u53d6\u6a21\u62df\u901a\u9053\u5931\u8d25！");
	} else {
		LogMessage("\u83b7\u53d6\u6a21\u62df\u901a\u9053\u6210\u529f");
		// document.getElementById("ChannelList").length = 0; // 先清空下拉列表
		for (var i = 0; i < m_iChannelNum; i++) {
			var szChannelName = m_bDVRControl.GetChannelName(i);
			if (szChannelName == "") {
				szChannelName = "通道" + (i + 1);
			}
			// document.getElementById("ChannelList").options.add(new Option(
			// szChannelName, i));
			cl.addChannel(szChannelName, i);
		}
	}
	if (m_iIPChannelNum < 1) {
		LogMessage("\u83b7\u53d6IP\u901a\u9053\u5931\u8d25\uff01");
	} else {
		LogMessage("\u83b7\u53d6IP\u901a\u9053\u6210\u529f\uff01");
		m_bDVRControl.GetIPParaCfg();
		var szIPChanInfo = m_bDVRControl.GetIPCConfig();
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = "false";
		xmlDoc.loadXML(szIPChanInfo);
		for (var i = m_iChannelNum; i < m_iChannelNum + m_iIPChannelNum; i++) {
			var m_iIPConnet = parseInt(xmlDoc.documentElement.childNodes[i].childNodes[3].childNodes[0].nodeValue);
			if (m_iIPConnet == 1) {
				LogMessage("\u83b7\u53d6IP\u901a\u9053" + (i - m_iChannelNum + 1) + "\u6210\u529f\uff01");
				var szChannelName = m_bDVRControl.GetChannelName(i);
				if (szChannelName == "") {
					szChannelName = "IP通道" + (i - m_iChannelNum + 1);
				}
				// document.getElementById("ChannelList").options.add(new
				// Option(
				// szChannelName, i));
				cl.addChannel(szChannelName, i);
			}
		}
	}

}

function initTools() {

	$(".k-music").on('click', function() {
		var $this = $(this);
		var is = !!!$this.data("is"), t = 'voice:start';
		if (!is) {
			t = 'voice:stop';
		}
		var rst = ButtonPress(t);
		if (rst) {
			$this.data("is", is);
			$this.find('.k-image').attr({
				src : contextPath + "/images/music-" + is + ".png"
			});
		}
	});

	$(".k-btn").each(function() {
		var t = $(this).attr("t");
		$(this).on("mousedown", function() {
			ButtonPress(t);
			if(t == "PTZ:stop"){
				sv_web_live_ctrl.PTZControl(0, 0, "");
			} else if(t == "PTZ:up"){
				sv_web_live_ctrl.PTZControl(101, 10, "");
			} else if(t == "PTZ:leftup"){
				sv_web_live_ctrl.PTZControl(105, 10, "");
			} else if(t == "PTZ:rightup"){
				sv_web_live_ctrl.PTZControl(108, 10, "");
			} else if(t == "PTZ:left"){
				sv_web_live_ctrl.PTZControl(103, 10, "");
			} else if(t == "PTZ:right"){
				sv_web_live_ctrl.PTZControl(104, 10, "");
			} else if(t == "PTZ:leftdown"){
				sv_web_live_ctrl.PTZControl(106, 10, "");
			} else if(t == "PTZ:rightdown"){
				sv_web_live_ctrl.PTZControl(108, 10, "");
			} else if(t == "PTZ:down"){
				sv_web_live_ctrl.PTZControl(102, 10, "");
			} else if(t == "zoom:in"){
				sv_web_live_ctrl.PTZControl(203, 10, "");
			} else if(t == "zoom:out"){
				sv_web_live_ctrl.PTZControl(204, 10, "");
			} else if(t == "PTZ:play"){
				sv_web_live_ctrl.StartLive(window.sv_data.cameraId);
			} else if(t == "sound:open"){
				sv_web_live_ctrl.object.OpenVouice();
                LogMessage("\u5f00\u542f\u58f0\u97f3");
			} else if(t == "sound:close"){
				sv_web_live_ctrl.object.CloseVoice();
				LogMessage("\u5173\u95ed\u58f0\u97f3");
			} else if(t == "CatPic:bmp"){
					var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
					var code = sv_web_live_ctrl.object.Capture("C:\\Picture\\"+date+".bmp");
					if (code > 0) {
						LogMessage("\u6293BMP\u56fe\u6210\u529f\uff01");
					} else {
						LogMessage("\u6293BMP\u56fe\u5931\u8d25\uff01");
					}
				
			} else if(t == "Record:start"){
						var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
						var code = sv_web_live_ctrl.object.Capture("C:\\Picture\\"+date+".bmp");
						if (sv_web_live_ctrl.object.StartRecord("C:/OCXRecordFiles")) {
							LogMessage("\u5f00\u59cb\u5f55\u50cf\u6210\u529f\uff01");
							this.m_iRecord = 1;
						} else {
							LogMessage("\u5f00\u59cb\u5f55\u50cf\u5931\u8d25\uff01");
						}
					
				
			} else if(t == "Record:stop"){
					if (sv_web_live_ctrl.object.StopRecord(1)) {
						LogMessage("\u505c\u6b62\u5f55\u50cf\u6210\u529f\uff01");
						this.m_iRecord = 0;
					} else {
						LogMessage("\u505c\u6b62\u5f55\u50cf\u5931\u8d25\uff01");
					}
				
			}
			
		}).on('mouseup', function() {
			ButtonPress("PTZ:stop");
		});
	});

}

function NetVideoActiveX23Func(sv_web_live_ctrlID) {
	this.sv_web_live_ctrlID = sv_web_live_ctrlID;
	this.m_bDVRControl = document.getElementById(this.sv_web_live_ctrlID);// OCX控件对象
	this.channelNo = -1; // 当前通道号(不变)
	this.m_iNowChanNo = -1; // 当前通道号
	this.m_iLoginUserId = m_iLoginUserId; // 注册设备用户ID
	this.m_iChannelNum = m_iChannelNum; // 模拟通道总数
	this.m_iIPChannelNum = m_iIPChannelNum; // IP通道总数
	this.m_iProtocolType = m_iProtocolType; // 协议类型，0 – TCP， 1 - UDP
	this.m_iStreamType = m_iStreamType; // 码流类型，0 表示主码流， 1 表示子码流
	this.m_iPlay = 0; // 当前是否正在预览
	this.m_iRecord = 0; // 当前是否正在录像
	this.m_iTalk = 0; // 当前是否正在对讲
	this.m_iVoice = 0; // 当前是否打开声音
	this.m_iAutoPTZ = 0; // 当前云台是否正在自转
	this.m_iPTZSpeed = 1; // 云台速度
	this.m_szDeviceType = m_szDeviceType; // 设备类型
	this.m_iPlayback = 0; // 是否回放状态
	this.m_iDownload = 0; // 是否下载状态
}

/**
 * 注册
 */
NetVideoActiveX23Func.prototype.LoginDev = function(videoId) {
	// var that = this;
	// $.ajax({
	// data : {
	// id : videoId
	// },
	// async : false,
	// url : contextPath + "/mx/form/defined/getVideoSetById",
	// type : 'post',
	// dataType : 'JSON',
	// success : initNetVideo
	// });
	// function initNetVideo(data) {
	// if (data) {
	// that.m_iLoginUserId = that.m_bDVRControl.Login(data.szDevIp,
	// data.szDevPort, data.szDevUser, data.szDevPwd);
	// if (that.m_iLoginUserId == -1) {
	// LogMessage("注册失败！");
	// } else {
	// LogMessage("注册成功！");
	// for ( var i = 2; i <= 4; i++) {
	// document.getElementById("sv_web_live_ctrl" + i).SetUserID(
	// that.m_iLoginUserId);
	// }
	// }
	// } else {
	// return ErrorParams();
	// }
	// }
};

/**
 * 获取通道
 */
NetVideoActiveX23Func.prototype.getDevChan = function() {
	// var that = this;
	// that.szServerInfo = that.m_bDVRControl.GetServerInfo();
	// var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	// xmlDoc.async = "false";
	// xmlDoc.loadXML(that.szServerInfo);
	//
	// that.m_iChannelNum =
	// parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
	// that.m_iIPChannelNum =
	// parseInt(xmlDoc.documentElement.childNodes[8].childNodes[0].nodeValue);
	// that.m_szDeviceType =
	// xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
	// var cl = new ChannelList();
	// if (that.m_iChannelNum < 1) {
	// LogMessage("获取模拟通道失败！");
	// } else {
	// LogMessage("获取模拟通道成功！");
	// document.getElementById("ChannelList").length = 0; // 先清空下拉列表
	// for ( var i = 0; i < that.m_iChannelNum; i++) {
	// var szChannelName = that.m_bDVRControl.GetChannelName(i);
	// if (szChannelName == "") {
	// szChannelName = "通道" + (i + 1);
	// }
	// document.getElementById("ChannelList").options.add(new Option(
	// szChannelName, i));
	// cl.addChannel(szChannelName, i);
	// }
	// }
	// if (that.m_iIPChannelNum < 1) {
	// LogMessage("获取IP通道失败！");
	// } else {
	// LogMessage("获取IP通道成功！");
	// that.m_bDVRControl.GetIPParaCfg();
	// that.szIPChanInfo = that.m_bDVRControl.GetIPCConfig();
	// var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	// xmlDoc.async = "false";
	// xmlDoc.loadXML(that.szIPChanInfo);
	// for ( var i = that.m_iChannelNum; i < that.m_iChannelNum
	// + that.m_iIPChannelNum; i++) {
	// that.m_iIPConnet =
	// parseInt(xmlDoc.documentElement.childNodes[i].childNodes[3].childNodes[0].nodeValue);
	// if (that.m_iIPConnet == 1) {
	// LogMessage("获取IP通道" + (i - that.m_iChannelNum + 1) + "成功！");
	// var szChannelName = m_bDVRControl.GetChannelName(i);
	// if (szChannelName == "") {
	// szChannelName = "IP通道" + (i - that.m_iChannelNum + 1);
	// }
	// document.getElementById("ChannelList").options.add(new Option(
	// szChannelName, i));
	// cl.addChannel(szChannelName, i);
	// }
	// }
	// }
};

/**
 * 注销
 */
NetVideoActiveX23Func.prototype.LogoutDev = function() {
	if (this.m_bDVRControl.Logout()) {
		LogMessage("\u6ce8\u9500\u6210\u529f\uff01");
	} else {
		LogMessage("\u6ce8\u9500\u5931\u8d25\uff01");
	}
};

/**
 * 获取名称
 */
NetVideoActiveX23Func.prototype.getDevName = function() {
	var szDecName = this.m_bDVRControl.GetServerName();
	if (szDecName == "") {
		LogMessage("\u83b7\u53d6\u540d\u79f0\u5931\u8d25\uff01");
		szDecName = "Embedded Net DVR";
	} else {
		LogMessage("\u83b7\u53d6\u540d\u79f0\u6210\u529f！");
	}
	document.getElementById("DeviceName").value = szDecName;
};

/**
 * 预览
 */
NetVideoActiveX23Func.prototype["Preview:start"] = function() {
	var that = this;
	LogMessage(that.m_szDeviceType);
	if (that.m_iIPChannelNum >= 64) {
		LogMessage("IP\u901a\u9053\u4e2a\u6570\u5927\u4e8e\u7b49\u4e8e64\uff0c" + "\u901a\u9053\u53f7\u53d6\u503c\u4ece0\u5f00\u59cb\uff01");
		that.m_iIPChanStart = 0;
	} else {
		LogMessage("\u5982\u679c\u8bbe\u5907\u6709IP\u901a\u9053\uff0cIP\u901a\u9053\u53f7\u53d6\u503c\u4ece32\u5f00\u59cb\uff01");
		that.m_iIPChanStart = 32;
	}
	// that.m_iNowChanNo =
	// parseInt(document.getElementById("ChannelList").value);
	that.m_iNowChanNo = that.channelNo;
	if (that.m_iNowChanNo >= that.m_iChannelNum) {
		that.m_iNowChanNo = that.m_iNowChanNo - that.m_iChannelNum + that.m_iIPChanStart;
	}
	if (this.m_iNowChanNo > -1) {
		if (that.m_iPlay == 1) {
			that.m_bDVRControl.StopRealPlay();
			that.m_iPlay = 0;
		}
		var bRet = that.m_bDVRControl.StartRealPlay(that.m_iNowChanNo, that.m_iProtocolType, that.m_iStreamType);
		if (bRet) {
			LogMessage("\u9884\u89c8\u901a\u9053" + (that.m_iNowChanNo + 1) + "\u6210\u529f\uff01");
			that.m_iPlay = 1;
		} else {
			LogMessage("\u9884\u89c8\u901a\u9053" + (that.m_iNowChanNo + 1) + "\u5931\u8d25\uff01");
			var dRet = that.m_bDVRControl.GetLastError();
			LogMessage("\u9884\u89c8\u5931\u8d25\u9519\u8bef\u53f7\uff1a" + dRet);
		}
	} else {
		LogMessage("\u8bf7\u9009\u62e9\u901a\u9053\u53f7！");
	}
};

/**
 * 停止预览
 */
NetVideoActiveX23Func.prototype["Preview:stop"] = function() {
	if (this.m_bDVRControl.StopRealPlay()) {
		LogMessage("\u8bf7\u9009\u62e9\u901a\u9053\u53f7");
		this.m_iPlay = 0;
	} else {
		LogMessage("\u505c\u6b62\u9884\u89c8\u5931\u8d25\uff01");
	}
};

/**
 * 抓bmp图
 */
NetVideoActiveX23Func.prototype["CatPic:bmp"] = function() {
	if (this.m_iPlay == 1) {
		var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
		var code = sv_web_live_ctrl.Capture("C:\\Picture\\"+date+".bmp");
		if (code > 0) {
			LogMessage("\u6293BMP\u56fe\u6210\u529f\uff01");
		} else {
			LogMessage("\u6293BMP\u56fe\u5931\u8d25\uff01");
		}
	} else {
		LogMessage("\u8bf7\u5148\u9884\u89c8\uff01");
	}
};
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
/**
 * 抓jpeg图
 */
NetVideoActiveX23Func.prototype["CatPic:jpeg"] = function() {
	if (this.m_iPlay == 1) {
		if (sv_web_live_ctrl.JPEGCapturePicture((this.m_iNowChanNo + 1), 2, 0, "C:/OCXJPEGCaptureFiles", 1)) {
			LogMessage("抓JPEG图成功！");
		} else {
			LogMessage("抓JPEG图失败！");
		}
	} else {
		LogMessage("请先预览！");
	}
};

/**
 * 停止录音
 */
NetVideoActiveX23Func.prototype["Record:stop"] = function() {
	if (this.m_iRecord == 1) {
		if (sv_web_live_ctrl.StopRecord(1)) {
			LogMessage("\u505c\u6b62\u5f55\u50cf\u6210\u529f\uff01");
			this.m_iRecord = 0;
		} else {
			LogMessage("\u505c\u6b62\u5f55\u50cf\u5931\u8d25\uff01");
		}
	}
};

/**
 * 开始录像
 */
NetVideoActiveX23Func.prototype["Record:start"] = function() {
	if (this.m_iPlay == 1) {
		if (this.m_iRecord == 0) {
			var date = new Date().Format("yyyy-MM-dd HH:mm:ss");
			var code = sv_web_live_ctrl.Capture("C:\\Picture\\"+date+".bmp");
			if (sv_web_live_ctrl.StartRecord("C:/OCXRecordFiles")) {
				LogMessage("\u5f00\u59cb\u5f55\u50cf\u6210\u529f\uff01");
				this.m_iRecord = 1;
			} else {
				LogMessage("\u5f00\u59cb\u5f55\u50cf\u5931\u8d25\uff01");
			}
		}
	} else {
		LogMessage("\u8bf7\u5148\u9884\u89c8\uff01");
	}
};

/**
 * 开始录音
 */
NetVideoActiveX23Func.prototype["talk:start"] = function() {
	if (this.m_iLoginUserId > -1) {
		if (this.m_iTalk == 0) {
			if (sv_web_live_ctrl.StartTalk(1)) {
				LogMessage("\u5f00\u59cb\u5bf9\u8bb2\u6210\u529f\uff01");
				this.m_iTalk = 1;
			} else {
				LogMessage("\u5f00\u59cb\u5bf9\u8bb2\u5931\u8d25\uff01");
			}
		}
	} else {
		LogMessage("\u8bf7\u6ce8\u518c\u8bbe\u5907\uff01");
	}
};

/**
 * 停止录音
 */
NetVideoActiveX23Func.prototype["talk:stop"] = function() {
	if (this.m_iTalk == 1) {
		if (sv_web_live_ctrl.StopTalk()) {
			LogMessage("\u505c\u6b62\u5bf9\u8bb2\u6210\u529f\uff01");
			this.m_iTalk = 0;
		} else {
			LogMessage("\u505c\u6b62\u5bf9\u8bb2\u5931\u8d25\uff01");
		}
	}
};

/**
 * 打开声音
 */
NetVideoActiveX23Func.prototype["voice:start"] = function() {
	if (this.m_iPlay == 1) {
		if (this.m_iVoice == 0) {
			if (this.m_bDVRControl.OpenSound(1)) {
				LogMessage("\u6253\u5f00\u58f0\u97f3\u6210\u529f\uff01");
				this.m_iVoice = 1;
				return true;
			} else {
				LogMessage("\u6253\u5f00\u58f0\u97f3\u5931\u8d25\uff01");
			}
		}
	} else {
		LogMessage("\u8bf7\u5148\u9884\u89c8\uff01");
	}
	return false;
};

/**
 * 关闭声音
 */
NetVideoActiveX23Func.prototype["voice:stop"] = function() {
	if (this.m_iVoice == 1) {
		if (this.m_bDVRControl.CloseSound(1)) {
			LogMessage("\u5173\u95ed\u58f0\u97f3\u6210\u529f\uff01");
			this.m_iVoice = 0;
			return true;
		} else {
			LogMessage("\u5173\u95ed\u58f0\u97f3\u5931\u8d25\uff01");
		}
	}
	return false;
};

/**
 * 按时间回放
 */
NetVideoActiveX23Func.prototype["playback:start"] = function() {
	var that = this;
	if (that.m_iIPChannelNum >= 64) {
		LogMessage("IP通道个数大于等于64，" + "IP通道号取值从0开始！");
		that.m_iIPChanStart = 0;
	} else {
		LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
		that.m_iIPChanStart = 32;
	}
	// that.m_iNowChanNo =
	// parseInt(document.getElementById("ChannelList").value);
	that.m_iNowChanNo = that.channelNo;
	if (that.m_iNowChanNo >= that.m_iChannelNum) {
		that.m_iNowChanNo = that.m_iNowChanNo - that.m_iChannelNum + that.m_iIPChanStart;
	}
	if (that.m_iLoginUserId > -1) {
		if (that.m_iPlayback == 1) {
			that.m_bDVRControl.StopPlayBack();
			that.m_iPlayback = 0;
		}
		if (that.m_iPlayback == 0) {
			if (that.m_bDVRControl.PlayBackByTime(that.m_iNowChanNo, "2013-10-11 11:10:00", "2013-10-11 11:35:50")) {
				LogMessage("开始时间回放成功，起止时间：2013-10-11 11:10:00 ~ 2013-10-11 11:35:50！");
			}
			that.m_iPlayback = 1;
		}
	} else {
		LogMessage("请注册设备！");
	}
};

/**
 * 停止回放
 */
NetVideoActiveX23Func.prototype["playback:stop"] = function() {
	if (this.m_bDVRControl.StopPlayBack()) {
		LogMessage("停止回放成功！");
		this.m_iPlayback = 0;
	} else {
		LogMessage("停止回放失败！");
	}
};

/**
 * 按文件下载
 */
NetVideoActiveX23Func.prototype["downloadfile:start"] = function() {
	var that = this;
	if (that.m_iLoginUserId > -1) {
		if (that.m_iIPChannelNum >= 64) {
			LogMessage("IP通道个数大于等于64，" + "通道号取值从0开始！");
			that.m_iIPChanStart = 0;
		} else {
			LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
			that.m_iIPChanStart = 32;
		}
		// that.m_iNowChanNo =
		// parseInt(document.getElementById("ChannelList").value);
		that.m_iNowChanNo = that.channelNo;
		if (that.m_iNowChanNo >= that.m_iChannelNum) {
			that.m_iNowChanNo = that.m_iNowChanNo - that.m_iChannelNum + that.m_iIPChanStart;
		}
		if (that.m_iDownload == 1) {
			that.m_bDVRControl.StopDownLoadFile();
			that.m_iDownload = 0;
		}
		if (that.m_iDownload == 0) {
			LogMessage("查找录像文件，通道号: " + (m_iNowChanNo + 1) + "，起止时间：2013-10-11 11:10:00, 2013-10-11 11:35:50");

			that.szFileInfo = that.m_bDVRControl.SearchRemoteRecordFile(that.m_iNowChanNo, 0, "2013-10-11 11:10:00", "2013-10-11 11:35:50", false, false, "");
			if (that.szFileInfo == " ") {
				var dRet = that.m_bDVRControl.GetLastError();
				LogMessage("搜索录像文件失败！错误号：" + dRet);
			} else if (that.szFileInfo == "null") {
				LogMessage("没有搜索到录像文件！");
			} else {
				LogMessage("搜索录像文件成功！");
				var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = "false";
				xmlDoc.loadXML(that.szFileInfo);
				var szFileName = xmlDoc.documentElement.childNodes[0].childNodes[2].childNodes[0].nodeValue;

				if (that.m_bDVRControl.DownLoadByFileName(szFileName, "D:\\")) {
					LogMessage("下载查找到的第一个录像文件" + szFileName + "下载成功！");
					that.m_iDownload = 1;
				} else {
					LogMessage("开始下载失败！");
				}
			}
		}
	} else {
		LogMessage("请注册设备！");
	}
};

/**
 * 按时间下载
 */
NetVideoActiveX23Func.prototype["downloadtime:start"] = function() {
	var that = this;
	if (that.m_iIPChannelNum >= 64) {
		LogMessage("IP通道个数大于等于64，" + "IP通道号取值从0开始！");
		that.m_iIPChanStart = 0;
	} else {
		LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
		that.m_iIPChanStart = 32;
	}
	// that.m_iNowChanNo =
	// parseInt(document.getElementById("ChannelList").value);
	that.m_iNowChanNo = that.channelNo;
	if (that.m_iNowChanNo >= that.m_iChannelNum) {
		that.m_iNowChanNo = that.m_iNowChanNo - that.m_iChannelNum + that.m_iIPChanStart;
	}
	if (that.m_iLoginUserId > -1) {
		if (that.m_iDownload == 1) {
			that.m_bDVRControl.StopDownLoadFile();
			that.m_iDownload = 0;
		}
		if (that.m_iDownload == 0) {
			if (that.m_bDVRControl.DownLoadByTime(that.m_iNowChanNo, "2013-10-11 11:10:00", "2013-10-11 11:35:50", "D:\\")) {
				LogMessage("开始按时间下载成功，起止时间：2013-10-11 11:10:00 ~ 2013-10-11 11:35:50！");
			}
			that.m_iDownload = 1;
		}
	} else {
		LogMessage("请注册设备！");
	}
};

/**
 * 停止下载
 */
NetVideoActiveX23Func.prototype["downloadfile:stop"] = function() {
	var that = this;
	if (that.m_bDVRControl.StopDownLoadFile()) {
		LogMessage("停止下载成功！");
		that.m_iDownload = 0;
	} else {
		LogMessage("停止下载失败！");
	}
};

/**
 * 停止
 */
NetVideoActiveX23Func.prototype["PTZ:stop"] = function() {
	var that = this;
	if (that.m_iPlay == 1) {
		if (that.m_bDVRControl.PTZCtrlStop(10, that.m_iPTZSpeed)) {
			LogMessage("停止PTZ成功！");
			that.m_iAutoPTZ = 0;
		} else {
			LogMessage("停止PTZ失败！");
		}
	}
};

/**
 * 左上
 */
NetVideoActiveX23Func.prototype["PTZ:leftup"] = function() {
	return this.PTZ(13, "PTZ左上");
};

/**
 * 右上
 */
NetVideoActiveX23Func.prototype["PTZ:rightup"] = function() {
	return this.PTZ(14, "PTZ右上");
};

/**
 * 上
 */
NetVideoActiveX23Func.prototype["PTZ:up"] = function() {
	return this.PTZ(0, "PTZ上");
};
/**
 * 左
 */
NetVideoActiveX23Func.prototype["PTZ:left"] = function() {
	return this.PTZ(2, "PTZ向左");
};
/**
 * 右
 */
NetVideoActiveX23Func.prototype["PTZ:right"] = function() {
	return this.PTZ(3, "PTZ向右");
};
/**
 * 右下
 */
NetVideoActiveX23Func.prototype["PTZ:rightdown"] = function() {
	return this.PTZ(16, "PTZ右下");
};

/**
 * 左下
 */
NetVideoActiveX23Func.prototype["PTZ:leftdown"] = function() {
	return this.PTZ(15, "PTZ左下");
};

/**
 * 向下
 */
NetVideoActiveX23Func.prototype["PTZ:down"] = function() {
	return this.PTZ(1, "PTZ向下");
};

/**
 * 自转
 */
NetVideoActiveX23Func.prototype["PTZ:auto"] = function() {
	var that = this;
	if (that.m_iPlay == 1) {
		if (that.m_bDVRControl.PTZCtrlStart(10, that.m_iPTZSpeed)) {
			LogMessage("PTZ自转成功！");
			that.m_iAutoPTZ = 1;
		} else {
			LogMessage("PTZ自转失败！");
		}
	} else {
		LogMessage("请先预览！");
	}
};

/**
 * 焦距拉近
 */
NetVideoActiveX23Func.prototype["zoom:in"] = function() {
	return this.PTZ(4, "焦距拉近");
};

/**
 * 焦距拉远
 */
NetVideoActiveX23Func.prototype["zoom:out"] = function() {
	return this.PTZ(5, "焦距拉远");
};

/**
 * 聚焦拉近
 */
NetVideoActiveX23Func.prototype["focus:in"] = function() {
	return this.PTZ(6, "聚焦拉近");
};

/**
 * 聚焦拉远
 */
NetVideoActiveX23Func.prototype["focus:out"] = function() {
	return this.PTZ(7, "聚焦拉远");
};

/**
 * 光圈大
 */
NetVideoActiveX23Func.prototype["iris:in"] = function() {
	return this.PTZ(8, "光圈大");
};

/**
 * 光圈小
 */
NetVideoActiveX23Func.prototype["iris:out"] = function() {
	return this.PTZ(9, "光圈小");
};

/**
 * @param n
 * @param name
 */
NetVideoActiveX23Func.prototype.PTZ = function(n, name) {
	var that = this;
	if (that.m_iPlay == 1) {
		if (that.m_iAutoPTZ == 1) {
			that.m_bDVRControl.PTZCtrlStop(10, that.m_iPTZSpeed);
			that.m_iAutoPTZ = 0;
		}
		if (that.m_bDVRControl.PTZCtrlStart(n, that.m_iPTZSpeed)) {
			LogMessage(name + "成功！");
		} else {
			LogMessage(name + "失败！");
		}
	} else {
		LogMessage("请先预览！");
	}
};

/**
 * 获取图像
 */
NetVideoActiveX23Func.prototype.getImagePar = function() {
	var that = this;
	if (that.m_iPlay == 1) {
		var szXmlInfo = that.m_bDVRControl.GetVideoEffect();
		if (szXmlInfo != "") {
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(szXmlInfo);
			document.getElementById("PicLight").value = xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue;
			document.getElementById("PicContrast").value = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
			document.getElementById("PicSaturation").value = xmlDoc.documentElement.childNodes[2].childNodes[0].nodeValue;
			document.getElementById("PicTonal").value = xmlDoc.documentElement.childNodes[3].childNodes[0].nodeValue;
			LogMessage("获取图像参数成功！");
		} else {
			LogMessage("获取图像参数失败！");
		}
	} else {
		LogMessage("请先预览！");
	}

};

/**
 * 设置图像参数
 */
NetVideoActiveX23Func.prototype.setImagePar = function() {
	var that = this;
	if (that.m_iPlay == 1) {
		var iL = parseInt(document.getElementById("PicLight").value);
		var iC = parseInt(document.getElementById("PicContrast").value);
		var iS = parseInt(document.getElementById("PicSaturation").value);
		var iT = parseInt(document.getElementById("PicTonal").value);
		var bRet = that.m_bDVRControl.SetVideoEffect(iL, iC, iS, iT);
		if (bRet) {
			LogMessage("设置图像参数成功！");
		} else {
			LogMessage("设置图像参数失败！");
		}
	} else {
		LogMessage("请先预览！");
	}

};

/**
 * 设置预置点
 */
NetVideoActiveX23Func.prototype.setPreset = function() {
	var that = this;
	if (that.m_iPlay == 1) {
		var iPreset = parseInt(document.getElementById("Preset").value);
		var bRet = that.m_bDVRControl.PTZCtrlSetPreset(iPreset);
		if (bRet) {
			LogMessage("设置预置点成功！");
		} else {
			LogMessage("设置预置点失败！");
		}
	} else {
		LogMessage("请先预览！");
	}
};

/**
 * 调用预置点
 */
NetVideoActiveX23Func.prototype.goPreset = function() {
	var that = this;
	if (that.m_iPlay == 1) {
		var iPreset = parseInt(document.getElementById("Preset").value);
		var bRet = that.m_bDVRControl.PTZCtrlGotoPreset(iPreset);
		if (bRet) {
			LogMessage("调用预置点成功！");
		} else {
			LogMessage("调用预置点成功！");
		}
	} else {
		LogMessage("请先预览！");
	}
};

/*******************************************************************************
 * Function: LogMessage Description: 写执行结果日志 Input: msg:日志 Output: 无 Return: 无
 ******************************************************************************/
function LogMessage(msg) {
	var myDate = new Date();
	var szNowTime = myDate.toLocaleString(); // 获取日期与时间
	document.getElementById("OperatLogBody").innerHTML = szNowTime + " --> " + msg + "<br>" + document.getElementById("OperatLogBody").innerHTML;
}

/*******************************************************************************
 * Function: ArrangeWindow Description: 画面分割为几个窗口 Input: x : 窗口数目 Output: 无
 * return: 无
 ******************************************************************************/
function ArrangeWindow(x, t) {
	var iMaxWidth = document.getElementById("OCXBody").offsetWidth;
	var iMaxHeight = document.getElementById("OCXBody").offsetHeight;
	for (var i = 1; i <= VideoNum; i++) {
		if(t != undefined){
			if(i == t){
				$("#NetPlayOCX" + (i)).show();
			} else {
				$("#NetPlayOCX" + i).hide();
			}
			continue;
		}
		
		if (i <= x) {
			$("#NetPlayOCX" + (i)).show();
		} else {
			// document.getElementById("NetPlayOCX" + i).style.display = "none";
			$("#NetPlayOCX" + i).hide();
		}
	}
	var d = Math.sqrt(x);
	var iWidth = (iMaxWidth / d) - 5;
	var iHight = (iMaxHeight / d) - 5;
	for (var j = 1; j <= x; j++) {
		$("#NetPlayOCX" + (t || j)).css({
			width : iWidth,
			height : iHight
		});
	}
	if (x == 1) {

	} else if (x == 4) {

	} else {
		//	
	}
}

/*******************************************************************************
 * Function: ChangeStatus Description: 选中窗口时，相应通道的状态显示 Input: iWindowNum : 选中窗口号
 * Output: 无 return: 无
 ******************************************************************************/
function ChangeStatus(iWindowNum) {
	m_bDVRControl = document.getElementById("sv_web_live_ctrl" + iWindowNum);
	//for (var i = 1; i <= 4; i++) {
	//	if (i == iWindowNum) {
	//		document.getElementById("NetPlayOCX" + i).style.border = "1px solid #00F";
	//	} else {
	//		document.getElementById("NetPlayOCX" + i).style.border = "1px solid #EBEBEB";
	//	}
	//}

	
}

/**
 * 生成channel 列表
 * 
 * @returns {ChannelList}
 */
function ChannelList() {
	this.num = 1;
	this.collection = $("#ChannelList-0").empty();
	this.template = [ "<tr class='mt-ocx'>", "<td>", "<label>", " {2}、<input class='k-chk' type='checkbox' value='{0}' > ", " <span>{1}</span>", "</label>", "</td>", "</tr>" ].join('');
	this.getTemplate = function() {
		if (arguments.length == 0)
			return this.template;
		var template = this.template;
		for (var i = 0; i < arguments.length; i++)
			template = template.replace(new RegExp("\\{" + (i) + "\\}", "g"), arguments[i]);
		return template;
	};
	this.addChannel = function(name, value) {

		var ocx = new NetVideoActiveX23Func("sv_web_live_ctrl" + (value + 1));

		ocx.channelNo = ocx.m_iNowChanNo = value;

		var $tr = $(this.getTemplate(value, name, this.num++)).data("ocx", ocx);

		this.collection.append($tr);

		return this.initEvents($tr);
	};
	
	
	function oneChange($tr){
		var num = $("#OCXBody div.smallocxdiv:visible").length;
		if(num != 1){
			return;
		}
		
		var index = $tr.index() + 1;
		$("#NetPlayOCX" + index).show().siblings().hide()//
			.each(function(){
				if(!this.id){
					return;
				}
			var n = this.id.replace("NetPlayOCX", "")*1;
			var chk = $("input.k-chk[value="+(n - 1) +"]").get(0);
			if(chk && chk.checked){
				window.setTimeout(function(){
					closeOcx($(chk).closest("tr"));
				});
				chk.checked = false;
			}
		});
		
		ArrangeWindow(1, index);
	}
	
	
	
	this.initEvents = function($tr) {
		$tr.find(".k-chk").on('click', function() {
			CURRENTOCX = $tr.data("ocx");
			if (this.checked) {
//				ButtonPress('Preview:start');
				sv_web_live_ctrl.StartLive();
				oneChange($tr.closest("tr"));
			} else {
//				ButtonPress('Preview:stop');
				sv_web_live_ctrl.StopLive();
			}
		});
		return $tr;
	};
	
	
}

function closeAllOcx(){
	try {
		$("tr.mt-ocx").each(function(){
			closeOcx($(this));
		});
	} catch(e){
		console.log(e);
	}
}

function closeOcx($tr){
	var ocx;
	if(ocx = $tr.data("ocx")){
//		ocx["Preview:stop"]();
		sv_web_live_ctrl.StopLive();
	}
}


function ErrorParams() {
	return false;
}