package com.glaf.core.test;

import org.junit.Test;

public class Base64Test {

	@Test
	public void test() throws Exception {
		String str = net.iharder.Base64.encodeBytes("中文测试ABC".getBytes("UTF-8"));
		System.out.println(str);
		System.out.println("1->" + new String(net.iharder.Base64.decode(str), "UTF-8"));
	}

	@Test
	public void test2() throws Exception {
		String str = net.iharder.Base64.encodeBytes("中文测试ABC".getBytes("UTF-8"));
		System.out.println(str);
		System.out.println("2->" + new String(org.apache.commons.codec.binary.Base64.decodeBase64(str), "UTF-8"));
	}

	@Test
	public void test3() throws Exception {
		String str = org.apache.commons.codec.binary.Base64.encodeBase64String("中文测试ABC".getBytes("UTF-8"));
		System.out.println(str);
		System.out.println("3->" + new String(net.iharder.Base64.decode(str), "UTF-8"));
	}

	@Test
	public void test4() throws Exception {
		String str = org.apache.commons.codec.binary.Base64.encodeBase64String("中文测试ABC".getBytes("UTF-8"));
		System.out.println(str);
		System.out.println("4->" + new String(org.apache.commons.codec.binary.Base64.decodeBase64(str), "UTF-8"));
	}

}
