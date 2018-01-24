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
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.PageUrlConversion;
import com.glaf.form.core.query.PageUrlConversionQuery;
import com.glaf.form.core.service.PageUrlConversionService;
import com.glaf.form.core.util.PageUrlConversionDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/pageUrlConversion")
@RequestMapping("/form/pageUrlConversion")
public class PageUrlConversionController {
	protected static final Log logger = LogFactory.getLog(PageUrlConversionController.class);

	protected PageUrlConversionService pageUrlConversionService;

	public PageUrlConversionController() {

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
					PageUrlConversion pageUrlConversion = pageUrlConversionService
							.getPageUrlConversion(String.valueOf(x));
					if (pageUrlConversion != null && loginContext.isSystemAdministrator()) {
						pageUrlConversionService.deleteById(pageUrlConversion.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			PageUrlConversion pageUrlConversion = pageUrlConversionService.getPageUrlConversion(String.valueOf(id));
			if (pageUrlConversion != null && (loginContext.isSystemAdministrator())) {
				pageUrlConversionService.deleteById(pageUrlConversion.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		PageUrlConversion pageUrlConversion = pageUrlConversionService.getPageUrlConversion(request.getParameter("id"));
		if (pageUrlConversion != null) {
			request.setAttribute("pageUrlConversion", pageUrlConversion);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("pageUrlConversion.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/pageUrlConversion/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PageUrlConversionQuery query = new PageUrlConversionQuery();
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
		int total = pageUrlConversionService.getPageUrlConversionCountByQueryCriteria(query);
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

			List<PageUrlConversion> list = pageUrlConversionService.getPageUrlConversionsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (PageUrlConversion pageUrlConversion : list) {
					JSONObject rowJSON = pageUrlConversion.toJsonObject();
					rowJSON.put("id", pageUrlConversion.getId());
					rowJSON.put("rowId", pageUrlConversion.getId());
					rowJSON.put("pageUrlConversionId", pageUrlConversion.getId());
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

		return new ModelAndView("/form/pageUrlConversion/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("pageUrlConversion.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/pageUrlConversion/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PageUrlConversionQuery query = new PageUrlConversionQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		PageUrlConversionDomainFactory.processDataRequest(dataRequest);

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
		int total = pageUrlConversionService.getPageUrlConversionCountByQueryCriteria(query);
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

			List<PageUrlConversion> list = pageUrlConversionService.getPageUrlConversionsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (PageUrlConversion pageUrlConversion : list) {
					JSONObject rowJSON = pageUrlConversion.toJsonObject();
					rowJSON.put("id", pageUrlConversion.getId());
					rowJSON.put("pageUrlConversionId", pageUrlConversion.getId());
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

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		PageUrlConversion pageUrlConversion = new PageUrlConversion();
		Tools.populate(pageUrlConversion, params);

		pageUrlConversion.setSrcUrl(request.getParameter("srcUrl"));
		pageUrlConversion.setDestUrl(request.getParameter("destUrl"));
		pageUrlConversion.setLocked(RequestUtils.getInt(request, "locked"));

		pageUrlConversionService.save(pageUrlConversion);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody PageUrlConversion saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		PageUrlConversion pageUrlConversion = new PageUrlConversion();
		try {
			Tools.populate(pageUrlConversion, model);
			pageUrlConversion.setSrcUrl(ParamUtils.getString(model, "srcUrl"));
			pageUrlConversion.setDestUrl(ParamUtils.getString(model, "destUrl"));
			pageUrlConversion.setLocked(ParamUtils.getInt(model, "locked"));

			this.pageUrlConversionService.save(pageUrlConversion);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return pageUrlConversion;
	}

	@ResponseBody
	@RequestMapping("/savePageUrlConversion")
	public byte[] savePageUrlConversion(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PageUrlConversion pageUrlConversion = new PageUrlConversion();
		try {
			Tools.populate(pageUrlConversion, params);
			pageUrlConversion.setSrcUrl(request.getParameter("srcUrl"));
			pageUrlConversion.setDestUrl(request.getParameter("destUrl"));
			pageUrlConversion.setLocked(RequestUtils.getInt(request, "locked"));
			// pageUrlConversion.setCreateBy(actorId);
			this.pageUrlConversionService.save(pageUrlConversion);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "pageUrlConversionService")
	public void setPageUrlConversionService(PageUrlConversionService pageUrlConversionService) {
		this.pageUrlConversionService = pageUrlConversionService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		PageUrlConversion pageUrlConversion = pageUrlConversionService.getPageUrlConversion(request.getParameter("id"));

		Tools.populate(pageUrlConversion, params);

		pageUrlConversion.setSrcUrl(request.getParameter("srcUrl"));
		pageUrlConversion.setDestUrl(request.getParameter("destUrl"));
		pageUrlConversion.setLocked(RequestUtils.getInt(request, "locked"));

		pageUrlConversionService.save(pageUrlConversion);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		PageUrlConversion pageUrlConversion = pageUrlConversionService.getPageUrlConversion(request.getParameter("id"));
		request.setAttribute("pageUrlConversion", pageUrlConversion);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("pageUrlConversion.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/pageUrlConversion/view");
	}

}
