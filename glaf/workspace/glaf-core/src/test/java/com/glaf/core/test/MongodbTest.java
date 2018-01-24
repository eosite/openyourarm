package com.glaf.core.test;

import org.junit.Test;

import com.glaf.core.factory.MongodbFactory;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;

public class MongodbTest {

	@Test
	public void testPutGet() {
		System.setProperty("config.path", ".");
		for (int i = 0; i < 1000; i++) {
			MongodbFactory.getInstance().put("test", "xx" + i, UUID32.getUUID());
			System.out.println(MongodbFactory.getInstance().getString("test", "xx" + i));
		}
	}

	@Test
	public void testSmallFile() {
		System.setProperty("config.path", ".");
		byte[] bytes = FileUtils.getBytes("E:/stg/download/ERP/oracle_grant_all.txt");
		MongodbFactory.getInstance().put("tmp", "oracle_grant_all.txt", bytes);
		byte[] tmp = (byte[]) MongodbFactory.getInstance().getBytes("tmp", "oracle_grant_all.txt");
		FileUtils.save("C:/temp/oracle_grant_all.txt", tmp);
	}

	@Test
	public void testBigFile() {
		System.setProperty("config.path", ".");
		byte[] bytes = FileUtils.getBytes("E:/stg/download/ERP/openbravo-3.0PR15Q3.2.tar.bz2");
		MongodbFactory.getInstance().put("tmp", "openbravo-3.0PR15Q3.2.tar.bz2", bytes);
		byte[] tmp = (byte[]) MongodbFactory.getInstance().getObject("tmp", "openbravo-3.0PR15Q3.2.tar.bz2");
		FileUtils.save("C:/temp/1.tar.bz2", tmp);
	}

}
