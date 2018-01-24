package com.glaf.dingtalk.service;

import com.alibaba.fastjson.JSONObject;


public interface DingTalkService {

	JSONObject getAccessTokenByBaseId(String baseId);

	JSONObject getUserInfo(String baseId, String code, String accessToken);

	public String getConfig(String baseId, String urlString, String queryString);
	
	public JSONObject callService(String tmpId, String baseId, JSONObject paramsJson);
}
