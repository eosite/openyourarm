package com.glaf.dingtalk.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.utils.HttpClientUtil;
import com.glaf.core.cache.CacheFactory;
import com.glaf.teim.domain.EimBaseInfo;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.mapper.EimBaseInfoMapper;
import com.glaf.teim.mapper.EimServerTmpMapper;

@Service("com.glaf.dingtalk.service.dingTalkService")
@Transactional(readOnly = true)
public class DingTalkServiceImpl implements DingTalkService {
	protected final Log logger = LogFactory.getLog(getClass());
	protected EimBaseInfoMapper eimBaseInfoMapper;
	protected EimServerTmpMapper eimServerTmpMapper;
	public static final long cacheTime = 1000 * 60 * 55 * 2;

	public JSONObject getAccessTokenByBaseId(EimBaseInfo baseInfo) {
		// TODO Auto-generated method stub
		BaseDataManager manager = BaseDataManager.getInstance();
		// 获取访问令牌服务模板ID;
		BaseDataInfo baseDataInfo = manager.getBaseData("accessTokenTmp", "dingtalk");
		String accessTokenTmp = baseDataInfo.getValue();
		// 从缓存中获取AccessToken
		String accessToken = CacheFactory.getString("dingtalk", "accessToken");
		/*
		 * 因cache有问题，暂时每次都重新获取
		 */
		//String accessToken =null;
		if (StringUtils.isNotEmpty(accessToken)) {
			JSONObject accessTokenJson = JSONObject.parseObject(accessToken);
			// 获取token过期时间
			long invalidTime = accessTokenJson.getLongValue("invalidTime");
			long currentTime = new Date().getTime();
			if (currentTime >= invalidTime) {
				return createAccessToken(baseInfo, accessTokenTmp);
			} else {
				return accessTokenJson;
			}
		} else {
			return createAccessToken(baseInfo, accessTokenTmp);
		}
	}

	@Override
	public JSONObject getAccessTokenByBaseId(String baseId) {
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		return getAccessTokenByBaseId(baseInfo);
	}

	public JSONObject getJsapiTicket(EimBaseInfo baseInfo) {
		// 从缓存中获取AccessToken
		//String jsapiTicket = CacheFactory.getString("dingtalk", "jsapiTicket");
		String jsapiTicket =null;
		long currentTime = new Date().getTime();
		long invalidTime = 0l;
		JSONObject jsapiTicketJson = null;
		if (StringUtils.isNotEmpty(jsapiTicket)) {
			// 获取过期时间
			jsapiTicketJson = JSONObject.parseObject(jsapiTicket);
			invalidTime = jsapiTicketJson.getLongValue("invalidTime");
		}
		if (StringUtils.isEmpty(jsapiTicket) || currentTime >= invalidTime) {
			JSONObject accessTokenJson = getAccessTokenByBaseId(baseInfo);
			if (accessTokenJson == null || !accessTokenJson.containsKey("access_token")) {
				return null;
			}
			// 获取访问令牌服务模板ID;
			BaseDataManager manager = BaseDataManager.getInstance();
			BaseDataInfo baseDataInfo = manager.getBaseData("jsapiTicketTmp", "dingtalk");
			String jsapiTicketTmp = baseDataInfo.getValue();
			String access_token = getAccessToken(baseInfo);
			return createJsapiTicket(baseInfo, jsapiTicketTmp, access_token);
		} else {
			return jsapiTicketJson;
		}
	}

	public String getAccessToken(EimBaseInfo baseInfo) {
		JSONObject accessTokenJson = getAccessTokenByBaseId(baseInfo);
		// 获取访问令牌服务模板ID;
		BaseDataManager manager = BaseDataManager.getInstance();
		BaseDataInfo baseDataInfo = manager.getBaseData("jsapiTicketTmp", "dingtalk");
		String jsapiTicketTmp = baseDataInfo.getValue();
		if (accessTokenJson == null || !accessTokenJson.containsKey("access_token")) {
			return null;
		}
		String access_token = accessTokenJson.getString("access_token");
		return access_token;
	}

	/**
	 * 生成访问令牌
	 * 
	 * @param baseInfo
	 * @param codeTmp
	 */
	private JSONObject createAccessToken(EimBaseInfo baseInfo, String codeTmp) {
		// 生成访问令牌
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(codeTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		String corpid = baseInfo.getPaasId();
		String corpsecret = baseInfo.getSecret();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("corpid", corpid);
		paramsJson.put("corpsecret", corpsecret);
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			requestHead = createMapByParamsJson(paramsJson, reqHeaderTmp);
		}
		String reqContentType = tmp.getReqContentType();
		if (StringUtils.isNotEmpty(reqContentType)) {
			requestHead.put("content-Type", "text/json;charset=utf-8");
		}
		String resContentType = tmp.getResContentType();
		if (StringUtils.isEmpty(resContentType)) {
			resContentType = "application/json";
		}
		String url = baseInfo.getIp() + tmp.getPath_();
		Map<String, String> queryParams = new HashMap<String, String>();
		// 获取URL请求参数模板
		String queryParamTmp = tmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			queryParams = createMapByParamsJson(paramsJson, queryParamTmp);
		}
		JSONObject requestBody = new JSONObject();
		// 获取请求体模板
		String reqBodyTmp = tmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			requestBody = createJSONByParamsJson(paramsJson, reqBodyTmp);
		}
		try {
			// 获取请求方式
			String reqType = tmp.getReqType();
			if (reqType.equals("POST")) {
				returnJSON = HttpClientUtil.postRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			} else if (reqType.equals("GET")) {
				returnJSON = HttpClientUtil.getRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("DELETE")) {
				returnJSON = HttpClientUtil.deleteRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("PUT")) {
				returnJSON = HttpClientUtil.putRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			}
			if (returnJSON == null) {
				return null;
			}
			returnJSON = returnJSON.getJSONObject("return");
			if (returnJSON != null && returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				logger.info("获取访问令牌成功！" + returnJSON.toJSONString());
				// 记录数据到缓存
				Date curr = new Date();
				long invalidTime = curr.getTime() + cacheTime;
				returnJSON.put("invalidTime", invalidTime);
				CacheFactory.put("dingtalk", "accessToken", returnJSON.toJSONString());
				return returnJSON;
			} else {
				logger.error("获取访问令牌失败！" + returnJSON.toJSONString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成访问令牌
	 * 
	 * @param baseInfo
	 * @param codeTmp
	 */
	private JSONObject createJsapiTicket(EimBaseInfo baseInfo, String codeTmp, String access_token) {
		// 生成访问令牌
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(codeTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("access_token", access_token);
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			requestHead = createMapByParamsJson(paramsJson, reqHeaderTmp);
		}
		String reqContentType = tmp.getReqContentType();
		if (StringUtils.isNotEmpty(reqContentType)) {
			requestHead.put("content-Type", "text/json;charset=utf-8");
		}
		String resContentType = tmp.getResContentType();
		if (StringUtils.isEmpty(resContentType)) {
			resContentType = "application/json";
		}
		String url = baseInfo.getIp() + tmp.getPath_();
		Map<String, String> queryParams = new HashMap<String, String>();
		// 获取URL请求参数模板
		String queryParamTmp = tmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			queryParams = createMapByParamsJson(paramsJson, queryParamTmp);
		}
		JSONObject requestBody = new JSONObject();
		// 获取请求体模板
		String reqBodyTmp = tmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			requestBody = createJSONByParamsJson(paramsJson, reqBodyTmp);
		}
		try {
			// 获取请求方式
			String reqType = tmp.getReqType();
			if (reqType.equals("POST")) {
				returnJSON = HttpClientUtil.postRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			} else if (reqType.equals("GET")) {
				returnJSON = HttpClientUtil.getRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("DELETE")) {
				returnJSON = HttpClientUtil.deleteRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("PUT")) {
				returnJSON = HttpClientUtil.putRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			}
			if (returnJSON == null) {
				return null;
			}
			returnJSON = returnJSON.getJSONObject("return");
			if (returnJSON != null && returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				logger.info("获取临时访问票据成功！" + returnJSON.toJSONString());
				// 记录数据到缓存
				Date curr = new Date();
				long invalidTime = curr.getTime() + cacheTime;
				returnJSON.put("invalidTime", invalidTime);
				CacheFactory.put("dingtalk", "jsapiTicket", returnJSON.toJSONString());
				return returnJSON;
			} else {
				logger.error("获取临时访问票据失败！" + returnJSON.toJSONString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据参数值和模板生成实例
	 * 
	 * @param paramsJson
	 * @param tmpJsonStr
	 * @return
	 */
	public Map<String, String> createMapByParamsJson(JSONObject paramsJson, String tmpJsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject tmpJson = JSONObject.parseObject(tmpJsonStr);
		String key = null;
		JSONObject itemJSON = null;
		String val = null;
		for (Entry<String, Object> entry : tmpJson.entrySet()) {
			key = entry.getKey();
			itemJSON = entry.getValue() != null ? (JSONObject) entry.getValue() : null;
			if (StringUtils.isNotEmpty(key) && itemJSON != null) {
				// 获取值
				val = paramsJson.getString(key);
				if (StringUtils.isEmpty(val)) {
					val = itemJSON.getString("defaultval");
				}
				if (StringUtils.isNotEmpty(val)) {
					if (key.equals("redirect_uri")) {
						val = URLEncoder.encode(val);
					}
					map.put(key, val);
				}
			}

		}
		return map;
	}

	/**
	 * 根据参数值和模板生成实例
	 * 
	 * @param paramsJson
	 * @param tmpJsonStr
	 * @return
	 */
	public JSONObject createJSONByParamsJson(JSONObject paramsJson, String tmpJsonStr) {
		JSONObject json = new JSONObject();
		JSONObject tmpJson = JSONObject.parseObject(tmpJsonStr);
		String key = null;
		JSONObject itemJSON = null;
		String type = null;
		Object val = null;
		for (Entry<String, Object> entry : tmpJson.entrySet()) {
			key = entry.getKey();
			itemJSON = entry.getValue() != null ? (JSONObject) entry.getValue() : null;
			if (StringUtils.isNotEmpty(key) && itemJSON != null) {
				// 获取值
				type = itemJSON.getString("type");
				if (type.equals("string")) {
					val = paramsJson.getString(key);
					if (StringUtils.isEmpty((String) val)) {
						val = itemJSON.getString("defaultval");
					}
					if (StringUtils.isNotEmpty((String) val)) {
						json.put(key, (String) val);
					}
				} else if (type.equals("int")) {
					val = paramsJson.getInteger(key);
					if (val != null) {
						val = itemJSON.getInteger("defaultval");
					}
					if (val != null) {
						json.put(key, (Integer) val);
					}
				}

			}
		}
		return json;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimBaseInfoMapper")
	public void setEimBaseInfoMapper(EimBaseInfoMapper eimBaseInfoMapper) {
		this.eimBaseInfoMapper = eimBaseInfoMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerTmpMapper")
	public void setEimServerTmpMapper(EimServerTmpMapper eimServerTmpMapper) {
		this.eimServerTmpMapper = eimServerTmpMapper;
	}

	@Override
	public JSONObject getUserInfo(String baseId, String code, String accessToken) {
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		// 生成访问令牌
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		BaseDataManager manager = BaseDataManager.getInstance();
		// 获取访问令牌服务模板ID;
		BaseDataInfo baseDataInfo = manager.getBaseData("userInfoTmp", "dingtalk");
		String codeTmp = baseDataInfo.getValue();
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(codeTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("code", code);
		paramsJson.put("access_token", accessToken);
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			requestHead = createMapByParamsJson(paramsJson, reqHeaderTmp);
		}
		String reqContentType = tmp.getReqContentType();
		if (StringUtils.isNotEmpty(reqContentType)) {
			requestHead.put("content-Type", "text/json;charset=utf-8");
		}
		String resContentType = tmp.getResContentType();
		if (StringUtils.isEmpty(resContentType)) {
			resContentType = "application/json";
		}
		String url = baseInfo.getIp() + tmp.getPath_();
		Map<String, String> queryParams = new HashMap<String, String>();
		// 获取URL请求参数模板
		String queryParamTmp = tmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			queryParams = createMapByParamsJson(paramsJson, queryParamTmp);
		}
		JSONObject requestBody = new JSONObject();
		// 获取请求体模板
		String reqBodyTmp = tmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			requestBody = createJSONByParamsJson(paramsJson, reqBodyTmp);
		}
		try {
			// 获取请求方式
			String reqType = tmp.getReqType();
			if (reqType.equals("POST")) {
				returnJSON = HttpClientUtil.postRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			} else if (reqType.equals("GET")) {
				returnJSON = HttpClientUtil.getRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("DELETE")) {
				returnJSON = HttpClientUtil.deleteRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("PUT")) {
				returnJSON = HttpClientUtil.putRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			}
			if (returnJSON == null) {
				return null;
			}
			returnJSON = returnJSON.getJSONObject("return");
			if (returnJSON != null && returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				logger.info("获取用户信息成功！" + returnJSON.toJSONString("UTF-8"));
				return returnJSON;
			} else {
				logger.error("获取用户信息失败！" + returnJSON.toJSONString("UTF-8"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return null;
	}
	@Override
	public JSONObject callService(String tmpId, String baseId, JSONObject paramsJson) {
		// TODO Auto-generated method stub
		JSONObject returnJSON = new JSONObject();
		// 获取蓝信应用实例信息
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		// 获取服务模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(tmpId);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		JSONObject accesstokenJson = getAccessTokenByBaseId(baseId);
		String access_token = null;
		if (accesstokenJson != null) {
			access_token = accesstokenJson.getString("access_token");
		}
		if (StringUtils.isEmpty(access_token)) {
			return null;
		}
		paramsJson.put("access_token", access_token);
		Map<String, String> requestHead = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			requestHead = createMapByParamsJson(paramsJson, reqHeaderTmp);
		}
		String reqContentType = tmp.getReqContentType();
		if (StringUtils.isNotEmpty(reqContentType)) {
			requestHead.put("content-Type", "application/json;charset=utf-8");
		}
		String resContentType = tmp.getResContentType();
		if (StringUtils.isEmpty(resContentType)) {
			resContentType = "application/json";
		}
		String url = baseInfo.getIp() + tmp.getPath_();
		Map<String, String> queryParams = new HashMap<String, String>();
		// 获取URL请求参数模板
		String queryParamTmp = tmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			queryParams = createMapByParamsJson(paramsJson, queryParamTmp);
		}
		JSONObject requestBody = new JSONObject();
		// 获取请求体模板
		String reqBodyTmp = tmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			requestBody = createJSONByParamsJson(paramsJson, reqBodyTmp);
		}
		try {
			// 获取请求方式
			String reqType = tmp.getReqType();
			if (reqType.equals("POST")) {
				returnJSON = HttpClientUtil.postRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			} else if (reqType.equals("GET")) {
				returnJSON = HttpClientUtil.getRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("DELETE")) {
				returnJSON = HttpClientUtil.deleteRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("PUT")) {
				returnJSON = HttpClientUtil.putRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return returnJSON;
	}
	/**
	 * 计算当前请求的jsapi的签名数据<br/>
	 * <p>
	 * 如果签名数据是通过ajax异步请求的话，签名计算中的url必须是给用户展示页面的url
	 *
	 * @param request
	 * @return
	 */
	public String getConfig(String baseId, String urlString, String queryString) {
		String queryStringEncode = null;
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		String url = null;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = url;
		String ticket = null;
		String signature = null;
		String agentid = null;
		JSONObject ticketJson = getJsapiTicket(baseInfo);
		if(ticketJson.containsKey("ticket")){
		   ticket = ticketJson.getString("ticket");
		}
		try {
			signature = sign(ticket, nonceStr, timeStamp, signedUrl);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		agentid = "";
		String configValue = "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr
				+ "',timeStamp:'" + timeStamp + "',corpId:'" + baseInfo.getPaasId() + "',agentid:'" + agentid + "'}";
		return configValue;
	}

	public String sign(String ticket, String nonceStr, long timeStamp, String url)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return byteToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
	}

	private String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
