package com.glaf.base.modules.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;

@Controller("/form/treedot")
@RequestMapping("/form/treedot")
public class IsdpTreedotController {

	protected final static Log logger = LogFactory.getLog(IsdpTreedotController.class);

	protected IDatabaseService databaseService;

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping
	public ModelAndView tree(HttpServletRequest request, ModelMap modelMap) {
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		String systemName = Environment.getCurrentSystemName();
		Long databaseId = RequestUtils.getLong(request, "databaseId");
		String searchWord = request.getParameter("searchWord");
		if (StringUtils.isNotEmpty(searchWord)) {
			request.setAttribute("searchWord_enc", RequestUtils.encodeString(searchWord));
		}
		try {

			List<Database> databases = config.getDatabases(loginContext);

			if (databases != null && !databases.isEmpty()) {
				request.setAttribute("databases", databases);
			}

			if (databaseId != null && databaseId > 0) {
				Database database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					request.setAttribute("database", database);
					Environment.setCurrentSystemName(database.getName());
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(systemName);
		}
		return new ModelAndView("/form/treedot/tree", modelMap);
	}

	@RequestMapping("/treeJson")
	@ResponseBody
	public byte[] treeJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		String systemName = Environment.getCurrentSystemName();
		
		Long databaseId = RequestUtils.getLong(request, "databaseId");
		String sql = " select index_id as id, parent_id as parentId, index_name as name, index_name as text, listno as sort from cell_treedot ";
		String searchWord_enc = request.getParameter("searchWord_enc");
		if (StringUtils.isNotEmpty(searchWord_enc)) {
			// paramMap.put("searchWordLike", "'%" + searchWord + "%'");
			searchWord_enc = RequestUtils.decodeString(searchWord_enc);
			searchWord_enc = StringTools.replace(searchWord_enc, "'", "");
			sql += " where index_name like '%" + searchWord_enc + "%' ";
		}
		JSONArray array = new JSONArray();
		if (DBUtils.isAllowedSql(sql) && DBUtils.isLegalQuerySql(sql)) {
			logger.debug(sql);
			QueryHelper helper = new QueryHelper();
			try {
				if (databaseId != null && databaseId > 0) {
					Database database = databaseService.getDatabaseById(databaseId);
					if (database != null) {
						Environment.setCurrentSystemName(database.getName());
					}
				}
				List<Map<String, Object>> resultList = helper.getResultList(Environment.getCurrentSystemName(), sql,
						paramMap);
				if (resultList != null && !resultList.isEmpty()) {
					List<TreeComponent> treeList = new ArrayList<TreeComponent>();
					Map<String, TreeComponent> treeMap = new HashMap<String, TreeComponent>();
					for (Map<String, Object> dataMap : resultList) {
						TreeComponent tree = new TreeComponent();
						tree.setId(ParamUtils.getString(dataMap, "id"));
						tree.setTitle(ParamUtils.getString(dataMap, "name")+"["+ParamUtils.getString(dataMap, "id")+"]");
						String parentId = ParamUtils.getString(dataMap, "parentId");
						if (parentId != null && parentId.trim().length() > 0) {
							TreeComponent parent = treeMap.get(parentId);
							tree.setParent(parent);
							tree.setParentId(parentId);
						}
						treeList.add(tree);
						treeMap.put(tree.getId(), tree);
					}

					List<TreeComponent> trees = new ArrayList<TreeComponent>();
					for (TreeComponent tree : treeList) {
						String parentId = tree.getParentId();
						if (parentId != null && parentId.trim().length() > 0) {
							TreeComponent parent = treeMap.get(parentId);
							tree.setParent(parent);
						}
						trees.add(tree);
					}

					TreeComponent task = new TreeComponent();
					task.setId("-10000");
					task.setParentId("-1");
					task.setTitle("任务列表");
					trees.add(task);

					logger.debug("trees->" + treeList.size());
					TreeHelper helper2 = new TreeHelper();
					array = helper2.getJSONArray(trees, 0);
					// logger.debug(array.toJSONString());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			} finally {
				Environment.setCurrentSystemName(systemName);
			}
		}
		return array.toJSONString().getBytes("UTF-8");
	}

}
