package com.glaf.dep.base.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_BASE_WDATASET")
public class DepBaseWdataSet implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 数据集代码
	 */
	@Column(name = "DATASET_CODE_", length = 50)
	protected String dataSetCode;

	/**
	 * 数据集名称
	 */
	@Column(name = "DATASET_NAME_", length = 50)
	protected String dataSetName;

	/**
	 * 数据集描述
	 */
	@Column(name = "DATASET_DESC_", length = 150)
	protected String dataSetDesc;

	/**
	 * 数据集规则JSON
	 */
	@Lob
	@Column(name = "RULEJSON_")
	protected String ruleJson;

	/**
	 * 数据表名
	 */
	@Column(name = "TABLE_NAME_", length = 50)
	protected String tableName;

	/**
	 * 数据表物理表名
	 */
	@Column(name = "DATATABLE_NAME_", length = 30)
	protected String dataTableName;

	/**
	 * 新增/更新
	 */
	@Column(name = "WTYPE_", length = 1)
	protected String wtype;

	/**
	 * 版本号
	 */
	@Column(name = "VER_")
	protected Integer ver;

	/**
	 * 创建人
	 */
	@Column(name = "CREATOR_", length = 20)
	protected String creator;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDatetime;

	/**
	 * 修改人
	 */
	@Column(name = "MODIFIER_", length = 20)
	protected String modifier;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDatetime;

	/**
	 * 删除标识
	 */
	@Column(name = "DELFLAG_", length = 1)
	protected String delFlag;

	@Column(name = "NODEID_", length = 10)
	protected String nodeId;

	public DepBaseWdataSet() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataSetCode() {
		return this.dataSetCode;
	}

	public String getDataSetName() {
		return this.dataSetName;
	}

	public String getDataSetDesc() {
		return this.dataSetDesc;
	}

	public String getRuleJson() {
		return this.ruleJson;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getDataTableName() {
		return this.dataTableName;
	}

	public String getWtype() {
		return this.wtype;
	}

	public Integer getVer() {
		return this.ver;
	}

	public String getCreator() {
		return this.creator;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public String getCreateDatetimeString() {
		if (this.createDatetime != null) {
			return DateUtils.getDateTime(this.createDatetime);
		}
		return "";
	}

	public String getModifier() {
		return this.modifier;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public String getModifyDatetimeString() {
		if (this.modifyDatetime != null) {
			return DateUtils.getDateTime(this.modifyDatetime);
		}
		return "";
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDataSetCode(String dataSetCode) {
		this.dataSetCode = dataSetCode;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	public void setDataSetDesc(String dataSetDesc) {
		this.dataSetDesc = dataSetDesc;
	}

	public void setRuleJson(String ruleJson) {
		this.ruleJson = ruleJson;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setWtype(String wtype) {
		this.wtype = wtype;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseWdataSet other = (DepBaseWdataSet) obj;
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

	public DepBaseWdataSet jsonToObject(JSONObject jsonObject) {
		return DepBaseWdataSetJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBaseWdataSetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBaseWdataSetJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

}
