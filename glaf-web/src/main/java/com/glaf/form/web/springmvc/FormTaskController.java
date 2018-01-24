package com.glaf.form.web.springmvc;

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
 
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.*;
import com.glaf.form.core.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/formTask")
@RequestMapping("/form/formTask")
public class FormTaskController {
	protected static final Log logger = LogFactory.getLog(FormTaskController.class);

	protected FormTaskService formTaskService;

	public FormTaskController() {

	}

        @javax.annotation.Resource(name = "com.glaf.form.core.service.formTaskService")
	public void setFormTaskService(FormTaskService formTaskService) {
		this.formTaskService = formTaskService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		FormTask formTask = new FormTask();
		Tools.populate(formTask, params);

                formTask.setPageId(request.getParameter("pageId"));
                formTask.setTmId(RequestUtils.getLong(request, "tmId"));
                formTask.setTableName(request.getParameter("tableName"));
                formTask.setIdValue(request.getParameter("idValue"));
                formTask.setStatus(RequestUtils.getInt(request, "status"));
                formTask.setCreateBy(request.getParameter("createBy"));
                formTask.setCreateDate(RequestUtils.getDate(request, "createDate"));
                formTask.setUpdateBy(request.getParameter("updateBy"));
                formTask.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		
		//formTask.setCreateBy(actorId);
         
		formTaskService.save(formTask);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveFormTask")
	public byte[] saveFormTask(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTask formTask = new FormTask();
		try {
		    Tools.populate(formTask, params);
                    formTask.setPageId(request.getParameter("pageId"));
                    formTask.setTmId(RequestUtils.getLong(request, "tmId"));
                    formTask.setTableName(request.getParameter("tableName"));
                    formTask.setIdValue(request.getParameter("idValue"));
                    formTask.setStatus(RequestUtils.getInt(request, "status"));
                    formTask.setCreateBy(request.getParameter("createBy"));
                    formTask.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    formTask.setUpdateBy(request.getParameter("updateBy"));
                    formTask.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		    //formTask.setCreateBy(actorId);
		    this.formTaskService.save(formTask);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormTask saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		FormTask formTask = new FormTask();
		try {
		    Tools.populate(formTask, model);
                    formTask.setPageId(ParamUtils.getString(model, "pageId"));
                    formTask.setTmId(ParamUtils.getLong(model, "tmId"));
                    formTask.setTableName(ParamUtils.getString(model, "tableName"));
                    formTask.setIdValue(ParamUtils.getString(model, "idValue"));
                    formTask.setStatus(ParamUtils.getInt(model, "status"));
                    formTask.setCreateBy(ParamUtils.getString(model, "createBy"));
                    formTask.setCreateDate(ParamUtils.getDate(model, "createDate"));
                    formTask.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    formTask.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
		    formTask.setCreateBy(actorId);
		    this.formTaskService.save(formTask);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return formTask;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                FormTask formTask = formTaskService.getFormTask(RequestUtils.getLong(request, "id"));

		Tools.populate(formTask, params);

                formTask.setPageId(request.getParameter("pageId"));
                formTask.setTmId(RequestUtils.getLong(request, "tmId"));
                formTask.setTableName(request.getParameter("tableName"));
                formTask.setIdValue(request.getParameter("idValue"));
                formTask.setStatus(RequestUtils.getInt(request, "status"));
                formTask.setCreateBy(request.getParameter("createBy"));
                formTask.setCreateDate(RequestUtils.getDate(request, "createDate"));
                formTask.setUpdateBy(request.getParameter("updateBy"));
                formTask.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
	
		formTaskService.save(formTask);   

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
					FormTask formTask = formTaskService.getFormTask(Long.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (formTask != null && (StringUtils.equals(formTask.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//formTask.setDeleteFlag(1);
						formTaskService.save(formTask);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormTask formTask = formTaskService
					.getFormTask(Long.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (formTask != null && ( StringUtils.equals(formTask.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//formTask.setDeleteFlag(1);
				formTaskService.save(formTask);
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
                FormTask formTask = formTaskService.getFormTask(RequestUtils.getLong(request, "id"));
		if(formTask != null) {
		    request.setAttribute("formTask", formTask);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formTask.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/formTask/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                FormTask formTask = formTaskService.getFormTask(RequestUtils.getLong(request, "id"));
		request.setAttribute("formTask", formTask);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formTask.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/formTask/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formTask.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/formTask/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTaskQuery query = new FormTaskQuery();
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
		int total = formTaskService.getFormTaskCountByQueryCriteria(query);
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
			List<FormTask> list = formTaskService.getFormTasksByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormTask formTask : list) {
					JSONObject rowJSON = formTask.toJsonObject();
					rowJSON.put("id", formTask.getId());
					rowJSON.put("rowId", formTask.getId());
					rowJSON.put("formTaskId", formTask.getId());
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
		FormTaskQuery query = new FormTaskQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormTaskDomainFactory.processDataRequest(dataRequest);

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
		int total = formTaskService.getFormTaskCountByQueryCriteria(query);
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
			List<FormTask> list = formTaskService.getFormTasksByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormTask formTask : list) {
					JSONObject rowJSON = formTask.toJsonObject();
					rowJSON.put("id", formTask.getId());
					rowJSON.put("formTaskId", formTask.getId());
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
 

		return new ModelAndView("/form/formTask/list", modelMap);
	}

}
