package com.glaf.base.modules.sys.web.springmvc;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.glaf.core.base.TreeModel;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */
@Controller("/identity/agent")
@RequestMapping("/identity/agent")
public class SysAgentController {
	protected static final Log logger = LogFactory.getLog(SysAgentController.class);

	protected SysAgentService sysAgentService;

	protected SysTreeService sysTreeService;

	public SysAgentController() {

	}

	@RequestMapping
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String assignFrom = request.getParameter("assignFrom");
		if (StringUtils.isEmpty(assignFrom)) {
			if (loginContext.isSystemAdministrator()) {
				if (!(StringUtils.equalsIgnoreCase(loginContext.getActorId(), "admin")
						|| StringUtils.equalsIgnoreCase(loginContext.getActorId(), "root"))) {
					assignFrom = loginContext.getActorId();
				}
			} else {
				assignFrom = loginContext.getActorId();
			}
		} else {
			if (assignFrom.length() > 30) {
				assignFrom = RequestUtils.decodeString(assignFrom);
			}
		}
		Map<String, User> userMap = IdentityFactory.getUserMap();
		SysAgentQuery query = new SysAgentQuery();
		query.assignFrom(assignFrom);
		List<SysAgent> agents = sysAgentService.list(query);
		StringBuffer global = new StringBuffer();
		StringBuffer globalName = new StringBuffer();
		Map<String, Agent> agentMap = new HashMap<String, Agent>();
		if (agents == null) {
			agents = new ArrayList<SysAgent>();
		}
		Iterator<SysAgent> iter99 = agents.iterator();
		while (iter99.hasNext()) {
			Agent agent = iter99.next();
			User u = userMap.get(agent.getAssignTo());
			if (u != null) {
				switch (agent.getAgentType()) {
				case 0:
					global.append(u.getActorId()).append(',');
					globalName.append(u.getName() != null ? u.getName() : u.getActorId()).append(',');
					modelMap.put("global_locked", agent.getLocked());
					modelMap.put("global_startDate", agent.getStartDate());
					modelMap.put("global_endDate", agent.getEndDate());
					break;
				case 1:
					agentMap.put(agent.getProcessName(), agent);
					break;
				case 2:
					agentMap.put(agent.getProcessName() + "_" + agent.getTaskName(), agent);
					break;
				default:
					break;
				}
			}
		}

		if (global.length() > 0) {
			global.delete(global.length() - 1, global.length());
		}

		if (globalName.length() > 0) {
			globalName.delete(globalName.length() - 1, globalName.length());
		}

		modelMap.put("global_agentIds", global.toString());
		modelMap.put("global_agentNames", globalName.toString());

		modelMap.put("agentMap", agentMap);
		modelMap.put("agents", agents);

		try {
			SysTree root = sysTreeService.getSysTreeByCode("process_root_node");
			if (root != null) {
				List<SysTree> list = sysTreeService.getSysTreeList(root.getId());
				Collection<TreeModel> processDefinitions = new ArrayList<TreeModel>();
				Collection<String> processDefinitionNames = new ArrayList<String>();
				for (SysTree object : list) {
					SysTree pd = (SysTree) object;
					if (!processDefinitionNames.contains(pd.getName())) {
						processDefinitions.add(pd);
						processDefinitionNames.add(pd.getName());
					}
				}
				if (processDefinitions != null && processDefinitions.size() > 0) {
					StringBuffer ids = new StringBuffer();
					StringBuffer names = new StringBuffer();
					request.setAttribute("processDefinitions", processDefinitions);

					Map<String, Collection<TreeModel>> dataMap = new HashMap<String, Collection<TreeModel>>();

					Iterator<TreeModel> iterator = processDefinitions.iterator();
					while (iterator.hasNext()) {
						TreeModel pd = iterator.next();

						ids.delete(0, ids.length());
						names.delete(0, names.length());

						iter99 = agents.iterator();
						while (iter99.hasNext()) {
							Agent agent = iter99.next();
							User u = userMap.get(agent.getAssignTo());
							if (u != null) {
								switch (agent.getAgentType()) {
								case 0:
									break;
								case 1:
									if (StringUtils.equals(agent.getProcessName(), pd.getName())) {
										ids.append(u.getActorId()).append(',');
										names.append(u.getName() != null ? u.getName() : u.getActorId()).append(',');
									}
									break;
								case 2:
									break;
								default:
									break;
								}
							}
						}

						if (ids.length() > 0) {
							ids.delete(ids.length() - 1, ids.length());
						}

						if (names.length() > 0) {
							names.delete(names.length() - 1, names.length());
						}

						modelMap.put("x_" + pd.getName() + "_agentIds", ids.toString());
						modelMap.put("x_" + pd.getName() + "_agentNames", names.toString());

						Map<String, TreeModel> taskMap = new HashMap<String, TreeModel>();

						List<SysTree> list2 = sysTreeService.getSysTreeList(pd.getId());
						for (SysTree object2 : list2) {
							SysTree task = (SysTree) object2;
							taskMap.put(task.getName(), task);
						}

						if (taskMap != null && taskMap.size() > 0) {
							dataMap.put(pd.getName(), taskMap.values());
							Iterator<TreeModel> iter88 = taskMap.values().iterator();
							while (iter88.hasNext()) {
								TreeModel task = iter88.next();

								ids.delete(0, ids.length());
								names.delete(0, names.length());

								iter99 = agents.iterator();
								while (iter99.hasNext()) {
									Agent agent = iter99.next();
									User u = userMap.get(agent.getAssignTo());
									if (u != null) {
										switch (agent.getAgentType()) {
										case 0:
											break;
										case 1:
											break;
										case 2:
											if (StringUtils.equals(agent.getProcessName(), pd.getName())
													&& StringUtils.equals(agent.getTaskName(), task.getName())) {
												ids.append(u.getActorId()).append(',');
												names.append(u.getName() != null ? u.getName() : u.getActorId())
														.append(',');
											}
											break;
										default:
											break;
										}
									}
								}

								if (ids.length() > 0) {
									ids.delete(ids.length() - 1, ids.length());
								}

								if (names.length() > 0) {
									names.delete(names.length() - 1, names.length());
								}

								modelMap.put("x_" + pd.getName() + "_" + task.getName() + "_agentIds", ids.toString());
								modelMap.put("x_" + pd.getName() + "_" + task.getName() + "_agentNames",
										names.toString());
							}
						}
					}
					modelMap.put("dataMap", dataMap);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysAgent.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/identity/agent/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveAgents")
	public byte[] saveAgents(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String assignFrom = request.getParameter("assignFrom");
		if (StringUtils.isEmpty(assignFrom)) {
			if (loginContext.isSystemAdministrator()) {
				if (!(StringUtils.equalsIgnoreCase(loginContext.getActorId(), "admin")
						|| StringUtils.equalsIgnoreCase(loginContext.getActorId(), "root"))) {
					assignFrom = loginContext.getActorId();
				}
			} else {
				assignFrom = loginContext.getActorId();
			}
		} else {
			if (assignFrom.length() > 30) {
				assignFrom = RequestUtils.decodeString(assignFrom);
			}
		}
		List<SysAgent> agents = new ArrayList<SysAgent>();
		String global_agentIds = request.getParameter("global_agentIds");
		List<String> agentIds = StringTools.split(global_agentIds);
		if (agentIds != null && agentIds.size() > 0) {
			Date startDate = RequestUtils.getDate(request, "global_startDate");
			Date endDate = RequestUtils.getDate(request, "global_endDate");
			String locked = request.getParameter("global_locked");
			logger.debug("locked=" + locked);
			Iterator<String> iter = agentIds.iterator();
			while (iter.hasNext()) {
				String agentId = iter.next();
				SysAgent agent = new SysAgent();
				agent.setAssignFrom(assignFrom);
				agent.setAssignTo(agentId);
				agent.setStartDate(startDate);
				agent.setEndDate(endDate);
				agent.setObjectId("agent");
				agent.setObjectValue("global");
				agent.setCreateBy(loginContext.getActorId());
				if (StringUtils.equals(locked, "on")) {
					agent.setLocked(0);
				} else {
					agent.setLocked(1);
				}
				agents.add(agent);
			}
		}

		try {
			SysTree root = sysTreeService.getSysTreeByCode("process_root_node");
			if (root != null) {
				List<SysTree> list = sysTreeService.getSysTreeList(root.getId());
				if (list != null && list.size() > 0) {
					Iterator<SysTree> iterator = list.iterator();
					while (iterator.hasNext()) {
						SysTree pd = iterator.next();
						String p_agentIds = request.getParameter(pd.getName());
						List<String> agentIds2 = StringTools.split(p_agentIds);
						if (agentIds2 != null && agentIds2.size() > 0) {
							Date startDate = RequestUtils.getDate(request, pd.getName() + "_startDate");
							Date endDate = RequestUtils.getDate(request, pd.getName() + "_endDate");
							String locked = request.getParameter(pd.getName() + "_locked");
							Iterator<String> iter = agentIds2.iterator();
							while (iter.hasNext()) {
								String agentId = iter.next();
								SysAgent agent = new SysAgent();
								agent.setAssignFrom(assignFrom);
								agent.setAssignTo(agentId);
								agent.setStartDate(startDate);
								agent.setEndDate(endDate);
								agent.setProcessName(pd.getName());
								agent.setAgentType(1);
								agent.setObjectId("agent");
								agent.setObjectValue(pd.getDiscriminator());
								if (StringUtils.equals(locked, "on")) {
									agent.setLocked(0);
								} else {
									agent.setLocked(1);
								}
								agents.add(agent);
							}
						}
						List<SysTree> tasks = sysTreeService.getSysTreeList(pd.getId());
						if (tasks != null && tasks.size() > 0) {
							Iterator<SysTree> iter88 = tasks.iterator();
							while (iter88.hasNext()) {
								SysTree task = iter88.next();
								String t_agentIds = request.getParameter(pd.getName() + "_" + task.getName());
								List<String> agentIds4 = StringTools.split(t_agentIds);
								if (agentIds4 != null && agentIds4.size() > 0) {
									Date startDate = RequestUtils.getDate(request,
											pd.getName() + "_" + task.getName() + "_startDate");
									Date endDate = RequestUtils.getDate(request,
											pd.getName() + "_" + task.getName() + "_endDate");
									String locked = request
											.getParameter(pd.getName() + "_" + task.getName() + "_locked");
									Iterator<String> iter = agentIds4.iterator();
									while (iter.hasNext()) {
										String agentId = iter.next();
										SysAgent agent = new SysAgent();
										agent.setAssignFrom(assignFrom);
										agent.setAssignTo(agentId);
										agent.setStartDate(startDate);
										agent.setEndDate(endDate);
										agent.setProcessName(pd.getName());
										agent.setTaskName(task.getName());
										agent.setAgentType(2);
										agent.setObjectId("agent");
										agent.setObjectValue(task.getDiscriminator());
										if (StringUtils.equals(locked, "on")) {
											agent.setLocked(0);
										} else {
											agent.setLocked(1);
										}
										agents.add(agent);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}

		try {
			sysAgentService.saveAgents(assignFrom, agents);
			return ResponseUtils.responseResult(true);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setSysAgentService(SysAgentService sysAgentService) {
		this.sysAgentService = sysAgentService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

}
