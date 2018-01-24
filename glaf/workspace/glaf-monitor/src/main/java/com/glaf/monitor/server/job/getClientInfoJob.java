package com.glaf.monitor.server.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.monitor.server.domain.MonitorLog;
import com.glaf.monitor.server.domain.MonitorProc;
import com.glaf.monitor.server.domain.MonitorProcItem;
import com.glaf.monitor.server.domain.MonitorTerminal;
import com.glaf.monitor.server.domain.MonitorTerminalItem;
import com.glaf.monitor.server.query.MonitorLogQuery;
import com.glaf.monitor.server.query.MonitorProcItemQuery;
import com.glaf.monitor.server.query.MonitorProcQuery;
import com.glaf.monitor.server.query.MonitorTerminalItemQuery;
import com.glaf.monitor.server.query.MonitorTerminalQuery;
import com.glaf.monitor.server.service.MonitorLogService;
import com.glaf.monitor.server.service.MonitorProcItemService;
import com.glaf.monitor.server.service.MonitorProcService;
import com.glaf.monitor.server.service.MonitorTerminalItemService;
import com.glaf.monitor.server.service.MonitorTerminalService;
import com.glaf.monitor.server.util.MonitorLogUtils;

/**
 * 发送短信，调度服务
 * 
 * @author ASUS
 *
 */
public class getClientInfoJob extends BaseJob {
	protected static final Log logger = LogFactory.getLog(getClientInfoJob.class);

	protected static boolean isrunning = false;
	//终端
	protected MonitorTerminalService monitorTerminalService;
	//终端预警项
	protected MonitorTerminalItemService monitorTerminalItemService;
	
//	protected MonitorLogService monitorLogService;
	//服务/进程
	protected MonitorProcService monitorProcService;
	//服务/进程预警项
	protected MonitorProcItemService monitorProcItemService;
	//日志
	protected MonitorLogUtils monitorLogUtils;
	
	public void checkTerminalWarning(MonitorTerminal terminal) {

	}


	/**
	 * 作业运行方法
	 */
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if(isrunning){
			return;
		}
		
		monitorTerminalService = ContextFactory.getBean("com.glaf.monitor.server.service.monitorTerminalService");
		monitorTerminalItemService = ContextFactory
				.getBean("com.glaf.monitor.server.service.monitorTerminalItemService");
		monitorLogUtils = ContextFactory.getBean("com.glaf.monitor.server.util.monitorServerLogUtils");
		
		monitorProcService = ContextFactory.getBean("com.glaf.monitor.server.service.monitorProcService");
		monitorProcItemService = ContextFactory.getBean("com.glaf.monitor.server.service.monitorProcItemService");

		// 获取调度作业名称
		String jobName = context.getJobDetail().getKey().getName();
		logger.info("Executing job: " + jobName + " executing at " + DateUtils.getDateTime(new Date()));

		isrunning = true;
		try {
			
			getTerminalInfo();
			getProcInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		isrunning = false;
	}

	/**
	 * 提醒用户和保存错误信息日志
	 * 
	 * @param terminal
	 * @param str
	 */
	public void warningPeople(MonitorTerminal terminal, String str) {
		System.out.println(str);
	}
	public void warningPeople(MonitorProc proc, String str) {
		System.out.println(str);
	}

	public void getTerminalInfo() {
		MonitorTerminalQuery monitorTerminalQuery = new MonitorTerminalQuery();
		monitorTerminalQuery.setDeleteFlag(0);
		List<MonitorTerminal> terminalList = monitorTerminalService.list(monitorTerminalQuery);
		JSONObject item = null;
		JSONObject itemParent = null;
		JSONObject item2 = null;
		List<MonitorTerminalItem> terminalItemList = null;
		MonitorTerminalItemQuery terminalItemQuery = null;
		String strValue = null;
		String message = null;
		String statusCode = null;
		String errorMessage = null;
		Double intValue = null;
		BufferedWriter bufferWrite  = null;
		logger.info("开始获取所有终端信息");
		for (MonitorTerminal terminal : terminalList) {
			try {
				bufferWrite = monitorLogUtils.getLogWriter(terminal);
				// 遍历终端列表
				logger.info("开始获取终端("+terminal.getId()+"---"+terminal.getName()+")的详细信息。");
				itemParent = getClientSystemInfo(terminal.getMonitorServiceAddress(),null);
				monitorLogUtils.writeLog(bufferWrite, "开始获取终端("+terminal.getId()+"---"+terminal.getName()+")的详细信息。");
				monitorLogUtils.writeLog(bufferWrite, "返回结果:");
				monitorLogUtils.writeLog(bufferWrite, itemParent.toJSONString());
				logger.info("终端结果:"+itemParent.toJSONString());
				logger.info("结束获取终端");
				
				statusCode = itemParent.getString("statusCode");
				if (StringUtils.isEquals(statusCode, "200")) {
					itemParent = itemParent.getJSONObject("data");
					monitorLogUtils.writeLog(bufferWrite, "结束获取终端，结果成功");
				}else{
					itemParent = null;
					monitorLogUtils.writeLog(bufferWrite, "结束获取终端，结果失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally{
				monitorLogUtils.closeBufferedWriter(bufferWrite);
			}
			if(itemParent == null){
				continue;
			}
			terminal.setOsName(itemParent.getString("osName")); // 操作系统名称
			terminal.setOsFac(itemParent.getString("osFac")); // 操作系统厂家
			terminal.setOsVer(itemParent.getString("osVer")); // 操作系统版本
			terminal.setCpuFac(itemParent.getString("cpuFac")); // CPU厂家
			terminal.setCpuCores(itemParent.getInteger("cpuCores")); // CPU核数
			terminal.setCoreMhz(itemParent.getInteger("coreMhz")); // 单核频率
			terminal.setMemType(itemParent.getString("memType")); // 内存类型
			terminal.setMemSize(itemParent.getLong("memSize")); // 内存大小
			terminal.setDiskType(itemParent.getString("diskType")); // 磁盘类型
			terminal.setDiskSize(itemParent.getLong("diskSize")); // 硬盘大小
			terminal.setOtherItems(itemParent.getString("all")); // 其他指标
			try{
				monitorTerminalService.save(terminal);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				logger.error("保存监控信息失败");
			}
			// 遍历预警项值
			terminalItemQuery = new MonitorTerminalItemQuery();
			terminalItemQuery.setTenantId(terminal.getId());
			terminalItemQuery.setDeleteFlag(0);
			terminalItemList = monitorTerminalItemService.list(terminalItemQuery);
			for (MonitorTerminalItem terminalItem : terminalItemList) {
				if (StringUtils.isNotEmpty(terminalItem.getMonitorServiceAddress())) {
					item = getClientSystemInfo(terminalItem.getMonitorServiceAddress(),null);
					statusCode = item.getString("statusCode");
					if (StringUtils.isEquals(statusCode, "200")) {
						item = item.getJSONObject("data");
					}else{
						item = null;
					}
				} else {
					item = itemParent.getJSONObject("all");
				}
				if(item == null){
					continue;
				}
				item2 = item.getJSONObject(terminalItem.getCode());
				strValue = terminalItem.getRefType();
				if (StringUtils.isNotEmpty(strValue)) {
					if (StringUtils.isEquals(strValue, "1")) {
						// 判断可用不低于
						if (StringUtils.isEquals(terminalItem.getUnit(), "percent")) {
							intValue = item2.getDouble("free") / item2.getDouble("total") * 100;
						} else {
							intValue = convertTob(item2.getDouble("free"), item2.getString("unit"));
						}
						// intValue =
						// item2.getInteger("avail_"+terminalItem.getUnit());
					} else {
						// 判断使用不高于
						if (StringUtils.isEquals(terminalItem.getUnit(), "percent")) {
							intValue = item2.getDouble("used") / item2.getDouble("total") * 100;
						} else {
							intValue = convertTob(item2.getDouble("used"), item2.getString("unit"));
						}
						// intValue =
						// item2.getInteger("used_"+terminalItem.getUnit());
					}
					if (intValue != null && terminalItem.getAlarmVal() < intValue) {
						// 超过预警值，预警
						errorMessage = "终端(名称:" + terminal.getName() + ",域名:" + terminal.getDomain() + ",地址:"
								+ terminal.getAddress() + ")的预警项(" + terminalItem.getName() + ")";
						if (StringUtils.isEquals(strValue, "1")) {
							errorMessage += "的可用值已超过范围";
						} else {
							errorMessage += "的使用值已低于范围";
						}
						errorMessage += "(预警值:" + terminalItem.getAlarmVal() + ",实际值:" + String.format("%.2f", intValue)
								+ ")";
						warningPeople(terminal, errorMessage);
					}
				}

			}
		}
	}

	// 获取服务/进程的情况
	public void getProcInfo() {
		MonitorProcQuery monitorProcQuery = new MonitorProcQuery();
		monitorProcQuery.setDeleteFlag(0);
		List<MonitorProc> procList = monitorProcService.list(monitorProcQuery);
		JSONObject item = null;
		JSONObject itemParent = null;
		JSONObject item2 = null;
		List<MonitorProcItem> ProcItemList = null;
		MonitorProcItemQuery terminalProcQuery = null;
		String strValue = null;
		String message = null;
		String errorMessage = null;
		String statusCode = null;
		Double intValue = null;
		BufferedWriter bufferWrite  = null;
		logger.info("开始获取所有服务/进程信息");
		for (MonitorProc monitorProc : procList) {
			try {
				bufferWrite = monitorLogUtils.getLogWriter(monitorProc);
				// 遍历终端列表
				logger.info("开始获取服务/进程("+monitorProc.getId()+"---"+monitorProc.getName()+")的详细信息。");
				NameValuePair[] param = { new NameValuePair("name",monitorProc.getProcessName()),
						new NameValuePair("id",monitorProc.getId()),
						new NameValuePair("port",String.valueOf(monitorProc.getPort()))};
				itemParent = getClientSystemInfo(monitorProc.getMonitorServiceAddress(),param);
				monitorLogUtils.writeLog(bufferWrite, "开始获取服务/进程("+monitorProc.getId()+"---"+monitorProc.getName()+")的详细信息。");
				monitorLogUtils.writeLog(bufferWrite, "返回结果:");
				monitorLogUtils.writeLog(bufferWrite, itemParent.toJSONString());
				logger.info("终端结果:"+itemParent.toJSONString());
				logger.info("结束获取服务/进程");
				
				statusCode = itemParent.getString("statusCode");
				if (StringUtils.isEquals(statusCode, "200")) {
					itemParent = itemParent.getJSONObject("data");
					monitorLogUtils.writeLog(bufferWrite, "结束获取终端，结果成功");
				}else{
					itemParent = null;
					monitorLogUtils.writeLog(bufferWrite, "结束获取终端，结果失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally{
				monitorLogUtils.closeBufferedWriter(bufferWrite);
			}
			if(itemParent == null){
				continue;
			}
			if(StringUtils.isNotEmpty(itemParent.getString("status"))){
				if(StringUtils.isEquals(itemParent.getString("status"), "-1")){
					//宕机，通知用户
					warningPeople(monitorProc, "进程/服务(id:"+monitorProc.getId()
						+",名称:"+monitorProc.getName()
						+",端口:"+monitorProc.getPort()+")宕机了。");
				}
				monitorProc.setStatus(itemParent.getInteger("status"));
			}
			monitorProc.setOtherItems(itemParent.getString("all")); // 其他指标
			try{
				monitorProcService.save(monitorProc);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
				logger.error("保存监控信息失败");
			}
			// 遍历预警项值
			terminalProcQuery = new MonitorProcItemQuery();
			terminalProcQuery.setTenantId(monitorProc.getId());
			terminalProcQuery.setDeleteFlag(0);
			ProcItemList = monitorProcItemService.list(terminalProcQuery);
			for (MonitorProcItem procItem : ProcItemList) {
				if (StringUtils.isNotEmpty(procItem.getMonitorServiceAddress())) {
					NameValuePair[] param = { new NameValuePair("name",monitorProc.getProcessName()),
							new NameValuePair("id",monitorProc.getId()),
							new NameValuePair("port",String.valueOf(monitorProc.getPort()))};
					
					item = getClientSystemInfo(procItem.getMonitorServiceAddress(),param);
					statusCode = item.getString("statusCode");
					if (StringUtils.isEquals(statusCode, "200")) {
						item = item.getJSONObject("data");
					}else{
						item = null;
					}
				} else {
					item = itemParent.getJSONObject("all");
				}
				if(item == null){
					continue;
				}
				item2 = item.getJSONObject(procItem.getCode());
				strValue = procItem.getRefType();
				if (item2 != null && StringUtils.isNotEmpty(strValue)) {
					if (StringUtils.isEquals(strValue, "1")) {
						// 判断可用不低于
						if (StringUtils.isEquals(procItem.getUnit(), "percent")) {
							intValue = item2.getDouble("free") / item2.getDouble("total") * 100;
						} else {
							intValue = convertTob(item2.getDouble("free"), item2.getString("unit"));
						}
						// intValue =
						// item2.getInteger("avail_"+terminalItem.getUnit());
					} else {
						// 判断使用不高于
						if (StringUtils.isEquals(procItem.getUnit(), "percent")) {
							intValue = item2.getDouble("used") / item2.getDouble("total") * 100;
						} else {
							intValue = convertTob(item2.getDouble("used"), item2.getString("unit"));
						}
						// intValue =
						// item2.getInteger("used_"+terminalItem.getUnit());
					}
					if (intValue != null && procItem.getAlarmVal() < intValue) {
						// 超过预警值，预警
						errorMessage = "服务/进程(id:"+monitorProc.getId()+"名称:" + monitorProc.getName() + ",端口:" + monitorProc.getPort() + ",地址:"
								+ monitorProc.getMonitorServiceAddress() + ")的预警项(" + procItem.getName() + ")";
						if (StringUtils.isEquals(strValue, "1")) {
							errorMessage += "的可用值已超过范围";
						} else {
							errorMessage += "的使用值已低于范围";
						}
						errorMessage += "(预警值:" + procItem.getAlarmVal() + ",实际值:" + String.format("%.2f", intValue)
								+ ")";
//						warningPeople(monitorProc, errorMessage);
					}
				}
			}
		}
	}

	public Double convertTob(double value, String type) {
		Double k = null;
		switch (type) {
		case "Kb":
			k = value * 1024;
			break;
		case "Mb":
			k = value * 1024 * 1024;
			break;
		case "Gb":
			k = value * 1024 * 1024 * 1024;
			break;
		}
		return k;
	}

	/**
	 * 获取客户端的操作系统信息
	 * 
	 * @param url
	 *            客户端url地址
	 * @return
	 */
	public JSONObject getClientSystemInfo(String url,NameValuePair[] param) {
		HttpClient client = new HttpClient();// 定义client对象
		PostMethod post = null;
		String result = ""; // 请求后返回的结果
		JSONObject resultObj = null; // 请求后返回的结果（json数据）
		String param1;
		JSONObject returnObj = new JSONObject(); // 返回结果

		logger.info("结束获取终端2222222222222");
		try {
			post = new PostMethod(url);

			// NameValuePair[] param = { new
			// NameValuePair("sysCode",node_message_code),
			// new NameValuePair("pwd",param2),
			// new NameValuePair("data",param1)} ;
			if(param != null){
				post.setRequestBody(param);
			}
			

			int statusCode = client.executeMethod(post); // 打印服务器返回的状态
			if (statusCode != HttpStatus.SC_OK) {
				// 连接请求失败
				returnObj.put("statusCode", "500");
				returnObj.put("message", "连接请求失败");
			}else{
				returnObj.put("statusCode", "500");
				result = post.getResponseBodyAsString(); // 反馈结果
				if (result != null && !result.isEmpty()) {
					resultObj = JSON.parseObject(result);
					param1 = resultObj.getString("statusCode");
					if (StringUtils.isEquals(param1, "200")) {
						returnObj.put("statusCode", "200");
						returnObj.put("data", resultObj.getJSONObject("data"));
					}else{
						returnObj = resultObj;
					}
				}else{
					returnObj.put("message", "无任何反馈结果");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
		
		return returnObj;
	}

	/**
	 * 获取客户端的操作系统信息
	 * 
	 * @param url
	 *            客户端url地址
	 * @return
	 */
	public static JSONObject executeCommand(String url) {
		HttpClient client = new HttpClient();// 定义client对象
		PostMethod post = null;
		String result = ""; // 请求后返回的结果
		JSONObject resultObj = null; // 请求后返回的结果（json数据）
		String param1;
		JSONObject returnObj = null; // 返回结果

		try {
			post = new PostMethod(url);

			// NameValuePair[] param = { new
			// NameValuePair("sysCode",node_message_code),
			// new NameValuePair("pwd",param2),
			// new NameValuePair("data",param1)} ;
			NameValuePair[] param = { new NameValuePair("command", "ipconfig") };
			post.setRequestBody(param);

			int statusCode = client.executeMethod(post); // 打印服务器返回的状态
			if (statusCode != HttpStatus.SC_OK) {
				// 连接请求失败
			}
			result = post.getResponseBodyAsString(); // 反馈结果
			if (result != null && !result.isEmpty()) {
				returnObj = JSON.parseObject(result);
				// param1 = resultObj.getString("statusCode");
				// if(StringUtils.equals(param1, "200")){
				// returnObj = resultObj.getJSONObject("data");
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}

		return returnObj;
	}

}
