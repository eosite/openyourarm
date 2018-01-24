package com.glaf.dep.report.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.report.util.*;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "DEP_REPORT_TMPCATEGORY")
public class DepReportTmpCategory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 目录唯一标识
	 */
	@Column(name = "DEP_ID_")
	protected Long depId;

	/**
	 * 模板唯一标识
	 */
	@Column(name = "TMP_ID_")
	protected Long tmpId;

	public DepReportTmpCategory() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepId() {
		return this.depId;
	}

	public Long getTmpId() {
		return this.tmpId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public void setTmpId(Long tmpId) {
		this.tmpId = tmpId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepReportTmpCategory other = (DepReportTmpCategory) obj;
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

	public DepReportTmpCategory jsonToObject(JSONObject jsonObject) {
		return DepReportTmpCategoryJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepReportTmpCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepReportTmpCategoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
