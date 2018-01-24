package com.glaf.base.project.domain;

import java.io.*;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJECT_VIEW")
public class ProjectView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "_UID_", length = 50)
	protected String uid;

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
	 * 类别
	 */
	@Column(name = "CATEGORY_", length = 50)
	protected String category;

	/**
	 * 代码
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

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
	 * 激活标识
	 */
	@Column(name = "ACTIVE_", length = 1)
	protected String active;

	/**
	 * 用户名
	 */
	@Column(name = "USERID_", length = 50)
	protected String userId;

	/**
	 * 用户姓名
	 */
	@Column(name = "USERNAME_", length = 200)
	protected String userName;

	/**
	 * 机构名称
	 */
	@Column(name = "ORGNAME_", length = 200)
	protected String orgName;

	public ProjectView() {

	}

	public String getActive() {
		return active;
	}

	public String getArea() {
		return area;
	}

	public String getCategory() {
		return category;
	}

	public String getCode() {
		return code;
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
		return name;
	}

	public String getOrgName() {
		return orgName;
	}

	public long getParentId() {
		return parentId;
	}

	public int getSort() {
		return sort;
	}

	public String getTitle() {
		return title;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getType() {
		return type;
	}

	public String getUid() {
		return uid;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setActive(String active) {
		this.active = active;
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

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setSort(int sort) {
		this.sort = sort;
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

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
