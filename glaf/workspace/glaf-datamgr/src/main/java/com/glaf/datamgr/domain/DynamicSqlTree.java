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
@Table(name = "SYS_DYNAMIC_SQL_TREE")
public class DynamicSqlTree implements Serializable, JSONable, TreeModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 父编号
	 */
	@Column(name = "PARENTID_")
	protected long parentId;

	/**
	 * 租户编号
	 */
	@Column(name = "TENANTID_", length = 50)
	protected String tenantId;

	/**
	 * 标题
	 */
	@Column(name = "NAME_", length = 200)
	protected String name;

	/**
	 * 模块编号
	 */
	@Column(name = "MODULEID_", length = 50)
	protected String moduleId;

	/**
	 * 业务编号
	 */
	@Column(name = "BUSINESSKEY_", length = 200)
	protected String businessKey;

	/**
	 * 列名
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * 列类型
	 */
	@Column(name = "COLUMNTYPE_", length = 50)
	protected String columnType;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 表别名
	 */
	@Column(name = "TABLEALIAS_", length = 50)
	protected String tableAlias;

	/**
	 * 参数名称
	 */
	@Column(name = "PARAMNAME_", length = 50)
	protected String paramName;

	/**
	 * 参数标题
	 */
	@Column(name = "PARAMTITLE_", length = 50)
	protected String paramTitle;

	/**
	 * 是否集合
	 */
	@Column(name = "COLLECTIONFLAG_", length = 1)
	protected String collectionFlag;

	/**
	 * 条件
	 */
	@Column(name = "CONDITION_", length = 50)
	protected String condition;

	/**
	 * 是否必须 如果必须但没有值会抛出参数错误异常
	 */
	@Column(name = "REQUIREDFLAG_", length = 1)
	protected String requiredFlag;

	/**
	 * 运算符
	 */
	@Column(name = "OPERATOR_", length = 50)
	protected String operator;

	/**
	 * 参数分隔符 多个参数值的分隔符，默认用半角的逗号,隔开
	 */
	@Column(name = "SEPARATOR_", length = 50)
	protected String separator;

	/**
	 * SQL片段 如果默认的列组装条件不能满足时可以自行写SQL
	 */
	@Column(name = "SQL_", length = 4000)
	protected String sql;

	/**
	 * 树编号
	 */
	@Column(name = "TREEID_", length = 500)
	protected String treeId;

	/**
	 * 树层级
	 */
	@Column(name = "LEVEL_")
	protected int level;

	/**
	 * 顺序
	 */
	@Column(name = "SORTNO_")
	protected int sortNo;

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

	@javax.persistence.Transient
	protected TreeModel parent;

	@javax.persistence.Transient
	protected Map<String, Object> dataMap;

	@javax.persistence.Transient
	protected List<TreeModel> children = new java.util.ArrayList<TreeModel>();

	public DynamicSqlTree() {

	}

	public void addChild(TreeModel treeModel) {
		if (children == null) {
			children = new java.util.ArrayList<TreeModel>();
		}
		children.add(treeModel);
	}

	@Override
	public int compareTo(TreeModel o) {
		if (o == null) {
			return -1;
		}

		TreeModel obj = o;

		int l = this.sortNo - obj.getSortNo();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DynamicSqlTree other = (DynamicSqlTree) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public List<TreeModel> getChildren() {
		return children;
	}

	public String getCode() {
		return null;
	}

	public String getCollectionFlag() {
		return this.collectionFlag;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public String getCondition() {
		return this.condition;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return createTime;
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

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public String getDescription() {
		return null;
	}

	public String getDiscriminator() {
		return null;
	}

	public String getIcon() {
		return null;
	}

	public String getIconCls() {
		return null;
	}

	public long getId() {
		return this.id;
	}

	public int getLevel() {
		return this.level;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public String getName() {
		return this.name;
	}

	public String getOperator() {
		return operator;
	}

	public String getParamName() {
		return this.paramName;
	}

	public String getParamTitle() {
		return this.paramTitle;
	}

	public TreeModel getParent() {
		return parent;
	}

	public long getParentId() {
		return this.parentId;
	}

	public String getRequiredFlag() {
		return requiredFlag;
	}

	public String getSeparator() {
		return this.separator;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public String getSql() {
		return this.sql;
	}

	public String getTableAlias() {
		return this.tableAlias;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public String getTreeId() {
		return this.treeId;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return updateTime;
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
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public boolean isChecked() {
		return false;
	}

	public DynamicSqlTree jsonToObject(JSONObject jsonObject) {
		return DynamicSqlTreeJsonFactory.jsonToObject(jsonObject);
	}

	public void removeChild(TreeModel treeModel) {
		if (children != null) {
			children.remove(treeModel);
		}
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setChecked(boolean checked) {

	}

	public void setChildren(List<TreeModel> children) {
		this.children = children;

	}

	public void setCode(String code) {

	}

	public void setCollectionFlag(String collectionFlag) {
		this.collectionFlag = collectionFlag;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createTime = createDate;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public void setDescription(String description) {

	}

	public void setDiscriminator(String discriminator) {

	}

	public void setIcon(String icon) {

	}

	public void setIconCls(String iconCls) {

	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setParamTitle(String paramTitle) {
		this.paramTitle = paramTitle;
	}

	public void setParent(TreeModel parent) {
		this.parent = parent;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setRequiredFlag(String requiredFlag) {
		this.requiredFlag = requiredFlag;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateTime = updateDate;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {

	}

	public JSONObject toJsonObject() {
		return DynamicSqlTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DynamicSqlTreeJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
