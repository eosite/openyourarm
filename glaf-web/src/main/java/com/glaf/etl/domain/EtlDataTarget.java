package com.glaf.etl.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.etl.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ETL_DATATARGET")
public class EtlDataTarget implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TARGET_ID_", nullable = false)
	protected String targetId_;

	/**
	 * tARGETNAME
	 */
	@Column(name = "TARGET_NAME_", length = 50)
	protected String targetName_;

	/**
	 * cOLUMNS
	 */
	@Column(name = "COLUMNS_")
	protected byte[] columns_;

	/**
	 * dATABASEID
	 */
	@Column(name = "DATABASEID_")
	protected Long databaseId_;

	/**
	 * dATABASENAME
	 */
	@Column(name = "DATABASENAME_", length = 50)
	protected String databaseName_;

	/**
	 * tABLENAME
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName_;

	/**
	 * ROUTTABLERULE
	 */
	@Column(name = "ROUT_TABLE_RULE_")
	protected byte[] routTableRule_;

	/**
	 * cREATEBY
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy_;

	/**
	 * cREATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime_;

	/**
	 * uPDATEBY
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy_;

	/**
	 * uPDATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime_;

	public EtlDataTarget() {

	}

	public String getTargetId_() {
		return this.targetId_;
	}

	public void setTargetId_(String targetId_) {
		this.targetId_ = targetId_;
	}

	public String getTargetName_() {
		return this.targetName_;
	}

	public byte[] getColumns_() {
		return this.columns_;
	}

	public Long getDatabaseId_() {
		return this.databaseId_;
	}

	public String getDatabaseName_() {
		return this.databaseName_;
	}

	public String getTableName_() {
		return this.tableName_;
	}

	public String getCreateBy_() {
		return this.createBy_;
	}

	public Date getCreateTime_() {
		return this.createTime_;
	}

	public String getCreateTime_String() {
		if (this.createTime_ != null) {
			return DateUtils.getDateTime(this.createTime_);
		}
		return "";
	}

	public String getUpdateBy_() {
		return this.updateBy_;
	}

	public Date getUpdateTime_() {
		return this.updateTime_;
	}

	public String getUpdateTime_String() {
		if (this.updateTime_ != null) {
			return DateUtils.getDateTime(this.updateTime_);
		}
		return "";
	}

	public void setTargetName_(String targetName_) {
		this.targetName_ = targetName_;
	}

	public void setColumns_(byte[] columns_) {
		this.columns_ = columns_;
	}

	public void setDatabaseId_(Long databaseId_) {
		this.databaseId_ = databaseId_;
	}

	public void setDatabaseName_(String databaseName_) {
		this.databaseName_ = databaseName_;
	}

	public void setTableName_(String tableName_) {
		this.tableName_ = tableName_;
	}

	public void setCreateBy_(String createBy_) {
		this.createBy_ = createBy_;
	}

	public void setCreateTime_(Date createTime_) {
		this.createTime_ = createTime_;
	}

	public void setUpdateBy_(String updateBy_) {
		this.updateBy_ = updateBy_;
	}

	public void setUpdateTime_(Date updateTime_) {
		this.updateTime_ = updateTime_;
	}

	public byte[] getRoutTableRule_() {
		return routTableRule_;
	}

	public void setRoutTableRule_(byte[] routTableRule_) {
		this.routTableRule_ = routTableRule_;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtlDataTarget other = (EtlDataTarget) obj;
		if (targetId_ == null) {
			if (other.targetId_ != null)
				return false;
		} else if (!targetId_.equals(other.targetId_))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((targetId_ == null) ? 0 : targetId_.hashCode());
		return result;
	}

	public EtlDataTarget jsonToObject(JSONObject jsonObject) {
		return EtlDataTargetJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EtlDataTargetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EtlDataTargetJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
