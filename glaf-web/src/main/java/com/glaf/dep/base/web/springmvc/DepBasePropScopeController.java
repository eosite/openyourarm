package com.glaf.dep.base.web.springmvc;

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

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;
import com.glaf.dep.base.service.*;
import com.glaf.dep.base.util.*;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/base/depBasePropScope")
@RequestMapping("/dep/base/depBasePropScope")
public class DepBasePropScopeController {
	protected static final Log logger = LogFactory
			.getLog(DepBasePropScopeController.class);

	protected DepBasePropScopeService depBasePropScopeService;

	public DepBasePropScopeController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBasePropScopeService")
	public void setDepBasePropScopeService(
			DepBasePropScopeService depBasePropScopeService) {
		this.depBasePropScopeService = depBasePropScopeService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBasePropScope depBasePropScope = new DepBasePropScope();
		Tools.populate(depBasePropScope, params);

		depBasePropScope.setDepBaseUIId(request.getParameter("depBaseUIId"));
		depBasePropScope.setCreator(request.getParameter("creator"));
		depBasePropScope.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));

		// depBasePropScope.setCreateBy(actorId);

		depBasePropScopeService.save(depBasePropScope);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepBasePropScope")
	public byte[] saveDepBasePropScope(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropScope depBasePropScope = new DepBasePropScope();
		try {
			Tools.populate(depBasePropScope, params);
			depBasePropScope
					.setDepBaseUIId(request.getParameter("depBaseUIId"));
			depBasePropScope.setCreator(request.getParameter("creator"));
			depBasePropScope.setCreateDateTime(RequestUtils.getDate(request,
					"createDateTime"));
			// depBasePropScope.setCreateBy(actorId);
			this.depBasePropScopeService.save(depBasePropScope);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	DepBasePropScope saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepBasePropScope depBasePropScope = new DepBasePropScope();
		try {
			Tools.populate(depBasePropScope, model);
			depBasePropScope.setDepBaseUIId(ParamUtils.getString(model,
					"depBaseUIId"));
			depBasePropScope.setCreator(ParamUtils.getString(model, "creator"));
			depBasePropScope.setCreateDateTime(ParamUtils.getDate(model,
					"createDateTime"));
			depBasePropScope.setCreator(actorId);
			this.depBasePropScopeService.save(depBasePropScope);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depBasePropScope;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBasePropScope depBasePropScope = new DepBasePropScope();

		Tools.populate(depBasePropScope, params);

		depBasePropScope.setDepBaseUIId(request.getParameter("depBaseUIId"));
		depBasePropScope.setCreator(request.getParameter("creator"));
		depBasePropScope.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));

		depBasePropScopeService.save(depBasePropScope);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String ruleId = RequestUtils.getString(request, "ruleId");
		String ruleIds = request.getParameter("ruleIds");
		if (StringUtils.isNotEmpty(ruleIds)) {
			StringTokenizer token = new StringTokenizer(ruleIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DepBasePropScope depBasePropScope = new DepBasePropScope();
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depBasePropScope != null
							&& (StringUtils.equals(
									depBasePropScope.getCreator(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// depBasePropScope.setDeleteFlag(1);
						depBasePropScopeService.save(depBasePropScope);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (ruleId != null) {
			DepBasePropScope depBasePropScope = new DepBasePropScope();
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depBasePropScope != null
					&& (StringUtils.equals(depBasePropScope.getCreator(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// depBasePropScope.setDeleteFlag(1);
				depBasePropScopeService.save(depBasePropScope);
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
		DepBasePropScope depBasePropScope = new DepBasePropScope();
		if (depBasePropScope != null) {
			request.setAttribute("depBasePropScope", depBasePropScope);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depBasePropScope.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/base/depBasePropScope/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropScope depBasePropScope = new DepBasePropScope();
		request.setAttribute("depBasePropScope", depBasePropScope);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depBasePropScope.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/base/depBasePropScope/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depBasePropScope.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/base/depBasePropScope/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropScopeQuery query = new DepBasePropScopeQuery();
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
		int total = depBasePropScopeService
				.getDepBasePropScopeCountByQueryCriteria(query);
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
			List<DepBasePropScope> list = depBasePropScopeService
					.getDepBasePropScopesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBasePropScope depBasePropScope : list) {
					JSONObject rowJSON = depBasePropScope.toJsonObject();
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
		DepBasePropScopeQuery query = new DepBasePropScopeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepBasePropScopeDomainFactory.processDataRequest(dataRequest);

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
		int total = depBasePropScopeService
				.getDepBasePropScopeCountByQueryCriteria(query);
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
			List<DepBasePropScope> list = depBasePropScopeService
					.getDepBasePropScopesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBasePropScope depBasePropScope : list) {
					JSONObject rowJSON = depBasePropScope.toJsonObject();
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

		return new ModelAndView("/dep/base/depBasePropScope/list", modelMap);
	}

}
