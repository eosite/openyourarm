package com.glaf.base.modules.sys.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.DictoryJsonFactory;

/**
 * 
 * @author Dane.Dong
 * @since 2015-06-24
 * @version 1.0
 */
public class DictoryTree extends Dictory implements Serializable {

	private static final long serialVersionUID = 2621764861467919301L;
	protected long dictoryTreeId;
	protected String treeCode;
	protected String treeDesc;
	protected String icon;
	protected String iconCls;
	protected int locked;
	protected String treeName;
	protected long parentId;
	protected int treeSort;
	protected String treeId;
	protected String url;
	protected boolean leafFlag;

	public long getDictoryTreeId() {
		return dictoryTreeId;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public String getTreeDesc() {
		return treeDesc;
	}

	public void setTreeDesc(String treeDesc) {
		this.treeDesc = treeDesc;
	}

	public String getIcon() {
		return icon;
	}

	public String getIconCls() {
		return iconCls;
	}

	public int getLocked() {
		return locked;
	}

	public String getTreeName() {
		return treeName;
	}

	public long getParentId() {
		return parentId;
	}

	public int getTreeSort() {
		return treeSort;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getUrl() {
		return url;
	}

	public void setDictoryTreeId(long dictoryTreeId) {
		this.dictoryTreeId = dictoryTreeId;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setTreeSort(int treeSort) {
		this.treeSort = treeSort;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(boolean leafFlag) {
		this.leafFlag = leafFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictoryTree other = (DictoryTree) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public JSONObject toJsonObject() {
		return DictoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DictoryJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
