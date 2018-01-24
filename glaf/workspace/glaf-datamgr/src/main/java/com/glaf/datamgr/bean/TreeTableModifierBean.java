package com.glaf.datamgr.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.tree.helper.TreeUpdateBean;
import com.glaf.core.util.DBUtils;
import com.glaf.datamgr.domain.TreeTableModifier;
import com.glaf.datamgr.service.TreeTableModifierService;

public class TreeTableModifierBean {

	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected long databaseId;

	protected long tableId;

	public TreeTableModifierBean() {

	}

	public TreeTableModifierBean(long databaseId, long tableId) {
		this.databaseId = databaseId;
		this.tableId = tableId;
	}

	public void execute(long databaseId, long tableId) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TreeTableModifierService treeTableModifierService = ContextFactory.getBean("com.glaf.datamgr.service.treeTableModifierService");
		try {
			Database database = null;
			if (databaseId > 0) {
				database = databaseService.getDatabaseById(databaseId);
				TreeTableModifier tbl = treeTableModifierService.getTreeTableModifier(tableId);
				List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

				ColumnDefinition col6 = new ColumnDefinition();
				col6.setColumnName(tbl.getTreeIdColumn());
				col6.setJavaType("String");
				col6.setLength(500);
				columns.add(col6);

				if (StringUtils.isNotEmpty(tbl.getLevelColumn())) {
					ColumnDefinition col7 = new ColumnDefinition();
					col7.setColumnName(tbl.getLevelColumn());
					col7.setJavaType("Integer");
					columns.add(col7);
				}

				DBUtils.alterTable(database.getName(), tbl.getTableName(), columns);

				TreeUpdateBean bean = new TreeUpdateBean();
				bean.updateTreeIds(database.getName(), tbl.getTableName(), tbl.getPrimaryKey(), tbl.getIdColumn(),
						tbl.getParentIdColumn(), tbl.getTreeIdColumn(), tbl.getLevelColumn(), null);
				logger.debug(database.getTitle() + "执行成功！");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		}
	}

}
