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


import com.glaf.theme.domain.SysThemeTmpLayoutAreaControl;
import com.glaf.theme.query.SysThemeTmpLayoutAreaControlQuery;
import com.glaf.theme.service.SysThemeTmpLayoutAreaControlService;
import com.glaf.theme.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/theme/sysThemeTmpLayoutAreaControl")
public class SysThemeTmpLayoutAreaControlResource {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpLayoutAreaControlResource.class);

	protected SysThemeTmpLayoutAreaControlService sysThemeTmpLayoutAreaControlService;

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
				sysThemeTmpLayoutAreaControlService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                sysThemeTmpLayoutAreaControlService.deleteById(request.getParameter("controlId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutAreaControlQuery query = new SysThemeTmpLayoutAreaControlQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SysThemeTmpLayoutAreaControlDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpLayoutAreaControlService.getSysThemeTmpLayoutAreaControlCountByQueryCriteria(query);
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
			List<SysThemeTmpLayoutAreaControl> list = sysThemeTmpLayoutAreaControlService.getSysThemeTmpLayoutAreaControlsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl : list) {
					JSONObject rowJSON = sysThemeTmpLayoutAreaControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayoutAreaControl.getControlId());
					rowJSON.put("sysThemeTmpLayoutAreaControlId", sysThemeTmpLayoutAreaControl.getControlId());
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
		SysThemeTmpLayoutAreaControlQuery query = new SysThemeTmpLayoutAreaControlQuery();
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
		int total = sysThemeTmpLayoutAreaControlService.getSysThemeTmpLayoutAreaControlCountByQueryCriteria(query);
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
			List<SysThemeTmpLayoutAreaControl> list = sysThemeTmpLayoutAreaControlService.getSysThemeTmpLayoutAreaControlsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl : list) {
					JSONObject rowJSON = sysThemeTmpLayoutAreaControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayoutAreaControl.getControlId());
					rowJSON.put("sysThemeTmpLayoutAreaControlId", sysThemeTmpLayoutAreaControl.getControlId());
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
	@Path("/saveSysThemeTmpLayoutAreaControl")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSysThemeTmpLayoutAreaControl(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl = new SysThemeTmpLayoutAreaControl();
		try {
		    Tools.populate(sysThemeTmpLayoutAreaControl, params);

                    sysThemeTmpLayoutAreaControl.setThemeTmpControlId(request.getParameter("themeTmpControlId"));
                    sysThemeTmpLayoutAreaControl.setAreaId(request.getParameter("areaId"));
                    sysThemeTmpLayoutAreaControl.setLayoutId(request.getParameter("layoutId"));
                    sysThemeTmpLayoutAreaControl.setControlName(request.getParameter("controlName"));
                    sysThemeTmpLayoutAreaControl.setControlCode(request.getParameter("controlCode"));
                    sysThemeTmpLayoutAreaControl.setCompType(request.getParameter("compType"));
                    sysThemeTmpLayoutAreaControl.setElemCode(request.getParameter("elemCode"));
                    sysThemeTmpLayoutAreaControl.setSelectorExp(request.getParameter("selectorExp"));
                    sysThemeTmpLayoutAreaControl.setCommonFlag(RequestUtils.getInt(request, "commonFlag"));
                    sysThemeTmpLayoutAreaControl.setContainerFlag(RequestUtils.getInt(request, "containerFlag"));
                    sysThemeTmpLayoutAreaControl.setPcontrolId(request.getParameter("pcontrolId"));
                    sysThemeTmpLayoutAreaControl.setCreateBy(request.getParameter("createBy"));
                    sysThemeTmpLayoutAreaControl.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    sysThemeTmpLayoutAreaControl.setUpdateBy(request.getParameter("updateBy"));
                    sysThemeTmpLayoutAreaControl.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                    sysThemeTmpLayoutAreaControl.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		    this.sysThemeTmpLayoutAreaControlService.save(sysThemeTmpLayoutAreaControl);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpLayoutAreaControlService")
	public void setSysThemeTmpLayoutAreaControlService(SysThemeTmpLayoutAreaControlService sysThemeTmpLayoutAreaControlService) {
		this.sysThemeTmpLayoutAreaControlService = sysThemeTmpLayoutAreaControlService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl = null;
		if (StringUtils.isNotEmpty(request.getParameter("controlId"))) {
                  sysThemeTmpLayoutAreaControl = sysThemeTmpLayoutAreaControlService.getSysThemeTmpLayoutAreaControl(request.getParameter("controlId"));
		}
		JSONObject result = new JSONObject();
		if (sysThemeTmpLayoutAreaControl != null) {
		    result =  sysThemeTmpLayoutAreaControl.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", sysThemeTmpLayoutAreaControl.getControlId());
		    result.put("sysThemeTmpLayoutAreaControlId", sysThemeTmpLayoutAreaControl.getControlId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
