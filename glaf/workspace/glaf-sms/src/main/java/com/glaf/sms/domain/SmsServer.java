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

package com.glaf.sms.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.sms.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SMS_SERVER")
public class SmsServer implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 主题
	 */
	@Column(name = "SUBJECT_", length = 200)
	protected String subject;

	/**
	 * SMS短信服务器IP地址
	 */
	@Column(name = "SERVERIP_", length = 200)
	protected String serverIP;

	/**
	 * 端口
	 */
	@Column(name = "PORT_")
	protected int port;

	/**
	 * 路径
	 */
	@Column(name = "PATH_", length = 500)
	protected String path;

	/**
	 * 钥匙
	 */
	@Column(name = "KEY_", length = 500)
	protected String key;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public SmsServer() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getServerIP() {
		return this.serverIP;
	}

	public int getPort() {
		return this.port;
	}

	public String getPath() {
		return this.path;
	}

	public String getKey() {
		return this.key;
	}

	public String getType() {
		return this.type;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmsServer other = (SmsServer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SmsServer jsonToObject(JSONObject jsonObject) {
		return SmsServerJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SmsServerJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SmsServerJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
