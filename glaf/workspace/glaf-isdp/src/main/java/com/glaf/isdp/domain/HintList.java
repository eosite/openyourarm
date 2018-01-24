package com.glaf.isdp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.JSONable;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "HINTLIST")
public class HintList implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "HINEID", length = 50)
	protected String hintId;

	@Column(name = "LIST", length = 100)
	protected String list;

	@Column(name = "CONTENT", length = 100)
	protected String content;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "HINTDATA")
	protected Integer hintData;

	public HintList() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHintId() {
		return hintId;
	}

	public void setHintId(String hintId) {
		this.hintId = hintId;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public Integer getHintData() {
		return hintData;
	}

	public void setHintData(Integer hintData) {
		this.hintData = hintData;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HintList other = (HintList) obj;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public Object jsonToObject(JSONObject jsonObject) {
		if (jsonObject.containsKey("id")) {
			this.id = jsonObject.getString("id");
		}
		if (jsonObject.containsKey("hintId")) {
			this.hintId = jsonObject.getString("hintId");
		}
		if (jsonObject.containsKey("list")) {
			this.list = jsonObject.getString("list");
		}
		if (jsonObject.containsKey("content")) {
			this.content = jsonObject.getString("content");
		}
		if (jsonObject.containsKey("listNo")) {
			this.listNo = jsonObject.getInteger("listNo");
		}
		if (jsonObject.containsKey("hintData")) {
			this.hintData = jsonObject.getInteger("hintData");
		}

		return this;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		if (this.hintId != null) {
			jsonObject.put("hintId", this.hintId);
		}
		if (this.list != null) {
			jsonObject.put("list", this.list);
		}
		if (this.content != null) {
			jsonObject.put("content", this.content);
		}
		if (this.listNo != null) {
			jsonObject.put("listNo", this.listNo);
		}
		if (this.hintData != null) {
			jsonObject.put("hintData", this.hintData);
		}

		return jsonObject;
	}

}
