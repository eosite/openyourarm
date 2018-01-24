package com.glaf.theme.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysThemeTmpControlQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> controlIds;
	protected Collection<String> appActorIds;
  	protected String themeTmpId;
  	protected String themeTmpIdLike;
  	protected List<String> themeTmpIds;
  	protected Integer commonFlag;
  	protected Integer commonFlagGreaterThanOrEqual;
  	protected Integer commonFlagLessThanOrEqual;
  	protected List<Integer> commonFlags;
  	protected String controlName;
  	protected String controlNameLike;
  	protected List<String> controlNames;
  	protected String controlCode;
  	protected String controlCodeLike;
  	protected List<String> controlCodes;
  	protected String compType;
  	protected String compTypeLike;
  	protected List<String> compTypes;
  	protected String elemCode;
  	protected String elemCodeLike;
  	protected List<String> elemCodes;
  	protected Integer compositionFlag;
  	protected Integer compositionFlagGreaterThanOrEqual;
  	protected Integer compositionFlagLessThanOrEqual;
  	protected List<Integer> compositionFlags;
  	protected Integer containerFlag;
  	protected Integer containerFlagGreaterThanOrEqual;
  	protected Integer containerFlagLessThanOrEqual;
  	protected List<Integer> containerFlags;
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
  	protected Integer defaultFlag;

  	
    public Integer getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public SysThemeTmpControlQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getThemeTmpId(){
        return themeTmpId;
    }

    public String getThemeTmpIdLike(){
	    if (themeTmpIdLike != null && themeTmpIdLike.trim().length() > 0) {
		if (!themeTmpIdLike.startsWith("%")) {
		    themeTmpIdLike = "%" + themeTmpIdLike;
		}
		if (!themeTmpIdLike.endsWith("%")) {
		   themeTmpIdLike = themeTmpIdLike + "%";
		}
	    }
	return themeTmpIdLike;
    }

    public List<String> getThemeTmpIds(){
	return themeTmpIds;
    }


    public Integer getCommonFlag(){
        return commonFlag;
    }

    public Integer getCommonFlagGreaterThanOrEqual(){
        return commonFlagGreaterThanOrEqual;
    }

    public Integer getCommonFlagLessThanOrEqual(){
	return commonFlagLessThanOrEqual;
    }

    public List<Integer> getCommonFlags(){
	return commonFlags;
    }

    public String getControlName(){
        return controlName;
    }

    public String getControlNameLike(){
	    if (controlNameLike != null && controlNameLike.trim().length() > 0) {
		if (!controlNameLike.startsWith("%")) {
		    controlNameLike = "%" + controlNameLike;
		}
		if (!controlNameLike.endsWith("%")) {
		   controlNameLike = controlNameLike + "%";
		}
	    }
	return controlNameLike;
    }

    public List<String> getControlNames(){
	return controlNames;
    }


    public String getControlCode(){
        return controlCode;
    }

    public String getControlCodeLike(){
	    if (controlCodeLike != null && controlCodeLike.trim().length() > 0) {
		if (!controlCodeLike.startsWith("%")) {
		    controlCodeLike = "%" + controlCodeLike;
		}
		if (!controlCodeLike.endsWith("%")) {
		   controlCodeLike = controlCodeLike + "%";
		}
	    }
	return controlCodeLike;
    }

    public List<String> getControlCodes(){
	return controlCodes;
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


    public Integer getCompositionFlag(){
        return compositionFlag;
    }

    public Integer getCompositionFlagGreaterThanOrEqual(){
        return compositionFlagGreaterThanOrEqual;
    }

    public Integer getCompositionFlagLessThanOrEqual(){
	return compositionFlagLessThanOrEqual;
    }

    public List<Integer> getCompositionFlags(){
	return compositionFlags;
    }

    public Integer getContainerFlag(){
        return containerFlag;
    }

    public Integer getContainerFlagGreaterThanOrEqual(){
        return containerFlagGreaterThanOrEqual;
    }

    public Integer getContainerFlagLessThanOrEqual(){
	return containerFlagLessThanOrEqual;
    }

    public List<Integer> getContainerFlags(){
	return containerFlags;
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

 

    public void setThemeTmpId(String themeTmpId){
        this.themeTmpId = themeTmpId;
    }

    public void setThemeTmpIdLike( String themeTmpIdLike){
	this.themeTmpIdLike = themeTmpIdLike;
    }

    public void setThemeTmpIds(List<String> themeTmpIds){
        this.themeTmpIds = themeTmpIds;
    }


    public void setCommonFlag(Integer commonFlag){
        this.commonFlag = commonFlag;
    }

    public void setCommonFlagGreaterThanOrEqual(Integer commonFlagGreaterThanOrEqual){
        this.commonFlagGreaterThanOrEqual = commonFlagGreaterThanOrEqual;
    }

    public void setCommonFlagLessThanOrEqual(Integer commonFlagLessThanOrEqual){
	this.commonFlagLessThanOrEqual = commonFlagLessThanOrEqual;
    }

    public void setCommonFlags(List<Integer> commonFlags){
        this.commonFlags = commonFlags;
    }


    public void setControlName(String controlName){
        this.controlName = controlName;
    }

    public void setControlNameLike( String controlNameLike){
	this.controlNameLike = controlNameLike;
    }

    public void setControlNames(List<String> controlNames){
        this.controlNames = controlNames;
    }


    public void setControlCode(String controlCode){
        this.controlCode = controlCode;
    }

    public void setControlCodeLike( String controlCodeLike){
	this.controlCodeLike = controlCodeLike;
    }

    public void setControlCodes(List<String> controlCodes){
        this.controlCodes = controlCodes;
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


    public void setCompositionFlag(Integer compositionFlag){
        this.compositionFlag = compositionFlag;
    }

    public void setCompositionFlagGreaterThanOrEqual(Integer compositionFlagGreaterThanOrEqual){
        this.compositionFlagGreaterThanOrEqual = compositionFlagGreaterThanOrEqual;
    }

    public void setCompositionFlagLessThanOrEqual(Integer compositionFlagLessThanOrEqual){
	this.compositionFlagLessThanOrEqual = compositionFlagLessThanOrEqual;
    }

    public void setCompositionFlags(List<Integer> compositionFlags){
        this.compositionFlags = compositionFlags;
    }


    public void setContainerFlag(Integer containerFlag){
        this.containerFlag = containerFlag;
    }

    public void setContainerFlagGreaterThanOrEqual(Integer containerFlagGreaterThanOrEqual){
        this.containerFlagGreaterThanOrEqual = containerFlagGreaterThanOrEqual;
    }

    public void setContainerFlagLessThanOrEqual(Integer containerFlagLessThanOrEqual){
	this.containerFlagLessThanOrEqual = containerFlagLessThanOrEqual;
    }

    public void setContainerFlags(List<Integer> containerFlags){
        this.containerFlags = containerFlags;
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




    public SysThemeTmpControlQuery themeTmpId(String themeTmpId){
	if (themeTmpId == null) {
	    throw new RuntimeException("themeTmpId is null");
        }         
	this.themeTmpId = themeTmpId;
	return this;
    }

    public SysThemeTmpControlQuery themeTmpIdLike( String themeTmpIdLike){
        if (themeTmpIdLike == null) {
            throw new RuntimeException("themeTmpId is null");
        }
        this.themeTmpIdLike = themeTmpIdLike;
        return this;
    }

    public SysThemeTmpControlQuery themeTmpIds(List<String> themeTmpIds){
        if (themeTmpIds == null) {
            throw new RuntimeException("themeTmpIds is empty ");
        }
        this.themeTmpIds = themeTmpIds;
        return this;
    }


    public SysThemeTmpControlQuery commonFlag(Integer commonFlag){
	if (commonFlag == null) {
            throw new RuntimeException("commonFlag is null");
        }         
	this.commonFlag = commonFlag;
	return this;
    }

    public SysThemeTmpControlQuery commonFlagGreaterThanOrEqual(Integer commonFlagGreaterThanOrEqual){
	if (commonFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("commonFlag is null");
        }         
	this.commonFlagGreaterThanOrEqual = commonFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery commonFlagLessThanOrEqual(Integer commonFlagLessThanOrEqual){
        if (commonFlagLessThanOrEqual == null) {
            throw new RuntimeException("commonFlag is null");
        }
        this.commonFlagLessThanOrEqual = commonFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery commonFlags(List<Integer> commonFlags){
        if (commonFlags == null) {
            throw new RuntimeException("commonFlags is empty ");
        }
        this.commonFlags = commonFlags;
        return this;
    }


    public SysThemeTmpControlQuery controlName(String controlName){
	if (controlName == null) {
	    throw new RuntimeException("controlName is null");
        }         
	this.controlName = controlName;
	return this;
    }

    public SysThemeTmpControlQuery controlNameLike( String controlNameLike){
        if (controlNameLike == null) {
            throw new RuntimeException("controlName is null");
        }
        this.controlNameLike = controlNameLike;
        return this;
    }

    public SysThemeTmpControlQuery controlNames(List<String> controlNames){
        if (controlNames == null) {
            throw new RuntimeException("controlNames is empty ");
        }
        this.controlNames = controlNames;
        return this;
    }


    public SysThemeTmpControlQuery controlCode(String controlCode){
	if (controlCode == null) {
	    throw new RuntimeException("controlCode is null");
        }         
	this.controlCode = controlCode;
	return this;
    }

    public SysThemeTmpControlQuery controlCodeLike( String controlCodeLike){
        if (controlCodeLike == null) {
            throw new RuntimeException("controlCode is null");
        }
        this.controlCodeLike = controlCodeLike;
        return this;
    }

    public SysThemeTmpControlQuery controlCodes(List<String> controlCodes){
        if (controlCodes == null) {
            throw new RuntimeException("controlCodes is empty ");
        }
        this.controlCodes = controlCodes;
        return this;
    }


    public SysThemeTmpControlQuery compType(String compType){
	if (compType == null) {
	    throw new RuntimeException("compType is null");
        }         
	this.compType = compType;
	return this;
    }

    public SysThemeTmpControlQuery compTypeLike( String compTypeLike){
        if (compTypeLike == null) {
            throw new RuntimeException("compType is null");
        }
        this.compTypeLike = compTypeLike;
        return this;
    }

    public SysThemeTmpControlQuery compTypes(List<String> compTypes){
        if (compTypes == null) {
            throw new RuntimeException("compTypes is empty ");
        }
        this.compTypes = compTypes;
        return this;
    }


    public SysThemeTmpControlQuery elemCode(String elemCode){
	if (elemCode == null) {
	    throw new RuntimeException("elemCode is null");
        }         
	this.elemCode = elemCode;
	return this;
    }

    public SysThemeTmpControlQuery elemCodeLike( String elemCodeLike){
        if (elemCodeLike == null) {
            throw new RuntimeException("elemCode is null");
        }
        this.elemCodeLike = elemCodeLike;
        return this;
    }

    public SysThemeTmpControlQuery elemCodes(List<String> elemCodes){
        if (elemCodes == null) {
            throw new RuntimeException("elemCodes is empty ");
        }
        this.elemCodes = elemCodes;
        return this;
    }


    public SysThemeTmpControlQuery compositionFlag(Integer compositionFlag){
	if (compositionFlag == null) {
            throw new RuntimeException("compositionFlag is null");
        }         
	this.compositionFlag = compositionFlag;
	return this;
    }

    public SysThemeTmpControlQuery compositionFlagGreaterThanOrEqual(Integer compositionFlagGreaterThanOrEqual){
	if (compositionFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("compositionFlag is null");
        }         
	this.compositionFlagGreaterThanOrEqual = compositionFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery compositionFlagLessThanOrEqual(Integer compositionFlagLessThanOrEqual){
        if (compositionFlagLessThanOrEqual == null) {
            throw new RuntimeException("compositionFlag is null");
        }
        this.compositionFlagLessThanOrEqual = compositionFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery compositionFlags(List<Integer> compositionFlags){
        if (compositionFlags == null) {
            throw new RuntimeException("compositionFlags is empty ");
        }
        this.compositionFlags = compositionFlags;
        return this;
    }


    public SysThemeTmpControlQuery containerFlag(Integer containerFlag){
	if (containerFlag == null) {
            throw new RuntimeException("containerFlag is null");
        }         
	this.containerFlag = containerFlag;
	return this;
    }

    public SysThemeTmpControlQuery containerFlagGreaterThanOrEqual(Integer containerFlagGreaterThanOrEqual){
	if (containerFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("containerFlag is null");
        }         
	this.containerFlagGreaterThanOrEqual = containerFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery containerFlagLessThanOrEqual(Integer containerFlagLessThanOrEqual){
        if (containerFlagLessThanOrEqual == null) {
            throw new RuntimeException("containerFlag is null");
        }
        this.containerFlagLessThanOrEqual = containerFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery containerFlags(List<Integer> containerFlags){
        if (containerFlags == null) {
            throw new RuntimeException("containerFlags is empty ");
        }
        this.containerFlags = containerFlags;
        return this;
    }


    public SysThemeTmpControlQuery selectorExp(String selectorExp){
	if (selectorExp == null) {
	    throw new RuntimeException("selectorExp is null");
        }         
	this.selectorExp = selectorExp;
	return this;
    }

    public SysThemeTmpControlQuery selectorExpLike( String selectorExpLike){
        if (selectorExpLike == null) {
            throw new RuntimeException("selectorExp is null");
        }
        this.selectorExpLike = selectorExpLike;
        return this;
    }

    public SysThemeTmpControlQuery selectorExps(List<String> selectorExps){
        if (selectorExps == null) {
            throw new RuntimeException("selectorExps is empty ");
        }
        this.selectorExps = selectorExps;
        return this;
    }


    public SysThemeTmpControlQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SysThemeTmpControlQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SysThemeTmpControlQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SysThemeTmpControlQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpControlQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SysThemeTmpControlQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SysThemeTmpControlQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SysThemeTmpControlQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpControlQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SysThemeTmpControlQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpControlQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("themeTmpId".equals(sortColumn)) {
                orderBy = "E.THEME_TMP_ID_" + a_x;
            } 

            if ("commonFlag".equals(sortColumn)) {
                orderBy = "E.COMMON_FLAG_" + a_x;
            } 

            if ("controlName".equals(sortColumn)) {
                orderBy = "E.CONTROL_NAME_" + a_x;
            } 

            if ("controlCode".equals(sortColumn)) {
                orderBy = "E.CONTROL_CODE_" + a_x;
            } 

            if ("compType".equals(sortColumn)) {
                orderBy = "E.COMP_TYPE_" + a_x;
            } 

            if ("elemCode".equals(sortColumn)) {
                orderBy = "E.ELEM_CODE_" + a_x;
            } 

            if ("compositionFlag".equals(sortColumn)) {
                orderBy = "E.COMPOSITION_FLAG_" + a_x;
            } 

            if ("containerFlag".equals(sortColumn)) {
                orderBy = "E.CONTAINER_FLAG_" + a_x;
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
        addColumn("controlId", "CONTROL_ID_");
        addColumn("themeTmpId", "THEME_TMP_ID_");
        addColumn("commonFlag", "COMMON_FLAG_");
        addColumn("controlName", "CONTROL_NAME_");
        addColumn("controlCode", "CONTROL_CODE_");
        addColumn("compType", "COMP_TYPE_");
        addColumn("elemCode", "ELEM_CODE_");
        addColumn("compositionFlag", "COMPOSITION_FLAG_");
        addColumn("containerFlag", "CONTAINER_FLAG_");
        addColumn("selectorExp", "SELECTOR_EXP_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}