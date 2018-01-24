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

@Controller("/dep/base/depBaseWdataSetColumn")
@RequestMapping("/dep/base/depBaseWdataSetColumn")
public class DepBaseWdataSetColumnController {
	protected static final Log logger = LogFactory
			.getLog(DepBaseWdataSetColumnController.class);

	protected DepBaseWdataSetColumnService depBaseWdataSetColumnService;

	public DepBaseWdataSetColumnController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseWdataSetColumnService")
	public void setDepBaseWdataSetColumnService(
			DepBaseWdataSetColumnService depBaseWdataSetColumnService) {
		this.depBaseWdataSetColumnService = depBaseWdataSetColumnService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseWdataSetColumn depBaseWdataSetColumn = new DepBaseWdataSetColumn();
		Tools.populate(depBaseWdataSetColumn, params);

		depBaseWdataSetColumn.setWdataSetId(RequestUtils.getLong(request,
				"wdataSetId"));
		depBaseWdataSetColumn.setColumnName(request.getParameter("columnName"));
		depBaseWdataSetColumn.setDataColumnName(request
				.getParameter("dataColumnName"));
		depBaseWdataSetColumn.setDefaultVal(request.getParameter("defaultVal"));
		depBaseWdataSetColumn.setCreator(request.getParameter("creator"));
		depBaseWdataSetColumn.setCreateDatetime(RequestUtils.getDate(request,
				"createDatetime"));
		depBaseWdataSetColumn.setModifier(request.getParameter("modifier"));
		depBaseWdataSetColumn.setModifyDatetime(RequestUtils.getDate(request,
				"modifyDatetime"));

		// depBaseWdataSetColumn.setModifier(actorId);

		depBaseWdataSetColumnService.save(depBaseWdataSetColumn);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepBaseWdataSetColumn")
	public byte[] saveDepBaseWdataSetColumn(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetColumn depBaseWdataSetColumn = new DepBaseWdataSetColumn();
		try {
			Tools.populate(depBaseWdataSetColumn, params);
			depBaseWdataSetColumn.setWdataSetId(RequestUtils.getLong(request,
					"wdataSetId"));
			depBaseWdataSetColumn.setColumnName(request
					.getParameter("columnName"));
			depBaseWdataSetColumn.setDataColumnName(request
					.getParameter("dataColumnName"));
			depBaseWdataSetColumn.setDefaultVal(request
					.getParameter("defaultVal"));
			depBaseWdataSetColumn.setCreator(request.getParameter("creator"));
			depBaseWdataSetColumn.setCreateDatetime(RequestUtils.getDate(
					request, "createDatetime"));
			depBaseWdataSetColumn.setModifier(request.getParameter("modifier"));
			depBaseWdataSetColumn.setModifyDatetime(RequestUtils.getDate(
					request, "modifyDatetime"));
			// depBaseWdataSetColumn.setModifier(actorId);
			this.depBaseWdataSetColumnService.save(depBaseWdataSetColumn);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody
	DepBaseWdataSetColumn saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepBaseWdataSetColumn depBaseWdataSetColumn = new DepBaseWdataSetColumn();
		try {
			Tools.populate(depBaseWdataSetColumn, model);
			depBaseWdataSetColumn.setWdataSetId(ParamUtils.getLong(model,
					"wdataSetId"));
			depBaseWdataSetColumn.setColumnName(ParamUtils.getString(model,
					"columnName"));
			depBaseWdataSetColumn.setDataColumnName(ParamUtils.getString(model,
					"dataColumnName"));
			depBaseWdataSetColumn.setDefaultVal(ParamUtils.getString(model,
					"defaultVal"));
			depBaseWdataSetColumn.setCreator(ParamUtils.getString(model,
					"creator"));
			depBaseWdataSetColumn.setCreateDatetime(ParamUtils.getDate(model,
					"createDatetime"));
			depBaseWdataSetColumn.setModifier(ParamUtils.getString(model,
					"modifier"));
			depBaseWdataSetColumn.setModifyDatetime(ParamUtils.getDate(model,
					"modifyDatetime"));
			depBaseWdataSetColumn.setModifier(actorId);
			this.depBaseWdataSetColumnService.save(depBaseWdataSetColumn);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depBaseWdataSetColumn;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseWdataSetColumn depBaseWdataSetColumn = depBaseWdataSetColumnService
				.getDepBaseWdataSetColumn(RequestUtils.getLong(request, "id"));

		Tools.populate(depBaseWdataSetColumn, params);

		depBaseWdataSetColumn.setWdataSetId(RequestUtils.getLong(request,
				"wdataSetId"));
		depBaseWdataSetColumn.setColumnName(request.getParameter("columnName"));
		depBaseWdataSetColumn.setDataColumnName(request
				.getParameter("dataColumnName"));
		depBaseWdataSetColumn.setDefaultVal(request.getParameter("defaultVal"));
		depBaseWdataSetColumn.setCreator(request.getParameter("creator"));
		depBaseWdataSetColumn.setCreateDatetime(RequestUtils.getDate(request,
				"createDatetime"));
		depBaseWdataSetColumn.setModifier(request.getParameter("modifier"));
		depBaseWdataSetColumn.setModifyDatetime(RequestUtils.getDate(request,
				"modifyDatetime"));

		depBaseWdataSetColumnService.save(depBaseWdataSetColumn);

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
					DepBaseWdataSetColumn depBaseWdataSetColumn = depBaseWdataSetColumnService
							.getDepBaseWdataSetColumn(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depBaseWdataSetColumn != null
							&& (StringUtils.equals(
									depBaseWdataSetColumn.getModifier(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						// depBaseWdataSetColumn.setDeleteFlag(1);
						depBaseWdataSetColumnService
								.save(depBaseWdataSetColumn);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepBaseWdataSetColumn depBaseWdataSetColumn = depBaseWdataSetColumnService
					.getDepBaseWdataSetColumn(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depBaseWdataSetColumn != null
					&& (StringUtils.equals(depBaseWdataSetColumn.getModifier(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				// depBaseWdataSetColumn.setDeleteFlag(1);
				depBaseWdataSetColumnService.save(depBaseWdataSetColumn);
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
		DepBaseWdataSetColumn depBaseWdataSetColumn = depBaseWdataSetColumnService
				.getDepBaseWdataSetColumn(RequestUtils.getLong(request, "id"));
		if (depBaseWdataSetColumn != null) {
			request.setAttribute("depBaseWdataSetColumn", depBaseWdataSetColumn);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depBaseWdataSetColumn.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/base/depBaseWdataSetColumn/edit",
				modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetColumn depBaseWdataSetColumn = depBaseWdataSetColumnService
				.getDepBaseWdataSetColumn(RequestUtils.getLong(request, "id"));
		request.setAttribute("depBaseWdataSetColumn", depBaseWdataSetColumn);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depBaseWdataSetColumn.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/base/depBaseWdataSetColumn/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depBaseWdataSetColumn.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/base/depBaseWdataSetColumn/query",
				modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetColumnQuery query = new DepBaseWdataSetColumnQuery();
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
		int total = depBaseWdataSetColumnService
				.getDepBaseWdataSetColumnCountByQueryCriteria(query);
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
			List<DepBaseWdataSetColumn> list = depBaseWdataSetColumnService
					.getDepBaseWdataSetColumnsByQueryCriteria(start, limit,
							query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseWdataSetColumn depBaseWdataSetColumn : list) {
					JSONObject rowJSON = depBaseWdataSetColumn.toJsonObject();
					rowJSON.put("id", depBaseWdataSetColumn.getId());
					rowJSON.put("rowId", depBaseWdataSetColumn.getId());
					rowJSON.put("depBaseWdataSetColumnId",
							depBaseWdataSetColumn.getId());
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
		DepBaseWdataSetColumnQuery query = new DepBaseWdataSetColumnQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepBaseWdataSetColumnDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseWdataSetColumnService
				.getDepBaseWdataSetColumnCountByQueryCriteria(query);
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
			List<DepBaseWdataSetColumn> list = depBaseWdataSetColumnService
					.getDepBaseWdataSetColumnsByQueryCriteria(start, limit,
							query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseWdataSetColumn depBaseWdataSetColumn : list) {
					JSONObject rowJSON = depBaseWdataSetColumn.toJsonObject();
					rowJSON.put("id", depBaseWdataSetColumn.getId());
					rowJSON.put("depBaseWdataSetColumnId",
							depBaseWdataSetColumn.getId());
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

		return new ModelAndView("/dep/base/depBaseWdataSetColumn/list",
				modelMap);
	}

}
