package com.glaf.form.web.rest;

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

import com.alibaba.fastjson.JSON;
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
import com.glaf.form.core.domain.FormPageCategory;
import com.glaf.form.core.query.FormPageCategoryQuery;
import com.glaf.form.core.service.FormPageCategoryService;
import com.glaf.form.core.util.FormPageCategoryDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/formPageCategory")
public class FormPageCategoryResource {
	protected static final Log logger = LogFactory.getLog(FormPageCategoryResource.class);

	protected FormPageCategoryService formPageCategoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				formPageCategoryService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		formPageCategoryService.deleteById(RequestUtils.getInt(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageCategoryQuery query = new FormPageCategoryQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FormPageCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = formPageCategoryService.getFormPageCategoryCountByQueryCriteria(query);
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
			List<FormPageCategory> list = formPageCategoryService.getFormPageCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormPageCategory formPageCategory : list) {
					JSONObject rowJSON = formPageCategory.toJsonObject();
					rowJSON.put("id", formPageCategory.getId());
					rowJSON.put("formPageCategoryId", formPageCategory.getId());
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
		FormPageCategoryQuery query = new FormPageCategoryQuery();
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
		int total = formPageCategoryService.getFormPageCategoryCountByQueryCriteria(query);
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
			List<FormPageCategory> list = formPageCategoryService.getFormPageCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormPageCategory formPageCategory : list) {
					JSONObject rowJSON = formPageCategory.toJsonObject();
					rowJSON.put("id", formPageCategory.getId());
					rowJSON.put("formPageCategoryId", formPageCategory.getId());
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
	@Path("/saveFormPageCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormPageCategory(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageCategory formPageCategory = new FormPageCategory();
		try {
			Tools.populate(formPageCategory, params);

			formPageCategory.setName(request.getParameter("name"));
			formPageCategory.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			formPageCategory.setSortNo(RequestUtils.getInt(request, "sortNo"));
			formPageCategory.setLocked(RequestUtils.getInt(request, "locked"));
			formPageCategory.setPermission(request.getParameter("permission"));
			formPageCategory.setExt1(request.getParameter("ext1"));
			formPageCategory.setExt2(request.getParameter("ext2"));
			formPageCategory.setExt3(request.getParameter("ext3"));
			formPageCategory.setCreateBy(request.getParameter("createBy"));
			formPageCategory.setCreateDate(RequestUtils.getDate(request, "createDate"));

			this.formPageCategoryService.save(formPageCategory);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formPageCategoryService")
	public void setFormPageCategoryService(FormPageCategoryService formPageCategoryService) {
		this.formPageCategoryService = formPageCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormPageCategory formPageCategory = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formPageCategory = formPageCategoryService.getFormPageCategory(RequestUtils.getInt(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (formPageCategory != null) {
			result = formPageCategory.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", formPageCategory.getId());
			result.put("formPageCategoryId", formPageCategory.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/all")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] all(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageCategoryQuery query = new FormPageCategoryQuery();
		Tools.populate(query, params);
		
		query.setSortColumn("sortNo");
		
		JSONArray result = new JSONArray();

		List<FormPageCategory> list = formPageCategoryService.list(query);
		if (list != null && !list.isEmpty()) {
			return JSON.toJSONString(list).getBytes("UTF-8");
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
