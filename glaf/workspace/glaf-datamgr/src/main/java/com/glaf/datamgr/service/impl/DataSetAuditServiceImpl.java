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
import com.glaf.core.security.Authentication;
import com.glaf.core.dao.*;

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.DataSetAuditService;

@Service("dataSetAuditService")
@Transactional(readOnly = true)
public class DataSetAuditServiceImpl implements DataSetAuditService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSetAuditMapper dataSetAuditMapper;

	public DataSetAuditServiceImpl() {

	}

	public int count(DataSetAuditQuery query) {
		return dataSetAuditMapper.getDataSetAuditCount(query);
	}

	public List<DataSetAudit> list(DataSetAuditQuery query) {
		List<DataSetAudit> list = dataSetAuditMapper.getDataSetAudits(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataSetAuditCountByQueryCriteria(DataSetAuditQuery query) {
		return dataSetAuditMapper.getDataSetAuditCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataSetAudit> getDataSetAuditsByQueryCriteria(int start, int pageSize, DataSetAuditQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataSetAudit> rows = sqlSessionTemplate.selectList("getDataSetAudits", query, rowBounds);
		return rows;
	}

	public DataSetAudit getDataSetAudit(Long id) {
		if (id == null) {
			return null;
		}
		DataSetAudit dataSetAudit = dataSetAuditMapper.getDataSetAuditById(id);
		return dataSetAudit;
	}

	/**
	 * 根据数据集编号获取最新的对象
	 * 
	 * @return
	 */
	public DataSetAudit getLastestDataSetAudit(String datasetId) {
		if (datasetId == null) {
			return null;
		}
		DataSetAudit dataSetAudit = dataSetAuditMapper.getLastestDataSetAudit(datasetId);
		return dataSetAudit;
	}

	@Transactional
	public void save(DataSetAudit dataSetAudit) {
		if (dataSetAudit.getId() == null) {
			dataSetAudit.setId(idGenerator.nextId("SYS_DATASET_AUDIT"));
			dataSetAudit.setCreateTime(new Date());
			Integer version = dataSetAuditMapper.getDataSetAuditMaxVersionByDataSetId(dataSetAudit.getDatasetId());
			if (version == null)
				version = 0;
			version = version + 1;
			dataSetAudit.setVersion(version);
			try {
				dataSetAudit.setCreateBy(Authentication.getAuthenticatedActorId());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			dataSetAuditMapper.insertDataSetAudit(dataSetAudit);
		}
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
	public void setDataSetAuditMapper(DataSetAuditMapper dataSetAuditMapper) {
		this.dataSetAuditMapper = dataSetAuditMapper;
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
