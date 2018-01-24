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
import com.glaf.isdp.domain.CellMyTaskMain;
import com.glaf.isdp.query.CellMyTaskMainQuery;
import com.glaf.isdp.service.CellMyTaskMainService;
import com.glaf.isdp.util.CellMyTaskMainDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellMyTaskMain")
public class CellMyTaskMainResource {
	protected static final Log logger = LogFactory
			.getLog(CellMyTaskMainResource.class);

	protected CellMyTaskMainService cellMyTaskMainService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellMyTaskMainService.deleteByIds(ids);
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
		cellMyTaskMainService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellMyTaskMainQuery query = new CellMyTaskMainQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellMyTaskMainDomainFactory.processDataRequest(dataRequest);

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
		int total = cellMyTaskMainService
				.getCellMyTaskMainCountByQueryCriteria(query);
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
			List<CellMyTaskMain> list = cellMyTaskMainService
					.getCellMyTaskMainsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellMyTaskMain cellMyTaskMain : list) {
					JSONObject rowJSON = cellMyTaskMain.toJsonObject();
					rowJSON.put("id", cellMyTaskMain.getId());
					rowJSON.put("cellMyTaskMainId", cellMyTaskMain.getId());
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
		CellMyTaskMainQuery query = new CellMyTaskMainQuery();
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
		int total = cellMyTaskMainService
				.getCellMyTaskMainCountByQueryCriteria(query);
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
			List<CellMyTaskMain> list = cellMyTaskMainService
					.getCellMyTaskMainsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellMyTaskMain cellMyTaskMain : list) {
					JSONObject rowJSON = cellMyTaskMain.toJsonObject();
					rowJSON.put("id", cellMyTaskMain.getId());
					rowJSON.put("cellMyTaskMainId", cellMyTaskMain.getId());
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
	@Path("/saveCellMyTaskMain")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellMyTaskMain(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellMyTaskMain cellMyTaskMain = new CellMyTaskMain();
		try {
			Tools.populate(cellMyTaskMain, params);

			cellMyTaskMain.setCtime(RequestUtils.getDate(request, "ctime"));
			cellMyTaskMain.setIndexId(RequestUtils.getInt(request, "indexId"));
			cellMyTaskMain.setTaskId(request.getParameter("taskId"));
			cellMyTaskMain.setName(request.getParameter("name"));
			cellMyTaskMain.setProjName(request.getParameter("projName"));
			cellMyTaskMain.setListNo(RequestUtils.getInt(request, "listNo"));
			cellMyTaskMain.setTypeIndexId(RequestUtils.getInt(request,
					"typeIndexId"));
			cellMyTaskMain.setFlagInt(RequestUtils.getInt(request, "flagInt"));
			cellMyTaskMain
					.setMyCellTsksId(request.getParameter("myCellTsksId"));
			cellMyTaskMain.setFromTasksId(request.getParameter("fromTasksId"));
			cellMyTaskMain.setToTaskId(request.getParameter("toTaskId"));
			cellMyTaskMain.setIntFinish(RequestUtils.getInt(request,
					"intFinish"));
			//cellMyTaskMain.setFileContent(request.getParameter("fileContent"));
			cellMyTaskMain.setTypeTableName(request
					.getParameter("typeTableName"));
			cellMyTaskMain.setTypeId(request.getParameter("typeId"));
			cellMyTaskMain.setUserId(request.getParameter("userId"));
			cellMyTaskMain.setNetRoleId(request.getParameter("netRoleId"));
			cellMyTaskMain.setIntIsFlow(RequestUtils.getInt(request,
					"intIsFlow"));
			cellMyTaskMain.setIntStop(RequestUtils.getInt(request, "intStop"));
			cellMyTaskMain.setFileTypeIndex(RequestUtils.getInt(request,
					"fileTypeIndex"));

			this.cellMyTaskMainService.save(cellMyTaskMain);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellMyTaskMainService")
	public void setCellMyTaskMainService(
			CellMyTaskMainService cellMyTaskMainService) {
		this.cellMyTaskMainService = cellMyTaskMainService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellMyTaskMain cellMyTaskMain = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			cellMyTaskMain = cellMyTaskMainService.getCellMyTaskMain(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (cellMyTaskMain != null) {
			result = cellMyTaskMain.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellMyTaskMain.getId());
			result.put("cellMyTaskMainId", cellMyTaskMain.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
