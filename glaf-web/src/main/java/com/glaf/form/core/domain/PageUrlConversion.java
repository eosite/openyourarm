package com.glaf.form.core.domain;

import java.io.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PAGE_URL_CONVERSION")
public class PageUrlConversion implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * srcUrl
	 */
	@Column(name = "SRCURL_", length = 500)
	protected String srcUrl;

	/**
	 * destUrl
	 */
	@Column(name = "DESTURL_", length = 500)
	protected String destUrl;

	/**
	 * locked
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	public PageUrlConversion() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageUrlConversion other = (PageUrlConversion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDestUrl() {
		return this.destUrl;
	}

	public String getId() {
		return this.id;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getSrcUrl() {
		return this.srcUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public PageUrlConversion jsonToObject(JSONObject jsonObject) {
		return PageUrlConversionJsonFactory.jsonToObject(jsonObject);
	}

	public void setDestUrl(String destUrl) {
		this.destUrl = destUrl;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public JSONObject toJsonObject() {
		return PageUrlConversionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PageUrlConversionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
