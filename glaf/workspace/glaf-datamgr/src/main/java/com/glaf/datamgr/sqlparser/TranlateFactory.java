package com.glaf.datamgr.sqlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.datamgr.domain.SystemDBFuncMapping;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.query.SystemDBFuncMappingQuery;
import com.glaf.datamgr.service.SystemDBFuncMappingService;
import com.glaf.datamgr.sqlparser.method.CommonMethod;
import com.glaf.datamgr.sqlparser.method.IMethod;

public class TranlateFactory {

	private static Map<String, List<ITranslator>> collections;

	private static Map<String, Map<String, ITranslator>> translates;

	private static Map<String, String> mapping = new HashMap<>();

	private static Set<String> noTran = new HashSet<String>();

	private static SystemDBFuncMappingService systemDBFuncMappingService;

	private static ThreadLocal<ConcurrentMap<String, String>> aliasMapping = new ThreadLocal<ConcurrentMap<String, String>>();

	protected static SystemDBFuncMappingService getSystemDBFuncMappingService() {
		return systemDBFuncMappingService == null ? (systemDBFuncMappingService = ContextFactory
				.getBean("com.glaf.datamgr.service.systemDBFuncMappingService")) : systemDBFuncMappingService;
	}

	static {
		mapping.put("0", "sqlserver");
		mapping.put("1", "oracle");
		mapping.put("2", "mysql");
		mapping.put("3", "db2");
		mapping.put("4", "postgre");
		noTran.addAll(Arrays.asList("round", "avg", "sum", "min", "max", "varchar"));

		reload();
	}

	/**
	 * 重新加载
	 */
	public static boolean reload() {
		translates = new HashMap<>();
		collections = new HashMap<>();

		// initFileResource();
		try {
			initDataBaseResource();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 从数据库中获取
	 */
	private static void initDataBaseResource() {
		SystemDBFuncMappingQuery query = new SystemDBFuncMappingQuery();
		List<SystemDBFuncMapping> list = getSystemDBFuncMappingService().list(query);

		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		for (SystemDBFuncMapping m : list) {
			String dbType = m.getDtype();

			if (StringUtils.isBlank(dbType)) {
				continue;
			}

			dbType = mapping.get(dbType);
			if (StringUtils.isBlank(dbType)) {
				continue;
			}

			String methodName = m.getFuncName().toLowerCase();

			String parentId = m.getFuncId();

			IMethod method = new CommonMethod(parentId);

			method.setResource(m.getParams(), dbType, null, methodName);

			add(dbType, methodName, method);

			if (StringUtils.isBlank(parentId)) {
				continue;
			}

			if (!collections.containsKey(parentId)) {
				collections.put(parentId, new ArrayList<>());
			}

			collections.get(parentId).add(method);
		}

	}

	/**
	 * 从资源文件获取
	 */
	static void initFileResource() {
		try {
			InputStream is = TranlateFactory.class.getResourceAsStream("/methods.conf");
			if (is == null) {
				return;
			}
			JSONArray methods = JSONArray.parseObject(is, null);
			if (CollectionUtils.isNotEmpty(methods)) {
				for (int i = 0; i < methods.size(); i++) {
					JSONObject m = methods.getJSONObject(i);
					String dbType = m.getString("DTYPE_");

					if (StringUtils.isBlank(dbType)) {
						continue;
					}

					dbType = mapping.get(dbType);

					if (StringUtils.isBlank(dbType)) {
						continue;
					}

					String methodName = m.getString("FUNCNAME_").toLowerCase();

					String parentId = m.getString("FUNC_ID_");

					IMethod method = new CommonMethod(parentId);

					method.setResource(m.getString("PARAMS_"), dbType, null, methodName);

					add(dbType, methodName, method);

					if (StringUtils.isBlank(parentId)) {
						continue;
					}

					if (!collections.containsKey(parentId)) {
						collections.put(parentId, new ArrayList<>());
					}

					collections.get(parentId).add(method);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void add(String dbType, String o, ITranslator translate) {
		if (!translates.containsKey(dbType)) {
			translates.put(dbType, new HashMap<>());
		}
		translates.get(dbType).put(o, translate);
	}

	public static ITranslator getTranslator(String dbType, String o) {
		if (translates.containsKey(dbType)) {
			return translates.get(dbType).get(o);
		}
		return null;
	}

	public static void add(String dbType, Map<String, ITranslator> map) {
		translates.put(dbType, map);
	}

	public static Map<String, ITranslator> getTranslatorMap(String dbType) {
		return translates.get(dbType);
	}

	/**
	 * 【通过源数据库函数名】获取目标函数
	 * 
	 * @param source
	 * @param methodName
	 * @return
	 */
	public static ITranslator getTMNBySMN(String source, String methodName, String target) {
		ITranslator trans = getTranslator(source, methodName);

		if (trans == null || StringUtils.isBlank(trans.getType())) {
			return null;
		}

		List<ITranslator> list = collections.get(trans.getType());

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		for (ITranslator tran : list) {
			if (tran.getSource().equalsIgnoreCase(target)) {
				return tran;
			}
		}
		return null;
	}

	/**
	 * 函数转换
	 * 
	 * @param function
	 * @param target
	 */
	public static void translateMethods(SQLMethodInvokeExpr function, String source, String target) {
		String methodName = function.getMethodName().toLowerCase();
		if (noTran.contains(methodName)) {
			return;
		}
		ITranslator translator = TranlateFactory.getTranslator(source, methodName);
		if (translator == null) {
			CANTTRANLATE(function);
			return;
		}
		translator.setResource(null, source, target, null);
		translator.convert(function);
	}

	/**
	 * 程序自动判断源，目标
	 * 
	 * @param sql
	 * @return
	 */
	public static String translateSQL(String sql) {
		if (!CustomProperties.getBoolean("dataSet.translate")) {
			return sql;
		}
		String source = CustomProperties.getString("dataSet.db_source");
		String target = CustomProperties.getString("dataSet.db_target");

		String currentType = DBConnectionFactory.getDatabaseType();

		if (StringUtils.isEmpty(target)) {
			target = currentType;
		} else if (!target.equalsIgnoreCase(currentType)) {// 参数设置不对
			/**
			 * 默认目标数据库为当前运行的数据库
			 */
			return sql;
		}

		return translateSQL(sql, source, target);
	}

	/**
	 * 转换sql
	 * 
	 * @param sql
	 * @param source
	 * @param target
	 * @return
	 */
	public static String translateSQL(String sql, String source, String target) {

		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(target)) {
			return sql;
		}
		if (StringUtils.equalsIgnoreCase(source, target)) {
			return sql;
		}

		SQLStatementParser parser = new SQLStatementParser(sql);

		SQLStatement statement = parser.parseStatement();

		SchemaStatVisitor visitor = new SchemaStatVisitor();

		statement.accept(visitor);

		/**
		 * 转换函数
		 */
		List<SQLMethodInvokeExpr> funcs = visitor.getFunctions();
		if (CollectionUtils.isNotEmpty(funcs)) {
			try {
				for (SQLMethodInvokeExpr func : funcs) {
					translateMethods(func, source, target);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

		/**
		 * 列转换 [列不能太长]
		 */
		TranlateFactory.set();
		if (statement instanceof SQLSelectStatement) {
			itConvert(((SQLSelectStatement) statement).getSelect().getQueryBlock(), true, target);
		}

		return statement.toString();
	}

	/**
	 * 深度替换较长的列
	 * 
	 * @param query
	 * @param top
	 * @param target
	 */
	static Map<String, String> itConvert(SQLSelectQueryBlock query, boolean top, String target) {
		Map<String, String> columnMapping = null;
		if (query.getFrom() != null && query.getFrom() instanceof SQLSubqueryTableSource) {
			SQLSubqueryTableSource s = (SQLSubqueryTableSource) query.getFrom();
			SQLSelectQueryBlock q = s.getSelect().getQueryBlock();
			if (q != null) {
				columnMapping = itConvert(q, false, target);
			}
		}

		/**
		 * 转换where 里面的列
		 */
		if (query.getWhere() != null) {
			convertWhere(query.getWhere(), columnMapping);
		}

		return convertColumn(query.getSelectList(), top, target, columnMapping);
	}

	static void convertWhere(SQLExpr expr, Map<String, String> subMapping) {
		if (expr == null || MapUtils.isEmpty(subMapping)) {
			return;
		}
		if (expr instanceof SQLBinaryOpExpr) {
			SQLBinaryOpExpr where = (SQLBinaryOpExpr) expr;
			convertWhere(where.getLeft(), subMapping);
			convertWhere(where.getRight(), subMapping);
		} else if (expr instanceof SQLPropertyExpr) {
			SQLPropertyExpr prop = (SQLPropertyExpr) expr;
			if (subMapping.containsKey(prop.getName())) {
				prop.setName(subMapping.get(prop.getName()));
			}
		}
	}

	/**
	 * 
	 * @param selects
	 * @param top
	 * @param target
	 * @param subMapping
	 * @return
	 */
	static Map<String, String> convertColumn(List<SQLSelectItem> selects, boolean top, String target,
			Map<String, String> subMapping) {
		Map<String, String> columnMapping = new HashMap<String, String>();
		if (CollectionUtils.isNotEmpty(selects)) {
			int length;
			boolean hasSyn = false;
			String alias, column, syn = "\"";
			long time = System.currentTimeMillis();
			for (SQLSelectItem item : selects) {
				if (StringUtils.isEmpty(alias = item.getAlias())) {
					continue;
				}

				/**
				 * 判断是否有双引号
				 */
				hasSyn = StringUtils.contains(alias, syn);

				column = StringUtils.replaceChars(alias, syn, "");

				if ((length = column.length()) > 30) {
					alias = "field_" + (time++);
					if (top)
						TranlateFactory.get().put(alias, column);
					item.setAlias(alias);
				} else {
					item.setAlias(column);
				}

				/**
				 * 本身有双引号需要加回去
				 */
				if (hasSyn /*|| (top && (DBUtils.DM_DBMS.equalsIgnoreCase(target) || 
						DBUtils.ORACLE.equalsIgnoreCase(target)))*/) {
					item.setAlias(syn + item.getAlias() + syn);
				}

				if (length > 30) {
					columnMapping.put(column, item.getAlias());
				}

				if (subMapping != null) {
					if (item.getExpr() != null && item.getExpr() instanceof SQLPropertyExpr) {
						SQLPropertyExpr prop = (SQLPropertyExpr) item.getExpr();
						if (subMapping.containsKey(prop.getName())) {
							prop.setName(subMapping.get(prop.getName()));
						}
					}
				}

			}
		}
		return columnMapping;
	}

	public static void CANTTRANLATE(SQLMethodInvokeExpr function) {
		function.getParent().addAfterComment("/*can't tranlate! */");
	}

	/**
	 * 全局数据库映射
	 * 
	 * @return
	 */
	public static Map<String, String> getMapping() {
		return mapping;
	}

	/**
	 * 转换列映射
	 * 
	 * @return
	 */
	public static Map<String, String> get() {
		return aliasMapping.get();
	}

	public static void set() {
		aliasMapping.set(new ConcurrentHashMap<String, String>());
	}

	/**
	 * 获取已转换列
	 * 
	 * @param column
	 * @return
	 */
	public static String getColumn(String column) {
		Map<String, String> map = get();

		/**
		 * 数据集存储的列信息
		 */
		column = DataSetBuilder.getColumn(column);

		if (map != null) {
			return map.getOrDefault(column, column);
		}
		return column;
	}

}
