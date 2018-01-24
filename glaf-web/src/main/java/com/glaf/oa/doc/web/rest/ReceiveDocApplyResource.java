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


import com.glaf.oa.doc.domain.ReceiveDocApply;
import com.glaf.oa.doc.query.ReceiveDocApplyQuery;
import com.glaf.oa.doc.service.ReceiveDocApplyService;
import com.glaf.oa.doc.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/oa/doc/receiveDocApply")
public class ReceiveDocApplyResource {
	protected static final Log logger = LogFactory.getLog(ReceiveDocApplyResource.class);

	protected ReceiveDocApplyService receiveDocApplyService;

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
				receiveDocApplyService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                receiveDocApplyService.deleteById(RequestUtils.getInt(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReceiveDocApplyQuery query = new ReceiveDocApplyQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		ReceiveDocApplyDomainFactory.processDataRequest(dataRequest);

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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReceiveDocApplyQuery query = new ReceiveDocApplyQuery();
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

	@POST
	@Path("/saveReceiveDocApply")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveReceiveDocApply(@Context HttpServletRequest request) {
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

		    this.receiveDocApplyService.save(receiveDocApply);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.oa.doc.service.receiveDocApplyService")
	public void setReceiveDocApplyService(ReceiveDocApplyService receiveDocApplyService) {
		this.receiveDocApplyService = receiveDocApplyService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ReceiveDocApply receiveDocApply = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  receiveDocApply = receiveDocApplyService.getReceiveDocApply(RequestUtils.getInt(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (receiveDocApply != null) {
		    result =  receiveDocApply.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", receiveDocApply.getId());
		    result.put("receiveDocApplyId", receiveDocApply.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
