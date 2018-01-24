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


import com.glaf.theme.domain.SysThemeTmpLayout;
import com.glaf.theme.query.SysThemeTmpLayoutQuery;
import com.glaf.theme.service.SysThemeTmpLayoutService;
import com.glaf.theme.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/theme/sysThemeTmpLayout")
public class SysThemeTmpLayoutResource {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpLayoutResource.class);

	protected SysThemeTmpLayoutService sysThemeTmpLayoutService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("layoutIds");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				sysThemeTmpLayoutService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sysThemeTmpLayoutService.deleteById(request.getParameter("layoutId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutQuery query = new SysThemeTmpLayoutQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SysThemeTmpLayoutDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpLayoutService.getSysThemeTmpLayoutCountByQueryCriteria(query);
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
			List<SysThemeTmpLayout> list = sysThemeTmpLayoutService.getSysThemeTmpLayoutsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpLayout sysThemeTmpLayout : list) {
					JSONObject rowJSON = sysThemeTmpLayout.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayout.getLayoutId());
					rowJSON.put("sysThemeTmpLayoutId", sysThemeTmpLayout.getLayoutId());
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
		SysThemeTmpLayoutQuery query = new SysThemeTmpLayoutQuery();
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
		int total = sysThemeTmpLayoutService.getSysThemeTmpLayoutCountByQueryCriteria(query);
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
			List<SysThemeTmpLayout> list = sysThemeTmpLayoutService.getSysThemeTmpLayoutsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpLayout sysThemeTmpLayout : list) {
					JSONObject rowJSON = sysThemeTmpLayout.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayout.getLayoutId());
					rowJSON.put("sysThemeTmpLayoutId", sysThemeTmpLayout.getLayoutId());
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
	@Path("/saveSysThemeTmpLayout")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysThemeTmpLayout(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayout sysThemeTmpLayout = new SysThemeTmpLayout();
		try {
		    Tools.populate(sysThemeTmpLayout, params);

                    sysThemeTmpLayout.setThemeTmpId(request.getParameter("themeTmpId"));
                    sysThemeTmpLayout.setLayoutName(request.getParameter("layoutName"));
                    sysThemeTmpLayout.setLayoutPlan(request.getParameter("layoutPlan"));
                    sysThemeTmpLayout.setLayoutCode(request.getParameter("layoutCode"));
                    sysThemeTmpLayout.setSelectorExp(request.getParameter("selectorExp"));
                    sysThemeTmpLayout.setCreateBy(request.getParameter("createBy"));
                    sysThemeTmpLayout.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    sysThemeTmpLayout.setUpdateBy(request.getParameter("updateBy"));
                    sysThemeTmpLayout.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                    sysThemeTmpLayout.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.sysThemeTmpLayoutService.save(sysThemeTmpLayout);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpLayoutService")
	public void setSysThemeTmpLayoutService(SysThemeTmpLayoutService sysThemeTmpLayoutService) {
		this.sysThemeTmpLayoutService = sysThemeTmpLayoutService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysThemeTmpLayout sysThemeTmpLayout = null;
		if (StringUtils.isNotEmpty(request.getParameter("layoutId"))) {
                  sysThemeTmpLayout = sysThemeTmpLayoutService.getSysThemeTmpLayout(request.getParameter("layoutId"));
		}
		JSONObject result = new JSONObject();
		if (sysThemeTmpLayout != null) {
		    result =  sysThemeTmpLayout.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sysThemeTmpLayout.getLayoutId());
		    result.put("sysThemeTmpLayoutId", sysThemeTmpLayout.getLayoutId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
