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

import com.glaf.datamgr.domain.TreeTableAggregate;
import com.glaf.datamgr.query.TreeTableAggregateQuery;
import com.glaf.datamgr.service.TreeTableAggregateService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/treeTableAggregate")
public class TreeTableAggregateResource {
	protected static final Log logger = LogFactory.getLog(TreeTableAggregateResource.class);

	protected TreeTableAggregateService treeTableAggregateService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableAggregateQuery query = new TreeTableAggregateQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);
		query.setPrivateFlag(0);
		query.setDataRequest(dataRequest);
		TreeTableAggregateDomainFactory.processDataRequest(dataRequest);

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
		int total = treeTableAggregateService.getTreeTableAggregateCountByQueryCriteria(query);
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

			List<TreeTableAggregate> list = treeTableAggregateService.getTreeTableAggregatesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableAggregate treeTableAggregate : list) {
					JSONObject rowJSON = treeTableAggregate.toJsonObject();
					rowJSON.put("id", treeTableAggregate.getId());
					rowJSON.put("aggregateId", treeTableAggregate.getId());
					rowJSON.put("treeTableAggregateId", treeTableAggregate.getId());
					if (StringUtils.isNotEmpty(treeTableAggregate.getTargetTableName())) {
						rowJSON.put("tableName_enc",
								RequestUtils.encodeString(treeTableAggregate.getTargetTableName()));
					}
					if (treeTableAggregate.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (treeTableAggregate.getSyncStatus() == -1) {
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
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		treeTableAggregateService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableAggregateQuery query = new TreeTableAggregateQuery();
		Tools.populate(query, params);
		query.setPrivateFlag(0);

		int start = 0;
		int limit = 10;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		String orderName = ParamUtils.getString(params, "sortName");
		String order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = treeTableAggregateService.getTreeTableAggregateCountByQueryCriteria(query);
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

			List<TreeTableAggregate> list = treeTableAggregateService.getTreeTableAggregatesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableAggregate treeTableAggregate : list) {
					JSONObject rowJSON = treeTableAggregate.toJsonObject();
					rowJSON.put("id", treeTableAggregate.getId());
					rowJSON.put("aggregateId", treeTableAggregate.getId());
					rowJSON.put("treeTableAggregateId", treeTableAggregate.getId());
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
	@Path("/saveTreeTableAggregate")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveTreeTableAggregate(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		TreeTableAggregate treeTableAggregate = new TreeTableAggregate();
		try {
			Tools.populate(treeTableAggregate, params);

			treeTableAggregate.setName(request.getParameter("name"));
			treeTableAggregate.setTitle(request.getParameter("title"));
			treeTableAggregate.setType(request.getParameter("type"));
			treeTableAggregate.setSourceTableName(request.getParameter("sourceTableName"));
			treeTableAggregate.setSourceIdColumn(request.getParameter("sourceIdColumn"));
			treeTableAggregate.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
			treeTableAggregate.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
			treeTableAggregate.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
			treeTableAggregate.setSourceTextColumn(request.getParameter("sourceTextColumn"));
			treeTableAggregate.setSourceWbsIndexColumn(request.getParameter("sourceWbsIndexColumn"));
			treeTableAggregate.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			treeTableAggregate.setDatabaseIds(request.getParameter("databaseIds"));
			treeTableAggregate.setTargetTableName(request.getParameter("targetTableName"));
			treeTableAggregate.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			treeTableAggregate.setCreateTableFlag(request.getParameter("createTableFlag"));
			treeTableAggregate.setAggregateFlag(request.getParameter("aggregateFlag"));
			treeTableAggregate.setScheduleFlag(request.getParameter("scheduleFlag"));
			treeTableAggregate.setGenByMonth(request.getParameter("genByMonth"));
			treeTableAggregate.setDeleteFetch(request.getParameter("deleteFetch"));
			treeTableAggregate.setSqlCriteria(request.getParameter("sqlCriteria"));
			treeTableAggregate.setSyncColumns(request.getParameter("syncColumns"));
			treeTableAggregate.setSyncStatus(RequestUtils.getInt(request, "syncStatus"));
			treeTableAggregate.setSyncTime(RequestUtils.getDate(request, "syncTime"));
			treeTableAggregate.setSortNo(RequestUtils.getInt(request, "sortNo"));
			treeTableAggregate.setLocked(RequestUtils.getInt(request, "locked"));
			treeTableAggregate.setCreateBy(actorId);
			treeTableAggregate.setUpdateBy(actorId);

			this.treeTableAggregateService.save(treeTableAggregate);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTreeTableAggregateService(TreeTableAggregateService treeTableAggregateService) {
		this.treeTableAggregateService = treeTableAggregateService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TreeTableAggregate treeTableAggregate = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			treeTableAggregate = treeTableAggregateService.getTreeTableAggregate(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (treeTableAggregate != null) {
			result = treeTableAggregate.toJsonObject();
			result.put("id", treeTableAggregate.getId());
			result.put("aggregateId", treeTableAggregate.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
