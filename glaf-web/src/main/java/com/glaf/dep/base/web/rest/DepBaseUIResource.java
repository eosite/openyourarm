package com.glaf.dep.base.web.rest;

import java.io.IOException;
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
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.dep.base.domain.DepBaseUI;
import com.glaf.dep.base.query.DepBaseUIQuery;
import com.glaf.dep.base.service.DepBaseUIService;
import com.glaf.dep.base.util.DepBaseUIDomainFactory;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/dep/base/depBaseUI")
public class DepBaseUIResource {
	protected static final Log logger = LogFactory
			.getLog(DepBaseUIResource.class);

	protected DepBaseUIService depBaseUIService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				depBaseUIService.deleteByIds(ids);
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
		depBaseUIService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseUIQuery query = new DepBaseUIQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DepBaseUIDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseUIService.getDepBaseUICountByQueryCriteria(query);
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
			List<DepBaseUI> list = depBaseUIService
					.getDepBaseUIsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseUI depBaseUI : list) {
					JSONObject rowJSON = depBaseUI.toJsonObject();
					rowJSON.put("id", depBaseUI.getId());
					rowJSON.put("depBaseUIId", depBaseUI.getId());
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
		DepBaseUIQuery query = new DepBaseUIQuery();
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
		int total = depBaseUIService.getDepBaseUICountByQueryCriteria(query);
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
			List<DepBaseUI> list = depBaseUIService
					.getDepBaseUIsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseUI depBaseUI : list) {
					JSONObject rowJSON = depBaseUI.toJsonObject();
					rowJSON.put("id", depBaseUI.getId());
					rowJSON.put("depBaseUIId", depBaseUI.getId());
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
	@Path("/saveDepBaseUI")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBaseUI(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseUI depBaseUI = new DepBaseUI();
		try {
			Tools.populate(depBaseUI, params);

			depBaseUI.setCode(request.getParameter("code"));
			depBaseUI.setName(request.getParameter("name"));
			depBaseUI.setType(request.getParameter("type"));
			depBaseUI.setCreator(request.getParameter("creator"));
			depBaseUI.setCreateDateTime(RequestUtils.getDate(request,
					"createDateTime"));
			depBaseUI.setModifier(request.getParameter("modifier"));
			depBaseUI.setModifyDateTime(RequestUtils.getDate(request,
					"modifyDateTime"));
			depBaseUI.setDelFlag("0");

			this.depBaseUIService.save(depBaseUI);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseUIService")
	public void setDepBaseUIService(DepBaseUIService depBaseUIService) {
		this.depBaseUIService = depBaseUIService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBaseUI depBaseUI = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			depBaseUI = depBaseUIService.getDepBaseUI(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (depBaseUI != null) {
			result = depBaseUI.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", depBaseUI.getId());
			result.put("depBaseUIId", depBaseUI.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/updateDelFlag")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] updateDelFlag(@Context HttpServletRequest request)
			throws IOException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		String delFlag = request.getParameter("delFlag");
		if (StringUtils.isEmpty(delFlag)) {
			delFlag = "0";
		}
		String id = request.getParameter("id");
		DepBaseUI model = depBaseUIService.getDepBaseUI(id);
		model.setDelFlag(delFlag);
		model.setModifier(actorId);
		model.setModifyDateTime(new Date());
		depBaseUIService.save(model);
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] json(@Context HttpServletRequest request) throws IOException {

		DepBaseUIQuery query = new DepBaseUIQuery();
		query.setDelFlag("0");
		JSONArray rowsJSON = new JSONArray();
		int total = depBaseUIService.getDepBaseUICountByQueryCriteria(query);
		if (total > 0) {

			List<DepBaseUI> list = depBaseUIService.list(query);

			if (list != null && !list.isEmpty()) {

				for (DepBaseUI depBaseUI : list) {
					JSONObject rowJSON = depBaseUI.toJsonObject();
					rowJSON.put("id", depBaseUI.getId());
					rowJSON.put("depBaseUIId", depBaseUI.getId());
					rowsJSON.add(rowJSON);
				}

			}
		}
		return rowsJSON.toJSONString().getBytes("UTF-8");
	}
}
