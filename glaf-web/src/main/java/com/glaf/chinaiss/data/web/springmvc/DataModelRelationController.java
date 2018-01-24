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

@Controller("/data/model/dataModelRelation")
@RequestMapping("/data/model/dataModelRelation")
public class DataModelRelationController {
	protected static final Log logger = LogFactory.getLog(DataModelRelationController.class);

	protected DataModelRelationService dataModelRelationService;

	public DataModelRelationController() {

	}

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.service.dataModelRelationService")
	public void setDataModelRelationService(DataModelRelationService dataModelRelationService) {
		this.dataModelRelationService = dataModelRelationService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataModelRelation dataModelRelation = new DataModelRelation();
		Tools.populate(dataModelRelation, params);

		dataModelRelation.setColumnName(request.getParameter("columnName"));
		dataModelRelation.setDescription(request.getParameter("description"));
		dataModelRelation.setType(request.getParameter("type"));
		dataModelRelation.setRelateTable(request.getParameter("relateTable"));
		dataModelRelation.setRelateColumn(request.getParameter("relateColumn"));
		dataModelRelation.setRelateType(request.getParameter("relateType"));
		dataModelRelation.setTopId(request.getParameter("topId"));
		dataModelRelation.setParentId(request.getParameter("parentId"));
		dataModelRelation.setCreateBy(request.getParameter("createBy"));
		dataModelRelation.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataModelRelation.setUpdateBy(request.getParameter("updateBy"));
		dataModelRelation.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		dataModelRelation.setListNo(RequestUtils.getInt(request, "listNo"));

		// dataModelRelation.setCreateBy(actorId);

		dataModelRelationService.save(dataModelRelation);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataModelRelation")
	public byte[] saveDataModelRelation(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelRelation dataModelRelation = new DataModelRelation();
		try {
			Tools.populate(dataModelRelation, params);
			dataModelRelation.setColumnName(request.getParameter("columnName"));
			dataModelRelation.setDescription(request.getParameter("description"));
			dataModelRelation.setType(request.getParameter("type"));
			dataModelRelation.setRelateTable(request.getParameter("relateTable"));
			dataModelRelation.setRelateColumn(request.getParameter("relateColumn"));
			dataModelRelation.setRelateType(request.getParameter("relateType"));
			dataModelRelation.setTopId(request.getParameter("topId"));
			dataModelRelation.setParentId(request.getParameter("parentId"));
			dataModelRelation.setCreateBy(request.getParameter("createBy"));
			dataModelRelation.setCreateDate(RequestUtils.getDate(request, "createDate"));
			dataModelRelation.setUpdateBy(request.getParameter("updateBy"));
			dataModelRelation.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			dataModelRelation.setListNo(RequestUtils.getInt(request, "listNo"));
			// dataModelRelation.setCreateBy(actorId);
			this.dataModelRelationService.save(dataModelRelation);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataModelRelation saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataModelRelation dataModelRelation = new DataModelRelation();
		try {
			Tools.populate(dataModelRelation, model);
			dataModelRelation.setColumnName(ParamUtils.getString(model, "columnName"));
			dataModelRelation.setDescription(ParamUtils.getString(model, "description"));
			dataModelRelation.setType(ParamUtils.getString(model, "type"));
			dataModelRelation.setRelateTable(ParamUtils.getString(model, "relateTable"));
			dataModelRelation.setRelateColumn(ParamUtils.getString(model, "relateColumn"));
			dataModelRelation.setRelateType(ParamUtils.getString(model, "relateType"));
			dataModelRelation.setTopId(ParamUtils.getString(model, "topId"));
			dataModelRelation.setParentId(ParamUtils.getString(model, "parentId"));
			dataModelRelation.setCreateBy(ParamUtils.getString(model, "createBy"));
			dataModelRelation.setCreateDate(ParamUtils.getDate(model, "createDate"));
			dataModelRelation.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			dataModelRelation.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			dataModelRelation.setListNo(ParamUtils.getInt(model, "listNo"));
			dataModelRelation.setCreateBy(actorId);
			this.dataModelRelationService.save(dataModelRelation);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataModelRelation;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataModelRelation dataModelRelation = dataModelRelationService.getDataModelRelation(request.getParameter("id"));

		Tools.populate(dataModelRelation, params);

		dataModelRelation.setColumnName(request.getParameter("columnName"));
		dataModelRelation.setDescription(request.getParameter("description"));
		dataModelRelation.setType(request.getParameter("type"));
		dataModelRelation.setRelateTable(request.getParameter("relateTable"));
		dataModelRelation.setRelateColumn(request.getParameter("relateColumn"));
		dataModelRelation.setRelateType(request.getParameter("relateType"));
		dataModelRelation.setTopId(request.getParameter("topId"));
		dataModelRelation.setParentId(request.getParameter("parentId"));
		dataModelRelation.setCreateBy(request.getParameter("createBy"));
		dataModelRelation.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataModelRelation.setUpdateBy(request.getParameter("updateBy"));
		dataModelRelation.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		dataModelRelation.setListNo(RequestUtils.getInt(request, "listNo"));

		dataModelRelationService.save(dataModelRelation);

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
					DataModelRelation dataModelRelation = dataModelRelationService
							.getDataModelRelation(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (dataModelRelation != null
							&& (StringUtils.equals(dataModelRelation.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// dataModelRelation.setDeleteFlag(1);
						dataModelRelationService.save(dataModelRelation);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataModelRelation dataModelRelation = dataModelRelationService.getDataModelRelation(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (dataModelRelation != null
					&& (StringUtils.equals(dataModelRelation.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// dataModelRelation.setDeleteFlag(1);
				dataModelRelationService.save(dataModelRelation);
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
		DataModelRelation dataModelRelation = dataModelRelationService.getDataModelRelation(request.getParameter("id"));
		if (dataModelRelation != null) {
			request.setAttribute("dataModelRelation", dataModelRelation);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataModelRelation.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/data/model/dataModelRelation/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelRelation dataModelRelation = dataModelRelationService.getDataModelRelation(request.getParameter("id"));
		request.setAttribute("dataModelRelation", dataModelRelation);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataModelRelation.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/data/model/dataModelRelation/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataModelRelation.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/data/model/dataModelRelation/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelRelationQuery query = new DataModelRelationQuery();
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
		int total = dataModelRelationService.getDataModelRelationCountByQueryCriteria(query);
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
			List<DataModelRelation> list = dataModelRelationService.getDataModelRelationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataModelRelation dataModelRelation : list) {
					JSONObject rowJSON = dataModelRelation.toJsonObject();
					rowJSON.put("id", dataModelRelation.getId());
					rowJSON.put("rowId", dataModelRelation.getId());
					rowJSON.put("dataModelRelationId", dataModelRelation.getId());
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
		DataModelRelationQuery query = new DataModelRelationQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DataModelRelationDomainFactory.processDataRequest(dataRequest);

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
		int total = dataModelRelationService.getDataModelRelationCountByQueryCriteria(query);
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
			List<DataModelRelation> list = dataModelRelationService.getDataModelRelationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataModelRelation dataModelRelation : list) {
					JSONObject rowJSON = dataModelRelation.toJsonObject();
					rowJSON.put("id", dataModelRelation.getId());
					rowJSON.put("dataModelRelationId", dataModelRelation.getId());
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

		return new ModelAndView("/data/model/dataModelRelation/list", modelMap);
	}

}
