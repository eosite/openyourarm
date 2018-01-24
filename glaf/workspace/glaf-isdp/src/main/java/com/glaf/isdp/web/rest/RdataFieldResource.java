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


import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.query.RdataFieldQuery;
import com.glaf.isdp.service.RdataFieldService;
import com.glaf.isdp.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/rdataField")
public class RdataFieldResource {
	protected static final Log logger = LogFactory.getLog(RdataFieldResource.class);

	protected RdataFieldService rdataFieldService;

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
				rdataFieldService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                rdataFieldService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataFieldQuery query = new RdataFieldQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		RdataFieldDomainFactory.processDataRequest(dataRequest);

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
		int total = rdataFieldService.getRdataFieldCountByQueryCriteria(query);
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
			List<RdataField> list = rdataFieldService.getRdataFieldsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (RdataField rdataField : list) {
					JSONObject rowJSON = rdataField.toJsonObject();
					rowJSON.put("id", rdataField.getId());
					rowJSON.put("rdataFieldId", rdataField.getId());
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
		RdataFieldQuery query = new RdataFieldQuery();
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
		int total = rdataFieldService.getRdataFieldCountByQueryCriteria(query);
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
			List<RdataField> list = rdataFieldService.getRdataFieldsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (RdataField rdataField : list) {
					JSONObject rowJSON = rdataField.toJsonObject();
					rowJSON.put("id", rdataField.getId());
					rowJSON.put("rdataFieldId", rdataField.getId());
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
	@Path("/saveRdataField")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveRdataField(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataField rdataField = new RdataField();
		try {
		    Tools.populate(rdataField, params);

                    rdataField.setTableid(request.getParameter("tableid"));
                    rdataField.setFieldname(request.getParameter("fieldname"));
                    rdataField.setUserid(request.getParameter("userid"));
                    rdataField.setMaxuser(RequestUtils.getInt(request, "maxuser"));
                    rdataField.setMaxsys(RequestUtils.getInt(request, "maxsys"));
                    rdataField.setCtime(RequestUtils.getDate(request, "ctime"));
                    rdataField.setSysnum(request.getParameter("sysnum"));
                    rdataField.setTablename(request.getParameter("tablename"));
                    rdataField.setDname(request.getParameter("dname"));
                    rdataField.setUserindex(request.getParameter("userindex"));
                    rdataField.setTreetablenameB(request.getParameter("treetablenameB"));
                    rdataField.setFormula(request.getParameter("formula"));
                    rdataField.setLgcexpress(request.getParameter("lgcexpress"));

		    this.rdataFieldService.save(rdataField);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.isdp.service.rdataFieldService")
	public void setRdataFieldService(RdataFieldService rdataFieldService) {
		this.rdataFieldService = rdataFieldService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		RdataField rdataField = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  rdataField = rdataFieldService.getRdataField(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (rdataField != null) {
		    result =  rdataField.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", rdataField.getId());
		    result.put("rdataFieldId", rdataField.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
