package com.glaf.lanxin.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
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

@Service("com.glaf.lanxin.service.lanxinService")
@Transactional(readOnly = true)
public class LanxinServiceImpl implements LanxinService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected EimBaseInfoMapper eimBaseInfoMapper;
	protected EimServerTmpMapper eimServerTmpMapper;

	@Override
	public JSONObject getAccessTokenByBaseId(String baseId) {
		// TODO Auto-generated method stub
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		BaseDataManager manager = BaseDataManager.getInstance();
		// 获取授权码服务模板ID
		BaseDataInfo baseDataInfo = manager.getBaseData("codeTmp", "lanxin");
		String codeTmp = baseDataInfo.getValue();
		// 获取访问令牌服务模板ID;
		baseDataInfo = manager.getBaseData("accessTokenTmp", "lanxin");
		String accessTokenTmp = baseDataInfo.getValue();
		// 从缓存中获取AccessToken
		String accessToken = CacheFactory.getString("lanxin", "accessToken");
		if (StringUtils.isNotEmpty(accessToken)) {
			JSONObject accessTokenJson = JSONObject.parseObject(accessToken);
			// 获取token过期时间
			long invalidTime = accessTokenJson.getLongValue("invalidTime");
			long currentTime = new Date().getTime();
			if (currentTime > invalidTime) {
				// 获取refresh_token的失效时间
				long freshTokenInvalidTime = accessTokenJson.getLongValue("freshTokenInvalidTime");
				if (currentTime > freshTokenInvalidTime) {
					return createAccessToken(baseInfo, codeTmp, accessTokenTmp);
				} else {
					// 获取刷新访问令牌服务模板ID;
					baseDataInfo = manager.getBaseData("freshAccessTokenTmp", "lanxin");
					String freshAccessTokenTmp = baseDataInfo.getValue();
					// 获取刷新令牌
					String refresh_token = accessTokenJson.getString("refresh_token");
					String code = accessTokenJson.getString("code");
					JSONObject returnJson=refreshAccessToken(baseInfo, freshAccessTokenTmp,accessTokenJson);
					return returnJson;
				}
			} else {
				return accessTokenJson;
			}
		} else {
			return createAccessToken(baseInfo, codeTmp, accessTokenTmp);
		}
	}

	@Override
	public JSONObject getAccessTokenByBaseIdAndCode(String baseId, String code, JSONObject accessTokenJson) {
		// TODO Auto-generated method stub
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		BaseDataManager manager = BaseDataManager.getInstance();
		// 获取授权码服务模板ID
		BaseDataInfo baseDataInfo = manager.getBaseData("codeTmp", "lanxin");
		String codeTmp = baseDataInfo.getValue();
		// 获取访问令牌服务模板ID;
		baseDataInfo = manager.getBaseData("accessTokenTmp", "lanxin");
		String accessTokenTmp = baseDataInfo.getValue();
		if (accessTokenJson != null) {
			// 获取token过期时间
			long invalidTime = accessTokenJson.getLongValue("invalidTime");
			long currentTime = new Date().getTime();
			if (currentTime > invalidTime) {
				// 获取refresh_token的失效时间
				long freshTokenInvalidTime =accessTokenJson.getLongValue("freshTokenInvalidTime");
				//invalidTime - 7200 * 1000 + 30 * 24 * 60 * 60 * 1000;
				if (currentTime > freshTokenInvalidTime) {
					return createAccessTokenByCode(baseInfo, code, accessTokenTmp);
				} else {
					// 获取刷新访问令牌服务模板ID;
					baseDataInfo = manager.getBaseData("freshAccessTokenTmp", "lanxin");
					String freshAccessTokenTmp = baseDataInfo.getValue();
					return refreshAccessToken(baseInfo, freshAccessTokenTmp,accessTokenJson);
				}
			} else {
				return accessTokenJson;
			}
		} else {
			return createAccessTokenByCode(baseInfo, code, accessTokenTmp);
		}
	}

	/**
	 * 生成访问令牌
	 * 
	 * @param baseInfo
	 * @param codeTmp
	 */
	private JSONObject createAccessToken(EimBaseInfo baseInfo, String codeTmp, String accessTokenTmp) {
		// TODO Auto-generated method stub
		// 从缓存中获取Authorization Code
		String code = CacheFactory.getString("lanxin", "code");
		String authorizationCode = null;
		if (StringUtils.isNotEmpty(code)) {
			JSONObject codeJson = JSONObject.parseObject(code);
			// 获取code的失效时间
			long invalidTime = codeJson.getLongValue("invalidTime");
			long currentTime = new Date().getTime();
			if (currentTime > invalidTime) {
				// 重新产生code
				authorizationCode = createAuthorizationCode(baseInfo, codeTmp);
			} else {
				authorizationCode = codeJson.getString("code");
			}
		} else {
			// 重新产生code
			authorizationCode = createAuthorizationCode(baseInfo, codeTmp);
		}
		if (StringUtils.isEmpty(authorizationCode)) {
			return null;
		}
		// 生成访问令牌
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(accessTokenTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		String appId = baseInfo.getPaasId();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("appid", appId);
		paramsJson.put("code", authorizationCode);
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
		queryParams.put("code", authorizationCode);
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
			if(returnJSON==null){
				return null;
			}
			returnJSON=returnJSON.getJSONObject("return");
			if (returnJSON!=null&&returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				logger.info("获取访问令牌成功！" + returnJSON.toJSONString());
				returnJSON.put("code", authorizationCode);
				// 记录数据到缓存
				Date curr=new Date();
				long invalidTime = curr.getTime() + 7200 * 1000;
				long freshAccessTokenTmp=curr.getTime()+30*24*60*60*1000;
				returnJSON.put("invalidTime", invalidTime);
				returnJSON.put("freshAccessTokenTmp", freshAccessTokenTmp);
				CacheFactory.put("lanxin", "accessToken", returnJSON.toJSONString());
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
	private JSONObject createAccessTokenByCode(EimBaseInfo baseInfo, String code, String accessTokenTmp) {
		// 生成访问令牌
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(accessTokenTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		String appId = baseInfo.getPaasId();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("appid", appId);
		paramsJson.put("code", code);
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
		queryParams.put("code", code);
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
			if(returnJSON==null)
			{
				return null;
			}
			returnJSON=returnJSON.getJSONObject("return");
			if (returnJSON!=null&&returnJSON.getInteger("errcode")!=null&&returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				returnJSON.put("code", code);
				// 记录数据到缓存
				Date curr=new Date();
				long invalidTime = curr.getTime() + 7200 * 1000;
				long freshAccessTokenTmp=curr.getTime()+30*24*60*60*1000;
				returnJSON.put("invalidTime", invalidTime);
				returnJSON.put("freshAccessTokenTmp", freshAccessTokenTmp);
				CacheFactory.put("lanxin", "accessToken", returnJSON.toJSONString());
				logger.info("获取访问令牌成功！" + returnJSON.toJSONString());
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
	 * 生成授权码
	 * 
	 * @param baseInfo
	 * @param codeTmp
	 * @return
	 */
	public String createAuthorizationCode(EimBaseInfo baseInfo, String codeTmp) {
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(codeTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		String appId = baseInfo.getPaasId();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("appid", appId);
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
			if(returnJSON==null){
				return null;
			}
			returnJSON=returnJSON.getJSONObject("return");
			if (returnJSON != null && returnJSON.getInteger("errcode") != null
					&& returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				logger.info("重新获取授权码成功！" + returnJSON.toJSONString());
				// 记录数据到缓存
				
				long invalidTime = new Date().getTime() + 5 * 60 * 1000;
				returnJSON.put("invalidTime", invalidTime);
				CacheFactory.put("lanxin", "code", returnJSON.toJSONString());
				return returnJSON.getString("code");
			} else {
				logger.error("重新获取授权码失败！" + returnJSON.toJSONString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 刷新访问令牌
	 * 
	 * @param baseInfo
	 * @param codeTmp
	 * @param refresh_token
	 * @return
	 */
	private JSONObject refreshAccessToken(EimBaseInfo baseInfo, String codeTmp,JSONObject accessTokenJson) {
		String refresh_token = accessTokenJson.getString("refresh_token");
		String code = accessTokenJson.getString("code");
		JSONObject returnJSON = new JSONObject();
		// 获取模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(codeTmp);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		String appId = baseInfo.getPaasId();
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("appid", appId);
		paramsJson.put("refresh_token", refresh_token);
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
		queryParams.put("code", code);
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
			if(returnJSON==null){
				return null;
			}
			returnJSON=returnJSON.getJSONObject("return");
			if (returnJSON!=null&&returnJSON.getInteger("errcode")!=null&&returnJSON.getInteger("errcode") == 0) {
				// 返回授权码
				logger.info("刷新访问令牌成功！" + returnJSON.toJSONString());
				// 记录数据到缓存
				long invalidTime = new Date().getTime() + 7200* 1000;
				returnJSON.put("invalidTime", invalidTime);
				returnJSON.put("code", code);
				returnJSON.put("freshAccessTokenTmp", accessTokenJson.getLongValue("freshAccessTokenTmp"));
				CacheFactory.put("lanxin", "accessToken", returnJSON.toJSONString());
				return returnJSON;
			} else {
				logger.error("刷新访问令牌失败！" + returnJSON.toJSONString());
				// 重新生成AccessToken
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return returnJSON;
	}
	@Override
	public JSONObject callService(String tmpId, String baseId,String code,JSONObject accessTokenJson,  JSONObject paramsJson) {
		// TODO Auto-generated method stub
		JSONObject returnJSON = new JSONObject();
		// 获取蓝信应用实例信息
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		// 获取服务模板对象
		EimServerTmp tmp = eimServerTmpMapper.getEimServerTmpById(tmpId);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		JSONObject accesstokenJson = getAccessTokenByBaseIdAndCode(baseId,code,accessTokenJson);
		//记录到缓存
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return returnJSON;
	}
	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimBaseInfoMapper")
	public void setEimBaseInfoMapper(EimBaseInfoMapper eimBaseInfoMapper) {
		this.eimBaseInfoMapper = eimBaseInfoMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerTmpMapper")
	public void setEimServerTmpMapper(EimServerTmpMapper eimServerTmpMapper) {
		this.eimServerTmpMapper = eimServerTmpMapper;
	}

}
