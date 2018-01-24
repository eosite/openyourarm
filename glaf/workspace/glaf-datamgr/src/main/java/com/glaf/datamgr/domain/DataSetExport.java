package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_DATASET_EXPORT")
public class DataSetExport implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 20)
	protected String type;

	/**
	 * 导出库名
	 */
	@Column(name = "EXPORTDBNAME_", length = 50)
	protected String exportDBName;

	/**
	 * 最后导出标记
	 */
	@Column(name = "LASTEXPORTSTATUS_")
	protected int lastExportStatus;

	/**
	 * 最后导出时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTEXPORTTIME_")
	protected Date lastExportTime;

	/**
	 * 服务标识
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 交换用户
	 */
	@Column(name = "USERID_", length = 50)
	protected String userId;

	/**
	 * 数据集编号
	 */
	@Lob
	@Column(name = "DATASETIDS_", length = 4000)
	protected String datasetIds;

	/**
	 * 是否调度
	 */
	@Column(name = "SCHEDULEFLAG_", length = 1)
	protected String scheduleFlag;

	/**
	 * 每次抓取前删除
	 */
	@Column(name = "DELETEFETCH_", length = 1)
	protected String deleteFetch;

	/**
	 * 是否公开
	 */
	@Column(name = "PUBLICFLAG_", length = 1)
	protected String publicFlag;

	/**
	 * 是否需要建表语句
	 */
	@Column(name = "CREATETABLEFLAG_", length = 1)
	protected String createTableFlag;

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
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public DataSetExport() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSetExport other = (DataSetExport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getCreateTableFlag() {
		return this.createTableFlag;
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

	public String getDatasetIds() {
		return this.datasetIds;
	}

	public String getDeleteFetch() {
		return this.deleteFetch;
	}

	public String getExportDBName() {
		return this.exportDBName;
	}

	public String getId() {
		return this.id;
	}

	public int getLastExportStatus() {
		return this.lastExportStatus;
	}

	public Date getLastExportTime() {
		return this.lastExportTime;
	}

	public String getLastExportTimeString() {
		if (this.lastExportTime != null) {
			return DateUtils.getDateTime(this.lastExportTime);
		}
		return "";
	}

	public int getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public String getPublicFlag() {
		return this.publicFlag;
	}

	public String getScheduleFlag() {
		return this.scheduleFlag;
	}

	public String getServiceKey() {
		return this.serviceKey;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
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

	public String getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DataSetExport jsonToObject(JSONObject jsonObject) {
		return DataSetExportJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTableFlag(String createTableFlag) {
		this.createTableFlag = createTableFlag;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatasetIds(String datasetIds) {
		this.datasetIds = datasetIds;
	}

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setExportDBName(String exportDBName) {
		this.exportDBName = exportDBName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastExportStatus(int lastExportStatus) {
		this.lastExportStatus = lastExportStatus;
	}

	public void setLastExportTime(Date lastExportTime) {
		this.lastExportTime = lastExportTime;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return DataSetExportJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataSetExportJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
