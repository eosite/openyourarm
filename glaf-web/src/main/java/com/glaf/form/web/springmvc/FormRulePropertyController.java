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
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormRuleProperty;
import com.glaf.form.core.query.FormRulePropertyQuery;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.FormRulePropertyDomainFactory;
import com.glaf.form.core.util.FormTableOperateUtils;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/formRuleProperty")
@RequestMapping("/form/formRuleProperty")
public class FormRulePropertyController {
	protected static final Log logger = LogFactory
			.getLog(FormRulePropertyController.class);

	protected FormRulePropertyService formRulePropertyService;

	public FormRulePropertyController() {

	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(
			FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormRuleProperty formRuleProperty = new FormRuleProperty();
		Tools.populate(formRuleProperty, params);

		formRuleProperty.setRuleId(request.getParameter("ruleId"));
		formRuleProperty.setName(request.getParameter("name"));
		formRuleProperty.setValue(request.getParameter("value"));
		formRuleProperty.setCreateBy(actorId);

		formRulePropertyService.save(formRuleProperty);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormRuleProperty")
	public byte[] saveFormRuleProperty(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormRuleProperty formRuleProperty = new FormRuleProperty();
		try {
			Tools.populate(formRuleProperty, params);
			formRuleProperty.setRuleId(request.getParameter("ruleId"));
			formRuleProperty.setName(request.getParameter("name"));
			formRuleProperty.setValue(request.getParameter("value"));
			formRuleProperty.setCreateBy(actorId);
			this.formRulePropertyService.save(formRuleProperty);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormRuleProperty saveOrUpdate(
			HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormRuleProperty formRuleProperty = new FormRuleProperty();
		try {
			Tools.populate(formRuleProperty, model);
			formRuleProperty.setRuleId(ParamUtils.getString(model, "ruleId"));
			formRuleProperty.setName(ParamUtils.getString(model, "name"));
			formRuleProperty.setValue(ParamUtils.getString(model, "value"));
			formRuleProperty.setCreateBy(actorId);
			this.formRulePropertyService.save(formRuleProperty);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formRuleProperty;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormRuleProperty formRuleProperty = formRulePropertyService
				.getFormRuleProperty(RequestUtils.getString(request, "id"));

		Tools.populate(formRuleProperty, params);

		formRuleProperty.setRuleId(request.getParameter("ruleId"));
		formRuleProperty.setName(request.getParameter("name"));
		formRuleProperty.setValue(request.getParameter("value"));

		formRulePropertyService.save(formRuleProperty);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormRuleProperty formRuleProperty = formRulePropertyService
							.getFormRuleProperty(x);
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (formRuleProperty != null
							&& (StringUtils.equals(
									formRuleProperty.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// formRuleProperty.setDeleteFlag(1);
						formRulePropertyService.save(formRuleProperty);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormRuleProperty formRuleProperty = formRulePropertyService
					.getFormRuleProperty(id.toString());
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (formRuleProperty != null
					&& (StringUtils.equals(formRuleProperty.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// formRuleProperty.setDeleteFlag(1);
				formRulePropertyService.save(formRuleProperty);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormRuleProperty formRuleProperty = formRulePropertyService
				.getFormRuleProperty(RequestUtils.getString(request, "id"));
		if (formRuleProperty != null) {
			request.setAttribute("formRuleProperty", formRuleProperty);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formRuleProperty.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/formRuleProperty/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormRuleProperty formRuleProperty = formRulePropertyService
				.getFormRuleProperty(RequestUtils.getString(request, "id"));
		request.setAttribute("formRuleProperty", formRuleProperty);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formRuleProperty.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/formRuleProperty/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formRuleProperty.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/formRuleProperty/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormRulePropertyQuery query = new FormRulePropertyQuery();
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
		int total = formRulePropertyService
				.getFormRulePropertyCountByQueryCriteria(query);
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

			List<FormRuleProperty> list = formRulePropertyService
					.getFormRulePropertysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormRuleProperty formRuleProperty : list) {
					JSONObject rowJSON = formRuleProperty.toJsonObject();
					rowJSON.put("id", formRuleProperty.getId());
					rowJSON.put("rowId", formRuleProperty.getId());
					rowJSON.put("formRulePropertyId", formRuleProperty.getId());
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
		FormRulePropertyQuery query = new FormRulePropertyQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormRulePropertyDomainFactory.processDataRequest(dataRequest);

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
		int total = formRulePropertyService
				.getFormRulePropertyCountByQueryCriteria(query);
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

			List<FormRuleProperty> list = formRulePropertyService
					.getFormRulePropertysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormRuleProperty formRuleProperty : list) {
					JSONObject rowJSON = formRuleProperty.toJsonObject();
					rowJSON.put("id", formRuleProperty.getId());
					rowJSON.put("formRulePropertyId", formRuleProperty.getId());
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

		return new ModelAndView("/form/formRuleProperty/list", modelMap);
	}
	@ResponseBody
	@RequestMapping("/formRuleMessage")
	public byte[] formRuleMessage(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		FormTableOperateUtils formtable = new FormTableOperateUtils();
	
		JSONArray jsonArray = new JSONArray();
		if(params.get("pageId") != null){
			String tsql = "select name_,value_ from form_rule where pageId_='"+params.get("pageId")+"'";
			List<Map<String,Object>> list = formtable.queryTable("default",tsql);
			for (Map<String, Object> map : list) {
				JSONObject json = new JSONObject();
				json.put("name", map.get("name_"));
				json.put("datasource", map.get("value_"));
				jsonArray.add(json);
			}
		}
	
		return jsonArray.toJSONString().getBytes("utf-8");
		
	}
}