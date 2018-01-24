package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import com.glaf.form.core.domain.FormEvent;
import com.glaf.form.core.domain.FormEventComplex;
import com.glaf.form.core.domain.FormEventTemplate;
import com.glaf.form.core.query.FormEventComplexQuery;
import com.glaf.form.core.query.FormEventQuery;
import com.glaf.form.core.service.FormEventComplexService;
import com.glaf.form.core.service.FormEventTemplateService;
import com.glaf.form.core.util.FormEventComplexDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/formEventComplex")
@RequestMapping("/form/formEventComplex")
public class FormEventComplexController {
	protected static final Log logger = LogFactory.getLog(FormEventComplexController.class);

	protected FormEventComplexService formEventComplexService;
	
	@Autowired
	protected FormEventTemplateService formEventTemplateService;

	public FormEventComplexController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formEventComplexService")
	public void setFormEventComplexService(FormEventComplexService formEventComplexService) {
		this.formEventComplexService = formEventComplexService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormEventComplex formEventComplex = new FormEventComplex();
		Tools.populate(formEventComplex, params);

		formEventComplex.setName(request.getParameter("name"));
		formEventComplex.setDiagram(request.getParameter("diagram"));
		formEventComplex.setRule(request.getParameter("rule"));
		formEventComplex.setPageId(request.getParameter("pageId"));
		formEventComplex.setComplexRule(request.getParameter("complexRule"));
		formEventComplex.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formEventComplex.setCreateBy(request.getParameter("createBy"));
		formEventComplex.setUpdateBy(request.getParameter("updateBy"));
		formEventComplex.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		// formEventComplex.setCreateBy(actorId);

		formEventComplexService.save(formEventComplex);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormEventComplex")
	public byte[] saveFormEventComplex(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventComplex formEventComplex = new FormEventComplex();
		try {
			Tools.populate(formEventComplex, params);
			String viewType = RequestUtils.getParameter(request, "viewType", "0");
			formEventComplex.setViewType(viewType);
			formEventComplex.setName(request.getParameter("name"));
			formEventComplex.setRemark(request.getParameter("remark"));
			formEventComplex.setPageId(request.getParameter("pageId"));
			if("1".equals(viewType)){
				formEventComplex.setDiagram(null);
				formEventComplex.setRule(null);
				formEventComplex.setComplexRule(null);
				formEventComplex.setComplexTableRule(request.getParameter("complexRule"));
				formEventComplex.setTableRule(request.getParameter("tableRule"));
			}else{
				formEventComplex.setDiagram(request.getParameter("diagram"));
				formEventComplex.setRule(request.getParameter("rule"));
				formEventComplex.setComplexRule(request.getParameter("complexRule"));
				formEventComplex.setComplexTableRule(null);
				formEventComplex.setTableRule(null);
			}
			if (formEventComplex.getId() == null) {
				formEventComplex.setCreateDate(new Date());
				formEventComplex.setCreateBy(actorId);
			}
			formEventComplex.setUpdateBy(actorId);
			formEventComplex.setUpdateDate(new Date());
			this.formEventComplexService.save(formEventComplex);
			return JSON.toJSONString(formEventComplex).getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	/**
	 * 转换图形xml
	 * 
	 * @param diagramXml
	 * @return
	 */
	private String converXml(String diagramXml,String ruleStr){
		Document document = null;
		JSONObject ruleObj = JSON.parseObject(ruleStr);
		try {
			document = DocumentHelper.parseText(diagramXml);
		} catch (Exception e) {
			e.printStackTrace();
			return diagramXml;
		}
		Element rootElement = document.getRootElement();
		/*
		 * 参数
		 */
		List<Element> selectNodes = rootElement.selectNodes("//bpmn2:chinaissSeqFlow[@hasParam='true']");
		for (Element element : selectNodes) {
			element.addAttribute("willChange", "true");
		}
		/*
		 * 回调
		 */
		selectNodes = rootElement.selectNodes("//bpmn2:chinaissSeqFlow[@hasCallback='true']");
		for (Element element : selectNodes) {
			element.addAttribute("willChange", "true");
		}
		selectNodes = rootElement.selectNodes("//bpmn2:chinaissContainers");
		JSONObject rule = null;
		for (Element element : selectNodes) {
			rule = ruleObj.getJSONObject(element.attributeValue("id"));
			element.addAttribute("name", rule.getString("name"));
		}
		return document.asXML();
	}
	
	@ResponseBody
	@RequestMapping("/saveFormEventComplex2")
	public byte[] saveFormEventComplex2(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventComplex formEventComplex = new FormEventComplex();
		try {
			FormEventTemplate formEventTemplate = formEventTemplateService.getFormEventTemplate(RequestUtils.getString(request, "sourceId"));
			Tools.populate(formEventComplex, params);
			String rule = request.getParameter("rule");
			formEventComplex.setName(request.getParameter("name"));
			formEventComplex.setRemark(request.getParameter("remark"));
			formEventComplex.setDiagram(converXml(formEventTemplate.getDiagram(),rule));
			formEventComplex.setRule(rule);
			formEventComplex.setPageId(request.getParameter("pageId"));
			formEventComplex.setComplexRule(request.getParameter("complexRule"));
			if (formEventComplex.getId() == null) {
				formEventComplex.setCreateDate(new Date());
				formEventComplex.setCreateBy(actorId);
			}
			formEventComplex.setUpdateBy(actorId);
			formEventComplex.setUpdateDate(new Date());
			this.formEventComplexService.save(formEventComplex);
			return JSON.toJSONString(formEventComplex).getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormEventComplex saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormEventComplex formEventComplex = new FormEventComplex();
		try {
			Tools.populate(formEventComplex, model);
			formEventComplex.setName(ParamUtils.getString(model, "name"));
			formEventComplex.setDiagram(ParamUtils.getString(model, "diagram"));
			formEventComplex.setRule(ParamUtils.getString(model, "rule"));
			formEventComplex.setPageId(ParamUtils.getString(model, "pageId"));
			formEventComplex.setComplexRule(ParamUtils.getString(model, "complexRule"));
			formEventComplex.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formEventComplex.setCreateBy(ParamUtils.getString(model, "createBy"));
			formEventComplex.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			formEventComplex.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			formEventComplex.setCreateBy(actorId);
			this.formEventComplexService.save(formEventComplex);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formEventComplex;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormEventComplex formEventComplex = formEventComplexService.getFormEventComplex(request.getParameter("id"));

		Tools.populate(formEventComplex, params);

		formEventComplex.setName(request.getParameter("name"));
		formEventComplex.setDiagram(request.getParameter("diagram"));
		formEventComplex.setRule(request.getParameter("rule"));
		formEventComplex.setPageId(request.getParameter("pageId"));
		formEventComplex.setComplexRule(request.getParameter("complexRule"));
		formEventComplex.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formEventComplex.setCreateBy(request.getParameter("createBy"));
		formEventComplex.setUpdateBy(request.getParameter("updateBy"));
		formEventComplex.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		formEventComplexService.save(formEventComplex);

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
					formEventComplexService.deleteById(x);
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			formEventComplexService.deleteById(id);
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
		FormEventComplex formEventComplex = formEventComplexService.getFormEventComplex(request.getParameter("id"));
		if (formEventComplex != null) {
			request.setAttribute("formEventComplex", formEventComplex);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formEventComplex.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/formEventComplex/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventComplex formEventComplex = formEventComplexService.getFormEventComplex(request.getParameter("id"));
		request.setAttribute("formEventComplex", formEventComplex);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formEventComplex.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/formEventComplex/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formEventComplex.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/formEventComplex/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventComplexQuery query = new FormEventComplexQuery();
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
		int total = formEventComplexService.getFormEventComplexCountByQueryCriteria(query);
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
			List<FormEventComplex> list = formEventComplexService.getFormEventComplexsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEventComplex formEventComplex : list) {
					JSONObject rowJSON = formEventComplex.toJsonObject();
					rowJSON.put("id", formEventComplex.getId());
					rowJSON.put("rowId", formEventComplex.getId());
					rowJSON.put("formEventComplexId", formEventComplex.getId());
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
		FormEventComplexQuery query = new FormEventComplexQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormEventComplexDomainFactory.processDataRequest(dataRequest);

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
		int total = formEventComplexService.getFormEventComplexCountByQueryCriteria(query);
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
			List<FormEventComplex> list = formEventComplexService.getFormEventComplexsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEventComplex formEventComplex : list) {
					JSONObject rowJSON = formEventComplex.toJsonObject();
					rowJSON.put("id", formEventComplex.getId());
					rowJSON.put("formEventComplexId", formEventComplex.getId());
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

		return new ModelAndView("/form/formEventComplex/list", modelMap);
	}

	@RequestMapping("/getEventByPageId")
	@ResponseBody
	public byte[] getEventByPageId(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventComplexQuery query = new FormEventComplexQuery();
		Tools.populate(query, params);

		JSONObject result = new JSONObject();

		String complexId = RequestUtils.getStringValue(request, "complexId");
		if (StringUtils.isNotEmpty(complexId)) {
			List<String> ids = new ArrayList<>();
			ids.add(complexId);
			query.setIds(ids);

			List<FormEventComplex> list = formEventComplexService.list(query);
			if (list != null && !list.isEmpty()) {
				result = JSON.parseObject(JSON.toJSONString(list.get(0)));
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取复合构件
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getComplexByPageId")
	@ResponseBody
	public byte[] getComplexByPageId(HttpServletRequest request, ModelMap modelMap) throws IOException {
		FormEventComplexQuery query = new FormEventComplexQuery();
		String pageId = RequestUtils.getStringValue(request, "pageId");
		String complexId = RequestUtils.getStringValue(request, "complexId");
		query.setPageId(pageId);
		query.setNotInId(complexId);
		JSONArray result = new JSONArray();
		if (StringUtils.isNotEmpty(pageId)) {
			List<FormEventComplex> list = formEventComplexService.queryComplexByPageId(query);
			if (list != null && !list.isEmpty()) {
				result = JSON.parseArray(JSON.toJSONString(list));
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

}
