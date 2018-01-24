package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormFileQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String parent;
  	protected String parentLike;
  	protected List<String> parents;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
  	protected String fieldType;
  	protected String fieldTypeLike;
  	protected List<String> fieldTypes;
  	protected String fileName;
  	protected String fileNameLike;
  	protected List<String> fileNames;
  	protected Integer fileSize;
  	protected Integer fileSizeGreaterThanOrEqual;
  	protected Integer fileSizeLessThanOrEqual;
  	protected List<Integer> fileSizes;
  	protected String fileContent;
  	protected String fileContentLike;
  	protected List<String> fileContents;
  	protected String saveServicePath;
  	protected String saveServicePathLike;
  	protected List<String> saveServicePaths;
  	protected Integer vision;
  	protected Integer visionGreaterThanOrEqual;
  	protected Integer visionLessThanOrEqual;
  	protected List<Integer> visions;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected String error;
  	protected String errorLike;
  	protected List<String> errors;
  	protected String business;
  	protected String businessLike;
  	protected List<String> businesss;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;

    public FormFileQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getParent(){
        return parent;
    }

    public String getParentLike(){
	    if (parentLike != null && parentLike.trim().length() > 0) {
		if (!parentLike.startsWith("%")) {
		    parentLike = "%" + parentLike;
		}
		if (!parentLike.endsWith("%")) {
		   parentLike = parentLike + "%";
		}
	    }
	return parentLike;
    }

    public List<String> getParents(){
	return parents;
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


    public String getFieldType(){
        return fieldType;
    }

    public String getFieldTypeLike(){
	    if (fieldTypeLike != null && fieldTypeLike.trim().length() > 0) {
		if (!fieldTypeLike.startsWith("%")) {
		    fieldTypeLike = "%" + fieldTypeLike;
		}
		if (!fieldTypeLike.endsWith("%")) {
		   fieldTypeLike = fieldTypeLike + "%";
		}
	    }
	return fieldTypeLike;
    }

    public List<String> getFieldTypes(){
	return fieldTypes;
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


    public Integer getFileSize(){
        return fileSize;
    }

    public Integer getFileSizeGreaterThanOrEqual(){
        return fileSizeGreaterThanOrEqual;
    }

    public Integer getFileSizeLessThanOrEqual(){
	return fileSizeLessThanOrEqual;
    }

    public List<Integer> getFileSizes(){
	return fileSizes;
    }

    public String getFileContent(){
        return fileContent;
    }

    public String getFileContentLike(){
	    if (fileContentLike != null && fileContentLike.trim().length() > 0) {
		if (!fileContentLike.startsWith("%")) {
		    fileContentLike = "%" + fileContentLike;
		}
		if (!fileContentLike.endsWith("%")) {
		   fileContentLike = fileContentLike + "%";
		}
	    }
	return fileContentLike;
    }

    public List<String> getFileContents(){
	return fileContents;
    }


    public String getSaveServicePath(){
        return saveServicePath;
    }

    public String getSaveServicePathLike(){
	    if (saveServicePathLike != null && saveServicePathLike.trim().length() > 0) {
		if (!saveServicePathLike.startsWith("%")) {
		    saveServicePathLike = "%" + saveServicePathLike;
		}
		if (!saveServicePathLike.endsWith("%")) {
		   saveServicePathLike = saveServicePathLike + "%";
		}
	    }
	return saveServicePathLike;
    }

    public List<String> getSaveServicePaths(){
	return saveServicePaths;
    }


    public Integer getVision(){
        return vision;
    }

    public Integer getVisionGreaterThanOrEqual(){
        return visionGreaterThanOrEqual;
    }

    public Integer getVisionLessThanOrEqual(){
	return visionLessThanOrEqual;
    }

    public List<Integer> getVisions(){
	return visions;
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

    public String getError(){
        return error;
    }

    public String getErrorLike(){
	    if (errorLike != null && errorLike.trim().length() > 0) {
		if (!errorLike.startsWith("%")) {
		    errorLike = "%" + errorLike;
		}
		if (!errorLike.endsWith("%")) {
		   errorLike = errorLike + "%";
		}
	    }
	return errorLike;
    }

    public List<String> getErrors(){
	return errors;
    }


    public String getBusiness(){
        return business;
    }

    public String getBusinessLike(){
	    if (businessLike != null && businessLike.trim().length() > 0) {
		if (!businessLike.startsWith("%")) {
		    businessLike = "%" + businessLike;
		}
		if (!businessLike.endsWith("%")) {
		   businessLike = businessLike + "%";
		}
	    }
	return businessLike;
    }

    public List<String> getBusinesss(){
	return businesss;
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


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
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


 

    public void setParent(String parent){
        this.parent = parent;
    }

    public void setParentLike( String parentLike){
	this.parentLike = parentLike;
    }

    public void setParents(List<String> parents){
        this.parents = parents;
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


    public void setFieldType(String fieldType){
        this.fieldType = fieldType;
    }

    public void setFieldTypeLike( String fieldTypeLike){
	this.fieldTypeLike = fieldTypeLike;
    }

    public void setFieldTypes(List<String> fieldTypes){
        this.fieldTypes = fieldTypes;
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


    public void setFileSize(Integer fileSize){
        this.fileSize = fileSize;
    }

    public void setFileSizeGreaterThanOrEqual(Integer fileSizeGreaterThanOrEqual){
        this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
    }

    public void setFileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual){
	this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
    }

    public void setFileSizes(List<Integer> fileSizes){
        this.fileSizes = fileSizes;
    }


    public void setFileContent(String fileContent){
        this.fileContent = fileContent;
    }

    public void setFileContentLike( String fileContentLike){
	this.fileContentLike = fileContentLike;
    }

    public void setFileContents(List<String> fileContents){
        this.fileContents = fileContents;
    }


    public void setSaveServicePath(String saveServicePath){
        this.saveServicePath = saveServicePath;
    }

    public void setSaveServicePathLike( String saveServicePathLike){
	this.saveServicePathLike = saveServicePathLike;
    }

    public void setSaveServicePaths(List<String> saveServicePaths){
        this.saveServicePaths = saveServicePaths;
    }


    public void setVision(Integer vision){
        this.vision = vision;
    }

    public void setVisionGreaterThanOrEqual(Integer visionGreaterThanOrEqual){
        this.visionGreaterThanOrEqual = visionGreaterThanOrEqual;
    }

    public void setVisionLessThanOrEqual(Integer visionLessThanOrEqual){
	this.visionLessThanOrEqual = visionLessThanOrEqual;
    }

    public void setVisions(List<Integer> visions){
        this.visions = visions;
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


    public void setError(String error){
        this.error = error;
    }

    public void setErrorLike( String errorLike){
	this.errorLike = errorLike;
    }

    public void setErrors(List<String> errors){
        this.errors = errors;
    }


    public void setBusiness(String business){
        this.business = business;
    }

    public void setBusinessLike( String businessLike){
	this.businessLike = businessLike;
    }

    public void setBusinesss(List<String> businesss){
        this.businesss = businesss;
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


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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




    public FormFileQuery parent(String parent){
	if (parent == null) {
	    throw new RuntimeException("parent is null");
        }         
	this.parent = parent;
	return this;
    }

    public FormFileQuery parentLike( String parentLike){
        if (parentLike == null) {
            throw new RuntimeException("parent is null");
        }
        this.parentLike = parentLike;
        return this;
    }

    public FormFileQuery parents(List<String> parents){
        if (parents == null) {
            throw new RuntimeException("parents is empty ");
        }
        this.parents = parents;
        return this;
    }


    public FormFileQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public FormFileQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public FormFileQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public FormFileQuery fieldType(String fieldType){
	if (fieldType == null) {
	    throw new RuntimeException("fieldType is null");
        }         
	this.fieldType = fieldType;
	return this;
    }

    public FormFileQuery fieldTypeLike( String fieldTypeLike){
        if (fieldTypeLike == null) {
            throw new RuntimeException("fieldType is null");
        }
        this.fieldTypeLike = fieldTypeLike;
        return this;
    }

    public FormFileQuery fieldTypes(List<String> fieldTypes){
        if (fieldTypes == null) {
            throw new RuntimeException("fieldTypes is empty ");
        }
        this.fieldTypes = fieldTypes;
        return this;
    }


    public FormFileQuery fileName(String fileName){
	if (fileName == null) {
	    throw new RuntimeException("fileName is null");
        }         
	this.fileName = fileName;
	return this;
    }

    public FormFileQuery fileNameLike( String fileNameLike){
        if (fileNameLike == null) {
            throw new RuntimeException("fileName is null");
        }
        this.fileNameLike = fileNameLike;
        return this;
    }

    public FormFileQuery fileNames(List<String> fileNames){
        if (fileNames == null) {
            throw new RuntimeException("fileNames is empty ");
        }
        this.fileNames = fileNames;
        return this;
    }


    public FormFileQuery fileSize(Integer fileSize){
	if (fileSize == null) {
            throw new RuntimeException("fileSize is null");
        }         
	this.fileSize = fileSize;
	return this;
    }

    public FormFileQuery fileSizeGreaterThanOrEqual(Integer fileSizeGreaterThanOrEqual){
	if (fileSizeGreaterThanOrEqual == null) {
	    throw new RuntimeException("fileSize is null");
        }         
	this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
        return this;
    }

    public FormFileQuery fileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual){
        if (fileSizeLessThanOrEqual == null) {
            throw new RuntimeException("fileSize is null");
        }
        this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
        return this;
    }

    public FormFileQuery fileSizes(List<Integer> fileSizes){
        if (fileSizes == null) {
            throw new RuntimeException("fileSizes is empty ");
        }
        this.fileSizes = fileSizes;
        return this;
    }


    public FormFileQuery fileContent(String fileContent){
	if (fileContent == null) {
	    throw new RuntimeException("fileContent is null");
        }         
	this.fileContent = fileContent;
	return this;
    }

    public FormFileQuery fileContentLike( String fileContentLike){
        if (fileContentLike == null) {
            throw new RuntimeException("fileContent is null");
        }
        this.fileContentLike = fileContentLike;
        return this;
    }

    public FormFileQuery fileContents(List<String> fileContents){
        if (fileContents == null) {
            throw new RuntimeException("fileContents is empty ");
        }
        this.fileContents = fileContents;
        return this;
    }


    public FormFileQuery saveServicePath(String saveServicePath){
	if (saveServicePath == null) {
	    throw new RuntimeException("saveServicePath is null");
        }         
	this.saveServicePath = saveServicePath;
	return this;
    }

    public FormFileQuery saveServicePathLike( String saveServicePathLike){
        if (saveServicePathLike == null) {
            throw new RuntimeException("saveServicePath is null");
        }
        this.saveServicePathLike = saveServicePathLike;
        return this;
    }

    public FormFileQuery saveServicePaths(List<String> saveServicePaths){
        if (saveServicePaths == null) {
            throw new RuntimeException("saveServicePaths is empty ");
        }
        this.saveServicePaths = saveServicePaths;
        return this;
    }


    public FormFileQuery vision(Integer vision){
	if (vision == null) {
            throw new RuntimeException("vision is null");
        }         
	this.vision = vision;
	return this;
    }

    public FormFileQuery visionGreaterThanOrEqual(Integer visionGreaterThanOrEqual){
	if (visionGreaterThanOrEqual == null) {
	    throw new RuntimeException("vision is null");
        }         
	this.visionGreaterThanOrEqual = visionGreaterThanOrEqual;
        return this;
    }

    public FormFileQuery visionLessThanOrEqual(Integer visionLessThanOrEqual){
        if (visionLessThanOrEqual == null) {
            throw new RuntimeException("vision is null");
        }
        this.visionLessThanOrEqual = visionLessThanOrEqual;
        return this;
    }

    public FormFileQuery visions(List<Integer> visions){
        if (visions == null) {
            throw new RuntimeException("visions is empty ");
        }
        this.visions = visions;
        return this;
    }


    public FormFileQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public FormFileQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public FormFileQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public FormFileQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public FormFileQuery error(String error){
	if (error == null) {
	    throw new RuntimeException("error is null");
        }         
	this.error = error;
	return this;
    }

    public FormFileQuery errorLike( String errorLike){
        if (errorLike == null) {
            throw new RuntimeException("error is null");
        }
        this.errorLike = errorLike;
        return this;
    }

    public FormFileQuery errors(List<String> errors){
        if (errors == null) {
            throw new RuntimeException("errors is empty ");
        }
        this.errors = errors;
        return this;
    }


    public FormFileQuery business(String business){
	if (business == null) {
	    throw new RuntimeException("business is null");
        }         
	this.business = business;
	return this;
    }

    public FormFileQuery businessLike( String businessLike){
        if (businessLike == null) {
            throw new RuntimeException("business is null");
        }
        this.businessLike = businessLike;
        return this;
    }

    public FormFileQuery businesss(List<String> businesss){
        if (businesss == null) {
            throw new RuntimeException("businesss is empty ");
        }
        this.businesss = businesss;
        return this;
    }


    public FormFileQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormFileQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormFileQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public FormFileQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormFileQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public FormFileQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public FormFileQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public FormFileQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public FormFileQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public FormFileQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
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

            if ("parent".equals(sortColumn)) {
                orderBy = "E.PARENT_" + a_x;
            } 

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("fieldType".equals(sortColumn)) {
                orderBy = "E.FILETYPE_" + a_x;
            } 

            if ("fileName".equals(sortColumn)) {
                orderBy = "E.FILENAME_" + a_x;
            } 

            if ("fileSize".equals(sortColumn)) {
                orderBy = "E.FILESIZE_" + a_x;
            } 

            if ("fileContent".equals(sortColumn)) {
                orderBy = "E.FILECONTENT_" + a_x;
            } 

            if ("saveServicePath".equals(sortColumn)) {
                orderBy = "E.SAVESERVICEPATH_" + a_x;
            } 

            if ("vision".equals(sortColumn)) {
                orderBy = "E.VISION_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("error".equals(sortColumn)) {
                orderBy = "E.ERROR" + a_x;
            } 

            if ("business".equals(sortColumn)) {
                orderBy = "E.BUSINESS" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("parent", "PARENT_");
        addColumn("type", "TYPE_");
        addColumn("fieldType", "FILETYPE_");
        addColumn("fileName", "FILENAME_");
        addColumn("fileSize", "FILESIZE_");
        addColumn("fileContent", "FILECONTENT_");
        addColumn("saveServicePath", "SAVESERVICEPATH_");
        addColumn("vision", "VISION_");
        addColumn("status", "STATUS_");
        addColumn("error", "ERROR");
        addColumn("business", "BUSINESS");
        addColumn("createBy", "CREATEBY");
        addColumn("createDate", "CREATEDATE");
        addColumn("updateBy", "UPDATEBY");
        addColumn("updateDate", "UPDATEDATE");
    }

}