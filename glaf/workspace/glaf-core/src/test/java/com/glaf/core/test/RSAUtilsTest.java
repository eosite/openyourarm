package com.glaf.core.test;

import com.glaf.test.AbstractTest;

import org.junit.Test;

import com.glaf.core.security.*;
import com.glaf.core.util.UUID32;

public class RSAUtilsTest extends AbstractTest {

	@Test
	public void test0() {
		long start = System.currentTimeMillis();
		String str = null;
		for (int i = 0; i < 2; i++) {
			str = RSAUtils.encryptString(UUID32.getUUID());
			if (i % 100 == 0) {
				System.out.println(str);
			}
		}
		long ts = System.currentTimeMillis() - start;
		System.out.println("加解密用时:" + ts);
	}

	@Test
	public void test1() {
		String str = RSAUtils.encryptString("中文测试AbC!@#$");
		System.out.println(str);
		System.out.println(RSAUtils.decryptString(str));
	}

	@Test
	public void test2() {
		long start = System.currentTimeMillis();
		String str = null;
		for (int i = 0; i < 10000; i++) {
			str = RSAUtils.encryptString(UUID32.getUUID());
			if (i % 1000 == 0) {
				System.out.println(RSAUtils.decryptString(str));
			}
		}
		long ts = System.currentTimeMillis() - start;
		System.out.println("加解密用时:" + ts);
	}

}
