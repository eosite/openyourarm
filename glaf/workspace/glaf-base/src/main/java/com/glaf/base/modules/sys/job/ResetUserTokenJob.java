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

import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;

public class ResetUserTokenJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < 600000) {
			return;
		}
		Connection conn = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(" select UserID, TOKENTIME from UserInfo where status <> '1' ");
			psmt2 = conn.prepareStatement(" update UserInfo set TOKEN = ?, TOKENTIME = ? where UserID = ?  ");
			rs = psmt.executeQuery();
			while (rs.next()) {
				Timestamp t = rs.getTimestamp(2);
				if (t != null && (System.currentTimeMillis() - t.getTime() > 7200000)) {
					psmt2.setString(1, UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID());
					psmt2.setTimestamp(2, DateUtils.toTimestamp(new java.util.Date()));
					psmt2.setString(3, rs.getString(1));
					psmt2.addBatch();
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
	}

}
