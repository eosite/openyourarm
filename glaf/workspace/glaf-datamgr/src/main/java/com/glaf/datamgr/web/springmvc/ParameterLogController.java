package com.glaf.datamgr.web.springmvc;

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

import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/parameterLog")
@RequestMapping("/sys/parameterLog")
public class ParameterLogController {
	protected static final Log logger = LogFactory.getLog(ParameterLogController.class);

	protected ParameterLogService parameterLogService;

	public ParameterLogController() {

	}

	@RequestMapping("/delete")
	@ResponseBody
	public byte[] delete(HttpServletRequest request) throws IOException {
		String type = request.getParameter("type");
		String businessKey = request.getParameter("businessKey");
		if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(businessKey)) {
			parameterLogService.deleteTodayParameterLogs(type, businessKey);
			return ResponseUtils.responseResult(true);
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/deleteOverdue")
	@ResponseBody
	public byte[] deleteOverdue(HttpServletRequest request) throws IOException {
		Date now = new Date();
		Date dateBefore = DateUtils.getDateBefore(now, 30);
		parameterLogService.deleteOverdueParameterLogs(dateBefore);
		return ResponseUtils.responseResult(true);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ParameterLogQuery query = new ParameterLogQuery();
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

		String dateAfter = request.getParameter("dateAfter");
		if (StringUtils.isNotEmpty(dateAfter)) {
			Date date = null;

			if ("1D".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY);
			} else if ("2D".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 2);
			} else if ("3D".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 3);
			} else if ("4D".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 4);
			} else if ("5D".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 5);
			} else if ("6D".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 6);
			} else if ("1W".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 7);
			} else if ("2W".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 14);
			} else if ("1M".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 30);
			} else if ("2M".equals(dateAfter)) {
				date = new Date(System.currentTimeMillis() - DateUtils.DAY * 60);
			}

			if (date != null) {
				query.createTimeGreaterThanOrEqual(date);
			}
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
		int total = parameterLogService.getParameterLogCountByQueryCriteria(query);
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

			List<ParameterLog> list = parameterLogService.getParameterLogsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ParameterLog parameterLog : list) {
					JSONObject rowJSON = parameterLog.toJsonObject();
					rowJSON.put("id", parameterLog.getId());
					rowJSON.put("rowId", parameterLog.getId());
					rowJSON.put("parameterLogId", parameterLog.getId());
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

		return new ModelAndView("/datamgr/parameterLog/list", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ParameterLogQuery query = new ParameterLogQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ParameterLogDomainFactory.processDataRequest(dataRequest);

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
		int total = parameterLogService.getParameterLogCountByQueryCriteria(query);
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

			List<ParameterLog> list = parameterLogService.getParameterLogsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ParameterLog parameterLog : list) {
					JSONObject rowJSON = parameterLog.toJsonObject();
					rowJSON.put("id", parameterLog.getId());
					rowJSON.put("parameterLogId", parameterLog.getId());
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

	@javax.annotation.Resource
	public void setParameterLogService(ParameterLogService parameterLogService) {
		this.parameterLogService = parameterLogService;
	}

}
