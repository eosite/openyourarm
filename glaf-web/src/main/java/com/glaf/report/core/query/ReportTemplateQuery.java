package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTemplateQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected Integer rev;
  	protected Integer revGreaterThanOrEqual;
  	protected Integer revLessThanOrEqual;
  	protected List<Integer> revs;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDatatimeGreaterThanOrEqual;
  	protected Date createDatatimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date modifyDatatimeGreaterThanOrEqual;
  	protected Date modifyDatatimeLessThanOrEqual;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected Integer publish;
  	protected Integer publishGreaterThanOrEqual;
  	protected Integer publishLessThanOrEqual;
  	protected List<Integer> publishs;
  	protected String publishUser;
  	protected String publishUserLike;
  	protected List<String> publishUsers;
    protected Date publishDatetimeGreaterThanOrEqual;
  	protected Date publishDatetimeLessThanOrEqual;
  	protected String fileName;
  	protected String fileNameLike;
  	protected List<String> fileNames;
  	protected String ext;
  	protected String extLike;
  	protected List<String> exts;
  	protected Integer categoryId;

    public ReportTemplateQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Integer getRev(){
        return rev;
    }

    public Integer getRevGreaterThanOrEqual(){
        return revGreaterThanOrEqual;
    }

    public Integer getRevLessThanOrEqual(){
	return revLessThanOrEqual;
    }

    public List<Integer> getRevs(){
	return revs;
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


    public Date getCreateDatatimeGreaterThanOrEqual(){
        return createDatatimeGreaterThanOrEqual;
    }

    public Date getCreateDatatimeLessThanOrEqual(){
	return createDatatimeLessThanOrEqual;
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


    public Date getModifyDatatimeGreaterThanOrEqual(){
        return modifyDatatimeGreaterThanOrEqual;
    }

    public Date getModifyDatatimeLessThanOrEqual(){
	return modifyDatatimeLessThanOrEqual;
    }


    @Override
	public Integer getStatus(){
        return status;
    }

    @Override
	public Integer getStatusGreaterThanOrEqual(){
        return statusGreaterThanOrEqual;
    }

    @Override
	public Integer getStatusLessThanOrEqual(){
	return statusLessThanOrEqual;
    }

    public List<Integer> getStatuss(){
	return statuss;
    }

    public Integer getPublish(){
        return publish;
    }

    public Integer getPublishGreaterThanOrEqual(){
        return publishGreaterThanOrEqual;
    }

    public Integer getPublishLessThanOrEqual(){
	return publishLessThanOrEqual;
    }

    public List<Integer> getPublishs(){
	return publishs;
    }

    public String getPublishUser(){
        return publishUser;
    }

    public String getPublishUserLike(){
	    if (publishUserLike != null && publishUserLike.trim().length() > 0) {
		if (!publishUserLike.startsWith("%")) {
		    publishUserLike = "%" + publishUserLike;
		}
		if (!publishUserLike.endsWith("%")) {
		   publishUserLike = publishUserLike + "%";
		}
	    }
	return publishUserLike;
    }

    public List<String> getPublishUsers(){
	return publishUsers;
    }


    public Date getPublishDatetimeGreaterThanOrEqual(){
        return publishDatetimeGreaterThanOrEqual;
    }

    public Date getPublishDatetimeLessThanOrEqual(){
	return publishDatetimeLessThanOrEqual;
    }


    public String getFileName(){
        return fileName;
    }

    public String getFileNameLike(){
	    if (fileNameLike != null && fileNameLike.trim().length() > 0) {
		if (!fileNameLike.startsWith("%")) {
		    fileNameLike = "%" + fileNameLike;
		}
		if (!fileNameLike.endsWith("%")) {
		   fileNameLike = fileNameLike + "%";
		}
	    }
	return fileNameLike;
    }

    public List<String> getFileNames(){
	return fileNames;
    }


    public String getExt(){
        return ext;
    }

    public String getExtLike(){
	    if (extLike != null && extLike.trim().length() > 0) {
		if (!extLike.startsWith("%")) {
		    extLike = "%" + extLike;
		}
		if (!extLike.endsWith("%")) {
		   extLike = extLike + "%";
		}
	    }
	return extLike;
    }

    public List<String> getExts(){
	return exts;
    }


 

    public void setRev(Integer rev){
        this.rev = rev;
    }

    public void setRevGreaterThanOrEqual(Integer revGreaterThanOrEqual){
        this.revGreaterThanOrEqual = revGreaterThanOrEqual;
    }

    public void setRevLessThanOrEqual(Integer revLessThanOrEqual){
	this.revLessThanOrEqual = revLessThanOrEqual;
    }

    public void setRevs(List<Integer> revs){
        this.revs = revs;
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


    public void setCode(String code){
        this.code = code;
    }

    public void setCodeLike( String codeLike){
	this.codeLike = codeLike;
    }

    public void setCodes(List<String> codes){
        this.codes = codes;
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


    public void setCreateDatatimeGreaterThanOrEqual(Date createDatatimeGreaterThanOrEqual){
        this.createDatatimeGreaterThanOrEqual = createDatatimeGreaterThanOrEqual;
    }

    public void setCreateDatatimeLessThanOrEqual(Date createDatatimeLessThanOrEqual){
	this.createDatatimeLessThanOrEqual = createDatatimeLessThanOrEqual;
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


    public void setModifyDatatimeGreaterThanOrEqual(Date modifyDatatimeGreaterThanOrEqual){
        this.modifyDatatimeGreaterThanOrEqual = modifyDatatimeGreaterThanOrEqual;
    }

    public void setModifyDatatimeLessThanOrEqual(Date modifyDatatimeLessThanOrEqual){
	this.modifyDatatimeLessThanOrEqual = modifyDatatimeLessThanOrEqual;
    }


    @Override
	public void setStatus(Integer status){
        this.status = status;
    }

    @Override
	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
        this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
    }

    @Override
	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual){
	this.statusLessThanOrEqual = statusLessThanOrEqual;
    }

    public void setStatuss(List<Integer> statuss){
        this.statuss = statuss;
    }


    public void setPublish(Integer publish){
        this.publish = publish;
    }

    public void setPublishGreaterThanOrEqual(Integer publishGreaterThanOrEqual){
        this.publishGreaterThanOrEqual = publishGreaterThanOrEqual;
    }

    public void setPublishLessThanOrEqual(Integer publishLessThanOrEqual){
	this.publishLessThanOrEqual = publishLessThanOrEqual;
    }

    public void setPublishs(List<Integer> publishs){
        this.publishs = publishs;
    }


    public void setPublishUser(String publishUser){
        this.publishUser = publishUser;
    }

    public void setPublishUserLike( String publishUserLike){
	this.publishUserLike = publishUserLike;
    }

    public void setPublishUsers(List<String> publishUsers){
        this.publishUsers = publishUsers;
    }


    public void setPublishDatetimeGreaterThanOrEqual(Date publishDatetimeGreaterThanOrEqual){
        this.publishDatetimeGreaterThanOrEqual = publishDatetimeGreaterThanOrEqual;
    }

    public void setPublishDatetimeLessThanOrEqual(Date publishDatetimeLessThanOrEqual){
	this.publishDatetimeLessThanOrEqual = publishDatetimeLessThanOrEqual;
    }


    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public void setFileNameLike( String fileNameLike){
	this.fileNameLike = fileNameLike;
    }

    public void setFileNames(List<String> fileNames){
        this.fileNames = fileNames;
    }


    public void setExt(String ext){
        this.ext = ext;
    }

    public void setExtLike( String extLike){
	this.extLike = extLike;
    }

    public void setExts(List<String> exts){
        this.exts = exts;
    }




    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public ReportTemplateQuery rev(Integer rev){
	if (rev == null) {
            throw new RuntimeException("rev is null");
        }         
	this.rev = rev;
	return this;
    }

    public ReportTemplateQuery revGreaterThanOrEqual(Integer revGreaterThanOrEqual){
	if (revGreaterThanOrEqual == null) {
	    throw new RuntimeException("rev is null");
        }         
	this.revGreaterThanOrEqual = revGreaterThanOrEqual;
        return this;
    }

    public ReportTemplateQuery revLessThanOrEqual(Integer revLessThanOrEqual){
        if (revLessThanOrEqual == null) {
            throw new RuntimeException("rev is null");
        }
        this.revLessThanOrEqual = revLessThanOrEqual;
        return this;
    }

    public ReportTemplateQuery revs(List<Integer> revs){
        if (revs == null) {
            throw new RuntimeException("revs is empty ");
        }
        this.revs = revs;
        return this;
    }


    public ReportTemplateQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public ReportTemplateQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public ReportTemplateQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public ReportTemplateQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public ReportTemplateQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public ReportTemplateQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public ReportTemplateQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTemplateQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTemplateQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTemplateQuery createDatatimeGreaterThanOrEqual(Date createDatatimeGreaterThanOrEqual){
	if (createDatatimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatatime is null");
        }         
	this.createDatatimeGreaterThanOrEqual = createDatatimeGreaterThanOrEqual;
        return this;
    }

    public ReportTemplateQuery createDatatimeLessThanOrEqual(Date createDatatimeLessThanOrEqual){
        if (createDatatimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatatime is null");
        }
        this.createDatatimeLessThanOrEqual = createDatatimeLessThanOrEqual;
        return this;
    }



    public ReportTemplateQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportTemplateQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportTemplateQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportTemplateQuery modifyDatatimeGreaterThanOrEqual(Date modifyDatatimeGreaterThanOrEqual){
	if (modifyDatatimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatatime is null");
        }         
	this.modifyDatatimeGreaterThanOrEqual = modifyDatatimeGreaterThanOrEqual;
        return this;
    }

    public ReportTemplateQuery modifyDatatimeLessThanOrEqual(Date modifyDatatimeLessThanOrEqual){
        if (modifyDatatimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDatatime is null");
        }
        this.modifyDatatimeLessThanOrEqual = modifyDatatimeLessThanOrEqual;
        return this;
    }



    @Override
	public ReportTemplateQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    @Override
	public ReportTemplateQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    @Override
	public ReportTemplateQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public ReportTemplateQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public ReportTemplateQuery publish(Integer publish){
	if (publish == null) {
            throw new RuntimeException("publish is null");
        }         
	this.publish = publish;
	return this;
    }

    public ReportTemplateQuery publishGreaterThanOrEqual(Integer publishGreaterThanOrEqual){
	if (publishGreaterThanOrEqual == null) {
	    throw new RuntimeException("publish is null");
        }         
	this.publishGreaterThanOrEqual = publishGreaterThanOrEqual;
        return this;
    }

    public ReportTemplateQuery publishLessThanOrEqual(Integer publishLessThanOrEqual){
        if (publishLessThanOrEqual == null) {
            throw new RuntimeException("publish is null");
        }
        this.publishLessThanOrEqual = publishLessThanOrEqual;
        return this;
    }

    public ReportTemplateQuery publishs(List<Integer> publishs){
        if (publishs == null) {
            throw new RuntimeException("publishs is empty ");
        }
        this.publishs = publishs;
        return this;
    }


    public ReportTemplateQuery publishUser(String publishUser){
	if (publishUser == null) {
	    throw new RuntimeException("publishUser is null");
        }         
	this.publishUser = publishUser;
	return this;
    }

    public ReportTemplateQuery publishUserLike( String publishUserLike){
        if (publishUserLike == null) {
            throw new RuntimeException("publishUser is null");
        }
        this.publishUserLike = publishUserLike;
        return this;
    }

    public ReportTemplateQuery publishUsers(List<String> publishUsers){
        if (publishUsers == null) {
            throw new RuntimeException("publishUsers is empty ");
        }
        this.publishUsers = publishUsers;
        return this;
    }



    public ReportTemplateQuery publishDatetimeGreaterThanOrEqual(Date publishDatetimeGreaterThanOrEqual){
	if (publishDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("publishDatetime is null");
        }         
	this.publishDatetimeGreaterThanOrEqual = publishDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTemplateQuery publishDatetimeLessThanOrEqual(Date publishDatetimeLessThanOrEqual){
        if (publishDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("publishDatetime is null");
        }
        this.publishDatetimeLessThanOrEqual = publishDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTemplateQuery fileName(String fileName){
	if (fileName == null) {
	    throw new RuntimeException("fileName is null");
        }         
	this.fileName = fileName;
	return this;
    }

    public ReportTemplateQuery fileNameLike( String fileNameLike){
        if (fileNameLike == null) {
            throw new RuntimeException("fileName is null");
        }
        this.fileNameLike = fileNameLike;
        return this;
    }

    public ReportTemplateQuery fileNames(List<String> fileNames){
        if (fileNames == null) {
            throw new RuntimeException("fileNames is empty ");
        }
        this.fileNames = fileNames;
        return this;
    }


    public ReportTemplateQuery ext(String ext){
	if (ext == null) {
	    throw new RuntimeException("ext is null");
        }         
	this.ext = ext;
	return this;
    }

    public ReportTemplateQuery extLike( String extLike){
        if (extLike == null) {
            throw new RuntimeException("ext is null");
        }
        this.extLike = extLike;
        return this;
    }

    public ReportTemplateQuery exts(List<String> exts){
        if (exts == null) {
            throw new RuntimeException("exts is empty ");
        }
        this.exts = exts;
        return this;
    }



    @Override
	public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("rev".equals(sortColumn)) {
                orderBy = "E.REV_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("bytes".equals(sortColumn)) {
                orderBy = "E.BYTES_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDatatime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("modifyDatatime".equals(sortColumn)) {
                orderBy = "E.MODIFYDATETIME_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS" + a_x;
            } 

            if ("publish".equals(sortColumn)) {
                orderBy = "E.PUBLISH_" + a_x;
            } 

            if ("publishUser".equals(sortColumn)) {
                orderBy = "E.PUBLISH_USER_" + a_x;
            } 

            if ("publishDatetime".equals(sortColumn)) {
                orderBy = "E.PUBLISHDATETIME" + a_x;
            } 

            if ("fileName".equals(sortColumn)) {
                orderBy = "E.FILENAME_" + a_x;
            } 

            if ("ext".equals(sortColumn)) {
                orderBy = "E.EXT_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("rev", "REV_");
        addColumn("name", "NAME_");
        addColumn("code", "CODE_");
        addColumn("bytes", "BYTES_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatatime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatatime", "MODIFYDATETIME_");
        addColumn("status", "STATUS");
        addColumn("publish", "PUBLISH_");
        addColumn("publishUser", "PUBLISH_USER_");
        addColumn("publishDatetime", "PUBLISHDATETIME");
        addColumn("fileName", "FILENAME_");
        addColumn("ext", "EXT_");
    }

}