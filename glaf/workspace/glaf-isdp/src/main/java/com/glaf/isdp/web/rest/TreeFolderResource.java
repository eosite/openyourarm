package com.glaf.isdp.web.rest;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.helper.JacksonTreeHelper;
import com.glaf.base.modules.sys.model.ITree;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.TreeFolder;
import com.glaf.isdp.query.TreeFolderQuery;
import com.glaf.isdp.service.ITreeFolderService;

@Controller
@Path("/rs/isdp/treeFolder")
public class TreeFolderResource {
	protected static final Log logger = LogFactory
			.getLog(TreeFolderResource.class);

	protected ITreeFolderService treeFolderService;
	
	
	/**
	 * 获取书
	 * @param request
	 * @return
	 */
	@GET
	@POST
	@Path("/dataLibTreeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] dataLibTreeJson(@Context HttpServletRequest request) {
		
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		
		int inttype = RequestUtils.getInt(request, "inttype");
		TreeFolderQuery query = new TreeFolderQuery();
		query.inttype(inttype);
		long parentId = RequestUtils.getLong(request, "pid",0);
		if(parentId>0){
			query.setParentId(parentId);
		}
		List<TreeFolder> treeFolders = treeFolderService.list(query);
		List<ITree> treeModels = new ArrayList<ITree>();
		for (TreeFolder row : treeFolders) {
			treeModels.add(row);
			// logger.debug(row.toJsonObject().toJSONString());
		}
		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode responseJSON = treeHelper.getTreeArrayNode(treeModels);
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@POST
	@Path("/deleteAll")
	public void deleteAll(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				// treeFolderService.deleteByIds(ids);
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
				// treeFolderService.deleteByIds(ids);
			}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete")
	public void deleteById(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String treeFolderId = request.getParameter("treeFolderId");
		if (StringUtils.isEmpty(treeFolderId)) {
			treeFolderId = request.getParameter("id");
		}
		if (treeFolderId != null) {
			treeFolderService.deleteById(treeFolderId);
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@POST
	@Path("/delete/{treeFolderId}")
	public void deleteById(@PathParam("treeFolderId") String treeFolderId,
			@Context UriInfo uriInfo) {
		if (treeFolderId != null) {
			treeFolderService.deleteById(treeFolderId);
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@POST
	@Path("/json")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] json(@Context HttpServletRequest request) {
		int cellTreedotIndexId = RequestUtils.getInt(request,
				"cellTreedotIndexId");
		List<TreeFolder> treeFolders = treeFolderService.getTreeFolders(24,
				cellTreedotIndexId);
		List<ITree> treeModels = new ArrayList<ITree>();
		for (TreeFolder row : treeFolders) {
			treeModels.add(row);
			// logger.debug(row.toJsonObject().toJSONString());
		}
		JacksonTreeHelper treeHelper = new JacksonTreeHelper();
		ArrayNode responseJSON = treeHelper.getTreeArrayNode(treeModels);
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeFolderQuery query = new TreeFolderQuery();
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
		int total = treeFolderService.getTreeFolderCountByQueryCriteria(query);
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

			List<TreeFolder> list = treeFolderService
					.getTreeFoldersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				ArrayNode rowsJSON = new ObjectMapper().createArrayNode();
				if ("yui".equals(gridType)) {
					responseJSON.set("records", rowsJSON);
				} else {
					responseJSON.set("rows", rowsJSON);
				}

				for (TreeFolder treeFolder : list) {
					rowsJSON.add(treeFolder.toObjectNode());
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
	public void saveTreeFolder(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) {
		String treeFolderId = request.getParameter("treeFolderId");
		if (StringUtils.isEmpty(treeFolderId)) {
			treeFolderId = request.getParameter("id");
		}
		TreeFolder treeFolder = null;
		if (StringUtils.isNotEmpty(treeFolderId)) {
			treeFolder = treeFolderService.getTreeFolderById(treeFolderId);
		}

		if (treeFolder == null) {
			treeFolder = new TreeFolder();
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Tools.populate(treeFolder, params);

		this.treeFolderService.save(treeFolder);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.treeFolderService")
	public void setTreeFolderService(ITreeFolderService treeFolderService) {
		this.treeFolderService = treeFolderService;
	}

	@GET
	@POST
	@Path("/view/{treeFolderId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("treeFolderId") String treeFolderId,
			@Context UriInfo uriInfo) {
		TreeFolder treeFolder = null;
		if (StringUtils.isNotEmpty(treeFolderId)) {
			treeFolder = treeFolderService.getTreeFolderById(treeFolderId);
		}
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		if (treeFolder != null) {
			responseJSON = treeFolder.toObjectNode();
		}
		try {
			return responseJSON.toString().getBytes("UTF-8");
		} catch (IOException e) {
			return responseJSON.toString().getBytes();
		}
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param uriInfo
	 */
	@GET
	@POST
	@Path("/download")
	public void download(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TreeFolderQuery query = new TreeFolderQuery();
		query.inttype(0);
		query.setOrderBy("num");
		List<TreeFolder> treeFolders = treeFolderService.list(query);
		StringBuilder sb = new StringBuilder();
		sb.append("资料分类表\r\n");
		sb.append("格式：编号+空格//名称//文件分类号//著录项类别（0文书资料1声像资料2照片资料3图纸资料4其他资料）//分类描述(&表示回车)//密级//保管期限\r\n\r\n");
		if(treeFolders!=null){
				for (TreeFolder treeFolder : treeFolders) {
					sb.append(treeFolder.getIndexName()); // 编号+空格//名称
					sb.append("//");
					if(treeFolder.getFilenum() != null ){
						sb.append(treeFolder.getFilenum());//  文件分类号
					}
					sb.append("//");
					sb.append(treeFolder.getZtype()); // 著录项类别
					sb.append("//");
					if(treeFolder.getContent() != null){
						sb.append(treeFolder.getContent().replace("\r\n", "&")); // 分类描述(&表示回车)
					}
					sb.append("//");
					if(treeFolder.getSlevel() != null){
						sb.append(treeFolder.getSlevel()); //密级
					}
					sb.append("//");
					if(treeFolder.getSavetime() != null){
						sb.append(treeFolder.getSavetime()); //保管期限
					}
					sb.append("//\r\n");
				}
				try {
					ResponseUtils.download(request, response,sb.toString().getBytes("GBK"), "资料分类表.txt");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
