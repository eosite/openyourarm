package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormDatasetRelationQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String pageId;
  	protected String pageIdLike;
  	protected List<String> pageIds;
  	protected String widgetId;
  	protected String widgetIdLike;
  	protected List<String> widgetIds;
  	protected String pid;
  	protected String pidLike;
  	protected List<String> pids;
  	protected String attrName;
  	protected String attrNameLike;
  	protected List<String> attrNames;

    public FormDatasetRelationQuery() {

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


    public String getWidgetId(){
        return widgetId;
    }

    public String getWidgetIdLike(){
	    if (widgetIdLike != null && widgetIdLike.trim().length() > 0) {
		if (!widgetIdLike.startsWith("%")) {
		    widgetIdLike = "%" + widgetIdLike;
		}
		if (!widgetIdLike.endsWith("%")) {
		   widgetIdLike = widgetIdLike + "%";
		}
	    }
	return widgetIdLike;
    }

    public List<String> getWidgetIds(){
	return widgetIds;
    }


    public String getPid(){
        return pid;
    }

    public String getPidLike(){
	    if (pidLike != null && pidLike.trim().length() > 0) {
		if (!pidLike.startsWith("%")) {
		    pidLike = "%" + pidLike;
		}
		if (!pidLike.endsWith("%")) {
		   pidLike = pidLike + "%";
		}
	    }
	return pidLike;
    }

    public List<String> getPids(){
	return pids;
    }


    public String getAttrName(){
        return attrName;
    }

    public String getAttrNameLike(){
	    if (attrNameLike != null && attrNameLike.trim().length() > 0) {
		if (!attrNameLike.startsWith("%")) {
		    attrNameLike = "%" + attrNameLike;
		}
		if (!attrNameLike.endsWith("%")) {
		   attrNameLike = attrNameLike + "%";
		}
	    }
	return attrNameLike;
    }

    public List<String> getAttrNames(){
	return attrNames;
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


    public void setWidgetId(String widgetId){
        this.widgetId = widgetId;
    }

    public void setWidgetIdLike( String widgetIdLike){
	this.widgetIdLike = widgetIdLike;
    }

    public void setWidgetIds(List<String> widgetIds){
        this.widgetIds = widgetIds;
    }


    public void setPid(String pid){
        this.pid = pid;
    }

    public void setPidLike( String pidLike){
	this.pidLike = pidLike;
    }

    public void setPids(List<String> pids){
        this.pids = pids;
    }


    public void setAttrName(String attrName){
        this.attrName = attrName;
    }

    public void setAttrNameLike( String attrNameLike){
	this.attrNameLike = attrNameLike;
    }

    public void setAttrNames(List<String> attrNames){
        this.attrNames = attrNames;
    }




    public FormDatasetRelationQuery pageId(String pageId){
	if (pageId == null) {
	    throw new RuntimeException("pageId is null");
        }         
	this.pageId = pageId;
	return this;
    }

    public FormDatasetRelationQuery pageIdLike( String pageIdLike){
        if (pageIdLike == null) {
            throw new RuntimeException("pageId is null");
        }
        this.pageIdLike = pageIdLike;
        return this;
    }

    public FormDatasetRelationQuery pageIds(List<String> pageIds){
        if (pageIds == null) {
            throw new RuntimeException("pageIds is empty ");
        }
        this.pageIds = pageIds;
        return this;
    }


    public FormDatasetRelationQuery widgetId(String widgetId){
	if (widgetId == null) {
	    throw new RuntimeException("widgetId is null");
        }         
	this.widgetId = widgetId;
	return this;
    }

    public FormDatasetRelationQuery widgetIdLike( String widgetIdLike){
        if (widgetIdLike == null) {
            throw new RuntimeException("widgetId is null");
        }
        this.widgetIdLike = widgetIdLike;
        return this;
    }

    public FormDatasetRelationQuery widgetIds(List<String> widgetIds){
        if (widgetIds == null) {
            throw new RuntimeException("widgetIds is empty ");
        }
        this.widgetIds = widgetIds;
        return this;
    }


    public FormDatasetRelationQuery pid(String pid){
	if (pid == null) {
	    throw new RuntimeException("pid is null");
        }         
	this.pid = pid;
	return this;
    }

    public FormDatasetRelationQuery pidLike( String pidLike){
        if (pidLike == null) {
            throw new RuntimeException("pid is null");
        }
        this.pidLike = pidLike;
        return this;
    }

    public FormDatasetRelationQuery pids(List<String> pids){
        if (pids == null) {
            throw new RuntimeException("pids is empty ");
        }
        this.pids = pids;
        return this;
    }


    public FormDatasetRelationQuery attrName(String attrName){
	if (attrName == null) {
	    throw new RuntimeException("attrName is null");
        }         
	this.attrName = attrName;
	return this;
    }

    public FormDatasetRelationQuery attrNameLike( String attrNameLike){
        if (attrNameLike == null) {
            throw new RuntimeException("attrName is null");
        }
        this.attrNameLike = attrNameLike;
        return this;
    }

    public FormDatasetRelationQuery attrNames(List<String> attrNames){
        if (attrNames == null) {
            throw new RuntimeException("attrNames is empty ");
        }
        this.attrNames = attrNames;
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

            if ("widgetId".equals(sortColumn)) {
                orderBy = "E.WIDGETID_" + a_x;
            } 

            if ("pid".equals(sortColumn)) {
                orderBy = "E.PID_" + a_x;
            } 

            if ("attrName".equals(sortColumn)) {
                orderBy = "E.ATTRNAME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("pageId", "PAGEID_");
        addColumn("widgetId", "WIDGETID_");
        addColumn("pid", "PID_");
        addColumn("attrName", "ATTRNAME_");
    }

}