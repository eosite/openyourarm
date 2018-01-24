package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ObjectTemplateStyleQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> styleIds;
	protected Collection<String> appActorIds;
  	protected Integer delFlag;
  	protected Integer delFlagGreaterThanOrEqual;
  	protected Integer delFlagLessThanOrEqual;
  	protected List<Integer> delFlags;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createTimeGreaterThanOrEqual;
  	protected Date createTimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date updateTimeGreaterThanOrEqual;
  	protected Date updateTimeLessThanOrEqual;

    public ObjectTemplateStyleQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Integer getDelFlag(){
        return delFlag;
    }

    public Integer getDelFlagGreaterThanOrEqual(){
        return delFlagGreaterThanOrEqual;
    }

    public Integer getDelFlagLessThanOrEqual(){
	return delFlagLessThanOrEqual;
    }

    public List<Integer> getDelFlags(){
	return delFlags;
    }

    public String getCreator(){
        return creator;
    }

    public String getCreatorLike(){
	    if (creatorLike != null && creatorLike.trim().length() > 0) {
		if (!creatorLike.startsWith("%")) {
		    creatorLike = "%" + creatorLike;
		}
		if (!creatorLike.endsWith("%")) {
		   creatorLike = creatorLike + "%";
		}
	    }
	return creatorLike;
    }

    public List<String> getCreators(){
	return creators;
    }


    public Date getCreateTimeGreaterThanOrEqual(){
        return createTimeGreaterThanOrEqual;
    }

    public Date getCreateTimeLessThanOrEqual(){
	return createTimeLessThanOrEqual;
    }


    public String getModifier(){
        return modifier;
    }

    public String getModifierLike(){
	    if (modifierLike != null && modifierLike.trim().length() > 0) {
		if (!modifierLike.startsWith("%")) {
		    modifierLike = "%" + modifierLike;
		}
		if (!modifierLike.endsWith("%")) {
		   modifierLike = modifierLike + "%";
		}
	    }
	return modifierLike;
    }

    public List<String> getModifiers(){
	return modifiers;
    }


    public Date getUpdateTimeGreaterThanOrEqual(){
        return updateTimeGreaterThanOrEqual;
    }

    public Date getUpdateTimeLessThanOrEqual(){
	return updateTimeLessThanOrEqual;
    }


 

    public void setDelFlag(Integer delFlag){
        this.delFlag = delFlag;
    }

    public void setDelFlagGreaterThanOrEqual(Integer delFlagGreaterThanOrEqual){
        this.delFlagGreaterThanOrEqual = delFlagGreaterThanOrEqual;
    }

    public void setDelFlagLessThanOrEqual(Integer delFlagLessThanOrEqual){
	this.delFlagLessThanOrEqual = delFlagLessThanOrEqual;
    }

    public void setDelFlags(List<Integer> delFlags){
        this.delFlags = delFlags;
    }


    public void setCreator(String creator){
        this.creator = creator;
    }

    public void setCreatorLike( String creatorLike){
	this.creatorLike = creatorLike;
    }

    public void setCreators(List<String> creators){
        this.creators = creators;
    }


    public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
        this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
    }

    public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
	this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
    }


    public void setModifier(String modifier){
        this.modifier = modifier;
    }

    public void setModifierLike( String modifierLike){
	this.modifierLike = modifierLike;
    }

    public void setModifiers(List<String> modifiers){
        this.modifiers = modifiers;
    }


    public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
        this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
    }

    public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
	this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
    }




    public ObjectTemplateStyleQuery delFlag(Integer delFlag){
	if (delFlag == null) {
            throw new RuntimeException("delFlag is null");
        }         
	this.delFlag = delFlag;
	return this;
    }

    public ObjectTemplateStyleQuery delFlagGreaterThanOrEqual(Integer delFlagGreaterThanOrEqual){
	if (delFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("delFlag is null");
        }         
	this.delFlagGreaterThanOrEqual = delFlagGreaterThanOrEqual;
        return this;
    }

    public ObjectTemplateStyleQuery delFlagLessThanOrEqual(Integer delFlagLessThanOrEqual){
        if (delFlagLessThanOrEqual == null) {
            throw new RuntimeException("delFlag is null");
        }
        this.delFlagLessThanOrEqual = delFlagLessThanOrEqual;
        return this;
    }

    public ObjectTemplateStyleQuery delFlags(List<Integer> delFlags){
        if (delFlags == null) {
            throw new RuntimeException("delFlags is empty ");
        }
        this.delFlags = delFlags;
        return this;
    }


    public ObjectTemplateStyleQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ObjectTemplateStyleQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ObjectTemplateStyleQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ObjectTemplateStyleQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public ObjectTemplateStyleQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public ObjectTemplateStyleQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ObjectTemplateStyleQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ObjectTemplateStyleQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ObjectTemplateStyleQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public ObjectTemplateStyleQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("delFlag".equals(sortColumn)) {
                orderBy = "E.DELFLAG_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createTime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("updateTime".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("styleId", "STYLE_ID_");
        addColumn("delFlag", "DELFLAG_");
        addColumn("creator", "CREATOR_");
        addColumn("createTime", "CREATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("updateTime", "UPDATETIME_");
    }

}