package com.glaf.report.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.report.core.domain.SysReportTemplate;
import com.glaf.report.core.query.SysReportTemplateQuery;
import com.glaf.report.core.service.SysReportTemplateService;
import com.glaf.report.core.util.SysReportTemplateDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/report/sysReportTemplate")
@RequestMapping("/report/sysReportTemplate")
public class SysReportTemplateController {
	protected static final Log logger = LogFactory
			.getLog(SysReportTemplateController.class);

	protected SysReportTemplateService sysReportTemplateService;

	public SysReportTemplateController() {

	}

	@javax.annotation.Resource(name = "com.glaf.report.service.sysReportTemplateService")
	public void setSysReportTemplateService(
			SysReportTemplateService sysReportTemplateService) {
		this.sysReportTemplateService = sysReportTemplateService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysReportTemplate sysReportTemplate = new SysReportTemplate();
		Tools.populate(sysReportTemplate, params);

		sysReportTemplate.setReportTemplateName(request
				.getParameter("reportTemplateName"));
		sysReportTemplate.setCtime(RequestUtils.getDate(request, "ctime"));
		sysReportTemplate.setUtime(RequestUtils.getDate(request, "utime"));

		// sysReportTemplate.setCreateBy(actorId);

		sysReportTemplateService.save(sysReportTemplate);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSysReportTemplate")
	public byte[] saveSysReportTemplate(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplate sysReportTemplate = new SysReportTemplate();
		try {
			Tools.populate(sysReportTemplate, params);
			sysReportTemplate.setReportTemplateName(request
					.getParameter("reportTemplateName"));
			sysReportTemplate.setCtime(RequestUtils.getDate(request, "ctime"));
			sysReportTemplate.setUtime(RequestUtils.getDate(request, "utime"));
			// sysReportTemplate.setCreateBy(actorId);
			this.sysReportTemplateService.save(sysReportTemplate);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysReportTemplate saveOrUpdate(
			HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SysReportTemplate sysReportTemplate = new SysReportTemplate();
		try {
			Tools.populate(sysReportTemplate, model);
			sysReportTemplate.setReportTemplateName(ParamUtils.getString(model,
					"reportTemplateName"));
			sysReportTemplate.setCtime(ParamUtils.getDate(model, "ctime"));
			sysReportTemplate.setUtime(ParamUtils.getDate(model, "utime"));
			this.sysReportTemplateService.save(sysReportTemplate);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sysReportTemplate;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysReportTemplate sysReportTemplate = sysReportTemplateService
				.getSysReportTemplate(request.getParameter("id"));

		Tools.populate(sysReportTemplate, params);

		sysReportTemplate.setReportTemplateName(request
				.getParameter("reportTemplateName"));
		sysReportTemplate.setCtime(RequestUtils.getDate(request, "ctime"));
		sysReportTemplate.setUtime(RequestUtils.getDate(request, "utime"));

		sysReportTemplateService.save(sysReportTemplate);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysReportTemplate sysReportTemplate = sysReportTemplateService
							.getSysReportTemplate(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (sysReportTemplate != null
							&& (StringUtils.equals("",
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// sysReportTemplate.setDeleteFlag(1);
						sysReportTemplateService.save(sysReportTemplate);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysReportTemplate sysReportTemplate = sysReportTemplateService
					.getSysReportTemplate(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (sysReportTemplate != null
					&& (StringUtils.equals("", loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// sysReportTemplate.setDeleteFlag(1);
				sysReportTemplateService.save(sysReportTemplate);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplate sysReportTemplate = sysReportTemplateService
				.getSysReportTemplate(request.getParameter("id"));
		if (sysReportTemplate != null) {
			request.setAttribute("sysReportTemplate", sysReportTemplate);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysReportTemplate.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/report/sysReportTemplate/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplate sysReportTemplate = sysReportTemplateService
				.getSysReportTemplate(request.getParameter("id"));
		request.setAttribute("sysReportTemplate", sysReportTemplate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysReportTemplate.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/report/sysReportTemplate/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysReportTemplate.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/report/sysReportTemplate/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplateQuery query = new SysReportTemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
			limit = Paging.DEFAULT_PAGE_SIZE;
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
					rowJSON.put("rowId", sysReportTemplate.getId());
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

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysReportTemplateQuery query = new SysReportTemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysReportTemplateDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/report/sysReportTemplate/list", modelMap);
	}

}
