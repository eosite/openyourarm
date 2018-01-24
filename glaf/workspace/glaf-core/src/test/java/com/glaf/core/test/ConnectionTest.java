package com.glaf.core.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryConnectionFactory;
import com.glaf.core.util.JdbcUtils;

public class ConnectionTest {

	protected static volatile Configuration conf = BaseConfiguration.create();

	public static void main(String[] args) throws Exception {
		long ts = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection("default");
			System.out.println(conn.toString());
			java.util.Properties props = conn.getClientInfo();
			java.util.Enumeration<?> e = props.propertyNames();
			System.out.println("-----------------------------------------");
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				System.out.println(key + "\t\t" + props.getProperty(key));
			}
			System.out.println("-----------------------------------------");
			ts = System.currentTimeMillis();
			QueryConnectionFactory.getInstance().register(ts, conn);
			psmt = conn.prepareStatement(" select * from userinfo");
			rs = psmt.executeQuery();
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			System.out.println("Query OK");
			Thread.sleep(65000);

			psmt = conn.prepareStatement(" select * from userinfo");
			rs = psmt.executeQuery();
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			System.out.println("Query OK");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (conn != null) {
				QueryConnectionFactory.getInstance().unregister(ts, conn);
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
	}

}
