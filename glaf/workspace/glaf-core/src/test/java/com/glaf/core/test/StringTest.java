package com.glaf.core.test;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.glaf.core.util.ExpressionConstants;

public class StringTest {

	@Test
	public void testSplits() {
		StringTokenizer token = new StringTokenizer("127.0.0.1:6379,192.168.10.125:6479", ",");
		while (token.hasMoreTokens()) {
			String item = token.nextToken();
			if (StringUtils.contains(item, ":")) {
				String h = item.substring(0, item.indexOf(":"));
				int p = Integer.parseInt(item.substring(item.indexOf(":") + 1, item.length()));
				System.out.println(h + ":" + p);
			}
		}
	}

	@Test
	public void test() {
		String expr = "#{id:myKey}";
		String name = expr.substring(ExpressionConstants.ID_PREFIX_EXPRESSION.length(), expr.length() - 1);
		System.out.println(name);
	}

}
