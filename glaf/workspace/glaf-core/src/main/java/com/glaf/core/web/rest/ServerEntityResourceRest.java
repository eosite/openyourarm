/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.query.ServerEntityQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;

/**
 * 
 * Rest响应类
 * 
 */

@Controller("/rs/sys/server")
@Path("/rs/sys/server")
public class ServerEntityResourceRest {
	protected static final Log logger = LogFactory.getLog(ServerEntityResourceRest.class);

	protected IServerEntityService serverEntityService;

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		long serverEntityId = RequestUtils.getLong(request, "id");
		if (serverEntityId > 0) {
			serverEntityService.deleteById(serverEntityId);
			// sqlResultService.deleteByServerEntityId(serverEntityId);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ServerEntityQuery query = new ServerEntityQuery();
		Tools.populate(query, params);

		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = serverEntityService.getServerEntityCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<ServerEntity> list = serverEntityService.getServerEntitiesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ServerEntity m : list) {
					JSONObject rowJSON = m.toJsonObject();
					rowJSON.put("id", m.getId());
					rowJSON.put("rowId", m.getId());
					rowJSON.put("serverId", m.getId());
					rowJSON.put("startIndex", ++start);
					rowJSON.remove("key");
					rowJSON.remove("user");
					rowJSON.remove("password");
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveServer")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveServer(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		ServerEntity serverEntity = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			serverEntity = serverEntityService.getServerEntityById(RequestUtils.getLong(request, "id"));
		}
		if (serverEntity == null) {
			serverEntity = new ServerEntity();
			Tools.populate(serverEntity, RequestUtils.getParameterMap(request));
			serverEntity.setCreateBy(loginContext.getActorId());
		}

		String user = request.getParameter("user");
		String password = request.getParameter("password");
		serverEntity.setUser(user);
		serverEntity.setPassword(password);
		serverEntity.setTitle(request.getParameter("title"));
		serverEntity.setCode(request.getParameter("code"));
		serverEntity.setMapping(request.getParameter("mapping"));
		serverEntity.setNodeId(RequestUtils.getLong(request, "nodeId"));
		serverEntity.setHost(request.getParameter("host"));
		serverEntity.setPort(RequestUtils.getInt(request, "port"));
		serverEntity.setName(request.getParameter("name"));
		serverEntity.setType(request.getParameter("type"));
		serverEntity.setLevel(RequestUtils.getInt(request, "level"));
		serverEntity.setPriority(RequestUtils.getInt(request, "priority"));
		serverEntity.setOperation(RequestUtils.getInt(request, "operation"));
		serverEntity.setDbname(request.getParameter("dbname"));
		serverEntity.setPath(request.getParameter("path"));
		serverEntity.setProgram(request.getParameter("program"));
		serverEntity.setCatalog(request.getParameter("catalog"));
		serverEntity.setProviderClass(request.getParameter("providerClass"));
		serverEntity.setAddressPerms(request.getParameter("addressPerms"));
		serverEntity.setPerms(request.getParameter("perms"));
		serverEntity.setAttribute(request.getParameter("attribute"));
		serverEntity.setActive(request.getParameter("active"));
		serverEntity.setDetectionFlag(request.getParameter("detectionFlag"));
		serverEntity.setSecretAlgorithm(request.getParameter("secretAlgorithm"));
        serverEntity.setSecretKey(request.getParameter("secretKey"));
        serverEntity.setSecretIv(request.getParameter("secretIv"));
		serverEntity.setUpdateBy(loginContext.getActorId());
		if (StringUtils.isEmpty(serverEntity.getType())) {
			serverEntity.setType("sqlserver");
		}
		try {
			this.serverEntityService.save(serverEntity);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ServerEntity serverEntity = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			serverEntity = serverEntityService.getServerEntityById(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (serverEntity != null) {
			result = serverEntity.toJsonObject();
			result.put("id", serverEntity.getId());
			result.put("serverId", serverEntity.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
