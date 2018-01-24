package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormEventTemplateQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String pId;
  	protected String pIdLike;
  	protected List<String> pIds;
  	protected String complexId;
  	protected String complexIdLike;
  	protected List<String> complexIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String remark;
  	protected String remarkLike;
  	protected List<String> remarks;
  	protected String diagram;
  	protected String diagramLike;
  	protected List<String> diagrams;
  	protected String rule;
  	protected String ruleLike;
  	protected List<String> rules;
  	protected String pageId;
  	protected String pageIdLike;
  	protected List<String> pageIds;
  	protected String complexRule;
  	protected String complexRuleLike;
  	protected List<String> complexRules;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;

    public FormEventTemplateQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getPId(){
        return pId;
    }

    public String getPIdLike(){
	    if (pIdLike != null && pIdLike.trim().length() > 0) {
		if (!pIdLike.startsWith("%")) {
		    pIdLike = "%" + pIdLike;
		}
		if (!pIdLike.endsWith("%")) {
		   pIdLike = pIdLike + "%";
		}
	    }
	return pIdLike;
    }

    public List<String> getPIds(){
	return pIds;
    }


    public String getComplexId(){
        return complexId;
    }

    public String getComplexIdLike(){
	    if (complexIdLike != null && complexIdLike.trim().length() > 0) {
		if (!complexIdLike.startsWith("%")) {
		    complexIdLike = "%" + complexIdLike;
		}
		if (!complexIdLike.endsWith("%")) {
		   complexIdLike = complexIdLike + "%";
		}
	    }
	return complexIdLike;
    }

    public List<String> getComplexIds(){
	return complexIds;
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


    public String getRemark(){
        return remark;
    }

    public String getRemarkLike(){
	    if (remarkLike != null && remarkLike.trim().length() > 0) {
		if (!remarkLike.startsWith("%")) {
		    remarkLike = "%" + remarkLike;
		}
		if (!remarkLike.endsWith("%")) {
		   remarkLike = remarkLike + "%";
		}
	    }
	return remarkLike;
    }

    public List<String> getRemarks(){
	return remarks;
    }


    public String getDiagram(){
        return diagram;
    }

    public String getDiagramLike(){
	    if (diagramLike != null && diagramLike.trim().length() > 0) {
		if (!diagramLike.startsWith("%")) {
		    diagramLike = "%" + diagramLike;
		}
		if (!diagramLike.endsWith("%")) {
		   diagramLike = diagramLike + "%";
		}
	    }
	return diagramLike;
    }

    public List<String> getDiagrams(){
	return diagrams;
    }


    public String getRule(){
        return rule;
    }

    public String getRuleLike(){
	    if (ruleLike != null && ruleLike.trim().length() > 0) {
		if (!ruleLike.startsWith("%")) {
		    ruleLike = "%" + ruleLike;
		}
		if (!ruleLike.endsWith("%")) {
		   ruleLike = ruleLike + "%";
		}
	    }
	return ruleLike;
    }

    public List<String> getRules(){
	return rules;
    }


    public String getPageId(){
        return pageId;
    }

    public String getPageIdLike(){
	    if (pageIdLike != null && pageIdLike.trim().length() > 0) {
		if (!pageIdLike.startsWith("%")) {
		    pageIdLike = "%" + pageIdLike;
		}
		if (!pageIdLike.endsWith("%")) {
		   pageIdLike = pageIdLike + "%";
		}
	    }
	return pageIdLike;
    }

    public List<String> getPageIds(){
	return pageIds;
    }


    public String getComplexRule(){
        return complexRule;
    }

    public String getComplexRuleLike(){
	    if (complexRuleLike != null && complexRuleLike.trim().length() > 0) {
		if (!complexRuleLike.startsWith("%")) {
		    complexRuleLike = "%" + complexRuleLike;
		}
		if (!complexRuleLike.endsWith("%")) {
		   complexRuleLike = complexRuleLike + "%";
		}
	    }
	return complexRuleLike;
    }

    public List<String> getComplexRules(){
	return complexRules;
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

    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
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


    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
    }


 

    public void setPId(String pId){
        this.pId = pId;
    }

    public void setPIdLike( String pIdLike){
	this.pIdLike = pIdLike;
    }

    public void setPIds(List<String> pIds){
        this.pIds = pIds;
    }


    public void setComplexId(String complexId){
        this.complexId = complexId;
    }

    public void setComplexIdLike( String complexIdLike){
	this.complexIdLike = complexIdLike;
    }

    public void setComplexIds(List<String> complexIds){
        this.complexIds = complexIds;
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


    public void setRemark(String remark){
        this.remark = remark;
    }

    public void setRemarkLike( String remarkLike){
	this.remarkLike = remarkLike;
    }

    public void setRemarks(List<String> remarks){
        this.remarks = remarks;
    }


    public void setDiagram(String diagram){
        this.diagram = diagram;
    }

    public void setDiagramLike( String diagramLike){
	this.diagramLike = diagramLike;
    }

    public void setDiagrams(List<String> diagrams){
        this.diagrams = diagrams;
    }


    public void setRule(String rule){
        this.rule = rule;
    }

    public void setRuleLike( String ruleLike){
	this.ruleLike = ruleLike;
    }

    public void setRules(List<String> rules){
        this.rules = rules;
    }


    public void setPageId(String pageId){
        this.pageId = pageId;
    }

    public void setPageIdLike( String pageIdLike){
	this.pageIdLike = pageIdLike;
    }

    public void setPageIds(List<String> pageIds){
        this.pageIds = pageIds;
    }


    public void setComplexRule(String complexRule){
        this.complexRule = complexRule;
    }

    public void setComplexRuleLike( String complexRuleLike){
	this.complexRuleLike = complexRuleLike;
    }

    public void setComplexRules(List<String> complexRules){
        this.complexRules = complexRules;
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


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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


    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    public void setUpdateByLike( String updateByLike){
	this.updateByLike = updateByLike;
    }

    public void setUpdateBys(List<String> updateBys){
        this.updateBys = updateBys;
    }


    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
    }




    public FormEventTemplateQuery pId(String pId){
	if (pId == null) {
	    throw new RuntimeException("pId is null");
        }         
	this.pId = pId;
	return this;
    }

    public FormEventTemplateQuery pIdLike( String pIdLike){
        if (pIdLike == null) {
            throw new RuntimeException("pId is null");
        }
        this.pIdLike = pIdLike;
        return this;
    }

    public FormEventTemplateQuery pIds(List<String> pIds){
        if (pIds == null) {
            throw new RuntimeException("pIds is empty ");
        }
        this.pIds = pIds;
        return this;
    }


    public FormEventTemplateQuery complexId(String complexId){
	if (complexId == null) {
	    throw new RuntimeException("complexId is null");
        }         
	this.complexId = complexId;
	return this;
    }

    public FormEventTemplateQuery complexIdLike( String complexIdLike){
        if (complexIdLike == null) {
            throw new RuntimeException("complexId is null");
        }
        this.complexIdLike = complexIdLike;
        return this;
    }

    public FormEventTemplateQuery complexIds(List<String> complexIds){
        if (complexIds == null) {
            throw new RuntimeException("complexIds is empty ");
        }
        this.complexIds = complexIds;
        return this;
    }


    public FormEventTemplateQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormEventTemplateQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormEventTemplateQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormEventTemplateQuery remark(String remark){
	if (remark == null) {
	    throw new RuntimeException("remark is null");
        }         
	this.remark = remark;
	return this;
    }

    public FormEventTemplateQuery remarkLike( String remarkLike){
        if (remarkLike == null) {
            throw new RuntimeException("remark is null");
        }
        this.remarkLike = remarkLike;
        return this;
    }

    public FormEventTemplateQuery remarks(List<String> remarks){
        if (remarks == null) {
            throw new RuntimeException("remarks is empty ");
        }
        this.remarks = remarks;
        return this;
    }


    public FormEventTemplateQuery diagram(String diagram){
	if (diagram == null) {
	    throw new RuntimeException("diagram is null");
        }         
	this.diagram = diagram;
	return this;
    }

    public FormEventTemplateQuery diagramLike( String diagramLike){
        if (diagramLike == null) {
            throw new RuntimeException("diagram is null");
        }
        this.diagramLike = diagramLike;
        return this;
    }

    public FormEventTemplateQuery diagrams(List<String> diagrams){
        if (diagrams == null) {
            throw new RuntimeException("diagrams is empty ");
        }
        this.diagrams = diagrams;
        return this;
    }


    public FormEventTemplateQuery rule(String rule){
	if (rule == null) {
	    throw new RuntimeException("rule is null");
        }         
	this.rule = rule;
	return this;
    }

    public FormEventTemplateQuery ruleLike( String ruleLike){
        if (ruleLike == null) {
            throw new RuntimeException("rule is null");
        }
        this.ruleLike = ruleLike;
        return this;
    }

    public FormEventTemplateQuery rules(List<String> rules){
        if (rules == null) {
            throw new RuntimeException("rules is empty ");
        }
        this.rules = rules;
        return this;
    }


    public FormEventTemplateQuery pageId(String pageId){
	if (pageId == null) {
	    throw new RuntimeException("pageId is null");
        }         
	this.pageId = pageId;
	return this;
    }

    public FormEventTemplateQuery pageIdLike( String pageIdLike){
        if (pageIdLike == null) {
            throw new RuntimeException("pageId is null");
        }
        this.pageIdLike = pageIdLike;
        return this;
    }

    public FormEventTemplateQuery pageIds(List<String> pageIds){
        if (pageIds == null) {
            throw new RuntimeException("pageIds is empty ");
        }
        this.pageIds = pageIds;
        return this;
    }


    public FormEventTemplateQuery complexRule(String complexRule){
	if (complexRule == null) {
	    throw new RuntimeException("complexRule is null");
        }         
	this.complexRule = complexRule;
	return this;
    }

    public FormEventTemplateQuery complexRuleLike( String complexRuleLike){
        if (complexRuleLike == null) {
            throw new RuntimeException("complexRule is null");
        }
        this.complexRuleLike = complexRuleLike;
        return this;
    }

    public FormEventTemplateQuery complexRules(List<String> complexRules){
        if (complexRules == null) {
            throw new RuntimeException("complexRules is empty ");
        }
        this.complexRules = complexRules;
        return this;
    }


    public FormEventTemplateQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public FormEventTemplateQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public FormEventTemplateQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public FormEventTemplateQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }



    public FormEventTemplateQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormEventTemplateQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public FormEventTemplateQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormEventTemplateQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormEventTemplateQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }


    public FormEventTemplateQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public FormEventTemplateQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public FormEventTemplateQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public FormEventTemplateQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public FormEventTemplateQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("pId".equals(sortColumn)) {
                orderBy = "E.PID_" + a_x;
            } 

            if ("complexId".equals(sortColumn)) {
                orderBy = "E.COMPLEXID_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("remark".equals(sortColumn)) {
                orderBy = "E.REMARK_" + a_x;
            } 

            if ("diagram".equals(sortColumn)) {
                orderBy = "E.DIAGRAM_" + a_x;
            } 

            if ("rule".equals(sortColumn)) {
                orderBy = "E.RULE_" + a_x;
            } 

            if ("pageId".equals(sortColumn)) {
                orderBy = "E.PAGEID_" + a_x;
            } 

            if ("complexRule".equals(sortColumn)) {
                orderBy = "E.COMPLEX_RULE_" + a_x;
            } 

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETEFLAG_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("pId", "PID_");
        addColumn("complexId", "COMPLEXID_");
        addColumn("name", "NAME_");
        addColumn("remark", "REMARK_");
        addColumn("diagram", "DIAGRAM_");
        addColumn("rule", "RULE_");
        addColumn("pageId", "PAGEID_");
        addColumn("complexRule", "COMPLEX_RULE_");
        addColumn("deleteFlag", "DELETEFLAG_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("createBy", "CREATEBY_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateDate", "UPDATEDATE_");
    }

}