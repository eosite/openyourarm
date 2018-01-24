package com.glaf.form.web.rest;

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


import com.glaf.form.core.domain.FormEventTemplateTree;
import com.glaf.form.core.query.FormEventTemplateTreeQuery;
import com.glaf.form.core.service.FormEventTemplateTreeService;
import com.glaf.form.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/formEventTemplateTree")
public class FormEventTemplateTreeResource {
	protected static final Log logger = LogFactory.getLog(FormEventTemplateTreeResource.class);

	protected FormEventTemplateTreeService formEventTemplateTreeService;

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
				formEventTemplateTreeService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		//formEventTemplateTreeService.deleteById(request.getParameter("id"));
		FormEventTemplateTree formEventTemplateTree = new FormEventTemplateTree();
		formEventTemplateTree.setId(request.getParameter("id"));
		formEventTemplateTree.setDeleteFlag(1);
		formEventTemplateTreeService.save(formEventTemplateTree);
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplateTreeQuery query = new FormEventTemplateTreeQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		FormEventTemplateTreeDomainFactory.processDataRequest(dataRequest);

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
		int total = formEventTemplateTreeService.getFormEventTemplateTreeCountByQueryCriteria(query);
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
			List<FormEventTemplateTree> list = formEventTemplateTreeService.getFormEventTemplateTreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormEventTemplateTree formEventTemplateTree : list) {
					JSONObject rowJSON = formEventTemplateTree.toJsonObject();
					rowJSON.put("id", formEventTemplateTree.getId());
					rowJSON.put("formEventTemplateTreeId", formEventTemplateTree.getId());
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
		FormEventTemplateTreeQuery query = new FormEventTemplateTreeQuery();
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
		int total = formEventTemplateTreeService.getFormEventTemplateTreeCountByQueryCriteria(query);
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
			List<FormEventTemplateTree> list = formEventTemplateTreeService.getFormEventTemplateTreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormEventTemplateTree formEventTemplateTree : list) {
					JSONObject rowJSON = formEventTemplateTree.toJsonObject();
					rowJSON.put("id", formEventTemplateTree.getId());
					rowJSON.put("formEventTemplateTreeId", formEventTemplateTree.getId());
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
	@Path("/saveFormEventTemplateTree")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormEventTemplateTree(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplateTree formEventTemplateTree = new FormEventTemplateTree();
		try {
		    Tools.populate(formEventTemplateTree, params);

                    formEventTemplateTree.setIndexId(RequestUtils.getInt(request, "indexId"));
                    formEventTemplateTree.setParentId(RequestUtils.getInt(request, "parentId"));
                    formEventTemplateTree.setTreeId(request.getParameter("treeId"));
                    formEventTemplateTree.setType(RequestUtils.getInt(request, "type"));
                    formEventTemplateTree.setName(request.getParameter("name"));
                    formEventTemplateTree.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
                    formEventTemplateTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    formEventTemplateTree.setCreateBy(request.getParameter("createBy"));
                    formEventTemplateTree.setUpdateBy(request.getParameter("updateBy"));
                    formEventTemplateTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		    this.formEventTemplateTreeService.save(formEventTemplateTree);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.form.core.service.formEventTemplateTreeService")
	public void setFormEventTemplateTreeService(FormEventTemplateTreeService formEventTemplateTreeService) {
		this.formEventTemplateTreeService = formEventTemplateTreeService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormEventTemplateTree formEventTemplateTree = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  formEventTemplateTree = formEventTemplateTreeService.getFormEventTemplateTree(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (formEventTemplateTree != null) {
		    result =  formEventTemplateTree.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", formEventTemplateTree.getId());
		    result.put("formEventTemplateTreeId", formEventTemplateTree.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
