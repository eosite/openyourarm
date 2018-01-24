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

@Controller("/report/reportTmpCol")
@RequestMapping("/report/reportTmpCol")
public class ReportTmpColController {
	protected static final Log logger = LogFactory.getLog(ReportTmpColController.class);

	protected ReportTmpColService reportTmpColService;

	public ReportTmpColController() {

	}

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpColService")
	public void setReportTmpColService(ReportTmpColService reportTmpColService) {
		this.reportTmpColService = reportTmpColService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTmpCol reportTmpCol = new ReportTmpCol();
		Tools.populate(reportTmpCol, params);

		reportTmpCol.setDataSetId(request.getParameter("dataSetId"));
		reportTmpCol.setCode(request.getParameter("code"));
		reportTmpCol.setName(request.getParameter("name"));
		reportTmpCol.setTitle(request.getParameter("title"));
		reportTmpCol.setDtype(request.getParameter("dtype"));
		reportTmpCol.setCreator(request.getParameter("creator"));
		reportTmpCol.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		reportTmpCol.setModifier(request.getParameter("modifier"));
		reportTmpCol.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

		// reportTmpCol.setCreateBy(actorId);

		reportTmpColService.save(reportTmpCol);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveReportTmpCol")
	public byte[] saveReportTmpCol(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpCol reportTmpCol = new ReportTmpCol();
		try {
			Tools.populate(reportTmpCol, params);
			reportTmpCol.setDataSetId(request.getParameter("dataSetId"));
			reportTmpCol.setCode(request.getParameter("code"));
			reportTmpCol.setName(request.getParameter("name"));
			reportTmpCol.setTitle(request.getParameter("title"));
			reportTmpCol.setDtype(request.getParameter("dtype"));
			reportTmpCol.setCreator(request.getParameter("creator"));
			reportTmpCol.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
			reportTmpCol.setModifier(request.getParameter("modifier"));
			reportTmpCol.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
			// reportTmpCol.setCreateBy(actorId);
			this.reportTmpColService.save(reportTmpCol);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReportTmpCol saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ReportTmpCol reportTmpCol = new ReportTmpCol();
		try {
			Tools.populate(reportTmpCol, model);
			reportTmpCol.setDataSetId(ParamUtils.getString(model, "dataSetId"));
			reportTmpCol.setCode(ParamUtils.getString(model, "code"));
			reportTmpCol.setName(ParamUtils.getString(model, "name"));
			reportTmpCol.setTitle(ParamUtils.getString(model, "title"));
			reportTmpCol.setDtype(ParamUtils.getString(model, "dtype"));
			reportTmpCol.setCreator(ParamUtils.getString(model, "creator"));
			reportTmpCol.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
			reportTmpCol.setModifier(ParamUtils.getString(model, "modifier"));
			reportTmpCol.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
			reportTmpCol.setCreator(actorId);
			this.reportTmpColService.save(reportTmpCol);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return reportTmpCol;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		ReportTmpCol reportTmpCol = reportTmpColService.getReportTmpCol(request.getParameter("id"));

		Tools.populate(reportTmpCol, params);

		reportTmpCol.setDataSetId(request.getParameter("dataSetId"));
		reportTmpCol.setCode(request.getParameter("code"));
		reportTmpCol.setName(request.getParameter("name"));
		reportTmpCol.setTitle(request.getParameter("title"));
		reportTmpCol.setDtype(request.getParameter("dtype"));
		reportTmpCol.setCreator(request.getParameter("creator"));
		reportTmpCol.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		reportTmpCol.setModifier(request.getParameter("modifier"));
		reportTmpCol.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

		reportTmpColService.save(reportTmpCol);

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
					ReportTmpCol reportTmpCol = reportTmpColService.getReportTmpCol(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (reportTmpCol != null
							&& (StringUtils.equals(reportTmpCol.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// reportTmpCol.setDeleteFlag(1);
						reportTmpColService.save(reportTmpCol);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReportTmpCol reportTmpCol = reportTmpColService.getReportTmpCol(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (reportTmpCol != null && (StringUtils.equals(reportTmpCol.getCreator(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// reportTmpCol.setDeleteFlag(1);
				reportTmpColService.save(reportTmpCol);
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
		ReportTmpCol reportTmpCol = reportTmpColService.getReportTmpCol(request.getParameter("id"));
		if (reportTmpCol != null) {
			request.setAttribute("reportTmpCol", reportTmpCol);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("reportTmpCol.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/report/reportTmpCol/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpCol reportTmpCol = reportTmpColService.getReportTmpCol(request.getParameter("id"));
		request.setAttribute("reportTmpCol", reportTmpCol);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("reportTmpCol.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/report/reportTmpCol/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("reportTmpCol.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/report/reportTmpCol/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpColQuery query = new ReportTmpColQuery();
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
		int total = reportTmpColService.getReportTmpColCountByQueryCriteria(query);
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
			List<ReportTmpCol> list = reportTmpColService.getReportTmpColsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTmpCol reportTmpCol : list) {
					JSONObject rowJSON = reportTmpCol.toJsonObject();
					rowJSON.put("id", reportTmpCol.getId());
					rowJSON.put("rowId", reportTmpCol.getId());
					rowJSON.put("reportTmpColId", reportTmpCol.getId());
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
		ReportTmpColQuery query = new ReportTmpColQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReportTmpColDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTmpColService.getReportTmpColCountByQueryCriteria(query);
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
			List<ReportTmpCol> list = reportTmpColService.getReportTmpColsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTmpCol reportTmpCol : list) {
					JSONObject rowJSON = reportTmpCol.toJsonObject();
					rowJSON.put("id", reportTmpCol.getId());
					rowJSON.put("reportTmpColId", reportTmpCol.getId());
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

		return new ModelAndView("/report/reportTmpCol/list", modelMap);
	}

}
