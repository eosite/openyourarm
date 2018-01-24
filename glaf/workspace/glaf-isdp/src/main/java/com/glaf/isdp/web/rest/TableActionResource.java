package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.config.Environment;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.query.CellDataFieldQuery;
import com.glaf.isdp.query.CellDataTableQuery;
import com.glaf.isdp.service.CellDataFieldService;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.TableActionService;
import com.glaf.isdp.util.ColumnTypeUtils;

@Controller
@Path("/rs/isdp/table")
public class TableActionResource {

	protected TableActionService tableActionService;

	protected CellDataTableService cellDataTableService;

	protected CellDataFieldService cellDataFieldService;

	protected IFieldInterfaceService fieldInterfaceService;

	/**
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getColumnsByTableId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getColumnsByTableId(@Context HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		Environment.setCurrentSystemName(RequestUtils.getString(request, "systemName", currentSystemName));
		try {
			String tableid = RequestUtils.getString(request, "tableid");
			CellDataTable cellDataTable = null;
			if (StringUtils.isNotBlank(tableid)) {
				cellDataTable = cellDataTableService.getCellDataTable(tableid);
				if (cellDataTable != null) {
					List<FieldInterface> list = fieldInterfaceService.getFieldsByFrmType(tableid);
					Map<String, FieldInterface> tmp = new HashMap<String, FieldInterface>();
					if (list != null && !list.isEmpty()) {
						for (FieldInterface fi : list) {
							tmp.put(fi.getDname(), fi);
						}
					}
					CellDataFieldQuery query = new CellDataFieldQuery();
					query.setTableId(tableid);
					JSONObject json;
					String fieldName;
					List<CellDataField> cdfs = cellDataFieldService.list(query);
					if (cdfs != null && !cdfs.isEmpty()) {
						JSONArray jsons = new JSONArray();
						JSONObject result = new JSONObject();
						for (CellDataField cdf : cdfs) {
							json = new JSONObject();
							fieldName = cdf.getFieldName();
							if (tmp.containsKey(fieldName)) {
								json.put("listId", tmp.get(fieldName).getListId());
								json.put("text", tmp.get(fieldName).getFname());
								json.put("strlen", tmp.get(fieldName).getStrlen());
								json.put("dType", tmp.get(fieldName).getDtype());

								json.put("fieldId", cdf.getId());
								json.put("fieldName", cdf.getFieldName());

								json.put("tableId", cellDataTable.getId());
								json.put("tableName", cellDataTable.getTableName());
								json.put("tableNameCN", cellDataTable.getName());
								jsons.add(json);
							}
						}
						result.put("table", cellDataTable);
						result.put("columns", jsons);
						return result.toJSONString().getBytes("UTF-8");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return null;
	}

	/**
	 * 创建表
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/saveTableInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTableInfo(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Boolean r = RequestUtils.getBoolean(request, "R");// R 平台表格
		String tableid = RequestUtils.getString(request, "tableid");
		if (r) {// 新增的表格都放到r平台
			request.getRequestDispatcher("/mx/isdp/rdataTable/saveTableInfo").forward(request, response);
			return null;
		}

		String currentSystemName = Environment.getCurrentSystemName();
		try {
			CellDataTable table = this.getSaveTable(request, loginContext); // 创建表
			Environment.setCurrentSystemName(RequestUtils.getString(request, "systemName", currentSystemName));
			if (table != null) {
				String columnsStr = RequestUtils.getString(request, "columns");
				if (StringUtils.isNotBlank(columnsStr)) {
					List<JSONObject> columns = JSONArray.parseArray(columnsStr, JSONObject.class);
					if (columns != null) {
						int fieldLen;
						tableid = table.getId();
						JSONObject result = new JSONObject();
						String fieldType, name, fieldId, listId, referenced_field = "", referenced_table = "",
								userindex = "";
						
						boolean isOracle = org.apache.commons.lang3.StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType());
						
						for (JSONObject json : columns) {
							fieldType = json.getString("dType");
							//oracle 没有datetime 类型 用date类型
							if(isOracle && "datetime".equalsIgnoreCase(fieldType)){
								fieldType = "date" ;
							}
							fieldLen = json.getIntValue("strlen");
							name = json.getString("text");
							fieldId = json.getString("fieldId");
							listId = json.getString("listId");

							String columnType = ColumnTypeUtils.getColumnType(fieldType, fieldLen);
							CellDataField cellDataField = null;
							FieldInterface fieldInterface = null;
							if (StringUtils.isNotBlank(fieldId)) {
								cellDataField = cellDataFieldService.getCellDataField(fieldId);
							}
							if (cellDataField != null) { // 更新字段

								cellDataField.setUserIndex(userindex);
								cellDataField.setTableName(referenced_table);
								cellDataField.setFormula("");
								cellDataField.setDname(referenced_field);

								fieldInterface = fieldInterfaceService.getFieldInterface(listId); // 默认这个是有值的
								if (fieldInterface != null) {
									fieldInterface.setFname(StringUtils.trim(name));
									fieldInterface.setDtype(fieldType);
									fieldInterface.setStrlen(fieldLen);
								}
								tableActionService.modifyColumn(table.getTableName(), columnType, cellDataField,
										fieldInterface);
							} else { // 新增字段
								int maxUser = cellDataFieldService.getNextMaxUser(tableid);
								String fieldName = table.getTableName() + "_user" + maxUser;
								cellDataField = new CellDataField();
								cellDataField.setMaxUser(maxUser);
								cellDataField.setTableId(tableid);
								cellDataField.setCtime(new Date());
								cellDataField.setFieldName(fieldName);
								cellDataField.setUserIndex(userindex);
								cellDataField.setTableName(referenced_table);
								cellDataField.setUserId(loginContext.getActorId());
								cellDataField.setFormula("");
								cellDataField.setDname(referenced_field);

								fieldInterface = new FieldInterface();
								fieldInterface.setDatapoint(3);
								fieldInterface.setDname(fieldName);
								fieldInterface.setFname(StringUtils.trim(name));
								fieldInterface.setIssystem("1");
								fieldInterface.setDtype(fieldType);
								fieldInterface.setStrlen(fieldLen);
								fieldInterface.setFrmtype(table.getId());

								tableActionService.addColumn(table.getTableName(), fieldName, columnType, cellDataField,
										fieldInterface);

							}
							json.put("fieldId", cellDataField.getId());
							if (fieldInterface != null) {
								json.put("listId", fieldInterface.getListId());
							}

							json.put("fieldName", cellDataField.getFieldName());
							json.put("tableId", table.getId());
							json.put("tableName", table.getTableName());
							json.put("tableNameCN", table.getName());
						}
						result.put("table", table);
						result.put("columns", columns);
						return result.toJSONString().getBytes("UTF-8");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return ResponseUtils.responseJsonResult(false);

	}

	/**
	 * 创建表
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@POST
	@Path("/createTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] createTable(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		CellDataTable cellDataTable = this.getSaveTable(request, loginContext);
		if (cellDataTable != null) {
			try {
				return cellDataTable.toJsonObject().toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}

	private CellDataTable getSaveTable(HttpServletRequest request, LoginContext loginContext) {
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);
			String name = RequestUtils.getString(request, "name");// 表中文描述
			Integer indexId = RequestUtils.getInteger(request, "indexId", 1);
			String tableid = RequestUtils.getString(request, "tableid");
			CellDataTable cellDataTable = null;
			if (StringUtils.isNotBlank(tableid)) {
				cellDataTable = cellDataTableService.getCellDataTable(tableid);
			}

			if (cellDataTable != null) {// 修改表
				cellDataTable.setName(name);
				cellDataTable.setIndexId(indexId);
				cellDataTableService.save(cellDataTable);
			} else {
				int maxUser = cellDataTableService.getNextMaxUser();
				String tableName = "cell_useradd" + maxUser;// 新建的表名

				// 在cell_data_table中插入一条记录
				cellDataTable = new CellDataTable();
				cellDataTable.setTableName(tableName);
				cellDataTable.setUserId(loginContext.getActorId());
				cellDataTable.setCtime(new Date());
				cellDataTable.setMaxUser(maxUser);
				cellDataTable.setName(name);
				cellDataTable.setIsSubTable("0");
				cellDataTable.setTopId("");
				// cellDataTable.setIndexId(1);
				cellDataTable.setIndexId(indexId);
				cellDataTable.setAddType(1);

				tableActionService.createTable(tableName, cellDataTable);
			}

			return cellDataTable;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return null;
	}

	// 删除表
	@GET
	@POST
	@Path("/deleteTable")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteTable(@Context HttpServletRequest request) {
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			LoginContext loginContext = RequestUtils.getLoginContext(request);

			String tableid = RequestUtils.getString(request, "id", "");

			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("非管理员不能删除表格! 表格id:" + tableid);
			}

			List<String> tableids = new ArrayList<String>();
			tableids.add(tableid);

			CellDataTable table = cellDataTableService.getCellDataTable(tableid);

			List<String> tableNames = new ArrayList<String>();
			tableNames.add(table.getTableName());

			// 查询子表并删除子表
			List<CellDataTable> childrenTableList = new ArrayList<CellDataTable>();
			CellDataTableQuery query = new CellDataTableQuery();
			query.setIsSubTable("1");
			// 普通表
			query.setTopId(table.getId());
			// CELL表,暂时不做Cell表删除操作
			// query.setFileDotFileId(table.getFileDotFileId());
			childrenTableList = cellDataTableService.list(query);

			for (CellDataTable childrenTable : childrenTableList) {
				tableids.add(childrenTable.getId());
				tableNames.add(childrenTable.getTableName());
			}

			tableActionService.deleteTable(tableids, tableNames);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 新增字段
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@POST
	@Path("/addColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] addColumn(@Context HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			String name = RequestUtils.getString(request, "name");
			String fieldType = RequestUtils.getString(request, "fieldType");
			int fieldLen = RequestUtils.getInt(request, "fieldLen");
			String referenced_table_obj = RequestUtils.getString(request, "referenced_table_obj", "");
			String referenced_field_obj = RequestUtils.getString(request, "referenced_field_obj", "");

			String referenced_table = "";
			String userindex = "";
			if (StringUtils.isNotEmpty(referenced_table_obj)) {
				JSONArray tableArray = JSONArray.parseArray(referenced_table_obj);
				JSONObject tableObj = tableArray.getJSONObject(0);
				referenced_table = tableObj.getString("TableName");
				userindex = referenced_table + "_id";
			}

			String referenced_field = "";
			if (StringUtils.isNotEmpty(referenced_field_obj)) {
				JSONArray fieldArray = JSONArray.parseArray(referenced_field_obj);
				JSONObject fieldObj = fieldArray.getJSONObject(0);
				referenced_field = fieldObj.getString("ColumnName");
			}

			String tableid = RequestUtils.getString(request, "tableid");
			CellDataTable table = cellDataTableService.getCellDataTable(tableid);

			int maxUser = cellDataFieldService.getNextMaxUser(table.getId());
			String fieldName = table.getTableName() + "_user" + maxUser;
			String columnType = ColumnTypeUtils.getColumnType(fieldType, fieldLen);

			CellDataField cellDataField = new CellDataField();
			cellDataField.setMaxUser(maxUser);
			cellDataField.setTableId(tableid);
			cellDataField.setCtime(new Date());
			cellDataField.setFieldName(fieldName);
			cellDataField.setUserIndex(userindex);
			cellDataField.setTableName(referenced_table);
			cellDataField.setUserId(loginContext.getActorId());
			cellDataField.setFormula("");
			cellDataField.setDname(referenced_field);

			FieldInterface fieldInterface = new FieldInterface();
			fieldInterface.setDatapoint(3);
			fieldInterface.setDname(fieldName);
			fieldInterface.setFname(StringUtils.trim(name));
			fieldInterface.setIssystem("1");
			fieldInterface.setDtype(fieldType);
			fieldInterface.setStrlen(fieldLen);
			fieldInterface.setFrmtype(table.getId());

			tableActionService.addColumn(table.getTableName(), fieldName, columnType, cellDataField, fieldInterface);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return ResponseUtils.responseJsonResult(true);

	}

	/**
	 * 修改字段
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@POST
	@Path("/modifyColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] modifyColumn(@Context HttpServletRequest request) {

		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			String fieldId = RequestUtils.getString(request, "fieldId");
			String listId = RequestUtils.getString(request, "listId");
			String tablename = RequestUtils.getString(request, "tablename");

			String referenced_table_obj = RequestUtils.getString(request, "referenced_table_obj", "");
			String referenced_field_obj = RequestUtils.getString(request, "referenced_field_obj", "");

			String referenced_table = "";
			String userindex = "";
			if (StringUtils.isNotEmpty(referenced_table_obj)) {
				JSONArray tableArray = JSONArray.parseArray(referenced_table_obj);
				JSONObject tableObj = tableArray.getJSONObject(0);
				referenced_table = tableObj.getString("TableName");
				userindex = referenced_table + "_id";
			}

			String referenced_field = "";
			if (StringUtils.isNotEmpty(referenced_field_obj)) {
				JSONArray fieldArray = JSONArray.parseArray(referenced_field_obj);
				JSONObject fieldObj = fieldArray.getJSONObject(0);
				referenced_field = fieldObj.getString("ColumnName");
			}

			String name = RequestUtils.getString(request, "name");
			String fieldType = RequestUtils.getString(request, "fieldType");
			int fieldLen = RequestUtils.getInt(request, "fieldLen");
			String columnType = ColumnTypeUtils.getColumnType(fieldType, fieldLen);

			CellDataField cellDataField = cellDataFieldService.getCellDataField(fieldId);
			cellDataField.setUserIndex(userindex);
			cellDataField.setTableName(referenced_table);
			cellDataField.setFormula("");
			cellDataField.setDname(referenced_field);

			FieldInterface fieldInterface = fieldInterfaceService.getFieldInterface(listId);
			fieldInterface.setFname(StringUtils.trim(name));
			fieldInterface.setDtype(fieldType);
			fieldInterface.setStrlen(fieldLen);

			tableActionService.modifyColumn(tablename, columnType, cellDataField, fieldInterface);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 删除字段
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@POST
	@Path("/deleteColumn")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteColumn(@Context HttpServletRequest request) {
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			String tablename = RequestUtils.getString(request, "tablename");
			String FieldId = RequestUtils.getString(request, "FieldId");
			String listId = RequestUtils.getString(request, "listId");
			String dname = RequestUtils.getString(request, "dname");

			tableActionService.deleteColumn(tablename, dname, listId, FieldId);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 查询引用表及引用字段
	 * 
	 * @param request
	 * @return
	 */
	@GET
	@POST
	@Path("/selectReferenced")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] selectReferenced(@Context HttpServletRequest request) {
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			String tablename = RequestUtils.getString(request, "tablename");
			String fieldname = RequestUtils.getString(request, "fieldname");

			JSONObject results = new JSONObject();

			CellDataTable table = cellDataTableService.getCellDataTableByTableName(tablename);
			CellDataField field = cellDataFieldService.getCellDataFieldByFieldName(fieldname);
			FieldInterface fieldInterface = fieldInterfaceService.getFieldInterface(table.getId(), fieldname);

			JSONArray fieldArray = new JSONArray();
			JSONObject tempObject = new JSONObject();
			tempObject.put("FieldTable", table.getTableName());
			tempObject.put("FieldLength", fieldInterface.getStrlen());
			tempObject.put("FieldType", fieldInterface.getDtype());
			tempObject.put("name", fieldInterface.getFname());
			tempObject.put("ColumnName", field.getFieldName());
			tempObject.put("FieldId", field.getId());
			fieldArray.add(tempObject);
			results.put("fieldArray", fieldArray);

			String tableType = "1";
			if (table.getFileDotFileId() != null && StringUtils.isNotEmpty(table.getFileDotFileId())) {
				tableType = "2";
			}

			JSONArray tableArray = new JSONArray();
			tempObject = new JSONObject();
			tempObject.put("TableId", table.getId());
			tempObject.put("tableType", tableType);
			tempObject.put("TableName", table.getTableName());
			tempObject.put("name", table.getName());
			tableArray.add(tempObject);
			results.put("tableArray", tableArray);

			return results.toJSONString().getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
	}

	@Resource(name = "com.glaf.isdp.service.tableActionService")
	public void setTableActoinService(TableActionService tableActionService) {
		this.tableActionService = tableActionService;
	}

	@Resource(name = "com.glaf.isdp.service.cellDataTableService")
	public void setCellDataTableService(CellDataTableService cellDataTableService) {
		this.cellDataTableService = cellDataTableService;
	}

	@Resource(name = "com.glaf.isdp.service.cellDataFieldService")
	public void setCellDataFieldService(CellDataFieldService cellDataFieldService) {
		this.cellDataFieldService = cellDataFieldService;
	}

	@Resource
	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

}
