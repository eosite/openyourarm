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

@Controller("/core/formDatasetRelation")
@RequestMapping("/core/formDatasetRelation")
public class FormDatasetRelationController {
	protected static final Log logger = LogFactory.getLog(FormDatasetRelationController.class);

	protected FormDatasetRelationService formDatasetRelationService;

	public FormDatasetRelationController() {

	}

        @javax.annotation.Resource(name = "com.glaf.form.core.service.formDatasetRelationService")
	public void setFormDatasetRelationService(FormDatasetRelationService formDatasetRelationService) {
		this.formDatasetRelationService = formDatasetRelationService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
		Tools.populate(formDatasetRelation, params);

                formDatasetRelation.setPageId(request.getParameter("pageId"));
                formDatasetRelation.setWidgetId(request.getParameter("widgetId"));
                formDatasetRelation.setPid(request.getParameter("pid"));
                formDatasetRelation.setAttrName(request.getParameter("attrName"));
		
		//formDatasetRelation.setCreateBy(actorId);
         
		formDatasetRelationService.save(formDatasetRelation);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveFormDatasetRelation")
	public byte[] saveFormDatasetRelation(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
		try {
		    Tools.populate(formDatasetRelation, params);
                    formDatasetRelation.setPageId(request.getParameter("pageId"));
                    formDatasetRelation.setWidgetId(request.getParameter("widgetId"));
                    formDatasetRelation.setPid(request.getParameter("pid"));
                    formDatasetRelation.setAttrName(request.getParameter("attrName"));
		    //formDatasetRelation.setCreateBy(actorId);
		    this.formDatasetRelationService.save(formDatasetRelation);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormDatasetRelation saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
		try {
		    Tools.populate(formDatasetRelation, model);
                    formDatasetRelation.setPageId(ParamUtils.getString(model, "pageId"));
                    formDatasetRelation.setWidgetId(ParamUtils.getString(model, "widgetId"));
                    formDatasetRelation.setPid(ParamUtils.getString(model, "pid"));
                    formDatasetRelation.setAttrName(ParamUtils.getString(model, "attrName"));
		    //formDatasetRelation.setCreateBy(actorId);
		    this.formDatasetRelationService.save(formDatasetRelation);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return formDatasetRelation;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                FormDatasetRelation formDatasetRelation = formDatasetRelationService.getFormDatasetRelation(request.getParameter("id"));

		Tools.populate(formDatasetRelation, params);

                formDatasetRelation.setPageId(request.getParameter("pageId"));
                formDatasetRelation.setWidgetId(request.getParameter("widgetId"));
                formDatasetRelation.setPid(request.getParameter("pid"));
                formDatasetRelation.setAttrName(request.getParameter("attrName"));
	
		formDatasetRelationService.save(formDatasetRelation);   

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
					FormDatasetRelation formDatasetRelation = formDatasetRelationService.getFormDatasetRelation(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (formDatasetRelation != null /*&& (StringUtils.equals(formDatasetRelation.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())*/) {
						//formDatasetRelation.setDeleteFlag(1);
						formDatasetRelationService.save(formDatasetRelation);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormDatasetRelation formDatasetRelation = formDatasetRelationService
					.getFormDatasetRelation(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (formDatasetRelation != null /*&& ( StringUtils.equals(formDatasetRelation.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())*/) {
				//formDatasetRelation.setDeleteFlag(1);
				formDatasetRelationService.save(formDatasetRelation);
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
                FormDatasetRelation formDatasetRelation = formDatasetRelationService.getFormDatasetRelation(request.getParameter("id"));
		if(formDatasetRelation != null) {
		    request.setAttribute("formDatasetRelation", formDatasetRelation);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formDatasetRelation.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/core/formDatasetRelation/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                FormDatasetRelation formDatasetRelation = formDatasetRelationService.getFormDatasetRelation(request.getParameter("id"));
		request.setAttribute("formDatasetRelation", formDatasetRelation);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formDatasetRelation.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/core/formDatasetRelation/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formDatasetRelation.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/core/formDatasetRelation/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormDatasetRelationQuery query = new FormDatasetRelationQuery();
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
		int total = formDatasetRelationService.getFormDatasetRelationCountByQueryCriteria(query);
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
			List<FormDatasetRelation> list = formDatasetRelationService.getFormDatasetRelationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormDatasetRelation formDatasetRelation : list) {
					JSONObject rowJSON = formDatasetRelation.toJsonObject();
					rowJSON.put("id", formDatasetRelation.getId());
					rowJSON.put("rowId", formDatasetRelation.getId());
					rowJSON.put("formDatasetRelationId", formDatasetRelation.getId());
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
		FormDatasetRelationQuery query = new FormDatasetRelationQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormDatasetRelationDomainFactory.processDataRequest(dataRequest);

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
		int total = formDatasetRelationService.getFormDatasetRelationCountByQueryCriteria(query);
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
			List<FormDatasetRelation> list = formDatasetRelationService.getFormDatasetRelationsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormDatasetRelation formDatasetRelation : list) {
					JSONObject rowJSON = formDatasetRelation.toJsonObject();
					rowJSON.put("id", formDatasetRelation.getId());
					rowJSON.put("formDatasetRelationId", formDatasetRelation.getId());
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
 

		return new ModelAndView("/core/formDatasetRelation/list", modelMap);
	}

}
