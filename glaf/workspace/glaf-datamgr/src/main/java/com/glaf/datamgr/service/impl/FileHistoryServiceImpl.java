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

package com.glaf.datamgr.service.impl;

import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.dao.*;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.FileHistoryService;

@Service("fileHistoryService")
@Transactional(readOnly = true)
public class FileHistoryServiceImpl implements FileHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FileHistoryMapper fileHistoryMapper;

	public FileHistoryServiceImpl() {

	}

	public int count(FileHistoryQuery query) {
		return fileHistoryMapper.getFileHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FileHistory> getFileHistoriesByQueryCriteria(int start,
			int pageSize, FileHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FileHistory> rows = sqlSessionTemplate.selectList(
				"getFileHistories", query, rowBounds);
		return rows;
	}

	public FileHistory getFileHistory(String id) {
		if (id == null) {
			return null;
		}
		FileHistory fileHistory = null;
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			fileHistory = fileHistoryMapper.getFileHistoryById_postgres(id);
		} else {
			fileHistory = fileHistoryMapper.getFileHistoryById(id);
		}
		return fileHistory;
	}
	
	
	public FileHistory getBackupFileHistory(String fileId){
		FileHistoryQuery query = new FileHistoryQuery();
		query.referId(fileId);
		query.setType("pkg_backup");
		query.pkgStatus("BACKUP");
		List<FileHistory> list = fileHistoryMapper.getFileHistories(query);
		if(list != null && !list.isEmpty()){
			FileHistory bean = list.get(0);
			return this.getFileHistory(bean.getFileId());
		}
		return null;
	}

	public FileHistory getUpdatePkgFileHistory(String fileId){
		FileHistoryQuery query = new FileHistoryQuery();
		query.referId(fileId);
		query.setType("pkg_update");
		query.pkgStatus("UPDATE");
		List<FileHistory> list = fileHistoryMapper.getFileHistories(query);
		if(list != null && !list.isEmpty()){
			FileHistory bean = list.get(0);
			return this.getFileHistory(bean.getFileId());
		}
		return null;
	}

	
	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFileHistoryCountByQueryCriteria(FileHistoryQuery query) {
		return fileHistoryMapper.getFileHistoryCount(query);
	}

	public List<FileHistory> list(FileHistoryQuery query) {
		List<FileHistory> list = fileHistoryMapper.getFileHistories(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<FileHistory> listLastest(FileHistoryQuery query) {
		List<FileHistory> list = fileHistoryMapper
				.getLastestFileHistories(query);
		return list;
	}

	@Transactional
	public void save(FileHistory fileHistory) {
		FileHistoryQuery query = new FileHistoryQuery();
		if (fileHistory.getFileContent() != null) {
			String md5 = DigestUtils.md5Hex(fileHistory.getFileContent());
			fileHistory.setMd5(md5);
			fileHistory.setFileId(DigestUtils.md5Hex(fileHistory.getPath()));
			query.fileName(fileHistory.getFileName());
		}
		query.path(fileHistory.getPath());
		List<FileHistory> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			if (StringUtils.equals(fileHistory.getMd5(), list.get(0).getMd5())) {
				return;
			}
			fileHistory.setVersion(list.size() + 1);
		} else {
			fileHistory.setVersion(1);
		}
		if (fileHistory.getFileId() != null) {
			fileHistory.setFileId(fileHistory.getFileId() + "_"
					+ fileHistory.getVersion());
		}
		if (fileHistory.getFileId() == null) {
			fileHistory.setFileId(UUID32.getUUID());
		}
		fileHistory.setCreateTime(new Date());
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			fileHistoryMapper.insertFileHistory_postgres(fileHistory);
		} else {
			fileHistoryMapper.insertFileHistory(fileHistory);
		}
	}
	

	
	@Transactional
	public void saveUpdatePackage(FileHistory fileHistory) {
		fileHistory.setCreateTime(new Date());
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			fileHistoryMapper.insertFileHistory_postgres(fileHistory);
		} else {
			fileHistoryMapper.insertFileHistory(fileHistory);
		}
	}
	
	@Transactional
	public void update(FileHistory fileHistory){
		fileHistoryMapper.updateFileHistory(fileHistory);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFileHistoryMapper(FileHistoryMapper fileHistoryMapper) {
		this.fileHistoryMapper = fileHistoryMapper;
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
