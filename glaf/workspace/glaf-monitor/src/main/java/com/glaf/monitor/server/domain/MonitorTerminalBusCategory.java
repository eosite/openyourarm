package com.glaf.monitor.server.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.monitor.server.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "MONITOR_TERMINAL_BUS_CATEGORY")
public class MonitorTerminalBusCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "TERMINAL_ID_", nullable = false)
        protected String terminalId;

        /**
         * 分类ID
         */
        @Id
        @Column(name = "CATEGORY_ID_")
        protected Integer categoryId;

        /**
         * CREATEBY_
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createby;

        /**
         * CREATETIME_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createtime;


         
	public MonitorTerminalBusCategory() {

	}

	public String getTerminalId(){
	    return this.terminalId;
	}

	public void setTerminalId(String terminalId) {
	    this.terminalId = terminalId; 
	}


        public Integer getCategoryId(){
	    return this.categoryId;
	}
        public String getCreateby(){
	    return this.createby;
	}
	public Date getCreatetime(){
	    return this.createtime;
	}
	public String getCreatetimeString(){
	    if(this.createtime != null){
	        return DateUtils.getDateTime(this.createtime);
	    }
	    return "";
	}


        public void setCategoryId(Integer categoryId) {
	    this.categoryId = categoryId; 
	}
        public void setCreateby(String createby) {
	    this.createby = createby; 
	}
        public void setCreatetime(Date createtime) {
	    this.createtime = createtime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitorTerminalBusCategory other = (MonitorTerminalBusCategory) obj;
		if (terminalId == null) {
			if (other.terminalId != null)
				return false;
		} else if (!terminalId.equals(other.terminalId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((terminalId == null) ? 0 : terminalId.hashCode());
		return result;
	}


	public MonitorTerminalBusCategory jsonToObject(JSONObject jsonObject) {
            return MonitorTerminalBusCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorTerminalBusCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorTerminalBusCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
