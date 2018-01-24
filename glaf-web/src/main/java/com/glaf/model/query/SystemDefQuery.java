package com.glaf.model.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SystemDefQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> sysIds;
	protected Collection<String> appActorIds;
  	protected String sysName;
  	protected String sysNameLike;
  	protected List<String> sysNames;
  	protected String sysCode;
  	protected String sysCodeLike;
  	protected List<String> sysCodes;
  	protected String sysDesc;
  	protected String sysDescLike;
  	protected List<String> sysDescs;
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
  	protected String version;
  	protected String versionLike;
  	protected List<String> versions;
  	protected String publisher;
  	protected String publisherLike;
  	protected List<String> publishers;
        protected Date publishTimeGreaterThanOrEqual;
  	protected Date publishTimeLessThanOrEqual;

    public SystemDefQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getSysName(){
        return sysName;
    }

    public String getSysNameLike(){
	    if (sysNameLike != null && sysNameLike.trim().length() > 0) {
		if (!sysNameLike.startsWith("%")) {
		    sysNameLike = "%" + sysNameLike;
		}
		if (!sysNameLike.endsWith("%")) {
		   sysNameLike = sysNameLike + "%";
		}
	    }
	return sysNameLike;
    }

    public List<String> getSysNames(){
	return sysNames;
    }


    public String getSysCode(){
        return sysCode;
    }

    public String getSysCodeLike(){
	    if (sysCodeLike != null && sysCodeLike.trim().length() > 0) {
		if (!sysCodeLike.startsWith("%")) {
		    sysCodeLike = "%" + sysCodeLike;
		}
		if (!sysCodeLike.endsWith("%")) {
		   sysCodeLike = sysCodeLike + "%";
		}
	    }
	return sysCodeLike;
    }

    public List<String> getSysCodes(){
	return sysCodes;
    }


    public String getSysDesc(){
        return sysDesc;
    }

    public String getSysDescLike(){
	    if (sysDescLike != null && sysDescLike.trim().length() > 0) {
		if (!sysDescLike.startsWith("%")) {
		    sysDescLike = "%" + sysDescLike;
		}
		if (!sysDescLike.endsWith("%")) {
		   sysDescLike = sysDescLike + "%";
		}
	    }
	return sysDescLike;
    }

    public List<String> getSysDescs(){
	return sysDescs;
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

    public String getVersion(){
        return version;
    }

    public String getVersionLike(){
	    if (versionLike != null && versionLike.trim().length() > 0) {
		if (!versionLike.startsWith("%")) {
		    versionLike = "%" + versionLike;
		}
		if (!versionLike.endsWith("%")) {
		   versionLike = versionLike + "%";
		}
	    }
	return versionLike;
    }

    public List<String> getVersions(){
	return versions;
    }


    public String getPublisher(){
        return publisher;
    }

    public String getPublisherLike(){
	    if (publisherLike != null && publisherLike.trim().length() > 0) {
		if (!publisherLike.startsWith("%")) {
		    publisherLike = "%" + publisherLike;
		}
		if (!publisherLike.endsWith("%")) {
		   publisherLike = publisherLike + "%";
		}
	    }
	return publisherLike;
    }

    public List<String> getPublishers(){
	return publishers;
    }


    public Date getPublishTimeGreaterThanOrEqual(){
        return publishTimeGreaterThanOrEqual;
    }

    public Date getPublishTimeLessThanOrEqual(){
	return publishTimeLessThanOrEqual;
    }


 

    public void setSysName(String sysName){
        this.sysName = sysName;
    }

    public void setSysNameLike( String sysNameLike){
	this.sysNameLike = sysNameLike;
    }

    public void setSysNames(List<String> sysNames){
        this.sysNames = sysNames;
    }


    public void setSysCode(String sysCode){
        this.sysCode = sysCode;
    }

    public void setSysCodeLike( String sysCodeLike){
	this.sysCodeLike = sysCodeLike;
    }

    public void setSysCodes(List<String> sysCodes){
        this.sysCodes = sysCodes;
    }


    public void setSysDesc(String sysDesc){
        this.sysDesc = sysDesc;
    }

    public void setSysDescLike( String sysDescLike){
	this.sysDescLike = sysDescLike;
    }

    public void setSysDescs(List<String> sysDescs){
        this.sysDescs = sysDescs;
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


    public void setVersion(String version){
        this.version = version;
    }

    public void setVersionLike( String versionLike){
	this.versionLike = versionLike;
    }

    public void setVersions(List<String> versions){
        this.versions = versions;
    }


    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public void setPublisherLike( String publisherLike){
	this.publisherLike = publisherLike;
    }

    public void setPublishers(List<String> publishers){
        this.publishers = publishers;
    }


    public void setPublishTimeGreaterThanOrEqual(Date publishTimeGreaterThanOrEqual){
        this.publishTimeGreaterThanOrEqual = publishTimeGreaterThanOrEqual;
    }

    public void setPublishTimeLessThanOrEqual(Date publishTimeLessThanOrEqual){
	this.publishTimeLessThanOrEqual = publishTimeLessThanOrEqual;
    }




    public SystemDefQuery sysName(String sysName){
	if (sysName == null) {
	    throw new RuntimeException("sysName is null");
        }         
	this.sysName = sysName;
	return this;
    }

    public SystemDefQuery sysNameLike( String sysNameLike){
        if (sysNameLike == null) {
            throw new RuntimeException("sysName is null");
        }
        this.sysNameLike = sysNameLike;
        return this;
    }

    public SystemDefQuery sysNames(List<String> sysNames){
        if (sysNames == null) {
            throw new RuntimeException("sysNames is empty ");
        }
        this.sysNames = sysNames;
        return this;
    }


    public SystemDefQuery sysCode(String sysCode){
	if (sysCode == null) {
	    throw new RuntimeException("sysCode is null");
        }         
	this.sysCode = sysCode;
	return this;
    }

    public SystemDefQuery sysCodeLike( String sysCodeLike){
        if (sysCodeLike == null) {
            throw new RuntimeException("sysCode is null");
        }
        this.sysCodeLike = sysCodeLike;
        return this;
    }

    public SystemDefQuery sysCodes(List<String> sysCodes){
        if (sysCodes == null) {
            throw new RuntimeException("sysCodes is empty ");
        }
        this.sysCodes = sysCodes;
        return this;
    }


    public SystemDefQuery sysDesc(String sysDesc){
	if (sysDesc == null) {
	    throw new RuntimeException("sysDesc is null");
        }         
	this.sysDesc = sysDesc;
	return this;
    }

    public SystemDefQuery sysDescLike( String sysDescLike){
        if (sysDescLike == null) {
            throw new RuntimeException("sysDesc is null");
        }
        this.sysDescLike = sysDescLike;
        return this;
    }

    public SystemDefQuery sysDescs(List<String> sysDescs){
        if (sysDescs == null) {
            throw new RuntimeException("sysDescs is empty ");
        }
        this.sysDescs = sysDescs;
        return this;
    }


    public SystemDefQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SystemDefQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SystemDefQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SystemDefQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SystemDefQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SystemDefQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SystemDefQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SystemDefQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SystemDefQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SystemDefQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SystemDefQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SystemDefQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SystemDefQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SystemDefQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }


    public SystemDefQuery version(String version){
	if (version == null) {
	    throw new RuntimeException("version is null");
        }         
	this.version = version;
	return this;
    }

    public SystemDefQuery versionLike( String versionLike){
        if (versionLike == null) {
            throw new RuntimeException("version is null");
        }
        this.versionLike = versionLike;
        return this;
    }

    public SystemDefQuery versions(List<String> versions){
        if (versions == null) {
            throw new RuntimeException("versions is empty ");
        }
        this.versions = versions;
        return this;
    }


    public SystemDefQuery publisher(String publisher){
	if (publisher == null) {
	    throw new RuntimeException("publisher is null");
        }         
	this.publisher = publisher;
	return this;
    }

    public SystemDefQuery publisherLike( String publisherLike){
        if (publisherLike == null) {
            throw new RuntimeException("publisher is null");
        }
        this.publisherLike = publisherLike;
        return this;
    }

    public SystemDefQuery publishers(List<String> publishers){
        if (publishers == null) {
            throw new RuntimeException("publishers is empty ");
        }
        this.publishers = publishers;
        return this;
    }



    public SystemDefQuery publishTimeGreaterThanOrEqual(Date publishTimeGreaterThanOrEqual){
	if (publishTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("publishTime is null");
        }         
	this.publishTimeGreaterThanOrEqual = publishTimeGreaterThanOrEqual;
        return this;
    }

    public SystemDefQuery publishTimeLessThanOrEqual(Date publishTimeLessThanOrEqual){
        if (publishTimeLessThanOrEqual == null) {
            throw new RuntimeException("publishTime is null");
        }
        this.publishTimeLessThanOrEqual = publishTimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("sysName".equals(sortColumn)) {
                orderBy = "E.SYS_NAME_" + a_x;
            } 

            if ("sysCode".equals(sortColumn)) {
                orderBy = "E.SYS_CODE_" + a_x;
            } 

            if ("sysDesc".equals(sortColumn)) {
                orderBy = "E.SYS_DESC_" + a_x;
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

            if ("version".equals(sortColumn)) {
                orderBy = "E.VERSION_" + a_x;
            } 

            if ("publisher".equals(sortColumn)) {
                orderBy = "E.PUBLISHER_" + a_x;
            } 

            if ("publishTime".equals(sortColumn)) {
                orderBy = "E.PUBLISHTIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("sysId", "SYS_ID_");
        addColumn("sysName", "SYS_NAME_");
        addColumn("sysCode", "SYS_CODE_");
        addColumn("sysDesc", "SYS_DESC_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
        addColumn("version", "VERSION_");
        addColumn("publisher", "PUBLISHER_");
        addColumn("publishTime", "PUBLISHTIME_");
    }

}