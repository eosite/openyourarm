package com.glaf.dep.base.web.rest;

import java.io.IOException;
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

import com.glaf.dep.base.domain.DepBaseCompProp;
import com.glaf.dep.base.query.DepBaseCompPropQuery;
import com.glaf.dep.base.service.DepBaseCompPropService;
import com.glaf.dep.base.util.*;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/dep/base/depBaseCompProp")
public class DepBaseCompPropResource {
	protected static final Log logger = LogFactory
			.getLog(DepBaseCompPropResource.class);

	protected DepBaseCompPropService depBaseCompPropService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("Ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				//depBaseCompPropService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		depBaseCompPropService.deleteByRuleId(request.getParameter("ruleId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCompPropQuery query = new DepBaseCompPropQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DepBaseCompPropDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
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
		int total = depBaseCompPropService
				.getDepBaseCompPropCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
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
			List<DepBaseCompProp> list = depBaseCompPropService
					.getDepBaseCompPropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseCompProp depBaseCompProp : list) {
					JSONObject rowJSON = depBaseCompProp.toJsonObject();
					rowJSON.put("id", depBaseCompProp.getDepBaseComponentId());
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
		DepBaseCompPropQuery query = new DepBaseCompPropQuery();
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
		int total = depBaseCompPropService
				.getDepBaseCompPropCountByQueryCriteria(query);
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
			List<DepBaseCompProp> list = depBaseCompPropService
					.getDepBaseCompPropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseCompProp depBaseCompProp : list) {
					JSONObject rowJSON = depBaseCompProp.toJsonObject();
					rowJSON.put("id", depBaseCompProp.getDepBaseComponentId());
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
	@Path("/saveDepBaseCompProp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBaseCompProp(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCompProp depBaseCompProp = new DepBaseCompProp();
		try {
			Tools.populate(depBaseCompProp, params);

			depBaseCompProp.setRuleId(request.getParameter("ruleId"));

			this.depBaseCompPropService.save(depBaseCompProp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseCompPropService")
	public void setDepBaseCompPropService(
			DepBaseCompPropService depBaseCompPropService) {
		this.depBaseCompPropService = depBaseCompPropService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBaseCompProp depBaseCompProp = null;
		if (StringUtils.isNotEmpty(request.getParameter("Id"))) {
			depBaseCompProp = depBaseCompPropService.getDepBaseCompProp(request
					.getParameter("Id"));
		}
		JSONObject result = new JSONObject();
		if (depBaseCompProp != null) {
			result = depBaseCompProp.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", depBaseCompProp.getDepBaseComponentId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/insert")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] insert(@Context HttpServletRequest request) throws IOException {
		try {
			String componentId = request.getParameter("componentId");
			String ruleId = request.getParameter("ruleId");
			
			if(StringUtils.isEmpty(componentId) || StringUtils.isEmpty(ruleId)){
				return ResponseUtils.responseJsonResult(false);
			}
			
			DepBaseCompProp depBaseCompProp = new DepBaseCompProp();
			depBaseCompProp.setDepBaseComponentId(componentId);
			depBaseCompProp.setRuleId(ruleId);
			depBaseCompPropService.save(depBaseCompProp);
			
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}
	
	@GET
	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] delete(@Context HttpServletRequest request) throws IOException {
		try {
			String componentId = request.getParameter("componentId");
			String ruleId = request.getParameter("ruleId");
			
			if(StringUtils.isEmpty(componentId) || StringUtils.isEmpty(ruleId)){
				return ResponseUtils.responseJsonResult(false);
			}
			
			depBaseCompPropService.deleteByPrimaryKey(componentId,ruleId);
			
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}
}
