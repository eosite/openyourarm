package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FileAtt;
import com.glaf.isdp.query.FileAttQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FileAttMapper")
public interface FileAttMapper {

	void deleteFileAtts(FileAttQuery query);

	void deleteFileAttById(String id);

	FileAtt getFileAttById(String id);

	int getFileAttCount(FileAttQuery query);

	List<FileAtt> getFileAtts(FileAttQuery query);

	void insertFileAtt(FileAtt model);

	void updateFileAtt(FileAtt model);

}
