package com.glaf.monitor.server.web.springmvc;

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

import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;
import com.glaf.monitor.server.service.*;
import com.glaf.monitor.server.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/monitor/monitorTerminalBusCategory")
@RequestMapping("/monitor/monitorTerminalBusCategory")
public class MonitorTerminalBusCategoryController {
	protected static final Log logger = LogFactory.getLog(MonitorTerminalBusCategoryController.class);

	protected MonitorTerminalBusCategoryService monitorTerminalBusCategoryService;

	public MonitorTerminalBusCategoryController() {

	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorTerminalBusCategoryService")
	public void setMonitorTerminalBusCategoryService(
			MonitorTerminalBusCategoryService monitorTerminalBusCategoryService) {
		this.monitorTerminalBusCategoryService = monitorTerminalBusCategoryService;
	}

	@ResponseBody
	@RequestMapping("/saveMonitorTerminalBusCategory")
	public byte[] saveMonitorTerminalBusCategory(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String categoryIds = RequestUtils.getParameter(request, "categoryIds");
		String terminalId = RequestUtils.getParameter(request, "terminalId");
		MonitorTerminalBusCategory monitorTerminalBusCategory = new MonitorTerminalBusCategory();
		try {
			if(StringUtils.isEmpty(terminalId) || StringUtils.isEmpty(categoryIds)){
				return ResponseUtils.responseJsonResult(false,"无终端或分类信息!");
			}
			this.monitorTerminalBusCategoryService.save(terminalId,categoryIds);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String terminalId = RequestUtils.getString(request, "terminalId");
		String terminalIds = request.getParameter("terminalIds");
		if (StringUtils.isNotEmpty(terminalIds)) {
			StringTokenizer token = new StringTokenizer(terminalIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MonitorTerminalBusCategory monitorTerminalBusCategory = monitorTerminalBusCategoryService
							.getMonitorTerminalBusCategory(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (terminalId != null) {
			MonitorTerminalBusCategory monitorTerminalBusCategory = monitorTerminalBusCategoryService
					.getMonitorTerminalBusCategory(String.valueOf(terminalId));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
		}
		return ResponseUtils.responseResult(false);
	}


	@RequestMapping("/list")
	@ResponseBody
	public byte[] list(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalBusCategoryQuery query = new MonitorTerminalBusCategoryQuery();
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

		String orderName = null;
		String order = null;

		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		JSONObject result = new JSONObject();
		int total = monitorTerminalBusCategoryService.getMonitorTerminalBusCategoryCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<MonitorTerminalBusCategory> list = monitorTerminalBusCategoryService
					.list(query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorTerminalBusCategory monitorTerminalBusCategory : list) {
					JSONObject rowJSON = monitorTerminalBusCategory.toJsonObject();
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

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalBusCategoryQuery query = new MonitorTerminalBusCategoryQuery();
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
		int total = monitorTerminalBusCategoryService.getMonitorTerminalBusCategoryCountByQueryCriteria(query);
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
			List<MonitorTerminalBusCategory> list = monitorTerminalBusCategoryService
					.getMonitorTerminalBusCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorTerminalBusCategory monitorTerminalBusCategory : list) {
					JSONObject rowJSON = monitorTerminalBusCategory.toJsonObject();
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
		MonitorTerminalBusCategoryQuery query = new MonitorTerminalBusCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		MonitorTerminalBusCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorTerminalBusCategoryService.getMonitorTerminalBusCategoryCountByQueryCriteria(query);
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
			List<MonitorTerminalBusCategory> list = monitorTerminalBusCategoryService
					.getMonitorTerminalBusCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorTerminalBusCategory monitorTerminalBusCategory : list) {
					JSONObject rowJSON = monitorTerminalBusCategory.toJsonObject();
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

}
