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


import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.query.RdataTableQuery;
import com.glaf.isdp.service.RdataTableService;
import com.glaf.isdp.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/rdataTable")
public class RdataTableResource {
	protected static final Log logger = LogFactory.getLog(RdataTableResource.class);

	protected RdataTableService rdataTableService;

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
				rdataTableService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                rdataTableService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTableQuery query = new RdataTableQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		RdataTableDomainFactory.processDataRequest(dataRequest);

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
		int total = rdataTableService.getRdataTableCountByQueryCriteria(query);
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
			List<RdataTable> list = rdataTableService.getRdataTablesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (RdataTable rdataTable : list) {
					JSONObject rowJSON = rdataTable.toJsonObject();
					rowJSON.put("id", rdataTable.getId());
					rowJSON.put("rdataTableId", rdataTable.getId());
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
		RdataTableQuery query = new RdataTableQuery();
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
		int total = rdataTableService.getRdataTableCountByQueryCriteria(query);
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
			List<RdataTable> list = rdataTableService.getRdataTablesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (RdataTable rdataTable : list) {
					JSONObject rowJSON = rdataTable.toJsonObject();
					rowJSON.put("id", rdataTable.getId());
					rowJSON.put("rdataTableId", rdataTable.getId());
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
	@Path("/saveRdataTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveRdataTable(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTable rdataTable = new RdataTable();
		try {
		    Tools.populate(rdataTable, params);

                    rdataTable.setTablename(request.getParameter("tablename"));
                    rdataTable.setName(request.getParameter("name"));
                    rdataTable.setAddtype(RequestUtils.getInt(request, "addtype"));
                    rdataTable.setMaxuser(RequestUtils.getInt(request, "maxuser"));
                    rdataTable.setMaxsys(RequestUtils.getInt(request, "maxsys"));
                    rdataTable.setUserid(request.getParameter("userid"));
                    rdataTable.setCtime(RequestUtils.getDate(request, "ctime"));
                    rdataTable.setContent(request.getParameter("content"));
                    rdataTable.setSysnum(request.getParameter("sysnum"));
                    rdataTable.setIssubtable(request.getParameter("issubtable"));
                    rdataTable.setTopid(request.getParameter("topid"));
                    rdataTable.setFiledotFileid(request.getParameter("filedotFileid"));
                    rdataTable.setIndexId(RequestUtils.getInt(request, "indexId"));
                    rdataTable.setWinWidth(RequestUtils.getInt(request, "winWidth"));
                    rdataTable.setWinHeight(RequestUtils.getInt(request, "winHeight"));
                    rdataTable.setIntQuote(RequestUtils.getInt(request, "intQuote"));
                    rdataTable.setIntLineEdit(RequestUtils.getInt(request, "intLineEdit"));
                    rdataTable.setPrintfileid(request.getParameter("printfileid"));
                    rdataTable.setINTUSESTREEWBS(RequestUtils.getInt(request, "INTUSESTREEWBS"));
                    rdataTable.setIntUseIf(RequestUtils.getInt(request, "intUseIf"));

		    this.rdataTableService.save(rdataTable);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.isdp.service.rdataTableService")
	public void setRdataTableService(RdataTableService rdataTableService) {
		this.rdataTableService = rdataTableService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		RdataTable rdataTable = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
                  rdataTable = rdataTableService.getRdataTable(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (rdataTable != null) {
		    result =  rdataTable.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", rdataTable.getId());
		    result.put("rdataTableId", rdataTable.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
