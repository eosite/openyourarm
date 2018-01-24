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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.DataExportService;

@Service("dataExportService")
@Transactional(readOnly = true)
public class DataExportServiceImpl implements DataExportService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataExportMapper dataExportMapper;

	public DataExportServiceImpl() {

	}

	public int count(DataExportQuery query) {
		return dataExportMapper.getDataExportCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dataExportMapper.deleteDataExportById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				dataExportMapper.deleteDataExportById(id);
			}
		}
	}

	public DataExport getDataExport(Long id) {
		if (id == null) {
			return null;
		}
		DataExport dataExport = dataExportMapper.getDataExportById(id);
		return dataExport;
	}

	public DataExport getDataExportByUserId(String userId) {
		if (userId == null) {
			return null;
		}
		DataExport dataExport = dataExportMapper.getDataExportByUserId(userId);
		return dataExport;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataExportCountByQueryCriteria(DataExportQuery query) {
		return dataExportMapper.getDataExportCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataExport> getDataExportsByQueryCriteria(int start, int pageSize, DataExportQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataExport> rows = sqlSessionTemplate.selectList("getDataExports", query, rowBounds);
		return rows;
	}

	public List<DataExport> list(DataExportQuery query) {
		List<DataExport> list = dataExportMapper.getDataExports(query);
		return list;
	}

	@Transactional
	public void save(DataExport dataExport) {
		if (dataExport.getId() == null) {
			dataExport.setId(idGenerator.nextId("SYS_DATA_EXPORT"));
			dataExport.setCreateTime(new Date());
			dataExportMapper.insertDataExport(dataExport);
		} else {
			dataExport.setUpdateTime(new Date());
			dataExportMapper.updateDataExport(dataExport);
		}
	}

	@javax.annotation.Resource
	public void setDataExportMapper(DataExportMapper dataExportMapper) {
		this.dataExportMapper = dataExportMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
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

	@Transactional
	public void updateExportStatus(DataExport model) {
		dataExportMapper.updateDataExportExportStatus(model);
	}

}
