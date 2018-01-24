package com.glaf.dep.base.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.glaf.dep.base.domain.DepBaseCompProp;
import com.glaf.dep.base.domain.DepBaseProp;
import com.glaf.dep.base.domain.DepBasePropCategory;
import com.glaf.dep.base.query.DepBaseCompPropQuery;
import com.glaf.dep.base.query.DepBasePropCategoryQuery;
import com.glaf.dep.base.query.DepBasePropQuery;
import com.glaf.dep.base.service.DepBaseCompPropService;
import com.glaf.dep.base.service.DepBasePropCategoryService;
import com.glaf.dep.base.service.DepBasePropService;
import com.glaf.dep.base.util.DepBasePropDomainFactory;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/dep/base/depBaseProp")
public class DepBasePropResource {
	protected static final Log logger = LogFactory.getLog(DepBasePropResource.class);

	protected DepBasePropService depBasePropService;

	@Resource(name = "com.glaf.dep.base.service.depBasePropCategoryService")
	protected DepBasePropCategoryService depBasePropCategoryService;
	
	@Resource(name = "com.glaf.dep.base.service.depBaseCompPropService")
	protected DepBaseCompPropService depBaseCompPropService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ruleIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				depBasePropService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		depBasePropService.deleteById(request.getParameter("ruleId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropQuery query = new DepBasePropQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DepBasePropDomainFactory.processDataRequest(dataRequest);

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

		String categoryId = request.getParameter("categoryId");
		List<String> ruleIds = new ArrayList<String>();
		ruleIds.add("-2");
		if (StringUtils.isNotEmpty(categoryId)) {
			List<DepBasePropCategory> lists = depBasePropCategoryService
					.getDepBasePropCatgorysByCategoryId(Long.parseLong(categoryId));
			for (DepBasePropCategory model : lists) {
				ruleIds.add(model.getRuleId());
			}
		}
		query.setRuleIds(ruleIds);
		query.setOrderBy("ORDERNO_ ASC");

		JSONObject result = new JSONObject();
		int total = depBasePropService.getDepBasePropCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DepBaseProp> list = depBasePropService.getDepBasePropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseProp depBaseProp : list) {
					JSONObject rowJSON = depBaseProp.toJsonObject();
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
		
		//取得规则id集合
		String categoryId = request.getParameter("categoryId");
		List<String> ruleIds = new ArrayList<String>();
		ruleIds.add("-2");
		if (StringUtils.isNotEmpty(categoryId)) {
			List<DepBasePropCategory> lists = depBasePropCategoryService
					.getDepBasePropCatgorysByCategoryId(Long.parseLong(categoryId));
			for (DepBasePropCategory model : lists) {
				ruleIds.add(model.getRuleId());
			}
		}
		
		//先取得各组件与规则的映射关系，方便后面使用时调用，提高查询性能
		Map<String,JSONArray> propCompIdsMap = new HashMap<String,JSONArray>();
		DepBaseCompPropQuery compPropQuery = new DepBaseCompPropQuery();
		compPropQuery.setRuleIds(ruleIds);
		List<DepBaseCompProp> compProps = depBaseCompPropService.list(compPropQuery);
		for(DepBaseCompProp compProp : compProps){
			String key = compProp.getRuleId();
			String value = compProp.getDepBaseComponentId();
			
			if(!propCompIdsMap.containsKey(key)){
				JSONArray array = new JSONArray();
				array.add(value);
				propCompIdsMap.put(key, array);
			}else{
				JSONArray array = propCompIdsMap.get(key);
				array.add(value);
				propCompIdsMap.put(key, array);
			}
			
		}
		
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropQuery query = new DepBasePropQuery();
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
		
		query.setRuleIds(ruleIds);
		query.setOrderBy("ORDERNO_ ASC");
		
		JSONObject result = new JSONObject();
		int total = depBasePropService.getDepBasePropCountByQueryCriteria(query);
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
			List<DepBaseProp> list = depBasePropService.getDepBasePropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseProp depBaseProp : list) {
					JSONObject rowJSON = depBaseProp.toJsonObject();
					/*
					 * 循环中使用异步查询，性能太差，放弃使用
					List<DepBaseComponent> components = depBaseProp.getComponents();
					JSONArray componentIds = new JSONArray();
					if (components != null && components.size() > 0) {
						for (DepBaseComponent component : components) {
							componentIds.add(component.getId());
						}
					}
					rowJSON.put("componentIds", componentIds);
					*/
					rowJSON.put("componentIds", propCompIdsMap.get(depBaseProp.getRuleId()));
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
	@Path("/saveDepBaseProp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBaseProp(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseProp depBaseProp = new DepBaseProp();
		try {
			Tools.populate(depBaseProp, params);

			depBaseProp.setRuleCode(request.getParameter("ruleCode"));
			depBaseProp.setRuleName(request.getParameter("ruleName"));
			depBaseProp.setRuleDesc(request.getParameter("ruleDesc"));
			depBaseProp.setSysCategory(request.getParameter("sysCategory"));
			depBaseProp.setUseCategory(request.getParameter("useCategory"));
			depBaseProp.setOpenFlag(request.getParameter("openFlag"));
			depBaseProp.setOrderNo(RequestUtils.getInt(request, "orderNo"));
			depBaseProp.setReadOnly(request.getParameter("readOnly"));
			depBaseProp.setRepeatFlag(request.getParameter("repeatFlag"));
			depBaseProp.setNotNull(request.getParameter("notNull"));
			depBaseProp.setInputType(request.getParameter("inputType"));
			depBaseProp.setDefaultVal(request.getParameter("defaultVal"));
			depBaseProp.setExtJson(request.getParameter("extJson"));
			depBaseProp.setCreator(request.getParameter("creator"));
			depBaseProp.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
			depBaseProp.setModifier(request.getParameter("modifier"));
			depBaseProp.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
			depBaseProp.setDelFlag(request.getParameter("delFlag"));

			this.depBasePropService.save(depBaseProp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBasePropService")
	public void setDepBasePropService(DepBasePropService depBasePropService) {
		this.depBasePropService = depBasePropService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBaseProp depBaseProp = null;
		if (StringUtils.isNotEmpty(request.getParameter("ruleId"))) {
			depBaseProp = depBasePropService.getDepBaseProp(request.getParameter("ruleId"));
		}
		JSONObject result = new JSONObject();
		if (depBaseProp != null) {
			result = depBaseProp.toJsonObject();
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/updateDelFlag")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] updateDelFlag(@Context HttpServletRequest request) throws Exception {
		try {
			String ruleId = request.getParameter("ruleId");
			DepBaseProp model = depBasePropService.getDepBaseProp(ruleId);
			model.setDelFlag("1");
			depBasePropService.save(model);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@GET
	@POST
	@Path("/validateRuleCode")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] validateRuleCode(@Context HttpServletRequest request) throws Exception {
		try {
			String ruleCode = request.getParameter("ruleCode");
			DepBasePropQuery query = new DepBasePropQuery();
			query.setRuleCode(ruleCode);
			List<DepBaseProp> depBaseProps = depBasePropService.list(query);

			JSONObject rst = new JSONObject();
			if (depBaseProps == null)
				rst.put("rstNumber", 0);
			else
				rst.put("rstNumber", depBaseProps.size());

			rst.put("statusCode", 200);

			return rst.toJSONString().getBytes("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@GET
	@POST
	@Path("/allData")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] allData(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws Exception {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropQuery query = new DepBasePropQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DepBasePropDomainFactory.processDataRequest(dataRequest);

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

		String categoryTreeId = request.getParameter("categoryTreeId");
		List<String> ruleIds = new ArrayList<String>();
		ruleIds.add("-2");
		if (StringUtils.isNotEmpty(categoryTreeId)) {

			DepBasePropCategoryQuery cateQuery = new DepBasePropCategoryQuery();
			cateQuery.setTreeIdLike(categoryTreeId);

			List<DepBasePropCategory> lists = depBasePropCategoryService.list(cateQuery);
			for (DepBasePropCategory model : lists) {
				ruleIds.add(model.getRuleId());
			}
		}
		query.setRuleIds(ruleIds);
		query.setOrderBy("ORDERNO_ ASC");

		JSONObject result = new JSONObject();
		int total = depBasePropService.getDepBasePropCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DepBaseProp> list = depBasePropService.getDepBasePropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseProp depBaseProp : list) {
					JSONObject rowJSON = depBaseProp.toJsonObject();
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

}
