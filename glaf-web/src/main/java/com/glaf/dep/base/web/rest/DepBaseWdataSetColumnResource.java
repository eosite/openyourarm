package com.glaf.dep.base.web.rest;

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
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.dep.base.domain.DepBaseWdataSetColumn;
import com.glaf.dep.base.query.DepBaseWdataSetColumnQuery;
import com.glaf.dep.base.service.DepBaseWdataSetColumnService;
import com.glaf.dep.base.util.DepBaseWdataSetColumnDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/dep/base/depBaseWdataSetColumn")
public class DepBaseWdataSetColumnResource {
	protected static final Log logger = LogFactory.getLog(DepBaseWdataSetColumnResource.class);

	protected DepBaseWdataSetColumnService depBaseWdataSetColumnService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				depBaseWdataSetColumnService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                depBaseWdataSetColumnService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetColumnQuery query = new DepBaseWdataSetColumnQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		DepBaseWdataSetColumnDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseWdataSetColumnService.getDepBaseWdataSetColumnCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DepBaseWdataSetColumn> list = depBaseWdataSetColumnService.getDepBaseWdataSetColumnsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepBaseWdataSetColumn depBaseWdataSetColumn : list) {
					JSONObject rowJSON = depBaseWdataSetColumn.toJsonObject();
					rowJSON.put("id", depBaseWdataSetColumn.getId());
					rowJSON.put("depBaseWdataSetColumnId", depBaseWdataSetColumn.getId());
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
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetColumnQuery query = new DepBaseWdataSetColumnQuery();
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
		int total = depBaseWdataSetColumnService.getDepBaseWdataSetColumnCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DepBaseWdataSetColumn> list = depBaseWdataSetColumnService.getDepBaseWdataSetColumnsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepBaseWdataSetColumn depBaseWdataSetColumn : list) {
					JSONObject rowJSON = depBaseWdataSetColumn.toJsonObject();
					rowJSON.put("id", depBaseWdataSetColumn.getId());
					rowJSON.put("depBaseWdataSetColumnId", depBaseWdataSetColumn.getId());
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
	@Path("/saveDepBaseWdataSetColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBaseWdataSetColumn(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetColumn depBaseWdataSetColumn = new DepBaseWdataSetColumn();
		try {
		    Tools.populate(depBaseWdataSetColumn, params);

                    depBaseWdataSetColumn.setWdataSetId(RequestUtils.getLong(request, "wdataSetId"));
                    depBaseWdataSetColumn.setColumnName(request.getParameter("columnName"));
                    depBaseWdataSetColumn.setDataColumnName(request.getParameter("dataColumnName"));
                    depBaseWdataSetColumn.setDefaultVal(request.getParameter("defaultVal"));
                    depBaseWdataSetColumn.setCreator(request.getParameter("creator"));
                    depBaseWdataSetColumn.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                    depBaseWdataSetColumn.setModifier(request.getParameter("modifier"));
                    depBaseWdataSetColumn.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

		    this.depBaseWdataSetColumnService.save(depBaseWdataSetColumn);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseWdataSetColumnService")
	public void setDepBaseWdataSetColumnService(DepBaseWdataSetColumnService depBaseWdataSetColumnService) {
		this.depBaseWdataSetColumnService = depBaseWdataSetColumnService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBaseWdataSetColumn depBaseWdataSetColumn = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  depBaseWdataSetColumn = depBaseWdataSetColumnService.getDepBaseWdataSetColumn(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (depBaseWdataSetColumn != null) {
		    result =  depBaseWdataSetColumn.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", depBaseWdataSetColumn.getId());
		    result.put("depBaseWdataSetColumnId", depBaseWdataSetColumn.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}