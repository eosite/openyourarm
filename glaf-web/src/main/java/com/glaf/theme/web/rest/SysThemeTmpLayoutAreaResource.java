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


import com.glaf.theme.domain.SysThemeTmpLayoutArea;
import com.glaf.theme.query.SysThemeTmpLayoutAreaQuery;
import com.glaf.theme.service.SysThemeTmpLayoutAreaService;
import com.glaf.theme.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/theme/sysThemeTmpLayoutArea")
public class SysThemeTmpLayoutAreaResource {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpLayoutAreaResource.class);

	protected SysThemeTmpLayoutAreaService sysThemeTmpLayoutAreaService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("areaIds");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				sysThemeTmpLayoutAreaService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sysThemeTmpLayoutAreaService.deleteById(request.getParameter("areaId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutAreaQuery query = new SysThemeTmpLayoutAreaQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SysThemeTmpLayoutAreaDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpLayoutAreaService.getSysThemeTmpLayoutAreaCountByQueryCriteria(query);
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
			List<SysThemeTmpLayoutArea> list = sysThemeTmpLayoutAreaService.getSysThemeTmpLayoutAreasByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpLayoutArea sysThemeTmpLayoutArea : list) {
					JSONObject rowJSON = sysThemeTmpLayoutArea.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayoutArea.getAreaId());
					rowJSON.put("sysThemeTmpLayoutAreaId", sysThemeTmpLayoutArea.getAreaId());
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
		SysThemeTmpLayoutAreaQuery query = new SysThemeTmpLayoutAreaQuery();
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
		int total = sysThemeTmpLayoutAreaService.getSysThemeTmpLayoutAreaCountByQueryCriteria(query);
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
			List<SysThemeTmpLayoutArea> list = sysThemeTmpLayoutAreaService.getSysThemeTmpLayoutAreasByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpLayoutArea sysThemeTmpLayoutArea : list) {
					JSONObject rowJSON = sysThemeTmpLayoutArea.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayoutArea.getAreaId());
					rowJSON.put("sysThemeTmpLayoutAreaId", sysThemeTmpLayoutArea.getAreaId());
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
	@Path("/saveSysThemeTmpLayoutArea")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysThemeTmpLayoutArea(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutArea sysThemeTmpLayoutArea = new SysThemeTmpLayoutArea();
		try {
		    Tools.populate(sysThemeTmpLayoutArea, params);

                    sysThemeTmpLayoutArea.setLayoutId(request.getParameter("layoutId"));
                    sysThemeTmpLayoutArea.setAreaName(request.getParameter("areaName"));
                    sysThemeTmpLayoutArea.setAreaCode(request.getParameter("areaCode"));
                    sysThemeTmpLayoutArea.setCompType(request.getParameter("compType"));
                    sysThemeTmpLayoutArea.setElemCode(request.getParameter("elemCode"));
                    sysThemeTmpLayoutArea.setSelectorExp(request.getParameter("selectorExp"));
                    sysThemeTmpLayoutArea.setCreateBy(request.getParameter("createBy"));
                    sysThemeTmpLayoutArea.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    sysThemeTmpLayoutArea.setUpdateBy(request.getParameter("updateBy"));
                    sysThemeTmpLayoutArea.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                    sysThemeTmpLayoutArea.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.sysThemeTmpLayoutAreaService.save(sysThemeTmpLayoutArea);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpLayoutAreaService")
	public void setSysThemeTmpLayoutAreaService(SysThemeTmpLayoutAreaService sysThemeTmpLayoutAreaService) {
		this.sysThemeTmpLayoutAreaService = sysThemeTmpLayoutAreaService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysThemeTmpLayoutArea sysThemeTmpLayoutArea = null;
		if (StringUtils.isNotEmpty(request.getParameter("areaId"))) {
                  sysThemeTmpLayoutArea = sysThemeTmpLayoutAreaService.getSysThemeTmpLayoutArea(request.getParameter("areaId"));
		}
		JSONObject result = new JSONObject();
		if (sysThemeTmpLayoutArea != null) {
		    result =  sysThemeTmpLayoutArea.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sysThemeTmpLayoutArea.getAreaId());
		    result.put("sysThemeTmpLayoutAreaId", sysThemeTmpLayoutArea.getAreaId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
