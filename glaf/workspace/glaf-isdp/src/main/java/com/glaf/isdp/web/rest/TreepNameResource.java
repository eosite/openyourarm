package com.glaf.isdp.web.rest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.query.TreepNameQuery;
import com.glaf.isdp.service.TreepNameService;
import com.glaf.isdp.util.TreepNameDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/treepName")
public class TreepNameResource {
	protected static final Log logger = LogFactory
			.getLog(TreepNameResource.class);

	protected TreepNameService treepNameService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("indexIds");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				treepNameService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		treepNameService.deleteById(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreepNameQuery query = new TreepNameQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TreepNameDomainFactory.processDataRequest(dataRequest);

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
		int total = treepNameService.getTreepNameCountByQueryCriteria(query);
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

			//Map<String, User> userMap = IdentityFactory.getUserMap();
			List<TreepName> list = treepNameService
					.getTreepNamesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreepName treepName : list) {
					JSONObject rowJSON = treepName.toJsonObject();
					rowJSON.put("id", treepName.getId());
					rowJSON.put("treepNameId", treepName.getId());
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
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreepNameQuery query = new TreepNameQuery();
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
		int total = treepNameService.getTreepNameCountByQueryCriteria(query);
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

			//Map<String, User> userMap = IdentityFactory.getUserMap();
			List<TreepName> list = treepNameService
					.getTreepNamesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreepName treepName : list) {
					JSONObject rowJSON = treepName.toJsonObject();
					rowJSON.put("id", treepName.getId());
					rowJSON.put("treepNameId", treepName.getId());
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
	@Path("/saveTreepName")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTreepName(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreepName treepName = new TreepName();
		try {
			Tools.populate(treepName, params);

			treepName.setId(request.getParameter("id"));
			treepName.setNum(request.getParameter("num"));
			treepName.setParentId(RequestUtils.getInt(request, "parentId"));
			treepName.setIndexName(request.getParameter("indexName"));
			treepName.setLevel(RequestUtils.getInt(request, "Level"));
			treepName.setShowId(RequestUtils.getInt(request, "showId"));
			treepName.setRuleId(request.getParameter("ruleId"));
			treepName.setNodeIco(RequestUtils.getInt(request, "nodeIco"));
			treepName.setFruleId(request.getParameter("fruleId"));
			treepName.setWcompany(request.getParameter("wcompany"));
			treepName.setListNo(RequestUtils.getInt(request, "listNo"));
			treepName.setSysId(request.getParameter("sysId"));
			treepName.setDomainIndex(RequestUtils
					.getInt(request, "domainIndex"));

			this.treepNameService.save(treepName);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.treepNameService")
	public void setTreepNameService(TreepNameService treepNameService) {
		this.treepNameService = treepNameService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TreepName treepName = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			treepName = treepNameService.getTreepName(RequestUtils.getInt(
					request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (treepName != null) {
			result = treepName.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", treepName.getId());
			result.put("treepNameId", treepName.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
