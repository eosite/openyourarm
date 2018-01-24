package com.glaf.base.modules.image.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.image.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "T_IMAGE")
public class Image implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "PARENTID_")
	protected Long parentId;

	@Column(name = "NAME_", length = 200)
	protected String name;

	@Column(name = "FILENAME_", length = 200)
	protected String filename;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "DESC_", length = 100)
	protected String desc;

	@Column(name = "IMAGEPATH_", length = 200)
	protected String imagePath;

	@Column(name = "WIDTH_")
	protected Integer width;

	@Column(name = "HEIGHT_")
	protected Integer height;

	@Column(name = "LOCKED_")
	protected Integer locked;

	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;

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

	@javax.persistence.Transient
	protected byte[] data;

	@javax.persistence.Transient
	protected DataFile dataFile;

	public Image() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public byte[] getData() {
		return data;
	}

	public DataFile getDataFile() {
		return dataFile;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getFilename() {
		return filename;
	}

	public Integer getHeight() {
		return height;
	}

	public Long getId() {
		return this.id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public Integer getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public String getType() {
		return this.type;
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

	public Integer getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Image jsonToObject(JSONObject jsonObject) {
		return ImageJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setDataFile(DataFile dataFile) {
		this.dataFile = dataFile;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public void setWidth(Integer width) {
		this.width = width;
	}

	public JSONObject toJsonObject() {
		return ImageJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ImageJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
