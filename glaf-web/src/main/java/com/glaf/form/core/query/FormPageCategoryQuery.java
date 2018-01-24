package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormPageCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Integer> ids;
	protected Collection<String> appActorIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;
  	protected Integer sortNo;
  	protected Integer sortNoGreaterThanOrEqual;
  	protected Integer sortNoLessThanOrEqual;
  	protected List<Integer> sortNos;
  	protected Integer locked;
  	protected Integer lockedGreaterThanOrEqual;
  	protected Integer lockedLessThanOrEqual;
  	protected List<Integer> lockeds;
  	protected String permission;
  	protected String permissionLike;
  	protected List<String> permissions;
  	protected String ext1;
  	protected String ext1Like;
  	protected List<String> ext1s;
  	protected String ext2;
  	protected String ext2Like;
  	protected List<String> ext2s;
  	protected String ext3;
  	protected String ext3Like;
  	protected List<String> ext3s;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;

    public FormPageCategoryQuery() {

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

    public Integer getSortNo(){
        return sortNo;
    }

    public Integer getSortNoGreaterThanOrEqual(){
        return sortNoGreaterThanOrEqual;
    }

    public Integer getSortNoLessThanOrEqual(){
	return sortNoLessThanOrEqual;
    }

    public List<Integer> getSortNos(){
	return sortNos;
    }

    public Integer getLocked(){
        return locked;
    }

    public Integer getLockedGreaterThanOrEqual(){
        return lockedGreaterThanOrEqual;
    }

    public Integer getLockedLessThanOrEqual(){
	return lockedLessThanOrEqual;
    }

    public List<Integer> getLockeds(){
	return lockeds;
    }

    public String getPermission(){
        return permission;
    }

    public String getPermissionLike(){
	    if (permissionLike != null && permissionLike.trim().length() > 0) {
		if (!permissionLike.startsWith("%")) {
		    permissionLike = "%" + permissionLike;
		}
		if (!permissionLike.endsWith("%")) {
		   permissionLike = permissionLike + "%";
		}
	    }
	return permissionLike;
    }

    public List<String> getPermissions(){
	return permissions;
    }


    public String getExt1(){
        return ext1;
    }

    public String getExt1Like(){
	    if (ext1Like != null && ext1Like.trim().length() > 0) {
		if (!ext1Like.startsWith("%")) {
		    ext1Like = "%" + ext1Like;
		}
		if (!ext1Like.endsWith("%")) {
		   ext1Like = ext1Like + "%";
		}
	    }
	return ext1Like;
    }

    public List<String> getExt1s(){
	return ext1s;
    }


    public String getExt2(){
        return ext2;
    }

    public String getExt2Like(){
	    if (ext2Like != null && ext2Like.trim().length() > 0) {
		if (!ext2Like.startsWith("%")) {
		    ext2Like = "%" + ext2Like;
		}
		if (!ext2Like.endsWith("%")) {
		   ext2Like = ext2Like + "%";
		}
	    }
	return ext2Like;
    }

    public List<String> getExt2s(){
	return ext2s;
    }


    public String getExt3(){
        return ext3;
    }

    public String getExt3Like(){
	    if (ext3Like != null && ext3Like.trim().length() > 0) {
		if (!ext3Like.startsWith("%")) {
		    ext3Like = "%" + ext3Like;
		}
		if (!ext3Like.endsWith("%")) {
		   ext3Like = ext3Like + "%";
		}
	    }
	return ext3Like;
    }

    public List<String> getExt3s(){
	return ext3s;
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


 

    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
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


    public void setSortNo(Integer sortNo){
        this.sortNo = sortNo;
    }

    public void setSortNoGreaterThanOrEqual(Integer sortNoGreaterThanOrEqual){
        this.sortNoGreaterThanOrEqual = sortNoGreaterThanOrEqual;
    }

    public void setSortNoLessThanOrEqual(Integer sortNoLessThanOrEqual){
	this.sortNoLessThanOrEqual = sortNoLessThanOrEqual;
    }

    public void setSortNos(List<Integer> sortNos){
        this.sortNos = sortNos;
    }


    public void setLocked(Integer locked){
        this.locked = locked;
    }

    public void setLockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual){
        this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
    }

    public void setLockedLessThanOrEqual(Integer lockedLessThanOrEqual){
	this.lockedLessThanOrEqual = lockedLessThanOrEqual;
    }

    public void setLockeds(List<Integer> lockeds){
        this.lockeds = lockeds;
    }


    public void setPermission(String permission){
        this.permission = permission;
    }

    public void setPermissionLike( String permissionLike){
	this.permissionLike = permissionLike;
    }

    public void setPermissions(List<String> permissions){
        this.permissions = permissions;
    }


    public void setExt1(String ext1){
        this.ext1 = ext1;
    }

    public void setExt1Like( String ext1Like){
	this.ext1Like = ext1Like;
    }

    public void setExt1s(List<String> ext1s){
        this.ext1s = ext1s;
    }


    public void setExt2(String ext2){
        this.ext2 = ext2;
    }

    public void setExt2Like( String ext2Like){
	this.ext2Like = ext2Like;
    }

    public void setExt2s(List<String> ext2s){
        this.ext2s = ext2s;
    }


    public void setExt3(String ext3){
        this.ext3 = ext3;
    }

    public void setExt3Like( String ext3Like){
	this.ext3Like = ext3Like;
    }

    public void setExt3s(List<String> ext3s){
        this.ext3s = ext3s;
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




    public FormPageCategoryQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormPageCategoryQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormPageCategoryQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormPageCategoryQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public FormPageCategoryQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }


    public FormPageCategoryQuery sortNo(Integer sortNo){
	if (sortNo == null) {
            throw new RuntimeException("sortNo is null");
        }         
	this.sortNo = sortNo;
	return this;
    }

    public FormPageCategoryQuery sortNoGreaterThanOrEqual(Integer sortNoGreaterThanOrEqual){
	if (sortNoGreaterThanOrEqual == null) {
	    throw new RuntimeException("sortNo is null");
        }         
	this.sortNoGreaterThanOrEqual = sortNoGreaterThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery sortNoLessThanOrEqual(Integer sortNoLessThanOrEqual){
        if (sortNoLessThanOrEqual == null) {
            throw new RuntimeException("sortNo is null");
        }
        this.sortNoLessThanOrEqual = sortNoLessThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery sortNos(List<Integer> sortNos){
        if (sortNos == null) {
            throw new RuntimeException("sortNos is empty ");
        }
        this.sortNos = sortNos;
        return this;
    }


    public FormPageCategoryQuery locked(Integer locked){
	if (locked == null) {
            throw new RuntimeException("locked is null");
        }         
	this.locked = locked;
	return this;
    }

    public FormPageCategoryQuery lockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual){
	if (lockedGreaterThanOrEqual == null) {
	    throw new RuntimeException("locked is null");
        }         
	this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery lockedLessThanOrEqual(Integer lockedLessThanOrEqual){
        if (lockedLessThanOrEqual == null) {
            throw new RuntimeException("locked is null");
        }
        this.lockedLessThanOrEqual = lockedLessThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery lockeds(List<Integer> lockeds){
        if (lockeds == null) {
            throw new RuntimeException("lockeds is empty ");
        }
        this.lockeds = lockeds;
        return this;
    }


    public FormPageCategoryQuery permission(String permission){
	if (permission == null) {
	    throw new RuntimeException("permission is null");
        }         
	this.permission = permission;
	return this;
    }

    public FormPageCategoryQuery permissionLike( String permissionLike){
        if (permissionLike == null) {
            throw new RuntimeException("permission is null");
        }
        this.permissionLike = permissionLike;
        return this;
    }

    public FormPageCategoryQuery permissions(List<String> permissions){
        if (permissions == null) {
            throw new RuntimeException("permissions is empty ");
        }
        this.permissions = permissions;
        return this;
    }


    public FormPageCategoryQuery ext1(String ext1){
	if (ext1 == null) {
	    throw new RuntimeException("ext1 is null");
        }         
	this.ext1 = ext1;
	return this;
    }

    public FormPageCategoryQuery ext1Like( String ext1Like){
        if (ext1Like == null) {
            throw new RuntimeException("ext1 is null");
        }
        this.ext1Like = ext1Like;
        return this;
    }

    public FormPageCategoryQuery ext1s(List<String> ext1s){
        if (ext1s == null) {
            throw new RuntimeException("ext1s is empty ");
        }
        this.ext1s = ext1s;
        return this;
    }


    public FormPageCategoryQuery ext2(String ext2){
	if (ext2 == null) {
	    throw new RuntimeException("ext2 is null");
        }         
	this.ext2 = ext2;
	return this;
    }

    public FormPageCategoryQuery ext2Like( String ext2Like){
        if (ext2Like == null) {
            throw new RuntimeException("ext2 is null");
        }
        this.ext2Like = ext2Like;
        return this;
    }

    public FormPageCategoryQuery ext2s(List<String> ext2s){
        if (ext2s == null) {
            throw new RuntimeException("ext2s is empty ");
        }
        this.ext2s = ext2s;
        return this;
    }


    public FormPageCategoryQuery ext3(String ext3){
	if (ext3 == null) {
	    throw new RuntimeException("ext3 is null");
        }         
	this.ext3 = ext3;
	return this;
    }

    public FormPageCategoryQuery ext3Like( String ext3Like){
        if (ext3Like == null) {
            throw new RuntimeException("ext3 is null");
        }
        this.ext3Like = ext3Like;
        return this;
    }

    public FormPageCategoryQuery ext3s(List<String> ext3s){
        if (ext3s == null) {
            throw new RuntimeException("ext3s is empty ");
        }
        this.ext3s = ext3s;
        return this;
    }


    public FormPageCategoryQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormPageCategoryQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormPageCategoryQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public FormPageCategoryQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormPageCategoryQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETEFLAG_" + a_x;
            } 

            if ("sortNo".equals(sortColumn)) {
                orderBy = "E.SORTNO_" + a_x;
            } 

            if ("locked".equals(sortColumn)) {
                orderBy = "E.LOCKED_" + a_x;
            } 

            if ("permission".equals(sortColumn)) {
                orderBy = "E.PERMISSION_" + a_x;
            } 

            if ("ext1".equals(sortColumn)) {
                orderBy = "E.EXT1_" + a_x;
            } 

            if ("ext2".equals(sortColumn)) {
                orderBy = "E.EXT2_" + a_x;
            } 

            if ("ext3".equals(sortColumn)) {
                orderBy = "E.EXT3_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATA_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("name", "NAME_");
        addColumn("deleteFlag", "DELETEFLAG_");
        addColumn("sortNo", "SORTNO_");
        addColumn("locked", "LOCKED_");
        addColumn("permission", "PERMISSION_");
        addColumn("ext1", "EXT1_");
        addColumn("ext2", "EXT2_");
        addColumn("ext3", "EXT3_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createDate", "CREATEDATA_");
    }

}