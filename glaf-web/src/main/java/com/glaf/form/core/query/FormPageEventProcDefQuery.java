package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormPageEventProcDefQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String pageId;
  	protected String pageIdLike;
  	protected List<String> pageIds;
  	protected String compId;
  	protected String compIdLike;
  	protected List<String> compIds;
  	protected String event_;
  	protected String event_Like;
  	protected List<String> event_s;
  	protected String procDefKey;
  	protected String procDefKeyLike;
  	protected List<String> procDefKeys;
  	protected String procDefId;
  	protected String procDefIdLike;
  	protected List<String> procDefIds;
  	protected String procModelId;
  	protected String procModelIdLike;
  	protected List<String> procModelIds;

    public FormPageEventProcDefQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getPageId(){
        return pageId;
    }

    public String getPageIdLike(){
	    if (pageIdLike != null && pageIdLike.trim().length() > 0) {
		if (!pageIdLike.startsWith("%")) {
		    pageIdLike = "%" + pageIdLike;
		}
		if (!pageIdLike.endsWith("%")) {
		   pageIdLike = pageIdLike + "%";
		}
	    }
	return pageIdLike;
    }

    public List<String> getPageIds(){
	return pageIds;
    }


    public String getCompId(){
        return compId;
    }

    public String getCompIdLike(){
	    if (compIdLike != null && compIdLike.trim().length() > 0) {
		if (!compIdLike.startsWith("%")) {
		    compIdLike = "%" + compIdLike;
		}
		if (!compIdLike.endsWith("%")) {
		   compIdLike = compIdLike + "%";
		}
	    }
	return compIdLike;
    }

    public List<String> getCompIds(){
	return compIds;
    }


    public String getEvent_(){
        return event_;
    }

    public String getEvent_Like(){
	    if (event_Like != null && event_Like.trim().length() > 0) {
		if (!event_Like.startsWith("%")) {
		    event_Like = "%" + event_Like;
		}
		if (!event_Like.endsWith("%")) {
		   event_Like = event_Like + "%";
		}
	    }
	return event_Like;
    }

    public List<String> getEvent_s(){
	return event_s;
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


 

    public void setPageId(String pageId){
        this.pageId = pageId;
    }

    public void setPageIdLike( String pageIdLike){
	this.pageIdLike = pageIdLike;
    }

    public void setPageIds(List<String> pageIds){
        this.pageIds = pageIds;
    }


    public void setCompId(String compId){
        this.compId = compId;
    }

    public void setCompIdLike( String compIdLike){
	this.compIdLike = compIdLike;
    }

    public void setCompIds(List<String> compIds){
        this.compIds = compIds;
    }


    public void setEvent_(String event_){
        this.event_ = event_;
    }

    public void setEvent_Like( String event_Like){
	this.event_Like = event_Like;
    }

    public void setEvent_s(List<String> event_s){
        this.event_s = event_s;
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




    public FormPageEventProcDefQuery pageId(String pageId){
	if (pageId == null) {
	    throw new RuntimeException("pageId is null");
        }         
	this.pageId = pageId;
	return this;
    }

    public FormPageEventProcDefQuery pageIdLike( String pageIdLike){
        if (pageIdLike == null) {
            throw new RuntimeException("pageId is null");
        }
        this.pageIdLike = pageIdLike;
        return this;
    }

    public FormPageEventProcDefQuery pageIds(List<String> pageIds){
        if (pageIds == null) {
            throw new RuntimeException("pageIds is empty ");
        }
        this.pageIds = pageIds;
        return this;
    }


    public FormPageEventProcDefQuery compId(String compId){
	if (compId == null) {
	    throw new RuntimeException("compId is null");
        }         
	this.compId = compId;
	return this;
    }

    public FormPageEventProcDefQuery compIdLike( String compIdLike){
        if (compIdLike == null) {
            throw new RuntimeException("compId is null");
        }
        this.compIdLike = compIdLike;
        return this;
    }

    public FormPageEventProcDefQuery compIds(List<String> compIds){
        if (compIds == null) {
            throw new RuntimeException("compIds is empty ");
        }
        this.compIds = compIds;
        return this;
    }


    public FormPageEventProcDefQuery event_(String event_){
	if (event_ == null) {
	    throw new RuntimeException("event_ is null");
        }         
	this.event_ = event_;
	return this;
    }

    public FormPageEventProcDefQuery event_Like( String event_Like){
        if (event_Like == null) {
            throw new RuntimeException("event_ is null");
        }
        this.event_Like = event_Like;
        return this;
    }

    public FormPageEventProcDefQuery event_s(List<String> event_s){
        if (event_s == null) {
            throw new RuntimeException("event_s is empty ");
        }
        this.event_s = event_s;
        return this;
    }


    public FormPageEventProcDefQuery procDefKey(String procDefKey){
	if (procDefKey == null) {
	    throw new RuntimeException("procDefKey is null");
        }         
	this.procDefKey = procDefKey;
	return this;
    }

    public FormPageEventProcDefQuery procDefKeyLike( String procDefKeyLike){
        if (procDefKeyLike == null) {
            throw new RuntimeException("procDefKey is null");
        }
        this.procDefKeyLike = procDefKeyLike;
        return this;
    }

    public FormPageEventProcDefQuery procDefKeys(List<String> procDefKeys){
        if (procDefKeys == null) {
            throw new RuntimeException("procDefKeys is empty ");
        }
        this.procDefKeys = procDefKeys;
        return this;
    }


    public FormPageEventProcDefQuery procDefId(String procDefId){
	if (procDefId == null) {
	    throw new RuntimeException("procDefId is null");
        }         
	this.procDefId = procDefId;
	return this;
    }

    public FormPageEventProcDefQuery procDefIdLike( String procDefIdLike){
        if (procDefIdLike == null) {
            throw new RuntimeException("procDefId is null");
        }
        this.procDefIdLike = procDefIdLike;
        return this;
    }

    public FormPageEventProcDefQuery procDefIds(List<String> procDefIds){
        if (procDefIds == null) {
            throw new RuntimeException("procDefIds is empty ");
        }
        this.procDefIds = procDefIds;
        return this;
    }


    public FormPageEventProcDefQuery procModelId(String procModelId){
	if (procModelId == null) {
	    throw new RuntimeException("procModelId is null");
        }         
	this.procModelId = procModelId;
	return this;
    }

    public FormPageEventProcDefQuery procModelIdLike( String procModelIdLike){
        if (procModelIdLike == null) {
            throw new RuntimeException("procModelId is null");
        }
        this.procModelIdLike = procModelIdLike;
        return this;
    }

    public FormPageEventProcDefQuery procModelIds(List<String> procModelIds){
        if (procModelIds == null) {
            throw new RuntimeException("procModelIds is empty ");
        }
        this.procModelIds = procModelIds;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("pageId".equals(sortColumn)) {
                orderBy = "E.PAGEID_" + a_x;
            } 

            if ("compId".equals(sortColumn)) {
                orderBy = "E.COMPID_" + a_x;
            } 

            if ("event_".equals(sortColumn)) {
                orderBy = "E.EVENT_" + a_x;
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

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("pageId", "PAGEID_");
        addColumn("compId", "COMPID_");
        addColumn("event_", "EVENT_");
        addColumn("procDefKey", "PROCDEF_KEY_");
        addColumn("procDefId", "PROCDEF_ID_");
        addColumn("procModelId", "PROCMODEL_ID_");
    }

}