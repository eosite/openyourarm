package com.glaf.teim.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "EIM_SERVER_DATA_IMP")
public class EimServerDataImp implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * nAME
	 */
	@Column(name = "NAME_", length = 150)
	protected String name;

	/**
	 * aPPID
	 */
	@Column(name = "APP_ID_", length = 50)
	protected String appId;

	/**
	 * tMPID
	 */
	@Column(name = "TMP_ID_", length = 50)
	protected String tmpId;

	/**
	 * eMPTYTABLE
	 */
	@Column(name = "EMPTY_TABLE_")
	protected Integer emptyTable;

	/**
	 * pRESQL
	 */
	@javax.persistence.Lob
	@Column(name = "PRE_SQL_")
	protected String preSql;

	/**
	 * iNCREMENTFLAG
	 */
	@Column(name = "INCREMENT_FLAG_")
	protected Integer incrementFlag;

	/**
	 * pARAMS
	 */
	@javax.persistence.Lob
	@Column(name = "PARAMS_")
	protected String params;

	/**
	 * tARGETDATABASE
	 */
	@Column(name = "TARGET_DATABASE_", length = 50)
	protected String targetDatabase;

	/**
	 * tARGETTABLE
	 */
	@Column(name = "TARGET_TABLE_", length = 50)
	protected String targetTable;
	/**
	 * columnMapping
	 */
	@javax.persistence.Lob
	@Column(name = "COLUMN_MAPPING_")
	protected String columnMapping;
	/**
	 * cREATEBY
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * cREATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * uPDATEBY
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * uPDATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * dELETEFLAG
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag;

	public EimServerDataImp() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getAppId() {
		return this.appId;
	}

	public String getTmpId() {
		return this.tmpId;
	}

	public Integer getEmptyTable() {
		return this.emptyTable;
	}

	public String getPreSql() {
		return this.preSql;
	}

	public Integer getIncrementFlag() {
		return this.incrementFlag;
	}

	public String getParams() {
		return this.params;
	}

	public String getTargetDatabase() {
		return this.targetDatabase;
	}

	public String getTargetTable() {
		return this.targetTable;
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

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public void setEmptyTable(Integer emptyTable) {
		this.emptyTable = emptyTable;
	}

	public void setPreSql(String preSql) {
		this.preSql = preSql;
	}

	public void setIncrementFlag(Integer incrementFlag) {
		this.incrementFlag = incrementFlag;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getColumnMapping() {
		return columnMapping;
	}

	public void setColumnMapping(String columnMapping) {
		this.columnMapping = columnMapping;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EimServerDataImp other = (EimServerDataImp) obj;
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

	public EimServerDataImp jsonToObject(JSONObject jsonObject) {
		return EimServerDataImpJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EimServerDataImpJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EimServerDataImpJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
