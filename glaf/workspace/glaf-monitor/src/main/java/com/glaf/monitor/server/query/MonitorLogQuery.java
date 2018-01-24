package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorLogQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
        protected Date logTimeGreaterThanOrEqual;
  	protected Date logTimeLessThanOrEqual;
  	protected String logPath;
  	protected String logPathLike;
  	protected List<String> logPaths;
  	protected String objectId;
  	protected String objectIdLike;
  	protected List<String> objectIds;
  	protected String objectType;
  	protected String objectTypeLike;
  	protected List<String> objectTypes;
  	protected String createby;
  	protected String createbyLike;
  	protected List<String> createbys;
        protected Date createtimeGreaterThanOrEqual;
  	protected Date createtimeLessThanOrEqual;
  	protected String updateby;
  	protected String updatebyLike;
  	protected List<String> updatebys;
        protected Date updatetimeGreaterThanOrEqual;
  	protected Date updatetimeLessThanOrEqual;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;

    public MonitorLogQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Date getLogTimeGreaterThanOrEqual(){
        return logTimeGreaterThanOrEqual;
    }

    public Date getLogTimeLessThanOrEqual(){
	return logTimeLessThanOrEqual;
    }


    public String getLogPath(){
        return logPath;
    }

    public String getLogPathLike(){
	    if (logPathLike != null && logPathLike.trim().length() > 0) {
		if (!logPathLike.startsWith("%")) {
		    logPathLike = "%" + logPathLike;
		}
		if (!logPathLike.endsWith("%")) {
		   logPathLike = logPathLike + "%";
		}
	    }
	return logPathLike;
    }

    public List<String> getLogPaths(){
	return logPaths;
    }


    public String getObjectId(){
        return objectId;
    }

    public String getObjectIdLike(){
	    if (objectIdLike != null && objectIdLike.trim().length() > 0) {
		if (!objectIdLike.startsWith("%")) {
		    objectIdLike = "%" + objectIdLike;
		}
		if (!objectIdLike.endsWith("%")) {
		   objectIdLike = objectIdLike + "%";
		}
	    }
	return objectIdLike;
    }

    public List<String> getObjectIds(){
	return objectIds;
    }


    public String getObjectType(){
        return objectType;
    }

    public String getObjectTypeLike(){
	    if (objectTypeLike != null && objectTypeLike.trim().length() > 0) {
		if (!objectTypeLike.startsWith("%")) {
		    objectTypeLike = "%" + objectTypeLike;
		}
		if (!objectTypeLike.endsWith("%")) {
		   objectTypeLike = objectTypeLike + "%";
		}
	    }
	return objectTypeLike;
    }

    public List<String> getObjectTypes(){
	return objectTypes;
    }


    public String getCreateby(){
        return createby;
    }

    public String getCreatebyLike(){
	    if (createbyLike != null && createbyLike.trim().length() > 0) {
		if (!createbyLike.startsWith("%")) {
		    createbyLike = "%" + createbyLike;
		}
		if (!createbyLike.endsWith("%")) {
		   createbyLike = createbyLike + "%";
		}
	    }
	return createbyLike;
    }

    public List<String> getCreatebys(){
	return createbys;
    }


    public Date getCreatetimeGreaterThanOrEqual(){
        return createtimeGreaterThanOrEqual;
    }

    public Date getCreatetimeLessThanOrEqual(){
	return createtimeLessThanOrEqual;
    }


    public String getUpdateby(){
        return updateby;
    }

    public String getUpdatebyLike(){
	    if (updatebyLike != null && updatebyLike.trim().length() > 0) {
		if (!updatebyLike.startsWith("%")) {
		    updatebyLike = "%" + updatebyLike;
		}
		if (!updatebyLike.endsWith("%")) {
		   updatebyLike = updatebyLike + "%";
		}
	    }
	return updatebyLike;
    }

    public List<String> getUpdatebys(){
	return updatebys;
    }


    public Date getUpdatetimeGreaterThanOrEqual(){
        return updatetimeGreaterThanOrEqual;
    }

    public Date getUpdatetimeLessThanOrEqual(){
	return updatetimeLessThanOrEqual;
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

 

    public void setLogTimeGreaterThanOrEqual(Date logTimeGreaterThanOrEqual){
        this.logTimeGreaterThanOrEqual = logTimeGreaterThanOrEqual;
    }

    public void setLogTimeLessThanOrEqual(Date logTimeLessThanOrEqual){
	this.logTimeLessThanOrEqual = logTimeLessThanOrEqual;
    }


    public void setLogPath(String logPath){
        this.logPath = logPath;
    }

    public void setLogPathLike( String logPathLike){
	this.logPathLike = logPathLike;
    }

    public void setLogPaths(List<String> logPaths){
        this.logPaths = logPaths;
    }


    public void setObjectId(String objectId){
        this.objectId = objectId;
    }

    public void setObjectIdLike( String objectIdLike){
	this.objectIdLike = objectIdLike;
    }

    public void setObjectIds(List<String> objectIds){
        this.objectIds = objectIds;
    }


    public void setObjectType(String objectType){
        this.objectType = objectType;
    }

    public void setObjectTypeLike( String objectTypeLike){
	this.objectTypeLike = objectTypeLike;
    }

    public void setObjectTypes(List<String> objectTypes){
        this.objectTypes = objectTypes;
    }


    public void setCreateby(String createby){
        this.createby = createby;
    }

    public void setCreatebyLike( String createbyLike){
	this.createbyLike = createbyLike;
    }

    public void setCreatebys(List<String> createbys){
        this.createbys = createbys;
    }


    public void setCreatetimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
        this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
    }

    public void setCreatetimeLessThanOrEqual(Date createtimeLessThanOrEqual){
	this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
    }


    public void setUpdateby(String updateby){
        this.updateby = updateby;
    }

    public void setUpdatebyLike( String updatebyLike){
	this.updatebyLike = updatebyLike;
    }

    public void setUpdatebys(List<String> updatebys){
        this.updatebys = updatebys;
    }


    public void setUpdatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
        this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
    }

    public void setUpdatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
	this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
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





    public MonitorLogQuery logTimeGreaterThanOrEqual(Date logTimeGreaterThanOrEqual){
	if (logTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("logTime is null");
        }         
	this.logTimeGreaterThanOrEqual = logTimeGreaterThanOrEqual;
        return this;
    }

    public MonitorLogQuery logTimeLessThanOrEqual(Date logTimeLessThanOrEqual){
        if (logTimeLessThanOrEqual == null) {
            throw new RuntimeException("logTime is null");
        }
        this.logTimeLessThanOrEqual = logTimeLessThanOrEqual;
        return this;
    }



    public MonitorLogQuery logPath(String logPath){
	if (logPath == null) {
	    throw new RuntimeException("logPath is null");
        }         
	this.logPath = logPath;
	return this;
    }

    public MonitorLogQuery logPathLike( String logPathLike){
        if (logPathLike == null) {
            throw new RuntimeException("logPath is null");
        }
        this.logPathLike = logPathLike;
        return this;
    }

    public MonitorLogQuery logPaths(List<String> logPaths){
        if (logPaths == null) {
            throw new RuntimeException("logPaths is empty ");
        }
        this.logPaths = logPaths;
        return this;
    }


    public MonitorLogQuery objectId(String objectId){
	if (objectId == null) {
	    throw new RuntimeException("objectId is null");
        }         
	this.objectId = objectId;
	return this;
    }

    public MonitorLogQuery objectIdLike( String objectIdLike){
        if (objectIdLike == null) {
            throw new RuntimeException("objectId is null");
        }
        this.objectIdLike = objectIdLike;
        return this;
    }

    public MonitorLogQuery objectIds(List<String> objectIds){
        if (objectIds == null) {
            throw new RuntimeException("objectIds is empty ");
        }
        this.objectIds = objectIds;
        return this;
    }


    public MonitorLogQuery objectType(String objectType){
	if (objectType == null) {
	    throw new RuntimeException("objectType is null");
        }         
	this.objectType = objectType;
	return this;
    }

    public MonitorLogQuery objectTypeLike( String objectTypeLike){
        if (objectTypeLike == null) {
            throw new RuntimeException("objectType is null");
        }
        this.objectTypeLike = objectTypeLike;
        return this;
    }

    public MonitorLogQuery objectTypes(List<String> objectTypes){
        if (objectTypes == null) {
            throw new RuntimeException("objectTypes is empty ");
        }
        this.objectTypes = objectTypes;
        return this;
    }


    public MonitorLogQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorLogQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorLogQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorLogQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorLogQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }



    public MonitorLogQuery updateby(String updateby){
	if (updateby == null) {
	    throw new RuntimeException("updateby is null");
        }         
	this.updateby = updateby;
	return this;
    }

    public MonitorLogQuery updatebyLike( String updatebyLike){
        if (updatebyLike == null) {
            throw new RuntimeException("updateby is null");
        }
        this.updatebyLike = updatebyLike;
        return this;
    }

    public MonitorLogQuery updatebys(List<String> updatebys){
        if (updatebys == null) {
            throw new RuntimeException("updatebys is empty ");
        }
        this.updatebys = updatebys;
        return this;
    }



    public MonitorLogQuery updatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
	if (updatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updatetime is null");
        }         
	this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
        return this;
    }

    public MonitorLogQuery updatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
        if (updatetimeLessThanOrEqual == null) {
            throw new RuntimeException("updatetime is null");
        }
        this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
        return this;
    }



    public MonitorLogQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public MonitorLogQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public MonitorLogQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public MonitorLogQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("logTime".equals(sortColumn)) {
                orderBy = "E.LOG_TIME_" + a_x;
            } 

            if ("logPath".equals(sortColumn)) {
                orderBy = "E.LOG_PATH_" + a_x;
            } 

            if ("objectId".equals(sortColumn)) {
                orderBy = "E.OBJECT_ID_" + a_x;
            } 

            if ("objectType".equals(sortColumn)) {
                orderBy = "E.OBJECT_TYPE_" + a_x;
            } 

            if ("createby".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createtime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateby".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updatetime".equals(sortColumn)) {
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
        addColumn("logTime", "LOG_TIME_");
        addColumn("logPath", "LOG_PATH_");
        addColumn("objectId", "OBJECT_ID_");
        addColumn("objectType", "OBJECT_TYPE_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
        addColumn("updateby", "UPDATEBY_");
        addColumn("updatetime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}