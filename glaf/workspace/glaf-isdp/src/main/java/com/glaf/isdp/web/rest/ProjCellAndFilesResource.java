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
import com.glaf.isdp.domain.ProjCellAndFiles;
import com.glaf.isdp.query.ProjCellAndFilesQuery;
import com.glaf.isdp.service.ProjCellAndFilesService;
import com.glaf.isdp.util.ProjCellAndFilesDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/projCellAndFiles")
public class ProjCellAndFilesResource {
	protected static final Log logger = LogFactory
			.getLog(ProjCellAndFilesResource.class);

	protected ProjCellAndFilesService projCellAndFilesService;

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
				projCellAndFilesService.deleteByIds(ids);
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
		projCellAndFilesService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjCellAndFilesQuery query = new ProjCellAndFilesQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		ProjCellAndFilesDomainFactory.processDataRequest(dataRequest);

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
		int total = projCellAndFilesService
				.getProjCellAndFilesCountByQueryCriteria(query);
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
			List<ProjCellAndFiles> list = projCellAndFilesService
					.getProjCellAndFilessByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjCellAndFiles projCellAndFiles : list) {
					JSONObject rowJSON = projCellAndFiles.toJsonObject();
					rowJSON.put("id", projCellAndFiles.getId());
					rowJSON.put("projCellAndFilesId", projCellAndFiles.getId());
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
		ProjCellAndFilesQuery query = new ProjCellAndFilesQuery();
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
		int total = projCellAndFilesService
				.getProjCellAndFilesCountByQueryCriteria(query);
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
			List<ProjCellAndFiles> list = projCellAndFilesService
					.getProjCellAndFilessByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ProjCellAndFiles projCellAndFiles : list) {
					JSONObject rowJSON = projCellAndFiles.toJsonObject();
					rowJSON.put("id", projCellAndFiles.getId());
					rowJSON.put("projCellAndFilesId", projCellAndFiles.getId());
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
	@Path("/saveProjCellAndFiles")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveProjCellAndFiles(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ProjCellAndFiles projCellAndFiles = new ProjCellAndFiles();
		try {
			Tools.populate(projCellAndFiles, params);

			projCellAndFiles
					.setIndexId(RequestUtils.getInt(request, "indexId"));
			projCellAndFiles
					.setIntType(RequestUtils.getInt(request, "intType"));
			projCellAndFiles.setName(request.getParameter("name"));
			projCellAndFiles.setDefId(request.getParameter("defId"));
			projCellAndFiles.setUseId(request.getParameter("useId"));
			projCellAndFiles.setIntPage0(RequestUtils.getInt(request,
					"intPage0"));
			projCellAndFiles.setIntPage1(RequestUtils.getInt(request,
					"intPage1"));
			projCellAndFiles.setIntPage2(RequestUtils.getInt(request,
					"intPage2"));
			projCellAndFiles.setIntFinish(RequestUtils.getInt(request,
					"intFinish"));

			this.projCellAndFilesService.save(projCellAndFiles);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.projCellAndFilesService")
	public void setProjCellAndFilesService(
			ProjCellAndFilesService projCellAndFilesService) {
		this.projCellAndFilesService = projCellAndFilesService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ProjCellAndFiles projCellAndFiles = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			projCellAndFiles = projCellAndFilesService
					.getProjCellAndFiles(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (projCellAndFiles != null) {
			result = projCellAndFiles.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", projCellAndFiles.getId());
			result.put("projCellAndFilesId", projCellAndFiles.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
