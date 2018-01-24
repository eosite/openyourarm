package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTmpMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String systemId;
  	protected String systemIdLike;
  	protected List<String> systemIds;
  	protected String templateId;
  	protected String templateIdLike;
  	protected List<String> templateIds;
  	protected String templateCode;
  	protected String templateCodeLike;
  	protected List<String> templateCodes;
  	protected String templateName;
  	protected String templateNameLike;
  	protected List<String> templateNames;
  	protected String desc;
  	protected String descLike;
  	protected List<String> descs;
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
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;

    public ReportTmpMappingQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getSystemId(){
        return systemId;
    }

    public String getSystemIdLike(){
	    if (systemIdLike != null && systemIdLike.trim().length() > 0) {
		if (!systemIdLike.startsWith("%")) {
		    systemIdLike = "%" + systemIdLike;
		}
		if (!systemIdLike.endsWith("%")) {
		   systemIdLike = systemIdLike + "%";
		}
	    }
	return systemIdLike;
    }

    public List<String> getSystemIds(){
	return systemIds;
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


    public String getTemplateCode(){
        return templateCode;
    }

    public String getTemplateCodeLike(){
	    if (templateCodeLike != null && templateCodeLike.trim().length() > 0) {
		if (!templateCodeLike.startsWith("%")) {
		    templateCodeLike = "%" + templateCodeLike;
		}
		if (!templateCodeLike.endsWith("%")) {
		   templateCodeLike = templateCodeLike + "%";
		}
	    }
	return templateCodeLike;
    }

    public List<String> getTemplateCodes(){
	return templateCodes;
    }


    public String getTemplateName(){
        return templateName;
    }

    public String getTemplateNameLike(){
	    if (templateNameLike != null && templateNameLike.trim().length() > 0) {
		if (!templateNameLike.startsWith("%")) {
		    templateNameLike = "%" + templateNameLike;
		}
		if (!templateNameLike.endsWith("%")) {
		   templateNameLike = templateNameLike + "%";
		}
	    }
	return templateNameLike;
    }

    public List<String> getTemplateNames(){
	return templateNames;
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

 

    public void setSystemId(String systemId){
        this.systemId = systemId;
    }

    public void setSystemIdLike( String systemIdLike){
	this.systemIdLike = systemIdLike;
    }

    public void setSystemIds(List<String> systemIds){
        this.systemIds = systemIds;
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


    public void setTemplateCode(String templateCode){
        this.templateCode = templateCode;
    }

    public void setTemplateCodeLike( String templateCodeLike){
	this.templateCodeLike = templateCodeLike;
    }

    public void setTemplateCodes(List<String> templateCodes){
        this.templateCodes = templateCodes;
    }


    public void setTemplateName(String templateName){
        this.templateName = templateName;
    }

    public void setTemplateNameLike( String templateNameLike){
	this.templateNameLike = templateNameLike;
    }

    public void setTemplateNames(List<String> templateNames){
        this.templateNames = templateNames;
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




    public ReportTmpMappingQuery systemId(String systemId){
	if (systemId == null) {
	    throw new RuntimeException("systemId is null");
        }         
	this.systemId = systemId;
	return this;
    }

    public ReportTmpMappingQuery systemIdLike( String systemIdLike){
        if (systemIdLike == null) {
            throw new RuntimeException("systemId is null");
        }
        this.systemIdLike = systemIdLike;
        return this;
    }

    public ReportTmpMappingQuery systemIds(List<String> systemIds){
        if (systemIds == null) {
            throw new RuntimeException("systemIds is empty ");
        }
        this.systemIds = systemIds;
        return this;
    }


    public ReportTmpMappingQuery templateId(String templateId){
	if (templateId == null) {
	    throw new RuntimeException("templateId is null");
        }         
	this.templateId = templateId;
	return this;
    }

    public ReportTmpMappingQuery templateIdLike( String templateIdLike){
        if (templateIdLike == null) {
            throw new RuntimeException("templateId is null");
        }
        this.templateIdLike = templateIdLike;
        return this;
    }

    public ReportTmpMappingQuery templateIds(List<String> templateIds){
        if (templateIds == null) {
            throw new RuntimeException("templateIds is empty ");
        }
        this.templateIds = templateIds;
        return this;
    }


    public ReportTmpMappingQuery templateCode(String templateCode){
	if (templateCode == null) {
	    throw new RuntimeException("templateCode is null");
        }         
	this.templateCode = templateCode;
	return this;
    }

    public ReportTmpMappingQuery templateCodeLike( String templateCodeLike){
        if (templateCodeLike == null) {
            throw new RuntimeException("templateCode is null");
        }
        this.templateCodeLike = templateCodeLike;
        return this;
    }

    public ReportTmpMappingQuery templateCodes(List<String> templateCodes){
        if (templateCodes == null) {
            throw new RuntimeException("templateCodes is empty ");
        }
        this.templateCodes = templateCodes;
        return this;
    }


    public ReportTmpMappingQuery templateName(String templateName){
	if (templateName == null) {
	    throw new RuntimeException("templateName is null");
        }         
	this.templateName = templateName;
	return this;
    }

    public ReportTmpMappingQuery templateNameLike( String templateNameLike){
        if (templateNameLike == null) {
            throw new RuntimeException("templateName is null");
        }
        this.templateNameLike = templateNameLike;
        return this;
    }

    public ReportTmpMappingQuery templateNames(List<String> templateNames){
        if (templateNames == null) {
            throw new RuntimeException("templateNames is empty ");
        }
        this.templateNames = templateNames;
        return this;
    }


    public ReportTmpMappingQuery desc(String desc){
	if (desc == null) {
	    throw new RuntimeException("desc is null");
        }         
	this.desc = desc;
	return this;
    }

    public ReportTmpMappingQuery descLike( String descLike){
        if (descLike == null) {
            throw new RuntimeException("desc is null");
        }
        this.descLike = descLike;
        return this;
    }

    public ReportTmpMappingQuery descs(List<String> descs){
        if (descs == null) {
            throw new RuntimeException("descs is empty ");
        }
        this.descs = descs;
        return this;
    }


    public ReportTmpMappingQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTmpMappingQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTmpMappingQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTmpMappingQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpMappingQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTmpMappingQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportTmpMappingQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportTmpMappingQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportTmpMappingQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpMappingQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
        if (modifyDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDatetime is null");
        }
        this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTmpMappingQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public ReportTmpMappingQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public ReportTmpMappingQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public ReportTmpMappingQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("systemId".equals(sortColumn)) {
                orderBy = "E.SYSTEM_ID_" + a_x;
            } 

            if ("templateId".equals(sortColumn)) {
                orderBy = "E.TEMPLATE_ID_" + a_x;
            } 

            if ("templateCode".equals(sortColumn)) {
                orderBy = "E.TEMPLATE_CODE_" + a_x;
            } 

            if ("templateName".equals(sortColumn)) {
                orderBy = "E.TEMPLATE_NAME_" + a_x;
            } 

            if ("desc".equals(sortColumn)) {
                orderBy = "E.DESC_" + a_x;
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
        addColumn("systemId", "SYSTEM_ID_");
        addColumn("templateId", "TEMPLATE_ID_");
        addColumn("templateCode", "TEMPLATE_CODE_");
        addColumn("templateName", "TEMPLATE_NAME_");
        addColumn("desc", "DESC_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}