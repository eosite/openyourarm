package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.ProjMuiProjListJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJ_MUIPROJLIST")
public class ProjMuiProjList implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INDEX_ID", nullable = false)
	protected Integer indexId;

	@Column(name = "ID", length = 100)
	protected String id;

	@Column(name = "INTFLAG")
	protected Integer intFlag;

	@Column(name = "SYS_ID", length = 50)
	protected String sysId;

	@Column(name = "PROJNAME", length = 250)
	protected String projName;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "CONTENT", length = 200)
	protected String content;

	@Column(name = "SDBNAME", length = 100)
	protected String dbName;

	@Column(name = "SSERVERNAME", length = 150)
	protected String serverName;

	@Column(name = "SUSER", length = 100)
	protected String user;

	@Column(name = "SPASSWORD", length = 50)
	protected String password;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "EMAIL", length = 100)
	protected String email;

	@Column(name = "PARENT_ID")
	protected Integer parentId;

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "INTLINE")
	protected Integer intLine;

	@Column(name = "DOMAIN_INDEX")
	protected Integer domainIndex;

	@Column(name = "INTLOCAL")
	protected Integer intLocal;

	@Column(name = "EMAIL_PSW", length = 50)
	protected String emailPSW;

	@Column(name = "INTCONNECTED")
	protected Integer intConnected;

	@Column(name = "EMAIL_S", length = 100)
	protected String emails;

	@Column(name = "INTORGLEVEL")
	protected Integer intorgLevel;

	@Column(name = "INTSENDTYPE")
	protected Integer intSendType;

	@Column(name = "EMAIL_BACKUP", length = 50)
	protected String emailBackup;

	@Column(name = "EMAIL_IMPLEMENT", length = 50)
	protected String emailImplement;

	public ProjMuiProjList() {

	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public String getId() {
		return this.id;
	}

	public Integer getIntFlag() {
		return this.intFlag;
	}

	public String getSysId() {
		return this.sysId;
	}

	public String getProjName() {
		return this.projName;
	}

	public String getNum() {
		return this.num;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeString() {
		if (this.ctime != null) {
			return DateUtils.getDateTime(this.ctime);
		}
		return "";
	}

	public String getContent() {
		return this.content;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getServerName() {
		return this.serverName;
	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public Integer getIntLine() {
		return this.intLine;
	}

	public Integer getDomainIndex() {
		return this.domainIndex;
	}

	public Integer getIntLocal() {
		return this.intLocal;
	}

	public String getEmailPSW() {
		return this.emailPSW;
	}

	public Integer getIntConnected() {
		return this.intConnected;
	}

	public String getEmails() {
		return this.emails;
	}

	public Integer getIntorgLevel() {
		return this.intorgLevel;
	}

	public Integer getIntSendType() {
		return this.intSendType;
	}

	public String getEmailBackup() {
		return this.emailBackup;
	}

	public String getEmailImplement() {
		return this.emailImplement;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIntFlag(Integer intFlag) {
		this.intFlag = intFlag;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setIntLine(Integer intLine) {
		this.intLine = intLine;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setIntLocal(Integer intLocal) {
		this.intLocal = intLocal;
	}

	public void setEmailPSW(String emailPSW) {
		this.emailPSW = emailPSW;
	}

	public void setIntConnected(Integer intConnected) {
		this.intConnected = intConnected;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public void setIntorgLevel(Integer intorgLevel) {
		this.intorgLevel = intorgLevel;
	}

	public void setIntSendType(Integer intSendType) {
		this.intSendType = intSendType;
	}

	public void setEmailBackup(String emailBackup) {
		this.emailBackup = emailBackup;
	}

	public void setEmailImplement(String emailImplement) {
		this.emailImplement = emailImplement;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjMuiProjList other = (ProjMuiProjList) obj;
		if (indexId == null) {
			if (other.indexId != null)
				return false;
		} else if (!indexId.equals(other.indexId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indexId == null) ? 0 : indexId.hashCode());
		return result;
	}

	public ProjMuiProjList jsonToObject(JSONObject jsonObject) {
		return ProjMuiProjListJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ProjMuiProjListJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjMuiProjListJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
