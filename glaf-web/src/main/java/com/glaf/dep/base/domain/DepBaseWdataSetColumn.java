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
@Table(name = "DEP_BASE_WDATASET_COLUMN_")
public class DepBaseWdataSetColumn implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 更新数据集唯一标识
	 */
	@Column(name = "WDATASET_ID_")
	protected Long wdataSetId;

	/**
	 * 字段名称
	 */
	@Column(name = "COLUMN_NAME_", length = 50)
	protected String columnName;

	/**
	 * 物理字段名
	 */
	@Column(name = "DATACOLUMN_NAME_", length = 50)
	protected String dataColumnName;

	/**
	 * 默认值
	 */
	@Column(name = "DEFAULTVAL_", length = 150)
	protected String defaultVal;

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

	public DepBaseWdataSetColumn() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWdataSetId() {
		return this.wdataSetId;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getDataColumnName() {
		return this.dataColumnName;
	}

	public String getDefaultVal() {
		return this.defaultVal;
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

	public void setWdataSetId(Long wdataSetId) {
		this.wdataSetId = wdataSetId;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setDataColumnName(String dataColumnName) {
		this.dataColumnName = dataColumnName;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseWdataSetColumn other = (DepBaseWdataSetColumn) obj;
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

	public DepBaseWdataSetColumn jsonToObject(JSONObject jsonObject) {
		return DepBaseWdataSetColumnJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBaseWdataSetColumnJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBaseWdataSetColumnJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
