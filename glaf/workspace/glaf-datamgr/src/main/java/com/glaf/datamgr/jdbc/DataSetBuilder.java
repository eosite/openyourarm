/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.datamgr.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.FilterItem;
import org.apache.metamodel.query.FromItem;
import org.apache.metamodel.query.FunctionType;
import org.apache.metamodel.query.GroupByItem;
import org.apache.metamodel.query.JoinType;
import org.apache.metamodel.query.LogicalOperator;
import org.apache.metamodel.query.OrderByItem;
import org.apache.metamodel.query.OrderByItem.Direction;
import org.apache.metamodel.query.Query;
import org.apache.metamodel.query.SelectItem;
import org.apache.metamodel.schema.MutableColumn;
import org.apache.metamodel.schema.MutableTable;
import org.apache.metamodel.schema.Table;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.CollectionUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.domain.DataSetMapping;
import com.glaf.datamgr.domain.DataSetMappingItem;
import com.glaf.datamgr.domain.FromSegment;
import com.glaf.datamgr.domain.GroupBySegment;
import com.glaf.datamgr.domain.JoinSegment;
import com.glaf.datamgr.domain.OrderBySegment;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.WhereSegment;
import com.glaf.datamgr.query.DataSetMappingItemQuery;
import com.glaf.datamgr.query.DataSetMappingQuery;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetMappingItemService;
import com.glaf.datamgr.service.DataSetMappingService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.sqlparser.TranlateFactory;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.netty.util.concurrent.FastThreadLocal;

public class DataSetBuilder {
	protected static final Log logger = LogFactory.getLog(DataSetBuilder.class);

	protected static Cache<String, Query> cache = CacheBuilder.newBuilder().maximumSize(5000)
			.expireAfterWrite(30, TimeUnit.MINUTES).build();

	protected static Cache<String, String> jsonCache = CacheBuilder.newBuilder().maximumSize(5000)
			.expireAfterWrite(30, TimeUnit.MINUTES).build();

	private static ConcurrentMap<String, Table> myTableMap = new ConcurrentHashMap<String, Table>();

	// private static ConcurrentMap<String, IQueryRewriter> queryRewriterMap =
	// new ConcurrentHashMap<String, IQueryRewriter>();

	private static ConcurrentMap<String, String> systemNameMap = new ConcurrentHashMap<String, String>();

	// private static ConcurrentMap<String, String> columnMap = new
	// ConcurrentHashMap<String, String>();

	private static ThreadLocal<ConcurrentMap<String, String>> columnMap = new ThreadLocal<ConcurrentMap<String, String>>();

	protected static FastThreadLocal<Boolean> newVer = new FastThreadLocal<Boolean>();

	private static final String connector = "connector";

	private static final String operator = "operator";

	private static final String sourceTable = "sourceTable";

	private static final String sourceColumn = "sourceColumn";

	private static final String targetTable = "targetTable";

	private static final String targetColumn = "targetColumn";
	private static final String DATABASETYPEKEY = "__DATABASETYPEKEY__";

	private static final String items = "items";

	public static final String tableNameKey = "tableName";

	private long execTime;

	protected static Map<String, String> dataBasePing;

	static {
		dataBasePing = new HashMap<String, String>();
		dataBasePing.put("sqlserver", "+");
		dataBasePing.put(DBUtils.ORACLE, "||");
		dataBasePing.put(DBUtils.POSTGRESQL, "");
		dataBasePing.put("mysql", "");
		dataBasePing.put(DBUtils.DM_DBMS, "||");
	}

	private static volatile AtomicBoolean isInit = new AtomicBoolean(false);

	private static DataSetService dataSetService = null;

	public static DataSetService getDataSetService() {
		return dataSetService == null ? (dataSetService = ContextFactory.getBean("dataSetService")) : dataSetService;
	}

	/**
	 * 验证sql 执行时间
	 */
	public static final long VALIDATE_TIME_MILLIS = 15 * 1000;

	/**
	 * dataset 缓存key
	 */
	public static final String dr = "__dataset__region", use_query_cacheKey = "use_query_cache";

	public static void cacheDataSet(com.glaf.datamgr.domain.DataSet definition, boolean removeOnly) {
		boolean use_query_cache = SystemConfig.getBoolean(use_query_cacheKey);
		if (use_query_cache) {
			String key = definition.getId();
			CacheFactory.remove(dr, key);
			if (removeOnly) {

			} else {
				CacheFactory.put(dr, key, JSON.toJSONString(definition));
			}
		}
	}

	/**
	 * 获取数据集，优先从缓存里面取
	 * 
	 * @param datasetId
	 * @return
	 */
	public static com.glaf.datamgr.domain.DataSet getDataSet(String datasetId) {

		String region = dr;
		com.glaf.datamgr.domain.DataSet definition = null;
		boolean use_query_cache = SystemConfig.getBoolean(use_query_cacheKey);

		if (use_query_cache) {
			String str = CacheFactory.getString(region, datasetId);
			if (StringUtils.isNotBlank(str)) {
				definition = JSON.parseObject(str, com.glaf.datamgr.domain.DataSet.class);
			}
		}

		if (definition == null) {
			cacheDataSet(definition = getDataSet_(datasetId), false);
		}

		return definition;
	}

	/**
	 * 获取数据集，优先从历史库获取最新版本
	 * 
	 * @param datasetId
	 * @return
	 */
	public static com.glaf.datamgr.domain.DataSet getDataSet_(String datasetId) {
		com.glaf.datamgr.domain.DataSet definition = null;
		DataSetAuditService dataSetAuditService = ContextFactory.getBean("dataSetAuditService");
		DataSetAudit dataSetAudit = dataSetAuditService.getLastestDataSetAudit(datasetId);
		if (dataSetAudit != null) {
			definition = JSON.parseObject(dataSetAudit.getContent(), com.glaf.datamgr.domain.DataSet.class);
			definition.setId(datasetId);
			definition.setLastestJson(dataSetAudit.getContent());
		} else
			definition = getDataSetService().getDataSet(datasetId);
		return definition;
	}

	private static JSONObject getLastestDataSetAudit(com.glaf.datamgr.domain.DataSet definition) {

		if (definition.getLastJson() != null) {
			return definition.getLastJson();
		}

		String lastestJson = definition.getLastestJson();
		if (StringUtils.isBlank(lastestJson)) {
			DataSetAuditService dataSetAuditService = ContextFactory.getBean("dataSetAuditService");
			DataSetAudit dataSetAudit = dataSetAuditService.getLastestDataSetAudit(definition.getId());
			lastestJson = dataSetAudit.getContent();
			definition.setLastestJson(lastestJson);
		}
		definition.setLastJson(JSON.parseObject(lastestJson));

		return definition.getLastJson();
	}

	public static void clearAllCache() {
		cache.cleanUp();
		jsonCache.cleanUp();
		myTableMap = new ConcurrentHashMap<String, Table>();
		systemNameMap = new ConcurrentHashMap<String, String>();
		// queryRewriterMap = new ConcurrentHashMap<String, IQueryRewriter>();
	}

	public static void clearCache(String datasetId) {
		Iterator<String> iterator = systemNameMap.keySet().iterator();
		while (iterator.hasNext()) {
			String systemName = iterator.next();
			String cacheKey = "cache_ds_" + systemName + "_" + datasetId;
			cache.invalidate(cacheKey);

			cacheKey = "cache_json_" + systemName + "_" + datasetId;
			jsonCache.invalidate(cacheKey);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataSetBuilder builder = new DataSetBuilder();
		com.glaf.datamgr.domain.DataSet dataset = new com.glaf.datamgr.domain.DataSet();
		SelectSegment seg01 = new SelectSegment();
		seg01.setColumnName("COLUMNNAME_");
		seg01.setTableName("SYS_COLUMN");
		seg01.setOrdinal(0);
		dataset.addSelect(seg01);

		SelectSegment seg02 = new SelectSegment();
		seg02.setColumnName("JAVATYPE_");
		seg02.setTableName("SYS_COLUMN");
		seg02.setOrdinal(1);
		dataset.addSelect(seg02);

		SelectSegment seg03 = new SelectSegment();
		seg03.setColumnName("TABLENAME_");
		seg03.setTableName("SYS_TABLE");
		seg03.setOrdinal(2);
		dataset.addSelect(seg03);

		FromSegment seg11 = new FromSegment();
		seg11.setTableName("SYS_TABLE");
		dataset.addFrom(seg11);

		FromSegment seg12 = new FromSegment();
		seg12.setTableName("SYS_COLUMN");
		dataset.addFrom(seg12);

		JoinSegment seg21 = new JoinSegment();
		seg21.setConnector("INNER JOIN");
		seg21.setSourceTable("SYS_TABLE");
		seg21.setSourceColumn("TABLENAME_");
		seg21.setTargetTable("SYS_COLUMN");
		seg21.setTargetColumn("TABLENAME_");
		dataset.addJoin(seg21);

		WhereSegment seg32 = new WhereSegment();
		seg32.setConnector("AND");
		seg32.setTableName("SYS_TABLE");
		seg32.setColumnName("TABLENAME_");
		seg32.setOperator("=");
		seg32.setParameName("tableLike");
		dataset.addWhere(seg32);

		OrderBySegment seg41 = new OrderBySegment();
		seg41.setSort("asc");
		seg41.setColumnName("COLUMNNAME_");
		seg41.setTableName("SYS_COLUMN");

		dataset.addOrderBy(seg41);

		dataset.setDistinctFlag("Y");

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tableLike", "TMP_ROLE");

		JSONObject jsonObject = builder.getJson("default", dataset, 0, 100, parameter);
		System.out.println(jsonObject.toJSONString());
	}

	/**
	 * 排序
	 * 
	 * @param definition
	 * @param query
	 * @param parameter
	 */
	private void buildOrderBy(com.glaf.datamgr.domain.DataSet definition, Query query, Map<String, Object> parameter) {
		JSONObject dataSetJson = getLastestDataSetAudit(definition);
		String key = "orderBySegments";
		if (dataSetJson.containsKey(key) && !isNullOrEmpty(dataSetJson.getString(key))) {
			Direction d;
			JSONObject jsonObject = null;
			String expression, tableName, columnName, sort;
			JSONArray orderBySegments = dataSetJson.getJSONArray(key);
			if (CollectionUtils.isEmpty(orderBySegments)
					&& CollectionUtils.isNotEmpty(definition.getOrderBySegments())) {
				orderBySegments = new JSONArray();
				orderBySegments.addAll(definition.getOrderBySegments());
			}

			String expressionKey = "expression";
			Map<String, JSONObject> fromMap = new HashMap<String, JSONObject>();
			if (definition.getFromSegments() != null && !definition.getFromSegments().isEmpty()) {
				JSONObject json, expressionJson;
				String jsonStr;
				for (FromSegment seg : definition.getFromSegments()) {
					json = seg.toJsonObject();
					if (StringUtils.isNotEmpty(jsonStr = json.getString(expressionKey))) {
						try {
							expressionJson = JSON.parseObject(jsonStr);
						} catch (Exception ex) {
							expressionJson = null;
							logger.debug(ex.getMessage());
						}
					} else {
						expressionJson = null;
					}

					if (expressionJson != null) {
						json.put("isDataSet", StringUtils.isNotEmpty(expressionJson.getString("dataSetId")));
					}

					json.put(expressionKey, expressionJson);
					fromMap.put(seg.getTableName(), json);
					fromMap.put(seg.getTableName().toLowerCase(), json);
				}
			}

			for (int i = 0; i < orderBySegments.size(); i++) {
				jsonObject = orderBySegments.getJSONObject(i);

				expression = jsonObject.getString("expression");
				tableName = jsonObject.getString("tableName");
				columnName = jsonObject.getString("columnName");
				sort = jsonObject.getString("sort");
				d = StringUtils.equalsIgnoreCase(sort, "asc") ? Direction.ASC : Direction.DESC;

				if (!isNullOrEmpty(expression)) {
					JSONObject expItem = jsonObject.getJSONObject("expItem");
					if (expItem != null) {
						String expActVal = ExpressionConvertUtil.expressionConvert(expItem.getString("expActVal"),
								ExpressionConvertUtil.DATABASE_TYPE, parameter);
						expression = this.populateVal(expActVal, parameter);
						if (!isNullOrEmpty(expression)) {
							if (StringUtils.equalsIgnoreCase(expression, "true")) {
								// query.orderBy(tableName.toLowerCase() + "." +
								// columnName + " " + d);

								OrderByItem orderByItem = new OrderByItem(
										new SelectItem("", tableName.toLowerCase() + "." + //
												getColumnLabelFormat(tableName, columnName, fromMap)/* columnName */),
										d);
								query.orderBy(orderByItem);
							} else {
								// query.orderBy(expression + " " + d);
								OrderByItem orderByItem = new OrderByItem(new SelectItem("", expression), d);

								query.orderBy(orderByItem);
							}
						} else {
							// query.orderBy(tableName.toLowerCase() + "." +
							// columnName + " " + d);
							OrderByItem orderByItem = new OrderByItem(new SelectItem("", tableName.toLowerCase() + "." + //
									getColumnLabelFormat(tableName, columnName, fromMap)/* columnName */), d);
							query.orderBy(orderByItem);
						}
					}
				} else if (!isNullOrEmpty(tableName) && !isNullOrEmpty(columnName)) {
					// query.orderBy(tableName.toLowerCase() + "." + columnName
					// + " " + d);
					OrderByItem orderByItem = new OrderByItem(new SelectItem("", tableName.toLowerCase() + "." + //
							getColumnLabelFormat(tableName, columnName, fromMap)/* columnName */), d);
					query.orderBy(orderByItem);

				} else {
					if (!isNullOrEmpty(sort)) {
						query.orderBy(sort);
					}
				}
			}
		}
	}

	public Query buildQuery(com.glaf.datamgr.domain.DataSet definition, Map<String, Object> parameter) {
		return this.buildQuery(Environment.DEFAULT_SYSTEM_NAME, definition, parameter);
	}

	public Query buildQuery(String systemName, com.glaf.datamgr.domain.DataSet definition,
			Map<String, Object> parameter) {
		String cacheKey = "cache_ds_" + systemName + "_" + definition.getId();
		if (StringUtils.equals(definition.getCacheFlag(), "Y")
				&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
			systemNameMap.put(systemName, systemName);
			Query query = cache.getIfPresent(cacheKey);
			if (query != null) {
				return query;
			}
		}

		String verify = definition.getVerify();
		if (StringUtils.equalsIgnoreCase(verify, "N")) {
			throw new RuntimeException(String.format("数据集[%s]未通过验证!", definition.getName()));
		}

		definition = this.convertDataSet(definition, parameter);

		JSONObject audit = getLastestDataSetAudit(definition);

		newVer.set(audit.getBoolean("newVer"));

		// Properties props = DBConfiguration.getQueryRewriterMappings();
		long start = System.currentTimeMillis();
		Connection conn = null;
		// DataSet ds = null;
		try {

			systemName = this.getSystemName(systemName, definition.getDatabaseId());

			conn = DBConnectionFactory.getConnection(systemName);

			String dbType = DBConnectionFactory.getDatabaseType(conn);

			JdbcUtils.close(conn);

			// DataSetConnectionFactory.getInstance().register(definition.getId(),
			// start, conn);

			// JdbcDataContext dc = new JdbcDataContext(conn);
			// dc.setQueryRewriter(new DefaultQueryRewriter(dc));
			//
			// IQueryRewriter queryRewriter = queryRewriterMap.get(systemName);
			// if (queryRewriter != null) {
			// dc.setQueryRewriter(queryRewriter);
			// } else {
			// String className = props.getProperty(dbType);
			// if (StringUtils.isNotEmpty(className)) {
			// Object[] args = { dc };
			// queryRewriter = (IQueryRewriter)
			// ReflectUtils.instantiate(className, args);
			// dc.setQueryRewriter(queryRewriter);
			// queryRewriterMap.put(systemName, queryRewriter);
			// logger.debug("queryRewriter:" + className);
			// }
			// }

			Map<String, Object> tmp = new HashMap<String, Object>(), oldParameter = parameter;
			tmp.putAll(parameter);

			parameter = this.populateParameters(definition, tmp);
			if (parameter == null)
				parameter = new JSONObject();
			parameter.put(DATABASETYPEKEY, dbType);

			if (oldParameter != null) {// 添加默认参数
				for (String key : new String[] { "_ROWNUMBER_" }) {
					if (parameter.containsKey(key))
						oldParameter.put(key, parameter.get(key));
				}
			}

			Query query = this.getQuery(definition, parameter, null);
			// Query query = this.getQuery(definition, parameter, dc);

			if (StringUtils.equals(definition.getDistinctFlag(), "Y")) {
				query.selectDistinct();
			}

			if (definition.getOrderBySegments() != null && !definition.getOrderBySegments().isEmpty()) {
				this.buildOrderBy(definition, query, parameter);
			}

			if (StringUtils.equals(definition.getCacheFlag(), "Y")
					&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
				cache.put(cacheKey, query);
			}
			logger.debug("query SQL:" + query.toSql());
			return query;
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (conn != null) {
				JdbcUtils.close(conn);
				logger.debug("dataset connection close.");
			}
			long time = System.currentTimeMillis() - start;

			String log = String.format("%s[%s] 构建查询用时(毫秒):%s", definition.getName(), definition.getId(), time);

			logger.debug(log);
		}
	}

	public Query buildQuery(String datasetId, Map<String, Object> parameter) {
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		return this.buildQuery(Environment.DEFAULT_SYSTEM_NAME, definition, parameter);
	}

	public Query buildQuery(String systemName, String datasetId, Map<String, Object> parameter) {
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		return this.buildQuery(systemName, definition, parameter);
	}

	public Query buildQuery(String datasetId, String whereSql, String orderBySql, Map<String, Object> parameter) {
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);

		this.buildOuterSql(definition, whereSql, orderBySql, parameter);

		return this.buildQuery(Environment.DEFAULT_SYSTEM_NAME, definition, parameter);
	}

	// 组装 where having
	private void buildWhereAndHaving(com.glaf.datamgr.domain.DataSet definition, Query query,
			Map<String, Object> parameter) {

		JSONObject dataSetJson = getLastestDataSetAudit(definition);

		if (definition.getWhereSegments() != null && !definition.getWhereSegments().isEmpty()) {
			this.invokeWhereAndHaving(dataSetJson, definition, query, parameter, "where");
		}

		if (definition.getHavingSegments() != null && !definition.getHavingSegments().isEmpty()) {
			this.invokeWhereAndHaving(dataSetJson, definition, query, parameter, "having");
		}
	}

	/**
	 * having and where the same method
	 * 
	 * @param dataSetJson
	 * @param definition
	 * @param query
	 * @param parameter
	 * @param type
	 */
	public void invokeWhereAndHaving(JSONObject dataSetJson, com.glaf.datamgr.domain.DataSet definition, Query query,
			Map<String, Object> parameter, String type) {

		String expressionKey = "expression";
		Map<String, JSONObject> fromMap = new HashMap<String, JSONObject>();
		if (definition.getFromSegments() != null && !definition.getFromSegments().isEmpty()) {
			JSONObject json, expressionJson;
			String jsonStr;
			for (FromSegment seg : definition.getFromSegments()) {
				json = seg.toJsonObject();
				if (StringUtils.isNotEmpty(jsonStr = json.getString(expressionKey))) {
					try {
						expressionJson = JSON.parseObject(jsonStr);
					} catch (Exception ex) {
						expressionJson = null;
						logger.debug(ex.getMessage());
					}
				} else {
					expressionJson = null;
				}

				if (expressionJson != null) {
					json.put("isDataSet", StringUtils.isNotEmpty(expressionJson.getString("dataSetId")));
				}

				json.put(expressionKey, expressionJson);
				fromMap.put(seg.getTableName(), json);
				fromMap.put(seg.getTableName().toLowerCase(), json);
			}
		}

		JSONObject seg;
		FilterItem item = null;
		JSONArray jsonArray = null;

		LogicalOperator connector = LogicalOperator.AND;

		String key = type + "Segments", tree = type + "SegmentTree";

		if (dataSetJson.containsKey(key)) {
			jsonArray = dataSetJson.getJSONArray(key);
			if (CollectionUtils.isNotEmpty(jsonArray)) {
				for (int i = 0; i < jsonArray.size(); i++) {
					seg = jsonArray.getJSONObject(i);
					if (seg.get("datasetId") == null) {
						String express = seg.getString(expressionKey);
						if (express != null) {
							express = express.trim();
							if (express.toUpperCase().startsWith("AND")) {// metamodal
								express = express.substring(3);
								connector = LogicalOperator.AND;
							} else if (express.toUpperCase().startsWith("OR")) {
								express = express.substring(2);
								connector = LogicalOperator.OR;
							}
							item = new FilterItem(express);
						}
					}
					String exp = this.translateExpression(seg, parameter, fromMap);
					seg.put(expressionKey, exp);
				}
			}
		}

		List<FilterItem> items = null;
		if (dataSetJson.containsKey(tree)) {// 外掛規則樹
			if (jsonArray == null) {
				jsonArray = new JSONArray();
				jsonArray.addAll(definition.getWhereSegments());
			}
			items = this.getFilterItem(dataSetJson, tree, jsonArray);
		}

		if (item != null) {
			if (items != null && items.size() > 0) {
				List<FilterItem> items0 = new ArrayList<FilterItem>();
				items0.add(item);
				items0.add(new FilterItem(items));
				item = new FilterItem(connector, items0);// 组合外部条件与内部条件之间的连接
				// query.where(item);

				// ReflectUtils.invoke(query, type, new Object[]{item});

			} // else {
				// query.where(item);
				// }

			items = new ArrayList<FilterItem>();

			items.add(item);

			// ReflectUtils.invoke(query, type, new Object[]{item});
		}

		if (items != null && items.size() > 0) {
			item = new FilterItem(items);
			if (type.equalsIgnoreCase("where")) {
				query.where(item);
			} else if (type.equalsIgnoreCase("having")) {
				query.having(item);
			}
		}

	}

	// 获取条件
	public List<FilterItem> getFilterItem(JSONObject dataSetJson, String key, JSONArray wheres) {
		if (dataSetJson.containsKey(key)) {
			JSONArray array = JSON.parseArray(dataSetJson.getString(key));
			JSONObject where = new JSONObject();
			JSONObject seg;
			for (int i = 0; i < wheres.size(); i++) {
				seg = wheres.getJSONObject(i);
				if (!isNullOrEmpty(seg.getString("ordinal")))
					where.put(seg.getString("ordinal"), seg);
			}
			Map<String, List<JSONObject>> tree = treeMap(array, where);
			List<JSONObject> roots = tree.get("root");
			List<FilterItem> items = new ArrayList<FilterItem>();
			buildFilterItem(roots, tree, null, items);
			return items;
		}
		return null;
	}

	// 组装条件
	public List<FilterItem> buildFilterItem(List<JSONObject> list, Map<String, List<JSONObject>> tree,
			LogicalOperator connector, List<FilterItem> items) {
		JSONObject r;
		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				r = list.get(i);
				if (r.getBooleanValue("isParent")) {
					List<JSONObject> list1 = tree.get(r.get("id"));
					if (list1 == null)
						continue;
					List<FilterItem> items1 = new ArrayList<FilterItem>();
					LogicalOperator connector0 = LogicalOperator.OR;
					if (r.getString("connector").equalsIgnoreCase("AND")) {
						connector0 = LogicalOperator.AND;
					}
					items1 = buildFilterItem(list1, tree, connector0, items1);
					if (items1.size() > 0) {
						FilterItem item = new FilterItem(connector0, items1);
						items.add(item);
					}
				} else {
					String dynamic = r.getString("dynamic"), expression;
					if (dynamic == null || !dynamic.equalsIgnoreCase("Y")) {
						expression = r.getString("expression");
						if (!isNullOrEmpty(expression))
							items.add(new FilterItem(expression));
					}
				}
			}
		return items;
	}

	// 外挂树与条件联合
	public Map<String, List<JSONObject>> treeMap(JSONArray array, JSONObject where) {
		Map<String, List<JSONObject>> tree = new HashMap<String, List<JSONObject>>();
		JSONObject json;
		String pId, id;
		for (int i = 0; i < array.size(); i++) {
			json = array.getJSONObject(i);
			pId = json.getString("pId");
			id = json.getString("id");
			if (where.containsKey(id)) {
				json.putAll(where.getJSONObject(id));
			}
			if (pId == null)
				pId = "root";
			if (!tree.containsKey(pId)) {
				tree.put(pId, new ArrayList<JSONObject>());
			}
			tree.get(pId).add(json);
		}
		return tree;
	}

	public FunctionType getFunctionType(String type) {
		if (!isNullOrEmpty(type)) {
			switch (type.toUpperCase()) {
			case "COUNT":
				return FunctionType.COUNT;
			case "MAX":
				return FunctionType.MAX;
			case "MIN":
				return FunctionType.MIN;
			case "SUM":
				return FunctionType.SUM;
			case "AVG":
				return FunctionType.AVG;
			}
		}
		return null;
	}

	/**
	 * 获取JSON数据
	 * 
	 * @param systemName
	 *            系统名
	 * @param definition
	 *            数据集定义
	 * @param firstRow
	 *            起始行数，从0开始
	 * @param limit
	 *            每页记录数
	 * @param parameter
	 *            参数
	 * @return
	 */
	public JSONObject getJson(String systemName, com.glaf.datamgr.domain.DataSet definition, int firstRow, int limit,
			Map<String, Object> parameter) {
		String verify = definition.getVerify();
		if (StringUtils.equalsIgnoreCase(verify, "N")) {
			throw new RuntimeException(String.format("数据集[%s]未通过验证!", definition.getName()));
		}

		/**
		 * 处理验证数据集(如果验证数据集返回空，不执行)
		 */
		if (StringUtils.isNotBlank(definition.getExtText()) && StringUtils.equalsIgnoreCase(//
				definition.getAccessType(), "PUB")) {
			JSONObject json = JSON.parseObject(definition.getExtText());
			if (json.containsKey("valiDataSet")) {
				com.glaf.datamgr.domain.DataSet ds = /*
														 * getDataSetService()// .
														 */getDataSet(json.getString("valiDataSet"));
				if (ds != null) {
					json = new DataSetBuilder().getJson(systemName, ds, firstRow, limit, parameter);
					if (json.getLong("total") == 0) {
						return json;
					}
				}
			}
		}

		definition = this.convertDataSet(definition, parameter);

		JSONObject audit = getLastestDataSetAudit(definition);

		newVer.set(audit.getBoolean("newVer"));

		// Properties props = DBConfiguration.getQueryRewriterMappings();
		JSONObject jsonObject = new JSONObject();

		long start = System.currentTimeMillis();
		// CompiledQuery compiledQuery = null;
		Connection conn = null;
		// DataSet ds = null;
		try {
			systemName = this.getSystemName(systemName, definition.getDatabaseId());
			logger.debug("@@->systemName:" + systemName);
			conn = DBConnectionFactory.getConnection(systemName);

			DataSetConnectionFactory.getInstance().register(definition.getId(), start, conn);

			String dbType = DBConnectionFactory.getDatabaseType(conn);

			// JdbcDataContext dc = new JdbcDataContext(conn);
			// dc.setQueryRewriter(new DefaultQueryRewriter(dc));
			//
			// IQueryRewriter queryRewriter = queryRewriterMap.get(systemName);
			// if (queryRewriter != null) {
			// dc.setQueryRewriter(queryRewriter);
			// logger.debug("queryRewriter:" +
			// queryRewriter.getClass().getName());
			// } else {
			// String className = props.getProperty(dbType);
			// if (StringUtils.isNotEmpty(className)) {
			// Object[] args = { dc };
			// queryRewriter = (IQueryRewriter)
			// ReflectUtils.instantiate(className, args);
			// dc.setQueryRewriter(queryRewriter);
			// queryRewriterMap.put(systemName, queryRewriter);
			// logger.debug("queryRewriter:" + className);
			// }
			// }
			parameter = this.populateParameters(definition, parameter);
			if (parameter == null)
				parameter = new JSONObject();
			else {
				if (parameter.containsKey("_ROWNUMBER_") && //
						MapUtils.getInteger(parameter, "_ROWNUMBER_") > 0) {
					limit = MapUtils.getInteger(parameter, "_ROWNUMBER_");
				}
			}

			jsonObject.put("start", firstRow);
			jsonObject.put("begin", firstRow);

			jsonObject.put("limit", limit);
			jsonObject.put("pageSize", limit);

			parameter.put(DATABASETYPEKEY, dbType);
			List<Object> values = new ArrayList<Object>();

			Query query = this.getQuery(definition, parameter, null);
			// Query query = this.getQuery(definition, parameter, dc);

			if (StringUtils.equals(definition.getDistinctFlag(), "Y")) {
				query.selectDistinct();
			}

			if (definition.getOrderBySegments() != null && !definition.getOrderBySegments().isEmpty()) {
				this.buildOrderBy(definition, query, parameter);
			}

			String querySql = query.toSql();

			logger.debug("原始 SQL :" + querySql);

			querySql = TranlateFactory.translateSQL(querySql);

			String countSql = PagerUtils.count(querySql, dbType);

			// System.out.println(countSql);

			Object[] args = values.toArray();

			QueryRunner runner = new QueryRunner(true);

			// Query countQuery = new Query().from(new
			// FromItem(query).setAlias("t")).selectCount();

			// String countSql = countQuery.toSql();
			// compiledQuery = dc.compileQuery(countQuery);
			logger.debug("count SQL:" + countSql);
			// ds = dc.executeQuery(compiledQuery, args);
			Object o = runner.query(conn, countSql, new ScalarHandler<Object>(), args);
			Long total = 0L;
			if (o != null) {
				total = TypeUtils.castToLong(o);
			}

			// if (ds.next()) {
			// total = Long.parseLong(ds.getRow().getValue(0).toString());
			// }
			// ds.close();
			// compiledQuery.close();

			if (total != null && total > 0) {
				jsonObject.put("total", total);
				JSONArray array = new JSONArray();
				jsonObject.put("rows", array);

				JSONArray encrypts = getDataSetService().getDataSetEncrypts(definition.getId());

				jsonObject.put("encrypts", encrypts);
				// if (StringUtils.equals(definition.getDistinctFlag(), "Y")) {
				// query.selectDistinct();
				// }
				//
				// if (definition.getOrderBySegments() != null &&
				// !definition.getOrderBySegments().isEmpty()) {
				// this.buildOrderBy(definition, query, parameter);
				// }

				Boolean om = MapUtils.getBoolean(parameter, "om", false);// 只获取mapping字段

				Dialect dialect = DBConfiguration.getDatabaseDialect(conn);
				if (dialect != null && dialect.supportsPhysicalPage()) {

					String sql = querySql;// TranlateFactory.translateSQL(/*query.toSql()*/querySql);

					sql = dialect.getLimitString(sql, firstRow, limit);

					logger.debug(sql);

					ResultSet rs = null;

					PreparedStatement pstmt = null;
					try {
						pstmt = conn.prepareStatement(sql);
						JdbcUtils.fillStatement(pstmt, Arrays.asList(args));
						rs = pstmt.executeQuery();
						int count = 1;
						if (rs != null && rs.next()) {
							String name = null;
							String mapping = null;
							Object value = null;
							JSONObject json = null;
							ResultSetMetaData rsmd = rs.getMetaData();
							int cc = rsmd.getColumnCount();

							ConcurrentMap<String, String> cm = columnMap.get();
							do {
								json = new JSONObject();
								for (int i = 1; i <= cc; i++) {

									/**
									 * 还原转换过的列
									 */

									name = TranlateFactory.getColumn(rsmd.getColumnLabel(i));

									/**
									 * 增加映射字段k,v
									 */
									mapping = cm.get(name);

									value = ValueConverter.convert(rs.getObject(i));
									if (!om) {
										json.put(name, value);
										json.put(name.toLowerCase(), value);
									}
									if (StringUtils.isNotEmpty(mapping)) {
										json.put(mapping, value);
										json.put(mapping.toLowerCase(), value);
									} else {
										mapping = cm.get(name.toLowerCase());
										if (StringUtils.isNotEmpty(mapping)) {
											json.put(mapping, value);
											json.put(mapping.toLowerCase(), value);
										}
									}
								}
								json.put("startIndex", count++);
								json.put("row_number", ++firstRow);
								array.add(json);
							} while (rs.next());
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.debug(ex.getMessage());
					} finally {
						JdbcUtils.close(rs);
						JdbcUtils.close(pstmt);
					}
				}

			} else {
				jsonObject.put("total", 0);
				jsonObject.put("rows", new JSONArray());
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (conn != null) {
				DataSetConnectionFactory.getInstance().unregister(definition.getId(), start, conn);
				JdbcUtils.close(conn);
				logger.debug("dataset connection close.");
			}
			long time = System.currentTimeMillis() - start;

			String log = String.format("%s[%s] 数据集查询用时(毫秒):%s", definition.getName(), definition.getId(),
					this.execTime = time);

			logger.debug(log);
		}
		return jsonObject;
	}

	private static String getColumnLabelFormat(String table, String column, Map<String, JSONObject> fromMap) {
		if (isDataSet(fromMap, table)) {
			column = getColumnLabelFormat(column);
		}
		return column;
	}

	private static String getColumnLabelFormat(String label) {
		if (newVer.get() != null && newVer.get() == true) {
			return label;
		}
		return "\"" + label + "\"";
	}

	protected Query getQuery(com.glaf.datamgr.domain.DataSet definition, Map<String, Object> parameter,
			JdbcDataContext dc) {
		if (parameter == null)
			parameter = new HashMap<String, Object>();
		Query query = new Query();

		if (columnMap.get() == null)
			columnMap.set(new ConcurrentHashMap<String, String>());
		boolean dm = parameter.getOrDefault(DATABASETYPEKEY, "").equals(DBUtils.DM_DBMS) //
				|| parameter.getOrDefault(DATABASETYPEKEY, "").equals(DBUtils.ORACLE);

		String expressionKey = "expression";
		Map<String, JSONObject> fromMap = new HashMap<String, JSONObject>();

		JSONArray fromSegments;
		JSONObject lastJson = getLastestDataSetAudit(definition);
		if (CollectionUtils.isNotEmpty(fromSegments = lastJson.getJSONArray("fromSegments"))) {
			JSONObject json, expressionJson;
			String jsonStr;
			for (int i = 0; i < fromSegments.size(); i++) {
				json = fromSegments.getJSONObject(i);
				if (StringUtils.isNotEmpty(jsonStr = json.getString(expressionKey))) {
					try {
						expressionJson = JSON.parseObject(jsonStr);
					} catch (Exception ex) {
						expressionJson = null;
						logger.debug(ex.getMessage());
					}
				} else {
					expressionJson = null;
				}

				if (expressionJson != null) {
					json.put("isDataSet", StringUtils.isNotEmpty(expressionJson.getString("dataSetId")));
				}

				json.put(expressionKey, expressionJson);
				fromMap.put(json.getString(tableNameKey), json);
				fromMap.put(json.getString(tableNameKey).toLowerCase(), json);
			}
		}
		String columnLabel;
		for (SelectSegment seg : definition.getSelectSegments()) {
			if (!Boolean.parseBoolean(seg.getOutput())) {// 没有勾选输出
				continue;
			}
			columnLabel = seg.getColumnLabel();
			if (seg.getColumnMapping() != null) {
				columnMap.get().put(columnLabel, seg.getColumnMapping());
				columnMap.get().put(columnLabel.toLowerCase(), seg.getColumnMapping().toLowerCase());
			}
			columnMap.get().put(getColumnAlias(columnLabel), columnLabel);
			/**
			 * 如果是物理字段
			 */

			if (StringUtils.isNotEmpty(seg.getTableName()) && StringUtils.isNotEmpty(seg.getColumnName())) {
				// String tableName = seg.getTableName();

				FunctionType ft = getFunctionType(seg.getAggregate());

				boolean isDataSet = isDataSet(fromMap, seg.getTableName());

				if (StringUtils.isNotEmpty(seg.getColumnLabel())) {// 保证不会为空
					String alias = seg.getTableName().toLowerCase();
					// SelectItem item = new SelectItem(ft, alias + "." +
					// seg.getColumnName(), seg.getColumnLabel());
					SelectItem item = new SelectItem(ft, alias + "."
							+ (dm && isDataSet ? getColumnLabelFormat(seg.getColumnName()) : seg.getColumnName()),
							getColumnLabelFormat(seg.getColumnLabel()));
					query.select(item);
				} else {
					/*
					 * Table table = this.getTable(dc, tableName.toLowerCase()); if (table != null)
					 * { Column column = table.getColumnByName(seg.getColumnName());
					 * query.select(ft, column); }
					 */
				}
			} else if (StringUtils.isNotEmpty(seg.getExpression())) {
				/**
				 * 如果是表达式
				 */
				if (StringUtils.isNotEmpty(seg.getColumnLabel())) {

					String express = seg.getExpression();
					String exp = express;
					try {
						JSONObject json = JSON.parseObject(express);
						if (json.containsKey("columnCode")) {
							exp = json.getString("columnCode");
						} else {
							exp = json.getString("code");
						}
					} catch (Exception e) {
						logger.debug(e.getMessage());
					}
					exp = ExpressionConvertUtil.expressionConvert(exp, ExpressionConvertUtil.DATABASE_TYPE, parameter);
					query.select(exp, getColumnLabelFormat(seg.getColumnLabel()));
					// query.select(exp, seg.getColumnLabel());
				}
			}
		}

		if (definition.getJoinSegments() != null && !definition.getJoinSegments().isEmpty()) {
			if (!this.initJoin(query, definition, parameter, fromMap))
				for (JoinSegment seg : definition.getJoinSegments()) {
					if (StringUtils.isNotEmpty(seg.getSourceTable()) && StringUtils.isNotEmpty(seg.getSourceColumn())
							&& StringUtils.isNotEmpty(seg.getTargetTable())
							&& StringUtils.isNotEmpty(seg.getTargetColumn())) {

						FromItem leftSide = null, rightSide = null, item;
						JoinType joinType = JoinType.INNER;
						if (StringUtils.containsIgnoreCase(seg.getConnector(), "LEFT")) {
							joinType = JoinType.LEFT;
						} else if (StringUtils.containsIgnoreCase(seg.getConnector(), "RIGHT")) {
							joinType = JoinType.RIGHT;
						}

						leftSide = this.getFromItem(seg.getSourceTable(), parameter, fromMap);

						rightSide = this.getFromItem(seg.getTargetTable(), parameter, fromMap);

						SelectItem[] leftOn = new SelectItem[] {
								new SelectItem(new MutableColumn(dm && isDataSet(fromMap, seg.getSourceTable()) ? //
										getColumnLabelFormat(seg.getSourceColumn()) : seg.getSourceColumn())) };

						SelectItem[] rightOn = new SelectItem[] {
								new SelectItem(new MutableColumn(dm && isDataSet(fromMap, seg.getTargetTable()) ? //
										getColumnLabelFormat(seg.getTargetColumn()) : seg.getTargetColumn())) };

						item = new FromItem(joinType, leftSide, rightSide, leftOn, rightOn);

						query.from(item);

					}
				}
		} else if (definition.getFromSegments() != null && !definition.getFromSegments().isEmpty()) {
			for (FromSegment seg : definition.getFromSegments()) {
				String tableName = seg.getTableName(), expression = seg.getExpression();
				if (StringUtils.isNotBlank(expression)) { // 动态数据集(动态表名)
					Object value;
					JSONObject expressionJson = JSON.parseObject(expression);
					String expActVal = expressionJson.getString("expActVal"), param;
					JSONArray varValArr = expressionJson.getJSONArray("varVal");
					if (CollectionUtils.isNotEmpty(varValArr)) {
						/**
						 * 取出默认值再做替换
						 */
						for (int i = 0, size = varValArr.size(); i < size; i++) {
							JSONObject varValJson = varValArr.getJSONObject(i);
							JSONObject valueJson = varValJson.getJSONObject("value");
							param = valueJson.getString("param");
							value = MapUtils.getObject(parameter, param, valueJson.get("defVal"));
							if (value != null && value.toString().trim().length() > 0) {
								parameter.put(param, value);
							}
						}
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
								ExpressionConvertUtil.DATABASE_TYPE, parameter);

						if (StringUtils.isNotBlank(expActVal)) {
							tableName = expActVal;
						}
					}
				}

				if (StringUtils.isNotEmpty(tableName)) {

					FromItem fi = this.getFromItem(tableName, parameter, fromMap);

					query.from(fi);

				}
			}
		}

		if (parameter.containsKey("buildWhereAndHaving")) {
			this.buildWhereAndHaving(definition, query, new HashMap<String, Object>());
		} else {
			this.buildWhereAndHaving(definition, query, parameter);
		}

		if (definition.getGroupBySegments() != null && !definition.getGroupBySegments().isEmpty()) {
			for (GroupBySegment seg : definition.getGroupBySegments()) {
				if (StringUtils.isNotEmpty(seg.getTableName()) && StringUtils.isNotEmpty(seg.getColumnName())) {
					String tableName = seg.getTableName();
					// query.groupBy(tableName + "." +
					// getColumnLabelFormat(seg.getColumnName()));

					GroupByItem item = new GroupByItem(new SelectItem("", tableName.toLowerCase() + "." + //
							getColumnLabelFormat(tableName, seg.getColumnName(), fromMap)));

					query.groupBy(item);
				}
				if (StringUtils.isEmpty(seg.getTableName()) && StringUtils.isNotEmpty(seg.getColumnName())) { // 表达式分组
					String exp = ExpressionConvertUtil.expressionConvert(seg.getColumnName(),
							ExpressionConvertUtil.DATABASE_TYPE, parameter);
					if (exp != null)
						query.groupBy(exp);
				}

			}
		}
		return query;
	}

	protected static boolean isDataSet(Map<String, JSONObject> fromMap, String table) {
		boolean isDataSet = false;
		table = table.toLowerCase();
		if (fromMap.containsKey(table)) {
			isDataSet = fromMap.get(table).getBooleanValue("isDataSet");
		}
		return isDataSet;
	}

	@SuppressWarnings("unchecked")
	public FromItem getFromItem(String tableName, Map<String, Object> parameter, Map<String, JSONObject> fromMap) {
		FromItem item = new FromItem(tableName);
		if (parameter != null && parameter.containsKey(tableNameKey)) {// 外部动态传参[表名]
			// 并且表名参数在当前数据集参数定义里面

			String populateParametersKey = "populateParameters";
			Map<String, Object> populateParameters = null;
			if (parameter.containsKey(populateParametersKey)) {
				populateParameters = MapUtils.getMap(parameter, populateParametersKey, null);
			}

			if (populateParameters == null || populateParameters.containsKey(tableNameKey))
				item = new FromItem(MapUtils.getString(parameter, tableNameKey));
		}

		JSONObject json, expressionJson;
		if (fromMap.containsKey(tableName)) {
			json = fromMap.get(tableName);
			String key = "tb_params";

			{ // 动态表名参数
				if (json.containsKey(key)) {
					JSONObject j = json.getJSONObject(key);
					String expActVal = j.getString("expActVal");
					if (StringUtils.isNotEmpty(expActVal)) {
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
								ExpressionConvertUtil.DATABASE_TYPE, parameter);
						if (StringUtils.isNotBlank(expActVal)) {
							item = new FromItem(expActVal);
						}
					}
				}
			}

			if (json.containsKey(key = "expression")) {
				expressionJson = json.getJSONObject(key);

				/**
				 * 如果是数据集,先构造子数据集，相当于子查询[可嵌套<建议不要嵌套太多>]
				 */
				key = "dataSetId";
				if (expressionJson != null && expressionJson.containsKey(key)) {
					Query subQuery = new DataSetBuilder()//
							.buildQuery(expressionJson.getString(key), parameter);
					item = new FromItem(subQuery);
				}
			}
		}
		return item.setAlias(tableName);
	}

	/**
	 * 获取JSON数据
	 * 
	 * @param datasetId
	 *            数据集定义编号
	 * @param firstRow
	 *            起始行数，从1开始
	 * @param limit
	 *            每页记录数
	 * @param parameter
	 *            参数
	 * @return
	 */
	public JSONObject getJson(String datasetId, int firstRow, int limit, Map<String, Object> parameter) {
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		return this.getJson(systemName, definition, firstRow, limit, parameter);
	}

	/**
	 * 获取JSON数据
	 * 
	 * @param systemName
	 *            系统名
	 * @param datasetId
	 *            数据集定义编号
	 * @param firstRow
	 *            起始行数，从1开始
	 * @param limit
	 *            每页记录数
	 * @param parameter
	 *            参数
	 * @return
	 */
	public JSONObject getJson(String systemName, String datasetId, int firstRow, int limit,
			Map<String, Object> parameter) {
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		return this.getJson(systemName, definition, firstRow, limit, parameter);
	}

	public JSONObject getJson(String datasetId, String whereSql, String orderBySql, int firstRow, int limit,
			Map<String, Object> parameter) {
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		String systemName = this.buildOuterSql(definition, whereSql, orderBySql, parameter);
		return this.getJson(systemName, definition, firstRow, limit, parameter);
	}

	private String buildOuterSql(com.glaf.datamgr.domain.DataSet definition, String whereSql, String orderBySql,
			Map<String, Object> parameter) {
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		JSONObject dataSetJson = getLastestDataSetAudit(definition);
		if (!isNullOrEmpty(whereSql)) {// 外部传入查询条件
			WhereSegment segment = new WhereSegment();
			segment.setExpression(whereSql);
			definition.addWhere(segment);

			JSONArray whereSegments = null;
			String whereSegmentsKey = "whereSegments";
			if (dataSetJson.containsKey(whereSegmentsKey)) {
				whereSegments = dataSetJson.getJSONArray(whereSegmentsKey);
			} else {
				whereSegments = new JSONArray();
			}
			whereSegments.add(segment.toJsonObject());
			dataSetJson.put(whereSegmentsKey, whereSegments);
			definition.setLastestJson(dataSetJson.toJSONString());
		}

		if (!isNullOrEmpty(orderBySql)) {// 外部传入排序
			OrderBySegment segment = new OrderBySegment();
			segment.setSort(orderBySql);
			definition.addOrderBy(segment);

			JSONArray orderBySegments = null;
			String orderBySegmentsKey = "orderBySegments";
			if (dataSetJson.containsKey(orderBySegmentsKey)) {
				orderBySegments = dataSetJson.getJSONArray(orderBySegmentsKey);
			} else {
			}
			orderBySegments = new JSONArray();
			orderBySegments.add(segment.toJsonObject());
			dataSetJson.put(orderBySegmentsKey, orderBySegments);
			definition.setLastestJson(dataSetJson.toJSONString());

		}

		if (parameter != null) {
			if (parameter.containsKey("databaseId")) {
				Long databaseId = ParamUtils.getLongValue(parameter, "databaseId");
				definition.setDatabaseId(databaseId);
			}
			if (parameter.containsKey("systemName")) {
				systemName = MapUtils.getString(parameter, "systemName", Environment.DEFAULT_SYSTEM_NAME);
			}
		}
		return systemName;

	}

	/**
	 * 获取JSON数组数据
	 * 
	 * @param datasetId
	 *            数据集定义编号
	 * @param parameter
	 *            参数
	 * @return
	 */
	public JSONArray getJsonArray(String datasetId, Map<String, Object> parameter) {
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		String cacheKey = "cache_json_" + systemName + "_" + datasetId;
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		if (StringUtils.equals(definition.getCacheFlag(), "Y")
				&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
			String text = jsonCache.getIfPresent(cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					return JSON.parseObject(text).getJSONArray("rows");
				} catch (Exception ex) {
				}
			}
		}

		JSONObject jsonObject = this.getJson(systemName, definition, 0, 200000, parameter);
		if (jsonObject.containsKey("rows")) {
			if (StringUtils.equals(definition.getCacheFlag(), "Y")
					&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
				systemNameMap.put(systemName, systemName);
				jsonCache.put(cacheKey, jsonObject.toJSONString());
			}
			return jsonObject.getJSONArray("rows");
		}
		return new JSONArray();
	}

	/**
	 * 获取JSON数组数据
	 * 
	 * @param systemName
	 *            系统名
	 * @param datasetId
	 *            数据集定义编号
	 * @param parameter
	 *            参数
	 * @return
	 */
	public JSONArray getJsonArray(String systemName, String datasetId, Map<String, Object> parameter) {
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet definition = getDataSet(datasetId);
		String cacheKey = "cache_json_" + systemName + "_" + datasetId;
		if (StringUtils.equals(definition.getCacheFlag(), "Y")
				&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
			String text = jsonCache.getIfPresent(cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					return JSON.parseObject(text).getJSONArray("rows");
				} catch (Exception ex) {
				}
			}
		}

		JSONObject jsonObject = this.getJson(systemName, definition, 0, 200000, parameter);
		if (jsonObject.containsKey("rows")) {
			if (StringUtils.equals(definition.getCacheFlag(), "Y")
					&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
				systemNameMap.put(systemName, systemName);
				jsonCache.put(cacheKey, jsonObject.toJSONString());
			}
			return jsonObject.getJSONArray("rows");
		}
		return new JSONArray();
	}

	/**
	 * 获取JSON数组数据
	 * 
	 * @param systemName
	 *            系统名
	 * @param datasetId
	 *            数据集定义编号
	 * @param parameter
	 *            参数
	 * @return
	 */
	public JSONArray getJsonArray(String systemName, com.glaf.datamgr.domain.DataSet definition,
			Map<String, Object> parameter) {
		String cacheKey = "cache_json_" + systemName + "_" + definition.getId();
		if (StringUtils.equals(definition.getCacheFlag(), "Y")
				&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
			String text = jsonCache.getIfPresent(cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					return JSON.parseObject(text).getJSONArray("rows");
				} catch (Exception ex) {
				}
			}
		}

		JSONObject jsonObject = this.getJson(systemName, definition, 0, 200000, parameter);// 黄朝文修改，从0开始
		if (jsonObject.containsKey("rows")) {
			if (StringUtils.equals(definition.getCacheFlag(), "Y")
					&& StringUtils.equals(definition.getDynamicFlag(), "N")) {
				systemNameMap.put(systemName, systemName);
				jsonCache.put(cacheKey, jsonObject.toJSONString());
			}
			return jsonObject.getJSONArray("rows");
		}
		return new JSONArray();
	}

	/**
	 * 判断并获取数据库名称
	 * 
	 * @param systemName
	 * @param databaseId
	 * @return
	 */
	private String getSystemName(String systemName, Long databaseId) {
		if ((systemName == null || Environment.DEFAULT_SYSTEM_NAME.equalsIgnoreCase(systemName))
				&& (databaseId != null && databaseId > 0L)) {
			IDatabaseService databaseService = ContextFactory.getBean("databaseService");
			Database database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				systemName = database.getName();
			}
		}
		return systemName == null ? Environment.DEFAULT_SYSTEM_NAME : systemName;
	}

	protected Table getTable(JdbcDataContext dc, String tablename) {
		Table table = myTableMap.get(tablename);
		if (table != null) {
			try {
				table.getColumn(0);
			} catch (java.lang.Throwable e) {
				table = null;
				logger.debug(tablename + " connection is closed " + e.getMessage());
			}
		}
		if (table == null) {
			table = dc.getTableByQualifiedLabel(tablename);
			if (table != null) {
				table = new MutableTable(table.getName(), table.getType(), null, table.getColumns());// 此方法可行，但是每次增加表字段是需要重新

				// 加载
				myTableMap.put(tablename, table);
			}
		} else {
			logger.debug("get table from cache");
		}
		return table;
	}

	private boolean initJoin(Query query, com.glaf.datamgr.domain.DataSet definition, Map<String, Object> parameter,
			Map<String, JSONObject> fromMap) {
		String jsonStr = definition.getLastestJson();
		boolean rst = false;
		if (!isNullOrEmpty(jsonStr)) {
			String joinSegmentTree = "joinSegmentTree";
			JSONObject dataSetJson = JSON.parseObject(jsonStr), node = null;
			if (dataSetJson.containsKey("joinSegmentLinkType")) {
				rst = dataSetJson.getInteger("joinSegmentLinkType") == 1;
			}
			if (rst = dataSetJson.containsKey(joinSegmentTree)) {
				StringBuffer SB = new StringBuffer();
				JSONArray jsonarray = dataSetJson.getJSONArray(joinSegmentTree);
				if (rst = jsonarray.size() > 0) {
					node = jsonarray.getJSONObject(0);
					this.toJoin(SB, node, 0, parameter, fromMap);
					String expressItems = SB.toString();
					query.from(expressItems);
				}
			}
		}
		return rst;
	}

	private void toJoin(final StringBuffer SB, JSONObject node, int index, Map<String, Object> parameter,
			Map<String, JSONObject> fromMap) {

		switch (index) {
		case 0:
			String st = this.getFromItem(node.getString(sourceTable), parameter, fromMap).toSql();
			SB.append(" ").append(st);
			break;
		default:
			break;
		}

		String tt = this.getFromItem(node.getString(targetTable), parameter, fromMap).toSql();

		SB.append(" ").append(node.getString(connector)).append(" ").append(tt).append(" ON ");

		String linkType = MapUtils.getString(node, "linkType", "");
		if (StringUtils.equals(linkType, "1")) { // 复合连接
			BuilderUtils.buildCondition("", SB, node.getJSONObject("joinTree"), new BuilderUtils.ConditionBuild() {
				public Object exec(String expressionl, String operator, String expressionr) {
					return SB.append(expressionl).append(" ").append(operator).append(" ").append(expressionr);
				}
			}, parameter);
		} else {
			String st = node.getString(sourceTable);
			String sc = node.getString(sourceColumn);
			sc = getColumnLabelFormat(st, sc, fromMap);

			String tt_ = node.getString(targetTable);
			String tc = node.getString(targetColumn);
			tc = getColumnLabelFormat(tt_, tc, fromMap);

			SB.append(st).append(".").append(sc).append(node.getString(operator)).append(tt_).append(".").append(tc);
		}

		if (node.containsKey(items)) {
			JSONArray children = node.getJSONArray(items);
			int i = 0, len = children.size();
			for (; i < len; i++) {
				node = children.getJSONObject(i);
				this.toJoin(SB, node, index + 1, parameter, fromMap);
			}
		}
	}

	public boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	// private Map<String, JSONObject> populateParameters = null;

	/**
	 * 参数赋值定义
	 * 
	 * @param definition
	 * @param parameter
	 */
	private Map<String, Object> populateParameters(com.glaf.datamgr.domain.DataSet definition,
			Map<String, Object> parameter) {
		JSONObject dataSetJson = getLastestDataSetAudit(definition), jsonObject;
		if (dataSetJson.containsKey("params")) {
			JSONArray params = dataSetJson.getJSONArray("params");
			if (!CollectionUtils.isEmpty(params)) {
				String param;
				Object value;
				if (parameter == null)
					parameter = new HashMap<String, Object>();
				Map<String, JSONObject> populateParameters = new HashMap<String, JSONObject>();
				parameter.put("populateParameters", populateParameters);

				Map<String, Object> codeMap = new HashMap<String, Object>();
				for (int i = 0; i < params.size(); i++) {
					jsonObject = params.getJSONObject(i);
					param = jsonObject.getString("param");
					value = MapUtils.getObject(parameter, param, jsonObject.get("defVal"));
					populateParameters.put(param, jsonObject);
					if (value != null && value.toString().trim().length() > 0) {
						if (StringUtils.endsWithIgnoreCase(value.toString(), "Z") && //
								StringUtils.contains(value.toString(), "T")) {// 可能是日期类型
							try {
								value = new java.sql.Date(DateUtils.toDate(value.toString()).getTime());
							} catch (Exception e) {
								logger.debug(e);
							}
						}
						parameter.put(param, value);
					}
				}

				String expActVal = null, tmp;
				JSONObject paramJson = null;
				Object paramVal = null;
				for (int i = 0; i < params.size(); i++) {// 防止参数交换使用
					jsonObject = params.getJSONObject(i);
					param = jsonObject.getString("param");
					
					paramVal = parameter.get(param);
					if (jsonObject.containsKey("expItem") && paramVal != null && paramVal.toString().trim().length() > 0) {
						paramJson = jsonObject.getJSONObject("expItem");
						expActVal = paramJson.getString("expActVal");
						if (!isNullOrEmpty(expActVal)) {

							expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
									ExpressionConvertUtil.DATABASE_TYPE, parameter);

							tmp = expActVal;
							if (StringUtils.isNotBlank(tmp) && !tmp.equals("''") && !tmp.equals("\"\"")) {
								codeMap.put(param + "." + param, tmp);
								codeMap.put(param, tmp);
							}
						}
					}
				}
				parameter.putAll(codeMap);
			}
		}
		return parameter;
	}

	private String populateVal(String val, Map<String, Object> map) {
		if (!isNullOrEmpty(val) && map != null && map.size() > 0) {
			String col, str;
			for (String key : map.keySet()) {
				col = key + "." + key;
				if (map.containsKey(col)) {
					str = MapUtils.getString(map, col, "");
				} else
					str = MapUtils.getString(map, key, "");
				if (val.contains(col))
					val = val.replaceAll(col, str);
				if (val.contains(key))
					val = val.replaceAll(key, str);
			}
		}
		return val;
	}

	/**
	 * 
	 * @param seg
	 * @param parameter
	 * @param fromMap
	 * @return
	 */
	private String translateExpression(JSONObject seg, Map<String, Object> parameter, Map<String, JSONObject> fromMap) {
		String expression = seg.getString("expression");
		String exp = ExpressionConvertUtil.expressionConvert(expression, ExpressionConvertUtil.DATABASE_TYPE,
				parameter);
		// exp = this.populateVal(exp, parameter);
		boolean ok = false;
		if (expression != null) {
			if (expression.startsWith("'")) {
				ok = true;
			}
		}

		if (!isNullOrEmpty(exp)) {
			exp = exp.replaceAll("\"", "\'");

			if (exp.equals("''") && ok) {
				return null;
			}

			String operator = seg.getString("operator");

			String ping = GetPing(MapUtils.getString(parameter, DATABASETYPEKEY, "sqlserver"));

			if (operator != null && StringUtils.containsIgnoreCase(operator, "like")) {
				if (StringUtils.equalsIgnoreCase(operator, "rLike")) {
					// exp = "'%'+" + exp;
					exp = "'%'" + ping + exp;
					operator = "like";
				} else if (StringUtils.equalsIgnoreCase(operator, "lLike")) {
					// exp = exp + "+'%'";
					exp = exp + ping + "'%'";
					operator = "like";
				} else if (StringUtils.equalsIgnoreCase(operator, "like")) {
					// exp = "'%'+" + exp + "+'%'";
					exp = "'%'" + ping + exp + ping + "'%'";
				}
			} else if (seg.getString("parameType") != null
					&& StringUtils.equalsIgnoreCase("string", seg.getString("parameType"))) {
			}
			String ret = "";
			if (seg.containsKey("expressionl")) {
				ret = ExpressionConvertUtil.expressionConvert(seg.getString("expressionl"),
						ExpressionConvertUtil.DATABASE_TYPE, parameter);
			} else {
				String tableName = seg.getString("tableName"), columnName = seg.getString("columnName");
				if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(columnName))
					ret = tableName + "."
							+ getColumnLabelFormat(tableName, columnName, fromMap) /* seg.getString("columnName") */;
			}

			return ret + " " + operator + " " + exp;
		}
		return null;
	}

	protected static String GetPing(String type) {
		return dataBasePing.get(type);
	}

	public long getExecTime() {
		return execTime;
	}

	/**
	 * 验证sql 执行（时间）
	 * 
	 * @param definition
	 * @param parameter
	 * @return
	 */
	public static long validate(com.glaf.datamgr.domain.DataSet definition, Map<String, Object> parameter) {
		DataSetBuilder builder = new DataSetBuilder();
		builder.getJson(Environment.DEFAULT_SYSTEM_NAME, definition, 0, 500, parameter);// 500条数据测试速度
		long time = builder.getExecTime();
		// DataSetService dataSetService =
		// ContextFactory.getBean("dataSetService");
		com.glaf.datamgr.domain.DataSet dataSet = getDataSet(definition.getId());
		if (dataSet != null) {
			if (time > DataSetBuilder.VALIDATE_TIME_MILLIS) { // 执行时间超过
				dataSet.setVerify("N");
			} else {
				dataSet.setVerify("Y");
			}
			definition.setVerify(dataSet.getVerify());
			getDataSetService().saveOrUpdate(dataSet);

			if (!DataSetBuilder.isInit.get()) {
				Statement stmt = null;
				Connection connection = null;
				try {
					connection = DBConnectionFactory.getConnection(
							builder.getSystemName(Environment.DEFAULT_SYSTEM_NAME, definition.getDatabaseId()));
					String sql = "update SYS_DATASET set VERIFY_ = 'Y' WHERE VERIFY_ IS NULL";
					stmt = connection.createStatement();
					stmt.execute(sql);
					DataSetBuilder.isInit.set(true);
				} catch (Exception ex) {
					logger.debug(ex.getMessage());
				} finally {
					JdbcUtils.close(stmt);
					JdbcUtils.close(connection);
				}
			}

		}
		return time;
	}

	/**
	 * 数据集映射转换
	 * 
	 * @param definition
	 * @param parameter
	 * @return
	 */
	protected com.glaf.datamgr.domain.DataSet convertDataSet(com.glaf.datamgr.domain.DataSet definition, //
			Map<String, Object> parameter) {
		DataSetMapping dsm = null;
		try {
			if (definition.isHasMapping() == null || definition.isHasMapping()) {
				dsm = this.getDataSetMappingById(definition.getId());
				definition.setHasMapping(dsm != null);
				cacheDataSet(definition, false);
			}
		} catch (Exception ex) {
			dsm = null;
		}
		if (dsm != null) {
			logger.debug(String.format("获取到映射数据集[%s],ID:%s", dsm.getDsmName(), dsm.getDsmId()));
			List<DataSetMappingItem> items = //
					this.getDataSetMappingItems(definition.getId(), dsm);
			if (CollectionUtils.isNotEmpty(items)) {
				Map<String, Map<String, String>> map = new HashMap<>();
				for (DataSetMappingItem item : items) {
					if (StringUtils.isEmpty(item.getMappingType())//
							|| StringUtils.isEmpty(item.getCode()))
						continue;
					if (!map.containsKey(item.getMappingType()))
						map.put(item.getMappingType(), new HashMap<>());
					map.get(item.getMappingType()).put(item.getMappingCode(), item.getCode());
				}

				com.glaf.datamgr.domain.DataSet dataSetMapping = //
						/* getDataSetService(). */getDataSet(dsm.getDsmId());

				/**
				 * 字段转换
				 */
				Map<String, String> cMap = map.get("column");
				if (cMap != null) {
					if (CollectionUtils.isNotEmpty(dataSetMapping.getSelectSegments())) {
						for (SelectSegment seg : dataSetMapping.getSelectSegments()) {
							if (StringUtils.isNotBlank(cMap.get(seg.getColumnLabel())))
								seg.setColumnLabel(cMap.get(seg.getColumnLabel()));
						}
					}
				}

				/**
				 * 参数转换(直接追加参数)
				 */
				Map<String, String> pMap = map.get("param");
				if (pMap != null && parameter != null) {
					for (String key : pMap.keySet()) {
						// parameter.put(pMap.get(key), parameter.get(key));
						parameter.put(key, parameter.get(pMap.get(key)));
					}
				}
				return dataSetMapping;
			}
		}
		return definition;
	}

	/**
	 * 获取映射数据集
	 * 
	 * @param id
	 * @return
	 */
	protected DataSetMapping getDataSetMappingById(String id) {
		DataSetMapping dsm = null;

		DataSetMappingService dataSetMappingService = //
				ContextFactory.getBean("com.glaf.datamgr.service.dataSetMappingService");
		DataSetMappingQuery query = new DataSetMappingQuery();
		query.setParentId_(id);
		query.setStatus(1);

		List<DataSetMapping> dataSetMappings = dataSetMappingService.list(query);
		if (CollectionUtils.isNotEmpty(dataSetMappings)) {
			dsm = dataSetMappings.get(0);
		}
		return dsm;
	}

	/**
	 * 获取映射明细
	 * 
	 * @param id
	 * @param dsm
	 * @return
	 */
	protected List<DataSetMappingItem> getDataSetMappingItems(String id, DataSetMapping dsm) {
		List<DataSetMappingItem> items = null;
		if (dsm == null)
			dsm = this.getDataSetMappingById(id);
		if (dsm != null) {
			DataSetMappingItemService dataSetMappingItemService = //
					ContextFactory.getBean("com.glaf.datamgr.service.dataSetMappingItemService");
			DataSetMappingItemQuery query = new DataSetMappingItemQuery();
			query.setParentId_(dsm.getId());
			items = dataSetMappingItemService.list(query);
		}
		return items;
	}

	/**
	 * 列别名组合key
	 * 
	 * @param columnLabel
	 * @return
	 */
	private static String getColumnAlias(String columnLabel) {
		return "column_" + columnLabel.toLowerCase();
	}

	/**
	 * 转换列映射
	 * 
	 * @return
	 */
	public static String getColumn(String columnLabel) {

		Map<String, String> map = getColumnMap();

		if (map != null) {
			String column = map.get(getColumnAlias(columnLabel));
			if (StringUtils.isNotBlank(column)) {
				return column;
			}
		}
		return columnLabel;
	}

	/**
	 * 转换列映射
	 * 
	 * @return
	 */
	public static Map<String, String> getColumnMap() {
		return columnMap.get();
	}

	/**
	 * 转换列映射(不同数据库转换)
	 * 
	 * @return
	 */
	public Map<String, String> getTranlateMapping() {
		return TranlateFactory.get();
	}

}
