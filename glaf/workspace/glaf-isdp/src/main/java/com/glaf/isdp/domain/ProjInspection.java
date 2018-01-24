package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.ProjInspectionJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJ_INSPECTION")
public class ProjInspection implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "INTFLAG")
	protected Integer intFlag;

	@Column(name = "CELL_TMPFILETYPE_ID", length = 50)
	protected String cellTmpFileTypeId;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "CHKRESULT")
	protected Integer chkResult;

	@Column(name = "PFILE_ID", length = 50)
	protected String pfileId;

	@Column(name = "REFILLFLAG")
	protected Integer refillFlag;

	@Column(name = "GROUPID")
	protected Integer groupId;

	@Column(name = "OLD_ID", length = 50)
	protected String oldId;

	@Column(name = "EMAIL_ID", length = 50)
	protected String emailId;

	@Column(name = "RECEMAIL_ID", length = 50)
	protected String recemailId;

	@Column(name = "MAIN_ID", length = 50)
	protected String mainId;

	@Column(name = "TAGNUM", length = 50)
	protected String tagNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "TNAME", length = 250)
	protected String tname;

	@Column(name = "PAGE")
	protected Integer page;

	@Column(name = "DUTY", length = 100)
	protected String duty;

	@Column(name = "THEMATIC", length = 100)
	protected String thematic;

	@Column(name = "ANNOTATIONS", length = 100)
	protected String annotations;

	public ProjInspection() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public Integer getIntFlag() {
		return this.intFlag;
	}

	public String getCellTmpFileTypeId() {
		return this.cellTmpFileTypeId;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getChkResult() {
		return this.chkResult;
	}

	public String getPfileId() {
		return this.pfileId;
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

	public String getEmailId() {
		return this.emailId;
	}

	public String getRecemailId() {
		return this.recemailId;
	}

	public String getMainId() {
		return this.mainId;
	}

	public String getTagNum() {
		return this.tagNum;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeString() {
		if (this.ctime != null) {
			return DateUtils.getDateTime(this.ctime);
		}
		return "";
	}

	public String getTname() {
		return this.tname;
	}

	public Integer getPage() {
		return this.page;
	}

	public String getDuty() {
		return this.duty;
	}

	public String getThematic() {
		return this.thematic;
	}

	public String getAnnotations() {
		return this.annotations;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIntFlag(Integer intFlag) {
		this.intFlag = intFlag;
	}

	public void setCellTmpFileTypeId(String cellTmpFileTypeId) {
		this.cellTmpFileTypeId = cellTmpFileTypeId;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setChkResult(Integer chkResult) {
		this.chkResult = chkResult;
	}

	public void setPfileId(String pfileId) {
		this.pfileId = pfileId;
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

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setRecemailId(String recemailId) {
		this.recemailId = recemailId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public void setTagNum(String tagNum) {
		this.tagNum = tagNum;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setThematic(String thematic) {
		this.thematic = thematic;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjInspection other = (ProjInspection) obj;
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

	public ProjInspection jsonToObject(JSONObject jsonObject) {
		return ProjInspectionJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ProjInspectionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjInspectionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
