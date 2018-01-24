package com.glaf.theme.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysThemeTmpLayoutAreaQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> areaIds;
	protected Collection<String> appActorIds;
  	protected String layoutId;
  	protected String layoutIdLike;
  	protected List<String> layoutIds;
  	protected String areaName;
  	protected String areaNameLike;
  	protected List<String> areaNames;
  	protected String areaCode;
  	protected String areaCodeLike;
  	protected List<String> areaCodes;
  	protected String compType;
  	protected String compTypeLike;
  	protected List<String> compTypes;
  	protected String elemCode;
  	protected String elemCodeLike;
  	protected List<String> elemCodes;
  	protected String selectorExp;
  	protected String selectorExpLike;
  	protected List<String> selectorExps;
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
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;

    public SysThemeTmpLayoutAreaQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getLayoutId(){
        return layoutId;
    }

    public String getLayoutIdLike(){
	    if (layoutIdLike != null && layoutIdLike.trim().length() > 0) {
		if (!layoutIdLike.startsWith("%")) {
		    layoutIdLike = "%" + layoutIdLike;
		}
		if (!layoutIdLike.endsWith("%")) {
		   layoutIdLike = layoutIdLike + "%";
		}
	    }
	return layoutIdLike;
    }

    public List<String> getLayoutIds(){
	return layoutIds;
    }


    public String getAreaName(){
        return areaName;
    }

    public String getAreaNameLike(){
	    if (areaNameLike != null && areaNameLike.trim().length() > 0) {
		if (!areaNameLike.startsWith("%")) {
		    areaNameLike = "%" + areaNameLike;
		}
		if (!areaNameLike.endsWith("%")) {
		   areaNameLike = areaNameLike + "%";
		}
	    }
	return areaNameLike;
    }

    public List<String> getAreaNames(){
	return areaNames;
    }


    public String getAreaCode(){
        return areaCode;
    }

    public String getAreaCodeLike(){
	    if (areaCodeLike != null && areaCodeLike.trim().length() > 0) {
		if (!areaCodeLike.startsWith("%")) {
		    areaCodeLike = "%" + areaCodeLike;
		}
		if (!areaCodeLike.endsWith("%")) {
		   areaCodeLike = areaCodeLike + "%";
		}
	    }
	return areaCodeLike;
    }

    public List<String> getAreaCodes(){
	return areaCodes;
    }


    public String getCompType(){
        return compType;
    }

    public String getCompTypeLike(){
	    if (compTypeLike != null && compTypeLike.trim().length() > 0) {
		if (!compTypeLike.startsWith("%")) {
		    compTypeLike = "%" + compTypeLike;
		}
		if (!compTypeLike.endsWith("%")) {
		   compTypeLike = compTypeLike + "%";
		}
	    }
	return compTypeLike;
    }

    public List<String> getCompTypes(){
	return compTypes;
    }


    public String getElemCode(){
        return elemCode;
    }

    public String getElemCodeLike(){
	    if (elemCodeLike != null && elemCodeLike.trim().length() > 0) {
		if (!elemCodeLike.startsWith("%")) {
		    elemCodeLike = "%" + elemCodeLike;
		}
		if (!elemCodeLike.endsWith("%")) {
		   elemCodeLike = elemCodeLike + "%";
		}
	    }
	return elemCodeLike;
    }

    public List<String> getElemCodes(){
	return elemCodes;
    }


    public String getSelectorExp(){
        return selectorExp;
    }

    public String getSelectorExpLike(){
	    if (selectorExpLike != null && selectorExpLike.trim().length() > 0) {
		if (!selectorExpLike.startsWith("%")) {
		    selectorExpLike = "%" + selectorExpLike;
		}
		if (!selectorExpLike.endsWith("%")) {
		   selectorExpLike = selectorExpLike + "%";
		}
	    }
	return selectorExpLike;
    }

    public List<String> getSelectorExps(){
	return selectorExps;
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

 

    public void setLayoutId(String layoutId){
        this.layoutId = layoutId;
    }

    public void setLayoutIdLike( String layoutIdLike){
	this.layoutIdLike = layoutIdLike;
    }

    public void setLayoutIds(List<String> layoutIds){
        this.layoutIds = layoutIds;
    }


    public void setAreaName(String areaName){
        this.areaName = areaName;
    }

    public void setAreaNameLike( String areaNameLike){
	this.areaNameLike = areaNameLike;
    }

    public void setAreaNames(List<String> areaNames){
        this.areaNames = areaNames;
    }


    public void setAreaCode(String areaCode){
        this.areaCode = areaCode;
    }

    public void setAreaCodeLike( String areaCodeLike){
	this.areaCodeLike = areaCodeLike;
    }

    public void setAreaCodes(List<String> areaCodes){
        this.areaCodes = areaCodes;
    }


    public void setCompType(String compType){
        this.compType = compType;
    }

    public void setCompTypeLike( String compTypeLike){
	this.compTypeLike = compTypeLike;
    }

    public void setCompTypes(List<String> compTypes){
        this.compTypes = compTypes;
    }


    public void setElemCode(String elemCode){
        this.elemCode = elemCode;
    }

    public void setElemCodeLike( String elemCodeLike){
	this.elemCodeLike = elemCodeLike;
    }

    public void setElemCodes(List<String> elemCodes){
        this.elemCodes = elemCodes;
    }


    public void setSelectorExp(String selectorExp){
        this.selectorExp = selectorExp;
    }

    public void setSelectorExpLike( String selectorExpLike){
	this.selectorExpLike = selectorExpLike;
    }

    public void setSelectorExps(List<String> selectorExps){
        this.selectorExps = selectorExps;
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




    public SysThemeTmpLayoutAreaQuery layoutId(String layoutId){
	if (layoutId == null) {
	    throw new RuntimeException("layoutId is null");
        }         
	this.layoutId = layoutId;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery layoutIdLike( String layoutIdLike){
        if (layoutIdLike == null) {
            throw new RuntimeException("layoutId is null");
        }
        this.layoutIdLike = layoutIdLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery layoutIds(List<String> layoutIds){
        if (layoutIds == null) {
            throw new RuntimeException("layoutIds is empty ");
        }
        this.layoutIds = layoutIds;
        return this;
    }


    public SysThemeTmpLayoutAreaQuery areaName(String areaName){
	if (areaName == null) {
	    throw new RuntimeException("areaName is null");
        }         
	this.areaName = areaName;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery areaNameLike( String areaNameLike){
        if (areaNameLike == null) {
            throw new RuntimeException("areaName is null");
        }
        this.areaNameLike = areaNameLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery areaNames(List<String> areaNames){
        if (areaNames == null) {
            throw new RuntimeException("areaNames is empty ");
        }
        this.areaNames = areaNames;
        return this;
    }


    public SysThemeTmpLayoutAreaQuery areaCode(String areaCode){
	if (areaCode == null) {
	    throw new RuntimeException("areaCode is null");
        }         
	this.areaCode = areaCode;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery areaCodeLike( String areaCodeLike){
        if (areaCodeLike == null) {
            throw new RuntimeException("areaCode is null");
        }
        this.areaCodeLike = areaCodeLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery areaCodes(List<String> areaCodes){
        if (areaCodes == null) {
            throw new RuntimeException("areaCodes is empty ");
        }
        this.areaCodes = areaCodes;
        return this;
    }


    public SysThemeTmpLayoutAreaQuery compType(String compType){
	if (compType == null) {
	    throw new RuntimeException("compType is null");
        }         
	this.compType = compType;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery compTypeLike( String compTypeLike){
        if (compTypeLike == null) {
            throw new RuntimeException("compType is null");
        }
        this.compTypeLike = compTypeLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery compTypes(List<String> compTypes){
        if (compTypes == null) {
            throw new RuntimeException("compTypes is empty ");
        }
        this.compTypes = compTypes;
        return this;
    }


    public SysThemeTmpLayoutAreaQuery elemCode(String elemCode){
	if (elemCode == null) {
	    throw new RuntimeException("elemCode is null");
        }         
	this.elemCode = elemCode;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery elemCodeLike( String elemCodeLike){
        if (elemCodeLike == null) {
            throw new RuntimeException("elemCode is null");
        }
        this.elemCodeLike = elemCodeLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery elemCodes(List<String> elemCodes){
        if (elemCodes == null) {
            throw new RuntimeException("elemCodes is empty ");
        }
        this.elemCodes = elemCodes;
        return this;
    }


    public SysThemeTmpLayoutAreaQuery selectorExp(String selectorExp){
	if (selectorExp == null) {
	    throw new RuntimeException("selectorExp is null");
        }         
	this.selectorExp = selectorExp;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery selectorExpLike( String selectorExpLike){
        if (selectorExpLike == null) {
            throw new RuntimeException("selectorExp is null");
        }
        this.selectorExpLike = selectorExpLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery selectorExps(List<String> selectorExps){
        if (selectorExps == null) {
            throw new RuntimeException("selectorExps is empty ");
        }
        this.selectorExps = selectorExps;
        return this;
    }


    public SysThemeTmpLayoutAreaQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SysThemeTmpLayoutAreaQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpLayoutAreaQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SysThemeTmpLayoutAreaQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpLayoutAreaQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SysThemeTmpLayoutAreaQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("layoutId".equals(sortColumn)) {
                orderBy = "E.LAYOUT_ID_" + a_x;
            } 

            if ("areaName".equals(sortColumn)) {
                orderBy = "E.AREA_NAME_" + a_x;
            } 

            if ("areaCode".equals(sortColumn)) {
                orderBy = "E.AREA_CODE_" + a_x;
            } 

            if ("compType".equals(sortColumn)) {
                orderBy = "E.COMP_TYPE_" + a_x;
            } 

            if ("elemCode".equals(sortColumn)) {
                orderBy = "E.ELEM_CODE_" + a_x;
            } 

            if ("selectorExp".equals(sortColumn)) {
                orderBy = "E.SELECTOR_EXP_" + a_x;
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

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETE_FLAG_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("areaId", "AREA_ID_");
        addColumn("layoutId", "LAYOUT_ID_");
        addColumn("areaName", "AREA_NAME_");
        addColumn("areaCode", "AREA_CODE_");
        addColumn("compType", "COMP_TYPE_");
        addColumn("elemCode", "ELEM_CODE_");
        addColumn("selectorExp", "SELECTOR_EXP_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}