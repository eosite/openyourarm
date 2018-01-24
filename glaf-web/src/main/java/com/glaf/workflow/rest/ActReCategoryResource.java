package com.glaf.workflow.rest;

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
import org.activiti.engine.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.workflow.core.domain.ActReCategory;
import com.glaf.workflow.core.query.ActReCategoryQuery;
import com.glaf.workflow.core.service.ActReCategoryService;
import com.glaf.workflow.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/workflow/actReCategory")
public class ActReCategoryResource {
	protected static final Log logger = LogFactory.getLog(ActReCategoryResource.class);

	protected ActReCategoryService actReCategoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				actReCategoryService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		actReCategoryService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ActReCategoryQuery query = new ActReCategoryQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ActReCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = actReCategoryService.getActReCategoryCountByQueryCriteria(query);
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
			List<ActReCategory> list = actReCategoryService.getActReCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ActReCategory actReCategory : list) {
					JSONObject rowJSON = actReCategory.toJsonObject();
					rowJSON.put("id", actReCategory.getId());
					rowJSON.put("actReCategoryId", actReCategory.getId());
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
		ActReCategoryQuery query = new ActReCategoryQuery();
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
		int total = actReCategoryService.getActReCategoryCountByQueryCriteria(query);
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
			List<ActReCategory> list = actReCategoryService.getActReCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ActReCategory actReCategory : list) {
					JSONObject rowJSON = actReCategory.toJsonObject();
					rowJSON.put("id", actReCategory.getId());
					rowJSON.put("actReCategoryId", actReCategory.getId());
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
	@Path("/saveActReCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveActReCategory(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ActReCategory actReCategory = new ActReCategory();
		try {
			Tools.populate(actReCategory, params);

			actReCategory.setParentId(RequestUtils.getLong(request, "parentId"));
			actReCategory.setCode(request.getParameter("code"));
			actReCategory.setName(request.getParameter("name"));
			actReCategory.setTreeId(request.getParameter("treeId"));
			actReCategory.setLevel(RequestUtils.getInt(request, "level"));
			actReCategory.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			actReCategory.setCreator(request.getParameter("creator"));
			actReCategory.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
			actReCategory.setModifier(request.getParameter("modifier"));
			actReCategory.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

			this.actReCategoryService.save(actReCategory);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.workflow.core.service.actReCategoryService")
	public void setActReCategoryService(ActReCategoryService actReCategoryService) {
		this.actReCategoryService = actReCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ActReCategory actReCategory = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			actReCategory = actReCategoryService.getActReCategory(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (actReCategory != null) {
			result = actReCategory.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", actReCategory.getId());
			result.put("actReCategoryId", actReCategory.getId());
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
	@Path("/categorys")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategorys(@Context HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String categoryJson = null;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		List<ActReCategory> actReCategorys = actReCategoryService.list(null);
		if (actReCategorys != null && actReCategorys.size() > 0) {
			categoryJson = jsonObject.toJSONString(actReCategorys);
			return categoryJson.getBytes("UTF-8");
		}
		return JSONObject.toJSONString(jsonObject).getBytes("UTF-8");
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
	@Path("/category/rename")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategoryRename(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		String name = RequestUtils.getString(request, "name");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			actReCategoryService.rename(categoryId, name, actorId, new Date());
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
	@Path("/category/move")
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
			actReCategoryService.move(moveType, categoryId, pId, treeId, actorId, new Date());
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
	@Path("/category/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategoryDelete(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		actReCategoryService.deleteById(categoryId);
		return ResponseUtils.responseJsonResult(true);
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
	@Path("/category/add")
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
			ActReCategory obj = new ActReCategory();
			obj.setDeleteFlag(0);
			obj.setTreeId(pTreeId);
			obj.setCreateDatetime(new Date());
			obj.setCreator(actorId);
			obj.setLevel(level);
			obj.setName(name);
			obj.setModifier(actorId);
			obj.setModifyDatetime(new Date());
			obj.setParentId(pId);
			actReCategoryService.save(obj);
			jsonObject = (JSONObject) jsonObject.toJSON(obj);
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/moveToCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] moveWorkFlowToCategory(@Context HttpServletRequest request) throws Exception {
		String modelId = RequestUtils.getString(request, "modelId");
		String categoryId = RequestUtils.getString(request, "categoryId");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject jsonObject = new JSONObject();
		try {
			actReCategoryService.workFlowMoveToCategory(modelId, categoryId);
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
}
