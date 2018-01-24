package com.glaf.datamgr.domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SysSynchronization")
public class SynchronizationRule implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INTERFACEID", length = 500, nullable = false)
	protected String id;

	/**
	 * 名称
	 */
	@Lob
	@Column(name = "ACTIONNAME", length = 4000)
	protected String actionName;

	/**
	 * 公式
	 */
	@Lob
	@Column(name = "FORMULAS", length = 4000)
	protected String formulas;

	/**
	 * 目标表
	 */
	@Lob
	@Column(name = "TARGETTABLE", length = 4000)
	protected String targetTable;

	/**
	 * 来源表
	 */
	@Lob
	@Column(name = "SOUCETABLE", length = 4000)
	protected String souceTable;

	/**
	 * Wbs表
	 */
	@Lob
	@Column(name = "WBSTABLE", length = 4000)
	protected String wbsTable;

	/**
	 * 目标表模型
	 */
	@Lob
	@Column(name = "TARGETTABLEMODEL", length = 4000)
	protected String targetTableModel;

	public SynchronizationRule() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SynchronizationRule other = (SynchronizationRule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActionName() {
		return this.actionName;
	}

	public String getFormulas() {
		return this.formulas;
	}

	public String getId() {
		return this.id;
	}

	public String getSouceTable() {
		return this.souceTable;
	}

	public List<String> getSouceTables() {
		List<String> tables = new ArrayList<String>();
		if (StringUtils.isNotEmpty(souceTable)) {
			StringTokenizer token = new StringTokenizer(souceTable, ",");
			while (token.hasMoreTokens()) {
				String str = token.nextToken();
				str = StringTools.replace(str, "[", "");
				str = StringTools.replace(str, "]", "");
				tables.add(str);
			}
		}
		return tables;
	}

	public String getTargetTable() {
		return this.targetTable;
	}

	public String getTargetTableModel() {
		return this.targetTableModel;
	}

	public String getWbsTable() {
		return this.wbsTable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SynchronizationRule jsonToObject(JSONObject jsonObject) {
		return SynchronizationRuleJsonFactory.jsonToObject(jsonObject);
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public void setFormulas(String formulas) {
		this.formulas = formulas;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSouceTable(String souceTable) {
		this.souceTable = souceTable;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public void setTargetTableModel(String targetTableModel) {
		this.targetTableModel = targetTableModel;
	}

	public void setWbsTable(String wbsTable) {
		this.wbsTable = wbsTable;
	}

	public JSONObject toJsonObject() {
		return SynchronizationRuleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SynchronizationRuleJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
