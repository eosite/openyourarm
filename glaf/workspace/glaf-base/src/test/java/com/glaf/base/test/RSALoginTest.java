package com.glaf.base.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.glaf.core.util.Hex;
import com.glaf.core.util.RSA;

public class RSALoginTest {

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

	public static void main(String[] args) throws Exception {
		String url = "http://zygs.fzmt.com.cn:18054/glaf/website/rsa_pwd_login/getPublicKey?systemCode=SYS_18054";
		String key = doGet(url + "&userId=test");
		System.out.println(key);
		String userId = Hex.byte2hex(RSA.encryptByPublicKey("test".getBytes(), key));
		String pwd = Hex.byte2hex(RSA.encryptByPublicKey("111111".getBytes(), key));
		String str = "http://zygs.fzmt.com.cn:18054/glaf/website/rsa_pwd_login?systemCode=SYS_18054&userId=" + userId
				+ "&password=" + pwd;
		System.out.println(str);
		Runtime.getRuntime().exec(" C:\\Program Files\\Internet Explorer\\iexplore.exe " + str);
	}

}
