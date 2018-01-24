package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormParamsQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String pageId;
  	protected String pageIdLike;
  	protected List<String> pageIds;
  	protected String widgetId;
  	protected String widgetIdLike;
  	protected List<String> widgetIds;
  	protected String paramName;
  	protected String paramNameLike;
  	protected List<String> paramNames;
  	protected String datasetId;
  	protected String datasetIdLike;
  	protected List<String> datasetIds;

    public FormParamsQuery() {

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


    public String getParamName(){
        return paramName;
    }

    public String getParamNameLike(){
	    if (paramNameLike != null && paramNameLike.trim().length() > 0) {
		if (!paramNameLike.startsWith("%")) {
		    paramNameLike = "%" + paramNameLike;
		}
		if (!paramNameLike.endsWith("%")) {
		   paramNameLike = paramNameLike + "%";
		}
	    }
	return paramNameLike;
    }

    public List<String> getParamNames(){
	return paramNames;
    }


    public String getDatasetId(){
        return datasetId;
    }

    public String getDatasetIdLike(){
	    if (datasetIdLike != null && datasetIdLike.trim().length() > 0) {
		if (!datasetIdLike.startsWith("%")) {
		    datasetIdLike = "%" + datasetIdLike;
		}
		if (!datasetIdLike.endsWith("%")) {
		   datasetIdLike = datasetIdLike + "%";
		}
	    }
	return datasetIdLike;
    }

    public List<String> getDatasetIds(){
	return datasetIds;
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


    public void setParamName(String paramName){
        this.paramName = paramName;
    }

    public void setParamNameLike( String paramNameLike){
	this.paramNameLike = paramNameLike;
    }

    public void setParamNames(List<String> paramNames){
        this.paramNames = paramNames;
    }


    public void setDatasetId(String datasetId){
        this.datasetId = datasetId;
    }

    public void setDatasetIdLike( String datasetIdLike){
	this.datasetIdLike = datasetIdLike;
    }

    public void setDatasetIds(List<String> datasetIds){
        this.datasetIds = datasetIds;
    }




    public FormParamsQuery pageId(String pageId){
	if (pageId == null) {
	    throw new RuntimeException("pageId is null");
        }         
	this.pageId = pageId;
	return this;
    }

    public FormParamsQuery pageIdLike( String pageIdLike){
        if (pageIdLike == null) {
            throw new RuntimeException("pageId is null");
        }
        this.pageIdLike = pageIdLike;
        return this;
    }

    public FormParamsQuery pageIds(List<String> pageIds){
        if (pageIds == null) {
            throw new RuntimeException("pageIds is empty ");
        }
        this.pageIds = pageIds;
        return this;
    }


    public FormParamsQuery widgetId(String widgetId){
	if (widgetId == null) {
	    throw new RuntimeException("widgetId is null");
        }         
	this.widgetId = widgetId;
	return this;
    }

    public FormParamsQuery widgetIdLike( String widgetIdLike){
        if (widgetIdLike == null) {
            throw new RuntimeException("widgetId is null");
        }
        this.widgetIdLike = widgetIdLike;
        return this;
    }

    public FormParamsQuery widgetIds(List<String> widgetIds){
        if (widgetIds == null) {
            throw new RuntimeException("widgetIds is empty ");
        }
        this.widgetIds = widgetIds;
        return this;
    }


    public FormParamsQuery paramName(String paramName){
	if (paramName == null) {
	    throw new RuntimeException("paramName is null");
        }         
	this.paramName = paramName;
	return this;
    }

    public FormParamsQuery paramNameLike( String paramNameLike){
        if (paramNameLike == null) {
            throw new RuntimeException("paramName is null");
        }
        this.paramNameLike = paramNameLike;
        return this;
    }

    public FormParamsQuery paramNames(List<String> paramNames){
        if (paramNames == null) {
            throw new RuntimeException("paramNames is empty ");
        }
        this.paramNames = paramNames;
        return this;
    }


    public FormParamsQuery datasetId(String datasetId){
	if (datasetId == null) {
	    throw new RuntimeException("datasetId is null");
        }         
	this.datasetId = datasetId;
	return this;
    }

    public FormParamsQuery datasetIdLike( String datasetIdLike){
        if (datasetIdLike == null) {
            throw new RuntimeException("datasetId is null");
        }
        this.datasetIdLike = datasetIdLike;
        return this;
    }

    public FormParamsQuery datasetIds(List<String> datasetIds){
        if (datasetIds == null) {
            throw new RuntimeException("datasetIds is empty ");
        }
        this.datasetIds = datasetIds;
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

            if ("paramName".equals(sortColumn)) {
                orderBy = "E.PARAMNAME_" + a_x;
            } 

            if ("datasetId".equals(sortColumn)) {
                orderBy = "E.DATASETID_" + a_x;
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
        addColumn("paramName", "PARAMNAME_");
        addColumn("datasetId", "DATASETID_");
    }

}