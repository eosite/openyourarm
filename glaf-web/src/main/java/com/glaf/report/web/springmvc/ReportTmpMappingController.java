package com.glaf.report.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.CollectionUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.query.DataSetQuery;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.report.core.domain.ReportTmpDataSetMapping;
import com.glaf.report.core.domain.ReportTmpMapping;
import com.glaf.report.core.query.ReportTmpMappingQuery;
import com.glaf.report.core.service.ReportTmpMappingService;
import com.glaf.report.core.util.ReportTmpMappingDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/report/reportTmpMapping")
@RequestMapping("/report/reportTmpMapping")
public class ReportTmpMappingController {
	protected static final Log logger = LogFactory.getLog(ReportTmpMappingController.class);

	protected ReportTmpMappingService reportTmpMappingService;

	protected DataSetAuditService dataSetAuditService;

	protected DataSetService dataSetService;

	public ReportTmpMappingController() {

	}

	@javax.annotation.Resource
	public void setDataSetAuditService(DataSetAuditService dataSetAuditService) {
		this.dataSetAuditService = dataSetAuditService;
	}

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpMappingService")
	public void setReportTmpMappingService(ReportTmpMappingService reportTmpMappingService) {
		this.reportTmpMappingService = reportTmpMappingService;
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTmpMapping reportTmpMapping = new ReportTmpMapping();
		Tools.populate(reportTmpMapping, params);

		reportTmpMapping.setSystemId(request.getParameter("systemId"));
		reportTmpMapping.setTemplateId(request.getParameter("templateId"));
		reportTmpMapping.setTemplateCode(request.getParameter("templateCode"));
		reportTmpMapping.setTemplateName(request.getParameter("templateName"));
		reportTmpMapping.setDesc(request.getParameter("desc"));
		reportTmpMapping.setCreator(request.getParameter("creator"));
		reportTmpMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		reportTmpMapping.setModifier(request.getParameter("modifier"));
		reportTmpMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		reportTmpMapping.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		// reportTmpMapping.setCreator(actorId);

		reportTmpMappingService.save(reportTmpMapping);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveReportTmpMapping")
	public byte[] saveReportTmpMapping(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpMapping reportTmpMapping = new ReportTmpMapping();
		try {
			Tools.populate(reportTmpMapping, params);
			reportTmpMapping.setSystemId(request.getParameter("systemId"));
			reportTmpMapping.setTemplateId(request.getParameter("templateId"));
			reportTmpMapping.setTemplateCode(request.getParameter("templateCode"));
			reportTmpMapping.setTemplateName(request.getParameter("templateName"));
			reportTmpMapping.setDesc(request.getParameter("desc"));
			reportTmpMapping.setCreator(request.getParameter("creator"));
			reportTmpMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
			reportTmpMapping.setModifier(request.getParameter("modifier"));
			reportTmpMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
			reportTmpMapping.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			// reportTmpMapping.setCreator(actorId);
			this.reportTmpMappingService.save(reportTmpMapping);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReportTmpMapping saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ReportTmpMapping reportTmpMapping = new ReportTmpMapping();
		try {
			Tools.populate(reportTmpMapping, model);
			reportTmpMapping.setSystemId(ParamUtils.getString(model, "systemId"));
			reportTmpMapping.setTemplateId(ParamUtils.getString(model, "templateId"));
			reportTmpMapping.setTemplateCode(ParamUtils.getString(model, "templateCode"));
			reportTmpMapping.setTemplateName(ParamUtils.getString(model, "templateName"));
			reportTmpMapping.setDesc(ParamUtils.getString(model, "desc"));
			reportTmpMapping.setCreator(ParamUtils.getString(model, "creator"));
			reportTmpMapping.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
			reportTmpMapping.setModifier(ParamUtils.getString(model, "modifier"));
			reportTmpMapping.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
			reportTmpMapping.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			reportTmpMapping.setCreator(actorId);
			this.reportTmpMappingService.save(reportTmpMapping);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return reportTmpMapping;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTmpMapping reportTmpMapping = reportTmpMappingService.getReportTmpMapping(request.getParameter("id"));

		Tools.populate(reportTmpMapping, params);

		reportTmpMapping.setSystemId(request.getParameter("systemId"));
		reportTmpMapping.setTemplateId(request.getParameter("templateId"));
		reportTmpMapping.setTemplateCode(request.getParameter("templateCode"));
		reportTmpMapping.setTemplateName(request.getParameter("templateName"));
		reportTmpMapping.setDesc(request.getParameter("desc"));
		reportTmpMapping.setCreator(request.getParameter("creator"));
		reportTmpMapping.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		reportTmpMapping.setModifier(request.getParameter("modifier"));
		reportTmpMapping.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		reportTmpMapping.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		reportTmpMappingService.save(reportTmpMapping);

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
					ReportTmpMapping reportTmpMapping = reportTmpMappingService.getReportTmpMapping(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (reportTmpMapping != null
							&& (StringUtils.equals(reportTmpMapping.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						reportTmpMapping.setDeleteFlag(1);
						reportTmpMappingService.save(reportTmpMapping);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReportTmpMapping reportTmpMapping = reportTmpMappingService.getReportTmpMapping(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (reportTmpMapping != null
					&& (StringUtils.equals(reportTmpMapping.getCreator(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				reportTmpMapping.setDeleteFlag(1);
				reportTmpMappingService.save(reportTmpMapping);
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
		ReportTmpMapping reportTmpMapping = reportTmpMappingService.getReportTmpMapping(request.getParameter("id"));
		if (reportTmpMapping != null) {
			request.setAttribute("reportTmpMapping", reportTmpMapping);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTmpMapping.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/report/reportTmpMapping/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpMapping reportTmpMapping = reportTmpMappingService.getReportTmpMapping(request.getParameter("id"));
		request.setAttribute("reportTmpMapping", reportTmpMapping);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTmpMapping.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/report/reportTmpMapping/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTmpMapping.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/report/reportTmpMapping/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpMappingQuery query = new ReportTmpMappingQuery();
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
		int total = reportTmpMappingService.getReportTmpMappingCountByQueryCriteria(query);
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
			List<ReportTmpMapping> list = reportTmpMappingService.getReportTmpMappingsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTmpMapping reportTmpMapping : list) {
					JSONObject rowJSON = reportTmpMapping.toJsonObject();
					rowJSON.put("id", reportTmpMapping.getId());
					rowJSON.put("rowId", reportTmpMapping.getId());
					rowJSON.put("reportTmpMappingId", reportTmpMapping.getId());
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

	@RequestMapping("/getTempMappingById")
	@ResponseBody
	public byte[] getTemplateById(HttpServletRequest request, ModelMap modelMap) throws IOException {

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String id = ParamUtils.getString(params, "id");

		ReportTmpMapping reportTmpMapping = this.reportTmpMappingService.getReportTmpMapping(id);

		JSONObject result = new JSONObject();

		result.put("data", reportTmpMapping);

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpMappingQuery query = new ReportTmpMappingQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReportTmpMappingDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTmpMappingService.getReportTmpMappingCountByQueryCriteria(query);
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
			List<ReportTmpMapping> list = reportTmpMappingService.getReportTmpMappingsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTmpMapping reportTmpMapping : list) {
					JSONObject rowJSON = reportTmpMapping.toJsonObject();
					rowJSON.put("id", reportTmpMapping.getId());
					rowJSON.put("reportTmpMappingId", reportTmpMapping.getId());
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

	/**
	 * 根据映射模版id 获取数据集所有参数
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParamsByMappingId")
	public byte[] getParamsByMappingId(HttpServletRequest request) throws IOException {
		JSONObject result = new JSONObject();
		String mappingId = RequestUtils.getString(request, "mappingId");
		ReportTmpMapping tm = this.reportTmpMappingService.getReportTmpMapping(mappingId);
		if (tm != null) {
			List<ReportTmpDataSetMapping> dataSets = tm.getReportTmpDataSetMappings();
			if (CollectionUtils.isNotEmpty(dataSets)) {
				List<JSONObject> array = new ArrayList<JSONObject>();
				for (ReportTmpDataSetMapping dataSet : dataSets) {
					String datasetId = dataSet.getMappingDataSetId();
					if (StringUtils.isNotBlank(datasetId)) {
						DataSetQuery query = new DataSetQuery();
						List<String> datasetIds = new ArrayList<String>();
						datasetIds.add(datasetId);
						query.setDatasetIds(datasetIds);
						List<DataSet> list = this.dataSetService.list(query);
						if (CollectionUtils.isNotEmpty(list)) {
							DataSet ds = list.get(0);
							DataSetAudit dataSetAudit = this.dataSetAuditService.getLastestDataSetAudit(ds.getId());
							if (dataSetAudit != null) {
								String content = dataSetAudit.getContent();
								if (StringUtils.isNotBlank(content)) {
									JSONObject jsonObject = JSON.parseObject(content);
									JSONObject row = new JSONObject();
									row.put("id", dataSet.getDataSetCode());
									row.put("text", dataSet.getDataSetName());
									if (StringUtils.isNotBlank(jsonObject.getString("params"))) {
										row.put("items", jsonObject.getJSONArray("params"));
									}
									array.add(row);
								}
							}
						}
					}
				}
				result.put("data", array);
			}
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

		return new ModelAndView("/report/reportTmpMapping/list", modelMap);
	}

}
