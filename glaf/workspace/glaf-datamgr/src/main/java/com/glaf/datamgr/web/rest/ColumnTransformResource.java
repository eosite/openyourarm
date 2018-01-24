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
import com.glaf.core.identity.User;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.ColumnTransform;
import com.glaf.datamgr.query.ColumnTransformQuery;
import com.glaf.datamgr.service.ColumnTransformService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/columnTransform")
public class ColumnTransformResource {
	protected static final Log logger = LogFactory.getLog(ColumnTransformResource.class);

	protected ColumnTransformService columnTransformService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ColumnTransformQuery query = new ColumnTransformQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ColumnTransformDomainFactory.processDataRequest(dataRequest);

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
		int total = columnTransformService.getColumnTransformCountByQueryCriteria(query);
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

			List<ColumnTransform> list = columnTransformService.getColumnTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (ColumnTransform model : list) {
					JSONObject rowJSON = model.toJsonObject();
					rowJSON.put("id", model.getId());
					rowJSON.put("columnTransformId", model.getId());
					if (model.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (model.getSyncStatus() == -1) {
						rowJSON.put("syncStatusText", "失败");
					}
					if (StringUtils.equals(model.getTargetType(), "Date")) {
						rowJSON.put("typeName", "日期时间型");
					} else if (StringUtils.equals(model.getTargetType(), "Integer")) {
						rowJSON.put("typeName", "整数型");
					} else if (StringUtils.equals(model.getTargetType(), "Long")) {
						rowJSON.put("typeName", "长整数型");
					} else if (StringUtils.equals(model.getTargetType(), "Double")) {
						rowJSON.put("typeName", "数值型");
					} else if (StringUtils.equals(model.getTargetType(), "Boolean")) {
						rowJSON.put("typeName", "逻辑型");
					} else {
						rowJSON.put("typeName", "字符串型");
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
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				columnTransformService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		columnTransformService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ColumnTransformQuery query = new ColumnTransformQuery();
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
		int total = columnTransformService.getColumnTransformCountByQueryCriteria(query);
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

			List<ColumnTransform> list = columnTransformService.getColumnTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (ColumnTransform model : list) {
					JSONObject rowJSON = model.toJsonObject();
					rowJSON.put("id", model.getId());
					rowJSON.put("columnTransformId", model.getId());
					if (model.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (model.getSyncStatus() == -1) {
						rowJSON.put("syncStatusText", "失败");
					}
					if (StringUtils.equals(model.getTargetType(), "Date")) {
						rowJSON.put("typeName", "日期时间型");
					} else if (StringUtils.equals(model.getTargetType(), "Integer")) {
						rowJSON.put("typeName", "整数型");
					} else if (StringUtils.equals(model.getTargetType(), "Long")) {
						rowJSON.put("typeName", "长整数型");
					} else if (StringUtils.equals(model.getTargetType(), "Double")) {
						rowJSON.put("typeName", "数值型");
					} else if (StringUtils.equals(model.getTargetType(), "Boolean")) {
						rowJSON.put("typeName", "逻辑型");
					} else {
						rowJSON.put("typeName", "字符串型");
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
	@Path("/saveColumnTransform")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveColumnTransform(@Context HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ColumnTransform columnTransform = new ColumnTransform();
		try {
			Tools.populate(columnTransform, params);

			columnTransform.setName(request.getParameter("name"));
			columnTransform.setTitle(request.getParameter("title"));
			columnTransform.setTableName(request.getParameter("tableName"));
			columnTransform.setColumnName(request.getParameter("columnName"));
			columnTransform.setTargetColumnPrecision(RequestUtils.getInt(request, "targetColumnPrecision"));
			columnTransform.setTargetType(request.getParameter("targetType"));
			columnTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
			columnTransform.setCondition(request.getParameter("condition"));
			columnTransform.setExpression(request.getParameter("expression"));
			columnTransform.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
			columnTransform.setType(request.getParameter("type"));
			columnTransform.setSort(RequestUtils.getInt(request, "sort"));
			columnTransform.setLocked(RequestUtils.getInt(request, "locked"));
			columnTransform.setCreateBy(actorId);
			columnTransform.setUpdateBy(actorId);

			this.columnTransformService.save(columnTransform);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setColumnTransformService(ColumnTransformService columnTransformService) {
		this.columnTransformService = columnTransformService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ColumnTransform columnTransform = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			columnTransform = columnTransformService.getColumnTransform(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (columnTransform != null) {
			result = columnTransform.toJsonObject();
			result.put("id", columnTransform.getId());
			result.put("columnTransformId", columnTransform.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
