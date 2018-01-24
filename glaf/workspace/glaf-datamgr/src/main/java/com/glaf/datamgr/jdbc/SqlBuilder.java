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

import java.lang.reflect.Field;
import java.util.*;

import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.QueryUtils;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.SqlSegment;
import com.glaf.datamgr.util.Constants;

import io.netty.util.concurrent.FastThreadLocal;

public class SqlBuilder {

	private static final FastThreadLocal<SQL> localSQL = new FastThreadLocal<SQL>();

	public static void BEGIN() {
		RESET();
	}

	public static void RESET() {
		localSQL.set(new SQL());
	}

	public static void SELECT(String columns) {
		sql().SELECT(columns);
	}

	public static void SELECT_DISTINCT(String columns) {
		sql().SELECT_DISTINCT(columns);
	}

	public static void FROM(String table) {
		sql().FROM(table);
	}

	public static void JOIN(String join) {
		sql().JOIN(join);
	}

	public static void INNER_JOIN(String join) {
		sql().INNER_JOIN(join);
	}

	public static void LEFT_OUTER_JOIN(String join) {
		sql().LEFT_OUTER_JOIN(join);
	}

	public static void RIGHT_OUTER_JOIN(String join) {
		sql().RIGHT_OUTER_JOIN(join);
	}

	public static void OUTER_JOIN(String join) {
		sql().OUTER_JOIN(join);
	}

	public static void WHERE(String conditions) {
		sql().WHERE(conditions);
	}

	public static void OR() {
		sql().OR();
	}

	public static void AND() {
		sql().AND();
	}

	public static void GROUP_BY(String columns) {
		sql().GROUP_BY(columns);
	}

	public static void HAVING(String conditions) {
		sql().HAVING(conditions);
	}

	public static void ORDER_BY(String columns) {
		sql().ORDER_BY(columns);
	}

	public static String SQL() {
		try {
			return sql().toString();
		} finally {
			RESET();
		}
	}

	private static SQL sql() {
		return localSQL.get();
	}

	public SqlExecutor buildSql(List<SqlSegment> sqlSegments, List<Map<String, Object>> paramList) {
		SqlExecutor sqlExecutor = new SqlExecutor();

		BEGIN();

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.SELECT_SQL_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getLoopCount() > 0) {
					if (paramList != null && !paramList.isEmpty()) {
						int index = 0;
						for (Map<String, Object> row : paramList) {
							row.put("index", index);
							String segment = sqlSegment.getSegment();
							segment = QueryUtils.replaceTextParas(segment, row);
							SELECT(sqlSegment.getSegment());
							index++;
						}
					}
				} else {
					SELECT(sqlSegment.getSegment());
				}
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.FROM_SQL_SEGMENT.equals(sqlSegment.getType())) {
				FROM(sqlSegment.getSegment());
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.JOIN_SQL_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getLoopCount() > 0) {
					if (paramList != null && !paramList.isEmpty()) {
						int index = 0;
						for (Map<String, Object> row : paramList) {
							row.put("index", index);
							String segment = sqlSegment.getSegment();
							segment = QueryUtils.replaceTextParas(segment, row);
							JOIN(sqlSegment.getSegment());
							index++;
						}
					}
				} else {
					JOIN(sqlSegment.getSegment());
				}
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.INNER_JOIN_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getLoopCount() > 0) {
					if (paramList != null && !paramList.isEmpty()) {
						int index = 0;
						for (Map<String, Object> row : paramList) {
							row.put("index", index);
							String segment = sqlSegment.getSegment();
							segment = QueryUtils.replaceTextParas(segment, row);
							INNER_JOIN(sqlSegment.getSegment());
							index++;
						}
					}
				} else {
					INNER_JOIN(sqlSegment.getSegment());
				}
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.OUTER_JOIN_SQL_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getLoopCount() > 0) {
					if (paramList != null && !paramList.isEmpty()) {
						int index = 0;
						for (Map<String, Object> row : paramList) {
							row.put("index", index);
							String segment = sqlSegment.getSegment();
							segment = QueryUtils.replaceTextParas(segment, row);
							OUTER_JOIN(sqlSegment.getSegment());
							index++;
						}
					}
				} else {
					OUTER_JOIN(sqlSegment.getSegment());
				}
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.LEFT_OUTER_JOIN_SQL_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getLoopCount() > 0) {
					if (paramList != null && !paramList.isEmpty()) {
						int index = 0;
						for (Map<String, Object> row : paramList) {
							row.put("index", index);
							String segment = sqlSegment.getSegment();
							segment = QueryUtils.replaceTextParas(segment, row);
							LEFT_OUTER_JOIN(sqlSegment.getSegment());
							index++;
						}
					}
				} else {
					LEFT_OUTER_JOIN(sqlSegment.getSegment());
				}
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.RIGHT_OUTER_JOIN_SQL_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getLoopCount() > 0) {
					if (paramList != null && !paramList.isEmpty()) {
						int index = 0;
						for (Map<String, Object> row : paramList) {
							row.put("index", index);
							String segment = sqlSegment.getSegment();
							segment = QueryUtils.replaceTextParas(segment, row);
							RIGHT_OUTER_JOIN(sqlSegment.getSegment());
							index++;
						}
					}
				} else {
					RIGHT_OUTER_JOIN(sqlSegment.getSegment());
				}
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.WHERE_SQL_SEGMENT.equals(sqlSegment.getType())) {
				if (sqlSegment.getConnector() != null) {
					if ("AND".equalsIgnoreCase(sqlSegment.getConnector())) {
						AND();
					} else if ("OR".equalsIgnoreCase(sqlSegment.getConnector())) {
						OR();
					}
				}
				WHERE(sqlSegment.getSegment());
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.GROUP_BY_SQL_SEGMENT.equals(sqlSegment.getType())) {
				GROUP_BY(sqlSegment.getSegment());
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.HAVING_SQL_SEGMENT.equals(sqlSegment.getType())) {
				HAVING(sqlSegment.getSegment());
			}
		}

		for (SqlSegment sqlSegment : sqlSegments) {
			if (Constants.ORDER_BY_SQL_SEGMENT.equals(sqlSegment.getType())) {
				ORDER_BY(sqlSegment.getSegment());
			}
		}

		sqlExecutor.setSql(SQL());

		return sqlExecutor;
	}

	public static String buildInsertSql(Class<?> cls) {
		javax.persistence.Table table = cls.getAnnotation(javax.persistence.Table.class);
		if (table != null) {
			int i = 0;
			StringBuffer SB = new StringBuffer("INSERT INTO ").append(table.name()).append(" (");
			StringBuffer VALUES = new StringBuffer(") VALUES ( ");
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				javax.persistence.Column column = field.getAnnotation(javax.persistence.Column.class);
				if (column != null) {
					switch (i) {
					case 0:
						break;
					default:
						SB.append(", ");
						VALUES.append(", ");
						break;
					}
					SB.append(column.name());
					VALUES.append("?");
					i++;
				}
			}
			return SB.append(VALUES.append(" )")).toString();
		}
		return null;
	}

	public static void main(String[] args) {
		String sql = buildInsertSql(SelectSegment.class);
		System.out.println(sql);
	}

}
