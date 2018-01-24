package com.glaf.teim.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EimServerDataImpQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String appId;
  	protected String appIdLike;
  	protected List<String> appIds;
  	protected String tmpId;
  	protected String tmpIdLike;
  	protected List<String> tmpIds;
  	protected Integer emptyTable;
  	protected Integer emptyTableGreaterThanOrEqual;
  	protected Integer emptyTableLessThanOrEqual;
  	protected List<Integer> emptyTables;
  	protected Integer incrementFlag;
  	protected Integer incrementFlagGreaterThanOrEqual;
  	protected Integer incrementFlagLessThanOrEqual;
  	protected List<Integer> incrementFlags;
  	protected String targetDatabase;
  	protected String targetDatabaseLike;
  	protected List<String> targetDatabases;
  	protected String targetTable;
  	protected String targetTableLike;
  	protected List<String> targetTables;
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

    public EimServerDataImpQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getAppId(){
        return appId;
    }

    public String getAppIdLike(){
	    if (appIdLike != null && appIdLike.trim().length() > 0) {
		if (!appIdLike.startsWith("%")) {
		    appIdLike = "%" + appIdLike;
		}
		if (!appIdLike.endsWith("%")) {
		   appIdLike = appIdLike + "%";
		}
	    }
	return appIdLike;
    }

    public List<String> getAppIds(){
	return appIds;
    }


    public String getTmpId(){
        return tmpId;
    }

    public String getTmpIdLike(){
	    if (tmpIdLike != null && tmpIdLike.trim().length() > 0) {
		if (!tmpIdLike.startsWith("%")) {
		    tmpIdLike = "%" + tmpIdLike;
		}
		if (!tmpIdLike.endsWith("%")) {
		   tmpIdLike = tmpIdLike + "%";
		}
	    }
	return tmpIdLike;
    }

    public List<String> getTmpIds(){
	return tmpIds;
    }


    public Integer getEmptyTable(){
        return emptyTable;
    }

    public Integer getEmptyTableGreaterThanOrEqual(){
        return emptyTableGreaterThanOrEqual;
    }

    public Integer getEmptyTableLessThanOrEqual(){
	return emptyTableLessThanOrEqual;
    }

    public List<Integer> getEmptyTables(){
	return emptyTables;
    }

    public Integer getIncrementFlag(){
        return incrementFlag;
    }

    public Integer getIncrementFlagGreaterThanOrEqual(){
        return incrementFlagGreaterThanOrEqual;
    }

    public Integer getIncrementFlagLessThanOrEqual(){
	return incrementFlagLessThanOrEqual;
    }

    public List<Integer> getIncrementFlags(){
	return incrementFlags;
    }

    public String getTargetDatabase(){
        return targetDatabase;
    }

    public String getTargetDatabaseLike(){
	    if (targetDatabaseLike != null && targetDatabaseLike.trim().length() > 0) {
		if (!targetDatabaseLike.startsWith("%")) {
		    targetDatabaseLike = "%" + targetDatabaseLike;
		}
		if (!targetDatabaseLike.endsWith("%")) {
		   targetDatabaseLike = targetDatabaseLike + "%";
		}
	    }
	return targetDatabaseLike;
    }

    public List<String> getTargetDatabases(){
	return targetDatabases;
    }


    public String getTargetTable(){
        return targetTable;
    }

    public String getTargetTableLike(){
	    if (targetTableLike != null && targetTableLike.trim().length() > 0) {
		if (!targetTableLike.startsWith("%")) {
		    targetTableLike = "%" + targetTableLike;
		}
		if (!targetTableLike.endsWith("%")) {
		   targetTableLike = targetTableLike + "%";
		}
	    }
	return targetTableLike;
    }

    public List<String> getTargetTables(){
	return targetTables;
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

 

    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setAppId(String appId){
        this.appId = appId;
    }

    public void setAppIdLike( String appIdLike){
	this.appIdLike = appIdLike;
    }

    public void setAppIds(List<String> appIds){
        this.appIds = appIds;
    }


    public void setTmpId(String tmpId){
        this.tmpId = tmpId;
    }

    public void setTmpIdLike( String tmpIdLike){
	this.tmpIdLike = tmpIdLike;
    }

    public void setTmpIds(List<String> tmpIds){
        this.tmpIds = tmpIds;
    }


    public void setEmptyTable(Integer emptyTable){
        this.emptyTable = emptyTable;
    }

    public void setEmptyTableGreaterThanOrEqual(Integer emptyTableGreaterThanOrEqual){
        this.emptyTableGreaterThanOrEqual = emptyTableGreaterThanOrEqual;
    }

    public void setEmptyTableLessThanOrEqual(Integer emptyTableLessThanOrEqual){
	this.emptyTableLessThanOrEqual = emptyTableLessThanOrEqual;
    }

    public void setEmptyTables(List<Integer> emptyTables){
        this.emptyTables = emptyTables;
    }


    public void setIncrementFlag(Integer incrementFlag){
        this.incrementFlag = incrementFlag;
    }

    public void setIncrementFlagGreaterThanOrEqual(Integer incrementFlagGreaterThanOrEqual){
        this.incrementFlagGreaterThanOrEqual = incrementFlagGreaterThanOrEqual;
    }

    public void setIncrementFlagLessThanOrEqual(Integer incrementFlagLessThanOrEqual){
	this.incrementFlagLessThanOrEqual = incrementFlagLessThanOrEqual;
    }

    public void setIncrementFlags(List<Integer> incrementFlags){
        this.incrementFlags = incrementFlags;
    }


    public void setTargetDatabase(String targetDatabase){
        this.targetDatabase = targetDatabase;
    }

    public void setTargetDatabaseLike( String targetDatabaseLike){
	this.targetDatabaseLike = targetDatabaseLike;
    }

    public void setTargetDatabases(List<String> targetDatabases){
        this.targetDatabases = targetDatabases;
    }


    public void setTargetTable(String targetTable){
        this.targetTable = targetTable;
    }

    public void setTargetTableLike( String targetTableLike){
	this.targetTableLike = targetTableLike;
    }

    public void setTargetTables(List<String> targetTables){
        this.targetTables = targetTables;
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




    public EimServerDataImpQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public EimServerDataImpQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public EimServerDataImpQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public EimServerDataImpQuery appId(String appId){
	if (appId == null) {
	    throw new RuntimeException("appId is null");
        }         
	this.appId = appId;
	return this;
    }

    public EimServerDataImpQuery appIdLike( String appIdLike){
        if (appIdLike == null) {
            throw new RuntimeException("appId is null");
        }
        this.appIdLike = appIdLike;
        return this;
    }

    public EimServerDataImpQuery appIds(List<String> appIds){
        if (appIds == null) {
            throw new RuntimeException("appIds is empty ");
        }
        this.appIds = appIds;
        return this;
    }


    public EimServerDataImpQuery tmpId(String tmpId){
	if (tmpId == null) {
	    throw new RuntimeException("tmpId is null");
        }         
	this.tmpId = tmpId;
	return this;
    }

    public EimServerDataImpQuery tmpIdLike( String tmpIdLike){
        if (tmpIdLike == null) {
            throw new RuntimeException("tmpId is null");
        }
        this.tmpIdLike = tmpIdLike;
        return this;
    }

    public EimServerDataImpQuery tmpIds(List<String> tmpIds){
        if (tmpIds == null) {
            throw new RuntimeException("tmpIds is empty ");
        }
        this.tmpIds = tmpIds;
        return this;
    }


    public EimServerDataImpQuery emptyTable(Integer emptyTable){
	if (emptyTable == null) {
            throw new RuntimeException("emptyTable is null");
        }         
	this.emptyTable = emptyTable;
	return this;
    }

    public EimServerDataImpQuery emptyTableGreaterThanOrEqual(Integer emptyTableGreaterThanOrEqual){
	if (emptyTableGreaterThanOrEqual == null) {
	    throw new RuntimeException("emptyTable is null");
        }         
	this.emptyTableGreaterThanOrEqual = emptyTableGreaterThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery emptyTableLessThanOrEqual(Integer emptyTableLessThanOrEqual){
        if (emptyTableLessThanOrEqual == null) {
            throw new RuntimeException("emptyTable is null");
        }
        this.emptyTableLessThanOrEqual = emptyTableLessThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery emptyTables(List<Integer> emptyTables){
        if (emptyTables == null) {
            throw new RuntimeException("emptyTables is empty ");
        }
        this.emptyTables = emptyTables;
        return this;
    }


    public EimServerDataImpQuery incrementFlag(Integer incrementFlag){
	if (incrementFlag == null) {
            throw new RuntimeException("incrementFlag is null");
        }         
	this.incrementFlag = incrementFlag;
	return this;
    }

    public EimServerDataImpQuery incrementFlagGreaterThanOrEqual(Integer incrementFlagGreaterThanOrEqual){
	if (incrementFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("incrementFlag is null");
        }         
	this.incrementFlagGreaterThanOrEqual = incrementFlagGreaterThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery incrementFlagLessThanOrEqual(Integer incrementFlagLessThanOrEqual){
        if (incrementFlagLessThanOrEqual == null) {
            throw new RuntimeException("incrementFlag is null");
        }
        this.incrementFlagLessThanOrEqual = incrementFlagLessThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery incrementFlags(List<Integer> incrementFlags){
        if (incrementFlags == null) {
            throw new RuntimeException("incrementFlags is empty ");
        }
        this.incrementFlags = incrementFlags;
        return this;
    }


    public EimServerDataImpQuery targetDatabase(String targetDatabase){
	if (targetDatabase == null) {
	    throw new RuntimeException("targetDatabase is null");
        }         
	this.targetDatabase = targetDatabase;
	return this;
    }

    public EimServerDataImpQuery targetDatabaseLike( String targetDatabaseLike){
        if (targetDatabaseLike == null) {
            throw new RuntimeException("targetDatabase is null");
        }
        this.targetDatabaseLike = targetDatabaseLike;
        return this;
    }

    public EimServerDataImpQuery targetDatabases(List<String> targetDatabases){
        if (targetDatabases == null) {
            throw new RuntimeException("targetDatabases is empty ");
        }
        this.targetDatabases = targetDatabases;
        return this;
    }


    public EimServerDataImpQuery targetTable(String targetTable){
	if (targetTable == null) {
	    throw new RuntimeException("targetTable is null");
        }         
	this.targetTable = targetTable;
	return this;
    }

    public EimServerDataImpQuery targetTableLike( String targetTableLike){
        if (targetTableLike == null) {
            throw new RuntimeException("targetTable is null");
        }
        this.targetTableLike = targetTableLike;
        return this;
    }

    public EimServerDataImpQuery targetTables(List<String> targetTables){
        if (targetTables == null) {
            throw new RuntimeException("targetTables is empty ");
        }
        this.targetTables = targetTables;
        return this;
    }


    public EimServerDataImpQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public EimServerDataImpQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public EimServerDataImpQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public EimServerDataImpQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public EimServerDataImpQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public EimServerDataImpQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public EimServerDataImpQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public EimServerDataImpQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public EimServerDataImpQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public EimServerDataImpQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public EimServerDataImpQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("appId".equals(sortColumn)) {
                orderBy = "E.APP_ID_" + a_x;
            } 

            if ("tmpId".equals(sortColumn)) {
                orderBy = "E.TMP_ID_" + a_x;
            } 

            if ("emptyTable".equals(sortColumn)) {
                orderBy = "E.EMPTY_TABLE_" + a_x;
            } 

            if ("incrementFlag".equals(sortColumn)) {
                orderBy = "E.INCREMENT_FLAG_" + a_x;
            } 

            if ("targetDatabase".equals(sortColumn)) {
                orderBy = "E.TARGET_DATABASE_" + a_x;
            } 

            if ("targetTable".equals(sortColumn)) {
                orderBy = "E.TARGET_TABLE_" + a_x;
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
        addColumn("name", "NAME_");
        addColumn("appId", "APP_ID_");
        addColumn("tmpId", "TMP_ID_");
        addColumn("emptyTable", "EMPTY_TABLE_");
        addColumn("incrementFlag", "INCREMENT_FLAG_");
        addColumn("targetDatabase", "TARGET_DATABASE_");
        addColumn("targetTable", "TARGET_TABLE_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}