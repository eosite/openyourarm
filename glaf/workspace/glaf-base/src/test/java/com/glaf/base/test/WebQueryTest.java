package com.glaf.base.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class WebQueryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(md5("mtcs001").toUpperCase());
		System.out.println(DigestUtils.md5Hex("mtcs001").toUpperCase());
		// System.out.println(WebQueryTest.doGet("http://www.chinaiss.com"));
		String sAuthSeed = WebQueryTest.doGet("http://192.168.10.148:81/GNRemote.dll?GNFunction=GetAuthSeed");
		System.out.println(sAuthSeed);

		String sSeed = String.format("mtxx87668438%s", sAuthSeed);
		String sMd5 = WebQueryTest.md5(sSeed);
		sMd5 = sMd5.toLowerCase();

		String sUrl = String.format(
				"http://192.168.10.148:81/GNRemote.dll?GNFunction=ChangeUser&UserName=mtcs002&Password=mtcs002&NewPassword=mtcs002&AuthCode=%s",
				sMd5);

		System.out.println(WebQueryTest.doGet(sUrl));
	}

	public static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String doGet(String sUrl) {
		String sResult = "";
		try {
			BasicCookieStore cookieStore = new BasicCookieStore();
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			try {
				HttpGet httpGet = new HttpGet(sUrl);
				CloseableHttpResponse response1 = httpclient.execute(httpGet);
				try {
					HttpEntity entity = response1.getEntity();
					if (entity != null) {
						InputStream instream = entity.getContent();
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(instream));
							StringBuffer buffer = new StringBuffer();
							String line = "";
							while ((line = in.readLine()) != null) {
								// System.out.print(line);
								buffer.append(line);
							}
							// System.out.println("");
							sResult = buffer.toString();
						} catch (IOException ex) {
							throw ex;
						} finally {
							instream.close();
						}
					}
				} finally {
					response1.close();
				}
			} finally {
				httpclient.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sResult;
	}

}