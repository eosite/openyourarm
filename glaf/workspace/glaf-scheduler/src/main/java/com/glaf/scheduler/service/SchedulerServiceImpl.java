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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Parameter;
import com.glaf.core.base.Scheduler;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.UUID32;

import com.glaf.scheduler.domain.ExSchedulerParam;
import com.glaf.scheduler.mapper.ExSchedulerMapper;
import com.glaf.scheduler.mapper.ExSchedulerParamMapper;
import com.glaf.scheduler.query.ExSchedulerQuery;

@Service("com.glaf.scheduler.service.schedulerService")
@Transactional(readOnly = true)
public class SchedulerServiceImpl implements ISchedulerService {
	protected final static Log logger = LogFactory.getLog(SchedulerServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ExSchedulerMapper exSchedulerMapper;

	protected ExSchedulerParamMapper exSchedulerParamMapper;

	public SchedulerServiceImpl() {

	}

	public int count(ExSchedulerQuery query) {
		return exSchedulerMapper.getExSchedulerCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		exSchedulerMapper.deleteExSchedulerById(id);
		exSchedulerParamMapper.deleteExSchedulerParamsByTaskId(id);
	}

	@Transactional
	public void deleteByIds(List<String> rowIds) {
		ExSchedulerQuery query = new ExSchedulerQuery();
		query.rowIds(rowIds);
		exSchedulerMapper.deleteExSchedulers(query);
	}

	@Transactional
	public void deleteScheduler(String taskId) {
		exSchedulerMapper.deleteExSchedulerByTaskId(taskId);
		exSchedulerParamMapper.deleteExSchedulerParamsByTaskId(taskId);
	}

	public List<Scheduler> getAllSchedulers() {
		ExSchedulerQuery query = new ExSchedulerQuery();
		return this.list(query);
	}

	public Scheduler getScheduler(String id) {
		Scheduler scheduler = exSchedulerMapper.getExSchedulerById(id);
		if (scheduler == null) {
			scheduler = this.getSchedulerByTaskId(id);
		}
		if (scheduler != null) {
			List<ExSchedulerParam> params = exSchedulerParamMapper.getExSchedulerParamsByTaskId(scheduler.getTaskId());
			if (params != null && !params.isEmpty()) {
				for (ExSchedulerParam param : params) {
					scheduler.getJobDataMap().put(param.getKeyName(), param);
				}
			}
		}
		return scheduler;
	}

	public Scheduler getSchedulerByTaskId(String taskId) {
		Scheduler scheduler = exSchedulerMapper.getExSchedulerByTaskId(taskId);
		if (scheduler != null) {
			List<ExSchedulerParam> params = exSchedulerParamMapper.getExSchedulerParamsByTaskId(scheduler.getTaskId());
			if (params != null && !params.isEmpty()) {
				for (ExSchedulerParam param : params) {
					scheduler.getJobDataMap().put(param.getKeyName(), param);
				}
			}
		}
		return scheduler;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSchedulerCountByQueryCriteria(ExSchedulerQuery query) {
		return exSchedulerMapper.getExSchedulerCount(query);
	}

	public List<Scheduler> getSchedulers(String taskType) {
		ExSchedulerQuery query = new ExSchedulerQuery();
		query.taskType(taskType);
		return this.list(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Scheduler> getSchedulersByQueryCriteria(int start, int pageSize, ExSchedulerQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Scheduler> rows = sqlSessionTemplate.selectList("getExSchedulers", query, rowBounds);
		return rows;
	}

	public List<Scheduler> getUserSchedulers(String createBy) {
		ExSchedulerQuery query = new ExSchedulerQuery();
		query.createBy(createBy);
		return this.list(query);
	}

	public List<Scheduler> list(ExSchedulerQuery query) {
		List<Scheduler> list = exSchedulerMapper.getExSchedulers(query);
		return list;
	}

	@Transactional
	public void locked(String taskId, int locked) {
		Scheduler model = this.getSchedulerByTaskId(taskId);
		if (model != null) {
			model.setLocked(locked);
			exSchedulerMapper.updateExScheduler(model);
		}
	}

	@Transactional
	public void save(Scheduler model) {
		if (StringUtils.isEmpty(model.getId())) {
			if (StringUtils.isEmpty(model.getTaskId())) {
				model.setTaskId(UUID32.getUUID());
			}
			if (model.getStartDate() == null) {
				model.setStartDate(new Date());
			}
			if (model.getEndDate() == null) {
				model.setEndDate(DateUtils.toDate("2099-12-31"));
			}
			if (model.getRepeatInterval() <= 0) {
				model.setRepeatInterval(3600);
			}
			model.setBusinessType("EX");
			model.setCreateDate(new Date());
			model.setId(idGenerator.getNextId());
			exSchedulerMapper.insertExScheduler(model);
		} else {
			exSchedulerMapper.updateExScheduler(model);
		}
		exSchedulerParamMapper.deleteExSchedulerParamsByTaskId(model.getTaskId());
		Collection<Parameter> params = model.getJobDataMap().values();
		if (params != null && !params.isEmpty()) {
			for (Parameter param : params) {
				if (param instanceof ExSchedulerParam) {
					ExSchedulerParam p = (ExSchedulerParam) param;
					if (StringUtils.isEmpty(p.getId())) {
						p.setId(idGenerator.getNextId());
						p.setTaskId(model.getTaskId());
						exSchedulerParamMapper.insertExSchedulerParam(p);
					}
				}
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

	@javax.annotation.Resource(name = "com.glaf.scheduler.mapper.ExSchedulerMapper")
	public void setExSchedulerMapper(ExSchedulerMapper exSchedulerMapper) {
		this.exSchedulerMapper = exSchedulerMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.scheduler.mapper.ExSchedulerParamMapper")
	public void setExSchedulerParamMapper(ExSchedulerParamMapper exSchedulerParamMapper) {
		this.exSchedulerParamMapper = exSchedulerParamMapper;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	public void update(Scheduler model) {
		exSchedulerMapper.updateExScheduler(model);
	}

}