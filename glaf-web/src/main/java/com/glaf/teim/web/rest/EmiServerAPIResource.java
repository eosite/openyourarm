package com.glaf.teim.web.rest;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.teim.service.EimServerSyncService;
import com.glaf.teim.service.EimServerTmpService;

@RestController
@RequestMapping("/teim/api")
public class EmiServerAPIResource {
	protected static final Log logger = LogFactory.getLog(EmiServerAPIResource.class);
	private EimServerTmpService eimServerTmpService;
	private EimServerSyncService eimServerSyncService;
    /**
     * 腾讯通服务调用接口
     * @param request
     * @param tmpId 模板ID
     * @param baseId 应用实例ID
     * @return
     * @throws IOException
     */
	@RequestMapping("/{tmpId}/{baseId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] callHttpRequest(@Context HttpServletRequest request,@PathVariable String tmpId,@PathVariable String baseId) throws IOException {
		JSONObject returnJson = new JSONObject();
		String params = request.getParameter("params");
		JSONObject paramsJson = null;
		if (StringUtils.isNotEmpty(params)) {
			paramsJson = JSONObject.parseObject(params);
		} else {
			paramsJson = new JSONObject();
		}
		//从cookie获取TGT
		Cookie[] cookies=request.getCookies();
		for(Cookie cookie:cookies) {
			paramsJson.put(cookie.getName(), cookie.getValue());
		}
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		paramsJson.put("actorId", loginContext.getActorId());
	/*	paramsJson.put("unitName", "测试部门");
		paramsJson.put("parentUnitId", 0);
		paramsJson.put("orderId", 0);
		paramsJson.put("virtual", 1);
		tmpId = "7c104a47d6d044779995620036c22a4c";
		baseId = "1";*/
		if (StringUtils.isEmpty(tmpId) || StringUtils.isEmpty(baseId)) {
			return ResponseUtils.responseJsonResult(false);
		}
		returnJson = eimServerTmpService.callService(tmpId, baseId, paramsJson);

		return returnJson.toJSONString().getBytes("UTF-8");
	}
	/**
	 * 同步数据
	 * @param request
	 * @param tmpId 应用实例ID
	 * @param baseId 服务模板ID
	 * @param tablename 目标表名（自己创建）
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/{tmpId}/{baseId}/sync/{tablename}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] syncHttpRequest(@Context HttpServletRequest request, @PathVariable String tmpId,
			@PathVariable String baseId,@PathVariable String tablename) throws IOException {
		JSONObject returnJson = new JSONObject();
		String params = request.getParameter("params");
		JSONObject paramsJson = null;
		if (StringUtils.isNotEmpty(params)) {
			paramsJson = JSONObject.parseObject(params);
		} else {
			paramsJson = new JSONObject();
		}
		if (StringUtils.isEmpty(tmpId) || StringUtils.isEmpty(baseId)) {
			return ResponseUtils.responseJsonResult(false);
		}
		try {
			eimServerSyncService.syncNoToken(baseId, tmpId, null, tablename, null, paramsJson,null);
			returnJson.put("success", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnJson.put("success", 0);
			returnJson.put("errmsg", "同步数据出错！");
			//e.printStackTrace();
		}
		return returnJson.toJSONString().getBytes();
	}
	@javax.annotation.Resource(name = "com.glaf.teim.service.eimServerTmpService")
	public void setEimServerTmpService(EimServerTmpService eimServerTmpService) {
		this.eimServerTmpService = eimServerTmpService;
	}
	@javax.annotation.Resource(name = "com.glaf.teim.service.eimServerSyncService")
	public void setEimServerSyncService(EimServerSyncService eimServerSyncService) {
		this.eimServerSyncService = eimServerSyncService;
	}
}
