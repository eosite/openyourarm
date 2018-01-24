package com.glaf.base.modules.website.springmvc;

import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;

@Controller("/server_status")
@RequestMapping("/server_status")
public class ServerStatusController {

	protected static AtomicBoolean running = new AtomicBoolean(false);

	@ResponseBody
	@RequestMapping
	public byte[] status(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		JSONObject json = new JSONObject();
		Connection conn = null;
		if (!running.get()) {
			try {
				running.set(true);
				long start = System.currentTimeMillis();
				conn = DBConnectionFactory.getConnection();
				if (conn != null) {
					json.put("db_status", "SUCCESS");
				} else {
					json.put("db_status", "FAILURE");
				}
				long ts = System.currentTimeMillis() - start;
				json.put("db_connection_ts", ts);
				Thread.sleep(500);
			} catch (Exception ex) {
				json.put("db_status", "FAILURE");
			} finally {
				JdbcUtils.close(conn);
				running.set(false);
			}
		}
		return json.toJSONString().getBytes("UTF-8");
	}

}
