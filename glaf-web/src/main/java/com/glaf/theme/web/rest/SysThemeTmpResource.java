package com.glaf.theme.web.rest;

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


import com.glaf.theme.domain.SysThemeTmp;
import com.glaf.theme.query.SysThemeTmpQuery;
import com.glaf.theme.service.SysThemeTmpService;
import com.glaf.theme.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/theme/sysThemeTmp")
public class SysThemeTmpResource {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpResource.class);

	protected SysThemeTmpService sysThemeTmpService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("themeTmpIds");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				sysThemeTmpService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sysThemeTmpService.deleteById(request.getParameter("themeTmpId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpQuery query = new SysThemeTmpQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SysThemeTmpDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpService.getSysThemeTmpCountByQueryCriteria(query);
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
			List<SysThemeTmp> list = sysThemeTmpService.getSysThemeTmpsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmp sysThemeTmp : list) {
					JSONObject rowJSON = sysThemeTmp.toJsonObject();
					rowJSON.put("id", sysThemeTmp.getThemeTmpId());
					rowJSON.put("sysThemeTmpId", sysThemeTmp.getThemeTmpId());
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
		SysThemeTmpQuery query = new SysThemeTmpQuery();
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
		int total = sysThemeTmpService.getSysThemeTmpCountByQueryCriteria(query);
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
			List<SysThemeTmp> list = sysThemeTmpService.getSysThemeTmpsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmp sysThemeTmp : list) {
					JSONObject rowJSON = sysThemeTmp.toJsonObject();
					rowJSON.put("id", sysThemeTmp.getThemeTmpId());
					rowJSON.put("sysThemeTmpId", sysThemeTmp.getThemeTmpId());
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
	@Path("/saveSysThemeTmp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysThemeTmp(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmp sysThemeTmp = new SysThemeTmp();
		try {
		    Tools.populate(sysThemeTmp, params);

                    sysThemeTmp.setThemeTmpName(request.getParameter("themeTmpName"));
                    sysThemeTmp.setThemeTmpCode(request.getParameter("themeTmpCode"));
                    sysThemeTmp.setUi(request.getParameter("ui"));
                    sysThemeTmp.setCreateBy(request.getParameter("createBy"));
                    sysThemeTmp.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    sysThemeTmp.setUpdateBy(request.getParameter("updateBy"));
                    sysThemeTmp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                    sysThemeTmp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
                    sysThemeTmp.setPublisher(request.getParameter("publisher"));
                    sysThemeTmp.setPublishTime(RequestUtils.getDate(request, "publishTime"));
                    sysThemeTmp.setStatus(RequestUtils.getInt(request, "status"));
                    sysThemeTmp.setVer(RequestUtils.getInt(request, "ver"));

		    this.sysThemeTmpService.save(sysThemeTmp);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpService")
	public void setSysThemeTmpService(SysThemeTmpService sysThemeTmpService) {
		this.sysThemeTmpService = sysThemeTmpService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysThemeTmp sysThemeTmp = null;
		if (StringUtils.isNotEmpty(request.getParameter("themeTmpId"))) {
                  sysThemeTmp = sysThemeTmpService.getSysThemeTmp(request.getParameter("themeTmpId"));
		}
		JSONObject result = new JSONObject();
		if (sysThemeTmp != null) {
		    result =  sysThemeTmp.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sysThemeTmp.getThemeTmpId());
		    result.put("sysThemeTmpId", sysThemeTmp.getThemeTmpId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
