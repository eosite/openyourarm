package com.glaf.base.modules.uis.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.uis.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "UIS_APP_USERID_MAPPING")
public class UisAppUserIdMapping implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * appUserId
         */
        @Column(name = "APP_USER_ID_", length=50) 
        protected String appUserId;

        /**
         * userId
         */
        @Column(name = "USER_ID_", length=50) 
        protected String userId;

        /**
         * appId
         */
        @Column(name = "APP_ID_", length=50) 
        protected String appId;


         
	public UisAppUserIdMapping() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getAppUserId(){
	    return this.appUserId;
	}
        public String getUserId(){
	    return this.userId;
	}
        public String getAppId(){
	    return this.appId;
	}


        public void setAppUserId(String appUserId) {
	    this.appUserId = appUserId; 
	}
        public void setUserId(String userId) {
	    this.userId = userId; 
	}
        public void setAppId(String appId) {
	    this.appId = appId; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UisAppUserIdMapping other = (UisAppUserIdMapping) obj;
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


	public UisAppUserIdMapping jsonToObject(JSONObject jsonObject) {
            return UisAppUserIdMappingJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return UisAppUserIdMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return UisAppUserIdMappingJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
