package com.glaf.form.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
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
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormRuleProperty;
import com.glaf.form.core.query.FormRulePropertyQuery;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.FormRulePropertyDomainFactory;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/form/formRuleProperty")
public class FormRulePropertyResource {
	protected static final Log logger = LogFactory
			.getLog(FormRulePropertyResource.class);

	protected FormRulePropertyService formRulePropertyService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {

			List<String> ids = new ArrayList<String>();
			for (String id : rowIds.split(","))
				ids.add(id);
			if (ids != null && !ids.isEmpty()) {
				formRulePropertyService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		formRulePropertyService.deleteById(RequestUtils
				.getString(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormRulePropertyQuery query = new FormRulePropertyQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FormRulePropertyDomainFactory.processDataRequest(dataRequest);

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
		int total = formRulePropertyService
				.getFormRulePropertyCountByQueryCriteria(query);
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

			List<FormRuleProperty> list = formRulePropertyService
					.getFormRulePropertysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormRuleProperty formRuleProperty : list) {
					JSONObject rowJSON = formRuleProperty.toJsonObject();
					rowJSON.put("id", formRuleProperty.getId());
					rowJSON.put("formRulePropertyId", formRuleProperty.getId());
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
		FormRulePropertyQuery query = new FormRulePropertyQuery();
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
		int total = formRulePropertyService
				.getFormRulePropertyCountByQueryCriteria(query);
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

			List<FormRuleProperty> list = formRulePropertyService
					.getFormRulePropertysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormRuleProperty formRuleProperty : list) {
					JSONObject rowJSON = formRuleProperty.toJsonObject();
					rowJSON.put("id", formRuleProperty.getId());
					rowJSON.put("formRulePropertyId", formRuleProperty.getId());
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
	@Path("/saveFormRuleProperty")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFormRuleProperty(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormRuleProperty formRuleProperty = new FormRuleProperty();
		try {
			Tools.populate(formRuleProperty, params);

			formRuleProperty.setRuleId(request.getParameter("ruleId"));
			formRuleProperty.setName(request.getParameter("name"));
			formRuleProperty.setValue(request.getParameter("value"));

			this.formRulePropertyService.save(formRuleProperty);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(
			FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FormRuleProperty formRuleProperty = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			formRuleProperty = formRulePropertyService
					.getFormRuleProperty(RequestUtils.getString(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (formRuleProperty != null) {
			result = formRuleProperty.toJsonObject();
			result.put("id", formRuleProperty.getId());
			result.put("formRulePropertyId", formRuleProperty.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/viewPgComPrRule/{pageId}/{dataRole}/{propName}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getPageComponentPropertyRule(@PathParam("pageId") String pageId,@PathParam("dataRole") String dataRole,@PathParam("propName")String propName, @Context HttpServletRequest request) throws IOException
	{
		FormRuleProperty formRuleProperty = null;
		if (StringUtils.isNotEmpty(pageId)&&StringUtils.isNotEmpty(dataRole)&&StringUtils.isNotEmpty(propName)) {
			if("pageParameters".equalsIgnoreCase(propName)){
				propName = "inParamDefined" ;
			}
			formRuleProperty = formRulePropertyService
					.getPageComponentPropertyRule(pageId,dataRole,propName,null);
		}
		JSONObject result = new JSONObject();
		if (formRuleProperty != null) {
			result = formRuleProperty.toJsonObject();
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
