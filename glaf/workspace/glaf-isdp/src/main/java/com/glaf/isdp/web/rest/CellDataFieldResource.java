package com.glaf.isdp.web.rest;

import java.io.IOException;
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

import org.apache.commons.collections.CollectionUtils;
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
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.query.CellDataFieldQuery;
import com.glaf.isdp.service.CellDataFieldService;
import com.glaf.isdp.util.CellDataFieldDomainFactory;
import com.glaf.isdp.util.JSONConvertUtil;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellDataField")
public class CellDataFieldResource {

	protected static final Log logger = LogFactory.getLog(CellDataFieldResource.class);

	protected CellDataFieldService cellDataFieldService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellDataFieldService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		cellDataFieldService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellDataFieldQuery query = new CellDataFieldQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellDataFieldDomainFactory.processDataRequest(dataRequest);

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
		int total = cellDataFieldService.getCellDataFieldCountByQueryCriteria(query);
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
			List<CellDataField> list = cellDataFieldService.getCellDataFieldsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellDataField cellDataField : list) {
					JSONObject rowJSON = cellDataField.toJsonObject();
					rowJSON.put("id", cellDataField.getId());
					rowJSON.put("cellDataFieldId", cellDataField.getId());
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
		CellDataFieldQuery query = new CellDataFieldQuery();
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
		int total = cellDataFieldService.getCellDataFieldCountByQueryCriteria(query);
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
			List<CellDataField> list = cellDataFieldService.getCellDataFieldsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellDataField cellDataField : list) {
					JSONObject rowJSON = cellDataField.toJsonObject();
					rowJSON.put("id", cellDataField.getId());
					rowJSON.put("cellDataFieldId", cellDataField.getId());
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
	@Path("/saveCellDataField")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellDataField(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellDataField cellDataField = new CellDataField();
		try {
			Tools.populate(cellDataField, params);

			cellDataField.setTableId(request.getParameter("tableId"));
			cellDataField.setFieldName(request.getParameter("fieldName"));
			cellDataField.setUserId(request.getParameter("userId"));
			cellDataField.setMaxUser(RequestUtils.getInt(request, "maxUser"));
			cellDataField.setMaxSys(RequestUtils.getInt(request, "maxSys"));
			cellDataField.setCtime(RequestUtils.getDate(request, "ctime"));
			cellDataField.setSysNum(request.getParameter("sysNum"));
			cellDataField.setTableName(request.getParameter("tableName"));
			cellDataField.setDname(request.getParameter("dname"));
			cellDataField.setUserIndex(request.getParameter("userIndex"));
			cellDataField.setTreeTableNameB(request.getParameter("treeTableNameB"));

			this.cellDataFieldService.save(cellDataField);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellDataFieldService")
	public void setCellDataFieldService(CellDataFieldService cellDataFieldService) {
		this.cellDataFieldService = cellDataFieldService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellDataField cellDataField = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			cellDataField = cellDataFieldService.getCellDataField(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (cellDataField != null) {
			result = cellDataField.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellDataField.getId());
			result.put("cellDataFieldId", cellDataField.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/selectFieldByTableId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] selectFieldByTableId(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();

		JSONObject results = new JSONObject();
		try {

			String tableId = "", type = "1", tableName = "";

			tableId = RequestUtils.getString(request, "tableId");
			type = RequestUtils.getString(request, "type", "1");
			tableName = RequestUtils.getString(request, "tableName");

			String systemName = currentSystemName;
			systemName = RequestUtils.getString(request, "systemName", currentSystemName);

			Boolean R = RequestUtils.getBoolean(request, "R");// R平台元数据
			if (StringUtils.isNotEmpty(tableId) && StringUtils.contains(tableId, "-")) {

				// 20160912/admin-00000101 00000101 8位
				R = StringUtils.split(tableId, "-")[1].length() == 8;
			}

			// 如果不是datasource传入的数据，则通过request取得参数
			if (tableId == null || StringUtils.isEmpty(tableId)) {
				tableId = RequestUtils.getString(request, "tableId", "");
			}
			if (type == null || StringUtils.isEmpty(type)) {
				type = RequestUtils.getString(request, "type", "1");
			}
			if (tableName == null || StringUtils.isEmpty(tableName)) {
				tableName = RequestUtils.getString(request, "tableName");
			}

			if (systemName == null || StringUtils.isEmpty(systemName)) {
				systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			}

			if (StringUtils.isBlank(tableName) && StringUtils.isBlank(tableId))
				return null;

			Environment.setCurrentSystemName(systemName);

			StringBuffer sb = new StringBuffer();
			sb.append("select");
			sb.append(" t0.tablename as \"tablename\",t0.id as \"tableid\",t0.name as \"tableNameCN\",");
			sb.append(
					" t1.id as \"FieldId\",t1.tablename as \"referencedTableName\",t1.dname as \"referencedFieldName\", ");
			sb.append(
					" t2.listid as \"listid\",t2.dname as \"dname\",t2.fname as \"fname\",t2.dtype \"dtype\",t2.strlen \"strlen\" ");

			StringBuffer totalsb = new StringBuffer();
			totalsb.append("select count(*) ");

			StringBuffer sbcause = new StringBuffer();
			StringBuffer rsb = null;
			switch (Integer.parseInt(type)) {
			case 1:
			case 3:
				sbcause.append(" from cell_data_table t0,cell_data_field t1,interface t2 ");
				// sbcause.append(" where t0.id=t1.tableid and t1.id=t2.listid
				// ");
				sbcause.append(" where t0.id=t1.tableid and t1.tableid=t2.frmtype and t1.fieldname=t2.dname ");
				break;
			case 2:
				sbcause.append(" from cell_data_table t0,cell_data_field t1,cell_repinfo t2 ");
				sbcause.append(" where t0.id=t1.tableid and t1.fieldname=t2.dname ");
				break;
			case 4:
				// 扩展表查询字段
				if (tableName == null || StringUtils.isEmpty(tableName)) {
					throw new RuntimeException("程序出错，扩展表查询需要传入表名!");
				}
				rsb = new StringBuffer();
				rsb.append("select ");
				rsb.append("sys.tables.name as \"tablename\",sys.tables.object_id as \"tableid\",");
				rsb.append(
						"sys.columns.column_id as \"FieldId\",sys.columns.name as \"dname\",sys.columns.name as \"fname\",sys.columns.max_length as \"strlen\",");
				rsb.append("sys.types.name as \"dtype\"");
				rsb.append(" from sys.columns,sys.tables,sys.types ");
				if (org.apache.commons.codec.binary.StringUtils.equals(DBUtils.ORACLE,
						DBConnectionFactory.getDatabaseType())) {
					rsb.append(
							" where to_char(sys.tables.object_id)= to_char(sys.columns.object_id)  AND to_char(sys.types.user_type_id)= to_char(sys.columns.user_type_id) ");
				} else {
					rsb.append(
							" where CONVERT(VARCHAR, sys.tables.object_id)= CONVERT(VARCHAR, sys.columns.object_id)  AND CONVERT(VARCHAR, sys.types.user_type_id)= CONVERT(VARCHAR, sys.columns.user_type_id) ");
				}
				rsb.append(" and sys.tables.name='").append(tableName).append("'");

				JSONArray jsonArray0 = this.cellDataFieldService.getInterfaceColumnDefinitions(systemName, tableName,
						tableId, R);

				results.put("rows", jsonArray0);
				results.put("total", jsonArray0.size());

				return results.toJSONString().getBytes("UTF-8");

			// break;
			case 99:
				// 树形结构查询字段，包括系统自建字段
				rsb = new StringBuffer();

				// rsb.append(
				// "SELECT * FROM (select c.name as fname,c.name as
				// dname,c.length as strlen,t.name as dtype from syscolumns c");
				// rsb.append(
				// " left join sys.types t on CONVERT(VARCHAR,c.xtype) =
				// CONVERT(VARCHAR,t.system_type_id) where id=(select id from
				// sysobjects where xtype='U' and name='");
				// rsb.append(tableName)
				// .append("') and (c.name not like 'cell_useradd%' or c.name
				// like '%_id') and c.name not in ( SELECT dname FROM interface
				// WHERE frmtype='")
				// .append(tableId).append("')) t1");
				// rsb.append(" UNION SELECT * FROM (select
				// fname,dname,strlen,dtype from interface where frmtype='")
				// .append(tableId).append("') t2 ");

				// Map<String, Object> o;
				// JSONObject jsonObject;
				// String columnName;
				// List<ColumnDefinition> columns = null;
				// if (StringUtils.isNotEmpty(tableName)) {
				// columns = DBUtils.getColumnDefinitions(systemName,
				// tableName);
				// }
				// JSONArray jsonArray = new JSONArray();
				// if (CollectionUtils.isNotEmpty(columns)) {
				// Map<String, JSONObject> tmpJson = new HashMap<String,
				// JSONObject>();
				// for (ColumnDefinition column : columns) {
				// columnName = column.getColumnName().toLowerCase();
				// tmpJson.put(columnName, column.toJsonObject());
				// }
				//
				// Map<String, Map<String, Object>> tmp = new HashMap<String,
				// Map<String, Object>>();
				// String sql = "select fname,dname,strlen,dtype from " + (R ?
				// "r_interface" : "interface")
				// + " where frmtype='" + tableId + "'";
				// List<Map<String, Object>> list = new
				// QueryHelper().getResultList(systemName, sql,
				// new HashMap<String, Object>());
				// if (CollectionUtils.isNotEmpty(list)) {
				// for (Map<String, Object> map : list) {
				// columnName = map.get("dname").toString().toLowerCase();
				// if (tmpJson.containsKey(columnName))
				// tmp.put(columnName, map);
				// }
				// }
				//
				// for (ColumnDefinition column : columns) {
				// columnName = column.getColumnName().toLowerCase();
				// o = tmp.get(columnName);
				// if (o != null) {
				// jsonObject = new JSONObject(o);
				// } else {
				// jsonObject = new JSONObject();
				// jsonObject.put("fname", columnName);
				// jsonObject.put("dname", columnName);
				// jsonObject.put("strlen", column.getLength());
				// jsonObject.put("dtype",
				// column.getJavaType() == null ? "string" :
				// column.getJavaType().toLowerCase());
				// }
				//
				// jsonArray.add(jsonObject);
				// }
				// }
				// results.put("rows", jsonArray);
				// results.put("total", jsonArray.size());

				JSONArray jsonArray = this.cellDataFieldService.getInterfaceColumnDefinitions(systemName, tableName,
						tableId, R);

				results.put("rows", jsonArray);
				results.put("total", jsonArray.size());

				return results.toJSONString().getBytes("UTF-8");
			default:
				sbcause.append(" from cell_data_table t0,cell_data_field t1,cell_repinfo t2 ");
				// sbcause.append(" where t0.id=t1.tableid and t1.id=t2.listid
				// ");
				sbcause.append(" where t0.id=t1.tableid and t1.tableid=t2.frmtype and t1.fieldname=t2.dname ");
			}
			sbcause.append(" and t1.tableid= '");
			sbcause.append(tableId);
			sbcause.append("'");

			totalsb.append(sbcause.toString());
			sb.append(sbcause.toString());

			if (Integer.parseInt(type) == 4 || Integer.parseInt(type) == 99) {
				sb = rsb;
				totalsb = new StringBuffer();
				totalsb.append("select count(*) from (").append(sb.toString()).append(") c");
			}

			logger.debug(totalsb.toString());
			QueryHelper helper = new QueryHelper();
			int total = helper.getTotal(Environment.getCurrentSystemName(), totalsb.toString(),
					new HashMap<String, Object>());
			results.put("total", total);

			if (total > 0) {
				logger.debug(sb.toString());
				List<Map<String, Object>> list = helper.getResultList(Environment.getCurrentSystemName(), sb.toString(),
						new HashMap<String, Object>());

				JSONArray rows = JSONConvertUtil.mapListToJSONArray(list), rows_ = new JSONArray();
				if (CollectionUtils.isNotEmpty(rows)) { // 过滤数据库物理字段
					String dname;
					int i = 0, length = rows.size();
					JSONObject json, tmp = new JSONObject();
					List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(systemName, tableName);
					if (CollectionUtils.isNotEmpty(columns)) {
						for (ColumnDefinition cd : columns) {
							tmp.put(cd.getColumnName().toLowerCase(), cd);
						}
					}
					for (; i < length; i++) {
						json = rows.getJSONObject(i);
						dname = json.getString("dname");
						if (StringUtils.isEmpty(dname) || //
								!tmp.containsKey(dname.toLowerCase())) {
							continue;
						}
						rows_.add(json);
					}
				}
				results.put("rows", rows_);
			} else {
				results.put("rows", new JSONArray());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error=" + e.getMessage());
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return results.toJSONString().getBytes("UTF-8");
	}
	
	
	
	

}
