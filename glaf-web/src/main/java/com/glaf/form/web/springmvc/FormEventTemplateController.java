package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.*;
import com.glaf.form.core.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/formEventTemplate")
@RequestMapping("/form/formEventTemplate")
public class FormEventTemplateController {
	protected static final Log logger = LogFactory.getLog(FormEventTemplateController.class);

	protected FormEventTemplateService formEventTemplateService;
	
	@Autowired
	protected FormEventComplexService formEventComplexService;

	public FormEventTemplateController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formEventTemplateService")
	public void setFormEventTemplateService(FormEventTemplateService formEventTemplateService) {
		this.formEventTemplateService = formEventTemplateService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormEventTemplate formEventTemplate = new FormEventTemplate();
		Tools.populate(formEventTemplate, params);

		formEventTemplate.setPId(request.getParameter("pId"));
		formEventTemplate.setComplexId(request.getParameter("complexId"));
		formEventTemplate.setName(request.getParameter("name"));
		formEventTemplate.setRemark(request.getParameter("remark"));
		formEventTemplate.setDiagram(request.getParameter("diagram"));
		formEventTemplate.setRule(request.getParameter("rule"));
		formEventTemplate.setPageId(request.getParameter("pageId"));
		formEventTemplate.setComplexRule(request.getParameter("complexRule"));
		formEventTemplate.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		formEventTemplate.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formEventTemplate.setCreateBy(request.getParameter("createBy"));
		formEventTemplate.setUpdateBy(request.getParameter("updateBy"));
		formEventTemplate.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		// formEventTemplate.setCreateBy(actorId);

		formEventTemplateService.save(formEventTemplate);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormEventTemplate")
	public byte[] saveFormEventTemplate(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplate formEventTemplate = new FormEventTemplate();
		try {
			Tools.populate(formEventTemplate, params);
			formEventTemplate.setPId(request.getParameter("pId"));
			String complexId = request.getParameter("complexId");
			formEventTemplate.setComplexId(complexId);
			
			formEventTemplate.setName(request.getParameter("name"));
			formEventTemplate.setRemark(request.getParameter("remark"));
			if(StringUtils.isNotEmpty(complexId)){
				FormEventComplex formEventComplex = formEventComplexService.getFormEventComplex(complexId);
				
				formEventTemplate.setDiagram(formEventComplex.getDiagram());
				formEventTemplate.setRule(formEventComplex.getRule());
				formEventTemplate.setPageId(formEventComplex.getPageId());
				formEventTemplate.setComplexRule(formEventComplex.getComplexRule());
				formEventTemplate.setDeleteFlag(0);
				formEventTemplate.setCreateDate(new Date());
				formEventTemplate.setCreateBy(actorId);
				formEventTemplate.setUpdateBy(actorId);
				formEventTemplate.setUpdateDate(new Date());
				
				formEventTemplate.setViewType(formEventComplex.getViewType());
				formEventTemplate.setTableRule(formEventComplex.getTableRule());
				formEventTemplate.setComplexTableRule(formEventComplex.getComplexTableRule());
				
				this.formEventTemplateService.save(formEventTemplate);
				
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormEventTemplate saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormEventTemplate formEventTemplate = new FormEventTemplate();
		try {
			Tools.populate(formEventTemplate, model);
			formEventTemplate.setPId(ParamUtils.getString(model, "pId"));
			formEventTemplate.setComplexId(ParamUtils.getString(model, "complexId"));
			formEventTemplate.setName(ParamUtils.getString(model, "name"));
			formEventTemplate.setRemark(ParamUtils.getString(model, "remark"));
			formEventTemplate.setDiagram(ParamUtils.getString(model, "diagram"));
			formEventTemplate.setRule(ParamUtils.getString(model, "rule"));
			formEventTemplate.setPageId(ParamUtils.getString(model, "pageId"));
			formEventTemplate.setComplexRule(ParamUtils.getString(model, "complexRule"));
			formEventTemplate.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			formEventTemplate.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formEventTemplate.setCreateBy(ParamUtils.getString(model, "createBy"));
			formEventTemplate.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			formEventTemplate.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			formEventTemplate.setCreateBy(actorId);
			this.formEventTemplateService.save(formEventTemplate);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formEventTemplate;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(request.getParameter("id"));

		Tools.populate(formEventTemplate, params);

		formEventTemplate.setPId(request.getParameter("pId"));
		formEventTemplate.setComplexId(request.getParameter("complexId"));
		formEventTemplate.setName(request.getParameter("name"));
		formEventTemplate.setRemark(request.getParameter("remark"));
		formEventTemplate.setDiagram(request.getParameter("diagram"));
		formEventTemplate.setRule(request.getParameter("rule"));
		formEventTemplate.setPageId(request.getParameter("pageId"));
		formEventTemplate.setComplexRule(request.getParameter("complexRule"));
		formEventTemplate.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		formEventTemplate.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formEventTemplate.setCreateBy(request.getParameter("createBy"));
		formEventTemplate.setUpdateBy(request.getParameter("updateBy"));
		formEventTemplate.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		formEventTemplateService.save(formEventTemplate);

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
					FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formEventTemplate != null && (StringUtils.equals(formEventTemplate.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						// formEventTemplate.setDeleteFlag(1);
						formEventTemplateService.save(formEventTemplate);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formEventTemplate != null && (StringUtils.equals(formEventTemplate.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				// formEventTemplate.setDeleteFlag(1);
				formEventTemplateService.save(formEventTemplate);
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
		FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(request.getParameter("id"));
		if (formEventTemplate != null) {
			request.setAttribute("formEventTemplate", formEventTemplate);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formEventTemplate.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/formEventTemplate/edit", modelMap);
	}
	

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(request.getParameter("id"));
		request.setAttribute("formEventTemplate", formEventTemplate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formEventTemplate.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/formEventTemplate/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formEventTemplate.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/formEventTemplate/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventTemplateQuery query = new FormEventTemplateQuery();
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
		int total = formEventTemplateService.getFormEventTemplateCountByQueryCriteria(query);
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
			List<FormEventTemplate> list = formEventTemplateService.getFormEventTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEventTemplate formEventTemplate : list) {
					JSONObject rowJSON = formEventTemplate.toJsonObject();
					rowJSON.put("id", formEventTemplate.getId());
					rowJSON.put("rowId", formEventTemplate.getId());
					rowJSON.put("formEventTemplateId", formEventTemplate.getId());
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
		FormEventTemplateQuery query = new FormEventTemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormEventTemplateDomainFactory.processDataRequest(dataRequest);

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
		int total = formEventTemplateService.getFormEventTemplateCountByQueryCriteria(query);
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
			List<FormEventTemplate> list = formEventTemplateService.getFormEventTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEventTemplate formEventTemplate : list) {
					JSONObject rowJSON = formEventTemplate.toJsonObject();
					rowJSON.put("id", formEventTemplate.getId());
					rowJSON.put("formEventTemplateId", formEventTemplate.getId());
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

		return new ModelAndView("/apps/formEventTemplate/list", modelMap);
	}
	
	
	@RequestMapping("/getEventByPageId")
	@ResponseBody
	public byte[] getEventByPageId(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String templateId = RequestUtils.getString(request, "templateId", null);
		if(StringUtils.isNotEmpty(templateId)){
			FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(templateId);
			return JSON.toJSONString(formEventTemplate).getBytes("UTF-8");
		}
		return "{}".getBytes("UTF-8");
	}
}
