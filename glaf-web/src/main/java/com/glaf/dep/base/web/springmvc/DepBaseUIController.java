package com.glaf.dep.base.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;
import com.glaf.dep.base.service.*;
import com.glaf.dep.base.util.*;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/base/depBaseUI")
@RequestMapping("/dep/base/depBaseUI")
public class DepBaseUIController {
	protected static final Log logger = LogFactory
			.getLog(DepBaseUIController.class);

	protected DepBaseUIService depBaseUIService;

	public DepBaseUIController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseUIService")
	public void setDepBaseUIService(DepBaseUIService depBaseUIService) {
		this.depBaseUIService = depBaseUIService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseUI depBaseUI = new DepBaseUI();
		Tools.populate(depBaseUI, params);

		depBaseUI.setCode(request.getParameter("code"));
		depBaseUI.setName(request.getParameter("name"));
		depBaseUI.setType(request.getParameter("type"));
		depBaseUI.setCreator(request.getParameter("creator"));
		depBaseUI.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));
		depBaseUI.setModifier(request.getParameter("modifier"));
		depBaseUI.setModifyDateTime(RequestUtils.getDate(request,
				"modifyDateTime"));
		depBaseUI.setDelFlag(request.getParameter("delFlag"));

		// depBaseUI.setCreateBy(actorId);

		depBaseUIService.save(depBaseUI);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepBaseUI")
	public byte[] saveDepBaseUI(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseUI depBaseUI = new DepBaseUI();
		try {
			Tools.populate(depBaseUI, params);
			depBaseUI.setCode(request.getParameter("code"));
			depBaseUI.setName(request.getParameter("name"));
			depBaseUI.setType(request.getParameter("type"));
			if (depBaseUI.getId() == null) {
				depBaseUI.setCreator(actorId);
				depBaseUI.setCreateDateTime(new Date());
			}
			depBaseUI.setModifier(actorId);
			depBaseUI.setModifyDateTime(new Date());
			depBaseUI.setDelFlag("0");
			this.depBaseUIService.save(depBaseUI);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	DepBaseUI saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepBaseUI depBaseUI = new DepBaseUI();
		try {
			Tools.populate(depBaseUI, model);
			depBaseUI.setCode(ParamUtils.getString(model, "code"));
			depBaseUI.setName(ParamUtils.getString(model, "name"));
			depBaseUI.setType(ParamUtils.getString(model, "type"));
			depBaseUI.setCreator(ParamUtils.getString(model, "creator"));
			depBaseUI.setCreateDateTime(ParamUtils.getDate(model,
					"createDateTime"));
			depBaseUI.setModifier(ParamUtils.getString(model, "modifier"));
			depBaseUI.setModifyDateTime(ParamUtils.getDate(model,
					"modifyDateTime"));
			depBaseUI.setDelFlag(ParamUtils.getString(model, "delFlag"));
			depBaseUI.setCreator(actorId);
			this.depBaseUIService.save(depBaseUI);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depBaseUI;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseUI depBaseUI = depBaseUIService.getDepBaseUI(request
				.getParameter("id"));

		Tools.populate(depBaseUI, params);

		depBaseUI.setCode(request.getParameter("code"));
		depBaseUI.setName(request.getParameter("name"));
		depBaseUI.setType(request.getParameter("type"));
		depBaseUI.setCreator(request.getParameter("creator"));
		depBaseUI.setCreateDateTime(RequestUtils.getDate(request,
				"createDateTime"));
		depBaseUI.setModifier(request.getParameter("modifier"));
		depBaseUI.setModifyDateTime(RequestUtils.getDate(request,
				"modifyDateTime"));
		depBaseUI.setDelFlag(request.getParameter("delFlag"));

		depBaseUIService.save(depBaseUI);

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
					DepBaseUI depBaseUI = depBaseUIService.getDepBaseUI(String
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depBaseUI != null
							&& (StringUtils.equals(depBaseUI.getCreator(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// depBaseUI.setDeleteFlag(1);
						depBaseUIService.save(depBaseUI);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepBaseUI depBaseUI = depBaseUIService.getDepBaseUI(String
					.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depBaseUI != null
					&& (StringUtils.equals(depBaseUI.getCreator(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// depBaseUI.setDeleteFlag(1);
				depBaseUIService.save(depBaseUI);
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
		DepBaseUI depBaseUI = depBaseUIService.getDepBaseUI(request
				.getParameter("id"));
		if (depBaseUI != null) {
			request.setAttribute("depBaseUI", depBaseUI);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depBaseUI.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/base/depBaseUI/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseUI depBaseUI = depBaseUIService.getDepBaseUI(request
				.getParameter("id"));
		request.setAttribute("depBaseUI", depBaseUI);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depBaseUI.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/base/depBaseUI/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depBaseUI.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/base/depBaseUI/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseUIQuery query = new DepBaseUIQuery();
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
		int total = depBaseUIService.getDepBaseUICountByQueryCriteria(query);
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
			List<DepBaseUI> list = depBaseUIService
					.getDepBaseUIsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseUI depBaseUI : list) {
					JSONObject rowJSON = depBaseUI.toJsonObject();
					rowJSON.put("id", depBaseUI.getId());
					rowJSON.put("rowId", depBaseUI.getId());
					rowJSON.put("depBaseUIId", depBaseUI.getId());
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
		DepBaseUIQuery query = new DepBaseUIQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepBaseUIDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseUIService.getDepBaseUICountByQueryCriteria(query);
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
			List<DepBaseUI> list = depBaseUIService
					.getDepBaseUIsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseUI depBaseUI : list) {
					JSONObject rowJSON = depBaseUI.toJsonObject();
					rowJSON.put("id", depBaseUI.getId());
					rowJSON.put("depBaseUIId", depBaseUI.getId());
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

		return new ModelAndView("/dep/base/depBaseUI/list", modelMap);
	}

}
