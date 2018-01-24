package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
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

@Controller("/dataset/dataSetMapping")
@RequestMapping("/dataset/dataSetMapping")
public class DataSetMappingController {
	protected static final Log logger = LogFactory.getLog(DataSetMappingController.class);

	protected DataSetMappingService dataSetMappingService;

	public DataSetMappingController() {

	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.dataSetMappingService")
	public void setDataSetMappingService(DataSetMappingService dataSetMappingService) {
		this.dataSetMappingService = dataSetMappingService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataSetMapping dataSetMapping = new DataSetMapping();
		Tools.populate(dataSetMapping, params);

		dataSetMapping.setDsName(request.getParameter("dsName"));
		dataSetMapping.setDsmId(request.getParameter("dsmId"));
		dataSetMapping.setDsmName(request.getParameter("dsmName"));
		dataSetMapping.setStatus(RequestUtils.getInt(request, "status"));
		dataSetMapping.setType(request.getParameter("type"));
		dataSetMapping.setTreeId(request.getParameter("treeId"));
		dataSetMapping.setTopId(request.getParameter("topId"));
		dataSetMapping.setParentId(request.getParameter("parentId"));
		dataSetMapping.setCreateBy(request.getParameter("createBy"));
		dataSetMapping.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataSetMapping.setUpdateBy(request.getParameter("updateBy"));
		dataSetMapping.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		// dataSetMapping.setCreateBy(actorId);

		dataSetMappingService.save(dataSetMapping);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataSetMapping")
	public byte[] saveDataSetMapping(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetMapping dataSetMapping = new DataSetMapping();
		try {
			Tools.populate(dataSetMapping, params);
			dataSetMapping.setDsName(request.getParameter("dsName"));
			dataSetMapping.setDsmId(request.getParameter("dsmId"));
			dataSetMapping.setDsmName(request.getParameter("dsmName"));
			dataSetMapping.setStatus(RequestUtils.getInt(request, "status"));
			dataSetMapping.setType(request.getParameter("type"));
			dataSetMapping.setTreeId(request.getParameter("treeId"));
			dataSetMapping.setTopId(request.getParameter("topId"));
			dataSetMapping.setParentId(request.getParameter("parentId"));
			dataSetMapping.setCreateBy(request.getParameter("createBy"));
			dataSetMapping.setCreateDate(RequestUtils.getDate(request, "createDate"));
			dataSetMapping.setUpdateBy(request.getParameter("updateBy"));
			dataSetMapping.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			// dataSetMapping.setCreateBy(actorId);
			this.dataSetMappingService.save(dataSetMapping);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataSetMapping saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataSetMapping dataSetMapping = new DataSetMapping();
		try {
			Tools.populate(dataSetMapping, model);
			dataSetMapping.setDsName(ParamUtils.getString(model, "dsName"));
			dataSetMapping.setDsmId(ParamUtils.getString(model, "dsmId"));
			dataSetMapping.setDsmName(ParamUtils.getString(model, "dsmName"));
			dataSetMapping.setStatus(ParamUtils.getInt(model, "status"));
			dataSetMapping.setType(ParamUtils.getString(model, "type"));
			dataSetMapping.setTreeId(ParamUtils.getString(model, "treeId"));
			dataSetMapping.setTopId(ParamUtils.getString(model, "topId"));
			dataSetMapping.setParentId(ParamUtils.getString(model, "parentId"));
			dataSetMapping.setCreateBy(ParamUtils.getString(model, "createBy"));
			dataSetMapping.setCreateDate(ParamUtils.getDate(model, "createDate"));
			dataSetMapping.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			dataSetMapping.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			dataSetMapping.setCreateBy(actorId);
			this.dataSetMappingService.save(dataSetMapping);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataSetMapping;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataSetMapping dataSetMapping = dataSetMappingService.getDataSetMapping(request.getParameter("id"));

		Tools.populate(dataSetMapping, params);

		dataSetMapping.setDsName(request.getParameter("dsName"));
		dataSetMapping.setDsmId(request.getParameter("dsmId"));
		dataSetMapping.setDsmName(request.getParameter("dsmName"));
		dataSetMapping.setStatus(RequestUtils.getInt(request, "status"));
		dataSetMapping.setType(request.getParameter("type"));
		dataSetMapping.setTreeId(request.getParameter("treeId"));
		dataSetMapping.setTopId(request.getParameter("topId"));
		dataSetMapping.setParentId(request.getParameter("parentId"));
		dataSetMapping.setCreateBy(request.getParameter("createBy"));
		dataSetMapping.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataSetMapping.setUpdateBy(request.getParameter("updateBy"));
		dataSetMapping.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		dataSetMappingService.save(dataSetMapping);

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
					DataSetMapping dataSetMapping = dataSetMappingService.getDataSetMapping(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (dataSetMapping != null
							&& (StringUtils.equals(dataSetMapping.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// dataSetMapping.setDeleteFlag(1);
						dataSetMappingService.save(dataSetMapping);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataSetMapping dataSetMapping = dataSetMappingService.getDataSetMapping(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (dataSetMapping != null && (StringUtils.equals(dataSetMapping.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// dataSetMapping.setDeleteFlag(1);
				dataSetMappingService.save(dataSetMapping);
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
		DataSetMapping dataSetMapping = dataSetMappingService.getDataSetMapping(request.getParameter("id"));
		if (dataSetMapping != null) {
			request.setAttribute("dataSetMapping", dataSetMapping);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataSetMapping.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dataset/dataSetMapping/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetMapping dataSetMapping = dataSetMappingService.getDataSetMapping(request.getParameter("id"));
		request.setAttribute("dataSetMapping", dataSetMapping);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataSetMapping.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dataset/dataSetMapping/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataSetMapping.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dataset/dataSetMapping/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetMappingQuery query = new DataSetMappingQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setParentId_(MapUtils.getString(params, "parentId"));

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
		int total = dataSetMappingService.getDataSetMappingCountByQueryCriteria(query);
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
			List<DataSetMapping> list = dataSetMappingService.getDataSetMappingsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataSetMapping dataSetMapping : list) {
					JSONObject rowJSON = dataSetMapping.toJsonObject();
					rowJSON.put("id", dataSetMapping.getId());
					rowJSON.put("rowId", dataSetMapping.getId());
					rowJSON.put("dataSetMappingId", dataSetMapping.getId());
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
		DataSetMappingQuery query = new DataSetMappingQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DataSetMappingDomainFactory.processDataRequest(dataRequest);

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
		int total = dataSetMappingService.getDataSetMappingCountByQueryCriteria(query);
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
			List<DataSetMapping> list = dataSetMappingService.getDataSetMappingsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataSetMapping dataSetMapping : list) {
					JSONObject rowJSON = dataSetMapping.toJsonObject();
					rowJSON.put("id", dataSetMapping.getId());
					rowJSON.put("dataSetMappingId", dataSetMapping.getId());
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

		return new ModelAndView("/dataset/dataSetMapping/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataSetMappings")
	public byte[] saveDataSetMappings(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String mappings = RequestUtils.getString(request, "mappings");
		String parentId = RequestUtils.getString(request, "parentId");
		try {

			if (StringUtils.isNotEmpty(mappings)) {
				List<DataSetMapping> dataSetMappings = JSON.parseArray(mappings, DataSetMapping.class);
				for (DataSetMapping mapping : dataSetMappings) {
					mapping.setParentId(parentId);

					this.dataSetMappingService.save(mapping);
				}
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/deleteByParentIdAndDsId")
	public byte[] deleteByParentIdAndDsId(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String dsmId = RequestUtils.getString(request, "dsmId");
		String parentId = RequestUtils.getString(request, "parentId");
		try {
			DataSetMappingQuery query = new DataSetMappingQuery();
			query.setDsmId(dsmId);
			query.setParentId_(parentId);

			List<DataSetMapping> list = dataSetMappingService.list(query);
			if (CollectionUtils.isNotEmpty(list)) {
				dataSetMappingService.deleteByIdWithItems(list.get(0).getId());
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/mapping")
	public ModelAndView mapping(HttpServletRequest request, ModelMap modelMap) {
		String dsmId = RequestUtils.getString(request, "dsmId");
		String parentId = RequestUtils.getString(request, "parentId");

		DataSetMappingQuery query = new DataSetMappingQuery();
		query.setDsmId(dsmId);
		query.setParentId_(parentId);

		List<DataSetMapping> list = dataSetMappingService.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			modelMap.put("mapping", list.get(0));
		}
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}
		String x_view = ViewProperties.getString("dataSetMapping.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}
		return new ModelAndView("/dataset/dataSetMapping/view");
	}

}
