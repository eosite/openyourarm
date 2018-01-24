//全局变量定义
var m_iNowChanNo = -1; // 当前通道号
var m_iLoginUserId = -1; // 注册设备用户ID
var m_iChannelNum = -1; // 模拟通道总数
var m_iIPChannelNum = -1; // IP通道总数
var m_bDVRControl = null; // OCX控件对象
var m_iProtocolType = 0; // 协议类型，0 – TCP， 1 - UDP
var m_iStreamType = 0; // 码流类型，0 表示主码流， 1 表示子码流
var m_iPlay = 0; // 当前是否正在预览
var m_iRecord = 0; // 当前是否正在录像
var m_iTalk = 0; // 当前是否正在对讲
var m_iVoice = 0; // 当前是否打开声音
var m_iAutoPTZ = 0; // 当前云台是否正在自转
var m_iPTZSpeed = 1; // 云台速度
var m_szDeviceType = 0; // 设备类型
var m_iPlayback = 0; // 是否回放状态
var m_iDownload = 0; // 是否下载状态
/*******************************************************************************
 * Function: onload Description: 页面加载完后判断系统日期是否在1971-2037范围 Input: 无 Output: 无
 * Return: 无
 ******************************************************************************/
var videoFn;
jQuery(function() {
	if(!id){
		return ErrorParams();
	}
	$("#OCXBody").css({
		height : $(window).height() * 0.7
	});
	var myDate = new Date();
	var iYear = myDate.getFullYear();
	if (iYear < 1971 || iYear > 2037) {
		alert("为了正常使用本软件，请将系统日期年限设置在1971-2037范围内！");
	}
	if (document.getElementById("HIKOBJECT1").object == null) {
		alert("请先下载控件并注册！");
		m_bDVRControl = null;
	} else {
		m_bDVRControl = document.getElementById("HIKOBJECT1");
		ChangeStatus(1);
		ArrangeWindow(4);
		videoFn = new ButtonPressFunc(id);
		setTimeout(initNetVideoActiveX23, 100);
	}
});

function ErrorParams(){
	alert('设备参数不对!');
	return false;
}

function initNetVideoActiveX23() {
	videoFn.LoginDev();
	videoFn.getDevChan();
	//$.each([ "LoginDev", "getDevName", "getDevChan" ], function(i, v) {
	//	ButtonPress(v);
	//});
}

/*******************************************************************************
 * Function: rightclick Description: 网页禁用右键 Input: 无 Output: 无 Return: bool:
 * true false
 ******************************************************************************/
function rightclick() {
	return false;
}
/*******************************************************************************
 * Function: rightclick Description: 网页禁用右键 Input: 无 Output: 无 Return: bool:
 * true false
 ******************************************************************************/
function ButtonPressFunc(id){
	this.videoId = id;
}
ButtonPressFunc.prototype = {
		constructor : ButtonPressFunc,
		LoginDev : function(){
			var that = this;$.ajax({data:{id:that.videoId},async:false,url:contextPath+"/mx/form/defined/getVideoSetById",type:'post',dataType:'JSON',success:initNetVideo});
			function initNetVideo(data){if(data){m_iLoginUserId = m_bDVRControl.Login(data.szDevIp, data.szDevPort, data.szDevUser,data.szDevPwd);if(m_iLoginUserId == -1) {LogMessage("注册失败！");} else {LogMessage("注册成功！");for ( var i = 2; i <= 4; i++) {document.getElementById("HIKOBJECT" + i).SetUserID(m_iLoginUserId);}}} else {return ErrorParams();}}
		},
		LogoutDev : function(){
			if (m_bDVRControl.Logout()) {
				LogMessage("注销成功！");
			} else {
				LogMessage("注销失败！");
			}
		},
		getDevName : function(){
			var szDecName = m_bDVRControl.GetServerName();
			if (szDecName == "") {
				LogMessage("获取名称失败！");
				szDecName = "Embedded Net DVR";
			} else {
				LogMessage("获取名称成功！");
			}
			document.getElementById("DeviceName").value = szDecName;
		},
		"getDevChan" : function(){
			szServerInfo = m_bDVRControl.GetServerInfo();
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(szServerInfo);
			m_iChannelNum = parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
			m_iIPChannelNum = parseInt(xmlDoc.documentElement.childNodes[8].childNodes[0].nodeValue);
			m_szDeviceType = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
			// m_iChannelNum = parseInt(iChannelNum);
			var cl = new ChannelList();
			if (m_iChannelNum < 1) {
				LogMessage("获取模拟通道失败！");
			} else {
				LogMessage("获取模拟通道成功！");
				document.getElementById("ChannelList").length = 0; // 先清空下拉列表
				for ( var i = 0; i < m_iChannelNum; i++) {
					var szChannelName = m_bDVRControl.GetChannelName(i);
					if (szChannelName == "") {
						szChannelName = "通道" + (i + 1);
					}
					document.getElementById("ChannelList").options
							.add(new Option(szChannelName, i));
					cl.addChannel(szChannelName, i);
				}
			}
			if (m_iIPChannelNum < 1) {
				LogMessage("获取IP通道失败！");
			} else {
				LogMessage("获取IP通道成功！");
				m_bDVRControl.GetIPParaCfg();
				szIPChanInfo = m_bDVRControl.GetIPCConfig();
				var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = "false";
				xmlDoc.loadXML(szIPChanInfo);
				for ( var i = m_iChannelNum; i < m_iChannelNum
						+ m_iIPChannelNum; i++) {
					m_iIPConnet = parseInt(xmlDoc.documentElement.childNodes[i].childNodes[3].childNodes[0].nodeValue);
					if (m_iIPConnet == 1) {
						LogMessage("获取IP通道" + (i - m_iChannelNum + 1) + "成功！");
						var szChannelName = m_bDVRControl.GetChannelName(i);
						if (szChannelName == "") {
							szChannelName = "IP通道" + (i - m_iChannelNum + 1);
						}
						document.getElementById("ChannelList").options
								.add(new Option(szChannelName, i));
						cl.addChannel(szChannelName, i);
					}
				}
			}

		}
};

function ButtonPress(sKey) {
	try {
		switch (sKey) {
		case "LoginDev": {
			var szDevIp = document.getElementById("DeviceIP").value;
			var szDevPort = document.getElementById("DevicePort").value;
			var szDevUser = document.getElementById("DeviceUsername").value;
			var szDevPwd = document.getElementById("DevicePasswd").value;
			m_iLoginUserId = m_bDVRControl.Login(szDevIp, szDevPort, szDevUser,
					szDevPwd);
			if (m_iLoginUserId == -1) {
				LogMessage("注册失败！");
			} else {
				LogMessage("注册成功！");
				for ( var i = 2; i <= 4; i++) {
					document.getElementById("HIKOBJECT" + i).SetUserID(
							m_iLoginUserId);
				}
			}
			//fn.LoginDev("");
			break;
		}
		case "LogoutDev": {
			if (m_bDVRControl.Logout()) {
				LogMessage("注销成功！");
			} else {
				LogMessage("注销失败！");
			}
			break;
		}
		case "getDevName": {
			var szDecName = m_bDVRControl.GetServerName();
			// szDecName = szDecName.replace(/\s/g,"&nbsp;");
			if (szDecName == "") {
				LogMessage("获取名称失败！");
				szDecName = "Embedded Net DVR";
			} else {
				LogMessage("获取名称成功！");
			}
			document.getElementById("DeviceName").value = szDecName;
			break;
		}
		case "getDevChan": {
			szServerInfo = m_bDVRControl.GetServerInfo();
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(szServerInfo);
			m_iChannelNum = parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
			m_iIPChannelNum = parseInt(xmlDoc.documentElement.childNodes[8].childNodes[0].nodeValue);
			m_szDeviceType = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
			// m_iChannelNum = parseInt(iChannelNum);
			var cl = new ChannelList();
			if (m_iChannelNum < 1) {
				LogMessage("获取模拟通道失败！");
			} else {
				LogMessage("获取模拟通道成功！");
				document.getElementById("ChannelList").length = 0; // 先清空下拉列表
				for ( var i = 0; i < m_iChannelNum; i++) {
					var szChannelName = m_bDVRControl.GetChannelName(i);
					if (szChannelName == "") {
						szChannelName = "通道" + (i + 1);
					}
					document.getElementById("ChannelList").options
							.add(new Option(szChannelName, i));
					cl.addChannel(szChannelName, i);
				}
			}
			if (m_iIPChannelNum < 1) {
				LogMessage("获取IP通道失败！");
			} else {
				LogMessage("获取IP通道成功！");
				m_bDVRControl.GetIPParaCfg();
				szIPChanInfo = m_bDVRControl.GetIPCConfig();
				var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = "false";
				xmlDoc.loadXML(szIPChanInfo);
				for ( var i = m_iChannelNum; i < m_iChannelNum
						+ m_iIPChannelNum; i++) {
					m_iIPConnet = parseInt(xmlDoc.documentElement.childNodes[i].childNodes[3].childNodes[0].nodeValue);
					if (m_iIPConnet == 1) {
						LogMessage("获取IP通道" + (i - m_iChannelNum + 1) + "成功！");
						var szChannelName = m_bDVRControl.GetChannelName(i);
						if (szChannelName == "") {
							szChannelName = "IP通道" + (i - m_iChannelNum + 1);
						}
						document.getElementById("ChannelList").options
								.add(new Option(szChannelName, i));
						cl.addChannel(szChannelName, i);
					}
				}
			}

			break;
		}

		case "Preview:start": {
			LogMessage(m_szDeviceType);
			if (m_iIPChannelNum >= 64) {
				LogMessage("IP通道个数大于等于64，" + "通道号取值从0开始！");
				m_iIPChanStart = 0;
			} else {
				LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
				m_iIPChanStart = 32;
			}
			m_iNowChanNo = parseInt(document.getElementById("ChannelList").value);
			if (m_iNowChanNo >= m_iChannelNum) {
				m_iNowChanNo = m_iNowChanNo - m_iChannelNum + m_iIPChanStart;
			}
			if (m_iNowChanNo > -1) {
				if (m_iPlay == 1) {
					m_bDVRControl.StopRealPlay();
					m_iPlay = 0;
				}
				var bRet = m_bDVRControl.StartRealPlay(m_iNowChanNo,
						m_iProtocolType, m_iStreamType);
				if (bRet) {
					LogMessage("预览通道" + (m_iNowChanNo + 1) + "成功！");
					m_iPlay = 1;
				} else {
					LogMessage("预览通道" + (m_iNowChanNo + 1) + "失败！");
					var dRet = m_bDVRControl.GetLastError();
					LogMessage("预览失败错误号：" + dRet);
				}
			} else {
				LogMessage("请选择通道号！");
			}
			break;
		}
		case "Preview:stop": {
			if (m_bDVRControl.StopRealPlay()) {
				LogMessage("停止预览成功！");
				m_iPlay = 0;
			} else {
				LogMessage("停止预览失败！");
			}
			break;
		}
		case "CatPic:bmp": {
			if (m_iPlay == 1) {
				if (m_bDVRControl.BMPCapturePicture("C:/OCXBMPCaptureFiles", 1)) {
					LogMessage("抓BMP图成功！");
				} else {
					LogMessage("抓BMP图失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "CatPic:jpeg": {
			if (m_iPlay == 1) {
				if (m_bDVRControl.JPEGCapturePicture((m_iNowChanNo + 1), 2, 0,
						"C:/OCXJPEGCaptureFiles", 1)) {
					LogMessage("抓JPEG图成功！");
				} else {
					LogMessage("抓JPEG图失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "Record:start": {
			if (m_iPlay == 1) {
				if (m_iRecord == 0) {
					if (m_bDVRControl.StartRecord("C:/OCXRecordFiles")) {
						LogMessage("开始录像成功！");
						m_iRecord = 1;
					} else {
						LogMessage("开始录像失败！");
					}
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "Record:stop": {
			if (m_iRecord == 1) {
				if (m_bDVRControl.StopRecord(1)) {
					LogMessage("停止录像成功！");
					m_iRecord = 0;
				} else {
					LogMessage("停止录像失败！");
				}
			}
			break;
		}
		case "talk:start": {
			if (m_iLoginUserId > -1) {
				if (m_iTalk == 0) {
					if (m_bDVRControl.StartTalk(1)) {
						LogMessage("开始对讲成功！");
						m_iTalk = 1;
					} else {
						LogMessage("开始对讲失败！");
					}
				}
			} else {
				LogMessage("请注册设备！");
			}
			break;
		}
		case "talk:stop": {
			if (m_iTalk == 1) {
				if (m_bDVRControl.StopTalk()) {
					LogMessage("停止对讲成功！");
					m_iTalk = 0;
				} else {
					LogMessage("停止对讲失败！");
				}
			}
			break;
		}
		case "voice:start": {
			if (m_iPlay == 1) {
				if (m_iVoice == 0) {
					if (m_bDVRControl.OpenSound(1)) {
						LogMessage("打开声音成功！");
						m_iVoice = 1;
					} else {
						LogMessage("打开声音失败！");
					}
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "voice:stop": {
			if (m_iVoice == 1) {
				if (m_bDVRControl.CloseSound(1)) {
					LogMessage("关闭声音成功！");
					m_iVoice = 0;
				} else {
					LogMessage("关闭声音失败！");
				}
			}
			break;
		}
		case "playback:start": {
			if (m_iIPChannelNum >= 64) {

				LogMessage("IP通道个数大于等于64，" + "IP通道号取值从0开始！");

				m_iIPChanStart = 0;

			}

			else {
				LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
				m_iIPChanStart = 32;
			}

			m_iNowChanNo = parseInt(document.getElementById("ChannelList").value);
			if (m_iNowChanNo >= m_iChannelNum) {
				m_iNowChanNo = m_iNowChanNo - m_iChannelNum + m_iIPChanStart;
			}

			if (m_iLoginUserId > -1) {

				if (m_iPlayback == 1) {
					m_bDVRControl.StopPlayBack();
					m_iPlayback = 0;
				}

				// m_iNowChanNo =
				// parseInt(document.getElementById("ChannelList").value);
				if (m_iPlayback == 0) {
					if (m_bDVRControl.PlayBackByTime(m_iNowChanNo,
							"2013-10-11 11:10:00", "2013-10-11 11:35:50")) {
						LogMessage("开始时间回放成功，起止时间：2013-10-11 11:10:00 ~ 2013-10-11 11:35:50！");
					}
					m_iPlayback = 1;
				}
			} else {
				LogMessage("请注册设备！");
			}
			break;
		}
		case "playback:stop": {
			if (m_bDVRControl.StopPlayBack()) {
				LogMessage("停止回放成功！");
				m_iPlayback = 0;
			} else {
				LogMessage("停止回放失败！");
			}
			break;
		}
		case "downloadfile:start": {
			if (m_iLoginUserId > -1) {
				if (m_iIPChannelNum >= 64) {
					LogMessage("IP通道个数大于等于64，" + "通道号取值从0开始！");
					m_iIPChanStart = 0;

				} else {
					LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
					m_iIPChanStart = 32;
				}
				m_iNowChanNo = parseInt(document.getElementById("ChannelList").value);
				if (m_iNowChanNo >= m_iChannelNum) {
					m_iNowChanNo = m_iNowChanNo - m_iChannelNum
							+ m_iIPChanStart;
				}

				if (m_iDownload == 1) {
					m_bDVRControl.StopDownLoadFile();
					m_iDownload = 0;
				}

				if (m_iDownload == 0) {
					// m_iNowChanNo =
					// parseInt(document.getElementById("ChannelList").value);
					LogMessage("查找录像文件，通道号: " + (m_iNowChanNo + 1)
							+ "，起止时间：2013-10-11 11:10:00, 2013-10-11 11:35:50");

					szFileInfo = m_bDVRControl.SearchRemoteRecordFile(
							m_iNowChanNo, 0, "2013-10-11 11:10:00",
							"2013-10-11 11:35:50", false, false, "");
					if (szFileInfo == " ") {
						var dRet = m_bDVRControl.GetLastError();
						LogMessage("搜索录像文件失败！错误号：" + dRet);
						break;
					} else if (szFileInfo == "null") {
						LogMessage("没有搜索到录像文件！");
						break;
					} else {
						LogMessage("搜索录像文件成功！");
						var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
						xmlDoc.async = "false";
						xmlDoc.loadXML(szFileInfo);
						var szFileName = xmlDoc.documentElement.childNodes[0].childNodes[2].childNodes[0].nodeValue;

						if (m_bDVRControl
								.DownLoadByFileName(szFileName, "D:\\")) {
							LogMessage("下载查找到的第一个录像文件" + szFileName + "下载成功！");
							m_iDownload = 1;
						} else {
							LogMessage("开始下载失败！");
						}
					}
					break;
				}
			}

			else {
				LogMessage("请注册设备！");
			}

			break;

		}
		case "downloadtime:start": {
			if (m_iIPChannelNum >= 64) {

				LogMessage("IP通道个数大于等于64，" + "IP通道号取值从0开始！");

				m_iIPChanStart = 0;

			}

			else {
				LogMessage("如果设备有IP通道，IP通道号取值从32开始！");
				m_iIPChanStart = 32;
			}

			m_iNowChanNo = parseInt(document.getElementById("ChannelList").value);
			if (m_iNowChanNo >= m_iChannelNum) {
				m_iNowChanNo = m_iNowChanNo - m_iChannelNum + m_iIPChanStart;
			}

			if (m_iLoginUserId > -1) {

				if (m_iDownload == 1) {
					m_bDVRControl.StopDownLoadFile();
					m_iDownload = 0;
				}

				// m_iNowChanNo =
				// parseInt(document.getElementById("ChannelList").value);
				if (m_iDownload == 0) {
					if (m_bDVRControl.DownLoadByTime(m_iNowChanNo,
							"2013-10-11 11:10:00", "2013-10-11 11:35:50",
							"D:\\")) {
						LogMessage("开始按时间下载成功，起止时间：2013-10-11 11:10:00 ~ 2013-10-11 11:35:50！");
					}
					m_iDownload = 1;
				}
			} else {
				LogMessage("请注册设备！");
			}
			break;
		}

		case "downloadfile:stop": {
			if (m_bDVRControl.StopDownLoadFile()) {
				LogMessage("停止下载成功！");
				m_iDownload = 0;
			} else {
				LogMessage("停止下载失败！");
			}

			break;
		}

		case "PTZ:stop": {
			if (m_iPlay == 1) {
				if (m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed)) {
					LogMessage("停止PTZ成功！");
					m_iAutoPTZ = 0;
				} else {
					LogMessage("停止PTZ失败！");
				}
			}
			break;
		}
		case "PTZ:leftup": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(13, m_iPTZSpeed)) {
					LogMessage("PTZ左上成功！");
				} else {
					LogMessage("PTZ左上失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:rightup": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(14, m_iPTZSpeed)) {
					LogMessage("PTZ右上成功！");
				} else {
					LogMessage("PTZ右上失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:up": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(0, m_iPTZSpeed)) {
					LogMessage("PTZ上成功！");
				} else {
					LogMessage("PTZ上失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:left": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(2, m_iPTZSpeed)) {
					LogMessage("PTZ向左成功！");
				} else {
					LogMessage("PTZ向左失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:right": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(3, m_iPTZSpeed)) {
					LogMessage("PTZ向右成功！");
				} else {
					LogMessage("PTZ向右失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:rightdown": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(16, m_iPTZSpeed)) {
					LogMessage("PTZ右下成功！");
				} else {
					LogMessage("PTZ右下失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:leftdown": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(15, m_iPTZSpeed)) {
					LogMessage("PTZ左下成功！");
				} else {
					LogMessage("PTZ左下失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:down": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(1, m_iPTZSpeed)) {
					LogMessage("PTZ向下成功！");
				} else {
					LogMessage("PTZ向下失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "PTZ:auto": {
			if (m_iPlay == 1) {
				if (m_bDVRControl.PTZCtrlStart(10, m_iPTZSpeed)) {
					LogMessage("PTZ自转成功！");
					m_iAutoPTZ = 1;
				} else {
					LogMessage("PTZ自转失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "zoom:in": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(4, m_iPTZSpeed)) {
					LogMessage("焦距拉近成功！");
				} else {
					LogMessage("焦距拉近失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "zoom:out": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(5, m_iPTZSpeed)) {
					LogMessage("焦距拉远成功！");
				} else {
					LogMessage("焦距拉远失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "focus:in": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(6, m_iPTZSpeed)) {
					LogMessage("聚焦拉近成功！");
				} else {
					LogMessage("聚焦拉近失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "focus:out": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(7, m_iPTZSpeed)) {
					LogMessage("聚焦拉远成功！");
				} else {
					LogMessage("聚焦拉远失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "iris:in": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(8, m_iPTZSpeed)) {
					LogMessage("光圈大成功！");
				} else {
					LogMessage("光圈大失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "iris:out": {
			if (m_iPlay == 1) {
				if (m_iAutoPTZ == 1) {
					m_bDVRControl.PTZCtrlStop(10, m_iPTZSpeed);
					m_iAutoPTZ = 0;
				}
				if (m_bDVRControl.PTZCtrlStart(9, m_iPTZSpeed)) {
					LogMessage("光圈小成功！");
				} else {
					LogMessage("光圈小失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "getImagePar": {
			if (m_iPlay == 1) {
				var szXmlInfo = m_bDVRControl.GetVideoEffect();
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
			break;
		}
		case "setImagePar": {
			if (m_iPlay == 1) {
				var iL = parseInt(document.getElementById("PicLight").value);
				var iC = parseInt(document.getElementById("PicContrast").value);
				var iS = parseInt(document.getElementById("PicSaturation").value);
				var iT = parseInt(document.getElementById("PicTonal").value);
				var bRet = m_bDVRControl.SetVideoEffect(iL, iC, iS, iT);
				if (bRet) {
					LogMessage("设置图像参数成功！");
				} else {
					LogMessage("设置图像参数失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "setPreset": {
			if (m_iPlay == 1) {
				var iPreset = parseInt(document.getElementById("Preset").value);
				var bRet = m_bDVRControl.PTZCtrlSetPreset(iPreset);
				if (bRet) {
					LogMessage("设置预置点成功！");
				} else {
					LogMessage("设置预置点失败！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		case "goPreset": {
			if (m_iPlay == 1) {
				var iPreset = parseInt(document.getElementById("Preset").value);
				var bRet = m_bDVRControl.PTZCtrlGotoPreset(iPreset);
				if (bRet) {
					LogMessage("调用预置点成功！");
				} else {
					LogMessage("调用预置点成功！");
				}
			} else {
				LogMessage("请先预览！");
			}
			break;
		}
		default: {
			// Record:start setPreset
			break;
		}
		} // switch
	} catch (err) {
		alert(err);
	}
}

function NetVideoActiveX23Func(id){
	this.m_iNowChanNo = -1; // 当前通道号
	this.m_iLoginUserId = -1; // 注册设备用户ID
	this.m_iChannelNum = -1; // 模拟通道总数
	this.m_iIPChannelNum = -1; // IP通道总数
	this.m_bDVRControl = null; // OCX控件对象
	this.m_iProtocolType = 0; // 协议类型，0 – TCP， 1 - UDP
	this.m_iStreamType = 0; // 码流类型，0 表示主码流， 1 表示子码流
	this.m_iPlay = 0; // 当前是否正在预览
	this.m_iRecord = 0; // 当前是否正在录像
	this.m_iTalk = 0; // 当前是否正在对讲
	this.m_iVoice = 0; // 当前是否打开声音
	this.m_iAutoPTZ = 0; // 当前云台是否正在自转
	this.m_iPTZSpeed = 1; // 云台速度
	this.m_szDeviceType = 0; // 设备类型
	this.m_iPlayback = 0; // 是否回放状态
	this.m_iDownload = 0; // 是否下载状态
}
NetVideoActiveX23Func.prototype = {
	constructor : NetVideoActiveX23Func,
	LoginDev : function(){
		var that = this;$.ajax({data:{id:that.videoId},async:false,url:contextPath+"/mx/form/defined/getVideoSetById",type:'post',dataType:'JSON',success:initNetVideo});
		function initNetVideo(data){if(data){m_iLoginUserId = m_bDVRControl.Login(data.szDevIp, data.szDevPort, data.szDevUser,data.szDevPwd);if(m_iLoginUserId == -1) {LogMessage("注册失败！");} else {LogMessage("注册成功！");for ( var i = 2; i <= 4; i++) {document.getElementById("HIKOBJECT" + i).SetUserID(m_iLoginUserId);}}} else {return ErrorParams();}}
	},
	LogoutDev : function(){
		if (m_bDVRControl.Logout()) {
			LogMessage("注销成功！");
		} else {
			LogMessage("注销失败！");
		}
	},
	getDevName : function(){
		if (szDecName == "") {
			LogMessage("获取名称失败！");
			szDecName = "Embedded Net DVR";
		} else {
			LogMessage("获取名称成功！");
		}
		document.getElementById("DeviceName").value = szDecName;
	},
	"getDevChan" : function(){
		szServerInfo = m_bDVRControl.GetServerInfo();
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async = "false";
		xmlDoc.loadXML(szServerInfo);
		m_iChannelNum = parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
		m_iIPChannelNum = parseInt(xmlDoc.documentElement.childNodes[8].childNodes[0].nodeValue);
		m_szDeviceType = xmlDoc.documentElement.childNodes[1].childNodes[0].nodeValue;
		// m_iChannelNum = parseInt(iChannelNum);
		var cl = new ChannelList();
		if (m_iChannelNum < 1) {
			LogMessage("获取模拟通道失败！");
		} else {
			LogMessage("获取模拟通道成功！");
			document.getElementById("ChannelList").length = 0; // 先清空下拉列表
			for ( var i = 0; i < m_iChannelNum; i++) {
				var szChannelName = m_bDVRControl.GetChannelName(i);
				if (szChannelName == "") {
					szChannelName = "通道" + (i + 1);
				}
				document.getElementById("ChannelList").options
						.add(new Option(szChannelName, i));
				cl.addChannel(szChannelName, i);
			}
		}
		if (m_iIPChannelNum < 1) {
			LogMessage("获取IP通道失败！");
		} else {
			LogMessage("获取IP通道成功！");
			m_bDVRControl.GetIPParaCfg();
			szIPChanInfo = m_bDVRControl.GetIPCConfig();
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false";
			xmlDoc.loadXML(szIPChanInfo);
			for ( var i = m_iChannelNum; i < m_iChannelNum
					+ m_iIPChannelNum; i++) {
				m_iIPConnet = parseInt(xmlDoc.documentElement.childNodes[i].childNodes[3].childNodes[0].nodeValue);
				if (m_iIPConnet == 1) {
					LogMessage("获取IP通道" + (i - m_iChannelNum + 1) + "成功！");
					var szChannelName = m_bDVRControl.GetChannelName(i);
					if (szChannelName == "") {
						szChannelName = "IP通道" + (i - m_iChannelNum + 1);
					}
					document.getElementById("ChannelList").options
							.add(new Option(szChannelName, i));
					cl.addChannel(szChannelName, i);
				}
			}
		}
	}
};

/*******************************************************************************
 * Function: LogMessage Description: 写执行结果日志 Input: msg:日志 Output: 无 Return: 无
 ******************************************************************************/
function LogMessage(msg) {
	var myDate = new Date();
	var szNowTime = myDate.toLocaleString(); // 获取日期与时间
	document.getElementById("OperatLogBody").innerHTML = szNowTime + " --> "
			+ msg + "<br>" + document.getElementById("OperatLogBody").innerHTML;
}

/*******************************************************************************
 * Function: ArrangeWindow Description: 画面分割为几个窗口 Input: x : 窗口数目 Output: 无
 * return: 无
 ******************************************************************************/
function ArrangeWindow(x) {
	var iMaxWidth = document.getElementById("OCXBody").offsetWidth;
	var iMaxHeight = document.getElementById("OCXBody").offsetHeight;
	for ( var i = 1; i <= 4; i++) {
		if (i <= x) {
			document.getElementById("NetPlayOCX" + i).style.display = "";
		} else {
			document.getElementById("NetPlayOCX" + i).style.display = "none";
		}
	}
	var d = Math.sqrt(x);
	var iWidth = (iMaxWidth / d) - 5;
	var iHight = (iMaxHeight / d) - 5;
	for ( var j = 1; j <= x; j++) {
		$("#NetPlayOCX" + j).css({
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
	m_bDVRControl = document.getElementById("HIKOBJECT" + iWindowNum);
	for ( var i = 1; i <= 4; i++) {
		if (i == iWindowNum) {
			document.getElementById("NetPlayOCX" + i).style.border = "1px solid #00F";
		} else {
			document.getElementById("NetPlayOCX" + i).style.border = "1px solid #EBEBEB";
		}
	}
	LogMessage("当前选中窗口" + iWindowNum);
}

function ChannelList() {
	this.num = 1;
	this.collection = $("#ChannelList-0").empty();
	this.template = [ "<tr>", "<td>", "<label>",
			" {2}、<input class='k-chk' type='checkbox' value='{0}' > ",
			" <span>{1}</span>", "</label>", "</td>", "</tr>" ].join('');

	this.getTemplate = function() {
		if (arguments.length == 0)
			return this.template;
		for ( var i = 0; i < arguments.length; i++)
			this.template = this.template.replace(new RegExp("\\{" + (i)
					+ "\\}", "g"), arguments[i]);
		return this.template;
	};

	this.addChannel = function(name, value) {
		var $tr = $(this.getTemplate(value, name, this.num++));
		this.collection.append($tr);
		this.initEvents($tr);
	};

	this.initEvents = function($tr) {
		$tr.find(".k-chk").on('click', function() {
			if (this.checked) {
				ButtonPress('Preview:start', this);
			} else {
				ButtonPress('Preview:stop', this);
			}
		});
	};

}
