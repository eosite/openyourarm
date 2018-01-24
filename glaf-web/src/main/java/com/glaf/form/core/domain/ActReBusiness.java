package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ACT_RE_BUSINESS")
public class ActReBusiness implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * BUSTBNAME
	 */
	@Column(name = "BUSTBNAME_", length = 50)
	protected String bustbName;

	/**
	 * BUSTBID
	 */
	@Column(name = "BUSTBID_", length = 30)
	protected String bustbId;

	/**
	 * BUSVALUE
	 */
	@Column(name = "BUSVALUE_", length = 50)
	protected String busValue;

	/**
	 * PROCESSID
	 */
	@Column(name = "PROCESSID_", length = 50)
	protected String processId;

	/**
	 * PROCESSNAME
	 */
	@Column(name = "PROCESSNAME_", length = 200)
	protected String processName;

	/**
	 * PAGEID
	 */
	@Column(name = "PAGEID_", length = 50)
	protected String pageId;

	/**
	 * KEY
	 */
	@Column(name = "KEY_", length = 100)
	protected String key;

	/**
	 * URL
	 */
	@Column(name = "URL_", length = 200)
	protected String url;

	/**
	 * DEFID
	 */
	@Column(name = "DEF_ID_", length = 50)
	protected String defId;

	/**
	 * CREATEBY
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * CREATEDATE
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * UPDATEBY
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * UPDATEDATE
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	public ActReBusiness() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBustbName() {
		return this.bustbName;
	}

	public String getBustbId() {
		return this.bustbId;
	}

	public String getBusValue() {
		return this.busValue;
	}

	public String getProcessId() {
		return this.processId;
	}

	public String getProcessName() {
		return this.processName;
	}

	public String getPageId() {
		return this.pageId;
	}

	public String getKey() {
		return this.key;
	}

	public String getUrl() {
		return this.url;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateDateString() {
		if (this.updateDate != null) {
			return DateUtils.getDateTime(this.updateDate);
		}
		return "";
	}

	public void setBustbName(String bustbName) {
		this.bustbName = bustbName;
	}

	public void setBustbId(String bustbId) {
		this.bustbId = bustbId;
	}

	public void setBusValue(String busValue) {
		this.busValue = busValue;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActReBusiness other = (ActReBusiness) obj;
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

	public ActReBusiness jsonToObject(JSONObject jsonObject) {
		return ActReBusinessJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ActReBusinessJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ActReBusinessJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

}
