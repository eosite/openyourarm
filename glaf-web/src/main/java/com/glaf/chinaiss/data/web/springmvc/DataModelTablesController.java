package com.glaf.chinaiss.data.web.springmvc;

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

import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;
import com.glaf.chinaiss.data.service.*;
import com.glaf.chinaiss.data.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/data/model/dataModelTables")
@RequestMapping("/data/model/dataModelTables")
public class DataModelTablesController {
	protected static final Log logger = LogFactory.getLog(DataModelTablesController.class);

	protected DataModelTablesService dataModelTablesService;

	public DataModelTablesController() {

	}

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.service.dataModelTablesService")
	public void setDataModelTablesService(DataModelTablesService dataModelTablesService) {
		this.dataModelTablesService = dataModelTablesService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataModelTables dataModelTables = new DataModelTables();
		Tools.populate(dataModelTables, params);

		dataModelTables.setTableName(request.getParameter("tableName"));
		dataModelTables.setDescription(request.getParameter("description"));
		dataModelTables.setType(request.getParameter("type"));
		dataModelTables.setTopId(request.getParameter("topId"));
		dataModelTables.setParentId(request.getParameter("parentId"));
		dataModelTables.setCreateBy(request.getParameter("createBy"));
		dataModelTables.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataModelTables.setUpdateBy(request.getParameter("updateBy"));
		dataModelTables.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		dataModelTables.setListNo(RequestUtils.getInt(request, "listNo"));

		// dataModelTables.setCreateBy(actorId);

		dataModelTablesService.save(dataModelTables);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataModelTables")
	public byte[] saveDataModelTables(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelTables dataModelTables = new DataModelTables();
		try {
			Tools.populate(dataModelTables, params);
			dataModelTables.setTableName(request.getParameter("tableName"));
			dataModelTables.setDescription(request.getParameter("description"));
			dataModelTables.setType(request.getParameter("type"));
			dataModelTables.setTopId(request.getParameter("topId"));
			dataModelTables.setParentId(request.getParameter("parentId"));
			dataModelTables.setCreateBy(request.getParameter("createBy"));
			dataModelTables.setCreateDate(RequestUtils.getDate(request, "createDate"));
			dataModelTables.setUpdateBy(request.getParameter("updateBy"));
			dataModelTables.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			dataModelTables.setListNo(RequestUtils.getInt(request, "listNo"));
			// dataModelTables.setCreateBy(actorId);
			this.dataModelTablesService.save(dataModelTables);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataModelTables saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataModelTables dataModelTables = new DataModelTables();
		try {
			Tools.populate(dataModelTables, model);
			dataModelTables.setTableName(ParamUtils.getString(model, "tableName"));
			dataModelTables.setDescription(ParamUtils.getString(model, "description"));
			dataModelTables.setType(ParamUtils.getString(model, "type"));
			dataModelTables.setTopId(ParamUtils.getString(model, "topId"));
			dataModelTables.setParentId(ParamUtils.getString(model, "parentId"));
			dataModelTables.setCreateBy(ParamUtils.getString(model, "createBy"));
			dataModelTables.setCreateDate(ParamUtils.getDate(model, "createDate"));
			dataModelTables.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			dataModelTables.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			dataModelTables.setListNo(ParamUtils.getInt(model, "listNo"));
			dataModelTables.setCreateBy(actorId);
			this.dataModelTablesService.save(dataModelTables);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataModelTables;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataModelTables dataModelTables = dataModelTablesService.getDataModelTables(request.getParameter("id"));

		Tools.populate(dataModelTables, params);

		dataModelTables.setTableName(request.getParameter("tableName"));
		dataModelTables.setDescription(request.getParameter("description"));
		dataModelTables.setType(request.getParameter("type"));
		dataModelTables.setTopId(request.getParameter("topId"));
		dataModelTables.setParentId(request.getParameter("parentId"));
		dataModelTables.setCreateBy(request.getParameter("createBy"));
		dataModelTables.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataModelTables.setUpdateBy(request.getParameter("updateBy"));
		dataModelTables.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		dataModelTables.setListNo(RequestUtils.getInt(request, "listNo"));

		dataModelTablesService.save(dataModelTables);

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
					DataModelTables dataModelTables = dataModelTablesService.getDataModelTables(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (dataModelTables != null
							&& (StringUtils.equals(dataModelTables.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// dataModelTables.setDeleteFlag(1);
						dataModelTablesService.save(dataModelTables);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataModelTables dataModelTables = dataModelTablesService.getDataModelTables(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (dataModelTables != null && (StringUtils.equals(dataModelTables.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// dataModelTables.setDeleteFlag(1);
				dataModelTablesService.save(dataModelTables);
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
		DataModelTables dataModelTables = dataModelTablesService.getDataModelTables(request.getParameter("id"));
		if (dataModelTables != null) {
			request.setAttribute("dataModelTables", dataModelTables);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataModelTables.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/data/model/dataModelTables/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelTables dataModelTables = dataModelTablesService.getDataModelTables(request.getParameter("id"));
		request.setAttribute("dataModelTables", dataModelTables);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataModelTables.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/data/model/dataModelTables/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataModelTables.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/data/model/dataModelTables/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelTablesQuery query = new DataModelTablesQuery();
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
		int total = dataModelTablesService.getDataModelTablesCountByQueryCriteria(query);
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
			List<DataModelTables> list = dataModelTablesService.getDataModelTablessByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataModelTables dataModelTables : list) {
					JSONObject rowJSON = dataModelTables.toJsonObject();
					rowJSON.put("id", dataModelTables.getId());
					rowJSON.put("rowId", dataModelTables.getId());
					rowJSON.put("dataModelTablesId", dataModelTables.getId());
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
		DataModelTablesQuery query = new DataModelTablesQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DataModelTablesDomainFactory.processDataRequest(dataRequest);

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
		int total = dataModelTablesService.getDataModelTablesCountByQueryCriteria(query);
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
			List<DataModelTables> list = dataModelTablesService.getDataModelTablessByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataModelTables dataModelTables : list) {
					JSONObject rowJSON = dataModelTables.toJsonObject();
					rowJSON.put("id", dataModelTables.getId());
					rowJSON.put("dataModelTablesId", dataModelTables.getId());
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

		return new ModelAndView("/data/model/dataModelTables/list", modelMap);
	}

}
