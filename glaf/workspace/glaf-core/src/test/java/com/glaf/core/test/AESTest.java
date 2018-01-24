package com.glaf.core.test;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.glaf.core.security.AESUtils;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.util.Hex;
import com.glaf.core.util.UUID32;

public class AESTest {

	public static final String KEY_ALGORITHM = "AES";

	/**
	 * 
	 * 解密 <br/>
	 * 解密过程： <br/>
	 * 1.同加密1-4步 <br/>
	 * 2.将加密后的字符串反纺成byte[]数组 <br/>
	 * 3.将加密内容解密 <br/>
	 */
	public static byte[] aesDecode(String password, byte[] data) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
			// 2.根据password规则初始化密钥生成器
			// 根据传入的字节数组,生成一个256位的随机源
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");// 加解密保持同个随机种子
			random.setSeed(password.getBytes());
			keygen.init(256, random);
			// keygen.init(256, new SecureRandom(password.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, KEY_ALGORITHM);
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM, "BC");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密
			byte[] byte_decode = cipher.doFinal(data);
			return byte_decode;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密 <br/>
	 * 1.构造密钥生成器 <br/>
	 * 2.根据password规则初始化密钥生成器 <br/>
	 * 3.产生密钥 <br/>
	 * 4.创建和初始化密码器 <br/>
	 * 5.内容加密 <br/>
	 */
	public static byte[] aesEncode(String password, String data) {
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
			// 2.根据password规则初始化密钥生成器
			// 根据传入的字节数组,生成一个256位的随机源
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");// 加解密保持同个随机种子
			random.setSeed(password.getBytes());
			keygen.init(256, random);
			// keygen.init(256, new SecureRandom(password.getBytes()));
			// 3.产生原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte[] raw = original_key.getEncoded();
			// 5.根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, KEY_ALGORITHM);
			// 6.根据指定算法AES自成密码器
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM, "BC");
			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 8.获取加密内容的字节数组(这里要设置为UTF-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] byte_encode = data.getBytes("UTF-8");
			// 9.根据密码器的初始化方式--加密：将数据加密
			byte[] byte_aes = cipher.doFinal(byte_encode);
			return byte_aes;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		// byte[] key = AESUtils.initkey();
		System.out.println(org.apache.commons.codec.digest.DigestUtils.md5Hex(UUID32.getUUID()).length());
		String password = UUID32.getUUID();
		byte[] key = org.apache.commons.codec.digest.DigestUtils.md5Hex(password).getBytes();// 32个字符即256位
		System.out.println("key:" + new String(key));
		System.out.println("key length:" + key.length);
		String data = "GLAF基础应用开发平台";
		byte[] encryptData = AESUtils.encrypt(password, data.getBytes());
		System.out.println("加密后：" + new String(encryptData));
		encryptData = AESUtils.decrypt(password, encryptData);
		System.out.println("解密后：" + new String(encryptData));

		password = SecurityUtils.genKey().substring(0, 256);
		System.out.println("password:" + password);
		System.out.println("password length:" + password.length());
		// 加密
		byte[] encryptResult = AESUtils.encrypt(password, data.getBytes());
		// 解密
		byte[] decryptResult = AESUtils.decrypt(password, encryptResult);
		System.out.println("解密后：" + new String(decryptResult));
		String ikey = "xYz123aBc567ddfdfdySadfFG";
		byte[] b = AESUtils.encode(ikey.getBytes(), data.getBytes());
		System.out.println(Hex.byte2hex(b));
		System.out.println("解密后：" + new String(AESUtils.decode(ikey.getBytes(), b)));
		byte[] bytes = aesEncode(password, data);
		String tmp = Hex.byte2hex(bytes);
		System.out.println(tmp);
		byte[] bytes2 = Hex.hex2byte(tmp);
		System.out.println("解密后：" + new String(aesDecode(password, bytes2)));
	}

}
