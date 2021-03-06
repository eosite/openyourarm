package com.glaf.theme.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.theme.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_THEME_TMP_CONTROL_AREA_")
public class SysThemeTmpControlArea implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "CONTROL_AREA_ID_", nullable = false)
        protected String controlAreaId;

        /**
         * 控件唯一标识
         */
        @Column(name = "CONTROL_ID_", length=50) 
        protected String controlId;

        /**
         * 区域控件名称
         */
        @Column(name = "AREA_NAME_", length=30) 
        protected String areaName;

        /**
         * 区域控件代码
         */
        @Column(name = "AREA_CODE_", length=30) 
        protected String areaCode;

        /**
         * 控件类型
         */
        @Column(name = "COMP_TYPE_", length=30) 
        protected String compType;

        /**
         * 元素代码
         */
        @Column(name = "ELEM_CODE_", length=30) 
        protected String elemCode;

        /**
         * 筛选表达式
         */
        @Column(name = "SELECTOR_EXP_", length=300) 
        protected String selectorExp;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * 修改人
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;

        /**
         * 删除标识
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;


         
	public SysThemeTmpControlArea() {

	}

	public String getControlAreaId(){
	    return this.controlAreaId;
	}

	public void setControlAreaId(String ControlAreaId) {
	    this.controlAreaId = ControlAreaId; 
	}


        public String getControlId(){
	    return this.controlId;
	}
        public String getAreaName(){
	    return this.areaName;
	}
        public String getAreaCode(){
	    return this.areaCode;
	}
        public String getCompType(){
	    return this.compType;
	}
        public String getElemCode(){
	    return this.elemCode;
	}
        public String getSelectorExp(){
	    return this.selectorExp;
	}
        public String getCreateBy(){
	    return this.createBy;
	}
	public Date getCreateTime(){
	    return this.createTime;
	}
	public String getCreateTimeString(){
	    if(this.createTime != null){
	        return DateUtils.getDateTime(this.createTime);
	    }
	    return "";
	}
        public String getUpdateBy(){
	    return this.updateBy;
	}
	public Date getUpdateTime(){
	    return this.updateTime;
	}
	public String getUpdateTimeString(){
	    if(this.updateTime != null){
	        return DateUtils.getDateTime(this.updateTime);
	    }
	    return "";
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}


        public void setControlId(String controlId) {
	    this.controlId = controlId; 
	}
        public void setAreaName(String areaName) {
	    this.areaName = areaName; 
	}
        public void setAreaCode(String areaCode) {
	    this.areaCode = areaCode; 
	}
        public void setCompType(String compType) {
	    this.compType = compType; 
	}
        public void setElemCode(String elemCode) {
	    this.elemCode = elemCode; 
	}
        public void setSelectorExp(String selectorExp) {
	    this.selectorExp = selectorExp; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateTime(Date createTime) {
	    this.createTime = createTime; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateTime(Date updateTime) {
	    this.updateTime = updateTime; 
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
		SysThemeTmpControlArea other = (SysThemeTmpControlArea) obj;
		if (controlAreaId == null) {
			if (other.controlAreaId != null)
				return false;
		} else if (!controlAreaId.equals(other.controlAreaId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((controlAreaId == null) ? 0 : controlAreaId.hashCode());
		return result;
	}


	public SysThemeTmpControlArea jsonToObject(JSONObject jsonObject) {
            return SysThemeTmpControlAreaJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SysThemeTmpControlAreaJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SysThemeTmpControlAreaJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
