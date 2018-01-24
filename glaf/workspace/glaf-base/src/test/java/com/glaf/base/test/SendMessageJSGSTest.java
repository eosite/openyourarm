package com.glaf.base.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.mq.rabbitmq.QueueProducer;

//http://218.94.73.104:8900/glaf/mx/login/doLogin?userId=hxj&clientIP=59.61.184.11&token=7ea2b81a0e494b0fb1bf4ffbc4dc8ad1
public class SendMessageJSGSTest {

	public static void main(String[] args) throws Exception {
		String token = "de5ddc7b309e45f8b4005e31d7560428";
		QueueProducer producer = new QueueProducer("", TestConstants.KEY);
		List<String> rows = new ArrayList<String>();
		rows.add("588854f0-b28e-42bd-9118-a40b00f0292c");
		rows.add("ac74e799-9848-42b4-8bf0-a4ad00e9d89b");
		rows.add("1ca49239-cf01-49b5-9262-a43d014f94d7");
		rows.add("2270447e-f55f-4122-b3da-a40d00b285e0");
		rows.add("285ec31a-94d9-4a6a-9108-a41700b0d60c");
		rows.add("3d0dae8e-b4a0-4901-a98a-a418011c702c");
		rows.add("456ab766-db1c-4426-a1f9-a451010d6ef8");
		rows.add("65d4142b-5ee6-49e3-ba5e-a41900a1e51a");
		rows.add("826cd8c0-9367-43f2-b39b-a40d00ad2338");
		rows.add("9a9c5d0a-4204-4f0e-8b6f-a40400f04a1d");
		rows.add("a6483376-159e-48a2-9e40-a41800a62268");
		rows.add("be6e5742-e933-4a01-88e2-a42200b61797");
		rows.add("cdabb59e-8eab-419c-a11a-a43e00b41f73");
		rows.add("d4a0afea-c448-4536-a916-a4190110055c");
		rows.add("e035b7d9-d567-40e0-9752-a42f00f715e1");
		for (int k = 0; k < 15; k++) {
			String aid = rows.get(k % 15);

			String xml = "<userdata sysname=\"runsystem\"><server>Data Source=192.168.10.202;Initial Catalog=paydb1;Integrated Security=False;uid=sa;password=mtxx87668438;</server><aid>"
					+ aid
					+ "</aid><flowid></flowid><userid>00技术员</userid><cell_treedot_index>1440</cell_treedot_index><position>131,22,1789,999</position></userdata>";
			File file = new File("./Config.xml");
			if (file.exists() && file.isFile()) {
				xml = FileUtils.readFileToString(file, "UTF-8");
			}
			String clientIP = TestConstants.HOST;
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
				json.put("token", token);
				json.put("dbconfig", xml);
				// producer.init();
				// producer.getChannel().queueDeclare(TestContants.USER, true, false,
				// false, null);
				producer.sendBytesMessage(Hex.encodeHexString(json.toJSONString().getBytes()).getBytes());
				System.out.println("Message Number " + i + " sent.");
				System.out.println(json.toJSONString());
			}
			Thread.sleep(1000);
		}
		producer.close();
	}

}
