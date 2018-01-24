package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.DotUse;
import com.glaf.isdp.query.DotUseQuery;
import com.glaf.isdp.service.DotUseService;
import com.glaf.isdp.service.FileDotService;
import com.glaf.isdp.util.DotUseDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/dotUse")
public class DotUseResource {
	protected static final Log logger = LogFactory.getLog(DotUseResource.class);

	protected DotUseService dotUseService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("fileIDs");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				dotUseService.deleteByIds(ids);
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
		dotUseService.deleteById(request.getParameter("fileID"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DotUseQuery query = new DotUseQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		DotUseDomainFactory.processDataRequest(dataRequest);

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
		int total = dotUseService.getDotUseCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DotUse> list = dotUseService.getDotUsesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DotUse dotUse : list) {
					JSONObject rowJSON = dotUse.toJsonObject();
					rowJSON.put("id", dotUse.getFileID());
					rowJSON.put("dotUseId", dotUse.getFileID());
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
		DotUseQuery query = new DotUseQuery();
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
		int total = dotUseService.getDotUseCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DotUse> list = dotUseService.getDotUsesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DotUse dotUse : list) {
					JSONObject rowJSON = dotUse.toJsonObject();
					rowJSON.put("id", dotUse.getFileID());
					rowJSON.put("dotUseId", dotUse.getFileID());
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
	@Path("/saveDotUse")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveDotUse(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DotUse dotUse = new DotUse();
		try {
			Tools.populate(dotUse, params);

			dotUse.setIndexId(RequestUtils.getInt(request, "indexId"));
			dotUse.setProjId(RequestUtils.getInt(request, "projId"));
			dotUse.setPid(RequestUtils.getInt(request, "pid"));
			dotUse.setDotId(request.getParameter("dotId"));
			dotUse.setNum(request.getParameter("num"));
			dotUse.setName(request.getParameter("name"));
			dotUse.setCman(request.getParameter("cman"));
			dotUse.setCtime(RequestUtils.getDate(request, "ctime"));
			dotUse.setFileName(request.getParameter("fileName"));
			// dotUse.setFileContent(request.getParameter("fileContent"));
			dotUse.setFileSize(RequestUtils.getInt(request, "fileSize"));
			dotUse.setVision(request.getParameter("vision"));
			dotUse.setSaveTime(request.getParameter("saveTime"));
			dotUse.setRemark(request.getParameter("remark"));
			dotUse.setDwid(RequestUtils.getInt(request, "dwid"));
			dotUse.setFbid(RequestUtils.getInt(request, "fbid"));
			dotUse.setFxid(RequestUtils.getInt(request, "fxid"));
			dotUse.setJid(request.getParameter("jid"));
			dotUse.setFlid(request.getParameter("flid"));
			dotUse.setTopNode(request.getParameter("topNode"));
			dotUse.setTopId(request.getParameter("topId"));
			dotUse.setType(RequestUtils.getInt(request, "type"));
			dotUse.setFname(request.getParameter("fname"));
			dotUse.setIsInk(request.getParameter("isInk"));
			dotUse.setOldId(request.getParameter("oldId"));
			dotUse.setTaskId(request.getParameter("taskId"));
			dotUse.setIsCheck(RequestUtils.getInt(request, "isCheck"));

			this.dotUseService.save(dotUse);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.dotUseService")
	public void setDotUseService(DotUseService dotUseService) {
		this.dotUseService = dotUseService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		DotUse dotUse = null;
		if (StringUtils.isNotEmpty(request.getParameter("fileID"))) {
			dotUse = dotUseService.getDotUse(request.getParameter("fileID"));
		}
		JSONObject result = new JSONObject();
		if (dotUse != null) {
			result = dotUse.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", dotUse.getFileID());
			result.put("dotUseId", dotUse.getFileID());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@Resource
	private FileDotService fileDotService;

	@GET
	@POST
	@Path("/showCellContent")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] showCellContent(@Context HttpServletRequest request)
			throws Exception {
		String currentSystemName = Environment.getCurrentSystemName();
		String systemName = request.getParameter("systemName");
		if (StringUtils.isEmpty(systemName)) {
			systemName = Environment.DEFAULT_SYSTEM_NAME;
		}
		Environment.setCurrentSystemName(systemName);

		byte[] fileContent = null;
		try {
			String fileID = request.getParameter("fileID");
			logger.debug("查询cell文件:fileID=" + fileID);

			DotUse dotUse = dotUseService.getDotUse(fileID);

			if (dotUse != null) {
				fileContent = dotUse.getFileContent();
				return fileContent;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return fileContent;
	}
}
