package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_PAGE")
public class FormPage implements java.lang.Comparable<FormPage>, Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "PARENTID_", length = 50)
	protected String parentId;

	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	@Column(name = "NAME_", length = 100)
	protected String name;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Lob
	@Column(name = "FORMHTML_")
	protected String formHtml;

	@Lob
	@Column(name = "DESIGNERHTML_")
	protected String designerHtml;

	@Lob
	@Column(name = "DESIGNERJSON_")
	protected String designerJson;

	@Lob
	@Column(name = "FORMCONFIG_")
	protected String formConfig;

	@Lob
	@Column(name = "OUTPUTHTML_")
	protected String outputHtml;

	@Column(name = "FORMTYPE_", length = 50)
	protected String formType;

	@Column(name = "SORTNO_")
	protected int sortNo;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	@Column(name = "VERSION_")
	protected int version;

	@Column(name = "ACCESSTYPE_", length = 20)
	protected String accessType;

	@Column(name = "PERMS_", length = 500)
	protected String perms;

	@Column(name = "ADDRESSPERMS_", length = 2000)
	protected String addressPerms;
	
	/**
	 * 用来区分模板还是实例   0/null 为实例  1为模板
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "CACHEFLAG_", length = 1)
	protected String cacheFlag;

	@javax.persistence.Transient
	protected long cacheTime;

	@Column(name = "PUBLICFLAG_", length = 1)
	protected String publicFlag;

	@Column(name = "USERSTYLEFLAG_", length = 1)
	protected String userStyleFlag;

	/**
	 * 业务表名
	 */
	@Column(name = "BUSINESSTABLE_", length = 50)
	protected String businessTable;

	/**
	 * 业务主键列
	 */
	@Column(name = "PRIMARYKEYCOLUMN_", length = 50)
	protected String primaryKeyColumn;

	@Column(name = "PROCESSNAME_", length = 200)
	protected String processName;

	@Column(name = "TASKFLAG_", length = 20)
	protected String taskFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Column(name = "PAGECATEGORY_", length = 50)
	protected Integer pageCategory;

	@javax.persistence.Transient
	private boolean extend = false;

	/**
	 * UI类型
	 */
	@Column(name = "UITYPE_", length = 50)
	protected String uiType;

	/**
	 * 调试变量
	 */
	@Lob
	@Column(name = "DEBUGGERVAR_")
	protected String debuggerVar;
	
	/**
	 * 主题
	 */
	@Column(name = "THEME_TMP_ID_", length = 50)
	protected String themeTmpId;

	public String getThemeTmpId() {
		return themeTmpId;
	}

	public void setThemeTmpId(String themeTmpId) {
		this.themeTmpId = themeTmpId;
	}

	public FormPage() {

	}

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	public int compareTo(FormPage o) {
		if (o == null) {
			return -1;
		}

		FormPage obj = o;

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
		FormPage other = (FormPage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAccessType() {
		return accessType;
	}

	public String getAddressPerms() {
		return addressPerms;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public String getCacheFlag() {
		return cacheFlag;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public int getDeleteFlag() {
		return this.deleteFlag;
	}

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public String getFormConfig() {
		return this.formConfig;
	}

	public String getFormHtml() {
		return this.formHtml;
	}

	public String getFormType() {
		return this.formType;
	}

	public String getId() {
		return this.id;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public String getOutputHtml() {
		return outputHtml;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getPerms() {
		return perms;
	}

	public String getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public String getProcessName() {
		return processName;
	}

	public String getPublicFlag() {
		return publicFlag;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public String getTaskFlag() {
		return taskFlag;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateDateString() {
		if (this.updateDate != null) {
			return DateUtils.getDateTime(this.updateDate);
		}
		return "";
	}

	public String getUserStyleFlag() {
		return userStyleFlag;
	}

	public int getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormPage jsonToObject(JSONObject jsonObject) {
		return FormPageJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public void setAddressPerms(String addressPerms) {
		this.addressPerms = addressPerms;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public void setCacheFlag(String cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setFormConfig(String formConfig) {
		this.formConfig = formConfig;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOutputHtml(String outputHtml) {
		this.outputHtml = outputHtml;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public void setPrimaryKeyColumn(String primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserStyleFlag(String userStyleFlag) {
		this.userStyleFlag = userStyleFlag;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public JSONObject toJsonObject() {
		return FormPageJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormPageJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean isExtend() {
		return extend;
	}

	public void setExtend(boolean extend) {
		this.extend = extend;
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}

	public String getDesignerHtml() {
		return designerHtml;
	}

	public void setDesignerHtml(String designerHtml) {
		this.designerHtml = designerHtml;
	}

	public String getDesignerJson() {
		return designerJson;
	}

	public void setDesignerJson(String designerJson) {
		this.designerJson = designerJson;
	}

	public Integer getPageCategory() {
		return pageCategory;
	}

	public void setPageCategory(Integer pageCategory) {
		this.pageCategory = pageCategory;
	}

	public String getDebuggerVar() {
		return debuggerVar;
	}

	public void setDebuggerVar(String debuggerVar) {
		this.debuggerVar = debuggerVar;
	}

}
