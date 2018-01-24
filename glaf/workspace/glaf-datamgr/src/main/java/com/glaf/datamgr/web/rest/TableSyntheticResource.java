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

import com.glaf.datamgr.domain.TableSynthetic;
import com.glaf.datamgr.query.TableSyntheticQuery;
import com.glaf.datamgr.service.TableSyntheticService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/tableSynthetic")
public class TableSyntheticResource {
	protected static final Log logger = LogFactory.getLog(TableSyntheticResource.class);

	protected TableSyntheticService tableSyntheticService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				tableSyntheticService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		tableSyntheticService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSyntheticQuery query = new TableSyntheticQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TableSyntheticDomainFactory.processDataRequest(dataRequest);

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
		int total = tableSyntheticService.getTableSyntheticCountByQueryCriteria(query);
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

			List<TableSynthetic> list = tableSyntheticService.getTableSyntheticsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (TableSynthetic tableSynthetic : list) {
					JSONObject rowJSON = tableSynthetic.toJsonObject();
					rowJSON.put("id", tableSynthetic.getId());
					rowJSON.put("tableSyntheticId", tableSynthetic.getId());
					rowJSON.put("startIndex", ++start);
					if (tableSynthetic.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (tableSynthetic.getSyncStatus() == -1) {
						rowJSON.put("syncStatusText", "失败");
					}
					if (tableSynthetic.getTargetTableName() != null) {
						rowJSON.put("targetTableName_enc",
								RequestUtils.encodeString(tableSynthetic.getTargetTableName()));
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSyntheticQuery query = new TableSyntheticQuery();
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
		int total = tableSyntheticService.getTableSyntheticCountByQueryCriteria(query);
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

			List<TableSynthetic> list = tableSyntheticService.getTableSyntheticsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableSynthetic tableSynthetic : list) {
					JSONObject rowJSON = tableSynthetic.toJsonObject();
					rowJSON.put("id", tableSynthetic.getId());
					rowJSON.put("tableSyntheticId", tableSynthetic.getId());
					if (StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
						rowJSON.put("tableName_enc", RequestUtils.encodeString(tableSynthetic.getTargetTableName()));
					}
					if (tableSynthetic.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (tableSynthetic.getSyncStatus() == -1) {
						rowJSON.put("syncStatusText", "失败");
					}
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
	@Path("/saveTableSynthetic")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveTableSynthetic(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableSynthetic tableSynthetic = new TableSynthetic();
		try {
			Tools.populate(tableSynthetic, params);

			tableSynthetic.setName(request.getParameter("name"));
			tableSynthetic.setTitle(request.getParameter("title"));
			tableSynthetic.setType(request.getParameter("type"));
			tableSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
			tableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
			tableSynthetic.setPrimaryKey(request.getParameter("primaryKey"));
			tableSynthetic.setSyncColumns(request.getParameter("syncColumns"));
			tableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
			tableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			tableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			tableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			tableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			tableSynthetic.setInsertOnly(request.getParameter("insertOnly"));
			tableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			tableSynthetic.setCreateBy(loginContext.getActorId());
			tableSynthetic.setUpdateBy(loginContext.getActorId());
			this.tableSyntheticService.save(tableSynthetic);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTableSyntheticService(TableSyntheticService tableSyntheticService) {
		this.tableSyntheticService = tableSyntheticService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TableSynthetic tableSynthetic = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			tableSynthetic = tableSyntheticService.getTableSynthetic(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (tableSynthetic != null) {
			result = tableSynthetic.toJsonObject();

			result.put("id", tableSynthetic.getId());
			result.put("tableSyntheticId", tableSynthetic.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
