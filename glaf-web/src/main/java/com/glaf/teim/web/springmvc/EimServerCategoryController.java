package com.glaf.teim.web.springmvc;

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
 
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;
import com.glaf.teim.service.*;
import com.glaf.teim.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/teim/category")
@RequestMapping("/teim/category")
public class EimServerCategoryController {
	protected static final Log logger = LogFactory.getLog(EimServerCategoryController.class);

	protected EimServerCategoryService eimServerCategoryService;

	public EimServerCategoryController() {

	}

        @javax.annotation.Resource(name = "com.glaf.teim.service.eimServerCategoryService")
	public void setEimServerCategoryService(EimServerCategoryService eimServerCategoryService) {
		this.eimServerCategoryService = eimServerCategoryService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		EimServerCategory eimServerCategory = new EimServerCategory();
		Tools.populate(eimServerCategory, params);

                eimServerCategory.setCode(request.getParameter("code"));
                eimServerCategory.setName(request.getParameter("name"));
                eimServerCategory.setTreeId(request.getParameter("treeId"));
                eimServerCategory.setParentId(RequestUtils.getLong(request, "parentId"));
                eimServerCategory.setCreateBy(request.getParameter("createBy"));
                eimServerCategory.setCreateTime(RequestUtils.getDate(request, "createTime"));
                eimServerCategory.setUpdateBy(request.getParameter("updateBy"));
                eimServerCategory.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		
		//eimServerCategory.setCreateBy(actorId);
         
		eimServerCategoryService.save(eimServerCategory);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveEimServerCategory")
	public byte[] saveEimServerCategory(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerCategory eimServerCategory = new EimServerCategory();
		try {
		    Tools.populate(eimServerCategory, params);
                    eimServerCategory.setCode(request.getParameter("code"));
                    eimServerCategory.setName(request.getParameter("name"));
                    eimServerCategory.setTreeId(request.getParameter("treeId"));
                    eimServerCategory.setParentId(RequestUtils.getLong(request, "parentId"));
                    eimServerCategory.setCreateBy(request.getParameter("createBy"));
                    eimServerCategory.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    eimServerCategory.setUpdateBy(request.getParameter("updateBy"));
                    eimServerCategory.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		    //eimServerCategory.setCreateBy(actorId);
		    this.eimServerCategoryService.save(eimServerCategory);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody EimServerCategory saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		EimServerCategory eimServerCategory = new EimServerCategory();
		try {
		    Tools.populate(eimServerCategory, model);
                    eimServerCategory.setCode(ParamUtils.getString(model, "code"));
                    eimServerCategory.setName(ParamUtils.getString(model, "name"));
                    eimServerCategory.setTreeId(ParamUtils.getString(model, "treeId"));
                    eimServerCategory.setParentId(ParamUtils.getLong(model, "parentId"));
                    eimServerCategory.setCreateBy(ParamUtils.getString(model, "createBy"));
                    eimServerCategory.setCreateTime(ParamUtils.getDate(model, "createTime"));
                    eimServerCategory.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    eimServerCategory.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
		    eimServerCategory.setCreateBy(actorId);
		    this.eimServerCategoryService.save(eimServerCategory);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return eimServerCategory;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                EimServerCategory eimServerCategory = eimServerCategoryService.getEimServerCategory(RequestUtils.getLong(request, "id"));

		Tools.populate(eimServerCategory, params);

                eimServerCategory.setCode(request.getParameter("code"));
                eimServerCategory.setName(request.getParameter("name"));
                eimServerCategory.setTreeId(request.getParameter("treeId"));
                eimServerCategory.setParentId(RequestUtils.getLong(request, "parentId"));
                eimServerCategory.setCreateBy(request.getParameter("createBy"));
                eimServerCategory.setCreateTime(RequestUtils.getDate(request, "createTime"));
                eimServerCategory.setUpdateBy(request.getParameter("updateBy"));
                eimServerCategory.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
	
		eimServerCategoryService.save(eimServerCategory);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					EimServerCategory eimServerCategory = eimServerCategoryService.getEimServerCategory(Long.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (eimServerCategory != null && (StringUtils.equals(eimServerCategory.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//eimServerCategory.setDeleteFlag(1);
						eimServerCategoryService.save(eimServerCategory);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			EimServerCategory eimServerCategory = eimServerCategoryService
					.getEimServerCategory(Long.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (eimServerCategory != null && ( StringUtils.equals(eimServerCategory.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//eimServerCategory.setDeleteFlag(1);
				eimServerCategoryService.save(eimServerCategory);
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
                EimServerCategory eimServerCategory = eimServerCategoryService.getEimServerCategory(RequestUtils.getLong(request, "id"));
		if(eimServerCategory != null) {
		    request.setAttribute("eimServerCategory", eimServerCategory);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("eimServerCategory.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/teim/category/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                EimServerCategory eimServerCategory = eimServerCategoryService.getEimServerCategory(RequestUtils.getLong(request, "id"));
		request.setAttribute("eimServerCategory", eimServerCategory);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("eimServerCategory.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/teim/category/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("eimServerCategory.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/teim/category/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerCategoryQuery query = new EimServerCategoryQuery();
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
		int total = eimServerCategoryService.getEimServerCategoryCountByQueryCriteria(query);
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
			List<EimServerCategory> list = eimServerCategoryService.getEimServerCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EimServerCategory eimServerCategory : list) {
					JSONObject rowJSON = eimServerCategory.toJsonObject();
					rowJSON.put("id", eimServerCategory.getId());
					rowJSON.put("rowId", eimServerCategory.getId());
					rowJSON.put("eimServerCategoryId", eimServerCategory.getId());
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
		EimServerCategoryQuery query = new EimServerCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		EimServerCategoryDomainFactory.processDataRequest(dataRequest);

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
		int total = eimServerCategoryService.getEimServerCategoryCountByQueryCriteria(query);
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
			List<EimServerCategory> list = eimServerCategoryService.getEimServerCategorysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EimServerCategory eimServerCategory : list) {
					JSONObject rowJSON = eimServerCategory.toJsonObject();
					rowJSON.put("id", eimServerCategory.getId());
					rowJSON.put("eimServerCategoryId", eimServerCategory.getId());
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
 

		return new ModelAndView("/teim/category/list", modelMap);
	}

}
