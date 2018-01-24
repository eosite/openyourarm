package com.glaf.model.web.springmvc;

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
 
import com.glaf.model.domain.*;
import com.glaf.model.query.*;
import com.glaf.model.service.*;
import com.glaf.model.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/model/systemFuncDataObj")
@RequestMapping("/model/systemFuncDataObj")
public class SystemFuncDataObjController {
	protected static final Log logger = LogFactory.getLog(SystemFuncDataObjController.class);

	protected SystemFuncDataObjService systemFuncDataObjService;

	public SystemFuncDataObjController() {

	}

        @javax.annotation.Resource(name = "com.glaf.model.service.systemFuncDataObjService")
	public void setSystemFuncDataObjService(SystemFuncDataObjService systemFuncDataObjService) {
		this.systemFuncDataObjService = systemFuncDataObjService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		SystemFuncDataObj systemFuncDataObj = new SystemFuncDataObj();
		Tools.populate(systemFuncDataObj, params);

                systemFuncDataObj.setFuncId(RequestUtils.getString(request, "funcId"));
                systemFuncDataObj.setDataObjId(RequestUtils.getString(request, "dataObjId"));
                systemFuncDataObj.setCreateBy(request.getParameter("createBy"));
                systemFuncDataObj.setCreateTime(RequestUtils.getDate(request, "createTime"));
                systemFuncDataObj.setUpdateBy(request.getParameter("updateBy"));
                systemFuncDataObj.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		
		//systemFuncDataObj.setCreateBy(actorId);
         
		systemFuncDataObjService.save(systemFuncDataObj);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveSystemFuncDataObj")
	public byte[] saveSystemFuncDataObj(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemFuncDataObj systemFuncDataObj = new SystemFuncDataObj();
		try {
		    Tools.populate(systemFuncDataObj, params);
                    systemFuncDataObj.setFuncId(RequestUtils.getString(request, "funcId"));
                    systemFuncDataObj.setDataObjId(RequestUtils.getString(request, "dataObjId"));
                    systemFuncDataObj.setCreateBy(request.getParameter("createBy"));
                    systemFuncDataObj.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    systemFuncDataObj.setUpdateBy(request.getParameter("updateBy"));
                    systemFuncDataObj.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		    //systemFuncDataObj.setCreateBy(actorId);
		    this.systemFuncDataObjService.save(systemFuncDataObj);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SystemFuncDataObj saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		SystemFuncDataObj systemFuncDataObj = new SystemFuncDataObj();
		try {
		    Tools.populate(systemFuncDataObj, model);
                    systemFuncDataObj.setFuncId(ParamUtils.getString(model, "funcId"));
                    systemFuncDataObj.setDataObjId(ParamUtils.getString(model, "dataObjId"));
                    systemFuncDataObj.setCreateBy(ParamUtils.getString(model, "createBy"));
                    systemFuncDataObj.setCreateTime(ParamUtils.getDate(model, "createTime"));
                    systemFuncDataObj.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    systemFuncDataObj.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
		    systemFuncDataObj.setCreateBy(actorId);
		    this.systemFuncDataObjService.save(systemFuncDataObj);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return systemFuncDataObj;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                SystemFuncDataObj systemFuncDataObj = systemFuncDataObjService.getSystemFuncDataObj(RequestUtils.getString(request, "sysDataObjId"));

		Tools.populate(systemFuncDataObj, params);

                systemFuncDataObj.setFuncId(RequestUtils.getString(request, "funcId"));
                systemFuncDataObj.setDataObjId(RequestUtils.getString(request, "dataObjId"));
                systemFuncDataObj.setCreateBy(request.getParameter("createBy"));
                systemFuncDataObj.setCreateTime(RequestUtils.getDate(request, "createTime"));
                systemFuncDataObj.setUpdateBy(request.getParameter("updateBy"));
                systemFuncDataObj.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
	
		systemFuncDataObjService.save(systemFuncDataObj);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String sysDataObjId = RequestUtils.getString(request, "sysDataObjId");
		String sysDataObjIds = request.getParameter("sysDataObjIds");
		if (StringUtils.isNotEmpty(sysDataObjIds)) {
			StringTokenizer token = new StringTokenizer(sysDataObjIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SystemFuncDataObj systemFuncDataObj = systemFuncDataObjService.getSystemFuncDataObj(x);
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (systemFuncDataObj != null && (StringUtils.equals(systemFuncDataObj.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//systemFuncDataObj.setDeleteFlag(1);
						systemFuncDataObjService.save(systemFuncDataObj);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (sysDataObjId != null) {
			SystemFuncDataObj systemFuncDataObj = systemFuncDataObjService
					.getSystemFuncDataObj(sysDataObjId);
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (systemFuncDataObj != null && ( StringUtils.equals(systemFuncDataObj.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//systemFuncDataObj.setDeleteFlag(1);
				systemFuncDataObjService.save(systemFuncDataObj);
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
                SystemFuncDataObj systemFuncDataObj = systemFuncDataObjService.getSystemFuncDataObj(RequestUtils.getString(request, "sysDataObjId"));
		if(systemFuncDataObj != null) {
		    request.setAttribute("systemFuncDataObj", systemFuncDataObj);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("systemFuncDataObj.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/model/systemFuncDataObj/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                SystemFuncDataObj systemFuncDataObj = systemFuncDataObjService.getSystemFuncDataObj(RequestUtils.getString(request, "sysDataObjId"));
		request.setAttribute("systemFuncDataObj", systemFuncDataObj);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("systemFuncDataObj.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/model/systemFuncDataObj/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("systemFuncDataObj.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/model/systemFuncDataObj/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemFuncDataObjQuery query = new SystemFuncDataObjQuery();
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
		int total = systemFuncDataObjService.getSystemFuncDataObjCountByQueryCriteria(query);
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
			List<SystemFuncDataObj> list = systemFuncDataObjService.getSystemFuncDataObjsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SystemFuncDataObj systemFuncDataObj : list) {
					JSONObject rowJSON = systemFuncDataObj.toJsonObject();
					rowJSON.put("id", systemFuncDataObj.getSysDataObjId());
					rowJSON.put("rowId", systemFuncDataObj.getSysDataObjId());
					rowJSON.put("systemFuncDataObjId", systemFuncDataObj.getSysDataObjId());
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
		SystemFuncDataObjQuery query = new SystemFuncDataObjQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SystemFuncDataObjDomainFactory.processDataRequest(dataRequest);

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
		int total = systemFuncDataObjService.getSystemFuncDataObjCountByQueryCriteria(query);
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
			List<SystemFuncDataObj> list = systemFuncDataObjService.getSystemFuncDataObjsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SystemFuncDataObj systemFuncDataObj : list) {
					JSONObject rowJSON = systemFuncDataObj.toJsonObject();
					rowJSON.put("id", systemFuncDataObj.getSysDataObjId());
					rowJSON.put("systemFuncDataObjId", systemFuncDataObj.getSysDataObjId());
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
 

		return new ModelAndView("/model/systemFuncDataObj/list", modelMap);
	}

}
