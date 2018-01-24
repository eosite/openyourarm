package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ObjectCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> categoryIds;
	protected Collection<String> appActorIds;
  	protected String treeID;
  	protected String treeIDLike;
  	protected List<String> treeIDs;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected Integer custom;
  	protected Integer customGreaterThanOrEqual;
  	protected Integer customLessThanOrEqual;
  	protected List<Integer> customs;
  	protected String owner_;
  	protected String ownerLike_;
  	protected List<String> owners_;
  	protected Long parentId;
  	protected Long parentIdGreaterThanOrEqual;
  	protected Long parentIdLessThanOrEqual;
  	protected List<Long> parentIds;
  	protected Integer orderNo;
  	protected Integer orderNoGreaterThanOrEqual;
  	protected Integer orderNoLessThanOrEqual;
  	protected List<Integer> orderNos;
  	protected Boolean level;
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

    public ObjectCategoryQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTreeID(){
        return treeID;
    }

    public String getTreeIDLike(){
	    if (treeIDLike != null && treeIDLike.trim().length() > 0) {
		if (!treeIDLike.startsWith("%")) {
		    treeIDLike = "%" + treeIDLike;
		}
		if (!treeIDLike.endsWith("%")) {
		   treeIDLike = treeIDLike + "%";
		}
	    }
	return treeIDLike;
    }

    public List<String> getTreeIDs(){
	return treeIDs;
    }


    public String getCode(){
        return code;
    }

    public String getCodeLike(){
	    if (codeLike != null && codeLike.trim().length() > 0) {
		if (!codeLike.startsWith("%")) {
		    codeLike = "%" + codeLike;
		}
		if (!codeLike.endsWith("%")) {
		   codeLike = codeLike + "%";
		}
	    }
	return codeLike;
    }

    public List<String> getCodes(){
	return codes;
    }


    public String getName(){
        return name;
    }

    public String getNameLike(){
	    if (nameLike != null && nameLike.trim().length() > 0) {
		if (!nameLike.startsWith("%")) {
		    nameLike = "%" + nameLike;
		}
		if (!nameLike.endsWith("%")) {
		   nameLike = nameLike + "%";
		}
	    }
	return nameLike;
    }

    public List<String> getNames(){
	return names;
    }


    public Integer getCustom(){
        return custom;
    }

    public Integer getCustomGreaterThanOrEqual(){
        return customGreaterThanOrEqual;
    }

    public Integer getCustomLessThanOrEqual(){
	return customLessThanOrEqual;
    }

    public List<Integer> getCustoms(){
	return customs;
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


    public Long getParentId(){
        return parentId;
    }

    public Long getParentIdGreaterThanOrEqual(){
        return parentIdGreaterThanOrEqual;
    }

    public Long getParentIdLessThanOrEqual(){
	return parentIdLessThanOrEqual;
    }

    public List<Long> getParentIds(){
	return parentIds;
    }

    public Integer getOrderNo(){
        return orderNo;
    }

    public Integer getOrderNoGreaterThanOrEqual(){
        return orderNoGreaterThanOrEqual;
    }

    public Integer getOrderNoLessThanOrEqual(){
	return orderNoLessThanOrEqual;
    }

    public List<Integer> getOrderNos(){
	return orderNos;
    }

    public Boolean getLevel(){
        return level;
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


 

    public void setTreeID(String treeID){
        this.treeID = treeID;
    }

    public void setTreeIDLike( String treeIDLike){
	this.treeIDLike = treeIDLike;
    }

    public void setTreeIDs(List<String> treeIDs){
        this.treeIDs = treeIDs;
    }


    public void setCode(String code){
        this.code = code;
    }

    public void setCodeLike( String codeLike){
	this.codeLike = codeLike;
    }

    public void setCodes(List<String> codes){
        this.codes = codes;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setCustom(Integer custom){
        this.custom = custom;
    }

    public void setCustomGreaterThanOrEqual(Integer customGreaterThanOrEqual){
        this.customGreaterThanOrEqual = customGreaterThanOrEqual;
    }

    public void setCustomLessThanOrEqual(Integer customLessThanOrEqual){
	this.customLessThanOrEqual = customLessThanOrEqual;
    }

    public void setCustoms(List<Integer> customs){
        this.customs = customs;
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


    public void setParentId(Long parentId){
        this.parentId = parentId;
    }

    public void setParentIdGreaterThanOrEqual(Long parentIdGreaterThanOrEqual){
        this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
    }

    public void setParentIdLessThanOrEqual(Long parentIdLessThanOrEqual){
	this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
    }

    public void setParentIds(List<Long> parentIds){
        this.parentIds = parentIds;
    }


    public void setOrderNo(Integer orderNo){
        this.orderNo = orderNo;
    }

    public void setOrderNoGreaterThanOrEqual(Integer orderNoGreaterThanOrEqual){
        this.orderNoGreaterThanOrEqual = orderNoGreaterThanOrEqual;
    }

    public void setOrderNoLessThanOrEqual(Integer orderNoLessThanOrEqual){
	this.orderNoLessThanOrEqual = orderNoLessThanOrEqual;
    }

    public void setOrderNos(List<Integer> orderNos){
        this.orderNos = orderNos;
    }


    public void setLevel(Boolean level){
        this.level = level;
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




    public ObjectCategoryQuery treeID(String treeID){
	if (treeID == null) {
	    throw new RuntimeException("treeID is null");
        }         
	this.treeID = treeID;
	return this;
    }

    public ObjectCategoryQuery treeIDLike( String treeIDLike){
        if (treeIDLike == null) {
            throw new RuntimeException("treeID is null");
        }
        this.treeIDLike = treeIDLike;
        return this;
    }

    public ObjectCategoryQuery treeIDs(List<String> treeIDs){
        if (treeIDs == null) {
            throw new RuntimeException("treeIDs is empty ");
        }
        this.treeIDs = treeIDs;
        return this;
    }


    public ObjectCategoryQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public ObjectCategoryQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public ObjectCategoryQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public ObjectCategoryQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public ObjectCategoryQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public ObjectCategoryQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public ObjectCategoryQuery custom(Integer custom){
	if (custom == null) {
            throw new RuntimeException("custom is null");
        }         
	this.custom = custom;
	return this;
    }

    public ObjectCategoryQuery customGreaterThanOrEqual(Integer customGreaterThanOrEqual){
	if (customGreaterThanOrEqual == null) {
	    throw new RuntimeException("custom is null");
        }         
	this.customGreaterThanOrEqual = customGreaterThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery customLessThanOrEqual(Integer customLessThanOrEqual){
        if (customLessThanOrEqual == null) {
            throw new RuntimeException("custom is null");
        }
        this.customLessThanOrEqual = customLessThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery customs(List<Integer> customs){
        if (customs == null) {
            throw new RuntimeException("customs is empty ");
        }
        this.customs = customs;
        return this;
    }


    public ObjectCategoryQuery owner_(String owner_){
	if (owner_ == null) {
	    throw new RuntimeException("owner is null");
        }         
	this.owner_ = owner_;
	return this;
    }

    public ObjectCategoryQuery ownerLike_( String ownerLike_){
        if (ownerLike_ == null) {
            throw new RuntimeException("owner is null");
        }
        this.ownerLike_ = ownerLike_;
        return this;
    }

    public ObjectCategoryQuery owners_(List<String> owners_){
        if (owners_ == null) {
            throw new RuntimeException("owners is empty ");
        }
        this.owners_ = owners_;
        return this;
    }


    public ObjectCategoryQuery parentId(Long parentId){
	if (parentId == null) {
            throw new RuntimeException("parentId is null");
        }         
	this.parentId = parentId;
	return this;
    }

    public ObjectCategoryQuery parentIdGreaterThanOrEqual(Long parentIdGreaterThanOrEqual){
	if (parentIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("parentId is null");
        }         
	this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery parentIdLessThanOrEqual(Long parentIdLessThanOrEqual){
        if (parentIdLessThanOrEqual == null) {
            throw new RuntimeException("parentId is null");
        }
        this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery parentIds(List<Long> parentIds){
        if (parentIds == null) {
            throw new RuntimeException("parentIds is empty ");
        }
        this.parentIds = parentIds;
        return this;
    }


    public ObjectCategoryQuery orderNo(Integer orderNo){
	if (orderNo == null) {
            throw new RuntimeException("orderNo is null");
        }         
	this.orderNo = orderNo;
	return this;
    }

    public ObjectCategoryQuery orderNoGreaterThanOrEqual(Integer orderNoGreaterThanOrEqual){
	if (orderNoGreaterThanOrEqual == null) {
	    throw new RuntimeException("orderNo is null");
        }         
	this.orderNoGreaterThanOrEqual = orderNoGreaterThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery orderNoLessThanOrEqual(Integer orderNoLessThanOrEqual){
        if (orderNoLessThanOrEqual == null) {
            throw new RuntimeException("orderNo is null");
        }
        this.orderNoLessThanOrEqual = orderNoLessThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery orderNos(List<Integer> orderNos){
        if (orderNos == null) {
            throw new RuntimeException("orderNos is empty ");
        }
        this.orderNos = orderNos;
        return this;
    }


    public ObjectCategoryQuery level(Boolean level){
	if (level == null) {
	    throw new RuntimeException("level is null");
        }         
	this.level = level;
	return this;
    }


    public ObjectCategoryQuery delFlag(Integer delFlag){
	if (delFlag == null) {
            throw new RuntimeException("delFlag is null");
        }         
	this.delFlag = delFlag;
	return this;
    }

    public ObjectCategoryQuery delFlagGreaterThanOrEqual(Integer delFlagGreaterThanOrEqual){
	if (delFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("delFlag is null");
        }         
	this.delFlagGreaterThanOrEqual = delFlagGreaterThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery delFlagLessThanOrEqual(Integer delFlagLessThanOrEqual){
        if (delFlagLessThanOrEqual == null) {
            throw new RuntimeException("delFlag is null");
        }
        this.delFlagLessThanOrEqual = delFlagLessThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery delFlags(List<Integer> delFlags){
        if (delFlags == null) {
            throw new RuntimeException("delFlags is empty ");
        }
        this.delFlags = delFlags;
        return this;
    }


    public ObjectCategoryQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ObjectCategoryQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ObjectCategoryQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ObjectCategoryQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public ObjectCategoryQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ObjectCategoryQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ObjectCategoryQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ObjectCategoryQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public ObjectCategoryQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
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

            if ("treeID".equals(sortColumn)) {
                orderBy = "E.TREEID_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("custom".equals(sortColumn)) {
                orderBy = "E.CUSTOM_" + a_x;
            } 

            if ("owner".equals(sortColumn)) {
                orderBy = "E.OWNER_" + a_x;
            } 

            if ("parentId".equals(sortColumn)) {
                orderBy = "E.PARENTID_" + a_x;
            } 

            if ("orderNo".equals(sortColumn)) {
                orderBy = "E.ORDERNO_" + a_x;
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
        addColumn("categoryId", "CATEGORY_ID_");
        addColumn("treeID", "TREEID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("custom", "CUSTOM_");
        addColumn("owner", "OWNER_");
        addColumn("parentId", "PARENTID_");
        addColumn("orderNo", "ORDERNO_");
        addColumn("level", "LEVEL_");
        addColumn("delFlag", "DELFLAG_");
        addColumn("creator", "CREATOR_");
        addColumn("createTime", "CREATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("updateTime", "UPDATETIME_");
    }

}