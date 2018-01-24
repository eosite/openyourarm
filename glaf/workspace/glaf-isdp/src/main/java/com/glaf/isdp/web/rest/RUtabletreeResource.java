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


import com.glaf.isdp.domain.RUtabletree;
import com.glaf.isdp.query.RUtabletreeQuery;
import com.glaf.isdp.service.RUtabletreeService;
import com.glaf.isdp.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/rUtabletree")
public class RUtabletreeResource {
	protected static final Log logger = LogFactory.getLog(RUtabletreeResource.class);

	protected RUtabletreeService rUtabletreeService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("indexIds");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				rUtabletreeService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
                rUtabletreeService.deleteById(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}



	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RUtabletreeQuery query = new RUtabletreeQuery();
		Tools.populate(query, params);
                query.setDataRequest(dataRequest);
		RUtabletreeDomainFactory.processDataRequest(dataRequest);

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
		int total = rUtabletreeService.getRUtabletreeCountByQueryCriteria(query);
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
			List<RUtabletree> list = rUtabletreeService.getRUtabletreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (RUtabletree rUtabletree : list) {
					JSONObject rowJSON = rUtabletree.toJsonObject();
					rowJSON.put("id", rUtabletree.getId());
					rowJSON.put("rUtabletreeId", rUtabletree.getId());
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
		RUtabletreeQuery query = new RUtabletreeQuery();
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
		int total = rUtabletreeService.getRUtabletreeCountByQueryCriteria(query);
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
			List<RUtabletree> list = rUtabletreeService.getRUtabletreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (RUtabletree rUtabletree : list) {
					JSONObject rowJSON = rUtabletree.toJsonObject();
					rowJSON.put("id", rUtabletree.getId());
					rowJSON.put("rUtabletreeId", rUtabletree.getId());
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
	@Path("/saveRUtabletree")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveRUtabletree(@Context HttpServletRequest request) {
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		RUtabletree rUtabletree = new RUtabletree();
		try {
		    Tools.populate(rUtabletree, params);

                    rUtabletree.setId(request.getParameter("id"));
                    rUtabletree.setParentId(RequestUtils.getInt(request, "parentId"));
                    rUtabletree.setIndexName(request.getParameter("indexName"));
                    rUtabletree.setNlevel(RequestUtils.getInt(request, "nlevel"));
                    rUtabletree.setNodeico(RequestUtils.getInt(request, "nodeico"));
                    rUtabletree.setListno(RequestUtils.getInt(request, "listno"));
                    rUtabletree.setTabletype(RequestUtils.getInt(request, "tabletype"));
                    rUtabletree.setIntdel(RequestUtils.getInt(request, "intdel"));
                    rUtabletree.setBusiessId(request.getParameter("busiessId"));
                    rUtabletree.setContent(request.getParameter("content"));
                    rUtabletree.setNum(request.getParameter("num"));
                    rUtabletree.setMenuindex(RequestUtils.getInt(request, "menuindex"));
                    rUtabletree.setDomainIndex(RequestUtils.getInt(request, "domainIndex"));
                    rUtabletree.setWinWidth(RequestUtils.getInt(request, "winWidth"));
                    rUtabletree.setWinHeight(RequestUtils.getInt(request, "winHeight"));

		    this.rUtabletreeService.save(rUtabletree);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

        @javax.annotation.Resource(name = "com.glaf.isdp.service.rUtabletreeService")
	public void setRUtabletreeService(RUtabletreeService rUtabletreeService) {
		this.rUtabletreeService = rUtabletreeService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		RUtabletree rUtabletree = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
                  rUtabletree = rUtabletreeService.getRUtabletree(RequestUtils.getInt(request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (rUtabletree != null) {
		    result =  rUtabletree.toJsonObject();
		    Map<String, User> userMap = IdentityFactory.getUserMap();
		    result.put("id", rUtabletree.getId());
		    result.put("rUtabletreeId", rUtabletree.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
