package com.glaf.core.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.BaseTree;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.tree.component.TreeRepository;
import com.glaf.core.tree.helper.JacksonTreeHelper;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;

public class TreeHelperTest {
	protected static Log logger = LogFactory.getLog(TreeHelperTest.class);

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		props.setProperty("jdbc.name", "XZLLGLA1");
		props.setProperty("jdbc.url", "jdbc:sqlserver://127.0.0.1:1433;databaseName=XZLLGLA1");
		props.setProperty("jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		props.setProperty("jdbc.user", "sa");
		props.setProperty("jdbc.password", "654321");
		props.setProperty("jdbc.type", "sqlserver");
		DBConfiguration.addDataSourceProperties("XZLLGLA1", props);
		QueryHelper helper = new QueryHelper();
		List<TreeModel> treeNodes = new ArrayList<TreeModel>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(props);
			psmt = conn.prepareStatement(" select * from treepinfo ");
			rs = psmt.executeQuery();
			List<Map<String, Object>> list = helper.getResults(rs, 50000);
			for (Map<String, Object> dataMap : list) {
				TreeModel treeNode = new BaseTree();
				treeNode.setId(ParamUtils.getInt(dataMap, "index_id"));
				treeNode.setParentId(ParamUtils.getInt(dataMap, "parent_id"));
				// treeNode.setValue(ParamUtils.getDouble(dataMap, "cell1"));
				treeNodes.add(treeNode);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}

		if (treeNodes.size() > 0) {
			JacksonTreeHelper treeHelper = new JacksonTreeHelper();
			TreeRepository rep = treeHelper.build(treeNodes);
			logger.debug("top size:" + rep.getTopTrees().size());
		}
	}

}
