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
@Table(name = "SYS_THEME_TMP_LAYOUT_AREA_CONTROL_")
public class SysThemeTmpLayoutAreaControl implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "CONTROL_ID_", nullable = false)
        protected String controlId;

        /**
         * 主题模板控件唯一标识
         */
        @Column(name = "THEME_TMP_CONTROL_ID_", length=50) 
        protected String themeTmpControlId;

        /**
         * 区域唯一标识
         */
        @Column(name = "AREA_ID_", length=50) 
        protected String areaId;

        /**
         * 布局唯一标识
         */
        @Column(name = "LAYOUT_ID_", length=50) 
        protected String layoutId;

        /**
         * 控件名称
         */
        @Column(name = "CONTROL_NAME_", length=50) 
        protected String controlName;

        /**
         * 控件代码
         */
        @Column(name = "CONTROL_CODE_", length=30) 
        protected String controlCode;

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
         * 缩略图
         */
        @Lob
        @Column(name = "THUMBNAIL_")  
        protected byte[] thumbnail;

        /**
         * 筛选表达式
         */
        @Column(name = "SELECTOR_EXP_", length=300) 
        protected String selectorExp;

        /**
         * 通用控件标识
         */
        @Column(name = "COMMON_FLAG_")
        protected Integer commonFlag;

        /**
         * 容器标识
         */
        @Column(name = "CONTAINER_FLAG_")
        protected Integer containerFlag;

        /**
         * 容器标识
         */
        @Column(name = "P_CONTROL_ID_", length=50) 
        protected String pcontrolId;

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


         
	public SysThemeTmpLayoutAreaControl() {

	}

	public String getControlId(){
	    return this.controlId;
	}

	public void setControlId(String controlId) {
	    this.controlId = controlId; 
	}


        public String getThemeTmpControlId(){
	    return this.themeTmpControlId;
	}
        public String getAreaId(){
	    return this.areaId;
	}
        public String getLayoutId(){
	    return this.layoutId;
	}
        public String getControlName(){
	    return this.controlName;
	}
        public String getControlCode(){
	    return this.controlCode;
	}
        public String getCompType(){
	    return this.compType;
	}
        public String getElemCode(){
	    return this.elemCode;
	}
        public byte[] getThumbnail(){
	    return this.thumbnail;
	}
        public String getSelectorExp(){
	    return this.selectorExp;
	}
        public Integer getCommonFlag(){
	    return this.commonFlag;
	}
        public Integer getContainerFlag(){
	    return this.containerFlag;
	}
        public String getPcontrolId(){
	    return this.pcontrolId;
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


        public void setThemeTmpControlId(String themeTmpControlId) {
	    this.themeTmpControlId = themeTmpControlId; 
	}
        public void setAreaId(String areaId) {
	    this.areaId = areaId; 
	}
        public void setLayoutId(String layoutId) {
	    this.layoutId = layoutId; 
	}
        public void setControlName(String controlName) {
	    this.controlName = controlName; 
	}
        public void setControlCode(String controlCode) {
	    this.controlCode = controlCode; 
	}
        public void setCompType(String compType) {
	    this.compType = compType; 
	}
        public void setElemCode(String elemCode) {
	    this.elemCode = elemCode; 
	}
        public void setThumbnail(byte[] thumbnail) {
	    this.thumbnail = thumbnail; 
	}
        public void setSelectorExp(String selectorExp) {
	    this.selectorExp = selectorExp; 
	}
        public void setCommonFlag(Integer commonFlag) {
	    this.commonFlag = commonFlag; 
	}
        public void setContainerFlag(Integer containerFlag) {
	    this.containerFlag = containerFlag; 
	}
        public void setPcontrolId(String pcontrolId) {
	    this.pcontrolId = pcontrolId; 
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
		SysThemeTmpLayoutAreaControl other = (SysThemeTmpLayoutAreaControl) obj;
		if (controlId == null) {
			if (other.controlId != null)
				return false;
		} else if (!controlId.equals(other.controlId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((controlId == null) ? 0 : controlId.hashCode());
		return result;
	}


	public SysThemeTmpLayoutAreaControl jsonToObject(JSONObject jsonObject) {
            return SysThemeTmpLayoutAreaControlJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SysThemeTmpLayoutAreaControlJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SysThemeTmpLayoutAreaControlJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
