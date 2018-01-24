package com.glaf.isdp.util;

import java.util.Random;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.security.DigestUtil;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;

public class EncodeUtils {

	private final static String SALT = getSalt();

	private static String getSalt() {
		 return SystemConfig.getToken();
	}

	public static String decode(String value) {
		value = RequestUtils.decodeString(value);// 解码
		value = StringTools.replaceIgnoreCase(value, SALT, "");// 去掉SALT
		value = value.substring(32, value.length());// 去掉定长32为UUID
		return value;
	}

	public static String decode(String salt, String value) {
		String tmp = DigestUtil.digestString(salt, "MD5");
		value = StringTools.replaceIgnoreCase(value, tmp, "");
		value = RequestUtils.decodeString(value);
		return value;
	}

	public static String encode(String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(UUID32.getUUID());
		Random random = new Random();
		int r = Math.abs(random.nextInt(32));
		sb.insert(r, SALT);// 将SALT随机插入到字符串中
		sb.append(value);
		// System.out.println(sb.toString());
		return RequestUtils.encodeString(sb.toString());
	}

	public static String encode(String salt, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(DigestUtil.digestString(salt, "MD5"));
		sb.append(RequestUtils.encodeString(value));
		return sb.toString();
	}

}
