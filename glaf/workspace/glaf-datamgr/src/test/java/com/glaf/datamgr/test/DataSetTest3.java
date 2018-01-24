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

public class DataSetTest3 {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			JdbcDataContext dc = new JdbcDataContext(conn);
			JdbcDataContext strategy = new JdbcDataContext(conn, new TableType[] { TableType.TABLE, TableType.VIEW },
					null);
			Schema schema = strategy.getSchemaByName(strategy.getDefaultSchemaName());
			// QueryParameter queryParameter = new QueryParameter();

			Table table = schema.getTableByName("userinfo");
			Query q = new Query();
			q.select(table.getColumnByName("UserId"));
			q.select(table.getColumnByName("UserName"));
			q.from(table);
			q.where(" issystem = '1' and ( userId = 'admin' or userId = 'root' ) ");

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
			compiledQuery.close();
			compiledQuery = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

}
