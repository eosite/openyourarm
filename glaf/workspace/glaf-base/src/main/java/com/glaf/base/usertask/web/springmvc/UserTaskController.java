package com.glaf.base.usertask.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.base.modules.sys.model.ITree;

import com.glaf.base.helper.JacksonTreeHelper;
import com.glaf.base.usertask.bean.UserTaskTotalBean;
import com.glaf.base.usertask.domain.UserTask;
import com.glaf.base.usertask.query.UserTaskQuery;
import com.glaf.base.usertask.service.IUserTaskService;

@Controller("/isdp/task")
@RequestMapping("/isdp/task")
public class UserTaskController {

	private static ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

	protected static final Log logger = LogFactory.getLog(UserTaskController.class);

	protected EntityService entityService;

	protected IDatabaseService databaseService;

	protected IUserTaskService userTaskService;

	@ResponseBody
	@RequestMapping("/executeCountTask")
	public byte[] executeCountTask(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext.isSystemAdministrator()) {
			UserTaskTotalBean bean = new UserTaskTotalBean();
			bean.countTask();
			return ResponseUtils.responseJsonResult(true);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@javax.annotation.Resource(name = "com.glaf.base.usertask.service.userTaskService")
	public void setUserTaskService(IUserTaskService userTaskService) {
		this.userTaskService = userTaskService;
	}

	@ResponseBody
	@RequestMapping("/taskCount")
	public byte[] taskCount(HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		Database database = null;
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("actorId", actorId);
			int total = userTaskService.getWorkflowUserTaskCount(parameter);
			return String.valueOf(total).getBytes();
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return "0".getBytes();
	}

	@RequestMapping("/taskList")
	public ModelAndView taskList(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String actorId = RequestUtils.getActorId(request);
		String taskType = request.getParameter("taskType");
		String orderCondition = request.getParameter("orderCondition");
		String keyword = request.getParameter("keyword");
		if (StringUtils.isEmpty(orderCondition)) {
			orderCondition = RequestUtils.getCookieValue(request, "isdp_taskList_orderCondition");
		}
		String cacheKey = "TASK_" + actorId + "_" + taskType;
		Database database = null;
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				long start = System.currentTimeMillis();
				database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}

				userTaskService.deleteUserTasks(actorId);

				if ("WBS".equals(orderCondition)) {
					userTaskService.reloadUserTasksGroupByWBS(actorId, taskType);
				} else {
					userTaskService.reloadUserTasksGroupByTime(actorId, taskType);
				}

				long times = System.currentTimeMillis() - start;
				logger.debug("创建任务耗时(毫秒):" + times);
			}

			UserTaskQuery query = new UserTaskQuery();
			Tools.populate(query, paramMap);
			query.actorId(actorId);
			query.name("activ");
			query.setIndexNameLike(keyword);
			int total = userTaskService.getUserTaskCountByQueryCriteria(query);
			request.setAttribute("total", total);
			logger.debug(actorId + "任务项是" + total);
			if (total > 0) {
				List<UserTask> rows = userTaskService.list(query);
				request.setAttribute("tasks", rows);
			}

		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		} finally {
			cache.remove(cacheKey);
			Environment.setCurrentSystemName(currentSystemName);
		}

		return new ModelAndView("/isdp/task/taskList");
	}

	@ResponseBody
	@RequestMapping("/json")
	public byte[] taskListJson(HttpServletRequest request) {
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		String actorId = RequestUtils.getActorId(request);
		logger.debug("paramMap:" + paramMap);
		String keyword = request.getParameter("keyword");
		UserTaskQuery query = new UserTaskQuery();
		Tools.populate(query, paramMap);
		query.actorId(actorId);
		query.setIndexNameLike(keyword);
		List<ITree> treeModels = new ArrayList<ITree>();

		Database database = null;
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String currentSystemName = Environment.getCurrentSystemName();
		try {

			database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}

			int total = userTaskService.getUserTaskCountByQueryCriteria(query);

			int pageNo = ParamUtils.getInt(paramMap, "page");
			int limit = ParamUtils.getInt(paramMap, "rows");

			if (pageNo <= 0) {
				pageNo = 1;
			}

			if (limit <= 0) {
				limit = 100;
			}

			if (limit > total) {
				limit = total;
			}

			int begin = (pageNo - 1) * limit;

			if (total > limit) {
				query.parentIdNotEqual(-1);
			}

			query.setOrderBy(" E.parent_id asc ");

			List<UserTask> rows = userTaskService.getUserTasksByQueryCriteria(begin, limit, query);

			for (UserTask row : rows) {
				treeModels.add(row);
			}

			if (total > limit) {
				query.setParentIdNotEqual(null);
				query.parentId(-1L);
				List<UserTask> tops = userTaskService.list(query);
				for (UserTask row : tops) {
					row.setCls("folder");
					treeModels.add(row);
				}
			}

		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		logger.debug("treeModels size:" + treeModels.size());
		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode result = treeHelper.getTreeArrayNode(treeModels, false);
		try {
			// logger.debug(result.toString());
			return result.toString().getBytes("UTF-8");
		} catch (IOException ex) {
			return result.toString().getBytes();
		}
	}

	@RequestMapping("/taskMain")
	public ModelAndView taskMain(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String actorId = RequestUtils.getActorId(request);
		Database database = null;
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}
			userTaskService.deleteUserTasks(actorId);
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return new ModelAndView("/isdp/task/taskMain");
	}

	@ResponseBody
	@RequestMapping("/taskTotal")
	public byte[] taskTotal(HttpServletRequest request) {
		String actorId = RequestUtils.getActorId(request);
		String taskType = "RN";
		String cacheKey = "TASK_" + actorId + "_" + taskType;
		Database database = null;
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				long start = System.currentTimeMillis();
				database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}
				userTaskService.deleteUserTasks(actorId);
				userTaskService.reloadUserTasksGroupByTime(actorId, taskType);
				long times = System.currentTimeMillis() - start;
				logger.debug("创建任务耗时(毫秒):" + times);
				UserTaskQuery query = new UserTaskQuery();
				query.actorId(actorId);
				int total = userTaskService.getUserTaskCountByQueryCriteria(query);
				return String.valueOf(total).getBytes();
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		} finally {
			cache.remove(cacheKey);
			Environment.setCurrentSystemName(currentSystemName);
		}
		return "0".getBytes();
	}

	@RequestMapping("/todoList")
	public ModelAndView todoList(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String actorId = RequestUtils.getActorId(request);
		String taskType = "RN";
		String orderCondition = request.getParameter("orderCondition");
		String keyword = request.getParameter("keyword");
		if (StringUtils.isEmpty(orderCondition)) {
			orderCondition = RequestUtils.getCookieValue(request, "isdp_taskList_orderCondition");
		}

		String cacheKey = "TASK_" + actorId + "_" + taskType;
		Database database = null;
		long databaseId = RequestUtils.getLong(request, "databaseId");
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			if (cache.get(cacheKey) == null) {
				cache.put(cacheKey, "1");
				long start = System.currentTimeMillis();

				database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}
				userTaskService.deleteUserTasks(actorId);

				if ("WBS".equals(orderCondition)) {
					userTaskService.reloadUserTasksGroupByWBS(actorId, taskType);
				} else {
					userTaskService.reloadUserTasksGroupByTime(actorId, taskType);
				}

				long times = System.currentTimeMillis() - start;
				logger.debug("创建任务耗时(毫秒):" + times);
			}

			UserTaskQuery query = new UserTaskQuery();
			Tools.populate(query, paramMap);
			query.actorId(actorId);
			query.name("activ");
			query.setIndexNameLike(keyword);
			int total = userTaskService.getUserTaskCountByQueryCriteria(query);
			request.setAttribute("total", total);
			logger.debug(actorId + "任务项是" + total);
			if (total > 0) {
				List<UserTask> rows = userTaskService.list(query);
				request.setAttribute("tasks", rows);
			}

		} catch (Exception ex) {
			// ex.printStackTrace();
			logger.error(ex);
		} finally {
			cache.remove(cacheKey);
			Environment.setCurrentSystemName(currentSystemName);
		}
		return new ModelAndView("/isdp/task/todoList");
	}

}
