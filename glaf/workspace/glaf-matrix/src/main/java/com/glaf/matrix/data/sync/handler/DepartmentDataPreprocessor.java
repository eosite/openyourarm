package com.glaf.matrix.data.sync.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.glaf.core.util.JdbcUtils;
import com.glaf.matrix.data.sync.model.TargetTable;

public class DepartmentDataPreprocessor implements DataPreprocessor {

	public Object prepare(java.sql.Connection conn, TargetTable table) {
		Map<String, String> deptMap = this.getDepartmentMap(conn, (String) table.getAttributeMap().get("sysId"));
		return deptMap;
	}

	public Map<String, String> getDepartmentMap(Connection conn, String sysId) {
		Map<String, String> deptMap = new HashMap<String, String>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(" select NAME, CODE from SYS_DEPARTMENT ");
			rs = psmt.executeQuery();
			while (rs.next()) {
				deptMap.put(rs.getString(2), rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		try {
			psmt = conn.prepareStatement(
					" select a.NAME, b.CODE_ from SYS_DEPARTMENT a inner join SYS_DEPARTMENT_CORRELATION b on a.ID = b.DEPTID_ where b.SYSID_ = '"
							+ sysId + "'");
			rs = psmt.executeQuery();
			while (rs.next()) {
				deptMap.put(rs.getString(2), rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(rs);
		}
		return deptMap;
	}
}
