package com.glaf.etl.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.DataRequest;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.util.DataSetJsonFactory;
import com.glaf.etl.domain.ETLDataSrc;
import com.glaf.etl.query.ETLDataSrcQuery;
import com.glaf.etl.service.ETLDataSrcService;
import com.glaf.etl.service.ETLDataTransferService;
import com.glaf.form.core.util.MutilDatabaseBean;

/**
 * 
 * 
 * @author lvd
 *
 */
@Controller("/datasrc")
@RequestMapping("/datasrc")
public class ETLDataSrcController {
	protected static final Log logger = LogFactory.getLog(ETLDataSrcController.class);

	@Autowired
	protected ETLDataSrcService etlDataInService;

	@Autowired
	protected ETLDataTransferService etlDataTransferService;

	@Autowired
	protected MutilDatabaseBean mutilDatabaseBean;

	@Autowired
	protected ITablePageService tablePageService;

	@Autowired
	protected IDatabaseService databaseService;

	@RequestMapping("/previewJSON")
	@ResponseBody
	public byte[] previewJSON(HttpServletRequest request) throws IOException {

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		DataSetBuilder builder = new DataSetBuilder();
		String datasetId = request.getParameter("id");

		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(datasetId)) {
			ETLDataSrc etlDataSrc = etlDataInService.getETLDataSrc(datasetId);

			Long dataBaseId = etlDataSrc.getDatabaseId();
			String sql = new String(etlDataSrc.getSql(), "UTF-8");

			int pageNo = RequestUtils.getInt(request, "page", 1);
			int limit = RequestUtils.getInt(request, "rows", 10);
			int start = (pageNo - 1) * limit;
			if (start <= 0) {
				start = 0;
			}
			if (limit <= 0) {
				limit = Paging.DEFAULT_PAGE_SIZE;
			}
			// limit = pageNo * limit;

			// 执行查询
			List<Map<String, Object>> dataMaps1 = mutilDatabaseBean.getDataListBySql(sql, dataBaseId);
			int total = dataMaps1.size();

			JSONArray array = new JSONArray();
			if (total > 0) {
				List<Map<String, Object>> dataMaps = mutilDatabaseBean.getDataListByQueryCriteria(sql, start, limit,
						dataBaseId);
				for (Map<String, Object> map : dataMaps) {
					JSONObject jo = new JSONObject();
					Set<String> keys = map.keySet();
					Iterator<String> iterator = keys.iterator();
					while (iterator.hasNext()) {
						String key = (String) iterator.next();
						jo.put(key, map.get(key));
					}
					array.add(jo);
				}
			}
			result.put("rows", array);
			result.put("total", total);
			result.put("start", start);
			result.put("begin", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

		} else {
			result.put("total", 0);
			result.put("rows", new JSONArray());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/toSql")
	@ResponseBody
	// @Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] toSql(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		String parentId = request.getParameter("parentId");
		String json = request.getParameter("jsonResult");

		String sqlStr = request.getParameter("sql");
		String datasetId = request.getParameter("datasetId");
		String database = request.getParameter("database");
		JSONObject databaseObj = JSON.parseObject(database);

		logger.debug("json=" + json);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		DataSet dataset = null;
		JSONObject sqlObj = null;

		if (StringUtils.isNotEmpty(json)) {
			json = StringTools.replaceIgnoreCase(json, "\"select\"", "\"selectSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"from\"", "\"fromSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"join\"", "\"joinSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"where\"", "\"whereSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"groupBy\"", "\"groupBySegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"having\"", "\"havingSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"orderBy\"", "\"orderBySegments\"");

			logger.debug(json);
			// JsonFactory f = new JsonFactory();
			// ObjectMapper mapper = new ObjectMapper(f);
			try {
				JSONObject jsonObject = JSON.parseObject(json);

				dataset = DataSetJsonFactory.jsonToObject(jsonObject);
				// dataset = mapper.readValue(json, DataSet.class);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false, "解析JSON字符串失败，格式不正确！");
			}

			if (dataset == null) {
				return ResponseUtils.responseJsonResult(false, "参数格式不正确！");
			}

			DataSetBuilder builder = new DataSetBuilder();
			Map<String, Object> parameter = new HashMap<String, Object>(), parameter2 = new HashMap<String, Object>();
			parameter.put("HttpServletRequest", request);
			dataset.setLastestJson(json);

			parameter2.putAll(parameter);
			long time = DataSetBuilder.validate(dataset, parameter2);
			if (time > DataSetBuilder.VALIDATE_TIME_MILLIS) {// 大于5秒的不能通过
				responseJSON.put("time", time / 1000);
				return responseJSON.toString().getBytes("UTF-8");
			}

			Query query = builder.buildQuery(dataset, parameter);
			dataset.setSql(query.toSql());

			/*
			 * SqlExecutor sqlExecutor = SqlSegmentUtils.toSql(dataset, null);
			 * dataset.setSql(sqlExecutor.getSql());
			 */
			logger.debug("sql:" + dataset.getSql());

			QueryDefinition queryDefinition = new QueryDefinition();
			queryDefinition.setSql(dataset.getSql());
			queryDefinition.setParentId(parentId);
			queryDefinition.setDatabaseId(dataset.getDatabaseId());
			// MxTransformManager manager = new MxTransformManager();
			try {
				// manager.toTableDefinition(queryDefinition, dataset.getSql());
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false, "查询失败，SQL语句不正确！\n\n" + dataset.getSql());
			}

			if (StringUtils.isNotEmpty(dataset.getSql())) {
				responseJSON.put("sql", dataset.getSql());
				return responseJSON.toString().getBytes("UTF-8");
			}

		} else if (StringUtils.isNotEmpty(sqlStr)) {

			sqlObj = (JSONObject) JSON.parseArray(sqlStr).get(0);
			Database dBase = databaseService.getDatabaseById(databaseObj.getLong("databaseId"));
			String systemName = dBase.getName();
			Connection conn = DBConnectionFactory.getConnection(systemName);

			if (!DBUtils.isLegalQuerySql(" " + sqlObj.getString("sql"))) {
				return ResponseUtils.responseJsonResult(false, "SQL查询不合法！");
			}

			PreparedStatement ps;
			String error = null;
			try {
				ps = conn.prepareStatement(sqlObj.getString("sql"));
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData meta = rs.getMetaData();
				int total = meta.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
				error = e.getMessage();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (error != null) {
					return ResponseUtils.responseJsonResult(false, error);
				}
			}

			responseJSON.put("sql", sqlObj.getString("sql"));
			return responseJSON.toString().getBytes("UTF-8");
		}

		return null;
	}

	@RequestMapping("/save")
	@ResponseBody
	public byte[] save(@Context HttpServletRequest request) throws IOException {
		String parentId = request.getParameter("parentId");

		String datasetId = request.getParameter("datasetId");
		String database = request.getParameter("database");
		JSONObject databaseObject = JSON.parseObject(database);

		String json = request.getParameter("jsonResult");

		String sqlArray = request.getParameter("sql");

		JSONObject sqlObject = new JSONObject();
		if (StringUtils.isNotEmpty(sqlArray)) {
			sqlObject = (JSONObject) ((JSONArray) JSONArray.parse(sqlArray)).get(0);
		}
		String sqlStr = sqlObject.getString("sql");

		String actorId = RequestUtils.getActorId(request);
		JSONObject jsonObject = null;
		if (StringUtils.isNotEmpty(json)) {
			json = StringTools.replaceIgnoreCase(json, "\"select\"", "\"selectSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"from\"", "\"fromSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"join\"", "\"joinSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"where\"", "\"whereSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"groupBy\"", "\"groupBySegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"having\"", "\"havingSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"orderBy\"", "\"orderBySegments\"");

			logger.debug(json);

			try {
				jsonObject = JSON.parseObject(json);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false, "解析JSON字符串失败，格式不正确！");
			}
		}

		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>(), parameter2 = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		parameter2.putAll(parameter);

		// QueryDefinition queryDefinition = new QueryDefinition();
		// queryDefinition.setSql(query.toSql());
		// queryDefinition.setParentId(parentId);
		// queryDefinition.setDatabaseId(Long.parseLong(databaseObject.getString("databaseId")));
		// MxTransformManager manager = new MxTransformManager();
		try {
			// manager.toTableDefinition(queryDefinition, dataset.getSql());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false, "查询失败，SQL语句不正确！\n" + ex.getMessage());
		}

		ETLDataSrc model = new ETLDataSrc();
		// model.setConnectionAddr(databaseObject.getString("host"));

		String code = databaseObject.getString("code");
		Database dBase = null;
		if (Environment.DEFAULT_SYSTEM_NAME.equalsIgnoreCase(code)) {
			DatabaseQuery query = new DatabaseQuery();
			List<Database> list = databaseService.list(query);
			if (CollectionUtils.isNotEmpty(list)) {
				for (Database db : list) {
					if (StringUtils.equalsIgnoreCase(db.getMapping(), "master")) {
						dBase = db;
						break;
					}
				}
			}
		} else {
			dBase = databaseService.getDatabaseByCode(code);
		}


		if(dBase != null){
			model.setDatabaseId(dBase.getId());
		} else {
			throw new RuntimeException("数据库code :" + code + " 未找到!");
		}
		
	//	model.setDatabaseId(Long.parseLong(databaseObject.getString("databaseId"))); // 设置数据库ID

	//	dBase = databaseService.getDatabaseById(model.getDatabaseId());
		String databaseName = dBase.getDbname();
		model.setDatabaseName(databaseName);// 设置数据库名称

		String systemName = dBase.getName();
		Connection conn = DBConnectionFactory.getConnection(systemName);
		JSONArray columnsArray = new JSONArray();

		if (jsonObject != null) {

			DataSet ds0 = DataSetJsonFactory.jsonToObject(jsonObject);
			ds0.setLastestJson(json);
			Query query = builder.buildQuery(ds0, parameter);
			if (!DBUtils.isLegalQuerySql(query.toSql())) {
				return ResponseUtils.responseJsonResult(false, "SQL查询不合法！");
			}

			String sqlstr = query.toSql(); // 去除sql中的别名
			sqlstr = sqlstr.toUpperCase();
			String[] sqls = sqlstr.split(",");
			String[] newSqls = new String[sqls.length];
			String newSql = "";
			for (int i = 0; i < sqls.length; i++) {
				if (sqls[i].contains("AS")) {
					if (sqls[i].contains("FROM")) {
						newSqls[i] = sqls[i].substring(0, sqls[i].indexOf("AS"))
								+ sqls[i].substring(sqls[i].indexOf("FROM"));
					} else {
						newSqls[i] = sqls[i].substring(0, sqls[i].indexOf("AS"));
					}
				} else {
					newSqls[i] = sqls[i];
				}
				newSql = newSql + newSqls[i] + ",";
			}
			newSql = newSql.substring(0, newSql.lastIndexOf(","));
			model.setSql(newSql.getBytes("UTF-8")); // 设置sql

			JSONArray fromSegments = (JSONArray) jsonObject.get("fromSegments"); // 设置表名
			if (fromSegments.size() == 1) {
				String tableName = ((JSONObject) fromSegments.get(0)).getString("tableName");
				model.setTableName(tableName);
			} else {
				model.setTableName("");
			}

			model.setRestoreJson(json.toString().getBytes("UTF-8"));// 设置还原json

			JSONArray selectSegments = (JSONArray) jsonObject.get("selectSegments");
			String sql = query.toSql();

			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData meta = rs.getMetaData();
				int total = meta.getColumnCount();
				for (int i = 1; i <= total; i++) {
					String columnName = meta.getColumnName(i);
					// int columnType = meta.getColumnType(i);
					String dType = meta.getColumnTypeName(i);
					int size = meta.getColumnDisplaySize(i);
					int isNullable = meta.isNullable(i);

					String columnClassName = meta.getColumnClassName(i);
					int precision = meta.getPrecision(i);
					int scale = meta.getScale(i);
					String schemaName = meta.getSchemaName(i);

					JSONObject column = new JSONObject();
					for (Object object : selectSegments) {
						JSONObject jo = (JSONObject) object;
						if (columnName.equals(jo.get("columnLabel"))) {
							column.put("columnName", jo.get("columnName"));
							column.put("columnNameCN", jo.get("title"));
							column.put("tableName", jo.get("tableName"));
							column.put("tableNameCN", jo.get("tableNameCN"));
							column.put("dType", dType);
							column.put("size", size);
							column.put("isNullable", isNullable);
							column.put("javaType", columnClassName);
							column.put("precision", precision);
							column.put("scale", scale);
							column.put("schemaName", schemaName);
						}
					}
					columnsArray.add(column);
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.fillInStackTrace();
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else {

			if (StringUtils.isNotEmpty(sqlStr)) {

				if (!DBUtils.isLegalQuerySql(" " + sqlStr)) {
					return ResponseUtils.responseJsonResult(false, "SQL查询不合法！");
				}

				String error = null;
				model.setSql(sqlStr.getBytes("UTF-8"));// 设置sql

				try {
					String sql = sqlStr;
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					ResultSetMetaData meta = rs.getMetaData();
					int total = meta.getColumnCount();

					for (int i = 1; i <= total; i++) {
						String columnName = meta.getColumnName(i);
						String tableName = meta.getTableName(i);
						String dType = meta.getColumnTypeName(i);
						int size = meta.getColumnDisplaySize(i);
						int isNullable = meta.isNullable(i);

						String columnClassName = meta.getColumnClassName(i);
						int precision = meta.getPrecision(i);
						int scale = meta.getScale(i);
						String schemaName = meta.getSchemaName(i);

						JSONObject column = new JSONObject();
						column.put("columnName", columnName);
						column.put("columnNameCN", columnName);
						column.put("tableName", tableName);
						column.put("dType", dType);
						column.put("size", size);
						column.put("isNullable", isNullable);
						column.put("javaType", columnClassName);
						column.put("precision", precision);
						column.put("scale", scale);
						column.put("schemaName", schemaName);

						columnsArray.add(column);
					}
					rs.close();
					ps.close();
				} catch (SQLException e) {
					e.fillInStackTrace();
					e.printStackTrace();
					error = e.getMessage();
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (error != null) {
						return ResponseUtils.responseJsonResult(false, error);
					}
				}
			}

		}

		model.setColumnInfos(columnsArray.toJSONString().getBytes("UTF-8"));

		model.setRestoreJson("".getBytes("UTF-8"));
		model.setUpdateBy(actorId);
		Date date = new Date();
		model.setUpdateTime(date);

		if (StringUtils.isNotEmpty(datasetId)) {
			model.setId(datasetId);
		}
		etlDataInService.save(model);

		JSONObject result = new JSONObject();
		result.putAll(model.toJsonObject());
		// long time = DataSetBuilder.validate(dataset, parameter2);
		// if (time > DataSetBuilder.validateTime) {// 大于5秒的不能通过
		// result.put("time", time / 1000);
		// }
		result.put("message", "保存数据源成功");
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/getLastestJson")
	@ResponseBody
	public byte[] getLastestJson(@Context HttpServletRequest request) throws IOException {
		String datasetId = request.getParameter("datasetId");

		ETLDataSrc model = etlDataInService.getETLDataSrc(datasetId);

		if (model != null) {
			String restoreJson = new String(model.getRestoreJson(), "UTF-8");
			if (StringUtils.isNotEmpty(restoreJson)) {
				return model.getRestoreJson();
			} else {
				JSONObject result = new JSONObject();
				if (model.getSql() != null) {
					result.put("sql", new String(model.getSql(), "UTF-8"));
					return result.toJSONString().getBytes("UTF-8");
				} else {
					return "".getBytes("UTF-8");
				}

			}
		}
		return "".getBytes("UTF-8");
	}

	@RequestMapping("/listDataSources")
	@ResponseBody
	public byte[] listDataSources(HttpServletRequest request) throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");

		ETLDataSrcQuery query = new ETLDataSrcQuery();
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
		}

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int pageNo = 1;
		int limit = 10;
		String orderName = null;
		String order = null;
		if (StringUtils.isNotEmpty(page)) {
			pageNo = Integer.parseInt(page);
		}
		if (StringUtils.isNotEmpty(pageSize)) {
			limit = Integer.parseInt(pageSize);
		}

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		orderName = ParamUtils.getString(params, "sort[0][field]");
		order = ParamUtils.getString(params, "sort[0][dir]");

		JSONObject result = new JSONObject();
		int total = etlDataInService.getETLDataSrcCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			if (StringUtils.isEmpty(pageSize)) {
				limit = total;
			}
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<ETLDataSrc> list = etlDataInService.getETLDataSrcsByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (ETLDataSrc eTLDataSrc : list) {
					JSONObject rowJSON = eTLDataSrc.toJsonObject();
					rowJSON.put("id", eTLDataSrc.getId());
					rowJSON.put("rowId", eTLDataSrc.getId());
					rowJSON.put("eTLDataSrcId", eTLDataSrc.getId());
					rowJSON.put("startIndex", ++start);
					User createBy = userMap.get(eTLDataSrc.getCreateBy());
					if (createBy != null) {
						rowJSON.put("createByName", createBy.getName());
					}
					User updateBy = userMap.get(eTLDataSrc.getUpdateBy());
					if (updateBy != null) {
						rowJSON.put("updateByName", updateBy.getName());
					}
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

	@RequestMapping("/updateDataSource")
	@ResponseBody
	public byte[] updateDataSource(@Context HttpServletRequest request, ETLDataSrc etlDataSrc) throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		ETLDataSrc src = null;
		String message = null;
		if (StringUtils.isNotEmpty(etlDataSrc.getId())) {
			src = etlDataInService.getETLDataSrc(etlDataSrc.getId());
			if (StringUtils.isNotEmpty(etlDataSrc.getSourceName())) {
				src.setSourceName(etlDataSrc.getSourceName());
			}
			message = "数据源编辑成功";
		} else {
			src = etlDataSrc;

			src.setCreateBy(loginContext.getActorId());
			Date date = new Date();
			src.setCreateTime(date);

			message = "新增数据源成功";
		}

		etlDataInService.save(src);
		JSONObject result = new JSONObject();
		result.put("message", message);
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping("/deleteDataSource")
	@ResponseBody
	public byte[] deleteDataSource(@Context HttpServletRequest request) throws IOException {

		String sourceId = request.getParameter("sourceId");
		etlDataInService.deleteById(sourceId);
		JSONObject result = new JSONObject();
		result.put("message", "删除数据源成功");
		return result.toString().getBytes("UTF-8");
	}

}
