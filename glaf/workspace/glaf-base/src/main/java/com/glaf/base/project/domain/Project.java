package com.glaf.base.project.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.domain.Database;
import com.glaf.core.util.DateUtils;
import com.glaf.base.project.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJECT_QUERY")
public class Project implements Serializable, JSONable {
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
	 * 名称
	 */
	@Column(name = "NAME_", length = 200)
	protected String name;

	/**
	 * 代码
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * 类别
	 */
	@Column(name = "CATEGORY_", length = 50)
	protected String category;

	/**
	 * 识别码
	 */
	@Column(name = "DISCRIMINATOR_", length = 10)
	protected String discriminator;

	/**
	 * 区域
	 */
	@Column(name = "AREA_", length = 200)
	protected String area;

	/**
	 * 图标
	 */
	@Column(name = "ICON_", length = 50)
	protected String icon;

	/**
	 * 图标样式
	 */
	@Column(name = "ICONCLS_", length = 50)
	protected String iconCls;

	/**
	 * 层级
	 */
	@Column(name = "LEVEL_")
	protected int level;

	/**
	 * 树编号
	 */
	@Column(name = "TREEID_", length = 500)
	protected String treeId;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 100)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 顺序
	 */
	@Column(name = "SORT_")
	protected int sort;

	/**
	 * 从属节点编号
	 */
	@Column(name = "SUBIDS_", length = 500)
	protected String subIds;

	/**
	 * 激活标识
	 */
	@Column(name = "ACTIVE_", length = 1)
	protected String active;

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
	 * 更新人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	@javax.persistence.Transient
	protected Collection<Long> subordinateIds = new HashSet<Long>();

	@javax.persistence.Transient
	protected Collection<String> actorIds = new HashSet<String>();

	@javax.persistence.Transient
	protected List<ProjectAccess> accesses = new ArrayList<ProjectAccess>();

	@javax.persistence.Transient
	protected List<ProjectOwner> owners = new ArrayList<ProjectOwner>();

	@javax.persistence.Transient
	protected List<Database> databases = new ArrayList<Database>();

	public Project() {

	}

	public void addAccessor(String actorId) {
		if (actorIds == null) {
			actorIds = new HashSet<String>();
		}
		actorIds.add(actorId);
	}

	public void addDatabase(Database database) {
		if (databases == null) {
			databases = new ArrayList<Database>();
		}
		databases.add(database);
	}

	public void addOwner(ProjectOwner owner) {
		if (owners == null) {
			owners = new ArrayList<ProjectOwner>();
		}
		owners.add(owner);
	}

	public void addSubordinate(Long subordinateId) {
		if (subordinateIds == null) {
			subordinateIds = new HashSet<Long>();
		}
		subordinateIds.add(subordinateId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<ProjectAccess> getAccesses() {
		return accesses;
	}

	public String getActive() {
		return this.active;
	}

	public Collection<String> getActorIds() {
		return actorIds;
	}

	public String getArea() {
		return area;
	}

	public String getCategory() {
		return category;
	}

	public String getCode() {
		return this.code;
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

	public List<Database> getDatabases() {
		return databases;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public String getIcon() {
		return icon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public long getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return this.name;
	}

	public List<ProjectOwner> getOwners() {
		return owners;
	}

	public long getParentId() {
		return parentId;
	}

	public int getSort() {
		return sort;
	}

	public String getSubIds() {
		return subIds;
	}

	public Collection<Long> getSubordinateIds() {
		return subordinateIds;
	}

	public String getTitle() {
		return this.title;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getType() {
		return type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public Project jsonToObject(JSONObject jsonObject) {
		return ProjectJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccesses(List<ProjectAccess> accesses) {
		this.accesses = accesses;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setActorIds(Collection<String> actorIds) {
		this.actorIds = actorIds;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwners(List<ProjectOwner> owners) {
		this.owners = owners;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setSubIds(String subIds) {
		this.subIds = subIds;
	}

	public void setSubordinateIds(Collection<Long> subordinateIds) {
		this.subordinateIds = subordinateIds;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
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

	public JSONObject toJsonObject() {
		return ProjectJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjectJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
