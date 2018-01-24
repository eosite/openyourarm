package com.glaf.isdp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.isdp.util.CellFillFormJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_FILLFORM")
public class CellFillForm implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "TOPID", length = 50)
	protected String topId;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "TASK_ID", length = 50)
	protected String taskId;

	@Column(name = "PFILEFLAG")
	protected Integer pfileFlag;

	@Column(name = "FILEDOT_FILEID", length = 50)
	protected String fileDotFileId;

	@Column(name = "NAME", length = 50)
	protected String name;

	@Column(name = "CHKNUM", length = 50)
	protected String chkNum;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "CHKTOTAL")
	protected Integer chkTotal;

	@Column(name = "CHKRESULT")
	protected Integer chkResult;

	@Column(name = "PFILE_ID", length = 50)
	protected String pfileId;

	@Column(name = "TEMPSAVE")
	protected Integer tempSave;

	@Column(name = "USERID", length = 20)
	protected String userId;

	@Column(name = "REFILLFLAG")
	protected Integer refillFlag;

	@Column(name = "GROUPID")
	protected Integer groupId;

	@Column(name = "OLD_ID", length = 50)
	protected String oldId;

	@Column(name = "ROLE_ID")
	protected Integer roleId;

	@Column(name = "ISFINISH")
	protected Integer isFinish;

	@Column(name = "TYPE_TABLENAME", length = 50)
	protected String typeTableName;

	@Column(name = "TYPE_ID", length = 50)
	protected String typeId;

	@Column(name = "TYPE_INDEX_ID")
	protected Integer typeIndexId;

	@Column(name = "MAIN_ID", length = 50)
	protected String mainId;

	@Column(name = "INTLASTPAGE")
	protected Integer intLastPage;

	@Column(name = "INTSHEETS")
	protected Integer intSheets;

	public CellFillForm() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopId() {
		return this.topId;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public Integer getPfileFlag() {
		return this.pfileFlag;
	}

	public String getFileDotFileId() {
		return this.fileDotFileId;
	}

	public String getName() {
		return this.name;
	}

	public String getChkNum() {
		return this.chkNum;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getChkTotal() {
		return this.chkTotal;
	}

	public Integer getChkResult() {
		return this.chkResult;
	}

	public String getPfileId() {
		return this.pfileId;
	}

	public Integer getTempSave() {
		return this.tempSave;
	}

	public String getUserId() {
		return this.userId;
	}

	public Integer getRefillFlag() {
		return this.refillFlag;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public String getOldId() {
		return this.oldId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public Integer getIsFinish() {
		return this.isFinish;
	}

	public String getTypeTableName() {
		return this.typeTableName;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public Integer getTypeIndexId() {
		return this.typeIndexId;
	}

	public String getMainId() {
		return this.mainId;
	}

	public Integer getIntLastPage() {
		return this.intLastPage;
	}

	public Integer getIntSheets() {
		return this.intSheets;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setPfileFlag(Integer pfileFlag) {
		this.pfileFlag = pfileFlag;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChkNum(String chkNum) {
		this.chkNum = chkNum;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setChkTotal(Integer chkTotal) {
		this.chkTotal = chkTotal;
	}

	public void setChkResult(Integer chkResult) {
		this.chkResult = chkResult;
	}

	public void setPfileId(String pfileId) {
		this.pfileId = pfileId;
	}

	public void setTempSave(Integer tempSave) {
		this.tempSave = tempSave;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRefillFlag(Integer refillFlag) {
		this.refillFlag = refillFlag;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public void setTypeTableName(String typeTableName) {
		this.typeTableName = typeTableName;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setTypeIndexId(Integer typeIndexId) {
		this.typeIndexId = typeIndexId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public void setIntLastPage(Integer intLastPage) {
		this.intLastPage = intLastPage;
	}

	public void setIntSheets(Integer intSheets) {
		this.intSheets = intSheets;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellFillForm other = (CellFillForm) obj;
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

	public CellFillForm jsonToObject(JSONObject jsonObject) {
		return CellFillFormJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellFillFormJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellFillFormJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
