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


import com.glaf.theme.domain.SysThemeTmpControl;
import com.glaf.theme.query.SysThemeTmpControlQuery;
import com.glaf.theme.service.SysThemeTmpControlService;
import com.glaf.theme.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/theme/sysThemeTmpControl")
public class SysThemeTmpControlResource {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpControlResource.class);

	protected SysThemeTmpControlService sysThemeTmpControlService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("controlIds");
		if (rowIds != null) {
 			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				sysThemeTmpControlService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sysThemeTmpControlService.deleteById(request.getParameter("controlId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SysThemeTmpControlDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpControlService.getSysThemeTmpControlCountByQueryCriteria(query);
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
			List<SysThemeTmpControl> list = sysThemeTmpControlService.getSysThemeTmpControlsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpControl sysThemeTmpControl : list) {
					JSONObject rowJSON = sysThemeTmpControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpControl.getThemeTmpId());
					rowJSON.put("sysThemeTmpControlId", sysThemeTmpControl.getThemeTmpId());
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
		SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
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
		int total = sysThemeTmpControlService.getSysThemeTmpControlCountByQueryCriteria(query);
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
			List<SysThemeTmpControl> list = sysThemeTmpControlService.getSysThemeTmpControlsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpControl sysThemeTmpControl : list) {
					JSONObject rowJSON = sysThemeTmpControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpControl.getThemeTmpId());
					rowJSON.put("sysThemeTmpControlId", sysThemeTmpControl.getThemeTmpId());
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
	@Path("/saveSysThemeTmpControl")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysThemeTmpControl(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpControl sysThemeTmpControl = new SysThemeTmpControl();
		try {
		    Tools.populate(sysThemeTmpControl, params);

                    sysThemeTmpControl.setThemeTmpId(request.getParameter("themeTmpId"));
                    sysThemeTmpControl.setCommonFlag(RequestUtils.getInt(request, "commonFlag"));
                    sysThemeTmpControl.setControlName(request.getParameter("controlName"));
                    sysThemeTmpControl.setControlCode(request.getParameter("controlCode"));
                    sysThemeTmpControl.setCompType(request.getParameter("compType"));
                    sysThemeTmpControl.setElemCode(request.getParameter("elemCode"));
                    sysThemeTmpControl.setCompositionFlag(RequestUtils.getInt(request, "compositionFlag"));
                    sysThemeTmpControl.setContainerFlag(RequestUtils.getInt(request, "containerFlag"));
                    sysThemeTmpControl.setSelectorExp(request.getParameter("selectorExp"));
                    sysThemeTmpControl.setCreateBy(request.getParameter("createBy"));
                    sysThemeTmpControl.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    sysThemeTmpControl.setUpdateBy(request.getParameter("updateBy"));
                    sysThemeTmpControl.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                    sysThemeTmpControl.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.sysThemeTmpControlService.save(sysThemeTmpControl);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpControlService")
	public void setSysThemeTmpControlService(SysThemeTmpControlService sysThemeTmpControlService) {
		this.sysThemeTmpControlService = sysThemeTmpControlService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysThemeTmpControl sysThemeTmpControl = null;
		if (StringUtils.isNotEmpty(request.getParameter("controlId"))) {
                  sysThemeTmpControl = sysThemeTmpControlService.getSysThemeTmpControl(request.getParameter("controlId"));
		}
		JSONObject result = new JSONObject();
		if (sysThemeTmpControl != null) {
		    result =  sysThemeTmpControl.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sysThemeTmpControl.getThemeTmpId());
		    result.put("sysThemeTmpControlId", sysThemeTmpControl.getThemeTmpId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
