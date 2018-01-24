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

import com.glaf.core.util.*;

import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.query.TreeTableSyntheticQuery;
import com.glaf.datamgr.service.TreeTableSyntheticService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/treeTableSynthetic")
public class TreeTableSyntheticResource {
	protected static final Log logger = LogFactory.getLog(TreeTableSyntheticResource.class);

	protected TreeTableSyntheticService treeTableSyntheticService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);
		query.setDataRequest(dataRequest);
		TreeTableSyntheticDomainFactory.processDataRequest(dataRequest);

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
		int total = treeTableSyntheticService.getTreeTableSyntheticCountByQueryCriteria(query);
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

			List<TreeTableSynthetic> list = treeTableSyntheticService.getTreeTableSyntheticsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableSynthetic treeTableSynthetic : list) {
					JSONObject rowJSON = treeTableSynthetic.toJsonObject();
					rowJSON.put("id", treeTableSynthetic.getId());
					rowJSON.put("treeTableSyntheticId", treeTableSynthetic.getId());
					if(StringUtils.isNotEmpty(treeTableSynthetic.getTargetTableName())){
						rowJSON.put("tableName_enc", RequestUtils.encodeString(treeTableSynthetic.getTargetTableName()));
					}
					if (treeTableSynthetic.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (treeTableSynthetic.getSyncStatus() == -1) {
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
		treeTableSyntheticService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
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
		int total = treeTableSyntheticService.getTreeTableSyntheticCountByQueryCriteria(query);
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

			List<TreeTableSynthetic> list = treeTableSyntheticService.getTreeTableSyntheticsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableSynthetic treeTableSynthetic : list) {
					JSONObject rowJSON = treeTableSynthetic.toJsonObject();
					rowJSON.put("id", treeTableSynthetic.getId());
					rowJSON.put("treeTableSyntheticId", treeTableSynthetic.getId());
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
	@Path("/saveTreeTableSynthetic")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveTreeTableSynthetic(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableSynthetic treeTableSynthetic = new TreeTableSynthetic();
		try {
			Tools.populate(treeTableSynthetic, params);

			treeTableSynthetic.setName(request.getParameter("name"));
			treeTableSynthetic.setTitle(request.getParameter("title"));
			treeTableSynthetic.setType(request.getParameter("type"));
			treeTableSynthetic.setSourceTableName(request.getParameter("sourceTableName"));
			treeTableSynthetic.setSourceIdColumn(request.getParameter("sourceIdColumn"));
			treeTableSynthetic.setSourceIndexIdColumn(request.getParameter("sourceIndexIdColumn"));
			treeTableSynthetic.setSourceParentIdColumn(request.getParameter("sourceParentIdColumn"));
			treeTableSynthetic.setSourceTreeIdColumn(request.getParameter("sourceTreeIdColumn"));
			treeTableSynthetic.setSourceTextColumn(request.getParameter("sourceTextColumn"));
			treeTableSynthetic.setSourceWbsIndexColumn(request.getParameter("sourceWbsIndexColumn"));
			treeTableSynthetic.setSourceDatabaseIds(request.getParameter("sourceDatabaseIds"));
			treeTableSynthetic.setSourceTableExecutionIds(request.getParameter("sourceTableExecutionIds"));
			treeTableSynthetic.setSqlCriteria(request.getParameter("sqlCriteria"));
			treeTableSynthetic.setTargetTableName(request.getParameter("targetTableName"));
			treeTableSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			treeTableSynthetic.setTargetTableExecutionIds(request.getParameter("targetTableExecutionIds"));
			treeTableSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			treeTableSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			treeTableSynthetic.setGenNewPrimaryKey(request.getParameter("genNewPrimaryKey"));
			treeTableSynthetic.setGenByMonth(request.getParameter("genByMonth"));
			treeTableSynthetic.setSyntheticFlag(request.getParameter("syntheticFlag"));
			treeTableSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			treeTableSynthetic.setSyncStatus(RequestUtils.getInt(request, "syncStatus"));
			treeTableSynthetic.setSyncTime(RequestUtils.getDate(request, "syncTime"));
			treeTableSynthetic.setSortNo(RequestUtils.getInt(request, "sortNo"));
			treeTableSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			treeTableSynthetic.setCreateBy(request.getParameter("createBy"));
			treeTableSynthetic.setCreateTime(RequestUtils.getDate(request, "createTime"));
			treeTableSynthetic.setUpdateBy(request.getParameter("updateBy"));
			treeTableSynthetic.setUpdateTime(RequestUtils.getDate(request, "updateTime"));

			this.treeTableSyntheticService.save(treeTableSynthetic);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setTreeTableSyntheticService(TreeTableSyntheticService treeTableSyntheticService) {
		this.treeTableSyntheticService = treeTableSyntheticService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TreeTableSynthetic treeTableSynthetic = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			treeTableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (treeTableSynthetic != null) {
			result = treeTableSynthetic.toJsonObject();
			result.put("id", treeTableSynthetic.getId());
			result.put("treeTableSyntheticId", treeTableSynthetic.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
