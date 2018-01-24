package com.glaf.chinaiss.data.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DATA_MODEL_METADATA")
public class DataModelMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * code
	 */
	@Column(name = "CODE_", length = 100)
	protected String code;

	/**
	 * mapping
	 */
	@Column(name = "MAPPING_", length = 100)
	protected String mapping;

	/**
	 * mappingId
	 */
	@Column(name = "MAPPINGID_", length = 50)
	protected String mappingId;

	/**
	 * Type
	 */
	@Column(name = "TYPE_", length = 10)
	protected String type;

	public DataModelMetadata() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

}
