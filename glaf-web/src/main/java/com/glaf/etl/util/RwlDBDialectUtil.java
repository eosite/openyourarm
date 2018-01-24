package com.glaf.etl.util;

import org.apache.log4j.Logger;

/**
 * 数据库SQL规则处理
 *
 */
public class RwlDBDialectUtil {

	private volatile static RwlDBDialectUtil instance = null;

	private RwlDBDialectUtil.dbtype currentDialect = RwlDBDialectUtil.dbtype.mysql;

	/**
	 * 是否支持分页: 1:支持分页(缺省) 0:不支持分页
	 */
	private int SQL_SUPPORT_PAGING = 1;

	private static Logger log = Logger.getLogger(RwlDBDialectUtil.class);

	private RwlDBDialectUtil() {
		_init();
	}

	private void _init() {

	}

	public static RwlDBDialectUtil getInstance() {
		if (instance == null) {
			synchronized (RwlDBDialectUtil.class) {
				if (instance == null) {
					instance = new RwlDBDialectUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 获取分页的SQL语句
	 * 
	 * @param _sql
	 *            基础语句
	 * @param hasOffset
	 *            是否限定数量(一般都是true)
	 * @param _start
	 *            起始数
	 * @param _limit
	 *            限定的数量
	 * @return 返回设定好分页的SQL语句
	 * @throws RwlDialectException
	 */
	public String getSqlLimit(String _sql, boolean hasOffset, int _start, int _limit) {

		if (log.isDebugEnabled()) {
			log.debug(">>RwlDBDialect-start:" + _sql);
		}

		/**
		 * #############Oracle/kingbase分页方式###############
		 */
		if (currentDialect == dbtype.oracle || currentDialect == dbtype.kingbase) {
			_sql = _sql.trim();
			boolean isForUpdate = false;
			if (_sql.toLowerCase().endsWith(" for update")) {
				_sql = _sql.substring(0, _sql.length() - 11);
				isForUpdate = true;
			}

			StringBuffer pagingSelect = new StringBuffer(_sql.length() + 100);
			if (hasOffset) {
				pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
			} else {
				pagingSelect.append("select * from ( ");
			}
			pagingSelect.append(_sql);
			if (hasOffset) {
				pagingSelect.append(" ) row_ where rownum <= " + (_start + _limit) + ") where rownum_ > " + _start);
			} else {
				pagingSelect.append(" ) where rownum <= " + (_start + _limit));
			}

			if (isForUpdate)
				pagingSelect.append(" for update");

			// 结束
			if (log.isDebugEnabled()) {
				log.debug(">>RwlDBDialect-end(oracle):" + pagingSelect.toString());
			}

			return pagingSelect.toString();
		}
		/**
		 * ############## HSQL方式 ###############
		 */
		else if (currentDialect == dbtype.hsql) {
			return new StringBuffer(_sql.length() + 10).append(_sql).insert(_sql.toLowerCase().indexOf("select") + 6,
					hasOffset ? " limit " + _start + " " + _limit : " top " + _start).toString();
		}

		// 缺省使用的是mysql的分页方式
		else if (currentDialect == dbtype.mysql) {
			String result = new StringBuffer(_sql.length() + 20).append(_sql)
					.append(hasOffset ? " limit " + _start + ", " + _limit : " limit " + _start).toString();

			// 结束
			if (log.isDebugEnabled()) {
				log.debug(">>RwlDBDialect-end(mysql):" + result);
			}

			return result;
		}

		/**
		 * ############## SQLServer分页方式 ################
		 */
		else if (currentDialect == dbtype.sqlserver) {

			StringBuffer pagingBuilder = new StringBuffer();
			String orderby = getOrderByPart(_sql);
			String distinctStr = "";

			String loweredString = _sql.toLowerCase();
			String sqlPartString = _sql.trim();
			if (loweredString.trim().startsWith("select")) {
				int index = 6;
				if (loweredString.startsWith("select distinct")) {
					distinctStr = "DISTINCT ";
					index = 15;
				}
				sqlPartString = sqlPartString.substring(index);
			}
			pagingBuilder.append(sqlPartString);

			// if no ORDER BY is specified use fake ORDER BY field to avoid
			// errors
			if (orderby == null || orderby.length() == 0) {
				orderby = "ORDER BY CURRENT_TIMESTAMP";
			}

			StringBuffer result = new StringBuffer();
			result.append("SELECT * FROM (").append("SELECT ").append(distinctStr)
					.append(" TOP 100 PERCENT ROW_NUMBER() OVER (") // 使用TOP 100
																	// PERCENT可以提高性能
					.append(orderby).append(") AS __hibernate_row_nr__, ").append(pagingBuilder)
					.append(") as ucstarTempTable WHERE __hibernate_row_nr__ >").append(_start)
					.append(" AND __hibernate_row_nr__ <=").append(_start + _limit)
					.append(" ORDER BY __hibernate_row_nr__");

			// 结束
			if (log.isDebugEnabled()) {
				log.debug(">>RwlDBDialect-end(sqlserver):" + result.toString());
			}

			return result.toString();
		}

		/**
		 * ############# 不支持的分页 ##############
		 */
		else {
			log.error("No support Paging!");
			return _sql;
		}
	}

	/**
	 * SQLServer的处理 polarbear 2009-5-9
	 * 
	 * @param sql
	 * @return
	 */
	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct") >= 0;
	}

	private static String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50).append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if (orderByIndex > 0 && !hasDistinct(sql)) {
			rownumber.append(sql.substring(orderByIndex));
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}

	/**
	 * 专门针对DB2处理的SQL代码 polarbear 2009-8-31
	 * 
	 * @param _sql
	 * @return
	 */
	private static String genReturnField(String _sql) {
		int startOfSelect = _sql.toLowerCase().indexOf("select");
		int startOfFrom = _sql.toLowerCase().indexOf("from");
		int startOfWhere = _sql.toLowerCase().indexOf("where");
		int startOfOrderBy = _sql.toLowerCase().indexOf("order by");
		int startOfGroupBy = _sql.toLowerCase().indexOf("group by");

		String returnField = "";
		if (startOfFrom >= 0) {
			String fromTableStr = "";
			if (startOfWhere >= 0) {
				fromTableStr = _sql.substring(startOfFrom + "from".length(), startOfWhere);
			} else if (startOfOrderBy >= 0) {
				fromTableStr = _sql.substring(startOfFrom + "from".length(), startOfOrderBy);
			} else if (startOfGroupBy >= 0) {
				fromTableStr = _sql.substring(startOfFrom + "from".length(), startOfGroupBy);
			} else {
				fromTableStr = _sql.substring(startOfFrom + "from".length());
			}
			if (fromTableStr.length() > 0) {
				String[] fromTableStrArr = fromTableStr.split(",");
				for (String fromTable : fromTableStrArr) {
					if (fromTable != null && fromTable.length() > 0) {
						String fromTable2 = fromTable.trim();
						int startTableName = fromTable2.indexOf(" ");
						String tableNick = "";
						if (startTableName > 0) {
							tableNick = fromTable2.substring(startTableName);
						} else {
							tableNick = fromTable2;
						}
						tableNick = tableNick.trim();
						returnField += tableNick + ".*" + ",";
					}

				}
			}
			if (returnField.length() > 0) {
				returnField = returnField.substring(0, returnField.length() - 1);
			}

		}

		if (startOfSelect >= 0 && startOfFrom >= 0) {
			String selectFromStr = _sql.substring(startOfSelect + "select".length(), startOfFrom);
			String fromEndStr = _sql.substring(startOfFrom + "from".length(), _sql.length());
			selectFromStr = selectFromStr.trim();
			if (selectFromStr.length() > 0) {
				String selectField = "";
				String[] tempSqlArr = selectFromStr.split(",");
				for (String tempStr : tempSqlArr) {
					if (tempStr != null && tempStr.length() > 0) {
						if (tempStr.equalsIgnoreCase("*")) {
							selectField += returnField + ",";
						} else {
							selectField += tempStr + ",";
						}
					}
				}
				if (selectField.length() > 0) {
					selectField = selectField.substring(0, selectField.length() - 1);
					return "select" + " " + selectField + " from " + fromEndStr;
				}
			}
		}

		return _sql;
	}

	/**
	 * 数据库类型
	 * 
	 * @author polarrwl
	 */
	public enum dbtype {
		oracle, mysql, sqlserver, db2, hsql, kingbase
	}

	/**
	 * 根据驱动得到对应的数据库类型
	 * 
	 * @param _driver
	 * @return
	 */
	public static dbtype getDbtypeByDriver(String _driver) {
		if (_driver != null) {
			if (_driver.toLowerCase().indexOf("oracle") >= 0) {
				return dbtype.oracle;
			} else if (_driver.toLowerCase().indexOf("kingbase") >= 0) {
				return dbtype.kingbase;
			} else if (_driver.toLowerCase().indexOf("mysql") >= 0) {
				return dbtype.mysql;
			} else if (_driver.toLowerCase().indexOf("sqlserver") >= 0) {
				return dbtype.sqlserver;
			} else if (_driver.toLowerCase().indexOf("hsql") >= 0) {
				return dbtype.hsql;
			} else if (_driver.toLowerCase().indexOf("db2") >= 0) {
				return dbtype.db2;
			}
		}
		return null;
	}

	/**
	 * 设定当前的数据库类型
	 * 
	 * @param _dbtype
	 */
	public void setCurrentDialect(dbtype _dbtype) {

		log.info("设定当前的数据库类型(currentDialect):" + _dbtype);

		if (_dbtype != null) {
			currentDialect = _dbtype;
		}
	}

	public static void main(String[] args) {
		//System.out.println(genReturnField("select * from user order by type"));
	}
}