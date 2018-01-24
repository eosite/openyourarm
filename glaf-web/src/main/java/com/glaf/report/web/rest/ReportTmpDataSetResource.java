package com.glaf.report.web.rest;

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


import com.glaf.report.core.domain.ReportTmpDataSet;
import com.glaf.report.core.query.ReportTmpDataSetQuery;
import com.glaf.report.core.service.ReportTmpDataSetService;
import com.glaf.report.core.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/report/reportTmpDataSet")
public class ReportTmpDataSetResource {
	protected static final Log logger = LogFactory.getLog(ReportTmpDataSetResource.class);

	protected ReportTmpDataSetService reportTmpDataSetService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				reportTmpDataSetService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                reportTmpDataSetService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSetQuery query = new ReportTmpDataSetQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		ReportTmpDataSetDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTmpDataSetService.getReportTmpDataSetCountByQueryCriteria(query);
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
			List<ReportTmpDataSet> list = reportTmpDataSetService.getReportTmpDataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReportTmpDataSet reportTmpDataSet : list) {
					JSONObject rowJSON = reportTmpDataSet.toJsonObject();
					rowJSON.put("id", reportTmpDataSet.getId());
					rowJSON.put("reportTmpDataSetId", reportTmpDataSet.getId());
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
		ReportTmpDataSetQuery query = new ReportTmpDataSetQuery();
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
		int total = reportTmpDataSetService.getReportTmpDataSetCountByQueryCriteria(query);
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
			List<ReportTmpDataSet> list = reportTmpDataSetService.getReportTmpDataSetsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (ReportTmpDataSet reportTmpDataSet : list) {
					JSONObject rowJSON = reportTmpDataSet.toJsonObject();
					rowJSON.put("id", reportTmpDataSet.getId());
					rowJSON.put("reportTmpDataSetId", reportTmpDataSet.getId());
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
	@Path("/saveReportTmpDataSet")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveReportTmpDataSet(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTmpDataSet reportTmpDataSet = new ReportTmpDataSet();
		try {
		    Tools.populate(reportTmpDataSet, params);

                    reportTmpDataSet.setTemplateId(request.getParameter("templateId"));
                    reportTmpDataSet.setCode(request.getParameter("code"));
                    reportTmpDataSet.setName(request.getParameter("name"));
                    reportTmpDataSet.setTitle(request.getParameter("title"));
                    reportTmpDataSet.setType(request.getParameter("tYPE"));
                    reportTmpDataSet.setCreator(request.getParameter("creator"));
                    reportTmpDataSet.setCreateDatetime(RequestUtils.getDate(request, "createDatetime"));
                    reportTmpDataSet.setModifier(request.getParameter("modifier"));
                    reportTmpDataSet.setModifyDatetime(RequestUtils.getDate(request, "modifyDatetime"));

		    this.reportTmpDataSetService.save(reportTmpDataSet);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpDataSetService")
	public void setReportTmpDataSetService(ReportTmpDataSetService reportTmpDataSetService) {
		this.reportTmpDataSetService = reportTmpDataSetService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ReportTmpDataSet reportTmpDataSet = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  reportTmpDataSet = reportTmpDataSetService.getReportTmpDataSet(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (reportTmpDataSet != null) {
		    result =  reportTmpDataSet.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", reportTmpDataSet.getId());
		    result.put("reportTmpDataSetId", reportTmpDataSet.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
