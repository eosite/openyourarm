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

@Controller("/form/formTaskmain")
@RequestMapping("/form/formTaskmain")
public class FormTaskmainController {
	protected static final Log logger = LogFactory.getLog(FormTaskmainController.class);

	protected FormTaskmainService formTaskmainService;

	public FormTaskmainController() {

	}

        @javax.annotation.Resource(name = "com.glaf.form.core.service.formTaskmainService")
	public void setFormTaskmainService(FormTaskmainService formTaskmainService) {
		this.formTaskmainService = formTaskmainService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		FormTaskmain formTaskmain = new FormTaskmain();
		Tools.populate(formTaskmain, params);

                formTaskmain.setPlanId(RequestUtils.getLong(request, "planId"));
                formTaskmain.setDefinedId(request.getParameter("definedId"));
                formTaskmain.setProcessId(request.getParameter("processId"));
                formTaskmain.setVariableVal(request.getParameter("variableVal"));
                formTaskmain.setName(request.getParameter("name"));
                formTaskmain.setStatus(RequestUtils.getInt(request, "status"));
                formTaskmain.setCreateBy(request.getParameter("createBy"));
                formTaskmain.setCreateDate(RequestUtils.getDate(request, "createDate"));
                formTaskmain.setUpdateBy(request.getParameter("updateBy"));
                formTaskmain.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		
		//formTaskmain.setCreateBy(actorId);
         
		formTaskmainService.save(formTaskmain);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveFormTaskmain")
	public byte[] saveFormTaskmain(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTaskmain formTaskmain = new FormTaskmain();
		try {
		    Tools.populate(formTaskmain, params);
                    formTaskmain.setPlanId(RequestUtils.getLong(request, "planId"));
                    formTaskmain.setDefinedId(request.getParameter("definedId"));
                    formTaskmain.setProcessId(request.getParameter("processId"));
                    formTaskmain.setVariableVal(request.getParameter("variableVal"));
                    formTaskmain.setName(request.getParameter("name"));
                    formTaskmain.setStatus(RequestUtils.getInt(request, "status"));
                    formTaskmain.setCreateBy(request.getParameter("createBy"));
                    formTaskmain.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    formTaskmain.setUpdateBy(request.getParameter("updateBy"));
                    formTaskmain.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		    //formTaskmain.setCreateBy(actorId);
		    this.formTaskmainService.save(formTaskmain);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormTaskmain saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		FormTaskmain formTaskmain = new FormTaskmain();
		try {
		    Tools.populate(formTaskmain, model);
                    formTaskmain.setPlanId(ParamUtils.getLong(model, "planId"));
                    formTaskmain.setDefinedId(ParamUtils.getString(model, "definedId"));
                    formTaskmain.setProcessId(ParamUtils.getString(model, "processId"));
                    formTaskmain.setVariableVal(ParamUtils.getString(model, "variableVal"));
                    formTaskmain.setName(ParamUtils.getString(model, "name"));
                    formTaskmain.setStatus(ParamUtils.getInt(model, "status"));
                    formTaskmain.setCreateBy(ParamUtils.getString(model, "createBy"));
                    formTaskmain.setCreateDate(ParamUtils.getDate(model, "createDate"));
                    formTaskmain.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    formTaskmain.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
		    formTaskmain.setCreateBy(actorId);
		    this.formTaskmainService.save(formTaskmain);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return formTaskmain;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                FormTaskmain formTaskmain = formTaskmainService.getFormTaskmain(RequestUtils.getLong(request, "id"));

		Tools.populate(formTaskmain, params);

                formTaskmain.setPlanId(RequestUtils.getLong(request, "planId"));
                formTaskmain.setDefinedId(request.getParameter("definedId"));
                formTaskmain.setProcessId(request.getParameter("processId"));
                formTaskmain.setVariableVal(request.getParameter("variableVal"));
                formTaskmain.setName(request.getParameter("name"));
                formTaskmain.setStatus(RequestUtils.getInt(request, "status"));
                formTaskmain.setCreateBy(request.getParameter("createBy"));
                formTaskmain.setCreateDate(RequestUtils.getDate(request, "createDate"));
                formTaskmain.setUpdateBy(request.getParameter("updateBy"));
                formTaskmain.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
	
		formTaskmainService.save(formTaskmain);   

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
					FormTaskmain formTaskmain = formTaskmainService.getFormTaskmain(Long.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (formTaskmain != null && (StringUtils.equals(formTaskmain.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//formTaskmain.setDeleteFlag(1);
						formTaskmainService.save(formTaskmain);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormTaskmain formTaskmain = formTaskmainService
					.getFormTaskmain(Long.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (formTaskmain != null && ( StringUtils.equals(formTaskmain.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//formTaskmain.setDeleteFlag(1);
				formTaskmainService.save(formTaskmain);
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
                FormTaskmain formTaskmain = formTaskmainService.getFormTaskmain(RequestUtils.getLong(request, "id"));
		if(formTaskmain != null) {
		    request.setAttribute("formTaskmain", formTaskmain);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formTaskmain.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/formTaskmain/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                FormTaskmain formTaskmain = formTaskmainService.getFormTaskmain(RequestUtils.getLong(request, "id"));
		request.setAttribute("formTaskmain", formTaskmain);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formTaskmain.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/formTaskmain/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formTaskmain.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/formTaskmain/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTaskmainQuery query = new FormTaskmainQuery();
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
		int total = formTaskmainService.getFormTaskmainCountByQueryCriteria(query);
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
			List<FormTaskmain> list = formTaskmainService.getFormTaskmainsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormTaskmain formTaskmain : list) {
					JSONObject rowJSON = formTaskmain.toJsonObject();
					rowJSON.put("id", formTaskmain.getId());
					rowJSON.put("rowId", formTaskmain.getId());
					rowJSON.put("formTaskmainId", formTaskmain.getId());
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
		FormTaskmainQuery query = new FormTaskmainQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormTaskmainDomainFactory.processDataRequest(dataRequest);

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
		int total = formTaskmainService.getFormTaskmainCountByQueryCriteria(query);
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
			List<FormTaskmain> list = formTaskmainService.getFormTaskmainsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (FormTaskmain formTaskmain : list) {
					JSONObject rowJSON = formTaskmain.toJsonObject();
					rowJSON.put("id", formTaskmain.getId());
					rowJSON.put("formTaskmainId", formTaskmain.getId());
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
 

		return new ModelAndView("/form/formTaskmain/list", modelMap);
	}

}
