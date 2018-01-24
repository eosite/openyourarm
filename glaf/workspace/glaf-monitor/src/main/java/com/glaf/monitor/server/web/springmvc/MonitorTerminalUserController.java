package com.glaf.monitor.server.web.springmvc;

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
 
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
 
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;
import com.glaf.monitor.server.service.*;
import com.glaf.monitor.server.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/monitor/monitorTerminalUser")
@RequestMapping("/monitor/monitorTerminalUser")
public class MonitorTerminalUserController {
	protected static final Log logger = LogFactory.getLog(MonitorTerminalUserController.class);

	protected MonitorTerminalUserService monitorTerminalUserService;

	public MonitorTerminalUserController() {

	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorTerminalUserService")
	public void setMonitorTerminalUserService(MonitorTerminalUserService monitorTerminalUserService) {
		this.monitorTerminalUserService = monitorTerminalUserService;
	}



        @ResponseBody
	@RequestMapping("/saveMonitorTerminalUser")
	public byte[] saveMonitorTerminalUser(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalUser monitorTerminalUser = new MonitorTerminalUser();
		try {
		    Tools.populate(monitorTerminalUser, params);
                    monitorTerminalUser.setTerminalId(request.getParameter("terminalId"));
                    monitorTerminalUser.setRole(request.getParameter("role"));
                    monitorTerminalUser.setUsername(request.getParameter("username"));
                    monitorTerminalUser.setTel(request.getParameter("tel"));
                    monitorTerminalUser.setPhone(request.getParameter("phone"));
                    monitorTerminalUser.setEmail(request.getParameter("email"));
                    monitorTerminalUser.setCreateby(request.getParameter("createby"));
                    monitorTerminalUser.setCreatetime(RequestUtils.getDate(request, "createtime"));
                    monitorTerminalUser.setUpdateby(request.getParameter("updateby"));
                    monitorTerminalUser.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                    monitorTerminalUser.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		    //monitorTerminalUser.setCreateBy(actorId);
		    this.monitorTerminalUserService.save(monitorTerminalUser);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}



    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MonitorTerminalUser monitorTerminalUser = monitorTerminalUserService.getMonitorTerminalUser(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorTerminalUser monitorTerminalUser = monitorTerminalUserService
					.getMonitorTerminalUser(String.valueOf(id));
			if(monitorTerminalUser != null){
				MonitorTerminalUser monitorTerminalUser2 = new MonitorTerminalUser();
				monitorTerminalUser2.setId(monitorTerminalUser.getId());
				monitorTerminalUser2.setDeleteFlag(0);
				monitorTerminalUserService.save(monitorTerminalUser2);
				return ResponseUtils.responseResult(true);
			}
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
		}
		return ResponseUtils.responseResult(false);
	}

    

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalUserQuery query = new MonitorTerminalUserQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
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
		int total = monitorTerminalUserService.getMonitorTerminalUserCountByQueryCriteria(query);
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
			List<MonitorTerminalUser> list = monitorTerminalUserService.getMonitorTerminalUsersByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalUser monitorTerminalUser : list) {
					JSONObject rowJSON = monitorTerminalUser.toJsonObject();
					rowJSON.put("id", monitorTerminalUser.getId());
					rowJSON.put("rowId", monitorTerminalUser.getId());
					rowJSON.put("monitorTerminalUserId", monitorTerminalUser.getId());
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
		MonitorTerminalUserQuery query = new MonitorTerminalUserQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		MonitorTerminalUserDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
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
		int total = monitorTerminalUserService.getMonitorTerminalUserCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
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
			List<MonitorTerminalUser> list = monitorTerminalUserService.getMonitorTerminalUsersByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalUser monitorTerminalUser : list) {
					JSONObject rowJSON = monitorTerminalUser.toJsonObject();
					rowJSON.put("id", monitorTerminalUser.getId());
					rowJSON.put("monitorTerminalUserId", monitorTerminalUser.getId());
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
}
