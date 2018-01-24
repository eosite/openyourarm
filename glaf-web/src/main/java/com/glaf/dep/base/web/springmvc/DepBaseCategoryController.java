package com.glaf.dep.base.web.springmvc;

import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.util.HtmlUtils;

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
import com.glaf.dep.base.domain.DepBaseCategory;
import com.glaf.dep.base.query.DepBaseCategoryQuery;
import com.glaf.dep.base.service.DepBaseCategoryService;
import com.glaf.dep.base.util.DepBaseCategoryDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/base/depBaseCategory")
@RequestMapping("/dep/base/depBaseCategory")
public class DepBaseCategoryController {
	protected static final Log logger = LogFactory
			.getLog(DepBaseCategoryController.class);

	protected DepBaseCategoryService depBaseCategoryService;

	public DepBaseCategoryController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseCategoryService")
	public void setDepBaseCategoryService(
			DepBaseCategoryService depBaseCategoryService) {
		this.depBaseCategoryService = depBaseCategoryService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseCategory depBaseCategory = new DepBaseCategory();
		Tools.populate(depBaseCategory, params);

		depBaseCategory.setCode(request.getParameter("code"));
		depBaseCategory.setName(request.getParameter("name"));
		depBaseCategory.setTreeId(request.getParameter("treeId"));
		depBaseCategory.setExpandFlag(request.getParameter("expandFlag"));
		depBaseCategory.setPid(RequestUtils.getLong(request, "pid"));
		depBaseCategory.setOrderNo(RequestUtils.getInt(request, "orderNo"));
		depBaseCategory.setCreator(request.getParameter("creator"));
		depBaseCategory.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));
		depBaseCategory.setModifier(request.getParameter("modifier"));
		depBaseCategory.setModifyDateTime(RequestUtils.getDate(request,
				"modifyDateTime"));
		depBaseCategory.setDelFlag(request.getParameter("delFlag"));

		// depBaseCategory.setCreateBy(actorId);

		depBaseCategoryService.save(depBaseCategory);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepBaseCategory")
	public byte[] saveDepBaseCategory(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCategory depBaseCategory = new DepBaseCategory();
		try {
			Tools.populate(depBaseCategory, params);
			depBaseCategory.setCode(request.getParameter("code"));
			depBaseCategory.setName(request.getParameter("name"));
			depBaseCategory.setExpandFlag(request.getParameter("expandFlag"));
			depBaseCategory.setPid(RequestUtils.getLong(request, "pid"));
			depBaseCategory.setOrderNo(RequestUtils.getInt(request, "orderNo"));
			depBaseCategory.setToolBarTemplate(request.getParameter("toolBarTemplate"));

			String ptreeid = request.getParameter("ptreeid");
			depBaseCategory.setTreeId(ptreeid);

			if (depBaseCategory.getId() == null) {
				depBaseCategory.setCreator(actorId);
				depBaseCategory.setCreateDateTime(new Date());
			}
			depBaseCategory.setModifier(actorId);
			depBaseCategory.setModifyDateTime(new Date());
			depBaseCategory.setDelFlag("0");
			this.depBaseCategoryService.save(depBaseCategory);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	DepBaseCategory saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepBaseCategory depBaseCategory = new DepBaseCategory();
		try {
			Tools.populate(depBaseCategory, model);
			depBaseCategory.setCode(ParamUtils.getString(model, "code"));
			depBaseCategory.setName(ParamUtils.getString(model, "name"));
			depBaseCategory.setTreeId(ParamUtils.getString(model, "treeId"));
			depBaseCategory.setExpandFlag(ParamUtils.getString(model,
					"expandFlag"));
			depBaseCategory.setPid(ParamUtils.getLong(model, "pid"));
			depBaseCategory.setOrderNo(ParamUtils.getInt(model, "orderNo"));
			depBaseCategory.setCreator(ParamUtils.getString(model, "creator"));
			depBaseCategory.setCreateDateTime(ParamUtils.getDate(model,
					"createDateTime"));
			depBaseCategory
					.setModifier(ParamUtils.getString(model, "modifier"));
			depBaseCategory.setModifyDateTime(ParamUtils.getDate(model,
					"modifyDateTime"));
			depBaseCategory.setDelFlag(ParamUtils.getString(model, "delFlag"));
			depBaseCategory.setCreator(actorId);
			this.depBaseCategoryService.save(depBaseCategory);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depBaseCategory;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseCategory depBaseCategory = depBaseCategoryService
				.getDepBaseCategory(RequestUtils.getLong(request, "id"));

		Tools.populate(depBaseCategory, params);

		depBaseCategory.setCode(request.getParameter("code"));
		depBaseCategory.setName(request.getParameter("name"));
		depBaseCategory.setTreeId(request.getParameter("treeId"));
		depBaseCategory.setExpandFlag(request.getParameter("expandFlag"));
		depBaseCategory.setPid(RequestUtils.getLong(request, "pid"));
		depBaseCategory.setOrderNo(RequestUtils.getInt(request, "orderNo"));
		depBaseCategory.setCreator(request.getParameter("creator"));
		depBaseCategory.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));
		depBaseCategory.setModifier(request.getParameter("modifier"));
		depBaseCategory.setModifyDateTime(RequestUtils.getDate(request,
				"modifyDateTime"));
		depBaseCategory.setDelFlag(request.getParameter("delFlag"));

		depBaseCategoryService.save(depBaseCategory);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DepBaseCategory depBaseCategory = depBaseCategoryService
							.getDepBaseCategory(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depBaseCategory != null
							&& (StringUtils.equals(
									depBaseCategory.getCreator(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// depBaseCategory.setDeleteFlag(1);
						depBaseCategoryService.save(depBaseCategory);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepBaseCategory depBaseCategory = depBaseCategoryService
					.getDepBaseCategory(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depBaseCategory != null
					&& (StringUtils.equals(depBaseCategory.getCreator(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// depBaseCategory.setDeleteFlag(1);
				depBaseCategoryService.save(depBaseCategory);
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
		DepBaseCategory depBaseCategory = depBaseCategoryService
				.getDepBaseCategory(RequestUtils.getLong(request, "id"));

		String pid = params.get("pid").toString();
		if (depBaseCategory != null) {
			depBaseCategory.setToolBarTemplate(HtmlUtils.htmlEscape(depBaseCategory.getToolBarTemplate()));
			request.setAttribute("depBaseCategory", depBaseCategory);
			request.setAttribute("pid", depBaseCategory.getPid());
		} else {
			request.setAttribute("pid", pid);
			depBaseCategory = new DepBaseCategory();

			String code = depBaseCategoryService.getNextCode(Long.parseLong(pid));
			depBaseCategory.setCode(code);
			request.setAttribute("depBaseCategory", depBaseCategory);
		}

		DepBaseCategory pCategory = null;
		if (StringUtils.isNotEmpty(pid)) {
			if (Integer.parseInt(pid) != -1) {
				pCategory = depBaseCategoryService
						.getDepBaseCategory(Long.parseLong(pid));
				request.setAttribute("pCategory", pCategory);
			}
		}
		
		

		return new ModelAndView("/dep/base/depBaseCategory/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCategory depBaseCategory = depBaseCategoryService
				.getDepBaseCategory(RequestUtils.getLong(request, "id"));
		request.setAttribute("depBaseCategory", depBaseCategory);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depBaseCategory.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/base/depBaseCategory/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depBaseCategory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/base/depBaseCategory/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
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
		int total = depBaseCategoryService
				.getDepBaseCategoryCountByQueryCriteria(query);
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
			List<DepBaseCategory> list = depBaseCategoryService
					.getDepBaseCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseCategory depBaseCategory : list) {
					JSONObject rowJSON = depBaseCategory.toJsonObject();
					rowJSON.put("id", depBaseCategory.getId());
					rowJSON.put("rowId", depBaseCategory.getId());
					rowJSON.put("depBaseCategoryId", depBaseCategory.getId());
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
		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepBaseCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseCategoryService
				.getDepBaseCategoryCountByQueryCriteria(query);
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
			List<DepBaseCategory> list = depBaseCategoryService
					.getDepBaseCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseCategory depBaseCategory : list) {
					JSONObject rowJSON = depBaseCategory.toJsonObject();
					rowJSON.put("id", depBaseCategory.getId());
					rowJSON.put("depBaseCategoryId", depBaseCategory.getId());
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

		return new ModelAndView("/dep/base/depBaseCategory/list", modelMap);
	}

	@RequestMapping("/categoryTree")
	public ModelAndView categoryTree(HttpServletRequest request) {
		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
		query.setDelFlag("0");
		query.setPid(-1L);
		query.setOrderBy("ORDERNO_ ASC");
		List<DepBaseCategory> list = depBaseCategoryService.list(query);
		JSONArray result = new JSONArray();
		for (DepBaseCategory model : list) {
			JSONObject rowJSON = model.toJsonObject();
			result.add(rowJSON);
		}
		request.setAttribute("categorys", result.toJSONString());
		return new ModelAndView("/dep/base/depBaseCategory/category_tree");
	}
	
	@RequestMapping("/copyCategoryList")
	public ModelAndView copyCategoryList(HttpServletRequest request) {
		DepBaseCategoryQuery query = new DepBaseCategoryQuery();
		query.setDelFlag("0");
		query.setPid(-1L);
		query.setOrderBy("ORDERNO_ ASC");
		
		String filterId = request.getParameter("filterId");
		if(StringUtils.isNotEmpty(filterId)){
			query.setSqlCondition(" and ID_ not in ("+filterId+")");//过滤指定id数据
		}
		
		List<DepBaseCategory> list = depBaseCategoryService.list(query);
		JSONArray result = new JSONArray();
		for (DepBaseCategory model : list) {
			JSONObject rowJSON = model.toJsonObject();
			result.add(rowJSON);
		}
		request.setAttribute("categorys", result.toJSONString());
		
		return new ModelAndView("/dep/base/depBaseCategory/copy_category");
	}
}