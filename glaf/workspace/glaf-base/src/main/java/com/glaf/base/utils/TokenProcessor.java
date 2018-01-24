package com.glaf.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;

import sun.misc.BASE64Encoder;

//令牌生产器
public class TokenProcessor {
	private TokenProcessor() {
	}

	private static TokenProcessor instance = new TokenProcessor();

	public static TokenProcessor getInstance() {
		return instance;
	}

	public String generateTokenCode() {
		String value = System.currentTimeMillis() + new Random().nextInt() + "";
		// 获取数据指纹，指纹是唯一的
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] b = md.digest(value.getBytes());// 产生数据的指纹
			// Base64编码
			BASE64Encoder be = new BASE64Encoder();
			be.encode(b);
			return be.encode(b);// 制定一个编码
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取tokenCode对应的链接
	 * 
	 * @return
	 */
	public String generateTokenCodeUrl() {
		String token = generateTokenCode();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		jsonObject.put("tokentime", System.currentTimeMillis());
		CacheFactory.put("qrlogin", token, jsonObject.toJSONString());
		String tokenCodeUrl = "/QR/cf/" + token;
		return tokenCodeUrl;
	}

	/**
	 * 验证token是否有效
	 * 
	 * @param token
	 * @return
	 */
	public boolean checkToken(String token) {
		// 从缓存获取token
		String tokenJSONStr = CacheFactory.getString("qrlogin", token);
		if (StringUtils.isEmpty(tokenJSONStr)) {
			return false;
		}
		// 获取token是否过期
		JSONObject tokenJSON = JSONObject.parseObject(tokenJSONStr);
		long startTime = tokenJSON.getLongValue("tokentime");
		long currTime = System.currentTimeMillis();
		if ((currTime - startTime) > 60 * 1000) {
			CacheFactory.remove("qrlogin", token);
			return false;
		}
		return true;
	}

	/**
	 * 确认登陆
	 * 
	 * @param token
	 * @param status
	 * @return
	 */
	public boolean confirmToken(String token, String actorId, String status) {
		// 从缓存获取token
		String tokenJSONStr = CacheFactory.getString("qrlogin", token);
		if (StringUtils.isEmpty(tokenJSONStr)) {
			return false;
		}
		// 获取token是否过期
		JSONObject tokenJSON = JSONObject.parseObject(tokenJSONStr);
		long startTime = tokenJSON.getLongValue("tokentime");
		long currTime = System.currentTimeMillis();
		if ((currTime - startTime) > 60 * 1000) {
			CacheFactory.remove("qrlogin", token);
			return false;
		}
		// 确认登陆
		tokenJSON.put("status", status);
		if (status.equals("1")) {
			tokenJSON.put("actorId", actorId);
		}
		CacheFactory.put("qrlogin", token, tokenJSON.toJSONString());
		return true;
	}
    /**
     * 移除token
     * @param token
     */
	public void removeToken(String token) {
		CacheFactory.remove("qrlogin", token);
	}

	public static void main(String[] args) {
		TokenProcessor processor = new TokenProcessor();
		processor.generateTokenCode();
	}
}