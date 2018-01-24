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
@Table(name = "SYS_FIELD_SET")
public class FieldSet implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 数据集编号
	 */
	@Column(name = "DATASETID_")
	protected Long datasetId;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 代码
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * 表名
	 */
	@Column(name = "FIELDTABLE_", length = 50)
	protected String fieldTable;

	/**
	 * 中文表名
	 */
	@Column(name = "TABLENAMECN_", length = 100)
	protected String tableNameCN;

	/**
	 * 列名
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * 列宽
	 */
	@Column(name = "COLUMNWIDTH_", length = 50)
	protected String columnWidth;

	/**
	 * 标题
	 */
	@Column(name = "TEXT_", length = 200)
	protected String text;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * 字段编号
	 */
	@Column(name = "FIELDID_", length = 50)
	protected String fieldId;

	/**
	 * 字段长度
	 */
	@Column(name = "FIELDLENGTH_")
	protected int fieldLength;

	/**
	 * 字段类型
	 */
	@Column(name = "FIELDTYPE_", length = 20)
	protected String fieldType;

	/**
	 * 列表显示
	 */
	@Column(name = "ISSHOWLIST_", length = 10)
	protected String isShowList;

	/**
	 * 显示提示
	 */
	@Column(name = "ISSHOWTOOLTIP_", length = 10)
	protected String isShowTooltip;

	/**
	 * 启用编辑
	 */
	@Column(name = "ISEDITOR_", length = 10)
	protected String isEditor;

	/**
	 * 编辑器
	 */
	@Column(name = "EDITOR_", length = 50)
	protected String editor;

	/**
	 * 状态
	 */
	@Column(name = "STATE_", length = 50)
	protected String state;

	/**
	 * 选中
	 */
	@Column(name = "CHECKED_", length = 10)
	protected String checked;

	/**
	 * 对齐
	 */
	@Column(name = "ALIGNMENT_", length = 10)
	protected String alignment;

	/**
	 * domId
	 */
	@Column(name = "DOMID_", length = 50)
	protected String domId;

	/**
	 * target
	 */
	@Column(name = "TARGET_", length = 50)
	protected String target;

	/**
	 * url
	 */
	@Column(name = "URL_", length = 500)
	protected String url;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

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

	public FieldSet() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldSet other = (FieldSet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAlignment() {
		return this.alignment;
	}

	public String getChecked() {
		return this.checked;
	}

	public String getCode() {
		return this.code;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getColumnWidth() {
		return this.columnWidth;
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

	public Long getDatasetId() {
		return this.datasetId;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getDescription() {
		return this.description;
	}

	public String getDomId() {
		return this.domId;
	}

	public String getEditor() {
		return this.editor;
	}

	public String getFieldId() {
		return this.fieldId;
	}

	public int getFieldLength() {
		return this.fieldLength;
	}

	public String getFieldTable() {
		return this.fieldTable;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public String getId() {
		return this.id;
	}

	public String getIsEditor() {
		return this.isEditor;
	}

	public String getIsShowList() {
		return this.isShowList;
	}

	public String getIsShowTooltip() {
		return this.isShowTooltip;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public String getState() {
		return this.state;
	}

	public String getTableNameCN() {
		return this.tableNameCN;
	}

	public String getTarget() {
		return this.target;
	}

	public String getText() {
		return this.text;
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

	public String getUrl() {
		return this.url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FieldSet jsonToObject(JSONObject jsonObject) {
		return FieldSetJsonFactory.jsonToObject(jsonObject);
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDomId(String domId) {
		this.domId = domId;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}

	public void setFieldTable(String fieldTable) {
		this.fieldTable = fieldTable;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsEditor(String isEditor) {
		this.isEditor = isEditor;
	}

	public void setIsShowList(String isShowList) {
		this.isShowList = isShowList;
	}

	public void setIsShowTooltip(String isShowTooltip) {
		this.isShowTooltip = isShowTooltip;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTableNameCN(String tableNameCN) {
		this.tableNameCN = tableNameCN;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setText(String text) {
		this.text = text;
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

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJsonObject() {
		return FieldSetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FieldSetJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
