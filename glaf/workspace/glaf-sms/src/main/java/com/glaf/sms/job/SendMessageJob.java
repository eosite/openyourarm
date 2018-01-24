package com.glaf.sms.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.sms.domain.SmsClient;
import com.glaf.sms.domain.SmsHistoryMessage;
import com.glaf.sms.domain.SmsMessage;
import com.glaf.sms.domain.SmsServer;
import com.glaf.sms.query.SmsClientQuery;
import com.glaf.sms.query.SmsHistoryMessageQuery;
import com.glaf.sms.query.SmsMessageQuery;
import com.glaf.sms.query.SmsServerQuery;
import com.glaf.sms.service.SmsClientService;
import com.glaf.sms.service.SmsHistoryMessageService;
import com.glaf.sms.service.SmsMessageService;
import com.glaf.sms.service.SmsServerService;

public class SendMessageJob extends BaseJob {
	protected final static Log logger = LogFactory.getLog(SendMessageJob.class);
	protected SmsMessageService smsMessageService;
	protected SmsServerService smsServerService;
	protected SmsClientService smsClientService;
	protected SmsHistoryMessageService smsHistoryMessageService;
	
	protected static int runNum = 0;
	
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		
		if(runNum > 0){
			return;
		}
		
		smsServerService = ContextFactory.getBean("com.glaf.sms.service.smsServerService");
		smsMessageService = ContextFactory.getBean("com.glaf.sms.service.smsMessageService");
		
		int count = smsMessageService.getSmsMessageAbleSendCount();
		int sendSize = 1000;	//每次查询发送数量
		List<SmsMessage> list;
		
		SmsServerQuery serverQuery = new SmsServerQuery();
		serverQuery.setLocked(0);
		List<SmsServer> serverList = smsServerService.list(serverQuery);
		
		Thread thread = null;
		try {
			for(int i = 0 ,k = 0; i < count ; i += sendSize, k++){
				list = smsMessageService.getSmsMessageAbleSend();
//				list = smsMessageService.getSmsMessagesByQueryCriteria(i, sendSize, query);
				if(k >= serverList.size()){
					k = 0;
				}
				while(runNum>5){
					Thread.sleep(1000);
				}
				
				runNum++;
				thread = new SendMessageThread(list,serverList.get(k)); 
				thread.start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
		}
	}
}

class SendMessageThread extends Thread {
	protected final static Log logger = LogFactory.getLog(SendMessageThread.class);
	protected SmsMessageService smsMessageService;
	protected SmsServerService smsServerService;
	protected SmsHistoryMessageService smsHistoryMessageService;
	protected SmsClientService SmsClientService;
	protected BufferedWriter bufferWriter;
	protected SimpleDateFormat sdf;
	
	private List<SmsMessage> list;
	private SmsServer smsServer;
	private Date zeroDate = null; 
	private Map<String,Integer> smsClientNumMap;
	
	public SendMessageThread(List<SmsMessage> list,SmsServer smsServer){
		super(); 
		this.list = list;
		this.smsServer = smsServer;
		smsMessageService = ContextFactory.getBean("com.glaf.sms.service.smsMessageService");
		smsHistoryMessageService = ContextFactory.getBean("com.glaf.sms.service.smsHistoryMessageService");
		SmsClientService = ContextFactory.getBean("com.glaf.sms.service.smsClientService");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        zeroDate = calendar.getTime();
        
        SmsClientQuery smsClientQuery = new SmsClientQuery();
        smsClientQuery.setLocked(0);
        List<SmsClient> smsClientList = SmsClientService.list(smsClientQuery);
    	smsClientNumMap = new HashMap<String, Integer>();
    	for(SmsClient smsClient : smsClientList){
    		smsClientNumMap.put(smsClient.getId(), smsClient.getLimit());
    	}
    	
		
		String dirpath = "/sms";
		String projectpath = SystemProperties.getConfigRootPath();
		String dir =  projectpath +"/" + dirpath;
		String sendMsgLogPath = dir + "/" + "smsSendLog.log";
		
		File sendMsgLogFile = new File(sendMsgLogPath);
		bufferWriter = null;
		try{
			Path dirPath= Paths.get(dir);
			if(!Files.exists(dirPath)){
				Files.createDirectory(dirPath);
			}
			if(!sendMsgLogFile.exists()){
				sendMsgLogFile.createNewFile();
			}
			bufferWriter = new BufferedWriter(new FileWriter(sendMsgLogFile,true));
		}catch(Exception e){
			
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			this.send(list, smsServer);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		SendMessageJob.runNum--;
	}
	
	public void send(List<SmsMessage> list,SmsServer smsServer){
		String sendIp = smsServer.getServerIP();
		int sendPort = smsServer.getPort();
		String sendPath = smsServer.getPath();
		String serverId = smsServer.getId();
		
		String url = "";
		String telphone = "";
		String msg = "";
		HttpClient client = null;
		GetMethod method = null;
		int variable =0;
		String result = "";
		Date nowDate = new Date();
		SmsHistoryMessage smsHistoryMessage = null;
		Calendar calendar = Calendar.getInstance();
		String logMessage = "";
		for(SmsMessage smsMessage : list){
			logMessage = "";
			try{
				
				if(!smsClientNumMap.containsKey(smsMessage.getClientId())){
					continue;
				}else{
					SmsHistoryMessageQuery smsHistoryMessageQuery = new SmsHistoryMessageQuery();
					smsHistoryMessageQuery.setClientId(smsMessage.getClientId());
					smsHistoryMessageQuery.setSendTimeGreaterThanOrEqual(zeroDate);
					smsHistoryMessageQuery.setStatus(2);
					variable = smsHistoryMessageService.getSmsHistoryMessageCountByQueryCriteria(smsHistoryMessageQuery);
					System.out.println(smsClientNumMap.get(smsMessage.getClientId()));
					System.out.println(smsClientNumMap.get(smsMessage.getClientId())<= variable);
					System.out.println(variable);
					if(smsClientNumMap.get(smsMessage.getClientId())<= variable){
						logMessage += ""+sdf.format(new Date())+":" + "客户端可发送短信已达到上限，不允许发送";
						continue;
					}
					smsHistoryMessageQuery.setMobile(smsMessage.getMobile());
					variable = smsHistoryMessageService.getSmsHistoryMessageCountByQueryCriteria(smsHistoryMessageQuery);
					if(smsMessage.getLimit()<= variable){
						logMessage += ""+sdf.format(new Date())+":" + "该用户可发送短信已达到上限，不允许发送";
						continue;
					}
				}
				
				variable = smsMessage.getRetryTimes();
				variable ++;
				if(variable > 3){
					smsMessage.setStatus(-1);
				}
				smsMessage.setSendTime(nowDate);
				smsMessage.setRetryTimes(variable);
				smsMessage.setServerId(serverId);
				smsMessageService.save(smsMessage);
				if(variable > 3){
					smsMessageService.deleteById(smsMessage.getId());
					smsHistoryMessage= new SmsHistoryMessage();
					smsHistoryMessage.setClientId(smsMessage.getClientId());
					smsHistoryMessage.setServerId(serverId);
					smsHistoryMessage.setName(smsMessage.getName());
					smsHistoryMessage.setMobile(smsMessage.getMobile());
					smsHistoryMessage.setSubject(smsMessage.getSubject());
					smsHistoryMessage.setMessage(smsMessage.getMessage());
					calendar.setTime(smsMessage.getCreateTime());
					smsHistoryMessage.setYear(calendar.get(Calendar.YEAR));
					smsHistoryMessage.setMonth(calendar.get(Calendar.MONTH) + 1);
					smsHistoryMessage.setSendTime(nowDate);
					smsHistoryMessage.setFullDay(DateUtils.getYearMonthDay(nowDate));
					
					smsHistoryMessage.setResult(smsMessage.getResult());
					smsHistoryMessage.setStatus(-1);
					smsHistoryMessageService.save(smsHistoryMessage);
					continue;
				}
				
				telphone = smsMessage.getMobile();
				msg = smsMessage.getMessage();
				url = sendIp + ":" + sendPort + sendPath;
				url += "?token=A1A1" + DigestUtils.md5Hex(telphone+msg);
				url += "&telphone=" + telphone;
				url += "&msg=" + URLEncoder.encode(msg,"UTF-8");
				client = new HttpClient();
				
				logger.info(":发送短信("+smsMessage.getId()+"),url:"+url+";短信内容:"+msg);
				
				logMessage += ""+sdf.format(new Date())+":";
				method = new GetMethod(url);
				
				variable = client.executeMethod(method);
				if (variable != HttpStatus.SC_OK) {  
					logger.error("号码("+telphone+")发送短信失败,远程访问失败，请稍后再试。");
					result = "远程访问失败，请稍后再试。";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					smsMessageService.save(smsMessage);
					logMessage += "结果失败(远程访问失败);";
				}
				result = method.getResponseBodyAsString();	
				if(result == null ){
					logger.error("号码("+telphone+")发送短信失败,返回结果解析不正确。");
					result = "返回结果解析不正确。";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(返回结果解析不正确);";
				}else if(result.equals("YES")){
					variable = smsClientNumMap.get(smsMessage.getClientId());
					variable++;
					smsClientNumMap.put(smsMessage.getClientId(),variable);
					
					smsMessageService.deleteById(smsMessage.getId());
					logMessage += "结果成功(发送成功);";
					smsHistoryMessage= new SmsHistoryMessage();
					smsHistoryMessage.setClientId(smsMessage.getClientId());
					smsHistoryMessage.setServerId(serverId);
					smsHistoryMessage.setName(smsMessage.getName());
					smsHistoryMessage.setMobile(smsMessage.getMobile());
					smsHistoryMessage.setSubject(smsMessage.getSubject());
					smsHistoryMessage.setMessage(smsMessage.getMessage());
					calendar.setTime(smsMessage.getCreateTime());
					smsHistoryMessage.setYear(calendar.get(Calendar.YEAR));
					smsHistoryMessage.setMonth(calendar.get(Calendar.MONTH) + 1);
					smsHistoryMessage.setSendTime(nowDate);
					smsHistoryMessage.setFullDay(DateUtils.getYearMonthDay(nowDate));
					
					smsHistoryMessage.setStatus(2);
					smsHistoryMessageService.save(smsHistoryMessage);
					continue;
				}else if(result.equals("NO")){
					result = "发送失败。";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(发送失败);";
				}else if(result.equals("No Code")){
					result = "token错误";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(token没有此编号或者不符号36位长度);";
				}else if(result.equals("No Telphone")){
					result = "手机号码格式不对";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(手机号码格式不对);";
				}else if(result.equals("No msg")){
					result = "没有需要发送的内容";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(没有需要发送的内容);";
				}else if(result.equals("Send Error")){
					result = "MD5值不相同";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(MD5值不相同);";
				}else{
					logger.error("号码("+telphone+")发送短信失败,返回结果解析不正确。");
					result = "返回结果解析不正确。";
					smsMessage.setResult(result);
					smsMessage.setStatus(1);
					logMessage += "结果失败(返回结果解析不正确);";
				}
				smsMessageService.save(smsMessage);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(""+(new Date())+":发送短信("+smsMessage.getId()+")失败,url:"+url);
				logger.error("操作异常:"+e.getMessage());
				logMessage += "结果失败(异常操作,"+e.getMessage()+");";
				try{
					smsMessage.setResult("操作异常:"+e.getMessage());
					smsMessage.setStatus(1);
					smsMessageService.save(smsMessage);
				}catch(Exception e1){
					
				}
			}finally{
				if(client != null){
					client.getHttpConnectionManager().closeIdleConnections(1);
				}
				if(bufferWriter != null){
					logMessage += "发送短信(短信ID:"+smsMessage.getId()+";客户端ID:"+smsMessage.getClientId()+";服务器ID:"+smsMessage.getServerId()+"),url:"+url+";短信内容:"+msg;
					try{
						bufferWriter.write(logMessage);
						bufferWriter.newLine();
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		if(bufferWriter != null){
			try {
				bufferWriter.flush();
				bufferWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}