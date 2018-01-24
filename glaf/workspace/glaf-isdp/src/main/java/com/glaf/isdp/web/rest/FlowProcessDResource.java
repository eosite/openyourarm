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
import com.glaf.isdp.domain.FlowProcessD;
import com.glaf.isdp.query.FlowProcessDQuery;
import com.glaf.isdp.service.FlowProcessDService;
import com.glaf.isdp.util.FlowProcessDDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/flowProcessD")
public class FlowProcessDResource {
	protected static final Log logger = LogFactory
			.getLog(FlowProcessDResource.class);

	protected FlowProcessDService flowProcessDService;

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
				flowProcessDService.deleteByIds(ids);
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
		flowProcessDService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FlowProcessDQuery query = new FlowProcessDQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FlowProcessDDomainFactory.processDataRequest(dataRequest);

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
		int total = flowProcessDService
				.getFlowProcessDCountByQueryCriteria(query);
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
			List<FlowProcessD> list = flowProcessDService
					.getFlowProcessDsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FlowProcessD flowProcessD : list) {
					JSONObject rowJSON = flowProcessD.toJsonObject();
					rowJSON.put("id", flowProcessD.getId());
					rowJSON.put("flowProcessDId", flowProcessD.getId());
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
		FlowProcessDQuery query = new FlowProcessDQuery();
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
		int total = flowProcessDService
				.getFlowProcessDCountByQueryCriteria(query);
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
			List<FlowProcessD> list = flowProcessDService
					.getFlowProcessDsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FlowProcessD flowProcessD : list) {
					JSONObject rowJSON = flowProcessD.toJsonObject();
					rowJSON.put("id", flowProcessD.getId());
					rowJSON.put("flowProcessDId", flowProcessD.getId());
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
	@Path("/saveFlowProcessD")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveFlowProcessD(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FlowProcessD flowProcessD = new FlowProcessD();
		try {
			Tools.populate(flowProcessD, params);

			flowProcessD.setMainId(request.getParameter("mainId"));
			flowProcessD.setFileId(request.getParameter("fileId"));
			flowProcessD.setName(request.getParameter("name"));
			flowProcessD.setContent(request.getParameter("content"));
			flowProcessD.setActor(request.getParameter("actor"));
			flowProcessD.setCtime(RequestUtils.getDate(request, "ctime"));
			flowProcessD.setVersion(request.getParameter("version"));
			flowProcessD.setTcadFile(request.getParameter("tcadFile"));
			flowProcessD.setIsSave(RequestUtils.getInt(request, "isSave"));
			flowProcessD.setIntFlag(RequestUtils.getInt(request, "intFlag"));

			this.flowProcessDService.save(flowProcessD);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.flowProcessDService")
	public void setFlowProcessDService(FlowProcessDService flowProcessDService) {
		this.flowProcessDService = flowProcessDService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FlowProcessD flowProcessD = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			flowProcessD = flowProcessDService.getFlowProcessD(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (flowProcessD != null) {
			result = flowProcessD.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", flowProcessD.getId());
			result.put("flowProcessDId", flowProcessD.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
