package com.glaf.lanxin.service;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Transactional(readOnly = true)
public interface LanxinService {
	/**
	 * 获取访问token
	 * 
	 * @param baseId
	 *            蓝信应用基础信息
	 * @return
	 */
	JSONObject getAccessTokenByBaseId(String baseId);

	/**
	 * 蓝信接口调用统一入口服务方法
	 * 
	 * @param tmpId
	 *            服务模板ID
	 * @param baseId
	 *            蓝信应用基础信息
	 * @param paramsJson
	 *            参数
	 * @return
	 */
	JSONObject callService(String tmpId, String baseId, JSONObject paramsJson);

	/**
	 * 获取某个用户的访问令牌
	 * @param baseId 应用ID
	 * @param code 授权代码
	 * @param accessTokenJson sessin中的访问令牌
	 * @return
	 */
	JSONObject getAccessTokenByBaseIdAndCode(String baseId, String code, JSONObject accessTokenJson);
    /**
     * 某个用户访问服务
     * @param tmpId
     * @param baseId 应用ID
     * @param code 授权代码
     * @param accessTokenJson 历史访问令牌
     * @param paramsJson 参数
     * @return
     */
	JSONObject callService(String tmpId, String baseId, String code,JSONObject accessTokenJson,JSONObject paramsJson);
}
