package com.glaf.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	protected static final Logger logger = Logger.getLogger(HttpClientUtil.class.getName());

	/**
	 * HTTP POST请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数 return 响应体
	 */
	public static String getResponseContent(String url, Map<String, String> params) {
		InputStream ins = null;
		StringBuffer sb = null;
		String content = null;
		// 创建HttpClient实例
		HttpClient httpclient = null;
		PostMethod postMethod = null;
		try {
			httpclient = new HttpClient();
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			postMethod.addRequestHeader("Connection", "keep-alive");
			postMethod.addRequestHeader("Cache-Control", "max-age=0");
			postMethod.addRequestHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			postMethod.addRequestHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.72 Safari/537.36");
			postMethod.addRequestHeader("Accept-Encoding", "gzip,deflate,sdch");
			postMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

			if (params != null && params.size() > 0) {
				NameValuePair[] param = new NameValuePair[params.size()];
				int i = 0;
				for (Entry<String, String> entry : params.entrySet()) {
					param[i] = new NameValuePair(entry.getKey(), entry.getValue());
					i++;
				}
				postMethod.setRequestBody(param);
			}
			int statusCode = httpclient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				sb = new StringBuffer();
				ins = postMethod.getResponseBodyAsStream();
				byte[] b = new byte[1024];
				int r_len = 0;
				while ((r_len = ins.read(b)) > 0) {
					sb.append(new String(b, 0, r_len, postMethod.getResponseCharSet()));
				}
				content = sb.length() > 0 ? sb.toString() : null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return content;
	}

	/**
	 * XML转Map
	 * 
	 * @param xml
	 * @param fields
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> parseXML(String xml, String[] fields) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String tmp = new String(xml.getBytes("utf-8"), "ISO-8859-1");
		SAXReader reader = new SAXReader();
		Reader input = null;
		try {
			input = new StringReader(tmp);
			Document doc = reader.read(input);
			if (doc == null) {
				return null;
			}
			Element root = doc.getRootElement();
			if (root == null) {
				return null;
			}
			for (int i = 0; i < fields.length; i++) {
				map.put(fields[i], root.elementText(fields[i]));
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				input.close();
				input = null;
			}
		}
		return null;
	}

	/**
	 * POST 请求通用方法
	 * 
	 * @param url
	 *            请求地址
	 * @param queryParams
	 *            请求查询参数 url?params
	 * @param requestHead
	 *            请求头信息
	 * @param requestBody
	 *            请求体
	 * @param timeout
	 *            调用超时
	 * @return
	 * @throws IOException
	 */
	public static JSONObject postRequest(String url, Map<String, String> queryParams, Map<String, String> requestHead,
			String resContentType, JSONObject requestBody, int timeout) throws IOException {
		JSONObject responseJson = new JSONObject();
		InputStream ins = null;
		StringBuffer sb = null;
		String content = null;
		// 创建HttpClient实例
		HttpClient httpclient = null;
		PostMethod postMethod = null;
		String queryParamStr = null;
		try {
			httpclient = new HttpClient();
			for (Entry<String, String> queryParam : queryParams.entrySet()) {
				if (queryParamStr == null) {
					queryParamStr = "?" + queryParam.getKey() + "=" + queryParam.getValue();
				} else {
					queryParamStr += "&" + queryParam.getKey() + "=" + queryParam.getValue();
				}
			}
			if (queryParamStr != null) {
				url += queryParamStr;
			}
			postMethod = new PostMethod(url);
			JSONObject queryJson = new JSONObject();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
					.build();
			httpclient.setConnectionTimeout(timeout);
			String reqContentType=requestHead.get("content-Type");
			if(reqContentType!=null&&reqContentType.indexOf("json")>-1) {
				String queryJsonstr = JSONObject.toJSONString(requestBody);
				for (Entry<String, String> headEntry : requestHead.entrySet()) {
					postMethod.addRequestHeader(headEntry.getKey(), headEntry.getValue());
				}
				RequestEntity se = new StringRequestEntity(queryJsonstr, resContentType, "UTF-8");
				postMethod.setRequestEntity(se);
				postMethod.setContentChunked(true);
				postMethod.setDoAuthentication(false);
			}else {
				 // 设置请求体也就是请求的表单提交数据：form表单提交的数据  
			    NameValuePair[] paires = null;  
			    if(requestBody!=null&&requestBody.size()>0){  
			        paires = new NameValuePair[requestBody.size()];  
			        int index = 0;  
			        for(Entry<String, Object> entry:requestBody.entrySet()){  
			            NameValuePair pair = new NameValuePair(entry.getKey(),(String)entry.getValue());  
			            paires[index] = pair;  
			            index++;  
			        }  
			        //请求参数作为数据请求体  
			        postMethod.setRequestBody(paires);  
			    }  
			}
			
			int statusCode = httpclient.executeMethod(postMethod);
			responseJson.put("statusCode", statusCode);
			// if (statusCode == HttpStatus.SC_OK) {
			sb = new StringBuffer();
			ins = postMethod.getResponseBodyAsStream();
			byte[] b = new byte[1024];
			int r_len = 0;
			while ((r_len = ins.read(b)) > 0) {
				sb.append(new String(b, 0, r_len, postMethod.getResponseCharSet()));
			}
			content = sb.length() > 0 ? sb.toString() : null;
			//替换回车换行符
			if(content!=null) {
			   content=content.replaceAll("[\\t\\n\\r]", "");
			}
			responseJson.put("return", content);
			// }
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return responseJson;
	}

	/**
	 * GET 请求通用方法
	 * 
	 * @param url
	 *            请求地址
	 * @param queryParams
	 *            请求查询参数 url?params
	 * @param requestHead
	 *            请求头信息
	 * @param requestBody
	 *            请求体
	 * @param timeout
	 *            调用超时
	 * @return
	 * @throws IOException
	 */
	public static JSONObject getRequest(String url, Map<String, String> queryParams, Map<String, String> requestHead,
			String resContentType, int timeout) throws IOException {
		JSONObject responseJson = new JSONObject();
		InputStream ins = null;
		StringBuffer sb = null;
		String content = null;
		// 创建HttpClient实例
		HttpClient httpclient = null;
		GetMethod getMethod = null;
		String queryParamStr = null;
		try {
			httpclient = new HttpClient();
			if (queryParams != null) {
				for (Entry<String, String> queryParam : queryParams.entrySet()) {
					if (queryParamStr == null) {
						queryParamStr = "?" + queryParam.getKey() + "=" + queryParam.getValue();
					} else {
						queryParamStr += "&" + queryParam.getKey() + "=" + queryParam.getValue();
					}
				}
				if (queryParamStr != null) {
					url += queryParamStr;
				}
			}
			getMethod = new GetMethod(url);
			JSONObject queryJson = new JSONObject();
			httpclient.setConnectionTimeout(timeout);
			if (requestHead != null) {
				for (Entry<String, String> headEntry : requestHead.entrySet()) {
					getMethod.addRequestHeader(headEntry.getKey(), headEntry.getValue());
				}
			}
			getMethod.setDoAuthentication(false);
			int statusCode = httpclient.executeMethod(getMethod);
			responseJson.put("statusCode", statusCode);
			// if (statusCode == HttpStatus.SC_OK) {
			sb = new StringBuffer();
			ins = getMethod.getResponseBodyAsStream();
			/*
			 * byte[] b = new byte[1024]; int r_len = 0; while ((r_len =
			 * ins.read(b)) > 0) { sb.append(new String(b, 0, r_len,
			 * getMethod.getResponseCharSet())); }
			 */
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				sb.append(tmp);
			}
			reader.close();
			ins.close();
			content = sb.length() > 0 ? sb.toString() : null;
			responseJson.put("return", content);
			// }
		} catch (IOException e) {
			logger.error(url+":"+e.getMessage());
			throw e;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return responseJson;
	}

	/**
	 * PUT 请求通用方法
	 * 
	 * @param url
	 *            请求地址
	 * @param queryParams
	 *            请求查询参数 url?params
	 * @param requestHead
	 *            请求头信息
	 * @param requestBody
	 *            请求体
	 * @param timeout
	 *            调用超时
	 * @return
	 * @throws IOException
	 */
	public static JSONObject putRequest(String url, Map<String, String> queryParams, Map<String, String> requestHead,
			String resContentType, JSONObject requestBody, int timeout) throws IOException {
		JSONObject responseJson = new JSONObject();
		InputStream ins = null;
		StringBuffer sb = null;
		String content = null;
		// 创建HttpClient实例
		HttpClient httpclient = null;
		PutMethod putMethod = null;
		String queryParamStr = null;
		try {
			httpclient = new HttpClient();
			if (queryParams != null) {
				for (Entry<String, String> queryParam : queryParams.entrySet()) {
					if (queryParamStr == null) {
						queryParamStr = "?" + queryParam.getKey() + "=" + queryParam.getValue();
					} else {
						queryParamStr += "&" + queryParam.getKey() + "=" + queryParam.getValue();
					}
				}
				if (queryParamStr != null) {
					url += queryParamStr;
				}
			}
			putMethod = new PutMethod(url);
			JSONObject queryJson = new JSONObject();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
					.build();
			httpclient.setConnectionTimeout(timeout);
			String queryJsonstr = JSONObject.toJSONString(requestBody);
			if (requestHead != null) {
				for (Entry<String, String> headEntry : requestHead.entrySet()) {
					putMethod.addRequestHeader(headEntry.getKey(), headEntry.getValue());
				}
			}
			RequestEntity se = new StringRequestEntity(queryJsonstr, resContentType, "UTF-8");
			putMethod.setRequestEntity(se);
			putMethod.setContentChunked(true);
			putMethod.setDoAuthentication(false);
			int statusCode = httpclient.executeMethod(putMethod);
			responseJson.put("statusCode", statusCode);
			// if (statusCode == HttpStatus.SC_OK) {
			sb = new StringBuffer();
			ins = putMethod.getResponseBodyAsStream();
			byte[] b = new byte[1024];
			int r_len = 0;
			while ((r_len = ins.read(b)) > 0) {
				sb.append(new String(b, 0, r_len, putMethod.getResponseCharSet()));
			}
			content = sb.length() > 0 ? sb.toString() : null;
			//替换回车换行符
			if(content!=null) {
			   content=content.replaceAll("[\\t\\n\\r]", "");
			}
			responseJson.put("return", content);
			// }
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (putMethod != null) {
				putMethod.releaseConnection();
			}
		}
		return responseJson;
	}

	/**
	 * DELETE 请求通用方法
	 * 
	 * @param url
	 *            请求地址
	 * @param queryParams
	 *            请求查询参数 url?params
	 * @param requestHead
	 *            请求头信息
	 * @param requestBody
	 *            请求体
	 * @param timeout
	 *            调用超时
	 * @return
	 * @throws IOException
	 */
	public static JSONObject deleteRequest(String url, Map<String, String> queryParams, Map<String, String> requestHead,
			String resContentType, int timeout) throws IOException {
		JSONObject responseJson = new JSONObject();
		InputStream ins = null;
		StringBuffer sb = null;
		String content = null;
		// 创建HttpClient实例
		HttpClient httpclient = null;
		DeleteMethod deleteMethod = null;
		String queryParamStr = null;
		try {
			httpclient = new HttpClient();
			if (queryParams != null) {
				for (Entry<String, String> queryParam : queryParams.entrySet()) {
					if (queryParamStr == null) {
						queryParamStr = "?" + queryParam.getKey() + "=" + queryParam.getValue();
					} else {
						queryParamStr += "&" + queryParam.getKey() + "=" + queryParam.getValue();
					}
				}
				if (queryParamStr != null) {
					url += queryParamStr;
				}
			}
			deleteMethod = new DeleteMethod(url);
			JSONObject queryJson = new JSONObject();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
					.build();
			httpclient.setConnectionTimeout(timeout);
			if (requestHead != null) {
				for (Entry<String, String> headEntry : requestHead.entrySet()) {
					deleteMethod.addRequestHeader(headEntry.getKey(), headEntry.getValue());
				}
			}
			deleteMethod.setDoAuthentication(false);
			int statusCode = httpclient.executeMethod(deleteMethod);
			responseJson.put("statusCode", statusCode);
			// if (statusCode == HttpStatus.SC_OK) {
			sb = new StringBuffer();
			ins = deleteMethod.getResponseBodyAsStream();
			byte[] b = new byte[1024];
			int r_len = 0;
			while ((r_len = ins.read(b)) > 0) {
				sb.append(new String(b, 0, r_len, deleteMethod.getResponseCharSet()));
			}
			content = sb.length() > 0 ? sb.toString() : null;
			//替换回车换行符
			if(content!=null) {
			   content=content.replaceAll("[\\t\\n\\r]", "");
			}
			responseJson.put("return", content);
			// }
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			if (deleteMethod != null) {
				deleteMethod.releaseConnection();
			}
		}
		return responseJson;
	}

	public static void main(String args[]) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "2006");
		params.put("auims_ticket", "476E6C8FE1B34622B87CD08E5CD1816303AF7BE042D34EFDB0A1B9279E35F622");
		String content = HttpClientUtil.getResponseContent("http://218.85.66.148:8299/validate", params);
		System.out.println(content);
	}
}
