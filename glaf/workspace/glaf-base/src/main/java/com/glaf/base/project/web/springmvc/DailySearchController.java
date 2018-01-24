package com.glaf.base.project.web.springmvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.core.domain.Database;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.base.project.domain.Project;
import com.glaf.base.project.query.ProjectQuery;
import com.glaf.base.project.service.ProjectService;


/**
 * 各标段基本情况
 * 
 * @author Rocky
 *
 */
@Controller("/datamgr/dailySearch")
@RequestMapping("/datamgr/dailySearch")
public class DailySearchController {

	private ProjectService projectService;

	private SqlDefinitionService sqlDefinitionService;

	private IDatabaseService databaseService;

	@RequestMapping(params = "method=charts")
	public ModelAndView charts(HttpServletRequest request) throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String type = RequestUtils.getString(request, "type", "project");

		JSONObject results = new JSONObject();
		// List<SqlDefinition> sqlDefinitionList = sqlDefinitionService
		// .getSqlDefinitions(loginContext.getActorId(), "select");

		BaseDataManager bdm = BaseDataManager.getInstance();
		List<Dictory> dictorys = bdm.getDictoryList("daily_sql_set");

		List<SqlDefinition> sql_list = sqlDefinitionService.getSqlDefinitionsByUserId(loginContext.getActorId());
		Map<String, SqlDefinition> sqlMap = new HashMap<String, SqlDefinition>();
		if (sql_list != null && !sql_list.isEmpty()) {
			for (SqlDefinition sql : sql_list) {
				sqlMap.put(sql.getCode(), sql);
			}
		}
		JSONArray jArray = new JSONArray();
		JSONObject jobject = null;
		for (Dictory model : dictorys) {
			if (sqlMap.get(model.getCode()) != null) {
				jobject = new JSONObject();
				jobject.put("value", model.getCode());
				jobject.put("text", model.getName());
				jArray.add(jobject);
			}
		}
		results.put("series", jArray);

		ModelAndView modelAndView = null;
		if ("project".equalsIgnoreCase(type)) {
			List<Project> list = new ArrayList<Project>();
			if (loginContext.isSystemAdministrator()) {
				// 全部
				ProjectQuery q = new ProjectQuery();
				q.locked(0);
				list = projectService.list(q);
			} else {
				list = projectService.getProjects(loginContext.getActorId());
			}
			JSONArray project = new JSONArray();
			for (Project model : list) {
				jobject = new JSONObject();
				jobject.put("value", model.getId());
				jobject.put("text", model.getName());
				project.add(jobject);
			}
			results.put("project", project);

			modelAndView = new ModelAndView("/datamgr/daily_search/daily_search_charts2");
		} else {

			jArray = new JSONArray();
			List<Database> dbList = new ArrayList<Database>();
			if (loginContext.isSystemAdministrator()) {
				DatabaseQuery query = new DatabaseQuery();
				query.setActive("1");
				dbList = databaseService.list(query);
			} else {
				dbList = databaseService.getDatabases(loginContext.getActorId());
			}

			for (Database db : dbList) {
				jobject = new JSONObject();
				jobject.put("value", db.getId());
				jobject.put("text", db.getTitle());
				jArray.add(jobject);
			}
			results.put("databaseArray", jArray);

			modelAndView = new ModelAndView("/datamgr/daily_search/daily_search_charts");
		}

		request.setAttribute("results", results);

		return modelAndView;
	}

	@Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@RequestMapping(params = "method=list")
	public ModelAndView show(HttpServletRequest request) throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String type = RequestUtils.getString(request, "type", "project");

		List<Project> list = new ArrayList<Project>();
		if (loginContext.isSystemAdministrator()) {
			// 全部
			ProjectQuery q = new ProjectQuery();
			q.locked(0);
			list = projectService.list(q);
		} else {
			list = projectService.getProjects(loginContext.getActorId());
		}

		JSONArray results = new JSONArray();
		JSONObject jobject = new JSONObject();

		if ("project".equalsIgnoreCase(type)) {
			jobject.put("value", "all");
			jobject.put("text", "查询所有项目");
			results.add(jobject);
		}

		for (Project model : list) {
			jobject = new JSONObject();
			jobject.put("value", model.getId());
			jobject.put("text", model.getName());
			results.add(jobject);
		}
		request.setAttribute("results", results);

		// List<SqlDefinition> sqlList = sqlDefinitionService.getSqlDefinitions(
		// loginContext.getActorId(), "select");
		BaseDataManager bdm = BaseDataManager.getInstance();
		List<Dictory> dictorys = bdm.getDictoryList("daily_sql_set");

		List<SqlDefinition> sql_list = sqlDefinitionService.getSqlDefinitionsByUserId(loginContext.getActorId());
		Map<String, SqlDefinition> sqlMap = new HashMap<String, SqlDefinition>();
		if (sql_list != null && !sql_list.isEmpty()) {
			for (SqlDefinition sql : sql_list) {
				sqlMap.put(sql.getCode(), sql);
			}
		}

		List<Dictory> dictoryList = new ArrayList<Dictory>();
		for (Dictory model : dictorys) {
			if (sqlMap.get(model.getCode()) != null) {
				dictoryList.add(model);
			}
		}

		request.setAttribute("sqlList", dictoryList);

		if ("project".equalsIgnoreCase(type)) {
			return new ModelAndView("/datamgr/daily_search/daily_search_list");
		} else {
			int startRunDay = RequestUtils.getInt(request, "startRunDay");
			int endRunDay = RequestUtils.getInt(request, "endRunDay");

			int betweenDays = 0;
			if (startRunDay != 0 && endRunDay != 0) {
				betweenDays = DateUtils.getDaysBetween(DateUtils.toDate(String.valueOf(startRunDay)),
						DateUtils.toDate(String.valueOf(endRunDay)));
			}
			request.setAttribute("betweenDays", betweenDays);
			request.setAttribute("projectId", RequestUtils.getLong(request, "projectId"));
			request.setAttribute("startRunDay", startRunDay);
			request.setAttribute("endRunDay", endRunDay);

			return new ModelAndView("/datamgr/daily_search/daily_search_list2");
		}
	}

}
