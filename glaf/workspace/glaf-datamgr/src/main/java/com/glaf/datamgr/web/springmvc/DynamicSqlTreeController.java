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
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.tree.helper.TreeUpdateBean;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/dynamicSqlTree")
@RequestMapping("/datamgr/dynamicSqlTree")
public class DynamicSqlTreeController {
	protected static final Log logger = LogFactory.getLog(DynamicSqlTreeController.class);

	protected DynamicSqlTreeService dynamicSqlTreeService;

	public DynamicSqlTreeController() {

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
					DynamicSqlTree dynamicSqlTree = dynamicSqlTreeService.getDynamicSqlTree(Long.valueOf(x));
					if (dynamicSqlTree != null
							&& (StringUtils.equals(dynamicSqlTree.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						dynamicSqlTreeService.deleteById(dynamicSqlTree.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DynamicSqlTree dynamicSqlTree = dynamicSqlTreeService.getDynamicSqlTree(Long.valueOf(id));
			if (dynamicSqlTree != null && (StringUtils.equals(dynamicSqlTree.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				dynamicSqlTreeService.deleteById(dynamicSqlTree.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DynamicSqlTree dynamicSqlTree = dynamicSqlTreeService.getDynamicSqlTree(RequestUtils.getLong(request, "id"));
		if (dynamicSqlTree != null) {
			request.setAttribute("dynamicSqlTree", dynamicSqlTree);
			request.setAttribute("tableName", dynamicSqlTree.getTableName());
		}

		String moduleId = request.getParameter("moduleId");
		String businessKey = request.getParameter("businessKey");

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
		Tools.populate(query, params);
		query.businessKey(businessKey);
		query.moduleId(moduleId);

		List<DynamicSqlTree> dynamicSqlTrees = dynamicSqlTreeService.list(query);
		request.setAttribute("dynamicSqlTrees", dynamicSqlTrees);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dynamicSqlTree.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dynamicSqlTree/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
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
		int total = dynamicSqlTreeService.getDynamicSqlTreeCountByQueryCriteria(query);
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

			List<DynamicSqlTree> list = dynamicSqlTreeService.getDynamicSqlTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DynamicSqlTree dynamicSqlTree : list) {
					JSONObject rowJSON = dynamicSqlTree.toJsonObject();
					rowJSON.put("id", dynamicSqlTree.getId());
					rowJSON.put("rowId", dynamicSqlTree.getId());
					rowJSON.put("dynamicSqlTreeId", dynamicSqlTree.getId());
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

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/dynamicSqlTree/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dynamicSqlTree.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/dynamicSqlTree/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDynamicSqlTree")
	public byte[] saveDynamicSqlTree(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DynamicSqlTree dynamicSqlTree = new DynamicSqlTree();
		try {
			Tools.populate(dynamicSqlTree, params);
			dynamicSqlTree.setParentId(RequestUtils.getLong(request, "parentId"));
			dynamicSqlTree.setTenantId(loginContext.getActorId());
			dynamicSqlTree.setName(request.getParameter("name"));
			dynamicSqlTree.setModuleId(request.getParameter("moduleId"));
			dynamicSqlTree.setBusinessKey(request.getParameter("businessKey"));
			dynamicSqlTree.setColumnName(request.getParameter("columnName"));
			dynamicSqlTree.setColumnType(request.getParameter("columnType"));
			dynamicSqlTree.setTableName(request.getParameter("tableName"));
			dynamicSqlTree.setTableAlias(request.getParameter("tableAlias"));
			dynamicSqlTree.setParamName(request.getParameter("paramName"));
			dynamicSqlTree.setParamTitle(request.getParameter("paramTitle"));
			dynamicSqlTree.setCollectionFlag(request.getParameter("collectionFlag"));
			dynamicSqlTree.setCondition(request.getParameter("condition"));
			dynamicSqlTree.setOperator(request.getParameter("operator"));
			dynamicSqlTree.setRequiredFlag(request.getParameter("requiredFlag"));
			dynamicSqlTree.setSeparator(request.getParameter("separator"));
			dynamicSqlTree.setSql(request.getParameter("sql"));
			dynamicSqlTree.setLocked(RequestUtils.getInt(request, "locked"));
			dynamicSqlTree.setCreateBy(actorId);
			dynamicSqlTree.setUpdateBy(actorId);

			if (StringUtils.isNotEmpty(dynamicSqlTree.getSql())) {
				if (!DBUtils.isAllowedSql(dynamicSqlTree.getSql())) {
					return ResponseUtils.responseJsonResult(false, "SQL条件不合法！");
				}
				if (!DBUtils.isLegalQuerySql(dynamicSqlTree.getSql())) {
					return ResponseUtils.responseJsonResult(false, "SQL条件不合法！");
				}
			}

			this.dynamicSqlTreeService.save(dynamicSqlTree);

			TreeUpdateBean bean = new TreeUpdateBean();
			bean.updateTreeIds("default", "SYS_DYNAMIC_SQL_TREE", null, "ID_", "PARENTID_", "TREEID_", "LEVEL_",
					" and MODULEID_ = '" + dynamicSqlTree.getModuleId() + "' and BUSINESSKEY_ = '"
							+ dynamicSqlTree.getBusinessKey() + "' ");

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 提交增加信息
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/saveSort")
	@ResponseBody
	public byte[] saveSort(HttpServletRequest request) {
		String items = request.getParameter("items");
		if (StringUtils.isNotEmpty(items)) {
			int sort = 0;
			List<TableModel> rows = new ArrayList<TableModel>();
			StringTokenizer token = new StringTokenizer(items, ",");
			while (token.hasMoreTokens()) {
				String item = token.nextToken();
				if (StringUtils.isNotEmpty(item)) {
					sort++;
					TableModel t1 = new TableModel();
					t1.setTableName("SYS_DYNAMIC_SQL_TREE");
					ColumnModel idColumn1 = new ColumnModel();
					idColumn1.setColumnName("ID_");
					idColumn1.setValue(Long.parseLong(item));
					t1.setIdColumn(idColumn1);
					ColumnModel column = new ColumnModel();
					column.setColumnName("SORTNO_");
					column.setValue(sort);
					t1.addColumn(column);
					rows.add(t1);
				}
			}
			try {
				DataServiceFactory.getInstance().updateAllTableData(rows);
				return ResponseUtils.responseResult(true);
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.dynamicSqlTreeService")
	public void setDynamicSqlTreeService(DynamicSqlTreeService dynamicSqlTreeService) {
		this.dynamicSqlTreeService = dynamicSqlTreeService;
	}

	/**
	 * 显示排序页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/showSort")
	public ModelAndView showSort(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long parentId = RequestUtils.getLongParameter(request, "parentId", 0);
		String moduleId = request.getParameter("moduleId");
		String businessKey = request.getParameter("businessKey");

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
		Tools.populate(query, params);
		query.businessKey(businessKey);
		query.moduleId(moduleId);
		query.parentId(parentId);

		List<DynamicSqlTree> dynamicSqlTrees = dynamicSqlTreeService.list(query);
		request.setAttribute("dynamicSqlTrees", dynamicSqlTrees);

		String x_view = ViewProperties.getString("dynamicSqlTree.showSort");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dynamicSqlTree/showSort", modelMap);
	}

	@ResponseBody
	@RequestMapping("/treeJson")
	public byte[] treeJson(HttpServletRequest request) throws IOException {
		String moduleId = request.getParameter("moduleId");
		String businessKey = request.getParameter("businessKey");

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
		Tools.populate(query, params);
		query.businessKey(businessKey);
		query.moduleId(moduleId);

		List<DynamicSqlTree> dynamicSqlTrees = dynamicSqlTreeService.list(query);
		if (dynamicSqlTrees != null && !dynamicSqlTrees.isEmpty()) {
			List<TreeModel> trees = new ArrayList<TreeModel>();
			for (DynamicSqlTree p : dynamicSqlTrees) {
				TreeModel t = new BaseTree();
				t.setId(p.getId());
				t.setParentId(p.getParentId());
				t.setTreeId(p.getTreeId());
				t.setIcon(p.getIcon());
				t.setIconCls(p.getIconCls());
				t.setName(p.getName());
				t.setCode(p.getCode());
				t.setDiscriminator(p.getDiscriminator());
				t.setLocked(p.getLocked());
				t.setCreateBy(p.getCreateBy());
				t.setCreateDate(p.getCreateTime());
				trees.add(t);
			}
			TreeHelper treeHelper = new TreeHelper();
			JSONArray result = treeHelper.getTreeJSONArray(trees);
			logger.debug(result.toJSONString());
			return result.toJSONString().getBytes("UTF-8");
		}

		JSONArray result = new JSONArray();
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		DynamicSqlTree dynamicSqlTree = dynamicSqlTreeService.getDynamicSqlTree(RequestUtils.getLong(request, "id"));

		Tools.populate(dynamicSqlTree, params);

		dynamicSqlTree.setParentId(RequestUtils.getLong(request, "parentId"));
		dynamicSqlTree.setTenantId(request.getParameter("tenantId"));
		dynamicSqlTree.setName(request.getParameter("name"));
		dynamicSqlTree.setModuleId(request.getParameter("moduleId"));
		dynamicSqlTree.setBusinessKey(request.getParameter("businessKey"));
		dynamicSqlTree.setColumnName(request.getParameter("columnName"));
		dynamicSqlTree.setColumnType(request.getParameter("columnType"));
		dynamicSqlTree.setTableName(request.getParameter("tableName"));
		dynamicSqlTree.setTableAlias(request.getParameter("tableAlias"));
		dynamicSqlTree.setParamName(request.getParameter("paramName"));
		dynamicSqlTree.setParamTitle(request.getParameter("paramTitle"));
		dynamicSqlTree.setCollectionFlag(request.getParameter("collectionFlag"));
		dynamicSqlTree.setCondition(request.getParameter("condition"));
		dynamicSqlTree.setOperator(request.getParameter("operator"));
		dynamicSqlTree.setRequiredFlag(request.getParameter("requiredFlag"));
		dynamicSqlTree.setSeparator(request.getParameter("separator"));
		dynamicSqlTree.setSql(request.getParameter("sql"));
		dynamicSqlTree.setLocked(RequestUtils.getInt(request, "locked"));
		dynamicSqlTree.setUpdateBy(actorId);
		dynamicSqlTreeService.save(dynamicSqlTree);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DynamicSqlTree dynamicSqlTree = dynamicSqlTreeService.getDynamicSqlTree(RequestUtils.getLong(request, "id"));
		request.setAttribute("dynamicSqlTree", dynamicSqlTree);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dynamicSqlTree.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/dynamicSqlTree/view");
	}

}
