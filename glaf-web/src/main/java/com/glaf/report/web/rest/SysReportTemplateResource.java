package com.glaf.report.web.rest;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.report.core.domain.SysReportTemplate;
import com.glaf.report.core.query.SysReportTemplateQuery;
import com.glaf.report.core.service.SysReportTemplateService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/report/sysReportTemplate")
public class SysReportTemplateResource {
	protected static final Log logger = LogFactory
			.getLog(SysReportTemplateResource.class);

	protected SysReportTemplateService sysReportTemplateService;

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
				sysReportTemplateService.deleteByIds(ids);
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
		sysReportTemplateService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplateQuery query = new SysReportTemplateQuery();
		Tools.populate(query, params);
		// query.setDataRequest(dataRequest);
		// SysReportTemplateDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
		}

		String currentSystemName = Environment.getCurrentSystemName();
		if (modelMap.get("systemName") != null) {
			String systemName = modelMap.get("systemName").toString();
			if (systemName != null) {
				Environment.setCurrentSystemName(systemName);
			}
		}

		JSONObject result = new JSONObject();
		try {
			int start = 0;
			int limit = PageResult.DEFAULT_PAGE_SIZE;

			int pageNo = 0;
			if (modelMap.get("page") != null) {
				pageNo = Integer.parseInt(modelMap.get("page").toString());
			}

			if (modelMap.get("pageSize") != null) {
				limit = Integer.parseInt(modelMap.get("pageSize").toString());
			}

			start = (pageNo - 1) * limit;

			if (start < 0) {
				start = 0;
			}

			if (limit <= 0) {
				limit = PageResult.DEFAULT_PAGE_SIZE;
			}

			result = new JSONObject();
			int total = sysReportTemplateService
					.getSysReportTemplateCountByQueryCriteria(query);
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

				if (StringUtils.isNotEmpty(orderName)) {
					query.setSortColumn(orderName);
					if (StringUtils.equals(order, "desc")) {
						query.setSortOrder(" desc ");
					}
				}

				List<SysReportTemplate> list = sysReportTemplateService
						.getSysReportTemplatesByQueryCriteria(start, limit,
								query);

				if (list != null && !list.isEmpty()) {
					JSONArray rowsJSON = new JSONArray();

					result.put("rows", rowsJSON);

					for (SysReportTemplate sysReportTemplate : list) {
						JSONObject rowJSON = sysReportTemplate.toJsonObject();
						rowJSON.put("id", sysReportTemplate.getId());
						rowJSON.put("sysReportTemplateId",
								sysReportTemplate.getId());
						rowJSON.put("startIndex", ++start);
						rowsJSON.add(rowJSON);
					}

				}
			} else {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				result.put("total", total);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
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
		SysReportTemplateQuery query = new SysReportTemplateQuery();
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
		int total = sysReportTemplateService
				.getSysReportTemplateCountByQueryCriteria(query);
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
			List<SysReportTemplate> list = sysReportTemplateService
					.getSysReportTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysReportTemplate sysReportTemplate : list) {
					JSONObject rowJSON = sysReportTemplate.toJsonObject();
					rowJSON.put("id", sysReportTemplate.getId());
					rowJSON.put("sysReportTemplateId",
							sysReportTemplate.getId());
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
	@Path("/saveSysReportTemplate")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysReportTemplate(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplate sysReportTemplate = new SysReportTemplate();
		try {
			Tools.populate(sysReportTemplate, params);

			sysReportTemplate.setReportTemplateName(request
					.getParameter("reportTemplateName"));
			sysReportTemplate.setCtime(RequestUtils.getDate(request, "ctime"));
			sysReportTemplate.setUtime(RequestUtils.getDate(request, "utime"));

			this.sysReportTemplateService.save(sysReportTemplate);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.report.service.sysReportTemplateService")
	public void setSysReportTemplateService(
			SysReportTemplateService sysReportTemplateService) {
		this.sysReportTemplateService = sysReportTemplateService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysReportTemplate sysReportTemplate = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			sysReportTemplate = sysReportTemplateService
					.getSysReportTemplate(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (sysReportTemplate != null) {
			result = sysReportTemplate.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", sysReportTemplate.getId());
			result.put("sysReportTemplateId", sysReportTemplate.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
