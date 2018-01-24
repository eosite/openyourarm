package com.glaf.textsearch.web.rest;

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


import com.glaf.textsearch.domain.SysFullTextSearchSrc;
import com.glaf.textsearch.query.SysFullTextSearchSrcQuery;
import com.glaf.textsearch.service.SysFullTextSearchSrcService;
import com.glaf.textsearch.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/apps/sysFullTextSearchSrc")
public class SysFullTextSearchSrcResource {
	protected static final Log logger = LogFactory.getLog(SysFullTextSearchSrcResource.class);

	protected SysFullTextSearchSrcService sysFullTextSearchSrcService;

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
				sysFullTextSearchSrcService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sysFullTextSearchSrcService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysFullTextSearchSrcQuery query = new SysFullTextSearchSrcQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SysFullTextSearchSrcDomainFactory.processDataRequest(dataRequest);

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
		int total = sysFullTextSearchSrcService.getSysFullTextSearchSrcCountByQueryCriteria(query);
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
			List<SysFullTextSearchSrc> list = sysFullTextSearchSrcService.getSysFullTextSearchSrcsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysFullTextSearchSrc sysFullTextSearchSrc : list) {
					JSONObject rowJSON = sysFullTextSearchSrc.toJsonObject();
					rowJSON.put("id", sysFullTextSearchSrc.getId());
					rowJSON.put("sysFullTextSearchSrcId", sysFullTextSearchSrc.getId());
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
		SysFullTextSearchSrcQuery query = new SysFullTextSearchSrcQuery();
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
		int total = sysFullTextSearchSrcService.getSysFullTextSearchSrcCountByQueryCriteria(query);
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
			List<SysFullTextSearchSrc> list = sysFullTextSearchSrcService.getSysFullTextSearchSrcsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysFullTextSearchSrc sysFullTextSearchSrc : list) {
					JSONObject rowJSON = sysFullTextSearchSrc.toJsonObject();
					rowJSON.put("id", sysFullTextSearchSrc.getId());
					rowJSON.put("sysFullTextSearchSrcId", sysFullTextSearchSrc.getId());
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
	@Path("/saveSysFullTextSearchSrc")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysFullTextSearchSrc(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysFullTextSearchSrc sysFullTextSearchSrc = new SysFullTextSearchSrc();
		try {
		    Tools.populate(sysFullTextSearchSrc, params);

                    sysFullTextSearchSrc.setServiceName_(request.getParameter("serviceName_"));
                    sysFullTextSearchSrc.setServiceAddress_(request.getParameter("serviceAddress_"));
                    sysFullTextSearchSrc.setFullTextServer_(request.getParameter("fullTextServer_"));
                    sysFullTextSearchSrc.setIndexName_(request.getParameter("indexName_"));
                    sysFullTextSearchSrc.setTypeName_(request.getParameter("typeName_"));
                    sysFullTextSearchSrc.setCreateBy_(request.getParameter("createBy_"));
                    sysFullTextSearchSrc.setCreateTime_(RequestUtils.getDate(request, "createTime_"));
                    sysFullTextSearchSrc.setUpdateBy_(request.getParameter("updateBy_"));
                    sysFullTextSearchSrc.setUpdateTime_(RequestUtils.getDate(request, "updateTime_"));
                    sysFullTextSearchSrc.setDeleteFlag_(RequestUtils.getInt(request, "deleteFlag_"));

		    this.sysFullTextSearchSrcService.save(sysFullTextSearchSrc);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.textsearch.service.sysFullTextSearchSrcService")
	public void setSysFullTextSearchSrcService(SysFullTextSearchSrcService sysFullTextSearchSrcService) {
		this.sysFullTextSearchSrcService = sysFullTextSearchSrcService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysFullTextSearchSrc sysFullTextSearchSrc = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  sysFullTextSearchSrc = sysFullTextSearchSrcService.getSysFullTextSearchSrc(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (sysFullTextSearchSrc != null) {
		    result =  sysFullTextSearchSrc.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sysFullTextSearchSrc.getId());
		    result.put("sysFullTextSearchSrcId", sysFullTextSearchSrc.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
