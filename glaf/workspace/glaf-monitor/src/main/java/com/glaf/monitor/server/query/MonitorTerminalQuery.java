package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorTerminalQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
  	protected String desc;
  	protected String descLike;
  	protected List<String> descs;
  	protected String level;
  	protected String levelLike;
  	protected List<String> levels;
  	protected String prod;
  	protected String prodLike;
  	protected List<String> prods;
  	protected String domain;
  	protected String domainLike;
  	protected List<String> domains;
  	protected String address;
  	protected String addressLike;
  	protected List<String> addresss;
  	protected String monitorServiceAddress;
  	protected String monitorServiceAddressLike;
  	protected List<String> monitorServiceAddresss;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected String platform;
  	protected String platformLike;
  	protected List<String> platforms;
  	protected String osName;
  	protected String osNameLike;
  	protected List<String> osNames;
  	protected String osFac;
  	protected String osFacLike;
  	protected List<String> osFacs;
  	protected String osVer;
  	protected String osVerLike;
  	protected List<String> osVers;
  	protected String cpuFac;
  	protected String cpuFacLike;
  	protected List<String> cpuFacs;
  	protected Integer cpuCores;
  	protected Integer cpuCoresGreaterThanOrEqual;
  	protected Integer cpuCoresLessThanOrEqual;
  	protected List<Integer> cpuCoress;
  	protected Integer coreMhz;
  	protected Integer coreMhzGreaterThanOrEqual;
  	protected Integer coreMhzLessThanOrEqual;
  	protected List<Integer> coreMhzs;
  	protected String memType;
  	protected String memTypeLike;
  	protected List<String> memTypes;
  	protected Integer memSize;
  	protected Integer memSizeGreaterThanOrEqual;
  	protected Integer memSizeLessThanOrEqual;
  	protected List<Integer> memSizes;
  	protected String diskType;
  	protected String diskTypeLike;
  	protected List<String> diskTypes;
  	protected Integer diskSize;
  	protected Integer diskSizeGreaterThanOrEqual;
  	protected Integer diskSizeLessThanOrEqual;
  	protected List<Integer> diskSizes;
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

	public MonitorTerminalQuery() {

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


    public String getDomain(){
        return domain;
    }

    public String getDomainLike(){
	    if (domainLike != null && domainLike.trim().length() > 0) {
		if (!domainLike.startsWith("%")) {
		    domainLike = "%" + domainLike;
		}
		if (!domainLike.endsWith("%")) {
		   domainLike = domainLike + "%";
		}
	    }
	return domainLike;
    }

    public List<String> getDomains(){
	return domains;
    }


    public String getAddress(){
        return address;
    }

    public String getAddressLike(){
	    if (addressLike != null && addressLike.trim().length() > 0) {
		if (!addressLike.startsWith("%")) {
		    addressLike = "%" + addressLike;
		}
		if (!addressLike.endsWith("%")) {
		   addressLike = addressLike + "%";
		}
	    }
	return addressLike;
    }

    public List<String> getAddresss(){
	return addresss;
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

    public String getPlatform(){
        return platform;
    }

    public String getPlatformLike(){
	    if (platformLike != null && platformLike.trim().length() > 0) {
		if (!platformLike.startsWith("%")) {
		    platformLike = "%" + platformLike;
		}
		if (!platformLike.endsWith("%")) {
		   platformLike = platformLike + "%";
		}
	    }
	return platformLike;
    }

    public List<String> getPlatforms(){
	return platforms;
    }


    public String getOsName(){
        return osName;
    }

    public String getOsNameLike(){
	    if (osNameLike != null && osNameLike.trim().length() > 0) {
		if (!osNameLike.startsWith("%")) {
		    osNameLike = "%" + osNameLike;
		}
		if (!osNameLike.endsWith("%")) {
		   osNameLike = osNameLike + "%";
		}
	    }
	return osNameLike;
    }

    public List<String> getOsNames(){
	return osNames;
    }


    public String getOsFac(){
        return osFac;
    }

    public String getOsFacLike(){
	    if (osFacLike != null && osFacLike.trim().length() > 0) {
		if (!osFacLike.startsWith("%")) {
		    osFacLike = "%" + osFacLike;
		}
		if (!osFacLike.endsWith("%")) {
		   osFacLike = osFacLike + "%";
		}
	    }
	return osFacLike;
    }

    public List<String> getOsFacs(){
	return osFacs;
    }


    public String getOsVer(){
        return osVer;
    }

    public String getOsVerLike(){
	    if (osVerLike != null && osVerLike.trim().length() > 0) {
		if (!osVerLike.startsWith("%")) {
		    osVerLike = "%" + osVerLike;
		}
		if (!osVerLike.endsWith("%")) {
		   osVerLike = osVerLike + "%";
		}
	    }
	return osVerLike;
    }

    public List<String> getOsVers(){
	return osVers;
    }


    public String getCpuFac(){
        return cpuFac;
    }

    public String getCpuFacLike(){
	    if (cpuFacLike != null && cpuFacLike.trim().length() > 0) {
		if (!cpuFacLike.startsWith("%")) {
		    cpuFacLike = "%" + cpuFacLike;
		}
		if (!cpuFacLike.endsWith("%")) {
		   cpuFacLike = cpuFacLike + "%";
		}
	    }
	return cpuFacLike;
    }

    public List<String> getCpuFacs(){
	return cpuFacs;
    }


    public Integer getCpuCores(){
        return cpuCores;
    }

    public Integer getCpuCoresGreaterThanOrEqual(){
        return cpuCoresGreaterThanOrEqual;
    }

    public Integer getCpuCoresLessThanOrEqual(){
	return cpuCoresLessThanOrEqual;
    }

    public List<Integer> getCpuCoress(){
	return cpuCoress;
    }

    public Integer getCoreMhz(){
        return coreMhz;
    }

    public Integer getCoreMhzGreaterThanOrEqual(){
        return coreMhzGreaterThanOrEqual;
    }

    public Integer getCoreMhzLessThanOrEqual(){
	return coreMhzLessThanOrEqual;
    }

    public List<Integer> getCoreMhzs(){
	return coreMhzs;
    }

    public String getMemType(){
        return memType;
    }

    public String getMemTypeLike(){
	    if (memTypeLike != null && memTypeLike.trim().length() > 0) {
		if (!memTypeLike.startsWith("%")) {
		    memTypeLike = "%" + memTypeLike;
		}
		if (!memTypeLike.endsWith("%")) {
		   memTypeLike = memTypeLike + "%";
		}
	    }
	return memTypeLike;
    }

    public List<String> getMemTypes(){
	return memTypes;
    }


    public Integer getMemSize(){
        return memSize;
    }

    public Integer getMemSizeGreaterThanOrEqual(){
        return memSizeGreaterThanOrEqual;
    }

    public Integer getMemSizeLessThanOrEqual(){
	return memSizeLessThanOrEqual;
    }

    public List<Integer> getMemSizes(){
	return memSizes;
    }

    public String getDiskType(){
        return diskType;
    }

    public String getDiskTypeLike(){
	    if (diskTypeLike != null && diskTypeLike.trim().length() > 0) {
		if (!diskTypeLike.startsWith("%")) {
		    diskTypeLike = "%" + diskTypeLike;
		}
		if (!diskTypeLike.endsWith("%")) {
		   diskTypeLike = diskTypeLike + "%";
		}
	    }
	return diskTypeLike;
    }

    public List<String> getDiskTypes(){
	return diskTypes;
    }


    public Integer getDiskSize(){
        return diskSize;
    }

    public Integer getDiskSizeGreaterThanOrEqual(){
        return diskSizeGreaterThanOrEqual;
    }

    public Integer getDiskSizeLessThanOrEqual(){
	return diskSizeLessThanOrEqual;
    }

    public List<Integer> getDiskSizes(){
	return diskSizes;
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

 

    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
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


    public void setLevel(String level){
        this.level = level;
    }

    public void setLevelLike( String levelLike){
	this.levelLike = levelLike;
    }

    public void setLevels(List<String> levels){
        this.levels = levels;
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


    public void setDomain(String domain){
        this.domain = domain;
    }

    public void setDomainLike( String domainLike){
	this.domainLike = domainLike;
    }

    public void setDomains(List<String> domains){
        this.domains = domains;
    }


    public void setAddress(String address){
        this.address = address;
    }

    public void setAddressLike( String addressLike){
	this.addressLike = addressLike;
    }

    public void setAddresss(List<String> addresss){
        this.addresss = addresss;
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


    public void setPlatform(String platform){
        this.platform = platform;
    }

    public void setPlatformLike( String platformLike){
	this.platformLike = platformLike;
    }

    public void setPlatforms(List<String> platforms){
        this.platforms = platforms;
    }


    public void setOsName(String osName){
        this.osName = osName;
    }

    public void setOsNameLike( String osNameLike){
	this.osNameLike = osNameLike;
    }

    public void setOsNames(List<String> osNames){
        this.osNames = osNames;
    }


    public void setOsFac(String osFac){
        this.osFac = osFac;
    }

    public void setOsFacLike( String osFacLike){
	this.osFacLike = osFacLike;
    }

    public void setOsFacs(List<String> osFacs){
        this.osFacs = osFacs;
    }


    public void setOsVer(String osVer){
        this.osVer = osVer;
    }

    public void setOsVerLike( String osVerLike){
	this.osVerLike = osVerLike;
    }

    public void setOsVers(List<String> osVers){
        this.osVers = osVers;
    }


    public void setCpuFac(String cpuFac){
        this.cpuFac = cpuFac;
    }

    public void setCpuFacLike( String cpuFacLike){
	this.cpuFacLike = cpuFacLike;
    }

    public void setCpuFacs(List<String> cpuFacs){
        this.cpuFacs = cpuFacs;
    }


    public void setCpuCores(Integer cpuCores){
        this.cpuCores = cpuCores;
    }

    public void setCpuCoresGreaterThanOrEqual(Integer cpuCoresGreaterThanOrEqual){
        this.cpuCoresGreaterThanOrEqual = cpuCoresGreaterThanOrEqual;
    }

    public void setCpuCoresLessThanOrEqual(Integer cpuCoresLessThanOrEqual){
	this.cpuCoresLessThanOrEqual = cpuCoresLessThanOrEqual;
    }

    public void setCpuCoress(List<Integer> cpuCoress){
        this.cpuCoress = cpuCoress;
    }


    public void setCoreMhz(Integer coreMhz){
        this.coreMhz = coreMhz;
    }

    public void setCoreMhzGreaterThanOrEqual(Integer coreMhzGreaterThanOrEqual){
        this.coreMhzGreaterThanOrEqual = coreMhzGreaterThanOrEqual;
    }

    public void setCoreMhzLessThanOrEqual(Integer coreMhzLessThanOrEqual){
	this.coreMhzLessThanOrEqual = coreMhzLessThanOrEqual;
    }

    public void setCoreMhzs(List<Integer> coreMhzs){
        this.coreMhzs = coreMhzs;
    }


    public void setMemType(String memType){
        this.memType = memType;
    }

    public void setMemTypeLike( String memTypeLike){
	this.memTypeLike = memTypeLike;
    }

    public void setMemTypes(List<String> memTypes){
        this.memTypes = memTypes;
    }


    public void setMemSize(Integer memSize){
        this.memSize = memSize;
    }

    public void setMemSizeGreaterThanOrEqual(Integer memSizeGreaterThanOrEqual){
        this.memSizeGreaterThanOrEqual = memSizeGreaterThanOrEqual;
    }

    public void setMemSizeLessThanOrEqual(Integer memSizeLessThanOrEqual){
	this.memSizeLessThanOrEqual = memSizeLessThanOrEqual;
    }

    public void setMemSizes(List<Integer> memSizes){
        this.memSizes = memSizes;
    }


    public void setDiskType(String diskType){
        this.diskType = diskType;
    }

    public void setDiskTypeLike( String diskTypeLike){
	this.diskTypeLike = diskTypeLike;
    }

    public void setDiskTypes(List<String> diskTypes){
        this.diskTypes = diskTypes;
    }


    public void setDiskSize(Integer diskSize){
        this.diskSize = diskSize;
    }

    public void setDiskSizeGreaterThanOrEqual(Integer diskSizeGreaterThanOrEqual){
        this.diskSizeGreaterThanOrEqual = diskSizeGreaterThanOrEqual;
    }

    public void setDiskSizeLessThanOrEqual(Integer diskSizeLessThanOrEqual){
	this.diskSizeLessThanOrEqual = diskSizeLessThanOrEqual;
    }

    public void setDiskSizes(List<Integer> diskSizes){
        this.diskSizes = diskSizes;
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




    public MonitorTerminalQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public MonitorTerminalQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public MonitorTerminalQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public MonitorTerminalQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public MonitorTerminalQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public MonitorTerminalQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public MonitorTerminalQuery desc(String desc){
	if (desc == null) {
	    throw new RuntimeException("desc is null");
        }         
	this.desc = desc;
	return this;
    }

    public MonitorTerminalQuery descLike( String descLike){
        if (descLike == null) {
            throw new RuntimeException("desc is null");
        }
        this.descLike = descLike;
        return this;
    }

    public MonitorTerminalQuery descs(List<String> descs){
        if (descs == null) {
            throw new RuntimeException("descs is empty ");
        }
        this.descs = descs;
        return this;
    }


    public MonitorTerminalQuery level(String level){
	if (level == null) {
	    throw new RuntimeException("level is null");
        }         
	this.level = level;
	return this;
    }

    public MonitorTerminalQuery levelLike( String levelLike){
        if (levelLike == null) {
            throw new RuntimeException("level is null");
        }
        this.levelLike = levelLike;
        return this;
    }

    public MonitorTerminalQuery levels(List<String> levels){
        if (levels == null) {
            throw new RuntimeException("levels is empty ");
        }
        this.levels = levels;
        return this;
    }


    public MonitorTerminalQuery prod(String prod){
	if (prod == null) {
	    throw new RuntimeException("prod is null");
        }         
	this.prod = prod;
	return this;
    }

    public MonitorTerminalQuery prodLike( String prodLike){
        if (prodLike == null) {
            throw new RuntimeException("prod is null");
        }
        this.prodLike = prodLike;
        return this;
    }

    public MonitorTerminalQuery prods(List<String> prods){
        if (prods == null) {
            throw new RuntimeException("prods is empty ");
        }
        this.prods = prods;
        return this;
    }


    public MonitorTerminalQuery domain(String domain){
	if (domain == null) {
	    throw new RuntimeException("domain is null");
        }         
	this.domain = domain;
	return this;
    }

    public MonitorTerminalQuery domainLike( String domainLike){
        if (domainLike == null) {
            throw new RuntimeException("domain is null");
        }
        this.domainLike = domainLike;
        return this;
    }

    public MonitorTerminalQuery domains(List<String> domains){
        if (domains == null) {
            throw new RuntimeException("domains is empty ");
        }
        this.domains = domains;
        return this;
    }


    public MonitorTerminalQuery address(String address){
	if (address == null) {
	    throw new RuntimeException("address is null");
        }         
	this.address = address;
	return this;
    }

    public MonitorTerminalQuery addressLike( String addressLike){
        if (addressLike == null) {
            throw new RuntimeException("address is null");
        }
        this.addressLike = addressLike;
        return this;
    }

    public MonitorTerminalQuery addresss(List<String> addresss){
        if (addresss == null) {
            throw new RuntimeException("addresss is empty ");
        }
        this.addresss = addresss;
        return this;
    }


    public MonitorTerminalQuery monitorServiceAddress(String monitorServiceAddress){
	if (monitorServiceAddress == null) {
	    throw new RuntimeException("monitorServiceAddress is null");
        }         
	this.monitorServiceAddress = monitorServiceAddress;
	return this;
    }

    public MonitorTerminalQuery monitorServiceAddressLike( String monitorServiceAddressLike){
        if (monitorServiceAddressLike == null) {
            throw new RuntimeException("monitorServiceAddress is null");
        }
        this.monitorServiceAddressLike = monitorServiceAddressLike;
        return this;
    }

    public MonitorTerminalQuery monitorServiceAddresss(List<String> monitorServiceAddresss){
        if (monitorServiceAddresss == null) {
            throw new RuntimeException("monitorServiceAddresss is empty ");
        }
        this.monitorServiceAddresss = monitorServiceAddresss;
        return this;
    }


    public MonitorTerminalQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public MonitorTerminalQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public MonitorTerminalQuery platform(String platform){
	if (platform == null) {
	    throw new RuntimeException("platform is null");
        }         
	this.platform = platform;
	return this;
    }

    public MonitorTerminalQuery platformLike( String platformLike){
        if (platformLike == null) {
            throw new RuntimeException("platform is null");
        }
        this.platformLike = platformLike;
        return this;
    }

    public MonitorTerminalQuery platforms(List<String> platforms){
        if (platforms == null) {
            throw new RuntimeException("platforms is empty ");
        }
        this.platforms = platforms;
        return this;
    }


    public MonitorTerminalQuery osName(String osName){
	if (osName == null) {
	    throw new RuntimeException("osName is null");
        }         
	this.osName = osName;
	return this;
    }

    public MonitorTerminalQuery osNameLike( String osNameLike){
        if (osNameLike == null) {
            throw new RuntimeException("osName is null");
        }
        this.osNameLike = osNameLike;
        return this;
    }

    public MonitorTerminalQuery osNames(List<String> osNames){
        if (osNames == null) {
            throw new RuntimeException("osNames is empty ");
        }
        this.osNames = osNames;
        return this;
    }


    public MonitorTerminalQuery osFac(String osFac){
	if (osFac == null) {
	    throw new RuntimeException("osFac is null");
        }         
	this.osFac = osFac;
	return this;
    }

    public MonitorTerminalQuery osFacLike( String osFacLike){
        if (osFacLike == null) {
            throw new RuntimeException("osFac is null");
        }
        this.osFacLike = osFacLike;
        return this;
    }

    public MonitorTerminalQuery osFacs(List<String> osFacs){
        if (osFacs == null) {
            throw new RuntimeException("osFacs is empty ");
        }
        this.osFacs = osFacs;
        return this;
    }


    public MonitorTerminalQuery osVer(String osVer){
	if (osVer == null) {
	    throw new RuntimeException("osVer is null");
        }         
	this.osVer = osVer;
	return this;
    }

    public MonitorTerminalQuery osVerLike( String osVerLike){
        if (osVerLike == null) {
            throw new RuntimeException("osVer is null");
        }
        this.osVerLike = osVerLike;
        return this;
    }

    public MonitorTerminalQuery osVers(List<String> osVers){
        if (osVers == null) {
            throw new RuntimeException("osVers is empty ");
        }
        this.osVers = osVers;
        return this;
    }


    public MonitorTerminalQuery cpuFac(String cpuFac){
	if (cpuFac == null) {
	    throw new RuntimeException("cpuFac is null");
        }         
	this.cpuFac = cpuFac;
	return this;
    }

    public MonitorTerminalQuery cpuFacLike( String cpuFacLike){
        if (cpuFacLike == null) {
            throw new RuntimeException("cpuFac is null");
        }
        this.cpuFacLike = cpuFacLike;
        return this;
    }

    public MonitorTerminalQuery cpuFacs(List<String> cpuFacs){
        if (cpuFacs == null) {
            throw new RuntimeException("cpuFacs is empty ");
        }
        this.cpuFacs = cpuFacs;
        return this;
    }


    public MonitorTerminalQuery cpuCores(Integer cpuCores){
	if (cpuCores == null) {
            throw new RuntimeException("cpuCores is null");
        }         
	this.cpuCores = cpuCores;
	return this;
    }

    public MonitorTerminalQuery cpuCoresGreaterThanOrEqual(Integer cpuCoresGreaterThanOrEqual){
	if (cpuCoresGreaterThanOrEqual == null) {
	    throw new RuntimeException("cpuCores is null");
        }         
	this.cpuCoresGreaterThanOrEqual = cpuCoresGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery cpuCoresLessThanOrEqual(Integer cpuCoresLessThanOrEqual){
        if (cpuCoresLessThanOrEqual == null) {
            throw new RuntimeException("cpuCores is null");
        }
        this.cpuCoresLessThanOrEqual = cpuCoresLessThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery cpuCoress(List<Integer> cpuCoress){
        if (cpuCoress == null) {
            throw new RuntimeException("cpuCoress is empty ");
        }
        this.cpuCoress = cpuCoress;
        return this;
    }


    public MonitorTerminalQuery coreMhz(Integer coreMhz){
	if (coreMhz == null) {
            throw new RuntimeException("coreMhz is null");
        }         
	this.coreMhz = coreMhz;
	return this;
    }

    public MonitorTerminalQuery coreMhzGreaterThanOrEqual(Integer coreMhzGreaterThanOrEqual){
	if (coreMhzGreaterThanOrEqual == null) {
	    throw new RuntimeException("coreMhz is null");
        }         
	this.coreMhzGreaterThanOrEqual = coreMhzGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery coreMhzLessThanOrEqual(Integer coreMhzLessThanOrEqual){
        if (coreMhzLessThanOrEqual == null) {
            throw new RuntimeException("coreMhz is null");
        }
        this.coreMhzLessThanOrEqual = coreMhzLessThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery coreMhzs(List<Integer> coreMhzs){
        if (coreMhzs == null) {
            throw new RuntimeException("coreMhzs is empty ");
        }
        this.coreMhzs = coreMhzs;
        return this;
    }


    public MonitorTerminalQuery memType(String memType){
	if (memType == null) {
	    throw new RuntimeException("memType is null");
        }         
	this.memType = memType;
	return this;
    }

    public MonitorTerminalQuery memTypeLike( String memTypeLike){
        if (memTypeLike == null) {
            throw new RuntimeException("memType is null");
        }
        this.memTypeLike = memTypeLike;
        return this;
    }

    public MonitorTerminalQuery memTypes(List<String> memTypes){
        if (memTypes == null) {
            throw new RuntimeException("memTypes is empty ");
        }
        this.memTypes = memTypes;
        return this;
    }


    public MonitorTerminalQuery memSize(Integer memSize){
	if (memSize == null) {
            throw new RuntimeException("memSize is null");
        }         
	this.memSize = memSize;
	return this;
    }

    public MonitorTerminalQuery memSizeGreaterThanOrEqual(Integer memSizeGreaterThanOrEqual){
	if (memSizeGreaterThanOrEqual == null) {
	    throw new RuntimeException("memSize is null");
        }         
	this.memSizeGreaterThanOrEqual = memSizeGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery memSizeLessThanOrEqual(Integer memSizeLessThanOrEqual){
        if (memSizeLessThanOrEqual == null) {
            throw new RuntimeException("memSize is null");
        }
        this.memSizeLessThanOrEqual = memSizeLessThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery memSizes(List<Integer> memSizes){
        if (memSizes == null) {
            throw new RuntimeException("memSizes is empty ");
        }
        this.memSizes = memSizes;
        return this;
    }


    public MonitorTerminalQuery diskType(String diskType){
	if (diskType == null) {
	    throw new RuntimeException("diskType is null");
        }         
	this.diskType = diskType;
	return this;
    }

    public MonitorTerminalQuery diskTypeLike( String diskTypeLike){
        if (diskTypeLike == null) {
            throw new RuntimeException("diskType is null");
        }
        this.diskTypeLike = diskTypeLike;
        return this;
    }

    public MonitorTerminalQuery diskTypes(List<String> diskTypes){
        if (diskTypes == null) {
            throw new RuntimeException("diskTypes is empty ");
        }
        this.diskTypes = diskTypes;
        return this;
    }


    public MonitorTerminalQuery diskSize(Integer diskSize){
	if (diskSize == null) {
            throw new RuntimeException("diskSize is null");
        }         
	this.diskSize = diskSize;
	return this;
    }

    public MonitorTerminalQuery diskSizeGreaterThanOrEqual(Integer diskSizeGreaterThanOrEqual){
	if (diskSizeGreaterThanOrEqual == null) {
	    throw new RuntimeException("diskSize is null");
        }         
	this.diskSizeGreaterThanOrEqual = diskSizeGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery diskSizeLessThanOrEqual(Integer diskSizeLessThanOrEqual){
        if (diskSizeLessThanOrEqual == null) {
            throw new RuntimeException("diskSize is null");
        }
        this.diskSizeLessThanOrEqual = diskSizeLessThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery diskSizes(List<Integer> diskSizes){
        if (diskSizes == null) {
            throw new RuntimeException("diskSizes is empty ");
        }
        this.diskSizes = diskSizes;
        return this;
    }


    public MonitorTerminalQuery otherItems(String otherItems){
	if (otherItems == null) {
	    throw new RuntimeException("otherItems is null");
        }         
	this.otherItems = otherItems;
	return this;
    }

    public MonitorTerminalQuery otherItemsLike( String otherItemsLike){
        if (otherItemsLike == null) {
            throw new RuntimeException("otherItems is null");
        }
        this.otherItemsLike = otherItemsLike;
        return this;
    }

    public MonitorTerminalQuery otherItemss(List<String> otherItemss){
        if (otherItemss == null) {
            throw new RuntimeException("otherItemss is empty ");
        }
        this.otherItemss = otherItemss;
        return this;
    }


    public MonitorTerminalQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorTerminalQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorTerminalQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorTerminalQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }



    public MonitorTerminalQuery updateby(String updateby){
	if (updateby == null) {
	    throw new RuntimeException("updateby is null");
        }         
	this.updateby = updateby;
	return this;
    }

    public MonitorTerminalQuery updatebyLike( String updatebyLike){
        if (updatebyLike == null) {
            throw new RuntimeException("updateby is null");
        }
        this.updatebyLike = updatebyLike;
        return this;
    }

    public MonitorTerminalQuery updatebys(List<String> updatebys){
        if (updatebys == null) {
            throw new RuntimeException("updatebys is empty ");
        }
        this.updatebys = updatebys;
        return this;
    }



    public MonitorTerminalQuery updatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
	if (updatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updatetime is null");
        }         
	this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery updatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
        if (updatetimeLessThanOrEqual == null) {
            throw new RuntimeException("updatetime is null");
        }
        this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
        return this;
    }



    public MonitorTerminalQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public MonitorTerminalQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public MonitorTerminalQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("desc".equals(sortColumn)) {
                orderBy = "E.DESC_" + a_x;
            } 

            if ("level".equals(sortColumn)) {
                orderBy = "E.LEVEL_" + a_x;
            } 

            if ("prod".equals(sortColumn)) {
                orderBy = "E.PROD_" + a_x;
            } 

            if ("domain".equals(sortColumn)) {
                orderBy = "E.DOMAIN_" + a_x;
            } 

            if ("address".equals(sortColumn)) {
                orderBy = "E.ADDRESS_" + a_x;
            } 

            if ("monitorServiceAddress".equals(sortColumn)) {
                orderBy = "E.MONITOR_SERVICE_ADDRESS_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("platform".equals(sortColumn)) {
                orderBy = "E.PLATFORM_" + a_x;
            } 

            if ("osName".equals(sortColumn)) {
                orderBy = "E.OS_NAME_" + a_x;
            } 

            if ("osFac".equals(sortColumn)) {
                orderBy = "E.OS_FAC_" + a_x;
            } 

            if ("osVer".equals(sortColumn)) {
                orderBy = "E.OS_VER_" + a_x;
            } 

            if ("cpuFac".equals(sortColumn)) {
                orderBy = "E.CPU_FAC_" + a_x;
            } 

            if ("cpuCores".equals(sortColumn)) {
                orderBy = "E.CPU_CORES_" + a_x;
            } 

            if ("coreMhz".equals(sortColumn)) {
                orderBy = "E.CORE_MHZ_" + a_x;
            } 

            if ("memType".equals(sortColumn)) {
                orderBy = "E.MEM_TYPE_" + a_x;
            } 

            if ("memSize".equals(sortColumn)) {
                orderBy = "E.MEM_SIZE_" + a_x;
            } 

            if ("diskType".equals(sortColumn)) {
                orderBy = "E.DISK_TYPE_" + a_x;
            } 

            if ("diskSize".equals(sortColumn)) {
                orderBy = "E.DISK_SIZE_" + a_x;
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
        addColumn("name", "NAME_");
        addColumn("type", "TYPE_");
        addColumn("desc", "DESC_");
        addColumn("level", "LEVEL_");
        addColumn("prod", "PROD_");
        addColumn("domain", "DOMAIN_");
        addColumn("address", "ADDRESS_");
        addColumn("monitorServiceAddress", "MONITOR_SERVICE_ADDRESS_");
        addColumn("status", "STATUS_");
        addColumn("platform", "PLATFORM_");
        addColumn("osName", "OS_NAME_");
        addColumn("osFac", "OS_FAC_");
        addColumn("osVer", "OS_VER_");
        addColumn("cpuFac", "CPU_FAC_");
        addColumn("cpuCores", "CPU_CORES_");
        addColumn("coreMhz", "CORE_MHZ_");
        addColumn("memType", "MEM_TYPE_");
        addColumn("memSize", "MEM_SIZE_");
        addColumn("diskType", "DISK_TYPE_");
        addColumn("diskSize", "DISK_SIZE_");
        addColumn("otherItems", "OTHER_ITEMS_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
        addColumn("updateby", "UPDATEBY_");
        addColumn("updatetime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}