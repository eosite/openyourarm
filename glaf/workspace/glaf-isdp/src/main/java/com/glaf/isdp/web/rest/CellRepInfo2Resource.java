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
import com.glaf.isdp.domain.CellRepInfo2;
import com.glaf.isdp.query.CellRepInfo2Query;
import com.glaf.isdp.service.CellRepInfo2Service;
import com.glaf.isdp.util.CellRepInfo2DomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellRepInfo2")
public class CellRepInfo2Resource {
	protected static final Log logger = LogFactory.getLog(CellRepInfo2Resource.class);

	protected CellRepInfo2Service cellRepInfo2Service;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellRepInfo2Service.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		cellRepInfo2Service.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellRepInfo2Query query = new CellRepInfo2Query();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellRepInfo2DomainFactory.processDataRequest(dataRequest);

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
		int total = cellRepInfo2Service.getCellRepInfo2CountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<CellRepInfo2> list = cellRepInfo2Service.getCellRepInfo2sByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellRepInfo2 cellRepInfo2 : list) {
					JSONObject rowJSON = cellRepInfo2.toJsonObject();
					rowJSON.put("id", cellRepInfo2.getId());
					rowJSON.put("cellRepInfo2Id", cellRepInfo2.getId());
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
		CellRepInfo2Query query = new CellRepInfo2Query();
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
		int total = cellRepInfo2Service.getCellRepInfo2CountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<CellRepInfo2> list = cellRepInfo2Service.getCellRepInfo2sByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellRepInfo2 cellRepInfo2 : list) {
					JSONObject rowJSON = cellRepInfo2.toJsonObject();
					rowJSON.put("id", cellRepInfo2.getId());
					rowJSON.put("cellRepInfo2Id", cellRepInfo2.getId());
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
	@Path("/saveCellRepInfo2")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellRepInfo2(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellRepInfo2 cellRepInfo2 = new CellRepInfo2();
		try {
			Tools.populate(cellRepInfo2, params);

			cellRepInfo2.setFrmType(request.getParameter("frmType"));
			cellRepInfo2.setType(RequestUtils.getInt(request, "type"));
			cellRepInfo2.setContent(request.getParameter("content"));
			cellRepInfo2.setFormula(request.getParameter("formula"));
			cellRepInfo2.setOstTableName(request.getParameter("ostTableName"));
			cellRepInfo2.setOstRow(RequestUtils.getInt(request, "ostRow"));
			cellRepInfo2.setOstCol(RequestUtils.getInt(request, "ostCol"));
			cellRepInfo2.setOstRowEnd(RequestUtils.getInt(request, "ostRowEnd"));
			cellRepInfo2.setOstColEnd(RequestUtils.getInt(request, "ostColEnd"));
			cellRepInfo2.setOstCellId(request.getParameter("ostCellId"));
			cellRepInfo2.setFileDotFileId(request.getParameter("fileDotFileId"));
			cellRepInfo2.setOstColor(RequestUtils.getInt(request, "ostColor"));
			cellRepInfo2.setOstWay(RequestUtils.getInt(request, "ostWay"));
			cellRepInfo2.setRoleId(RequestUtils.getInt(request, "roleId"));
			cellRepInfo2.setTableName(request.getParameter("tableName"));
			cellRepInfo2.setFname(request.getParameter("fname"));
			cellRepInfo2.setDname(request.getParameter("dname"));
			cellRepInfo2.setIsSubTable(request.getParameter("isSubTable"));
			cellRepInfo2.setTableName2(request.getParameter("tableName2"));
			cellRepInfo2.setIntAutoinValue(RequestUtils.getInt(request, "intAutoinValue"));
			cellRepInfo2.setIntSelfClick(RequestUtils.getInt(request, "intSelfClick"));

			this.cellRepInfo2Service.save(cellRepInfo2);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellRepInfo2Service")
	public void setCellRepInfo2Service(CellRepInfo2Service cellRepInfo2Service) {
		this.cellRepInfo2Service = cellRepInfo2Service;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellRepInfo2 cellRepInfo2 = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			cellRepInfo2 = cellRepInfo2Service.getCellRepInfo2(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (cellRepInfo2 != null) {
			result = cellRepInfo2.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellRepInfo2.getId());
			result.put("cellRepInfo2Id", cellRepInfo2.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
