package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.Role;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.DataPermission;
import com.glaf.datamgr.query.DataPermissionQuery;
import com.glaf.datamgr.service.DataItemDefinitionService;
import com.glaf.datamgr.service.DataPermissionService;
import com.glaf.datamgr.util.DataItemFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/dataPermission")
@RequestMapping("/sys/dataPermission")
public class DataPermissionController {
	protected static final Log logger = LogFactory.getLog(DataPermissionController.class);

	protected DataPermissionService dataPermissionService;

	protected DataItemDefinitionService dataItemDefinitionService;

	public DataPermissionController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String businessType = request.getParameter("businessType");
		DataPermissionQuery query = new DataPermissionQuery();
		query.businessType(businessType);
		List<DataPermission> list = dataPermissionService.list(query);
		request.setAttribute("list", list);

		JSONArray array = DataItemFactory.getJSONArray(businessType);
		request.setAttribute("array", array);

		List<Role> roles = IdentityFactory.getRoles();
		request.setAttribute("roles", roles);

		List<User> users = IdentityFactory.getUsers();
		request.setAttribute("users", users);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataPermission.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataPermission/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataPermissionQuery query = new DataPermissionQuery();
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
		int total = dataPermissionService.getDataPermissionCountByQueryCriteria(query);
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

			List<DataPermission> list = dataPermissionService.getDataPermissionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (DataPermission dataPermission : list) {
					JSONObject rowJSON = dataPermission.toJsonObject();
					rowJSON.put("id", dataPermission.getId());
					rowJSON.put("rowId", dataPermission.getId());
					rowJSON.put("dataPermissionId", dataPermission.getId());
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

	@ResponseBody
	@RequestMapping("/saveAll")
	public byte[] saveAll(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String businessType = request.getParameter("businessType");
		try {
			if (StringUtils.isNotEmpty(request.getParameter("objectIds"))) {
				List<String> objectIds = StringTools.split(request.getParameter("objectIds"));
				List<DataPermission> list = new ArrayList<DataPermission>();

				if (StringUtils.isNotEmpty(request.getParameter("userIds"))) {
					List<String> userIds = StringTools.split(request.getParameter("userIds"));
					if (userIds != null && !userIds.isEmpty()) {
						if (userIds.size() == 1) {
							String userId = userIds.get(0);
							for (String businessKey : objectIds) {
								DataPermission model = new DataPermission();
								model.setBusinessKey(businessKey);
								model.setBusinessType(businessType);
								model.setAccessType("user");
								model.setUserId(userId);
								model.setCreateBy(actorId);
								list.add(model);
							}
							DataPermissionQuery query = new DataPermissionQuery();
							query.accessType("user");
							query.businessType(businessType);
							query.userId(userId);
							dataPermissionService.saveAll(query, list);
						} else {
							for (String userId : userIds) {
								list.clear();
								for (String businessKey : objectIds) {
									DataPermission model = new DataPermission();
									model.setBusinessKey(businessKey);
									model.setBusinessType(businessType);
									model.setAccessType("user");
									model.setUserId(userId);
									model.setCreateBy(actorId);
									list.add(model);
								}
								if (list.size() > 0) {
									DataPermissionQuery query = new DataPermissionQuery();
									query.accessType("user");
									query.businessType(businessType);
									query.userId(userId);
									dataPermissionService.saveAll(query, list);
									list.clear();
								}
							}
						}
					}
				}

				if (StringUtils.isNotEmpty(request.getParameter("roleIds"))) {
					List<String> roleIds = StringTools.split(request.getParameter("roleIds"));
					if (roleIds != null && !roleIds.isEmpty()) {
						if (roleIds.size() == 1) {
							String roleId = roleIds.get(0);
							for (String businessKey : objectIds) {
								DataPermission model = new DataPermission();
								model.setBusinessKey(businessKey);
								model.setBusinessType(businessType);
								model.setAccessType("role");
								model.setRoleId(roleId);
								model.setCreateBy(actorId);
								list.add(model);
							}
							DataPermissionQuery query = new DataPermissionQuery();
							query.accessType("role");
							query.businessType(businessType);
							query.roleId(roleId);
							dataPermissionService.saveAll(query, list);
						} else {
							for (String roleId : roleIds) {
								list.clear();
								for (String businessKey : objectIds) {
									DataPermission model = new DataPermission();
									model.setBusinessKey(businessKey);
									model.setBusinessType(businessType);
									model.setAccessType("role");
									model.setRoleId(roleId);
									model.setCreateBy(actorId);
									list.add(model);
								}

								if (list.size() > 0) {
									DataPermissionQuery query = new DataPermissionQuery();
									query.accessType("role");
									query.businessType(businessType);
									query.roleId(roleId);
									dataPermissionService.saveAll(query, list);
									list.clear();
								}
							}
						}
					}
				}

				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveByUser")
	public byte[] saveByUser(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String userId = request.getParameter("userId");
		String businessType = request.getParameter("businessType");
		try {
			if (StringUtils.isNotEmpty(request.getParameter("userId"))) {
				List<String> objectIds = StringTools.split(request.getParameter("objectIds"));
				List<DataPermission> list = new ArrayList<DataPermission>();

				for (String businessKey : objectIds) {
					DataPermission model = new DataPermission();
					model.setBusinessKey(businessKey);
					model.setBusinessType(businessType);
					model.setAccessType("user");
					model.setUserId(userId);
					model.setCreateBy(actorId);
					list.add(model);
				}
				DataPermissionQuery query = new DataPermissionQuery();
				query.accessType("user");
				query.businessType(businessType);
				query.userId(userId);
				dataPermissionService.saveAll(query, list);

				return ResponseUtils.responseJsonResult(true);
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDataItemDefinitionService(DataItemDefinitionService dataItemDefinitionService) {
		this.dataItemDefinitionService = dataItemDefinitionService;
	}

	@javax.annotation.Resource
	public void setDataPermissionService(DataPermissionService dataPermissionService) {
		this.dataPermissionService = dataPermissionService;
	}

	@RequestMapping("/viewByUser")
	public ModelAndView viewByUser(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String businessType = request.getParameter("businessType");
		String userId = request.getParameter("userId");
		DataPermissionQuery query = new DataPermissionQuery();
		query.businessType(businessType);
		if (userId != null && userId.length() > 0) {
			query.setUserId(userId);
		}
		List<DataPermission> list = dataPermissionService.list(query);
		request.setAttribute("list", list);

		JSONArray array = DataItemFactory.getJSONArray(businessType);
		if (array != null && array.size() > 0) {
			if (StringUtils.isNotEmpty(userId)) {
				for (DataPermission model : list) {
					if (StringUtils.equals(model.getUserId(), userId)) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject json = (JSONObject) array.getJSONObject(i);
							String key = json.getString("key");
							if (StringUtils.equals(key, model.getBusinessKey())) {
								json.put("selected", "true");
							}
						}
					}
				}
			}
			request.setAttribute("array", array);
		}

		List<Role> roles = IdentityFactory.getRoles();
		request.setAttribute("roles", roles);

		List<User> users = IdentityFactory.getUsers();
		request.setAttribute("users", users);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataPermission.viewByUser");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataPermission/viewByUser", modelMap);
	}

}
