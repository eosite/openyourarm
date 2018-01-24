package com.glaf.dep.report.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.CollectionUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.dep.report.domain.DepReportTmpCategory;
import com.glaf.dep.report.query.DepReportTemplateQuery;
import com.glaf.dep.report.service.DepReportTemplateService;
import com.glaf.dep.report.service.DepReportTmpCategoryService;
import com.glaf.dep.report.util.DepReportTemplateDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/report/depReportTemplate")
@RequestMapping("/dep/report/depReportTemplate")
public class DepReportTemplateController {
	protected static final Log logger = LogFactory.getLog(DepReportTemplateController.class);

	protected DepReportTemplateService depReportTemplateService;

	protected DepReportTmpCategoryService depReportTmpCategoryService;

	public DepReportTemplateController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportTmpCategoryService")
	public void setDepReportTmpCategoryService(DepReportTmpCategoryService depReportTmpCategoryService) {
		this.depReportTmpCategoryService = depReportTmpCategoryService;
	}

	@javax.annotation.Resource(name = "com.glaf.dep.report.service.depReportTemplateService")
	public void setDepReportTemplateService(DepReportTemplateService depReportTemplateService) {
		this.depReportTemplateService = depReportTemplateService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepReportTemplate depReportTemplate = new DepReportTemplate();
		Tools.populate(depReportTemplate, params);

		depReportTemplate.setCode(request.getParameter("code"));
		depReportTemplate.setName(request.getParameter("name"));
		depReportTemplate.setTmpJson(request.getParameter("tmpJson"));
		depReportTemplate.setRuleJson(request.getParameter("ruleJson"));
		depReportTemplate.setVer(RequestUtils.getInt(request, "ver"));
		depReportTemplate.setCreator(request.getParameter("creator"));
		depReportTemplate.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
		depReportTemplate.setModifier(request.getParameter("modifier"));
		depReportTemplate.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		depReportTemplate.setDelFlag(request.getParameter("delFlag"));

		// depReportTemplate.setCreator(actorId);

		depReportTemplateService.save(depReportTemplate);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepReportTemplate")
	public byte[] saveDepReportTemplate(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportTemplate depReportTemplate = new DepReportTemplate();
		try {
			Tools.populate(depReportTemplate, params);
			depReportTemplate.setCode(request.getParameter("code"));
			depReportTemplate.setName(request.getParameter("name"));
			depReportTemplate.setTmpJson(request.getParameter("tmpJson"));
			depReportTemplate.setRuleJson(request.getParameter("ruleJson"));
			depReportTemplate.setVer(RequestUtils.getInt(request, "ver"));
			depReportTemplate.setCreator(request.getParameter("creator"));
			depReportTemplate.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
			depReportTemplate.setModifier(request.getParameter("modifier"));
			depReportTemplate.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
			depReportTemplate.setDelFlag(request.getParameter("delFlag"));
			// depReportTemplate.setCreator(actorId);
			if (depReportTemplate.getId() == null) {
				depReportTemplate.setCreator(actorId);
			} else {
				depReportTemplate.setModifier(actorId);
			}
			this.depReportTemplateService.save(depReportTemplate);

			if (params.containsKey("depId")) {
				DepReportTmpCategory depReportTmpCategory = new DepReportTmpCategory();
				depReportTmpCategory.setDepId(ParamUtils.getLongValue(params, "depId"));
				// depReportTmpCategory.setId(depReportTemplate.getId());
				depReportTmpCategory.setTmpId(depReportTemplate.getId());
				depReportTmpCategoryService.save(depReportTmpCategory);
			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DepReportTemplate saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepReportTemplate depReportTemplate = new DepReportTemplate();
		try {
			Tools.populate(depReportTemplate, model);
			depReportTemplate.setCode(ParamUtils.getString(model, "code"));
			depReportTemplate.setName(ParamUtils.getString(model, "name"));
			depReportTemplate.setTmpJson(ParamUtils.getString(model, "tmpJson"));
			depReportTemplate.setRuleJson(ParamUtils.getString(model, "ruleJson"));
			depReportTemplate.setVer(ParamUtils.getInt(model, "ver"));
			depReportTemplate.setCreator(ParamUtils.getString(model, "creator"));
			depReportTemplate.setCreateDateTime(ParamUtils.getDate(model, "createDateTime"));
			depReportTemplate.setModifier(ParamUtils.getString(model, "modifier"));
			depReportTemplate.setModifyDateTime(ParamUtils.getDate(model, "modifyDateTime"));
			depReportTemplate.setDelFlag(ParamUtils.getString(model, "delFlag"));
			depReportTemplate.setCreator(actorId);
			this.depReportTemplateService.save(depReportTemplate);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depReportTemplate;
	}

	@RequestMapping(value = "/getDepReportTemplate", method = RequestMethod.POST)
	public @ResponseBody DepReportTemplate getById(HttpServletRequest request) {
		DepReportTemplate depReportTemplate = null;
		try {
			depReportTemplate = this.depReportTemplateService.getDepReportTemplate(RequestUtils.getLong(request, "id"));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depReportTemplate;
	}

	@RequestMapping(value = "/downLoadJsonTemplate")
	public void downLoadJsonTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ids = RequestUtils.getString(request, "ids");
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = StringUtils.split(ids, ',');
			DepReportTemplateQuery query = new DepReportTemplateQuery();
			for (String id : idArr) {
				query.addId(Long.parseLong(id));
			}
			List<DepReportTemplate> list = this.depReportTemplateService.list(query);
			if (CollectionUtils.isNotEmpty(list)) {
				for (DepReportTemplate drt : list) {
					drt.setId(null);
				}
				JSONArray jsonArray = new JSONArray();
				jsonArray.addAll(list);
				ResponseUtils.download(request, response, jsonArray.toJSONString().getBytes("UTF-8"), "Cell.json");
			}
		}
	}

	@RequestMapping(value = "/uploadFiles")
	public ModelAndView uploadFiles(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/dep/report/depReportTemplate/uploadFiles", modelMap);
	}

	@ResponseBody
	@RequestMapping(value = "/importJsonTemplate")
	public byte[] importJsonTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject result = new JSONObject();
		Long pId = RequestUtils.getLong(request, "pId");
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			// int maxSize = 50 * FileUtils.MB_SIZE;
			List<String> fileMsg = new ArrayList<String>();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					String jsonStr;
					List<DepReportTemplate> list;
					DepReportTmpCategory depReportTmpCategory;
					try {
						jsonStr = FileUtils.readFile(mFile.getInputStream());
						if (StringUtils.isNotBlank(jsonStr)) {
							list = JSONArray.parseArray(jsonStr, DepReportTemplate.class);
							if (CollectionUtils.isNotEmpty(list)) {
								for (DepReportTemplate drt : list) {
									drt.setId(null);
									depReportTemplateService.save(drt);
									depReportTmpCategory = new DepReportTmpCategory();
									depReportTmpCategory.setDepId(pId);
									depReportTmpCategory.setTmpId(drt.getId());
									depReportTmpCategoryService.save(depReportTmpCategory);
								}
							}
						}
					} catch (IOException e) {
						fileMsg.add(mFile.getName());
						e.printStackTrace();
						logger.debug(e.getMessage());
					}
				}
			}
			if (fileMsg.size() > 0) {
				result.put("fileMsg", StringUtils.join(fileMsg, ',') + " 导入失败!");
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepReportTemplate depReportTemplate = depReportTemplateService
				.getDepReportTemplate(RequestUtils.getLong(request, "id"));

		Tools.populate(depReportTemplate, params);

		depReportTemplate.setCode(request.getParameter("code"));
		depReportTemplate.setName(request.getParameter("name"));
		depReportTemplate.setTmpJson(request.getParameter("tmpJson"));
		depReportTemplate.setRuleJson(request.getParameter("ruleJson"));
		depReportTemplate.setVer(RequestUtils.getInt(request, "ver"));
		depReportTemplate.setCreator(request.getParameter("creator"));
		depReportTemplate.setCreateDateTime(RequestUtils.getDate(request, "createDateTime"));
		depReportTemplate.setModifier(request.getParameter("modifier"));
		depReportTemplate.setModifyDateTime(RequestUtils.getDate(request, "modifyDateTime"));
		depReportTemplate.setDelFlag(request.getParameter("delFlag"));

		depReportTemplateService.save(depReportTemplate);

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
					DepReportTemplate depReportTemplate = depReportTemplateService
							.getDepReportTemplate(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depReportTemplate != null
							&& (StringUtils.equals(depReportTemplate.getCreator(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						depReportTemplate.setDelFlag("1");
						depReportTemplateService.save(depReportTemplate);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depReportTemplate != null
					&& (StringUtils.equals(depReportTemplate.getCreator(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				depReportTemplate.setDelFlag("1");
				depReportTemplateService.save(depReportTemplate);
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
		DepReportTemplate depReportTemplate = depReportTemplateService
				.getDepReportTemplate(RequestUtils.getLong(request, "id"));
		if (depReportTemplate != null) {
			request.setAttribute("depReportTemplate", depReportTemplate);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depReportTemplate.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/report/depReportTemplate/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportTemplate depReportTemplate = depReportTemplateService
				.getDepReportTemplate(RequestUtils.getLong(request, "id"));
		request.setAttribute("depReportTemplate", depReportTemplate);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depReportTemplate.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/report/depReportTemplate/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depReportTemplate.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/report/depReportTemplate/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Boolean onlyName = RequestUtils.getBoolean(request, "onlyName");
		DepReportTemplateQuery query = new DepReportTemplateQuery();
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
		int total = depReportTemplateService.getDepReportTemplateCountByQueryCriteria(query);
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
			List<DepReportTemplate> list = depReportTemplateService.getDepReportTemplatesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepReportTemplate depReportTemplate : list) {
					if (onlyName) {
						depReportTemplate.setRuleJson(null);
						depReportTemplate.setTmpJson(null);
					}
					JSONObject rowJSON = depReportTemplate.toJsonObject();
					rowJSON.put("id", depReportTemplate.getId());
					rowJSON.put("rowId", depReportTemplate.getId());
					rowJSON.put("depReportTemplateId", depReportTemplate.getId());
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

	@RequestMapping("/templateJson")
	@ResponseBody
	public byte[] templateJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepReportTemplateQuery query = new DepReportTemplateQuery();
		Tools.populate(query, params);

		int start = 0;
		int limit = 10;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		params.put("delFlag", "0");
		int total = depReportTemplateService.getDepReportTemplatesByParamsCount(params);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			List<Map<String, Object>> list = depReportTemplateService.getDepReportTemplatesByParams(start, limit,
					params);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				for (Map<String, Object> map : list) {
					JSONObject rowJSON = new JSONObject(map);
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}
				result.put("rows", rowsJSON);
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
		DepReportTemplateQuery query = new DepReportTemplateQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepReportTemplateDomainFactory.processDataRequest(dataRequest);

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
		int total = depReportTemplateService.getDepReportTemplateCountByQueryCriteria(query);
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
			List<DepReportTemplate> list = depReportTemplateService.getDepReportTemplatesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepReportTemplate depReportTemplate : list) {
					JSONObject rowJSON = depReportTemplate.toJsonObject();
					rowJSON.put("id", depReportTemplate.getId());
					rowJSON.put("depReportTemplateId", depReportTemplate.getId());
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

		return new ModelAndView("/dep/report/depReportTemplate/list", modelMap);
	}

}
