package com.glaf.datamgr.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.datamgr.jdbc.MyBatisHelper;
import com.glaf.datamgr.tree.TreeNode;
import com.glaf.datamgr.tree.TreeNodeSumBuilder;

public class TreeNodeSumTest {
	protected static Log logger = LogFactory.getLog(TreeNodeSumTest.class);

	public static void main(String[] args) throws Exception {
		System.out.println( Integer.parseInt("1234.0"));
		Properties props = new Properties();
		props.setProperty("jdbc.name", "XZLLGLA1");
		props.setProperty("jdbc.url", "jdbc:sqlserver://127.0.0.1:1433;databaseName=XZLLGLA1");
		props.setProperty("jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		props.setProperty("jdbc.user", "sa");
		props.setProperty("jdbc.password", "654321");
		props.setProperty("jdbc.type", "sqlserver");
		DBConfiguration.addDataSourceProperties("XZLLGLA1", props);

		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		MyBatisHelper helper = new MyBatisHelper();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection(props);
			psmt = conn.prepareStatement(" select * from treepinfo where cell1 is not null order by index_id asc ");
			rs = psmt.executeQuery();
			List<Map<String, Object>> list = helper.getResults(rs);
			for (Map<String, Object> dataMap : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(ParamUtils.getInt(dataMap, "index_id"));
				treeNode.setParentId(ParamUtils.getInt(dataMap, "parent_id"));
				treeNode.setValue(ParamUtils.getDouble(dataMap, "cell1"));
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
			logger.info("treeNodes size:" + treeNodes.size());
			TreeNodeSumBuilder builder = new TreeNodeSumBuilder();
			// TreeNodeRepository rep = builder.buildTree(treeNodes);
			// logger.debug("top size:" + rep.getTopTrees().size());
			builder.sum(treeNodes);
			for (int i = 0; i < 200; i++) {
				logger.debug("" + treeNodes.get(i));
				logger.debug("->parent:" + treeNodes.get(i).getParent());
			}

			try {
				conn = DBConnectionFactory.getConnection(props);
				conn.setAutoCommit(false);
				List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(conn, "treepinfo");
				ColumnDefinition col = new ColumnDefinition();
				col.setColumnName("cell1_sum");
				col.setJavaType("Double");
				columns.add(col);
				TableDefinition table = new TableDefinition();
				table.setTableName("treepinfo");
				table.setColumns(columns);
				DBUtils.alterTable(conn, table);
				int index = 0;
				psmt = conn.prepareStatement("update treepinfo set cell1_sum = ? where index_id = ? ");
				for (int i = 0, len = treeNodes.size(); i < len; i++) {
					index++;
					TreeNode treeNode = treeNodes.get(i);
					psmt.setDouble(1, treeNode.getSumValue());
					psmt.setLong(2, treeNode.getId());
					psmt.addBatch();
					if (index % 1000 == 0) {
						psmt.executeBatch();
						psmt.clearBatch();
					}
				}
				psmt.executeBatch();
				conn.commit();
				logger.debug("execute update ok.");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				JdbcUtils.close(psmt);
				JdbcUtils.close(conn);
			}
		}
	}

}
