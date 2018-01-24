package com.glaf.core.test;

import java.sql.*;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;

public class H2Test2 {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		Connection conn1 = null;
		Connection conn2 = null;
		PreparedStatement psmt1 = null;
		PreparedStatement psmt2 = null;
		ResultSet rs1 = null;
		try {
			Class.forName("org.h2.Driver");
			conn1 = DBConnectionFactory.getConnection("default");
			// conn2 = DBConnectionFactory.getConnection("default");
			conn2 = DriverManager.getConnection("jdbc:h2:./testdb2", "sa", "");
			conn2.setAutoCommit(false);
			psmt1 = conn1.prepareStatement(" select index_id, parent_id, id from treepinfo ");
			psmt2 = conn2.prepareStatement(
					" create table test_treepinfo(index_id int not null, parent_id int, id varchar(200), primary key (index_id)) ");
			psmt2.executeUpdate();
			conn2.commit();
			JdbcUtils.close(psmt2);
			psmt2 = conn2.prepareStatement(" insert into test_treepinfo(index_id, parent_id, id) values(?, ?, ?) ");
			rs1 = psmt1.executeQuery();
			int index = 0;
			while (rs1.next()) {
				index++;
				psmt2.setInt(1, rs1.getInt(1));
				psmt2.setInt(2, rs1.getInt(2));
				psmt2.setString(3, rs1.getString(3));
				psmt2.addBatch();
				if (index > 0 && index % 2000 == 0) {
					psmt2.executeBatch();
					conn2.commit();
					// System.out.println(index + " insert ok .");
				}
			}
			psmt2.executeBatch();
			conn2.commit();
			System.out.println(index + " insert ok .");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(rs1);
			JdbcUtils.close(psmt1);
			JdbcUtils.close(psmt2);
			JdbcUtils.close(conn1);
			JdbcUtils.close(conn2);
		}
		long ts = System.currentTimeMillis() - start;
		System.out.println("Time(ms):" + ts);
	}

}
