package com.glaf.bim.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class BimModelQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String parentId;
  	protected String parentIdLike;
  	protected List<String> parentIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String nodeType;
  	protected String nodeTypeLike;
  	protected List<String> nodeTypes;
  	protected String describe;
  	protected String describeLike;
  	protected List<String> describes;

    public BimModelQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    /*public String getParentId(){
        return parentId;
    }*/

    public String getParentIdLike(){
	    if (parentIdLike != null && parentIdLike.trim().length() > 0) {
		if (!parentIdLike.startsWith("%")) {
		    parentIdLike = "%" + parentIdLike;
		}
		if (!parentIdLike.endsWith("%")) {
		   parentIdLike = parentIdLike + "%";
		}
	    }
	return parentIdLike;
    }

   /* public List<String> getParentIds(){
	return parentIds;
    }*/


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


    public String getNodeType(){
        return nodeType;
    }

    public String getNodeTypeLike(){
	    if (nodeTypeLike != null && nodeTypeLike.trim().length() > 0) {
		if (!nodeTypeLike.startsWith("%")) {
		    nodeTypeLike = "%" + nodeTypeLike;
		}
		if (!nodeTypeLike.endsWith("%")) {
		   nodeTypeLike = nodeTypeLike + "%";
		}
	    }
	return nodeTypeLike;
    }

    public List<String> getNodeTypes(){
	return nodeTypes;
    }


    public String getDescribe(){
        return describe;
    }

    public String getDescribeLike(){
	    if (describeLike != null && describeLike.trim().length() > 0) {
		if (!describeLike.startsWith("%")) {
		    describeLike = "%" + describeLike;
		}
		if (!describeLike.endsWith("%")) {
		   describeLike = describeLike + "%";
		}
	    }
	return describeLike;
    }

    public List<String> getDescribes(){
	return describes;
    }


 

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public void setParentIdLike( String parentIdLike){
	this.parentIdLike = parentIdLike;
    }

   /* public void setParentIds(List<String> parentIds){
        this.parentIds = parentIds;
    }*/


    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setNodeType(String nodeType){
        this.nodeType = nodeType;
    }

    public void setNodeTypeLike( String nodeTypeLike){
	this.nodeTypeLike = nodeTypeLike;
    }

    public void setNodeTypes(List<String> nodeTypes){
        this.nodeTypes = nodeTypes;
    }


    public void setDescribe(String describe){
        this.describe = describe;
    }

    public void setDescribeLike( String describeLike){
	this.describeLike = describeLike;
    }

    public void setDescribes(List<String> describes){
        this.describes = describes;
    }




    public BimModelQuery parentId(String parentId){
	if (parentId == null) {
	    throw new RuntimeException("parentId is null");
        }         
	this.parentId = parentId;
	return this;
    }

    public BimModelQuery parentIdLike( String parentIdLike){
        if (parentIdLike == null) {
            throw new RuntimeException("parentId is null");
        }
        this.parentIdLike = parentIdLike;
        return this;
    }

   /* public BimModelQuery parentIds(List<String> parentIds){
        if (parentIds == null) {
            throw new RuntimeException("parentIds is empty ");
        }
        this.parentIds = parentIds;
        return this;
    }*/


    public BimModelQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public BimModelQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public BimModelQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public BimModelQuery nodeType(String nodeType){
	if (nodeType == null) {
	    throw new RuntimeException("nodeType is null");
        }         
	this.nodeType = nodeType;
	return this;
    }

    public BimModelQuery nodeTypeLike( String nodeTypeLike){
        if (nodeTypeLike == null) {
            throw new RuntimeException("nodeType is null");
        }
        this.nodeTypeLike = nodeTypeLike;
        return this;
    }

    public BimModelQuery nodeTypes(List<String> nodeTypes){
        if (nodeTypes == null) {
            throw new RuntimeException("nodeTypes is empty ");
        }
        this.nodeTypes = nodeTypes;
        return this;
    }


    public BimModelQuery describe(String describe){
	if (describe == null) {
	    throw new RuntimeException("describe is null");
        }         
	this.describe = describe;
	return this;
    }

    public BimModelQuery describeLike( String describeLike){
        if (describeLike == null) {
            throw new RuntimeException("describe is null");
        }
        this.describeLike = describeLike;
        return this;
    }

    public BimModelQuery describes(List<String> describes){
        if (describes == null) {
            throw new RuntimeException("describes is empty ");
        }
        this.describes = describes;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("parentId".equals(sortColumn)) {
                orderBy = "E.PARENTID_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("nodeType".equals(sortColumn)) {
                orderBy = "E.NODETYPE_" + a_x;
            } 

            if ("describe".equals(sortColumn)) {
                orderBy = "E.DESCRIBE_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("parentId", "PARENTID_");
        addColumn("name", "NAME_");
        addColumn("nodeType", "NODETYPE_");
        addColumn("describe", "DESCRIBE_");
    }

}