package com.glaf.ui.model;

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

import com.glaf.core.util.DateUtils;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "UI_THEME")
public class Theme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Integer id;

	@Column(name = "THEMESTYLE_", length = 20)
	protected String themeStyle;

	@Column(name = "LAYOUTMODEL_", length = 20)
	protected String layoutModel;

	@Column(name = "BACKGROUND_", length = 100)
	protected String background;

	@Column(name = "BACKGROUNDTYPE_", length = 20)
	protected String backgroundType;

	@Column(name = "LOCKED_")
	protected Integer locked;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	public Theme() {

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getThemeStyle() {
		return this.themeStyle;
	}

	public String getLayoutModel() {
		return this.layoutModel;
	}

	public String getBackground() {
		return this.background;
	}

	public String getBackgroundType() {
		return this.backgroundType;
	}

	public Integer getLocked() {
		return this.locked;
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

	public void setThemeStyle(String themeStyle) {
		this.themeStyle = themeStyle;
	}

	public void setLayoutModel(String layoutModel) {
		this.layoutModel = layoutModel;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public void setBackgroundType(String backgroundType) {
		this.backgroundType = backgroundType;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Theme other = (Theme) obj;
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

	// public Theme jsonToObject(JSONObject jsonObject) {
	// return ThemeJsonFactory.jsonToObject(jsonObject);
	// }
	//
	//
	// public JSONObject toJsonObject() {
	// return ThemeJsonFactory.toJsonObject(this);
	// }
	//
	// public ObjectNode toObjectNode(){
	// return ThemeJsonFactory.toObjectNode(this);
	// }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
