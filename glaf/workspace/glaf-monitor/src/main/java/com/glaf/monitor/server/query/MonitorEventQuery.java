package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorEventQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> eventIds;
	protected Collection<String> appActorIds;
  	protected String objectId;
  	protected String objectIdLike;
  	protected List<String> objectIds;
  	protected String objectType;
  	protected String objectTypeLike;
  	protected List<String> objectTypes;
  	protected String eventType;
  	protected String eventTypeLike;
  	protected List<String> eventTypes;
  	protected String eventMonitorItem;
  	protected String eventMonitorItemLike;
  	protected List<String> eventMonitorItems;
        protected Date happenTimeGreaterThanOrEqual;
  	protected Date happenTimeLessThanOrEqual;
  	protected String snapshot;
  	protected String snapshotLike;
  	protected List<String> snapshots;
        protected Date recoveryTimeGreaterThanOrEqual;
  	protected Date recoveryTimeLessThanOrEqual;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
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

    public MonitorEventQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getEventType(){
        return eventType;
    }

    public String getEventTypeLike(){
	    if (eventTypeLike != null && eventTypeLike.trim().length() > 0) {
		if (!eventTypeLike.startsWith("%")) {
		    eventTypeLike = "%" + eventTypeLike;
		}
		if (!eventTypeLike.endsWith("%")) {
		   eventTypeLike = eventTypeLike + "%";
		}
	    }
	return eventTypeLike;
    }

    public List<String> getEventTypes(){
	return eventTypes;
    }


    public String getEventMonitorItem(){
        return eventMonitorItem;
    }

    public String getEventMonitorItemLike(){
	    if (eventMonitorItemLike != null && eventMonitorItemLike.trim().length() > 0) {
		if (!eventMonitorItemLike.startsWith("%")) {
		    eventMonitorItemLike = "%" + eventMonitorItemLike;
		}
		if (!eventMonitorItemLike.endsWith("%")) {
		   eventMonitorItemLike = eventMonitorItemLike + "%";
		}
	    }
	return eventMonitorItemLike;
    }

    public List<String> getEventMonitorItems(){
	return eventMonitorItems;
    }


    public Date getHappenTimeGreaterThanOrEqual(){
        return happenTimeGreaterThanOrEqual;
    }

    public Date getHappenTimeLessThanOrEqual(){
	return happenTimeLessThanOrEqual;
    }


    public String getSnapshot(){
        return snapshot;
    }

    public String getSnapshotLike(){
	    if (snapshotLike != null && snapshotLike.trim().length() > 0) {
		if (!snapshotLike.startsWith("%")) {
		    snapshotLike = "%" + snapshotLike;
		}
		if (!snapshotLike.endsWith("%")) {
		   snapshotLike = snapshotLike + "%";
		}
	    }
	return snapshotLike;
    }

    public List<String> getSnapshots(){
	return snapshots;
    }


    public Date getRecoveryTimeGreaterThanOrEqual(){
        return recoveryTimeGreaterThanOrEqual;
    }

    public Date getRecoveryTimeLessThanOrEqual(){
	return recoveryTimeLessThanOrEqual;
    }


    public Integer getStatus(){
        return status;
    }

    public Integer getStatusGreaterThanOrEqual(){
        return statusGreaterThanOrEqual;
    }

    public Integer getStatusLessThanOrEqual(){
	return statusLessThanOrEqual;
    }

    public List<Integer> getStatuss(){
	return statuss;
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


    public void setEventType(String eventType){
        this.eventType = eventType;
    }

    public void setEventTypeLike( String eventTypeLike){
	this.eventTypeLike = eventTypeLike;
    }

    public void setEventTypes(List<String> eventTypes){
        this.eventTypes = eventTypes;
    }


    public void setEventMonitorItem(String eventMonitorItem){
        this.eventMonitorItem = eventMonitorItem;
    }

    public void setEventMonitorItemLike( String eventMonitorItemLike){
	this.eventMonitorItemLike = eventMonitorItemLike;
    }

    public void setEventMonitorItems(List<String> eventMonitorItems){
        this.eventMonitorItems = eventMonitorItems;
    }


    public void setHappenTimeGreaterThanOrEqual(Date happenTimeGreaterThanOrEqual){
        this.happenTimeGreaterThanOrEqual = happenTimeGreaterThanOrEqual;
    }

    public void setHappenTimeLessThanOrEqual(Date happenTimeLessThanOrEqual){
	this.happenTimeLessThanOrEqual = happenTimeLessThanOrEqual;
    }


    public void setSnapshot(String snapshot){
        this.snapshot = snapshot;
    }

    public void setSnapshotLike( String snapshotLike){
	this.snapshotLike = snapshotLike;
    }

    public void setSnapshots(List<String> snapshots){
        this.snapshots = snapshots;
    }


    public void setRecoveryTimeGreaterThanOrEqual(Date recoveryTimeGreaterThanOrEqual){
        this.recoveryTimeGreaterThanOrEqual = recoveryTimeGreaterThanOrEqual;
    }

    public void setRecoveryTimeLessThanOrEqual(Date recoveryTimeLessThanOrEqual){
	this.recoveryTimeLessThanOrEqual = recoveryTimeLessThanOrEqual;
    }


    public void setStatus(Integer status){
        this.status = status;
    }

    public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
        this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
    }

    public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual){
	this.statusLessThanOrEqual = statusLessThanOrEqual;
    }

    public void setStatuss(List<Integer> statuss){
        this.statuss = statuss;
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




    public MonitorEventQuery objectId(String objectId){
	if (objectId == null) {
	    throw new RuntimeException("objectId is null");
        }         
	this.objectId = objectId;
	return this;
    }

    public MonitorEventQuery objectIdLike( String objectIdLike){
        if (objectIdLike == null) {
            throw new RuntimeException("objectId is null");
        }
        this.objectIdLike = objectIdLike;
        return this;
    }

    public MonitorEventQuery objectIds(List<String> objectIds){
        if (objectIds == null) {
            throw new RuntimeException("objectIds is empty ");
        }
        this.objectIds = objectIds;
        return this;
    }


    public MonitorEventQuery objectType(String objectType){
	if (objectType == null) {
	    throw new RuntimeException("objectType is null");
        }         
	this.objectType = objectType;
	return this;
    }

    public MonitorEventQuery objectTypeLike( String objectTypeLike){
        if (objectTypeLike == null) {
            throw new RuntimeException("objectType is null");
        }
        this.objectTypeLike = objectTypeLike;
        return this;
    }

    public MonitorEventQuery objectTypes(List<String> objectTypes){
        if (objectTypes == null) {
            throw new RuntimeException("objectTypes is empty ");
        }
        this.objectTypes = objectTypes;
        return this;
    }


    public MonitorEventQuery eventType(String eventType){
	if (eventType == null) {
	    throw new RuntimeException("eventType is null");
        }         
	this.eventType = eventType;
	return this;
    }

    public MonitorEventQuery eventTypeLike( String eventTypeLike){
        if (eventTypeLike == null) {
            throw new RuntimeException("eventType is null");
        }
        this.eventTypeLike = eventTypeLike;
        return this;
    }

    public MonitorEventQuery eventTypes(List<String> eventTypes){
        if (eventTypes == null) {
            throw new RuntimeException("eventTypes is empty ");
        }
        this.eventTypes = eventTypes;
        return this;
    }


    public MonitorEventQuery eventMonitorItem(String eventMonitorItem){
	if (eventMonitorItem == null) {
	    throw new RuntimeException("eventMonitorItem is null");
        }         
	this.eventMonitorItem = eventMonitorItem;
	return this;
    }

    public MonitorEventQuery eventMonitorItemLike( String eventMonitorItemLike){
        if (eventMonitorItemLike == null) {
            throw new RuntimeException("eventMonitorItem is null");
        }
        this.eventMonitorItemLike = eventMonitorItemLike;
        return this;
    }

    public MonitorEventQuery eventMonitorItems(List<String> eventMonitorItems){
        if (eventMonitorItems == null) {
            throw new RuntimeException("eventMonitorItems is empty ");
        }
        this.eventMonitorItems = eventMonitorItems;
        return this;
    }



    public MonitorEventQuery happenTimeGreaterThanOrEqual(Date happenTimeGreaterThanOrEqual){
	if (happenTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("happenTime is null");
        }         
	this.happenTimeGreaterThanOrEqual = happenTimeGreaterThanOrEqual;
        return this;
    }

    public MonitorEventQuery happenTimeLessThanOrEqual(Date happenTimeLessThanOrEqual){
        if (happenTimeLessThanOrEqual == null) {
            throw new RuntimeException("happenTime is null");
        }
        this.happenTimeLessThanOrEqual = happenTimeLessThanOrEqual;
        return this;
    }



    public MonitorEventQuery snapshot(String snapshot){
	if (snapshot == null) {
	    throw new RuntimeException("snapshot is null");
        }         
	this.snapshot = snapshot;
	return this;
    }

    public MonitorEventQuery snapshotLike( String snapshotLike){
        if (snapshotLike == null) {
            throw new RuntimeException("snapshot is null");
        }
        this.snapshotLike = snapshotLike;
        return this;
    }

    public MonitorEventQuery snapshots(List<String> snapshots){
        if (snapshots == null) {
            throw new RuntimeException("snapshots is empty ");
        }
        this.snapshots = snapshots;
        return this;
    }



    public MonitorEventQuery recoveryTimeGreaterThanOrEqual(Date recoveryTimeGreaterThanOrEqual){
	if (recoveryTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("recoveryTime is null");
        }         
	this.recoveryTimeGreaterThanOrEqual = recoveryTimeGreaterThanOrEqual;
        return this;
    }

    public MonitorEventQuery recoveryTimeLessThanOrEqual(Date recoveryTimeLessThanOrEqual){
        if (recoveryTimeLessThanOrEqual == null) {
            throw new RuntimeException("recoveryTime is null");
        }
        this.recoveryTimeLessThanOrEqual = recoveryTimeLessThanOrEqual;
        return this;
    }



    public MonitorEventQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public MonitorEventQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public MonitorEventQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public MonitorEventQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public MonitorEventQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorEventQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorEventQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorEventQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorEventQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }



    public MonitorEventQuery updateby(String updateby){
	if (updateby == null) {
	    throw new RuntimeException("updateby is null");
        }         
	this.updateby = updateby;
	return this;
    }

    public MonitorEventQuery updatebyLike( String updatebyLike){
        if (updatebyLike == null) {
            throw new RuntimeException("updateby is null");
        }
        this.updatebyLike = updatebyLike;
        return this;
    }

    public MonitorEventQuery updatebys(List<String> updatebys){
        if (updatebys == null) {
            throw new RuntimeException("updatebys is empty ");
        }
        this.updatebys = updatebys;
        return this;
    }



    public MonitorEventQuery updatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
	if (updatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updatetime is null");
        }         
	this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
        return this;
    }

    public MonitorEventQuery updatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
        if (updatetimeLessThanOrEqual == null) {
            throw new RuntimeException("updatetime is null");
        }
        this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
        return this;
    }



    public MonitorEventQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public MonitorEventQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public MonitorEventQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public MonitorEventQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("objectId".equals(sortColumn)) {
                orderBy = "E.OBJECT_ID_" + a_x;
            } 

            if ("objectType".equals(sortColumn)) {
                orderBy = "E.OBJECT_TYPE_" + a_x;
            } 

            if ("eventType".equals(sortColumn)) {
                orderBy = "E.EVENT_TYPE_" + a_x;
            } 

            if ("eventMonitorItem".equals(sortColumn)) {
                orderBy = "E.EVENT_MONITOR_ITEM_" + a_x;
            } 

            if ("happenTime".equals(sortColumn)) {
                orderBy = "E.HAPPEN_TIME_" + a_x;
            } 

            if ("snapshot".equals(sortColumn)) {
                orderBy = "E.SNAPSHOT_" + a_x;
            } 

            if ("recoveryTime".equals(sortColumn)) {
                orderBy = "E.RECOVERY_TIME_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS" + a_x;
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
        addColumn("eventId", "EVENT_ID_");
        addColumn("objectId", "OBJECT_ID_");
        addColumn("objectType", "OBJECT_TYPE_");
        addColumn("eventType", "EVENT_TYPE_");
        addColumn("eventMonitorItem", "EVENT_MONITOR_ITEM_");
        addColumn("happenTime", "HAPPEN_TIME_");
        addColumn("snapshot", "SNAPSHOT_");
        addColumn("recoveryTime", "RECOVERY_TIME_");
        addColumn("status", "STATUS");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
        addColumn("updateby", "UPDATEBY_");
        addColumn("updatetime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}