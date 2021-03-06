package com.glaf.datamgr.web.springmvc;

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

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/operation")
@RequestMapping("/datamgr/operation")
public class OperationController {
	protected static final Log logger = LogFactory
			.getLog(OperationController.class);

	protected OperationService operationService;

	public OperationController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Operation operation = operationService.getOperation(Long
							.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */

					if (operation != null
							&& (StringUtils.equals(operation.getCreateBy(),
									loginContext.getActorId()) || loginContext
									.isSystemAdministrator())) {
						operationService.save(operation);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			Operation operation = operationService.getOperation(Long
					.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			if (operation != null
					&& (StringUtils.equals(operation.getCreateBy(),
							loginContext.getActorId()) || loginContext
							.isSystemAdministrator())) {
				operationService.save(operation);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Operation operation = operationService.getOperation(RequestUtils
				.getLong(request, "id"));
		if (operation != null) {
			request.setAttribute("operation", operation);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("operation.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/operation/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		OperationQuery query = new OperationQuery();
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
		int total = operationService.getOperationCountByQueryCriteria(query);
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

			List<Operation> list = operationService
					.getOperationsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Operation operation : list) {
					JSONObject rowJSON = operation.toJsonObject();
					rowJSON.put("id", operation.getId());
					rowJSON.put("rowId", operation.getId());
					rowJSON.put("operationId", operation.getId());
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

		return new ModelAndView("/datamgr/operation/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("operation.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/operation/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		OperationQuery query = new OperationQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		OperationDomainFactory.processDataRequest(dataRequest);

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
		int total = operationService.getOperationCountByQueryCriteria(query);
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

			List<Operation> list = operationService
					.getOperationsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Operation operation : list) {
					JSONObject rowJSON = operation.toJsonObject();
					rowJSON.put("id", operation.getId());
					rowJSON.put("operationId", operation.getId());
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

		Operation operation = new Operation();
		Tools.populate(operation, params);

		operation.setName(request.getParameter("name"));
		operation.setCode(request.getParameter("code"));
		operation.setDescription(request.getParameter("description"));
		operation.setMethod(request.getParameter("method"));
		operation.setUrl(request.getParameter("url"));
		operation.setTablename(request.getParameter("tablename"));
		operation.setIdField(request.getParameter("idField"));
		operation.setIdColumn(request.getParameter("idColumn"));
		operation.setIdJavaType(request.getParameter("idJavaType"));
		operation.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
		operation.setSort(RequestUtils.getInt(request, "sort"));
		operation.setCreateBy(actorId);

		operationService.save(operation);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveOperation")
	public byte[] saveOperation(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Operation operation = new Operation();
		try {
			Tools.populate(operation, params);
			operation.setName(request.getParameter("name"));
			operation.setCode(request.getParameter("code"));
			operation.setDescription(request.getParameter("description"));
			operation.setMethod(request.getParameter("method"));
			operation.setUrl(request.getParameter("url"));
			operation.setTablename(request.getParameter("tablename"));
			operation.setIdField(request.getParameter("idField"));
			operation.setIdColumn(request.getParameter("idColumn"));
			operation.setIdJavaType(request.getParameter("idJavaType"));
			operation.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
			operation.setSort(RequestUtils.getInt(request, "sort"));
			operation.setCreateBy(actorId);
			this.operationService.save(operation);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Operation saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Operation operation = new Operation();
		try {
			Tools.populate(operation, model);
			operation.setName(ParamUtils.getString(model, "name"));
			operation.setCode(ParamUtils.getString(model, "code"));
			operation
					.setDescription(ParamUtils.getString(model, "description"));
			operation.setMethod(ParamUtils.getString(model, "method"));
			operation.setUrl(ParamUtils.getString(model, "url"));
			operation.setTablename(ParamUtils.getString(model, "tablename"));
			operation.setIdField(ParamUtils.getString(model, "idField"));
			operation.setIdColumn(ParamUtils.getString(model, "idColumn"));
			operation.setIdJavaType(ParamUtils.getString(model, "idJavaType"));
			operation.setSqlDefId(ParamUtils.getLong(model, "sqlDefId"));
			operation.setSort(ParamUtils.getInt(model, "sort"));
			operation.setCreateBy(actorId);
			this.operationService.save(operation);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return operation;
	}

	@javax.annotation.Resource
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Operation operation = operationService.getOperation(RequestUtils
				.getLong(request, "id"));

		Tools.populate(operation, params);

		operation.setName(request.getParameter("name"));
		operation.setCode(request.getParameter("code"));
		operation.setDescription(request.getParameter("description"));
		operation.setMethod(request.getParameter("method"));
		operation.setUrl(request.getParameter("url"));
		operation.setTablename(request.getParameter("tablename"));
		operation.setIdField(request.getParameter("idField"));
		operation.setIdColumn(request.getParameter("idColumn"));
		operation.setIdJavaType(request.getParameter("idJavaType"));
		operation.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
		operation.setSort(RequestUtils.getInt(request, "sort"));

		operationService.save(operation);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Operation operation = operationService.getOperation(RequestUtils
				.getLong(request, "id"));
		request.setAttribute("operation", operation);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("operation.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/operation/view");
	}

}
