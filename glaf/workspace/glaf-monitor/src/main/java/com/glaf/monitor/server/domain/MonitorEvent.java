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
@Table(name = "MONITOR_EVENT_")
public class MonitorEvent implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "EVENT_ID_", nullable = false)
        protected String eventId;

        /**
         * 监控对象程序ID/终端ID
         */
        @Column(name = "OBJECT_ID_", length=50) 
        protected String objectId;

        /**
         * 监控对象类型
         */
        @Column(name = "OBJECT_TYPE_", length=20) 
        protected String objectType;

        /**
         * 事件类型
         */
        @Column(name = "EVENT_TYPE_", length=20) 
        protected String eventType;

        /**
         * 事件指标项
         */
        @Column(name = "EVENT_MONITOR_ITEM_", length=50) 
        protected String eventMonitorItem;

        /**
         * 发生时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "HAPPEN_TIME_")	
        protected Date happenTime;

        /**
         * 快照
         */
        @Column(name = "SNAPSHOT_", length=0) 
        protected String snapshot;

        /**
         * 恢复时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "RECOVERY_TIME_")	
        protected Date recoveryTime;

        /**
         * 状态
         */
        @Column(name = "STATUS")
        protected Integer status;

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


         
	public MonitorEvent() {

	}

	public String getEventId(){
	    return this.eventId;
	}

	public void setEventId(String eventId) {
	    this.eventId = eventId; 
	}


        public String getObjectId(){
	    return this.objectId;
	}
        public String getObjectType(){
	    return this.objectType;
	}
        public String getEventType(){
	    return this.eventType;
	}
        public String getEventMonitorItem(){
	    return this.eventMonitorItem;
	}
	public Date getHappenTime(){
	    return this.happenTime;
	}
	public String getHappenTimeString(){
	    if(this.happenTime != null){
	        return DateUtils.getDateTime(this.happenTime);
	    }
	    return "";
	}
        public String getSnapshot(){
	    return this.snapshot;
	}
	public Date getRecoveryTime(){
	    return this.recoveryTime;
	}
	public String getRecoveryTimeString(){
	    if(this.recoveryTime != null){
	        return DateUtils.getDateTime(this.recoveryTime);
	    }
	    return "";
	}
        public Integer getStatus(){
	    return this.status;
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


        public void setObjectId(String objectId) {
	    this.objectId = objectId; 
	}
        public void setObjectType(String objectType) {
	    this.objectType = objectType; 
	}
        public void setEventType(String eventType) {
	    this.eventType = eventType; 
	}
        public void setEventMonitorItem(String eventMonitorItem) {
	    this.eventMonitorItem = eventMonitorItem; 
	}
        public void setHappenTime(Date happenTime) {
	    this.happenTime = happenTime; 
	}
        public void setSnapshot(String snapshot) {
	    this.snapshot = snapshot; 
	}
        public void setRecoveryTime(Date recoveryTime) {
	    this.recoveryTime = recoveryTime; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
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
		MonitorEvent other = (MonitorEvent) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventId == null) ? 0 : eventId.hashCode());
		return result;
	}


	public MonitorEvent jsonToObject(JSONObject jsonObject) {
            return MonitorEventJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorEventJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorEventJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
