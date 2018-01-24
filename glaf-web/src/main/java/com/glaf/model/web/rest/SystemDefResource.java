package com.glaf.model.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.model.domain.SystemDef;
import com.glaf.model.query.SystemDefQuery;
import com.glaf.model.service.SystemDefService;
import com.glaf.model.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/modeling/systemDef")
public class SystemDefResource {
	protected static final Log logger = LogFactory.getLog(SystemDefResource.class);

	protected SystemDefService systemDefService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("sysIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				systemDefService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		systemDefService.deleteById(RequestUtils.getString(request, "sysId"));
		return ResponseUtils.responseJsonResult(true);
	}
	@POST
	@Path("/deleteSystem")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteSystem(@Context HttpServletRequest request) throws IOException {
		systemDefService.deleteSystem(RequestUtils.getString(request, "sysId"));
		return ResponseUtils.responseJsonResult(true);
	}
	@GET
	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDefQuery query = new SystemDefQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SystemDefDomainFactory.processDataRequest(dataRequest);
		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
		}
		query.setDeleteFlag(0);
		HashMap<String,Object> paramMap=dataRequest.getData();
		String sysName=paramMap.get("sysName")!=null?(String)paramMap.get("sysName"):null;
		if(StringUtils.isNotEmpty(sysName))
		{
			query.setSysNameLike(sysName);
		}
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = systemDefService.getSystemDefCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SystemDef> list = systemDefService.getSystemDefsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SystemDef systemDef : list) {
					JSONObject rowJSON = systemDef.toJsonObject();
					rowJSON.put("id", systemDef.getSysId());
					rowJSON.put("systemDefId", systemDef.getSysId());
					rowJSON.put("startIndex", ++start);
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDefQuery query = new SystemDefQuery();
		Tools.populate(query, params);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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
		int total = systemDefService.getSystemDefCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SystemDef> list = systemDefService.getSystemDefsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SystemDef systemDef : list) {
					JSONObject rowJSON = systemDef.toJsonObject();
					rowJSON.put("id", systemDef.getSysId());
					rowJSON.put("systemDefId", systemDef.getSysId());
					rowJSON.put("startIndex", ++start);
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
	@Path("/saveSystemDef")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSystemDef(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject result = new JSONObject();
		try {

			SystemDef systemDef = null;
			if (StringUtils.isEmpty(request.getParameter("sysId"))) {
				systemDef = new SystemDef();
				systemDef.setCreateBy(actorId);
				systemDef.setUpdateBy(actorId);
			} else {
				systemDef = systemDefService.getSystemDef(request.getParameter("sysId"));
				systemDef.setUpdateBy(actorId);
			}
			Tools.populate(systemDef, params);
			systemDef.setSysName(request.getParameter("sysName"));
			systemDef.setSysCode(request.getParameter("sysCode"));
			systemDef.setSysDesc(request.getParameter("sysDesc"));
			systemDef.setDeleteFlag(0);
			this.systemDefService.save(systemDef);
			result.put("result", 1);
			result.put("sysId", systemDef.getSysId());
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.error(ex.getMessage());
			result.put("result", -1);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/publishSystemDef")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] publishSystemDef(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject result = new JSONObject();
		try {

			SystemDef systemDef = null;
			if (StringUtils.isNotEmpty(request.getParameter("sysId"))) {
				systemDef = systemDefService.getSystemDef(request.getParameter("sysId"));
				systemDef.setUpdateBy(actorId);
				systemDef.setPublisher(actorId);
				systemDef.setPublishTime(new Date());
				systemDef.setVersion("1.0");
				this.systemDefService.publish(systemDef);
				
				result.put("result", 1);
				result.put("sysId", systemDef.getSysId());
			}
			
		} catch (Exception ex) {
			//ex.printStackTrace();
			logger.error(ex.getMessage());
			result.put("result", -1);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	
	@javax.annotation.Resource(name = "com.glaf.model.service.systemDefService")
	public void setSystemDefService(SystemDefService systemDefService) {
		this.systemDefService = systemDefService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SystemDef systemDef = null;
		if (StringUtils.isNotEmpty(request.getParameter("sysId"))) {
			systemDef = systemDefService.getSystemDef(RequestUtils.getString(request, "sysId"));
		}
		JSONObject result = new JSONObject();
		if (systemDef != null) {
			result = systemDef.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", systemDef.getSysId());
			result.put("systemDefId", systemDef.getSysId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
