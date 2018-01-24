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
import com.glaf.isdp.domain.CellTreeDot;
import com.glaf.isdp.query.CellTreeDotQuery;
import com.glaf.isdp.service.CellTreeDotService;
import com.glaf.isdp.util.CellTreeDotDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellTreeDot")
public class CellTreeDotResource {
	protected static final Log logger = LogFactory
			.getLog(CellTreeDotResource.class);

	protected CellTreeDotService cellTreeDotService;

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
				cellTreeDotService.deleteByIds(ids);
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
		cellTreeDotService.deleteById(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellTreeDotQuery query = new CellTreeDotQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellTreeDotDomainFactory.processDataRequest(dataRequest);

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
		int total = cellTreeDotService
				.getCellTreeDotCountByQueryCriteria(query);
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
			List<CellTreeDot> list = cellTreeDotService
					.getCellTreeDotsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellTreeDot cellTreeDot : list) {
					JSONObject rowJSON = cellTreeDot.toJsonObject();
					rowJSON.put("id", cellTreeDot.getId());
					rowJSON.put("cellTreeDotId", cellTreeDot.getId());
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
		CellTreeDotQuery query = new CellTreeDotQuery();
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
		int total = cellTreeDotService
				.getCellTreeDotCountByQueryCriteria(query);
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
			List<CellTreeDot> list = cellTreeDotService
					.getCellTreeDotsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellTreeDot cellTreeDot : list) {
					JSONObject rowJSON = cellTreeDot.toJsonObject();
					rowJSON.put("id", cellTreeDot.getId());
					rowJSON.put("cellTreeDotId", cellTreeDot.getId());
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
	@Path("/saveCellTreeDot")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellTreeDot(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellTreeDot cellTreeDot = new CellTreeDot();
		try {
			Tools.populate(cellTreeDot, params);

			cellTreeDot.setId(request.getParameter("id"));
			cellTreeDot.setParentId(RequestUtils.getInt(request, "parentId"));
			cellTreeDot.setIndexName(request.getParameter("indexName"));
			cellTreeDot.setLevel(RequestUtils.getInt(request, "Level"));
			cellTreeDot.setNum(request.getParameter("num"));
			cellTreeDot.setContent(request.getParameter("content"));
			cellTreeDot.setNodeIco(RequestUtils.getInt(request, "nodeIco"));
			cellTreeDot.setSindexName(request.getParameter("sindexName"));
			cellTreeDot.setListNo(RequestUtils.getInt(request, "listNo"));
			cellTreeDot.setViewType(RequestUtils.getInt(request, "viewType"));
			cellTreeDot.setIsMode(request.getParameter("isMode"));
			cellTreeDot.setModeTableId(request.getParameter("modeTableId"));
			cellTreeDot.setIsSystem(RequestUtils.getInt(request, "isSystem"));
			cellTreeDot.setCustomData(request.getParameter("customData"));
			cellTreeDot.setIntSystemSelect(RequestUtils.getInt(request,
					"intSystemSelect"));
			cellTreeDot.setIntUsed(RequestUtils.getInt(request, "intUsed"));
			cellTreeDot.setIntDel(RequestUtils.getInt(request, "intDel"));
			cellTreeDot.setTypeTableName(request.getParameter("typeTableName"));
			cellTreeDot.setIntOperation(RequestUtils.getInt(request,
					"intOperation"));
			cellTreeDot.setPicFile(request.getParameter("picFile"));
			//cellTreeDot.setFileContent(request.getParameter("fileContent"));
			cellTreeDot.setIntMuiFrm(RequestUtils.getInt(request, "intMuiFrm"));
			cellTreeDot.setIntNoShow(RequestUtils.getInt(request, "intNoShow"));
			cellTreeDot.setTypeBaseTable(request.getParameter("typeBaseTable"));
			cellTreeDot.setTypeIndex(RequestUtils.getInt(request, "typeIndex"));
			cellTreeDot.setGid(request.getParameter("gid"));
			cellTreeDot.setFileName(request.getParameter("fileName"));
			cellTreeDot.setLinkFileContent(request
					.getParameter("linkFileContent"));
			cellTreeDot.setLinkFileName(request.getParameter("linkFileName"));

			this.cellTreeDotService.save(cellTreeDot);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellTreeDotService")
	public void setCellTreeDotService(CellTreeDotService cellTreeDotService) {
		this.cellTreeDotService = cellTreeDotService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellTreeDot cellTreeDot = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			cellTreeDot = cellTreeDotService.getCellTreeDot(RequestUtils
					.getInt(request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (cellTreeDot != null) {
			result = cellTreeDot.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellTreeDot.getId());
			result.put("cellTreeDotId", cellTreeDot.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
