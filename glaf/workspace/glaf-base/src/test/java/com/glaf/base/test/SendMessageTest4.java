package com.glaf.base.test;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.mq.rabbitmq.PublishProducer;

public class SendMessageTest4 {

	public static void main(String[] args) throws Exception {
		PublishProducer producer = new PublishProducer(TestConstants.QNAME);

		String clientIP = "221.13.61.86";
		String userId = "joy";
		String username = TestConstants.USER;
		String password = TestConstants.PWD;
		if (args != null && args.length >= 1) {
			clientIP = args[0];
			System.out.println("clientIP:" + clientIP);
		}
		if (args != null && args.length >= 2) {
			userId = args[1];
			System.out.println("userId:" + userId);
		}
		if (args != null && args.length >= 3) {
			username = args[2];
			System.out.println("username:" + userId);
		}
		if (args != null && args.length >= 4) {
			password = args[3];
			System.out.println("password:" + password);
		}
		producer.setHost(TestConstants.HOST);
		producer.setPort(5672);
		producer.setUsername(username);
		producer.setPassword(password);
		for (int i = 0; i < 10; i++) {
			JSONObject json = new JSONObject();
			json.put("clientIP", clientIP);
			// json.put("section", "12");
			json.put("userId", userId);
			json.put("timeLive", 480);
			json.put("loginTime", System.currentTimeMillis());
			json.put("token", "tk_" + System.currentTimeMillis());
			json.put("title", "登录测试2");
			String str = "http://zygs.fzmt.com.cn:18002/glaf/website/token_login?userId=" + json.getString("userId")
					+ "&token=" + json.getString("token");
			producer.sendMessage("fanout", json.toJSONString());
			System.out.println("Message Number " + i + " sent.");
			System.out.println(json.toJSONString());
			System.out.println(str);
			Thread.sleep(5000);
			//Runtime.getRuntime().exec("C:\\Users\\root\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe " + str);
			 Runtime.getRuntime().exec(" C:\\Program Files\\Internet Explorer\\iexplore.exe " + str);
		}

		producer.closeConnection();
	}

}
