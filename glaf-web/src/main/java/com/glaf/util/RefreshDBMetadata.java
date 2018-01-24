package com.glaf.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;
import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;

import com.alibaba.druid.util.JdbcUtils;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.AnnotationUtils;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.ThreadFactory;

/**
 * 更新数据库字段长度
 * 
 * @author
 *
 */
public class RefreshDBMetadata {

	private final static Log logger = LogFactory.getLog(RefreshDBMetadata.class);

	protected static Map<String, ColumnStrategy> csMap = new HashMap<String, ColumnStrategy>();

	static {
		addStrategy("string", new StringStrategy());
	}

	public static void refresh(List<String> targetPackages) {
		// String targetPackages[] = new String[] { "com.glaf.datamgr.domain",
		// "com.glaf.workflow.core.domain" };
		String pn;
		Class<?> cls;
		Collection<String> clses = AnnotationUtils.findJPAEntity("com.glaf");
		Map<String, Collection<Class<?>>> map = new HashMap<String, Collection<Class<?>>>();
		if (CollectionUtils.isNotEmpty(targetPackages)) {
			for (String package_ : targetPackages) {
				map.put(package_, new ArrayList<Class<?>>());
			}
		}
		if (CollectionUtils.isNotEmpty(clses)) {
			for (String className : clses) {
				cls = ClassUtils.classForName(className);
				pn = cls.getPackage().getName();
				if (CollectionUtils.isEmpty(targetPackages)) {// 全部更新
					if (map.get(pn) == null) {
						map.put(pn, new ArrayList<Class<?>>());
					}
					map.get(pn).add(cls);
				} else {
					if (map.get(pn) != null) {
						map.get(pn).add(cls);
					}
				}
			}
			for (String key : map.keySet()) {
				ThreadFactory.execute(new RunRefreshMetaModel(map.get(key)));
			}
		}
	}

	public static class RunRefreshMetaModel implements Runnable {

		private Collection<Class<?>> clses;

		private DataSource ds = ContextFactory.getBean("dataSource");

		public RunRefreshMetaModel(Collection<Class<?>> clses) {
			this.clses = clses;
		}

		public void run() {
			if (CollectionUtils.isEmpty(clses)) {
				return;
			}
			Object value;
			Table table;
			String name;
			Field[] fields;
			Statement stmt = null;
			Connection connection = null;
			org.apache.metamodel.schema.Table t;
			try {
				connection = this.ds.getConnection();
				connection.setAutoCommit(false);
				DataContext dataContext = new JdbcDataContext(connection);
				Schema schema = dataContext.getDefaultSchema();
				stmt = connection.createStatement();
				int batch = 0;
				for (Class<?> cls : this.clses) {
					table = cls.getAnnotation(Table.class);
					if (table != null) {
						if (StringUtils.isNotBlank(name = table.name())) {
							if ((t = schema.getTableByName(name)) == null)
								continue;
							fields = cls.getDeclaredFields();
							for (Field field : fields) {
								ColumnStrategy cs = csMap.get(field.getType()//
										.getSimpleName().toLowerCase());
								if (cs != null) {
									value = cs.exec(field, t);
									if (value != null) {
										addBatch(stmt, value.toString());
										batch++;
									}
								}
							}
						}
					}
				}
				if (batch > 0) {
					stmt.executeBatch();
					connection.commit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage());
			} finally {
				JdbcUtils.close(stmt);
				JdbcUtils.close(connection);
			}

		}
	}

	protected static void addBatch(Statement stmt, String sql) throws SQLException {
		logger.debug(sql);
		stmt.addBatch(sql);
	}

	/**
	 * 字符串扩展
	 * 
	 */
	static class StringStrategy implements ColumnStrategy {

		private static final String sql = "ALTER TABLE %s ALTER COLUMN %s %s";

		@Override
		public Object exec(Field field, org.apache.metamodel.schema.Table table) {
			javax.persistence.Column column = //
					field.getAnnotation(javax.persistence.Column.class);
			if (column != null) {
				String type;
				org.apache.metamodel.schema.Column c = table.getColumnByName(column.name());
				if (c != null && !c.isPrimaryKey()) {
					if (c.getColumnSize() != null && column.length() > c.getColumnSize()) {
						logger.debug(table.getName() + " | " + column.name() + "--> model size : " + //
								column.length() + ", database size : " + c.getColumnSize());
						if (column.length() >= 4000) {// 转为text
							type = "TEXT";
						} else {
							type = String.format("VARCHAR(%s)", column.length());
						}
						return String.format(sql, table.getName(), column.name(), type);
					}
				}
			}
			return null;
		}
	}

	protected static void addStrategy(String type, ColumnStrategy strategy) {
		csMap.put(type, strategy);
	}

	interface ColumnStrategy {
		Object exec(Field field, org.apache.metamodel.schema.Table t);
	}
}
