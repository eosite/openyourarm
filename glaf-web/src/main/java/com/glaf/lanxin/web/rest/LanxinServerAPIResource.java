package com.glaf.lanxin.web.rest;

import java.io.IOException;

import javax.annotation.Resource;
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
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.util.ResponseUtils;
import com.glaf.lanxin.service.LanxinService;
import com.glaf.lanxin.service.LanxinSyncService;

@RestController
@RequestMapping("/lanxin/api")
public class LanxinServerAPIResource {
	protected static final Log logger = LogFactory.getLog(LanxinServerAPIResource.class);
	private LanxinService lanxinService;
	private LanxinSyncService lanxinSyncService;
    /**
     * 调用外部服务接口
     * @param request
     * @param tmpId 应用实例ID
     * @param baseId 服务模板ID
     * @return
     * @throws IOException
     */
	@RequestMapping("/{tmpId}/{baseId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] callHttpRequest(@Context HttpServletRequest request, @PathVariable String tmpId,
			@PathVariable String baseId) throws IOException {
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
		//获取访问令牌
		JSONObject accessTokenJson=null;
		String code=request.getParameter("code");
		//缓存获取accessToken
		String accessToken=CacheFactory.getString("lanxin", "accessToken");
		if(StringUtils.isNotEmpty(code)||StringUtils.isNotEmpty(accessToken)){
			if(StringUtils.isNotEmpty(accessToken)){
				accessTokenJson=JSONObject.parseObject(accessToken);
				String cacheCode=accessTokenJson.getString("code");
				if(!cacheCode.equals(code)){
					accessTokenJson=null;
				}
			}
			paramsJson.put("code", code);
			returnJson = lanxinService.callService(tmpId, baseId,code,accessTokenJson,paramsJson);
			if(returnJson==null){
				returnJson = new JSONObject();
			}
		}
		return returnJson.toJSONString().getBytes();
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
			lanxinSyncService.sync(baseId, tmpId, null, tablename, null, paramsJson);
			returnJson.put("success", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnJson.put("success", 0);
			returnJson.put("errmsg", e.getMessage());
			e.printStackTrace();
		}
		return returnJson.toJSONString().getBytes();
	}

	@Resource(name = "com.glaf.lanxin.service.lanxinService")
	public void setLanxinService(LanxinService lanxinService) {
		this.lanxinService = lanxinService;
	}
	@Resource(name = "com.glaf.lanxin.service.lanxinSyncService")
	public void setLanxinSyncService(LanxinSyncService lanxinSyncService) {
		this.lanxinSyncService = lanxinSyncService;
	}

}
