package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Integer> ids;
	protected Collection<String> appActorIds;
  	protected Integer parentId_;
  	protected Integer parentId_GreaterThanOrEqual;
  	protected Integer parentId_LessThanOrEqual;
  	protected List<Integer> parentId_s;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String treeId;
  	protected String treeIdLike;
  	protected List<String> treeIds;
  	protected Integer level;
  	protected Integer levelGreaterThanOrEqual;
  	protected Integer levelLessThanOrEqual;
  	protected List<Integer> levels;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDatetimeGreaterThanOrEqual;
  	protected Date createDatetimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date modifyDatetimeGreaterThanOrEqual;
  	protected Date modifyDatetimeLessThanOrEqual;

    public ReportCategoryQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Integer getParentId_(){
        return parentId_;
    }

    public Integer getParentId_GreaterThanOrEqual(){
        return parentId_GreaterThanOrEqual;
    }

    public Integer getParentId_LessThanOrEqual(){
	return parentId_LessThanOrEqual;
    }

    public List<Integer> getParentId_s(){
	return parentId_s;
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


    public String getTreeId(){
        return treeId;
    }

    public String getTreeIdLike(){
	    if (treeIdLike != null && treeIdLike.trim().length() > 0) {
		if (!treeIdLike.startsWith("%")) {
		    treeIdLike = "%" + treeIdLike;
		}
		if (!treeIdLike.endsWith("%")) {
		   treeIdLike = treeIdLike + "%";
		}
	    }
	return treeIdLike;
    }

    public List<String> getTreeIds(){
	return treeIds;
    }


    public Integer getLevel(){
        return level;
    }

    public Integer getLevelGreaterThanOrEqual(){
        return levelGreaterThanOrEqual;
    }

    public Integer getLevelLessThanOrEqual(){
	return levelLessThanOrEqual;
    }

    public List<Integer> getLevels(){
	return levels;
    }

    public Integer getDeleteFlag(){
        return deleteFlag;
    }

    public Integer getDeleteFlagGreaterThanOrEqual(){
        return deleteFlagGreaterThanOrEqual;
    }

    public Integer getDeleteFlagLessThanOrEqual(){
	return deleteFlagLessThanOrEqual;
    }

    public List<Integer> getDeleteFlags(){
	return deleteFlags;
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


    public Date getCreateDatetimeGreaterThanOrEqual(){
        return createDatetimeGreaterThanOrEqual;
    }

    public Date getCreateDatetimeLessThanOrEqual(){
	return createDatetimeLessThanOrEqual;
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


    public Date getModifyDatetimeGreaterThanOrEqual(){
        return modifyDatetimeGreaterThanOrEqual;
    }

    public Date getModifyDatetimeLessThanOrEqual(){
	return modifyDatetimeLessThanOrEqual;
    }


 

    public void setParentId_(Integer parentId_){
        this.parentId_ = parentId_;
    }

    public void setParentId_GreaterThanOrEqual(Integer parentId_GreaterThanOrEqual){
        this.parentId_GreaterThanOrEqual = parentId_GreaterThanOrEqual;
    }

    public void setParentId_LessThanOrEqual(Integer parentId_LessThanOrEqual){
	this.parentId_LessThanOrEqual = parentId_LessThanOrEqual;
    }

    public void setParentId_s(List<Integer> parentId_s){
        this.parentId_s = parentId_s;
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


    public void setTreeId(String treeId){
        this.treeId = treeId;
    }

    public void setTreeIdLike( String treeIdLike){
	this.treeIdLike = treeIdLike;
    }

    public void setTreeIds(List<String> treeIds){
        this.treeIds = treeIds;
    }


    public void setLevel(Integer level){
        this.level = level;
    }

    public void setLevelGreaterThanOrEqual(Integer levelGreaterThanOrEqual){
        this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
    }

    public void setLevelLessThanOrEqual(Integer levelLessThanOrEqual){
	this.levelLessThanOrEqual = levelLessThanOrEqual;
    }

    public void setLevels(List<Integer> levels){
        this.levels = levels;
    }


    public void setDeleteFlag(Integer deleteFlag){
        this.deleteFlag = deleteFlag;
    }

    public void setDeleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
        this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
    }

    public void setDeleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
	this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
    }

    public void setDeleteFlags(List<Integer> deleteFlags){
        this.deleteFlags = deleteFlags;
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


    public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
        this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
    }

    public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
	this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
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


    public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
        this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
    }

    public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
	this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
    }




    public ReportCategoryQuery parentId_(Integer parentId_){
	if (parentId_ == null) {
            throw new RuntimeException("parentId_ is null");
        }         
	this.parentId_ = parentId_;
	return this;
    }

    public ReportCategoryQuery parentId_GreaterThanOrEqual(Integer parentId_GreaterThanOrEqual){
	if (parentId_GreaterThanOrEqual == null) {
	    throw new RuntimeException("parentId_ is null");
        }         
	this.parentId_GreaterThanOrEqual = parentId_GreaterThanOrEqual;
        return this;
    }

    public ReportCategoryQuery parentId_LessThanOrEqual(Integer parentId_LessThanOrEqual){
        if (parentId_LessThanOrEqual == null) {
            throw new RuntimeException("parentId_ is null");
        }
        this.parentId_LessThanOrEqual = parentId_LessThanOrEqual;
        return this;
    }

    public ReportCategoryQuery parentId_s(List<Integer> parentId_s){
        if (parentId_s == null) {
            throw new RuntimeException("parentId_s is empty ");
        }
        this.parentId_s = parentId_s;
        return this;
    }


    public ReportCategoryQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public ReportCategoryQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public ReportCategoryQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public ReportCategoryQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public ReportCategoryQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public ReportCategoryQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public ReportCategoryQuery treeId(String treeId){
	if (treeId == null) {
	    throw new RuntimeException("treeId is null");
        }         
	this.treeId = treeId;
	return this;
    }

    public ReportCategoryQuery treeIdLike( String treeIdLike){
        if (treeIdLike == null) {
            throw new RuntimeException("treeId is null");
        }
        this.treeIdLike = treeIdLike;
        return this;
    }

    public ReportCategoryQuery treeIds(List<String> treeIds){
        if (treeIds == null) {
            throw new RuntimeException("treeIds is empty ");
        }
        this.treeIds = treeIds;
        return this;
    }


    public ReportCategoryQuery level(Integer level){
	if (level == null) {
            throw new RuntimeException("level is null");
        }         
	this.level = level;
	return this;
    }

    public ReportCategoryQuery levelGreaterThanOrEqual(Integer levelGreaterThanOrEqual){
	if (levelGreaterThanOrEqual == null) {
	    throw new RuntimeException("level is null");
        }         
	this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
        return this;
    }

    public ReportCategoryQuery levelLessThanOrEqual(Integer levelLessThanOrEqual){
        if (levelLessThanOrEqual == null) {
            throw new RuntimeException("level is null");
        }
        this.levelLessThanOrEqual = levelLessThanOrEqual;
        return this;
    }

    public ReportCategoryQuery levels(List<Integer> levels){
        if (levels == null) {
            throw new RuntimeException("levels is empty ");
        }
        this.levels = levels;
        return this;
    }


    public ReportCategoryQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public ReportCategoryQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public ReportCategoryQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public ReportCategoryQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }


    public ReportCategoryQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportCategoryQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportCategoryQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportCategoryQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportCategoryQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ReportCategoryQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportCategoryQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportCategoryQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportCategoryQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportCategoryQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
        if (modifyDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDatetime is null");
        }
        this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("parentId_".equals(sortColumn)) {
                orderBy = "E.PARENT_ID_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("treeId".equals(sortColumn)) {
                orderBy = "E.TREE_ID_" + a_x;
            } 

            if ("level".equals(sortColumn)) {
                orderBy = "E.LEVEL_" + a_x;
            } 

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETE_FLAG_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDatetime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("modifyDatetime".equals(sortColumn)) {
                orderBy = "E.MODIFYDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("parentId_", "PARENT_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("treeId", "TREE_ID_");
        addColumn("level", "LEVEL_");
        addColumn("deleteFlag", "DELETE_FLAG_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}