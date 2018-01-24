package com.glaf.model.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SystemFuncQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> funcIds;
	protected Collection<String> appActorIds;
  	protected String sysId;
  	protected List<String> sysIds;
  	protected String funcCode;
  	protected String funcCodeLike;
  	protected List<String> funcCodes;
  	protected String funcName;
  	protected String funcNameLike;
  	protected List<String> funcNames;
  	protected String funcType;
  	protected String funcTypeLike;
  	protected List<String> funcTypes;
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

    public SystemFuncQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getSysId(){
        return sysId;
    }

  
    public List<String> getSysIds(){
	return sysIds;
    }

    public String getFuncCode(){
        return funcCode;
    }

    public String getFuncCodeLike(){
	    if (funcCodeLike != null && funcCodeLike.trim().length() > 0) {
		if (!funcCodeLike.startsWith("%")) {
		    funcCodeLike = "%" + funcCodeLike;
		}
		if (!funcCodeLike.endsWith("%")) {
		   funcCodeLike = funcCodeLike + "%";
		}
	    }
	return funcCodeLike;
    }

    public List<String> getFuncCodes(){
	return funcCodes;
    }


    public String getFuncName(){
        return funcName;
    }

    public String getFuncNameLike(){
	    if (funcNameLike != null && funcNameLike.trim().length() > 0) {
		if (!funcNameLike.startsWith("%")) {
		    funcNameLike = "%" + funcNameLike;
		}
		if (!funcNameLike.endsWith("%")) {
		   funcNameLike = funcNameLike + "%";
		}
	    }
	return funcNameLike;
    }

    public List<String> getFuncNames(){
	return funcNames;
    }


    public String getFuncType(){
        return funcType;
    }

    public String getFuncTypeLike(){
	    if (funcTypeLike != null && funcTypeLike.trim().length() > 0) {
		if (!funcTypeLike.startsWith("%")) {
		    funcTypeLike = "%" + funcTypeLike;
		}
		if (!funcTypeLike.endsWith("%")) {
		   funcTypeLike = funcTypeLike + "%";
		}
	    }
	return funcTypeLike;
    }

    public List<String> getFuncTypes(){
	return funcTypes;
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

 

    public void setSysId(String sysId){
        this.sysId = sysId;
    }

   
    public void setSysIds(List<String> sysIds){
        this.sysIds = sysIds;
    }


    public void setFuncCode(String funcCode){
        this.funcCode = funcCode;
    }

    public void setFuncCodeLike( String funcCodeLike){
	this.funcCodeLike = funcCodeLike;
    }

    public void setFuncCodes(List<String> funcCodes){
        this.funcCodes = funcCodes;
    }


    public void setFuncName(String funcName){
        this.funcName = funcName;
    }

    public void setFuncNameLike( String funcNameLike){
	this.funcNameLike = funcNameLike;
    }

    public void setFuncNames(List<String> funcNames){
        this.funcNames = funcNames;
    }


    public void setFuncType(String funcType){
        this.funcType = funcType;
    }

    public void setFuncTypeLike( String funcTypeLike){
	this.funcTypeLike = funcTypeLike;
    }

    public void setFuncTypes(List<String> funcTypes){
        this.funcTypes = funcTypes;
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




    public SystemFuncQuery sysId(String sysId){
	if (sysId == null) {
            throw new RuntimeException("sysId is null");
        }         
	this.sysId = sysId;
	return this;
    }

 
    public SystemFuncQuery sysIds(List<String> sysIds){
        if (sysIds == null) {
            throw new RuntimeException("sysIds is empty ");
        }
        this.sysIds = sysIds;
        return this;
    }


    public SystemFuncQuery funcCode(String funcCode){
	if (funcCode == null) {
	    throw new RuntimeException("funcCode is null");
        }         
	this.funcCode = funcCode;
	return this;
    }

    public SystemFuncQuery funcCodeLike( String funcCodeLike){
        if (funcCodeLike == null) {
            throw new RuntimeException("funcCode is null");
        }
        this.funcCodeLike = funcCodeLike;
        return this;
    }

    public SystemFuncQuery funcCodes(List<String> funcCodes){
        if (funcCodes == null) {
            throw new RuntimeException("funcCodes is empty ");
        }
        this.funcCodes = funcCodes;
        return this;
    }


    public SystemFuncQuery funcName(String funcName){
	if (funcName == null) {
	    throw new RuntimeException("funcName is null");
        }         
	this.funcName = funcName;
	return this;
    }

    public SystemFuncQuery funcNameLike( String funcNameLike){
        if (funcNameLike == null) {
            throw new RuntimeException("funcName is null");
        }
        this.funcNameLike = funcNameLike;
        return this;
    }

    public SystemFuncQuery funcNames(List<String> funcNames){
        if (funcNames == null) {
            throw new RuntimeException("funcNames is empty ");
        }
        this.funcNames = funcNames;
        return this;
    }


    public SystemFuncQuery funcType(String funcType){
	if (funcType == null) {
	    throw new RuntimeException("funcType is null");
        }         
	this.funcType = funcType;
	return this;
    }

    public SystemFuncQuery funcTypeLike( String funcTypeLike){
        if (funcTypeLike == null) {
            throw new RuntimeException("funcType is null");
        }
        this.funcTypeLike = funcTypeLike;
        return this;
    }

    public SystemFuncQuery funcTypes(List<String> funcTypes){
        if (funcTypes == null) {
            throw new RuntimeException("funcTypes is empty ");
        }
        this.funcTypes = funcTypes;
        return this;
    }


    public SystemFuncQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SystemFuncQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SystemFuncQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SystemFuncQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SystemFuncQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SystemFuncQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SystemFuncQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SystemFuncQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SystemFuncQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SystemFuncQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SystemFuncQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SystemFuncQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SystemFuncQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SystemFuncQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("sysId".equals(sortColumn)) {
                orderBy = "E.SYS_ID_" + a_x;
            } 

            if ("funcCode".equals(sortColumn)) {
                orderBy = "E.FUNC_CODE_" + a_x;
            } 

            if ("funcName".equals(sortColumn)) {
                orderBy = "E.FUNC_NAME_" + a_x;
            } 

            if ("funcType".equals(sortColumn)) {
                orderBy = "E.FUNC_TYPE_" + a_x;
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
        addColumn("funcId", "FUNC_ID_");
        addColumn("sysId", "SYS_ID_");
        addColumn("funcCode", "FUNC_CODE_");
        addColumn("funcName", "FUNC_NAME_");
        addColumn("funcType", "FUNC_TYPE_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}