package com.glaf.monitor.client.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.glaf.monitor.client.domain.SystemResource;


public class MonitorClient {
	protected static final Log logger = LogFactory.getLog(MonitorClient.class);
	
	public MonitorClient() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean ishang(String port){
		boolean flag = false;
		
		
		
		return flag;
	}
	public static JSONObject getProcStatus(String port,String url) throws Exception{
		if(StringUtils.isEmpty(port)){
			return null;
		}
		JSONObject ret = new JSONObject();
		boolean flag = true;
		//执行命令
		Runtime run = Runtime.getRuntime();
		List<String> cmd = new ArrayList<String>();  
		//执行命令
        ProcessBuilder pb = new ProcessBuilder(); 
        
		String osName = SystemMonitorUtils.getOsName();
		if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
			//Windows
			pb.directory(Paths.get("C:/Windows/System32").toFile()); 
			cmd.add("cmd");
			cmd.add("/c");
			cmd.add("netstat");
			cmd.add("-aon|findstr");
		}else{
//			pb.directory(Paths.get("/bin/sh").toFile());  
			//Linux
			cmd.add("/bin/sh");
			cmd.add("/c");
			cmd.add("netstat");
			cmd.add("-anp|grep");
		}
		cmd.add(port);
		pb.command(cmd);  
        pb.redirectErrorStream(true);
        Process process = pb.start();  
        int w = process.waitFor();
        if (w != 0){	
        	// 0代表正常退出,表示该端口已被占用,即运行中
        	// !0表示该端口未被占用
			byte[] b = new byte[1024];  
			int readbytes = -1;  
			StringBuffer sb = new StringBuffer();  
			try(InputStream in = process.getInputStream(); ){
				while((readbytes = in.read(b)) != -1){  
					sb.append(new String(b,0,readbytes));  
				}  
			}
			ret.put("status", "500");
			ret.put("message", "改端口未启动!");
			ret.put("errormsg", sb.toString());
        	flag = false;
        }
        if(flag){
        	//判断是否宕机，请求下
        	HttpClient client = new HttpClient();// 定义client对象
        	PostMethod post = null;
        	try{
        		if(StringUtils.isEmpty(url)){
        			url = "http://127.0.0.1:"+port+"/glaf/mx/login";
        		}
        		post = new PostMethod(url);
        		int statusCode = client.executeMethod(post); // 打印服务器返回的状态
        		if (statusCode != HttpStatus.SC_OK) {  
        			//宕机
        			ret.put("status", "500");
        			ret.put("message", "宕机!");
        		}
        	}catch(Exception e){
        		e.printStackTrace();
        		logger.error(e.getMessage());
        	}finally{
        		if(post != null){
        			post.releaseConnection();
        		}
        	}
        }
        process.getOutputStream().close();  // 不要忘记了一定要关
        return ret;
	}
	/**
	 * 检测服务/进程是否运行中
	 * 根据端口检测，是否已经启动
	 * @param port
	 * @return
	 * @throws Exception 
	 */
	public static int checkProcStatus(String port) throws Exception{
		int status = 0;
		//执行命令
		Runtime run = Runtime.getRuntime();
		List<String> cmd = new ArrayList<String>();  
		//执行命令
        ProcessBuilder pb = new ProcessBuilder(); 
        
		String osName = SystemMonitorUtils.getOsName();
		if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
			//Windows
			cmd.add("netstat -aon|findstr");
			
		}else{
			pb.directory(Paths.get("/bin/sh").toFile());  
			//Linux
			cmd.add("netstat -anp|grep");
		}
		cmd.add(port);
		pb.command(cmd);  
        pb.redirectErrorStream(true);
        Process process = pb.start();  
        int w = process.waitFor();
        if (w == 0)// 0代表正常退出  
        {
        	return 1;
        }else {
        	
        }
        process.getOutputStream().close();  // 不要忘记了一定要关
        
//            //进程已启动，判断服务是否宕机
//            if(status == 1){
//            	cmd[2] = "curl http://127.0.0.1:"+port+"/glaf/";
//                process = run.exec(cmd);
//                try(BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                		BufferedReader errorIn = new BufferedReader(new InputStreamReader(process.getErrorStream()));){
//                	String line = null;
//                	line = in.readLine();
//                	if(line == null || (line.indexOf("curl") == 0 && (line.indexOf("Failed") > -1))){
//                		//宕机
//                		status = -1;
//                	}
//                }catch(Exception e1){
//                	e1.printStackTrace();
//                }
//            }
//            process.waitFor();   
//            process.getOutputStream().close();  // 不要忘记了一定要关
		return status;
	}
	
	public static JSONObject getProcInfo(String name,String port){
		JSONObject ret = new JSONObject();
		
		//判断是否启动起来了
		int status = 0;
		try {
			status = checkProcStatus(port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ret.put("status", status);//设置启动状态
		
		return ret;
	}
	
	public static JSONObject startProc(String name,String port,String command){
		//启动
		JSONObject result = new JSONObject();
		BufferedWriter bufferedWriter=LogUtils.getProcLogWriter();
		try{
			LogUtils.writeLog(bufferedWriter,"开始启动服务/进程");
			LogUtils.writeLog(bufferedWriter,"进程名称:"+name+",端口:"+port+",命令:"+command);
			JSONObject ret = executeCommand(command);
			LogUtils.writeLog(bufferedWriter,"执行命令完毕,结果如下:");
			LogUtils.writeLog(bufferedWriter,ret.toJSONString());
			String statusCode = ret.getString("statusCode"); 
			if(StringUtils.isEquals(statusCode, "200")){
				String successMessage = ret.getString("successMessage");
				String errorMessage = ret.getString("errorMessage");
				
				if(StringUtils.isNotEmpty(errorMessage)){
					result.put("statusCode", "500");
					result.put("message", errorMessage);
				}else{
					result.put("statusCode", "200");
					result.put("message", successMessage);
					
					//判断是否启动起来了
					//判断是否启动起来了
					int status = 0;
					try {
						status = checkProcStatus(port);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//					String osName = SystemMonitorUtils.getOsName();
//					if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
//						//Windows
//					}else{
//						//Linux
//						command = "netstat -anp|grep "+port;
//						ret = executeCommand(command);
//						statusCode = ret.getString("statusCode"); 
//						if(StringUtils.isEquals(statusCode, "200")){
//							String successMessage2 = ret.getString("successMessage");
//							String errorMessage2 = ret.getString("errorMessage");
//							if(StringUtils.isNotEmpty(errorMessage)){
//								successMessage += "\n" + errorMessage;
//								result.put("statusCode", "500");
//								result.put("message", successMessage);
//							}else if(StringUtils.isEmpty(successMessage2)){
//								successMessage += "\n未能监听到端口使用情况,请重新操作";
//								result.put("statusCode", "500");
//								result.put("message", successMessage);
//							}
//						}
//					}
				}
			}else{
				result = ret;
			}
		}catch(Exception e){
			LogUtils.closeBufferedWriter(bufferedWriter);
		}
		return result;
	}
	
	public static JSONObject stopProc(String name,String port,String command){
		JSONObject result = new JSONObject();
		BufferedWriter bufferedWriter=LogUtils.getProcLogWriter();
		try{
			LogUtils.writeLog(bufferedWriter,"开始停止服务/进程");
			LogUtils.writeLog(bufferedWriter,"进程名称:"+name+",端口:"+port+",命令:"+command);
			JSONObject ret = executeCommand(command);
			LogUtils.writeLog(bufferedWriter,"执行命令完毕,结果如下:");
			LogUtils.writeLog(bufferedWriter,ret.toJSONString());
			
			String statusCode = ret.getString("statusCode"); 
			if(StringUtils.isEquals(statusCode, "200")){
				String successMessage = ret.getString("successMessage");
				String errorMessage = ret.getString("errorMessage");
				
				if(StringUtils.isNotEmpty(errorMessage)){
					result.put("statusCode", "500");
					result.put("message", errorMessage);
				}else{
					result.put("statusCode", "200");
					result.put("message", successMessage);
				}
			}
		}catch(Exception e){
			LogUtils.closeBufferedWriter(bufferedWriter);
		}
		
		return result;
	}
	
	public static JSONObject terminateProc(String name,String port,String command){
		
		//根据进程名称或端口，找到对应的进程
		
		//杀死对应的进程
//		executeCommand("tasklist|findstr ");
		
		JSONObject result = new JSONObject();
		return result;
	}
	
	
	/**
	 * 
	 * @param command
	 * @return
	 * cmd /c dir 是执行完dir命令后关闭命令窗口。
	   cmd /k dir 是执行完dir命令后不关闭命令窗口。
	   cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。
	   cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭。
	 */
	public static JSONObject executeCommand(String command){
		JSONObject result = new JSONObject();
		String successMessage = "";
		String errorMessage = "";
		if(StringUtils.isNotEmpty(command)){
			try{
				Runtime run = Runtime.getRuntime();
				String[] cmd = new String[3];
				// 如果是window系统
				String osName = SystemMonitorUtils.getOsName();
				if (osName.toLowerCase().contains("windows") || osName.toLowerCase().contains("win")) {
					cmd[0] = "cmd.exe";
					cmd[1] = "/C";
				}else{
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
				}
				cmd[2] = command;
				
	            Process process = run.exec(cmd);
	            System.out.println(process.toString());
	            int lineNum = 0;
	            try(BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            		BufferedReader errorIn = new BufferedReader(new InputStreamReader(process.getErrorStream()));){
	            	String line = null;
	            	lineNum =0;
	            	while ((line = in.readLine()) != null && lineNum <3) {
	            		successMessage += line;
	            		successMessage += "\n";
	            		lineNum++;
		            }
	            	
	            	while ((line = errorIn.readLine()) != null) {
		                errorMessage += line;
		                errorMessage += "\n";
		            }
	            }catch(Exception e1){
	            	e1.printStackTrace();
	            	
	            }
	            
	            int exitValue = process.waitFor();   
	            process.getOutputStream().close();  // 不要忘记了一定要关
	            
	            result.put("statusCode", 200);
	            result.put("successMessage", successMessage);
	            result.put("errorMessage", errorMessage);
			}catch(Exception e){
				result.put("statusCode", 500);
			}
		}else{
			result.put("statusCode", 500);
			result.put("message", "无任何命令");
		}
		return result;
	}
}
