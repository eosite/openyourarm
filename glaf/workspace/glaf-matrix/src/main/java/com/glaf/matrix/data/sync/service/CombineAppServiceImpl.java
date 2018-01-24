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

package com.glaf.matrix.data.sync.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.matrix.data.sync.domain.CombineApp;
import com.glaf.matrix.data.sync.mapper.CombineAppMapper;
import com.glaf.matrix.data.sync.query.CombineAppQuery;

@Service("com.glaf.matrix.data.sync.service.combineAppService")
@Transactional(readOnly = true)
public class CombineAppServiceImpl implements CombineAppService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CombineAppMapper combineAppMapper;

	public CombineAppServiceImpl() {

	}

	public int count(CombineAppQuery query) {
		return combineAppMapper.getCombineAppCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			combineAppMapper.deleteCombineAppById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				combineAppMapper.deleteCombineAppById(id);
			}
		}
	}

	public CombineApp getCombineApp(Long id) {
		if (id == null) {
			return null;
		}
		CombineApp combineApp = combineAppMapper.getCombineAppById(id);
		return combineApp;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCombineAppCountByQueryCriteria(CombineAppQuery query) {
		return combineAppMapper.getCombineAppCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CombineApp> getCombineAppsByQueryCriteria(int start, int pageSize, CombineAppQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CombineApp> rows = sqlSessionTemplate.selectList("getCombineApps", query, rowBounds);
		return rows;
	}

	public List<CombineApp> list(CombineAppQuery query) {
		List<CombineApp> list = combineAppMapper.getCombineApps(query);
		return list;
	}

	@Transactional
	public void save(CombineApp combineApp) {
		if (combineApp.getId() == 0) {
			combineApp.setId(idGenerator.nextId("SYS_COMBINE_APP"));
			combineApp.setCreateTime(new Date());
			combineAppMapper.insertCombineApp(combineApp);
		} else {
			combineAppMapper.updateCombineApp(combineApp);
		}
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.data.sync.mapper.CombineAppMapper")
	public void setCombineAppMapper(CombineAppMapper combineAppMapper) {
		this.combineAppMapper = combineAppMapper;
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

}
