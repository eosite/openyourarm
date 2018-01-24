package com.glaf.monitor.server.website.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.RequestUtils;
import com.glaf.monitor.server.util.MonitorServerUtil;

@Controller("/monitor/Server")
@RequestMapping("/monitor/Server")
public class MonitorServerController {
	protected static final Log logger = LogFactory.getLog(MonitorServerController.class);
	
	@Autowired
	protected MonitorServerUtil monitorServerUtil;
	
	public MonitorServerController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setInfo")
	@ResponseBody
	public byte[] getSystemInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		logger.info("开始获取操作系统信息");
		try{
			String data =  RequestUtils.getParameter(request, "data");
			
			monitorServerUtil.parseClientData(JSON.parseObject(data));
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
}
