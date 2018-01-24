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

@Controller("/monitor/monitorProcCategory")
@RequestMapping("/monitor/monitorProcCategory")
public class MonitorProcCategoryController {
	protected static final Log logger = LogFactory.getLog(MonitorProcCategoryController.class);

	protected MonitorProcCategoryService monitorProcCategoryService;

	public MonitorProcCategoryController() {

	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorProcCategoryService")
	public void setMonitorProcCategoryService(MonitorProcCategoryService monitorProcCategoryService) {
		this.monitorProcCategoryService = monitorProcCategoryService;
	}

	/**
	 * 查询树形数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/treeData")
	@ResponseBody
	public byte[] treeData(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcCategoryQuery query = new MonitorProcCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		
		String orderName = RequestUtils.getParameter(request, "orderName");
		String order = RequestUtils.getParameter(request, "order");

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}
		
		if(query.getPid() == null){
			query.setPid(-1);
		}

		JSONObject result = new JSONObject();
		int total = monitorProcCategoryService.getMonitorProcCategoryCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}else{
				query.setSortColumn("createtime");
				query.setSortOrder(" desc ");
			}
			List<MonitorProcCategory> list = monitorProcCategoryService.list(query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (MonitorProcCategory monitorCategory : list) {
					JSONObject rowJSON = monitorCategory.toJsonObject();
					rowJSON.put("id", monitorCategory.getId());
					rowJSON.put("monitorProcCategoryId", monitorCategory.getId());
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
	
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		MonitorProcCategory monitorProcCategory = new MonitorProcCategory();
		Tools.populate(monitorProcCategory, params);
		
		Date nowDate = new Date();
		if(monitorProcCategory.getId() == null){
			monitorProcCategory.setCreateby(actorId);
			monitorProcCategory.setCreatetime(nowDate);
			monitorProcCategory.setDeleteFlag(0);
		}else{
			monitorProcCategory.setUpdateby(actorId);
			monitorProcCategory.setUpdatetime(nowDate);
		}

		monitorProcCategoryService.save(monitorProcCategory);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveMonitorProcCategory")
	public byte[] saveMonitorProcCategory(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcCategory monitorProcCategory = new MonitorProcCategory();
		try {
			Tools.populate(monitorProcCategory, params);
			
			Date nowDate = new Date();
			if(monitorProcCategory.getId() == null){
				//新增节点
				//父节点的indexId
				Integer parentIndexId = ParamUtils.getIntValue(params, "parentIndexId");
				//父节点的treeId
				String parentTreeId = ParamUtils.getString(params, "parentTreeId");
				
				monitorProcCategory.setCreateby(actorId);
				monitorProcCategory.setCreatetime(nowDate);
				monitorProcCategory.setDeleteFlag(0);
				
				monitorProcCategoryService.add(monitorProcCategory,parentIndexId,parentTreeId);
			}else{
				monitorProcCategory.setUpdateby(actorId);
				monitorProcCategory.setUpdatetime(nowDate);
				
				this.monitorProcCategoryService.save(monitorProcCategory);
			}
			
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody MonitorProcCategory saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		MonitorProcCategory monitorProcCategory = new MonitorProcCategory();
		try {
			Tools.populate(monitorProcCategory, model);
			monitorProcCategory.setName(ParamUtils.getString(model, "name"));
			monitorProcCategory.setCode(ParamUtils.getString(model, "code"));
			monitorProcCategory.setPid(ParamUtils.getInt(model, "pid"));
			monitorProcCategory.setTreeid(ParamUtils.getString(model, "treeid"));
			monitorProcCategory.setCreateby(ParamUtils.getString(model, "createby"));
			monitorProcCategory.setCreatetime(ParamUtils.getDate(model, "createtime"));
			monitorProcCategory.setUpdateby(ParamUtils.getString(model, "updateby"));
			monitorProcCategory.setUpdatetime(ParamUtils.getDate(model, "updatetime"));
			monitorProcCategory.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			this.monitorProcCategoryService.save(monitorProcCategory);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return monitorProcCategory;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		MonitorProcCategory monitorProcCategory = monitorProcCategoryService
				.getMonitorProcCategory(RequestUtils.getInt(request, "id"));

		Tools.populate(monitorProcCategory, params);

		monitorProcCategory.setName(request.getParameter("name"));
		monitorProcCategory.setCode(request.getParameter("code"));
		monitorProcCategory.setPid(RequestUtils.getInt(request, "pid"));
		monitorProcCategory.setTreeid(request.getParameter("treeid"));
		monitorProcCategory.setCreateby(request.getParameter("createby"));
		monitorProcCategory.setCreatetime(RequestUtils.getDate(request, "createtime"));
		monitorProcCategory.setUpdateby(request.getParameter("updateby"));
		monitorProcCategory.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
		monitorProcCategory.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		monitorProcCategoryService.save(monitorProcCategory);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Integer id = RequestUtils.getInteger(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MonitorProcCategory monitorProcCategory = monitorProcCategoryService
							.getMonitorProcCategory(Integer.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorProcCategory monitorProcCategory = monitorProcCategoryService
					.getMonitorProcCategory(Integer.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (monitorProcCategory != null) {
				monitorProcCategory.setDeleteFlag(1);
				monitorProcCategoryService.save(monitorProcCategory);
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
		MonitorProcCategory monitorProcCategory = monitorProcCategoryService
				.getMonitorProcCategory(RequestUtils.getInt(request, "id"));
		if (monitorProcCategory != null) {
			request.setAttribute("monitorProcCategory", monitorProcCategory);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("monitorProcCategory.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/monitor/monitorProcCategory/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcCategory monitorProcCategory = monitorProcCategoryService
				.getMonitorProcCategory(RequestUtils.getInt(request, "id"));
		request.setAttribute("monitorProcCategory", monitorProcCategory);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("monitorProcCategory.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/monitor/monitorProcCategory/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("monitorProcCategory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/monitor/monitorProcCategory/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcCategoryQuery query = new MonitorProcCategoryQuery();
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
		int total = monitorProcCategoryService.getMonitorProcCategoryCountByQueryCriteria(query);
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
			}else{
				query.setSortColumn("createtime");
				query.setSortOrder(" desc ");
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<MonitorProcCategory> list = monitorProcCategoryService.getMonitorProcCategorysByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorProcCategory monitorProcCategory : list) {
					JSONObject rowJSON = monitorProcCategory.toJsonObject();
					rowJSON.put("id", monitorProcCategory.getId());
					rowJSON.put("rowId", monitorProcCategory.getId());
					rowJSON.put("monitorProcCategoryId", monitorProcCategory.getId());
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
		MonitorProcCategoryQuery query = new MonitorProcCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		MonitorProcCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorProcCategoryService.getMonitorProcCategoryCountByQueryCriteria(query);
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
			List<MonitorProcCategory> list = monitorProcCategoryService.getMonitorProcCategorysByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorProcCategory monitorProcCategory : list) {
					JSONObject rowJSON = monitorProcCategory.toJsonObject();
					rowJSON.put("id", monitorProcCategory.getId());
					rowJSON.put("monitorProcCategoryId", monitorProcCategory.getId());
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

		return new ModelAndView("/monitor/monitorProcCategory/list", modelMap);
	}

}
