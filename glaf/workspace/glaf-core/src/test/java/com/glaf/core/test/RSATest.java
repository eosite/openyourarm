package com.glaf.core.test;

import java.util.Map;

import com.glaf.core.util.RSA;
import com.glaf.core.util.UUID32;

public class RSATest {

	public static void main(String[] args) {
		String word = "中文" + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID()
				+ UUID32.getUUID();
		String privateKeyServer = "";
		String publicKeyServer = "";

		String privateKeyClient = "";
		String publicKeyClient = "";
		// 生成公钥私钥
		Map<String, Object> map = RSA.init();
		publicKeyServer = RSA.getPublicKey(map);
		privateKeyServer = RSA.getPrivateKey(map);
		System.out.println("公钥: \n" + publicKeyServer);
		System.out.println("私钥： \n" + privateKeyServer);

		Map<String, Object> map2 = RSA.init();
		publicKeyClient = RSA.getPublicKey(map2);
		privateKeyClient = RSA.getPrivateKey(map2);

		System.out.println("client公钥: \n" + publicKeyClient);
		System.out.println("client私钥： \n" + privateKeyClient);

		byte[] encWord = RSA.encryptByPublicKey(word.getBytes(), publicKeyServer);
		String decWord = new String(RSA.decryptByPrivateKey(encWord, privateKeyServer));
		System.out.println("加密前: " + word + "\n解密后: " + decWord);

	}

}
