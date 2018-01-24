package com.glaf.ui.web.springmvc;

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

import com.alibaba.fastjson.JSON;
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
import com.glaf.ui.model.UserTheme;
import com.glaf.ui.query.UserThemeQuery;
import com.glaf.ui.service.UserThemeService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/ui/userTheme")
@RequestMapping("/ui/userTheme")
public class UserThemeController {
	protected static final Log logger = LogFactory
			.getLog(UserThemeController.class);

	protected UserThemeService userThemeService;

	public UserThemeController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Integer id = RequestUtils.getInteger(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					UserTheme userTheme = userThemeService.getUserTheme(Integer
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					if (userTheme != null
							&& (StringUtils.equals(userTheme.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// userTheme.setDeleteFlag(1);
						userThemeService.save(userTheme);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			UserTheme userTheme = userThemeService.getUserTheme(Integer
					.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (userTheme != null
					&& (StringUtils.equals(userTheme.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// userTheme.setDeleteFlag(1);
				userThemeService.save(userTheme);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		UserTheme userTheme = userThemeService.getUserTheme(RequestUtils
				.getInt(request, "id"));
		if (userTheme != null) {
			request.setAttribute("userTheme", userTheme);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("userTheme.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/ui/userTheme/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserThemeQuery query = new UserThemeQuery();
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
		int total = userThemeService.getUserThemeCountByQueryCriteria(query);
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

			List<UserTheme> list = userThemeService
					.getUserThemesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (UserTheme userTheme : list) {
					JSONObject rowJSON = (JSONObject) JSON.toJSON(userTheme);
					rowJSON.put("id", userTheme.getId());
					rowJSON.put("rowId", userTheme.getId());
					rowJSON.put("userThemeId", userTheme.getId());
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

		return new ModelAndView("/modules/ui/userTheme/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("userTheme.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/ui/userTheme/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserThemeQuery query = new UserThemeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		// UserThemeDomainFactory.processDataRequest(dataRequest);

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
		int total = userThemeService.getUserThemeCountByQueryCriteria(query);
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

			List<UserTheme> list = userThemeService
					.getUserThemesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (UserTheme userTheme : list) {
					JSONObject rowJSON = (JSONObject) JSON.toJSON(userTheme);
					rowJSON.put("id", userTheme.getId());
					rowJSON.put("userThemeId", userTheme.getId());
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

		UserTheme userTheme = new UserTheme();
		Tools.populate(userTheme, params);

		userTheme.setActorId(request.getParameter("actorId"));
		userTheme.setThemeStyle(request.getParameter("themeStyle"));
		userTheme.setLayoutModel(request.getParameter("layoutModel"));
		userTheme.setBackground(request.getParameter("background"));
		userTheme.setBackgroundType(request.getParameter("backgroundType"));
		userTheme.setCourse(RequestUtils.getInt(request, "course"));
		userTheme.setCreateBy(actorId);

		userThemeService.save(userTheme);

		return this.list(request, modelMap);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody UserTheme saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		UserTheme userTheme = new UserTheme();
		try {
			Tools.populate(userTheme, model);
			userTheme.setActorId(ParamUtils.getString(model, "actorId"));
			userTheme.setThemeStyle(ParamUtils.getString(model, "themeStyle"));
			userTheme
					.setLayoutModel(ParamUtils.getString(model, "layoutModel"));
			userTheme.setBackground(ParamUtils.getString(model, "background"));
			userTheme.setBackgroundType(ParamUtils.getString(model,
					"backgroundType"));
			userTheme.setCourse(ParamUtils.getInt(model, "course"));
			userTheme.setCreateBy(actorId);
			this.userThemeService.save(userTheme);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return userTheme;
	}

	@ResponseBody
	@RequestMapping("/saveUserTheme")
	public byte[] saveUserTheme(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserTheme userTheme = new UserTheme();
		try {
			Tools.populate(userTheme, params);
			userTheme.setActorId(request.getParameter("actorId"));
			userTheme.setThemeStyle(request.getParameter("themeStyle"));
			userTheme.setLayoutModel(request.getParameter("layoutModel"));
			userTheme.setBackground(request.getParameter("background"));
			userTheme.setBackgroundType(request.getParameter("backgroundType"));
			userTheme.setCourse(RequestUtils.getInt(request, "course"));
			userTheme.setCreateBy(actorId);

			this.userThemeService.save(userTheme);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.ui.service.userThemeService")
	public void setUserThemeService(UserThemeService userThemeService) {
		this.userThemeService = userThemeService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		UserTheme userTheme = userThemeService.getUserTheme(RequestUtils
				.getInt(request, "id"));

		Tools.populate(userTheme, params);

		userTheme.setActorId(request.getParameter("actorId"));
		userTheme.setThemeStyle(request.getParameter("themeStyle"));
		userTheme.setLayoutModel(request.getParameter("layoutModel"));
		userTheme.setBackground(request.getParameter("background"));
		userTheme.setBackgroundType(request.getParameter("backgroundType"));
		userTheme.setCourse(RequestUtils.getInt(request, "course"));
		userTheme.setCreateBy(request.getParameter("createBy"));
		userTheme.setCreateDate(RequestUtils.getDate(request, "createDate"));

		userThemeService.save(userTheme);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		UserTheme userTheme = userThemeService.getUserTheme(RequestUtils
				.getInt(request, "id"));
		request.setAttribute("userTheme", userTheme);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("userTheme.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/ui/userTheme/view");
	}

}
