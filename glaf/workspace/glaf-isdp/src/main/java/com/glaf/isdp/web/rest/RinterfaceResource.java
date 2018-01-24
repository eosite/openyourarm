package com.glaf.isdp.web.rest;

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


import com.glaf.isdp.domain.Rinterface;
import com.glaf.isdp.query.RinterfaceQuery;
import com.glaf.isdp.service.RinterfaceService;
import com.glaf.isdp.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/rinterface")
public class RinterfaceResource {
	protected static final Log logger = LogFactory.getLog(RinterfaceResource.class);

	protected RinterfaceService rinterfaceService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<Long> ids = StringTools.splitToLong(rowIds);
			if (ids != null && !ids.isEmpty()) {
				rinterfaceService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                rinterfaceService.deleteById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RinterfaceQuery query = new RinterfaceQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		RinterfaceDomainFactory.processDataRequest(dataRequest);

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
		int total = rinterfaceService.getRinterfaceCountByQueryCriteria(query);
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
			List<Rinterface> list = rinterfaceService.getRinterfacesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (Rinterface rinterface : list) {
					JSONObject rowJSON = rinterface.toJsonObject();
					rowJSON.put("id", rinterface.getListId());
					rowJSON.put("rinterfaceId", rinterface.getListId());
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
		RinterfaceQuery query = new RinterfaceQuery();
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
		int total = rinterfaceService.getRinterfaceCountByQueryCriteria(query);
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
			List<Rinterface> list = rinterfaceService.getRinterfacesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (Rinterface rinterface : list) {
					JSONObject rowJSON = rinterface.toJsonObject();
					rowJSON.put("id", rinterface.getListId());
					rowJSON.put("rinterfaceId", rinterface.getListId());
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
	@Path("/saveRinterface")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveRinterface(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		Rinterface rinterface = new Rinterface();
		try {
		    Tools.populate(rinterface, params);

                    rinterface.setIndexId(request.getParameter("indexId"));
                    rinterface.setFrmtype(request.getParameter("frmtype"));
                    rinterface.setListId(request.getParameter("listId"));
                    rinterface.setIssystem(request.getParameter("issystem"));
                    rinterface.setFname(request.getParameter("fname"));
                    rinterface.setDname(request.getParameter("dname"));
                    rinterface.setDtype(request.getParameter("dtype"));
                    rinterface.setShowtype(request.getParameter("showtype"));
                    rinterface.setStrlen(RequestUtils.getInt(request, "strlen"));
                    rinterface.setForm(request.getParameter("form"));
                    rinterface.setIntype(request.getParameter("intype"));
                    rinterface.setHintID(request.getParameter("hintID"));
                    rinterface.setListno(RequestUtils.getInt(request, "listno"));
                    rinterface.setZtype(request.getParameter("ztype"));
                    rinterface.setIsmustfill(request.getParameter("ismustfill"));
                    rinterface.setIsListShow(request.getParameter("isListShow"));
                    rinterface.setListweigth(RequestUtils.getInt(request, "listweigth"));
                    rinterface.setIsallwidth(request.getParameter("isallwidth"));
                    rinterface.setIstname(request.getParameter("istname"));
                    rinterface.setImportType(RequestUtils.getInt(request, "importType"));
                    rinterface.setDatapoint(RequestUtils.getInt(request, "datapoint"));
                    rinterface.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    rinterface.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
                    rinterface.setCreateBy(request.getParameter("createBy"));
                    rinterface.setUpdateBy(request.getParameter("updateBy"));
                    rinterface.setIsPrimaryKey(request.getParameter("isPrimaryKey"));
                    rinterface.setIsGroupBy(request.getParameter("isGroupBy"));

		    this.rinterfaceService.save(rinterface);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.isdp.service.rinterfaceService")
	public void setRinterfaceService(RinterfaceService rinterfaceService) {
		this.rinterfaceService = rinterfaceService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		Rinterface rinterface = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  rinterface = rinterfaceService.getRinterface(RequestUtils.getString(request, "id"));
		}
		JSONObject result = new JSONObject();
		if (rinterface != null) {
		    result =  rinterface.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", rinterface.getListId());
		    result.put("rinterfaceId", rinterface.getListId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
