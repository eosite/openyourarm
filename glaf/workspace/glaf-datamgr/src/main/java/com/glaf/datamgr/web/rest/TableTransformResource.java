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
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.TableTransform;
import com.glaf.datamgr.query.TableTransformQuery;
import com.glaf.datamgr.service.TableTransformService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/tableTransform")
public class TableTransformResource {
	protected static final Log logger = LogFactory.getLog(TableTransformResource.class);

	protected TableTransformService tableTransformService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransformQuery query = new TableTransformQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TableTransformDomainFactory.processDataRequest(dataRequest);
		query.setDeleteFlag(0);
		query.setLocked(0);

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
		int total = tableTransformService.getTableTransformCountByQueryCriteria(query);
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

			List<TableTransform> list = tableTransformService.getTableTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TableTransform tableTransform : list) {
					JSONObject rowJSON = tableTransform.toJsonObject();
					rowJSON.put("id", tableTransform.getTableName());
					rowJSON.put("tableTransformId", tableTransform.getTableName());
					if (tableTransform.getTransformStatus() == 1) {
						rowJSON.put("transformStatusText", "成功");
					} else if (tableTransform.getTransformStatus() == -1) {
						rowJSON.put("transformStatusText", "失败");
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
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		tableTransformService.deleteById(request.getParameter("tableName"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransformQuery query = new TableTransformQuery();
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
		int total = tableTransformService.getTableTransformCountByQueryCriteria(query);
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

			List<TableTransform> list = tableTransformService.getTableTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (TableTransform tableTransform : list) {
					JSONObject rowJSON = tableTransform.toJsonObject();
					rowJSON.put("id", tableTransform.getTableName());
					rowJSON.put("tableTransformId", tableTransform.getTableName());
					if (tableTransform.getTransformStatus() == 1) {
						rowJSON.put("transformStatusText", "成功");
					} else if (tableTransform.getTransformStatus() == -1) {
						rowJSON.put("transformStatusText", "失败");
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
	@Path("/saveTableTransform")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveTableTransform(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableTransform tableTransform = new TableTransform();
		try {
			Tools.populate(tableTransform, params);

			tableTransform.setTitle(request.getParameter("title"));
			tableTransform.setDatabaseIds(request.getParameter("databaseIds"));
			tableTransform.setPrimaryKey(request.getParameter("primaryKey"));
			tableTransform.setTargetTableName(request.getParameter("targetTableName"));
			tableTransform.setTransformColumns(request.getParameter("transformColumns"));
			tableTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
			tableTransform.setSort(RequestUtils.getInt(request, "sort"));
			tableTransform.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
			tableTransform.setTransformFlag(request.getParameter("transformFlag"));
			tableTransform.setTransformStatus(RequestUtils.getInt(request, "transformStatus"));
			tableTransform.setTransformTime(RequestUtils.getDate(request, "transformTime"));
			tableTransform.setCurrentUserFlag(request.getParameter("currentUserFlag"));
			tableTransform.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			tableTransform.setLocked(RequestUtils.getInt(request, "locked"));
			tableTransform.setCreateBy(loginContext.getActorId());
			tableTransform.setUpdateBy(loginContext.getActorId());

			this.tableTransformService.save(tableTransform);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTableTransformService(TableTransformService tableTransformService) {
		this.tableTransformService = tableTransformService;
	}

}
