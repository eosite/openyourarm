package com.glaf.base.project.domain;

import java.io.*;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJECT_SUBORDINATE")
public class ProjectSubordinate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "PROJECTID_")
	protected Long projectId;

	@Column(name = "SUBORDINATEID_")
	protected Long subordinateId;

	public ProjectSubordinate() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectSubordinate other = (ProjectSubordinate) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return this.id;
	}

	public Long getProjectId() {
		return this.projectId;
	}

	public Long getSubordinateId() {
		return subordinateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setSubordinateId(Long subordinateId) {
		this.subordinateId = subordinateId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
