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
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.sqlparser.TranlateFactory;
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/systemDBFuncMapping")
@RequestMapping("/datamgr/systemDBFuncMapping")
public class SystemDBFuncMappingController {
	protected static final Log logger = LogFactory.getLog(SystemDBFuncMappingController.class);

	protected SystemDBFuncMappingService systemDBFuncMappingService;

	public SystemDBFuncMappingController() {

	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.systemDBFuncMappingService")
	public void setSystemDBFuncMappingService(SystemDBFuncMappingService systemDBFuncMappingService) {
		this.systemDBFuncMappingService = systemDBFuncMappingService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SystemDBFuncMapping systemDBFuncMapping = new SystemDBFuncMapping();
		Tools.populate(systemDBFuncMapping, params);

		systemDBFuncMapping.setFuncId(request.getParameter("funcId"));
		systemDBFuncMapping.setDtype(request.getParameter("dtype"));
		systemDBFuncMapping.setFuncName(request.getParameter("funcName"));
		systemDBFuncMapping.setParams(request.getParameter("params"));
		systemDBFuncMapping.setCreateBy(request.getParameter("createBy"));
		systemDBFuncMapping.setCreateTime(RequestUtils.getDate(request, "createTime"));
		systemDBFuncMapping.setUpdateBy(request.getParameter("updateBy"));
		systemDBFuncMapping.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		systemDBFuncMapping.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		// systemDBFuncMapping.setCreateBy(actorId);

		systemDBFuncMappingService.save(systemDBFuncMapping);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSystemDBFuncMapping")
	public byte[] saveSystemDBFuncMapping(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFuncMapping systemDBFuncMapping = new SystemDBFuncMapping();
		try {
			Tools.populate(systemDBFuncMapping, params);
			systemDBFuncMapping.setFuncId(request.getParameter("funcId"));
			systemDBFuncMapping.setDtype(request.getParameter("dtype"));
			systemDBFuncMapping.setFuncName(request.getParameter("funcName"));
			systemDBFuncMapping.setParams(request.getParameter("params"));
			systemDBFuncMapping.setCreateBy(request.getParameter("createBy"));
			systemDBFuncMapping.setCreateTime(RequestUtils.getDate(request, "createTime"));
			systemDBFuncMapping.setUpdateBy(request.getParameter("updateBy"));
			systemDBFuncMapping.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
			systemDBFuncMapping.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			// systemDBFuncMapping.setCreateBy(actorId);
			this.systemDBFuncMappingService.save(systemDBFuncMapping);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SystemDBFuncMapping saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SystemDBFuncMapping systemDBFuncMapping = new SystemDBFuncMapping();
		try {
			Tools.populate(systemDBFuncMapping, model);
			systemDBFuncMapping.setFuncId(ParamUtils.getString(model, "funcId"));
			systemDBFuncMapping.setDtype(ParamUtils.getString(model, "dtype"));
			systemDBFuncMapping.setFuncName(ParamUtils.getString(model, "funcName"));
			systemDBFuncMapping.setParams(ParamUtils.getString(model, "params"));
			systemDBFuncMapping.setCreateBy(ParamUtils.getString(model, "createBy"));
			systemDBFuncMapping.setCreateTime(ParamUtils.getDate(model, "createTime"));
			systemDBFuncMapping.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			systemDBFuncMapping.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
			systemDBFuncMapping.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			systemDBFuncMapping.setCreateBy(actorId);
			this.systemDBFuncMappingService.save(systemDBFuncMapping);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return systemDBFuncMapping;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SystemDBFuncMapping systemDBFuncMapping = systemDBFuncMappingService
				.getSystemDBFuncMapping(request.getParameter("id"));

		Tools.populate(systemDBFuncMapping, params);

		systemDBFuncMapping.setFuncId(request.getParameter("funcId"));
		systemDBFuncMapping.setDtype(request.getParameter("dtype"));
		systemDBFuncMapping.setFuncName(request.getParameter("funcName"));
		systemDBFuncMapping.setParams(request.getParameter("params"));
		systemDBFuncMapping.setCreateBy(request.getParameter("createBy"));
		systemDBFuncMapping.setCreateTime(RequestUtils.getDate(request, "createTime"));
		systemDBFuncMapping.setUpdateBy(request.getParameter("updateBy"));
		systemDBFuncMapping.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		systemDBFuncMapping.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		systemDBFuncMappingService.save(systemDBFuncMapping);

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
					SystemDBFuncMapping systemDBFuncMapping = systemDBFuncMappingService
							.getSystemDBFuncMapping(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (systemDBFuncMapping != null
							&& (StringUtils.equals(systemDBFuncMapping.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// systemDBFuncMapping.setDeleteFlag(1);
						systemDBFuncMappingService.save(systemDBFuncMapping);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SystemDBFuncMapping systemDBFuncMapping = systemDBFuncMappingService
					.getSystemDBFuncMapping(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (systemDBFuncMapping != null
					&& (StringUtils.equals(systemDBFuncMapping.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// systemDBFuncMapping.setDeleteFlag(1);
				systemDBFuncMappingService.save(systemDBFuncMapping);
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
		SystemDBFuncMapping systemDBFuncMapping = systemDBFuncMappingService
				.getSystemDBFuncMapping(request.getParameter("id"));
		if (systemDBFuncMapping != null) {
			request.setAttribute("systemDBFuncMapping", systemDBFuncMapping);
		}
		
		request.setAttribute("mapping", TranlateFactory.getMapping());

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("systemDBFuncMapping.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/systemDBFuncMapping/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFuncMapping systemDBFuncMapping = systemDBFuncMappingService
				.getSystemDBFuncMapping(request.getParameter("id"));
		request.setAttribute("systemDBFuncMapping", systemDBFuncMapping);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("systemDBFuncMapping.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/systemDBFuncMapping/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("systemDBFuncMapping.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/systemDBFuncMapping/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFuncMappingQuery query = new SystemDBFuncMappingQuery();
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
		int total = systemDBFuncMappingService.getSystemDBFuncMappingCountByQueryCriteria(query);
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
			List<SystemDBFuncMapping> list = systemDBFuncMappingService.getSystemDBFuncMappingsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SystemDBFuncMapping systemDBFuncMapping : list) {
					JSONObject rowJSON = systemDBFuncMapping.toJsonObject();
					rowJSON.put("id", systemDBFuncMapping.getId());
					rowJSON.put("rowId", systemDBFuncMapping.getId());
					rowJSON.put("systemDBFuncMappingId", systemDBFuncMapping.getId());
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
		SystemDBFuncMappingQuery query = new SystemDBFuncMappingQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SystemDBFuncMappingDomainFactory.processDataRequest(dataRequest);

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
		int total = systemDBFuncMappingService.getSystemDBFuncMappingCountByQueryCriteria(query);
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
			List<SystemDBFuncMapping> list = systemDBFuncMappingService.getSystemDBFuncMappingsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SystemDBFuncMapping systemDBFuncMapping : list) {
					JSONObject rowJSON = systemDBFuncMapping.toJsonObject();
					rowJSON.put("id", systemDBFuncMapping.getId());
					rowJSON.put("systemDBFuncMappingId", systemDBFuncMapping.getId());
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

		return new ModelAndView("/datamgr/systemDBFuncMapping/list", modelMap);
	}

}
