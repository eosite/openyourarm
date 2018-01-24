package com.glaf.workflow.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ActReElementDefQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> IDs;
	protected Collection<String> appActorIds;
  	protected String eleType;
  	protected String eleTypeLike;
  	protected List<String> eleTypes;
  	protected String eleResourceId;
  	protected String eleResourceIdLike;
  	protected List<String> eleResourceIds;
  	protected String eleID;
  	protected String eleIDLike;
  	protected List<String> eleIDs;
  	protected String eleName;
  	protected String eleNameLike;
  	protected List<String> eleNames;
  	protected String eleDesc;
  	protected String eleDescLike;
  	protected List<String> eleDescs;
  	protected String modelId;
  	protected String modelIdLike;
  	protected List<String> modelIds;
  	protected String proceDefId;
  	protected String proceDefIdLike;
  	protected List<String> proceDefIds;
  	protected String bytes;
  	protected String bytesLike;
  	protected List<String> bytess;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDatetimeGreaterThanOrEqual;
  	protected Date createDatetimeLessThanOrEqual;
  	protected String modify;
  	protected String modifyLike;
  	protected List<String> modifys;
        protected Date modifyDatetimeGreaterThanOrEqual;
  	protected Date modifyDatetimeLessThanOrEqual;

    public ActReElementDefQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getEleType(){
        return eleType;
    }

    public String getEleTypeLike(){
	    if (eleTypeLike != null && eleTypeLike.trim().length() > 0) {
		if (!eleTypeLike.startsWith("%")) {
		    eleTypeLike = "%" + eleTypeLike;
		}
		if (!eleTypeLike.endsWith("%")) {
		   eleTypeLike = eleTypeLike + "%";
		}
	    }
	return eleTypeLike;
    }

    public List<String> getEleTypes(){
	return eleTypes;
    }


    public String getEleResourceId(){
        return eleResourceId;
    }

    public String getEleResourceIdLike(){
	    if (eleResourceIdLike != null && eleResourceIdLike.trim().length() > 0) {
		if (!eleResourceIdLike.startsWith("%")) {
		    eleResourceIdLike = "%" + eleResourceIdLike;
		}
		if (!eleResourceIdLike.endsWith("%")) {
		   eleResourceIdLike = eleResourceIdLike + "%";
		}
	    }
	return eleResourceIdLike;
    }

    public List<String> getEleResourceIds(){
	return eleResourceIds;
    }


    public String getEleID(){
        return eleID;
    }

    public String getEleIDLike(){
	    if (eleIDLike != null && eleIDLike.trim().length() > 0) {
		if (!eleIDLike.startsWith("%")) {
		    eleIDLike = "%" + eleIDLike;
		}
		if (!eleIDLike.endsWith("%")) {
		   eleIDLike = eleIDLike + "%";
		}
	    }
	return eleIDLike;
    }

    public List<String> getEleIDs(){
	return eleIDs;
    }


    public String getEleName(){
        return eleName;
    }

    public String getEleNameLike(){
	    if (eleNameLike != null && eleNameLike.trim().length() > 0) {
		if (!eleNameLike.startsWith("%")) {
		    eleNameLike = "%" + eleNameLike;
		}
		if (!eleNameLike.endsWith("%")) {
		   eleNameLike = eleNameLike + "%";
		}
	    }
	return eleNameLike;
    }

    public List<String> getEleNames(){
	return eleNames;
    }


    public String getEleDesc(){
        return eleDesc;
    }

    public String getEleDescLike(){
	    if (eleDescLike != null && eleDescLike.trim().length() > 0) {
		if (!eleDescLike.startsWith("%")) {
		    eleDescLike = "%" + eleDescLike;
		}
		if (!eleDescLike.endsWith("%")) {
		   eleDescLike = eleDescLike + "%";
		}
	    }
	return eleDescLike;
    }

    public List<String> getEleDescs(){
	return eleDescs;
    }


    public String getModelId(){
        return modelId;
    }

    public String getModelIdLike(){
	    if (modelIdLike != null && modelIdLike.trim().length() > 0) {
		if (!modelIdLike.startsWith("%")) {
		    modelIdLike = "%" + modelIdLike;
		}
		if (!modelIdLike.endsWith("%")) {
		   modelIdLike = modelIdLike + "%";
		}
	    }
	return modelIdLike;
    }

    public List<String> getModelIds(){
	return modelIds;
    }


    public String getProceDefId(){
        return proceDefId;
    }

    public String getProceDefIdLike(){
	    if (proceDefIdLike != null && proceDefIdLike.trim().length() > 0) {
		if (!proceDefIdLike.startsWith("%")) {
		    proceDefIdLike = "%" + proceDefIdLike;
		}
		if (!proceDefIdLike.endsWith("%")) {
		   proceDefIdLike = proceDefIdLike + "%";
		}
	    }
	return proceDefIdLike;
    }

    public List<String> getProceDefIds(){
	return proceDefIds;
    }


    public String getBytes(){
        return bytes;
    }

    public String getBytesLike(){
	    if (bytesLike != null && bytesLike.trim().length() > 0) {
		if (!bytesLike.startsWith("%")) {
		    bytesLike = "%" + bytesLike;
		}
		if (!bytesLike.endsWith("%")) {
		   bytesLike = bytesLike + "%";
		}
	    }
	return bytesLike;
    }

    public List<String> getBytess(){
	return bytess;
    }


    public String getCreator(){
        return creator;
    }

    public String getCreatorLike(){
	    if (creatorLike != null && creatorLike.trim().length() > 0) {
		if (!creatorLike.startsWith("%")) {
		    creatorLike = "%" + creatorLike;
		}
		if (!creatorLike.endsWith("%")) {
		   creatorLike = creatorLike + "%";
		}
	    }
	return creatorLike;
    }

    public List<String> getCreators(){
	return creators;
    }


    public Date getCreateDatetimeGreaterThanOrEqual(){
        return createDatetimeGreaterThanOrEqual;
    }

    public Date getCreateDatetimeLessThanOrEqual(){
	return createDatetimeLessThanOrEqual;
    }


    public String getModify(){
        return modify;
    }

    public String getModifyLike(){
	    if (modifyLike != null && modifyLike.trim().length() > 0) {
		if (!modifyLike.startsWith("%")) {
		    modifyLike = "%" + modifyLike;
		}
		if (!modifyLike.endsWith("%")) {
		   modifyLike = modifyLike + "%";
		}
	    }
	return modifyLike;
    }

    public List<String> getModifys(){
	return modifys;
    }


    public Date getModifyDatetimeGreaterThanOrEqual(){
        return modifyDatetimeGreaterThanOrEqual;
    }

    public Date getModifyDatetimeLessThanOrEqual(){
	return modifyDatetimeLessThanOrEqual;
    }


 

    public void setEleType(String eleType){
        this.eleType = eleType;
    }

    public void setEleTypeLike( String eleTypeLike){
	this.eleTypeLike = eleTypeLike;
    }

    public void setEleTypes(List<String> eleTypes){
        this.eleTypes = eleTypes;
    }


    public void setEleResourceId(String eleResourceId){
        this.eleResourceId = eleResourceId;
    }

    public void setEleResourceIdLike( String eleResourceIdLike){
	this.eleResourceIdLike = eleResourceIdLike;
    }

    public void setEleResourceIds(List<String> eleResourceIds){
        this.eleResourceIds = eleResourceIds;
    }


    public void setEleID(String eleID){
        this.eleID = eleID;
    }

    public void setEleIDLike( String eleIDLike){
	this.eleIDLike = eleIDLike;
    }

    public void setEleIDs(List<String> eleIDs){
        this.eleIDs = eleIDs;
    }


    public void setEleName(String eleName){
        this.eleName = eleName;
    }

    public void setEleNameLike( String eleNameLike){
	this.eleNameLike = eleNameLike;
    }

    public void setEleNames(List<String> eleNames){
        this.eleNames = eleNames;
    }


    public void setEleDesc(String eleDesc){
        this.eleDesc = eleDesc;
    }

    public void setEleDescLike( String eleDescLike){
	this.eleDescLike = eleDescLike;
    }

    public void setEleDescs(List<String> eleDescs){
        this.eleDescs = eleDescs;
    }


    public void setModelId(String modelId){
        this.modelId = modelId;
    }

    public void setModelIdLike( String modelIdLike){
	this.modelIdLike = modelIdLike;
    }

    public void setModelIds(List<String> modelIds){
        this.modelIds = modelIds;
    }


    public void setProceDefId(String proceDefId){
        this.proceDefId = proceDefId;
    }

    public void setProceDefIdLike( String proceDefIdLike){
	this.proceDefIdLike = proceDefIdLike;
    }

    public void setProceDefIds(List<String> proceDefIds){
        this.proceDefIds = proceDefIds;
    }


    public void setBytes(String bytes){
        this.bytes = bytes;
    }

    public void setBytesLike( String bytesLike){
	this.bytesLike = bytesLike;
    }

    public void setBytess(List<String> bytess){
        this.bytess = bytess;
    }


    public void setCreator(String creator){
        this.creator = creator;
    }

    public void setCreatorLike( String creatorLike){
	this.creatorLike = creatorLike;
    }

    public void setCreators(List<String> creators){
        this.creators = creators;
    }


    public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
        this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
    }

    public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
	this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
    }


    public void setModify(String modify){
        this.modify = modify;
    }

    public void setModifyLike( String modifyLike){
	this.modifyLike = modifyLike;
    }

    public void setModifys(List<String> modifys){
        this.modifys = modifys;
    }


    public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
        this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
    }

    public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
	this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
    }




    public ActReElementDefQuery eleType(String eleType){
	if (eleType == null) {
	    throw new RuntimeException("eleType is null");
        }         
	this.eleType = eleType;
	return this;
    }

    public ActReElementDefQuery eleTypeLike( String eleTypeLike){
        if (eleTypeLike == null) {
            throw new RuntimeException("eleType is null");
        }
        this.eleTypeLike = eleTypeLike;
        return this;
    }

    public ActReElementDefQuery eleTypes(List<String> eleTypes){
        if (eleTypes == null) {
            throw new RuntimeException("eleTypes is empty ");
        }
        this.eleTypes = eleTypes;
        return this;
    }


    public ActReElementDefQuery eleResourceId(String eleResourceId){
	if (eleResourceId == null) {
	    throw new RuntimeException("eleResourceId is null");
        }         
	this.eleResourceId = eleResourceId;
	return this;
    }

    public ActReElementDefQuery eleResourceIdLike( String eleResourceIdLike){
        if (eleResourceIdLike == null) {
            throw new RuntimeException("eleResourceId is null");
        }
        this.eleResourceIdLike = eleResourceIdLike;
        return this;
    }

    public ActReElementDefQuery eleResourceIds(List<String> eleResourceIds){
        if (eleResourceIds == null) {
            throw new RuntimeException("eleResourceIds is empty ");
        }
        this.eleResourceIds = eleResourceIds;
        return this;
    }


    public ActReElementDefQuery eleID(String eleID){
	if (eleID == null) {
	    throw new RuntimeException("eleID is null");
        }         
	this.eleID = eleID;
	return this;
    }

    public ActReElementDefQuery eleIDLike( String eleIDLike){
        if (eleIDLike == null) {
            throw new RuntimeException("eleID is null");
        }
        this.eleIDLike = eleIDLike;
        return this;
    }

    public ActReElementDefQuery eleIDs(List<String> eleIDs){
        if (eleIDs == null) {
            throw new RuntimeException("eleIDs is empty ");
        }
        this.eleIDs = eleIDs;
        return this;
    }


    public ActReElementDefQuery eleName(String eleName){
	if (eleName == null) {
	    throw new RuntimeException("eleName is null");
        }         
	this.eleName = eleName;
	return this;
    }

    public ActReElementDefQuery eleNameLike( String eleNameLike){
        if (eleNameLike == null) {
            throw new RuntimeException("eleName is null");
        }
        this.eleNameLike = eleNameLike;
        return this;
    }

    public ActReElementDefQuery eleNames(List<String> eleNames){
        if (eleNames == null) {
            throw new RuntimeException("eleNames is empty ");
        }
        this.eleNames = eleNames;
        return this;
    }


    public ActReElementDefQuery eleDesc(String eleDesc){
	if (eleDesc == null) {
	    throw new RuntimeException("eleDesc is null");
        }         
	this.eleDesc = eleDesc;
	return this;
    }

    public ActReElementDefQuery eleDescLike( String eleDescLike){
        if (eleDescLike == null) {
            throw new RuntimeException("eleDesc is null");
        }
        this.eleDescLike = eleDescLike;
        return this;
    }

    public ActReElementDefQuery eleDescs(List<String> eleDescs){
        if (eleDescs == null) {
            throw new RuntimeException("eleDescs is empty ");
        }
        this.eleDescs = eleDescs;
        return this;
    }


    public ActReElementDefQuery modelId(String modelId){
	if (modelId == null) {
	    throw new RuntimeException("modelId is null");
        }         
	this.modelId = modelId;
	return this;
    }

    public ActReElementDefQuery modelIdLike( String modelIdLike){
        if (modelIdLike == null) {
            throw new RuntimeException("modelId is null");
        }
        this.modelIdLike = modelIdLike;
        return this;
    }

    public ActReElementDefQuery modelIds(List<String> modelIds){
        if (modelIds == null) {
            throw new RuntimeException("modelIds is empty ");
        }
        this.modelIds = modelIds;
        return this;
    }


    public ActReElementDefQuery proceDefId(String proceDefId){
	if (proceDefId == null) {
	    throw new RuntimeException("proceDefId is null");
        }         
	this.proceDefId = proceDefId;
	return this;
    }

    public ActReElementDefQuery proceDefIdLike( String proceDefIdLike){
        if (proceDefIdLike == null) {
            throw new RuntimeException("proceDefId is null");
        }
        this.proceDefIdLike = proceDefIdLike;
        return this;
    }

    public ActReElementDefQuery proceDefIds(List<String> proceDefIds){
        if (proceDefIds == null) {
            throw new RuntimeException("proceDefIds is empty ");
        }
        this.proceDefIds = proceDefIds;
        return this;
    }


    public ActReElementDefQuery bytes(String bytes){
	if (bytes == null) {
	    throw new RuntimeException("bytes is null");
        }         
	this.bytes = bytes;
	return this;
    }

    public ActReElementDefQuery bytesLike( String bytesLike){
        if (bytesLike == null) {
            throw new RuntimeException("bytes is null");
        }
        this.bytesLike = bytesLike;
        return this;
    }

    public ActReElementDefQuery bytess(List<String> bytess){
        if (bytess == null) {
            throw new RuntimeException("bytess is empty ");
        }
        this.bytess = bytess;
        return this;
    }


    public ActReElementDefQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ActReElementDefQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ActReElementDefQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ActReElementDefQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ActReElementDefQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ActReElementDefQuery modify(String modify){
	if (modify == null) {
	    throw new RuntimeException("modify is null");
        }         
	this.modify = modify;
	return this;
    }

    public ActReElementDefQuery modifyLike( String modifyLike){
        if (modifyLike == null) {
            throw new RuntimeException("modify is null");
        }
        this.modifyLike = modifyLike;
        return this;
    }

    public ActReElementDefQuery modifys(List<String> modifys){
        if (modifys == null) {
            throw new RuntimeException("modifys is empty ");
        }
        this.modifys = modifys;
        return this;
    }



    public ActReElementDefQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ActReElementDefQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
        if (modifyDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDatetime is null");
        }
        this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
        return this;
    }




    @Override
	public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("eleType".equals(sortColumn)) {
                orderBy = "E.ELE_TYPE_" + a_x;
            } 

            if ("eleResourceId".equals(sortColumn)) {
                orderBy = "E.ELE_RESOURCE_ID_" + a_x;
            } 

            if ("eleID".equals(sortColumn)) {
                orderBy = "E.ELE_ID_" + a_x;
            } 

            if ("eleName".equals(sortColumn)) {
                orderBy = "E.ELE_NAME_" + a_x;
            } 

            if ("eleDesc".equals(sortColumn)) {
                orderBy = "E.ELE_DESC" + a_x;
            } 

            if ("modelId".equals(sortColumn)) {
                orderBy = "E.MODEL_ID_" + a_x;
            } 

            if ("proceDefId".equals(sortColumn)) {
                orderBy = "E.PROCEDEF_ID_" + a_x;
            } 

            if ("bytes".equals(sortColumn)) {
                orderBy = "E.BTYES_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDatetime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modify".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("modifyDatetime".equals(sortColumn)) {
                orderBy = "E.MODIFYDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("ID", "ID_");
        addColumn("eleType", "ELE_TYPE_");
        addColumn("eleResourceId", "ELE_RESOURCE_ID_");
        addColumn("eleID", "ELE_ID_");
        addColumn("eleName", "ELE_NAME_");
        addColumn("eleDesc", "ELE_DESC");
        addColumn("modelId", "MODEL_ID_");
        addColumn("proceDefId", "PROCEDEF_ID_");
        addColumn("bytes", "BTYES_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modify", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}