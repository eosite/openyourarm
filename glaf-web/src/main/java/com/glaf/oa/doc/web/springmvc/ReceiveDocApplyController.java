package com.glaf.oa.doc.web.springmvc;

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
 
import com.glaf.oa.doc.domain.*;
import com.glaf.oa.doc.query.*;
import com.glaf.oa.doc.service.*;
import com.glaf.oa.doc.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/oa/doc/receiveDocApply")
@RequestMapping("/oa/doc/receiveDocApply")
public class ReceiveDocApplyController {
	protected static final Log logger = LogFactory.getLog(ReceiveDocApplyController.class);

	protected ReceiveDocApplyService receiveDocApplyService;

	public ReceiveDocApplyController() {

	}

        @javax.annotation.Resource(name = "com.glaf.oa.doc.service.receiveDocApplyService")
	public void setReceiveDocApplyService(ReceiveDocApplyService receiveDocApplyService) {
		this.receiveDocApplyService = receiveDocApplyService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		ReceiveDocApply receiveDocApply = new ReceiveDocApply();
		Tools.populate(receiveDocApply, params);

                receiveDocApply.setSubject(request.getParameter("subject"));
                receiveDocApply.setSecurityLevel(RequestUtils.getInt(request, "securityLevel"));
                receiveDocApply.setUrgencyLevel(RequestUtils.getInt(request, "urgencyLevel"));
                receiveDocApply.setReceiveDocTime(RequestUtils.getDate(request, "receiveDocTime"));
                receiveDocApply.setDocType(request.getParameter("docType"));
                receiveDocApply.setFromCompany(request.getParameter("fromCompany"));
                receiveDocApply.setSerialNumber(request.getParameter("serialNumber"));
                receiveDocApply.setFromDocNo(request.getParameter("fromDocNo"));
                receiveDocApply.setReceiveDocNo(request.getParameter("receiveDocNo"));
                receiveDocApply.setDistributeCompany(request.getParameter("distributeCompany"));
                receiveDocApply.setNibanOption(request.getParameter("nibanOption"));
                receiveDocApply.setLeadOption(request.getParameter("leadOption"));
                receiveDocApply.setChengbanOption(request.getParameter("chengbanOption"));
                receiveDocApply.setRemark(request.getParameter("remark"));
                receiveDocApply.setStatus(RequestUtils.getInt(request, "status"));
                receiveDocApply.setCreateDate(RequestUtils.getDate(request, "createDate"));
                receiveDocApply.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
                receiveDocApply.setCreateBy(request.getParameter("createBy"));
                receiveDocApply.setUpdateBy(request.getParameter("updateBy"));
		
		//receiveDocApply.setCreateBy(actorId);
         
		receiveDocApplyService.save(receiveDocApply);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveReceiveDocApply")
	public byte[] saveReceiveDocApply(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReceiveDocApply receiveDocApply = new ReceiveDocApply();
		try {
		    Tools.populate(receiveDocApply, params);
                    receiveDocApply.setSubject(request.getParameter("subject"));
                    receiveDocApply.setSecurityLevel(RequestUtils.getInt(request, "securityLevel"));
                    receiveDocApply.setUrgencyLevel(RequestUtils.getInt(request, "urgencyLevel"));
                    receiveDocApply.setReceiveDocTime(RequestUtils.getDate(request, "receiveDocTime"));
                    receiveDocApply.setDocType(request.getParameter("docType"));
                    receiveDocApply.setFromCompany(request.getParameter("fromCompany"));
                    receiveDocApply.setSerialNumber(request.getParameter("serialNumber"));
                    receiveDocApply.setFromDocNo(request.getParameter("fromDocNo"));
                    receiveDocApply.setReceiveDocNo(request.getParameter("receiveDocNo"));
                    receiveDocApply.setDistributeCompany(request.getParameter("distributeCompany"));
                    receiveDocApply.setNibanOption(request.getParameter("nibanOption"));
                    receiveDocApply.setLeadOption(request.getParameter("leadOption"));
                    receiveDocApply.setChengbanOption(request.getParameter("chengbanOption"));
                    receiveDocApply.setRemark(request.getParameter("remark"));
                    receiveDocApply.setStatus(RequestUtils.getInt(request, "status"));
                    receiveDocApply.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    receiveDocApply.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
                    receiveDocApply.setCreateBy(request.getParameter("createBy"));
                    receiveDocApply.setUpdateBy(request.getParameter("updateBy"));
		    //receiveDocApply.setCreateBy(actorId);
		    this.receiveDocApplyService.save(receiveDocApply);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ReceiveDocApply saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		ReceiveDocApply receiveDocApply = new ReceiveDocApply();
		try {
		    Tools.populate(receiveDocApply, model);
                    receiveDocApply.setSubject(ParamUtils.getString(model, "subject"));
                    receiveDocApply.setSecurityLevel(ParamUtils.getInt(model, "securityLevel"));
                    receiveDocApply.setUrgencyLevel(ParamUtils.getInt(model, "urgencyLevel"));
                    receiveDocApply.setReceiveDocTime(ParamUtils.getDate(model, "receiveDocTime"));
                    receiveDocApply.setDocType(ParamUtils.getString(model, "docType"));
                    receiveDocApply.setFromCompany(ParamUtils.getString(model, "fromCompany"));
                    receiveDocApply.setSerialNumber(ParamUtils.getString(model, "serialNumber"));
                    receiveDocApply.setFromDocNo(ParamUtils.getString(model, "fromDocNo"));
                    receiveDocApply.setReceiveDocNo(ParamUtils.getString(model, "receiveDocNo"));
                    receiveDocApply.setDistributeCompany(ParamUtils.getString(model, "distributeCompany"));
                    receiveDocApply.setNibanOption(ParamUtils.getString(model, "nibanOption"));
                    receiveDocApply.setLeadOption(ParamUtils.getString(model, "leadOption"));
                    receiveDocApply.setChengbanOption(ParamUtils.getString(model, "chengbanOption"));
                    receiveDocApply.setRemark(ParamUtils.getString(model, "remark"));
                    receiveDocApply.setStatus(ParamUtils.getInt(model, "status"));
                    receiveDocApply.setCreateDate(ParamUtils.getDate(model, "createDate"));
                    receiveDocApply.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
                    receiveDocApply.setCreateBy(ParamUtils.getString(model, "createBy"));
                    receiveDocApply.setUpdateBy(ParamUtils.getString(model, "updateBy"));
		    receiveDocApply.setCreateBy(actorId);
		    this.receiveDocApplyService.save(receiveDocApply);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return receiveDocApply;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                ReceiveDocApply receiveDocApply = receiveDocApplyService.getReceiveDocApply(RequestUtils.getInt(request, "id"));

		Tools.populate(receiveDocApply, params);

                receiveDocApply.setSubject(request.getParameter("subject"));
                receiveDocApply.setSecurityLevel(RequestUtils.getInt(request, "securityLevel"));
                receiveDocApply.setUrgencyLevel(RequestUtils.getInt(request, "urgencyLevel"));
                receiveDocApply.setReceiveDocTime(RequestUtils.getDate(request, "receiveDocTime"));
                receiveDocApply.setDocType(request.getParameter("docType"));
                receiveDocApply.setFromCompany(request.getParameter("fromCompany"));
                receiveDocApply.setSerialNumber(request.getParameter("serialNumber"));
                receiveDocApply.setFromDocNo(request.getParameter("fromDocNo"));
                receiveDocApply.setReceiveDocNo(request.getParameter("receiveDocNo"));
                receiveDocApply.setDistributeCompany(request.getParameter("distributeCompany"));
                receiveDocApply.setNibanOption(request.getParameter("nibanOption"));
                receiveDocApply.setLeadOption(request.getParameter("leadOption"));
                receiveDocApply.setChengbanOption(request.getParameter("chengbanOption"));
                receiveDocApply.setRemark(request.getParameter("remark"));
                receiveDocApply.setStatus(RequestUtils.getInt(request, "status"));
                receiveDocApply.setCreateDate(RequestUtils.getDate(request, "createDate"));
                receiveDocApply.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
                receiveDocApply.setCreateBy(request.getParameter("createBy"));
                receiveDocApply.setUpdateBy(request.getParameter("updateBy"));
	
		receiveDocApplyService.save(receiveDocApply);   

		return this.list(request, modelMap);
	}

    @ResponseBody
    @RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Integer id = RequestUtils.getInteger(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					ReceiveDocApply receiveDocApply = receiveDocApplyService.getReceiveDocApply(Integer.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (receiveDocApply != null && (StringUtils.equals(receiveDocApply.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//receiveDocApply.setDeleteFlag(1);
						receiveDocApplyService.save(receiveDocApply);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			ReceiveDocApply receiveDocApply = receiveDocApplyService
					.getReceiveDocApply(Integer.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (receiveDocApply != null && ( StringUtils.equals(receiveDocApply.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//receiveDocApply.setDeleteFlag(1);
				receiveDocApplyService.save(receiveDocApply);
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
                ReceiveDocApply receiveDocApply = receiveDocApplyService.getReceiveDocApply(RequestUtils.getInt(request, "id"));
		if(receiveDocApply != null) {
		    request.setAttribute("receiveDocApply", receiveDocApply);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("receiveDocApply.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/oa/doc/receiveDocApply/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                ReceiveDocApply receiveDocApply = receiveDocApplyService.getReceiveDocApply(RequestUtils.getInt(request, "id"));
		request.setAttribute("receiveDocApply", receiveDocApply);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("receiveDocApply.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/oa/doc/receiveDocApply/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("receiveDocApply.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/oa/doc/receiveDocApply/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReceiveDocApplyQuery query = new ReceiveDocApplyQuery();
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
		int total = receiveDocApplyService.getReceiveDocApplyCountByQueryCriteria(query);
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
			List<ReceiveDocApply> list = receiveDocApplyService.getReceiveDocApplysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReceiveDocApply receiveDocApply : list) {
					JSONObject rowJSON = receiveDocApply.toJsonObject();
					rowJSON.put("id", receiveDocApply.getId());
					rowJSON.put("rowId", receiveDocApply.getId());
					rowJSON.put("receiveDocApplyId", receiveDocApply.getId());
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
		ReceiveDocApplyQuery query = new ReceiveDocApplyQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ReceiveDocApplyDomainFactory.processDataRequest(dataRequest);

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
		int total = receiveDocApplyService.getReceiveDocApplyCountByQueryCriteria(query);
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
			List<ReceiveDocApply> list = receiveDocApplyService.getReceiveDocApplysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReceiveDocApply receiveDocApply : list) {
					JSONObject rowJSON = receiveDocApply.toJsonObject();
					rowJSON.put("id", receiveDocApply.getId());
					rowJSON.put("receiveDocApplyId", receiveDocApply.getId());
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
 

		return new ModelAndView("/oa/doc/receiveDocApply/list", modelMap);
	}

}
