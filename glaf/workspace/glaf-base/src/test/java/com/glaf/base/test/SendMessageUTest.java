package com.glaf.base.test;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.mq.rabbitmq.QueueProducer;

//http://218.94.73.104:8900/glaf/mx/login/doLogin?userId=hxj&clientIP=59.61.184.11&token=7ea2b81a0e494b0fb1bf4ffbc4dc8ad1
public class SendMessageUTest {

	public static void main(String[] args) throws Exception {
		QueueProducer producer = new QueueProducer(TestConstants.QNAME, TestConstants.KEY);
		String xml = "<userdata sysname=\"runsystem\"><server>Data Source=192.168.10.32;Initial Catalog=pMagicFJGS20150323;Integrated Security=False;uid=sa;password=1;</server><aid>3136f4fd-70f8-4c33-a427-a49c009d26e3</aid><flowid></flowid><userid>00技术员</userid><cell_treedot_index>1440</cell_treedot_index><position>131,22,1789,999</position></userdata>";

		String clientIP = "218.94.73.104";
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
			json.put("dbconfig", Base64.encodeBase64(xml.getBytes()));

			producer.sendBytesMessage(json.toJSONString().getBytes());
			System.out.println("Message Number " + i + " sent.");
			System.out.println(json.toJSONString());

		}

		producer.close();
	}

}
