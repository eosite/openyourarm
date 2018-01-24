package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.JSON;
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
import com.glaf.core.util.UUID32;
import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;
import com.glaf.form.core.query.FormWorkflowPlanQuery;
import com.glaf.form.core.service.FormWorkFlowRuleService;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.form.core.util.FormWorkflowPlanDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/formWorkflowPlan")
@RequestMapping("/form/formWorkflowPlan")
public class FormWorkflowPlanController {
	protected static final Log logger = LogFactory.getLog(FormWorkflowPlanController.class);

	protected FormWorkflowPlanService formWorkflowPlanService;

	@Autowired
	protected FormWorkFlowRuleService formWorkFlowRuleService;

	public FormWorkflowPlanController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formWorkflowPlanService")
	public void setFormWorkflowPlanService(FormWorkflowPlanService formWorkflowPlanService) {
		this.formWorkflowPlanService = formWorkflowPlanService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormWorkflowPlan formWorkflowPlan = new FormWorkflowPlan();
		Tools.populate(formWorkflowPlan, params);

		formWorkflowPlan.setProcessDefId(RequestUtils.getParameter(request, "processDefId"));
		formWorkflowPlan.setPageId(request.getParameter("pageId"));
		formWorkflowPlan.setCreator(request.getParameter("creator"));
		formWorkflowPlan.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
		formWorkflowPlan.setModifier(request.getParameter("modifier"));
		formWorkflowPlan.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		formWorkflowPlan.setDefId(request.getParameter("defId"));
		formWorkflowPlan.setVersion(RequestUtils.getInt(request, "version"));

		// formWorkflowPlan.setCreateBy(actorId);

		formWorkflowPlanService.save(formWorkflowPlan);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormWorkflowPlan")
	public byte[] saveFormWorkflowPlan(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowPlan formWorkflowPlan = new FormWorkflowPlan();
		try {
			Tools.populate(formWorkflowPlan, params);
			formWorkflowPlan.setProcessDefId(RequestUtils.getParameter(request, "processDefId"));
			formWorkflowPlan.setPageId(request.getParameter("pageId"));
			formWorkflowPlan.setCreator(request.getParameter("creator"));
			formWorkflowPlan.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
			formWorkflowPlan.setModifier(request.getParameter("modifier"));
			formWorkflowPlan.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
			formWorkflowPlan.setDefId(request.getParameter("defId"));
			formWorkflowPlan.setVersion(RequestUtils.getInt(request, "version"));
			// formWorkflowPlan.setCreateBy(actorId);
			this.formWorkflowPlanService.save(formWorkflowPlan);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/batchSaveFormWorkflowPlan")
	public byte[] batchSaveFormWorkflowPlan(HttpServletRequest request) throws IOException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		String defId = RequestUtils.getString(request, "defId");// 用户自定义流程关联表ID(动态)
		String jsonStr = RequestUtils.getString(request, "jsonStr");// 规则串
		if (StringUtils.isNotBlank(jsonStr)) {
			/**
			 * jsonObject: { plans : [], rules : [] }
			 */
			JSONObject jsonObject = JSON.parseObject(jsonStr);

			Integer version = this.formWorkflowPlanService.getNextVersionByDefId(defId);

			boolean add = (defId == null || version == 1);
			if (add) {
				defId = UUID32.getUUID();
			} else
				this.updateVersion(defId);
			if (jsonObject.containsKey("plans")) {// 方案
				List<FormWorkflowPlan> plans = JSON.parseArray(jsonObject.getString("plans")//
						, FormWorkflowPlan.class);
				if (plans != null && !plans.isEmpty()) {
					for (FormWorkflowPlan p : plans) {
						p.setDefId(defId);
						p.setVersion(version);
						if (add) {
							p.setCreator(actorId);
						} else {
							p.setModifier(actorId);
						}

						if (StringUtils.isNotBlank(p.getBytes())) {
							JSONObject json = JSON.parseArray(p.getBytes())//
									.getJSONObject(0);
							if(json!=null)
								p.setKey(json.getString("key"));
						}

						this.formWorkflowPlanService.save(p);
					}
				}
			}

			if (jsonObject.containsKey("rules")) {// 表单权限规则
				List<FormWorkFlowRule> rules = JSONArray.parseArray(jsonObject.getString("rules"),
						FormWorkFlowRule.class);
				if (rules != null && !rules.isEmpty()) {
					for (FormWorkFlowRule r : rules) {
						r.setDefId(defId);
						r.setVersion(version);
						if (add) {
							r.setCreator(actorId);
						} else {
							r.setModifier(actorId);
						}
						this.formWorkFlowRuleService.save(r);
					}
				}
			}
			JSONObject result = new JSONObject();
			result.put("defId", defId);
			return result.toJSONString().getBytes("UTF-8");

		} else {
			throw new RuntimeException("参数不对!");
		}
	}

	/**
	 * update 版本号，或者要把这些数据写入历史表
	 * 
	 * @param defId
	 */
	private void updateVersion(String defId) {
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		query.setDefId(defId);
		List<FormWorkflowPlan> plans = this.formWorkflowPlanService.list(query);
		if (plans != null && plans.size() > 0) {
			for (FormWorkflowPlan f : plans) {
				if (f.getVersion() > 0) {
					f.setVersion(0 - f.getVersion());
					formWorkflowPlanService.save(f);
				}
			}
		}

		FormWorkFlowRuleQuery ruleQuery = new FormWorkFlowRuleQuery();
		ruleQuery.setDefId(defId);
		List<FormWorkFlowRule> rules = formWorkFlowRuleService.list(ruleQuery);
		if (rules != null && rules.size() > 0) {
			for (FormWorkFlowRule f : rules) {
				if (f.getVersion() > 0) {
					f.setVersion(0 - f.getVersion());
					formWorkFlowRuleService.save(f);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/getFormWorkflowPlanByDefId")
	public byte[] getFormWorkflowPlanByDefId(HttpServletRequest request) throws IOException {

		String defId = RequestUtils.getString(request, "defId");// 用户自定义流程关联表ID(动态)

		String pageId = RequestUtils.getString(request, "pageId", null);// 页面ID

		if (StringUtils.isNotBlank(defId)) {
			JSONObject result = new JSONObject();
			Integer version = this.formWorkflowPlanService.getNextVersionByDefId(defId) - 1;

			FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();

			query.setDefId(defId);
			query.setPageId(pageId);
			query.setVersion(version);
			List<FormWorkflowPlan> plans = this.formWorkflowPlanService.list(query);
			result.put("plans", plans);

			FormWorkFlowRuleQuery ruleQuery = new FormWorkFlowRuleQuery();
			ruleQuery.setDefId(defId);
			ruleQuery.setPageId(pageId);
			ruleQuery.setVersion(version);
			List<FormWorkFlowRule> rules = formWorkFlowRuleService.list(ruleQuery);
			result.put("rules", rules);

			return result.toJSONString().getBytes("UTF-8");
		}
		return null;
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormWorkflowPlan saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormWorkflowPlan formWorkflowPlan = new FormWorkflowPlan();
		try {
			Tools.populate(formWorkflowPlan, model);
			formWorkflowPlan.setProcessDefId(ParamUtils.getString(model, "processDefId"));
			formWorkflowPlan.setPageId(ParamUtils.getString(model, "pageId"));
			formWorkflowPlan.setCreator(ParamUtils.getString(model, "creator"));
			formWorkflowPlan.setCreateDateTime(ParamUtils.getDate(model, "createDateTime"));
			formWorkflowPlan.setModifier(ParamUtils.getString(model, "modifier"));
			formWorkflowPlan.setModifyDateTime(ParamUtils.getDate(model, "modifyDateTime"));
			formWorkflowPlan.setDefId(ParamUtils.getString(model, "defId"));
			formWorkflowPlan.setVersion(ParamUtils.getInt(model, "version"));
			formWorkflowPlan.setCreator(actorId);
			this.formWorkflowPlanService.save(formWorkflowPlan);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formWorkflowPlan;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormWorkflowPlan formWorkflowPlan = formWorkflowPlanService.getFormWorkflowPlan(request.getParameter("id"));

		Tools.populate(formWorkflowPlan, params);

		formWorkflowPlan.setProcessDefId(RequestUtils.getString(request, "processDefId"));
		formWorkflowPlan.setPageId(request.getParameter("pageId"));
		formWorkflowPlan.setCreator(request.getParameter("creator"));
		formWorkflowPlan.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
		formWorkflowPlan.setModifier(request.getParameter("modifier"));
		formWorkflowPlan.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		formWorkflowPlan.setDefId(request.getParameter("defId"));
		formWorkflowPlan.setVersion(RequestUtils.getInt(request, "version"));

		formWorkflowPlanService.save(formWorkflowPlan);

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
					FormWorkflowPlan formWorkflowPlan = formWorkflowPlanService.getFormWorkflowPlan(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formWorkflowPlan != null
							&& (StringUtils.equals(formWorkflowPlan.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// formWorkflowPlan.setDeleteFlag(1);
						formWorkflowPlanService.save(formWorkflowPlan);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormWorkflowPlan formWorkflowPlan = formWorkflowPlanService.getFormWorkflowPlan(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formWorkflowPlan != null
					&& (StringUtils.equals(formWorkflowPlan.getCreator(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// formWorkflowPlan.setDeleteFlag(1);
				formWorkflowPlanService.save(formWorkflowPlan);
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
		FormWorkflowPlan formWorkflowPlan = formWorkflowPlanService.getFormWorkflowPlan(request.getParameter("id"));
		if (formWorkflowPlan != null) {
			request.setAttribute("formWorkflowPlan", formWorkflowPlan);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formWorkflowPlan.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/formWorkflowPlan/formWorkflowPlan/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowPlan formWorkflowPlan = formWorkflowPlanService.getFormWorkflowPlan(request.getParameter("id"));
		request.setAttribute("formWorkflowPlan", formWorkflowPlan);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formWorkflowPlan.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/formWorkflowPlan/formWorkflowPlan/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formWorkflowPlan.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/formWorkflowPlan/formWorkflowPlan/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
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
		int total = formWorkflowPlanService.getFormWorkflowPlanCountByQueryCriteria(query);
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
			List<FormWorkflowPlan> list = formWorkflowPlanService.getFormWorkflowPlansByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormWorkflowPlan formWorkflowPlan : list) {
					JSONObject rowJSON = formWorkflowPlan.toJsonObject();
					rowJSON.put("id", formWorkflowPlan.getId());
					rowJSON.put("rowId", formWorkflowPlan.getId());
					rowJSON.put("formWorkflowPlanId", formWorkflowPlan.getId());
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
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormWorkflowPlanDomainFactory.processDataRequest(dataRequest);

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
		int total = formWorkflowPlanService.getFormWorkflowPlanCountByQueryCriteria(query);
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
			List<FormWorkflowPlan> list = formWorkflowPlanService.getFormWorkflowPlansByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormWorkflowPlan formWorkflowPlan : list) {
					JSONObject rowJSON = formWorkflowPlan.toJsonObject();
					rowJSON.put("id", formWorkflowPlan.getId());
					rowJSON.put("formWorkflowPlanId", formWorkflowPlan.getId());
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

		return new ModelAndView("/form/formWorkflowPlan/formWorkflowPlan/list", modelMap);
	}

}
