package com.glaf.base.modules.sys.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.query.ServerEntityQuery;
import com.glaf.core.security.*;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.*;

import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/loginModule")
@RequestMapping("/sys/loginModule")
public class LoginModuleController {
	protected static final Log logger = LogFactory.getLog(LoginModuleController.class);

	protected LoginModuleService loginModuleService;

	protected IServerEntityService serverEntityService;

	public LoginModuleController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					LoginModule loginModule = loginModuleService.getLoginModule(String.valueOf(x));
					if (loginModule != null && (StringUtils.equals(loginModule.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						loginModuleService.deleteById(loginModule.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			LoginModule loginModule = loginModuleService.getLoginModule(String.valueOf(id));

			if (loginModule != null && (StringUtils.equals(loginModule.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				loginModuleService.deleteById(loginModule.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		LoginModule loginModule = loginModuleService.getLoginModule(request.getParameter("id"));
		if (loginModule != null) {
			request.setAttribute("loginModule", loginModule);
			request.setAttribute("type", loginModule.getType());
			if (StringUtils.contains(loginModule.getType(), "server")) {
				request.setAttribute("typeName", "服务器端");
			} else {
				request.setAttribute("typeName", "客户端");
			}
		} else {
			String type = request.getParameter("type");
			if (StringUtils.contains(type, "server")) {
				request.setAttribute("typeName", "服务器端");
			} else {
				request.setAttribute("typeName", "客户端");
			}
		}

		ServerEntityQuery query = new ServerEntityQuery();
		query.type("rabbitmq");
		query.locked(0);
		List<ServerEntity> servers = serverEntityService.list(query);
		request.setAttribute("servers", servers);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("loginModule.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/sys/loginModule/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginModuleQuery query = new LoginModuleQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.type(request.getParameter("type"));

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
		int total = loginModuleService.getLoginModuleCountByQueryCriteria(query);
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

			List<LoginModule> list = loginModuleService.getLoginModulesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (LoginModule loginModule : list) {
					JSONObject rowJSON = loginModule.toJsonObject();
					rowJSON.put("id", loginModule.getId());
					rowJSON.put("rowId", loginModule.getId());
					rowJSON.put("loginModuleId", loginModule.getId());
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

		String type = request.getParameter("type");
		if (StringUtils.contains(type, "server")) {
			request.setAttribute("typeName", "服务器端");
		} else {
			request.setAttribute("typeName", "客户端");
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/sys/loginModule/list", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginModuleQuery query = new LoginModuleQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		query.type(request.getParameter("type"));

		LoginModuleDomainFactory.processDataRequest(dataRequest);

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
		int total = loginModuleService.getLoginModuleCountByQueryCriteria(query);
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

			List<LoginModule> list = loginModuleService.getLoginModulesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (LoginModule loginModule : list) {
					JSONObject rowJSON = loginModule.toJsonObject();
					rowJSON.put("id", loginModule.getId());
					rowJSON.put("loginModuleId", loginModule.getId());
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
	@RequestMapping("/resetLoginAppSecret")
	public byte[] resetLoginAppSecret(HttpServletRequest request) {
		try {
			LoginModule loginModule = loginModuleService.getLoginModule(request.getParameter("id"));
			if (loginModule != null) {
				loginModuleService.resetLoginAppSecret(loginModule.getId());
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		LoginModule loginModule = new LoginModule();
		Tools.populate(loginModule, params);

		loginModule.setTitle(request.getParameter("title"));
		loginModule.setContent(request.getParameter("content"));
		loginModule.setAppId(request.getParameter("appId"));
		loginModule.setAppSecret(request.getParameter("appSecret"));
		loginModule.setPeerPublicKey(request.getParameter("peerPublicKey"));
		loginModule.setUrl(request.getParameter("url"));
		loginModule.setLoginUrl(request.getParameter("loginUrl"));
		loginModule.setLoginUserId(request.getParameter("loginUserId"));
		loginModule.setServerId(RequestUtils.getLong(request, "serverId"));
		loginModule.setSystemCode(request.getParameter("systemCode"));
		loginModule.setTimeLive(RequestUtils.getInt(request, "timeLive"));
		loginModule.setType(request.getParameter("type"));
		loginModule.setCreateBy(RequestUtils.getActorId(request));

		loginModuleService.save(loginModule);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveLoginModule")
	public byte[] saveLoginModule(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginModule loginModule = new LoginModule();
		try {
			Tools.populate(loginModule, params);
			loginModule.setTitle(request.getParameter("title"));
			loginModule.setContent(request.getParameter("content"));
			loginModule.setAppId(request.getParameter("appId"));
			loginModule.setAppSecret(request.getParameter("appSecret"));
			loginModule.setPeerPublicKey(request.getParameter("peerPublicKey"));
			loginModule.setUrl(request.getParameter("url"));
			loginModule.setLoginUrl(request.getParameter("loginUrl"));
			loginModule.setLoginUserId(request.getParameter("loginUserId"));
			loginModule.setServerId(RequestUtils.getLong(request, "serverId"));
			loginModule.setSystemCode(request.getParameter("systemCode"));
			loginModule.setTimeLive(RequestUtils.getInt(request, "timeLive"));
			loginModule.setType(request.getParameter("type"));
			loginModule.setCreateBy(RequestUtils.getActorId(request));

			this.loginModuleService.save(loginModule);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody LoginModule saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		LoginModule loginModule = new LoginModule();
		try {
			Tools.populate(loginModule, model);
			loginModule.setTitle(request.getParameter("title"));
			loginModule.setContent(request.getParameter("content"));
			loginModule.setAppId(request.getParameter("appId"));
			loginModule.setAppSecret(request.getParameter("appSecret"));
			loginModule.setPeerPublicKey(request.getParameter("peerPublicKey"));
			loginModule.setUrl(request.getParameter("url"));
			loginModule.setLoginUrl(request.getParameter("loginUrl"));
			loginModule.setLoginUserId(request.getParameter("loginUserId"));
			loginModule.setServerId(RequestUtils.getLong(request, "serverId"));
			loginModule.setSystemCode(request.getParameter("systemCode"));
			loginModule.setTimeLive(RequestUtils.getInt(request, "timeLive"));
			loginModule.setType(request.getParameter("type"));
			loginModule.setCreateBy(RequestUtils.getActorId(request));
			this.loginModuleService.save(loginModule);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return loginModule;
	}

	@javax.annotation.Resource
	public void setLoginModuleService(LoginModuleService loginModuleService) {
		this.loginModuleService = loginModuleService;
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		LoginModule loginModule = loginModuleService.getLoginModule(request.getParameter("id"));

		Tools.populate(loginModule, params);

		loginModule.setTitle(request.getParameter("title"));
		loginModule.setContent(request.getParameter("content"));
		loginModule.setAppId(request.getParameter("appId"));
		loginModule.setAppSecret(request.getParameter("appSecret"));
		loginModule.setPeerPublicKey(request.getParameter("peerPublicKey"));
		loginModule.setUrl(request.getParameter("url"));
		loginModule.setLoginUrl(request.getParameter("loginUrl"));
		loginModule.setLoginUserId(request.getParameter("loginUserId"));
		loginModule.setServerId(RequestUtils.getLong(request, "serverId"));
		loginModule.setSystemCode(request.getParameter("systemCode"));
		loginModule.setTimeLive(RequestUtils.getInt(request, "timeLive"));
		loginModule.setType(request.getParameter("type"));
		loginModule.setCreateBy(RequestUtils.getActorId(request));

		loginModuleService.save(loginModule);

		return this.list(request, modelMap);
	}

}
