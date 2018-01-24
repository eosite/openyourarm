package com.glaf.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class CasClientUtils {
	private static final Log logger = LogFactory.getLog(CasClientUtils.class);
	public static String getTicket(final String server, final String username, final String password,
			final String service,final String appId) {
		notNull(server, "server must not be null");
		notNull(username, "username must not be null");
		notNull(password, "password must not be null");
		notNull(service, "service must not be null");
		return getServiceTicket(server, getTicketGrantingTicket(server, username, password), service,appId);
	}

	/**
	 * 取得ST
	 * @param server
	 * @param ticketGrantingTicket
	 * @param service
	 */
	public static String getServiceTicket(final String server, final String ticketGrantingTicket, final String service,final String appId) {
		if (ticketGrantingTicket == null)
			return null;
		final HttpClient client = new HttpClient();
		final PostMethod post = new PostMethod(server + "/" + ticketGrantingTicket);
		post.setRequestBody(new NameValuePair[] { new NameValuePair("service", service),new NameValuePair("appId", appId) });
		try {
			client.executeMethod(post);
			final String response = post.getResponseBodyAsString();
			switch (post.getStatusCode()) {
			case 200:
				return response;
			default:
				warning("Invalid response code (" + post.getStatusCode() + ") from CAS server!");
				info("Response (1k): " + response.substring(0, Math.min(1024, response.length())));
				break;
			}
		}
		catch (final IOException e) {
			warning(e.getMessage());
		}
		finally {
			post.releaseConnection();
		}
		return null;
	}

	/**
	 * @param server
	 * @param username
	 * @param password
	 */
	public static String getTicketGrantingTicket(final String server, final String username, final String password) {
		final HttpClient client = new HttpClient();

		final PostMethod post = new PostMethod(server);
		post.getParams().setContentCharset("utf-8");  
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		//post.addRequestHeader("Content-Type", "text/plain;charset=UTF-8 ");  
		client.getParams().setContentCharset("utf-8");
		post.setRequestBody(new NameValuePair[] { new NameValuePair("username", username),
				new NameValuePair("password", password) });

		try {
			client.executeMethod(post);

			final String response = post.getResponseBodyAsString();
			info("TGT="+response);
			switch (post.getStatusCode()) {
			case 201: {
				final Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(response);

				if (matcher.matches())
					return matcher.group(1);

				warning("Successful ticket granting request, but no ticket found!");
				info("Response (1k): " + response.substring(0, Math.min(1024, response.length())));
				break;
			}

			default:
				warning("Invalid response code (" + post.getStatusCode() + ") from CAS server!");
				info("Response (1k): " + response.substring(0, Math.min(1024, response.length())));
				break;
			}
		}

		catch (final IOException e) {
			warning(e.getMessage());
		}

		finally {
			post.releaseConnection();
		}

		return null;
	}
	public static String getTicketValidateInfo(String serverValidate, String serviceTicket, String service,String appId) {
		notNull(serviceTicket, "paramter 'serviceTicket' is not null");
		notNull(service, "paramter 'service' is not null");

		final HttpClient client = new HttpClient();
		GetMethod post = null;

		try {
			post = new GetMethod(serverValidate+"?"+"appId="+appId+"&ticket="+serviceTicket+"&service="+URLEncoder.encode(service, "UTF-8"));
			client.executeMethod(post);
			InputStream inputStream = post.getResponseBodyAsStream();   
	        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));   
	        StringBuffer stringBuffer = new StringBuffer();   
	        String str= "";   
	        while((str = br.readLine()) != null){
	            stringBuffer.append(str );   
	        }   
			final String response = stringBuffer.toString();
			info(response);
			switch (post.getStatusCode()) {
			case 200: {
				info("成功取得反馈");
				return response;
			}
			default: {

			}
			}

		} catch (Exception e) {
			warning(e.getMessage());
		} finally {
			//释放资源
			post.releaseConnection();
		}
		return null;
	}

	private static void ticketValidate(String serverValidate, String serviceTicket, String service,String appId) {
		notNull(serviceTicket, "paramter 'serviceTicket' is not null");
		notNull(service, "paramter 'service' is not null");

		final HttpClient client = new HttpClient();
		GetMethod post = null;

		try {
			post = new GetMethod(serverValidate+"?"+"appId="+appId+"&ticket="+serviceTicket+"&service="+URLEncoder.encode(service, "UTF-8"));
			client.executeMethod(post);
			InputStream inputStream = post.getResponseBodyAsStream();   
	        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));   
	        StringBuffer stringBuffer = new StringBuffer();   
	        String str= "";   
	        while((str = br.readLine()) != null){
	            stringBuffer.append(str );   
	        }   
			final String response = stringBuffer.toString();
			//info(response);
			switch (post.getStatusCode()) {
			case 200: {
				info("成功取得用户数据");
				String userId=null;
		        Pattern p = Pattern.compile("cas:user>([^</]+)</cas:user");//正则表达式 commend by danielinbiti  
		        Matcher m = p.matcher(response);//  
		        while (m.find()) {  
		        	userId=m.group(1);
		        	info("用户id：：："+userId);
		        	break;
		        }
			}
			default: {

			 }
			}

		} catch (Exception e) {
			warning(e.getMessage());
		} finally {
			//释放资源
			post.releaseConnection();
		}

	}
	/**
	 * 注销
	 * @param server
	 * @param tgt
	 * @return
	 */
	public static String logout(String server,String tgc){
		  HttpClient httpClient = new HttpClient();  
	        DeleteMethod deleteMethod = new DeleteMethod(server+"/"+tgc);  
	        try {  
	            int statusCode = httpClient.executeMethod(deleteMethod);  
	            if (statusCode != HttpStatus.SC_OK) {  
	                return "Method failed: " + deleteMethod.getStatusLine();  
	            }
	            // Read the response body.  
	            String responseBody = deleteMethod.getResponseBodyAsString();  
	            // Deal with the response.  
	            // Use caution: ensure correct character encoding and is not binary data
	            info(responseBody);
	            return responseBody;  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }finally {  
	            deleteMethod.releaseConnection();  
	        }  
	        return null;  
	}

	private static void notNull(final Object object, final String message) {
		if (object == null)
			throw new IllegalArgumentException(message);
	}

	public static void main(final String[] args) throws Exception {
		final String server = "https://cas.chinaiss.com:8443/cas/v1/tickets";
		final String username = "admin";
		final String password = "admin:111111";
		final String service = "https://cas2.chinaiss.com:8443/service";//随意写
		final String proxyValidate = "https://cas.chinaiss.com:8443/cas/p3/serviceValidate";
		//String tgt=getTicketGrantingTicket(server, username, password);
		ticketValidate(proxyValidate, getTicket(server, username, password, service,"1"), service,"1");
		//后台登陆产生TGT(记录到cookie)
		//获取TGT serviceId与TGT获取 TC (独立外部服务)
		//注销TGT 
		//logout(server,tgt);
		//根据TC和ServiceId获取用户信息  ，根据统一身份用户信息获取当前系统用户信息 ，  (用户账号映射)
	}

	private static void warning(String msg) {
		logger.error(msg);
	}

	private static void info(String msg) {
		logger.info(msg);
	}

}