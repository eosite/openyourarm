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
import com.glaf.isdp.domain.ProjInspection;
import com.glaf.isdp.query.ProjInspectionQuery;
import com.glaf.isdp.service.ProjInspectionService;
import com.glaf.isdp.util.ProjInspectionDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/projInspection")
public class ProjInspectionResource {
	protected static final Log logger = LogFactory
			.getLog(ProjInspectionResource.class);

	protected ProjInspectionService projInspectionService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				projInspectionService.deleteByIds(ids);
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
		projInspectionService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjInspectionQuery query = new ProjInspectionQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ProjInspectionDomainFactory.processDataRequest(dataRequest);

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
		int total = projInspectionService
				.getProjInspectionCountByQueryCriteria(query);
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
			List<ProjInspection> list = projInspectionService
					.getProjInspectionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjInspection projInspection : list) {
					JSONObject rowJSON = projInspection.toJsonObject();
					rowJSON.put("id", projInspection.getId());
					rowJSON.put("projInspectionId", projInspection.getId());
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
		ProjInspectionQuery query = new ProjInspectionQuery();
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
		int total = projInspectionService
				.getProjInspectionCountByQueryCriteria(query);
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
			List<ProjInspection> list = projInspectionService
					.getProjInspectionsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjInspection projInspection : list) {
					JSONObject rowJSON = projInspection.toJsonObject();
					rowJSON.put("id", projInspection.getId());
					rowJSON.put("projInspectionId", projInspection.getId());
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
	@Path("/saveProjInspection")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveProjInspection(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjInspection projInspection = new ProjInspection();
		try {
			Tools.populate(projInspection, params);

			projInspection.setIndexId(RequestUtils.getInt(request, "indexId"));
			projInspection.setIntFlag(RequestUtils.getInt(request, "intFlag"));
			projInspection.setCellTmpFileTypeId(request
					.getParameter("cellTmpFileTypeId"));
			projInspection.setListNo(RequestUtils.getInt(request, "listNo"));
			projInspection.setChkResult(RequestUtils.getInt(request,
					"chkResult"));
			projInspection.setPfileId(request.getParameter("pfileId"));
			projInspection.setRefillFlag(RequestUtils.getInt(request,
					"refillFlag"));
			projInspection.setGroupId(RequestUtils.getInt(request, "groupId"));
			projInspection.setOldId(request.getParameter("oldId"));
			projInspection.setEmailId(request.getParameter("emailId"));
			projInspection.setRecemailId(request.getParameter("recemailId"));
			projInspection.setMainId(request.getParameter("mainId"));
			projInspection.setTagNum(request.getParameter("tagNum"));
			projInspection.setCtime(RequestUtils.getDate(request, "ctime"));
			projInspection.setTname(request.getParameter("tname"));
			projInspection.setPage(RequestUtils.getInt(request, "page"));
			projInspection.setDuty(request.getParameter("duty"));
			projInspection.setThematic(request.getParameter("thematic"));
			projInspection.setAnnotations(request.getParameter("annotations"));

			this.projInspectionService.save(projInspection);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.projInspectionService")
	public void setProjInspectionService(
			ProjInspectionService projInspectionService) {
		this.projInspectionService = projInspectionService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ProjInspection projInspection = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			projInspection = projInspectionService.getProjInspection(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (projInspection != null) {
			result = projInspection.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", projInspection.getId());
			result.put("projInspectionId", projInspection.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
