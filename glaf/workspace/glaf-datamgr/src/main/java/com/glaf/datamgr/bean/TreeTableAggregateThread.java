package com.glaf.datamgr.bean;

import java.util.Map;

import com.glaf.core.domain.Database;
import com.glaf.datamgr.domain.TreeTableAggregate;

public class TreeTableAggregateThread implements java.lang.Runnable {

	protected Database database;

	protected TreeTableAggregate treeTableAggregate;

	protected String splitColumn;

	protected int splitValue;

	protected Map<String, Object> parameter;

	public TreeTableAggregateThread(Database database, TreeTableAggregate treeTableAggregate, String splitColumn,
			int splitValue, Map<String, Object> parameter) {
		this.database = database;
		this.treeTableAggregate = treeTableAggregate;
		this.splitColumn = splitColumn;
		this.splitValue = splitValue;
		this.parameter = parameter;
	}

	@Override
	public void run() {
		TreeTableAggregateBean bean = new TreeTableAggregateBean();
		bean.execute(database, treeTableAggregate, splitColumn, splitValue, parameter);
	}

}
