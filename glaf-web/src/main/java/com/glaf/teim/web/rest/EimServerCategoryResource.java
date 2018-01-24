package com.glaf.teim.web.rest;

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


import com.glaf.teim.domain.EimServerCategory;
import com.glaf.teim.query.EimServerCategoryQuery;
import com.glaf.teim.service.EimServerCategoryService;
import com.glaf.teim.util.*;
import com.glaf.workflow.core.domain.ActReCategory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/teim/category")
public class EimServerCategoryResource {
	protected static final Log logger = LogFactory.getLog(EimServerCategoryResource.class);

	protected EimServerCategoryService eimServerCategoryService;

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
				eimServerCategoryService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                eimServerCategoryService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerCategoryQuery query = new EimServerCategoryQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		EimServerCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = eimServerCategoryService.getEimServerCategoryCountByQueryCriteria(query);
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
			List<EimServerCategory> list = eimServerCategoryService.getEimServerCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EimServerCategory eimServerCategory : list) {
					JSONObject rowJSON = eimServerCategory.toJsonObject();
					rowJSON.put("id", eimServerCategory.getId());
					rowJSON.put("eimServerCategoryId", eimServerCategory.getId());
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
	/**
	 * 获取所有分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategorys(@Context HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String categoryJson = null;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		List<EimServerCategory> list = eimServerCategoryService.list(null);
		if (list != null && list.size() > 0) {
			categoryJson = jsonObject.toJSONString(list);
			return categoryJson.getBytes("UTF-8");
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerCategoryQuery query = new EimServerCategoryQuery();
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
		int total = eimServerCategoryService.getEimServerCategoryCountByQueryCriteria(query);
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
			List<EimServerCategory> list = eimServerCategoryService.getEimServerCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EimServerCategory eimServerCategory : list) {
					JSONObject rowJSON = eimServerCategory.toJsonObject();
					rowJSON.put("id", eimServerCategory.getId());
					rowJSON.put("eimServerCategoryId", eimServerCategory.getId());
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
	@Path("/saveEimServerCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveEimServerCategory(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerCategory eimServerCategory = new EimServerCategory();
		try {
		    Tools.populate(eimServerCategory, params);

                    eimServerCategory.setCode(request.getParameter("code"));
                    eimServerCategory.setName(request.getParameter("name"));
                    eimServerCategory.setTreeId(request.getParameter("treeId"));
                    eimServerCategory.setParentId(RequestUtils.getLong(request, "parentId"));
                    eimServerCategory.setCreateBy(request.getParameter("createBy"));
                    eimServerCategory.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    eimServerCategory.setUpdateBy(request.getParameter("updateBy"));
                    eimServerCategory.setUpdateTime(RequestUtils.getDate(request, "updateTime"));

		    this.eimServerCategoryService.save(eimServerCategory);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.teim.service.eimServerCategoryService")
	public void setEimServerCategoryService(EimServerCategoryService eimServerCategoryService) {
		this.eimServerCategoryService = eimServerCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EimServerCategory eimServerCategory = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  eimServerCategory = eimServerCategoryService.getEimServerCategory(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (eimServerCategory != null) {
		    result =  eimServerCategory.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", eimServerCategory.getId());
		    result.put("eimServerCategoryId", eimServerCategory.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	/**
	 * 新增分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/add")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] addCategory(@Context HttpServletRequest request) throws Exception {
		Long pId = RequestUtils.getLong(request, "pId");
		String pTreeId = RequestUtils.getString(request, "pTreeId");
		String name = RequestUtils.getString(request, "name");
		int level = RequestUtils.getInt(request, "level");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject jsonObject = new JSONObject();

		try {
			EimServerCategory obj = new EimServerCategory();
			obj.setDeleteFlag(0);
			obj.setTreeId(pTreeId);
			obj.setCreateTime(new Date());
			obj.setCreateBy(actorId);
			obj.setName(name);
			obj.setUpdateBy(actorId);
			obj.setUpdateTime(new Date());
			obj.setParentId(pId);
			eimServerCategoryService.save(obj);
			jsonObject = (JSONObject) jsonObject.toJSON(obj);
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	/**
	 * 更新分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/rename")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategoryRename(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		String name = RequestUtils.getString(request, "name");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			eimServerCategoryService.rename(categoryId, name, actorId, new Date());
		} catch (Exception e) {
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 更新分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/move")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] categoryMove(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		Long pId = RequestUtils.getLong(request, "pId");
		String treeId = RequestUtils.getString(request, "treeId");
		String moveType = RequestUtils.getString(request, "moveType");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			eimServerCategoryService.move(moveType, categoryId, pId, treeId, actorId, new Date());
		} catch (Exception e) {
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 删除分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] delete(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		eimServerCategoryService.deleteById(categoryId);
		return ResponseUtils.responseJsonResult(true);
	}

}
