package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.CellUpicInfo;
import com.glaf.isdp.domain.IsdpDataFile;
import com.glaf.isdp.domain.TreeFolder;
import com.glaf.isdp.query.CellUpicInfoQuery;
import com.glaf.isdp.query.TreeFolderQuery;
import com.glaf.isdp.service.ICellUpicInfoService;
import com.glaf.isdp.service.ITreeFolderService;

@Controller
@Path("/rs/isdp/cellUpicInfo")
public class CellUpicInfoResource {
	protected static final Log logger = LogFactory
			.getLog(CellUpicInfoResource.class);

	protected ICellUpicInfoService cellUpicInfoService;

	protected IFieldInterfaceService fieldInterfaceService;
	
	protected ITreeFolderService treeFolderService;
	@POST
	@Path("/deleteAll")
	public void deleteAll(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellUpicInfoService.deleteByIds(ids);
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/deleteAll/{rowIds}")
	public void deleteAll(@PathParam("rowIds") String rowIds,
			@Context UriInfo uriInfo) {
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellUpicInfoService.deleteByIds(ids);
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete")
	public void deleteById(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String cellUpicInfoId = request.getParameter("cellUpicInfoId");
		if (StringUtils.isEmpty(cellUpicInfoId)) {
			cellUpicInfoId = request.getParameter("id");
		}
		if (cellUpicInfoId != null) {
			cellUpicInfoService.deleteById(cellUpicInfoId);
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{cellUpicInfoId}")
	public void deleteById(@PathParam("cellUpicInfoId") String cellUpicInfoId,
			@Context UriInfo uriInfo) {
		if (cellUpicInfoId != null) {
			cellUpicInfoService.deleteById(cellUpicInfoId);
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@POST
	@Path("/download")
	public void download(@Context HttpServletRequest request,
			@Context HttpServletResponse response, @Context UriInfo uriInfo) {
		String cellUpicInfoId = request.getParameter("cellUpicInfoId");
		if (StringUtils.isEmpty(cellUpicInfoId)) {
			cellUpicInfoId = request.getParameter("id");
		}
		if (cellUpicInfoId != null) {
			IsdpDataFile dataFile = cellUpicInfoService
					.getCellDataFileById(cellUpicInfoId);
			if (dataFile != null && dataFile.getFileName() != null
					&& dataFile.getFileContent() != null) {
				try {
					ResponseUtils.download(request, response,
							dataFile.getFileContent(), dataFile.getFileName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) {
		int treeFolderIndexId = RequestUtils.getInt(request,
				"treeFolderIndexId");
		String includesChildren = RequestUtils.getString(request,"ic","false");
		JSONObject result = new JSONObject();
		CellUpicInfoQuery  query = new CellUpicInfoQuery();
		if(treeFolderIndexId == 0 && RequestUtils.getString(request, "searchWord", null) == null ){
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
			try {
				return result.toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return ResponseUtils.responseJsonResult(false);
			}
		}else if(treeFolderIndexId == 0){
			
		}else{
			if("true".equalsIgnoreCase(includesChildren)){ //是级联
				TreeFolderQuery treeFolderQuery = new TreeFolderQuery();
				treeFolderQuery.parentId((long)treeFolderIndexId);
				List<TreeFolder> treeFolders = treeFolderService.list(treeFolderQuery);
				List<Integer> indexIds = new ArrayList<Integer>(); 
				for (TreeFolder treeFolder : treeFolders) {
					indexIds.add(treeFolder.getIndexId());
				}
				indexIds.add(treeFolderIndexId);
				query.setIndexIds(indexIds);
			}else{
				query.indexId(treeFolderIndexId);
			}
		}
		query.setTnameLike(RequestUtils.getString(request, "searchWord", null));
		//query.setActorId(actorId);
		
		int total  = cellUpicInfoService.getCellUpicInfoCountByQueryCriteria(query);
        int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;
		int pageNo = RequestUtils.getInt(request, "page", 1);//dataRequest.getPage();
		limit = RequestUtils.getInt(request, "rows", 15);//dataRequest.getPageSize();
		start = (pageNo - 1) * limit;
		if (start < 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}
	
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);
			List<CellUpicInfo> list = cellUpicInfoService.getCellUpicInfosByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				
				result.put("rows", rowsJSON);
				int sortNo = 0;
				for (CellUpicInfo cellUpicInfo : list) {
					JSONObject rowJSON = (JSONObject) JSON.toJSON(cellUpicInfo);
					rowJSON.put("sortNo", ++sortNo);
					rowJSON.put("id", cellUpicInfo.getId());
					rowJSON.put("cellUpicInfoId", cellUpicInfo.getId());
					rowJSON.put("ctime", org.apache.commons.lang3.time.DateFormatUtils.format(cellUpicInfo.getCtime(), "yyyy-MM-dd"));
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
					
				}
				
			}
		}else{
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		try {
			return result.toJSONString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
		
//		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
//		ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
//		List<CellUpicInfo> list = cellUpicInfoService.getCellUpicInfos(query);
//		if (list != null && !list.isEmpty()) {
//			int sortNo = 0;
//			for (CellUpicInfo cellUpicInfo : list) {
//				sortNo++;
//				ObjectNode objectNode = cellUpicInfo.toObjectNode();
//				objectNode.put("sortNo", sortNo);
//				rowsJSON.add(objectNode);
//			}
//			responseJSON.put("total", list.size());
//		}
//
//		responseJSON.set("rows", rowsJSON);
//		try {
//			return responseJSON.toString().getBytes("UTF-8");
//		} catch (IOException e) {
//			return responseJSON.toString().getBytes();
//		}
	}

	@GET
	@POST
	@Path("/pagination")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] pagination(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellUpicInfoQuery query = new CellUpicInfoQuery();
		Tools.populate(query, params);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
		if ("easyui".equals(gridType)) {
			int pageNo = ParamUtils.getInt(params, "page");
			limit = ParamUtils.getInt(params, "rows");
			start = (pageNo - 1) * limit;
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "order");
		} else if ("extjs".equals(gridType)) {
			start = ParamUtils.getInt(params, "start");
			limit = ParamUtils.getInt(params, "limit");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		} else if ("yui".equals(gridType)) {
			start = ParamUtils.getInt(params, "startIndex");
			limit = ParamUtils.getInt(params, "results");
			orderName = ParamUtils.getString(params, "sort");
			order = ParamUtils.getString(params, "dir");
		}

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		int total = cellUpicInfoService
				.getCellUpicInfoCountByQueryCriteria(query);
		if (total > 0) {
			responseJSON.put("total", total);
			responseJSON.put("totalCount", total);
			responseJSON.put("totalRecords", total);
			responseJSON.put("start", start);
			responseJSON.put("startIndex", start);
			responseJSON.put("limit", limit);
			responseJSON.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder("desc");
				}
			}

			List<CellUpicInfo> list = cellUpicInfoService
					.getCellUpicInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.set("records", rowsJSON);
				} else {
					responseJSON.set("rows", rowsJSON);
				}

				for (CellUpicInfo cellUpicInfo : list) {
					rowsJSON.add(cellUpicInfo.toObjectNode());
				}
			}
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/save")
	public void saveCellUpicInfo(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String cellUpicInfoId = request.getParameter("cellUpicInfoId");
		if (StringUtils.isEmpty(cellUpicInfoId)) {
			cellUpicInfoId = request.getParameter("id");
		}
		CellUpicInfo cellUpicInfo = null;
		if (StringUtils.isNotEmpty(cellUpicInfoId)) {
			cellUpicInfo = cellUpicInfoService.getCellUpicInfo(cellUpicInfoId);
		}

		if (cellUpicInfo == null) {
			cellUpicInfo = new CellUpicInfo();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(cellUpicInfo, params);

		this.cellUpicInfoService.save(cellUpicInfo);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellUpicInfoService")
	public void setCellUpicInfoService(ICellUpicInfoService cellUpicInfoService) {
		this.cellUpicInfoService = cellUpicInfoService;
	}

	@javax.annotation.Resource
	public void setFieldInterfaceService(
			IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}
	
	@javax.annotation.Resource
	public void setTreeFolderService(ITreeFolderService treeFolderService) {
		this.treeFolderService = treeFolderService;
	}

	@GET
	@POST
	@Path("/view/{cellUpicInfoId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("cellUpicInfoId") String cellUpicInfoId,
			@Context UriInfo uriInfo) {
		CellUpicInfo cellUpicInfo = null;
		if (StringUtils.isNotEmpty(cellUpicInfoId)) {
			cellUpicInfo = cellUpicInfoService.getCellUpicInfo(cellUpicInfoId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (cellUpicInfo != null) {
			responseJSON = cellUpicInfo.toObjectNode();
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
}
