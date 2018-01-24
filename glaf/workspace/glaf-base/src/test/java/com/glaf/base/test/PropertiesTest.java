package com.glaf.base.test;

import java.io.IOException;
import java.util.*;

import com.glaf.core.util.FileUtils;
import com.glaf.core.util.PropertiesUtils;

public class PropertiesTest {

	public static void main(String[] args) throws IOException {
		Properties props = PropertiesUtils.loadFilePathResource("./conf/props/ApplicationStrings.properties");
		// props.load(FileUtils.getInputStream("./conf/props/ApplicationStrings.properties"));
		java.util.Enumeration<?> e = props.propertyNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement().toString();
			System.out.println(name + "=" + props.getProperty(name));
		}

		System.out.println("-----------------------------------------------");
		Map<String, String> dataMap = PropertiesUtils
				.load(FileUtils.getInputStream("./conf/props/ApplicationStrings.properties"));
		Iterator<String> iterator = dataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			System.out.println(name + "=" + dataMap.get(name));
		}
	}
}
