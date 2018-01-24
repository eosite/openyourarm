package com.glaf.maildata.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EmailRecAttQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> fileIds;
	protected Collection<String> appActorIds;
  	protected String topId;
  	protected String topIdLike;
  	protected List<String> topIds;
  	protected String fileName;
  	protected String fileNameLike;
  	protected List<String> fileNames;
        protected Date cTimeGreaterThanOrEqual;
  	protected Date cTimeLessThanOrEqual;
  	protected Integer fileSize;
  	protected Integer fileSizeGreaterThanOrEqual;
  	protected Integer fileSizeLessThanOrEqual;
  	protected List<Integer> fileSizes;

    public EmailRecAttQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTopId(){
        return topId;
    }

    public String getTopIdLike(){
	    if (topIdLike != null && topIdLike.trim().length() > 0) {
		if (!topIdLike.startsWith("%")) {
		    topIdLike = "%" + topIdLike;
		}
		if (!topIdLike.endsWith("%")) {
		   topIdLike = topIdLike + "%";
		}
	    }
	return topIdLike;
    }

    public List<String> getTopIds(){
	return topIds;
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


    public Date getCTimeGreaterThanOrEqual(){
        return cTimeGreaterThanOrEqual;
    }

    public Date getCTimeLessThanOrEqual(){
	return cTimeLessThanOrEqual;
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

 

    public void setTopId(String topId){
        this.topId = topId;
    }

    public void setTopIdLike( String topIdLike){
	this.topIdLike = topIdLike;
    }

    public void setTopIds(List<String> topIds){
        this.topIds = topIds;
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


    public void setCTimeGreaterThanOrEqual(Date cTimeGreaterThanOrEqual){
        this.cTimeGreaterThanOrEqual = cTimeGreaterThanOrEqual;
    }

    public void setCTimeLessThanOrEqual(Date cTimeLessThanOrEqual){
	this.cTimeLessThanOrEqual = cTimeLessThanOrEqual;
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




    public EmailRecAttQuery topId(String topId){
	if (topId == null) {
	    throw new RuntimeException("topId is null");
        }         
	this.topId = topId;
	return this;
    }

    public EmailRecAttQuery topIdLike( String topIdLike){
        if (topIdLike == null) {
            throw new RuntimeException("topId is null");
        }
        this.topIdLike = topIdLike;
        return this;
    }

    public EmailRecAttQuery topIds(List<String> topIds){
        if (topIds == null) {
            throw new RuntimeException("topIds is empty ");
        }
        this.topIds = topIds;
        return this;
    }


    public EmailRecAttQuery fileName(String fileName){
	if (fileName == null) {
	    throw new RuntimeException("fileName is null");
        }         
	this.fileName = fileName;
	return this;
    }

    public EmailRecAttQuery fileNameLike( String fileNameLike){
        if (fileNameLike == null) {
            throw new RuntimeException("fileName is null");
        }
        this.fileNameLike = fileNameLike;
        return this;
    }

    public EmailRecAttQuery fileNames(List<String> fileNames){
        if (fileNames == null) {
            throw new RuntimeException("fileNames is empty ");
        }
        this.fileNames = fileNames;
        return this;
    }



    public EmailRecAttQuery cTimeGreaterThanOrEqual(Date cTimeGreaterThanOrEqual){
	if (cTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("cTime is null");
        }         
	this.cTimeGreaterThanOrEqual = cTimeGreaterThanOrEqual;
        return this;
    }

    public EmailRecAttQuery cTimeLessThanOrEqual(Date cTimeLessThanOrEqual){
        if (cTimeLessThanOrEqual == null) {
            throw new RuntimeException("cTime is null");
        }
        this.cTimeLessThanOrEqual = cTimeLessThanOrEqual;
        return this;
    }



    public EmailRecAttQuery fileSize(Integer fileSize){
	if (fileSize == null) {
            throw new RuntimeException("fileSize is null");
        }         
	this.fileSize = fileSize;
	return this;
    }

    public EmailRecAttQuery fileSizeGreaterThanOrEqual(Integer fileSizeGreaterThanOrEqual){
	if (fileSizeGreaterThanOrEqual == null) {
	    throw new RuntimeException("fileSize is null");
        }         
	this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
        return this;
    }

    public EmailRecAttQuery fileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual){
        if (fileSizeLessThanOrEqual == null) {
            throw new RuntimeException("fileSize is null");
        }
        this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
        return this;
    }

    public EmailRecAttQuery fileSizes(List<Integer> fileSizes){
        if (fileSizes == null) {
            throw new RuntimeException("fileSizes is empty ");
        }
        this.fileSizes = fileSizes;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("topId".equals(sortColumn)) {
                orderBy = "E.TOPID" + a_x;
            } 

            if ("fileName".equals(sortColumn)) {
                orderBy = "E.FILE_NAME" + a_x;
            } 

            if ("cTime".equals(sortColumn)) {
                orderBy = "E.CTIME" + a_x;
            } 

            if ("fileSize".equals(sortColumn)) {
                orderBy = "E.FILESIZE" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("fileId", "FILEID");
        addColumn("topId", "TOPID");
        addColumn("fileName", "FILE_NAME");
        addColumn("cTime", "CTIME");
        addColumn("fileSize", "FILESIZE");
    }

}