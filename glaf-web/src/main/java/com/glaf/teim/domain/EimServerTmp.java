package com.glaf.teim.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "EIM_SERVER_TMP")
public class EimServerTmp implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TMP_ID_", nullable = false)
	protected String tmpId;

	/**
	 * categoryId
	 */
	@Column(name = "CATEGORY_ID_")
	protected Long categoryId;

	/**
	 * name
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * path_
	 */
	@Column(name = "PATH_", length = 50)
	protected String path_;

	/**
	 * reqUrlParam
	 */
	@Lob
	@Column(name = "REQ_URL_PARAM_")
	protected String reqUrlParam;

	/**
	 * reqType
	 */
	@Column(name = "REQ_TYPE_", length = 20)
	protected String reqType;

	/**
	 * reqHeader
	 */
	@Lob
	@Column(name = "REQ_HEADER_")
	protected String reqHeader;

	/**
	 * reqContentType
	 */
	@Column(name = "REQ_CONTENT_TYPE_", length = 100)
	protected String reqContentType;

	/**
	 * resContentType
	 */
	@Column(name = "RES_CONTENT_TYPE_", length = 100)
	protected String resContentType;

	/**
	 * reqBody
	 */
	@Lob
	@Column(name = "REQ_BODY_")
	protected String reqBody;
	
	/**
	 * reqBodyCustom
	 */
	@Lob
	@Column(name = "REQ_BODY_CUSTOM_")
	protected String reqBodyCustom;

	/**
	 * response
	 */
	@Lob
	@Column(name = "RESPONSE_")
	protected String response_;
	/**
	 * 分页条件
	 */
	@Column(name = "PAGING_CONTENT_", length = 200)
	protected String pagingContent;
	/**
	 * 递归条件
	 */
	@Column(name = "RECURSIVE_CONTENT_", length = 200)
	protected String recursiveContent;
	/**
	 * createBy
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * updateBy
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * updateTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * deleteFlag
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag = 0;

	public EimServerTmp() {

	}

	public String getTmpId() {
		return this.tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public String getName() {
		return this.name;
	}

	public String getPath_() {
		return this.path_;
	}

	public String getReqUrlParam() {
		return this.reqUrlParam;
	}

	public String getReqType() {
		return this.reqType;
	}

	public String getReqHeader() {
		return this.reqHeader;
	}

	public String getReqContentType() {
		return this.reqContentType;
	}

	public String getResContentType() {
		return this.resContentType;
	}

	public String getReqBody() {
		return this.reqBody;
	}

	public String getResponse_() {
		return this.response_;
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

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath_(String path_) {
		this.path_ = path_;
	}

	public void setReqUrlParam(String reqUrlParam) {
		this.reqUrlParam = reqUrlParam;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public void setReqHeader(String reqHeader) {
		this.reqHeader = reqHeader;
	}

	public void setReqContentType(String reqContentType) {
		this.reqContentType = reqContentType;
	}

	public void setResContentType(String resContentType) {
		this.resContentType = resContentType;
	}

	public void setReqBody(String reqBody) {
		this.reqBody = reqBody;
	}

	public void setResponse_(String response_) {
		this.response_ = response_;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getPagingContent() {
		return pagingContent;
	}

	public void setPagingContent(String pagingContent) {
		this.pagingContent = pagingContent;
	}

	public String getRecursiveContent() {
		return recursiveContent;
	}

	public void setRecursiveContent(String recursiveContent) {
		this.recursiveContent = recursiveContent;
	}

	public String getReqBodyCustom() {
		return reqBodyCustom;
	}

	public void setReqBodyCustom(String reqBodyCustom) {
		this.reqBodyCustom = reqBodyCustom;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EimServerTmp other = (EimServerTmp) obj;
		if (tmpId == null) {
			if (other.tmpId != null)
				return false;
		} else if (!tmpId.equals(other.tmpId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tmpId == null) ? 0 : tmpId.hashCode());
		return result;
	}

	public EimServerTmp jsonToObject(JSONObject jsonObject) {
		return EimServerTmpJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EimServerTmpJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EimServerTmpJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
