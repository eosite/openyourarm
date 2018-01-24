/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.form.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.security.BaseIdentityFactory;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.PageUrlConversion;
import com.glaf.form.core.query.FormComponentQuery;
import com.glaf.form.core.query.FormPageQuery;
import com.glaf.form.core.query.FormRuleQuery;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTemplateService;
import com.glaf.form.core.service.PageUrlConversionService;
import com.glaf.form.core.util.FormPageDomainFactory;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;
import com.glaf.form.rule.util.ListUtil;
import com.glaf.form.rule.util.TemplateExtUtils;
import com.glaf.template.Template;
import com.glaf.template.TemplateContainer;
import com.glaf.template.util.TemplateUtils;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/page")
@RequestMapping("/form/page")
public class FormPageController {
	protected static final Log logger = LogFactory.getLog(FormPageController.class);

	protected FormPageService formPageService;
	protected FormRuleService formRuleService;
	protected FormComponentService formComponentService;
	protected FormTemplateService formTemplateService;
	protected PageUrlConversionService pageUrlConversionService;

	@Autowired
	protected DataSetService dataSetService;

	public FormPageController() {

	}

	@RequestMapping("/setTheme")
	@ResponseBody
	public byte[] setTheme(HttpServletRequest request) throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getParameter(request, "id");
		String themeId = RequestUtils.getParameter(request, "themeId");
		FormPage page = this.formPageService.getFormPage(id);
		if (page != null) {
			this.formPageService.updateThemeId(page.getId(), themeId);
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/copy")
	@ResponseBody
	public byte[] copy(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		JSONObject result = new JSONObject();
		try {
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			String id = RequestUtils.getString(request, "id");

			if (StringUtils.isNotEmpty(id)) {
				FormPage oldPage = formPageService.getFormPage(id, false);
				FormPage newPage = oldPage;
				newPage.setId(null);
				newPage.setName(newPage.getName() + " - 副本");
				newPage.setCreateBy(loginContext.getActorId());
				newPage.setCreateDate(new Date());
				formPageService.save(newPage);

				List<FormRule> oldRuleList = formRuleService.getRules(id, false);
				for (FormRule rule : oldRuleList) {
					rule.setId(null);
					if (id.equalsIgnoreCase(rule.getName())) {
						rule.setName(newPage.getId());
						// 处理页面中事件定义器中的页面ID
						if (rule.getValue() != null) {
							rule.setValue(rule.getValue().replaceAll(id, newPage.getId()));
						}
					}
					rule.setPageId(newPage.getId());
					rule.setCreateBy(loginContext.getActorId());
					rule.setCreateDate(new Date());

					formRuleService.save(rule);
					formRulePropertyService.isRuleProperties(rule);
					formRulePropertyService.saveComExt(rule);
				}
			}

			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", 500);
			result.put("message", e.getMessage());
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormPage formPage = formPageService.getFormPage(String.valueOf(x));

					if (formPage != null && (StringUtils.equals(formPage.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						// formPageService.deleteById(x);
						// 更改为逻辑删除
						formPage.setDeleteFlag(1);
						try {
							formPageService.save(formPage);
						} catch (Exception e) {
						}
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormPage formPage = formPageService.getFormPage(String.valueOf(id));

			if (formPage != null && (StringUtils.equals(formPage.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {

				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = RequestUtils.getString(request, "id");
		FormPage formPage = formPageService.getFormPage(id);
		if (formPage != null && formPage.getFormHtml() != null) {
			try {
				byte[] bytes = formPage.getFormHtml().getBytes("UTF-8");
				ResponseUtils.download(request, response, bytes, formPage.getName() + ".html");
			} catch (ServletException e) {
			}
		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FormPage formPage = formPageService.getFormPage(request.getParameter("id"));
		if (formPage != null) {
			request.setAttribute("formPage", formPage);

			List<String> perms = StringTools.split(formPage.getPerms());
			StringBuffer buffer = new StringBuffer();
			List<Role> roles = IdentityFactory.getRoles();

			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					if (perms.contains(String.valueOf(role.getId()))) {
						buffer.append(role.getName());
						buffer.append(FileUtils.newline);
					}
				}
			}

			request.setAttribute("x_role_names", buffer.toString());

		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formPage.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/page/edit", modelMap);
	}

	/**
	 * 启用禁用
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/enabledDisable")
	public byte[] enabledDisable(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		int locked = RequestUtils.getInt(request, "locked", 0);
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormPage formPage = formPageService.getFormPage(String.valueOf(x));

					if (formPage != null && (StringUtils.equals(formPage.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						formPageService.enabledDisable(x, locked);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormPage formPage = formPageService.getFormPage(String.valueOf(id));

			if (formPage != null && (StringUtils.equals(formPage.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {

				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageQuery query = new FormPageQuery();
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

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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
		int total = formPageService.getFormPageCountByQueryCriteria(query);
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

			List<FormPage> list = formPageService.getFormPagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormPage formPage : list) {
					JSONObject rowJSON = formPage.toJsonObject();
					rowJSON.put("id", formPage.getId());
					rowJSON.put("rowId", formPage.getId());
					rowJSON.put("formPageId", formPage.getId());
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
		return new ModelAndView("/form/page/list", modelMap);
	}

	@RequestMapping("/listview")
	public ModelAndView listview(HttpServletRequest request, ModelMap modelMap) {
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

		return new ModelAndView("/form/page/listview", modelMap);
	}

	@RequestMapping("/move")
	@ResponseBody
	public byte[] move(HttpServletRequest request, ModelMap modelMap) throws UnsupportedEncodingException {
		JSONObject result = new JSONObject();
		try {
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			String ids = RequestUtils.getString(request, "ids");
			String parentid = RequestUtils.getString(request, "parentid");
			String[] idArr = ids.split(";");

			List<String> idList = new ArrayList<String>();
			for (String id : idArr) {
				idList.add(id);
			}
			if (idList.size() > 0 && StringUtils.isNotEmpty(parentid)) {
				formPageService.updateFormPageParentId(parentid, idList, loginContext.getActorId());
			}

			result.put("status", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", 500);
			result.put("message", e.getMessage());
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 参考文章：
	 * http://developer.51cto.com/art/201111/305181.htm
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getClientIp")
	@ResponseBody
	public byte[] getClientIp(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		JSONObject ret = new JSONObject();
		// ret.put("statusCode", 200);
		// ret.put("message", "获取IP成功");
		ret.put("clientIp", ip);
		return ret.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FormPage formPage = formPageService.getFormPage(request.getParameter("id"));
		if (formPage != null) {
			request.setAttribute("formPage", formPage);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formPage.preview");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/page/preview", modelMap);
	}

	@ResponseBody
	@RequestMapping("/publish")
	public byte[] publish(HttpServletRequest request, ModelMap modelMap) {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		String path = request.getServletContext().getRealPath("/");
		String id = RequestUtils.getString(request, "id");

		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			// StringTokenizer token = new StringTokenizer(ids, ",");
			// while (token.hasMoreTokens()) {
			// String x = token.nextToken();
			// if (StringUtils.isNotEmpty(x)) {
			// FormPage formPage =
			// formPageService.getFormPage(String.valueOf(x));
			// }
			// }
			// return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormPage formPage = formPageService.getFormPage(String.valueOf(id));
			if (formPage != null) {
				try {
					// 获取页面对应的规则
					FormRuleQuery formRuleQuery = new FormRuleQuery();
					formRuleQuery.setPageId(formPage.getId());
					formRuleQuery.setLocked(0);// 有效
					formRuleQuery.deleteFlag(0);// 无删除
					List<FormRule> formRules = formRuleService.list(formRuleQuery);

					// 查询所有控件
					FormComponentQuery query = new FormComponentQuery();
					query.setLocked(0);
					query.setDeleteFlag(0);
					List<FormComponent> formComponents = formComponentService.getComponentPropertyList(query);
					// 执行发布
					Class<?> clazz = (Class<?>) Class.forName(Global.PAGE_PARSER_CLASS_NAME);
					if (clazz != null) {
						Object model = clazz.newInstance();
						Method method = clazz.getMethod("publish", FormPage.class, List.class, List.class, String.class, FormPageService.class);
						method.invoke(model, formPage, formRules, formComponents, path, formPageService);
					}
					// FormPageParserUtil.publish(formPage, formRules,
					// formComponents, path, formPageService);
					return ResponseUtils.responseResult(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ResponseUtils.responseResult(false);
	}

	

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formPage.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/page/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormPageQuery query = new FormPageQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormPageDomainFactory.processDataRequest(dataRequest);

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
		int total = formPageService.getFormPageCountByQueryCriteria(query);
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

			List<FormPage> list = formPageService.getFormPagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormPage formPage : list) {
					JSONObject rowJSON = formPage.toJsonObject();
					rowJSON.put("id", formPage.getId());
					rowJSON.put("formPageId", formPage.getId());
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
	public ModelAndView save(HttpServletRequest request, @RequestParam("file") MultipartFile mFile, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

			Map<String, Object> params = RequestUtils.getParameterMap(req);
			params.remove("status");
			params.remove("wfStatus");

			FormPage formPage = new FormPage();
			Tools.populate(formPage, params);

			formPage.setName(req.getParameter("name"));
			formPage.setBusinessTable(request.getParameter("businessTable"));
			formPage.setPrimaryKeyColumn(request.getParameter("primaryKeyColumn"));
			try {
				formPage.setCreateBy(actorId);
				formPageService.save(formPage);
				request.setAttribute("formPage", formPage);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return new ModelAndView("/form/page/view");
	}

	@ResponseBody
	@RequestMapping("/saveFormPage")
	public byte[] saveFormPage(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		FormPage formPage = new FormPage();
		try {
			Tools.populate(formPage, params);
			formPage.setName(request.getParameter("name"));
			formPage.setBusinessTable(request.getParameter("businessTable"));
			formPage.setPrimaryKeyColumn(request.getParameter("primaryKeyColumn"));
			formPage.setCreateBy(actorId);
			formPage.setUpdateBy(actorId);
			formPage.setPageCategory(RequestUtils.getInteger(request, "pageCategory", 1));
			formPage.setType(RequestUtils.getString(request, "sortType", null));
			this.formPageService.save(formPage);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormPage saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormPage formPage = new FormPage();
		try {
			Tools.populate(formPage, model);
			formPage.setName(ParamUtils.getString(model, "name"));
			formPage.setCreateBy(actorId);
			this.formPageService.save(formPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formPage;
	}

	/**
	 * 更新节点顺序
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveSort")
	@ResponseBody
	public byte[] saveSort(HttpServletRequest request) {
		String items = request.getParameter("items");
		if (StringUtils.isNotEmpty(items)) {
			int sort = 0;
			List<TableModel> rows = new ArrayList<TableModel>();
			StringTokenizer token = new StringTokenizer(items, ",");
			while (token.hasMoreTokens()) {
				String item = token.nextToken();
				if (StringUtils.isNotEmpty(item)) {
					sort++;
					TableModel t1 = new TableModel();
					t1.setTableName("FORM_PAGE");
					ColumnModel idColumn1 = new ColumnModel();
					idColumn1.setColumnName("ID_");
					idColumn1.setValue(item);
					t1.setIdColumn(idColumn1);
					ColumnModel column = new ColumnModel();
					column.setColumnName("SORTNO_");
					column.setValue(sort);
					t1.addColumn(column);
					rows.add(t1);
				}
			}
			try {
				logger.debug("update rows:" + rows.size());
				DataServiceFactory.getInstance().updateAllTableData(rows);
				return ResponseUtils.responseResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setFormComponentService(FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@javax.annotation.Resource
	public void setFormPageService(FormPageService formPageService) {
		this.formPageService = formPageService;
	}

	@javax.annotation.Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@javax.annotation.Resource
	public void setFormTemplateService(FormTemplateService formTemplateService) {
		this.formTemplateService = formTemplateService;
	}

	@javax.annotation.Resource
	public void setPageUrlConversionService(PageUrlConversionService pageUrlConversionService) {
		this.pageUrlConversionService = pageUrlConversionService;
	}

	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formPage.showImport");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/page/showImport", modelMap);
	}

	/**
	 * 显示排序页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showSort")
	public ModelAndView showSort(HttpServletRequest request, ModelMap modelMap) {
		// RequestUtils.setRequestParameterToAttribute(request);
		String parentId = request.getParameter("parentId");
		if (StringUtils.isNotEmpty(parentId)) {
			List<FormPage> pages = formPageService.getChildren(parentId);
			request.setAttribute("pages", pages);
		}

		String x_view = ViewProperties.getString("formPage.showSort");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/page/showSort", modelMap);
	}

	@RequestMapping("/treeJson")
	@ResponseBody
	public byte[] treeJson(HttpServletRequest request, ModelMap modelMap) throws IOException {
		JSONArray array = new JSONArray();
		try {
			FormPageQuery query = new FormPageQuery();
			query.deleteFlag(0);
			List<FormPage> resultList = formPageService.list(query);
			if (resultList != null && !resultList.isEmpty()) {
				List<TreeComponent> treeList = new ArrayList<TreeComponent>();
				Map<String, TreeComponent> treeMap = new HashMap<String, TreeComponent>();
				for (FormPage model : resultList) {
					TreeComponent tree = new TreeComponent();
					tree.setId(model.getId());
					tree.setTitle(model.getName());
					String parentId = model.getParentId();
					if (parentId != null && parentId.trim().length() > 0) {
						TreeComponent parent = treeMap.get(parentId);
						tree.setParent(parent);
						tree.setParentId(parentId);
					}
					treeList.add(tree);
					treeMap.put(tree.getId(), tree);
				}
				List<TreeComponent> trees = new ArrayList<TreeComponent>();
				for (TreeComponent tree : treeList) {
					String parentId = tree.getParentId();
					if (parentId != null && parentId.trim().length() > 0) {
						TreeComponent parent = treeMap.get(parentId);
						tree.setParent(parent);
					}
					trees.add(tree);
				}
				logger.debug("trees->" + treeList.size());
				TreeHelper helper2 = new TreeHelper();
				array = helper2.getJSONArray(trees, 0);
				// logger.debug(array.toJSONString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return array.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormPage formPage = formPageService.getFormPage(request.getParameter("id"));
		if (formPage != null) {
			Tools.populate(formPage, params);

			formPage.setName(request.getParameter("name"));
			try {
				formPageService.save(formPage);
			} catch (Exception e) {
			}
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		FormPage formPage = formPageService.getFormPage(request.getParameter("id"));
		request.setAttribute("formPage", formPage);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formPage.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/page/view");
	}

	@ResponseBody
	@RequestMapping("/viewPage")
	public void viewPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		// 防止 xss 攻击
		Set<String> pkeys = params.keySet();
		Object value = null;
		for (String pkey : pkeys) {
			value = params.get(pkey);
			params.put(pkey, HtmlUtils.htmlEscape(value.toString()));
		}
		String mtKey = "__mt__";
		if (params.containsKey(mtKey)) {
			try {
				String encodeStr = (String) params.get(mtKey);
				String decodeStr = new String(Base64.decodeBase64(encodeStr), "UTF-8");
				if (StringUtils.isNotEmpty(decodeStr)) {
					JSONObject decodeObj = JSON.parseObject(decodeStr);
					params.putAll(decodeObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// long time1 = System.currentTimeMillis();
		params.put("sys_date", new Date().getTime());
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		id = InjectUtils.escapeSql(id);
		boolean isPublish = RequestUtils.getBoolean(request, "isPublish");
		// 发布
		if (isPublish) {
			this.publish(request, modelMap);
		}

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// response.setHeader("Cache-control", "public");
		// response.setHeader("last-modified", "Thu, 01 Dec 1994 16:00:00 GMT");
		// response.setHeader("Expires", "Thu, 01 Dec 1994 16:00:00 GMT");
		PrintWriter writer = null;
		Writer sw = null;
		String errorStr = "<h1 style='color:red;'>未找到对应页面</h1>";
		try {
			// 如果未登录跳转到登录页面
			if (loginContext == null) {
				request.getRequestDispatcher("/unauthorized.jsp").forward(request, response);
				return;
			}
			User user = loginContext.getUser();
			writer = response.getWriter();
			List<PageUrlConversion> urlList = pageUrlConversionService.getAllList();
			// long time22 = System.currentTimeMillis();
			// logger.debug("urlList时间=="+(time22-time1));

			FormPage formPage = formPageService.getFormPage(id);

			// long time23 = System.currentTimeMillis();
			// logger.debug("formPage读取时间=="+(time23-time22));

			if (formPage != null) {
				String uiType = formPage.getUiType() == null ? "kendo" : formPage.getUiType();
				Template template = TemplateContainer.getContainer().getTemplate(uiType);

				// long time24 = System.currentTimeMillis();
				// logger.debug("template读取时间=="+(time24-time23));
				/**
				 * 如果是私有的页面
				 */
				if (StringUtils.equals(formPage.getAccessType(), "PRV")) {
					/**
					 * 定义了权限，需要检查是否有权限
					 */
					if (StringUtils.isNotEmpty(formPage.getPerms())) {
						boolean hasPerm = false;
						Collection<String> permList = StringTools.split(formPage.getPerms());
						Collection<String> perms = loginContext.getPermissions();
						for (String perm : permList) {
							if (perms.contains(perm)) {
								hasPerm = true;
								break;
							}
						}
						/**
						 * 如果没有访问该页面的权限，跳转至未授权页面
						 */
						if (!loginContext.isSystemAdministrator() && !hasPerm) {
							request.getRequestDispatcher("/unauthorized.jsp").forward(request, response);
							return;
						}
					}
				}

				// long time2 = System.currentTimeMillis();
				// logger.debug("权限时间=="+(time2-time1));

				Document doc = Jsoup.parse(formPage.getOutputHtml());
				// long time3 = System.currentTimeMillis();
				// logger.debug("jsoup解析时间======="+(time3-time2));
				Map<String, Object> context = new HashMap<String, Object>();
				/*
				 * String str = ""; Boolean isDebug =
				 * SystemConfig.getBoolean("dynamicScript"); if
				 * (doc.select("script").outerHtml().indexOf("defineKendoui.js")
				 * == -1 && (isDebug == null || !isDebug)) { str =
				 * "<script type='text/javascript' src='${contextPath}/scripts/defineKendoui.js'></script>\n"
				 * ; }
				 */
				try {
					JSONObject detailObj = initPageDetail(request, params);
					params.put("detail", detailObj);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				// long time4 = System.currentTimeMillis();
				// logger.debug("initPageDetail时间======="+(time4-time3));
				context.put("script",
						doc.select("style").outerHtml() + doc.select("link").outerHtml() /* + str */
								+ (isPublish ? formPage.getDebuggerVar() : "") + doc.select("script").outerHtml());

				// long time41 = System.currentTimeMillis();
				// logger.debug("script转换时间======="+(time41-time4));

				context.put("body", doc.body().html()/* outerHtml() */);
				context.put("user", user);
				context.put("actorId", user.getActorId()); // 角色账号
				if(user.getName()==null){
					context.put("username", "");
					params.put("username", "");
				}else{
					context.put("username", user.getName());
					params.put("username", user.getName());
				}
				
				
				params.put("actorId", user.getActorId()); // 角色账号
				if(user.getRemark() == null){
					params.put("userRemark", "");	//用户备注
					context.put("userRemark", "");	//用户备注
				}else{
					context.put("userRemark", user.getRemark());	//用户备注
					params.put("userRemark", user.getRemark());	//用户备注
				}
				if(user.getMobile() == null){
					params.put("userMobile", "");	//用户电话
					context.put("userMobile", "");	//用户电话
				}else{
					context.put("userMobile", user.getMobile());	//用户备注
					params.put("userMobile", user.getMobile());	//用户备注
				}
				
				// 放到后面 防止表达式注入
				context.put("pageParameters", "${pageParameters}");
				context.put("contextPath", request.getContextPath());

				context.put("serviceUrl", RequestUtils.getServiceUrl(request));
				context.put("scriptTag", "script");
				context.put("title",
						/* SystemConfig.getString("res_system_name") */formPage.getName());

				context.put("isDebug", isPublish);
				context.put("isDynamicJs", SystemConfig.getBoolean("dynamicScript"));// 动态js调式
				context.put("isEncode", SystemConfig.getBoolean("isEncode"));
				// 获取用户角色
				List<String> actorIds = new ArrayList<String>();
				actorIds.add(loginContext.getActorId());

				List<String> roles = BaseIdentityFactory.getUserRoleCodes(actorIds);
				context.put("roleArys", ListUtil.joinList(roles, ","));
				List<SysRole> sysRoles = BaseIdentityFactory.getUserRoles(actorIds);
				// String roleNames =
				// sysRoles.stream().flatMap((sysRole)->Stream.of(sysRole.getName())).distinct().collect(Collectors.joining(","));
				StringBuilder buff = new StringBuilder(),
						userRoleCode = new StringBuilder(),
						userRoleId = new StringBuilder();
				for (SysRole role : sysRoles) {
					buff.append(role.getName()).append(",");
					userRoleCode.append(role.getCode()).append(",");
					userRoleId.append(role.getId()).append(",");
				}
				if (buff.length() > 2) {
					buff.delete(buff.length() - 1, buff.length());
				}
				if(userRoleCode.length() > 2){
					userRoleCode.delete(userRoleCode.length() - 1, userRoleCode.length());
				}
				if(userRoleId.length() > 2 ){
					userRoleId.delete(userRoleId.length() - 1, userRoleId.length());
				}
				params.put("roleNames", buff.toString());
				params.put("roleCodes", userRoleCode.toString());
				params.put("roleIds", userRoleId.toString());
				
				context.put("roleNames", buff.toString());
				context.put("roleCodes", userRoleCode.toString());
				context.put("roleIds", userRoleId.toString());
				
				// 获取用户部门
				SysDepartment sysDepartment = BaseIdentityFactory.getDepartmentById(user.getDeptId());
				List<String> departs = new ArrayList<String>();
				// departs.add(sysDepartment.getCode());
				context.put("departmentArys", ListUtil.joinList(departs, ","));
				// 获取角色账号
				context.put("userArys", loginContext.getActorId()); // 角色账号

				context.put("res_system_name", SystemConfig.getString("res_system_name"));// 系统名称
				context.put("res_version", SystemConfig.getString("res_version"));// 系统版本
				context.put("userdept", sysDepartment != null ? sysDepartment.getName() : "");// 用户部门
				context.put("deptId", user.getDeptId() + "");// 部门ID
				context.put("deptCode", sysDepartment != null ? sysDepartment.getCode() : "");// 部门代码
				SysDepartment parentSysDepartment = sysDepartment != null ? BaseIdentityFactory.getDepartmentById(sysDepartment.getParentId()) : null;
				context.put("parentDeptId", parentSysDepartment != null ? parentSysDepartment.getId() + "" : ""); // 父部门ID
				context.put("parentDept", parentSysDepartment != null ? parentSysDepartment.getName() : "");// 父部门名
				context.put("parentDeptCode", parentSysDepartment != null ? parentSysDepartment.getCode() : "");// 父部门代码

				// long time5 = System.currentTimeMillis();
				// logger.debug("读取系统变量时间======="+(time5-time4));

				String templateContent = TemplateUtils.process(context, template.getContent());

				// long time6 = System.currentTimeMillis();
				// logger.debug("templateContent时间======="+(time6-time5));

				if ("kendo".equalsIgnoreCase(uiType)) {
					String my_theme = com.glaf.core.util.RequestUtils.getTheme(request);
					String dataviz_theme = "kendo.dataviz.blueopal.min.css";
					if (my_theme == null) {
						my_theme = "kendo.default.min.css";
						dataviz_theme = "kendo.dataviz.default.min.css";
					} else if ("default".equals(my_theme)) {
						my_theme = "kendo.blueopal.min.css";
						dataviz_theme = "kendo.dataviz.blueopal.min.css";
					} else if ("gray".equals(my_theme)) {
						my_theme = "kendo.default.min.css";
						dataviz_theme = "kendo.dataviz.default.min.css";
					} else if ("sunny".equals(my_theme)) {
						my_theme = "kendo.default.min.css";
						dataviz_theme = "kendo.dataviz.default.min.css";
					} else if ("metro".equals(my_theme)) {
						my_theme = "kendo.metro.min.css";
						dataviz_theme = "kendo.dataviz.metro.min.css";
					} else if ("bootstrap".equals(my_theme)) {
						my_theme = "kendo.bootstrap.min.css";
						dataviz_theme = "kendo.dataviz.bootstrap.min.css";
					} else if ("black".equals(my_theme)) {
						my_theme = "kendo.black.min.css";
						dataviz_theme = "kendo.dataviz.black.min.css";
					}
					if (!StringUtils.equalsIgnoreCase(my_theme, "kendo.blueopal.min.css")) {
						templateContent = StringTools.replace(templateContent, "kendo.blueopal.min.css", my_theme);
						templateContent = StringTools.replace(templateContent, "kendo.dataviz.blueopal.min.css", dataviz_theme);
					}
				}
				sw = new java.io.StringWriter();
				context.put("pageParameters", JSON.toJSONString(params));
				TemplateExtUtils.process(context, templateContent, sw);
				sw.flush();
				String output = sw.toString().replaceAll("script-tag", "script");
				if (urlList != null && !urlList.isEmpty()) {
					for (PageUrlConversion bean : urlList) {
						if (StringUtils.isNotEmpty(bean.getSrcUrl()) && StringUtils.isNotEmpty(bean.getDestUrl())) {
							output = StringTools.replaceIgnoreCase(output, bean.getSrcUrl(), bean.getDestUrl());
						}
					}
				}
				// long time7 = System.currentTimeMillis();
				// logger.debug("templateContent222222时间======="+(time7-time6));
				writer.write(output);
				writer.flush();
			} else {
				writer.write(errorStr);
			}
		} catch (Exception e) {
			if (writer != null) {
				writer.write(errorStr);
			}
			e.printStackTrace();
			logger.error("FormPageController / viewPage 错误：" + e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (sw != null) {
				sw.close();
			}
		}
	}

	@ResponseBody
	@RequestMapping("/preViewPage")
	public void preViewPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		// 防止 xss 攻击
		Set<String> pkeys = params.keySet();
		Object value = null;
		for (String pkey : pkeys) {
			value = params.get(pkey);
			params.put(pkey, HtmlUtils.htmlEscape(value.toString()));
		}
		// 通过ID获取模板内容
		params.put("sys_date", new Date().getTime());
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String uiType = RequestUtils.getString(request, "uiType");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;
		Writer sw = null;
		String pageContent = RequestUtils.getString(request, "pageContent");
		try {
			writer = response.getWriter();
			List<PageUrlConversion> urlList = pageUrlConversionService.getAllList();
			Template template = TemplateContainer.getContainer().getTemplate(uiType == null ? "kendo" : uiType);
			Document doc = Jsoup.parse(pageContent);
			Map<String, Object> context = new HashMap<String, Object>();
			try {
				JSONObject detailObj = initPageDetail(request, params);
				params.put("detail", detailObj);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			context.put("script", doc.select("style").outerHtml() + doc.select("link").outerHtml() + doc.select("script").outerHtml());
			context.put("body", doc.html()/* outerHtml() */);
			context.put("user", loginContext.getUser());
			context.put("actorId", loginContext.getActorId()); // 角色账号
			context.put("username", loginContext.getUser().getName());

			params.put("actorId", loginContext.getActorId()); // 角色账号
			params.put("username", loginContext.getUser().getName());

			context.put("contextPath", request.getContextPath());
			context.put("pageParameters", JSON.toJSONString(params));
			context.put("serviceUrl", RequestUtils.getServiceUrl(request));
			context.put("scriptTag", "script");
			context.put("title", "模板预览");
			context.put("isDebug", false);
			// 获取用户角色
			List<String> actorIds = new ArrayList<String>();
			actorIds.add(loginContext.getActorId());
			List<String> roles = BaseIdentityFactory.getUserRoleCodes(actorIds);
			context.put("roleArys", ListUtil.joinList(roles, ","));
			List<SysRole> sysRoles = BaseIdentityFactory.getUserRoles(actorIds);
			// String roleNames =
			// sysRoles.stream().flatMap((sysRole)->Stream.of(sysRole.getName())).distinct().collect(Collectors.joining(","));
			StringBuilder buff = new StringBuilder();
			StringBuilder userRoleId = new StringBuilder();
			StringBuilder userRoleCode = new StringBuilder();
			for (SysRole role : sysRoles) {
				buff.append(role.getName()).append(",");
				userRoleCode.append(role.getCode()).append(",");
				userRoleId.append(role.getId()).append(",");
			}
			if (buff.length() > 2) {
				buff.delete(buff.length() - 1, buff.length());
			}
			if(userRoleCode.length() > 2){
				userRoleCode.delete(userRoleCode.length() - 1, userRoleCode.length());
			}
			if(userRoleId.length() > 2 ){
				userRoleId.delete(userRoleId.length() - 1, userRoleId.length());
			}
			params.put("roleNames", buff.toString());
			params.put("roleCodes", userRoleCode.toString());
			params.put("roleIds", userRoleId.toString());
			
			context.put("roleNames", buff.toString());
			context.put("roleCodes", userRoleCode.toString());
			context.put("roleIds", userRoleId.toString());
			// 获取用户部门
			SysDepartment sysDepartment = BaseIdentityFactory.getDepartmentById(loginContext.getUser().getDeptId());
			List<String> departs = new ArrayList<String>();
			// departs.add(sysDepartment.getCode());
			context.put("departmentArys", ListUtil.joinList(departs, ","));
			// 获取角色账号
			context.put("userArys", loginContext.getActorId()); // 角色账号

			String departName = BaseIdentityFactory.getDepartmentById(loginContext.getUser().getDeptId()) != null
					? BaseIdentityFactory.getDepartmentById(loginContext.getUser().getDeptId()).getName() : "";
			context.put("res_system_name", SystemConfig.getString("res_system_name"));// 系统名称
			context.put("res_version", SystemConfig.getString("res_version"));// 系统版本
			context.put("userdept", departName);// 用户部门
			String templateContent = TemplateUtils.process(context, template.getContent());

			String my_theme = com.glaf.core.util.RequestUtils.getTheme(request);
			String dataviz_theme = "kendo.dataviz.blueopal.min.css";
			if (my_theme == null) {
				my_theme = "kendo.default.min.css";
				dataviz_theme = "kendo.dataviz.default.min.css";
			} else if ("default".equals(my_theme)) {
				my_theme = "kendo.blueopal.min.css";
				dataviz_theme = "kendo.dataviz.blueopal.min.css";
			} else if ("gray".equals(my_theme)) {
				my_theme = "kendo.default.min.css";
				dataviz_theme = "kendo.dataviz.default.min.css";
			} else if ("sunny".equals(my_theme)) {
				my_theme = "kendo.default.min.css";
				dataviz_theme = "kendo.dataviz.default.min.css";
			} else if ("metro".equals(my_theme)) {
				my_theme = "kendo.metro.min.css";
				dataviz_theme = "kendo.dataviz.metro.min.css";
			} else if ("bootstrap".equals(my_theme)) {
				my_theme = "kendo.bootstrap.min.css";
				dataviz_theme = "kendo.dataviz.bootstrap.min.css";
			} else if ("black".equals(my_theme)) {
				my_theme = "kendo.black.min.css";
				dataviz_theme = "kendo.dataviz.black.min.css";
			}
			if (!StringUtils.equalsIgnoreCase(my_theme, "kendo.blueopal.min.css")) {
				templateContent = StringTools.replace(templateContent, "kendo.blueopal.min.css", my_theme);
				templateContent = StringTools.replace(templateContent, "kendo.dataviz.blueopal.min.css", dataviz_theme);
			}
			sw = new java.io.StringWriter();
			TemplateExtUtils.process(context, templateContent, sw);
			sw.flush();
			String output = sw.toString().replaceAll("script-tag", "script");
			if (urlList != null && !urlList.isEmpty()) {
				for (PageUrlConversion bean : urlList) {
					if (StringUtils.isNotEmpty(bean.getSrcUrl()) && StringUtils.isNotEmpty(bean.getDestUrl())) {
						output = StringTools.replaceIgnoreCase(output, bean.getSrcUrl(), bean.getDestUrl());
					}
				}
			}
			writer.write(output);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("预览错误：" + e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
			if (sw != null) {
				sw.close();
			}
		}
	}

	@ResponseBody
	@RequestMapping("/publishAll")
	public byte[] publishAll(HttpServletRequest request, ModelMap modelMap) {
		String path = request.getServletContext().getRealPath("/");
		FormPageQuery formPageQuery = new FormPageQuery();
		formPageQuery.setDeleteFlag(0);
		formPageQuery.setLocked(0);
		formPageQuery.setFormType("0");
		String uiType = RequestUtils.getString(request, "uiType", "bootstrap");
		Double count = RequestUtils.getDouble(request, "count", 200d);
		if (StringUtils.isNotEmpty(uiType)) {
			formPageQuery.setUiType(uiType);
		}
		List<FormPage> list = formPageService.list(formPageQuery);
		// 查询所有控件
		FormComponentQuery query = new FormComponentQuery();
		query.setLocked(0);
		query.setDeleteFlag(0);
		List<FormComponent> formComponents = formComponentService.getComponentPropertyList(query);

		exec(list, count, (subList) -> {
			FormPage formPage = null;
			for (Object object : subList) {
				formPage = (FormPage) object;
				if (formPage != null && formPage.getOutputHtml() != null) {
					try {
						// 获取页面对应的规则
						List<FormRule> formRules = formRuleService.getRules(formPage.getId());
						// 执行发布
						// FormPageParserUtil.publish(formPage, formRules,
						// formComponents, path, formPageService);
						Class<?> clazz = (Class<?>) Class.forName(Global.PAGE_PARSER_CLASS_NAME);
						if (clazz != null) {
							Object model = clazz.newInstance();
							Method method = clazz.getMethod("publish", FormPage.class, List.class, List.class, String.class, FormPageService.class);
							method.invoke(model, formPage, formRules, formComponents, path, formPageService);
						}
					} catch (Exception e) {
						e.printStackTrace();
						// return ResponseUtils.responseResult(false);
					}
				}
			}
			return null;
		});
		return ResponseUtils.responseResult(true);
	}

	protected FormRulePropertyService formRulePropertyService;

	/**
	 * 页面初始化赋值
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private JSONObject initPageDetail(HttpServletRequest request, Map<String, Object> params) throws IOException {
		String pageId = ParamUtils.getString(params, "id");// pageId
		if (pageId == null)
			throw new RuntimeException("pageId is null");
		// copy对象
		Map<String, Object> newParams = new HashMap();
		newParams.putAll(params);
		JSONObject result = new JSONObject(newParams);
		Map<String, String> ruleMap = this.getRuleMapByPageIdAndEleId(pageId, pageId);

		this.paramsFilter(params, ruleMap);
		if (ruleMap != null) {
			result.put("pageRuleId", ruleMap.get("__pageRuleId"));
			JSONArray jsonArray = null;
			JSONObject controlRule = null;
			// 页面输入输出参数对应
			String param = null;
			JSONObject jsonObject = null;
			Map<String, Object> datas = new HashMap<String, Object>();// 存储参数值,查询结果
			String inParamDefinedStr = ruleMap.get("inParamDefined");// 页面级参数
			if (!isNullOrEmpty(inParamDefinedStr)) {
				jsonArray = JSONArray.parseArray(inParamDefinedStr);
				if (jsonArray != null && !jsonArray.isEmpty()) {
					jsonObject = jsonArray.getJSONObject(0);
					if (jsonObject.containsKey("type")) {
						jsonArray = jsonObject.getJSONArray("source");
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							param = jsonObject.getString("param");
							if (params.get(param) != null) {
								datas.put(param, "" + params.get(param) + "");
							}
						}
					} else {
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							param = jsonObject.getString("param");
							if (params.get(param) != null) {
								datas.put(param, "" + params.get(param) + "");
							}
						}
					}
				}
			}

			// 已经对页面进行处理的 Pattern.matches("("+excludeRoleStr+").*",key)
			String[] excludeRole = new String[] { "definedTable", "grid", "ztree", "video", "treelist", "charts", "metroselect" };
			String excludeRoleStr = ListUtil.join(excludeRole, "|");

			String str = ruleMap.get("paraType");
			if (!isNullOrEmpty(str)) {
				jsonArray = JSONArray.parseArray(str);
				str = jsonArray.getJSONObject(0).getString("datas");
				controlRule = JSON.parseObject(str);
				Set<String> controlRuleSet = controlRule.keySet();
				JSONObject controlRuleObj = new JSONObject();
				for (String key : controlRuleSet) {
					if (!Pattern.matches("^(" + excludeRoleStr + ").*",
							key)/* !key.startsWith("definedTable") */) {
						controlRuleObj.put(key, controlRule.get(key));
					}
				}
				result.put("controlRule", controlRuleObj.isEmpty() ? null : controlRuleObj);
			}

			String dataSetId = null;
			str = ruleMap.get("dataSourceSet"); // 页面数据源
			JSONArray idValues = new JSONArray();
			if (!isNullOrEmpty(str)) {
				JSONArray tmp = JSONArray.parseArray(str);
				if (tmp != null && !tmp.isEmpty()) {
					JSONObject dataSource = JSONArray.parseArray(str).getJSONObject(0);
					String dataSourceStr = dataSource.getString("datasource");
					if (StringUtils.isNotBlank(dataSourceStr)) {
						jsonArray = JSONArray.parseArray(dataSourceStr);
					} else {
						/**
						 * 单个数据集
						 */
						jsonArray = JSONArray.parseArray(str);
					}
					List<JSONObject> batchRules = new ArrayList<JSONObject>();
					if (jsonArray != null && !jsonArray.isEmpty()) {
						JSONObject dataSources = new JSONObject();
						result.put("__idValues", idValues);
						result.put("dataSources", dataSources);
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							String batchRule = jsonObject.getString("batchRules");
							if (!isNullOrEmpty(batchRule)) {
								batchRules.add(JSON.parseObject(batchRule));
								continue;
							}

							dataSetId = jsonObject.getString("datasetId");
							String inParameters = ruleMap.get("inParameters");
							// 获取页面传递参数
							String psql = ParamsSqlHelper.getParamSql(JSON.toJSONString(datas), inParameters, dataSetId);
							logger.info(psql);
							boolean ok = true;
							if (!isNullOrEmpty(inParameters)) {
								ok = this.initParameters(inParameters, datas, result, dataSetId);
							}
							if (StringUtils.isNotBlank(dataSetId) && (ok || !"".equals(psql))) {
								// 构建sql start
								try {
									DataSetBuilder builder = new DataSetBuilder();
									JSONObject json = builder.getJson(dataSetId, psql, null, 0, 1, datas);
									if (json != null) {
										JSONArray arr = json.getJSONArray("rows");
										JSONObject job, newJob;
										if (arr != null && arr.size() > 0) {
											job = arr.getJSONObject(0);
											// 当前键和页面id冲突
											newJob = JSON.parseObject(job.toJSONString());
											newJob.put("_id_", newJob.get("id"));
											newJob.remove("id");
											params.putAll(newJob);
											// params.putAll(job =
											// arr.getJSONObject(0));
											dataSources.put(dataSetId, convertObj(job));
											DataSet ds = dataSetService.getDataSet(dataSetId);
											if (ds != null) {
												if (StringUtils.isNotEmpty(ds.getPrimaryKeys())) { // 获取主键
													if (CollectionUtils.isNotEmpty(ds.getFromSegments())) {
														String tn = ds.getFromSegments().get(0).getTableName();

														if (ds.getPrimaryKeys() != null) {
															Set<String> set = new HashSet<>(Arrays.asList(//
																	StringUtils.split(ds.getPrimaryKeys(), ",")));

															String key;
															for (SelectSegment select : ds.getSelectSegments()) {
																if (!set.contains(select.getColumnName())) {
																	continue;
																}
																key = select.getColumnName();
																Map<String, Object> map = new HashMap<String, Object>();
																map.put("tn", tn);
																map.put("value", params.getOrDefault(tn + "_0_" + key, //
																		params.getOrDefault(select.getColumnLabel(), "")));
																map.put("cn", key);
																idValues.add(map);
																if (StringUtils.startsWithIgnoreCase(key, "id")) {
																	params.put(tn + "_0_id", map.get("value"));
																}
															}
														}

														/*
														 * String keys[] =
														 * StringUtils.split(ds.
														 * getPrimaryKeys(),
														 * ","); for (String key
														 * : keys) { Map<String,
														 * Object> map = new
														 * HashMap<String,
														 * Object>();
														 * map.put("tn", tn);
														 * map.put("value",
														 * params.get(tn + "_0_"
														 * + key));
														 * map.put("cn", key);
														 * idValues.add(map); if
														 * (StringUtils.
														 * startsWithIgnoreCase(
														 * key, "id")) {
														 * params.put(tn +
														 * "_0_id",
														 * map.get("value")); }
														 * }
														 */
													}

												}
											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									logger.debug(e.getMessage());
									throw e;
								}
							}
						}
					}
					result.put("batchRules", batchRules);
				}
			}
			String idValue = null;
			if (controlRule != null) {
				Map<String, Object> data = new HashMap<String, Object>();// 页面控件id对应值

				if (CollectionUtils.isNotEmpty(idValues)) {
					for (int i = 0; i < idValues.size(); i++) {
						JSONObject json = idValues.getJSONObject(i);

						if ("id".equalsIgnoreCase(json.getString("cn"))) {
							idValue = json.getString("value");
						}

					}
				}

				for (String key : controlRule.keySet()) {
					jsonArray = JSONArray.parseArray(controlRule.getString(key));
					for (int k = 0; k < jsonArray.size(); k++) {
						JSONObject j = jsonArray.getJSONObject(k);
						String columnName = j.getString("columnName");
						if (StringUtils.isBlank(columnName)) {
							columnName = j.getString("inparam");
						}
						if (columnName != null) {
							j.put("idValue", idValue == null ? params.get(columnName.split("_0_")[0] + "_0_id") : idValue);
							if (key.startsWith("editor")) {
								j.put("value", params.get(columnName) != null ? params.get(columnName).toString().replace("\"", "\\\"") : "");
							} else if (key.startsWith("cell_")) {
								j.put("value", params.get(columnName) != null ? params.get(columnName).toString().replace("\\\"", "\\\\\"").replace("\"", "\\\"") : "");
							} else {
								// 防止注入
								Object obj = params.get(columnName);
								if (obj instanceof String) {
									j.put("value", params.get(columnName) != null ? HtmlUtils.htmlEscape(params.get(columnName).toString()) : "");
								} else {
									j.put("value", params.get(columnName));
								}
							}
						}
						if (idValue == null)
							idValue = j.getString("idValue");
					}
					data.put(key, JSON.toJSON(jsonArray));
				}
				result.put("data", data);
			}
			result.put("__idValue", idValue);

			str = ruleMap.get("saveSourceSet"); // 保存设置

			try {
				str = str == null ? ruleMap.get("linkageControlIn") : str;
				if (StringUtils.isNotBlank(str)) {

					result.put("handleColumns", JSON.parseArray(str));
				}

			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

		}

		return result;
	}

	/**
	 * 转换
	 * 
	 * @param oldObj
	 * @return
	 */
	private JSONObject convertObj(JSONObject oldObj) {
		JSONObject newObj = new JSONObject();
		Set<String> keys = oldObj.keySet();
		Object obj = null;
		for (String key : keys) {
			obj = oldObj.get(key);
			if (obj instanceof String) {
				newObj.put(key, obj != null ? obj.toString().replace("\\\"", "\\\\\"").replaceAll("\"", "\\\\\"") : obj);
			} else {
				newObj.put(key, obj);
			}
		}

		return newObj;
	}

	private Map<String, String> getRuleMapByPageIdAndEleId(String pageId, String eleId) {
		Map<String, String> ruleMap = null;
		List<FormRule> frs = formRuleService.getRulesByEleId(pageId, eleId);
		if (frs != null && frs.size() > 0) {
			FormRule fr = frs.get(0);
			ruleMap = this.getRuleMap(fr.getId());
			ruleMap.put("__pageRuleId", fr.getId());
		}
		return ruleMap;
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private boolean initParameters(String inParameters, Map<String, Object> datas, JSONObject result, String datasetId) {
		boolean rst = false;
		if (!isNullOrEmpty(inParameters)) {
			Map<String, List<Map<String, Object>>> tables = new HashMap<String, List<Map<String, Object>>>();
			JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
			if (jsonArray0 != null && !jsonArray0.isEmpty()) {
				for (int i = 0; i < jsonArray0.size(); i++) {
					String tabId = jsonArray0.getJSONObject(i).getString("tabId");
					if (!datasetId.equals(tabId)) {
						continue;
					}
					JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
					if (jsonArray.isEmpty()) {
						rst = true;
					}
					for (int ii = 0; ii < jsonArray.size(); ii++) {
						JSONObject jsonObject = jsonArray.getJSONObject(ii);
						String strs = jsonObject.getString("paramData");
						String expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);

						if (!isNullOrEmpty(expActVal))
							expActVal = StringUtils.split(expActVal, ".")[0];

						Object val = datas.get(expActVal);
						if (val == null) {
							continue;
						}

						strs = jsonObject.getString("fieldData");
						expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);
						if (!isNullOrEmpty(expActVal)) {
							String fieldName = StringUtils.split(expActVal, ".")[1];
							// if (fieldName.equalsIgnoreCase("id"))
							// continue;
							rst = true;
							String tableName = StringUtils.split(expActVal, ".")[0];
							Map<String, Object> m = new HashMap<String, Object>();
							if (!tables.containsKey(tableName))
								tables.put(tableName, new ArrayList<Map<String, Object>>());
							// String key = tableName + "_0_" + fieldName;
							m.put("fieldName", fieldName);
							m.put("value", val);
							tables.get(tableName).add(m);
						}
					}
				}
			}
			result.put("tables", tables);
		}
		return rst;
	}

	/**
	 * 过滤 zj 特殊参数
	 * 
	 * @param params
	 * @param ruleMap
	 */
	private void paramsFilter(Map<String, Object> params, Map<String, String> ruleMap) {

		if (params.get("flow-param") != null) { // pageId_0_id
			// String flowParam = params.get("flow-param").toString();
			// if (flowParam.contains("_0_")) {
			String inParameters = ruleMap.get("inParameters"), param = null;
			if (!isNullOrEmpty(inParameters)) {
				JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
				if (jsonArray0 != null && !jsonArray0.isEmpty()) {
					for (int i = 0; i < jsonArray0.size(); i++) {
						JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
						for (int ii = 0; ii < jsonArray.size(); ii++) {
							JSONObject jsonObject = jsonArray.getJSONObject(ii);
							String strs = jsonObject.getString("paramData");
							String expActVal = JSON.parseObject(strs).getString("expActVal");
							expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);

							if (!isNullOrEmpty(expActVal))
								param = StringUtils.split(expActVal, ".")[0];

							strs = jsonObject.getString("fieldData");
							expActVal = JSON.parseObject(strs).getString("expActVal");
							expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);
							if (!isNullOrEmpty(expActVal)) {
								String fieldName = StringUtils.split(expActVal, ".")[1];
								if (fieldName.equalsIgnoreCase("id") && param != null) {
									params.put(param, params.get("flow-param"));
								}
							}
						}
						// }
					}
				}
			}
		}
	}

	/**
	 * 多线程执行
	 * 
	 * @param list
	 *            多线程处理数组
	 * @param count
	 *            分片数量
	 * @param func
	 *            执行方法
	 */
	protected static void exec(List<? extends Object> list, Double count, Function<List<? extends Object>, ? extends Object> func) {
		ExecutorService es = Executors.newCachedThreadPool();
		double time = Double.parseDouble(list.size() + "") / count;
		int timeInt = (int) Math.ceil(time);
		int aa = 0;
		for (int i = 0; i < timeInt; i++) {
			aa = (int) (i * count);
			List<? extends Object> subList = list.subList(0 + aa, i == timeInt - 1 ? list.size() : count.intValue() + aa);
			es.execute(() -> {
				func.apply(subList);
			});
		}
	}

}
