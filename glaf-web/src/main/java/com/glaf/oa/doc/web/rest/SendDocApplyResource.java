package com.glaf.oa.doc.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;


import com.glaf.oa.doc.domain.SendDocApply;
import com.glaf.oa.doc.query.SendDocApplyQuery;
import com.glaf.oa.doc.service.SendDocApplyService;
import com.glaf.oa.doc.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/oa/doc/sendDocApply")
public class SendDocApplyResource {
	protected static final Log logger = LogFactory.getLog(SendDocApplyResource.class);

	protected SendDocApplyService sendDocApplyService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				sendDocApplyService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sendDocApplyService.deleteById(RequestUtils.getInt(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SendDocApplyQuery query = new SendDocApplyQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SendDocApplyDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
		    gridType = "kendoui";
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
		int total = sendDocApplyService.getSendDocApplyCountByQueryCriteria(query);
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
			List<SendDocApply> list = sendDocApplyService.getSendDocApplysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SendDocApply sendDocApply : list) {
					JSONObject rowJSON = sendDocApply.toJsonObject();
					rowJSON.put("id", sendDocApply.getId());
					rowJSON.put("sendDocApplyId", sendDocApply.getId());
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SendDocApplyQuery query = new SendDocApplyQuery();
		Tools.populate(query, params);

                String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = sendDocApplyService.getSendDocApplyCountByQueryCriteria(query);
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
			List<SendDocApply> list = sendDocApplyService.getSendDocApplysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SendDocApply sendDocApply : list) {
					JSONObject rowJSON = sendDocApply.toJsonObject();
					rowJSON.put("id", sendDocApply.getId());
					rowJSON.put("sendDocApplyId", sendDocApply.getId());
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

	@POST
	@Path("/saveSendDocApply")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSendDocApply(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SendDocApply sendDocApply = new SendDocApply();
		try {
		    Tools.populate(sendDocApply, params);

                    sendDocApply.setSubject(request.getParameter("subject"));
                    sendDocApply.setSecurityLevel(RequestUtils.getInt(request, "securityLevel"));
                    sendDocApply.setUrgencyLevel(RequestUtils.getInt(request, "urgencyLevel"));
                    sendDocApply.setDocNo(request.getParameter("docNo"));
                    sendDocApply.setDocType(request.getParameter("docType"));
                    sendDocApply.setDraftName(request.getParameter("draftName"));
                    sendDocApply.setDraftDate(RequestUtils.getDate(request, "draftDate"));
                    sendDocApply.setSendDocDate(RequestUtils.getDate(request, "sendDocDate"));
                    sendDocApply.setSerialNumber(request.getParameter("serialNumber"));
                    sendDocApply.setFromCompany(request.getParameter("fromCompany"));
                    sendDocApply.setKeywords(request.getParameter("keywords"));
                    sendDocApply.setDocToCompany(request.getParameter("docToCompany"));
                    sendDocApply.setDocCCCompany(request.getParameter("docCCCompany"));
                    sendDocApply.setCheckOpinion(request.getParameter("checkOpinion"));
                    sendDocApply.setCountersignOpinion(request.getParameter("countersignOpinion"));
                    sendDocApply.setSignAndIssueOpinion(request.getParameter("signAndIssueOpinion"));
                    sendDocApply.setRemark(request.getParameter("remark"));
                    sendDocApply.setStatus(RequestUtils.getInt(request, "status"));
                    sendDocApply.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    sendDocApply.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
                    sendDocApply.setCreateBy(request.getParameter("createBy"));
                    sendDocApply.setUpdateBy(request.getParameter("updateBy"));

		    this.sendDocApplyService.save(sendDocApply);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.oa.doc.service.sendDocApplyService")
	public void setSendDocApplyService(SendDocApplyService sendDocApplyService) {
		this.sendDocApplyService = sendDocApplyService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SendDocApply sendDocApply = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  sendDocApply = sendDocApplyService.getSendDocApply(RequestUtils.getInt(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (sendDocApply != null) {
		    result =  sendDocApply.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sendDocApply.getId());
		    result.put("sendDocApplyId", sendDocApply.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
