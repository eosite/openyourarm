package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.project.domain.Project;
import com.glaf.base.project.query.ProjectQuery;
import com.glaf.base.project.service.ProjectService;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.RequestUtils;

/**
 * 通用页面数据获取
 * 
 * @author Rocky 2015-04-04
 * 
 */
@Controller
@Path("/rs/isdp/global")
public class GlobalResource {

	private final int START_YEAR = 2013;

	protected ProjectService projectService;

	protected IDatabaseService databaseService;

	protected DictoryService dictoryService;

	protected ITreeModelService treeModelService;

	@GET
	@POST
	@Path("/treeDataJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] treeDataJson(@Context HttpServletRequest request, ModelMap modelMap) throws IOException {

		String currentSystemName = Environment.getCurrentSystemName();
		JSONArray rowsJSON = new JSONArray();

		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			String nodeCode = RequestUtils.getString(request, "nodeCode", "");

			if (nodeCode == null || StringUtils.isEmpty(nodeCode)) {
				nodeCode = modelMap.get("nodeCode").toString();
			}
			if (systemName == null || StringUtils.isEmpty(systemName)) {
				systemName = modelMap.get("systemName").toString();
			}
			Environment.setCurrentSystemName(systemName);

			if (nodeCode != null && StringUtils.isNotEmpty(nodeCode)) {
				TreeModel treeModel = treeModelService.getTreeModelByCode(nodeCode);

				if (treeModel != null) {
					List<TreeModel> treeModels = treeModelService.getSubTreeModels(treeModel.getId());
					for (TreeModel model : treeModels) {
						JSONObject rowJSON = model.toJsonObject();
						rowJSON.put("id", model.getId());
						rowsJSON.add(rowJSON);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return rowsJSON.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据基础数据分类Code查询数据
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/dictoryDataJson")
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] dictoryDataJson(@Context HttpServletRequest request) throws IOException {

		String currentSystemName = Environment.getCurrentSystemName();
		JSONArray rowsJSON = new JSONArray();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			String nodeCode = RequestUtils.getString(request, "nodeCode", "");
			Environment.setCurrentSystemName(systemName);

			if (nodeCode != null && StringUtils.isNotEmpty(nodeCode)) {
				List<Dictory> dictoryList = dictoryService.getDictoryList(nodeCode);

				if (dictoryList != null && dictoryList.size() > 0) {
					for (Dictory dictory : dictoryList) {
						JSONObject rowJSON = dictory.toJsonObject();
						rowJSON.put("id", dictory.getId());
						rowsJSON.add(rowJSON);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return rowsJSON.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 查询数据库，返回数组
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/databaseArray")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] databaseArray(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String currentSystemName = Environment.getCurrentSystemName();

		JSONArray results = new JSONArray();
		JSONArray array = new JSONArray();
		array.add(Environment.DEFAULT_SYSTEM_NAME);
		array.add("默认");
		results.add(array);
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);
			List<Database> list = databaseService.getDatabases(loginContext.getActorId());
			for (Database database : list) {
				array = new JSONArray();
				array.add(database.getCode());
				array.add(database.getTitle());
				results.add(array);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return results.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 查询数据库，返回JSON数组
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/databaseJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] databaseJson(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		// String currentSystemName = Environment.getCurrentSystemName();
		JSONArray results = new JSONArray();
		JSONObject jobject = new JSONObject();
		jobject.put("code", Environment.DEFAULT_SYSTEM_NAME);
		jobject.put("text", "默认");
		jobject.put("dataBaseType", DBConnectionFactory.getDatabaseType()/* this.getDataBaseType(Environment.DEFAULT_SYSTEM_NAME)*/);
		results.add(jobject);
		try {
			// 多数据库记录到主控库的，所以不用切换数据库上下文环境
			// String systemName = RequestUtils.getString(request, "systemName",
			// currentSystemName);
			// Environment.setCurrentSystemName(systemName);

			List<Database> list = databaseService.getDatabases(loginContext.getActorId());
			for (Database database : list) {
				jobject = new JSONObject();
				jobject.put("id", database.getId());
				jobject.put("code", database.getCode());
				//jobject.put("dataBaseType", this.getDataBaseType(database.getCode()));
				jobject.put("dataBaseType", database.getType());
				jobject.put("text", database.getTitle());
				jobject.put("host", database.getHost());
				results.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Environment.setCurrentSystemName(currentSystemName);
		}
		return results.toJSONString().getBytes("UTF-8");
	}

	protected String getDataBaseType(String systemName) {
		String type = null;
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			type = DBConnectionFactory.getDatabaseType(conn);
		} finally {
			JdbcUtils.close(conn);
		}
		return type;
	}

	/**
	 * 查询项目
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/projectQuery")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] projectQuery(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		JSONArray results = new JSONArray();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			ProjectQuery query = new ProjectQuery();
			query.setOrderBy(" ID_ ASC");
			List<Project> projectQueryList = projectService.list(query);

			JSONObject jobject = null;

			for (Project model : projectQueryList) {
				jobject = new JSONObject();
				jobject.put("value", model.getId());
				jobject.put("id", model.getId());
				jobject.put("text", model.getTitle());
				jobject.put("name", model.getTitle());
				jobject.put("pId", 0);
				results.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return results.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 查询项目，并构建年月树结构
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/proj_tree_yyyymm")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] projTreeYYYYMM(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String currentSystemName = Environment.getCurrentSystemName();

		JSONArray results = new JSONArray();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);
			long projectId = RequestUtils.getLong(request, "projectId");
			List<Database> list = this.getDatabaseListByProjectId(loginContext, projectId);
			JSONObject jobject = null;
			for (Database database : list) {
				jobject = new JSONObject();
				jobject.put("id", this.getRandom());
				jobject.put("treeData", database.getId());
				jobject.put("name", database.getTitle());
				jobject.put("zTreeType", "p");
				jobject.put("children", this.getYearMonthArray());
				results.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return results.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据当前登录用户及项目Id查询可访问的数据库
	 * 
	 * @param loginContext
	 * @param projectId
	 * @return
	 */
	private List<Database> getDatabaseListByProjectId(LoginContext loginContext, long projectId) {
		List<Long> databaseIds = projectService.getDatabaseIds(projectId);

		List<Database> list = new ArrayList<Database>();
		if (databaseIds != null && databaseIds.size() > 0) {
			DatabaseQuery query = new DatabaseQuery();
			query.setDatabaseIds(databaseIds);
			query.setActorId(loginContext.getActorId());
			list = databaseService.list(query);
		}
		return list;
	}

	/**
	 * 获取年月数组
	 * 
	 * @return
	 */
	private JSONArray getYearMonthArray() {
		JSONArray array = new JSONArray();

		JSONObject jobject = null;
		JSONArray monthArray = null;
		JSONObject monthObject = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		for (int year = START_YEAR; year <= cal.get(Calendar.YEAR); year++) {
			jobject = new JSONObject();
			jobject.put("id", this.getRandom());
			jobject.put("treeData", year);
			jobject.put("name", year);
			jobject.put("zTreeType", "y");
			// 月，一次性加载12个月
			monthArray = new JSONArray();
			for (int month = 1; month <= 12; month++) {
				monthObject = new JSONObject();
				monthObject.put("id", this.getRandom());
				monthObject.put("treeData", month);
				monthObject.put("name", month);
				monthObject.put("zTreeType", "m");
				monthArray.add(monthObject);
			}
			jobject.put("children", monthArray);
			array.add(jobject);
		}

		return array;
	}

	/**
	 * 生成10000~99999之间的随机数做为zTree的id
	 * 
	 * @return
	 */
	private int getRandom() {
		int min = 10000;
		int max = 99999;
		Random random = new Random(100);
		int result = random.nextInt(max) % (max - min + 1) + min;

		return result;
	}

	@Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	@Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

}
