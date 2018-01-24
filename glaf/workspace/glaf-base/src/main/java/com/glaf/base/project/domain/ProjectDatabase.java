package com.glaf.base.project.domain;

import java.io.*;

import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;

import com.glaf.base.project.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJECT_DATABASE")
public class ProjectDatabase implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "DATABASEID_")
	protected Long databaseId;

	@Column(name = "PROJECTID_")
	protected Long projectId;

	public ProjectDatabase() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDatabaseId() {
		return this.databaseId;
	}

	public Long getProjectId() {
		return this.projectId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectDatabase other = (ProjectDatabase) obj;
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

	public ProjectDatabase jsonToObject(JSONObject jsonObject) {
		return ProjectDatabaseJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ProjectDatabaseJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjectDatabaseJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
