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

@Controller("/monitor/monitorEvent")
@RequestMapping("/monitor/monitorEvent")
public class MonitorEventController {
	protected static final Log logger = LogFactory.getLog(MonitorEventController.class);

	protected MonitorEventService monitorEventService;

	public MonitorEventController() {

	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorEventService")
	public void setMonitorEventService(MonitorEventService monitorEventService) {
		this.monitorEventService = monitorEventService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		MonitorEvent monitorEvent = new MonitorEvent();
		Tools.populate(monitorEvent, params);

                monitorEvent.setObjectId(request.getParameter("objectId"));
                monitorEvent.setObjectType(request.getParameter("objectType"));
                monitorEvent.setEventType(request.getParameter("eventType"));
                monitorEvent.setEventMonitorItem(request.getParameter("eventMonitorItem"));
                monitorEvent.setHappenTime(RequestUtils.getDate(request, "happenTime"));
                monitorEvent.setSnapshot(request.getParameter("snapshot"));
                monitorEvent.setRecoveryTime(RequestUtils.getDate(request, "recoveryTime"));
                monitorEvent.setStatus(RequestUtils.getInt(request, "status"));
                monitorEvent.setCreateby(request.getParameter("createby"));
                monitorEvent.setCreatetime(RequestUtils.getDate(request, "createtime"));
                monitorEvent.setUpdateby(request.getParameter("updateby"));
                monitorEvent.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                monitorEvent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		
		//monitorEvent.setCreateBy(actorId);
         
		monitorEventService.save(monitorEvent);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveMonitorEvent")
	public byte[] saveMonitorEvent(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorEvent monitorEvent = new MonitorEvent();
		try {
		    Tools.populate(monitorEvent, params);
                    monitorEvent.setObjectId(request.getParameter("objectId"));
                    monitorEvent.setObjectType(request.getParameter("objectType"));
                    monitorEvent.setEventType(request.getParameter("eventType"));
                    monitorEvent.setEventMonitorItem(request.getParameter("eventMonitorItem"));
                    monitorEvent.setHappenTime(RequestUtils.getDate(request, "happenTime"));
                    monitorEvent.setSnapshot(request.getParameter("snapshot"));
                    monitorEvent.setRecoveryTime(RequestUtils.getDate(request, "recoveryTime"));
                    monitorEvent.setStatus(RequestUtils.getInt(request, "status"));
                    monitorEvent.setCreateby(request.getParameter("createby"));
                    monitorEvent.setCreatetime(RequestUtils.getDate(request, "createtime"));
                    monitorEvent.setUpdateby(request.getParameter("updateby"));
                    monitorEvent.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                    monitorEvent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		    //monitorEvent.setCreateBy(actorId);
		    this.monitorEventService.save(monitorEvent);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody MonitorEvent saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		MonitorEvent monitorEvent = new MonitorEvent();
		try {
		    Tools.populate(monitorEvent, model);
                    monitorEvent.setObjectId(ParamUtils.getString(model, "objectId"));
                    monitorEvent.setObjectType(ParamUtils.getString(model, "objectType"));
                    monitorEvent.setEventType(ParamUtils.getString(model, "eventType"));
                    monitorEvent.setEventMonitorItem(ParamUtils.getString(model, "eventMonitorItem"));
                    monitorEvent.setHappenTime(ParamUtils.getDate(model, "happenTime"));
                    monitorEvent.setSnapshot(ParamUtils.getString(model, "snapshot"));
                    monitorEvent.setRecoveryTime(ParamUtils.getDate(model, "recoveryTime"));
                    monitorEvent.setStatus(ParamUtils.getInt(model, "status"));
                    monitorEvent.setCreateby(ParamUtils.getString(model, "createby"));
                    monitorEvent.setCreatetime(ParamUtils.getDate(model, "createtime"));
                    monitorEvent.setUpdateby(ParamUtils.getString(model, "updateby"));
                    monitorEvent.setUpdatetime(ParamUtils.getDate(model, "updatetime"));
                    monitorEvent.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
		    this.monitorEventService.save(monitorEvent);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return monitorEvent;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                MonitorEvent monitorEvent = monitorEventService.getMonitorEvent(request.getParameter("eventId"));

		Tools.populate(monitorEvent, params);

                monitorEvent.setObjectId(request.getParameter("objectId"));
                monitorEvent.setObjectType(request.getParameter("objectType"));
                monitorEvent.setEventType(request.getParameter("eventType"));
                monitorEvent.setEventMonitorItem(request.getParameter("eventMonitorItem"));
                monitorEvent.setHappenTime(RequestUtils.getDate(request, "happenTime"));
                monitorEvent.setSnapshot(request.getParameter("snapshot"));
                monitorEvent.setRecoveryTime(RequestUtils.getDate(request, "recoveryTime"));
                monitorEvent.setStatus(RequestUtils.getInt(request, "status"));
                monitorEvent.setCreateby(request.getParameter("createby"));
                monitorEvent.setCreatetime(RequestUtils.getDate(request, "createtime"));
                monitorEvent.setUpdateby(request.getParameter("updateby"));
                monitorEvent.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                monitorEvent.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
	
		monitorEventService.save(monitorEvent);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String eventId = RequestUtils.getString(request, "eventId");
		String eventIds = request.getParameter("eventIds");
		if (StringUtils.isNotEmpty(eventIds)) {
			StringTokenizer token = new StringTokenizer(eventIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MonitorEvent monitorEvent = monitorEventService.getMonitorEvent(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (eventId != null) {
			MonitorEvent monitorEvent = monitorEventService
					.getMonitorEvent(String.valueOf(eventId));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
		}
		return ResponseUtils.responseResult(false);
	}

    
        @RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                MonitorEvent monitorEvent = monitorEventService.getMonitorEvent(request.getParameter("eventId"));
		if(monitorEvent != null) {
		    request.setAttribute("monitorEvent", monitorEvent);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("monitorEvent.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/monitor/monitorEvent/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                MonitorEvent monitorEvent = monitorEventService.getMonitorEvent(request.getParameter("eventId"));
		request.setAttribute("monitorEvent", monitorEvent);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("monitorEvent.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/monitor/monitorEvent/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("monitorEvent.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/monitor/monitorEvent/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorEventQuery query = new MonitorEventQuery();
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
		int total = monitorEventService.getMonitorEventCountByQueryCriteria(query);
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
			List<MonitorEvent> list = monitorEventService.getMonitorEventsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorEvent monitorEvent : list) {
					JSONObject rowJSON = monitorEvent.toJsonObject();
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
		MonitorEventQuery query = new MonitorEventQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		MonitorEventDomainFactory.processDataRequest(dataRequest);

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
		int total = monitorEventService.getMonitorEventCountByQueryCriteria(query);
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
			List<MonitorEvent> list = monitorEventService.getMonitorEventsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorEvent monitorEvent : list) {
					JSONObject rowJSON = monitorEvent.toJsonObject();
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
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

                String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
 

		return new ModelAndView("/monitor/monitorEvent/list", modelMap);
	}

}
