package com.glaf.form.web.springmvc;

import java.io.IOException;
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
import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;
import com.glaf.form.core.service.FormWorkFlowRuleService;
import com.glaf.form.core.util.FormWorkFlowRuleDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/workFlowRule")
@RequestMapping("/form/workFlowRule")
public class FormWorkFlowRuleController {
	protected static final Log logger = LogFactory.getLog(FormWorkFlowRuleController.class);

	protected FormWorkFlowRuleService formWorkFlowRuleService;

	public FormWorkFlowRuleController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formWorkFlowRuleService")
	public void setFormWorkFlowRuleService(FormWorkFlowRuleService formWorkFlowRuleService) {
		this.formWorkFlowRuleService = formWorkFlowRuleService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormWorkFlowRule formWorkFlowRule = new FormWorkFlowRule();
		Tools.populate(formWorkFlowRule, params);

		formWorkFlowRule.setActResDefId(RequestUtils.getLong(request, "actResDefId"));
		formWorkFlowRule.setActResourceId(request.getParameter("actResourceId"));
		formWorkFlowRule.setActTaskId(request.getParameter("actTaskId"));
		formWorkFlowRule.setActTaskName(request.getParameter("actTaskName"));
		formWorkFlowRule.setCreator(request.getParameter("creator"));
		formWorkFlowRule.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		formWorkFlowRule.setModifier(request.getParameter("modifier"));
		formWorkFlowRule.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		formWorkFlowRule.setPageId(request.getParameter("pageId"));
		formWorkFlowRule.setVersion(RequestUtils.getInt(request, "version"));

		formWorkFlowRule.setCreator(actorId);

		formWorkFlowRuleService.save(formWorkFlowRule);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/batchSaveFormWorkFlowRule")
	public byte[] batchSaveFormWorkFlowRule(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String pageId = RequestUtils.getString(request, "pageId");
		String jsonStr = RequestUtils.getString(request, "jsonStr");

		if (StringUtils.isNotBlank(jsonStr) && StringUtils.isNotBlank(pageId)) {

			try {
				Integer nextVersion = this.formWorkFlowRuleService.getNextVersionByPageId(pageId);

				List<FormWorkFlowRule> list = JSONArray.parseArray(jsonStr, FormWorkFlowRule.class);
				if (list != null) {
					for (FormWorkFlowRule formWorkFlowRule : list) {
						formWorkFlowRule.setVersion(nextVersion);
						formWorkFlowRule.setCreator(actorId);
						this.formWorkFlowRuleService.save(formWorkFlowRule);
					}
				}
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/getLastFormWorkFlowRules")
	public byte[] getLastFormWorkFlowRules(HttpServletRequest request) throws IOException {
		String pageId = RequestUtils.getString(request, "pageId");
		JSONObject result = new JSONObject();
		if (StringUtils.isNotBlank(pageId)) {
			try {
				Integer nextVersion = this.formWorkFlowRuleService.getNextVersionByPageId(pageId);
				FormWorkFlowRuleQuery query = new FormWorkFlowRuleQuery();
				query.setPageId(pageId);
				query.setVersion(nextVersion - 1);
				List<FormWorkFlowRule> list = this.formWorkFlowRuleService.list(query);
				result.put("rows", list);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/saveFormWorkFlowRule")
	public byte[] saveFormWorkFlowRule(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkFlowRule formWorkFlowRule = new FormWorkFlowRule();
		try {
			Tools.populate(formWorkFlowRule, params);
			formWorkFlowRule.setActResDefId(RequestUtils.getLong(request, "actResDefId"));
			formWorkFlowRule.setActResourceId(request.getParameter("actResourceId"));
			formWorkFlowRule.setActTaskId(request.getParameter("actTaskId"));
			formWorkFlowRule.setActTaskName(request.getParameter("actTaskName"));
			formWorkFlowRule.setCreator(request.getParameter("creator"));
			formWorkFlowRule.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
			formWorkFlowRule.setModifier(request.getParameter("modifier"));
			formWorkFlowRule.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
			formWorkFlowRule.setPageId(request.getParameter("pageId"));
			formWorkFlowRule.setVersion(RequestUtils.getInt(request, "version"));
			// formWorkFlowRule.setCreateBy(actorId);
			this.formWorkFlowRuleService.save(formWorkFlowRule);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormWorkFlowRule saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormWorkFlowRule formWorkFlowRule = new FormWorkFlowRule();
		try {
			Tools.populate(formWorkFlowRule, model);
			formWorkFlowRule.setActResDefId(ParamUtils.getLong(model, "actResDefId"));
			formWorkFlowRule.setActResourceId(ParamUtils.getString(model, "actResourceId"));
			formWorkFlowRule.setActTaskId(ParamUtils.getString(model, "actTaskId"));
			formWorkFlowRule.setActTaskName(ParamUtils.getString(model, "actTaskName"));
			formWorkFlowRule.setCreator(ParamUtils.getString(model, "creator"));
			formWorkFlowRule.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
			formWorkFlowRule.setModifier(ParamUtils.getString(model, "modifier"));
			formWorkFlowRule.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
			formWorkFlowRule.setPageId(ParamUtils.getString(model, "pageId"));
			formWorkFlowRule.setVersion(ParamUtils.getInt(model, "version"));
			formWorkFlowRule.setCreator(actorId);
			this.formWorkFlowRuleService.save(formWorkFlowRule);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formWorkFlowRule;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormWorkFlowRule formWorkFlowRule = formWorkFlowRuleService
				.getFormWorkFlowRule(RequestUtils.getLong(request, "id"));

		Tools.populate(formWorkFlowRule, params);

		formWorkFlowRule.setActResDefId(RequestUtils.getLong(request, "actResDefId"));
		formWorkFlowRule.setActResourceId(request.getParameter("actResourceId"));
		formWorkFlowRule.setActTaskId(request.getParameter("actTaskId"));
		formWorkFlowRule.setActTaskName(request.getParameter("actTaskName"));
		formWorkFlowRule.setCreator(request.getParameter("creator"));
		formWorkFlowRule.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		formWorkFlowRule.setModifier(request.getParameter("modifier"));
		formWorkFlowRule.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		formWorkFlowRule.setPageId(request.getParameter("pageId"));
		formWorkFlowRule.setVersion(RequestUtils.getInt(request, "version"));

		formWorkFlowRuleService.save(formWorkFlowRule);

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
					FormWorkFlowRule formWorkFlowRule = formWorkFlowRuleService.getFormWorkFlowRule(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formWorkFlowRule != null
							&& (StringUtils.equals(formWorkFlowRule.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// formWorkFlowRule.setDeleteFlag(1);
						formWorkFlowRuleService.save(formWorkFlowRule);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormWorkFlowRule formWorkFlowRule = formWorkFlowRuleService.getFormWorkFlowRule(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formWorkFlowRule != null
					&& (StringUtils.equals(formWorkFlowRule.getCreator(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// formWorkFlowRule.setDeleteFlag(1);
				formWorkFlowRuleService.save(formWorkFlowRule);
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
		FormWorkFlowRule formWorkFlowRule = formWorkFlowRuleService
				.getFormWorkFlowRule(RequestUtils.getLong(request, "id"));
		if (formWorkFlowRule != null) {
			request.setAttribute("formWorkFlowRule", formWorkFlowRule);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formWorkFlowRule.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/mx/formWorkFlowRule/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkFlowRule formWorkFlowRule = formWorkFlowRuleService
				.getFormWorkFlowRule(RequestUtils.getLong(request, "id"));
		request.setAttribute("formWorkFlowRule", formWorkFlowRule);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formWorkFlowRule.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/mx/formWorkFlowRule/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formWorkFlowRule.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/mx/formWorkFlowRule/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkFlowRuleQuery query = new FormWorkFlowRuleQuery();
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
		int total = formWorkFlowRuleService.getFormWorkFlowRuleCountByQueryCriteria(query);
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
			List<FormWorkFlowRule> list = formWorkFlowRuleService.getFormWorkFlowRulesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormWorkFlowRule formWorkFlowRule : list) {
					JSONObject rowJSON = formWorkFlowRule.toJsonObject();
					rowJSON.put("id", formWorkFlowRule.getId());
					rowJSON.put("rowId", formWorkFlowRule.getId());
					rowJSON.put("formWorkFlowRuleId", formWorkFlowRule.getId());
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
		FormWorkFlowRuleQuery query = new FormWorkFlowRuleQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormWorkFlowRuleDomainFactory.processDataRequest(dataRequest);

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
		int total = formWorkFlowRuleService.getFormWorkFlowRuleCountByQueryCriteria(query);
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
			List<FormWorkFlowRule> list = formWorkFlowRuleService.getFormWorkFlowRulesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormWorkFlowRule formWorkFlowRule : list) {
					JSONObject rowJSON = formWorkFlowRule.toJsonObject();
					rowJSON.put("id", formWorkFlowRule.getId());
					rowJSON.put("formWorkFlowRuleId", formWorkFlowRule.getId());
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

		return new ModelAndView("/mx/formWorkFlowRule/list", modelMap);
	}

}
