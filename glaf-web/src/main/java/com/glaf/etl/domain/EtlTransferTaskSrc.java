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
@Table(name = "ETL_TRANSFER_TASK_SRC_")
public class EtlTransferTaskSrc implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id_;

	/**
	 * taskId
	 */
	@Column(name = "TASK_ID_", length = 64)
	protected String taskId_;

	/**
	 * srcId
	 */
	@Column(name = "SRC_ID_", length = 64)
	protected String srcId_;

	/**
	 * preSQL
	 */
	@Column(name = "PRE_SQL_")
	protected byte[] preSQL_;

	/**
	 * postSQL
	 */
	@Column(name = "POST_SQL_")
	protected byte[] postSQL_;

	/**
	 * distinctFlag
	 */
	@Column(name = "DISTINCT_FLAG_")
	protected Integer distinctFlag_;

	/**
	 * querySQL
	 */
	@Column(name = "QUERY_SQL_")
	protected byte[] querySQL_;

	/**
	 * ownerName
	 */
	@Column(name = "OWNER_NAME_", length = 50)
	protected String ownerName_;

	/**
	 * taskConnId
	 */
	@Column(name = "TASK_CONN_ID_")
	protected Long taskConnId_;

	/**
	 * taskDatabaseName
	 */
	@Column(name = "TASK_DATABASENAME_", length = 30)
	protected String taskDatabaseName_;

	/**
	 * orderMapping
	 */
	@Column(name = "ORDER_MAPPING_")
	protected Integer orderMapping_;

	/**
	 * createBy
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy_;

	/**
	 * createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime_;

	/**
	 * updateBy
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy_;

	/**
	 * updateTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime_;

	public EtlTransferTaskSrc() {

	}

	public String getId_() {
		return this.id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getTaskId_() {
		return this.taskId_;
	}

	public String getSrcId_() {
		return this.srcId_;
	}

	public byte[] getPreSQL_() {
		return this.preSQL_;
	}

	public byte[] getPostSQL_() {
		return this.postSQL_;
	}

	public Integer getDistinctFlag_() {
		return this.distinctFlag_;
	}

	public byte[] getQuerySQL_() {
		return this.querySQL_;
	}

	public String getOwnerName_() {
		return this.ownerName_;
	}

	public Long getTaskConnId_() {
		return this.taskConnId_;
	}

	public String getTaskDatabaseName_() {
		return this.taskDatabaseName_;
	}

	public Integer getOrderMapping_() {
		return this.orderMapping_;
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

	public void setTaskId_(String taskId_) {
		this.taskId_ = taskId_;
	}

	public void setSrcId_(String srcId_) {
		this.srcId_ = srcId_;
	}

	public void setPreSQL_(byte[] preSQL_) {
		this.preSQL_ = preSQL_;
	}

	public void setPostSQL_(byte[] postSQL_) {
		this.postSQL_ = postSQL_;
	}

	public void setDistinctFlag_(Integer distinctFlag_) {
		this.distinctFlag_ = distinctFlag_;
	}

	public void setQuerySQL_(byte[] querySQL_) {
		this.querySQL_ = querySQL_;
	}

	public void setOwnerName_(String ownerName_) {
		this.ownerName_ = ownerName_;
	}

	public void setTaskConnId_(Long taskConnId_) {
		this.taskConnId_ = taskConnId_;
	}

	public void setTaskDatabaseName_(String taskDatabaseName_) {
		this.taskDatabaseName_ = taskDatabaseName_;
	}

	public void setOrderMapping_(Integer orderMapping_) {
		this.orderMapping_ = orderMapping_;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtlTransferTaskSrc other = (EtlTransferTaskSrc) obj;
		if (id_ == null) {
			if (other.id_ != null)
				return false;
		} else if (!id_.equals(other.id_))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_ == null) ? 0 : id_.hashCode());
		return result;
	}

	public EtlTransferTaskSrc jsonToObject(JSONObject jsonObject) {
		return EtlTransferTaskSrcJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EtlTransferTaskSrcJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EtlTransferTaskSrcJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
