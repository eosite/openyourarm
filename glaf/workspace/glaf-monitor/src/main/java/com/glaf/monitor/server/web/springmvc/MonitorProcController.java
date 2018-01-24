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

@Controller("/monitor/monitorProc")
@RequestMapping("/monitor/monitorProc")
public class MonitorProcController {
	protected static final Log logger = LogFactory.getLog(MonitorProcController.class);

	protected MonitorProcService monitorProcService;

	public MonitorProcController() {

	}

        @javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorProcService")
	public void setMonitorProcService(MonitorProcService monitorProcService) {
		this.monitorProcService = monitorProcService;
	}


        @ResponseBody
	@RequestMapping("/saveMonitorProc")
	public byte[] saveMonitorProc(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProc monitorProc = new MonitorProc();
		try {
		    Tools.populate(monitorProc, params);
		    
		    Date nowDate = new Date();
		    if (StringUtils.isEmpty(monitorProc.getId())) {
		    	// 新增
				monitorProc.setCreateby(actorId);
				monitorProc.setCreatetime(nowDate);
				monitorProc.setDeleteFlag(0);
				
		    	this.monitorProcService.save(monitorProc, ParamUtils.getIntValue(params, "categoryId"));
		    }else{
		    	// 修改
				monitorProc.setUpdateby(actorId);
				monitorProc.setUpdatetime(nowDate);
				
				this.monitorProcService.save(monitorProc);
		    }
		    
		    
		    //monitorProc.setCreateBy(actorId);
		    

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody MonitorProc saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		MonitorProc monitorProc = new MonitorProc();
		try {
		    Tools.populate(monitorProc, model);
                    monitorProc.setTerminalId(ParamUtils.getString(model, "terminalId"));
                    monitorProc.setLevel(ParamUtils.getString(model, "level"));
                    monitorProc.setProcessName(ParamUtils.getString(model, "processName"));
                    monitorProc.setName(ParamUtils.getString(model, "name"));
                    monitorProc.setProd(ParamUtils.getString(model, "prod"));
                    monitorProc.setVer(ParamUtils.getString(model, "ver"));
                    monitorProc.setType(ParamUtils.getString(model, "type"));
                    monitorProc.setDesc(ParamUtils.getString(model, "desc"));
                    monitorProc.setPort(ParamUtils.getInt(model, "port"));
                    monitorProc.setMonitorServiceAddress(ParamUtils.getString(model, "monitorServiceAddress"));
                    monitorProc.setStartAddress(ParamUtils.getString(model, "startAddress"));
                    monitorProc.setStopAddress(ParamUtils.getString(model, "stopAddress"));
                    monitorProc.setTerminateAddress(ParamUtils.getString(model, "terminateAddress"));
                    monitorProc.setStatus(ParamUtils.getInt(model, "status"));
                    monitorProc.setParentProcId(ParamUtils.getString(model, "parentProcId"));
                    monitorProc.setOtherItems(ParamUtils.getString(model, "otherItems"));
                    monitorProc.setCreateby(ParamUtils.getString(model, "createby"));
                    monitorProc.setCreatetime(ParamUtils.getDate(model, "createtime"));
                    monitorProc.setUpdateby(ParamUtils.getString(model, "updateby"));
                    monitorProc.setUpdatetime(ParamUtils.getDate(model, "updatetime"));
                    monitorProc.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
		    this.monitorProcService.save(monitorProc);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return monitorProc;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                MonitorProc monitorProc = monitorProcService.getMonitorProc(request.getParameter("id"));

		Tools.populate(monitorProc, params);

                monitorProc.setTerminalId(request.getParameter("terminalId"));
                monitorProc.setLevel(request.getParameter("level"));
                monitorProc.setProcessName(request.getParameter("processName"));
                monitorProc.setName(request.getParameter("name"));
                monitorProc.setProd(request.getParameter("prod"));
                monitorProc.setVer(request.getParameter("ver"));
                monitorProc.setType(request.getParameter("type"));
                monitorProc.setDesc(request.getParameter("desc"));
                monitorProc.setPort(RequestUtils.getInt(request, "port"));
                monitorProc.setMonitorServiceAddress(request.getParameter("monitorServiceAddress"));
                monitorProc.setStartAddress(request.getParameter("startAddress"));
                monitorProc.setStopAddress(request.getParameter("stopAddress"));
                monitorProc.setTerminateAddress(request.getParameter("terminateAddress"));
                monitorProc.setStatus(RequestUtils.getInt(request, "status"));
                monitorProc.setParentProcId(request.getParameter("parentProcId"));
                monitorProc.setOtherItems(request.getParameter("otherItems"));
                monitorProc.setCreateby(request.getParameter("createby"));
                monitorProc.setCreatetime(RequestUtils.getDate(request, "createtime"));
                monitorProc.setUpdateby(request.getParameter("updateby"));
                monitorProc.setUpdatetime(RequestUtils.getDate(request, "updatetime"));
                monitorProc.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
	
		monitorProcService.save(monitorProc);   

		return this.list(request, modelMap);
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
					MonitorProc monitorProc = monitorProcService.getMonitorProc(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorProc monitorProc = monitorProcService
					.getMonitorProc(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (monitorProc != null) {
				monitorProc.setDeleteFlag(1);
				monitorProcService.save(monitorProc);
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
                MonitorProc monitorProc = monitorProcService.getMonitorProc(request.getParameter("id"));
		if(monitorProc != null) {
		    request.setAttribute("monitorProc", monitorProc);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("monitorProc.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/monitor/monitorProc/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                MonitorProc monitorProc = monitorProcService.getMonitorProc(request.getParameter("id"));
		request.setAttribute("monitorProc", monitorProc);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("monitorProc.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/monitor/monitorProc/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("monitorProc.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/monitor/monitorProc/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorProcQuery query = new MonitorProcQuery();
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
		int total = monitorProcService.getMonitorProcCountByQueryCriteria(query);
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
			List<MonitorProc> list = monitorProcService.getMonitorProcsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProc monitorProc : list) {
					JSONObject rowJSON = monitorProc.toJsonObject();
					rowJSON.put("id", monitorProc.getId());
					rowJSON.put("rowId", monitorProc.getId());
					rowJSON.put("monitorProcId", monitorProc.getId());
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
		MonitorProcQuery query = new MonitorProcQuery();
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

		JSONObject result = new JSONObject();
		int total = monitorProcService.getMonitorProcCountByQueryCriteria(query);
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
			List<MonitorProc> list = monitorProcService.getMonitorProcsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (MonitorProc monitorProc : list) {
					JSONObject rowJSON = monitorProc.toJsonObject();
					rowJSON.put("id", monitorProc.getId());
					rowJSON.put("monitorProcId", monitorProc.getId());
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
 

		return new ModelAndView("/monitor/monitorProc/list", modelMap);
	}

}
