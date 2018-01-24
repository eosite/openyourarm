package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorProcQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String terminalId;
  	protected String terminalIdLike;
  	protected List<String> terminalIds;
  	protected String level;
  	protected String levelLike;
  	protected List<String> levels;
  	protected String processName;
  	protected String processNameLike;
  	protected List<String> processNames;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String prod;
  	protected String prodLike;
  	protected List<String> prods;
  	protected String ver;
  	protected String verLike;
  	protected List<String> vers;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
  	protected String desc;
  	protected String descLike;
  	protected List<String> descs;
  	protected Integer port;
  	protected Integer portGreaterThanOrEqual;
  	protected Integer portLessThanOrEqual;
  	protected List<Integer> ports;
  	protected String monitorServiceAddress;
  	protected String monitorServiceAddressLike;
  	protected List<String> monitorServiceAddresss;
  	protected String startAddress;
  	protected String startAddressLike;
  	protected List<String> startAddresss;
  	protected String stopAddress;
  	protected String stopAddressLike;
  	protected List<String> stopAddresss;
  	protected String terminateAddress;
  	protected String terminateAddressLike;
  	protected List<String> terminateAddresss;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected String parentProcId;
  	protected String parentProcIdLike;
  	protected List<String> parentProcIds;
  	protected String otherItems;
  	protected String otherItemsLike;
  	protected List<String> otherItemss;
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

  	/**
  	 * 类别ID，用于查询属于该类别下的所有终端信息
  	 */
  	protected Integer categoryId;

    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
  	
    public MonitorProcQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTerminalId(){
        return terminalId;
    }

    public String getTerminalIdLike(){
	    if (terminalIdLike != null && terminalIdLike.trim().length() > 0) {
		if (!terminalIdLike.startsWith("%")) {
		    terminalIdLike = "%" + terminalIdLike;
		}
		if (!terminalIdLike.endsWith("%")) {
		   terminalIdLike = terminalIdLike + "%";
		}
	    }
	return terminalIdLike;
    }

    public List<String> getTerminalIds(){
	return terminalIds;
    }


    public String getLevel(){
        return level;
    }

    public String getLevelLike(){
	    if (levelLike != null && levelLike.trim().length() > 0) {
		if (!levelLike.startsWith("%")) {
		    levelLike = "%" + levelLike;
		}
		if (!levelLike.endsWith("%")) {
		   levelLike = levelLike + "%";
		}
	    }
	return levelLike;
    }

    public List<String> getLevels(){
	return levels;
    }


    public String getProcessName(){
        return processName;
    }

    public String getProcessNameLike(){
	    if (processNameLike != null && processNameLike.trim().length() > 0) {
		if (!processNameLike.startsWith("%")) {
		    processNameLike = "%" + processNameLike;
		}
		if (!processNameLike.endsWith("%")) {
		   processNameLike = processNameLike + "%";
		}
	    }
	return processNameLike;
    }

    public List<String> getProcessNames(){
	return processNames;
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


    public String getProd(){
        return prod;
    }

    public String getProdLike(){
	    if (prodLike != null && prodLike.trim().length() > 0) {
		if (!prodLike.startsWith("%")) {
		    prodLike = "%" + prodLike;
		}
		if (!prodLike.endsWith("%")) {
		   prodLike = prodLike + "%";
		}
	    }
	return prodLike;
    }

    public List<String> getProds(){
	return prods;
    }


    public String getVer(){
        return ver;
    }

    public String getVerLike(){
	    if (verLike != null && verLike.trim().length() > 0) {
		if (!verLike.startsWith("%")) {
		    verLike = "%" + verLike;
		}
		if (!verLike.endsWith("%")) {
		   verLike = verLike + "%";
		}
	    }
	return verLike;
    }

    public List<String> getVers(){
	return vers;
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


    public Integer getPort(){
        return port;
    }

    public Integer getPortGreaterThanOrEqual(){
        return portGreaterThanOrEqual;
    }

    public Integer getPortLessThanOrEqual(){
	return portLessThanOrEqual;
    }

    public List<Integer> getPorts(){
	return ports;
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


    public String getStartAddress(){
        return startAddress;
    }

    public String getStartAddressLike(){
	    if (startAddressLike != null && startAddressLike.trim().length() > 0) {
		if (!startAddressLike.startsWith("%")) {
		    startAddressLike = "%" + startAddressLike;
		}
		if (!startAddressLike.endsWith("%")) {
		   startAddressLike = startAddressLike + "%";
		}
	    }
	return startAddressLike;
    }

    public List<String> getStartAddresss(){
	return startAddresss;
    }


    public String getStopAddress(){
        return stopAddress;
    }

    public String getStopAddressLike(){
	    if (stopAddressLike != null && stopAddressLike.trim().length() > 0) {
		if (!stopAddressLike.startsWith("%")) {
		    stopAddressLike = "%" + stopAddressLike;
		}
		if (!stopAddressLike.endsWith("%")) {
		   stopAddressLike = stopAddressLike + "%";
		}
	    }
	return stopAddressLike;
    }

    public List<String> getStopAddresss(){
	return stopAddresss;
    }


    public String getTerminateAddress(){
        return terminateAddress;
    }

    public String getTerminateAddressLike(){
	    if (terminateAddressLike != null && terminateAddressLike.trim().length() > 0) {
		if (!terminateAddressLike.startsWith("%")) {
		    terminateAddressLike = "%" + terminateAddressLike;
		}
		if (!terminateAddressLike.endsWith("%")) {
		   terminateAddressLike = terminateAddressLike + "%";
		}
	    }
	return terminateAddressLike;
    }

    public List<String> getTerminateAddresss(){
	return terminateAddresss;
    }


    public Integer getStatus(){
        return status;
    }

    public Integer getStatusGreaterThanOrEqual(){
        return statusGreaterThanOrEqual;
    }

    public Integer getStatusLessThanOrEqual(){
	return statusLessThanOrEqual;
    }

    public List<Integer> getStatuss(){
	return statuss;
    }

    public String getParentProcId(){
        return parentProcId;
    }

    public String getParentProcIdLike(){
	    if (parentProcIdLike != null && parentProcIdLike.trim().length() > 0) {
		if (!parentProcIdLike.startsWith("%")) {
		    parentProcIdLike = "%" + parentProcIdLike;
		}
		if (!parentProcIdLike.endsWith("%")) {
		   parentProcIdLike = parentProcIdLike + "%";
		}
	    }
	return parentProcIdLike;
    }

    public List<String> getParentProcIds(){
	return parentProcIds;
    }


    public String getOtherItems(){
        return otherItems;
    }

    public String getOtherItemsLike(){
	    if (otherItemsLike != null && otherItemsLike.trim().length() > 0) {
		if (!otherItemsLike.startsWith("%")) {
		    otherItemsLike = "%" + otherItemsLike;
		}
		if (!otherItemsLike.endsWith("%")) {
		   otherItemsLike = otherItemsLike + "%";
		}
	    }
	return otherItemsLike;
    }

    public List<String> getOtherItemss(){
	return otherItemss;
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

 

    public void setTerminalId(String terminalId){
        this.terminalId = terminalId;
    }

    public void setTerminalIdLike( String terminalIdLike){
	this.terminalIdLike = terminalIdLike;
    }

    public void setTerminalIds(List<String> terminalIds){
        this.terminalIds = terminalIds;
    }


    public void setLevel(String level){
        this.level = level;
    }

    public void setLevelLike( String levelLike){
	this.levelLike = levelLike;
    }

    public void setLevels(List<String> levels){
        this.levels = levels;
    }


    public void setProcessName(String processName){
        this.processName = processName;
    }

    public void setProcessNameLike( String processNameLike){
	this.processNameLike = processNameLike;
    }

    public void setProcessNames(List<String> processNames){
        this.processNames = processNames;
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


    public void setProd(String prod){
        this.prod = prod;
    }

    public void setProdLike( String prodLike){
	this.prodLike = prodLike;
    }

    public void setProds(List<String> prods){
        this.prods = prods;
    }


    public void setVer(String ver){
        this.ver = ver;
    }

    public void setVerLike( String verLike){
	this.verLike = verLike;
    }

    public void setVers(List<String> vers){
        this.vers = vers;
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


    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setDescLike( String descLike){
	this.descLike = descLike;
    }

    public void setDescs(List<String> descs){
        this.descs = descs;
    }


    public void setPort(Integer port){
        this.port = port;
    }

    public void setPortGreaterThanOrEqual(Integer portGreaterThanOrEqual){
        this.portGreaterThanOrEqual = portGreaterThanOrEqual;
    }

    public void setPortLessThanOrEqual(Integer portLessThanOrEqual){
	this.portLessThanOrEqual = portLessThanOrEqual;
    }

    public void setPorts(List<Integer> ports){
        this.ports = ports;
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


    public void setStartAddress(String startAddress){
        this.startAddress = startAddress;
    }

    public void setStartAddressLike( String startAddressLike){
	this.startAddressLike = startAddressLike;
    }

    public void setStartAddresss(List<String> startAddresss){
        this.startAddresss = startAddresss;
    }


    public void setStopAddress(String stopAddress){
        this.stopAddress = stopAddress;
    }

    public void setStopAddressLike( String stopAddressLike){
	this.stopAddressLike = stopAddressLike;
    }

    public void setStopAddresss(List<String> stopAddresss){
        this.stopAddresss = stopAddresss;
    }


    public void setTerminateAddress(String terminateAddress){
        this.terminateAddress = terminateAddress;
    }

    public void setTerminateAddressLike( String terminateAddressLike){
	this.terminateAddressLike = terminateAddressLike;
    }

    public void setTerminateAddresss(List<String> terminateAddresss){
        this.terminateAddresss = terminateAddresss;
    }


    public void setStatus(Integer status){
        this.status = status;
    }

    public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
        this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
    }

    public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual){
	this.statusLessThanOrEqual = statusLessThanOrEqual;
    }

    public void setStatuss(List<Integer> statuss){
        this.statuss = statuss;
    }


    public void setParentProcId(String parentProcId){
        this.parentProcId = parentProcId;
    }

    public void setParentProcIdLike( String parentProcIdLike){
	this.parentProcIdLike = parentProcIdLike;
    }

    public void setParentProcIds(List<String> parentProcIds){
        this.parentProcIds = parentProcIds;
    }


    public void setOtherItems(String otherItems){
        this.otherItems = otherItems;
    }

    public void setOtherItemsLike( String otherItemsLike){
	this.otherItemsLike = otherItemsLike;
    }

    public void setOtherItemss(List<String> otherItemss){
        this.otherItemss = otherItemss;
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




    public MonitorProcQuery terminalId(String terminalId){
	if (terminalId == null) {
	    throw new RuntimeException("terminalId is null");
        }         
	this.terminalId = terminalId;
	return this;
    }

    public MonitorProcQuery terminalIdLike( String terminalIdLike){
        if (terminalIdLike == null) {
            throw new RuntimeException("terminalId is null");
        }
        this.terminalIdLike = terminalIdLike;
        return this;
    }

    public MonitorProcQuery terminalIds(List<String> terminalIds){
        if (terminalIds == null) {
            throw new RuntimeException("terminalIds is empty ");
        }
        this.terminalIds = terminalIds;
        return this;
    }


    public MonitorProcQuery level(String level){
	if (level == null) {
	    throw new RuntimeException("level is null");
        }         
	this.level = level;
	return this;
    }

    public MonitorProcQuery levelLike( String levelLike){
        if (levelLike == null) {
            throw new RuntimeException("level is null");
        }
        this.levelLike = levelLike;
        return this;
    }

    public MonitorProcQuery levels(List<String> levels){
        if (levels == null) {
            throw new RuntimeException("levels is empty ");
        }
        this.levels = levels;
        return this;
    }


    public MonitorProcQuery processName(String processName){
	if (processName == null) {
	    throw new RuntimeException("processName is null");
        }         
	this.processName = processName;
	return this;
    }

    public MonitorProcQuery processNameLike( String processNameLike){
        if (processNameLike == null) {
            throw new RuntimeException("processName is null");
        }
        this.processNameLike = processNameLike;
        return this;
    }

    public MonitorProcQuery processNames(List<String> processNames){
        if (processNames == null) {
            throw new RuntimeException("processNames is empty ");
        }
        this.processNames = processNames;
        return this;
    }


    public MonitorProcQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public MonitorProcQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public MonitorProcQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public MonitorProcQuery prod(String prod){
	if (prod == null) {
	    throw new RuntimeException("prod is null");
        }         
	this.prod = prod;
	return this;
    }

    public MonitorProcQuery prodLike( String prodLike){
        if (prodLike == null) {
            throw new RuntimeException("prod is null");
        }
        this.prodLike = prodLike;
        return this;
    }

    public MonitorProcQuery prods(List<String> prods){
        if (prods == null) {
            throw new RuntimeException("prods is empty ");
        }
        this.prods = prods;
        return this;
    }


    public MonitorProcQuery ver(String ver){
	if (ver == null) {
	    throw new RuntimeException("ver is null");
        }         
	this.ver = ver;
	return this;
    }

    public MonitorProcQuery verLike( String verLike){
        if (verLike == null) {
            throw new RuntimeException("ver is null");
        }
        this.verLike = verLike;
        return this;
    }

    public MonitorProcQuery vers(List<String> vers){
        if (vers == null) {
            throw new RuntimeException("vers is empty ");
        }
        this.vers = vers;
        return this;
    }


    public MonitorProcQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public MonitorProcQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public MonitorProcQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public MonitorProcQuery desc(String desc){
	if (desc == null) {
	    throw new RuntimeException("desc is null");
        }         
	this.desc = desc;
	return this;
    }

    public MonitorProcQuery descLike( String descLike){
        if (descLike == null) {
            throw new RuntimeException("desc is null");
        }
        this.descLike = descLike;
        return this;
    }

    public MonitorProcQuery descs(List<String> descs){
        if (descs == null) {
            throw new RuntimeException("descs is empty ");
        }
        this.descs = descs;
        return this;
    }


    public MonitorProcQuery port(Integer port){
	if (port == null) {
            throw new RuntimeException("port is null");
        }         
	this.port = port;
	return this;
    }

    public MonitorProcQuery portGreaterThanOrEqual(Integer portGreaterThanOrEqual){
	if (portGreaterThanOrEqual == null) {
	    throw new RuntimeException("port is null");
        }         
	this.portGreaterThanOrEqual = portGreaterThanOrEqual;
        return this;
    }

    public MonitorProcQuery portLessThanOrEqual(Integer portLessThanOrEqual){
        if (portLessThanOrEqual == null) {
            throw new RuntimeException("port is null");
        }
        this.portLessThanOrEqual = portLessThanOrEqual;
        return this;
    }

    public MonitorProcQuery ports(List<Integer> ports){
        if (ports == null) {
            throw new RuntimeException("ports is empty ");
        }
        this.ports = ports;
        return this;
    }


    public MonitorProcQuery monitorServiceAddress(String monitorServiceAddress){
	if (monitorServiceAddress == null) {
	    throw new RuntimeException("monitorServiceAddress is null");
        }         
	this.monitorServiceAddress = monitorServiceAddress;
	return this;
    }

    public MonitorProcQuery monitorServiceAddressLike( String monitorServiceAddressLike){
        if (monitorServiceAddressLike == null) {
            throw new RuntimeException("monitorServiceAddress is null");
        }
        this.monitorServiceAddressLike = monitorServiceAddressLike;
        return this;
    }

    public MonitorProcQuery monitorServiceAddresss(List<String> monitorServiceAddresss){
        if (monitorServiceAddresss == null) {
            throw new RuntimeException("monitorServiceAddresss is empty ");
        }
        this.monitorServiceAddresss = monitorServiceAddresss;
        return this;
    }


    public MonitorProcQuery startAddress(String startAddress){
	if (startAddress == null) {
	    throw new RuntimeException("startAddress is null");
        }         
	this.startAddress = startAddress;
	return this;
    }

    public MonitorProcQuery startAddressLike( String startAddressLike){
        if (startAddressLike == null) {
            throw new RuntimeException("startAddress is null");
        }
        this.startAddressLike = startAddressLike;
        return this;
    }

    public MonitorProcQuery startAddresss(List<String> startAddresss){
        if (startAddresss == null) {
            throw new RuntimeException("startAddresss is empty ");
        }
        this.startAddresss = startAddresss;
        return this;
    }


    public MonitorProcQuery stopAddress(String stopAddress){
	if (stopAddress == null) {
	    throw new RuntimeException("stopAddress is null");
        }         
	this.stopAddress = stopAddress;
	return this;
    }

    public MonitorProcQuery stopAddressLike( String stopAddressLike){
        if (stopAddressLike == null) {
            throw new RuntimeException("stopAddress is null");
        }
        this.stopAddressLike = stopAddressLike;
        return this;
    }

    public MonitorProcQuery stopAddresss(List<String> stopAddresss){
        if (stopAddresss == null) {
            throw new RuntimeException("stopAddresss is empty ");
        }
        this.stopAddresss = stopAddresss;
        return this;
    }


    public MonitorProcQuery terminateAddress(String terminateAddress){
	if (terminateAddress == null) {
	    throw new RuntimeException("terminateAddress is null");
        }         
	this.terminateAddress = terminateAddress;
	return this;
    }

    public MonitorProcQuery terminateAddressLike( String terminateAddressLike){
        if (terminateAddressLike == null) {
            throw new RuntimeException("terminateAddress is null");
        }
        this.terminateAddressLike = terminateAddressLike;
        return this;
    }

    public MonitorProcQuery terminateAddresss(List<String> terminateAddresss){
        if (terminateAddresss == null) {
            throw new RuntimeException("terminateAddresss is empty ");
        }
        this.terminateAddresss = terminateAddresss;
        return this;
    }


    public MonitorProcQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public MonitorProcQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public MonitorProcQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public MonitorProcQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public MonitorProcQuery parentProcId(String parentProcId){
	if (parentProcId == null) {
	    throw new RuntimeException("parentProcId is null");
        }         
	this.parentProcId = parentProcId;
	return this;
    }

    public MonitorProcQuery parentProcIdLike( String parentProcIdLike){
        if (parentProcIdLike == null) {
            throw new RuntimeException("parentProcId is null");
        }
        this.parentProcIdLike = parentProcIdLike;
        return this;
    }

    public MonitorProcQuery parentProcIds(List<String> parentProcIds){
        if (parentProcIds == null) {
            throw new RuntimeException("parentProcIds is empty ");
        }
        this.parentProcIds = parentProcIds;
        return this;
    }


    public MonitorProcQuery otherItems(String otherItems){
	if (otherItems == null) {
	    throw new RuntimeException("otherItems is null");
        }         
	this.otherItems = otherItems;
	return this;
    }

    public MonitorProcQuery otherItemsLike( String otherItemsLike){
        if (otherItemsLike == null) {
            throw new RuntimeException("otherItems is null");
        }
        this.otherItemsLike = otherItemsLike;
        return this;
    }

    public MonitorProcQuery otherItemss(List<String> otherItemss){
        if (otherItemss == null) {
            throw new RuntimeException("otherItemss is empty ");
        }
        this.otherItemss = otherItemss;
        return this;
    }


    public MonitorProcQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorProcQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorProcQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorProcQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }



    public MonitorProcQuery updateby(String updateby){
	if (updateby == null) {
	    throw new RuntimeException("updateby is null");
        }         
	this.updateby = updateby;
	return this;
    }

    public MonitorProcQuery updatebyLike( String updatebyLike){
        if (updatebyLike == null) {
            throw new RuntimeException("updateby is null");
        }
        this.updatebyLike = updatebyLike;
        return this;
    }

    public MonitorProcQuery updatebys(List<String> updatebys){
        if (updatebys == null) {
            throw new RuntimeException("updatebys is empty ");
        }
        this.updatebys = updatebys;
        return this;
    }



    public MonitorProcQuery updatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
	if (updatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updatetime is null");
        }         
	this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcQuery updatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
        if (updatetimeLessThanOrEqual == null) {
            throw new RuntimeException("updatetime is null");
        }
        this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
        return this;
    }



    public MonitorProcQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public MonitorProcQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public MonitorProcQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public MonitorProcQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("terminalId".equals(sortColumn)) {
                orderBy = "E.TERMINAL_ID_" + a_x;
            } 

            if ("level".equals(sortColumn)) {
                orderBy = "E.LEVEL_" + a_x;
            } 

            if ("processName".equals(sortColumn)) {
                orderBy = "E.PROCESS_NAME_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("prod".equals(sortColumn)) {
                orderBy = "E.PROD_" + a_x;
            } 

            if ("ver".equals(sortColumn)) {
                orderBy = "E.VER_" + a_x;
            } 

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("desc".equals(sortColumn)) {
                orderBy = "E.DESC_" + a_x;
            } 

            if ("port".equals(sortColumn)) {
                orderBy = "E.PORT_" + a_x;
            } 

            if ("monitorServiceAddress".equals(sortColumn)) {
                orderBy = "E.MONITOR_SERVICE_ADDRESS_" + a_x;
            } 

            if ("startAddress".equals(sortColumn)) {
                orderBy = "E.START_ADDRESS_" + a_x;
            } 

            if ("stopAddress".equals(sortColumn)) {
                orderBy = "E.STOP_ADDRESS_" + a_x;
            } 

            if ("terminateAddress".equals(sortColumn)) {
                orderBy = "E.TERMINATE_ADDRESS_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("parentProcId".equals(sortColumn)) {
                orderBy = "E.PARENT_PROC_ID_" + a_x;
            } 

            if ("otherItems".equals(sortColumn)) {
                orderBy = "E.OTHER_ITEMS_" + a_x;
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
        addColumn("terminalId", "TERMINAL_ID_");
        addColumn("level", "LEVEL_");
        addColumn("processName", "PROCESS_NAME_");
        addColumn("name", "NAME_");
        addColumn("prod", "PROD_");
        addColumn("ver", "VER_");
        addColumn("type", "TYPE_");
        addColumn("desc", "DESC_");
        addColumn("port", "PORT_");
        addColumn("monitorServiceAddress", "MONITOR_SERVICE_ADDRESS_");
        addColumn("startAddress", "START_ADDRESS_");
        addColumn("stopAddress", "STOP_ADDRESS_");
        addColumn("terminateAddress", "TERMINATE_ADDRESS_");
        addColumn("status", "STATUS_");
        addColumn("parentProcId", "PARENT_PROC_ID_");
        addColumn("otherItems", "OTHER_ITEMS_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
        addColumn("updateby", "UPDATEBY_");
        addColumn("updatetime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}