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
@Table(name = "MONITOR_PROC")
public class MonitorProc implements Serializable, JSONable {
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
         * 程序级别
         */
        @Column(name = "LEVEL_", length=10) 
        protected String level;

        /**
         * 程序名
         */
        @Column(name = "PROCESS_NAME_", length=100) 
        protected String processName;

        /**
         * 中文名称
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * 厂家
         */
        @Column(name = "PROD_", length=100) 
        protected String prod;

        /**
         * 版本号
         */
        @Column(name = "VER_", length=20) 
        protected String ver;

        /**
         * 类型
         */
        @Column(name = "TYPE_", length=30) 
        protected String type;

        /**
         * 描述
         */
        @Column(name = "DESC_", length=150) 
        protected String desc;

        /**
         * 端口号
         */
        @Column(name = "PORT_")
        protected Integer port;

        /**
         * 监控服务地址
         */
        @Column(name = "MONITOR_SERVICE_ADDRESS_", length=150) 
        protected String monitorServiceAddress;

        /**
         * 启动服务地址
         */
        @Column(name = "START_ADDRESS_", length=150) 
        protected String startAddress;

        /**
         * 停止服务地址
         */
        @Column(name = "STOP_ADDRESS_", length=150) 
        protected String stopAddress;

        /**
         * 终止服务地址
         */
        @Column(name = "TERMINATE_ADDRESS_", length=150) 
        protected String terminateAddress;

        /**
         * 状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 上级程序
         */
        @Column(name = "PARENT_PROC_ID_", length=50) 
        protected String parentProcId;

        /**
         * 其它指标
         */
        @Column(name = "OTHER_ITEMS_", length=0) 
        protected String otherItems;

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

        /**
         * 启动服务命令
         */
        @Column(name = "START_COMMAND_", length=150) 
        protected String startCommand;

        /**
         * 停止服务命令
         */
        @Column(name = "STOP_COMMAND_", length=150) 
        protected String stopCommand;

        /**
         * 终止服务命令
         */
        @Column(name = "TERMINATE_COMMAND_", length=150) 
        protected String terminateCommand;
         
        
	public String getStartCommand() {
			return startCommand;
		}

		public void setStartCommand(String startCommand) {
			this.startCommand = startCommand;
		}

		public String getStopCommand() {
			return stopCommand;
		}

		public void setStopCommand(String stopCommand) {
			this.stopCommand = stopCommand;
		}

		public String getTerminateCommand() {
			return terminateCommand;
		}

		public void setTerminateCommand(String terminateCommand) {
			this.terminateCommand = terminateCommand;
		}

	public MonitorProc() {

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
        public String getLevel(){
	    return this.level;
	}
        public String getProcessName(){
	    return this.processName;
	}
        public String getName(){
	    return this.name;
	}
        public String getProd(){
	    return this.prod;
	}
        public String getVer(){
	    return this.ver;
	}
        public String getType(){
	    return this.type;
	}
        public String getDesc(){
	    return this.desc;
	}
        public Integer getPort(){
	    return this.port;
	}
        public String getMonitorServiceAddress(){
	    return this.monitorServiceAddress;
	}
        public String getStartAddress(){
	    return this.startAddress;
	}
        public String getStopAddress(){
	    return this.stopAddress;
	}
        public String getTerminateAddress(){
	    return this.terminateAddress;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public String getParentProcId(){
	    return this.parentProcId;
	}
        public String getOtherItems(){
	    return this.otherItems;
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
        public void setLevel(String level) {
	    this.level = level; 
	}
        public void setProcessName(String processName) {
	    this.processName = processName; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setProd(String prod) {
	    this.prod = prod; 
	}
        public void setVer(String ver) {
	    this.ver = ver; 
	}
        public void setType(String type) {
	    this.type = type; 
	}
        public void setDesc(String desc) {
	    this.desc = desc; 
	}
        public void setPort(Integer port) {
	    this.port = port; 
	}
        public void setMonitorServiceAddress(String monitorServiceAddress) {
	    this.monitorServiceAddress = monitorServiceAddress; 
	}
        public void setStartAddress(String startAddress) {
	    this.startAddress = startAddress; 
	}
        public void setStopAddress(String stopAddress) {
	    this.stopAddress = stopAddress; 
	}
        public void setTerminateAddress(String terminateAddress) {
	    this.terminateAddress = terminateAddress; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setParentProcId(String parentProcId) {
	    this.parentProcId = parentProcId; 
	}
        public void setOtherItems(String otherItems) {
	    this.otherItems = otherItems; 
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
		MonitorProc other = (MonitorProc) obj;
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


	public MonitorProc jsonToObject(JSONObject jsonObject) {
            return MonitorProcJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorProcJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorProcJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
