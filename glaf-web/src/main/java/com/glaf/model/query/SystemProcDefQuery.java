package com.glaf.model.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SystemProcDefQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String funcId;
  	protected List<String> funcIds;
  	protected String sysId;
  	protected List<String> sysIds;
  	protected String subSysId;
  	protected List<String> subSysIds;
  	protected String currProcDefKey;
  	protected String currProcDefKeyLike;
  	protected List<String> currProcDefKeys;
  	protected String currProcDefId;
  	protected String currProcDefIdLike;
  	protected List<String> currProcDefIds;
  	protected String currProcModelId;
  	protected String currProcModelIdLike;
  	protected List<String> currProcModelIds;
  	protected String currProcDeployId;
  	protected String currProcDeployIdLike;
  	protected List<String> currProcDeployIds;
  	protected String currProcDeployStatus;
  	protected String currProcDeployStatusLike;
  	protected List<String> currProcDeployStatuss;
  	protected String procDefKey;
  	protected String procDefKeyLike;
  	protected List<String> procDefKeys;
  	protected String procDefId;
  	protected String procDefIdLike;
  	protected List<String> procDefIds;
  	protected String procModelId;
  	protected String procModelIdLike;
  	protected List<String> procModelIds;
  	protected String procDeployId;
  	protected String procDeployIdLike;
  	protected List<String> procDeployIds;
  	protected String procDeployStatus;
  	protected String procDeployStatusLike;
  	protected List<String> procDeployStatuss;
  	protected String eleType;
  	protected String eleTypeLike;
  	protected List<String> eleTypes;
  	protected String eleResourceId;
  	protected String eleResourceIdLike;
  	protected List<String> eleResourceIds;
  	protected String eleId;
  	protected String eleIdLike;
  	protected List<String> eleIds;
  	protected String eleName;
  	protected String eleNameLike;
  	protected List<String> eleNames;
  	protected String eleDesc;
  	protected String eleDescLike;
  	protected List<String> eleDescs;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createTimeGreaterThanOrEqual;
  	protected Date createTimeLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateTimeGreaterThanOrEqual;
  	protected Date updateTimeLessThanOrEqual;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;

    public SystemProcDefQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getFuncId(){
        return funcId;
    }

    public List<String> getFuncIds(){
	return funcIds;
    }

    public String getSysId(){
        return sysId;
    }

    public List<String> getSysIds(){
	return sysIds;
    }

    public String getSubSysId(){
        return subSysId;
    }

    public List<String> getSubSysIds(){
	return subSysIds;
    }

    public String getCurrProcDefKey(){
        return currProcDefKey;
    }

    public String getCurrProcDefKeyLike(){
	    if (currProcDefKeyLike != null && currProcDefKeyLike.trim().length() > 0) {
		if (!currProcDefKeyLike.startsWith("%")) {
		    currProcDefKeyLike = "%" + currProcDefKeyLike;
		}
		if (!currProcDefKeyLike.endsWith("%")) {
		   currProcDefKeyLike = currProcDefKeyLike + "%";
		}
	    }
	return currProcDefKeyLike;
    }

    public List<String> getCurrProcDefKeys(){
	return currProcDefKeys;
    }


    public String getCurrProcDefId(){
        return currProcDefId;
    }

    public String getCurrProcDefIdLike(){
	    if (currProcDefIdLike != null && currProcDefIdLike.trim().length() > 0) {
		if (!currProcDefIdLike.startsWith("%")) {
		    currProcDefIdLike = "%" + currProcDefIdLike;
		}
		if (!currProcDefIdLike.endsWith("%")) {
		   currProcDefIdLike = currProcDefIdLike + "%";
		}
	    }
	return currProcDefIdLike;
    }

    public List<String> getCurrProcDefIds(){
	return currProcDefIds;
    }


    public String getCurrProcModelId(){
        return currProcModelId;
    }

    public String getCurrProcModelIdLike(){
	    if (currProcModelIdLike != null && currProcModelIdLike.trim().length() > 0) {
		if (!currProcModelIdLike.startsWith("%")) {
		    currProcModelIdLike = "%" + currProcModelIdLike;
		}
		if (!currProcModelIdLike.endsWith("%")) {
		   currProcModelIdLike = currProcModelIdLike + "%";
		}
	    }
	return currProcModelIdLike;
    }

    public List<String> getCurrProcModelIds(){
	return currProcModelIds;
    }


    public String getCurrProcDeployId(){
        return currProcDeployId;
    }

    public String getCurrProcDeployIdLike(){
	    if (currProcDeployIdLike != null && currProcDeployIdLike.trim().length() > 0) {
		if (!currProcDeployIdLike.startsWith("%")) {
		    currProcDeployIdLike = "%" + currProcDeployIdLike;
		}
		if (!currProcDeployIdLike.endsWith("%")) {
		   currProcDeployIdLike = currProcDeployIdLike + "%";
		}
	    }
	return currProcDeployIdLike;
    }

    public List<String> getCurrProcDeployIds(){
	return currProcDeployIds;
    }


    public String getCurrProcDeployStatus(){
        return currProcDeployStatus;
    }

    public String getCurrProcDeployStatusLike(){
	    if (currProcDeployStatusLike != null && currProcDeployStatusLike.trim().length() > 0) {
		if (!currProcDeployStatusLike.startsWith("%")) {
		    currProcDeployStatusLike = "%" + currProcDeployStatusLike;
		}
		if (!currProcDeployStatusLike.endsWith("%")) {
		   currProcDeployStatusLike = currProcDeployStatusLike + "%";
		}
	    }
	return currProcDeployStatusLike;
    }

    public List<String> getCurrProcDeployStatuss(){
	return currProcDeployStatuss;
    }


    public String getProcDefKey(){
        return procDefKey;
    }

    public String getProcDefKeyLike(){
	    if (procDefKeyLike != null && procDefKeyLike.trim().length() > 0) {
		if (!procDefKeyLike.startsWith("%")) {
		    procDefKeyLike = "%" + procDefKeyLike;
		}
		if (!procDefKeyLike.endsWith("%")) {
		   procDefKeyLike = procDefKeyLike + "%";
		}
	    }
	return procDefKeyLike;
    }

    public List<String> getProcDefKeys(){
	return procDefKeys;
    }


    public String getProcDefId(){
        return procDefId;
    }

    public String getProcDefIdLike(){
	    if (procDefIdLike != null && procDefIdLike.trim().length() > 0) {
		if (!procDefIdLike.startsWith("%")) {
		    procDefIdLike = "%" + procDefIdLike;
		}
		if (!procDefIdLike.endsWith("%")) {
		   procDefIdLike = procDefIdLike + "%";
		}
	    }
	return procDefIdLike;
    }

    public List<String> getProcDefIds(){
	return procDefIds;
    }


    public String getProcModelId(){
        return procModelId;
    }

    public String getProcModelIdLike(){
	    if (procModelIdLike != null && procModelIdLike.trim().length() > 0) {
		if (!procModelIdLike.startsWith("%")) {
		    procModelIdLike = "%" + procModelIdLike;
		}
		if (!procModelIdLike.endsWith("%")) {
		   procModelIdLike = procModelIdLike + "%";
		}
	    }
	return procModelIdLike;
    }

    public List<String> getProcModelIds(){
	return procModelIds;
    }


    public String getProcDeployId(){
        return procDeployId;
    }

    public String getProcDeployIdLike(){
	    if (procDeployIdLike != null && procDeployIdLike.trim().length() > 0) {
		if (!procDeployIdLike.startsWith("%")) {
		    procDeployIdLike = "%" + procDeployIdLike;
		}
		if (!procDeployIdLike.endsWith("%")) {
		   procDeployIdLike = procDeployIdLike + "%";
		}
	    }
	return procDeployIdLike;
    }

    public List<String> getProcDeployIds(){
	return procDeployIds;
    }


    public String getProcDeployStatus(){
        return procDeployStatus;
    }

    public String getProcDeployStatusLike(){
	    if (procDeployStatusLike != null && procDeployStatusLike.trim().length() > 0) {
		if (!procDeployStatusLike.startsWith("%")) {
		    procDeployStatusLike = "%" + procDeployStatusLike;
		}
		if (!procDeployStatusLike.endsWith("%")) {
		   procDeployStatusLike = procDeployStatusLike + "%";
		}
	    }
	return procDeployStatusLike;
    }

    public List<String> getProcDeployStatuss(){
	return procDeployStatuss;
    }


    public String getEleType(){
        return eleType;
    }

    public String getEleTypeLike(){
	    if (eleTypeLike != null && eleTypeLike.trim().length() > 0) {
		if (!eleTypeLike.startsWith("%")) {
		    eleTypeLike = "%" + eleTypeLike;
		}
		if (!eleTypeLike.endsWith("%")) {
		   eleTypeLike = eleTypeLike + "%";
		}
	    }
	return eleTypeLike;
    }

    public List<String> getEleTypes(){
	return eleTypes;
    }


    public String getEleResourceId(){
        return eleResourceId;
    }

    public String getEleResourceIdLike(){
	    if (eleResourceIdLike != null && eleResourceIdLike.trim().length() > 0) {
		if (!eleResourceIdLike.startsWith("%")) {
		    eleResourceIdLike = "%" + eleResourceIdLike;
		}
		if (!eleResourceIdLike.endsWith("%")) {
		   eleResourceIdLike = eleResourceIdLike + "%";
		}
	    }
	return eleResourceIdLike;
    }

    public List<String> getEleResourceIds(){
	return eleResourceIds;
    }


    public String getEleId(){
        return eleId;
    }

    public String getEleIdLike(){
	    if (eleIdLike != null && eleIdLike.trim().length() > 0) {
		if (!eleIdLike.startsWith("%")) {
		    eleIdLike = "%" + eleIdLike;
		}
		if (!eleIdLike.endsWith("%")) {
		   eleIdLike = eleIdLike + "%";
		}
	    }
	return eleIdLike;
    }

    public List<String> getEleIds(){
	return eleIds;
    }


    public String getEleName(){
        return eleName;
    }

    public String getEleNameLike(){
	    if (eleNameLike != null && eleNameLike.trim().length() > 0) {
		if (!eleNameLike.startsWith("%")) {
		    eleNameLike = "%" + eleNameLike;
		}
		if (!eleNameLike.endsWith("%")) {
		   eleNameLike = eleNameLike + "%";
		}
	    }
	return eleNameLike;
    }

    public List<String> getEleNames(){
	return eleNames;
    }


    public String getEleDesc(){
        return eleDesc;
    }

    public String getEleDescLike(){
	    if (eleDescLike != null && eleDescLike.trim().length() > 0) {
		if (!eleDescLike.startsWith("%")) {
		    eleDescLike = "%" + eleDescLike;
		}
		if (!eleDescLike.endsWith("%")) {
		   eleDescLike = eleDescLike + "%";
		}
	    }
	return eleDescLike;
    }

    public List<String> getEleDescs(){
	return eleDescs;
    }


    public String getCreateBy(){
        return createBy;
    }

    public String getCreateByLike(){
	    if (createByLike != null && createByLike.trim().length() > 0) {
		if (!createByLike.startsWith("%")) {
		    createByLike = "%" + createByLike;
		}
		if (!createByLike.endsWith("%")) {
		   createByLike = createByLike + "%";
		}
	    }
	return createByLike;
    }

    public List<String> getCreateBys(){
	return createBys;
    }


    public Date getCreateTimeGreaterThanOrEqual(){
        return createTimeGreaterThanOrEqual;
    }

    public Date getCreateTimeLessThanOrEqual(){
	return createTimeLessThanOrEqual;
    }


    public String getUpdateBy(){
        return updateBy;
    }

    public String getUpdateByLike(){
	    if (updateByLike != null && updateByLike.trim().length() > 0) {
		if (!updateByLike.startsWith("%")) {
		    updateByLike = "%" + updateByLike;
		}
		if (!updateByLike.endsWith("%")) {
		   updateByLike = updateByLike + "%";
		}
	    }
	return updateByLike;
    }

    public List<String> getUpdateBys(){
	return updateBys;
    }


    public Date getUpdateTimeGreaterThanOrEqual(){
        return updateTimeGreaterThanOrEqual;
    }

    public Date getUpdateTimeLessThanOrEqual(){
	return updateTimeLessThanOrEqual;
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

 

    public void setFuncId(String funcId){
        this.funcId = funcId;
    }


    public void setFuncIds(List<String> funcIds){
        this.funcIds = funcIds;
    }


    public void setSysId(String sysId){
        this.sysId = sysId;
    }

    public void setSysIds(List<String> sysIds){
        this.sysIds = sysIds;
    }


    public void setSubSysId(String subSysId){
        this.subSysId = subSysId;
    }


    public void setSubSysIds(List<String> subSysIds){
        this.subSysIds = subSysIds;
    }


    public void setCurrProcDefKey(String currProcDefKey){
        this.currProcDefKey = currProcDefKey;
    }

    public void setCurrProcDefKeyLike( String currProcDefKeyLike){
	this.currProcDefKeyLike = currProcDefKeyLike;
    }

    public void setCurrProcDefKeys(List<String> currProcDefKeys){
        this.currProcDefKeys = currProcDefKeys;
    }


    public void setCurrProcDefId(String currProcDefId){
        this.currProcDefId = currProcDefId;
    }

    public void setCurrProcDefIdLike( String currProcDefIdLike){
	this.currProcDefIdLike = currProcDefIdLike;
    }

    public void setCurrProcDefIds(List<String> currProcDefIds){
        this.currProcDefIds = currProcDefIds;
    }


    public void setCurrProcModelId(String currProcModelId){
        this.currProcModelId = currProcModelId;
    }

    public void setCurrProcModelIdLike( String currProcModelIdLike){
	this.currProcModelIdLike = currProcModelIdLike;
    }

    public void setCurrProcModelIds(List<String> currProcModelIds){
        this.currProcModelIds = currProcModelIds;
    }


    public void setCurrProcDeployId(String currProcDeployId){
        this.currProcDeployId = currProcDeployId;
    }

    public void setCurrProcDeployIdLike( String currProcDeployIdLike){
	this.currProcDeployIdLike = currProcDeployIdLike;
    }

    public void setCurrProcDeployIds(List<String> currProcDeployIds){
        this.currProcDeployIds = currProcDeployIds;
    }


    public void setCurrProcDeployStatus(String currProcDeployStatus){
        this.currProcDeployStatus = currProcDeployStatus;
    }

    public void setCurrProcDeployStatusLike( String currProcDeployStatusLike){
	this.currProcDeployStatusLike = currProcDeployStatusLike;
    }

    public void setCurrProcDeployStatuss(List<String> currProcDeployStatuss){
        this.currProcDeployStatuss = currProcDeployStatuss;
    }


    public void setProcDefKey(String procDefKey){
        this.procDefKey = procDefKey;
    }

    public void setProcDefKeyLike( String procDefKeyLike){
	this.procDefKeyLike = procDefKeyLike;
    }

    public void setProcDefKeys(List<String> procDefKeys){
        this.procDefKeys = procDefKeys;
    }


    public void setProcDefId(String procDefId){
        this.procDefId = procDefId;
    }

    public void setProcDefIdLike( String procDefIdLike){
	this.procDefIdLike = procDefIdLike;
    }

    public void setProcDefIds(List<String> procDefIds){
        this.procDefIds = procDefIds;
    }


    public void setProcModelId(String procModelId){
        this.procModelId = procModelId;
    }

    public void setProcModelIdLike( String procModelIdLike){
	this.procModelIdLike = procModelIdLike;
    }

    public void setProcModelIds(List<String> procModelIds){
        this.procModelIds = procModelIds;
    }


    public void setProcDeployId(String procDeployId){
        this.procDeployId = procDeployId;
    }

    public void setProcDeployIdLike( String procDeployIdLike){
	this.procDeployIdLike = procDeployIdLike;
    }

    public void setProcDeployIds(List<String> procDeployIds){
        this.procDeployIds = procDeployIds;
    }


    public void setProcDeployStatus(String procDeployStatus){
        this.procDeployStatus = procDeployStatus;
    }

    public void setProcDeployStatusLike( String procDeployStatusLike){
	this.procDeployStatusLike = procDeployStatusLike;
    }

    public void setProcDeployStatuss(List<String> procDeployStatuss){
        this.procDeployStatuss = procDeployStatuss;
    }


    public void setEleType(String eleType){
        this.eleType = eleType;
    }

    public void setEleTypeLike( String eleTypeLike){
	this.eleTypeLike = eleTypeLike;
    }

    public void setEleTypes(List<String> eleTypes){
        this.eleTypes = eleTypes;
    }


    public void setEleResourceId(String eleResourceId){
        this.eleResourceId = eleResourceId;
    }

    public void setEleResourceIdLike( String eleResourceIdLike){
	this.eleResourceIdLike = eleResourceIdLike;
    }

    public void setEleResourceIds(List<String> eleResourceIds){
        this.eleResourceIds = eleResourceIds;
    }


    public void setEleId(String eleId){
        this.eleId = eleId;
    }

    public void setEleIdLike( String eleIdLike){
	this.eleIdLike = eleIdLike;
    }

    public void setEleIds(List<String> eleIds){
        this.eleIds = eleIds;
    }


    public void setEleName(String eleName){
        this.eleName = eleName;
    }

    public void setEleNameLike( String eleNameLike){
	this.eleNameLike = eleNameLike;
    }

    public void setEleNames(List<String> eleNames){
        this.eleNames = eleNames;
    }


    public void setEleDesc(String eleDesc){
        this.eleDesc = eleDesc;
    }

    public void setEleDescLike( String eleDescLike){
	this.eleDescLike = eleDescLike;
    }

    public void setEleDescs(List<String> eleDescs){
        this.eleDescs = eleDescs;
    }


    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    public void setCreateByLike( String createByLike){
	this.createByLike = createByLike;
    }

    public void setCreateBys(List<String> createBys){
        this.createBys = createBys;
    }


    public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
        this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
    }

    public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
	this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
    }


    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    public void setUpdateByLike( String updateByLike){
	this.updateByLike = updateByLike;
    }

    public void setUpdateBys(List<String> updateBys){
        this.updateBys = updateBys;
    }


    public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
        this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
    }

    public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
	this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
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




    public SystemProcDefQuery funcId(String funcId){
	if (funcId == null) {
            throw new RuntimeException("funcId is null");
        }         
	this.funcId = funcId;
	return this;
    }

    public SystemProcDefQuery funcIds(List<String> funcIds){
        if (funcIds == null) {
            throw new RuntimeException("funcIds is empty ");
        }
        this.funcIds = funcIds;
        return this;
    }


    public SystemProcDefQuery sysId(String sysId){
	if (sysId == null) {
            throw new RuntimeException("sysId is null");
        }         
	this.sysId = sysId;
	return this;
    }

   
    public SystemProcDefQuery sysIds(List<String> sysIds){
        if (sysIds == null) {
            throw new RuntimeException("sysIds is empty ");
        }
        this.sysIds = sysIds;
        return this;
    }


    public SystemProcDefQuery subSysId(String subSysId){
	if (subSysId == null) {
            throw new RuntimeException("subSysId is null");
        }         
	this.subSysId = subSysId;
	return this;
    }

    public SystemProcDefQuery subSysIds(List<String> subSysIds){
        if (subSysIds == null) {
            throw new RuntimeException("subSysIds is empty ");
        }
        this.subSysIds = subSysIds;
        return this;
    }


    public SystemProcDefQuery currProcDefKey(String currProcDefKey){
	if (currProcDefKey == null) {
	    throw new RuntimeException("currProcDefKey is null");
        }         
	this.currProcDefKey = currProcDefKey;
	return this;
    }

    public SystemProcDefQuery currProcDefKeyLike( String currProcDefKeyLike){
        if (currProcDefKeyLike == null) {
            throw new RuntimeException("currProcDefKey is null");
        }
        this.currProcDefKeyLike = currProcDefKeyLike;
        return this;
    }

    public SystemProcDefQuery currProcDefKeys(List<String> currProcDefKeys){
        if (currProcDefKeys == null) {
            throw new RuntimeException("currProcDefKeys is empty ");
        }
        this.currProcDefKeys = currProcDefKeys;
        return this;
    }


    public SystemProcDefQuery currProcDefId(String currProcDefId){
	if (currProcDefId == null) {
	    throw new RuntimeException("currProcDefId is null");
        }         
	this.currProcDefId = currProcDefId;
	return this;
    }

    public SystemProcDefQuery currProcDefIdLike( String currProcDefIdLike){
        if (currProcDefIdLike == null) {
            throw new RuntimeException("currProcDefId is null");
        }
        this.currProcDefIdLike = currProcDefIdLike;
        return this;
    }

    public SystemProcDefQuery currProcDefIds(List<String> currProcDefIds){
        if (currProcDefIds == null) {
            throw new RuntimeException("currProcDefIds is empty ");
        }
        this.currProcDefIds = currProcDefIds;
        return this;
    }


    public SystemProcDefQuery currProcModelId(String currProcModelId){
	if (currProcModelId == null) {
	    throw new RuntimeException("currProcModelId is null");
        }         
	this.currProcModelId = currProcModelId;
	return this;
    }

    public SystemProcDefQuery currProcModelIdLike( String currProcModelIdLike){
        if (currProcModelIdLike == null) {
            throw new RuntimeException("currProcModelId is null");
        }
        this.currProcModelIdLike = currProcModelIdLike;
        return this;
    }

    public SystemProcDefQuery currProcModelIds(List<String> currProcModelIds){
        if (currProcModelIds == null) {
            throw new RuntimeException("currProcModelIds is empty ");
        }
        this.currProcModelIds = currProcModelIds;
        return this;
    }


    public SystemProcDefQuery currProcDeployId(String currProcDeployId){
	if (currProcDeployId == null) {
	    throw new RuntimeException("currProcDeployId is null");
        }         
	this.currProcDeployId = currProcDeployId;
	return this;
    }

    public SystemProcDefQuery currProcDeployIdLike( String currProcDeployIdLike){
        if (currProcDeployIdLike == null) {
            throw new RuntimeException("currProcDeployId is null");
        }
        this.currProcDeployIdLike = currProcDeployIdLike;
        return this;
    }

    public SystemProcDefQuery currProcDeployIds(List<String> currProcDeployIds){
        if (currProcDeployIds == null) {
            throw new RuntimeException("currProcDeployIds is empty ");
        }
        this.currProcDeployIds = currProcDeployIds;
        return this;
    }


    public SystemProcDefQuery currProcDeployStatus(String currProcDeployStatus){
	if (currProcDeployStatus == null) {
	    throw new RuntimeException("currProcDeployStatus is null");
        }         
	this.currProcDeployStatus = currProcDeployStatus;
	return this;
    }

    public SystemProcDefQuery currProcDeployStatusLike( String currProcDeployStatusLike){
        if (currProcDeployStatusLike == null) {
            throw new RuntimeException("currProcDeployStatus is null");
        }
        this.currProcDeployStatusLike = currProcDeployStatusLike;
        return this;
    }

    public SystemProcDefQuery currProcDeployStatuss(List<String> currProcDeployStatuss){
        if (currProcDeployStatuss == null) {
            throw new RuntimeException("currProcDeployStatuss is empty ");
        }
        this.currProcDeployStatuss = currProcDeployStatuss;
        return this;
    }


    public SystemProcDefQuery procDefKey(String procDefKey){
	if (procDefKey == null) {
	    throw new RuntimeException("procDefKey is null");
        }         
	this.procDefKey = procDefKey;
	return this;
    }

    public SystemProcDefQuery procDefKeyLike( String procDefKeyLike){
        if (procDefKeyLike == null) {
            throw new RuntimeException("procDefKey is null");
        }
        this.procDefKeyLike = procDefKeyLike;
        return this;
    }

    public SystemProcDefQuery procDefKeys(List<String> procDefKeys){
        if (procDefKeys == null) {
            throw new RuntimeException("procDefKeys is empty ");
        }
        this.procDefKeys = procDefKeys;
        return this;
    }


    public SystemProcDefQuery procDefId(String procDefId){
	if (procDefId == null) {
	    throw new RuntimeException("procDefId is null");
        }         
	this.procDefId = procDefId;
	return this;
    }

    public SystemProcDefQuery procDefIdLike( String procDefIdLike){
        if (procDefIdLike == null) {
            throw new RuntimeException("procDefId is null");
        }
        this.procDefIdLike = procDefIdLike;
        return this;
    }

    public SystemProcDefQuery procDefIds(List<String> procDefIds){
        if (procDefIds == null) {
            throw new RuntimeException("procDefIds is empty ");
        }
        this.procDefIds = procDefIds;
        return this;
    }


    public SystemProcDefQuery procModelId(String procModelId){
	if (procModelId == null) {
	    throw new RuntimeException("procModelId is null");
        }         
	this.procModelId = procModelId;
	return this;
    }

    public SystemProcDefQuery procModelIdLike( String procModelIdLike){
        if (procModelIdLike == null) {
            throw new RuntimeException("procModelId is null");
        }
        this.procModelIdLike = procModelIdLike;
        return this;
    }

    public SystemProcDefQuery procModelIds(List<String> procModelIds){
        if (procModelIds == null) {
            throw new RuntimeException("procModelIds is empty ");
        }
        this.procModelIds = procModelIds;
        return this;
    }


    public SystemProcDefQuery procDeployId(String procDeployId){
	if (procDeployId == null) {
	    throw new RuntimeException("procDeployId is null");
        }         
	this.procDeployId = procDeployId;
	return this;
    }

    public SystemProcDefQuery procDeployIdLike( String procDeployIdLike){
        if (procDeployIdLike == null) {
            throw new RuntimeException("procDeployId is null");
        }
        this.procDeployIdLike = procDeployIdLike;
        return this;
    }

    public SystemProcDefQuery procDeployIds(List<String> procDeployIds){
        if (procDeployIds == null) {
            throw new RuntimeException("procDeployIds is empty ");
        }
        this.procDeployIds = procDeployIds;
        return this;
    }


    public SystemProcDefQuery procDeployStatus(String procDeployStatus){
	if (procDeployStatus == null) {
	    throw new RuntimeException("procDeployStatus is null");
        }         
	this.procDeployStatus = procDeployStatus;
	return this;
    }

    public SystemProcDefQuery procDeployStatusLike( String procDeployStatusLike){
        if (procDeployStatusLike == null) {
            throw new RuntimeException("procDeployStatus is null");
        }
        this.procDeployStatusLike = procDeployStatusLike;
        return this;
    }

    public SystemProcDefQuery procDeployStatuss(List<String> procDeployStatuss){
        if (procDeployStatuss == null) {
            throw new RuntimeException("procDeployStatuss is empty ");
        }
        this.procDeployStatuss = procDeployStatuss;
        return this;
    }


    public SystemProcDefQuery eleType(String eleType){
	if (eleType == null) {
	    throw new RuntimeException("eleType is null");
        }         
	this.eleType = eleType;
	return this;
    }

    public SystemProcDefQuery eleTypeLike( String eleTypeLike){
        if (eleTypeLike == null) {
            throw new RuntimeException("eleType is null");
        }
        this.eleTypeLike = eleTypeLike;
        return this;
    }

    public SystemProcDefQuery eleTypes(List<String> eleTypes){
        if (eleTypes == null) {
            throw new RuntimeException("eleTypes is empty ");
        }
        this.eleTypes = eleTypes;
        return this;
    }


    public SystemProcDefQuery eleResourceId(String eleResourceId){
	if (eleResourceId == null) {
	    throw new RuntimeException("eleResourceId is null");
        }         
	this.eleResourceId = eleResourceId;
	return this;
    }

    public SystemProcDefQuery eleResourceIdLike( String eleResourceIdLike){
        if (eleResourceIdLike == null) {
            throw new RuntimeException("eleResourceId is null");
        }
        this.eleResourceIdLike = eleResourceIdLike;
        return this;
    }

    public SystemProcDefQuery eleResourceIds(List<String> eleResourceIds){
        if (eleResourceIds == null) {
            throw new RuntimeException("eleResourceIds is empty ");
        }
        this.eleResourceIds = eleResourceIds;
        return this;
    }


    public SystemProcDefQuery eleId(String eleId){
	if (eleId == null) {
	    throw new RuntimeException("eleId is null");
        }         
	this.eleId = eleId;
	return this;
    }

    public SystemProcDefQuery eleIdLike( String eleIdLike){
        if (eleIdLike == null) {
            throw new RuntimeException("eleId is null");
        }
        this.eleIdLike = eleIdLike;
        return this;
    }

    public SystemProcDefQuery eleIds(List<String> eleIds){
        if (eleIds == null) {
            throw new RuntimeException("eleIds is empty ");
        }
        this.eleIds = eleIds;
        return this;
    }


    public SystemProcDefQuery eleName(String eleName){
	if (eleName == null) {
	    throw new RuntimeException("eleName is null");
        }         
	this.eleName = eleName;
	return this;
    }

    public SystemProcDefQuery eleNameLike( String eleNameLike){
        if (eleNameLike == null) {
            throw new RuntimeException("eleName is null");
        }
        this.eleNameLike = eleNameLike;
        return this;
    }

    public SystemProcDefQuery eleNames(List<String> eleNames){
        if (eleNames == null) {
            throw new RuntimeException("eleNames is empty ");
        }
        this.eleNames = eleNames;
        return this;
    }


    public SystemProcDefQuery eleDesc(String eleDesc){
	if (eleDesc == null) {
	    throw new RuntimeException("eleDesc is null");
        }         
	this.eleDesc = eleDesc;
	return this;
    }

    public SystemProcDefQuery eleDescLike( String eleDescLike){
        if (eleDescLike == null) {
            throw new RuntimeException("eleDesc is null");
        }
        this.eleDescLike = eleDescLike;
        return this;
    }

    public SystemProcDefQuery eleDescs(List<String> eleDescs){
        if (eleDescs == null) {
            throw new RuntimeException("eleDescs is empty ");
        }
        this.eleDescs = eleDescs;
        return this;
    }


    public SystemProcDefQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SystemProcDefQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SystemProcDefQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SystemProcDefQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SystemProcDefQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SystemProcDefQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SystemProcDefQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SystemProcDefQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SystemProcDefQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SystemProcDefQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SystemProcDefQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SystemProcDefQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SystemProcDefQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SystemProcDefQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("funcId".equals(sortColumn)) {
                orderBy = "E.FUNC_ID_" + a_x;
            } 

            if ("sysId".equals(sortColumn)) {
                orderBy = "E.SYS_ID_" + a_x;
            } 

            if ("subSysId".equals(sortColumn)) {
                orderBy = "E.SUB_SYS_ID_" + a_x;
            } 

            if ("currProcDefKey".equals(sortColumn)) {
                orderBy = "E.CURR_PROCDEF_KEY_" + a_x;
            } 

            if ("currProcDefId".equals(sortColumn)) {
                orderBy = "E.CURR_PROCDEF_ID_" + a_x;
            } 

            if ("currProcModelId".equals(sortColumn)) {
                orderBy = "E.CURR_PROCMODEL_ID_" + a_x;
            } 

            if ("currProcDeployId".equals(sortColumn)) {
                orderBy = "E.CURR_PROCDEPLOY_ID_" + a_x;
            } 

            if ("currProcDeployStatus".equals(sortColumn)) {
                orderBy = "E.CURR_PROCDEPLOY_STATUS_" + a_x;
            } 

            if ("procDefKey".equals(sortColumn)) {
                orderBy = "E.PROCDEF_KEY_" + a_x;
            } 

            if ("procDefId".equals(sortColumn)) {
                orderBy = "E.PROCDEF_ID_" + a_x;
            } 

            if ("procModelId".equals(sortColumn)) {
                orderBy = "E.PROCMODEL_ID_" + a_x;
            } 

            if ("procDeployId".equals(sortColumn)) {
                orderBy = "E.PROCDEPLOY_ID_" + a_x;
            } 

            if ("procDeployStatus".equals(sortColumn)) {
                orderBy = "E.PROCDEPLOY_STATUS_" + a_x;
            } 

            if ("eleType".equals(sortColumn)) {
                orderBy = "E.ELE_TYPE_" + a_x;
            } 

            if ("eleResourceId".equals(sortColumn)) {
                orderBy = "E.ELE_RESOURCE_ID_" + a_x;
            } 

            if ("eleId".equals(sortColumn)) {
                orderBy = "E.ELE_ID_" + a_x;
            } 

            if ("eleName".equals(sortColumn)) {
                orderBy = "E.ELE_NAME_" + a_x;
            } 

            if ("eleDesc".equals(sortColumn)) {
                orderBy = "E.ELE_DESC" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createTime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateTime".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETE_FLAG_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("funcId", "FUNC_ID_");
        addColumn("sysId", "SYS_ID_");
        addColumn("subSysId", "SUB_SYS_ID_");
        addColumn("currProcDefKey", "CURR_PROCDEF_KEY_");
        addColumn("currProcDefId", "CURR_PROCDEF_ID_");
        addColumn("currProcModelId", "CURR_PROCMODEL_ID_");
        addColumn("currProcDeployId", "CURR_PROCDEPLOY_ID_");
        addColumn("currProcDeployStatus", "CURR_PROCDEPLOY_STATUS_");
        addColumn("procDefKey", "PROCDEF_KEY_");
        addColumn("procDefId", "PROCDEF_ID_");
        addColumn("procModelId", "PROCMODEL_ID_");
        addColumn("procDeployId", "PROCDEPLOY_ID_");
        addColumn("procDeployStatus", "PROCDEPLOY_STATUS_");
        addColumn("eleType", "ELE_TYPE_");
        addColumn("eleResourceId", "ELE_RESOURCE_ID_");
        addColumn("eleId", "ELE_ID_");
        addColumn("eleName", "ELE_NAME_");
        addColumn("eleDesc", "ELE_DESC");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}