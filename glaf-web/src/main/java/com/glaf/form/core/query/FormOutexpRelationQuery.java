package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormOutexpRelationQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String pageId;
  	protected String pageIdLike;
  	protected List<String> pageIds;
  	protected String widgetId;
  	protected String widgetIdLike;
  	protected List<String> widgetIds;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
  	protected String value;
  	protected String valueLike;
  	protected List<String> values;

    public FormOutexpRelationQuery() {

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


    public String getType(){
        return type;
    }

    public String getTypeLike(){
	    if (typeLike != null && typeLike.trim().length() > 0) {
		if (!typeLike.startsWith("%")) {
		    typeLike = "%" + typeLike;
		}
		if (!typeLike.endsWith("%")) {
		   typeLike = typeLike + "%";
		}
	    }
	return typeLike;
    }

    public List<String> getTypes(){
	return types;
    }


    public String getValue(){
        return value;
    }

    public String getValueLike(){
	    if (valueLike != null && valueLike.trim().length() > 0) {
		if (!valueLike.startsWith("%")) {
		    valueLike = "%" + valueLike;
		}
		if (!valueLike.endsWith("%")) {
		   valueLike = valueLike + "%";
		}
	    }
	return valueLike;
    }

    public List<String> getValues(){
	return values;
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


    public void setType(String type){
        this.type = type;
    }

    public void setTypeLike( String typeLike){
	this.typeLike = typeLike;
    }

    public void setTypes(List<String> types){
        this.types = types;
    }


    public void setValue(String value){
        this.value = value;
    }

    public void setValueLike( String valueLike){
	this.valueLike = valueLike;
    }

    public void setValues(List<String> values){
        this.values = values;
    }




    public FormOutexpRelationQuery pageId(String pageId){
	if (pageId == null) {
	    throw new RuntimeException("pageId is null");
        }         
	this.pageId = pageId;
	return this;
    }

    public FormOutexpRelationQuery pageIdLike( String pageIdLike){
        if (pageIdLike == null) {
            throw new RuntimeException("pageId is null");
        }
        this.pageIdLike = pageIdLike;
        return this;
    }

    public FormOutexpRelationQuery pageIds(List<String> pageIds){
        if (pageIds == null) {
            throw new RuntimeException("pageIds is empty ");
        }
        this.pageIds = pageIds;
        return this;
    }


    public FormOutexpRelationQuery widgetId(String widgetId){
	if (widgetId == null) {
	    throw new RuntimeException("widgetId is null");
        }         
	this.widgetId = widgetId;
	return this;
    }

    public FormOutexpRelationQuery widgetIdLike( String widgetIdLike){
        if (widgetIdLike == null) {
            throw new RuntimeException("widgetId is null");
        }
        this.widgetIdLike = widgetIdLike;
        return this;
    }

    public FormOutexpRelationQuery widgetIds(List<String> widgetIds){
        if (widgetIds == null) {
            throw new RuntimeException("widgetIds is empty ");
        }
        this.widgetIds = widgetIds;
        return this;
    }


    public FormOutexpRelationQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public FormOutexpRelationQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public FormOutexpRelationQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public FormOutexpRelationQuery value(String value){
	if (value == null) {
	    throw new RuntimeException("value is null");
        }         
	this.value = value;
	return this;
    }

    public FormOutexpRelationQuery valueLike( String valueLike){
        if (valueLike == null) {
            throw new RuntimeException("value is null");
        }
        this.valueLike = valueLike;
        return this;
    }

    public FormOutexpRelationQuery values(List<String> values){
        if (values == null) {
            throw new RuntimeException("values is empty ");
        }
        this.values = values;
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

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("value".equals(sortColumn)) {
                orderBy = "E.VALUE_" + a_x;
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
        addColumn("type", "TYPE_");
        addColumn("value", "VALUE_");
    }

}