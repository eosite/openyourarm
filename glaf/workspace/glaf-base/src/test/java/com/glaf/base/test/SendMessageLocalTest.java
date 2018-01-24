package com.glaf.base.test;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.mq.rabbitmq.QueueProducer;

//http://218.94.73.104:8900/glaf/mx/login/doLogin?userId=hxj&clientIP=59.61.184.11&token=7ea2b81a0e494b0fb1bf4ffbc4dc8ad1
public class SendMessageLocalTest {

	public static void main(String[] args) throws Exception {
		QueueProducer producer = new QueueProducer(TestConstants.QNAME);

		String clientIP = "127.0.0.1";
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
		for (int i = 0; i < 1; i++) {
			JSONObject json = new JSONObject();
			json.put("clientIP", clientIP);
			// json.put("section", "12");
			json.put("userId", userId);
			json.put("timeLive", 480);
			json.put("loginTime", System.currentTimeMillis());
			json.put("token", "tk_" + System.currentTimeMillis());

			producer.sendMessage(json.toJSONString());
			System.out.println("Message Number " + i + " sent.");
			System.out.println(json.toJSONString());
			String str = "http://127.0.0.1:8080/glaf/mx/login/doLogin?userId=" + json.getString("userId") + "&clientIP="
					+ json.getString("clientIP") + "&token=" + json.getString("token");
			System.out.println(str);
			Thread.sleep(2000);
			Runtime.getRuntime().exec(" C:\\Program Files\\Internet Explorer\\iexplore.exe " + str);
		}

		producer.close();
	}

}
