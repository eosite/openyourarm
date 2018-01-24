package com.glaf.dingtalk.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.BaseDataManager;

import jni.FlkWebCrypt;

public class FlkWebCryptUtils {

	protected static final Log logger = LogFactory.getLog(FlkWebCryptUtils.class);

	static {
		System.loadLibrary("webCryptJni");
	}

	static FlkWebCryptUtils flkWebCryptUtils = null;

	private FlkWebCrypt wbcrypt = null;
	// http type;
	public static final int TYPE_HTTP = 0;
	private static final int TYPE_HTTPS = 1;
	// coder type
	public static final int TYPE_CODER_BINARY = 0;
	public static final int TYPE_CODER_HEX = 1;
	public static final int TYPE_CODER_BASE64 = 2;

	FlkWebCryptUtils(String addr, int port, int httptype, String appId, String memberId, String token) {
		if (wbcrypt == null) {
			wbcrypt = new FlkWebCrypt();
			wbcrypt.FLK_InitwebCrype();
			int ret = wbcrypt.FLK_SetKmsAddr(addr, port, httptype);
			if (ret != 0) {
				logger.error("wbcrypt.FLK_SetKmsAddr failed" + ret);
			}
			ret = wbcrypt.FLK_GetKmsKey(appId, memberId, token);
			if (ret != 0) {
				logger.error("wbcrypt.FLK_GetKmsKey failed" + ret);
			}
		}
	}

	FlkWebCryptUtils(FlkWebCrypt wbcrypt) {
		this.wbcrypt = wbcrypt;
	}

	public static FlkWebCryptUtils getInstance() {
		if (flkWebCryptUtils == null) {
			BaseDataManager bdm = BaseDataManager.getInstance();
			String addr = bdm.getBaseData("addr", "flkwebcryp").getValue();
			int port = Integer.parseInt(bdm.getBaseData("port", "flkwebcryp").getValue());
			int httptype = Integer.parseInt(bdm.getBaseData("reqType", "flkwebcryp").getValue());
			String appId = bdm.getBaseData("appId", "flkwebcryp").getValue();
			String memberId = bdm.getBaseData("memberId", "flkwebcryp").getValue();
			String token = bdm.getBaseData("token", "flkwebcryp").getValue();
			flkWebCryptUtils = new FlkWebCryptUtils(addr, port, httptype, appId, memberId, token);
		}
		return flkWebCryptUtils;
	}

	/**
	 * 加密方法
	 * 
	 * @param plain
	 *            明文
	 * @param codeType
	 *            编码类型
	 * @return
	 */
	public String encrypt(String plain, int codeType) {
		String encryptStr = null;
		byte[] plainBt = plain.getBytes();
		int size = 256;
		byte[] cipher = new byte[size];
		int[] cipherLen = new int[1];
		cipherLen[0] = size;
		for (int i = 0; i < size; i++) {
			cipher[i] = 0;
		}
		int ret = wbcrypt.FLK_Sm4CbcEncrypt(plainBt, plainBt.length, cipher, cipherLen, codeType);
		if (ret != 0) {
			logger.error("wbcrypt.FLK_Sm4CbcEncrypt failed" + ret);
			return encryptStr;
		}
		encryptStr = new String(cipher).trim();
		return encryptStr;
	}

	/**
	 * 解密方法
	 * 
	 * @param cipher
	 *            密文
	 * @param codeType
	 *            编码类型
	 * @return
	 */
	public String decrypt(String cipher, int codeType) {
		String plainStr = null;
		byte[] cipherBt = cipher.getBytes();
		int size = 256;
		byte[] tmpplain = new byte[size];
		int[] tmpplainLen = new int[1];
		tmpplainLen[0] = size;
		for (int i = 0; i < size; i++) {
			tmpplain[i] = 0;
		}
		int ret = wbcrypt.FLK_Sm4CbcDecrypt(cipherBt, cipherBt.length, tmpplain, tmpplainLen, codeType);
		if (ret != 0) {
			logger.error("wbcrypt.FLK_Sm4CbcDecrypt failed" + ret);
			return plainStr;
		}
		plainStr = new String(tmpplain).trim();
		return plainStr;
	}

	public static void main(String args[]) {
		FlkWebCryptUtils FlkWebCryptUtils = new FlkWebCryptUtils("cde.qiyemixin.cn", 8080, TYPE_HTTP, "flk_test", "1.2",
				"112");
		String encodeStr = FlkWebCryptUtils.encrypt("埃文斯的范德萨发sdfs123123", TYPE_CODER_BASE64);
		System.out.println(encodeStr);
		String decodeStr = FlkWebCryptUtils.decrypt(encodeStr, TYPE_CODER_BASE64);
		System.out.println(decodeStr);
	}
}
