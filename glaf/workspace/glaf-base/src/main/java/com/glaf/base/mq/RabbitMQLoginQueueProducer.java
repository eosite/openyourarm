package com.glaf.base.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.mq.rabbitmq.QueueProducer;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;

public class RabbitMQLoginQueueProducer extends QueueProducer {

	public RabbitMQLoginQueueProducer(String endPointName) throws IOException, TimeoutException {
		super(endPointName);
	}

	public JSONObject buildMessage(String token, String userId, String loginIP) {
		JSONObject json = new JSONObject();
		json.put("token", token);
		json.put("clientIP", loginIP);
		json.put("userId", userId);
		json.put("timeLive", 480);
		json.put("loginTime", System.currentTimeMillis());
		return json;
	}

	public JSONObject buildMessage(String token, String userId, String loginIP, int timeLive) {
		JSONObject json = new JSONObject();
		json.put("token", token);
		json.put("clientIP", loginIP);
		json.put("userId", userId);
		json.put("timeLive", timeLive);
		json.put("loginTime", System.currentTimeMillis());
		return json;
	}

	public JSONObject buildMessage(String token, String userId, String loginIP, String uid, long databaseId) {
		JSONObject json = new JSONObject();
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(databaseId);
		if (database != null) {
			String password = SecurityUtils.decode(database.getKey(), database.getPassword());
			StringBuilder buffer = new StringBuilder();
			buffer.append("<userdata sysname=\"runsystem\"><server>Data Source=").append(database.getHost())
					.append(";Initial Catalog=").append(database.getDbname()).append(";Integrated Security=False;uid=")
					.append(database.getUser()).append(";password=").append(password).append(";</server><aid>")
					.append(uid).append("</aid>");
			if (StringUtils.isNotEmpty(database.getLoginAs())) {
				buffer.append("<userid>").append(database.getLoginAs()).append("</userid>");
			} else {
				buffer.append("<userid>").append(userId).append("</userid>");
			}
			buffer.append("</userdata>");
			json.put("dbconfig", buffer.toString());
		}

		if (database != null && StringUtils.isNotEmpty(database.getLoginAs())) {
			json.put("userId", database.getLoginAs());
		} else {
			json.put("userId", userId);
		}

		json.put("token", token);
		json.put("clientIP", loginIP);
		json.put("timeLive", 480);
		json.put("loginTime", System.currentTimeMillis());
		return json;
	}

	public JSONObject buildIsdpMessage(String token, String userId, String loginIP, String flowid,
			String cellTreedotIndex, String position, long databaseId) {
		JSONObject json = new JSONObject();
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(databaseId);
		if (database != null) {
			String password = SecurityUtils.decode(database.getKey(), database.getPassword());
			StringBuilder buffer = new StringBuilder();
			buffer.append("<userdata sysname=\"runsystem\"><server>Data Source=").append(database.getHost())
					.append(";Initial Catalog=").append(database.getDbname()).append(";Integrated Security=False;uid=")
					.append(database.getUser()).append(";password=").append(password).append(";</server>");
			buffer.append("<aid></aid>");
			if (flowid != null) {
				buffer.append("<flowid>").append(flowid).append("</flowid>");
			}
			if (StringUtils.isNotEmpty(database.getInfoServer())) {
				buffer.append("<einfo_server>").append(database.getInfoServer()).append("</einfo_server>");
			}
			if (cellTreedotIndex != null) {
				if (StringUtils.equals(cellTreedotIndex, "-10000")) {
					cellTreedotIndex = "-1";// 任务列表界面，这里是特别指定
				}
				buffer.append("<cell_treedot_index>").append(cellTreedotIndex).append("</cell_treedot_index>");
			}
			if (position != null) {
				buffer.append("<position>").append(position).append("</position>");
			}
			if (StringUtils.isNotEmpty(database.getLoginAs())) {
				buffer.append("<userid>").append(database.getLoginAs()).append("</userid>");
			} else {
				buffer.append("<userid>").append(userId).append("</userid>");
			}
			buffer.append("</userdata>");
			json.put("dbconfig", buffer.toString());
		}
		json.put("token", token);
		json.put("clientIP", loginIP);
		if (database != null && StringUtils.isNotEmpty(database.getLoginAs())) {
			json.put("userId", database.getLoginAs());
		} else {
			json.put("userId", userId);
		}
		json.put("timeLive", 480);
		json.put("loginTime", System.currentTimeMillis());
		return json;
	}

}
