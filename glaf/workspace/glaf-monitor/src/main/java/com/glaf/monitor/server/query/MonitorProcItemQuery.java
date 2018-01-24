package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorProcItemQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String procId;
  	protected String procIdLike;
  	protected List<String> procIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String unit;
  	protected String unitLike;
  	protected List<String> units;
  	protected Integer alarmVal;
  	protected Integer alarmValGreaterThanOrEqual;
  	protected Integer alarmValLessThanOrEqual;
  	protected List<Integer> alarmVals;
  	protected String refType;
  	protected String refTypeLike;
  	protected List<String> refTypes;
  	protected String warningType;
  	protected String warningTypeLike;
  	protected List<String> warningTypes;
  	protected String monitorServiceAddress;
  	protected String monitorServiceAddressLike;
  	protected List<String> monitorServiceAddresss;
  	protected String createby;
  	protected String createbyLike;
  	protected List<String> createbys;
        protected Date createtimeGreaterThanOrEqual;
  	protected Date createtimeLessThanOrEqual;
  	protected String updateby;
  	protected String updatebyLike;
  	protected List<String> updatebys;
        protected Date updatetimeGreaterThanOrEqual;
  	protected Date updatetimeLessThanOrEqual;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;
  	protected String type;

    public MonitorProcItemQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }
    
    public String getType(){
    	return type;
    }
    public void setType(String type) {
		this.type = type;
	}

	public String getProcId(){
        return procId;
    }

    public String getProcIdLike(){
	    if (procIdLike != null && procIdLike.trim().length() > 0) {
		if (!procIdLike.startsWith("%")) {
		    procIdLike = "%" + procIdLike;
		}
		if (!procIdLike.endsWith("%")) {
		   procIdLike = procIdLike + "%";
		}
	    }
	return procIdLike;
    }

    public List<String> getProcIds(){
	return procIds;
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


    public String getUnit(){
        return unit;
    }

    public String getUnitLike(){
	    if (unitLike != null && unitLike.trim().length() > 0) {
		if (!unitLike.startsWith("%")) {
		    unitLike = "%" + unitLike;
		}
		if (!unitLike.endsWith("%")) {
		   unitLike = unitLike + "%";
		}
	    }
	return unitLike;
    }

    public List<String> getUnits(){
	return units;
    }


    public Integer getAlarmVal(){
        return alarmVal;
    }

    public Integer getAlarmValGreaterThanOrEqual(){
        return alarmValGreaterThanOrEqual;
    }

    public Integer getAlarmValLessThanOrEqual(){
	return alarmValLessThanOrEqual;
    }

    public List<Integer> getAlarmVals(){
	return alarmVals;
    }

    public String getRefType(){
        return refType;
    }

    public String getRefTypeLike(){
	    if (refTypeLike != null && refTypeLike.trim().length() > 0) {
		if (!refTypeLike.startsWith("%")) {
		    refTypeLike = "%" + refTypeLike;
		}
		if (!refTypeLike.endsWith("%")) {
		   refTypeLike = refTypeLike + "%";
		}
	    }
	return refTypeLike;
    }

    public List<String> getRefTypes(){
	return refTypes;
    }


    public String getWarningType(){
        return warningType;
    }

    public String getWarningTypeLike(){
	    if (warningTypeLike != null && warningTypeLike.trim().length() > 0) {
		if (!warningTypeLike.startsWith("%")) {
		    warningTypeLike = "%" + warningTypeLike;
		}
		if (!warningTypeLike.endsWith("%")) {
		   warningTypeLike = warningTypeLike + "%";
		}
	    }
	return warningTypeLike;
    }

    public List<String> getWarningTypes(){
	return warningTypes;
    }


    public String getMonitorServiceAddress(){
        return monitorServiceAddress;
    }

    public String getMonitorServiceAddressLike(){
	    if (monitorServiceAddressLike != null && monitorServiceAddressLike.trim().length() > 0) {
		if (!monitorServiceAddressLike.startsWith("%")) {
		    monitorServiceAddressLike = "%" + monitorServiceAddressLike;
		}
		if (!monitorServiceAddressLike.endsWith("%")) {
		   monitorServiceAddressLike = monitorServiceAddressLike + "%";
		}
	    }
	return monitorServiceAddressLike;
    }

    public List<String> getMonitorServiceAddresss(){
	return monitorServiceAddresss;
    }


    public String getCreateby(){
        return createby;
    }

    public String getCreatebyLike(){
	    if (createbyLike != null && createbyLike.trim().length() > 0) {
		if (!createbyLike.startsWith("%")) {
		    createbyLike = "%" + createbyLike;
		}
		if (!createbyLike.endsWith("%")) {
		   createbyLike = createbyLike + "%";
		}
	    }
	return createbyLike;
    }

    public List<String> getCreatebys(){
	return createbys;
    }


    public Date getCreatetimeGreaterThanOrEqual(){
        return createtimeGreaterThanOrEqual;
    }

    public Date getCreatetimeLessThanOrEqual(){
	return createtimeLessThanOrEqual;
    }


    public String getUpdateby(){
        return updateby;
    }

    public String getUpdatebyLike(){
	    if (updatebyLike != null && updatebyLike.trim().length() > 0) {
		if (!updatebyLike.startsWith("%")) {
		    updatebyLike = "%" + updatebyLike;
		}
		if (!updatebyLike.endsWith("%")) {
		   updatebyLike = updatebyLike + "%";
		}
	    }
	return updatebyLike;
    }

    public List<String> getUpdatebys(){
	return updatebys;
    }


    public Date getUpdatetimeGreaterThanOrEqual(){
        return updatetimeGreaterThanOrEqual;
    }

    public Date getUpdatetimeLessThanOrEqual(){
	return updatetimeLessThanOrEqual;
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

 

    public void setProcId(String procId){
        this.procId = procId;
    }

    public void setProcIdLike( String procIdLike){
	this.procIdLike = procIdLike;
    }

    public void setProcIds(List<String> procIds){
        this.procIds = procIds;
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


    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setUnitLike( String unitLike){
	this.unitLike = unitLike;
    }

    public void setUnits(List<String> units){
        this.units = units;
    }


    public void setAlarmVal(Integer alarmVal){
        this.alarmVal = alarmVal;
    }

    public void setAlarmValGreaterThanOrEqual(Integer alarmValGreaterThanOrEqual){
        this.alarmValGreaterThanOrEqual = alarmValGreaterThanOrEqual;
    }

    public void setAlarmValLessThanOrEqual(Integer alarmValLessThanOrEqual){
	this.alarmValLessThanOrEqual = alarmValLessThanOrEqual;
    }

    public void setAlarmVals(List<Integer> alarmVals){
        this.alarmVals = alarmVals;
    }


    public void setRefType(String refType){
        this.refType = refType;
    }

    public void setRefTypeLike( String refTypeLike){
	this.refTypeLike = refTypeLike;
    }

    public void setRefTypes(List<String> refTypes){
        this.refTypes = refTypes;
    }


    public void setWarningType(String warningType){
        this.warningType = warningType;
    }

    public void setWarningTypeLike( String warningTypeLike){
	this.warningTypeLike = warningTypeLike;
    }

    public void setWarningTypes(List<String> warningTypes){
        this.warningTypes = warningTypes;
    }


    public void setMonitorServiceAddress(String monitorServiceAddress){
        this.monitorServiceAddress = monitorServiceAddress;
    }

    public void setMonitorServiceAddressLike( String monitorServiceAddressLike){
	this.monitorServiceAddressLike = monitorServiceAddressLike;
    }

    public void setMonitorServiceAddresss(List<String> monitorServiceAddresss){
        this.monitorServiceAddresss = monitorServiceAddresss;
    }


    public void setCreateby(String createby){
        this.createby = createby;
    }

    public void setCreatebyLike( String createbyLike){
	this.createbyLike = createbyLike;
    }

    public void setCreatebys(List<String> createbys){
        this.createbys = createbys;
    }


    public void setCreatetimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
        this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
    }

    public void setCreatetimeLessThanOrEqual(Date createtimeLessThanOrEqual){
	this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
    }


    public void setUpdateby(String updateby){
        this.updateby = updateby;
    }

    public void setUpdatebyLike( String updatebyLike){
	this.updatebyLike = updatebyLike;
    }

    public void setUpdatebys(List<String> updatebys){
        this.updatebys = updatebys;
    }


    public void setUpdatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
        this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
    }

    public void setUpdatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
	this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
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




    public MonitorProcItemQuery procId(String procId){
	if (procId == null) {
	    throw new RuntimeException("procId is null");
        }         
	this.procId = procId;
	return this;
    }

    public MonitorProcItemQuery procIdLike( String procIdLike){
        if (procIdLike == null) {
            throw new RuntimeException("procId is null");
        }
        this.procIdLike = procIdLike;
        return this;
    }

    public MonitorProcItemQuery procIds(List<String> procIds){
        if (procIds == null) {
            throw new RuntimeException("procIds is empty ");
        }
        this.procIds = procIds;
        return this;
    }


    public MonitorProcItemQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public MonitorProcItemQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public MonitorProcItemQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public MonitorProcItemQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public MonitorProcItemQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public MonitorProcItemQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public MonitorProcItemQuery unit(String unit){
	if (unit == null) {
	    throw new RuntimeException("unit is null");
        }         
	this.unit = unit;
	return this;
    }

    public MonitorProcItemQuery unitLike( String unitLike){
        if (unitLike == null) {
            throw new RuntimeException("unit is null");
        }
        this.unitLike = unitLike;
        return this;
    }

    public MonitorProcItemQuery units(List<String> units){
        if (units == null) {
            throw new RuntimeException("units is empty ");
        }
        this.units = units;
        return this;
    }


    public MonitorProcItemQuery alarmVal(Integer alarmVal){
	if (alarmVal == null) {
            throw new RuntimeException("alarmVal is null");
        }         
	this.alarmVal = alarmVal;
	return this;
    }

    public MonitorProcItemQuery alarmValGreaterThanOrEqual(Integer alarmValGreaterThanOrEqual){
	if (alarmValGreaterThanOrEqual == null) {
	    throw new RuntimeException("alarmVal is null");
        }         
	this.alarmValGreaterThanOrEqual = alarmValGreaterThanOrEqual;
        return this;
    }

    public MonitorProcItemQuery alarmValLessThanOrEqual(Integer alarmValLessThanOrEqual){
        if (alarmValLessThanOrEqual == null) {
            throw new RuntimeException("alarmVal is null");
        }
        this.alarmValLessThanOrEqual = alarmValLessThanOrEqual;
        return this;
    }

    public MonitorProcItemQuery alarmVals(List<Integer> alarmVals){
        if (alarmVals == null) {
            throw new RuntimeException("alarmVals is empty ");
        }
        this.alarmVals = alarmVals;
        return this;
    }


    public MonitorProcItemQuery refType(String refType){
	if (refType == null) {
	    throw new RuntimeException("refType is null");
        }         
	this.refType = refType;
	return this;
    }

    public MonitorProcItemQuery refTypeLike( String refTypeLike){
        if (refTypeLike == null) {
            throw new RuntimeException("refType is null");
        }
        this.refTypeLike = refTypeLike;
        return this;
    }

    public MonitorProcItemQuery refTypes(List<String> refTypes){
        if (refTypes == null) {
            throw new RuntimeException("refTypes is empty ");
        }
        this.refTypes = refTypes;
        return this;
    }


    public MonitorProcItemQuery warningType(String warningType){
	if (warningType == null) {
	    throw new RuntimeException("warningType is null");
        }         
	this.warningType = warningType;
	return this;
    }

    public MonitorProcItemQuery warningTypeLike( String warningTypeLike){
        if (warningTypeLike == null) {
            throw new RuntimeException("warningType is null");
        }
        this.warningTypeLike = warningTypeLike;
        return this;
    }

    public MonitorProcItemQuery warningTypes(List<String> warningTypes){
        if (warningTypes == null) {
            throw new RuntimeException("warningTypes is empty ");
        }
        this.warningTypes = warningTypes;
        return this;
    }


    public MonitorProcItemQuery monitorServiceAddress(String monitorServiceAddress){
	if (monitorServiceAddress == null) {
	    throw new RuntimeException("monitorServiceAddress is null");
        }         
	this.monitorServiceAddress = monitorServiceAddress;
	return this;
    }

    public MonitorProcItemQuery monitorServiceAddressLike( String monitorServiceAddressLike){
        if (monitorServiceAddressLike == null) {
            throw new RuntimeException("monitorServiceAddress is null");
        }
        this.monitorServiceAddressLike = monitorServiceAddressLike;
        return this;
    }

    public MonitorProcItemQuery monitorServiceAddresss(List<String> monitorServiceAddresss){
        if (monitorServiceAddresss == null) {
            throw new RuntimeException("monitorServiceAddresss is empty ");
        }
        this.monitorServiceAddresss = monitorServiceAddresss;
        return this;
    }


    public MonitorProcItemQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorProcItemQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorProcItemQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorProcItemQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcItemQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }



    public MonitorProcItemQuery updateby(String updateby){
	if (updateby == null) {
	    throw new RuntimeException("updateby is null");
        }         
	this.updateby = updateby;
	return this;
    }

    public MonitorProcItemQuery updatebyLike( String updatebyLike){
        if (updatebyLike == null) {
            throw new RuntimeException("updateby is null");
        }
        this.updatebyLike = updatebyLike;
        return this;
    }

    public MonitorProcItemQuery updatebys(List<String> updatebys){
        if (updatebys == null) {
            throw new RuntimeException("updatebys is empty ");
        }
        this.updatebys = updatebys;
        return this;
    }



    public MonitorProcItemQuery updatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
	if (updatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updatetime is null");
        }         
	this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcItemQuery updatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
        if (updatetimeLessThanOrEqual == null) {
            throw new RuntimeException("updatetime is null");
        }
        this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
        return this;
    }



    public MonitorProcItemQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public MonitorProcItemQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public MonitorProcItemQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public MonitorProcItemQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("procId".equals(sortColumn)) {
                orderBy = "E.PROC_ID_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("unit".equals(sortColumn)) {
                orderBy = "E.UNIT_" + a_x;
            } 

            if ("alarmVal".equals(sortColumn)) {
                orderBy = "E.ALARM_VAL_" + a_x;
            } 

            if ("refType".equals(sortColumn)) {
                orderBy = "E.REF_TYPE_" + a_x;
            } 

            if ("warningType".equals(sortColumn)) {
                orderBy = "E.WARNING_TYPE_" + a_x;
            } 

            if ("monitorServiceAddress".equals(sortColumn)) {
                orderBy = "E.MONITOR_SERVICE_ADDRESS_" + a_x;
            } 

            if ("createby".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createtime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateby".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updatetime".equals(sortColumn)) {
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
        addColumn("id", "ID_");
        addColumn("procId", "PROC_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("unit", "UNIT_");
        addColumn("alarmVal", "ALARM_VAL_");
        addColumn("refType", "REF_TYPE_");
        addColumn("warningType", "WARNING_TYPE_");
        addColumn("monitorServiceAddress", "MONITOR_SERVICE_ADDRESS_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
        addColumn("updateby", "UPDATEBY_");
        addColumn("updatetime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}