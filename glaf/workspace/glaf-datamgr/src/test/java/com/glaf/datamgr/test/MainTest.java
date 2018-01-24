package com.glaf.datamgr.test;

import org.apache.metamodel.query.FromItem;
import org.apache.metamodel.query.Query;

public class MainTest {

	public static void main(String[] args) {
		
		Long id = 12L;
		
		System.out.println(id == 0L);
		
		Query query = new Query(),queryCount = new Query();
		
		query.select("ID_","id").select("NAME_", "name").from("SYS_USER");
		
		
		//queryCount.select(" COUNT(*) total FROM ( " + query.toSql() + " )total");
		
		//FromItem fromItem  = new FromItem(query);
		
		queryCount.from(new FromItem(query).setAlias("t")).selectCount();
		
		System.out.println(queryCount.toSql());
		
		System.out.println(query);
	}
}
