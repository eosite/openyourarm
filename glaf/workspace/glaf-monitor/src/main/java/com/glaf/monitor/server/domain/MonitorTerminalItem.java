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
@Table(name = "MONITOR_TERMINAL_ITEM")
public class MonitorTerminalItem implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 终端ID
         */
        @Column(name = "TERMINAL_ID_", length=50) 
        protected String terminalId;

        /**
         * 指标类型代码
         */
        @Column(name = "CODE_", length=30) 
        protected String code;

        /**
         * 指标项名称
         */
        @Column(name = "NAME_", length=30) 
        protected String name;

        /**
         * 单位
         */
        @Column(name = "UNIT_", length=20) 
        protected String unit;

        /**
         * 预警值
         */
        @Column(name = "ALARM_VAL_")
        protected Integer alarmVal;

        /**
         * 参照类型
         */
        @Column(name = "REF_TYPE_", length=20) 
        protected String refType;
        
        /**
         * 类型
         */
        @Column(name = "TYPE_", length=20) 
        protected String type;

        /**
         * 提醒方式
         */
        @Column(name = "WARNING_TYPE_", length=20) 
        protected String warningType;

        /**
         * 监控服务地址
         */
        @Column(name = "MONITOR_SERVICE_ADDRESS_", length=150) 
        protected String monitorServiceAddress;
        
        /**
         * 值
         */
        @Column(name = "VALUE_", length=150) 
        protected String value;

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

        /**
         * UPDATEBY_
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateby;

        /**
         * UPDATETIME_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updatetime;

        /**
         * DELETE_FLAG_
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;


         
	public MonitorTerminalItem() {

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getTerminalId(){
	    return this.terminalId;
	}
        public String getCode(){
	    return this.code;
	}
        public String getName(){
	    return this.name;
	}
        public String getUnit(){
	    return this.unit;
	}
        public Integer getAlarmVal(){
	    return this.alarmVal;
	}
        public String getRefType(){
	    return this.refType;
	}
        public String getWarningType(){
	    return this.warningType;
	}
        public String getMonitorServiceAddress(){
	    return this.monitorServiceAddress;
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
        public String getUpdateby(){
	    return this.updateby;
	}
	public Date getUpdatetime(){
	    return this.updatetime;
	}
	public String getUpdatetimeString(){
	    if(this.updatetime != null){
	        return DateUtils.getDateTime(this.updatetime);
	    }
	    return "";
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}


        public void setTerminalId(String terminalId) {
	    this.terminalId = terminalId; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setUnit(String unit) {
	    this.unit = unit; 
	}
        public void setAlarmVal(Integer alarmVal) {
	    this.alarmVal = alarmVal; 
	}
        public void setRefType(String refType) {
	    this.refType = refType; 
	}
        public void setWarningType(String warningType) {
	    this.warningType = warningType; 
	}
        public void setMonitorServiceAddress(String monitorServiceAddress) {
	    this.monitorServiceAddress = monitorServiceAddress; 
	}
        public void setCreateby(String createby) {
	    this.createby = createby; 
	}
        public void setCreatetime(Date createtime) {
	    this.createtime = createtime; 
	}
        public void setUpdateby(String updateby) {
	    this.updateby = updateby; 
	}
        public void setUpdatetime(Date updatetime) {
	    this.updatetime = updatetime; 
	}
        public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitorTerminalItem other = (MonitorTerminalItem) obj;
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


	public MonitorTerminalItem jsonToObject(JSONObject jsonObject) {
            return MonitorTerminalItemJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorTerminalItemJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorTerminalItemJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
