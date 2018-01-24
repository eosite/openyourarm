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

@Controller("/transdata/emailSend")
@RequestMapping("/transdata/emailSend")
public class EmailSendController {
	protected static final Log logger = LogFactory.getLog(EmailSendController.class);

	protected EmailSendService emailSendService;

	public EmailSendController() {

	}

        @javax.annotation.Resource(name = "com.glaf.maildata.service.emailSendService")
	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		EmailSend emailSend = new EmailSend();
		Tools.populate(emailSend, params);

                emailSend.setMsgId(request.getParameter("msgId"));
                emailSend.setInReplyTo(request.getParameter("inReplyTo"));
                emailSend.setFrom(request.getParameter("from"));
                emailSend.setTo(request.getParameter("to"));
                emailSend.setCc(request.getParameter("cc"));
                emailSend.setDate(RequestUtils.getDate(request, "date"));
                emailSend.setSubject(request.getParameter("subject"));
                emailSend.setReplyTo(request.getParameter("replyTo"));
                emailSend.setIntFlag(RequestUtils.getInt(request, "intFlag"));
                emailSend.setEmail(request.getParameter("email"));
                emailSend.setFromSysId(request.getParameter("fromSysId"));
                emailSend.setIntAction(RequestUtils.getInt(request, "intAction"));
                emailSend.setIntOperat(RequestUtils.getInt(request, "intOperat"));
                emailSend.setListNo(RequestUtils.getInt(request, "listNo"));
                emailSend.setToSysId(request.getParameter("toSysId"));
		
		//emailSend.setCreateBy(actorId);
         
		emailSendService.save(emailSend);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveEmailSend")
	public byte[] saveEmailSend(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailSend emailSend = new EmailSend();
		try {
		    Tools.populate(emailSend, params);
                    emailSend.setMsgId(request.getParameter("msgId"));
                    emailSend.setInReplyTo(request.getParameter("inReplyTo"));
                    emailSend.setFrom(request.getParameter("from"));
                    emailSend.setTo(request.getParameter("to"));
                    emailSend.setCc(request.getParameter("cc"));
                    emailSend.setDate(RequestUtils.getDate(request, "date"));
                    emailSend.setSubject(request.getParameter("subject"));
                    emailSend.setReplyTo(request.getParameter("replyTo"));
                    emailSend.setIntFlag(RequestUtils.getInt(request, "intFlag"));
                    emailSend.setEmail(request.getParameter("email"));
                    emailSend.setFromSysId(request.getParameter("fromSysId"));
                    emailSend.setIntAction(RequestUtils.getInt(request, "intAction"));
                    emailSend.setIntOperat(RequestUtils.getInt(request, "intOperat"));
                    emailSend.setListNo(RequestUtils.getInt(request, "listNo"));
                    emailSend.setToSysId(request.getParameter("toSysId"));
		    //emailSend.setCreateBy(actorId);
		    this.emailSendService.save(emailSend);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody EmailSend saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		EmailSend emailSend = new EmailSend();
		try {
		    Tools.populate(emailSend, model);
                    emailSend.setMsgId(ParamUtils.getString(model, "msgId"));
                    emailSend.setInReplyTo(ParamUtils.getString(model, "inReplyTo"));
                    emailSend.setFrom(ParamUtils.getString(model, "from"));
                    emailSend.setTo(ParamUtils.getString(model, "to"));
                    emailSend.setCc(ParamUtils.getString(model, "cc"));
                    emailSend.setDate(ParamUtils.getDate(model, "date"));
                    emailSend.setSubject(ParamUtils.getString(model, "subject"));
                    emailSend.setReplyTo(ParamUtils.getString(model, "replyTo"));
                    emailSend.setIntFlag(ParamUtils.getInt(model, "intFlag"));
                    emailSend.setEmail(ParamUtils.getString(model, "email"));
                    emailSend.setFromSysId(ParamUtils.getString(model, "fromSysId"));
                    emailSend.setIntAction(ParamUtils.getInt(model, "intAction"));
                    emailSend.setIntOperat(ParamUtils.getInt(model, "intOperat"));
                    emailSend.setListNo(ParamUtils.getInt(model, "listNo"));
                    emailSend.setToSysId(ParamUtils.getString(model, "toSysId"));
		    this.emailSendService.save(emailSend);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return emailSend;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                EmailSend emailSend = emailSendService.getEmailSend(request.getParameter("id"));

		Tools.populate(emailSend, params);

                emailSend.setMsgId(request.getParameter("msgId"));
                emailSend.setInReplyTo(request.getParameter("inReplyTo"));
                emailSend.setFrom(request.getParameter("from"));
                emailSend.setTo(request.getParameter("to"));
                emailSend.setCc(request.getParameter("cc"));
                emailSend.setDate(RequestUtils.getDate(request, "date"));
                emailSend.setSubject(request.getParameter("subject"));
                emailSend.setReplyTo(request.getParameter("replyTo"));
                emailSend.setIntFlag(RequestUtils.getInt(request, "intFlag"));
                emailSend.setEmail(request.getParameter("email"));
                emailSend.setFromSysId(request.getParameter("fromSysId"));
                emailSend.setIntAction(RequestUtils.getInt(request, "intAction"));
                emailSend.setIntOperat(RequestUtils.getInt(request, "intOperat"));
                emailSend.setListNo(RequestUtils.getInt(request, "listNo"));
                emailSend.setToSysId(request.getParameter("toSysId"));
	
		emailSendService.save(emailSend);   

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
					EmailSend emailSend = emailSendService.getEmailSend(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (emailSend != null) {
						//emailSend.setDeleteFlag(1);
						emailSendService.save(emailSend);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			EmailSend emailSend = emailSendService
					.getEmailSend(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (emailSend != null) {
				//emailSend.setDeleteFlag(1);
				emailSendService.save(emailSend);
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
                EmailSend emailSend = emailSendService.getEmailSend(request.getParameter("id"));
		if(emailSend != null) {
		    request.setAttribute("emailSend", emailSend);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("emailSend.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/transdata/emailSend/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                EmailSend emailSend = emailSendService.getEmailSend(request.getParameter("id"));
		request.setAttribute("emailSend", emailSend);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("emailSend.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/transdata/emailSend/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("emailSend.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/transdata/emailSend/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailSendQuery query = new EmailSendQuery();
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
		int total = emailSendService.getEmailSendCountByQueryCriteria(query);
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
			List<EmailSend> list = emailSendService.getEmailSendsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EmailSend emailSend : list) {
					JSONObject rowJSON = emailSend.toJsonObject();
					rowJSON.put("id", emailSend.getId());
					rowJSON.put("rowId", emailSend.getId());
					rowJSON.put("emailSendId", emailSend.getId());
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
		EmailSendQuery query = new EmailSendQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		EmailSendDomainFactory.processDataRequest(dataRequest);

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
		int total = emailSendService.getEmailSendCountByQueryCriteria(query);
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
			List<EmailSend> list = emailSendService.getEmailSendsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (EmailSend emailSend : list) {
					JSONObject rowJSON = emailSend.toJsonObject();
					rowJSON.put("id", emailSend.getId());
					rowJSON.put("emailSendId", emailSend.getId());
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
 

		return new ModelAndView("/transdata/emailSend/list", modelMap);
	}

}
