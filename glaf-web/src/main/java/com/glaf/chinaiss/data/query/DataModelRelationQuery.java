package com.glaf.chinaiss.data.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataModelRelationQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String columnName;
  	protected String columnNameLike;
  	protected List<String> columnNames;
  	protected String description;
  	protected String descriptionLike;
  	protected List<String> descriptions;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
  	protected String relateTable;
  	protected String relateTableLike;
  	protected List<String> relateTables;
  	protected String relateColumn;
  	protected String relateColumnLike;
  	protected List<String> relateColumns;
  	protected String relateType;
  	protected String relateTypeLike;
  	protected List<String> relateTypes;
  	protected String topId;
  	protected String topIdLike;
  	protected List<String> topIds;
  	protected String parentId;
  	protected String parentIdLike;
  	protected List<String> parentIds;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;
  	protected Integer listNo;
  	protected Integer listNoGreaterThanOrEqual;
  	protected Integer listNoLessThanOrEqual;
  	protected List<Integer> listNos;

    public DataModelRelationQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getDescription(){
        return description;
    }

    public String getDescriptionLike(){
	    if (descriptionLike != null && descriptionLike.trim().length() > 0) {
		if (!descriptionLike.startsWith("%")) {
		    descriptionLike = "%" + descriptionLike;
		}
		if (!descriptionLike.endsWith("%")) {
		   descriptionLike = descriptionLike + "%";
		}
	    }
	return descriptionLike;
    }

    public List<String> getDescriptions(){
	return descriptions;
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


    public String getRelateTable(){
        return relateTable;
    }

    public String getRelateTableLike(){
	    if (relateTableLike != null && relateTableLike.trim().length() > 0) {
		if (!relateTableLike.startsWith("%")) {
		    relateTableLike = "%" + relateTableLike;
		}
		if (!relateTableLike.endsWith("%")) {
		   relateTableLike = relateTableLike + "%";
		}
	    }
	return relateTableLike;
    }

    public List<String> getRelateTables(){
	return relateTables;
    }


    public String getRelateColumn(){
        return relateColumn;
    }

    public String getRelateColumnLike(){
	    if (relateColumnLike != null && relateColumnLike.trim().length() > 0) {
		if (!relateColumnLike.startsWith("%")) {
		    relateColumnLike = "%" + relateColumnLike;
		}
		if (!relateColumnLike.endsWith("%")) {
		   relateColumnLike = relateColumnLike + "%";
		}
	    }
	return relateColumnLike;
    }

    public List<String> getRelateColumns(){
	return relateColumns;
    }


    public String getRelateType(){
        return relateType;
    }

    public String getRelateTypeLike(){
	    if (relateTypeLike != null && relateTypeLike.trim().length() > 0) {
		if (!relateTypeLike.startsWith("%")) {
		    relateTypeLike = "%" + relateTypeLike;
		}
		if (!relateTypeLike.endsWith("%")) {
		   relateTypeLike = relateTypeLike + "%";
		}
	    }
	return relateTypeLike;
    }

    public List<String> getRelateTypes(){
	return relateTypes;
    }


    public String getTopId(){
        return topId;
    }

    public String getTopIdLike(){
	    if (topIdLike != null && topIdLike.trim().length() > 0) {
		if (!topIdLike.startsWith("%")) {
		    topIdLike = "%" + topIdLike;
		}
		if (!topIdLike.endsWith("%")) {
		   topIdLike = topIdLike + "%";
		}
	    }
	return topIdLike;
    }

    public List<String> getTopIds(){
	return topIds;
    }


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


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
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


    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
    }


    public Integer getListNo(){
        return listNo;
    }

    public Integer getListNoGreaterThanOrEqual(){
        return listNoGreaterThanOrEqual;
    }

    public Integer getListNoLessThanOrEqual(){
	return listNoLessThanOrEqual;
    }

    public List<Integer> getListNos(){
	return listNos;
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


    public void setDescription(String description){
        this.description = description;
    }

    public void setDescriptionLike( String descriptionLike){
	this.descriptionLike = descriptionLike;
    }

    public void setDescriptions(List<String> descriptions){
        this.descriptions = descriptions;
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


    public void setRelateTable(String relateTable){
        this.relateTable = relateTable;
    }

    public void setRelateTableLike( String relateTableLike){
	this.relateTableLike = relateTableLike;
    }

    public void setRelateTables(List<String> relateTables){
        this.relateTables = relateTables;
    }


    public void setRelateColumn(String relateColumn){
        this.relateColumn = relateColumn;
    }

    public void setRelateColumnLike( String relateColumnLike){
	this.relateColumnLike = relateColumnLike;
    }

    public void setRelateColumns(List<String> relateColumns){
        this.relateColumns = relateColumns;
    }


    public void setRelateType(String relateType){
        this.relateType = relateType;
    }

    public void setRelateTypeLike( String relateTypeLike){
	this.relateTypeLike = relateTypeLike;
    }

    public void setRelateTypes(List<String> relateTypes){
        this.relateTypes = relateTypes;
    }


    public void setTopId(String topId){
        this.topId = topId;
    }

    public void setTopIdLike( String topIdLike){
	this.topIdLike = topIdLike;
    }

    public void setTopIds(List<String> topIds){
        this.topIds = topIds;
    }


    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public void setParentIdLike( String parentIdLike){
	this.parentIdLike = parentIdLike;
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


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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


    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
    }


    public void setListNo(Integer listNo){
        this.listNo = listNo;
    }

    public void setListNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual){
        this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
    }

    public void setListNoLessThanOrEqual(Integer listNoLessThanOrEqual){
	this.listNoLessThanOrEqual = listNoLessThanOrEqual;
    }

    public void setListNos(List<Integer> listNos){
        this.listNos = listNos;
    }




    public DataModelRelationQuery columnName(String columnName){
	if (columnName == null) {
	    throw new RuntimeException("columnName is null");
        }         
	this.columnName = columnName;
	return this;
    }

    public DataModelRelationQuery columnNameLike( String columnNameLike){
        if (columnNameLike == null) {
            throw new RuntimeException("columnName is null");
        }
        this.columnNameLike = columnNameLike;
        return this;
    }

    public DataModelRelationQuery columnNames(List<String> columnNames){
        if (columnNames == null) {
            throw new RuntimeException("columnNames is empty ");
        }
        this.columnNames = columnNames;
        return this;
    }


    public DataModelRelationQuery description(String description){
	if (description == null) {
	    throw new RuntimeException("description is null");
        }         
	this.description = description;
	return this;
    }

    public DataModelRelationQuery descriptionLike( String descriptionLike){
        if (descriptionLike == null) {
            throw new RuntimeException("description is null");
        }
        this.descriptionLike = descriptionLike;
        return this;
    }

    public DataModelRelationQuery descriptions(List<String> descriptions){
        if (descriptions == null) {
            throw new RuntimeException("descriptions is empty ");
        }
        this.descriptions = descriptions;
        return this;
    }


    public DataModelRelationQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public DataModelRelationQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public DataModelRelationQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public DataModelRelationQuery relateTable(String relateTable){
	if (relateTable == null) {
	    throw new RuntimeException("relateTable is null");
        }         
	this.relateTable = relateTable;
	return this;
    }

    public DataModelRelationQuery relateTableLike( String relateTableLike){
        if (relateTableLike == null) {
            throw new RuntimeException("relateTable is null");
        }
        this.relateTableLike = relateTableLike;
        return this;
    }

    public DataModelRelationQuery relateTables(List<String> relateTables){
        if (relateTables == null) {
            throw new RuntimeException("relateTables is empty ");
        }
        this.relateTables = relateTables;
        return this;
    }


    public DataModelRelationQuery relateColumn(String relateColumn){
	if (relateColumn == null) {
	    throw new RuntimeException("relateColumn is null");
        }         
	this.relateColumn = relateColumn;
	return this;
    }

    public DataModelRelationQuery relateColumnLike( String relateColumnLike){
        if (relateColumnLike == null) {
            throw new RuntimeException("relateColumn is null");
        }
        this.relateColumnLike = relateColumnLike;
        return this;
    }

    public DataModelRelationQuery relateColumns(List<String> relateColumns){
        if (relateColumns == null) {
            throw new RuntimeException("relateColumns is empty ");
        }
        this.relateColumns = relateColumns;
        return this;
    }


    public DataModelRelationQuery relateType(String relateType){
	if (relateType == null) {
	    throw new RuntimeException("relateType is null");
        }         
	this.relateType = relateType;
	return this;
    }

    public DataModelRelationQuery relateTypeLike( String relateTypeLike){
        if (relateTypeLike == null) {
            throw new RuntimeException("relateType is null");
        }
        this.relateTypeLike = relateTypeLike;
        return this;
    }

    public DataModelRelationQuery relateTypes(List<String> relateTypes){
        if (relateTypes == null) {
            throw new RuntimeException("relateTypes is empty ");
        }
        this.relateTypes = relateTypes;
        return this;
    }


    public DataModelRelationQuery topId(String topId){
	if (topId == null) {
	    throw new RuntimeException("topId is null");
        }         
	this.topId = topId;
	return this;
    }

    public DataModelRelationQuery topIdLike( String topIdLike){
        if (topIdLike == null) {
            throw new RuntimeException("topId is null");
        }
        this.topIdLike = topIdLike;
        return this;
    }

    public DataModelRelationQuery topIds(List<String> topIds){
        if (topIds == null) {
            throw new RuntimeException("topIds is empty ");
        }
        this.topIds = topIds;
        return this;
    }


    public DataModelRelationQuery parentId(String parentId){
	if (parentId == null) {
	    throw new RuntimeException("parentId is null");
        }         
	this.parentId = parentId;
	return this;
    }

    public DataModelRelationQuery parentIdLike( String parentIdLike){
        if (parentIdLike == null) {
            throw new RuntimeException("parentId is null");
        }
        this.parentIdLike = parentIdLike;
        return this;
    }

    public DataModelRelationQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public DataModelRelationQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public DataModelRelationQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public DataModelRelationQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public DataModelRelationQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public DataModelRelationQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public DataModelRelationQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public DataModelRelationQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public DataModelRelationQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public DataModelRelationQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }



    public DataModelRelationQuery listNo(Integer listNo){
	if (listNo == null) {
            throw new RuntimeException("listNo is null");
        }         
	this.listNo = listNo;
	return this;
    }

    public DataModelRelationQuery listNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual){
	if (listNoGreaterThanOrEqual == null) {
	    throw new RuntimeException("listNo is null");
        }         
	this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
        return this;
    }

    public DataModelRelationQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual){
        if (listNoLessThanOrEqual == null) {
            throw new RuntimeException("listNo is null");
        }
        this.listNoLessThanOrEqual = listNoLessThanOrEqual;
        return this;
    }

    public DataModelRelationQuery listNos(List<Integer> listNos){
        if (listNos == null) {
            throw new RuntimeException("listNos is empty ");
        }
        this.listNos = listNos;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("columnName".equals(sortColumn)) {
                orderBy = "E.COLUMNNAME_" + a_x;
            } 

            if ("description".equals(sortColumn)) {
                orderBy = "E.DESCRIPTION_" + a_x;
            } 

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("relateTable".equals(sortColumn)) {
                orderBy = "E.RELATETABLE_" + a_x;
            } 

            if ("relateColumn".equals(sortColumn)) {
                orderBy = "E.RELATECOLUMN_" + a_x;
            } 

            if ("relateType".equals(sortColumn)) {
                orderBy = "E.RELATETYPE_" + a_x;
            } 

            if ("topId".equals(sortColumn)) {
                orderBy = "E.TOPID_" + a_x;
            } 

            if ("parentId".equals(sortColumn)) {
                orderBy = "E.PARENTID_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE_" + a_x;
            } 

            if ("listNo".equals(sortColumn)) {
                orderBy = "E.LISTNO_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("columnName", "COLUMNNAME_");
        addColumn("description", "DESCRIPTION_");
        addColumn("type", "TYPE_");
        addColumn("relateTable", "RELATETABLE_");
        addColumn("relateColumn", "RELATECOLUMN_");
        addColumn("relateType", "RELATETYPE_");
        addColumn("topId", "TOPID_");
        addColumn("parentId", "PARENTID_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateDate", "UPDATEDATE_");
        addColumn("listNo", "LISTNO_");
    }

}