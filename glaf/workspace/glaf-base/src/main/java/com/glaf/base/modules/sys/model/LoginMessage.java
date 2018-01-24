package com.glaf.base.modules.sys.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_LOGIN_INFO")
public class LoginMessage implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "TOKEN_", length = 200, nullable = false)
	protected String token;

	/**
	 * 服务编号，关联登录服务器端配置如MQ信息或客户端配置
	 */
	@Column(name = "SERVERID_")
	protected Long serverId;

	/**
	 * 用户编号
	 */
	@Column(name = "USERID_", length = 50, nullable = false)
	protected String userId;

	/**
	 * 客户端编号
	 */
	@Column(name = "CLIENTIP_", length = 200, nullable = false)
	protected String clientIP;

	/**
	 * 标段
	 */
	@Column(name = "SECTION_", length = 50)
	protected String section;

	/**
	 * 内容
	 */
	@Column(name = "CONTENT_", length = 2000)
	protected String content;

	/**
	 * 界面ID
	 */
	@Column(name = "UID_", length = 500)
	protected String uid;

	@Column(name = "FLOWID_", length = 200)
	protected String flowid;

	@Column(name = "CELLTREEDOTINDEX_", length = 50)
	protected String cellTreedotIndex;

	@Column(name = "POSITION_", length = 50)
	protected String position;

	/**
	 * 有效时间（单位为分钟）
	 */
	@Column(name = "TIMELIVE_")
	protected int timeLive;

	/**
	 * 登录时间
	 */
	@Column(name = "LOGINTIME_")
	protected long loginTime;

	/**
	 * 登录年月日
	 */
	@Column(name = "DAY_")
	protected int day;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public LoginMessage() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginMessage other = (LoginMessage) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public String getCellTreedotIndex() {
		return cellTreedotIndex;
	}

	public String getClientIP() {
		return this.clientIP;
	}

	public String getContent() {
		return this.content;
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

	public int getDay() {
		return day;
	}

	public String getFlowid() {
		return flowid;
	}

	public long getLoginTime() {
		return this.loginTime;
	}

	public String getPosition() {
		return position;
	}

	public String getSection() {
		return this.section;
	}

	public Long getServerId() {
		return serverId;
	}

	public int getTimeLive() {
		return this.timeLive;
	}

	public String getToken() {
		return this.token;
	}

	public String getUid() {
		return uid;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	public LoginMessage jsonToObject(JSONObject jsonObject) {
		return LoginMessageJsonFactory.jsonToObject(jsonObject);
	}

	public void setCellTreedotIndex(String cellTreedotIndex) {
		this.cellTreedotIndex = cellTreedotIndex;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public void setTimeLive(int timeLive) {
		this.timeLive = timeLive;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return LoginMessageJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return LoginMessageJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
