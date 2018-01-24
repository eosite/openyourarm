/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.modules.file.service;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.base.BlobItem;
import com.glaf.core.dao.*;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.UUID32;
import com.glaf.base.modules.file.mapper.*;
import com.glaf.base.modules.file.domain.*;
import com.glaf.base.modules.file.query.*;

@Service("fileAttachmentService")
@Transactional(readOnly = true)
public class FileAttachmentServiceImpl implements FileAttachmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

 

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FileAttachmentMapper fileAttachmentMapper;

	protected IBlobService blobService;

	public FileAttachmentServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FileAttachment> list) {
		for (FileAttachment fileAttachment : list) {
			if (StringUtils.isEmpty(fileAttachment.getId())) {
				fileAttachment.setId(idGenerator.getNextId("T_ATTACHMENT"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			fileAttachmentMapper.bulkInsertFileAttachment_oracle(list);
		} else {
			fileAttachmentMapper.bulkInsertFileAttachment(list);
		}
	}

	public int count(FileAttachmentQuery query) {
		return fileAttachmentMapper.getFileAttachmentCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			blobService.deleteBlobByFileId(id);
			fileAttachmentMapper.deleteFileAttachmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				blobService.deleteBlobByFileId(id);
				fileAttachmentMapper.deleteFileAttachmentById(id);
			}
		}
	}

	public FileAttachment getFileAttachment(String id) {
		if (id == null) {
			return null;
		}
		FileAttachment fileAttachment = fileAttachmentMapper.getFileAttachmentById(id);
		return fileAttachment;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFileAttachmentCountByQueryCriteria(FileAttachmentQuery query) {
		return fileAttachmentMapper.getFileAttachmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FileAttachment> getFileAttachmentsByQueryCriteria(int start, int pageSize, FileAttachmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FileAttachment> rows = sqlSessionTemplate.selectList("getFileAttachments", query, rowBounds);
		return rows;
	}

	public List<FileAttachment> list(FileAttachmentQuery query) {
		List<FileAttachment> list = fileAttachmentMapper.getFileAttachments(query);
		return list;
	}

	@Transactional
	public void save(FileAttachment fileAttachment) {
		if (StringUtils.isEmpty(fileAttachment.getId())) {
			fileAttachment.setId(UUID32.getUUID());
			fileAttachment.setCreateDate(new Date());
			fileAttachment.setDeleteFlag(0);
			fileAttachmentMapper.insertFileAttachment(fileAttachment);
		} else {
			fileAttachment.setUpdateDate(new Date());
			fileAttachmentMapper.updateFileAttachment(fileAttachment);
		}

		if (fileAttachment.getBytes() != null) {
			BlobItem dataFile = new BlobItemEntity();
			dataFile.setLastModified(System.currentTimeMillis());
			dataFile.setCreateBy(fileAttachment.getCreateBy());
			dataFile.setFileId(fileAttachment.getId());
			dataFile.setPath(fileAttachment.getPath());
			dataFile.setFilename(fileAttachment.getFilename());
			dataFile.setName(fileAttachment.getName());
			dataFile.setType(fileAttachment.getType());
			dataFile.setStatus(0);
			dataFile.setServiceKey("file_attach_" + fileAttachment.getType());
			dataFile.setData(fileAttachment.getBytes());
			blobService.insertBlob(dataFile);
		}
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFileAttachmentMapper(FileAttachmentMapper fileAttachmentMapper) {
		this.fileAttachmentMapper = fileAttachmentMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
