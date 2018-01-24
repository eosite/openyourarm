package com.glaf.datamgr.test;

import java.sql.*;

import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.query.builder.GroupedQueryBuilder;

import com.alibaba.druid.util.JdbcUtils;
import com.glaf.core.jdbc.DBConnectionFactory;

public class DataSetTest {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection();
			JdbcDataContext dc = new JdbcDataContext(conn);
			GroupedQueryBuilder query = dc.query().from("sys_application").select("nodeId").selectCount()
					.groupBy("nodeId");
			System.out.println(query.toQuery().toSql());
			DataSet ds = query.execute();

			while (ds.next()) {
				Row row = ds.getRow();
				System.out.println(row.getValue(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

}
