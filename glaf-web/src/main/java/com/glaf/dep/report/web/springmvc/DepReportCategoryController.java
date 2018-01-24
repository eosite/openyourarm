package com.glaf.dep.report.web.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
import com.glaf.dep.report.domain.DepReportCategory;
import com.glaf.dep.report.query.DepReportCategoryQuery;
import com.glaf.dep.report.service.DepReportCategoryService;
import com.glaf.dep.report.util.DepReportCategoryDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/report/depReportCategory")
@RequestMapping("/dep/report/depReportCategory")
public class DepReportCategoryController {
	protected static final Log logger = LogFactory.getLog(DepReportCategoryController.class);

	protected DepReportCategoryService depReportCategoryService;

	@Autowired
	protected DepBaseCategoryService depBaseCategoryService;

	public DepReportCategoryController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportCategoryService")
	public void setDepReportCategoryService(DepReportCategoryService depReportCategoryService) {
		this.depReportCategoryService = depReportCategoryService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepReportCategory depReportCategory = new DepReportCategory();
		Tools.populate(depReportCategory, params);

		depReportCategory.setCode(request.getParameter("code"));
		depReportCategory.setName(request.getParameter("name"));
		depReportCategory.setTreeId(request.getParameter("treeId"));
		depReportCategory.setPId(RequestUtils.getLong(request, "pId"));
		depReportCategory.setCreator(request.getParameter("creator"));
		depReportCategory.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
		depReportCategory.setModifier(request.getParameter("modifier"));
		depReportCategory.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		depReportCategory.setDelFlag(request.getParameter("delFlag"));

		// depReportCategory.setCreateBy(actorId);

		depReportCategoryService.save(depReportCategory);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepReportCategory")
	public byte[] saveDepReportCategory(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportCategory depReportCategory = new DepReportCategory();
		try {
			Tools.populate(depReportCategory, params);
			depReportCategory.setCode(request.getParameter("code"));
			depReportCategory.setName(request.getParameter("name"));
			depReportCategory.setTreeId(request.getParameter("treeId"));
			depReportCategory.setPId(RequestUtils.getLong(request, "pId"));
			depReportCategory.setCreator(request.getParameter("creator"));
			depReportCategory.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
			depReportCategory.setModifier(request.getParameter("modifier"));
			depReportCategory.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
			depReportCategory.setDelFlag(request.getParameter("delFlag"));
			// depReportCategory.setCreateBy(actorId);
			if (depReportCategory.getId() == null) {
				depReportCategory.setCreator(actorId);
				depReportCategory.setCreateDateTime(new Date());

			} else {
				depReportCategory.setModifyDateTime(new Date());
				depReportCategory.setModifier(actorId);
			}
			this.depReportCategoryService.save(depReportCategory);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DepReportCategory saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepReportCategory depReportCategory = new DepReportCategory();
		try {
			Tools.populate(depReportCategory, model);
			depReportCategory.setCode(ParamUtils.getString(model, "code"));
			depReportCategory.setName(ParamUtils.getString(model, "name"));
			depReportCategory.setTreeId(ParamUtils.getString(model, "treeId"));
			depReportCategory.setPId(ParamUtils.getLong(model, "pId"));
			depReportCategory.setCreator(ParamUtils.getString(model, "creator"));
			depReportCategory.setCreateDateTime(ParamUtils.getDate(model, "createDateTime"));
			depReportCategory.setModifier(ParamUtils.getString(model, "modifier"));
			depReportCategory.setModifyDateTime(ParamUtils.getDate(model, "modifyDateTime"));
			depReportCategory.setDelFlag(ParamUtils.getString(model, "delFlag"));
			// depReportCategory.setCreateBy(actorId);
			this.depReportCategoryService.save(depReportCategory);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depReportCategory;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepReportCategory depReportCategory = depReportCategoryService
				.getDepReportCategory(RequestUtils.getLong(request, "id"));

		Tools.populate(depReportCategory, params);

		depReportCategory.setCode(request.getParameter("code"));
		depReportCategory.setName(request.getParameter("name"));
		depReportCategory.setTreeId(request.getParameter("treeId"));
		depReportCategory.setPId(RequestUtils.getLong(request, "pId"));
		depReportCategory.setCreator(request.getParameter("creator"));
		depReportCategory.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
		depReportCategory.setModifier(request.getParameter("modifier"));
		depReportCategory.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		depReportCategory.setDelFlag(request.getParameter("delFlag"));

		depReportCategoryService.save(depReportCategory);

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
					DepReportCategory depReportCategory = depReportCategoryService
							.getDepReportCategory(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depReportCategory != null
							&& (StringUtils.equals(depReportCategory.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// depReportCategory.setDeleteFlag(1);
						depReportCategoryService.save(depReportCategory);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepReportCategory depReportCategory = depReportCategoryService.getDepReportCategory(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depReportCategory != null
					&& (StringUtils.equals(depReportCategory.getCreator(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// depReportCategory.setDeleteFlag(1);
				depReportCategoryService.save(depReportCategory);
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
		DepReportCategory depReportCategory = depReportCategoryService
				.getDepReportCategory(RequestUtils.getLong(request, "id"));
		if (depReportCategory != null) {
			request.setAttribute("depReportCategory", depReportCategory);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depReportCategory.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/report/depReportCategory/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportCategory depReportCategory = depReportCategoryService
				.getDepReportCategory(RequestUtils.getLong(request, "id"));
		request.setAttribute("depReportCategory", depReportCategory);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depReportCategory.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/report/depReportCategory/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depReportCategory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/report/depReportCategory/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		// 治疗乱码问题
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportCategoryQuery query = new DepReportCategoryQuery();
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
		if(params.containsKey("pId") && params.get("pId")!=null){
			query.setTreeIdLike((String)params.get("pId"));
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
		int total = depReportCategoryService.getDepReportCategoryCountByQueryCriteria(query);
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
			List<DepReportCategory> list = depReportCategoryService.getDepReportCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepReportCategory depReportCategory : list) {
					JSONObject rowJSON = depReportCategory.toJsonObject();
					rowJSON.put("id", depReportCategory.getId());
					rowJSON.put("rowId", depReportCategory.getId());
					rowJSON.put("depReportCategoryId", depReportCategory.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}

		DepBaseCategoryQuery queryBase = new DepBaseCategoryQuery();
		queryBase.setPid(-1L);

		List<DepBaseCategory> dbcs = depBaseCategoryService.list(queryBase);
		JSONArray jar = new JSONArray();
		if (CollectionUtils.isNotEmpty(dbcs)) {
			for (DepBaseCategory dbc : dbcs) {
				dbc.setId((dbc.getId() + 1) * 3333);// 转换主分类id避免跟cell 分类id冲突
				JSONObject job = dbc.toJsonObject();
				job.put("t", "main"); // 主分类
				jar.add(job);
			}
		}
		result.put("parentRows", jar);
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportCategoryQuery query = new DepReportCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepReportCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = depReportCategoryService.getDepReportCategoryCountByQueryCriteria(query);
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
			List<DepReportCategory> list = depReportCategoryService.getDepReportCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepReportCategory depReportCategory : list) {
					JSONObject rowJSON = depReportCategory.toJsonObject();
					rowJSON.put("id", depReportCategory.getId());
					rowJSON.put("depReportCategoryId", depReportCategory.getId());
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

		return new ModelAndView("/dep/report/depReportCategory/list", modelMap);
	}

}
