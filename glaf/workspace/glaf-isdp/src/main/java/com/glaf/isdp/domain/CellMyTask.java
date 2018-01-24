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
import com.glaf.isdp.util.CellMyTaskJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_MYTASK")
public class CellMyTask implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "TOPID", length = 50)
	protected String topId;

	@Column(name = "FILLFORM_ID", length = 50)
	protected String fillFormId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "TASK_ID", length = 50)
	protected String taskId;

	@Column(name = "FILEDOT_FILEID", length = 50)
	protected String fileDotFileId;

	@Column(name = "NAME", length = 250)
	protected String name;

	@Column(name = "PROJNAME", length = 250)
	protected String projName;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "TYPE_INDEX_ID")
	protected Integer typeIndexId;

	@Column(name = "PAGE")
	protected Integer page;

	@Column(name = "FINISHINT")
	protected Integer finishInt;

	@Column(name = "FORMTYPEINT")
	protected Integer formTypeInt;

	@Column(name = "FLAGINT")
	protected Integer flagInt;

	@Column(name = "INTINFLOW")
	protected Integer intInFlow;

	@Column(name = "MAIN_ID", length = 50)
	protected String mainId;

	@Column(name = "INTLASTPAGE")
	protected Integer intLastPage;

	public CellMyTask() {

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

	public String getFillFormId() {
		return this.fillFormId;
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

	public Integer getIndexId() {
		return this.indexId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public String getFileDotFileId() {
		return this.fileDotFileId;
	}

	public String getName() {
		return this.name;
	}

	public String getProjName() {
		return this.projName;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getTypeIndexId() {
		return this.typeIndexId;
	}

	public Integer getPage() {
		return this.page;
	}

	public Integer getFinishInt() {
		return this.finishInt;
	}

	public Integer getFormTypeInt() {
		return this.formTypeInt;
	}

	public Integer getFlagInt() {
		return this.flagInt;
	}

	public Integer getIntInFlow() {
		return this.intInFlow;
	}

	public String getMainId() {
		return this.mainId;
	}

	public Integer getIntLastPage() {
		return this.intLastPage;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setFillFormId(String fillFormId) {
		this.fillFormId = fillFormId;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setTypeIndexId(Integer typeIndexId) {
		this.typeIndexId = typeIndexId;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setFinishInt(Integer finishInt) {
		this.finishInt = finishInt;
	}

	public void setFormTypeInt(Integer formTypeInt) {
		this.formTypeInt = formTypeInt;
	}

	public void setFlagInt(Integer flagInt) {
		this.flagInt = flagInt;
	}

	public void setIntInFlow(Integer intInFlow) {
		this.intInFlow = intInFlow;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public void setIntLastPage(Integer intLastPage) {
		this.intLastPage = intLastPage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellMyTask other = (CellMyTask) obj;
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

	public CellMyTask jsonToObject(JSONObject jsonObject) {
		return CellMyTaskJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellMyTaskJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellMyTaskJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
