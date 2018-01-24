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
@Table(name = "MONITOR_TERMINAL")
public class MonitorTerminal implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 终端名称
         */
        @Column(name = "NAME_", length=100) 
        protected String name;

        /**
         * 终端类型
         */
        @Column(name = "TYPE_", length=30) 
        protected String type;

        /**
         * 终端描述
         */
        @Column(name = "DESC_", length=150) 
        protected String desc;

        /**
         * 终端级别
         */
        @Column(name = "LEVEL_", length=10) 
        protected String level;

        /**
         * 终端产品品牌
         */
        @Column(name = "PROD_", length=50) 
        protected String prod;

        /**
         * 域名
         */
        @Column(name = "DOMAIN_", length=50) 
        protected String domain;

        /**
         * 地址
         */
        @Column(name = "ADDRESS_", length=50) 
        protected String address;

        /**
         * 监控服务地址
         */
        @Column(name = "MONITOR_SERVICE_ADDRESS_", length=150) 
        protected String monitorServiceAddress;

        /**
         * 状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 平台类型
         */
        @Column(name = "PLATFORM_", length=30) 
        protected String platform;

        /**
         * OS名称
         */
        @Column(name = "OS_NAME_", length=50) 
        protected String osName;

        /**
         * OS厂家
         */
        @Column(name = "OS_FAC_", length=30) 
        protected String osFac;

        /**
         * OS版本
         */
        @Column(name = "OS_VER_", length=30) 
        protected String osVer;

        /**
         * CPU厂家
         */
        @Column(name = "CPU_FAC_", length=30) 
        protected String cpuFac;

        /**
         * CPU核数
         */
        @Column(name = "CPU_CORES_")
        protected Integer cpuCores;

        /**
         * 单核频率
         */
        @Column(name = "CORE_MHZ_")
        protected Integer coreMhz;

        /**
         * 内存类型
         */
        @Column(name = "MEM_TYPE_", length=20) 
        protected String memType;

        /**
         * 内存大小
         */
        @Column(name = "MEM_SIZE_")
        protected Long memSize;

        /**
         * 磁盘类型
         */
        @Column(name = "DISK_TYPE_", length=20) 
        protected String diskType;

        /**
         * 硬盘大小
         */
        @Column(name = "DISK_SIZE_")
        protected Long diskSize;

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


         
	public MonitorTerminal() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getName(){
	    return this.name;
	}
        public String getType(){
	    return this.type;
	}
        public String getDesc(){
	    return this.desc;
	}
        public String getLevel(){
	    return this.level;
	}
        public String getProd(){
	    return this.prod;
	}
        public String getDomain(){
	    return this.domain;
	}
        public String getAddress(){
	    return this.address;
	}
        public String getMonitorServiceAddress(){
	    return this.monitorServiceAddress;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public String getPlatform(){
	    return this.platform;
	}
        public String getOsName(){
	    return this.osName;
	}
        public String getOsFac(){
	    return this.osFac;
	}
        public String getOsVer(){
	    return this.osVer;
	}
        public String getCpuFac(){
	    return this.cpuFac;
	}
        public Integer getCpuCores(){
	    return this.cpuCores;
	}
        public Integer getCoreMhz(){
	    return this.coreMhz;
	}
        public String getMemType(){
	    return this.memType;
	}
        public Long getMemSize(){
	    return this.memSize;
	}
        public String getDiskType(){
	    return this.diskType;
	}
        public Long getDiskSize(){
	    return this.diskSize;
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


        public void setName(String name) {
	    this.name = name; 
	}
        public void setType(String type) {
	    this.type = type; 
	}
        public void setDesc(String desc) {
	    this.desc = desc; 
	}
        public void setLevel(String level) {
	    this.level = level; 
	}
        public void setProd(String prod) {
	    this.prod = prod; 
	}
        public void setDomain(String domain) {
	    this.domain = domain; 
	}
        public void setAddress(String address) {
	    this.address = address; 
	}
        public void setMonitorServiceAddress(String monitorServiceAddress) {
	    this.monitorServiceAddress = monitorServiceAddress; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setPlatform(String platform) {
	    this.platform = platform; 
	}
        public void setOsName(String osName) {
	    this.osName = osName; 
	}
        public void setOsFac(String osFac) {
	    this.osFac = osFac; 
	}
        public void setOsVer(String osVer) {
	    this.osVer = osVer; 
	}
        public void setCpuFac(String cpuFac) {
	    this.cpuFac = cpuFac; 
	}
        public void setCpuCores(Integer cpuCores) {
	    this.cpuCores = cpuCores; 
	}
        public void setCoreMhz(Integer coreMhz) {
	    this.coreMhz = coreMhz; 
	}
        public void setMemType(String memType) {
	    this.memType = memType; 
	}
        public void setMemSize(Long memSize) {
	    this.memSize = memSize; 
	}
        public void setDiskType(String diskType) {
	    this.diskType = diskType; 
	}
        public void setDiskSize(Long diskSize) {
	    this.diskSize = diskSize; 
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
		MonitorTerminal other = (MonitorTerminal) obj;
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


	public MonitorTerminal jsonToObject(JSONObject jsonObject) {
            return MonitorTerminalJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorTerminalJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorTerminalJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
