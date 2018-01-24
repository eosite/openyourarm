package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.FlowProcessDJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FLOW_PROCESS_D")
public class FlowProcessD implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "MAIN_ID", length = 50)
	protected String mainId;

	@Column(name = "FILEID", length = 50)
	protected String fileId;

	@Column(name = "NAME", length = 200)
	protected String name;

	@Column(name = "CONTENT", length = 100)
	protected String content;

	@Column(name = "ACTOR", length = 50)
	protected String actor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "VERSION", length = 50)
	protected String version;

	@Lob
	@Column(name = "TCADFILE")
	protected String tcadFile;

	@Column(name = "ISSAVE")
	protected Integer isSave;

	@Column(name = "INTFLAG")
	protected Integer intFlag;

	public FlowProcessD() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainId() {
		return this.mainId;
	}

	public String getFileId() {
		return this.fileId;
	}

	public String getName() {
		return this.name;
	}

	public String getContent() {
		return this.content;
	}

	public String getActor() {
		return this.actor;
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

	public String getVersion() {
		return this.version;
	}

	public String getTcadFile() {
		return this.tcadFile;
	}

	public Integer getIsSave() {
		return this.isSave;
	}

	public Integer getIntFlag() {
		return this.intFlag;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setTcadFile(String tcadFile) {
		this.tcadFile = tcadFile;
	}

	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}

	public void setIntFlag(Integer intFlag) {
		this.intFlag = intFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowProcessD other = (FlowProcessD) obj;
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

	public FlowProcessD jsonToObject(JSONObject jsonObject) {
		return FlowProcessDJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FlowProcessDJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FlowProcessDJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
