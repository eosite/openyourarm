package com.glaf.core.test;

import com.glaf.core.security.AESUtils;

public class AESTest2 {

	public static void main(String[] args) throws Exception {
		byte[] key = AESUtils.initkey();
		byte[] data = "测试AES中文加密".getBytes("UTF-8");
		byte[] bytes = AESUtils.encryptECB(key, data);
		System.out.println(new String(AESUtils.decryptECB(key, bytes), "UTF-8"));
	}
}
