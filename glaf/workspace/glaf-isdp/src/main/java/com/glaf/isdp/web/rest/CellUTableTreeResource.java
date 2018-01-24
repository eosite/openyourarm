package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.query.CellDataTableQuery;
import com.glaf.isdp.query.CellUTableTreeQuery;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.CellUTableTreeService;
import com.glaf.isdp.service.RUtabletreeService;
import com.glaf.isdp.service.TableActionService;
import com.glaf.isdp.util.CellDataTableJsonFactory;
import com.glaf.isdp.util.CellUTableTreeDomainFactory;
import com.glaf.isdp.util.CellUTableTreeJsonFactory;

/**
 * 
 * Rest响应类
 * 
 */

@Controller
@Path("/rs/isdp/cellUTableTree")
public class CellUTableTreeResource {
	protected static final Log logger = LogFactory.getLog(CellUTableTreeResource.class);

	protected CellUTableTreeService cellUTableTreeService;

	@Autowired
	protected RUtabletreeService rUtabletreeService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("indexIds");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellUTableTreeService.deleteByIndexIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		cellUTableTreeService.deleteByIndexId(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellUTableTreeQuery query = new CellUTableTreeQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellUTableTreeDomainFactory.processDataRequest(dataRequest);

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
		int total = cellUTableTreeService.getCellUTableTreeCountByQueryCriteria(query);
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
			List<CellUTableTree> list = cellUTableTreeService.getCellUTableTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellUTableTree cellUTableTree : list) {
					JSONObject rowJSON = cellUTableTree.toJsonObject();
					rowJSON.put("id", cellUTableTree.getId());
					rowJSON.put("cellUTableTreeId", cellUTableTree.getId());
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
		CellUTableTreeQuery query = new CellUTableTreeQuery();
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
		int total = cellUTableTreeService.getCellUTableTreeCountByQueryCriteria(query);
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
			List<CellUTableTree> list = cellUTableTreeService.getCellUTableTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellUTableTree cellUTableTree : list) {
					JSONObject rowJSON = cellUTableTree.toJsonObject();
					rowJSON.put("id", cellUTableTree.getId());
					rowJSON.put("cellUTableTreeId", cellUTableTree.getId());
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
	@Path("/saveCellUTableTree")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellUTableTree(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellUTableTree cellUTableTree = new CellUTableTree();
		try {
			Tools.populate(cellUTableTree, params);

			cellUTableTree.setId(request.getParameter("id"));
			cellUTableTree.setParentId(RequestUtils.getInteger(request, "parentId"));
			cellUTableTree.setIndexName(request.getParameter("indexName"));
			cellUTableTree.setNlevel(RequestUtils.getInteger(request, "nlevel"));
			cellUTableTree.setNodeIco(RequestUtils.getInteger(request, "nodeIco"));
			cellUTableTree.setListNo(RequestUtils.getInteger(request, "listNo"));
			cellUTableTree.setTableType(RequestUtils.getInteger(request, "tableType"));
			cellUTableTree.setIntDel(RequestUtils.getInteger(request, "intDel"));
			cellUTableTree.setBusiessId(request.getParameter("busiessId"));
			cellUTableTree.setContent(request.getParameter("content"));
			cellUTableTree.setNum(request.getParameter("num"));
			cellUTableTree.setMenuIndex(RequestUtils.getInteger(request, "menuIndex"));
			cellUTableTree.setDomainIndex(RequestUtils.getInteger(request, "domainIndex"));
			cellUTableTree.setWinWidth(RequestUtils.getInteger(request, "winWidth"));
			cellUTableTree.setWinHeight(RequestUtils.getInteger(request, "winHeight"));

			this.cellUTableTreeService.save(cellUTableTree);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellUTableTreeService")
	public void setCellUTableTreeService(CellUTableTreeService cellUTableTreeService) {
		this.cellUTableTreeService = cellUTableTreeService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellUTableTree cellUTableTree = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			cellUTableTree = cellUTableTreeService.getCellUTableTree(RequestUtils.getInt(request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (cellUTableTree != null) {
			result = cellUTableTree.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellUTableTree.getId());
			result.put("cellUTableTreeId", cellUTableTree.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据tableType取得数据类别
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getUtableTreeByTableType")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getUtableTreeByTableType(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		int tableType = RequestUtils.getInt(request, "tableType", 2);
		int parentId = RequestUtils.getInt(request, "indexId", -9999);
		int level = RequestUtils.getInt(request, "level", -1);
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		Environment.setCurrentSystemName(systemName);

		JSONArray result = new JSONArray();
		try {
			List<CellUTableTree> list = cellUTableTreeService.getCellUTableTreesAndChildCountByTableType(tableType,
					level + 1, parentId == -9999 ? null : parentId);

			for (CellUTableTree model : list) {
				JSONObject jobject = CellUTableTreeJsonFactory.toJsonObject(model);

				boolean isParent = model.getChildrenCount() != null && model.getChildrenCount() > 0 ? true : false;

				jobject.put("hasChildren", isParent);
				jobject.put("isParent", isParent);
				result.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据tableType取得数据类别
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getUtableTreeByTableTypeInit")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getUtableTreeByTableTypeInit(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		int tableType = RequestUtils.getInt(request, "tableType", 2);
		int type = RequestUtils.getInt(request, "type", 1);
		int defp = -9999;
		int parentId = RequestUtils.getInt(request, "indexId", defp);
		int level = RequestUtils.getInt(request, "level", -1);
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		Environment.setCurrentSystemName(systemName);

		JSONArray result = new JSONArray();

		try {

			if (type >= 4) {
				result = this.getUtableTreeByTableTypeAndIndex(type, parentId);
			} else {
				// List<CellUTableTree> list =
				// cellUTableTreeService.getCellUTableTreesAndChildCountByTableType(tableType,
				// level + 1, parentId == -9999 ? null : parentId);

				List<CellUTableTree> list = this.getCellUTableTreesAndChildCountByTableType(tableType, level + 1,
						parentId == -9999 ? null : Long.valueOf(parentId));

				Map<String, JSONArray> indexMap = null;
				if (type == 1) {
					Set<Integer> set = new HashSet<Integer>();
					for (CellUTableTree model : list) {
						set.add(model.getIndexId());
					}
					indexMap = this.getType1Data(set);
				}

				for (CellUTableTree model : list) {
					JSONObject jobject = CellUTableTreeJsonFactory.toJsonObject(model);

					JSONArray children;
					if (type == 1 && indexMap != null) {
						children = indexMap.get(String.valueOf(model.getIndexId()));
					} else {
						children = this.getUtableTreeByTableTypeAndIndex(type, model.getIndexId());
					}
					if (children != null && children.size() > 0) {
						jobject.put("children", children);
					}
					// }

					boolean isParent = model.getChildrenCount() != null && model.getChildrenCount() > 0 ? true : false;
					jobject.put("hasChildren", isParent);
					jobject.put("isParent", isParent);
					result.add(jobject);
				}

				if (tableType == 2 && parentId == defp) {// 普通表，增加R平台的数据
					boolean exists = DBUtils.tableExists(systemName, "r_data_table");
					if (exists) {
						JSONArray r = this.rUtabletreeService.getTreeAndChildrenByType(tableType);
						result.addAll(r);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 代码处理目录层级结构，避免多次查询数据库以及多层级的情况下数据以咯偶
	 * 
	 * @param tableType
	 * @param level
	 * @param parentId
	 * @return
	 */
	public List<CellUTableTree> getCellUTableTreesAndChildCountByTableType(Integer tableType, Integer level,
			Long parentId) {

		CellUTableTreeQuery query = new CellUTableTreeQuery();

		if (parentId != null) {
			query.setIdLike(parentId + "|");
		}
		// query.setParentId(parentId);
		query.setTableType(tableType);
		query.setOrderBy(" index_id ");
		query.setSortOrder(" ASC ");

		List<CellUTableTree> list = cellUTableTreeService.list(query);

		List<CellUTableTree> collection = new ArrayList<CellUTableTree>();
		if (CollectionUtils.isNotEmpty(list)) {
			Map<Integer, List<CellUTableTree>> map = new HashMap<Integer, List<CellUTableTree>>();
			for (CellUTableTree model : list) {

				if (parentId != null && Long.parseLong(model.getIndexId() + "") == parentId) {
					continue;
				}

				if (!map.containsKey(model.getParentId())) {
					map.put(model.getParentId(), new ArrayList<CellUTableTree>());
				}
				map.get(model.getParentId()).add(model);
			}
			for (CellUTableTree model : list) {
				if (parentId != null && Long.parseLong(model.getIndexId() + "") == parentId) {
					continue;
				}
				if (map.containsKey(model.getParentId())) {
					model.setChildrenCount(map.get(model.getParentId()).size());
				} else
					model.setChildrenCount(0);
				if (parentId != null) {
					if (parentId != Long.valueOf(model.getIndexId())) {
						collection.add(model);
					}
				} else {
					collection.add(model);
				}
			}
			map = null;
		}
		return collection;
	}

	@Autowired
	protected CellDataTableService cellDataTableService;
	@Autowired
	protected ITablePageService tablePageService;
	@Resource(name = "com.glaf.isdp.service.tableActionService")
	protected TableActionService tableActionService;

	private JSONArray getUtableTreeByTableTypeAndIndex(int type, int indexId) throws Exception {
		CellDataTableQuery query = new CellDataTableQuery();
		List<CellDataTable> list = new ArrayList<CellDataTable>();
		int pagesize = 1000, start = 0;
		switch (type) {
		case 1:
			query.setSearchChildTable(true);
			// 普通表查询
			List<CellUTableTree> tempList = cellUTableTreeService.getAllChildCellUTableTrees(indexId);
			List<String> ids = new ArrayList<String>();
			ids.add(String.valueOf(indexId));
			for (CellUTableTree obj : tempList) {
				ids.add(String.valueOf(obj.getIndexId()));
			}
			String sqlCondition = QueryUtils.getSQLCondition(ids, "E", "index_id");
			query.setSqlCondition(sqlCondition);

			query.setOrderBy("id");

			pagesize = cellDataTableService.getCellDataTableCountAndChildTablesByQueryCriteria(query);

			list = cellDataTableService.getCellDataTablesAndChildTablesByQueryCriteria(start, pagesize, query);
			break;
		case 3:
		case 5:
			query = new CellDataTableQuery();
			// 虚拟表查询
			query.setIndexId(indexId);
			query.setSqlCondition(" AND ISSUBTABLE='' ");

			query.setOrderBy("id");

			list = cellDataTableService.getCellDataTablesByQueryCriteria(start, pagesize, query);
			break;
		case 4:
			// 扩展表查询
			// keyword = modelMap.get("keyword");
			// String sql = "SELECT OBJECT_ID AS id,NAME,NAME AS
			// tableName,CREATE_DATE AS ctime FROM SYS.TABLES WHERE NAME NOT
			// LIKE 'cell_useradd%' AND NAME NOT LIKE 'LOG_%'";
		/*	String basesql = "SELECT OBJECT_ID AS id,NAME,NAME AS tableName,CREATE_DATE AS ctime FROM SYS.TABLES  WHERE NAME NOT LIKE 'LOG_%'";

			boolean dm = RConstant.isDM();

			boolean isOracle = RConstant.isOracle() || dm;

			if (isOracle) {
				basesql = "SELECT table_name AS \"id\",table_name \"NAME\",table_name AS \"tableName\",sysdate AS \"ctime\" FROM user_tables  WHERE 1=1 ";
			}

			StringBuffer sb = new StringBuffer();
			sb.append(basesql).append(String.format(" and %s ", isOracle ? "TABLE_NAME" : "NAME"))
					.append(" NOT LIKE 'cell_useradd%' ");

			boolean unionflag = false;
			String tempsql = "";
			if (tableActionService.isExistsTable("CELL_DATA_TABLE")) {
				tempsql += String.format(" and %s NOT IN (SELECT distinct tablename FROM CELL_DATA_TABLE ) ",
						isOracle ? "table_name" : "NAME");
				unionflag = true;
			}
			if (tableActionService.isExistsTable("interface")) {
				tempsql += String.format(" and %s NOT IN (select distinct frmtype from interface) ",
						isOracle ? "table_name" : "NAME");
				unionflag = true;
			}

			if (unionflag) {
				sb.append(" union ");
				sb.append(basesql).append(tempsql);
			}

			pagesize = tablePageService.getQueryCount("select count(*) from (" + sb.toString() + ") c",
					new HashMap<String, Object>());

			String sql = "select * from (" + sb.toString() + ") c ORDER BY %s ";

			List<Map<String, Object>> tableList = tablePageService
					.getListData("select * from (" + sb.toString() + ") c ORDER BY c." + //
							(isOracle ? "NAME" : "NAME"), null, start, pagesize);
			CellDataTable table = null;*/

			Map<String, String> tmp = new HashMap<>();
			
			
			Set<String> dis = new HashSet<String>();
			dis.add("LOG_");
			dis.add("CELL_USERADD");
			dis.add("USERINFO");
			dis.add("R_");
			dis.add("SYS");
			dis.add("TRACE_XE");
			
			
			/*h1:for (Map<String, Object> map : tableList) {
				String tableName = map.get("tableName").toString();
				if (!tmp.containsKey(tableName.toLowerCase())) {
					tmp.put(tableName.toLowerCase(), tableName);
				} else {
					continue;
				}
				
				for (String di : dis) {
					if (StringUtils.startsWithIgnoreCase(tableName, di)) {
						continue h1;
					}
				}

				table = new CellDataTable();
				table.setId(map.get("id").toString());
				table.setTableName(tableName);
				table.setName(map.get("NAME").toString());
				Date ctime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("ctime").toString());
				table.setCtime(ctime);
				list.add(table);
			}*/

			/**
			 * 获取视图
			 */
			ResultSet rs = null;
			Connection conn = null;
			try {
				String tableName;
				
				conn = DBConnectionFactory.getCurrentConnection();
				rs = conn.getMetaData().getTables(/* conn.getCatalog() */null, null, null,
						new String[] { "TABLE", "VIEW" });
				h1: while (rs.next()) {
					tableName = rs.getString("TABLE_NAME");

					if (tmp.containsKey(tableName.toLowerCase())) {
						continue;
					}

					for (String di : dis) {
						if (StringUtils.startsWithIgnoreCase(tableName, di)) {
							continue h1;
						}
					}

					if (StringUtils.equalsIgnoreCase("CHECK_CONSTRAINTS", tableName)) {
						break;
					}

					CellDataTable table = new CellDataTable();
					table.setId(tableName);
					table.setTableName(tableName);
					table.setName(tableName);
					list.add(table);

				}
			} finally {
				JdbcUtils.close(rs);
				JdbcUtils.close(conn);
			}

			break;
		default:
			break;
		}

		// JSONArray rows = null;
		// if (list != null && list.size() > 0) {
		// rows = new JSONArray();
		// for (CellDataTable model : list) {
		// JSONObject jobject = CellDataTableJsonFactory.toJsonObject(model);
		// jobject.put("tableNameCN", model.getName());
		// jobject.put("indexName", model.getName());
		// jobject.put("tableId", model.getId());
		// if (indexId > -1) {
		// jobject.put("parentId", indexId);
		// }
		// rows.add(jobject);
		// }
		// }

		return this.getJSONArray(list, indexId);
	}

	/**
	 * 获取普通表数据
	 * 
	 * @return
	 */
	protected Map<String, JSONArray> getType1Data(Set<Integer> indexIds) {
		Map<String, JSONArray> json = new HashMap<String, JSONArray>();
		int pagesize = 10000, start = 0;
		CellDataTableQuery query = new CellDataTableQuery();
		query.setSearchChildTable(true);
		// 普通表查询
		List<CellUTableTree> tempList;
		List<String> ids = new ArrayList<String>();
		String index_id;
		for (Integer indexId : indexIds) {
			tempList = cellUTableTreeService.getAllChildCellUTableTrees(indexId);
			ids.add(String.valueOf(indexId));
			if (CollectionUtils.isNotEmpty(tempList)) {
				for (CellUTableTree obj : tempList) {
					if (!ids.contains((index_id = String.valueOf(obj.getIndexId())))) {
						ids.add(index_id);
					}
				}
			}
		}

		String sqlCondition = QueryUtils.getSQLCondition(ids, "E", "index_id");
		query.setSqlCondition(sqlCondition);

		query.setOrderBy("id");

		pagesize = cellDataTableService.getCellDataTableCountAndChildTablesByQueryCriteria(query);

		List<CellDataTable> list = cellDataTableService.getCellDataTablesAndChildTablesByQueryCriteria(start, pagesize,
				query);

		if (CollectionUtils.isNotEmpty(list)) {
			JSONArray jsonArray = null;
			Map<String, List<CellDataTable>> indexMap = new HashMap<String, List<CellDataTable>>();
			for (CellDataTable cdt : list) {// indexId 分组
				if (cdt.getIndexId() == null)
					continue;
				index_id = String.valueOf(cdt.getIndexId());
				if (!indexMap.containsKey(index_id)) {
					indexMap.put(index_id, new ArrayList<CellDataTable>());
				}
				indexMap.get(index_id).add(cdt);
			}
			for (String key : indexMap.keySet()) {
				jsonArray = this.getJSONArray(indexMap.get(key), Integer.parseInt(key));
				json.put(key, jsonArray);
			}
		}

		return json;
	}

	protected JSONArray getJSONArray(List<CellDataTable> list, int indexId) {
		JSONArray rows = null;
		if (list != null && list.size() > 0) {
			rows = new JSONArray();
			for (CellDataTable model : list) {
				JSONObject jobject = CellDataTableJsonFactory.toJsonObject(model);
				jobject.put("tableNameCN", model.getName());
				jobject.put("indexName", model.getName());
				jobject.put("tableId", model.getId());
				if (indexId > -1) {
					jobject.put("parentId", indexId);
				}
				rows.add(jobject);
			}
		}
		return rows;
	}

}
