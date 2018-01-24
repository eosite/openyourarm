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
import com.glaf.isdp.domain.ProjInspectionForm;
import com.glaf.isdp.query.ProjInspectionFormQuery;
import com.glaf.isdp.service.ProjInspectionFormService;
import com.glaf.isdp.util.ProjInspectionFormDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/idsp/projInspectionForm")
public class ProjInspectionFormResource {
	protected static final Log logger = LogFactory
			.getLog(ProjInspectionFormResource.class);

	protected ProjInspectionFormService projInspectionFormService;

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
				projInspectionFormService.deleteByIds(ids);
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
		projInspectionFormService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjInspectionFormQuery query = new ProjInspectionFormQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ProjInspectionFormDomainFactory.processDataRequest(dataRequest);

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
		int total = projInspectionFormService
				.getProjInspectionFormCountByQueryCriteria(query);
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
			List<ProjInspectionForm> list = projInspectionFormService
					.getProjInspectionFormsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjInspectionForm projInspectionForm : list) {
					JSONObject rowJSON = projInspectionForm.toJsonObject();
					rowJSON.put("id", projInspectionForm.getId());
					rowJSON.put("projInspectionFormId",
							projInspectionForm.getId());
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
		ProjInspectionFormQuery query = new ProjInspectionFormQuery();
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
		int total = projInspectionFormService
				.getProjInspectionFormCountByQueryCriteria(query);
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
			List<ProjInspectionForm> list = projInspectionFormService
					.getProjInspectionFormsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjInspectionForm projInspectionForm : list) {
					JSONObject rowJSON = projInspectionForm.toJsonObject();
					rowJSON.put("id", projInspectionForm.getId());
					rowJSON.put("projInspectionFormId",
							projInspectionForm.getId());
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
	@Path("/saveProjInspectionForm")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveProjInspectionForm(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjInspectionForm projInspectionForm = new ProjInspectionForm();
		try {
			Tools.populate(projInspectionForm, params);

			projInspectionForm.setProjInspectionId(request
					.getParameter("projInspectionId"));
			projInspectionForm
					.setCellFormId(request.getParameter("cellFormId"));
			projInspectionForm.setDotUseId(request.getParameter("dotUseId"));
			projInspectionForm
					.setListNo(RequestUtils.getInt(request, "listNo"));
			projInspectionForm.setIntIsCheck(RequestUtils.getInt(request,
					"intIsCheck"));
			projInspectionForm.setIntMust(RequestUtils.getInt(request,
					"intMust"));

			this.projInspectionFormService.save(projInspectionForm);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.projInspectionFormService")
	public void setProjInspectionFormService(
			ProjInspectionFormService projInspectionFormService) {
		this.projInspectionFormService = projInspectionFormService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ProjInspectionForm projInspectionForm = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			projInspectionForm = projInspectionFormService
					.getProjInspectionForm(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (projInspectionForm != null) {
			result = projInspectionForm.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", projInspectionForm.getId());
			result.put("projInspectionFormId", projInspectionForm.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
