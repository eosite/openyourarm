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

package com.glaf.datamgr.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.GZIPUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.util.TableModelReader;

public class SqliteXmlPackage {

	public void saveSqliteToDB(String userId, String sqliteDB, String type, int action, List<String> tables) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(
				"insert into email_send(id, [From], [To], [CC], Date, Subject, Text, intflag, email, intaction, intOperat) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		int index = 1;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			StringBuffer content = this.toXml(userId, sqliteDB, type, tables);
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(buffer.toString());
			psmt.setString(index++, UUID32.getUUID());
			psmt.setString(index++, userId);
			psmt.setString(index++, userId);
			psmt.setString(index++, userId);
			psmt.setTimestamp(index++, DateUtils.toTimestamp(new Date()));
			psmt.setString(index++, sqliteDB + "(" + DateUtils.getDate(new Date()) + ")交换文件");
			psmt.setString(index++, content.toString());
			psmt.setInt(index++, 0);
			psmt.setString(index++, userId);
			psmt.setInt(index++, action);
			psmt.setInt(index++, 0);
			psmt.executeUpdate();
			conn.commit();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
	}

	public StringBuffer toXml(String userId, String sqliteDB, String type, List<String> tables) {
		String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
		byte[] bytes = FileUtils.getBytes(dbpath);
		byte[] gzipBytes = GZIPUtils.zip(bytes);
		TableModelReader reader = new TableModelReader();
		Document doc = reader.mergeExportMapping(tables);

		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<文件交换信息>");
		buffer.append("  <送出单位>");
		buffer.append("    <电子邮箱>" + userId + "</电子邮箱>");
		buffer.append("    <邮件ID>{" + UUID32.getUUID() + "}</邮件ID>");
		buffer.append("  </送出单位>");
		buffer.append("  <接收单位>");
		buffer.append("    <电子邮箱>" + userId + "</电子邮箱>");
		buffer.append("  </接收单位>");
		buffer.append("  <邮件作用 类型=\"" + type + "\">");
		buffer.append("    <类型说明>文件数据</类型说明>");
		buffer.append("    <文档类型>无</文档类型>");
		buffer.append("    <回复邮件ID>{" + UUID32.getUUID() + "}</回复邮件ID>");
		buffer.append("  </邮件作用>");
		buffer.append("  <附件列表>");
		buffer.append("    <附件>");
		buffer.append("      <文件名>" + sqliteDB + "</文件名>");
		buffer.append("      <文件类型>sqlite</文件类型>");
		buffer.append("      <压缩方式>gzip</压缩方式>");
		buffer.append("      " + doc.getRootElement().asXML());
		buffer.append("      <MD5>" + DigestUtils.md5Hex(bytes) + "</MD5>");
		buffer.append("      <内容>").append(Base64.encodeBase64String(gzipBytes)).append("</内容>");
		buffer.append("    </附件>");
		buffer.append("  </附件列表>");
		buffer.append("</文件交换信息>");
		return buffer;
	}

}
