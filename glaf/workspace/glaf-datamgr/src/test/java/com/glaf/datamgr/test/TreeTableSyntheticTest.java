package com.glaf.datamgr.test;

import com.glaf.core.factory.DatabaseFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.datamgr.bean.TreeTableSyntheticBean;

public class TreeTableSyntheticTest {

	public static void main(String[] args) {
		if (DBConnectionFactory.checkConnection()) {
			DatabaseFactory.getInstance().reload();
		}
		TreeTableSyntheticBean bean = new TreeTableSyntheticBean();
		bean.execute(1224, 19, 301);
	}
}
