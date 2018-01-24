package com.glaf.monitor.server.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.glaf.monitor.server.domain.MonitorTerminalItem;
import com.glaf.monitor.server.query.MonitorTerminalItemQuery;
import com.glaf.monitor.server.service.MonitorTerminalItemService;
import com.glaf.monitor.server.util.MonitorTerminalItemDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/monitor/monitorTerminalItem")
@RequestMapping("/monitor/monitorTerminalItem")
public class MonitorTerminalItemController {
	protected static final Log logger = LogFactory.getLog(MonitorTerminalItemController.class);

	protected MonitorTerminalItemService monitorTerminalItemService;

	public MonitorTerminalItemController() {

	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorTerminalItemService")
	public void setMonitorTerminalItemService(MonitorTerminalItemService monitorTerminalItemService) {
		this.monitorTerminalItemService = monitorTerminalItemService;
	}


        @ResponseBody
	@RequestMapping("/saveMonitorTerminalItem")
	public byte[] saveMonitorTerminalItem(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalItem monitorTerminalItem = new MonitorTerminalItem();
		try {
		    Tools.populate(monitorTerminalItem, params);
                    monitorTerminalItem.setTerminalId(request.getParameter("terminalId"));
                    monitorTerminalItem.setCode(request.getParameter("code"));
                    monitorTerminalItem.setName(request.getParameter("name"));
                    monitorTerminalItem.setUnit(request.getParameter("unit"));
                    monitorTerminalItem.setAlarmVal(RequestUtils.getInt(request, "alarmVal"));
                    monitorTerminalItem.setRefType(request.getParameter("refType"));
                    monitorTerminalItem.setWarningType(request.getParameter("warningType"));
                    monitorTerminalItem.setMonitorServiceAddress(request.getParameter("monitorServiceAddress"));
                    monitorTerminalItem.setCreateby(request.getParameter("createby"));
                    monitorTerminalItem.setCreatetime(RequestUtils.getDate(request, "createtime"));
                    monitorTerminalItem.setUpdateby(request.getParameter("updateby"));
                    monitorTerminalItem.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                    monitorTerminalItem.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		    //monitorTerminalItem.setCreateBy(actorId);
		    this.monitorTerminalItemService.save(monitorTerminalItem);

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
					MonitorTerminalItem monitorTerminalItem = monitorTerminalItemService.getMonitorTerminalItem(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorTerminalItem monitorTerminalItem = monitorTerminalItemService
					.getMonitorTerminalItem(String.valueOf(id));
			if(monitorTerminalItem != null){
				MonitorTerminalItem monitorTerminalItem2 = new MonitorTerminalItem();
				monitorTerminalItem2.setId(monitorTerminalItem.getId());
				monitorTerminalItem2.setDeleteFlag(1);
				this.monitorTerminalItemService.save(monitorTerminalItem2);
			}
			return ResponseUtils.responseResult(true);
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
		MonitorTerminalItemQuery query = new MonitorTerminalItemQuery();
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
		int total = monitorTerminalItemService.getMonitorTerminalItemCountByQueryCriteria(query);
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
			List<MonitorTerminalItem> list = monitorTerminalItemService.getMonitorTerminalItemsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalItem monitorTerminalItem : list) {
					JSONObject rowJSON = monitorTerminalItem.toJsonObject();
					rowJSON.put("id", monitorTerminalItem.getId());
					rowJSON.put("rowId", monitorTerminalItem.getId());
					rowJSON.put("monitorTerminalItemId", monitorTerminalItem.getId());
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
	public byte[] read(HttpServletRequest request, @RequestBody Map<String, Object> dataRequest) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalItemQuery query = new MonitorTerminalItemQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		Tools.populate(query, dataRequest);

		
		if (dataRequest.get("params") != null ) {
			String paramstr = (String)dataRequest.get("params");
			if(StringUtils.isNotEmpty(paramstr)){
				Tools.populate(query, JSON.parseObject(paramstr));
			}
			
		}

		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
		  String actorId = loginContext.getActorId();
		  query.createBy(actorId);
		}

                int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = 0;
		limit = 0;
		if(dataRequest.get("page") != null){
			pageNo = (int)dataRequest.get("page");
		}
		if(dataRequest.get("pageSize") != null){
			limit = (int)dataRequest.get("pageSize");
		}

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = monitorTerminalItemService.getMonitorTerminalItemCountByQueryCriteria(query);
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


			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<MonitorTerminalItem> list = monitorTerminalItemService.getMonitorTerminalItemsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorTerminalItem monitorTerminalItem : list) {
					JSONObject rowJSON = monitorTerminalItem.toJsonObject();
					rowJSON.put("id", monitorTerminalItem.getId());
					rowJSON.put("monitorTerminalItemId", monitorTerminalItem.getId());
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
