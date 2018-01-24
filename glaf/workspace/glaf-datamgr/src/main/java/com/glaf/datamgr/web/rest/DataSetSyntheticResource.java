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
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetSynthetic;
import com.glaf.datamgr.query.DataSetSyntheticQuery;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.DataSetSyntheticService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/dataSetSynthetic")
public class DataSetSyntheticResource {
	protected static final Log logger = LogFactory.getLog(DataSetSyntheticResource.class);

	protected DataSetService dataSetService;

	protected DataSetSyntheticService dataSetSyntheticService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetSyntheticQuery query = new DataSetSyntheticQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		query.setDeleteFlag(0);
		DataSetSyntheticDomainFactory.processDataRequest(dataRequest);

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
		int total = dataSetSyntheticService.getDataSetSyntheticCountByQueryCriteria(query);
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

			List<DataSetSynthetic> list = dataSetSyntheticService.getDataSetSyntheticsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (DataSetSynthetic dataSetSynthetic : list) {
					JSONObject rowJSON = dataSetSynthetic.toJsonObject();
					rowJSON.put("id", dataSetSynthetic.getId());
					rowJSON.put("dataSetSyntheticId", dataSetSynthetic.getId());
					rowJSON.put("startIndex", ++start);
					if (dataSetSynthetic.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (dataSetSynthetic.getSyncStatus() == -1) {
						rowJSON.put("syncStatusText", "失败");
					}
					if(StringUtils.isNotEmpty(dataSetSynthetic.getTargetTableName())){
						rowJSON.put("tableName_enc", RequestUtils.encodeString(dataSetSynthetic.getTargetTableName()));
					}
					if (StringUtils.isNotEmpty(dataSetSynthetic.getSourceDataSetId())) {
						DataSet dataSet = dataSetService.getDataSet(dataSetSynthetic.getSourceDataSetId());
						rowJSON.put("datasetName", dataSet.getName());
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
				dataSetSyntheticService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		dataSetSyntheticService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetSyntheticQuery query = new DataSetSyntheticQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);

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
		int total = dataSetSyntheticService.getDataSetSyntheticCountByQueryCriteria(query);
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

			List<DataSetSynthetic> list = dataSetSyntheticService.getDataSetSyntheticsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataSetSynthetic dataSetSynthetic : list) {
					JSONObject rowJSON = dataSetSynthetic.toJsonObject();
					rowJSON.put("id", dataSetSynthetic.getId());
					rowJSON.put("dataSetSyntheticId", dataSetSynthetic.getId());
					if (dataSetSynthetic.getSyncStatus() == 1) {
						rowJSON.put("syncStatusText", "成功");
					} else if (dataSetSynthetic.getSyncStatus() == -1) {
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
	@Path("/saveDataSetSynthetic")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDataSetSynthetic(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetSynthetic dataSetSynthetic = new DataSetSynthetic();
		try {
			Tools.populate(dataSetSynthetic, params);

			dataSetSynthetic.setName(request.getParameter("name"));
			dataSetSynthetic.setTitle(request.getParameter("title"));
			dataSetSynthetic.setType(request.getParameter("type"));
			dataSetSynthetic.setSourceDatabaseIds(RequestUtils.getString(request, "sourceDatabaseIds"));
			dataSetSynthetic.setSourceDataSetId(request.getParameter("sourceDataSetId"));
			dataSetSynthetic.setAggregationKeys(request.getParameter("aggregationKeys"));
			dataSetSynthetic.setSyncColumns(request.getParameter("syncColumns"));
			dataSetSynthetic.setTargetTableName(request.getParameter("targetTableName"));
			dataSetSynthetic.setTargetDatabaseId(RequestUtils.getLong(request, "targetDatabaseId"));
			dataSetSynthetic.setScheduleFlag(request.getParameter("scheduleFlag"));
			dataSetSynthetic.setCreateTableFlag(request.getParameter("createTableFlag"));
			dataSetSynthetic.setDeleteFetch(request.getParameter("deleteFetch"));
			dataSetSynthetic.setInsertOnly(request.getParameter("insertOnly"));
			dataSetSynthetic.setLocked(RequestUtils.getInt(request, "locked"));
			dataSetSynthetic.setCreateBy(loginContext.getActorId());
			dataSetSynthetic.setUpdateBy(loginContext.getActorId());
			this.dataSetSyntheticService.save(dataSetSynthetic);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setDataSetSyntheticService(DataSetSyntheticService dataSetSyntheticService) {
		this.dataSetSyntheticService = dataSetSyntheticService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DataSetSynthetic dataSetSynthetic = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			dataSetSynthetic = dataSetSyntheticService.getDataSetSynthetic(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (dataSetSynthetic != null) {
			result = dataSetSynthetic.toJsonObject();

			result.put("id", dataSetSynthetic.getId());
			result.put("dataSetSyntheticId", dataSetSynthetic.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
