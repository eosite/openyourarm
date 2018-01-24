package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.Treedot;
import com.glaf.base.modules.sys.service.ITreedotService;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.domain.TreeDot;
import com.glaf.isdp.query.CellDataTableQuery;
import com.glaf.isdp.query.TreeDotQuery;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.TreeDotService;
import com.glaf.isdp.util.CellDataTableJsonFactory;
import com.glaf.isdp.util.JSONConvertUtil;
import com.glaf.isdp.util.TreeDotDomainFactory;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/isdp/treeDot")
public class TreeDotResource {
	protected static final Log logger = LogFactory.getLog(TreeDotResource.class);

	protected TreeDotService treeDotService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("indexIds");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				treeDotService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		treeDotService.deleteById(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeDotQuery query = new TreeDotQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TreeDotDomainFactory.processDataRequest(dataRequest);

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
		int total = treeDotService.getTreeDotCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
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
			List<TreeDot> list = treeDotService.getTreeDotsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeDot treeDot : list) {
					JSONObject rowJSON = treeDot.toJsonObject();
					rowJSON.put("id", treeDot.getId());
					rowJSON.put("treeDotId", treeDot.getId());
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
		TreeDotQuery query = new TreeDotQuery();
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
		int total = treeDotService.getTreeDotCountByQueryCriteria(query);
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
			List<TreeDot> list = treeDotService.getTreeDotsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeDot treeDot : list) {
					JSONObject rowJSON = treeDot.toJsonObject();
					rowJSON.put("id", treeDot.getId());
					rowJSON.put("treeDotId", treeDot.getId());
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
	@Path("/saveTreeDot")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTreeDot(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeDot treeDot = new TreeDot();
		try {
			Tools.populate(treeDot, params);

			treeDot.setId(request.getParameter("id"));
			treeDot.setParentId(RequestUtils.getInt(request, "parentId"));
			treeDot.setIndexName(request.getParameter("indexName"));
			treeDot.setNlevel(RequestUtils.getInt(request, "nlevel"));
			treeDot.setNum(request.getParameter("num"));
			treeDot.setContent(request.getParameter("content"));
			treeDot.setSindexName(request.getParameter("sindexName"));
			treeDot.setNodeIco(RequestUtils.getInt(request, "nodeIco"));
			treeDot.setListNo(RequestUtils.getInt(request, "listNo"));
			treeDot.setMenuId(RequestUtils.getInt(request, "menuId"));
			treeDot.setIsEnd(RequestUtils.getInt(request, "isEnd"));
			treeDot.setSysMenuId(request.getParameter("sysMenuId"));
			treeDot.setType(RequestUtils.getInt(request, "type"));
			treeDot.setFileNumId(request.getParameter("fileNumId"));
			treeDot.setFileNumId2(request.getParameter("fileNumId2"));
			treeDot.setProjIndex(RequestUtils.getInt(request, "projIndex"));
			treeDot.setDomainIndex(RequestUtils.getInt(request, "domainIndex"));

			this.treeDotService.save(treeDot);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.treeDotService")
	public void setTreeDotService(TreeDotService treeDotService) {
		this.treeDotService = treeDotService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TreeDot treeDot = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			treeDot = treeDotService.getTreeDot(RequestUtils.getInt(request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (treeDot != null) {
			result = treeDot.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", treeDot.getId());
			result.put("treeDotId", treeDot.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 查询Cell表分类
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getTreeDotByParentId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getTreeDotByParentId(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		int parentId = RequestUtils.getInt(request, "indexId", -1);
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		Environment.setCurrentSystemName(systemName);

		List<TreeDot> list = treeDotService.getTreeDotsAndChildCountByParentId(parentId);

		JSONArray result = new JSONArray();
		try {
			for (TreeDot model : list) {
				JSONObject jobject = JSONConvertUtil.toJSONObject(model);
				jobject.put("hasChildren",
						model.getChildrenCount() != null && model.getChildrenCount() > 0 ? true : false);
				jobject.put("isParent",
						model.getChildrenCount() != null && model.getChildrenCount() > 0 ? true : false);
				result.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Environment.setCurrentSystemName(currentSystemName);
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 查询Cell表分类(更改后)
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getTreeDotByParentIdInit")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getTreeDotByParentIdInit(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		int parentId = RequestUtils.getInt(request, "indexId", -1);
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		Environment.setCurrentSystemName(systemName);

		List<TreeDot> list = treeDotService.getTreeDotsAndChildCountByParentId(parentId);

		JSONArray result = new JSONArray();
		try {
			for (TreeDot model : list) {
				JSONObject jobject = JSONConvertUtil.toJSONObject(model);

				boolean isParent = model.getChildrenCount() != null && model.getChildrenCount() > 0 ? true : false;

				if (!isParent) {
					isParent = true;
					JSONArray children = this.getTreeDotChildrenByIndexId(model.getIndexId());
					if (children != null && children.size() > 0) {
						jobject.put("children", children);
					}
				}

				jobject.put("hasChildren", isParent);
				jobject.put("isParent", isParent);
				result.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Environment.setCurrentSystemName(currentSystemName);
		return result.toJSONString().getBytes("UTF-8");
	}

	@Autowired
	protected ITreedotService treedotService;
	@Autowired
	protected CellDataTableService cellDataTableService;

	private JSONArray getTreeDotChildrenByIndexId(int parentId) {

		CellDataTableQuery query = new CellDataTableQuery();
		// Cell表查询
		List<Treedot> treedotList = treedotService.getAllChildrenTreedotByIndexId(parentId);
		List<String> ids = new ArrayList<String>();
		ids.add(String.valueOf(parentId));
		for (Treedot treedot : treedotList) {
			ids.add(String.valueOf(treedot.getIndexId()));
		}
		String sqlCondition = QueryUtils.getSQLCondition(ids, "fileDot", "index_id");
		query.setSqlCondition(sqlCondition);

		List<CellDataTable> list = cellDataTableService.getCellDataTablesByTreedotIndexId(0, 1000, query);
		JSONArray rows = null;
		if (list != null && list.size() > 0) {
			rows = new JSONArray();
			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("indexName", model.getName());
				jobject.put("tableId", model.getId());
				if (parentId > -1) {
					jobject.put("parentId", parentId);
				}
				rows.add(jobject);
			}
		}

		return rows;
	}

	/**
	 * 查询Cell表分类(更改后)
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getTreeDotInit")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getTreeDotInit(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		// int parentId = RequestUtils.getInt(request, "indexId", -1);
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		Environment.setCurrentSystemName(systemName);

		// 根据parentId=-1查询
		List<TreeDot> pModels = treeDotService.getTreeDotsAndChildCountByParentId(-1);

		JSONArray resultArray = new JSONArray();
		try {

			if (pModels != null && !pModels.isEmpty()) {

				List<TreeDot> treedots = null;
				for (TreeDot pmodel : pModels) {
					treedots = treeDotService.getTreeDotsAndChildCountByIdLike(pmodel.getId() + "%");

					List<Integer> treedotIndexIds = new ArrayList<Integer>();
					for (TreeDot model : treedots) {
						boolean isParent = model.getChildrenCount() != null && model.getChildrenCount() > 0 ? true
								: false;
						
						if(model.getIndexId().intValue() == pmodel.getIndexId().intValue()){
							continue;
						}

						if (!isParent) {
							treedotIndexIds.add(model.getIndexId());
						}

						buildModelToArray(resultArray, model.getParentId(), model);
					}
					JSONArray array = getTreeDotChildrenByIndexIds(treedotIndexIds);
					resultArray.addAll(array);

					buildModelToArray(resultArray, pmodel.getParentId(), pmodel);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return resultArray.toJSONString().getBytes("UTF-8");
	}

	private JSONArray getTreeDotChildrenByIndexIds(List<Integer> indexIds) {

		CellDataTableQuery query = new CellDataTableQuery();
		List<String> ids = new ArrayList<String>();
		ids.add("-1");
		for (Integer id : indexIds) {
			ids.add(String.valueOf(id));
		}
		String sqlCondition = QueryUtils.getSQLCondition(ids, "fileDot", "index_id");
		query.setSqlCondition(sqlCondition);
		
		int count = cellDataTableService.getCellDataTablesCountByTreedotIndexId(query);

		List<CellDataTable> list = cellDataTableService.getCellDataTablesByTreedotIndexId(0, count, query);
		JSONArray rows = new JSONArray();
		if (list != null && list.size() > 0) {
			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("indexName", model.getName());
				jobject.put("tableId", model.getId());
				jobject.put("parentId", model.getFileDotIndexId());
				rows.add(jobject);
			}
		}

		return rows;
	}

	private void buildModelToArray(JSONArray array, Integer parentId, Object model) throws Exception {
		JSONObject jobject = JSONConvertUtil.toJSONObject(model);
		jobject.put("parentId", parentId);

		if (model instanceof TreeDot) {
			TreeDot treedot = (TreeDot) model;
			boolean isParent = treedot.getChildrenCount() != null && treedot.getChildrenCount() > 0 ? true : false;

			if (isParent) {
				jobject.put("hasChildren", isParent);
				jobject.put("isParent", isParent);
			}
		}

		array.add(jobject);
	}

}
