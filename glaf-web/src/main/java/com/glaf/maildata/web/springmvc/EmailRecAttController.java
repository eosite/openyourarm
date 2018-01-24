package com.glaf.maildata.web.springmvc;

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
 
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;
import com.glaf.maildata.service.*;
import com.glaf.maildata.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/transdata/emailRecAtt")
@RequestMapping("/transdata/emailRecAtt")
public class EmailRecAttController {
	protected static final Log logger = LogFactory.getLog(EmailRecAttController.class);

	protected EmailRecAttService emailRecAttService;

	public EmailRecAttController() {

	}

        @javax.annotation.Resource(name = "com.glaf.maildata.service.emailRecAttService")
	public void setEmailRecAttService(EmailRecAttService emailRecAttService) {
		this.emailRecAttService = emailRecAttService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		EmailRecAtt emailRecAtt = new EmailRecAtt();
		Tools.populate(emailRecAtt, params);

                emailRecAtt.setTopId(request.getParameter("topId"));
                emailRecAtt.setFileName(request.getParameter("fileName"));
                emailRecAtt.setCTime(RequestUtils.getDate(request, "cTime"));
                emailRecAtt.setFileSize(RequestUtils.getInt(request, "fileSize"));
		
		//emailRecAtt.setCreateBy(actorId);
         
		emailRecAttService.save(emailRecAtt);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveEmailRecAtt")
	public byte[] saveEmailRecAtt(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailRecAtt emailRecAtt = new EmailRecAtt();
		try {
		    Tools.populate(emailRecAtt, params);
                    emailRecAtt.setTopId(request.getParameter("topId"));
                    emailRecAtt.setFileName(request.getParameter("fileName"));
                    emailRecAtt.setCTime(RequestUtils.getDate(request, "cTime"));
                    emailRecAtt.setFileSize(RequestUtils.getInt(request, "fileSize"));
		    //emailRecAtt.setCreateBy(actorId);
		    this.emailRecAttService.save(emailRecAtt);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody EmailRecAtt saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		EmailRecAtt emailRecAtt = new EmailRecAtt();
		try {
		    Tools.populate(emailRecAtt, model);
                    emailRecAtt.setTopId(ParamUtils.getString(model, "topId"));
                    emailRecAtt.setFileName(ParamUtils.getString(model, "fileName"));
                    emailRecAtt.setCTime(ParamUtils.getDate(model, "cTime"));
                    emailRecAtt.setFileSize(ParamUtils.getInt(model, "fileSize"));
		    this.emailRecAttService.save(emailRecAtt);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return emailRecAtt;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                EmailRecAtt emailRecAtt = emailRecAttService.getEmailRecAtt(request.getParameter("fileId"));

		Tools.populate(emailRecAtt, params);

                emailRecAtt.setTopId(request.getParameter("topId"));
                emailRecAtt.setFileName(request.getParameter("fileName"));
                emailRecAtt.setCTime(RequestUtils.getDate(request, "cTime"));
                emailRecAtt.setFileSize(RequestUtils.getInt(request, "fileSize"));
	
		emailRecAttService.save(emailRecAtt);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String fileId = RequestUtils.getString(request, "fileId");
		String fileIds = request.getParameter("fileIds");
		if (StringUtils.isNotEmpty(fileIds)) {
			StringTokenizer token = new StringTokenizer(fileIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					EmailRecAtt emailRecAtt = emailRecAttService.getEmailRecAtt(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (emailRecAtt != null) {
						//emailRecAtt.setDeleteFlag(1);
						emailRecAttService.save(emailRecAtt);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (fileId != null) {
			EmailRecAtt emailRecAtt = emailRecAttService
					.getEmailRecAtt(String.valueOf(fileId));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (emailRecAtt != null) {
				//emailRecAtt.setDeleteFlag(1);
				emailRecAttService.save(emailRecAtt);
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
                EmailRecAtt emailRecAtt = emailRecAttService.getEmailRecAtt(request.getParameter("fileId"));
		if(emailRecAtt != null) {
		    request.setAttribute("emailRecAtt", emailRecAtt);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("emailRecAtt.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/transdata/emailRecAtt/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                EmailRecAtt emailRecAtt = emailRecAttService.getEmailRecAtt(request.getParameter("fileId"));
		request.setAttribute("emailRecAtt", emailRecAtt);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("emailRecAtt.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/transdata/emailRecAtt/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("emailRecAtt.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/transdata/emailRecAtt/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailRecAttQuery query = new EmailRecAttQuery();
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
		int total = emailRecAttService.getEmailRecAttCountByQueryCriteria(query);
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
			List<EmailRecAtt> list = emailRecAttService.getEmailRecAttsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EmailRecAtt emailRecAtt : list) {
					JSONObject rowJSON = emailRecAtt.toJsonObject();
					rowJSON.put("id", emailRecAtt.getFileId());
					rowJSON.put("rowId", emailRecAtt.getFileId());
					rowJSON.put("emailRecAttId", emailRecAtt.getFileId());
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
		EmailRecAttQuery query = new EmailRecAttQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		EmailRecAttDomainFactory.processDataRequest(dataRequest);

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
		int total = emailRecAttService.getEmailRecAttCountByQueryCriteria(query);
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
			List<EmailRecAtt> list = emailRecAttService.getEmailRecAttsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EmailRecAtt emailRecAtt : list) {
					JSONObject rowJSON = emailRecAtt.toJsonObject();
					rowJSON.put("id", emailRecAtt.getFileId());
					rowJSON.put("emailRecAttId", emailRecAtt.getFileId());
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
 

		return new ModelAndView("/transdata/emailRecAtt/list", modelMap);
	}

}
