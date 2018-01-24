package com.glaf.dep.base.web.rest;

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
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.dep.base.domain.DepBaseCategory;
import com.glaf.dep.base.query.DepBaseCategoryQuery;
import com.glaf.dep.base.service.DepBaseCategoryService;
import com.glaf.dep.base.util.*;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/dep/base/depBaseCategory")
public class DepBaseCategoryResource {
	protected static final Log logger = LogFactory.getLog(DepBaseCategoryResource.class);

	protected DepBaseCategoryService depBaseCategoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				depBaseCategoryService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		depBaseCategoryService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DepBaseCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseCategoryService.getDepBaseCategoryCountByQueryCriteria(query);
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
			List<DepBaseCategory> list = depBaseCategoryService.getDepBaseCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseCategory depBaseCategory : list) {
					JSONObject rowJSON = depBaseCategory.toJsonObject();
					rowJSON.put("id", depBaseCategory.getId());
					rowJSON.put("depBaseCategoryId", depBaseCategory.getId());
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
		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
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
		int total = depBaseCategoryService.getDepBaseCategoryCountByQueryCriteria(query);
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
			List<DepBaseCategory> list = depBaseCategoryService.getDepBaseCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseCategory depBaseCategory : list) {
					JSONObject rowJSON = depBaseCategory.toJsonObject();
					rowJSON.put("id", depBaseCategory.getId());
					rowJSON.put("depBaseCategoryId", depBaseCategory.getId());
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
	@Path("/saveDepBaseCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveDepBaseCategory(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCategory depBaseCategory = new DepBaseCategory();
		try {
			Tools.populate(depBaseCategory, params);

			depBaseCategory.setCode(request.getParameter("code"));
			depBaseCategory.setName(request.getParameter("name"));
			depBaseCategory.setTreeId(request.getParameter("treeId"));
			depBaseCategory.setExpandFlag(request.getParameter("expandFlag"));
			depBaseCategory.setPid(RequestUtils.getLong(request, "pid"));
			depBaseCategory.setOrderNo(RequestUtils.getInt(request, "orderNo"));
			depBaseCategory.setCreator(request.getParameter("creator"));
			depBaseCategory.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
			depBaseCategory.setModifier(request.getParameter("modifier"));
			depBaseCategory.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
			depBaseCategory.setDelFlag(request.getParameter("delFlag"));

			this.depBaseCategoryService.save(depBaseCategory);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseCategoryService")
	public void setDepBaseCategoryService(DepBaseCategoryService depBaseCategoryService) {
		this.depBaseCategoryService = depBaseCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DepBaseCategory depBaseCategory = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			depBaseCategory = depBaseCategoryService.getDepBaseCategory(RequestUtils.getLong(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (depBaseCategory != null) {
			result = depBaseCategory.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", depBaseCategory.getId());
			result.put("depBaseCategoryId", depBaseCategory.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/getCategoryByTreeId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategoryByPid(@Context HttpServletRequest request) throws IOException {

		// 判断是否需要过滤
		String filter = request.getParameter("filter");
		List<String> filters = new ArrayList<String>();
		if (BooleanUtils.toBoolean(filter)) {
			String filterType = request.getParameter("filterType");
			String filterValue = request.getParameter("filterValue");

			if ("code_by_rootid".equalsIgnoreCase(filterType)) {
				// 根据code过滤，但传入的是categoryid
				String sql = "select treeid_+'%' from dep_base_category where id_=" + filterValue;

				DepBaseCategoryQuery query = new DepBaseCategoryQuery();
				query.setDelFlag("0");
				query.setSqlCondition(" and treeid_ like (" + sql + ") ");
				List<DepBaseCategory> list = depBaseCategoryService.list(query);
				for (DepBaseCategory filterCate : list) {
					filters.add(filterCate.getCode());
				}
			}

		}

		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
		query.setDelFlag("0");
		String rootId = request.getParameter("rootId");
		query.setTreeIdLike(rootId);
		query.setOrderBy("ORDERNO_ ASC");

		JSONArray results = new JSONArray();
		int total = depBaseCategoryService.getDepBaseCategoryCountByQueryCriteria(query);
		if (total > 0) {
			List<DepBaseCategory> list = depBaseCategoryService.list(query);
			if (list != null && !list.isEmpty()) {
				for (DepBaseCategory depBaseCategory : list) {
					JSONObject rowJSON = depBaseCategory.toJsonObject();

					// 引入分类时，如果是根节点或已有分类，则不能再次引入，根据code判断是否已有分类
					if (depBaseCategory.getPid() == -1 || filters.contains(depBaseCategory.getCode())) {
						rowJSON.put("chkDisabled", true);
					}

					// 判断是否有子节点
					if (depBaseCategory.getChildrenNum() != null && depBaseCategory.getChildrenNum() > 0) {
						rowJSON.put("leaf", false);
						rowJSON.put("isParent", true);
					} else {
						rowJSON.put("leaf", true);
						rowJSON.put("isParent", false);
					}
					
					// 判断是否默认展开节点
					if ("1".equals(depBaseCategory.getExpandFlag())) {
						rowJSON.put("open", true);
					}
					results.add(rowJSON);
				}
			}
		}

		return results.toJSONString().getBytes("utf-8");
	}

	@GET
	@POST
	@Path("/updateDelFlagByTreeIdLike")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] updateDelFlagByTreeIdLike(@Context HttpServletRequest request) throws IOException {
		try {
			String treeId = request.getParameter("treeId");
			String delFlag = request.getParameter("delFlag");
			// 根据TreeId更新DelFlag标记
			depBaseCategoryService.updateDelFlagByTreeId(treeId + "%", delFlag);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@GET
	@POST
	@Path("/copyCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] copyCategory(@Context HttpServletRequest request) throws IOException {
		try {
			LoginContext loginContext = RequestUtils.getLoginContext(request);

			String copyObjParams = request.getParameter("copyObjParams");
			String categoryId = request.getParameter("categoryId");

			JSONArray paramsArray = JSON.parseArray(copyObjParams);

			List<Long> cateids = new ArrayList<Long>();
			for (int i = 0; i < paramsArray.size(); i++) {
				JSONObject jobject = paramsArray.getJSONObject(i);

				cateids.add(jobject.getLong("id"));// 取出所有选中的分类id
			}

			DepBaseCategoryQuery query = new DepBaseCategoryQuery();
			query.setIds(cateids);
			List<DepBaseCategory> categorys = depBaseCategoryService.list(query);// 查询所有分类
			Map<Long, DepBaseCategory> categoryMap = new HashMap<Long, DepBaseCategory>();
			for (DepBaseCategory model : categorys) {
				categoryMap.put(model.getId(), model);
			}

			DepBaseCategory category = depBaseCategoryService.getDepBaseCategory(Long.parseLong(categoryId));

			Map<Long, DepBaseCategory> mapping = new HashMap<Long, DepBaseCategory>();
			for (int i = 0; i < paramsArray.size(); i++) {
				JSONObject jobject = paramsArray.getJSONObject(i);
				DepBaseCategory old = categoryMap.get(jobject.getLong("id"));

				long oldid = old.getId();
				if (jobject.getBooleanValue("isParent")) {
					// 是父节点
					old.setId(null);
					old.setPid(category.getId());
					old.setTreeId(category.getTreeId());
					old.setCreateDateTime(new Date());
					old.setCreator(loginContext.getActorId());
					depBaseCategoryService.save(old);

					mapping.put(oldid, old);
				} else {
					// 不是父节点
					// 如果有选中父节点，则从选中的父节点中获取pid和treeid
					// 如果没有选中父节点，则从选中的tree中获取值
					DepBaseCategory parent = mapping.get(old.getPid());
					
					long pid = category.getId();
					String treeId = category.getTreeId();
					
					if(parent!=null){
						pid = parent.getId();
						treeId = parent.getTreeId();
					}
					

					old.setId(null);
					old.setPid(pid);
					old.setTreeId(treeId);
					old.setCreateDateTime(new Date());
					old.setCreator(loginContext.getActorId());
					depBaseCategoryService.save(old);
				}

			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}
}
