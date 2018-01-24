package com.glaf.theme.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysThemeTmpLayoutAreaControlQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> controlIds;
	protected Collection<String> appActorIds;
  	protected String themeTmpControlId;
  	protected String themeTmpControlIdLike;
  	protected List<String> themeTmpControlIds;
  	protected String areaId;
  	protected String areaIdLike;
  	protected List<String> areaIds;
  	protected String layoutId;
  	protected String layoutIdLike;
  	protected List<String> layoutIds;
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
  	protected String selectorExp;
  	protected String selectorExpLike;
  	protected List<String> selectorExps;
  	protected Integer commonFlag;
  	protected Integer commonFlagGreaterThanOrEqual;
  	protected Integer commonFlagLessThanOrEqual;
  	protected List<Integer> commonFlags;
  	protected Integer containerFlag;
  	protected Integer containerFlagGreaterThanOrEqual;
  	protected Integer containerFlagLessThanOrEqual;
  	protected List<Integer> containerFlags;
  	protected String pcontrolId;
  	protected String pcontrolIdLike;
  	protected List<String> pcontrolIds;
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

    public SysThemeTmpLayoutAreaControlQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getThemeTmpControlId(){
        return themeTmpControlId;
    }

    public String getThemeTmpControlIdLike(){
	    if (themeTmpControlIdLike != null && themeTmpControlIdLike.trim().length() > 0) {
		if (!themeTmpControlIdLike.startsWith("%")) {
		    themeTmpControlIdLike = "%" + themeTmpControlIdLike;
		}
		if (!themeTmpControlIdLike.endsWith("%")) {
		   themeTmpControlIdLike = themeTmpControlIdLike + "%";
		}
	    }
	return themeTmpControlIdLike;
    }

    public List<String> getThemeTmpControlIds(){
	return themeTmpControlIds;
    }


    public String getAreaId(){
        return areaId;
    }

    public String getAreaIdLike(){
	    if (areaIdLike != null && areaIdLike.trim().length() > 0) {
		if (!areaIdLike.startsWith("%")) {
		    areaIdLike = "%" + areaIdLike;
		}
		if (!areaIdLike.endsWith("%")) {
		   areaIdLike = areaIdLike + "%";
		}
	    }
	return areaIdLike;
    }

    public List<String> getAreaIds(){
	return areaIds;
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

    public String getPcontrolId(){
        return pcontrolId;
    }

    public String getPcontrolIdLike(){
	    if (pcontrolIdLike != null && pcontrolIdLike.trim().length() > 0) {
		if (!pcontrolIdLike.startsWith("%")) {
		    pcontrolIdLike = "%" + pcontrolIdLike;
		}
		if (!pcontrolIdLike.endsWith("%")) {
		   pcontrolIdLike = pcontrolIdLike + "%";
		}
	    }
	return pcontrolIdLike;
    }

    public List<String> getPcontrolIds(){
	return pcontrolIds;
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

 

    public void setThemeTmpControlId(String themeTmpControlId){
        this.themeTmpControlId = themeTmpControlId;
    }

    public void setThemeTmpControlIdLike( String themeTmpControlIdLike){
	this.themeTmpControlIdLike = themeTmpControlIdLike;
    }

    public void setThemeTmpControlIds(List<String> themeTmpControlIds){
        this.themeTmpControlIds = themeTmpControlIds;
    }


    public void setAreaId(String areaId){
        this.areaId = areaId;
    }

    public void setAreaIdLike( String areaIdLike){
	this.areaIdLike = areaIdLike;
    }

    public void setAreaIds(List<String> areaIds){
        this.areaIds = areaIds;
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


    public void setSelectorExp(String selectorExp){
        this.selectorExp = selectorExp;
    }

    public void setSelectorExpLike( String selectorExpLike){
	this.selectorExpLike = selectorExpLike;
    }

    public void setSelectorExps(List<String> selectorExps){
        this.selectorExps = selectorExps;
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


    public void setPcontrolId(String pcontrolId){
        this.pcontrolId = pcontrolId;
    }

    public void setPcontrolIdLike( String pcontrolIdLike){
	this.pcontrolIdLike = pcontrolIdLike;
    }

    public void setPcontrolIds(List<String> pcontrolIds){
        this.pcontrolIds = pcontrolIds;
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




    public SysThemeTmpLayoutAreaControlQuery themeTmpControlId(String themeTmpControlId){
	if (themeTmpControlId == null) {
	    throw new RuntimeException("themeTmpControlId is null");
        }         
	this.themeTmpControlId = themeTmpControlId;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery themeTmpControlIdLike( String themeTmpControlIdLike){
        if (themeTmpControlIdLike == null) {
            throw new RuntimeException("themeTmpControlId is null");
        }
        this.themeTmpControlIdLike = themeTmpControlIdLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery themeTmpControlIds(List<String> themeTmpControlIds){
        if (themeTmpControlIds == null) {
            throw new RuntimeException("themeTmpControlIds is empty ");
        }
        this.themeTmpControlIds = themeTmpControlIds;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery areaId(String areaId){
	if (areaId == null) {
	    throw new RuntimeException("areaId is null");
        }         
	this.areaId = areaId;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery areaIdLike( String areaIdLike){
        if (areaIdLike == null) {
            throw new RuntimeException("areaId is null");
        }
        this.areaIdLike = areaIdLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery areaIds(List<String> areaIds){
        if (areaIds == null) {
            throw new RuntimeException("areaIds is empty ");
        }
        this.areaIds = areaIds;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery layoutId(String layoutId){
	if (layoutId == null) {
	    throw new RuntimeException("layoutId is null");
        }         
	this.layoutId = layoutId;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery layoutIdLike( String layoutIdLike){
        if (layoutIdLike == null) {
            throw new RuntimeException("layoutId is null");
        }
        this.layoutIdLike = layoutIdLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery layoutIds(List<String> layoutIds){
        if (layoutIds == null) {
            throw new RuntimeException("layoutIds is empty ");
        }
        this.layoutIds = layoutIds;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery controlName(String controlName){
	if (controlName == null) {
	    throw new RuntimeException("controlName is null");
        }         
	this.controlName = controlName;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery controlNameLike( String controlNameLike){
        if (controlNameLike == null) {
            throw new RuntimeException("controlName is null");
        }
        this.controlNameLike = controlNameLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery controlNames(List<String> controlNames){
        if (controlNames == null) {
            throw new RuntimeException("controlNames is empty ");
        }
        this.controlNames = controlNames;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery controlCode(String controlCode){
	if (controlCode == null) {
	    throw new RuntimeException("controlCode is null");
        }         
	this.controlCode = controlCode;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery controlCodeLike( String controlCodeLike){
        if (controlCodeLike == null) {
            throw new RuntimeException("controlCode is null");
        }
        this.controlCodeLike = controlCodeLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery controlCodes(List<String> controlCodes){
        if (controlCodes == null) {
            throw new RuntimeException("controlCodes is empty ");
        }
        this.controlCodes = controlCodes;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery compType(String compType){
	if (compType == null) {
	    throw new RuntimeException("compType is null");
        }         
	this.compType = compType;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery compTypeLike( String compTypeLike){
        if (compTypeLike == null) {
            throw new RuntimeException("compType is null");
        }
        this.compTypeLike = compTypeLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery compTypes(List<String> compTypes){
        if (compTypes == null) {
            throw new RuntimeException("compTypes is empty ");
        }
        this.compTypes = compTypes;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery elemCode(String elemCode){
	if (elemCode == null) {
	    throw new RuntimeException("elemCode is null");
        }         
	this.elemCode = elemCode;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery elemCodeLike( String elemCodeLike){
        if (elemCodeLike == null) {
            throw new RuntimeException("elemCode is null");
        }
        this.elemCodeLike = elemCodeLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery elemCodes(List<String> elemCodes){
        if (elemCodes == null) {
            throw new RuntimeException("elemCodes is empty ");
        }
        this.elemCodes = elemCodes;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery selectorExp(String selectorExp){
	if (selectorExp == null) {
	    throw new RuntimeException("selectorExp is null");
        }         
	this.selectorExp = selectorExp;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery selectorExpLike( String selectorExpLike){
        if (selectorExpLike == null) {
            throw new RuntimeException("selectorExp is null");
        }
        this.selectorExpLike = selectorExpLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery selectorExps(List<String> selectorExps){
        if (selectorExps == null) {
            throw new RuntimeException("selectorExps is empty ");
        }
        this.selectorExps = selectorExps;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery commonFlag(Integer commonFlag){
	if (commonFlag == null) {
            throw new RuntimeException("commonFlag is null");
        }         
	this.commonFlag = commonFlag;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery commonFlagGreaterThanOrEqual(Integer commonFlagGreaterThanOrEqual){
	if (commonFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("commonFlag is null");
        }         
	this.commonFlagGreaterThanOrEqual = commonFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery commonFlagLessThanOrEqual(Integer commonFlagLessThanOrEqual){
        if (commonFlagLessThanOrEqual == null) {
            throw new RuntimeException("commonFlag is null");
        }
        this.commonFlagLessThanOrEqual = commonFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery commonFlags(List<Integer> commonFlags){
        if (commonFlags == null) {
            throw new RuntimeException("commonFlags is empty ");
        }
        this.commonFlags = commonFlags;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery containerFlag(Integer containerFlag){
	if (containerFlag == null) {
            throw new RuntimeException("containerFlag is null");
        }         
	this.containerFlag = containerFlag;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery containerFlagGreaterThanOrEqual(Integer containerFlagGreaterThanOrEqual){
	if (containerFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("containerFlag is null");
        }         
	this.containerFlagGreaterThanOrEqual = containerFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery containerFlagLessThanOrEqual(Integer containerFlagLessThanOrEqual){
        if (containerFlagLessThanOrEqual == null) {
            throw new RuntimeException("containerFlag is null");
        }
        this.containerFlagLessThanOrEqual = containerFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery containerFlags(List<Integer> containerFlags){
        if (containerFlags == null) {
            throw new RuntimeException("containerFlags is empty ");
        }
        this.containerFlags = containerFlags;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery pcontrolId(String pcontrolId){
	if (pcontrolId == null) {
	    throw new RuntimeException("pcontrolId is null");
        }         
	this.pcontrolId = pcontrolId;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery pcontrolIdLike( String pcontrolIdLike){
        if (pcontrolIdLike == null) {
            throw new RuntimeException("pcontrolId is null");
        }
        this.pcontrolIdLike = pcontrolIdLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery pcontrolIds(List<String> pcontrolIds){
        if (pcontrolIds == null) {
            throw new RuntimeException("pcontrolIds is empty ");
        }
        this.pcontrolIds = pcontrolIds;
        return this;
    }


    public SysThemeTmpLayoutAreaControlQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SysThemeTmpLayoutAreaControlQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpLayoutAreaControlQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SysThemeTmpLayoutAreaControlQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpLayoutAreaControlQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SysThemeTmpLayoutAreaControlQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutAreaControlQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("themeTmpControlId".equals(sortColumn)) {
                orderBy = "E.THEME_TMP_CONTROL_ID_" + a_x;
            } 

            if ("areaId".equals(sortColumn)) {
                orderBy = "E.AREA_ID_" + a_x;
            } 

            if ("layoutId".equals(sortColumn)) {
                orderBy = "E.LAYOUT_ID_" + a_x;
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

            if ("selectorExp".equals(sortColumn)) {
                orderBy = "E.SELECTOR_EXP_" + a_x;
            } 

            if ("commonFlag".equals(sortColumn)) {
                orderBy = "E.COMMON_FLAG_" + a_x;
            } 

            if ("containerFlag".equals(sortColumn)) {
                orderBy = "E.CONTAINER_FLAG_" + a_x;
            } 

            if ("pcontrolId".equals(sortColumn)) {
                orderBy = "E.P_CONTROL_ID_" + a_x;
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
        addColumn("themeTmpControlId", "THEME_TMP_CONTROL_ID_");
        addColumn("areaId", "AREA_ID_");
        addColumn("layoutId", "LAYOUT_ID_");
        addColumn("controlName", "CONTROL_NAME_");
        addColumn("controlCode", "CONTROL_CODE_");
        addColumn("compType", "COMP_TYPE_");
        addColumn("elemCode", "ELEM_CODE_");
        addColumn("selectorExp", "SELECTOR_EXP_");
        addColumn("commonFlag", "COMMON_FLAG_");
        addColumn("containerFlag", "CONTAINER_FLAG_");
        addColumn("pcontrolId", "P_CONTROL_ID_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}