package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.Treedot;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.base.modules.sys.service.ITreedotService;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
 
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.query.CellDataTableQuery;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.CellUTableTreeService;
import com.glaf.isdp.util.CellDataTableDomainFactory;
import com.glaf.isdp.util.CellDataTableJsonFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellDataTable")
public class CellDataTableResource {

	protected static final Log logger = LogFactory
			.getLog(CellDataTableResource.class);

	protected CellDataTableService cellDataTableService;

	protected CellUTableTreeService cellUTableTreeService;

	protected ITreedotService treedotService;

	protected IFieldInterfaceService fileInterfaceService;

	protected ITablePageService tablePageService;

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellDataTableService")
	public void setCellDataTableService(
			CellDataTableService cellDataTableService) {
		this.cellDataTableService = cellDataTableService;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellUTableTreeService")
	public void setCellUTableTreeService(
			CellUTableTreeService cellUTableTreeService) {
		this.cellUTableTreeService = cellUTableTreeService;
	}

	@javax.annotation.Resource
	public void setTreedotService(ITreedotService treedotService) {
		this.treedotService = treedotService;
	}

	@javax.annotation.Resource
	public void setFileInterfaceService(
			IFieldInterfaceService fileInterfaceService) {
		this.fileInterfaceService = fileInterfaceService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

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
				cellDataTableService.deleteByIds(ids);
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
		cellDataTableService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellDataTableQuery query = new CellDataTableQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellDataTableDomainFactory.processDataRequest(dataRequest);

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
		int total = cellDataTableService
				.getCellDataTableCountByQueryCriteria(query);
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
			List<CellDataTable> list = cellDataTableService
					.getCellDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellDataTable cellDataTable : list) {
					JSONObject rowJSON = cellDataTable.toJsonObject();
					rowJSON.put("id", cellDataTable.getId());
					rowJSON.put("cellDataTableId", cellDataTable.getId());
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
		CellDataTableQuery query = new CellDataTableQuery();
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
		int total = cellDataTableService
				.getCellDataTableCountByQueryCriteria(query);
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
			List<CellDataTable> list = cellDataTableService
					.getCellDataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellDataTable cellDataTable : list) {
					JSONObject rowJSON = cellDataTable.toJsonObject();
					rowJSON.put("id", cellDataTable.getId());
					rowJSON.put("cellDataTableId", cellDataTable.getId());
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
	@Path("/saveCellDataTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellDataTable(@Context HttpServletRequest request) {
		String currentSystemName = Environment.getCurrentSystemName();
		String systemName = RequestUtils.getString(request, "systemName",
				currentSystemName);
		Environment.setCurrentSystemName(systemName);

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellDataTable cellDataTable = new CellDataTable();
		try {
			Tools.populate(cellDataTable, params);

			cellDataTable.setId(request.getParameter("id"));
			cellDataTable.setTableName(request.getParameter("tableName"));
			cellDataTable.setName(request.getParameter("name"));
			cellDataTable.setAddType(RequestUtils.getInt(request, "addType"));
			cellDataTable.setMaxUser(RequestUtils.getInt(request, "maxUser"));
			cellDataTable.setMaxSys(RequestUtils.getInt(request, "maxSys"));
			cellDataTable.setUserId(request.getParameter("userId"));
			cellDataTable.setCtime(RequestUtils.getDate(request, "ctime"));
			cellDataTable.setContent(request.getParameter("content"));
			cellDataTable.setSysNum(request.getParameter("sysNum"));
			cellDataTable.setIsSubTable(request.getParameter("isSubTable"));
			cellDataTable.setTopId(request.getParameter("topId"));
			cellDataTable.setFileDotFileId(request
					.getParameter("fileDotFileId"));
			cellDataTable.setIndexId(RequestUtils.getInt(request, "indexId"));
			cellDataTable.setWinWidth(RequestUtils.getInt(request, "winWidth"));
			cellDataTable.setWinHeight(RequestUtils
					.getInt(request, "winHeight"));
			cellDataTable.setIntQuote(RequestUtils.getInt(request, "intQuote"));
			cellDataTable.setIntLineEdit(RequestUtils.getInt(request,
					"intLineEdit"));
			cellDataTable.setPrintFileId(request.getParameter("printFileId"));
			cellDataTable.setIntUseSTreeWBS(RequestUtils.getInt(request,
					"intUseSTreeWBS"));

			this.cellDataTableService.save(cellDataTable);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellDataTable cellDataTable = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			cellDataTable = cellDataTableService.getCellDataTable(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (cellDataTable != null) {
			result = cellDataTable.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellDataTable.getId());
			result.put("cellDataTableId", cellDataTable.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 检索
	 * 
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/searchTableName")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] searchTableName(@Context HttpServletRequest request)
			throws IOException {

		String currentSystemName = Environment.getCurrentSystemName();

		int indexId = RequestUtils.getInt(request, "indexId", -1);// cell_utabletree中的index_id
		boolean all = RequestUtils.getBoolean(request, "allChild");// 是否取出下级所有表

		String systemName = RequestUtils.getString(request, "systemName",
				currentSystemName);
		Environment.setCurrentSystemName(systemName);

		JSONObject result = new JSONObject();
		try {
			String keyword = RequestUtils.getString(request, "keyword", "");
			CellDataTableQuery query = new CellDataTableQuery();
			if (keyword != null && StringUtils.isNotEmpty(keyword)) {
				query.setNameLike(keyword);
			}
			if (!all) {
				query.setIndexId(indexId);
			} else {
				List<CellUTableTree> tempList = cellUTableTreeService
						.getAllChildCellUTableTrees(indexId);
				List<Integer> ids = new ArrayList<Integer>();
				for (CellUTableTree obj : tempList) {
					ids.add(obj.getIndexId());
				}
				query.setIndexIds(ids);
			}
			query.setOrderBy("id");

			int total = cellDataTableService
					.getCellDataTableCountByQueryCriteria(query);
			List<CellDataTable> list = cellDataTableService.list(query);
			result.put("total", total);

			JSONArray rows = new JSONArray();
			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory
						.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("tableId", model.getId());
				rows.add(jobject);
			}

			result.put("rows", rows);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据indexId取得数据表数据
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getTableListByIndexId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getTableListByIndexId(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		// 如果是普通表，index_id为cell_utabletree中的index_id
		// 如果是Cell表，index_id为treeDot的index_id
		int indexId = -1;
		boolean all = false;// 是否取出下级所有表
		String type = "1";// 是cell表还是普通表
		String systemName = currentSystemName;
		int start = 0, pageNo = 1, pagesize = PageResult.DEFAULT_PAGE_SIZE;

		all = RequestUtils.getBoolean(request, "allChild");
		type = RequestUtils.getString(request, "type","1");
		indexId = RequestUtils.getInt(request, "indexId");
		pagesize = RequestUtils.getInt(request, "rows",PageResult.DEFAULT_PAGE_SIZE);
		pageNo = RequestUtils.getInt(request, "page",1);
		systemName = RequestUtils.getString(request, "systemName",currentSystemName);
		
		Environment.setCurrentSystemName(systemName);
		start = (pageNo - 1) * pagesize;

		JSONObject result = new JSONObject();
		try {
			CellDataTableQuery query = null;
			List<CellDataTable> list = new ArrayList<CellDataTable>();

			String keyword = RequestUtils.getString(request, "keyword");
			int total = 0;
			switch (Integer.parseInt(type)) {
			case 1:
				query = new CellDataTableQuery();
				query.setSearchChildTable(true);
				// 普通表查询
				if (!all) {
					query.setIndexId(indexId);
				} else {
					List<CellUTableTree> tempList = cellUTableTreeService
							.getAllChildCellUTableTrees(indexId);
					List<String> ids = new ArrayList<String>();
					ids.add(String.valueOf(indexId));
					for (CellUTableTree obj : tempList) {
						ids.add(String.valueOf(obj.getIndexId()));
					}
					String sqlCondition = QueryUtils.getSQLCondition(ids, "E",
							"index_id");
					query.setSqlCondition(sqlCondition);
				}

				//keyword = modelMap.get("keyword");
				if (keyword != null
						&& StringUtils.isNotEmpty(keyword.toString())) {
					query.setSqlCondition(query.getSqlCondition()
							+ " and (E.name like '%" + keyword
							+ "%' or E.tablename like '%" + keyword + "%')");
				}

				query.setOrderBy("id");

				total = cellDataTableService
						.getCellDataTableCountAndChildTablesByQueryCriteria(query);
				result.put("total", total);

				list = cellDataTableService
						.getCellDataTablesAndChildTablesByQueryCriteria(start,
								pagesize, query);
				break;
			case 2:
				query = new CellDataTableQuery();
				// Cell表查询
				if (!all) {
					query.setTreedotIndexId(indexId);
				} else {
					List<Treedot> treedotList = treedotService
							.getAllChildrenTreedotByIndexId(indexId);
					List<String> ids = new ArrayList<String>();
					ids.add(String.valueOf(indexId));
					for (Treedot treedot : treedotList) {
						ids.add(String.valueOf(treedot.getIndexId()));
					}
					String sqlCondition = QueryUtils.getSQLCondition(ids,
							"fileDot", "index_id");
					query.setSqlCondition(sqlCondition);
				}

				//keyword = modelMap.get("keyword");
				if (keyword != null
						&& StringUtils.isNotEmpty(keyword.toString())) {
					query.setSqlCondition(query.getSqlCondition()
							+ " and (E.name like '%" + keyword
							+ "%' or E.tablename like '%" + keyword + "%')");
				}

				total = cellDataTableService
						.getCellDataTablesCountByTreedotIndexId(query);
				result.put("total", total);

				list = cellDataTableService.getCellDataTablesByTreedotIndexId(
						start, pagesize, query);
				break;
			case 3:
			case 5:
				query = new CellDataTableQuery();
				// 虚拟表查询
				query.setIndexId(indexId);
				query.setSqlCondition(" AND ISSUBTABLE='' ");

				//keyword = modelMap.get("keyword");
				if (keyword != null
						&& StringUtils.isNotEmpty(keyword.toString())) {
					query.setSqlCondition(query.getSqlCondition()
							+ " and (E.name like '%" + keyword
							+ "%' or E.tablename like '%" + keyword + "%')");
				}

				query.setOrderBy("id");

				total = cellDataTableService
						.getCellDataTableCountByQueryCriteria(query);
				result.put("total", total);

				list = cellDataTableService.getCellDataTablesByQueryCriteria(
						start, pagesize, query);
				break;
			case 4:
				// 扩展表查询
				//keyword = modelMap.get("keyword");
				String sql = "SELECT OBJECT_ID AS id,NAME,NAME AS tableName,CREATE_DATE AS ctime FROM SYS.TABLES  WHERE NAME NOT LIKE 'cell_useradd%'";
				if (keyword != null
						&& StringUtils.isNotEmpty(keyword.toString())) {
					sql += " AND NAME LIKE '%" + keyword + "%'";
				}

				total = tablePageService.getQueryCount("select count(*) from ("
						+ sql + ") t", new HashMap<String, Object>());
				result.put("total", total);
				List<Map<String, Object>> tableList = tablePageService
						.getListData(sql + " ORDER BY NAME", null, start,
								pagesize);
				CellDataTable table = null;
				for (Map<String, Object> map : tableList) {
					table = new CellDataTable();
					table.setId(map.get("id").toString());
					table.setTableName(map.get("tableName").toString());
					table.setName(map.get("NAME").toString());
					Date ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.parse(map.get("ctime").toString());
					table.setCtime(ctime);
					list.add(table);
				}
				break;
			default:
				break;
			}

			JSONArray rows = new JSONArray();
			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory
						.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("indexName", model.getName());
				jobject.put("tableId", model.getId());
				if(indexId > -1){
					
					jobject.put("parentId",indexId);
				}
				rows.add(jobject);
			}

			result.put("rows", rows);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取数据库所有用户表
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getDatabaseUserTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getDatabaseUserTable(@Context HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		JSONObject result = new JSONObject();
		try {
			int start = 0, pageNo = 1, pagesize = PageResult.DEFAULT_PAGE_SIZE;
			String systemName = currentSystemName;

			if (modelMap.containsKey("pageSize")) {
				pagesize = Integer
						.parseInt(modelMap.get("pageSize").toString());
			}

			if (modelMap.containsKey("page")) {
				pageNo = Integer.parseInt(modelMap.get("page").toString());
			}

			if (modelMap.containsKey("systemName")) {
				systemName = modelMap.get("systemName").toString();
			}
			Environment.setCurrentSystemName(systemName);
			start = (pageNo - 1) * pagesize;

			// 扩展表查询
			List<CellDataTable> list = new ArrayList<CellDataTable>();

			Object keyword = modelMap.get("keyword");
			String sql = "SELECT OBJECT_ID AS id,NAME,NAME AS tableName,CREATE_DATE AS ctime FROM SYS.TABLES  WHERE 1=1 ";// NAME
																															// NOT
																															// LIKE
																															// 'cell_useradd%'
			if (keyword != null && StringUtils.isNotEmpty(keyword.toString())) {
				sql += " AND NAME LIKE '%" + keyword + "%'";
			}

			int total = tablePageService.getQueryCount("select count(*) from ("
					+ sql + ") t", new HashMap<String, Object>());
			result.put("total", total);
			List<Map<String, Object>> tableList = tablePageService.getListData(
					sql + " ORDER BY NAME", null, start, pagesize);
			CellDataTable table = null;
			for (Map<String, Object> map : tableList) {
				table = new CellDataTable();
				table.setId(map.get("id").toString());
				table.setTableName(map.get("tableName").toString());
				table.setName(map.get("NAME").toString());
				Date ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(map.get("ctime").toString());
				table.setCtime(ctime);
				list.add(table);
			}

			JSONArray rows = new JSONArray();
			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory
						.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("tableId", model.getId());
				rows.add(jobject);
			}

			result.put("rows", rows);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据indexId取得数据表数据
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getTableListByFileDotFileId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getTableListByFileDotFileId(
			@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		String fileDotFileId = RequestUtils.getString(request, "fileDotFileId");
		String systemName = RequestUtils.getString(request, "systemName",
				currentSystemName);
		Environment.setCurrentSystemName(systemName);

		int start = 0;
		int pagesize = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = RequestUtils.getInt(request, "page");
		pagesize = RequestUtils.getInt(request, "pagesize");
		start = (pageNo - 1) * pagesize;

		JSONArray result = new JSONArray();
		try {
			CellDataTableQuery query = new CellDataTableQuery();
			query.setFileDotFileId(fileDotFileId);

			List<CellDataTable> list = cellDataTableService
					.getCellDataTablesByQueryCriteria(start, pagesize, query);

			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory
						.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("tableId", model.getId());
				jobject.put("num", model.getTableName());
				result.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/getTableListByTableId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getTableListByTableId(@Context HttpServletRequest request,
			ModelMap modelMap) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		JSONArray rows = new JSONArray();
		try {
			String tableId = "";
			if (modelMap.containsKey("tableId")) {
				tableId = modelMap.get("tableId").toString();
			}

			String systemName = currentSystemName;
			if (modelMap.containsKey("systemName")) {
				systemName = modelMap.get("systemName").toString();
			}

			if (tableId == null || StringUtils.isEmpty(tableId)) {
				tableId = RequestUtils.getString(request, "tableId", "");
			}

			if (systemName == null || StringUtils.isEmpty(systemName)) {
				systemName = RequestUtils.getString(request, "systemName",
						currentSystemName);
			}
			Environment.setCurrentSystemName(systemName);

			List<String> ids = new ArrayList<String>();
			String[] idArr = tableId.split(",");
			for (String id : idArr) {
				ids.add(id);
			}

			CellDataTableQuery query = new CellDataTableQuery();
			query.setIds(ids);
			List<CellDataTable> list = cellDataTableService.list(query);

			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory
						.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("tableId", model.getId());
				rows.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return rows.toJSONString().getBytes("UTF-8");
	}

}
