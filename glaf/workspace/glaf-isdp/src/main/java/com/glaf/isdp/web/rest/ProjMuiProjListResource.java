package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.ProjMuiProjList;
import com.glaf.isdp.query.ProjMuiProjListQuery;
import com.glaf.isdp.service.ProjMuiProjListService;
import com.glaf.isdp.util.ProjMuiProjListDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/projMuiProjList")
public class ProjMuiProjListResource {
	protected static final Log logger = LogFactory
			.getLog(ProjMuiProjListResource.class);

	protected ProjMuiProjListService projMuiProjListService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("indexIds");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				projMuiProjListService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		projMuiProjListService.deleteById(RequestUtils.getInt(request,
				"indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjMuiProjListQuery query = new ProjMuiProjListQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ProjMuiProjListDomainFactory.processDataRequest(dataRequest);

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
		int total = projMuiProjListService
				.getProjMuiProjListCountByQueryCriteria(query);
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

			//Map<String, User> userMap = IdentityFactory.getUserMap();
			List<ProjMuiProjList> list = projMuiProjListService
					.getProjMuiProjListsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjMuiProjList projMuiProjList : list) {
					JSONObject rowJSON = projMuiProjList.toJsonObject();
					rowJSON.put("id", projMuiProjList.getId());
					rowJSON.put("projMuiProjListId", projMuiProjList.getId());
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
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjMuiProjListQuery query = new ProjMuiProjListQuery();
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
		int total = projMuiProjListService
				.getProjMuiProjListCountByQueryCriteria(query);
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

			//Map<String, User> userMap = IdentityFactory.getUserMap();
			List<ProjMuiProjList> list = projMuiProjListService
					.getProjMuiProjListsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjMuiProjList projMuiProjList : list) {
					JSONObject rowJSON = projMuiProjList.toJsonObject();
					rowJSON.put("id", projMuiProjList.getId());
					rowJSON.put("projMuiProjListId", projMuiProjList.getId());
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
	@Path("/saveProjMuiProjList")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveProjMuiProjList(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjMuiProjList projMuiProjList = new ProjMuiProjList();
		try {
			Tools.populate(projMuiProjList, params);

			projMuiProjList.setId(request.getParameter("id"));
			projMuiProjList.setIntFlag(RequestUtils.getInt(request, "intFlag"));
			projMuiProjList.setSysId(request.getParameter("sysId"));
			projMuiProjList.setProjName(request.getParameter("projName"));
			projMuiProjList.setNum(request.getParameter("num"));
			projMuiProjList.setCtime(RequestUtils.getDate(request, "ctime"));
			projMuiProjList.setContent(request.getParameter("content"));
			projMuiProjList.setDbName(request.getParameter("dbName"));
			projMuiProjList.setServerName(request.getParameter("serverName"));
			projMuiProjList.setUser(request.getParameter("user"));
			projMuiProjList.setPassword(request.getParameter("password"));
			projMuiProjList.setListNo(RequestUtils.getInt(request, "listNo"));
			projMuiProjList.setEmail(request.getParameter("email"));
			projMuiProjList.setParentId(RequestUtils
					.getInt(request, "parentId"));
			projMuiProjList.setNodeIco(RequestUtils.getInt(request, "nodeIco"));
			projMuiProjList.setIntLine(RequestUtils.getInt(request, "intLine"));
			projMuiProjList.setDomainIndex(RequestUtils.getInt(request,
					"domainIndex"));
			projMuiProjList.setIntLocal(RequestUtils
					.getInt(request, "intLocal"));
			projMuiProjList.setEmailPSW(request.getParameter("emailPSW"));
			projMuiProjList.setIntConnected(RequestUtils.getInt(request,
					"intConnected"));
			projMuiProjList.setEmails(request.getParameter("emails"));
			projMuiProjList.setIntorgLevel(RequestUtils.getInt(request,
					"intorgLevel"));
			projMuiProjList.setIntSendType(RequestUtils.getInt(request,
					"intSendType"));
			projMuiProjList.setEmailBackup(request.getParameter("emailBackup"));
			projMuiProjList.setEmailImplement(request
					.getParameter("emailImplement"));

			this.projMuiProjListService.save(projMuiProjList);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.projMuiProjListService")
	public void setProjMuiProjListService(
			ProjMuiProjListService projMuiProjListService) {
		this.projMuiProjListService = projMuiProjListService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ProjMuiProjList projMuiProjList = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			projMuiProjList = projMuiProjListService
					.getProjMuiProjList(RequestUtils.getInt(request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (projMuiProjList != null) {
			result = projMuiProjList.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", projMuiProjList.getId());
			result.put("projMuiProjListId", projMuiProjList.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
