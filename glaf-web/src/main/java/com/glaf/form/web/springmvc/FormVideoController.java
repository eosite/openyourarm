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
import com.glaf.form.core.domain.FormVideo;
import com.glaf.form.core.query.FormVideoQuery;
import com.glaf.form.core.service.FormVideoService;
import com.glaf.form.core.util.FormVideoDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/video")
@RequestMapping("/form/video")
public class FormVideoController {
	protected static final Log logger = LogFactory
			.getLog(FormVideoController.class);

	protected FormVideoService formVideoService;

	public FormVideoController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formVideoService")
	public void setFormVideoService(FormVideoService formVideoService) {
		this.formVideoService = formVideoService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormVideo formVideo = new FormVideo();
		Tools.populate(formVideo, params);

		formVideo.setName(request.getParameter("name"));
		formVideo.setIp(request.getParameter("ip"));
		formVideo.setPort(request.getParameter("port"));
		formVideo.setStatus(RequestUtils.getInt(request, "status"));
		formVideo.setValided(RequestUtils.getInt(request, "valided"));
		formVideo.setUserName(request.getParameter("userName"));
		formVideo.setPwd(request.getParameter("pwd"));
		formVideo.setUpdateBy(request.getParameter("updateBy"));
		formVideo.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formVideo.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		formVideo.setCreateBy(request.getParameter("createBy"));

		// formVideo.setCreateBy(actorId);

		formVideoService.save(formVideo);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormVideo")
	public byte[] saveFormVideo(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormVideo formVideo = new FormVideo();
		try {
			Tools.populate(formVideo, params);
			formVideo.setName(request.getParameter("name"));
			formVideo.setIp(request.getParameter("ip"));
			formVideo.setPort(request.getParameter("port"));
			formVideo.setStatus(RequestUtils.getInt(request, "status"));
			formVideo.setValided(RequestUtils.getInt(request, "valided"));
			formVideo.setUserName(request.getParameter("userName"));
			formVideo.setPwd(request.getParameter("pwd"));
			formVideo.setUpdateBy(request.getParameter("updateBy"));
			formVideo
					.setCreateDate(RequestUtils.getDate(request, "createDate"));
			formVideo
					.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			formVideo.setCreateBy(request.getParameter("createBy"));
			// formVideo.setCreateBy(actorId);
			this.formVideoService.save(formVideo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	FormVideo saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormVideo formVideo = new FormVideo();
		try {
			Tools.populate(formVideo, model);
			formVideo.setName(ParamUtils.getString(model, "name"));
			formVideo.setIp(ParamUtils.getString(model, "ip"));
			formVideo.setPort(ParamUtils.getString(model, "port"));
			formVideo.setStatus(ParamUtils.getInt(model, "status"));
			formVideo.setValided(ParamUtils.getInt(model, "valided"));
			formVideo.setUserName(ParamUtils.getString(model, "userName"));
			formVideo.setPwd(ParamUtils.getString(model, "pwd"));
			formVideo.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			formVideo.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formVideo.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			formVideo.setCreateBy(ParamUtils.getString(model, "createBy"));
			formVideo.setCreateBy(actorId);
			this.formVideoService.save(formVideo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formVideo;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormVideo formVideo = formVideoService.getFormVideo(RequestUtils
				.getLong(request, "id"));

		Tools.populate(formVideo, params);

		formVideo.setName(request.getParameter("name"));
		formVideo.setIp(request.getParameter("ip"));
		formVideo.setPort(request.getParameter("port"));
		formVideo.setStatus(RequestUtils.getInt(request, "status"));
		formVideo.setValided(RequestUtils.getInt(request, "valided"));
		formVideo.setUserName(request.getParameter("userName"));
		formVideo.setPwd(request.getParameter("pwd"));
		formVideo.setUpdateBy(request.getParameter("updateBy"));
		formVideo.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formVideo.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		formVideo.setCreateBy(request.getParameter("createBy"));

		formVideoService.save(formVideo);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long id = RequestUtils.getLong(request, "formVideoId");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormVideo formVideo = formVideoService.getFormVideo(Long
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formVideo != null
							&& (StringUtils.equals(formVideo.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// formVideo.setDeleteFlag(1);
						formVideoService.save(formVideo);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormVideo formVideo = formVideoService.getFormVideo(Long
					.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formVideo != null
					&& (StringUtils.equals(formVideo.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// formVideo.setDeleteFlag(1);
				formVideoService.deleteById(id);
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
		FormVideo formVideo = formVideoService.getFormVideo(RequestUtils
				.getLong(request, "id"));
		if (formVideo != null) {
			request.setAttribute("formVideo", formVideo);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formVideo.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/defined/video/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormVideo formVideo = formVideoService.getFormVideo(RequestUtils
				.getLong(request, "id"));
		request.setAttribute("formVideo", formVideo);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formVideo.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/defined/video/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formVideo.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/defined/video/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormVideoQuery query = new FormVideoQuery();
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
		int total = formVideoService.getFormVideoCountByQueryCriteria(query);
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
			List<FormVideo> list = formVideoService
					.getFormVideosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormVideo formVideo : list) {
					JSONObject rowJSON = formVideo.toJsonObject();
					rowJSON.put("id", formVideo.getId());
					rowJSON.put("rowId", formVideo.getId());
					rowJSON.put("formVideoId", formVideo.getId());
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
		FormVideoQuery query = new FormVideoQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormVideoDomainFactory.processDataRequest(dataRequest);

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
		int total = formVideoService.getFormVideoCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<FormVideo> list = formVideoService
					.getFormVideosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormVideo formVideo : list) {
					JSONObject rowJSON = formVideo.toJsonObject();
					rowJSON.put("id", formVideo.getId());
					rowJSON.put("formVideoId", formVideo.getId());
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

		return new ModelAndView("/form/defined/video/list", modelMap);
	}

	@RequestMapping("selectWin")
	public ModelAndView selectWin(HttpServletRequest request, ModelMap modelMap) {
		return new ModelAndView("/form/defined/video/selectWin", modelMap);
	}

}
