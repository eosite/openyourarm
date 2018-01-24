package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SystemDBFuncMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String funcId;
  	protected String funcIdLike;
  	protected List<String> funcIds;
  	protected String dtype;
  	protected String dtypeLike;
  	protected List<String> dtypes;
  	protected String funcName;
  	protected String funcNameLike;
  	protected List<String> funcNames;
  	protected String params;
  	protected String paramsLike;
  	protected List<String> paramss;
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

    public SystemDBFuncMappingQuery() {

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

    public String getFuncIdLike(){
	    if (funcIdLike != null && funcIdLike.trim().length() > 0) {
		if (!funcIdLike.startsWith("%")) {
		    funcIdLike = "%" + funcIdLike;
		}
		if (!funcIdLike.endsWith("%")) {
		   funcIdLike = funcIdLike + "%";
		}
	    }
	return funcIdLike;
    }

    public List<String> getFuncIds(){
	return funcIds;
    }


    public String getDtype(){
        return dtype;
    }

    public String getDtypeLike(){
	    if (dtypeLike != null && dtypeLike.trim().length() > 0) {
		if (!dtypeLike.startsWith("%")) {
		    dtypeLike = "%" + dtypeLike;
		}
		if (!dtypeLike.endsWith("%")) {
		   dtypeLike = dtypeLike + "%";
		}
	    }
	return dtypeLike;
    }

    public List<String> getDtypes(){
	return dtypes;
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


    public String getParams(){
        return params;
    }

    public String getParamsLike(){
	    if (paramsLike != null && paramsLike.trim().length() > 0) {
		if (!paramsLike.startsWith("%")) {
		    paramsLike = "%" + paramsLike;
		}
		if (!paramsLike.endsWith("%")) {
		   paramsLike = paramsLike + "%";
		}
	    }
	return paramsLike;
    }

    public List<String> getParamss(){
	return paramss;
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

    public void setFuncIdLike( String funcIdLike){
	this.funcIdLike = funcIdLike;
    }

    public void setFuncIds(List<String> funcIds){
        this.funcIds = funcIds;
    }


    public void setDtype(String dtype){
        this.dtype = dtype;
    }

    public void setDtypeLike( String dtypeLike){
	this.dtypeLike = dtypeLike;
    }

    public void setDtypes(List<String> dtypes){
        this.dtypes = dtypes;
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


    public void setParams(String params){
        this.params = params;
    }

    public void setParamsLike( String paramsLike){
	this.paramsLike = paramsLike;
    }

    public void setParamss(List<String> paramss){
        this.paramss = paramss;
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




    public SystemDBFuncMappingQuery funcId(String funcId){
	if (funcId == null) {
	    throw new RuntimeException("funcId is null");
        }         
	this.funcId = funcId;
	return this;
    }

    public SystemDBFuncMappingQuery funcIdLike( String funcIdLike){
        if (funcIdLike == null) {
            throw new RuntimeException("funcId is null");
        }
        this.funcIdLike = funcIdLike;
        return this;
    }

    public SystemDBFuncMappingQuery funcIds(List<String> funcIds){
        if (funcIds == null) {
            throw new RuntimeException("funcIds is empty ");
        }
        this.funcIds = funcIds;
        return this;
    }


    public SystemDBFuncMappingQuery dtype(String dtype){
	if (dtype == null) {
	    throw new RuntimeException("dtype is null");
        }         
	this.dtype = dtype;
	return this;
    }

    public SystemDBFuncMappingQuery dtypeLike( String dtypeLike){
        if (dtypeLike == null) {
            throw new RuntimeException("dtype is null");
        }
        this.dtypeLike = dtypeLike;
        return this;
    }

    public SystemDBFuncMappingQuery dtypes(List<String> dtypes){
        if (dtypes == null) {
            throw new RuntimeException("dtypes is empty ");
        }
        this.dtypes = dtypes;
        return this;
    }


    public SystemDBFuncMappingQuery funcName(String funcName){
	if (funcName == null) {
	    throw new RuntimeException("funcName is null");
        }         
	this.funcName = funcName;
	return this;
    }

    public SystemDBFuncMappingQuery funcNameLike( String funcNameLike){
        if (funcNameLike == null) {
            throw new RuntimeException("funcName is null");
        }
        this.funcNameLike = funcNameLike;
        return this;
    }

    public SystemDBFuncMappingQuery funcNames(List<String> funcNames){
        if (funcNames == null) {
            throw new RuntimeException("funcNames is empty ");
        }
        this.funcNames = funcNames;
        return this;
    }


    public SystemDBFuncMappingQuery params(String params){
	if (params == null) {
	    throw new RuntimeException("params is null");
        }         
	this.params = params;
	return this;
    }

    public SystemDBFuncMappingQuery paramsLike( String paramsLike){
        if (paramsLike == null) {
            throw new RuntimeException("params is null");
        }
        this.paramsLike = paramsLike;
        return this;
    }

    public SystemDBFuncMappingQuery paramss(List<String> paramss){
        if (paramss == null) {
            throw new RuntimeException("paramss is empty ");
        }
        this.paramss = paramss;
        return this;
    }


    public SystemDBFuncMappingQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SystemDBFuncMappingQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SystemDBFuncMappingQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SystemDBFuncMappingQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SystemDBFuncMappingQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SystemDBFuncMappingQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SystemDBFuncMappingQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SystemDBFuncMappingQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SystemDBFuncMappingQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SystemDBFuncMappingQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SystemDBFuncMappingQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SystemDBFuncMappingQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SystemDBFuncMappingQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SystemDBFuncMappingQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("dtype".equals(sortColumn)) {
                orderBy = "E.DTYPE_" + a_x;
            } 

            if ("funcName".equals(sortColumn)) {
                orderBy = "E.FUNCNAME_" + a_x;
            } 

            if ("params".equals(sortColumn)) {
                orderBy = "E.PARAMS_" + a_x;
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
        addColumn("dtype", "DTYPE_");
        addColumn("funcName", "FUNCNAME_");
        addColumn("params", "PARAMS_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}