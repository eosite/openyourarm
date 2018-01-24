package com.glaf.report.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.glaf.report.core.domain.ReportTemplate;
import com.glaf.report.core.query.ReportTemplateQuery;
import com.glaf.report.core.service.ReportTemplateService;
import com.glaf.report.core.util.ReportTemplateDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/reportTemplate")
@RequestMapping("/reportTemplate")
public class ReportTemplateController {
	protected static final Log logger = LogFactory.getLog(ReportTemplateController.class);

	protected ReportTemplateService reportTemplateService;

	public ReportTemplateController() {

	}

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTemplateService")
	public void setReportTemplateService(ReportTemplateService reportTemplateService) {
		this.reportTemplateService = reportTemplateService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTemplate reportTemplate = new ReportTemplate();
		Tools.populate(reportTemplate, params);

		reportTemplate.setRev(RequestUtils.getInt(request, "rev"));
		reportTemplate.setName(request.getParameter("name"));
		reportTemplate.setCode(request.getParameter("code"));

		reportTemplate.setCreator(request.getParameter("creator"));
		reportTemplate.setCreateDatatime(RequestUtils.getDate(request, "createDatatime"));
		reportTemplate.setModifier(request.getParameter("modifier"));
		reportTemplate.setModifyDatatime(RequestUtils.getDate(request, "modifyDatatime"));
		reportTemplate.setStatus(RequestUtils.getInt(request, "status"));
		reportTemplate.setPublish(RequestUtils.getInt(request, "publish"));
		reportTemplate.setPublishUser(request.getParameter("publishUser"));
		reportTemplate.setPublishDatetime(RequestUtils.getDate(request, "publishDatetime"));
		reportTemplate.setFileName(request.getParameter("fileName"));
		reportTemplate.setExt(request.getParameter("ext"));

		// reportTemplate.setCreateBy(actorId);

		reportTemplateService.save(reportTemplate);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveReportTemplate")
	public byte[] saveReportTemplate(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTemplate reportTemplate = new ReportTemplate();
		try {
			Tools.populate(reportTemplate, params);
			reportTemplate.setRev(RequestUtils.getInt(request, "rev"));
			reportTemplate.setName(request.getParameter("name"));
			reportTemplate.setCode(request.getParameter("code"));

			reportTemplate.setCreator(request.getParameter("creator"));
			reportTemplate.setCreateDatatime(RequestUtils.getDate(request, "createDatatime"));
			reportTemplate.setModifier(request.getParameter("modifier"));
			reportTemplate.setModifyDatatime(RequestUtils.getDate(request, "modifyDatatime"));
			reportTemplate.setStatus(RequestUtils.getInt(request, "status"));
			reportTemplate.setPublish(RequestUtils.getInt(request, "publish"));
			reportTemplate.setPublishUser(request.getParameter("publishUser"));
			reportTemplate.setPublishDatetime(RequestUtils.getDate(request, "publishDatetime"));
			reportTemplate.setFileName(request.getParameter("fileName"));
			reportTemplate.setExt(request.getParameter("ext"));
			// reportTemplate.setCreateBy(actorId);
			this.reportTemplateService.save(reportTemplate);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReportTemplate saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ReportTemplate reportTemplate = new ReportTemplate();
		try {
			Tools.populate(reportTemplate, model);
			reportTemplate.setRev(ParamUtils.getInt(model, "rev"));
			reportTemplate.setName(ParamUtils.getString(model, "name"));
			reportTemplate.setCode(ParamUtils.getString(model, "code"));

			reportTemplate.setCreator(ParamUtils.getString(model, "creator"));
			reportTemplate.setCreateDatatime(ParamUtils.getDate(model, "createDatatime"));
			reportTemplate.setModifier(ParamUtils.getString(model, "modifier"));
			reportTemplate.setModifyDatatime(ParamUtils.getDate(model, "modifyDatatime"));
			reportTemplate.setStatus(ParamUtils.getInt(model, "status"));
			reportTemplate.setPublish(ParamUtils.getInt(model, "publish"));
			reportTemplate.setPublishUser(ParamUtils.getString(model, "publishUser"));
			reportTemplate.setPublishDatetime(ParamUtils.getDate(model, "publishDatetime"));
			reportTemplate.setFileName(ParamUtils.getString(model, "fileName"));
			reportTemplate.setExt(ParamUtils.getString(model, "ext"));
			reportTemplate.setCreator(actorId);
			this.reportTemplateService.save(reportTemplate);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return reportTemplate;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTemplate reportTemplate = reportTemplateService.getReportTemplate(request.getParameter("id"));

		Tools.populate(reportTemplate, params);

		reportTemplate.setRev(RequestUtils.getInt(request, "rev"));
		reportTemplate.setName(request.getParameter("name"));
		reportTemplate.setCode(request.getParameter("code"));

		reportTemplate.setCreator(request.getParameter("creator"));
		reportTemplate.setCreateDatatime(RequestUtils.getDate(request, "createDatatime"));
		reportTemplate.setModifier(request.getParameter("modifier"));
		reportTemplate.setModifyDatatime(RequestUtils.getDate(request, "modifyDatatime"));
		reportTemplate.setStatus(RequestUtils.getInt(request, "status"));
		reportTemplate.setPublish(RequestUtils.getInt(request, "publish"));
		reportTemplate.setPublishUser(request.getParameter("publishUser"));
		reportTemplate.setPublishDatetime(RequestUtils.getDate(request, "publishDatetime"));
		reportTemplate.setFileName(request.getParameter("fileName"));
		reportTemplate.setExt(request.getParameter("ext"));

		reportTemplateService.save(reportTemplate);

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
					ReportTemplate reportTemplate = reportTemplateService.getReportTemplate(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (reportTemplate != null
							&& (StringUtils.equals(reportTemplate.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// reportTemplate.setDeleteFlag(1);
						reportTemplateService.save(reportTemplate);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReportTemplate reportTemplate = reportTemplateService.getReportTemplate(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (reportTemplate != null && (StringUtils.equals(reportTemplate.getCreator(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// reportTemplate.setDeleteFlag(1);
				reportTemplateService.save(reportTemplate);
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
		ReportTemplate reportTemplate = reportTemplateService.getReportTemplate(request.getParameter("id"));
		if (reportTemplate != null) {
			request.setAttribute("reportTemplate", reportTemplate);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTemplate.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/reportTemplate/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTemplate reportTemplate = reportTemplateService.getReportTemplate(request.getParameter("id"));
		request.setAttribute("reportTemplate", reportTemplate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTemplate.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/reportTemplate/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTemplate.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/reportTemplate/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTemplateQuery query = new ReportTemplateQuery();
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
		int total = reportTemplateService.getReportTemplateCountByQueryCriteria(query);
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
			List<ReportTemplate> list = reportTemplateService.getReportTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTemplate reportTemplate : list) {
					JSONObject rowJSON = reportTemplate.toJsonObject();
					rowJSON.put("id", reportTemplate.getId());
					rowJSON.put("rowId", reportTemplate.getId());
					rowJSON.put("reportTemplateId", reportTemplate.getId());
					rowJSON.put("startIndex", ++start);
					System.out.println(reportTemplate.getReportTmpDataSets());
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

	@RequestMapping("/getTemplateById")
	@ResponseBody
	public byte[] getTemplateById(HttpServletRequest request, ModelMap modelMap) throws IOException {

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String id = ParamUtils.getString(params, "id");

		ReportTemplate reportTemplate = reportTemplateService.getReportTemplate(id);
		if (reportTemplate == null)
			return null;
		reportTemplate.setBytes(null);

		JSONObject result = new JSONObject();

		result.put("data", reportTemplate);

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTemplateQuery query = new ReportTemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReportTemplateDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTemplateService.getReportTemplateCountByQueryCriteria(query);
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
			List<ReportTemplate> list = reportTemplateService.getReportTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTemplate reportTemplate : list) {
					JSONObject rowJSON = reportTemplate.toJsonObject();
					rowJSON.put("id", reportTemplate.getId());
					rowJSON.put("reportTemplateId", reportTemplate.getId());
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
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/apps/reportTemplate/list", modelMap);
	}

	@RequestMapping("/templates")
	public ModelAndView templates(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String url = "/report/report_list";
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		modelMap.put("actorId", loginContext.getActorId());
		return new ModelAndView(url, modelMap);
	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		String url = "/report/report_template_choose";
		return new ModelAndView(url, modelMap);
	}
}
