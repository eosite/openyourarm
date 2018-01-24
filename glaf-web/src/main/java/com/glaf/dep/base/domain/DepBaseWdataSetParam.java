package com.glaf.dep.base.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_BASE_WDATASET_PARAM_")
public class DepBaseWdataSetParam implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 更新数据集唯一标识
         */
        @Column(name = "WDATASET_ID_")	 
        protected Long wdataSetId;

        /**
         * 参数代码
         */
        @Column(name = "CODE_", length=30) 
        protected String code;

        /**
         * 参数名称
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * 参数数据类型
         */
        @Column(name = "DTYPE_", length=20) 
        protected String dType;

        /**
         * 创建人
         */
        @Column(name = "CREATOR_", length=20) 
        protected String creator;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATETIME_")	
        protected Date createDatetime;

        /**
         * 修改人
         */
        @Column(name = "MODIFIER_", length=20) 
        protected String modifier;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "MODIFYDATETIME_")	
        protected Date modifyDatetime;


         
	public DepBaseWdataSetParam() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getWdataSetId(){
	    return this.wdataSetId;
	}
        public String getCode(){
	    return this.code;
	}
        public String getName(){
	    return this.name;
	}
        public String getDType(){
	    return this.dType;
	}
        public String getCreator(){
	    return this.creator;
	}
	public Date getCreateDatetime(){
	    return this.createDatetime;
	}
	public String getCreateDatetimeString(){
	    if(this.createDatetime != null){
	        return DateUtils.getDateTime(this.createDatetime);
	    }
	    return "";
	}
        public String getModifier(){
	    return this.modifier;
	}
	public Date getModifyDatetime(){
	    return this.modifyDatetime;
	}
	public String getModifyDatetimeString(){
	    if(this.modifyDatetime != null){
	        return DateUtils.getDateTime(this.modifyDatetime);
	    }
	    return "";
	}


        public void setWdataSetId(Long wdataSetId) {
	    this.wdataSetId = wdataSetId; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setDType(String dType) {
	    this.dType = dType; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}
        public void setModifier(String modifier) {
	    this.modifier = modifier; 
	}
        public void setModifyDatetime(Date modifyDatetime) {
	    this.modifyDatetime = modifyDatetime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseWdataSetParam other = (DepBaseWdataSetParam) obj;
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
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public DepBaseWdataSetParam jsonToObject(JSONObject jsonObject) {
            return DepBaseWdataSetParamJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepBaseWdataSetParamJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepBaseWdataSetParamJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
