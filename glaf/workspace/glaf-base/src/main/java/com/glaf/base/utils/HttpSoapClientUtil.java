package com.glaf.base.utils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;

import com.alibaba.fastjson.JSONObject;

public class HttpSoapClientUtil {
	static int socketTimeout = 30000;// 请求超时时间
	static int connectTimeout = 30000;// 传输超时时间
	private static final Log logger = LogFactory.getLog(HttpSoapClientUtil.class);

	/**
	 * 使用SOAP1.1发送消息
	 * 
	 * @param postUrl
	 * @param soapXml
	 * @param soapAction
	 * @return
	 */
	public static JSONObject doPostSoap1_1(String postUrl, String soapXml, String soapAction) {
		JSONObject responseJson = new JSONObject();
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			int statusCode= response.getStatusLine().getStatusCode();
			responseJson.put("statusCode", statusCode);
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info("response:" + retStr);
				//获取返回内容
				retStr=getResponseData(retStr);
				responseJson.put("return", retStr);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			logger.error("exception in doPostSoap1_1", e);
			responseJson.put("msg",  e.getMessage());
		}
		return responseJson;
	}
	/**
	 * 获取wsdl内容
	 * 
	 * @param postUrl
	 * @return
	 */
	public static String getWsdl(String postUrl) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpGet httpGet = new HttpGet(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpGet.setConfig(requestConfig);
		try {
			httpGet.setHeader("Content-Type", "text/xml;charset=UTF-8");
			CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info("response:" + retStr);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			logger.error("exception in getWsdl", e);
		}
		return retStr;
	}
	/**
	 * 获取xsd内容
	 * 
	 * @param postUrl
	 * @return
	 */
	public static String getWsXsd(String postUrl) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpGet httpGet = new HttpGet(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpGet.setConfig(requestConfig);
		try {
			httpGet.setHeader("Content-Type", "text/xml;charset=UTF-8");
			CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info("response:" + retStr);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			logger.error("exception in getWsd", e);
		}
		return retStr;
	}
	/**
	 * 使用SOAP1.2发送消息
	 * 
	 * @param postUrl
	 * @param soapXml
	 * @param soapAction
	 * @return
	 */
	public static JSONObject doPostSoap1_2(String postUrl, String soapXml, String soapAction) {
		JSONObject responseJson = new JSONObject();
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "application/soap+xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			int statusCode= response.getStatusLine().getStatusCode();
			responseJson.put("statusCode", statusCode);
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info("response:" + retStr);
				//获取返回内容
				retStr=getResponseData(retStr);
				responseJson.put("return", retStr);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			logger.error("exception in doPostSoap1_2", e);
			responseJson.put("msg",  e.getMessage());
		}
		return responseJson;
	}
    /**
     * 创建soap1.2请求xml结构内容
     * @param methodName 方法名称
     * @param namespace 命名空间
     * @param parameterMap 参数
     * @return
     */
	private  static String buildSoap2Xml(String methodName, String namespace, Map<String, String> parameterMap) {
		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:mcsc=\""+namespace+"\">");
		soapRequestData.append("<soap:Body>");
		soapRequestData.append("<mcsc:" + methodName+">");

		Set<String> nameSet = parameterMap.keySet();
		for (String name : nameSet) {
			soapRequestData.append("<" + name + ">" + parameterMap.get(name) + "</" + name + ">");
		}

		soapRequestData.append("</mcsc:" + methodName + ">");
		soapRequestData.append("</soap:Body>");
		soapRequestData.append("</soap:Envelope>");
		return soapRequestData.toString();
	}
	/**
     * 创建soap1.2请求xml结构内容
     * @param methodName 方法名称
     * @param namespace 命名空间
     * @param parameterMap 参数
     * @return
     */
	private  static String buildSoap2XmlType1(String methodName, String namespace, Map<String, String> parameterMap) {
		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
		soapRequestData.append("<soap12:Body>");
		soapRequestData.append("<" + methodName+" xmlns=\""+namespace+"\">");

		Set<String> nameSet = parameterMap.keySet();
		for (String name : nameSet) {
			soapRequestData.append("<" + name + ">" + parameterMap.get(name) + "</" + name + ">");
		}

		soapRequestData.append("</" + methodName + ">");
		soapRequestData.append("</soap12:Body>");
		soapRequestData.append("</soap12:Envelope>");
		return soapRequestData.toString();
	}
	/**
     * 创建soap1.1请求xml结构内容
     * @param methodName 方法名称
     * @param namespace 命名空间
     * @param parameterMap 参数
     * @return
     */
	private static String buildSoap1Xml(String methodName, String namespace, Map<String, String> parameterMap) {
		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData.append("<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mcsc=\""+namespace+"\">");
		soapRequestData.append("<soapenv:Body>");
		soapRequestData.append("<mcsc:" + methodName+">");
        if(parameterMap!=null){
			Set<String> nameSet = parameterMap.keySet();
			for (String name : nameSet) {
				soapRequestData.append("<" + name + ">" + parameterMap.get(name) + "</" + name + ">");
			}
        }
		soapRequestData.append("</mcsc:" + methodName + ">");
		soapRequestData.append("</soapenv:Body>");
		soapRequestData.append("</soapenv:Envelope>");
		return soapRequestData.toString();
	}
	/**
     * 创建soap1.1请求xml结构内容
     * @param methodName 方法名称
     * @param namespace 命名空间
     * @param parameterMap 参数
     * @return
     */
	private static String buildSoap1XmlType1(String methodName, String namespace, Map<String, String> parameterMap) {
		StringBuffer soapRequestData = new StringBuffer();
		soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		soapRequestData.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		soapRequestData.append("<soap:Body>");
		soapRequestData.append("<" + methodName+" xmlns=\""+namespace+"\">");

		Set<String> nameSet = parameterMap.keySet();
		for (String name : nameSet) {
			soapRequestData.append("<" + name + ">" + parameterMap.get(name) + "</" + name + ">");
		}

		soapRequestData.append("</" + methodName + ">");
		soapRequestData.append("</soap:Body>");
		soapRequestData.append("</soap:Envelope>");
		return soapRequestData.toString();
	}
	/**
	 * websevice调用入口方法
	 * @param protocalType
	 * @param wsdlLocation
	 * @param namespace
	 * @param methodName
	 * @param parameterMap
	 * @return
	 */
	public static JSONObject postSoap(String protocalType,String wsdlLocation,String namespace,String methodName,Map<String, String> parameterMap){
		if(protocalType.equals("soap1.2")){
			String soapXml=buildSoap2XmlType1(methodName,namespace,parameterMap);
			return doPostSoap1_2(wsdlLocation,soapXml,"");
		}else{
			String soapXml=buildSoap1XmlType1(methodName,namespace,parameterMap);
			return doPostSoap1_1(wsdlLocation,soapXml,"");
		}
	}
	
	public static String getResponseData(String result) {
		String resultData=null;
		if(StringUtils.isNotEmpty(result)){
			try {
				Document document = DocumentHelper.parseText(result);
				//获取根节点元素对象  
		        Element root = document.getRootElement(); 
		        Namespace snm = root.getNamespaceForPrefix("soap");
		        if(snm!=null&&root.nodeCount()==4) {
		        	resultData=root.node(3).getStringValue();
		        }else {
		           resultData=((Element)root.node(1)).node(0).getStringValue();
		        }
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
		return resultData;
	}
	public static void main(String[] args){
		String wsdlLocation="http://192.168.1.128:10000/ws";
		//postSoap("soap1.1",wsdlLocation,"http://mcsc.chinaiss.com/","helloWorld",null);
		Map<String,String> paramMap=new HashMap<String,String>();
		//paramMap.put("a","aaa");
		//postSoap("soap1.1",wsdlLocation,"http://mcsc.chinaiss.com/","HelloWorld",paramMap);
		//paramMap=new HashMap<String,String>();
		paramMap.put("a","1");
		paramMap.put("b","2");
		postSoap("soap1.2",wsdlLocation,"http://mcsc.chinaiss.com/","Add",paramMap);
		//getWsdl(wsdlLocation+"?wsdl");
		//getWsXsd(wsdlLocation+"?xsd=1");
	}
}
