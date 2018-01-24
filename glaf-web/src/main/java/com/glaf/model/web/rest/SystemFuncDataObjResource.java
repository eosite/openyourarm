package com.glaf.model.web.rest;

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


import com.glaf.model.domain.SystemFuncDataObj;
import com.glaf.model.query.SystemFuncDataObjQuery;
import com.glaf.model.service.SystemFuncDataObjService;
import com.glaf.model.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/model/systemFuncDataObj")
public class SystemFuncDataObjResource {
	protected static final Log logger = LogFactory.getLog(SystemFuncDataObjResource.class);

	protected SystemFuncDataObjService systemFuncDataObjService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("sysDataObjIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				systemFuncDataObjService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                systemFuncDataObjService.deleteById(RequestUtils.getString(request, "sysDataObjId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemFuncDataObjQuery query = new SystemFuncDataObjQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		SystemFuncDataObjDomainFactory.processDataRequest(dataRequest);

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
		int total = systemFuncDataObjService.getSystemFuncDataObjCountByQueryCriteria(query);
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
			List<SystemFuncDataObj> list = systemFuncDataObjService.getSystemFuncDataObjsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SystemFuncDataObj systemFuncDataObj : list) {
					JSONObject rowJSON = systemFuncDataObj.toJsonObject();
					rowJSON.put("id", systemFuncDataObj.getSysDataObjId());
					rowJSON.put("systemFuncDataObjId", systemFuncDataObj.getSysDataObjId());
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
		SystemFuncDataObjQuery query = new SystemFuncDataObjQuery();
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
		int total = systemFuncDataObjService.getSystemFuncDataObjCountByQueryCriteria(query);
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
			List<SystemFuncDataObj> list = systemFuncDataObjService.getSystemFuncDataObjsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SystemFuncDataObj systemFuncDataObj : list) {
					JSONObject rowJSON = systemFuncDataObj.toJsonObject();
					rowJSON.put("id", systemFuncDataObj.getSysDataObjId());
					rowJSON.put("systemFuncDataObjId", systemFuncDataObj.getSysDataObjId());
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
	@Path("/saveSystemFuncDataObj")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSystemFuncDataObj(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemFuncDataObj systemFuncDataObj = new SystemFuncDataObj();
		try {
		    Tools.populate(systemFuncDataObj, params);

                    systemFuncDataObj.setFuncId(RequestUtils.getString(request, "funcId"));
                    systemFuncDataObj.setDataObjId(RequestUtils.getString(request, "dataObjId"));
                    systemFuncDataObj.setCreateBy(request.getParameter("createBy"));
                    systemFuncDataObj.setCreateTime(RequestUtils.getDate(request, "createTime"));
                    systemFuncDataObj.setUpdateBy(request.getParameter("updateBy"));
                    systemFuncDataObj.setUpdateTime(RequestUtils.getDate(request, "updateTime"));

		    this.systemFuncDataObjService.save(systemFuncDataObj);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.model.service.systemFuncDataObjService")
	public void setSystemFuncDataObjService(SystemFuncDataObjService systemFuncDataObjService) {
		this.systemFuncDataObjService = systemFuncDataObjService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SystemFuncDataObj systemFuncDataObj = null;
		if (StringUtils.isNotEmpty(request.getParameter("sysDataObjId"))) {
                  systemFuncDataObj = systemFuncDataObjService.getSystemFuncDataObj(RequestUtils.getString(request, "sysDataObjId"));
		}
		JSONObject result = new JSONObject();
		if (systemFuncDataObj != null) {
		    result =  systemFuncDataObj.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", systemFuncDataObj.getSysDataObjId());
		    result.put("systemFuncDataObjId", systemFuncDataObj.getSysDataObjId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
