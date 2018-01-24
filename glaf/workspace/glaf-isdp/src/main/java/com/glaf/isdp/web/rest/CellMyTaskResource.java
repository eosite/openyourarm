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
import com.glaf.isdp.domain.CellMyTask;
import com.glaf.isdp.query.CellMyTaskQuery;
import com.glaf.isdp.service.CellMyTaskService;
import com.glaf.isdp.util.CellMyTaskDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellMyTask")
public class CellMyTaskResource {
	protected static final Log logger = LogFactory
			.getLog(CellMyTaskResource.class);

	protected CellMyTaskService cellMyTaskService;

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
				cellMyTaskService.deleteByIds(ids);
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
		cellMyTaskService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellMyTaskQuery query = new CellMyTaskQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellMyTaskDomainFactory.processDataRequest(dataRequest);

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
		int total = cellMyTaskService.getCellMyTaskCountByQueryCriteria(query);
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
			List<CellMyTask> list = cellMyTaskService
					.getCellMyTasksByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellMyTask cellMyTask : list) {
					JSONObject rowJSON = cellMyTask.toJsonObject();
					rowJSON.put("id", cellMyTask.getId());
					rowJSON.put("cellMyTaskId", cellMyTask.getId());
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
		CellMyTaskQuery query = new CellMyTaskQuery();
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
		int total = cellMyTaskService.getCellMyTaskCountByQueryCriteria(query);
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
			List<CellMyTask> list = cellMyTaskService
					.getCellMyTasksByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellMyTask cellMyTask : list) {
					JSONObject rowJSON = cellMyTask.toJsonObject();
					rowJSON.put("id", cellMyTask.getId());
					rowJSON.put("cellMyTaskId", cellMyTask.getId());
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
	@Path("/saveCellMyTask")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellMyTask(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellMyTask cellMyTask = new CellMyTask();
		try {
			Tools.populate(cellMyTask, params);

			cellMyTask.setTopId(request.getParameter("topId"));
			cellMyTask.setFillFormId(request.getParameter("fillFormId"));
			cellMyTask.setCtime(RequestUtils.getDate(request, "ctime"));
			cellMyTask.setIndexId(RequestUtils.getInt(request, "indexId"));
			cellMyTask.setTaskId(request.getParameter("taskId"));
			cellMyTask.setFileDotFileId(request.getParameter("fileDotFileId"));
			cellMyTask.setName(request.getParameter("name"));
			cellMyTask.setProjName(request.getParameter("projName"));
			cellMyTask.setListNo(RequestUtils.getInt(request, "listNo"));
			cellMyTask.setTypeIndexId(RequestUtils.getInt(request,
					"typeIndexId"));
			cellMyTask.setPage(RequestUtils.getInt(request, "page"));
			cellMyTask.setFinishInt(RequestUtils.getInt(request, "finishInt"));
			cellMyTask.setFormTypeInt(RequestUtils.getInt(request,
					"formTypeInt"));
			cellMyTask.setFlagInt(RequestUtils.getInt(request, "flagInt"));
			cellMyTask.setIntInFlow(RequestUtils.getInt(request, "intInFlow"));
			cellMyTask.setMainId(request.getParameter("mainId"));
			cellMyTask.setIntLastPage(RequestUtils.getInt(request,
					"intLastPage"));

			this.cellMyTaskService.save(cellMyTask);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellMyTaskService")
	public void setCellMyTaskService(CellMyTaskService cellMyTaskService) {
		this.cellMyTaskService = cellMyTaskService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellMyTask cellMyTask = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			cellMyTask = cellMyTaskService.getCellMyTask(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (cellMyTask != null) {
			result = cellMyTask.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellMyTask.getId());
			result.put("cellMyTaskId", cellMyTask.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
