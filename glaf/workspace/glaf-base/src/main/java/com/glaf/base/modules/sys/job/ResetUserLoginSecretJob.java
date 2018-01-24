/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.modules.sys.job;

import java.io.IOException;

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.RSA;
import com.glaf.core.util.UUID32;
import com.glaf.core.util.http.HttpUtils;

public class ResetUserLoginSecretJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < 600000) {
			return;
		}
		JSONArray result = new JSONArray();
		Connection conn = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(" select UserID, LOGINSECRETUPDATETIME_ from UserInfo where status <> '1' ");
			psmt2 = conn.prepareStatement(
					" update UserInfo set LOGINSECRET_ = ?, LOGINSECRETUPDATETIME_ = ? where UserID = ?  ");
			rs = psmt.executeQuery();
			while (rs.next()) {
				Timestamp t = rs.getTimestamp(2);
				if (t == null || (t != null && (System.currentTimeMillis() - t.getTime() > 7200000))) {
					String userId = rs.getString(1);
					String secret = UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID();
					psmt2.setString(1, secret);
					psmt2.setTimestamp(2, DateUtils.toTimestamp(new java.util.Date()));
					psmt2.setString(3, userId);
					psmt2.addBatch();
					JSONObject json = new JSONObject();
					json.put("userId", userId);
					json.put("secret", secret);
					result.add(json);
				}
			}
			psmt2.executeBatch();
			psmt2.close();
			psmt.close();
			rs.close();
			conn.commit();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(psmt2);
			JdbcUtils.close(conn);
		}

		lastExecuteTime.set(System.currentTimeMillis());
		if (StringUtils.isNotEmpty(SystemConfig.getString("third_login_secret_sync_url"))) {
			String url = SystemConfig.getString("third_login_secret_sync_url");
			String key_url = SystemConfig.getString("third_rsa_publickey_url");
			boolean isSSL = false;
			if (StringUtils.startsWithIgnoreCase(url, "https://")) {
				isSSL = true;
			}
			String text = result.toJSONString();
			if (StringUtils.isNotEmpty(key_url)) {
				String rsa_key = HttpUtils.doGet(key_url);
				byte[] data = null;
				try {
					data = text.getBytes("UTF-8");
					data = RSA.encryptByPublicKey(data, rsa_key);
					text = Base64.encodeBase64String(data);
				} catch (IOException ex) {
				}
			}
			HttpUtils.doRequest(url, "POST", text, isSSL);
		}
	}

}
