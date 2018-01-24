package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTmpDataSetMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String tmpMappingId;
  	protected String tmpMappingIdLike;
  	protected List<String> tmpMappingIds;
  	protected String templateId;
  	protected String templateIdLike;
  	protected List<String> templateIds;
  	protected String dataSetCode;
  	protected String dataSetCodeLike;
  	protected List<String> dataSetCodes;
  	protected String dataSetName;
  	protected String dataSetNameLike;
  	protected List<String> dataSetNames;
  	protected String mappingDataSetId;
  	protected String mappingDataSetIdLike;
  	protected List<String> mappingDataSetIds;
  	protected String mappingDataSetCode;
  	protected String mappingDataSetCodeLike;
  	protected List<String> mappingDataSetCodes;
  	protected String mappingDataSetName;
  	protected String mappingDataSetNameLike;
  	protected List<String> mappingDataSetNames;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDatetimeGreaterThanOrEqual;
  	protected Date createDatetimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date modifyDatetimeGreaterThanOrEqual;
  	protected Date modifyDatetimeLessThanOrEqual;

    public ReportTmpDataSetMappingQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTmpMappingId(){
        return tmpMappingId;
    }

    public String getTmpMappingIdLike(){
	    if (tmpMappingIdLike != null && tmpMappingIdLike.trim().length() > 0) {
		if (!tmpMappingIdLike.startsWith("%")) {
		    tmpMappingIdLike = "%" + tmpMappingIdLike;
		}
		if (!tmpMappingIdLike.endsWith("%")) {
		   tmpMappingIdLike = tmpMappingIdLike + "%";
		}
	    }
	return tmpMappingIdLike;
    }

    public List<String> getTmpMappingIds(){
	return tmpMappingIds;
    }


    public String getTemplateId(){
        return templateId;
    }

    public String getTemplateIdLike(){
	    if (templateIdLike != null && templateIdLike.trim().length() > 0) {
		if (!templateIdLike.startsWith("%")) {
		    templateIdLike = "%" + templateIdLike;
		}
		if (!templateIdLike.endsWith("%")) {
		   templateIdLike = templateIdLike + "%";
		}
	    }
	return templateIdLike;
    }

    public List<String> getTemplateIds(){
	return templateIds;
    }


    public String getDataSetCode(){
        return dataSetCode;
    }

    public String getDataSetCodeLike(){
	    if (dataSetCodeLike != null && dataSetCodeLike.trim().length() > 0) {
		if (!dataSetCodeLike.startsWith("%")) {
		    dataSetCodeLike = "%" + dataSetCodeLike;
		}
		if (!dataSetCodeLike.endsWith("%")) {
		   dataSetCodeLike = dataSetCodeLike + "%";
		}
	    }
	return dataSetCodeLike;
    }

    public List<String> getDataSetCodes(){
	return dataSetCodes;
    }


    public String getDataSetName(){
        return dataSetName;
    }

    public String getDataSetNameLike(){
	    if (dataSetNameLike != null && dataSetNameLike.trim().length() > 0) {
		if (!dataSetNameLike.startsWith("%")) {
		    dataSetNameLike = "%" + dataSetNameLike;
		}
		if (!dataSetNameLike.endsWith("%")) {
		   dataSetNameLike = dataSetNameLike + "%";
		}
	    }
	return dataSetNameLike;
    }

    public List<String> getDataSetNames(){
	return dataSetNames;
    }


    public String getMappingDataSetId(){
        return mappingDataSetId;
    }

    public String getMappingDataSetIdLike(){
	    if (mappingDataSetIdLike != null && mappingDataSetIdLike.trim().length() > 0) {
		if (!mappingDataSetIdLike.startsWith("%")) {
		    mappingDataSetIdLike = "%" + mappingDataSetIdLike;
		}
		if (!mappingDataSetIdLike.endsWith("%")) {
		   mappingDataSetIdLike = mappingDataSetIdLike + "%";
		}
	    }
	return mappingDataSetIdLike;
    }

    public List<String> getMappingDataSetIds(){
	return mappingDataSetIds;
    }


    public String getMappingDataSetCode(){
        return mappingDataSetCode;
    }

    public String getMappingDataSetCodeLike(){
	    if (mappingDataSetCodeLike != null && mappingDataSetCodeLike.trim().length() > 0) {
		if (!mappingDataSetCodeLike.startsWith("%")) {
		    mappingDataSetCodeLike = "%" + mappingDataSetCodeLike;
		}
		if (!mappingDataSetCodeLike.endsWith("%")) {
		   mappingDataSetCodeLike = mappingDataSetCodeLike + "%";
		}
	    }
	return mappingDataSetCodeLike;
    }

    public List<String> getMappingDataSetCodes(){
	return mappingDataSetCodes;
    }


    public String getMappingDataSetName(){
        return mappingDataSetName;
    }

    public String getMappingDataSetNameLike(){
	    if (mappingDataSetNameLike != null && mappingDataSetNameLike.trim().length() > 0) {
		if (!mappingDataSetNameLike.startsWith("%")) {
		    mappingDataSetNameLike = "%" + mappingDataSetNameLike;
		}
		if (!mappingDataSetNameLike.endsWith("%")) {
		   mappingDataSetNameLike = mappingDataSetNameLike + "%";
		}
	    }
	return mappingDataSetNameLike;
    }

    public List<String> getMappingDataSetNames(){
	return mappingDataSetNames;
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


    public String getModifier(){
        return modifier;
    }

    public String getModifierLike(){
	    if (modifierLike != null && modifierLike.trim().length() > 0) {
		if (!modifierLike.startsWith("%")) {
		    modifierLike = "%" + modifierLike;
		}
		if (!modifierLike.endsWith("%")) {
		   modifierLike = modifierLike + "%";
		}
	    }
	return modifierLike;
    }

    public List<String> getModifiers(){
	return modifiers;
    }


    public Date getModifyDatetimeGreaterThanOrEqual(){
        return modifyDatetimeGreaterThanOrEqual;
    }

    public Date getModifyDatetimeLessThanOrEqual(){
	return modifyDatetimeLessThanOrEqual;
    }


 

    public void setTmpMappingId(String tmpMappingId){
        this.tmpMappingId = tmpMappingId;
    }

    public void setTmpMappingIdLike( String tmpMappingIdLike){
	this.tmpMappingIdLike = tmpMappingIdLike;
    }

    public void setTmpMappingIds(List<String> tmpMappingIds){
        this.tmpMappingIds = tmpMappingIds;
    }


    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    public void setTemplateIdLike( String templateIdLike){
	this.templateIdLike = templateIdLike;
    }

    public void setTemplateIds(List<String> templateIds){
        this.templateIds = templateIds;
    }


    public void setDataSetCode(String dataSetCode){
        this.dataSetCode = dataSetCode;
    }

    public void setDataSetCodeLike( String dataSetCodeLike){
	this.dataSetCodeLike = dataSetCodeLike;
    }

    public void setDataSetCodes(List<String> dataSetCodes){
        this.dataSetCodes = dataSetCodes;
    }


    public void setDataSetName(String dataSetName){
        this.dataSetName = dataSetName;
    }

    public void setDataSetNameLike( String dataSetNameLike){
	this.dataSetNameLike = dataSetNameLike;
    }

    public void setDataSetNames(List<String> dataSetNames){
        this.dataSetNames = dataSetNames;
    }


    public void setMappingDataSetId(String mappingDataSetId){
        this.mappingDataSetId = mappingDataSetId;
    }

    public void setMappingDataSetIdLike( String mappingDataSetIdLike){
	this.mappingDataSetIdLike = mappingDataSetIdLike;
    }

    public void setMappingDataSetIds(List<String> mappingDataSetIds){
        this.mappingDataSetIds = mappingDataSetIds;
    }


    public void setMappingDataSetCode(String mappingDataSetCode){
        this.mappingDataSetCode = mappingDataSetCode;
    }

    public void setMappingDataSetCodeLike( String mappingDataSetCodeLike){
	this.mappingDataSetCodeLike = mappingDataSetCodeLike;
    }

    public void setMappingDataSetCodes(List<String> mappingDataSetCodes){
        this.mappingDataSetCodes = mappingDataSetCodes;
    }


    public void setMappingDataSetName(String mappingDataSetName){
        this.mappingDataSetName = mappingDataSetName;
    }

    public void setMappingDataSetNameLike( String mappingDataSetNameLike){
	this.mappingDataSetNameLike = mappingDataSetNameLike;
    }

    public void setMappingDataSetNames(List<String> mappingDataSetNames){
        this.mappingDataSetNames = mappingDataSetNames;
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


    public void setModifier(String modifier){
        this.modifier = modifier;
    }

    public void setModifierLike( String modifierLike){
	this.modifierLike = modifierLike;
    }

    public void setModifiers(List<String> modifiers){
        this.modifiers = modifiers;
    }


    public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
        this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
    }

    public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
	this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
    }




    public ReportTmpDataSetMappingQuery tmpMappingId(String tmpMappingId){
	if (tmpMappingId == null) {
	    throw new RuntimeException("tmpMappingId is null");
        }         
	this.tmpMappingId = tmpMappingId;
	return this;
    }

    public ReportTmpDataSetMappingQuery tmpMappingIdLike( String tmpMappingIdLike){
        if (tmpMappingIdLike == null) {
            throw new RuntimeException("tmpMappingId is null");
        }
        this.tmpMappingIdLike = tmpMappingIdLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery tmpMappingIds(List<String> tmpMappingIds){
        if (tmpMappingIds == null) {
            throw new RuntimeException("tmpMappingIds is empty ");
        }
        this.tmpMappingIds = tmpMappingIds;
        return this;
    }


    public ReportTmpDataSetMappingQuery templateId(String templateId){
	if (templateId == null) {
	    throw new RuntimeException("templateId is null");
        }         
	this.templateId = templateId;
	return this;
    }

    public ReportTmpDataSetMappingQuery templateIdLike( String templateIdLike){
        if (templateIdLike == null) {
            throw new RuntimeException("templateId is null");
        }
        this.templateIdLike = templateIdLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery templateIds(List<String> templateIds){
        if (templateIds == null) {
            throw new RuntimeException("templateIds is empty ");
        }
        this.templateIds = templateIds;
        return this;
    }


    public ReportTmpDataSetMappingQuery dataSetCode(String dataSetCode){
	if (dataSetCode == null) {
	    throw new RuntimeException("dataSetCode is null");
        }         
	this.dataSetCode = dataSetCode;
	return this;
    }

    public ReportTmpDataSetMappingQuery dataSetCodeLike( String dataSetCodeLike){
        if (dataSetCodeLike == null) {
            throw new RuntimeException("dataSetCode is null");
        }
        this.dataSetCodeLike = dataSetCodeLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery dataSetCodes(List<String> dataSetCodes){
        if (dataSetCodes == null) {
            throw new RuntimeException("dataSetCodes is empty ");
        }
        this.dataSetCodes = dataSetCodes;
        return this;
    }


    public ReportTmpDataSetMappingQuery dataSetName(String dataSetName){
	if (dataSetName == null) {
	    throw new RuntimeException("dataSetName is null");
        }         
	this.dataSetName = dataSetName;
	return this;
    }

    public ReportTmpDataSetMappingQuery dataSetNameLike( String dataSetNameLike){
        if (dataSetNameLike == null) {
            throw new RuntimeException("dataSetName is null");
        }
        this.dataSetNameLike = dataSetNameLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery dataSetNames(List<String> dataSetNames){
        if (dataSetNames == null) {
            throw new RuntimeException("dataSetNames is empty ");
        }
        this.dataSetNames = dataSetNames;
        return this;
    }


    public ReportTmpDataSetMappingQuery mappingDataSetId(String mappingDataSetId){
	if (mappingDataSetId == null) {
	    throw new RuntimeException("mappingDataSetId is null");
        }         
	this.mappingDataSetId = mappingDataSetId;
	return this;
    }

    public ReportTmpDataSetMappingQuery mappingDataSetIdLike( String mappingDataSetIdLike){
        if (mappingDataSetIdLike == null) {
            throw new RuntimeException("mappingDataSetId is null");
        }
        this.mappingDataSetIdLike = mappingDataSetIdLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery mappingDataSetIds(List<String> mappingDataSetIds){
        if (mappingDataSetIds == null) {
            throw new RuntimeException("mappingDataSetIds is empty ");
        }
        this.mappingDataSetIds = mappingDataSetIds;
        return this;
    }


    public ReportTmpDataSetMappingQuery mappingDataSetCode(String mappingDataSetCode){
	if (mappingDataSetCode == null) {
	    throw new RuntimeException("mappingDataSetCode is null");
        }         
	this.mappingDataSetCode = mappingDataSetCode;
	return this;
    }

    public ReportTmpDataSetMappingQuery mappingDataSetCodeLike( String mappingDataSetCodeLike){
        if (mappingDataSetCodeLike == null) {
            throw new RuntimeException("mappingDataSetCode is null");
        }
        this.mappingDataSetCodeLike = mappingDataSetCodeLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery mappingDataSetCodes(List<String> mappingDataSetCodes){
        if (mappingDataSetCodes == null) {
            throw new RuntimeException("mappingDataSetCodes is empty ");
        }
        this.mappingDataSetCodes = mappingDataSetCodes;
        return this;
    }


    public ReportTmpDataSetMappingQuery mappingDataSetName(String mappingDataSetName){
	if (mappingDataSetName == null) {
	    throw new RuntimeException("mappingDataSetName is null");
        }         
	this.mappingDataSetName = mappingDataSetName;
	return this;
    }

    public ReportTmpDataSetMappingQuery mappingDataSetNameLike( String mappingDataSetNameLike){
        if (mappingDataSetNameLike == null) {
            throw new RuntimeException("mappingDataSetName is null");
        }
        this.mappingDataSetNameLike = mappingDataSetNameLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery mappingDataSetNames(List<String> mappingDataSetNames){
        if (mappingDataSetNames == null) {
            throw new RuntimeException("mappingDataSetNames is empty ");
        }
        this.mappingDataSetNames = mappingDataSetNames;
        return this;
    }


    public ReportTmpDataSetMappingQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTmpDataSetMappingQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTmpDataSetMappingQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpDataSetMappingQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTmpDataSetMappingQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportTmpDataSetMappingQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportTmpDataSetMappingQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportTmpDataSetMappingQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpDataSetMappingQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
        if (modifyDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDatetime is null");
        }
        this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("tmpMappingId".equals(sortColumn)) {
                orderBy = "E.TMP_MAPPING_ID_" + a_x;
            } 

            if ("templateId".equals(sortColumn)) {
                orderBy = "E.TEMPLATE_ID_" + a_x;
            } 

            if ("dataSetCode".equals(sortColumn)) {
                orderBy = "E.DATASET_CODE_" + a_x;
            } 

            if ("dataSetName".equals(sortColumn)) {
                orderBy = "E.DATASET_NAME_" + a_x;
            } 

            if ("mappingDataSetId".equals(sortColumn)) {
                orderBy = "E.MAPPING_DATASET_ID_" + a_x;
            } 

            if ("mappingDataSetCode".equals(sortColumn)) {
                orderBy = "E.MAPPING_DATASET_CODE_" + a_x;
            } 

            if ("mappingDataSetName".equals(sortColumn)) {
                orderBy = "E.MAPPING_DATASET_NAME_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDatetime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
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
        addColumn("id", "ID_");
        addColumn("tmpMappingId", "TMP_MAPPING_ID_");
        addColumn("templateId", "TEMPLATE_ID_");
        addColumn("dataSetCode", "DATASET_CODE_");
        addColumn("dataSetName", "DATASET_NAME_");
        addColumn("mappingDataSetId", "MAPPING_DATASET_ID_");
        addColumn("mappingDataSetCode", "MAPPING_DATASET_CODE_");
        addColumn("mappingDataSetName", "MAPPING_DATASET_NAME_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}