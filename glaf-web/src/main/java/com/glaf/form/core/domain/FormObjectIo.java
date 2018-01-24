package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_OBJECT_IO")
public class FormObjectIo implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * NAME
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * CODE
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * CODEMAPPING
	 */
	@Column(name = "CODE_MAPPING_", length = 50)
	protected String codeMapping;

	/**
	 * PARAMTYPE
	 */
	@Column(name = "PARAM_TYPE_", length = 10)
	protected String paramType;

	/**
	 * DEFVALUE
	 */
	@Column(name = "DEFVALUE_", length = 50)
	protected String defValue;

	/**
	 * TYPE
	 */
	@Column(name = "TYPE_", length = 10)
	protected String type;

	/**
	 * PARENTID
	 */
	@Column(name = "PARENT_ID_", length = 50)
	protected String parent_id;

	public FormObjectIo() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getCodeMapping() {
		return this.codeMapping;
	}

	public String getParamType() {
		return this.paramType;
	}

	public String getDefValue() {
		return this.defValue;
	}

	public String getType() {
		return this.type;
	}

	public String getParent_id() {
		return this.parent_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeMapping(String codeMapping) {
		this.codeMapping = codeMapping;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormObjectIo other = (FormObjectIo) obj;
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

	public FormObjectIo jsonToObject(JSONObject jsonObject) {
		return FormObjectIoJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormObjectIoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormObjectIoJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
