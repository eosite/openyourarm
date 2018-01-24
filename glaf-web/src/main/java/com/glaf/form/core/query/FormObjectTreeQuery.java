package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormObjectTreeQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String desc;
  	protected String descLike;
  	protected List<String> descs;
  	protected String topId;
  	protected String topIdLike;
  	protected List<String> topIds;
  	protected String treeId;
  	protected String treeIdLike;
  	protected List<String> treeIds;
  	protected Integer level;
  	protected Integer levelGreaterThanOrEqual;
  	protected Integer levelLessThanOrEqual;
  	protected List<Integer> levels;
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

    public FormObjectTreeQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getDesc(){
        return desc;
    }

    public String getDescLike(){
	    if (descLike != null && descLike.trim().length() > 0) {
		if (!descLike.startsWith("%")) {
		    descLike = "%" + descLike;
		}
		if (!descLike.endsWith("%")) {
		   descLike = descLike + "%";
		}
	    }
	return descLike;
    }

    public List<String> getDescs(){
	return descs;
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


    public Integer getLevel(){
        return level;
    }

    public Integer getLevelGreaterThanOrEqual(){
        return levelGreaterThanOrEqual;
    }

    public Integer getLevelLessThanOrEqual(){
	return levelLessThanOrEqual;
    }

    public List<Integer> getLevels(){
	return levels;
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


 

    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
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


    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setDescLike( String descLike){
	this.descLike = descLike;
    }

    public void setDescs(List<String> descs){
        this.descs = descs;
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


    public void setTreeId(String treeId){
        this.treeId = treeId;
    }

    public void setTreeIdLike( String treeIdLike){
	this.treeIdLike = treeIdLike;
    }

    public void setTreeIds(List<String> treeIds){
        this.treeIds = treeIds;
    }


    public void setLevel(Integer level){
        this.level = level;
    }

    public void setLevelGreaterThanOrEqual(Integer levelGreaterThanOrEqual){
        this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
    }

    public void setLevelLessThanOrEqual(Integer levelLessThanOrEqual){
	this.levelLessThanOrEqual = levelLessThanOrEqual;
    }

    public void setLevels(List<Integer> levels){
        this.levels = levels;
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




    public FormObjectTreeQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormObjectTreeQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormObjectTreeQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormObjectTreeQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public FormObjectTreeQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public FormObjectTreeQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public FormObjectTreeQuery desc(String desc){
	if (desc == null) {
	    throw new RuntimeException("desc is null");
        }         
	this.desc = desc;
	return this;
    }

    public FormObjectTreeQuery descLike( String descLike){
        if (descLike == null) {
            throw new RuntimeException("desc is null");
        }
        this.descLike = descLike;
        return this;
    }

    public FormObjectTreeQuery descs(List<String> descs){
        if (descs == null) {
            throw new RuntimeException("descs is empty ");
        }
        this.descs = descs;
        return this;
    }


    public FormObjectTreeQuery topId(String topId){
	if (topId == null) {
	    throw new RuntimeException("topId is null");
        }         
	this.topId = topId;
	return this;
    }

    public FormObjectTreeQuery topIdLike( String topIdLike){
        if (topIdLike == null) {
            throw new RuntimeException("topId is null");
        }
        this.topIdLike = topIdLike;
        return this;
    }

    public FormObjectTreeQuery topIds(List<String> topIds){
        if (topIds == null) {
            throw new RuntimeException("topIds is empty ");
        }
        this.topIds = topIds;
        return this;
    }


    public FormObjectTreeQuery treeId(String treeId){
	if (treeId == null) {
	    throw new RuntimeException("treeId is null");
        }         
	this.treeId = treeId;
	return this;
    }

    public FormObjectTreeQuery treeIdLike( String treeIdLike){
        if (treeIdLike == null) {
            throw new RuntimeException("treeId is null");
        }
        this.treeIdLike = treeIdLike;
        return this;
    }

    public FormObjectTreeQuery treeIds(List<String> treeIds){
        if (treeIds == null) {
            throw new RuntimeException("treeIds is empty ");
        }
        this.treeIds = treeIds;
        return this;
    }


    public FormObjectTreeQuery level(Integer level){
	if (level == null) {
            throw new RuntimeException("level is null");
        }         
	this.level = level;
	return this;
    }

    public FormObjectTreeQuery levelGreaterThanOrEqual(Integer levelGreaterThanOrEqual){
	if (levelGreaterThanOrEqual == null) {
	    throw new RuntimeException("level is null");
        }         
	this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
        return this;
    }

    public FormObjectTreeQuery levelLessThanOrEqual(Integer levelLessThanOrEqual){
        if (levelLessThanOrEqual == null) {
            throw new RuntimeException("level is null");
        }
        this.levelLessThanOrEqual = levelLessThanOrEqual;
        return this;
    }

    public FormObjectTreeQuery levels(List<Integer> levels){
        if (levels == null) {
            throw new RuntimeException("levels is empty ");
        }
        this.levels = levels;
        return this;
    }


    public FormObjectTreeQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormObjectTreeQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormObjectTreeQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public FormObjectTreeQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormObjectTreeQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public FormObjectTreeQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public FormObjectTreeQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public FormObjectTreeQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public FormObjectTreeQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public FormObjectTreeQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("desc".equals(sortColumn)) {
                orderBy = "E.DESC_" + a_x;
            } 

            if ("topId".equals(sortColumn)) {
                orderBy = "E.TOP_ID_" + a_x;
            } 

            if ("treeId".equals(sortColumn)) {
                orderBy = "E.TREE_ID_" + a_x;
            } 

            if ("level".equals(sortColumn)) {
                orderBy = "E.LEVEL_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATE_BY_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATE_DATE_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATE_BY_" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATE_DATE_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("name", "NAME_");
        addColumn("code", "CODE_");
        addColumn("desc", "DESC_");
        addColumn("topId", "TOP_ID_");
        addColumn("treeId", "TREE_ID_");
        addColumn("level", "LEVEL_");
        addColumn("createBy", "CREATE_BY_");
        addColumn("createDate", "CREATE_DATE_");
        addColumn("updateBy", "UPDATE_BY_");
        addColumn("updateDate", "UPDATE_DATE_");
    }

}