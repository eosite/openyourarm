package com.glaf.datamgr.test;

import java.sql.Connection;

import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.jdbc.*;
import org.apache.metamodel.query.*;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.schema.TableType;

import com.alibaba.druid.util.JdbcUtils;
import com.glaf.core.jdbc.DBConnectionFactory;

public class DataSetTest2 {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			JdbcDataContext dc = new JdbcDataContext(conn);
			JdbcDataContext strategy = new JdbcDataContext(conn, new TableType[] { TableType.TABLE, TableType.VIEW },
					null);
			Schema schema = strategy.getSchemaByName(strategy.getDefaultSchemaName());
			// QueryParameter queryParameter = new QueryParameter();

			Query q = new Query();

			Table table1 = schema.getTableByName("sys_tree");
			Table table2 = schema.getTableByName("sys_application");
			q.from(table1, "sys_tree");
			q.from(table2, "sys_application");
			q.select(table1.getColumns());
			q.select(table2.getColumnByName("url"));
			q.where(table1.getColumnByName("id"), OperatorType.EQUALS_TO, table2.getColumnByName("nodeId"));
			q.where(table2.getColumnByName("url"), OperatorType.LIKE, "%activiti%");
			q.orderBy(table1.getColumnByName("id"));
			q.setFirstRow(1);
			q.setMaxRows(10);

			CompiledQuery compiledQuery = dc.compileQuery(q);
			System.out.println(compiledQuery.toSql());
			DataSet ds = dc.executeQuery(compiledQuery, new Object[] {});

			// DataSet ds = strategy.executeQuery(q);
			while (ds.next()) {
				Row row = ds.getRow();
				System.out.println(row.toString());
				SelectItem[] items = row.getSelectItems();
				for (SelectItem item : items) {
					System.out.println(
							item.getColumn().getName() + "->" + row.getValue(item.getColumn().getColumnNumber()));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

}
