package com.glaf.core.test;

import org.junit.Test;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.id.IdGenerator;

public class IdGenTest {

	public void test() {
		IdGenerator idGenerator = ContextFactory.getBean("idGenerator");
		for (int i = 0; i < 200; i++) {
			System.out.println(idGenerator.nextId());
		}
	}

	@Test
	public void testName() {
		IdGenerator idGenerator = ContextFactory.getBean("idGenerator");
		for (int i = 0; i < 2000; i++) {
			System.out.println(i + "->" + idGenerator.nextId("test"));
			
		}
	}

}
