package com.glaf.datamgr.web.rest;

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
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.TableSync;
import com.glaf.datamgr.query.TableSyncQuery;
import com.glaf.datamgr.service.TableSyncService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/tableSync")
public class TableSyncResource {
	protected static final Log logger = LogFactory.getLog(TableSyncResource.class);

	protected TableSyncService tableSyncService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSyncQuery query = new TableSyncQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TableSyncDomainFactory.processDataRequest(dataRequest);

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
		int total = tableSyncService.getTableSyncCountByQueryCriteria(query);
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

			List<TableSync> list = tableSyncService.getTableSyncsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (TableSync tableSync : list) {
					JSONObject rowJSON = tableSync.toJsonObject();
					rowJSON.put("id", tableSync.getId());
					rowJSON.put("tableSyncId", tableSync.getId());
					rowJSON.put("startIndex", ++start);
					if (StringUtils.isNotEmpty(tableSync.getTargetTableName())) {
						rowJSON.put("tableName_enc", RequestUtils.encodeString(tableSync.getTargetTableName()));
					}
					if (tableSync.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (tableSync.getSyncStatus() == -1) {
						rowJSON.put("syncStatusText", "失败");
					}
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
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				tableSyncService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		tableSyncService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSyncQuery query = new TableSyncQuery();
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
		int total = tableSyncService.getTableSyncCountByQueryCriteria(query);
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

			List<TableSync> list = tableSyncService.getTableSyncsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableSync tableSync : list) {
					JSONObject rowJSON = tableSync.toJsonObject();
					rowJSON.put("id", tableSync.getId());
					rowJSON.put("tableSyncId", tableSync.getId());
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
	@Path("/saveTableSync")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveTableSync(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSync tableSync = new TableSync();
		try {
			Tools.populate(tableSync, params);

			tableSync.setName(request.getParameter("name"));
			tableSync.setTitle(request.getParameter("title"));
			tableSync.setType(request.getParameter("type"));
			tableSync.setSourceDatabaseId(RequestUtils.getLong(request, "sourceDatabaseId"));
			tableSync.setSourceTableName(request.getParameter("sourceTableName"));
			tableSync.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			tableSync.setPrimaryKey(request.getParameter("primaryKey"));
			tableSync.setSyncColumns(request.getParameter("syncColumns"));
			tableSync.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableSync.setTargetTableName(request.getParameter("targetTableName"));
			tableSync.setTargetDatabaseIds(request.getParameter("targetDatabaseIds"));
			tableSync.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			tableSync.setScheduleFlag(request.getParameter("scheduleFlag"));
			tableSync.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableSync.setDeleteFetch(request.getParameter("deleteFetch"));
			tableSync.setInsertOnly(request.getParameter("insertOnly"));
			tableSync.setLocked(RequestUtils.getInt(request, "locked"));
			tableSync.setCreateBy(loginContext.getActorId());
			tableSync.setUpdateBy(loginContext.getActorId());
			this.tableSyncService.save(tableSync);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTableSyncService(TableSyncService tableSyncService) {
		this.tableSyncService = tableSyncService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TableSync tableSync = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			tableSync = tableSyncService.getTableSync(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (tableSync != null) {
			result = tableSync.toJsonObject();

			result.put("id", tableSync.getId());
			result.put("tableSyncId", tableSync.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
