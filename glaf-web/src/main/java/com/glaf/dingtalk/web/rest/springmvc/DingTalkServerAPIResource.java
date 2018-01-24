package com.glaf.dingtalk.web.rest.springmvc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.ResponseUtils;
import com.glaf.dingtalk.service.DingTalkService;
import com.glaf.dingtalk.service.DingTalkSyncService;
import com.glaf.lanxin.web.rest.LanxinServerAPIResource;
@RestController
@RequestMapping("/dingtalk/api")
public class DingTalkServerAPIResource {
	protected static final Log logger = LogFactory.getLog(LanxinServerAPIResource.class);
	private DingTalkService dingTalkService;
	private DingTalkSyncService dingTalkSyncService;

	/**
	 * 调用外部服务接口
	 * 
	 * @param request
	 * @param tmpId
	 *            应用实例ID
	 * @param baseId
	 *            服务模板ID
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
		// 获取访问令牌
		JSONObject accessTokenJson = dingTalkService.getAccessTokenByBaseId(baseId);
		if (accessTokenJson != null && accessTokenJson.containsKey("accessTokenJson")) {
			paramsJson.put("access_token", accessTokenJson.getString("access_token"));
			returnJson = dingTalkService.callService(tmpId, baseId, paramsJson);
			if (returnJson == null) {
				returnJson = new JSONObject();
			}
		}
		return returnJson.toJSONString().getBytes();
	}

	/**
	 * 同步数据
	 * 
	 * @param request
	 * @param tmpId
	 *            应用实例ID
	 * @param baseId
	 *            服务模板ID
	 * @param tablename
	 *            目标表名（自己创建）
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/{tmpId}/{baseId}/sync/{tablename}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] syncHttpRequest(@Context HttpServletRequest request, @PathVariable String tmpId,
			@PathVariable String baseId, @PathVariable String tablename) throws IOException {
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
			dingTalkSyncService.sync(baseId, tmpId, null, tablename, null, paramsJson,null);
			returnJson.put("success", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			returnJson.put("success", 0);
			returnJson.put("errmsg", e.getMessage());
			e.printStackTrace();
		}
		return returnJson.toJSONString().getBytes();
	}
	/**
	 * 同步部门用户数据
	 * 
	 * @param request
	 * @param tmpId
	 *            应用实例ID
	 * @param baseId
	 *            服务模板ID
	 * @param tablename
	 *            目标表名（自己创建）
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/{tmpId}/{baseId}/syncmember/{tablename}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] syncMemberHttpRequest(@Context HttpServletRequest request, @PathVariable String tmpId,
			@PathVariable String baseId, @PathVariable String tablename) throws IOException {
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
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("select id from DINGTALK_ORG order by parentid desc");
			long departId = 0;
			String[] extColumnStr=null;
			while (rs.next()) {
				departId = rs.getLong(1);
				paramsJson.put("department_id", departId);
				//默认列值
				extColumnStr=new String[2];
				extColumnStr[0]="departmentId";
				extColumnStr[1]=""+departId;
				dingTalkSyncService.sync(baseId, tmpId, null, tablename, null, paramsJson,extColumnStr);
			}
			returnJson.put("success", 1);
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				returnJson.put("success", 0);
				returnJson.put("errmsg", e.getMessage());
				e.printStackTrace();
			}
		}
		return returnJson.toJSONString().getBytes();
	}

	@Resource(name = "com.glaf.dingtalk.service.dingTalkService")
	public void setDingTalkService(DingTalkService dingTalkService) {
		this.dingTalkService = dingTalkService;
	}

	@Resource(name = "com.glaf.dingtalk.service.dingTalkSyncService")
	public void setDingTalkSyncService(DingTalkSyncService dingTalkSyncService) {
		this.dingTalkSyncService = dingTalkSyncService;
	}

}
