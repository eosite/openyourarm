package com.glaf.dep.base.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.expression.core.util.ExpressionConvertUtil;

/**
 * 
 * @author klaus.wang
 * 
 */
public class WdataSetBiluder {

	protected final static Logger logger = LoggerFactory.getLogger(WdataSetBiluder.class);

	private JSONObject ruleJson = null;

	private Map<String, Object> expressionMap = null;

	private Map<String, JSONObject> whereParamsMap = null;

	private static String tableKey = "table";
	private JSONObject table = null;

	private static String columnsKey = "columns";
	private JSONArray columns = null;

	private static String updateParametersKey = "updateParameters";
	private JSONArray updateParameters = null;

	private static String insertParametersKey = "insertParameters";
	private JSONArray insertParameters = null;

	private static String deleteParametersKey = "deleteParameters";

	private JSONArray deleteParameters = null;

	private static ThreadLocal<ConcurrentMap<String, Object>> parameterViews//
			= new ThreadLocal<>();

	/**
	 * 测试标识
	 */
	public static final String MTTEST = "MTTEST__";

	private JSONArray parameters = null;

	private void init() {
		this.table = this.getRuleJson().getJSONObject(tableKey);
		this.columns = this.getRuleJson().getJSONArray(columnsKey);
		this.updateParameters = this.getRuleJson().getJSONArray(updateParametersKey);
		this.insertParameters = this.getRuleJson().getJSONArray(insertParametersKey);
		this.deleteParameters = this.getRuleJson().getJSONArray(deleteParametersKey);

		expressionMap = new HashMap<String, Object>();
		whereParamsMap = new HashMap<String, JSONObject>();
		expressionMap.put("tableName", table.getString("dataTableName"));
	}

	public WdataSetBiluder() {

	}

	public WdataSetBiluder(JSONObject ruleJson) {
		this.setRuleJson(ruleJson);
	}

	/**
	 * 默认插入所有项
	 * 
	 * @return
	 */
	public String getInsertSql() {
		return getInsertDynamicSql_(false);
	}

	/**
	 * 动态插入选定项
	 * 
	 * @return
	 */
	public String getInsertDynamicSql() {
		return getInsertDynamicSql_(true);
	}

	private String getInsertDynamicSql_(final boolean dynamic) {
		final StringBuffer SQL = new StringBuffer("INSERT INTO ").append(this.table.get("dataTableName")).append(" ("),
				VALUES = new StringBuffer(" VALUES (");
		int length = SQL.length();
		this.exec(new JdbcIterator() {
			public Boolean iterator(int index, String columnName, boolean isIn, boolean isU) {
				if (dynamic && !isIn) {
					return false;
				}
				switch (index) {
				case 0:
					break;
				default:
					SQL.append(", ");
					VALUES.append(", ");
					break;
				}
				// VALUES.append("?");//TODO
				VALUES.append("${").append(columnName).append("}");
				SQL.append(columnName);
				return true;
			}
		});
		if (SQL.length() == length) {
			throw new RuntimeException("无可更新的列");
		}
		this.ruleJson.put(insertParametersKey, this.insertParameters = this.getParameters());
		SQL.append(")").append(VALUES.append(")"));
		String sql = SQL.toString();

		/**
		 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
		 */
		if (StringUtils.containsIgnoreCase(sql, " userinfo ") || StringUtils.containsIgnoreCase(sql, " userrole ")
				|| StringUtils.containsIgnoreCase(sql, " net_role ")
				|| StringUtils.containsIgnoreCase(sql, " login_module ")
				|| StringUtils.containsIgnoreCase(sql, " sys_key ")
				|| StringUtils.containsIgnoreCase(sql, " sys_property ")
				|| StringUtils.containsIgnoreCase(sql, " sys_server ")
				|| StringUtils.containsIgnoreCase(sql, " sys_dictory ")
				|| StringUtils.containsIgnoreCase(sql, " sys_scheduler ")
				|| StringUtils.containsIgnoreCase(sql, " sys_application ")
				|| StringUtils.containsIgnoreCase(sql, " sys_access ")
				|| StringUtils.containsIgnoreCase(sql, " sys_database ")) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		return sql;
	}

	/**
	 * 默认更新所有项
	 * 
	 * @return
	 */
	public String getUpdateSql() {
		return this.getUpdateDynamicSql_(false, null);
	}

	/**
	 * 动态更新选定项
	 * 
	 * @return
	 */
	public String getUpdateDynamicSql() {
		return this.getUpdateDynamicSql_(true, null);
	}

	private String getUpdateDynamicSql_(final boolean dynamic, final Set<String> fields) {
		final boolean isField = fields != null;
		final StringBuffer SQL = new StringBuffer("UPDATE ").append(this.table.get("dataTableName")).append(" SET ");
		final int length = SQL.length();
		this.exec(new JdbcIterator() {
			public Boolean iterator(int index, String columnName, boolean isIn, boolean isU) {

				if (isField && !fields.contains(columnName)) {
					return false;
				}

				if (dynamic && !isU) {
					return false;
				}

				if (length != SQL.length()) {
					SQL.append(", ");
				}
				if (!isField && dynamic) {
					SQL.append(columnName).append(" = ${" + columnName + "}");
				} else {
					SQL.append(columnName).append(" = ?");
				}

				return true;
			}
		});
		if (length == SQL.length()) {
			throw new RuntimeException("无可更新的列");
		}
		this.buildWhere(SQL, updateClausKey);

		this.ruleJson.put(updateParametersKey, this.updateParameters = this.getParameters());
		String sql = SQL.toString();

		/**
		 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
		 */
		if (StringUtils.containsIgnoreCase(sql, " userinfo ") || StringUtils.containsIgnoreCase(sql, " userrole ")
				|| StringUtils.containsIgnoreCase(sql, " net_role ")
				|| StringUtils.containsIgnoreCase(sql, " login_module ")
				|| StringUtils.containsIgnoreCase(sql, " sys_key ")
				|| StringUtils.containsIgnoreCase(sql, " sys_property ")
				|| StringUtils.containsIgnoreCase(sql, " sys_server ")
				|| StringUtils.containsIgnoreCase(sql, " sys_dictory ")
				|| StringUtils.containsIgnoreCase(sql, " sys_scheduler ")
				|| StringUtils.containsIgnoreCase(sql, " sys_application ")
				|| StringUtils.containsIgnoreCase(sql, " sys_access ")
				|| StringUtils.containsIgnoreCase(sql, " sys_database ")) {
			throw new RuntimeException(" SQL statement illegal ");
		}

		return sql;
	}

	protected String getUpdateDynamicSql_(final Set<String> fields) {
		// final StringBuffer SQL = new StringBuffer("UPDATE
		// ").append(this.table.get("dataTableName")).append(" SET ");
		// final int length = SQL.length();
		// this.exec(new JdbcIterator() {
		// public Boolean iterator(int index, String columnName, boolean isIn, boolean
		// isU) {
		// if (!fields.contains(columnName)) {
		// return false;
		// }
		// if (length != SQL.length()) {
		// SQL.append(", ");
		// }
		// SQL.append(columnName).append(" = ?");
		// return true;
		// }
		// });
		// if (length == SQL.length()) {
		// throw new RuntimeException("无可更新的列");
		// }
		// this.buildWhere(SQL, updateClausKey);
		//
		// this.ruleJson.put(updateParametersKey, this.updateParameters =
		// this.getParameters());
		return this.getUpdateDynamicSql_(false, fields);
	}

	public String getDeleteSql() {
		StringBuffer SQL = new StringBuffer("DELETE FROM ").append(this.table.get("dataTableName"));
		this.setParameters(new JSONArray());
		this.buildWhere(SQL, deleteClausKey);

		this.ruleJson.put(deleteParametersKey, this.deleteParameters = this.getParameters());

		String sql = SQL.toString();
		/**
		 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
		 */
		if (StringUtils.containsIgnoreCase(sql, " userinfo ") || StringUtils.containsIgnoreCase(sql, " userrole ")
				|| StringUtils.containsIgnoreCase(sql, " net_role ")
				|| StringUtils.containsIgnoreCase(sql, " login_module ")
				|| StringUtils.containsIgnoreCase(sql, " sys_key ")
				|| StringUtils.containsIgnoreCase(sql, " sys_property ")
				|| StringUtils.containsIgnoreCase(sql, " sys_server ")
				|| StringUtils.containsIgnoreCase(sql, " sys_dictory ")
				|| StringUtils.containsIgnoreCase(sql, " sys_scheduler ")
				|| StringUtils.containsIgnoreCase(sql, " sys_application ")
				|| StringUtils.containsIgnoreCase(sql, " sys_access ")
				|| StringUtils.containsIgnoreCase(sql, " sys_database ")) {
			throw new RuntimeException(" SQL statement illegal ");
		}
		return sql;
	}

	private void buildWhere(StringBuffer SQL, String key) {

		boolean rst = false;

		if (this.ruleJson.containsKey(key)) {
			rst = this.buildWhere(SQL, this.ruleJson.getJSONObject(key), false);
		}

		if (!rst) {
			JSONArray whereClausArray = this.ruleJson.getJSONArray("whereClaus");
			if (whereClausArray != null) {
				String dataColumnName;
				JSONObject json;
				int i = 0, size = whereClausArray.size();
				for (; i < size; i++) {
					json = whereClausArray.getJSONObject(i);
					switch (i) {
					case 0:
						SQL.append(" WHERE ");
						break;
					default:
						SQL.append(" AND ");
						break;
					}
					// SQL.append(json.getString("dataColumnName")).append(" =
					// ?");
					SQL.append(dataColumnName = json.getString("dataColumnName"))//
							.append(" = ${").append(dataColumnName).append("}");
					this.addParameter(json);
				}
			}

		}

	}

	private static String collectionKey = "collection";
	private static String childrenKey = "children";
	private static String updateClausKey = "updateClaus";
	private static String deleteClausKey = "deleteClaus";
	private static String outerTreeKey = "outerTree";

	public void putParams(JSONArray collection, boolean run) {
		if (CollectionUtils.isNotEmpty(collection)) {
			int i, size = collection.size();
			JSONObject json;
			String param;
			for (i = 0; i < size; i++) {
				json = collection.getJSONObject(i);
				this.expressionMap.put(param = json.getString("param"), run ? "${" + param + "}" : "?");
				this.whereParamsMap.put(param, json);
			}
		}
	}

	/**
	 * updateCluas/deleteClaus
	 * 
	 * @param SQL
	 * @param whereClaus
	 */
	protected boolean buildWhere(StringBuffer SQL, JSONObject whereClaus, boolean run) {
		boolean rst = false;
		if (whereClaus == null)
			return rst;
		if (whereClaus.containsKey(collectionKey)) {
			JSONArray collection = whereClaus.getJSONArray(collectionKey);
			if (CollectionUtils.isNotEmpty(collection)) {

				this.putParams(this.columns, run);

				/**
				 * 参数都变为${xxx}形式
				 */
				this.putParams(this.getRuleJson().getJSONArray("whereParams"), true);

				SQL.append(" WHERE ");

				int i = 0, size = collection.size();

				JSONObject map = new JSONObject(), json;
				for (; i < size; i++) {
					json = collection.getJSONObject(i);
					map.put(json.getString("ordinal"), json);
				}
				JSONArray outerTree = whereClaus.getJSONArray(outerTreeKey);
				if (CollectionUtils.isNotEmpty(outerTree)) {
					this.iterator(SQL, outerTree, map, "");
				}
				rst = true;
			}
		}
		return rst;
	}

	/**
	 * 递归
	 * 
	 * @param SQL
	 * @param jar
	 * @param json
	 */
	protected void iterator(StringBuffer SQL, JSONArray jar, JSONObject json, String connector) {
		int i, size;
		if (CollectionUtils.isNotEmpty(jar)) {
			size = jar.size();
			JSONObject j, j2, j3;
			String expression, param, operator;
			for (i = 0; i < size; i++) {
				j = jar.getJSONObject(i);
				if (i > 0) {
					SQL.append(" ").append(connector).append(" ");
				}
				if (json.containsKey(j.getString("id"))) {
					j2 = json.getJSONObject(j.getString("id"));
					if (j2 != null) {
						expression = j2.getString("expressionl");

						expression = ExpressionConvertUtil.expressionConvert(expression,
								ExpressionConvertUtil.DATABASE_TYPE, this.expressionMap);
						operator = j2.getString("operator");
						logger.debug(operator);
						if (StringUtils.equalsIgnoreCase(operator, "rLike") //
								|| StringUtils.equalsIgnoreCase(operator, "lLike")) {
							operator = "like";
						}
						SQL.append(expression).append(" ").append(operator).append(" ");

						expression = j2.getString("expression");

						j2.put("param", param = getParam(expression));

						if (StringUtils.isNotBlank(param) && //
								this.whereParamsMap.containsKey(param)) {
							j3 = this.whereParamsMap.get(param);
							j2.put("prepared", j3.getBooleanValue("prepared"));
						}

						this.addParameter(j2);

						expression = ExpressionConvertUtil.expressionConvert(expression,
								ExpressionConvertUtil.DATABASE_TYPE, this.expressionMap);

						SQL.append("${").append(param).append("} ");
						// SQL.append(expression).append(" ");

					}
				}
				if (j.containsKey(childrenKey)) {
					Boolean root = StringUtils.isBlank(connector);
					this.iterator(root ? SQL : SQL.append("("), j.getJSONArray(childrenKey), json,
							j.getString("connector"));
					if (!root)
						SQL.append(")");
				}

			}
		}
	}

	private void exec(JdbcIterator it) {
		JSONObject columnJsonObject;
		int i = 0, len = this.columns.size(), index = 0;
		this.setParameters(new JSONArray());
		String dataColumnName;
		for (; i < len; i++) {
			columnJsonObject = this.columns.getJSONObject(i);
			dataColumnName = columnJsonObject.getString("dataColumnName");
			if (StringUtils.isNotBlank(dataColumnName)) {
				if (it.iterator(index, dataColumnName, columnJsonObject.getBooleanValue("input"),
						columnJsonObject.getBooleanValue("update"))) {
					this.addParameter(columnJsonObject);
					index++;
				}
			}
		}
	}

	private void addParameter(final JSONObject columnJsonObject) {
		if (this.getParameters() == null) {
			return;
		}
		String[] keysStrings = { "param", "dname", "defVal", "dtype", "dataItem", "variable", "prepared", "operator",
				"conExpItem" };
		JSONObject json = new JSONObject();
		for (String key : keysStrings) {
			json.put(key, columnJsonObject.get(key));
		}
		this.getParameters().add(json);
	}

	/**
	 * 获取值、默认值
	 * 
	 * @param json
	 * @param param
	 * @return
	 */
	private Object getValue(JSONObject json, Map<String, Object> param) {
		Object defVal = MapUtils.getObject(param, json.getString("param"), //
				MapUtils.getObject(json, "defVal"));
		if (defVal != null && StringUtils.isNotBlank(defVal.toString())) {
			logger.debug(defVal.toString());
			defVal = evaluate(defVal.toString(), this.expressionMap);
		}
		return defVal;
	}

	/**
	 * 初始化参数
	 * 
	 * 必须按顺序来搞
	 * 
	 */
	private void toParams(Map<String, Object> param) {

		parameterViews.set(new ConcurrentHashMap<String, Object>());

		JSONArray whereParams = this.getRuleJson().getJSONArray("whereParams");// 参数列表
		if (CollectionUtils.isNotEmpty(whereParams)) {
			int i = 0, len = whereParams.size();
			Object val;
			String key, exp, expItemKey = "expItem";
			for (; i < len; i++) {
				JSONObject json = whereParams.getJSONObject(i), expItem;

				/**
				 * 获取值
				 */
				val = this.getValue(json, param);

				this.put(param, key = json.getString("param"), val);// 当前值保存

				if (json.containsKey(expItemKey) && val != null) {// 必须有值的时候才进表达式
					expItem = json.getJSONObject(expItemKey);
					exp = MapUtils.getString(expItem, "expActVal", "");// 表达式组合
					if (StringUtils.isNotBlank(exp)) {
						val = evaluate(exp.toString(), this.expressionMap);
						this.put(param, key, val);// 组合完以后覆盖掉
					}
				}
				if (val!=null&&!json.getBoolean("prepared")) {
					parameterViews.get().put(key, val);
				}

			}
		}
	}

	private void put(Map<String, Object> collection, String key, Object value) {
		this.expressionMap.put(key, value);
		collection.put(key, value);
	}

	private Map<String, Object> populateParameter(JSONArray parameters, Map<String, Object> param,
			List<Object> collections, boolean update) {
		Object value;
		String paramKey, tmpVal, dname = null, dtype, operator;
		JSONObject columnJsonObject;
		int i = 0, size = parameters.size();

		Map<String, Object> variables = new HashMap<String, Object>(), //
				views = new HashMap<String, Object>();// 明文参数
		if (MapUtils.isNotEmpty(param)) {
			for (String key : param.keySet()) {
				this.put(variables, key, param.get(key));
			}
		}

		boolean isNew = this.getRuleJson().getBooleanValue("isNew")//
				, prepared, variable;

		for (; i < size; i++) {
			columnJsonObject = parameters.getJSONObject(i);

			paramKey = columnJsonObject.getString("param");
			dname = columnJsonObject.getString("dname");
			dtype = columnJsonObject.getString("dtype");
			prepared = !columnJsonObject.containsKey("prepared") //
					|| columnJsonObject.getBooleanValue("prepared") || !isNew;
			variable = columnJsonObject.getBooleanValue("variable");

			operator = columnJsonObject.getString("operator");
			if (!param.containsKey(paramKey))
				paramKey = dname; // 参数或者物理字段都可以

			value = param.get(paramKey);
		//	if (!update || !prepared) {
				if (value == null) {
					if (columnJsonObject.containsKey("dataItem")) {
						String dataItem = columnJsonObject.getString("dataItem");
						JSONObject json = JSON.parseObject(dataItem);
						if (json != null) {
							value = json.getString("expActVal");
						}
					}
					if (value == null)
						value = columnJsonObject.get("defVal"); // 使用默认值
				}
		//	}

			if (value != null) {
				String exp = value.toString();
				if (StringUtils.contains(exp, "$USER_ID(~CONST)")) {
					this.expressionMap.put("idColumn", dname);
				}
				value = evaluate(exp, this.expressionMap);
				if (value != null) {
					tmpVal = value.toString().trim();
					if (prepared) {
						value = TrimSyn(tmpVal, "'");
						tmpVal = value.toString();
					}

					if (StringUtils.isNotEmpty(operator)) {
						if (!prepared) {
							tmpVal = TrimSyn(tmpVal, "'");
						}
						if (StringUtils.equalsIgnoreCase(operator, "rLike") //
								&& !StringUtils.startsWith(tmpVal, "%")) {
							value = "%" + tmpVal;
						} else if (StringUtils.equalsIgnoreCase(operator, "lLike") //
								&& !StringUtils.endsWith(tmpVal, "%")) {
							value = tmpVal + "%";
						} else if (StringUtils.equalsIgnoreCase(operator, "like") //
								&& !StringUtils.startsWith(tmpVal, "%")//
								&& !StringUtils.endsWith(tmpVal, "%")) {
							value = "%" + tmpVal + "%";
						}
						if (!prepared && StringUtils.containsIgnoreCase(operator, "like")) {
							value = "'" + value + "'";
						}
					}

					if (value != null && value.toString().trim().length() > 0
							&& StringUtils.startsWith(dtype, "date")) {
						try {
							if(value.toString().indexOf("GMT") != -1){
								String[] parsePatterns = {  
							            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",  
							            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",  
							            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"}; 
								value = parseTime(value.toString());
								value = DateUtils.parseDate(value.toString(), parsePatterns);
							}
							else{
								value = DateUtils.toDate(value.toString());
							}
							
						} catch (Exception ex) {
							logger.debug(ex.getMessage());
							value = new java.util.Date();
						}
					}
				}

				if (value != null && StringUtils.isEmpty(//
						value.toString()) && !StringUtils.contains(dtype, "char"))
					value = null;

				if (StringUtils.isNotBlank(paramKey)) {

					param.put(paramKey, value);
				}
				if (StringUtils.isNotBlank(dname)) {

					param.put(dname, value);
				}

			}

			if (variable) {
				variables.put(dname, value);
				variables.put(expressionMap.get("tableName") + "." + dname, value);
			}
			if (!prepared) {
				if (StringUtils.isNotBlank(paramKey)) {

					views.put(paramKey, value);
				}
				if (StringUtils.isNotBlank(dname)) {

					views.put(dname, value);
				}
			}
			collections.add(value);
		}

		// expressionConvert(collections, variables);

		logger.debug(collections.toString());

		/**
		 * 参数明文(预防少用,sql 注入)
		 */
		if (this.getRuleJson().getBooleanValue("isNewViews") && //
				MapUtils.isNotEmpty(parameterViews.get())) {
			views.putAll(parameterViews.get());
		}

		return views;
	}
	public static String parseTime(String datdString) {
	    datdString = datdString.replace("GMT", "").replaceAll("\\(.*\\)", "");
	    //将字符串转化为date类型，格式2016-10-12
	    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
	    Date dateTrans = null;
	    try {
	        dateTrans = format.parse(datdString);
	        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTrans).replace("-","/");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return datdString;

	}
	private static String TrimSyn(String tmpVal, String syn) {
		if (StringUtils.isBlank(tmpVal) || StringUtils.isBlank(syn)) {
			return tmpVal;
		}
		if (tmpVal.startsWith(syn)) {
			tmpVal = tmpVal.substring(syn.length());
		}
		if (tmpVal.endsWith(syn)) {
			tmpVal = tmpVal.substring(0, //
					tmpVal.length() - (syn.length()));
		}
		return tmpVal;
	}

	/**
	 * 表达式转换value
	 * 
	 * @param collections
	 * @param variables
	 * 
	 *            private static void expressionConvert(List<Object> collections,
	 *            Map<String, Object> variables) { if
	 *            (CollectionUtils.isNotEmpty(collections) && variables.size() > 0)
	 *            { Object value, result; List<Variable> list =
	 *            getVariableList(variables); for (int i = 0; i <
	 *            collections.size(); i++) { value = collections.get(i); if (value
	 *            != null) { result = evaluate(value.toString(), list); if (result
	 *            != null) { collections.set(i, result); } } } } }
	 */

	/**
	 * 转换表达式
	 * 
	 * @param value
	 * @param variables
	 * @return
	 */
	private static Object evaluate(String value, Map<String, Object> variables) {
		Object result = ExpressionConvertUtil.expressionConvert//
		(value, ExpressionConvertUtil.DATABASE_TYPE, variables), val = null;

		// List<Variable> list = getVariableList(variables);

		if (result != null) {
			// val = evaluate(result.toString(), list);//TODO
		}
		return val != null ? val : result;
	}

	/*
	 * private static List<Variable> getVariableList(Map<String, Object> variables)
	 * { List<Variable> list = new ArrayList<Variable>(); for (String key :
	 * variables.keySet()) { list.add(Variable.createVariable(key,
	 * variables.get(key))); } return list; }
	 * 
	 * private static Object evaluate(String value, List<Variable> list) { Object
	 * result = null; try { result = ExpressionEvaluator.evaluate(value, list);
	 * logger.debug(String.format("expression : %s, value : %s", value, result)); }
	 * catch (Exception ex) { logger.info("表达式解析异常，不处理!"); } return result; }
	 */

	static Pattern pattern = Pattern.compile("~F\\{(\\w*\\.)(\\w*\\.)(\\w*)\\}");

	private String getParam(String expActVal) {
		if (StringUtils.isNotBlank(expActVal)) {
			Matcher matcher = pattern.matcher(expActVal);
			if (matcher.find()) {
				return matcher.group(3);
			}
		}
		return null;
	}

	private boolean isUpdate(Map<String, Object> param, JSONArray whereClaus, Set<String> fields) {
		boolean rst = true;
		String dname;
		try {

			this.toParams(param);

			JSONObject json = this.getRuleJson().getJSONObject(updateClausKey);
			if (json != null) {
				String paramDataKey = "paramData", expActVal, paramKey;
				JSONArray collection = json.getJSONArray(collectionKey);
				if (CollectionUtils.isNotEmpty(collection)) {
					int i = 0, size = collection.size();
					for (; i < size; i++) {// 暂定，只要更新参数有一个符合就算
						json = collection.getJSONObject(i);
						if (json.containsKey(paramDataKey)) {
							json = json.getJSONObject(paramDataKey);
							expActVal = json.getString("expActVal");
							if (StringUtils.isNotBlank(expActVal)) {
								paramKey = getParam(expActVal);
								if (param.get(paramKey) != null) {
									return true;
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new RuntimeException(e);
		}

		if (whereClaus != null) {
			JSONObject whereJsonObject;
			int i = 0, size = whereClaus.size();
			for (; i < size; i++) {
				whereJsonObject = whereClaus.getJSONObject(i);
				dname = whereJsonObject.getString("dname");
				if (param.get(whereJsonObject.getString("param")) == null && param.get(dname) == null) {
					rst = false;
				}
				if (fields != null && fields.contains(dname))
					fields.remove(dname);
			}
		} else {
			dname = "id";
			if (fields != null && fields.contains(dname))
				fields.remove(dname);
			return param.get(dname) != null;
		}
		return rst;

	}

	/**
	 * 获取转义后的SQL以及参数
	 * 
	 * @param sql
	 * @param parameters
	 * @param param
	 * @param isUpdate
	 * @return
	 */
	protected SqlFormat getSqlFormat(String sql, JSONArray parameters, //
			Map<String, Object> param, boolean isUpdate) {
		SqlFormat sf = null;
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> views = this.populateParameter(//
				parameters, param, list, isUpdate);

		/**
		 * 动态根据参数构建SQL
		 */
		if (MapUtils.getBooleanValue(param, "isDynamic", false)) {
			// if (isDynamic && columnJsonObject.containsKey(conExpItemKey)) {
			// conExpItem = columnJsonObject.getJSONObject(conExpItemKey);
			// if(conExpItem != null){
			// String expActVal = conExpItem.getString("expActVal");
			//
			// Object result = evaluate(expActVal, null);
			//
			// System.out.println(result);
			// }
			// }
			String conExpItemKey = "conExpItem";
			int i = 0, size = parameters.size();
			JSONObject columnJsonObject, conExpItem;
			for (; i < size; i++) {
				columnJsonObject = parameters.getJSONObject(i);
				if (columnJsonObject.containsKey(conExpItemKey)) {
					conExpItem = columnJsonObject.getJSONObject(conExpItemKey);
					if (conExpItem != null) {
						String expActVal = conExpItem.getString("expActVal");
						Object result = evaluate(expActVal, null);

					}
				}

			}
		}

		if (this.getRuleJson().getBooleanValue("isNew")) {
			sf = SqlFormat.formatView(sql, views, param);
		} else {
			sf = new SqlFormat(sql, list);
		}
		return sf;
	}

	/**
	 * 执行
	 * 
	 * @param connection
	 * @param params
	 * @throws Exception
	 */
	public void execute(Connection connection, List<Map<String, Object>> params) throws Exception {
		int size;
		if (connection != null && params != null && (size = params.size()) > 0) {
			long start = System.currentTimeMillis();
			Map<String, Object> param;
			Map<String, Object> tmpMap;
			PreparedStatement pstmt = null;
			// PreparedStatement ipsmt = null;
			// PreparedStatement upsmt = null;
			int i = 0;
			Object value;
			String sql = null;
			boolean test = false;
			JSONArray whereClaus = this.ruleJson.getJSONArray("whereClaus");
			SqlFormat sf;
			try {
				for (i = 0; i < size; i++) {
					tmpMap = params.get(i);
					if (tmpMap == null)
						continue;

					param = new HashMap<String, Object>();
					for (String key : tmpMap.keySet()) {
						value = tmpMap.get(key);
						if (value != null && value.toString().trim().length() == 0)
							value = null;
						param.put(key, value);
						if (!param.containsKey(key.toLowerCase()))
							param.put(key.toLowerCase(), value);
					}

					if (this.isUpdate(param, whereClaus, null)) { // 更新

						sf = this.getSqlFormat(this.getRuleJson().getString("updateSql")//
								, this.updateParameters, param, true);

						// upsmt = connection.prepareStatement(sql =
						// sf.getSql());
						// logger.debug(sql);
						// JdbcUtils.fillStatement(upsmt, sf.getParameters());
						// upsmt.execute();
						// JdbcUtils.close(upsmt);
					} else { // 插入

						sf = this.getSqlFormat(this.getRuleJson().getString("insertSql")//
								, this.insertParameters, param, false);

						// ipsmt = connection.prepareStatement(sql =
						// sf.getSql());
						// logger.debug(sql);
						// JdbcUtils.fillStatement(ipsmt, sf.getParameters());
						// ipsmt.execute();
						// JdbcUtils.close(ipsmt);
					}

					sql = sf.getSql();

					tmpMap.putAll(param);
					if (test = tmpMap.containsKey(MTTEST)) {// 调试
						tmpMap.put(MTTEST, new Object[] { sql, sf.getParameters() });
						continue;
					}

					/**
					 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
					 */
					if (StringUtils.containsIgnoreCase(sql, " userinfo ")
							|| StringUtils.containsIgnoreCase(sql, " userrole ")
							|| StringUtils.containsIgnoreCase(sql, " net_role ")
							|| StringUtils.containsIgnoreCase(sql, " login_module ")
							|| StringUtils.containsIgnoreCase(sql, " sys_key ")
							|| StringUtils.containsIgnoreCase(sql, " sys_property ")
							|| StringUtils.containsIgnoreCase(sql, " sys_server ")
							|| StringUtils.containsIgnoreCase(sql, " sys_dictory ")
							|| StringUtils.containsIgnoreCase(sql, " sys_scheduler ")
							|| StringUtils.containsIgnoreCase(sql, " sys_application ")
							|| StringUtils.containsIgnoreCase(sql, " sys_access ")
							|| StringUtils.containsIgnoreCase(sql, " sys_database ")) {
						throw new RuntimeException(" SQL statement illegal ");
					}

					pstmt = connection.prepareStatement(sql);
					logger.debug(sql);
					JdbcUtils.fillStatement(pstmt, sf.getParameters());
					pstmt.execute();
					JdbcUtils.close(pstmt);

				}
				if (connection != null && !connection.getAutoCommit()) {
					connection.commit();
				}
				if (!test)
					logger.debug("更新集执行时间(毫秒):" + (System.currentTimeMillis() - start) + "--共" + size + " 条记录");
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("更新集执行失败 ", ex);
				throw ex;
			} finally {
				// JdbcUtils.close(upsmt);
				// JdbcUtils.close(ipsmt);
				JdbcUtils.close(pstmt);
			}
		}
	}

	@Deprecated
	public void execBatch(Connection connection, List<Map<String, Object>> params) throws Exception {
		int size;
		if (connection != null && params != null && (size = params.size()) > 0) {
			long start = System.currentTimeMillis();
			List<Object> list;
			Map<String, Object> param;
			PreparedStatement ipsmt = null;
			PreparedStatement upsmt = null;
			int update = 0, i, insert = 0;
			JSONArray whereClaus = this.ruleJson.getJSONArray("whereClaus");
			try {
				for (i = 0; i < size; i++) {
					param = params.get(i);
					if (param == null)
						continue;

					list = new ArrayList<Object>();

					if (this.isUpdate(param, whereClaus, null)) { // 更新
						if (upsmt == null) {
							String updateSql = this.getRuleJson().getString("updateSql");
							/**
							 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
							 */
							if (StringUtils.containsIgnoreCase(updateSql, " userinfo ")
									|| StringUtils.containsIgnoreCase(updateSql, " userrole ")
									|| StringUtils.containsIgnoreCase(updateSql, " net_role ")
									|| StringUtils.containsIgnoreCase(updateSql, " login_module ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_key ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_property ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_server ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_dictory ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_scheduler ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_application ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_access ")
									|| StringUtils.containsIgnoreCase(updateSql, " sys_database ")) {
								throw new RuntimeException(" SQL statement illegal ");
							}

							upsmt = connection.prepareStatement(updateSql);
						}
						this.populateParameter(this.updateParameters, param, list, true);
						JdbcUtils.fillStatement(upsmt, list);

						upsmt.addBatch();
						update++;
					} else { // 插入
						if (ipsmt == null) {
							String insertSql = this.getRuleJson().getString("insertSql");
							/**
							 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
							 */
							if (StringUtils.containsIgnoreCase(insertSql, " userinfo ")
									|| StringUtils.containsIgnoreCase(insertSql, " userrole ")
									|| StringUtils.containsIgnoreCase(insertSql, " net_role ")
									|| StringUtils.containsIgnoreCase(insertSql, " login_module ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_key ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_property ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_server ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_dictory ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_scheduler ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_application ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_access ")
									|| StringUtils.containsIgnoreCase(insertSql, " sys_database ")) {
								throw new RuntimeException(" SQL statement illegal ");
							}
							ipsmt = connection.prepareStatement(insertSql);
						}

						this.populateParameter(this.insertParameters, param, list, false);

						JdbcUtils.fillStatement(ipsmt, list);

						ipsmt.addBatch();
						insert++;
					}
				}
				if (insert > 0) {
					logger.debug(this.getRuleJson().getString("insertSql"));
					ipsmt.executeBatch();
				}
				if (update > 0) {
					logger.debug(this.getRuleJson().getString("updateSql"));
					upsmt.executeBatch();
				}
				logger.debug("更新集执行时间(毫秒):" + (System.currentTimeMillis() - start) + "--共" + size + " 条记录");
				if (connection != null && !connection.getAutoCommit()) {
					connection.commit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("更新集执行失败 ", ex);
				throw ex;
			} finally {
				JdbcUtils.close(upsmt);
				JdbcUtils.close(ipsmt);
			}
		}
	}

	public void execDynamic(Connection connection, List<Map<String, Object>> params, Set<String> fields)
			throws Exception {
		int size;
		if (connection != null && params != null && (size = params.size()) > 0) {
			long start = System.currentTimeMillis();
			List<Object> list;
			Map<String, Object> param;
			PreparedStatement upsmt = null;
			int update = 0, i;
			String updateSql = null;
			JSONArray whereClaus = this.ruleJson.getJSONArray("whereClaus");
			try {
				for (i = 0; i < size; i++) {
					param = params.get(i);
					if (param == null)
						continue;

					list = new ArrayList<Object>();

					if (this.isUpdate(param, whereClaus, fields)) { // 满足更新条件

						updateSql = this.getUpdateDynamicSql_(fields);

						/**
						 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
						 */
						if (StringUtils.containsIgnoreCase(updateSql, " userinfo ")
								|| StringUtils.containsIgnoreCase(updateSql, " userrole ")
								|| StringUtils.containsIgnoreCase(updateSql, " net_role ")
								|| StringUtils.containsIgnoreCase(updateSql, " login_module ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_key ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_property ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_server ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_dictory ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_scheduler ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_application ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_access ")
								|| StringUtils.containsIgnoreCase(updateSql, " sys_database ")) {
							throw new RuntimeException(" SQL statement illegal ");
						}

						upsmt = connection.prepareStatement(updateSql);

						this.populateParameter(this.updateParameters, param, list, true);

						JdbcUtils.fillStatement(upsmt, list);

						upsmt.addBatch();
						update++;
					}
				}
				if (update > 0) {
					logger.debug(updateSql);
					upsmt.executeBatch();
				}
				logger.debug("更新集执行时间(毫秒):" + (System.currentTimeMillis() - start) + "--共" + size + " 条记录");
				if (connection != null && !connection.getAutoCommit()) {
					connection.commit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("更新集执行失败 ", ex);
				throw ex;
			} finally {
				JdbcUtils.close(upsmt);
			}
		}
	}

	/**
	 * 执行删除操作
	 * 
	 * @param connection
	 * @param params
	 * @throws Exception
	 */
	public void executeRemove(Connection connection, List<Map<String, Object>> params) throws Exception {

		int size;
		if (connection != null && params != null && (size = params.size()) > 0) {
			long start = System.currentTimeMillis();
			Map<String, Object> param;
			int i, deletes = 0;
			Object tn = expressionMap.get("tableName");

			String deleteSql = this.getRuleJson().getString("deleteSql");

			PreparedStatement pstmt = null;

			StringBuffer SQL = new StringBuffer("DELETE FROM " + tn);

			boolean whereClaus = this.buildWhere(SQL, this.getRuleJson().getJSONObject(deleteClausKey), true);// 组装判断是否为where条件
			if (!whereClaus) {
				SQL.append(" WHERE ID = ?");
				// pstmt = connection.prepareStatement(SQL.append(" WHERE ID =
				// ?").toString());
			} else {

			}

			deleteSql = SQL.toString();

			/**
			 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
			 */
			if (StringUtils.containsIgnoreCase(deleteSql, " userinfo ")
					|| StringUtils.containsIgnoreCase(deleteSql, " userrole ")
					|| StringUtils.containsIgnoreCase(deleteSql, " net_role ")
					|| StringUtils.containsIgnoreCase(deleteSql, " login_module ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_key ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_property ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_server ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_dictory ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_scheduler ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_application ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_access ")
					|| StringUtils.containsIgnoreCase(deleteSql, " sys_database ")) {
				throw new RuntimeException(" SQL statement illegal ");
			}

			SqlFormat sf;
			try {
				for (i = 0; i < size; i++) {
					param = params.get(i);

					if (whereClaus) {
						sf = this.getSqlFormat(deleteSql, deleteParameters, param, false);

						// if (pstmt == null) {
						// pstmt = connection.prepareStatement(sf.getSql());
						// logger.debug(sf.getSql());
						// }
						// JdbcUtils.fillStatement(pstmt, sf.getParameters());
					} else {
						if (param == null || !param.containsKey("id"))
							continue;
						// pstmt.setObject(1, param.get("id"));

						sf = new SqlFormat(deleteSql, Arrays.asList(param.get("id")));
					}

					if (param.containsKey(MTTEST)) {// 调试
						param.put(MTTEST, new Object[] { sf.getSql(), sf.getParameters() });
						continue;
					}

					if (pstmt == null) {
						/**
						 * 严禁系统表被修改，系统表的维护只能通过特定界面操作！！！
						 */
						if (StringUtils.containsIgnoreCase(sf.getSql(), " userinfo ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " userrole ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " net_role ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " login_module ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_key ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_property ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_server ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_dictory ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_scheduler ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_application ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_access ")
								|| StringUtils.containsIgnoreCase(sf.getSql(), " sys_database ")) {
							throw new RuntimeException(" SQL statement illegal ");
						}
						pstmt = connection.prepareStatement(sf.getSql());
						logger.debug(sf.getSql());
					}
					JdbcUtils.fillStatement(pstmt, sf.getParameters());

					pstmt.addBatch();
					deletes++;
				}
				if (deletes > 0) {
					pstmt.executeBatch();
					logger.debug("更新集执行删除时间(毫秒):" + (System.currentTimeMillis() - start) + "--共" + deletes + " 条记录");
				}
				if (connection != null && !connection.getAutoCommit()) {
					connection.commit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("更新集执行失败 ", ex);
				throw ex;
			} finally {
				JdbcUtils.close(pstmt);
			}
		}

	}

	public JSONArray getParameters() {
		return parameters;
	}

	public void setParameters(JSONArray parameters) {
		this.parameters = parameters;
	}

	public JSONObject getRuleJson() {
		return ruleJson;
	}

	public void setRuleJson(JSONObject ruleJson) {
		this.ruleJson = ruleJson;
		this.init();
	}

	interface JdbcIterator {
		Boolean iterator(int index, String columnName, boolean isIn, boolean isU);
	}

	public static void main(String[] args) {

		String str = "$SYSDATE";

		str = ExpressionConvertUtil.expressionConvert(str, "database");

		System.out.println(str);

	}
}
