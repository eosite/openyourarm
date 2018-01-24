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

import com.glaf.form.core.domain.FormEventTemplate;
import com.glaf.form.core.query.FormEventTemplateQuery;
import com.glaf.form.core.service.FormEventTemplateService;
import com.glaf.form.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/formEventTemplate")
public class FormEventTemplateResource {
	protected static final Log logger = LogFactory.getLog(FormEventTemplateResource.class);

	protected FormEventTemplateService formEventTemplateService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				formEventTemplateService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		formEventTemplateService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplateQuery query = new FormEventTemplateQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FormEventTemplateDomainFactory.processDataRequest(dataRequest);

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
		int total = formEventTemplateService.getFormEventTemplateCountByQueryCriteria(query);
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
			List<FormEventTemplate> list = formEventTemplateService.getFormEventTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEventTemplate formEventTemplate : list) {
					JSONObject rowJSON = formEventTemplate.toJsonObject();
					rowJSON.put("id", formEventTemplate.getId());
					rowJSON.put("formEventTemplateId", formEventTemplate.getId());
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
		FormEventTemplateQuery query = new FormEventTemplateQuery();
		Tools.populate(query, params);
		
		String parentId = RequestUtils.getString(request, "parentId","-1");
		
		query.setPId(parentId);

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
		int total = formEventTemplateService.getFormEventTemplateCountByQueryCriteria(query);
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
			List<FormEventTemplate> list = formEventTemplateService.getFormEventTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEventTemplate formEventTemplate : list) {
					JSONObject rowJSON = formEventTemplate.toJsonObject();
					rowJSON.put("id", formEventTemplate.getId());
					rowJSON.put("formEventTemplateId", formEventTemplate.getId());
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
	@Path("/saveFormEventTemplate")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormEventTemplate(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplate formEventTemplate = new FormEventTemplate();
		try {
			Tools.populate(formEventTemplate, params);

			formEventTemplate.setPId(request.getParameter("pId"));
			formEventTemplate.setComplexId(request.getParameter("complexId"));
			formEventTemplate.setName(request.getParameter("name"));
			formEventTemplate.setRemark(request.getParameter("remark"));
			formEventTemplate.setDiagram(request.getParameter("diagram"));
			formEventTemplate.setRule(request.getParameter("rule"));
			formEventTemplate.setPageId(request.getParameter("pageId"));
			formEventTemplate.setComplexRule(request.getParameter("complexRule"));
			formEventTemplate.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			formEventTemplate.setCreateDate(RequestUtils.getDate(request, "createDate"));
			formEventTemplate.setCreateBy(request.getParameter("createBy"));
			formEventTemplate.setUpdateBy(request.getParameter("updateBy"));
			formEventTemplate.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

			this.formEventTemplateService.save(formEventTemplate);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formEventTemplateService")
	public void setFormEventTemplateService(FormEventTemplateService formEventTemplateService) {
		this.formEventTemplateService = formEventTemplateService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormEventTemplate formEventTemplate = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formEventTemplate = formEventTemplateService.getFormEventTemplate(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (formEventTemplate != null) {
			result = formEventTemplate.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", formEventTemplate.getId());
			result.put("formEventTemplateId", formEventTemplate.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
