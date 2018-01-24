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

package com.glaf.datamgr.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.entity.SqlExecutor;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.jdbc.SqlBuilder;

public class SqlSegmentUtils {

	public static void main(String[] args) throws Exception {
		DataSet dataset = new DataSet();
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
		seg32.setOperator("LIKE");
		seg32.setValue("'%test%'");
		dataset.addWhere(seg32);

		OrderBySegment seg41 = new OrderBySegment();
		seg41.setSort("asc");
		seg41.setColumnName("COLUMNNAME_");
		seg41.setTableName("SYS_COLUMN");

		dataset.addOrderBy(seg41);

		SqlExecutor executor = toSql(dataset, null);
		System.out.println(executor.getSql());
		System.out.println(DataSetJsonFactory.toJSON(dataset));

	}

	public static SqlExecutor toSql(DataSet dataset, List<Map<String, Object>> paramList) {
		SqlExecutor sqlExecutor = null;
		if (dataset.getSelectSegments() != null && !dataset.getSelectSegments().isEmpty()) {

			List<SqlSegment> sqlSegments = new ArrayList<SqlSegment>();

			for (SelectSegment seg : dataset.getSelectSegments()) {
				/**
				 * 如果是物理字段
				 */
				if (StringUtils.isNotEmpty(seg.getColumnName())) {
					SqlSegment model = new SqlSegment();
					model.setType(Constants.SELECT_SQL_SEGMENT);
					String alias = null;
					if (StringUtils.isNotEmpty(seg.getTableName())) {
						alias = seg.getTableName().toLowerCase();
					}
					if (alias != null) {
						model.setSegment(alias + "." + seg.getColumnName());
						if (StringUtils.isNotEmpty(seg.getColumnLabel())) {
							model.setSegment(model.getSegment() + " as " + seg.getColumnLabel());
						}
					}
					sqlSegments.add(model);
				} else if (StringUtils.isNotEmpty(seg.getExpression())) {
					/**
					 * 如果是表达式
					 */
					SqlSegment model = new SqlSegment();
					model.setType(Constants.SELECT_SQL_SEGMENT);
					if (StringUtils.isNotEmpty(seg.getColumnLabel())) {
						model.setSegment(seg.getExpression() + " as " + seg.getColumnLabel());
					} else {
						model.setSegment(seg.getExpression());
					}
					sqlSegments.add(model);
				}
			}

			if (dataset.getFromSegments() != null && !dataset.getFromSegments().isEmpty()) {
				for (FromSegment seg : dataset.getFromSegments()) {
					if (StringUtils.isNotEmpty(seg.getTableName())) {
						SqlSegment model = new SqlSegment();
						model.setType(Constants.FROM_SQL_SEGMENT);
						String alias = null;
						if (StringUtils.isNotEmpty(seg.getTableName())) {
							alias = seg.getTableName().toLowerCase();
						}
						if (alias != null) {
							model.setSegment(seg.getTableName() + " AS " + alias);
						}
						sqlSegments.add(model);
					}
				}
			}

			if (dataset.getJoinSegments() != null && !dataset.getJoinSegments().isEmpty()) {
				for (JoinSegment seg : dataset.getJoinSegments()) {
					if (StringUtils.isNotEmpty(seg.getSourceTable()) && StringUtils.isNotEmpty(seg.getSourceColumn())
							&& StringUtils.isNotEmpty(seg.getTargetTable())
							&& StringUtils.isNotEmpty(seg.getTargetColumn())) {
						SqlSegment model = new SqlSegment();
						model.setType(Constants.WHERE_SQL_SEGMENT);
						String connector = " = ";
						model.setConnector(connector);
						model.setSegment(seg.getSourceTable().toLowerCase() + "." + seg.getSourceColumn() + connector
								+ seg.getTargetTable().toLowerCase() + "." + seg.getTargetColumn());
						sqlSegments.add(model);
					}
				}
			}

			if (dataset.getWhereSegments() != null && !dataset.getWhereSegments().isEmpty()) {
				SqlSegment m = new SqlSegment();
				m.setType(Constants.WHERE_SQL_SEGMENT);
				m.setSegment(" 1 = 1 ");
				sqlSegments.add(m);
				for (WhereSegment seg : dataset.getWhereSegments()) {
					if (StringUtils.isNotEmpty(seg.getColumnName())
							&& StringUtils.isNotEmpty(seg.getValue().toString())) {
						SqlSegment model = new SqlSegment();
						model.setType(Constants.WHERE_SQL_SEGMENT);
						String connector = "AND";
						if (StringUtils.equalsIgnoreCase(seg.getConnector(), "AND")) {
							connector = "AND";
						} else if (StringUtils.equalsIgnoreCase(seg.getConnector(), "OR")) {
							connector = "OR";
						}
						model.setConnector(connector);
						String alias = null;
						if (StringUtils.isNotEmpty(seg.getTableName())) {
							alias = seg.getTableName().toLowerCase();
						}
						if (alias != null) {
							model.setSegment(
									alias + "." + seg.getColumnName() + " " + seg.getOperator() + " " + seg.getValue());
						} else {
							model.setSegment(seg.getColumnName() + " " + seg.getOperator() + " " + seg.getValue());
						}
						sqlSegments.add(model);
					}
				}
			}

			if (dataset.getGroupBySegments() != null && !dataset.getGroupBySegments().isEmpty()) {
				for (GroupBySegment seg : dataset.getGroupBySegments()) {
					if (StringUtils.isNotEmpty(seg.getColumnName())) {
						SqlSegment model = new SqlSegment();
						model.setType(Constants.GROUP_BY_SQL_SEGMENT);
						String alias = null;
						if (StringUtils.isNotEmpty(seg.getTableName())) {
							alias = seg.getTableName().toLowerCase();
						}
						if (alias != null) {
							model.setSegment(alias + "." + seg.getColumnName());
						} else {
							model.setSegment(seg.getColumnName());
						}
						sqlSegments.add(model);
					}
				}
			}

			if (dataset.getHavingSegments() != null && !dataset.getHavingSegments().isEmpty()) {
				for (HavingSegment seg : dataset.getHavingSegments()) {
					if (StringUtils.isNotEmpty(seg.getColumnName())
							&& StringUtils.isNotEmpty(seg.getValue().toString())) {
						SqlSegment model = new SqlSegment();
						model.setType(Constants.HAVING_SQL_SEGMENT);
						String connector = "AND";
						if (StringUtils.equalsIgnoreCase(seg.getConnector(), "AND")) {
							connector = "AND";
						} else if (StringUtils.equalsIgnoreCase(seg.getConnector(), "OR")) {
							connector = "OR";
						}
						model.setConnector(connector);
						String alias = null;
						if (StringUtils.isNotEmpty(seg.getTableName())) {
							alias = seg.getTableName().toLowerCase();
						}
						if (alias != null) {
							model.setSegment(alias + "." + seg.getColumnName() + " " + seg.getOperator() + " "
									+ seg.getValue().toString());
						} else {
							model.setSegment(
									seg.getColumnName() + " " + seg.getOperator() + " " + seg.getValue().toString());
						}
						model.setSegment(
								seg.getColumnName() + " " + seg.getOperator() + " " + seg.getValue().toString());
						sqlSegments.add(model);
					}
				}
			}

			Collections.sort(dataset.getOrderBySegments());

			if (dataset.getOrderBySegments() != null && !dataset.getOrderBySegments().isEmpty()) {
				for (OrderBySegment seg : dataset.getOrderBySegments()) {
					if (StringUtils.isNotEmpty(seg.getColumnName())) {
						SqlSegment model = new SqlSegment();
						model.setType(Constants.ORDER_BY_SQL_SEGMENT);
						String alias = null;
						if (StringUtils.isNotEmpty(seg.getTableName())) {
							alias = seg.getTableName().toLowerCase();
						}
						if ("asc".equals(seg.getSort())) {
							model.setSegment(alias + "." + seg.getColumnName() + " asc");
						} else {
							model.setSegment(alias + "." + seg.getColumnName() + " desc");
						}
						sqlSegments.add(model);
					}
				}
			}

			if (sqlSegments.size() > 0) {
				SqlBuilder builder = new SqlBuilder();
				sqlExecutor = builder.buildSql(sqlSegments, null);
				dataset.setSql(sqlExecutor.getSql());
			}
		}
		return sqlExecutor;
	}

	private SqlSegmentUtils() {

	}

}
