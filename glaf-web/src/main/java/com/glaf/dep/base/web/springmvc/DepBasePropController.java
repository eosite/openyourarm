package com.glaf.dep.base.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.glaf.dep.base.domain.DepBaseCompProp;
import com.glaf.dep.base.domain.DepBaseProp;
import com.glaf.dep.base.domain.DepBasePropCategory;
import com.glaf.dep.base.domain.DepBasePropScope;
import com.glaf.dep.base.query.DepBaseCategoryQuery;
import com.glaf.dep.base.query.DepBasePropQuery;
import com.glaf.dep.base.service.DepBaseCategoryService;
import com.glaf.dep.base.service.DepBaseCompPropService;
import com.glaf.dep.base.service.DepBasePropCategoryService;
import com.glaf.dep.base.service.DepBasePropScopeService;
import com.glaf.dep.base.service.DepBasePropService;
import com.glaf.dep.base.util.DepBasePropDomainFactory;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/base/depBaseProp")
@RequestMapping("/dep/base/depBaseProp")
public class DepBasePropController {
	protected static final Log logger = LogFactory
			.getLog(DepBasePropController.class);

	protected DepBasePropService depBasePropService;

	@Resource(name = "com.glaf.dep.base.service.depBasePropCategoryService")
	protected DepBasePropCategoryService depBasePropCategoryService;

	@Resource(name = "com.glaf.dep.base.service.depBasePropScopeService")
	protected DepBasePropScopeService depBasePropScopeService;

	@Resource(name = "com.glaf.dep.base.service.depBaseCompPropService")
	protected DepBaseCompPropService depBaseCompPropService;

	public DepBasePropController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBasePropService")
	public void setDepBasePropService(DepBasePropService depBasePropService) {
		this.depBasePropService = depBasePropService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseProp depBaseProp = new DepBaseProp();
		Tools.populate(depBaseProp, params);

		depBaseProp.setRuleCode(request.getParameter("ruleCode"));
		depBaseProp.setRuleName(request.getParameter("ruleName"));
		depBaseProp.setRuleDesc(request.getParameter("ruleDesc"));
		depBaseProp.setSysCategory(request.getParameter("sysCategory"));
		depBaseProp.setUseCategory(request.getParameter("useCategory"));
		depBaseProp.setOpenFlag(request.getParameter("openFlag"));
		depBaseProp.setOrderNo(RequestUtils.getInt(request, "orderNo"));
		depBaseProp.setReadOnly(request.getParameter("readOnly"));
		depBaseProp.setRepeatFlag(request.getParameter("repeatFlag"));
		depBaseProp.setNotNull(request.getParameter("notNull"));
		depBaseProp.setInputType(request.getParameter("inputType"));
		depBaseProp.setDefaultVal(request.getParameter("defaultVal"));
		depBaseProp.setExtJson(request.getParameter("extJson"));

		if (depBaseProp.getRuleId() == null) {
			depBaseProp.setCreator(actorId);
			depBaseProp.setCreateDateTime(new Date());
		}
		depBaseProp.setModifier(actorId);
		depBaseProp.setModifyDateTime(new Date());
		depBaseProp.setDelFlag("0");

		depBasePropService.save(depBaseProp);

		String categoryId = request.getParameter("categoryId");
		DepBasePropCategory category = new DepBasePropCategory();
		category.setDepBaseCategoryId(Long.parseLong(categoryId));
		category.setRuleId(depBaseProp.getRuleId());
		depBasePropCategoryService.save(category);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepBaseProp")
	public byte[] saveDepBaseProp(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseProp depBaseProp = new DepBaseProp();
		try {
			Tools.populate(depBaseProp, params);
			depBaseProp.setRuleCode(request.getParameter("ruleCode"));
			depBaseProp.setRuleName(request.getParameter("ruleName"));
			depBaseProp.setRuleDesc(request.getParameter("ruleDesc"));
			depBaseProp.setSysCategory(request.getParameter("sysCategory"));
			depBaseProp.setUseCategory(request.getParameter("useCategory"));
			depBaseProp.setOpenFlag(request.getParameter("openFlag"));
			depBaseProp.setOrderNo(RequestUtils.getInt(request, "orderNo"));
			depBaseProp.setReadOnly(request.getParameter("readOnly"));
			depBaseProp.setRepeatFlag(request.getParameter("repeatFlag"));
			depBaseProp.setNotNull(request.getParameter("notNull"));
			depBaseProp.setInputType(request.getParameter("inputType"));
			depBaseProp.setDefaultVal(request.getParameter("defaultVal"));
			if (depBaseProp.getRuleId() == null) {
				depBaseProp.setCreator(actorId);
				depBaseProp.setCreateDateTime(new Date());
			}
			depBaseProp.setModifier(actorId);
			depBaseProp.setModifyDateTime(new Date());
			depBaseProp.setDelFlag("0");

			// 扩展属性
			String extJsonCheck = request.getParameter("extJsonCheck");
			JSONObject extJson = new JSONObject();
			extJson.put("extJsonCheck", BooleanUtils.toBoolean(extJsonCheck));
			if (BooleanUtils.toBoolean(extJsonCheck)) {
				String param = request.getParameter("commonRule");
				if(StringUtils.isNotEmpty(param)){
					extJson.put("commonRule", BooleanUtils.toBoolean(param));
				}
				param = request.getParameter("pageId");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("pageId", param);
				}
				param = request.getParameter("pageName");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("pageName", param);
				}
				param = request.getParameter("pageUrl");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("pageUrl", param);
				}
				param = request.getParameter("dataType");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataType", param);
				}
				param = request.getParameter("labelText");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("labelText", param);
				}
				param = request.getParameter("dataUnit");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataUnit", param);
				}
				param = request.getParameter("dataSource");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataSource", param);
				}
				param = request.getParameter("dictCode");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dictCode", param);
				}
				param = request.getParameter("dataFormatter");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataFormatter", param);
				}
				param = request.getParameter("dataEnum");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataEnum", param);
				}
				param = request.getParameter("dataMinValue");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataMinValue", param);
				}
				param = request.getParameter("dataMaxValue");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataMaxValue", param);
				}
				param = request.getParameter("dataDecimal");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataDecimal", param);
				}
				param = request.getParameter("dataDigit");
				if (StringUtils.isNotEmpty(param)) {
					extJson.put("dataDigit", param);
				}
				param = request.getParameter("customAttr");
				if(StringUtils.isNotEmpty(param)){
					JSONObject json = JSONObject.parseObject(param);
					extJson.put("customAttr", json);
				}
			}
			depBaseProp.setExtJson(extJson.toJSONString());

			depBasePropService.save(depBaseProp);

			// 保存与分类树的关系
			String categoryId = request.getParameter("categoryId");
			DepBasePropCategory category = new DepBasePropCategory();
			category.setDepBaseCategoryId(Long.parseLong(categoryId));
			category.setRuleId(depBaseProp.getRuleId());
			depBasePropCategoryService.save(category);

			// 保存UI适用范围
			String propScopes = request.getParameter("propScope");
			if (StringUtils.isNotEmpty(propScopes)) {
				String[] propScopeArr = propScopes.split(",");
				if (propScopeArr.length > 0) {
					depBasePropScopeService.deleteByRuleId(depBaseProp
							.getRuleId());

					for (int i = 0; i < propScopeArr.length; i++) {
						DepBasePropScope model = new DepBasePropScope();
						model.setRuleId(depBaseProp.getRuleId());
						model.setDepBaseUIId(propScopeArr[i]);
						model.setCreateDateTime(new Date());
						model.setCreator(actorId);
						depBasePropScopeService.save(model);
					}
				}
			}
			// 保存组件适用范围
			String compProps = request.getParameter("compProp");
			if (StringUtils.isNotEmpty(compProps)) {
				String[] compPropArr = compProps.split(",");
				if (compPropArr.length > 0) {
					depBaseCompPropService.deleteByRuleId(depBaseProp
							.getRuleId());

					for (int i = 0; i < compPropArr.length; i++) {
						DepBaseCompProp model = new DepBaseCompProp();
						model.setRuleId(depBaseProp.getRuleId());
						model.setDepBaseComponentId(compPropArr[i]);
						depBaseCompPropService.save(model);
					}
				}
			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	DepBaseProp saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepBaseProp depBaseProp = new DepBaseProp();
		try {
			Tools.populate(depBaseProp, model);
			depBaseProp.setRuleCode(ParamUtils.getString(model, "ruleCode"));
			depBaseProp.setRuleName(ParamUtils.getString(model, "ruleName"));
			depBaseProp.setRuleDesc(ParamUtils.getString(model, "ruleDesc"));
			depBaseProp.setSysCategory(ParamUtils.getString(model,
					"sysCategory"));
			depBaseProp.setUseCategory(ParamUtils.getString(model,
					"useCategory"));
			depBaseProp.setOpenFlag(ParamUtils.getString(model, "openFlag"));
			depBaseProp.setOrderNo(ParamUtils.getInt(model, "orderNo"));
			depBaseProp.setReadOnly(ParamUtils.getString(model, "readOnly"));
			depBaseProp
					.setRepeatFlag(ParamUtils.getString(model, "repeatFlag"));
			depBaseProp.setNotNull(ParamUtils.getString(model, "notNull"));
			depBaseProp.setInputType(ParamUtils.getString(model, "inputType"));
			depBaseProp
					.setDefaultVal(ParamUtils.getString(model, "defaultVal"));
			depBaseProp.setExtJson(ParamUtils.getString(model, "extJson"));
			depBaseProp.setCreator(ParamUtils.getString(model, "creator"));
			depBaseProp.setCreateDateTime(ParamUtils.getDate(model,
					"createDateTime"));
			depBaseProp.setModifier(ParamUtils.getString(model, "modifier"));
			depBaseProp.setModifyDateTime(ParamUtils.getDate(model,
					"modifyDateTime"));
			depBaseProp.setDelFlag(ParamUtils.getString(model, "delFlag"));
			depBaseProp.setCreator(actorId);
			this.depBasePropService.save(depBaseProp);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depBaseProp;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseProp depBaseProp = depBasePropService.getDepBaseProp(request
				.getParameter("ruleId"));

		Tools.populate(depBaseProp, params);

		depBaseProp.setRuleCode(request.getParameter("ruleCode"));
		depBaseProp.setRuleName(request.getParameter("ruleName"));
		depBaseProp.setRuleDesc(request.getParameter("ruleDesc"));
		depBaseProp.setSysCategory(request.getParameter("sysCategory"));
		depBaseProp.setUseCategory(request.getParameter("useCategory"));
		depBaseProp.setOpenFlag(request.getParameter("openFlag"));
		depBaseProp.setOrderNo(RequestUtils.getInt(request, "orderNo"));
		depBaseProp.setReadOnly(request.getParameter("readOnly"));
		depBaseProp.setRepeatFlag(request.getParameter("repeatFlag"));
		depBaseProp.setNotNull(request.getParameter("notNull"));
		depBaseProp.setInputType(request.getParameter("inputType"));
		depBaseProp.setDefaultVal(request.getParameter("defaultVal"));
		depBaseProp.setExtJson(request.getParameter("extJson"));
		depBaseProp.setCreator(request.getParameter("creator"));
		depBaseProp.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));
		depBaseProp.setModifier(request.getParameter("modifier"));
		depBaseProp.setModifyDateTime(RequestUtils.getDate(request,
				"modifyDateTime"));
		depBaseProp.setDelFlag(request.getParameter("delFlag"));

		depBasePropService.save(depBaseProp);

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
					DepBaseProp depBaseProp = depBasePropService
							.getDepBaseProp(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depBaseProp != null
							&& (StringUtils.equals(depBaseProp.getCreator(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// depBaseProp.setDeleteFlag(1);
						depBasePropService.save(depBaseProp);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (ruleId != null) {
			DepBaseProp depBaseProp = depBasePropService.getDepBaseProp(String
					.valueOf(ruleId));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depBaseProp != null
					&& (StringUtils.equals(depBaseProp.getCreator(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// depBaseProp.setDeleteFlag(1);
				depBasePropService.save(depBaseProp);
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
		DepBaseProp depBaseProp = depBasePropService.getDepBaseProp(request
				.getParameter("ruleId"));
		if (depBaseProp != null) {
			request.setAttribute("depBaseProp", depBaseProp);

			// 查询适用UI
			List<DepBasePropScope> depBasePropScopes = depBasePropScopeService
					.getDepBasePropScopesByRuleId(depBaseProp.getRuleId());
			if (depBasePropScopes != null && !depBasePropScopes.isEmpty()) {
				List<String> uiids = new ArrayList<String>();
				for (DepBasePropScope model : depBasePropScopes) {
					uiids.add(model.getDepBaseUIId());
				}
				request.setAttribute("uiid",
						"'" + StringUtils.join(uiids, "','") + "'");
			}
			// 查询适用组件
			List<DepBaseCompProp> depBaseCompProps = depBaseCompPropService
					.getDepBaseCompPropsByRuleId(depBaseProp.getRuleId());
			if (depBaseCompProps != null && !depBaseCompProps.isEmpty()) {
				List<String> compids = new ArrayList<String>();
				for (DepBaseCompProp model : depBaseCompProps) {
					compids.add(model.getDepBaseComponentId());
				}
				request.setAttribute("compids",
						"'" + StringUtils.join(compids, "','") + "'");
			}
		}else{
			depBaseProp = new DepBaseProp();
			
			Long categoryId = 0L;
			if(StringUtils.isNotEmpty(request.getParameter("categoryId"))){
				categoryId = Long.parseLong(request.getParameter("categoryId"));
			}
			String ruleCode = depBasePropService.getNextRuleCode(categoryId);
			depBaseProp.setRuleCode(ruleCode);
			
			int orderNo = depBasePropService.getNextOrderNo();
			depBaseProp.setOrderNo(orderNo);
			
			request.setAttribute("depBaseProp", depBaseProp);
		}

		return new ModelAndView("/dep/base/depBaseProp/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseProp depBaseProp = depBasePropService.getDepBaseProp(request
				.getParameter("ruleId"));
		request.setAttribute("depBaseProp", depBaseProp);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depBaseProp.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/base/depBaseProp/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depBaseProp.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/base/depBaseProp/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBasePropQuery query = new DepBasePropQuery();
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
		int total = depBasePropService
				.getDepBasePropCountByQueryCriteria(query);
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
			List<DepBaseProp> list = depBasePropService
					.getDepBasePropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseProp depBaseProp : list) {
					JSONObject rowJSON = depBaseProp.toJsonObject();
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
		DepBasePropQuery query = new DepBasePropQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepBasePropDomainFactory.processDataRequest(dataRequest);

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
		int total = depBasePropService
				.getDepBasePropCountByQueryCriteria(query);
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
			List<DepBaseProp> list = depBasePropService
					.getDepBasePropsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseProp depBaseProp : list) {
					JSONObject rowJSON = depBaseProp.toJsonObject();
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

		return new ModelAndView("/dep/base/depBaseProp/list", modelMap);
	}

	@RequestMapping("/getPropByCategoryId/{jspfile}")
	public ModelAndView getPropByCategoryId(HttpServletRequest request,
			@PathVariable String jspfile) {
		RequestUtils.setRequestParameterToAttribute(request);

		this.getDictToAttribute(request, "ruleInputType");

		String categoryId = request.getParameter("categoryId");
		request.setAttribute("categoryId", categoryId);

		return new ModelAndView("/dep/base/depBaseProp/" + jspfile);
	}

	private void getDictToAttribute(HttpServletRequest request, String treeCode) {
		FormDictoryFactory factory = FormDictoryFactory.getInstance();
		List<FormDictory> dicts = factory
				.getFormDictoryListByTreeCode(treeCode);
		JSONObject jsonObj = new JSONObject();
		for (FormDictory dict : dicts) {
			jsonObj.put(dict.getValue(), dict.getName());
		}
		request.setAttribute("dicts", jsonObj.toJSONString());
	}

	@RequestMapping("/addRel")
	public ModelAndView addRel(HttpServletRequest request) {

		this.getDictToAttribute(request, "ruleRelType");

		String ruleId = request.getParameter("ruleId");
		request.setAttribute("ruleId", ruleId);

		String rootCategoryId = request.getParameter("rootCategoryId");
		request.setAttribute("rootCategoryId", rootCategoryId);

		return new ModelAndView("/dep/base/depBaseProp/addRel");
	}
	
	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseCategoryService")
	protected DepBaseCategoryService depBaseCategoryService;
	
	@RequestMapping("/copyDepBaseList")
	public ModelAndView copyDepBaseList(HttpServletRequest request) {
		
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

		String categoryId = request.getParameter("categoryId");
		request.setAttribute("categoryId", categoryId);
		
		return new ModelAndView("/dep/base/depBaseProp/copy_depBaseProp_list");
	}
}
