package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormDatasetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String datasetId;
  	protected String datasetIdLike;
  	protected List<String> datasetIds;
  	protected String columnName;
  	protected String columnNameLike;
  	protected List<String> columnNames;
  	protected String pageId;
  	protected String pageIdLike;
  	protected List<String> pageIds;
  	protected String widgetId;
  	protected String widgetIdLike;
  	protected List<String> widgetIds;

    public FormDatasetQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getColumnName(){
        return columnName;
    }

    public String getColumnNameLike(){
	    if (columnNameLike != null && columnNameLike.trim().length() > 0) {
		if (!columnNameLike.startsWith("%")) {
		    columnNameLike = "%" + columnNameLike;
		}
		if (!columnNameLike.endsWith("%")) {
		   columnNameLike = columnNameLike + "%";
		}
	    }
	return columnNameLike;
    }

    public List<String> getColumnNames(){
	return columnNames;
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


 

    public void setDatasetId(String datasetId){
        this.datasetId = datasetId;
    }

    public void setDatasetIdLike( String datasetIdLike){
	this.datasetIdLike = datasetIdLike;
    }

    public void setDatasetIds(List<String> datasetIds){
        this.datasetIds = datasetIds;
    }


    public void setColumnName(String columnName){
        this.columnName = columnName;
    }

    public void setColumnNameLike( String columnNameLike){
	this.columnNameLike = columnNameLike;
    }

    public void setColumnNames(List<String> columnNames){
        this.columnNames = columnNames;
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




    public FormDatasetQuery datasetId(String datasetId){
	if (datasetId == null) {
	    throw new RuntimeException("datasetId is null");
        }         
	this.datasetId = datasetId;
	return this;
    }

    public FormDatasetQuery datasetIdLike( String datasetIdLike){
        if (datasetIdLike == null) {
            throw new RuntimeException("datasetId is null");
        }
        this.datasetIdLike = datasetIdLike;
        return this;
    }

    public FormDatasetQuery datasetIds(List<String> datasetIds){
        if (datasetIds == null) {
            throw new RuntimeException("datasetIds is empty ");
        }
        this.datasetIds = datasetIds;
        return this;
    }


    public FormDatasetQuery columnName(String columnName){
	if (columnName == null) {
	    throw new RuntimeException("columnName is null");
        }         
	this.columnName = columnName;
	return this;
    }

    public FormDatasetQuery columnNameLike( String columnNameLike){
        if (columnNameLike == null) {
            throw new RuntimeException("columnName is null");
        }
        this.columnNameLike = columnNameLike;
        return this;
    }

    public FormDatasetQuery columnNames(List<String> columnNames){
        if (columnNames == null) {
            throw new RuntimeException("columnNames is empty ");
        }
        this.columnNames = columnNames;
        return this;
    }


    public FormDatasetQuery pageId(String pageId){
	if (pageId == null) {
	    throw new RuntimeException("pageId is null");
        }         
	this.pageId = pageId;
	return this;
    }

    public FormDatasetQuery pageIdLike( String pageIdLike){
        if (pageIdLike == null) {
            throw new RuntimeException("pageId is null");
        }
        this.pageIdLike = pageIdLike;
        return this;
    }

    public FormDatasetQuery pageIds(List<String> pageIds){
        if (pageIds == null) {
            throw new RuntimeException("pageIds is empty ");
        }
        this.pageIds = pageIds;
        return this;
    }


    public FormDatasetQuery widgetId(String widgetId){
	if (widgetId == null) {
	    throw new RuntimeException("widgetId is null");
        }         
	this.widgetId = widgetId;
	return this;
    }

    public FormDatasetQuery widgetIdLike( String widgetIdLike){
        if (widgetIdLike == null) {
            throw new RuntimeException("widgetId is null");
        }
        this.widgetIdLike = widgetIdLike;
        return this;
    }

    public FormDatasetQuery widgetIds(List<String> widgetIds){
        if (widgetIds == null) {
            throw new RuntimeException("widgetIds is empty ");
        }
        this.widgetIds = widgetIds;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("datasetId".equals(sortColumn)) {
                orderBy = "E.DATASETID_" + a_x;
            } 

            if ("columnName".equals(sortColumn)) {
                orderBy = "E.COLUMNNAME_" + a_x;
            } 

            if ("pageId".equals(sortColumn)) {
                orderBy = "E.PAGEID_" + a_x;
            } 

            if ("widgetId".equals(sortColumn)) {
                orderBy = "E.WIDGETID_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("datasetId", "DATASETID_");
        addColumn("columnName", "COLUMNNAME_");
        addColumn("pageId", "PAGEID_");
        addColumn("widgetId", "WIDGETID_");
    }

}