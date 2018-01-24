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

@Controller("/teim/dataimp")
@RequestMapping("/teim/dataimp")
public class EimServerDataImpController {
	protected static final Log logger = LogFactory.getLog(EimServerDataImpController.class);

	protected EimServerDataImpService eimServerDataImpService;

	public EimServerDataImpController() {

	}

        @javax.annotation.Resource(name = "com.glaf.teim.service.eimServerDataImpService")
	public void setEimServerDataImpService(EimServerDataImpService eimServerDataImpService) {
		this.eimServerDataImpService = eimServerDataImpService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		EimServerDataImp eimServerDataImp = new EimServerDataImp();
		Tools.populate(eimServerDataImp, params);

                eimServerDataImp.setName(request.getParameter("name"));
                eimServerDataImp.setAppId(request.getParameter("appId"));
                eimServerDataImp.setTmpId(request.getParameter("tmpId"));
                eimServerDataImp.setEmptyTable(RequestUtils.getInt(request, "emptyTable"));
                eimServerDataImp.setIncrementFlag(RequestUtils.getInt(request, "incrementFlag"));
                eimServerDataImp.setTargetDatabase(request.getParameter("targetDatabase"));
                eimServerDataImp.setTargetTable(request.getParameter("targetTable"));
                eimServerDataImp.setCreateBy(request.getParameter("createBy"));
                eimServerDataImp.setCreateTime(RequestUtils.getDate(request, "createTime"));
                eimServerDataImp.setUpdateBy(request.getParameter("updateBy"));
                eimServerDataImp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                eimServerDataImp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		
		//eimServerDataImp.setCreateBy(actorId);
         
		eimServerDataImpService.save(eimServerDataImp);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveEimServerDataImp")
	public byte[] saveEimServerDataImp(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerDataImp eimServerDataImp = new EimServerDataImp();
		try {
		    Tools.populate(eimServerDataImp, params);
                    eimServerDataImp.setName(request.getParameter("name"));
                    eimServerDataImp.setAppId(request.getParameter("appId"));
                    eimServerDataImp.setTmpId(request.getParameter("tmpId"));
                    eimServerDataImp.setEmptyTable(RequestUtils.getInt(request, "emptyTable"));
                    eimServerDataImp.setIncrementFlag(RequestUtils.getInt(request, "incrementFlag"));
                    eimServerDataImp.setTargetDatabase(request.getParameter("targetDatabase"));
                    eimServerDataImp.setTargetTable(request.getParameter("targetTable"));
                    eimServerDataImp.setCreateBy(request.getParameter("createBy"));
                    eimServerDataImp.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    eimServerDataImp.setUpdateBy(request.getParameter("updateBy"));
                    eimServerDataImp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                    eimServerDataImp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		    //eimServerDataImp.setCreateBy(actorId);
		    this.eimServerDataImpService.save(eimServerDataImp);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody EimServerDataImp saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		EimServerDataImp eimServerDataImp = new EimServerDataImp();
		try {
		    Tools.populate(eimServerDataImp, model);
                    eimServerDataImp.setName(ParamUtils.getString(model, "name"));
                    eimServerDataImp.setAppId(ParamUtils.getString(model, "appId"));
                    eimServerDataImp.setTmpId(ParamUtils.getString(model, "tmpId"));
                    eimServerDataImp.setEmptyTable(ParamUtils.getInt(model, "emptyTable"));
                    eimServerDataImp.setIncrementFlag(ParamUtils.getInt(model, "incrementFlag"));
                    eimServerDataImp.setTargetDatabase(ParamUtils.getString(model, "targetDatabase"));
                    eimServerDataImp.setTargetTable(ParamUtils.getString(model, "targetTable"));
                    eimServerDataImp.setCreateBy(ParamUtils.getString(model, "createBy"));
                    eimServerDataImp.setCreateTime(ParamUtils.getDate(model, "createTime"));
                    eimServerDataImp.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    eimServerDataImp.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
                    eimServerDataImp.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
		    eimServerDataImp.setCreateBy(actorId);
		    this.eimServerDataImpService.save(eimServerDataImp);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return eimServerDataImp;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                EimServerDataImp eimServerDataImp = eimServerDataImpService.getEimServerDataImp(request.getParameter("id"));

		Tools.populate(eimServerDataImp, params);

                eimServerDataImp.setName(request.getParameter("name"));
                eimServerDataImp.setAppId(request.getParameter("appId"));
                eimServerDataImp.setTmpId(request.getParameter("tmpId"));
                eimServerDataImp.setEmptyTable(RequestUtils.getInt(request, "emptyTable"));
                eimServerDataImp.setIncrementFlag(RequestUtils.getInt(request, "incrementFlag"));
                eimServerDataImp.setTargetDatabase(request.getParameter("targetDatabase"));
                eimServerDataImp.setTargetTable(request.getParameter("targetTable"));
                eimServerDataImp.setCreateBy(request.getParameter("createBy"));
                eimServerDataImp.setCreateTime(RequestUtils.getDate(request, "createTime"));
                eimServerDataImp.setUpdateBy(request.getParameter("updateBy"));
                eimServerDataImp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                eimServerDataImp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
	
		eimServerDataImpService.save(eimServerDataImp);   

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
					EimServerDataImp eimServerDataImp = eimServerDataImpService.getEimServerDataImp(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (eimServerDataImp != null && (StringUtils.equals(eimServerDataImp.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//eimServerDataImp.setDeleteFlag(1);
						eimServerDataImpService.save(eimServerDataImp);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			EimServerDataImp eimServerDataImp = eimServerDataImpService
					.getEimServerDataImp(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (eimServerDataImp != null && ( StringUtils.equals(eimServerDataImp.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//eimServerDataImp.setDeleteFlag(1);
				eimServerDataImpService.save(eimServerDataImp);
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
                EimServerDataImp eimServerDataImp = eimServerDataImpService.getEimServerDataImp(request.getParameter("id"));
		if(eimServerDataImp != null) {
		    request.setAttribute("eimServerDataImp", eimServerDataImp);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("eimServerDataImp.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/teim/dataimp/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                EimServerDataImp eimServerDataImp = eimServerDataImpService.getEimServerDataImp(request.getParameter("id"));
		request.setAttribute("eimServerDataImp", eimServerDataImp);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("eimServerDataImp.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/teim/dataimp/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("eimServerDataImp.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/teim/dataimp/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerDataImpQuery query = new EimServerDataImpQuery();
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
		int total = eimServerDataImpService.getEimServerDataImpCountByQueryCriteria(query);
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
			List<EimServerDataImp> list = eimServerDataImpService.getEimServerDataImpsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EimServerDataImp eimServerDataImp : list) {
					JSONObject rowJSON = eimServerDataImp.toJsonObject();
					rowJSON.put("id", eimServerDataImp.getId());
					rowJSON.put("rowId", eimServerDataImp.getId());
					rowJSON.put("eimServerDataImpId", eimServerDataImp.getId());
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
		EimServerDataImpQuery query = new EimServerDataImpQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		EimServerDataImpDomainFactory.processDataRequest(dataRequest);

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
		int total = eimServerDataImpService.getEimServerDataImpCountByQueryCriteria(query);
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
			List<EimServerDataImp> list = eimServerDataImpService.getEimServerDataImpsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EimServerDataImp eimServerDataImp : list) {
					JSONObject rowJSON = eimServerDataImp.toJsonObject();
					rowJSON.put("id", eimServerDataImp.getId());
					rowJSON.put("eimServerDataImpId", eimServerDataImp.getId());
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
 

		return new ModelAndView("/teim/dataimp/list", modelMap);
	}

}