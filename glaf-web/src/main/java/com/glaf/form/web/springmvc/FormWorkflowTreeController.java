package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.domain.FormWorkflowTree;
import com.glaf.form.core.query.FormWorkflowPlanQuery;
import com.glaf.form.core.query.FormWorkflowTreeQuery;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.form.core.service.FormWorkflowTreeService;
import com.glaf.form.core.util.FormWorkflowTreeDomainFactory;
import com.glaf.workflow.core.service.TaskExtService;
import com.glaf.workflow.core.service.WorkFlowDefinedService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/formWorkflowTree")
@RequestMapping("/form/formWorkflowTree")
public class FormWorkflowTreeController {
	protected static final Log logger = LogFactory.getLog(FormWorkflowTreeController.class);

	protected FormWorkflowTreeService formWorkflowTreeService;

	@Autowired
	protected TaskExtService taskExtService;

	@Autowired
	protected FormWorkflowPlanService formWorkflowPlanService;

	@Autowired
	protected WorkFlowDefinedService workFlowDefinedService;

	public FormWorkflowTreeController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formWorkflowTreeService")
	public void setFormWorkflowTreeService(FormWorkflowTreeService formWorkflowTreeService) {
		this.formWorkflowTreeService = formWorkflowTreeService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormWorkflowTree formWorkflowTree = new FormWorkflowTree();
		Tools.populate(formWorkflowTree, params);

		formWorkflowTree.setDefId(request.getParameter("defId"));
		formWorkflowTree.setP_defId(request.getParameter("p_defId"));
		formWorkflowTree.setP_processdefId(request.getParameter("p_processdefId"));
		formWorkflowTree.setProcessdefId(request.getParameter("processdefId"));

		// formWorkflowTree.setCreateBy(actorId);

		formWorkflowTreeService.save(formWorkflowTree);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormWorkflowTree")
	public byte[] saveFormWorkflowTree(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowTree formWorkflowTree = new FormWorkflowTree();
		try {
			Tools.populate(formWorkflowTree, params);
			formWorkflowTree.setDefId(request.getParameter("defId"));
			formWorkflowTree.setP_defId(request.getParameter("p_defId"));
			formWorkflowTree.setP_processdefId(request.getParameter("p_processdefId"));
			formWorkflowTree.setProcessdefId(request.getParameter("processdefId"));
			// formWorkflowTree.setCreateBy(actorId);
			this.formWorkflowTreeService.save(formWorkflowTree);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormWorkflowTree saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormWorkflowTree formWorkflowTree = new FormWorkflowTree();
		try {
			Tools.populate(formWorkflowTree, model);
			formWorkflowTree.setDefId(ParamUtils.getString(model, "defId"));
			formWorkflowTree.setP_defId(ParamUtils.getString(model, "p_defId"));
			formWorkflowTree.setP_processdefId(ParamUtils.getString(model, "p_processdefId"));
			formWorkflowTree.setProcessdefId(ParamUtils.getString(model, "processdefId"));
			// formWorkflowTree.setCreateBy(actorId);
			this.formWorkflowTreeService.save(formWorkflowTree);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formWorkflowTree;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormWorkflowTree formWorkflowTree = formWorkflowTreeService
				.getFormWorkflowTree(RequestUtils.getLong(request, "id"));

		Tools.populate(formWorkflowTree, params);

		formWorkflowTree.setDefId(request.getParameter("defId"));
		formWorkflowTree.setP_defId(request.getParameter("p_defId"));
		formWorkflowTree.setP_processdefId(request.getParameter("p_processdefId"));
		formWorkflowTree.setProcessdefId(request.getParameter("processdefId"));

		formWorkflowTreeService.save(formWorkflowTree);

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
					FormWorkflowTree formWorkflowTree = formWorkflowTreeService.getFormWorkflowTree(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formWorkflowTree != null
							&& (/*
								 * StringUtils.equals(formWorkflowTree.
								 * getCreateBy(), loginContext.getActorId()) ||
								 */ loginContext.isSystemAdministrator())) {
						// formWorkflowTree.setDeleteFlag(1);
						formWorkflowTreeService.save(formWorkflowTree);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormWorkflowTree formWorkflowTree = formWorkflowTreeService.getFormWorkflowTree(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formWorkflowTree != null
					&& ( /*
							 * StringUtils.equals(formWorkflowTree.getCreateBy()
							 * , loginContext.getActorId()) ||
							 */ loginContext.isSystemAdministrator())) {
				// formWorkflowTree.setDeleteFlag(1);
				formWorkflowTreeService.save(formWorkflowTree);
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
		FormWorkflowTree formWorkflowTree = formWorkflowTreeService
				.getFormWorkflowTree(RequestUtils.getLong(request, "id"));
		if (formWorkflowTree != null) {
			request.setAttribute("formWorkflowTree", formWorkflowTree);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formWorkflowTree.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/formWorkflowTree/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowTree formWorkflowTree = formWorkflowTreeService
				.getFormWorkflowTree(RequestUtils.getLong(request, "id"));
		request.setAttribute("formWorkflowTree", formWorkflowTree);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formWorkflowTree.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/formWorkflowTree/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formWorkflowTree.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/formWorkflowTree/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormWorkflowTreeQuery query = new FormWorkflowTreeQuery();
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
		int total = formWorkflowTreeService.getFormWorkflowTreeCountByQueryCriteria(query);
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
			List<FormWorkflowTree> list = formWorkflowTreeService.getFormWorkflowTreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormWorkflowTree formWorkflowTree : list) {
					JSONObject rowJSON = formWorkflowTree.toJsonObject();
					rowJSON.put("id", formWorkflowTree.getId());
					rowJSON.put("rowId", formWorkflowTree.getId());
					rowJSON.put("formWorkflowTreeId", formWorkflowTree.getId());
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
		FormWorkflowTreeQuery query = new FormWorkflowTreeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormWorkflowTreeDomainFactory.processDataRequest(dataRequest);

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
		int total = formWorkflowTreeService.getFormWorkflowTreeCountByQueryCriteria(query);
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
			List<FormWorkflowTree> list = formWorkflowTreeService.getFormWorkflowTreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormWorkflowTree formWorkflowTree : list) {
					JSONObject rowJSON = formWorkflowTree.toJsonObject();
					rowJSON.put("id", formWorkflowTree.getId());
					rowJSON.put("formWorkflowTreeId", formWorkflowTree.getId());
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

		return new ModelAndView("/form/formWorkflowTree/list", modelMap);
	}

	/**
	 * 获取流程树信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getWorkFlowPlanWithTreeByDefId", produces = "text/html;charset=utf-8")
	public byte[] getWorkFlowPlanWithTreeByDefId(HttpServletRequest request,
			@RequestParam Map<String, Object> parameter) throws IOException {

		String defId = MapUtils.getString(parameter, "defId")//
				, dsId = MapUtils.getString(parameter, "dsId");

		if (StringUtils.isBlank(defId)) {
			return null;
		}

		/**
		 * 获取流程配置数据集(名称)
		 */
		Map<String, JSONObject> nameMap = new HashMap<>();
		if (StringUtils.isNotBlank(dsId)) {
			DataSetBuilder builder = new DataSetBuilder();
			parameter.put("om", true);
			JSONArray dataSetRows = builder.getJsonArray(dsId, parameter);
			if (CollectionUtils.isNotEmpty(dataSetRows)) {
				dataSetRows.forEach(e -> {
					JSONObject json = new JSONObject(new CaseInsensitiveMap((JSONObject) e));
					String defid = MapUtils.getString(json, "defid");
					if (StringUtils.isBlank(defid)) {
						return;
					}
					nameMap.put(defid, json);
				});
			}
		}

		/**
		 * 父流程配置信息
		 */
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		query.setDefId(defId);
		query.setVersionGreaterThanOrEqual(0);
		List<FormWorkflowPlan> list = formWorkflowPlanService.list(query);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		FormWorkflowPlan plan = list.get(0);

		String processDefId = plan.getProcessDefId();

		ProcessDefinition definition = workFlowDefinedService//
				.getProcessDefinitionByKey(plan.getKey());

		List<String> flows = this.taskExtService.getSubProcessDefBySuperProcessDef(//
				definition.getId());

		if (CollectionUtils.isEmpty(flows)) {
			return null;
		}
		Set<String> single = new HashSet<>(), selected = new HashSet<>();

		/**
		 * 获取已经关联的树结构(还原)
		 */
		FormWorkflowTreeQuery treeQuery = new FormWorkflowTreeQuery();
		treeQuery.setP_defId(defId);
		List<FormWorkflowTree> trees = this.formWorkflowTreeService.list(treeQuery);
		if (CollectionUtils.isNotEmpty(trees)) {
			trees.forEach(e -> {
				selected.add(e.getDefId());
			});
		}

		/**
		 * 子流程配置信息
		 */
		query.setDefId(null);
		query.setKeys(flows);
		list = this.formWorkflowPlanService.list(query);

		Map<String, List<JSONObject>> childenMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(e -> {
				if (single.contains(e.getDefId())) {
					return;
				}

				if (!childenMap.containsKey(e.getKey())) {
					childenMap.put(e.getKey(), new ArrayList<>());
				}
				e.setBytes(null);

				JSONObject e0 = e.toJsonObject();
				if (selected.contains(e.getDefId())) {
					e0.put("checked", true);
				}
				String name = e.getPageName();

				if (nameMap.containsKey(e.getDefId())) {
					name = nameMap.get(e.getDefId()).getString("name");
				}

				e0.put("name", name);

				childenMap.get(e.getKey()).add(e0);
				single.add(e.getDefId());
			});
		}

		JSONObject e;
		List<JSONObject> rows = new ArrayList<>();
		for (String key : flows) {

			if (StringUtils.isBlank(key)) {
				continue;
			}

			definition = workFlowDefinedService.getProcessDefinitionByKey(key);
			if (definition == null) {
				continue;
			}

			e = new JSONObject();

			String name = definition.getName();

			e.put("name", name);
			e.put("id", key);
			e.put("nocheck", true);
			e.put("open", true);
			e.put("children", childenMap.get(key));

			rows.add(e);
		}

		JSONObject result = new JSONObject();

		result.put("data", rows);

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取流程树信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAll", produces = "text/html;charset=utf-8")
	public byte[] saveAll(HttpServletRequest request, @RequestParam Map<String, Object> parameter) throws IOException {

		String p_defId = MapUtils.getString(parameter, "p_defId");

		if (StringUtils.isBlank(p_defId)) {
			return ResponseUtils.responseJsonResult(false);
		}

		formWorkflowTreeService.deleteByPdefId(p_defId);

		String treesStr = MapUtils.getString(parameter, "treesStr");

		List<FormWorkflowTree> trees = JSON.parseArray(treesStr, FormWorkflowTree.class);

		if (CollectionUtils.isNotEmpty(trees)) {
			try {
				for (FormWorkflowTree tree : trees) {
					tree.setId(null);
					this.formWorkflowTreeService.save(tree);
				}
			} catch (Exception ex) {
				return ResponseUtils.responseJsonResult(false);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}
}
