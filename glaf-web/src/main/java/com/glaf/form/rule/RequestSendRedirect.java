package com.glaf.form.rule;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.service.DataSetService;

/**
 * --http 地址转发
 * 
 * @author klaus.wang
 *
 */
public class RequestSendRedirect {

	protected static final Log logger = LogFactory.getLog(RequestSendRedirect.class);

	private static final String dataSourceServiceKey = "dataSourceService";

	public static String redirect(Map<String, String> ruleMap, Map<String, Object> params) {
		// if (!ruleMap.containsKey(dataSourceServiceKey)) {
		// return null;
		// }

		JSONArray arr = null;
		JSONObject json = null;
		String url = null, dataSourceSet = ruleMap.get("dataSourceSet");
		if (StringUtils.isNotBlank(dataSourceSet)) {
			JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
			JSONObject datasourceSetJSONObject = null;
			if (CollectionUtils.isNotEmpty(datasourceSetJSONArray)) {
				datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
				DataSetService dataSetService = ContextFactory.getBean("dataSetService");
				DataSet dataSet = dataSetService.getDataSet(datasourceSetJSONObject//
						.getString("datasetId"));
				if (dataSet != null && StringUtils.isNotBlank(dataSet.getExtText())) {
					url = JSON.parseObject(dataSet.getExtText()).getString("url");
					
					params.put("rows_key", "data");
				//	params.put("om", false);
				}
			}
		}
		if (StringUtils.isBlank(url) && StringUtils.isNotBlank(//
				dataSourceSet = ruleMap.get(dataSourceServiceKey))) {

			if (CollectionUtils.isEmpty(arr = JSON.parseArray(dataSourceSet))) {
				return null;
			}

			json = arr.getJSONObject(0);
			if (json == null) {
				return null;
			}
			if (CollectionUtils.isEmpty(arr = json.getJSONArray("datasource"))) {
				return null;
			}

			json = arr.getJSONObject(0);
			if (json == null) {
				return null;
			}

			if ((url = json.getString("url")) == null) {
				return null;
			}
		}

		if (StringUtils.isBlank(url)) {
			return null;
		}

		try {
			return callPostService(url, params);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static String redirect(Map<String, String> ruleMap, //
			DataSourceRequest dataSourceRequest) {
		
		
		JSONObject params = new JSONObject();
		if (StringUtils.isNotBlank(dataSourceRequest.getParams())) {
			params.put("params", dataSourceRequest.getParams());
		}
		params.put("page", dataSourceRequest.getPage());
		params.put("pageSize", dataSourceRequest.getPageSize());
		params.put("rows", dataSourceRequest.getPageSize());
		return redirect(ruleMap, params);
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	private static HttpSession getSession() {
		HttpSession session = null;
		try {
			session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	/**
	 * 设置token
	 * 
	 * @param httpEntity
	 */
	private static void setHeader(HttpEntityEnclosingRequestBase httpEntity, Map<String, Object> params) {
		Object token = getSession().getAttribute(Global.MT_TOKEN);
		if (token != null) {
			params.put(Global.MT_TOKEN, token.toString());
			httpEntity.addHeader(Global.MT_TOKEN, token.toString());
		}
	}

	/**
	 * post方式调用服务
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String callPostService(String url, Map<String, Object> params)//
			throws UnsupportedEncodingException {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(url);
			setHeader(httpPost, params);
			RequestSendRedirect.setEntity(httpPost, params);
			// httpPost.addHeader("Content-type", "text/html; charset=utf-8");
			logger.debug(url);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("调用失败 :" + response.getStatusLine());
			}
			// 请求结束，返回结果
			String resData = EntityUtils.toString(response.getEntity());
			
			logger.debug("状态码：：：" + statusCode);
			return resData;
		} catch (Exception e) {
			logger.error("服务调用出错(地址：" + url + ")：" + e.getMessage());
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 地址参数传递
	 * 
	 * @param http
	 * @param params
	 * @throws UnsupportedEncodingException
	 */
	protected static void setEntity(HttpEntityEnclosingRequest http, Map<String, Object> params)//
			throws UnsupportedEncodingException {
		BasicNameValuePair nvp = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() == null)
				continue;
			nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
			nvps.add(nvp);
		}
		http.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	}
}
