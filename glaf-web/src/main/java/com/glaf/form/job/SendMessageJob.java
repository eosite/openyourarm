package com.glaf.form.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RSA;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormNodeMessage;
import com.glaf.form.core.domain.FormNodeMessageHistory;
import com.glaf.form.core.query.FormNodeMessageQuery;
import com.glaf.form.core.service.FormNodeMessageHistoryService;
import com.glaf.form.core.service.FormNodeMessageService;
import com.glaf.form.core.service.IFormDictoryService;

/**
 * 发送短信，调度服务
 * 
 * @author ASUS
 *
 */
public class SendMessageJob extends BaseJob {
	protected final static Log logger = LogFactory.getLog(SendMessageJob.class);
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	
	protected static boolean isRun = false;
	
	private String sys_message_url = null;
	private String node_message_code = null;
	private String node_message_pwd = null;
	private String node_message_key = null;
	private String message_encryptionFlag = null;
	FormNodeMessageService formNodeMessageService = null;
	FormNodeMessageHistoryService formNodeMessageHistoryService = null;

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// 判断时间段。
//		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 5) {
//			return;
//		}
		if(isRun){
			//若正在运行中时，跳过，为单实例云心
			return;
		}
		
		logger.info("开始短信发送");
		IFormDictoryService formDictoryService = ContextFactory.getBean("formDictoryService");
		formNodeMessageService = ContextFactory
				.getBean("com.glaf.form.core.service.formNodeMessageService");
		formNodeMessageHistoryService = ContextFactory
				.getBean("com.glaf.form.core.service.formNodeMessageHistoryService");
		// 获取调度作业名称
		String jobName = context.getJobDetail().getKey().getName();
		logger.info("Executing job: " + jobName + " executing at " + DateUtils.getDateTime(new Date()));
		HttpClient client = null;
		// 从系统参数中获取发送短信的地址
		sys_message_url = SystemConfig.getString("node_message");
		node_message_code = SystemConfig.getString("node_message_code");
		node_message_pwd = SystemConfig.getString("node_message_pwd");
		node_message_key = SystemConfig.getString("node_message_key");
		message_encryptionFlag = SystemConfig.getString("message_encryptionFlag");
		List<FormNodeMessage> nodeList = null;	//短信列表
		List<FormNodeMessage> nodeVariableList = new ArrayList();	//短信列表中间变量
		FormNodeMessageHistory nodeMessageHistory = null; // 历史纪录
		Integer state = 0;	//状态，中间变量
		String param1 = null;	//号码，中间变量
		String param2 = null;	//短信内容，中间变量
		JSONArray messageArray = new JSONArray();	//待发送列表
		PostMethod post = null;	//post请求
		JSONObject paramsObj = null;	//中间变量
		String result = null;	//中间变量
		try {
			// begin 获取字典中电话名称，和信息名称
			List<FormDictory> list = formDictoryService.getFormDictoryList("noteMessage");
			String telphoneName = ""; // 参数名称
			String msgName = ""; // 参数名称
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (FormDictory dictory : list) {
				if (dictory.getCode().equals("x")) {
					telphoneName = dictory.getValue();
				} else if (dictory.getCode().equals("y")) {
					msgName = dictory.getValue();
				}
			}
			// end 获取字典中电话名称，和信息名称
			
			isRun = true;	//设置为运行状态
			logger.debug("-------------------------发送短信请求------------------");
			// 查询待发送纪录
			nodeList = formNodeMessageService.list(new FormNodeMessageQuery());
			for(int i = 0,n = nodeList.size() ; i < n ; i++){
				FormNodeMessage nodeMessage = nodeList.get(i);
				
				param1 = nodeMessage.getTelephone(); // 获取电话号码
				param2 = nodeMessage.getMsg(); // 获取信息
				try {
					state = nodeMessage.getState();
					if (state == null || state == 0) {
						state = 1; // 状态没有时，设置为1。
					}
					if (state >= 4) {
						// 已发送过三次异常，不进行重新发送
						// 设置状态，从1开始，知道4,1为第一次,2为第二次，3为第三次，第四次跳过
						formNodeMessageService.deleteById(nodeMessage.getId());
						//保存如历史记录中
						try{
							nodeMessageHistory = new FormNodeMessageHistory();
							nodeMessageHistory.setCreator(nodeMessage.getCreator());
							nodeMessageHistory.setCreateDate(nodeMessage.getCreateDate());
							nodeMessageHistory.setTelephone(param1);
							nodeMessageHistory.setMsg(param2);
							nodeMessageHistory.setState(0);//设置失败
							nodeMessageHistory.setResult(nodeMessage.getResult());
							formNodeMessageHistoryService.save(nodeMessageHistory);
						}catch(Exception e){
							logger.error("历史记录保存失败");
							e.printStackTrace();
						}
						continue;
					}
					state++;
					nodeMessage.setState(state);
					formNodeMessageService.save(nodeMessage);
					paramsObj = new JSONObject();
					paramsObj.put("id", nodeMessage.getId());
					paramsObj.put(telphoneName, param1);
					paramsObj.put(msgName, param2);
					if(nodeMessage.getSendLaterTime() != null){
						paramsObj.put("sendLaterTime", sdf.format(nodeMessage.getSendLaterTime()));
					}
					paramsObj.put("subject", nodeMessage.getSubject());
					messageArray.add(paramsObj);
					nodeVariableList.add(nodeMessage);
				} catch (Exception e) {
					logger.error("号码:" + param1 + ",发送短信失败！");
					logger.error(e);

					nodeMessage.setResult("号码:" + param1 + ",发送短信失败！");
					formNodeMessageService.save(nodeMessage);
				}
				
				//每 50 条发送一次,或最后发送一次
				if((i+1) % 1 == 0 || i+1 == n ){
					SendMessage(messageArray,nodeVariableList);
					nodeVariableList = new ArrayList();
					messageArray = new JSONArray();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (client != null) {
				client.getHttpConnectionManager().closeIdleConnections(1);
			}
			isRun = false;	//设置运行状态为false。
		}
	}
	/**
	 * 使用公钥加密数据
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public byte[] encryptText(String publicKey, byte[] data) {
		if (StringUtils.isNotEmpty(publicKey)) {
			byte[] bytes = RSA.encryptByPublicKey(data, publicKey);
			return bytes;
		}
		return null;
	}
	
	private void SendMessage(JSONArray messageArray,List<FormNodeMessage> nodeList){
		HttpClient client = new HttpClient();// 定义client对象
		PostMethod post = null;
		String result = "";
		String param1 = "";
		String param2 = "";
		String param3 = "";
		JSONObject resultObj  = null;
		FormNodeMessageHistory nodeMessageHistory = null;
		
		try{
			post = new PostMethod(sys_message_url);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);// 设置连接超时时间为2秒（连接初始化时间）
			param1 = messageArray.toJSONString();
			param2 = node_message_pwd;
			if (message_encryptionFlag != null && StringUtils.equals(message_encryptionFlag,"ALL")){
				//密码加密
				param2 = Hex.encodeHex(encryptText(node_message_key, param2.getBytes()));
			}
			if (message_encryptionFlag != null && (StringUtils.equals(message_encryptionFlag,"ALL") || StringUtils.equals(message_encryptionFlag,"DATA"))){
				//加密数据
				param1 = Hex.encodeHex(encryptText(node_message_key, param1.getBytes()));
			}
			
			NameValuePair[] param = { new NameValuePair("sysCode",node_message_code),
		        new NameValuePair("pwd",param2),
		        new NameValuePair("data",param1)} ;
			post.setRequestBody(param);
			int statusCode = client.executeMethod(post); // 打印服务器返回的状态
			if (statusCode != HttpStatus.SC_OK) {  
				//连接请求失败
				//连接请求失败
				for (FormNodeMessage nodeMessage : nodeList) {
					nodeMessage.setResult("连接请求失败，等下次重试!");
					formNodeMessageService.save(nodeMessage);
				}
			}
			result = post.getResponseBodyAsString();	//反馈结果  
			
			if(result != null && !result.isEmpty()){
				resultObj = JSON.parseObject(result);
				param2 = resultObj.getString("statusCode");
				param1 = resultObj.getString("message");
				JSONArray errorDataArray = resultObj.getJSONArray("errorData");
				int i = 0;
				JSONObject paramObj = null;
				boolean flag = true;
				if(param2 != null && param2.equals("200")){
					//操作成功
					if(param1 == null || param1.isEmpty()){
						param1 = "发送短信成功!";
					}
					for (FormNodeMessage nodeMessage : nodeList) {
						flag = true;
						for(i=0;i<errorDataArray.size();i++){
							paramObj = errorDataArray.getJSONObject(i);
							if(nodeMessage.getId().longValue() == paramObj.getLongValue("id")){
								nodeMessage.setResult(new String(paramObj.getBytes("result"),"UTF-8"));
								formNodeMessageService.save(nodeMessage);
								flag =false;
								break;
							}
						}
						if(flag){
					    	//先删除后再插入
					    	formNodeMessageService.deleteById(nodeMessage.getId());
					    	try{
					    	//插入历史记录中
						    	nodeMessageHistory = new FormNodeMessageHistory();
						    	nodeMessageHistory.setState(2);	//设置为成功
							    nodeMessageHistory.setCreator(nodeMessage.getCreator());
							    nodeMessageHistory.setCreateDate(nodeMessage.getCreateDate());
							    nodeMessageHistory.setTelephone(nodeMessage.getTelephone());
							    nodeMessageHistory.setMsg(nodeMessage.getMsg());
							    formNodeMessageHistoryService.save(nodeMessageHistory);
					    	}catch(Exception e){
					    		e.printStackTrace();
					    		logger.error("历史记录保存失败");
					    	}
						}
					}
				}else{
					//操作失败
					if(param1 == null || param1.isEmpty()){
						param1 = "发送短信失败!";
					}
					for (FormNodeMessage nodeMessage : nodeList) {
						nodeMessage.setResult(param1);
						formNodeMessageService.save(nodeMessage);
					}
				}
			}
			post.releaseConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String getSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
