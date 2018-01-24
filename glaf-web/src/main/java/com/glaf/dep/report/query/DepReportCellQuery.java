package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportCellQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long repTemplateId;
  	protected Long repTemplateIdGreaterThanOrEqual;
  	protected Long repTemplateIdLessThanOrEqual;
  	protected List<Long> repTemplateIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String desc;
  	protected String descLike;
  	protected List<String> descs;
  	protected String hide;
  	protected String hideLike;
  	protected List<String> hides;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDateTimeGreaterThanOrEqual;
  	protected Date createDateTimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date modifyDateTimeGreaterThanOrEqual;
  	protected Date modifyDateTimeLessThanOrEqual;
  	protected String delflag;
  	protected String delflagLike;
  	protected List<String> delflags;

    public DepReportCellQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getRepTemplateId(){
        return repTemplateId;
    }

    public Long getRepTemplateIdGreaterThanOrEqual(){
        return repTemplateIdGreaterThanOrEqual;
    }

    public Long getRepTemplateIdLessThanOrEqual(){
	return repTemplateIdLessThanOrEqual;
    }

    public List<Long> getRepTemplateIds(){
	return repTemplateIds;
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


    public String getHide(){
        return hide;
    }

    public String getHideLike(){
	    if (hideLike != null && hideLike.trim().length() > 0) {
		if (!hideLike.startsWith("%")) {
		    hideLike = "%" + hideLike;
		}
		if (!hideLike.endsWith("%")) {
		   hideLike = hideLike + "%";
		}
	    }
	return hideLike;
    }

    public List<String> getHides(){
	return hides;
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


    public Date getCreateDateTimeGreaterThanOrEqual(){
        return createDateTimeGreaterThanOrEqual;
    }

    public Date getCreateDateTimeLessThanOrEqual(){
	return createDateTimeLessThanOrEqual;
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


    public Date getModifyDateTimeGreaterThanOrEqual(){
        return modifyDateTimeGreaterThanOrEqual;
    }

    public Date getModifyDateTimeLessThanOrEqual(){
	return modifyDateTimeLessThanOrEqual;
    }


    public String getDelflag(){
        return delflag;
    }

    public String getDelflagLike(){
	    if (delflagLike != null && delflagLike.trim().length() > 0) {
		if (!delflagLike.startsWith("%")) {
		    delflagLike = "%" + delflagLike;
		}
		if (!delflagLike.endsWith("%")) {
		   delflagLike = delflagLike + "%";
		}
	    }
	return delflagLike;
    }

    public List<String> getDelflags(){
	return delflags;
    }


 

    public void setRepTemplateId(Long repTemplateId){
        this.repTemplateId = repTemplateId;
    }

    public void setRepTemplateIdGreaterThanOrEqual(Long repTemplateIdGreaterThanOrEqual){
        this.repTemplateIdGreaterThanOrEqual = repTemplateIdGreaterThanOrEqual;
    }

    public void setRepTemplateIdLessThanOrEqual(Long repTemplateIdLessThanOrEqual){
	this.repTemplateIdLessThanOrEqual = repTemplateIdLessThanOrEqual;
    }

    public void setRepTemplateIds(List<Long> repTemplateIds){
        this.repTemplateIds = repTemplateIds;
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


    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setDescLike( String descLike){
	this.descLike = descLike;
    }

    public void setDescs(List<String> descs){
        this.descs = descs;
    }


    public void setHide(String hide){
        this.hide = hide;
    }

    public void setHideLike( String hideLike){
	this.hideLike = hideLike;
    }

    public void setHides(List<String> hides){
        this.hides = hides;
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


    public void setCreateDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
        this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
    }

    public void setCreateDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
	this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
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


    public void setModifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
        this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
    }

    public void setModifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
	this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
    }


    public void setDelflag(String delflag){
        this.delflag = delflag;
    }

    public void setDelflagLike( String delflagLike){
	this.delflagLike = delflagLike;
    }

    public void setDelflags(List<String> delflags){
        this.delflags = delflags;
    }




    public DepReportCellQuery repTemplateId(Long repTemplateId){
	if (repTemplateId == null) {
            throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateId = repTemplateId;
	return this;
    }

    public DepReportCellQuery repTemplateIdGreaterThanOrEqual(Long repTemplateIdGreaterThanOrEqual){
	if (repTemplateIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateIdGreaterThanOrEqual = repTemplateIdGreaterThanOrEqual;
        return this;
    }

    public DepReportCellQuery repTemplateIdLessThanOrEqual(Long repTemplateIdLessThanOrEqual){
        if (repTemplateIdLessThanOrEqual == null) {
            throw new RuntimeException("repTemplateId is null");
        }
        this.repTemplateIdLessThanOrEqual = repTemplateIdLessThanOrEqual;
        return this;
    }

    public DepReportCellQuery repTemplateIds(List<Long> repTemplateIds){
        if (repTemplateIds == null) {
            throw new RuntimeException("repTemplateIds is empty ");
        }
        this.repTemplateIds = repTemplateIds;
        return this;
    }


    public DepReportCellQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public DepReportCellQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public DepReportCellQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public DepReportCellQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public DepReportCellQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public DepReportCellQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public DepReportCellQuery desc(String desc){
	if (desc == null) {
	    throw new RuntimeException("desc is null");
        }         
	this.desc = desc;
	return this;
    }

    public DepReportCellQuery descLike( String descLike){
        if (descLike == null) {
            throw new RuntimeException("desc is null");
        }
        this.descLike = descLike;
        return this;
    }

    public DepReportCellQuery descs(List<String> descs){
        if (descs == null) {
            throw new RuntimeException("descs is empty ");
        }
        this.descs = descs;
        return this;
    }


    public DepReportCellQuery hide(String hide){
	if (hide == null) {
	    throw new RuntimeException("hide is null");
        }         
	this.hide = hide;
	return this;
    }

    public DepReportCellQuery hideLike( String hideLike){
        if (hideLike == null) {
            throw new RuntimeException("hide is null");
        }
        this.hideLike = hideLike;
        return this;
    }

    public DepReportCellQuery hides(List<String> hides){
        if (hides == null) {
            throw new RuntimeException("hides is empty ");
        }
        this.hides = hides;
        return this;
    }


    public DepReportCellQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportCellQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportCellQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportCellQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportCellQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportCellQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportCellQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportCellQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportCellQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportCellQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
        if (modifyDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDateTime is null");
        }
        this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportCellQuery delflag(String delflag){
	if (delflag == null) {
	    throw new RuntimeException("delflag is null");
        }         
	this.delflag = delflag;
	return this;
    }

    public DepReportCellQuery delflagLike( String delflagLike){
        if (delflagLike == null) {
            throw new RuntimeException("delflag is null");
        }
        this.delflagLike = delflagLike;
        return this;
    }

    public DepReportCellQuery delflags(List<String> delflags){
        if (delflags == null) {
            throw new RuntimeException("delflags is empty ");
        }
        this.delflags = delflags;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("repTemplateId".equals(sortColumn)) {
                orderBy = "E.REPTEMPLATE_ID_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("desc".equals(sortColumn)) {
                orderBy = "E.DESC_" + a_x;
            } 

            if ("hide".equals(sortColumn)) {
                orderBy = "E.HIDE_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDateTime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("modifyDateTime".equals(sortColumn)) {
                orderBy = "E.MODIFYDATETIME_" + a_x;
            } 

            if ("delflag".equals(sortColumn)) {
                orderBy = "E.DELFLAG_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("repTemplateId", "REPTEMPLATE_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("desc", "DESC_");
        addColumn("hide", "HIDE_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
        addColumn("delflag", "DELFLAG_");
    }

}