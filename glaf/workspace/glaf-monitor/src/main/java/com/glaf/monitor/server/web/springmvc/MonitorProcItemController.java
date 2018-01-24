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

@Controller("/monitor/monitorProcItem")
@RequestMapping("/monitor/monitorProcItem")
public class MonitorProcItemController {
	protected static final Log logger = LogFactory.getLog(MonitorProcItemController.class);

	protected MonitorProcItemService monitorProcItemService;

	public MonitorProcItemController() {

	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorProcItemService")
	public void setMonitorProcItemService(MonitorProcItemService monitorProcItemService) {
		this.monitorProcItemService = monitorProcItemService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		MonitorProcItem monitorProcItem = new MonitorProcItem();
		Tools.populate(monitorProcItem, params);

		//monitorProcItem.setCreateBy(actorId);
         
		monitorProcItemService.save(monitorProcItem);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveMonitorProcItem")
	public byte[] saveMonitorProcItem(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcItem monitorProcItem = new MonitorProcItem();
		try {
		    Tools.populate(monitorProcItem, params);
		    Date nowDate = new Date();
		    if(StringUtils.isEmpty(monitorProcItem.getId())){
		    	monitorProcItem.setCreatetime(nowDate);
		    	monitorProcItem.setCreateby(actorId);
		    	monitorProcItem.setDeleteFlag(0);
		    }else{
		    	monitorProcItem.setUpdatetime(nowDate);
		    	monitorProcItem.setUpdateby(actorId);
		    }
		    //monitorProcItem.setCreateBy(actorId);
		    this.monitorProcItemService.save(monitorProcItem);

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
					MonitorProcItem monitorProcItem = monitorProcItemService.getMonitorProcItem(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorProcItem monitorProcItem = monitorProcItemService
					.getMonitorProcItem(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (monitorProcItem != null) {
				monitorProcItem.setDeleteFlag(1);
				monitorProcItemService.save(monitorProcItem);
				return ResponseUtils.responseResult(true);
			}
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
                MonitorProcItem monitorProcItem = monitorProcItemService.getMonitorProcItem(request.getParameter("id"));
		if(monitorProcItem != null) {
		    request.setAttribute("monitorProcItem", monitorProcItem);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("monitorProcItem.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/monitor/monitorProcItem/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                MonitorProcItem monitorProcItem = monitorProcItemService.getMonitorProcItem(request.getParameter("id"));
		request.setAttribute("monitorProcItem", monitorProcItem);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("monitorProcItem.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/monitor/monitorProcItem/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("monitorProcItem.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/monitor/monitorProcItem/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcItemQuery query = new MonitorProcItemQuery();
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
		int total = monitorProcItemService.getMonitorProcItemCountByQueryCriteria(query);
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
			List<MonitorProcItem> list = monitorProcItemService.getMonitorProcItemsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProcItem monitorProcItem : list) {
					JSONObject rowJSON = monitorProcItem.toJsonObject();
					rowJSON.put("id", monitorProcItem.getId());
					rowJSON.put("rowId", monitorProcItem.getId());
					rowJSON.put("monitorProcItemId", monitorProcItem.getId());
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
	
	public int getIntValue(Map<String,Object> params,String key){
		int realValue = 0;
		String value = String.valueOf(params.get(key));
		if(StringUtils.isNotEmpty(value)){
			try{
				realValue = Integer.valueOf(value);
			}catch(Exception e){
				realValue = 0;
			}
		}
		return realValue;
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody Map<String,Object> params) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		MonitorProcItemQuery query = new MonitorProcItemQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (params.get("params") != null ) {
			String paramstr = (String)params.get("params");
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

		int pageNo = getIntValue(params,"page");
		limit = getIntValue(params,"pageSize");

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = monitorProcItemService.getMonitorProcItemCountByQueryCriteria(query);
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
			List<MonitorProcItem> list = monitorProcItemService.getMonitorProcItemsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProcItem monitorProcItem : list) {
					JSONObject rowJSON = monitorProcItem.toJsonObject();
					rowJSON.put("id", monitorProcItem.getId());
					rowJSON.put("monitorProcItemId", monitorProcItem.getId());
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
 

		return new ModelAndView("/monitor/monitorProcItem/list", modelMap);
	}

}
