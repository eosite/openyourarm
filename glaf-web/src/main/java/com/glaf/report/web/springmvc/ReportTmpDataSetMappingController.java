package com.glaf.report.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;
import com.glaf.report.core.service.*;
import com.glaf.report.core.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/report/reportTmpDataSetMapping")
@RequestMapping("/report/reportTmpDataSetMapping")
public class ReportTmpDataSetMappingController {
	protected static final Log logger = LogFactory.getLog(ReportTmpDataSetMappingController.class);

	protected ReportTmpDataSetMappingService reportTmpDataSetMappingService;

	public ReportTmpDataSetMappingController() {

	}

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpDataSetMappingService")
	public void setReportTmpDataSetMappingService(ReportTmpDataSetMappingService reportTmpDataSetMappingService) {
		this.reportTmpDataSetMappingService = reportTmpDataSetMappingService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTmpDataSetMapping reportTmpDataSetMapping = new ReportTmpDataSetMapping();
		Tools.populate(reportTmpDataSetMapping, params);

		reportTmpDataSetMapping.setTmpMappingId(request.getParameter("tmpMappingId"));
		reportTmpDataSetMapping.setTemplateId(request.getParameter("templateId"));
		reportTmpDataSetMapping.setDataSetCode(request.getParameter("dataSetCode"));
		reportTmpDataSetMapping.setDataSetName(request.getParameter("dataSetName"));
		reportTmpDataSetMapping.setMappingDataSetId(request.getParameter("mappingDataSetId"));
		reportTmpDataSetMapping.setMappingDataSetCode(request.getParameter("mappingDataSetCode"));
		reportTmpDataSetMapping.setMappingDataSetName(request.getParameter("mappingDataSetName"));
		reportTmpDataSetMapping.setCreator(request.getParameter("creator"));
		reportTmpDataSetMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		reportTmpDataSetMapping.setModifier(request.getParameter("modifier"));
		reportTmpDataSetMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

		// reportTmpDataSetMapping.setCreator(actorId);

		reportTmpDataSetMappingService.save(reportTmpDataSetMapping);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveReportTmpDataSetMappingStuff")
	public byte[] saveReportTmpDataSetMappingStuff(HttpServletRequest request) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		JSONObject json = new JSONObject(params);

		try {
			ReportTmpMapping reportTmpMapping = JSON.parseObject(json.getString("reportTmpMapping"),
					ReportTmpMapping.class);

			reportTmpDataSetMappingService.deleteByParentId(reportTmpMapping.getId());

			this.reportTmpDataSetMappingService.save(reportTmpMapping.getReportTmpDataSetMappings());

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveReportTmpDataSetMapping")
	public byte[] saveReportTmpDataSetMapping(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSetMapping reportTmpDataSetMapping = new ReportTmpDataSetMapping();
		try {
			Tools.populate(reportTmpDataSetMapping, params);
			reportTmpDataSetMapping.setTmpMappingId(request.getParameter("tmpMappingId"));
			reportTmpDataSetMapping.setTemplateId(request.getParameter("templateId"));
			reportTmpDataSetMapping.setDataSetCode(request.getParameter("dataSetCode"));
			reportTmpDataSetMapping.setDataSetName(request.getParameter("dataSetName"));
			reportTmpDataSetMapping.setMappingDataSetId(request.getParameter("mappingDataSetId"));
			reportTmpDataSetMapping.setMappingDataSetCode(request.getParameter("mappingDataSetCode"));
			reportTmpDataSetMapping.setMappingDataSetName(request.getParameter("mappingDataSetName"));
			reportTmpDataSetMapping.setCreator(request.getParameter("creator"));
			reportTmpDataSetMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
			reportTmpDataSetMapping.setModifier(request.getParameter("modifier"));
			reportTmpDataSetMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
			// reportTmpDataSetMapping.setCreator(actorId);
			this.reportTmpDataSetMappingService.save(reportTmpDataSetMapping);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReportTmpDataSetMapping saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ReportTmpDataSetMapping reportTmpDataSetMapping = new ReportTmpDataSetMapping();
		try {
			Tools.populate(reportTmpDataSetMapping, model);
			reportTmpDataSetMapping.setTmpMappingId(ParamUtils.getString(model, "tmpMappingId"));
			reportTmpDataSetMapping.setTemplateId(ParamUtils.getString(model, "templateId"));
			reportTmpDataSetMapping.setDataSetCode(ParamUtils.getString(model, "dataSetCode"));
			reportTmpDataSetMapping.setDataSetName(ParamUtils.getString(model, "dataSetName"));
			reportTmpDataSetMapping.setMappingDataSetId(ParamUtils.getString(model, "mappingDataSetId"));
			reportTmpDataSetMapping.setMappingDataSetCode(ParamUtils.getString(model, "mappingDataSetCode"));
			reportTmpDataSetMapping.setMappingDataSetName(ParamUtils.getString(model, "mappingDataSetName"));
			reportTmpDataSetMapping.setCreator(ParamUtils.getString(model, "creator"));
			reportTmpDataSetMapping.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
			reportTmpDataSetMapping.setModifier(ParamUtils.getString(model, "modifier"));
			reportTmpDataSetMapping.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
			reportTmpDataSetMapping.setCreator(actorId);
			this.reportTmpDataSetMappingService.save(reportTmpDataSetMapping);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return reportTmpDataSetMapping;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTmpDataSetMapping reportTmpDataSetMapping = reportTmpDataSetMappingService
				.getReportTmpDataSetMapping(request.getParameter("id"));

		Tools.populate(reportTmpDataSetMapping, params);

		reportTmpDataSetMapping.setTmpMappingId(request.getParameter("tmpMappingId"));
		reportTmpDataSetMapping.setTemplateId(request.getParameter("templateId"));
		reportTmpDataSetMapping.setDataSetCode(request.getParameter("dataSetCode"));
		reportTmpDataSetMapping.setDataSetName(request.getParameter("dataSetName"));
		reportTmpDataSetMapping.setMappingDataSetId(request.getParameter("mappingDataSetId"));
		reportTmpDataSetMapping.setMappingDataSetCode(request.getParameter("mappingDataSetCode"));
		reportTmpDataSetMapping.setMappingDataSetName(request.getParameter("mappingDataSetName"));
		reportTmpDataSetMapping.setCreator(request.getParameter("creator"));
		reportTmpDataSetMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		reportTmpDataSetMapping.setModifier(request.getParameter("modifier"));
		reportTmpDataSetMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

		reportTmpDataSetMappingService.save(reportTmpDataSetMapping);

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
					ReportTmpDataSetMapping reportTmpDataSetMapping = reportTmpDataSetMappingService
							.getReportTmpDataSetMapping(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (reportTmpDataSetMapping != null
							&& (StringUtils.equals(reportTmpDataSetMapping.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// reportTmpDataSetMapping.setDeleteFlag(1);
						reportTmpDataSetMappingService.save(reportTmpDataSetMapping);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReportTmpDataSetMapping reportTmpDataSetMapping = reportTmpDataSetMappingService
					.getReportTmpDataSetMapping(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (reportTmpDataSetMapping != null
					&& (StringUtils.equals(reportTmpDataSetMapping.getCreator(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// reportTmpDataSetMapping.setDeleteFlag(1);
				reportTmpDataSetMappingService.save(reportTmpDataSetMapping);
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
		ReportTmpDataSetMapping reportTmpDataSetMapping = reportTmpDataSetMappingService
				.getReportTmpDataSetMapping(request.getParameter("id"));
		if (reportTmpDataSetMapping != null) {
			request.setAttribute("reportTmpDataSetMapping", reportTmpDataSetMapping);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTmpDataSetMapping.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/report/reportTmpDataSetMapping/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSetMapping reportTmpDataSetMapping = reportTmpDataSetMappingService
				.getReportTmpDataSetMapping(request.getParameter("id"));
		request.setAttribute("reportTmpDataSetMapping", reportTmpDataSetMapping);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTmpDataSetMapping.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/report/reportTmpDataSetMapping/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTmpDataSetMapping.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/report/reportTmpDataSetMapping/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSetMappingQuery query = new ReportTmpDataSetMappingQuery();
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
		int total = reportTmpDataSetMappingService.getReportTmpDataSetMappingCountByQueryCriteria(query);
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
			List<ReportTmpDataSetMapping> list = reportTmpDataSetMappingService
					.getReportTmpDataSetMappingsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTmpDataSetMapping reportTmpDataSetMapping : list) {
					JSONObject rowJSON = reportTmpDataSetMapping.toJsonObject();
					rowJSON.put("id", reportTmpDataSetMapping.getId());
					rowJSON.put("rowId", reportTmpDataSetMapping.getId());
					rowJSON.put("reportTmpDataSetMappingId", reportTmpDataSetMapping.getId());
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
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSetMappingQuery query = new ReportTmpDataSetMappingQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReportTmpDataSetMappingDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTmpDataSetMappingService.getReportTmpDataSetMappingCountByQueryCriteria(query);
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
			List<ReportTmpDataSetMapping> list = reportTmpDataSetMappingService
					.getReportTmpDataSetMappingsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTmpDataSetMapping reportTmpDataSetMapping : list) {
					JSONObject rowJSON = reportTmpDataSetMapping.toJsonObject();
					rowJSON.put("id", reportTmpDataSetMapping.getId());
					rowJSON.put("reportTmpDataSetMappingId", reportTmpDataSetMapping.getId());
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

		return new ModelAndView("/report/reportTmpDataSetMapping/list", modelMap);
	}

}
