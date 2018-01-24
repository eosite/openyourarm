package com.glaf.base.test;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.mq.rabbitmq.PublishProducer;
import com.glaf.core.util.UUID32;

public class SendMessageTest5 {

	public static void main(String[] args) throws Exception {
		PublishProducer producer = new PublishProducer(TestConstants.QNAME, "LoginQueue");
		while (true) {
			String token = UUID32.getUUID();
		
			String clientIP = "221.13.60.10";
			String userId = "anquanyuan";
			String username = TestConstants.USER;
			String password = TestConstants.PWD;

			producer.setHost(TestConstants.HOST);
			producer.setPort(5672);
			producer.setUsername(username);
			producer.setPassword(password);

			JSONObject json = new JSONObject();
			json.put("clientIP", clientIP);
			// json.put("section", "12");
			json.put("userId", userId);
			json.put("timeLive", 480);
			json.put("loginTime", System.currentTimeMillis());
			json.put("token", token);
			// json.put("dbconfig", xml);
			// producer.init();
			// producer.getChannel().queueDeclare(TestContants.USER, true,
			// false,
			// false, null);
			// producer.sendBytesMessage(Hex.encodeHexString(json.toJSONString().getBytes()).getBytes());
			// producer.sendBytesMessage(json.toJSONString().getBytes());
			producer.sendBytesMessage("fanout", json.toJSONString().getBytes());
			producer.close();
			System.out.println("Message sent.");
			System.out.println(json.toJSONString());

			String str = "http://zygs.fzmt.com.cn:18007/glaf/website/mqlogin?userId=" + json.getString("userId")
					+ "&clientIP=" + json.getString("clientIP") + "&token=" + json.getString("token");
			System.out.println(str);
			Thread.sleep(5000);
			// Runtime.getRuntime().exec(" C:\\Program Files\\Internet
			// Explorer\\iexplore.exe " + str);
		}
	}

}
