package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ETLDataTransferQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String dataSrcId;
  	protected String dataSrcIdLike;
  	protected List<String> dataSrcIds;
  	protected String targetId;
  	protected String targetIdLike;
  	protected List<String> targetIds;
  	protected String transName;
  	protected String transNameLike;
  	protected List<String> transNames;
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
  	protected String keywordsLike;
  	
    public ETLDataTransferQuery() {

    }

    
    public String getKeywordsLike() {
		if (keywordsLike != null && keywordsLike.trim().length() > 0) {
			if (!keywordsLike.startsWith("%")) {
				keywordsLike = "%" + keywordsLike;
			}
			if (!keywordsLike.endsWith("%")) {
				keywordsLike = keywordsLike + "%";
			}
		}
		return keywordsLike;
	}
    
    
    public void setKeywordsLike(String keywordsLike) {
		this.keywordsLike = keywordsLike;
	}



	public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getDataSrcId(){
        return dataSrcId;
    }

    public String getDataSrcIdLike(){
	    if (dataSrcIdLike != null && dataSrcIdLike.trim().length() > 0) {
		if (!dataSrcIdLike.startsWith("%")) {
		    dataSrcIdLike = "%" + dataSrcIdLike;
		}
		if (!dataSrcIdLike.endsWith("%")) {
		   dataSrcIdLike = dataSrcIdLike + "%";
		}
	    }
	return dataSrcIdLike;
    }

    public List<String> getDataSrcIds(){
	return dataSrcIds;
    }


    public String getTargetId(){
        return targetId;
    }

    public String getTargetIdLike(){
	    if (targetIdLike != null && targetIdLike.trim().length() > 0) {
		if (!targetIdLike.startsWith("%")) {
		    targetIdLike = "%" + targetIdLike;
		}
		if (!targetIdLike.endsWith("%")) {
		   targetIdLike = targetIdLike + "%";
		}
	    }
	return targetIdLike;
    }

    public List<String> getTargetIds(){
	return targetIds;
    }


    public String getTransName(){
        return transName;
    }

    public String getTransNameLike(){
	    if (transNameLike != null && transNameLike.trim().length() > 0) {
		if (!transNameLike.startsWith("%")) {
		    transNameLike = "%" + transNameLike;
		}
		if (!transNameLike.endsWith("%")) {
		   transNameLike = transNameLike + "%";
		}
	    }
	return transNameLike;
    }

    public List<String> getTransNames(){
	return transNames;
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


 

    public void setDataSrcId(String dataSrcId){
        this.dataSrcId = dataSrcId;
    }

    public void setDataSrcIdLike( String dataSrcIdLike){
	this.dataSrcIdLike = dataSrcIdLike;
    }

    public void setDataSrcIds(List<String> dataSrcIds){
        this.dataSrcIds = dataSrcIds;
    }


    public void setTargetId(String targetId){
        this.targetId = targetId;
    }

    public void setTargetIdLike( String targetIdLike){
	this.targetIdLike = targetIdLike;
    }

    public void setTargetIds(List<String> targetIds){
        this.targetIds = targetIds;
    }


    public void setTransName(String transName){
        this.transName = transName;
    }

    public void setTransNameLike( String transNameLike){
	this.transNameLike = transNameLike;
    }

    public void setTransNames(List<String> transNames){
        this.transNames = transNames;
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




    public ETLDataTransferQuery dataSrcId(String dataSrcId){
	if (dataSrcId == null) {
	    throw new RuntimeException("dataSrcId is null");
        }         
	this.dataSrcId = dataSrcId;
	return this;
    }

    public ETLDataTransferQuery dataSrcIdLike( String dataSrcIdLike){
        if (dataSrcIdLike == null) {
            throw new RuntimeException("dataSrcId is null");
        }
        this.dataSrcIdLike = dataSrcIdLike;
        return this;
    }

    public ETLDataTransferQuery dataSrcIds(List<String> dataSrcIds){
        if (dataSrcIds == null) {
            throw new RuntimeException("dataSrcIds is empty ");
        }
        this.dataSrcIds = dataSrcIds;
        return this;
    }


    public ETLDataTransferQuery targetId(String targetId){
	if (targetId == null) {
	    throw new RuntimeException("targetId is null");
        }         
	this.targetId = targetId;
	return this;
    }

    public ETLDataTransferQuery targetIdLike( String targetIdLike){
        if (targetIdLike == null) {
            throw new RuntimeException("targetId is null");
        }
        this.targetIdLike = targetIdLike;
        return this;
    }

    public ETLDataTransferQuery targetIds(List<String> targetIds){
        if (targetIds == null) {
            throw new RuntimeException("targetIds is empty ");
        }
        this.targetIds = targetIds;
        return this;
    }


    public ETLDataTransferQuery transName(String transName){
	if (transName == null) {
	    throw new RuntimeException("transName is null");
        }         
	this.transName = transName;
	return this;
    }

    public ETLDataTransferQuery transNameLike( String transNameLike){
        if (transNameLike == null) {
            throw new RuntimeException("transName is null");
        }
        this.transNameLike = transNameLike;
        return this;
    }

    public ETLDataTransferQuery transNames(List<String> transNames){
        if (transNames == null) {
            throw new RuntimeException("transNames is empty ");
        }
        this.transNames = transNames;
        return this;
    }


    public ETLDataTransferQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public ETLDataTransferQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public ETLDataTransferQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public ETLDataTransferQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public ETLDataTransferQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public ETLDataTransferQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public ETLDataTransferQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public ETLDataTransferQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public ETLDataTransferQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public ETLDataTransferQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
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

            if ("dataSrcId".equals(sortColumn)) {
                orderBy = "E.DATASRCID_" + a_x;
            } 

            if ("targetId".equals(sortColumn)) {
                orderBy = "E.TARGET_ID_" + a_x;
            } 

            if ("transName".equals(sortColumn)) {
                orderBy = "E.TRANSNAME_" + a_x;
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
        addColumn("id", "ID");
        addColumn("dataSrcId", "DATASRCID_");
        addColumn("targetId", "TARGET_ID_");
        addColumn("transName", "TRANSNAME_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
    }

}