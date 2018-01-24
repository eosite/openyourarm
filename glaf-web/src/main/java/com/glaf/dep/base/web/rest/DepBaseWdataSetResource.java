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
import com.glaf.dep.base.domain.DepBaseWdataSet;
import com.glaf.dep.base.query.DepBaseWdataSetQuery;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.dep.base.util.DepBaseWdataSetDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/dep/base/depBaseWdataSet")
public class DepBaseWdataSetResource {
	protected static final Log logger = LogFactory.getLog(DepBaseWdataSetResource.class);

	protected DepBaseWdataSetService depBaseWdataSetService;

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
				depBaseWdataSetService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                depBaseWdataSetService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetQuery query = new DepBaseWdataSetQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		DepBaseWdataSetDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseWdataSetService.getDepBaseWdataSetCountByQueryCriteria(query);
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
			List<DepBaseWdataSet> list = depBaseWdataSetService.getDepBaseWdataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepBaseWdataSet depBaseWdataSet : list) {
					JSONObject rowJSON = depBaseWdataSet.toJsonObject();
					rowJSON.put("id", depBaseWdataSet.getId());
					rowJSON.put("depBaseWdataSetId", depBaseWdataSet.getId());
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
		DepBaseWdataSetQuery query = new DepBaseWdataSetQuery();
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
		int total = depBaseWdataSetService.getDepBaseWdataSetCountByQueryCriteria(query);
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
			List<DepBaseWdataSet> list = depBaseWdataSetService.getDepBaseWdataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DepBaseWdataSet depBaseWdataSet : list) {
					JSONObject rowJSON = depBaseWdataSet.toJsonObject();
					rowJSON.put("id", depBaseWdataSet.getId());
					rowJSON.put("depBaseWdataSetId", depBaseWdataSet.getId());
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
	@Path("/saveDepBaseWdataSet")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBaseWdataSet(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSet depBaseWdataSet = new DepBaseWdataSet();
		try {
		    Tools.populate(depBaseWdataSet, params);

                    depBaseWdataSet.setDataSetCode(request.getParameter("dataSetCode"));
                    depBaseWdataSet.setDataSetName(request.getParameter("dataSetName"));
                    depBaseWdataSet.setDataSetDesc(request.getParameter("dataSetDesc"));
                    depBaseWdataSet.setRuleJson(request.getParameter("ruleJson"));
                    depBaseWdataSet.setTableName(request.getParameter("tableName"));
                    depBaseWdataSet.setDataTableName(request.getParameter("dataTableName"));
                    depBaseWdataSet.setWtype(request.getParameter("wtype"));
                    depBaseWdataSet.setVer(RequestUtils.getInt(request, "ver"));
                    depBaseWdataSet.setCreator(request.getParameter("creator"));
                    depBaseWdataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                    depBaseWdataSet.setModifier(request.getParameter("modifier"));
                    depBaseWdataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
                    depBaseWdataSet.setDelFlag(request.getParameter("delFlag"));

		    this.depBaseWdataSetService.save(depBaseWdataSet);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseWdataSetService")
	public void setDepBaseWdataSetService(DepBaseWdataSetService depBaseWdataSetService) {
		this.depBaseWdataSetService = depBaseWdataSetService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBaseWdataSet depBaseWdataSet = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  depBaseWdataSet = depBaseWdataSetService.getDepBaseWdataSet(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (depBaseWdataSet != null) {
		    result =  depBaseWdataSet.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", depBaseWdataSet.getId());
		    result.put("depBaseWdataSetId", depBaseWdataSet.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	
	@GET
	@POST
	@Path("/getWDataSetParams")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getDataSetParams(@Context HttpServletRequest request)
			throws IOException {
		String datasetIds = request.getParameter("datasetIds");
		if (StringUtils.isNotBlank(datasetIds)) {
			String ids[] = StringUtils.split(datasetIds, ",");
			JSONArray params = new JSONArray();
			DepBaseWdataSet  dataSet;
			for (String datasetId : ids) {
				if(StringUtils.isNotEmpty(datasetId)){
					Long id = Long.parseLong(datasetId);
					dataSet =  this.depBaseWdataSetService.getDepBaseWdataSet(id);
					if(dataSet!=null){
						String ruleStr = dataSet.getRuleJson();
						if(StringUtils.isNotEmpty(ruleStr)){
							JSONObject ruleJson = JSONObject.parseObject(ruleStr), json, tmp;
							JSONArray columns = ruleJson.getJSONArray("columns");
							if(columns != null){
								for(int i = 0, size = columns.size(); i < size; i ++){
									tmp = columns.getJSONObject(i);
									json = new JSONObject();
									json.put("param", tmp.get("param"));
									json.put("name", tmp.get("paramName"));
									params.add(json);
								}
							}
						}
					}
				}
			}
			return params.toJSONString().getBytes("UTF-8");
		}
		return null;
	}
	
	@GET
	@POST
	@Path("/getSelectJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getSelectJson(@Context HttpServletRequest request)
			throws IOException {
		Long id = RequestUtils.getLong(request, "datasetId");
		DepBaseWdataSet dataSet = this.depBaseWdataSetService.getDepBaseWdataSet(id);
		JSONArray params = new JSONArray();
		if (dataSet != null) {
			String ruleStr = dataSet.getRuleJson();
			if (StringUtils.isNotEmpty(ruleStr)) {
				JSONObject ruleJson = JSONObject.parseObject(ruleStr), json, tmp;
				JSONArray columns = ruleJson.getJSONArray("columns");
				if (columns != null) {
					for (int i = 0, size = columns.size(); i < size; i++) {
						tmp = columns.getJSONObject(i);
						if(!tmp.getBooleanValue("input") && !tmp.getBooleanValue("output")){
							continue;
						}
						json = new JSONObject();
						json.put("dtype", tmp.get("dtype"));
						json.put("id", tmp.get("wdataSetId"));
						json.put("datasetId", tmp.get("wdataSetId"));
						json.put("columnName", tmp.get("columnName"));
						json.put("tableNameCN", tmp.get("tableNameCN"));
						params.add(json);
					}
				}
			}
		}
		return params.toJSONString().getBytes("UTF-8");
	}
}
