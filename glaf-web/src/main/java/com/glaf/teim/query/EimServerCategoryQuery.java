package com.glaf.teim.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EimServerCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String treeId;
  	protected String treeIdLike;
  	protected List<String> treeIds;
  	protected Long parentId;
  	protected Long parentIdGreaterThanOrEqual;
  	protected Long parentIdLessThanOrEqual;
  	protected List<Long> parentIds;
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

    public EimServerCategoryQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getCode(){
        return code;
    }

    public String getCodeLike(){
	    if (codeLike != null && codeLike.trim().length() > 0) {
		if (!codeLike.startsWith("%")) {
		    codeLike = "%" + codeLike;
		}
		if (!codeLike.endsWith("%")) {
		   codeLike = codeLike + "%";
		}
	    }
	return codeLike;
    }

    public List<String> getCodes(){
	return codes;
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


    public String getTreeId(){
        return treeId;
    }

    public String getTreeIdLike(){
	    if (treeIdLike != null && treeIdLike.trim().length() > 0) {
		if (!treeIdLike.startsWith("%")) {
		    treeIdLike = "%" + treeIdLike;
		}
		if (!treeIdLike.endsWith("%")) {
		   treeIdLike = treeIdLike + "%";
		}
	    }
	return treeIdLike;
    }

    public List<String> getTreeIds(){
	return treeIds;
    }


    public Long getParentId(){
        return parentId;
    }

    public Long getParentIdGreaterThanOrEqual(){
        return parentIdGreaterThanOrEqual;
    }

    public Long getParentIdLessThanOrEqual(){
	return parentIdLessThanOrEqual;
    }

    public List<Long> getParentIds(){
	return parentIds;
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


 

    public void setCode(String code){
        this.code = code;
    }

    public void setCodeLike( String codeLike){
	this.codeLike = codeLike;
    }

    public void setCodes(List<String> codes){
        this.codes = codes;
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


    public void setTreeId(String treeId){
        this.treeId = treeId;
    }

    public void setTreeIdLike( String treeIdLike){
	this.treeIdLike = treeIdLike;
    }

    public void setTreeIds(List<String> treeIds){
        this.treeIds = treeIds;
    }


    public void setParentId(Long parentId){
        this.parentId = parentId;
    }

    public void setParentIdGreaterThanOrEqual(Long parentIdGreaterThanOrEqual){
        this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
    }

    public void setParentIdLessThanOrEqual(Long parentIdLessThanOrEqual){
	this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
    }

    public void setParentIds(List<Long> parentIds){
        this.parentIds = parentIds;
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




    public EimServerCategoryQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public EimServerCategoryQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public EimServerCategoryQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public EimServerCategoryQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public EimServerCategoryQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public EimServerCategoryQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public EimServerCategoryQuery treeId(String treeId){
	if (treeId == null) {
	    throw new RuntimeException("treeId is null");
        }         
	this.treeId = treeId;
	return this;
    }

    public EimServerCategoryQuery treeIdLike( String treeIdLike){
        if (treeIdLike == null) {
            throw new RuntimeException("treeId is null");
        }
        this.treeIdLike = treeIdLike;
        return this;
    }

    public EimServerCategoryQuery treeIds(List<String> treeIds){
        if (treeIds == null) {
            throw new RuntimeException("treeIds is empty ");
        }
        this.treeIds = treeIds;
        return this;
    }


    public EimServerCategoryQuery parentId(Long parentId){
	if (parentId == null) {
            throw new RuntimeException("parentId is null");
        }         
	this.parentId = parentId;
	return this;
    }

    public EimServerCategoryQuery parentIdGreaterThanOrEqual(Long parentIdGreaterThanOrEqual){
	if (parentIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("parentId is null");
        }         
	this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
        return this;
    }

    public EimServerCategoryQuery parentIdLessThanOrEqual(Long parentIdLessThanOrEqual){
        if (parentIdLessThanOrEqual == null) {
            throw new RuntimeException("parentId is null");
        }
        this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
        return this;
    }

    public EimServerCategoryQuery parentIds(List<Long> parentIds){
        if (parentIds == null) {
            throw new RuntimeException("parentIds is empty ");
        }
        this.parentIds = parentIds;
        return this;
    }


    public EimServerCategoryQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public EimServerCategoryQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public EimServerCategoryQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public EimServerCategoryQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public EimServerCategoryQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public EimServerCategoryQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public EimServerCategoryQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public EimServerCategoryQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public EimServerCategoryQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public EimServerCategoryQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("treeId".equals(sortColumn)) {
                orderBy = "E.TREE_ID_" + a_x;
            } 

            if ("parentId".equals(sortColumn)) {
                orderBy = "E.PARENT_ID_" + a_x;
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

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("treeId", "TREE_ID_");
        addColumn("parentId", "PARENT_ID_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
    }

}