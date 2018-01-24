package com.glaf.dep.base.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.dep.base.domain.DepBasePropRel;
import com.glaf.dep.base.query.DepBasePropRelQuery;
import com.glaf.dep.base.service.DepBasePropRelService;
import com.glaf.dep.base.util.DepBasePropRelDomainFactory;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/dep/base/depBasePropRel")
public class DepBasePropRelResource {
	protected static final Log logger = LogFactory
			.getLog(DepBasePropRelResource.class);

	protected DepBasePropRelService depBasePropRelService;

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteByRuleId(@Context HttpServletRequest request)
			throws IOException {
		depBasePropRelService.deleteByRuleId(request.getParameter("ruleId"));
		return ResponseUtils.responseJsonResult(true);
	}
	
	@POST
	@Path("/deleteByPrimaryKey")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteByPrimaryKey(@Context HttpServletRequest request)
			throws IOException {
		try {
			String ruleId = request.getParameter("ruleId");
			String relRuleId = request.getParameter("relRuleId");
			depBasePropRelService.deleteByPrimaryKey(ruleId, relRuleId);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropRelQuery query = new DepBasePropRelQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DepBasePropRelDomainFactory.processDataRequest(dataRequest);

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
		int total = depBasePropRelService
				.getDepBasePropRelCountByQueryCriteria(query);
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
			List<DepBasePropRel> list = depBasePropRelService
					.getDepBasePropRelsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBasePropRel depBasePropRel : list) {
					JSONObject rowJSON = depBasePropRel.toJsonObject();
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
	@Path("/dataByRuleId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] dataByRuleId(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		String ruleId = request.getParameter("ruleId");
		DepBasePropRelQuery query = new DepBasePropRelQuery();
		query.setRuleId(ruleId);
		query.setDataRequest(dataRequest);
		DepBasePropRelDomainFactory.processDataRequest(dataRequest);

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
		int total = depBasePropRelService
				.getDepBasePropRelCountByQueryCriteria(query);
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

			List<DepBasePropRel> list = depBasePropRelService
					.getDepBasePropRelsByRuleId(ruleId, start, limit);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBasePropRel depBasePropRel : list) {
					JSONObject rowJSON = depBasePropRel.toJsonObject();
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
		DepBasePropRelQuery query = new DepBasePropRelQuery();
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
		int total = depBasePropRelService
				.getDepBasePropRelCountByQueryCriteria(query);
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
			List<DepBasePropRel> list = depBasePropRelService
					.getDepBasePropRelsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBasePropRel depBasePropRel : list) {
					JSONObject rowJSON = depBasePropRel.toJsonObject();
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
	@Path("/saveDepBasePropRel")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBasePropRel(@Context HttpServletRequest request) {
		try {
			String ruleId = request.getParameter("ruleId");
			
			List<DepBasePropRel> saveList = new ArrayList<DepBasePropRel>();
			String models = request.getParameter("models");
			JSONArray datas = JSONArray.parseArray(models);
			for (int i = 0; i < datas.size(); i++) {
				JSONObject jobject = datas.getJSONObject(i);
				String relRuleId = jobject.getString("relRuleId");
				String relType = jobject.getString("relType");
				
				DepBasePropRel depBasePropRel = new DepBasePropRel();
				depBasePropRel.setRuleId(ruleId);
				depBasePropRel.setRelRuleId(relRuleId);
				depBasePropRel.setRelType(relType);
				depBasePropRel.setCreator(RequestUtils.getLoginContext(request)
						.getActorId());
				depBasePropRel.setCreateDateTime(new Date());
				saveList.add(depBasePropRel);
			}
			this.depBasePropRelService.save(ruleId,saveList);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBasePropRelService")
	public void setDepBasePropRelService(
			DepBasePropRelService depBasePropRelService) {
		this.depBasePropRelService = depBasePropRelService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBasePropRel depBasePropRel = null;
		if (StringUtils.isNotEmpty(request.getParameter("ruleId"))) {
			depBasePropRel = depBasePropRelService.getDepBasePropRel(request
					.getParameter("ruleId"));
		}
		JSONObject result = new JSONObject();
		if (depBasePropRel != null) {
			result = depBasePropRel.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
