package com.glaf.dep.base.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.dep.base.domain.DepBaseWdataSet;
import com.glaf.dep.base.factory.DataProcessFactory;
import com.glaf.dep.base.query.DepBaseWdataSetQuery;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.dep.base.util.DepBaseWdataSetDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/dep/base/depBaseWdataSet")
@RequestMapping("/dep/base/depBaseWdataSet")
public class DepBaseWdataSetController {
	protected static final Log logger = LogFactory.getLog(DepBaseWdataSetController.class);

	protected DepBaseWdataSetService depBaseWdataSetService;

	public DepBaseWdataSetController() {

	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.service.depBaseWdataSetService")
	public void setDepBaseWdataSetService(DepBaseWdataSetService depBaseWdataSetService) {
		this.depBaseWdataSetService = depBaseWdataSetService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseWdataSet depBaseWdataSet = new DepBaseWdataSet();
		Tools.populate(depBaseWdataSet, params);

		depBaseWdataSet.setDataSetCode(request.getParameter("dataSetCode"));
		depBaseWdataSet.setDataSetName(request.getParameter("dataSetName"));
		depBaseWdataSet.setDataSetDesc(request.getParameter("dataSetDesc"));
		depBaseWdataSet.setRuleJson(request.getParameter("ruleJson"));
		depBaseWdataSet.setTableName(request.getParameter("tableName"));
		depBaseWdataSet.setDataTableName(request.getParameter("dataTableName"));
		depBaseWdataSet.setWtype(request.getParameter("wtype"));
		depBaseWdataSet.setVer(RequestUtils.getInt(request, "ver"));
		depBaseWdataSet.setCreator(request.getParameter("creator"));
		depBaseWdataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		depBaseWdataSet.setModifier(request.getParameter("modifier"));
		depBaseWdataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		depBaseWdataSet.setDelFlag(request.getParameter("delFlag"));

		// depBaseWdataSet.setModifier(actorId);

		depBaseWdataSetService.save(depBaseWdataSet);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepBaseWdataSet")
	public byte[] saveDepBaseWdataSet(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSet depBaseWdataSet = new DepBaseWdataSet();
		try {
			Tools.populate(depBaseWdataSet, params);
			String ruleJsonString = request.getParameter("ruleJson");
			depBaseWdataSet.setDataSetCode(request.getParameter("dataSetCode"));
			depBaseWdataSet.setDataSetName(request.getParameter("dataSetName"));
			depBaseWdataSet.setDataSetDesc(request.getParameter("dataSetDesc"));
			depBaseWdataSet.setTableName(request.getParameter("tableName"));
			depBaseWdataSet.setDataTableName(request.getParameter("dataTableName"));
			depBaseWdataSet.setWtype(request.getParameter("wtype"));
			depBaseWdataSet.setVer(RequestUtils.getInt(request, "ver"));
			depBaseWdataSet.setCreator(request.getParameter("creator"));
			depBaseWdataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
			depBaseWdataSet.setModifier(request.getParameter("modifier"));
			depBaseWdataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
			depBaseWdataSet.setDelFlag(request.getParameter("delFlag"));
			depBaseWdataSet.setNodeId(request.getParameter("nodeId"));
			if (depBaseWdataSet.getId() == null) {
				depBaseWdataSet.setCreateDatetime(new Date());
				depBaseWdataSet.setCreator(actorId);
			} else {
				depBaseWdataSet.setModifier(actorId);
				depBaseWdataSet.setModifyDatetime(new Date());
			}

			if (StringUtils.isNotBlank(ruleJsonString)) {
				JSONObject ruleJson = JSON.parseObject(ruleJsonString);

				// this.depBaseWdataSetService.getInsertSql(id, null);

				depBaseWdataSet.setRuleJson(ruleJson.toString());
			}

			if (depBaseWdataSet.getId() != null && //
					StringUtils.isNotBlank(MapUtils.getString(params, "saveAs"))) { // 另存为
				depBaseWdataSet = this.depBaseWdataSetService.getDepBaseWdataSet(depBaseWdataSet.getId());

				Tools.populate(depBaseWdataSet, params);

				depBaseWdataSet.setDataSetName(depBaseWdataSet.getDataSetName() + " 另存");
				depBaseWdataSet.setDataSetDesc(depBaseWdataSet.getDataSetDesc() + " 另存");
				depBaseWdataSet.setId(null);
			}

			this.depBaseWdataSetService.save(depBaseWdataSet);

			return depBaseWdataSet.toJsonObject().toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/create")
	public byte[] create(HttpServletRequest request) {

		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/test")
	public byte[] test(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map param;
		List params = new ArrayList();
		for (int i = 0; i < 10000; i++) {
			param = new HashMap();
			param.put("1460426058204", "java1234" + i);
			param.put("1460426058205", "1460426058205" + i);
			param.put("1460426058252", "22222");
			params.add(param);
		}
		// depBaseWdataSetService.execBatch(101L, params);
		DataProcessFactory.getInstance().execBatch(101L, params);// 2017-01-03
																	// by
																	// huangcw
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DepBaseWdataSet saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DepBaseWdataSet depBaseWdataSet = new DepBaseWdataSet();
		try {
			Tools.populate(depBaseWdataSet, model);
			depBaseWdataSet.setDataSetCode(ParamUtils.getString(model, "dataSetCode"));
			depBaseWdataSet.setDataSetName(ParamUtils.getString(model, "dataSetName"));
			depBaseWdataSet.setDataSetDesc(ParamUtils.getString(model, "dataSetDesc"));
			depBaseWdataSet.setRuleJson(ParamUtils.getString(model, "ruleJson"));
			depBaseWdataSet.setTableName(ParamUtils.getString(model, "tableName"));
			depBaseWdataSet.setDataTableName(ParamUtils.getString(model, "dataTableName"));
			depBaseWdataSet.setWtype(ParamUtils.getString(model, "wtype"));
			depBaseWdataSet.setVer(ParamUtils.getInt(model, "ver"));
			depBaseWdataSet.setCreator(ParamUtils.getString(model, "creator"));
			depBaseWdataSet.setCreateDatetime(ParamUtils.getDate(model, "createDatetime"));
			depBaseWdataSet.setModifier(ParamUtils.getString(model, "modifier"));
			depBaseWdataSet.setModifyDatetime(ParamUtils.getDate(model, "modifyDatetime"));
			depBaseWdataSet.setDelFlag(ParamUtils.getString(model, "delFlag"));
			depBaseWdataSet.setModifier(actorId);
			this.depBaseWdataSetService.save(depBaseWdataSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return depBaseWdataSet;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DepBaseWdataSet depBaseWdataSet = depBaseWdataSetService
				.getDepBaseWdataSet(RequestUtils.getLong(request, "id"));

		Tools.populate(depBaseWdataSet, params);

		depBaseWdataSet.setDataSetCode(request.getParameter("dataSetCode"));
		depBaseWdataSet.setDataSetName(request.getParameter("dataSetName"));
		depBaseWdataSet.setDataSetDesc(request.getParameter("dataSetDesc"));
		depBaseWdataSet.setRuleJson(request.getParameter("ruleJson"));
		depBaseWdataSet.setTableName(request.getParameter("tableName"));
		depBaseWdataSet.setDataTableName(request.getParameter("dataTableName"));
		depBaseWdataSet.setWtype(request.getParameter("wtype"));
		depBaseWdataSet.setVer(RequestUtils.getInt(request, "ver"));
		depBaseWdataSet.setCreator(request.getParameter("creator"));
		depBaseWdataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
		depBaseWdataSet.setModifier(request.getParameter("modifier"));
		depBaseWdataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));
		depBaseWdataSet.setDelFlag(request.getParameter("delFlag"));

		depBaseWdataSetService.save(depBaseWdataSet);

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
					DepBaseWdataSet depBaseWdataSet = depBaseWdataSetService.getDepBaseWdataSet(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (depBaseWdataSet != null
							&& (StringUtils.equals(depBaseWdataSet.getModifier(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// depBaseWdataSet.setDeleteFlag(1);
						depBaseWdataSetService.save(depBaseWdataSet);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepBaseWdataSet depBaseWdataSet = depBaseWdataSetService.getDepBaseWdataSet(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (depBaseWdataSet != null && (StringUtils.equals(depBaseWdataSet.getModifier(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// depBaseWdataSet.setDeleteFlag(1);
				depBaseWdataSetService.deleteById(id);
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
		DepBaseWdataSet depBaseWdataSet = depBaseWdataSetService
				.getDepBaseWdataSet(RequestUtils.getLong(request, "id"));
		if (depBaseWdataSet != null) {
			String id = depBaseWdataSet.getNodeId();
			request.setAttribute("depBaseWdataSet", depBaseWdataSet);
			if (StringUtils.isNotBlank(depBaseWdataSet.getRuleJson())) {
				JSONObject json, ruleJson = JSON.parseObject(depBaseWdataSet.getRuleJson());
				JSONArray columns = ruleJson.getJSONArray("columns");
				if (CollectionUtils.isNotEmpty(columns)) {
					JSONArray selected = new JSONArray(), others = new JSONArray();
					for (int i = 0, size = columns.size(); i < size; i++) {
						json = columns.getJSONObject(i);
						if (json.getBooleanValue("selected")) {
							selected.add(json);
						} else {
							others.add(json);
						}
					}
					request.setAttribute("selected", selected);
					request.setAttribute("others", others);
				}
				request.setAttribute("ruleJson", ruleJson);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("depBaseWdataSet.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/dep/base/depBaseWdataSet/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSet depBaseWdataSet = depBaseWdataSetService
				.getDepBaseWdataSet(RequestUtils.getLong(request, "id"));
		request.setAttribute("depBaseWdataSet", depBaseWdataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("depBaseWdataSet.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/dep/base/depBaseWdataSet/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("depBaseWdataSet.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/dep/base/depBaseWdataSet/query", modelMap);
	}

	@RequestMapping("/getById")
	@ResponseBody
	public byte[] getById(HttpServletRequest request) throws IOException {
		DepBaseWdataSet wdsBaseWdataSet = this.depBaseWdataSetService
				.getDepBaseWdataSet(RequestUtils.getLong(request, "id"));
		if (wdsBaseWdataSet != null) {
			return wdsBaseWdataSet.toJsonObject().toJSONString().getBytes("UTF-8");
		}
		return null;
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepBaseWdataSetQuery query = new DepBaseWdataSetQuery();
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
		int total = depBaseWdataSetService.getDepBaseWdataSetCountByQueryCriteria(query);
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
			List<DepBaseWdataSet> list = depBaseWdataSetService.getDepBaseWdataSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseWdataSet depBaseWdataSet : list) {
					JSONObject rowJSON = depBaseWdataSet.toJsonObject();
					rowJSON.put("id", depBaseWdataSet.getId());
					rowJSON.put("rowId", depBaseWdataSet.getId());
					rowJSON.put("depBaseWdataSetId", depBaseWdataSet.getId());
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
		DepBaseWdataSetQuery query = new DepBaseWdataSetQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DepBaseWdataSetDomainFactory.processDataRequest(dataRequest);

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
		int total = depBaseWdataSetService.getDepBaseWdataSetCountByQueryCriteria(query);
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
			List<DepBaseWdataSet> list = depBaseWdataSetService.getDepBaseWdataSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepBaseWdataSet depBaseWdataSet : list) {
					JSONObject rowJSON = depBaseWdataSet.toJsonObject();
					rowJSON.put("id", depBaseWdataSet.getId());
					rowJSON.put("depBaseWdataSetId", depBaseWdataSet.getId());
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

		return new ModelAndView("/dep/base/depBaseWdataSet/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/getPrimaryKeys")
	public byte[] getPrimaryKeys(HttpServletRequest request) throws IOException {

		String systemName = RequestUtils.getString(request, "systemName");
		String tableName = RequestUtils.getString(request, "tableName");

		List<String> list = DBUtils.getPrimaryKeys(systemName, tableName);
		JSONObject result = new JSONObject();
		result.put("keys", list);
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/refreshWDataSets")
	public byte[] refreshWDataSets(HttpServletRequest request) throws IOException {
		try {
			this.depBaseWdataSetService.refreshWDataSets();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}

}
