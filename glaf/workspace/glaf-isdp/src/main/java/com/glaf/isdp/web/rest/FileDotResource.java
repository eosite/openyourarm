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
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.FileDot;
import com.glaf.isdp.query.FileDotQuery;
import com.glaf.isdp.service.FileDotService;
import com.glaf.isdp.util.FileDotDomainFactory;
import com.glaf.isdp.util.JSONConvertUtil;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/fileDot")
public class FileDotResource {
	protected static final Log logger = LogFactory
			.getLog(FileDotResource.class);

	protected FileDotService fileDotService;

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
				fileDotService.deleteByIds(ids);
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
		fileDotService.deleteById(request.getParameter("fileID"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileDotQuery query = new FileDotQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FileDotDomainFactory.processDataRequest(dataRequest);

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
		int total = fileDotService.getFileDotCountByQueryCriteria(query);
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
			List<FileDot> list = fileDotService.getFileDotsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileDot fileDot : list) {
					JSONObject rowJSON = fileDot.toJsonObject();
					rowJSON.put("id", fileDot.getFileID());
					rowJSON.put("fileDotId", fileDot.getFileID());
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
		FileDotQuery query = new FileDotQuery();
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
		int total = fileDotService.getFileDotCountByQueryCriteria(query);
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
			List<FileDot> list = fileDotService.getFileDotsByQueryCriteria(
					start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FileDot fileDot : list) {
					JSONObject rowJSON = fileDot.toJsonObject();
					rowJSON.put("id", fileDot.getFileID());
					rowJSON.put("fileDotId", fileDot.getFileID());
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
	@Path("/saveFileDot")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveFileDot(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileDot fileDot = new FileDot();
		try {
			Tools.populate(fileDot, params);

			fileDot.setListId(request.getParameter("listId"));
			fileDot.setIndexId(RequestUtils.getInt(request, "indexId"));
			fileDot.setFbelong(request.getParameter("fbelong"));
			fileDot.setFnum(request.getParameter("fnum"));
			fileDot.setPbelong(request.getParameter("pbelong"));
			fileDot.setNum(request.getParameter("num"));
			fileDot.setFname(request.getParameter("fname"));
			fileDot.setDotName(request.getParameter("dotName"));
			fileDot.setCtime(RequestUtils.getDate(request, "ctime"));
			fileDot.setDtime(RequestUtils.getDate(request, "dtime"));
			fileDot.setFileName(request.getParameter("fileName"));
			fileDot.setFileSize(RequestUtils.getInt(request, "fileSize"));
			fileDot.setDwid(RequestUtils.getInt(request, "dwid"));
			fileDot.setFbid(RequestUtils.getInt(request, "fbid"));
			fileDot.setFxid(RequestUtils.getInt(request, "fxid"));
			fileDot.setJid(request.getParameter("jid"));
			fileDot.setFlid(request.getParameter("flid"));
			fileDot.setTopNode(request.getParameter("topNode"));
			fileDot.setCman(request.getParameter("cman"));
			fileDot.setContent(request.getParameter("content"));
			fileDot.setListFlag(request.getParameter("listFlag"));
			fileDot.setToFile(RequestUtils.getInt(request, "toFile"));
			fileDot.setIsInk(request.getParameter("isInk"));
			fileDot.setDotType(RequestUtils.getInt(request, "dotType"));
			fileDot.setCtimeDName(request.getParameter("ctimeDName"));
			fileDot.setType(RequestUtils.getInt(request, "type"));
			fileDot.setListNo(RequestUtils.getInt(request, "listNo"));
			fileDot.setUtreeIndex(RequestUtils.getInt(request, "utreeIndex"));
			fileDot.setIsQuan(request.getParameter("isQuan"));
			fileDot.setIntSysForm(RequestUtils.getInt(request, "intSysForm"));

			this.fileDotService.save(fileDot);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.fileDotService")
	public void setFileDotService(FileDotService fileDotService) {
		this.fileDotService = fileDotService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FileDot fileDot = null;
		if (StringUtils.isNotEmpty(request.getParameter("fileID"))) {
			fileDot = fileDotService.getFileDot(request.getParameter("fileID"));
		}
		JSONObject result = new JSONObject();
		if (fileDot != null) {
			result = fileDot.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", fileDot.getFileID());
			result.put("fileDotId", fileDot.getFileID());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 根据treedot的index_id获取数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getFileDotByIndexId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getFileDotByIndexId(@Context HttpServletRequest request) throws IOException {
		int indexId = RequestUtils.getInt(request, "indexId");//treedot的index_id
		String systemName = RequestUtils.getString(request, "systemName", Environment.DEFAULT_SYSTEM_NAME);
		Environment.setCurrentSystemName(systemName);
		
		int start = 0;
		int pagesize = 100;
		
		int pageNo = RequestUtils.getInt(request, "page", 1);
		pagesize = RequestUtils.getInt(request, "pageSize", 100);
		start = (pageNo - 1) * pagesize;
		
		FileDotQuery query = new FileDotQuery();
		query.setIndexId(indexId);
		
		int total = fileDotService.getFileDotCountByQueryCriteria(query);
		List<FileDot> list = fileDotService.getFileDotsByQueryCriteria(start, pagesize, query);
		
		JSONObject result = new JSONObject();
		result.put("total", total);
		try {
			
			JSONArray rows = new JSONArray();

			for(FileDot model : list){
				JSONObject jobject = JSONConvertUtil.toJSONObject(model);
				jobject.put("tableNameCN", model.getDotName());
				jobject.put("tableId", model.getFileID());
				rows.add(jobject);
			}
			
			result.put("rows", rows);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toJSONString().getBytes("UTF-8");
	}
}
