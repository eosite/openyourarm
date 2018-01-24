package com.glaf.monitor.client.job;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.monitor.client.util.LogUtils;
import com.glaf.monitor.client.util.MonitorClient;
import com.glaf.monitor.client.util.SystemMonitorUtils;
import com.glaf.monitor.server.domain.MonitorTerminal;

/**
 * 发送短信，调度服务
 * 
 * @author ASUS
 *
 */
public class GetClientInfoJob extends BaseJob {
	protected static final Log logger = LogFactory.getLog(GetClientInfoJob.class);

	protected static boolean isrunning = false;

	
	public void checkTerminalWarning(MonitorTerminal terminal) {

	}
	/**
	 * 获取监控数据
	 * @return
	 */
	public JSONObject getMonitorData(){
		JSONObject ret = null;
		try {
			String rootpath = SystemProperties.getConfigRootPath();

			Path configPath = Paths.get(rootpath,"monitor","clientConfig");
			if(Files.exists(configPath)){
				String str = new String(Files.readAllBytes(configPath));
				JSONObject data = JSON.parseObject(str);
				ret = new JSONObject();
				ret.put("serverDatas", getServerData(data.getJSONObject("serverDatas")));
				ret.put("procDatas", getProcData(data.getJSONObject("procDatas")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 获取各服务器对应的预警项的数据
	 * @param serverItems
	 * @return
	 */
	public JSONObject getServerData(JSONObject serverItems){
		JSONObject serverData,baseData,variable,ret = new JSONObject();
		String serverUrl;
		for(String key : serverItems.keySet()){
			serverData = serverItems.getJSONObject(key);
			serverUrl = serverData.getString("serverUrl");
			baseData = serverData.getJSONObject("baseItems");
			variable = new JSONObject();
			variable.put("alarmItems", getServerAlarmDataByType(serverData.getString("type"),serverData.getJSONArray("alarmItems"),baseData));
			ret.put(serverData.getString("id"), variable);
		}
		return ret;
	}
	/**
	 * 获取进程的监控数据
	 * @param procItems
	 * @return
	 */
	public JSONObject getProcData(JSONObject procItems){
		JSONObject serverData,baseData,ret = new JSONObject();
		String serverUrl;
		for(String key : procItems.keySet()){
			serverData = procItems.getJSONObject(key);
			serverUrl = serverData.getString("serverUrl");
			baseData = serverData.getJSONObject("baseItems");
			ret.put(serverData.getString("id"), getProcAlarmDataByType(serverData.getString("type"),serverData.getJSONArray("alarmItems"),baseData));
		}
		return ret;
	}
	/**
	 * 获取监控数据（服务器）
	 * @param type
	 * @param alarmItems
	 * @param params
	 * @return
	 */
	public JSONObject getServerAlarmDataByType(String type,JSONArray alarmItems,JSONObject params){
		JSONObject ret = null;
		switch(type){
			case "PC" : ;
			case "SERVER" : ret = getServerAlarmDataByPC(alarmItems,params);
				ret.put("os", SystemMonitorUtils.getComputerItemDataByCode("os",params));
				break;
			case "ROUTER" : break;
			case "SWITCH" : break;
			case "MOBILE" : break;
			default : return null;
		}
		return ret;
	}
	/**
	 * 获取监控数据（进程）
	 * @param type
	 * @param alarmItems
	 * @param params
	 * @return
	 */
	public JSONObject getProcAlarmDataByType(String type,JSONArray alarmItems,JSONObject params){
		JSONObject ret = null;
		switch(type){
			case "middleware" : 
				ret = getServerAlarmDataByPC(alarmItems,params);
				//检测是否宕机
				try {
					ret.put("status", MonitorClient.getProcStatus(params.getString("port"),params.getString("url")));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "database" : break;
			default : return null;
		}
		return ret;
	}
	/**
	 * 获取服务器的预警项
	 * @param alarmItems
	 * @param params
	 * @return
	 */
	public JSONObject getServerAlarmDataByPC(JSONArray alarmItems,JSONObject params){
		if(alarmItems == null){
			return new JSONObject();
		}
		JSONObject alarmData,ret = new JSONObject();
		String serverUrl,code;
		for(Object obj : alarmItems){
			alarmData = (JSONObject)obj;
			code = alarmData.getString("code");
			ret.put(code,SystemMonitorUtils.getComputerItemDataByCode(code,params));
		}
		return ret;
	}

	/**
	 * 作业运行方法
	 */
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if(isrunning){
			return;
		}
		
		if(context != null){
			// 获取调度作业名称
			String jobName = context.getJobDetail().getKey().getName();
			logger.info("Executing job: " + jobName + " executing at " + DateUtils.getDateTime(new Date()));
		}
		
		isrunning = true;
		try {
			String projectpath = SystemProperties.getConfigRootPath();
			String logFilePath = projectpath + File.separator + "monitor" +File.separator + "clientConfig";

			Path logPath = Paths.get(logFilePath);
			if(Files.exists(logPath)){
				List<String> strList = Files.readAllLines(logPath);
				StringBuffer strBuff = new StringBuffer();
				for(String str : strList){
					strBuff.append(str);
				}
				if(strBuff.length() > 0){
					monitor(JSON.parseObject(strBuff.toString()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		isrunning = false;
	}

	/**
	 * 监控
	 * @param data
	 */
	public void monitor(JSONObject data){
		BufferedWriter writer = LogUtils.getProcLogWriter();
		
		String projectpath = SystemProperties.getConfigRootPath();
		
		try{
			//返回到服务器端的url
			String serverUrl = data.getString("serverUrl");
			//客户端的id
			String id = data.getString("id");
			
			JSONObject ret = new JSONObject();
			ret.put("id", id);
			ret.put("terminalId", id);
			
			//获取客户端信息
			ret.put("infoData", SystemMonitorUtils.getMonitorSystemInfo());
			LogUtils.writeLog(writer,"客户端信息:\t"+ret.getJSONObject("infoData").toJSONString());
			
			//遍历需要监听的进程/服务器
			JSONObject procInfo = new JSONObject();
			//服务/进程配置信息
			JSONArray procConfigAry = data.getJSONArray("procDatas");
			JSONObject procConfig = null;
			for(int i = 0 ; i < procConfigAry.size() ; i++ ){
				procConfig = procConfigAry.getJSONObject(i);
				
				//设置服务/进程信息
				procInfo.put("id", getProcInfo(procConfig.getString("name"),procConfig.getString("port"),procConfig.getString("type")));
			}
			//返回数据
			ret.put("procDatas", procInfo);
			LogUtils.writeLog(writer,"进程/服务信息:\t"+ret.getJSONObject("procDatas").toJSONString());
			
			LogUtils.writeLog(writer,"返回服务端(url:"+serverUrl+")");
			//返回数据给服务器端
			NameValuePair[] param = { new NameValuePair("data",ret.toJSONString())};
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					sendClientSystemInfo(serverUrl,param);
				}
			});
			thread.run();
//			sendClientSystemInfo(serverUrl,param);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogUtils.writeLog(writer,"获取终端信息异常("+e.getMessage()+")");
		}finally{
			LogUtils.closeBufferedWriter(writer);
		}
		
	}
	
	/**
	 * 发送请求
	 * @param url	客户端url地址
	 * @return
	 */
	public JSONObject sendClientSystemInfo(String url, NameValuePair[] param){
		HttpClient client = new HttpClient();// 定义client对象
		PostMethod post = null;
		JSONObject result = new JSONObject();	//请求后返回的结果
		
		try{
			post = new PostMethod(url);
			if(param != null)
				post.setRequestBody(param);
			
			int statusCode = client.executeMethod(post); // 打印服务器返回的状态
			result.put("statusCode", statusCode);
			if (statusCode == HttpStatus.SC_OK) {  
				//连接请求失败
				result.put("result",post.getResponseBodyAsString());	//反馈结果
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(post != null){
				post.releaseConnection();
			}
		}
		return result;
	}
	
	public JSONObject getTerminalItemData(String type,String code,JSONObject params){
		JSONObject ret = null;
		switch(type){
			case "SERVER":;
			case "PC" : ret = SystemMonitorUtils.getComputerItemDataByCode(code,params);break;
			default : return null;
		}
		return ret;
	}
	
	/**
	 * 根据不同类型获取不同的终端信息
	 * 服务器(SERVER),家庭电脑(PC),路由器(ROUTER),转接器(SWITCH),手机(MOBILE)
	 * @param type
	 */
	public JSONObject getTerminalInfo(String type){
		switch(type){
			case "SERVER":;
			case "PC" : return SystemMonitorUtils.getSystemInfo();
			default : return null;
		}
	}
	
	/**
	 * 根据不同类型获取不同的终端信息
	 * 服务器(SERVER),家庭电脑(PC),路由器(ROUTER),转接器(SWITCH),手机(MOBILE)
	 * @param type
	 */
	public JSONObject getProcInfo(String name,String port,String type){
		switch(type){
			case "middleware" : return MonitorClient.getProcInfo(name,port);
			default : return null;
		}
	}
}
