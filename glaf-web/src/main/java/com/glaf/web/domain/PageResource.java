package com.glaf.web.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.web.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PAGE_RESOURCE")
public class PageResource implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RES_ID_", nullable = false)
	protected Long resId;

	/**
	 * resPath
	 */
	@Column(name = "RES_PATH_", length = 150)
	protected String resPath;

	/**
	 * resFileName
	 */
	@Column(name = "RES_FILENAME_", length = 150)
	protected String resFileName;

	/**
	 * resName
	 */
	@Column(name = "RES_NAME_", length = 150)
	protected String resName;

	/**
	 * resContent
	 */
	@Lob
	@Column(name = "RES_CONTENT_")
	protected byte[] resContent;

	/**
	 * resType
	 */
	@Column(name = "RES_TYPE_", length = 20)
	protected String resType;

	/**
	 * resMime
	 */
	@Column(name = "RES_MIME_", length = 50)
	protected String resMime;

	/**
	 * resCrDatetime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RES_CRDATETIME_")
	protected Date resCrDatetime;

	public PageResource() {

	}

	public Long getResId() {
		return this.resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getResPath() {
		return this.resPath;
	}

	public String getResFileName() {
		return this.resFileName;
	}

	public String getResName() {
		return this.resName;
	}

	public byte[] getResContent() {
		return this.resContent;
	}

	public String getResType() {
		return this.resType;
	}

	public String getResMime() {
		return this.resMime;
	}

	public Date getResCrDatetime() {
		return this.resCrDatetime;
	}

	public String getResCrDatetimeString() {
		if (this.resCrDatetime != null) {
			return DateUtils.getDateTime(this.resCrDatetime);
		}
		return "";
	}

	public void setResPath(String resPath) {
		this.resPath = resPath;
	}

	public void setResFileName(String resFileName) {
		this.resFileName = resFileName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public void setResContent(byte[] resContent) {
		this.resContent = resContent;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public void setResMime(String resMime) {
		this.resMime = resMime;
	}

	public void setResCrDatetime(Date resCrDatetime) {
		this.resCrDatetime = resCrDatetime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageResource other = (PageResource) obj;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
		return result;
	}

	public PageResource jsonToObject(JSONObject jsonObject) {
		return PageResourceJsonFactory.jsonToObject(jsonObject);
	}

	public PageResource jsonToObjectFull(JSONObject jsonObject) {
		return PageResourceJsonFactory.jsonToObjectFull(jsonObject);
	}

	public JSONObject toJsonObject() {
		JSONObject json = null;
		try {
			json = PageResourceJsonFactory.toJsonObject(this);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public ObjectNode toObjectNode() {
		return PageResourceJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
