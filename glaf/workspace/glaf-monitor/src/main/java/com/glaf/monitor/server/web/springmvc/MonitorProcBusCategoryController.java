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

@Controller("/monitor/monitorProcBusCategory")
@RequestMapping("/monitor/monitorProcBusCategory")
public class MonitorProcBusCategoryController {
	protected static final Log logger = LogFactory.getLog(MonitorProcBusCategoryController.class);

	protected MonitorProcBusCategoryService monitorProcBusCategoryService;

	public MonitorProcBusCategoryController() {

	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorProcBusCategoryService")
	public void setMonitorProcBusCategoryService(MonitorProcBusCategoryService monitorProcBusCategoryService) {
		this.monitorProcBusCategoryService = monitorProcBusCategoryService;
	}

	@RequestMapping("/list")
	@ResponseBody
	public byte[] list(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcBusCategoryQuery query = new MonitorProcBusCategoryQuery();
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
		int total = monitorProcBusCategoryService.getMonitorProcBusCategoryCountByQueryCriteria(query);
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
			List<MonitorProcBusCategory> list = monitorProcBusCategoryService
					.list(query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorProcBusCategory monitorTerminalBusCategory : list) {
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

	@ResponseBody
	@RequestMapping("/saveMonitorProcBusCategory")
	public byte[] saveMonitorProcBusCategory(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcBusCategory monitorProcBusCategory = new MonitorProcBusCategory();
		try {
			String categoryIds = RequestUtils.getParameter(request, "categoryIds");
			String id = RequestUtils.getParameter(request, "prodId");
			
			if(StringUtils.isEmpty(id) || StringUtils.isEmpty(categoryIds)){
				return ResponseUtils.responseJsonResult(false,"无终端或分类信息!");
			}
			this.monitorProcBusCategoryService.save(id,categoryIds);
			
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody MonitorProcBusCategory saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		MonitorProcBusCategory monitorProcBusCategory = new MonitorProcBusCategory();
		try {
			Tools.populate(monitorProcBusCategory, model);
			monitorProcBusCategory.setProcCategoryId(ParamUtils.getInt(model, "procCategoryId"));
			monitorProcBusCategory.setCreateby(ParamUtils.getString(model, "createby"));
			monitorProcBusCategory.setCreatetime(ParamUtils.getDate(model, "createtime"));
			this.monitorProcBusCategoryService.save(monitorProcBusCategory);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return monitorProcBusCategory;
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
					MonitorProcBusCategory monitorProcBusCategory = monitorProcBusCategoryService
							.getMonitorProcBusCategory(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorProcBusCategory monitorProcBusCategory = monitorProcBusCategoryService
					.getMonitorProcBusCategory(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
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
		MonitorProcBusCategory monitorProcBusCategory = monitorProcBusCategoryService
				.getMonitorProcBusCategory(request.getParameter("id"));
		if (monitorProcBusCategory != null) {
			request.setAttribute("monitorProcBusCategory", monitorProcBusCategory);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("monitorProcBusCategory.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/monitor/monitorProcBusCategory/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcBusCategory monitorProcBusCategory = monitorProcBusCategoryService
				.getMonitorProcBusCategory(request.getParameter("id"));
		request.setAttribute("monitorProcBusCategory", monitorProcBusCategory);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("monitorProcBusCategory.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/monitor/monitorProcBusCategory/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("monitorProcBusCategory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/monitor/monitorProcBusCategory/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcBusCategoryQuery query = new MonitorProcBusCategoryQuery();
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
		int total = monitorProcBusCategoryService.getMonitorProcBusCategoryCountByQueryCriteria(query);
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
			List<MonitorProcBusCategory> list = monitorProcBusCategoryService
					.getMonitorProcBusCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorProcBusCategory monitorProcBusCategory : list) {
					JSONObject rowJSON = monitorProcBusCategory.toJsonObject();
					rowJSON.put("id", monitorProcBusCategory.getId());
					rowJSON.put("rowId", monitorProcBusCategory.getId());
					rowJSON.put("monitorProcBusCategoryId", monitorProcBusCategory.getId());
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
		MonitorProcBusCategoryQuery query = new MonitorProcBusCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		MonitorProcBusCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorProcBusCategoryService.getMonitorProcBusCategoryCountByQueryCriteria(query);
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
			List<MonitorProcBusCategory> list = monitorProcBusCategoryService
					.getMonitorProcBusCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorProcBusCategory monitorProcBusCategory : list) {
					JSONObject rowJSON = monitorProcBusCategory.toJsonObject();
					rowJSON.put("id", monitorProcBusCategory.getId());
					rowJSON.put("monitorProcBusCategoryId", monitorProcBusCategory.getId());
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
