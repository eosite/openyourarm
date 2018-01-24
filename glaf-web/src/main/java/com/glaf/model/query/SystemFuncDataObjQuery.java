package com.glaf.model.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SystemFuncDataObjQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> sysDataObjIds;
	protected Collection<String> appActorIds;
  	protected Long funcId;
  	protected Long funcIdGreaterThanOrEqual;
  	protected Long funcIdLessThanOrEqual;
  	protected List<Long> funcIds;
  	protected Integer type;
  	protected Long dataObjId;
  	protected Long dataObjIdGreaterThanOrEqual;
  	protected Long dataObjIdLessThanOrEqual;
  	protected List<Long> dataObjIds;
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

    public SystemFuncDataObjQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getFuncId(){
        return funcId;
    }

    public Long getFuncIdGreaterThanOrEqual(){
        return funcIdGreaterThanOrEqual;
    }

    public Long getFuncIdLessThanOrEqual(){
	return funcIdLessThanOrEqual;
    }

    public List<Long> getFuncIds(){
	return funcIds;
    }

    public Long getDataObjId(){
        return dataObjId;
    }

    public Long getDataObjIdGreaterThanOrEqual(){
        return dataObjIdGreaterThanOrEqual;
    }

    public Long getDataObjIdLessThanOrEqual(){
	return dataObjIdLessThanOrEqual;
    }

    public List<Long> getDataObjIds(){
	return dataObjIds;
    }

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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


 

    public void setFuncId(Long funcId){
        this.funcId = funcId;
    }

    public void setFuncIdGreaterThanOrEqual(Long funcIdGreaterThanOrEqual){
        this.funcIdGreaterThanOrEqual = funcIdGreaterThanOrEqual;
    }

    public void setFuncIdLessThanOrEqual(Long funcIdLessThanOrEqual){
	this.funcIdLessThanOrEqual = funcIdLessThanOrEqual;
    }

    public void setFuncIds(List<Long> funcIds){
        this.funcIds = funcIds;
    }


    public void setDataObjId(Long dataObjId){
        this.dataObjId = dataObjId;
    }

    public void setDataObjIdGreaterThanOrEqual(Long dataObjIdGreaterThanOrEqual){
        this.dataObjIdGreaterThanOrEqual = dataObjIdGreaterThanOrEqual;
    }

    public void setDataObjIdLessThanOrEqual(Long dataObjIdLessThanOrEqual){
	this.dataObjIdLessThanOrEqual = dataObjIdLessThanOrEqual;
    }

    public void setDataObjIds(List<Long> dataObjIds){
        this.dataObjIds = dataObjIds;
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




    public SystemFuncDataObjQuery funcId(Long funcId){
	if (funcId == null) {
            throw new RuntimeException("funcId is null");
        }         
	this.funcId = funcId;
	return this;
    }

    public SystemFuncDataObjQuery funcIdGreaterThanOrEqual(Long funcIdGreaterThanOrEqual){
	if (funcIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("funcId is null");
        }         
	this.funcIdGreaterThanOrEqual = funcIdGreaterThanOrEqual;
        return this;
    }

    public SystemFuncDataObjQuery funcIdLessThanOrEqual(Long funcIdLessThanOrEqual){
        if (funcIdLessThanOrEqual == null) {
            throw new RuntimeException("funcId is null");
        }
        this.funcIdLessThanOrEqual = funcIdLessThanOrEqual;
        return this;
    }

    public SystemFuncDataObjQuery funcIds(List<Long> funcIds){
        if (funcIds == null) {
            throw new RuntimeException("funcIds is empty ");
        }
        this.funcIds = funcIds;
        return this;
    }


    public SystemFuncDataObjQuery dataObjId(Long dataObjId){
	if (dataObjId == null) {
            throw new RuntimeException("dataObjId is null");
        }         
	this.dataObjId = dataObjId;
	return this;
    }

    public SystemFuncDataObjQuery dataObjIdGreaterThanOrEqual(Long dataObjIdGreaterThanOrEqual){
	if (dataObjIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("dataObjId is null");
        }         
	this.dataObjIdGreaterThanOrEqual = dataObjIdGreaterThanOrEqual;
        return this;
    }

    public SystemFuncDataObjQuery dataObjIdLessThanOrEqual(Long dataObjIdLessThanOrEqual){
        if (dataObjIdLessThanOrEqual == null) {
            throw new RuntimeException("dataObjId is null");
        }
        this.dataObjIdLessThanOrEqual = dataObjIdLessThanOrEqual;
        return this;
    }

    public SystemFuncDataObjQuery dataObjIds(List<Long> dataObjIds){
        if (dataObjIds == null) {
            throw new RuntimeException("dataObjIds is empty ");
        }
        this.dataObjIds = dataObjIds;
        return this;
    }


    public SystemFuncDataObjQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SystemFuncDataObjQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SystemFuncDataObjQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SystemFuncDataObjQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SystemFuncDataObjQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SystemFuncDataObjQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SystemFuncDataObjQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SystemFuncDataObjQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SystemFuncDataObjQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SystemFuncDataObjQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
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

            if ("funcId".equals(sortColumn)) {
                orderBy = "E.FUNC_ID_" + a_x;
            } 

            if ("dataObjId".equals(sortColumn)) {
                orderBy = "E.DATA_OBJ_ID_" + a_x;
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

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("sysDataObjId", "SYS_DATA_OBJ_ID_");
        addColumn("funcId", "FUNC_ID_");
        addColumn("dataObjId", "DATA_OBJ_ID_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
    }

}