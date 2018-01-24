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

package com.glaf.scheduler.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.scheduler.domain.ExSchedulerLog;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.scheduler.mapper.ExSchedulerLogMapper;
import com.glaf.scheduler.query.ExSchedulerLogQuery;
import com.glaf.scheduler.service.ISchedulerLogService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.UUID32;

@Service("com.glaf.scheduler.service.schedulerLogService")
@Transactional(readOnly = true)
public class SchedulerLogServiceImpl implements ISchedulerLogService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ExSchedulerLogMapper exSchedulerLogMapper;

	public SchedulerLogServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ExSchedulerLog> list) {
		for (ExSchedulerLog schedulerLog : list) {
			schedulerLog.setId(UUID32.getUUID());
			schedulerLog.setCreateDate(new Date());
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			exSchedulerLogMapper.bulkInsertExSchedulerLog_oracle(list);
		} else {
			exSchedulerLogMapper.bulkInsertExSchedulerLog(list);
		}
	}

	public int count(ExSchedulerLogQuery query) {
		return exSchedulerLogMapper.getExSchedulerLogCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			exSchedulerLogMapper.deleteExSchedulerLogById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				exSchedulerLogMapper.deleteExSchedulerLogById(id);
			}
		}
	}

	@Transactional
	public void deleteSchedulerLogByTaskId(String taskId) {
		exSchedulerLogMapper.deleteExSchedulerLogByTaskId(taskId);
	}

	public ExSchedulerLog getSchedulerLog(String id) {
		if (id == null) {
			return null;
		}
		ExSchedulerLog schedulerLog = exSchedulerLogMapper.getExSchedulerLogById(id);
		return schedulerLog;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSchedulerLogCountByQueryCriteria(ExSchedulerLogQuery query) {
		return exSchedulerLogMapper.getExSchedulerLogCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ExSchedulerLog> getSchedulerLogsByQueryCriteria(int start, int pageSize, ExSchedulerLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ExSchedulerLog> rows = sqlSessionTemplate.selectList("getExSchedulerLogs", query, rowBounds);
		return rows;
	}

	public List<ExSchedulerLog> list(ExSchedulerLogQuery query) {
		List<ExSchedulerLog> list = exSchedulerLogMapper.getExSchedulerLogs(query);
		return list;
	}

	@Transactional
	public void save(ExSchedulerLog schedulerLog) {
		if (StringUtils.isEmpty(schedulerLog.getId())) {
			schedulerLog.setId(UUID32.getUUID());
			schedulerLog.setCreateDate(new Date());
			exSchedulerLogMapper.insertExSchedulerLog(schedulerLog);
		} else {
			if (this.getSchedulerLog(schedulerLog.getId()) == null) {
				schedulerLog.setCreateDate(new Date());
				exSchedulerLogMapper.insertExSchedulerLog(schedulerLog);
			} else {
				exSchedulerLogMapper.updateExSchedulerLog(schedulerLog);
			}
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
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource(name = "com.glaf.scheduler.mapper.ExSchedulerLogMapper")
	public void setExSchedulerLogMapper(ExSchedulerLogMapper exSchedulerLogMapper) {
		this.exSchedulerLogMapper = exSchedulerLogMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
