package com.glaf.workflow.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProcessDefMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> iDs;
	protected Collection<String> appActorIds;
  	protected String srcProcDefId;
  	protected String srcProcDefIdLike;
  	protected List<String> srcProcDefIds;
  	protected String desProcDefId;
  	protected String desProcDefIdLike;
  	protected List<String> desProcDefIds;
  	protected String srcSysId;
  	protected String srcSysIdLike;
  	protected List<String> srcSysIds;
  	protected String desSysId;
  	protected String desSysIdLike;
  	protected List<String> desSysIds;
        protected Date createDatetimeGreaterThanOrEqual;
  	protected Date createDatetimeLessThanOrEqual;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;

    public ProcessDefMappingQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getSrcProcDefId(){
        return srcProcDefId;
    }

    public String getSrcProcDefIdLike(){
	    if (srcProcDefIdLike != null && srcProcDefIdLike.trim().length() > 0) {
		if (!srcProcDefIdLike.startsWith("%")) {
		    srcProcDefIdLike = "%" + srcProcDefIdLike;
		}
		if (!srcProcDefIdLike.endsWith("%")) {
		   srcProcDefIdLike = srcProcDefIdLike + "%";
		}
	    }
	return srcProcDefIdLike;
    }

    public List<String> getSrcProcDefIds(){
	return srcProcDefIds;
    }


    public String getDesProcDefId(){
        return desProcDefId;
    }

    public String getDesProcDefIdLike(){
	    if (desProcDefIdLike != null && desProcDefIdLike.trim().length() > 0) {
		if (!desProcDefIdLike.startsWith("%")) {
		    desProcDefIdLike = "%" + desProcDefIdLike;
		}
		if (!desProcDefIdLike.endsWith("%")) {
		   desProcDefIdLike = desProcDefIdLike + "%";
		}
	    }
	return desProcDefIdLike;
    }

    public List<String> getDesProcDefIds(){
	return desProcDefIds;
    }


    public String getSrcSysId(){
        return srcSysId;
    }

    public String getSrcSysIdLike(){
	    if (srcSysIdLike != null && srcSysIdLike.trim().length() > 0) {
		if (!srcSysIdLike.startsWith("%")) {
		    srcSysIdLike = "%" + srcSysIdLike;
		}
		if (!srcSysIdLike.endsWith("%")) {
		   srcSysIdLike = srcSysIdLike + "%";
		}
	    }
	return srcSysIdLike;
    }

    public List<String> getSrcSysIds(){
	return srcSysIds;
    }


    public String getDesSysId(){
        return desSysId;
    }

    public String getDesSysIdLike(){
	    if (desSysIdLike != null && desSysIdLike.trim().length() > 0) {
		if (!desSysIdLike.startsWith("%")) {
		    desSysIdLike = "%" + desSysIdLike;
		}
		if (!desSysIdLike.endsWith("%")) {
		   desSysIdLike = desSysIdLike + "%";
		}
	    }
	return desSysIdLike;
    }

    public List<String> getDesSysIds(){
	return desSysIds;
    }


    public Date getCreateDatetimeGreaterThanOrEqual(){
        return createDatetimeGreaterThanOrEqual;
    }

    public Date getCreateDatetimeLessThanOrEqual(){
	return createDatetimeLessThanOrEqual;
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


 

    public void setSrcProcDefId(String srcProcDefId){
        this.srcProcDefId = srcProcDefId;
    }

    public void setSrcProcDefIdLike( String srcProcDefIdLike){
	this.srcProcDefIdLike = srcProcDefIdLike;
    }

    public void setSrcProcDefIds(List<String> srcProcDefIds){
        this.srcProcDefIds = srcProcDefIds;
    }


    public void setDesProcDefId(String desProcDefId){
        this.desProcDefId = desProcDefId;
    }

    public void setDesProcDefIdLike( String desProcDefIdLike){
	this.desProcDefIdLike = desProcDefIdLike;
    }

    public void setDesProcDefIds(List<String> desProcDefIds){
        this.desProcDefIds = desProcDefIds;
    }


    public void setSrcSysId(String srcSysId){
        this.srcSysId = srcSysId;
    }

    public void setSrcSysIdLike( String srcSysIdLike){
	this.srcSysIdLike = srcSysIdLike;
    }

    public void setSrcSysIds(List<String> srcSysIds){
        this.srcSysIds = srcSysIds;
    }


    public void setDesSysId(String desSysId){
        this.desSysId = desSysId;
    }

    public void setDesSysIdLike( String desSysIdLike){
	this.desSysIdLike = desSysIdLike;
    }

    public void setDesSysIds(List<String> desSysIds){
        this.desSysIds = desSysIds;
    }


    public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
        this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
    }

    public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
	this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
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




    public ProcessDefMappingQuery srcProcDefId(String srcProcDefId){
	if (srcProcDefId == null) {
	    throw new RuntimeException("srcProcDefId is null");
        }         
	this.srcProcDefId = srcProcDefId;
	return this;
    }

    public ProcessDefMappingQuery srcProcDefIdLike( String srcProcDefIdLike){
        if (srcProcDefIdLike == null) {
            throw new RuntimeException("srcProcDefId is null");
        }
        this.srcProcDefIdLike = srcProcDefIdLike;
        return this;
    }

    public ProcessDefMappingQuery srcProcDefIds(List<String> srcProcDefIds){
        if (srcProcDefIds == null) {
            throw new RuntimeException("srcProcDefIds is empty ");
        }
        this.srcProcDefIds = srcProcDefIds;
        return this;
    }


    public ProcessDefMappingQuery desProcDefId(String desProcDefId){
	if (desProcDefId == null) {
	    throw new RuntimeException("desProcDefId is null");
        }         
	this.desProcDefId = desProcDefId;
	return this;
    }

    public ProcessDefMappingQuery desProcDefIdLike( String desProcDefIdLike){
        if (desProcDefIdLike == null) {
            throw new RuntimeException("desProcDefId is null");
        }
        this.desProcDefIdLike = desProcDefIdLike;
        return this;
    }

    public ProcessDefMappingQuery desProcDefIds(List<String> desProcDefIds){
        if (desProcDefIds == null) {
            throw new RuntimeException("desProcDefIds is empty ");
        }
        this.desProcDefIds = desProcDefIds;
        return this;
    }


    public ProcessDefMappingQuery srcSysId(String srcSysId){
	if (srcSysId == null) {
	    throw new RuntimeException("srcSysId is null");
        }         
	this.srcSysId = srcSysId;
	return this;
    }

    public ProcessDefMappingQuery srcSysIdLike( String srcSysIdLike){
        if (srcSysIdLike == null) {
            throw new RuntimeException("srcSysId is null");
        }
        this.srcSysIdLike = srcSysIdLike;
        return this;
    }

    public ProcessDefMappingQuery srcSysIds(List<String> srcSysIds){
        if (srcSysIds == null) {
            throw new RuntimeException("srcSysIds is empty ");
        }
        this.srcSysIds = srcSysIds;
        return this;
    }


    public ProcessDefMappingQuery desSysId(String desSysId){
	if (desSysId == null) {
	    throw new RuntimeException("desSysId is null");
        }         
	this.desSysId = desSysId;
	return this;
    }

    public ProcessDefMappingQuery desSysIdLike( String desSysIdLike){
        if (desSysIdLike == null) {
            throw new RuntimeException("desSysId is null");
        }
        this.desSysIdLike = desSysIdLike;
        return this;
    }

    public ProcessDefMappingQuery desSysIds(List<String> desSysIds){
        if (desSysIds == null) {
            throw new RuntimeException("desSysIds is empty ");
        }
        this.desSysIds = desSysIds;
        return this;
    }



    public ProcessDefMappingQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ProcessDefMappingQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ProcessDefMappingQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ProcessDefMappingQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ProcessDefMappingQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("srcProcDefId".equals(sortColumn)) {
                orderBy = "E.SRC_PROC_DEF_ID_" + a_x;
            } 

            if ("desProcDefId".equals(sortColumn)) {
                orderBy = "E.DES_PROC_DEF_ID_" + a_x;
            } 

            if ("srcSysId".equals(sortColumn)) {
                orderBy = "E.SRC_SYS_ID_" + a_x;
            } 

            if ("desSysId".equals(sortColumn)) {
                orderBy = "E.DES_SYS_ID_" + a_x;
            } 

            if ("createDatetime".equals(sortColumn)) {
                orderBy = "E.CREAT_DATETIME_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("iD", "ID_");
        addColumn("srcProcDefId", "SRC_PROC_DEF_ID_");
        addColumn("desProcDefId", "DES_PROC_DEF_ID_");
        addColumn("srcSysId", "SRC_SYS_ID_");
        addColumn("desSysId", "DES_SYS_ID_");
        addColumn("createDatetime", "CREAT_DATETIME_");
        addColumn("creator", "CREATOR_");
    }

}