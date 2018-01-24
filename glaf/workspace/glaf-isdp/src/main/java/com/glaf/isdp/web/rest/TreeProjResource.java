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
import com.glaf.isdp.domain.TreeProj;
import com.glaf.isdp.query.TreeProjQuery;
import com.glaf.isdp.service.TreeProjService;
import com.glaf.isdp.util.TreeProjDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/treeProj")
public class TreeProjResource {
	protected static final Log logger = LogFactory
			.getLog(TreeProjResource.class);

	protected TreeProjService treeProjService;

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
				treeProjService.deleteByIds(ids);
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
		treeProjService.deleteById(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeProjQuery query = new TreeProjQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TreeProjDomainFactory.processDataRequest(dataRequest);

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
		int total = treeProjService.getTreeProjCountByQueryCriteria(query);
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
			List<TreeProj> list = treeProjService.getTreeProjsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeProj treeProj : list) {
					JSONObject rowJSON = treeProj.toJsonObject();
					rowJSON.put("id", treeProj.getId());
					rowJSON.put("treeProjId", treeProj.getId());
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
		TreeProjQuery query = new TreeProjQuery();
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
		int total = treeProjService.getTreeProjCountByQueryCriteria(query);
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
			List<TreeProj> list = treeProjService.getTreeProjsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeProj treeProj : list) {
					JSONObject rowJSON = treeProj.toJsonObject();
					rowJSON.put("id", treeProj.getId());
					rowJSON.put("treeProjId", treeProj.getId());
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
	@Path("/saveTreeProj")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTreeProj(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeProj treeProj = new TreeProj();
		try {
			Tools.populate(treeProj, params);

			treeProj.setProjType(request.getParameter("projType"));
			treeProj.setId(request.getParameter("id"));
			treeProj.setTopId(RequestUtils.getInt(request, "topId"));
			treeProj.setParentId(RequestUtils.getInt(request, "parentId"));
			treeProj.setIndexName(request.getParameter("indexName"));
			treeProj.setLevel(RequestUtils.getInt(request, "Level"));
			treeProj.setNum(request.getParameter("num"));
			treeProj.setContent(request.getParameter("content"));
			treeProj.setUseId(request.getParameter("useId"));
			treeProj.setSindexName(request.getParameter("sindexName"));
			treeProj.setContent2(request.getParameter("content2"));
			treeProj.setTopNode(request.getParameter("topNode"));
			treeProj.setNodeIco(RequestUtils.getInt(request, "nodeIco"));
			treeProj.setUnitNum(request.getParameter("unitNum"));
			treeProj.setShowId(RequestUtils.getInt(request, "showId"));
			treeProj.setScaleQ(RequestUtils.getDouble(request, "scaleQ"));
			treeProj.setIsPegWork(request.getParameter("isPegWork"));
			treeProj.setTreeProjUser2(request.getParameter("treeProjUser2"));

			this.treeProjService.save(treeProj);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.treeProjService")
	public void setTreeProjService(TreeProjService treeProjService) {
		this.treeProjService = treeProjService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TreeProj treeProj = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			treeProj = treeProjService.getTreeProj(RequestUtils.getInt(request,
					"indexId"));
		}
		JSONObject result = new JSONObject();
		if (treeProj != null) {
			result = treeProj.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", treeProj.getId());
			result.put("treeProjId", treeProj.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
