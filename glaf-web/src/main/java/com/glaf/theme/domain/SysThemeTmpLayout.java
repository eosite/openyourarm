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
@Table(name = "SYS_THEME_TMP_LAYOUT_")
public class SysThemeTmpLayout implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "LAYOUT_ID_", nullable = false)
        protected String layoutId;

        /**
         * 模板唯一标识
         */
        @Column(name = "THEME_TMP_ID_", length=50) 
        protected String themeTmpId;

        /**
         * 布局名称
         */
        @Column(name = "LAYOUT_NAME_", length=50) 
        protected String layoutName;

        /**
         * 布局方案
         */
        @Column(name = "LAYOUT_PLAN_", length=50) 
        protected String layoutPlan;

        /**
         * 布局代码
         */
        @Column(name = "LAYOUT_CODE_", length=30) 
        protected String layoutCode;

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


         
	public SysThemeTmpLayout() {

	}

	public String getLayoutId(){
	    return this.layoutId;
	}

	public void setLayoutId(String layoutId) {
	    this.layoutId = layoutId; 
	}


        public String getThemeTmpId(){
	    return this.themeTmpId;
	}
        public String getLayoutName(){
	    return this.layoutName;
	}
        public String getLayoutPlan(){
	    return this.layoutPlan;
	}
        public String getLayoutCode(){
	    return this.layoutCode;
	}
        public byte[] getThumbnail(){
	    return this.thumbnail;
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


        public void setThemeTmpId(String themeTmpId) {
	    this.themeTmpId = themeTmpId; 
	}
        public void setLayoutName(String layoutName) {
	    this.layoutName = layoutName; 
	}
        public void setLayoutPlan(String layoutPlan) {
	    this.layoutPlan = layoutPlan; 
	}
        public void setLayoutCode(String layoutCode) {
	    this.layoutCode = layoutCode; 
	}
        public void setThumbnail(byte[] thumbnail) {
	    this.thumbnail = thumbnail; 
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
		SysThemeTmpLayout other = (SysThemeTmpLayout) obj;
		if (layoutId == null) {
			if (other.layoutId != null)
				return false;
		} else if (!layoutId.equals(other.layoutId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((layoutId == null) ? 0 : layoutId.hashCode());
		return result;
	}


	public SysThemeTmpLayout jsonToObject(JSONObject jsonObject) {
            return SysThemeTmpLayoutJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SysThemeTmpLayoutJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SysThemeTmpLayoutJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
