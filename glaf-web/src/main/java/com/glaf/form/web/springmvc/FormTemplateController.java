package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormTemplate;
import com.glaf.form.core.query.FormTemplateQuery;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.service.FormTemplateService;
import com.glaf.form.core.util.FormTemplateDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/template")
@RequestMapping("/form/template")
public class FormTemplateController {
	protected static final Log logger = LogFactory
			.getLog(FormTemplateController.class);

	protected FormTemplateService formTemplateService;

	protected FormComponentService formComponentService;

	public FormTemplateController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		Integer id = RequestUtils.getInteger(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormTemplate formTemplate = formTemplateService
							.getFormTemplate(Integer.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (formTemplate != null
							&& (StringUtils.equals(formTemplate.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						formTemplate.setDeleteFlag(1);
						formTemplateService.save(formTemplate);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormTemplate formTemplate = formTemplateService
					.getFormTemplate(Integer.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (formTemplate != null
					&& (StringUtils.equals(formTemplate.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				formTemplate.setDeleteFlag(1);
				formTemplateService.save(formTemplate);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		// User user = RequestUtils.getUser(request);
		// String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTemplate formTemplate = formTemplateService
				.getFormTemplate(RequestUtils.getInt(request, "id"));
		if (formTemplate != null) {
			request.setAttribute("formTemplate", formTemplate);
		}
		request.setAttribute("componentId",
				RequestUtils.getInt(request, "componentId"));

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formTemplate.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/template/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTemplateQuery query = new FormTemplateQuery();
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
		int total = formTemplateService
				.getFormTemplateCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<FormTemplate> list = formTemplateService
					.getFormTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormTemplate formTemplate : list) {
					JSONObject rowJSON = formTemplate.toJsonObject();
					rowJSON.put("id", formTemplate.getId());
					rowJSON.put("rowId", formTemplate.getId());
					rowJSON.put("formTemplateId", formTemplate.getId());
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

	@RequestMapping("/list")
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

		long componentId = RequestUtils.getLong(request, "componentId");
		if (componentId > 0) {
			FormComponent formComponent = formComponentService
					.getFormComponent(componentId);
			if (formComponent != null) {
				request.setAttribute("formComponent", formComponent);
			}
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

		return new ModelAndView("/form/template/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formTemplate.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/template/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTemplateQuery query = new FormTemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormTemplateDomainFactory.processDataRequest(dataRequest);

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
		int total = formTemplateService
				.getFormTemplateCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<FormTemplate> list = formTemplateService
					.getFormTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormTemplate formTemplate : list) {
					JSONObject rowJSON = formTemplate.toJsonObject();
					rowJSON.put("id", formTemplate.getId());
					rowJSON.put("formTemplateId", formTemplate.getId());
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormTemplate formTemplate = new FormTemplate();
		Tools.populate(formTemplate, params);

		formTemplate.setName(request.getParameter("name"));
		formTemplate
				.setComponentId(RequestUtils.getInt(request, "componentId"));
		formTemplate.setCreateBy(actorId);
		formTemplate.setCreateDate(new Date());
		formTemplate.setType(request.getParameter("type"));
		formTemplate.setTemplate(request.getParameter("template"));
		formTemplate.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		formTemplateService.save(formTemplate);

		return this.list(request, modelMap);
	}

	@RequestMapping("/saveFormTemplate")
	public ModelAndView saveFormTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean res = true;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTemplate formTemplate = new FormTemplate();
		try {
			Long componentId = RequestUtils.getLong(request, "componentId");
			FormComponent formComponent = formComponentService.getFormComponent(componentId);
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request) && formComponent!=null){
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				MultipartFile mtFile = multiRequest.getFile("image");
				if(mtFile!=null && mtFile.getBytes()!=null && mtFile.getBytes().length>0){
					formTemplate.setImage(mtFile.getBytes());
				}
			}
			Tools.populate(formTemplate, params);
			formTemplate.setName(request.getParameter("name"));
			formTemplate.setComponentId(componentId.intValue());
			formTemplate.setCreateBy(actorId);
			formTemplate.setCreateDate(new Date());
			formTemplate.setType(request.getParameter("type"));
			formTemplate.setTemplate(request.getParameter("template"));
			formTemplate.setDeleteFlag(RequestUtils.getInt(request,
					"deleteFlag"));
			this.formTemplateService.save(formTemplate);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			res = false;
		}
		PrintWriter out = response.getWriter();
		out.print("<script>parent.callback("+res+");</script>");
		return null;
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormTemplate saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormTemplate formTemplate = new FormTemplate();
		try {
			Tools.populate(formTemplate, model);
			formTemplate.setName(ParamUtils.getString(model, "name"));
			formTemplate
					.setComponentId(ParamUtils.getInt(model, "componentId"));
			formTemplate.setType(ParamUtils.getString(model, "type"));
			formTemplate.setTemplate(ParamUtils.getString(model, "template"));
			formTemplate.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			formTemplate.setCreateBy(actorId);
			this.formTemplateService.save(formTemplate);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formTemplate;
	}

	@javax.annotation.Resource
	public void setFormComponentService(
			FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@javax.annotation.Resource
	public void setFormTemplateService(FormTemplateService formTemplateService) {
		this.formTemplateService = formTemplateService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormTemplate formTemplate = formTemplateService
				.getFormTemplate(RequestUtils.getInt(request, "id"));

		Tools.populate(formTemplate, params);

		formTemplate.setName(request.getParameter("name"));
		formTemplate
				.setComponentId(RequestUtils.getInt(request, "componentId"));
		formTemplate.setType(request.getParameter("type"));
		formTemplate.setTemplate(request.getParameter("template"));
		formTemplate.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		formTemplateService.save(formTemplate);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FormTemplate formTemplate = formTemplateService
				.getFormTemplate(RequestUtils.getInt(request, "id"));
		request.setAttribute("formTemplate", formTemplate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formTemplate.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/template/view");
	}

}
