package com.glaf.core.test;

import java.util.List;

import org.junit.Test;

import com.glaf.core.config.PropertyHelper;
import com.glaf.core.domain.SystemProperty;

public class PropertyTest {

	@Test
	public void test() {
		for (int i = 0; i < 100; i++) {
			PropertyHelper propertyHelper = new PropertyHelper();
			List<SystemProperty> list = propertyHelper.getAllSystemProperties();
			System.out.println(list);
		}
	}
}
