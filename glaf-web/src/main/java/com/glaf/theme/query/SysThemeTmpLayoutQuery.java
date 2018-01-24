package com.glaf.theme.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysThemeTmpLayoutQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> layoutIds;
	protected Collection<String> appActorIds;
  	protected String themeTmpId;
  	protected String themeTmpIdLike;
  	protected List<String> themeTmpIds;
  	protected String layoutName;
  	protected String layoutNameLike;
  	protected List<String> layoutNames;
  	protected String layoutPlan;
  	protected String layoutPlanLike;
  	protected List<String> layoutPlans;
  	protected String layoutCode;
  	protected String layoutCodeLike;
  	protected List<String> layoutCodes;
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

    public SysThemeTmpLayoutQuery() {

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


    public String getLayoutName(){
        return layoutName;
    }

    public String getLayoutNameLike(){
	    if (layoutNameLike != null && layoutNameLike.trim().length() > 0) {
		if (!layoutNameLike.startsWith("%")) {
		    layoutNameLike = "%" + layoutNameLike;
		}
		if (!layoutNameLike.endsWith("%")) {
		   layoutNameLike = layoutNameLike + "%";
		}
	    }
	return layoutNameLike;
    }

    public List<String> getLayoutNames(){
	return layoutNames;
    }


    public String getLayoutPlan(){
        return layoutPlan;
    }

    public String getLayoutPlanLike(){
	    if (layoutPlanLike != null && layoutPlanLike.trim().length() > 0) {
		if (!layoutPlanLike.startsWith("%")) {
		    layoutPlanLike = "%" + layoutPlanLike;
		}
		if (!layoutPlanLike.endsWith("%")) {
		   layoutPlanLike = layoutPlanLike + "%";
		}
	    }
	return layoutPlanLike;
    }

    public List<String> getLayoutPlans(){
	return layoutPlans;
    }


    public String getLayoutCode(){
        return layoutCode;
    }

    public String getLayoutCodeLike(){
	    if (layoutCodeLike != null && layoutCodeLike.trim().length() > 0) {
		if (!layoutCodeLike.startsWith("%")) {
		    layoutCodeLike = "%" + layoutCodeLike;
		}
		if (!layoutCodeLike.endsWith("%")) {
		   layoutCodeLike = layoutCodeLike + "%";
		}
	    }
	return layoutCodeLike;
    }

    public List<String> getLayoutCodes(){
	return layoutCodes;
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


    public void setLayoutName(String layoutName){
        this.layoutName = layoutName;
    }

    public void setLayoutNameLike( String layoutNameLike){
	this.layoutNameLike = layoutNameLike;
    }

    public void setLayoutNames(List<String> layoutNames){
        this.layoutNames = layoutNames;
    }


    public void setLayoutPlan(String layoutPlan){
        this.layoutPlan = layoutPlan;
    }

    public void setLayoutPlanLike( String layoutPlanLike){
	this.layoutPlanLike = layoutPlanLike;
    }

    public void setLayoutPlans(List<String> layoutPlans){
        this.layoutPlans = layoutPlans;
    }


    public void setLayoutCode(String layoutCode){
        this.layoutCode = layoutCode;
    }

    public void setLayoutCodeLike( String layoutCodeLike){
	this.layoutCodeLike = layoutCodeLike;
    }

    public void setLayoutCodes(List<String> layoutCodes){
        this.layoutCodes = layoutCodes;
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




    public SysThemeTmpLayoutQuery themeTmpId(String themeTmpId){
	if (themeTmpId == null) {
	    throw new RuntimeException("themeTmpId is null");
        }         
	this.themeTmpId = themeTmpId;
	return this;
    }

    public SysThemeTmpLayoutQuery themeTmpIdLike( String themeTmpIdLike){
        if (themeTmpIdLike == null) {
            throw new RuntimeException("themeTmpId is null");
        }
        this.themeTmpIdLike = themeTmpIdLike;
        return this;
    }

    public SysThemeTmpLayoutQuery themeTmpIds(List<String> themeTmpIds){
        if (themeTmpIds == null) {
            throw new RuntimeException("themeTmpIds is empty ");
        }
        this.themeTmpIds = themeTmpIds;
        return this;
    }


    public SysThemeTmpLayoutQuery layoutName(String layoutName){
	if (layoutName == null) {
	    throw new RuntimeException("layoutName is null");
        }         
	this.layoutName = layoutName;
	return this;
    }

    public SysThemeTmpLayoutQuery layoutNameLike( String layoutNameLike){
        if (layoutNameLike == null) {
            throw new RuntimeException("layoutName is null");
        }
        this.layoutNameLike = layoutNameLike;
        return this;
    }

    public SysThemeTmpLayoutQuery layoutNames(List<String> layoutNames){
        if (layoutNames == null) {
            throw new RuntimeException("layoutNames is empty ");
        }
        this.layoutNames = layoutNames;
        return this;
    }


    public SysThemeTmpLayoutQuery layoutPlan(String layoutPlan){
	if (layoutPlan == null) {
	    throw new RuntimeException("layoutPlan is null");
        }         
	this.layoutPlan = layoutPlan;
	return this;
    }

    public SysThemeTmpLayoutQuery layoutPlanLike( String layoutPlanLike){
        if (layoutPlanLike == null) {
            throw new RuntimeException("layoutPlan is null");
        }
        this.layoutPlanLike = layoutPlanLike;
        return this;
    }

    public SysThemeTmpLayoutQuery layoutPlans(List<String> layoutPlans){
        if (layoutPlans == null) {
            throw new RuntimeException("layoutPlans is empty ");
        }
        this.layoutPlans = layoutPlans;
        return this;
    }


    public SysThemeTmpLayoutQuery layoutCode(String layoutCode){
	if (layoutCode == null) {
	    throw new RuntimeException("layoutCode is null");
        }         
	this.layoutCode = layoutCode;
	return this;
    }

    public SysThemeTmpLayoutQuery layoutCodeLike( String layoutCodeLike){
        if (layoutCodeLike == null) {
            throw new RuntimeException("layoutCode is null");
        }
        this.layoutCodeLike = layoutCodeLike;
        return this;
    }

    public SysThemeTmpLayoutQuery layoutCodes(List<String> layoutCodes){
        if (layoutCodes == null) {
            throw new RuntimeException("layoutCodes is empty ");
        }
        this.layoutCodes = layoutCodes;
        return this;
    }


    public SysThemeTmpLayoutQuery selectorExp(String selectorExp){
	if (selectorExp == null) {
	    throw new RuntimeException("selectorExp is null");
        }         
	this.selectorExp = selectorExp;
	return this;
    }

    public SysThemeTmpLayoutQuery selectorExpLike( String selectorExpLike){
        if (selectorExpLike == null) {
            throw new RuntimeException("selectorExp is null");
        }
        this.selectorExpLike = selectorExpLike;
        return this;
    }

    public SysThemeTmpLayoutQuery selectorExps(List<String> selectorExps){
        if (selectorExps == null) {
            throw new RuntimeException("selectorExps is empty ");
        }
        this.selectorExps = selectorExps;
        return this;
    }


    public SysThemeTmpLayoutQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SysThemeTmpLayoutQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SysThemeTmpLayoutQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SysThemeTmpLayoutQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpLayoutQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SysThemeTmpLayoutQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SysThemeTmpLayoutQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SysThemeTmpLayoutQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpLayoutQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SysThemeTmpLayoutQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpLayoutQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("layoutName".equals(sortColumn)) {
                orderBy = "E.LAYOUT_NAME_" + a_x;
            } 

            if ("layoutPlan".equals(sortColumn)) {
                orderBy = "E.LAYOUT_PLAN_" + a_x;
            } 

            if ("layoutCode".equals(sortColumn)) {
                orderBy = "E.LAYOUT_CODE_" + a_x;
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
        addColumn("layoutId", "LAYOUT_ID_");
        addColumn("themeTmpId", "THEME_TMP_ID_");
        addColumn("layoutName", "LAYOUT_NAME_");
        addColumn("layoutPlan", "LAYOUT_PLAN_");
        addColumn("layoutCode", "LAYOUT_CODE_");
        addColumn("selectorExp", "SELECTOR_EXP_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}