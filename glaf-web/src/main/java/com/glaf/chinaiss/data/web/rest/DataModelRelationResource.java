package com.glaf.chinaiss.data.web.rest;

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
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;


import com.glaf.chinaiss.data.domain.DataModelRelation;
import com.glaf.chinaiss.data.query.DataModelRelationQuery;
import com.glaf.chinaiss.data.service.DataModelRelationService;
import com.glaf.chinaiss.data.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/data/model/dataModelRelation")
public class DataModelRelationResource {
	protected static final Log logger = LogFactory.getLog(DataModelRelationResource.class);

	protected DataModelRelationService dataModelRelationService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				dataModelRelationService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                dataModelRelationService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelRelationQuery query = new DataModelRelationQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		DataModelRelationDomainFactory.processDataRequest(dataRequest);

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
		int total = dataModelRelationService.getDataModelRelationCountByQueryCriteria(query);
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
			List<DataModelRelation> list = dataModelRelationService.getDataModelRelationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DataModelRelation dataModelRelation : list) {
					JSONObject rowJSON = dataModelRelation.toJsonObject();
					rowJSON.put("id", dataModelRelation.getId());
					rowJSON.put("dataModelRelationId", dataModelRelation.getId());
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
		DataModelRelationQuery query = new DataModelRelationQuery();
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
		int total = dataModelRelationService.getDataModelRelationCountByQueryCriteria(query);
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
			List<DataModelRelation> list = dataModelRelationService.getDataModelRelationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DataModelRelation dataModelRelation : list) {
					JSONObject rowJSON = dataModelRelation.toJsonObject();
					rowJSON.put("id", dataModelRelation.getId());
					rowJSON.put("dataModelRelationId", dataModelRelation.getId());
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
	@Path("/saveDataModelRelation")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDataModelRelation(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelRelation dataModelRelation = new DataModelRelation();
		try {
		    Tools.populate(dataModelRelation, params);

                    dataModelRelation.setColumnName(request.getParameter("columnName"));
                    dataModelRelation.setDescription(request.getParameter("description"));
                    dataModelRelation.setType(request.getParameter("type"));
                    dataModelRelation.setRelateTable(request.getParameter("relateTable"));
                    dataModelRelation.setRelateColumn(request.getParameter("relateColumn"));
                    dataModelRelation.setRelateType(request.getParameter("relateType"));
                    dataModelRelation.setTopId(request.getParameter("topId"));
                    dataModelRelation.setParentId(request.getParameter("parentId"));
                    dataModelRelation.setCreateBy(request.getParameter("createBy"));
                    dataModelRelation.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    dataModelRelation.setUpdateBy(request.getParameter("updateBy"));
                    dataModelRelation.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
                    dataModelRelation.setListNo(RequestUtils.getInt(request, "listNo"));

		    this.dataModelRelationService.save(dataModelRelation);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.chinaiss.data.service.dataModelRelationService")
	public void setDataModelRelationService(DataModelRelationService dataModelRelationService) {
		this.dataModelRelationService = dataModelRelationService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DataModelRelation dataModelRelation = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  dataModelRelation = dataModelRelationService.getDataModelRelation(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (dataModelRelation != null) {
		    result =  dataModelRelation.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", dataModelRelation.getId());
		    result.put("dataModelRelationId", dataModelRelation.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
