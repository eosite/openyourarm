package com.glaf.core.test;

import org.apache.commons.codec.binary.Base64;

import com.glaf.core.domain.SysKey;
import com.glaf.core.security.DESUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;

public class SecurityTest {

	public static void main(String[] args) {
		String token = "b4dc5d76ea0f42f48be3027cbee127c7";
		byte[] secretKey = "b7efcf45-9ff9-44bc-b397-".getBytes();
		byte[] secretIv = "12481632".getBytes();
		System.out.println("secretKey:" + new String(secretKey));
		System.out.println("secretIv:" + new String(secretIv));

		// SysKeyService sysKeyService = (SysKeyService)
		// ContextFactory.getBean("sysKeyService");
		// SysKey sysKey = sysKeyService.getSysKey("RSAKey");
		// FileUtils.save("./key.rsa", sysKey.getData());
		SysKey sysKey = new SysKey();
		sysKey.setData(FileUtils.getBytes("./key.rsa"));
		// 先通过RSA密锁加密密码
		String str = new String(DESUtils.encode("111111sa", sysKey.getData()));
		System.out.println("->RSA加密后密码:" + str);
		// 再用对方给的DES密锁加密用户名及密码
		String loginId = Base64.encodeBase64String(DESUtils.ecrypt3DES("admin".getBytes(), secretKey, secretIv));
		String password = Base64.encodeBase64String(DESUtils.ecrypt3DES(str.getBytes(), secretKey, secretIv));
		System.out.println("loginId:" + loginId);
		System.out.println("password:" + password);
		String userId = loginId;
		String pwd = password;

		loginId = StringTools.replace(loginId, "+", "_a");
		loginId = StringTools.replace(loginId, "=", "_b");
		loginId = StringTools.replace(loginId, "/", "_c");

		password = StringTools.replace(password, "+", "_a");
		password = StringTools.replace(password, "=", "_b");
		password = StringTools.replace(password, "/", "_c");

		System.out.println("token:" + token);
		System.out.println("#loginId:" + loginId);
		System.out.println("#password:" + password);

		StringBuffer sb = new StringBuffer();
		sb.append("http://zygs.fzmt.com.cn:18002/glaf/website/applogin?token=").append(token).append("&loginId=")
				.append(loginId).append("&passwd=").append(password);
		System.out.println(sb.toString());

		String x = new String(DESUtils.decrypt3DES(userId.getBytes(), "b7efcf45-9ff9-44bc-b397-", "12481632"));
		String y = new String(DESUtils.decrypt3DES(pwd.getBytes(), "b7efcf45-9ff9-44bc-b397-", "12481632"));
		System.out.println("->DES解密后密码:" + y);
		y = new String(DESUtils.decode(y, sysKey.getData()));
		System.out.println("->RSA解密后密码:" + y);
		System.out.println(x + "=" + y);

		try {
			Thread.sleep(200);
			Runtime.getRuntime().exec(" C:\\Program Files\\Internet Explorer\\iexplore.exe " + sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
