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

@Controller("/modeling/systemDef")
@RequestMapping("/modeling/systemDef")
public class SystemDefController {
	protected static final Log logger = LogFactory.getLog(SystemDefController.class);

	protected SystemDefService systemDefService;

	public SystemDefController() {

	}

        @javax.annotation.Resource(name = "com.glaf.model.service.systemDefService")
	public void setSystemDefService(SystemDefService systemDefService) {
		this.systemDefService = systemDefService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		SystemDef systemDef = new SystemDef();
		Tools.populate(systemDef, params);

                systemDef.setSysName(request.getParameter("sysName"));
                systemDef.setSysCode(request.getParameter("sysCode"));
                systemDef.setSysDesc(request.getParameter("sysDesc"));
                systemDef.setCreateBy(request.getParameter("createBy"));
                systemDef.setCreateTime(RequestUtils.getDate(request, "createTime"));
                systemDef.setUpdateBy(request.getParameter("updateBy"));
                systemDef.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                systemDef.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		
		//systemDef.setCreateBy(actorId);
         
		systemDefService.save(systemDef);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveSystemDef")
	public byte[] saveSystemDef(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDef systemDef = new SystemDef();
		try {
		    Tools.populate(systemDef, params);
            systemDef.setSysName(request.getParameter("sysName"));
            systemDef.setSysCode(request.getParameter("sysCode"));
            systemDef.setSysDesc(request.getParameter("sysDesc"));
            systemDef.setCreateBy(request.getParameter("createBy"));
            systemDef.setCreateTime(RequestUtils.getDate(request, "createTime"));
            systemDef.setUpdateBy(request.getParameter("updateBy"));
            systemDef.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
            systemDef.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		    //systemDef.setCreateBy(actorId);
		    this.systemDefService.save(systemDef);
		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SystemDef saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		SystemDef systemDef = new SystemDef();
		try {
		    Tools.populate(systemDef, model);
                    systemDef.setSysName(ParamUtils.getString(model, "sysName"));
                    systemDef.setSysCode(ParamUtils.getString(model, "sysCode"));
                    systemDef.setSysDesc(ParamUtils.getString(model, "sysDesc"));
                    systemDef.setCreateBy(ParamUtils.getString(model, "createBy"));
                    systemDef.setCreateTime(ParamUtils.getDate(model, "createTime"));
                    systemDef.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    systemDef.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
                    systemDef.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
		    systemDef.setCreateBy(actorId);
		    this.systemDefService.save(systemDef);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return systemDef;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                SystemDef systemDef = systemDefService.getSystemDef(RequestUtils.getString(request, "sysId"));

		Tools.populate(systemDef, params);

                systemDef.setSysName(request.getParameter("sysName"));
                systemDef.setSysCode(request.getParameter("sysCode"));
                systemDef.setSysDesc(request.getParameter("sysDesc"));
                systemDef.setCreateBy(request.getParameter("createBy"));
                systemDef.setCreateTime(RequestUtils.getDate(request, "createTime"));
                systemDef.setUpdateBy(request.getParameter("updateBy"));
                systemDef.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                systemDef.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
	
		systemDefService.save(systemDef);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String sysId = RequestUtils.getString(request, "sysId");
		String sysIds = request.getParameter("sysIds");
		if (StringUtils.isNotEmpty(sysIds)) {
			StringTokenizer token = new StringTokenizer(sysIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SystemDef systemDef = systemDefService.getSystemDef(x);
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (systemDef != null && (StringUtils.equals(systemDef.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//systemDef.setDeleteFlag(1);
						systemDefService.save(systemDef);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (sysId != null) {
			SystemDef systemDef = systemDefService
					.getSystemDef(sysId);
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (systemDef != null && ( StringUtils.equals(systemDef.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//systemDef.setDeleteFlag(1);
				systemDefService.save(systemDef);
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
                SystemDef systemDef = systemDefService.getSystemDef(RequestUtils.getString(request, "sysId"));
		if(systemDef != null) {
		    request.setAttribute("systemDef", systemDef);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("systemDef.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modeling/systemDef/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                SystemDef systemDef = systemDefService.getSystemDef(RequestUtils.getString(request, "sysId"));
		request.setAttribute("systemDef", systemDef);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("systemDef.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modeling/systemDef/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("systemDef.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modeling/systemDef/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDefQuery query = new SystemDefQuery();
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
		int total = systemDefService.getSystemDefCountByQueryCriteria(query);
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
			List<SystemDef> list = systemDefService.getSystemDefsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SystemDef systemDef : list) {
					JSONObject rowJSON = systemDef.toJsonObject();
					rowJSON.put("id", systemDef.getSysId());
					rowJSON.put("rowId", systemDef.getSysId());
					rowJSON.put("systemDefId", systemDef.getSysId());
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
		SystemDefQuery query = new SystemDefQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SystemDefDomainFactory.processDataRequest(dataRequest);

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
		int total = systemDefService.getSystemDefCountByQueryCriteria(query);
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
			List<SystemDef> list = systemDefService.getSystemDefsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SystemDef systemDef : list) {
					JSONObject rowJSON = systemDef.toJsonObject();
					rowJSON.put("id", systemDef.getSysId());
					rowJSON.put("systemDefId", systemDef.getSysId());
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
 

		return new ModelAndView("/modeling/systemDef/list", modelMap);
	}

}
