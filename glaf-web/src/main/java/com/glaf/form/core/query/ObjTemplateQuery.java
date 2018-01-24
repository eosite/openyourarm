package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ObjTemplateQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> templateIds;
	protected Collection<String> appActorIds;
  	protected String tmpName;
  	protected String tmpNameLike;
  	protected List<String> tmpNames;
  	protected String objType;
  	protected String objTypeLike;
  	protected List<String> objTypes;
  	protected String owner_;
  	protected String ownerLike_;
  	protected List<String> owners_;
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
  	protected Long categoryId;
  	protected String treeId;

    public ObjTemplateQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTmpName(){
        return tmpName;
    }

    public String getTmpNameLike(){
	    if (tmpNameLike != null && tmpNameLike.trim().length() > 0) {
		if (!tmpNameLike.startsWith("%")) {
		    tmpNameLike = "%" + tmpNameLike;
		}
		if (!tmpNameLike.endsWith("%")) {
		   tmpNameLike = tmpNameLike + "%";
		}
	    }
	return tmpNameLike;
    }

    public List<String> getTmpNames(){
	return tmpNames;
    }


    public String getObjType(){
        return objType;
    }

    public String getObjTypeLike(){
	    if (objTypeLike != null && objTypeLike.trim().length() > 0) {
		if (!objTypeLike.startsWith("%")) {
		    objTypeLike = "%" + objTypeLike;
		}
		if (!objTypeLike.endsWith("%")) {
		   objTypeLike = objTypeLike + "%";
		}
	    }
	return objTypeLike;
    }

    public List<String> getObjTypes(){
	return objTypes;
    }


    public String getOwner_(){
        return owner_;
    }

    public String getOwnerLike_(){
	    if (ownerLike_ != null && ownerLike_.trim().length() > 0) {
		if (!ownerLike_.startsWith("%")) {
		    ownerLike_ = "%" + ownerLike_;
		}
		if (!ownerLike_.endsWith("%")) {
			ownerLike_ = ownerLike_ + "%";
		}
	    }
	return ownerLike_;
    }

    public List<String> getOwners_(){
	return owners_;
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


 

    public void setTmpName(String tmpName){
        this.tmpName = tmpName;
    }

    public void setTmpNameLike( String tmpNameLike){
	this.tmpNameLike = tmpNameLike;
    }

    public void setTmpNames(List<String> tmpNames){
        this.tmpNames = tmpNames;
    }


    public void setObjType(String objType){
        this.objType = objType;
    }

    public void setObjTypeLike( String objTypeLike){
	this.objTypeLike = objTypeLike;
    }

    public void setObjTypes(List<String> objTypes){
        this.objTypes = objTypes;
    }


    public void setOwner_(String owner_){
        this.owner_ = owner_;
    }

    public void setOwnerLike_( String ownerLike_){
	this.ownerLike_ = ownerLike_;
    }

    public void setOwners_(List<String> owners_){
        this.owners_ = owners_;
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




    public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public ObjTemplateQuery tmpName(String tmpName){
	if (tmpName == null) {
	    throw new RuntimeException("tmpName is null");
        }         
	this.tmpName = tmpName;
	return this;
    }

    public ObjTemplateQuery tmpNameLike( String tmpNameLike){
        if (tmpNameLike == null) {
            throw new RuntimeException("tmpName is null");
        }
        this.tmpNameLike = tmpNameLike;
        return this;
    }

    public ObjTemplateQuery tmpNames(List<String> tmpNames){
        if (tmpNames == null) {
            throw new RuntimeException("tmpNames is empty ");
        }
        this.tmpNames = tmpNames;
        return this;
    }


    public ObjTemplateQuery objType(String objType){
	if (objType == null) {
	    throw new RuntimeException("objType is null");
        }         
	this.objType = objType;
	return this;
    }

    public ObjTemplateQuery objTypeLike( String objTypeLike){
        if (objTypeLike == null) {
            throw new RuntimeException("objType is null");
        }
        this.objTypeLike = objTypeLike;
        return this;
    }

    public ObjTemplateQuery objTypes(List<String> objTypes){
        if (objTypes == null) {
            throw new RuntimeException("objTypes is empty ");
        }
        this.objTypes = objTypes;
        return this;
    }


    public ObjTemplateQuery owner_(String owner_){
	if (owner_ == null) {
	    throw new RuntimeException("owner is null");
        }         
	this.owner_ = owner_;
	return this;
    }

    public ObjTemplateQuery ownerLike_( String ownerLike_){
        if (ownerLike_ == null) {
            throw new RuntimeException("owner is null");
        }
        this.ownerLike_ = ownerLike_;
        return this;
    }

    public ObjTemplateQuery owners_(List<String> owners_){
        if (owners_ == null) {
            throw new RuntimeException("owners is empty ");
        }
        this.owners_ = owners_;
        return this;
    }


    public ObjTemplateQuery delFlag(Integer delFlag){
	if (delFlag == null) {
            throw new RuntimeException("delFlag is null");
        }         
	this.delFlag = delFlag;
	return this;
    }

    public ObjTemplateQuery delFlagGreaterThanOrEqual(Integer delFlagGreaterThanOrEqual){
	if (delFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("delFlag is null");
        }         
	this.delFlagGreaterThanOrEqual = delFlagGreaterThanOrEqual;
        return this;
    }

    public ObjTemplateQuery delFlagLessThanOrEqual(Integer delFlagLessThanOrEqual){
        if (delFlagLessThanOrEqual == null) {
            throw new RuntimeException("delFlag is null");
        }
        this.delFlagLessThanOrEqual = delFlagLessThanOrEqual;
        return this;
    }

    public ObjTemplateQuery delFlags(List<Integer> delFlags){
        if (delFlags == null) {
            throw new RuntimeException("delFlags is empty ");
        }
        this.delFlags = delFlags;
        return this;
    }


    public ObjTemplateQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ObjTemplateQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ObjTemplateQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ObjTemplateQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public ObjTemplateQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public ObjTemplateQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ObjTemplateQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ObjTemplateQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ObjTemplateQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public ObjTemplateQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }
    public ObjTemplateQuery categoryId( Long categoryId){
        if (categoryId == null) {
            throw new RuntimeException("objType is null");
        }
        this.categoryId = categoryId;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("tmpName".equals(sortColumn)) {
                orderBy = "E.TMP_NAME_" + a_x;
            } 

            if ("objType".equals(sortColumn)) {
                orderBy = "E.OBJ_TYPE_" + a_x;
            } 

            if ("owner".equals(sortColumn)) {
                orderBy = "E.OWNER_" + a_x;
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
        addColumn("templateId", "TEMPLATE_ID_");
        addColumn("tmpName", "TMP_NAME_");
        addColumn("objType", "OBJ_TYPE_");
        addColumn("owner", "OWNER_");
        addColumn("delFlag", "DELFLAG_");
        addColumn("creator", "CREATOR_");
        addColumn("createTime", "CREATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("updateTime", "UPDATETIME_");
    }

}