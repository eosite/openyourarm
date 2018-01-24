package com.glaf.monitor.client.website.springmvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.monitor.client.domain.SystemResource;
import com.glaf.monitor.client.job.GetClientInfoJob;
import com.glaf.monitor.client.util.MonitorClient;
import com.glaf.monitor.client.util.SystemMonitorUtils;
import com.glaf.monitor.server.domain.MonitorProc;

@Controller("/monitor/client")
@RequestMapping("/monitor/client")
public class MonitorClientController {
	protected static final Log logger = LogFactory.getLog(MonitorClientController.class);
	
	public MonitorClientController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 获取监控的数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMonitorData")
	@ResponseBody
	public byte[] getMonitorData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		logger.info("获取监控的数据");
		try{
			result.put("statusCode","200");
			GetClientInfoJob job = new GetClientInfoJob();
			result.put("data", job.getMonitorData());
			logger.info("成功获取监控的数据");
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
			logger.error("获取监控的数据失败!");
			logger.error(e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSystemInfo")
	@ResponseBody
	public byte[] getSystemInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		logger.info("开始获取操作系统信息");
		try{
			result.put("statusCode","200");
			JSONObject item = SystemMonitorUtils.getSystemInfo();
			result.put("data",item);
			logger.info("获取操作系统信息成功");
			logger.info("信息如下:");
			logger.info(item.toJSONString());
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception

	 */
	@RequestMapping("/executeCommand")
	@ResponseBody
	public byte[] executeCommand(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		
		String command = RequestUtils.getString(request, "command");
		
		result = MonitorClient.executeCommand(command);
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startProc")
	@ResponseBody
	public byte[] startProc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		String name =  RequestUtils.getParameter(request, "name");
		String port =  RequestUtils.getParameter(request, "port");
		String command =  RequestUtils.getParameter(request, "command");
		try{
			String dirpath = "/sms";
			String projectpath = SystemProperties.getConfigRootPath();
			String dir =  projectpath +"/" + dirpath;
			String sendMsgLogPath = dir + "/" + "smsSendLog.log";
			
			result = MonitorClient.startProc(name,port,command);
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stopProc")
	@ResponseBody
	public byte[] stopProc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		String name =  RequestUtils.getParameter(request, "name");
		String port =  RequestUtils.getParameter(request, "port");
		String command =  RequestUtils.getParameter(request, "command");
		try{
			result = MonitorClient.stopProc(name,port,command);
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 终止进程
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/terminateProc")
	@ResponseBody
	public byte[] terminateProc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		String name =  RequestUtils.getParameter(request, "name");
		String port =  RequestUtils.getParameter(request, "port");
		String command =  RequestUtils.getParameter(request, "command");
		try{
			result = MonitorClient.terminateProc(name,port,command);
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getProcInfo")
	@ResponseBody
	public byte[] getProcInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		String name =  RequestUtils.getParameter(request, "name");
		String port =  RequestUtils.getParameter(request, "port");
		
		try{
			result.put("statusCode","200");
			result.put("data",MonitorClient.getProcInfo(name,port));
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/buildConnection")
	@ResponseBody
	public byte[] buildConnection(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String data =  RequestUtils.getParameter(request, "data");
		String projectpath = SystemProperties.getConfigRootPath();
		Path filePath = Paths.get(projectpath,"monitor","clientConfig");
		
		try{
			if (!Files.exists(filePath.getParent())) {
				Files.createDirectory(filePath.getParent());
			}
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, data.getBytes("UTF-8"));
			result.put("statusCode", "200");
		}catch(Exception e){
			result.put("statusCode", "500");
			result.put("message", e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
